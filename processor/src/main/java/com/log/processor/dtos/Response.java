package com.log.processor.dtos;

import lombok.Data;

@Data
public class Response <T> {
	private int status;
	private String message;
	private T result;
}
