package com.log.processor.dtos;

import lombok.Data;

@Data
public class LogFilter {
	private int page;
	private int size;
	private String idService;
	private String level;
	private String keySearch;
	private Long logTime;
	
}
