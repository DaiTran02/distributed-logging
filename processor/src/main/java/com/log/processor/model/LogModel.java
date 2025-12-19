package com.log.processor.model;

import com.log.processor.proto.LogEntry;

import lombok.Data;

@Data
public class LogModel {
	private String serviceName;
	private String level;
	private Long timestamp;
	private String messages;
	
	public LogModel() {
		
	}
	
	public LogModel(LogEntry logEntry) {
		this.serviceName = logEntry.getServiceName();
		this.level = logEntry.getLevel();
		this.timestamp = logEntry.getTimestamp();
		this.messages = logEntry.getMessage();
	}
	
}
