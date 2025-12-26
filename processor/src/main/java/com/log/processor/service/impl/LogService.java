package com.log.processor.service.impl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.stereotype.Service;

import com.log.processor.dtos.LogFilter;
import com.log.processor.model.InfoService;
import com.log.processor.model.LogModel;
import com.log.processor.model.ParsedLogModel;
import com.log.processor.repository.InfoServiceRepository;
import com.log.processor.repository.LogRepository;

@Service
public class LogService {
	@Autowired
	private LogRepository logRepository;

	@Autowired
	private InfoServiceRepository infoServiceRepository;

	@Autowired
	private ElasticsearchOperations elasticsearchOperations;


	public ParsedLogModel createNew(ParsedLogModel parsedLogModel,LogModel logOrigin) {
		try {
			InfoService service = infoServiceRepository.findByServiceName(parsedLogModel.getServiceName());
			if(service != null) {
				parsedLogModel.setIdService(service.getId());
			}else {
				InfoService newService = new InfoService();
				newService.setServiceName(parsedLogModel.getServiceName());
				InfoService info = infoServiceRepository.save(newService);
				parsedLogModel.setIdService(info.getId());
			}

			parsedLogModel.setLogOrigin(logOrigin.getMessages());

			ParsedLogModel log = logRepository.save(parsedLogModel);
			return log;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public List<ParsedLogModel> findLogs(LogFilter filter){
		try {
			List<Criteria> listCris = new ArrayList<Criteria>();
			listCris.add(new Criteria("idService").contains(filter.getIdService().equals("all") ? "" : filter.getIdService()));
			
			if(filter.getLevel() != null) {
				listCris.add(new Criteria("logLevel").contains(filter.getLevel().equals("all") ? "" : filter.getLevel()));
			}

			if(filter.getKeySearch() != null) {
				listCris.add(new Criteria("message").contains(filter.getKeySearch()));
			}
			
			if(filter.getLogTime() != null) {
				Instant instant = Instant.ofEpochMilli(filter.getLogTime());
				listCris.add(new Criteria("logTime").greaterThanEqual(instant));
			}

			PageRequest pageable = PageRequest.of(filter.getPage(), filter.getSize(),Sort.by(Sort.Direction.DESC,"logTime"));
			CriteriaQuery query = new CriteriaQuery(new Criteria().and(listCris.toArray(new Criteria[listCris.size()])));
			query.setPageable(pageable);
			return elasticsearchOperations.search(query, ParsedLogModel.class).map(SearchHit::getContent).stream().collect(Collectors.toList());
		}catch(Exception e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}

	public List<InfoService> getListInfoServices(){
		try {
			List<InfoService> listData = new ArrayList<InfoService>();
			infoServiceRepository.findAll().forEach(model->{
				listData.add(model);
			});
			return listData;
		}catch(Exception e) {
			return Collections.emptyList();
		}
	}

}
