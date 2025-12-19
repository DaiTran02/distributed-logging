package com.log.processor.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.log.processor.model.InfoService;

@Repository
public interface InfoServiceRepository extends ElasticsearchRepository<InfoService, String>{
	InfoService findByServiceName(String nameService);
}
