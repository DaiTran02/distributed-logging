package com.log.processor.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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
			Criteria criteria = new Criteria("idService").contains(filter.getIdService());
			CriteriaQuery query = new CriteriaQuery(criteria);
			return elasticsearchOperations.search(query, ParsedLogModel.class).map(SearchHit::getContent).stream().collect(Collectors.toList());
		}catch(Exception e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}
	
}
