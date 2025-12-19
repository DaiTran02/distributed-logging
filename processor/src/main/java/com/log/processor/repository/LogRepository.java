package com.log.processor.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.log.processor.model.ParsedLogModel;

@Repository
public interface LogRepository extends ElasticsearchRepository<ParsedLogModel, String>{

}
