package com.log.processor.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.Data;

@Document(indexName = "info_service")
@Data
public class InfoService {

	@Id
	private String id;

	@Field(type = FieldType.Keyword)
	private String serviceName;
	
}
