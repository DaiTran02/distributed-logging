package com.log.processor.model;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.Data;

@Data
@Document(indexName = "logs-alias")
public class ParsedLogModel {
	
	@Id
	private String id;
	
	@Field(type = FieldType.Date)
	private Instant logTime;
	
	@Field(type = FieldType.Keyword) 
    private String serviceName; 
    
    @Field(type = FieldType.Keyword)
    private String logLevel;

    @Field(type = FieldType.Keyword)
    private String threadName;
    
    @Field(type = FieldType.Keyword)
    private String loggerClass;
    
    @Field(type = FieldType.Text) 
    private String message;
    
    @Field(type = FieldType.Text)
    private String logOrigin;
    
    @Field(type = FieldType.Keyword)
    private String idService;
	
	
}
