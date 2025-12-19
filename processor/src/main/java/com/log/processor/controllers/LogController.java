package com.log.processor.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.log.processor.dtos.LogFilter;
import com.log.processor.model.ParsedLogModel;
import com.log.processor.service.impl.LogService;

@RestController
@RequestMapping("api/v1/")
public class LogController {
	
	@Autowired
	private LogService logService;
	
	@GetMapping(path = "logs")
	public Object getLogs(@RequestParam String idService) {
		try {
			LogFilter logFilter = new LogFilter();
			logFilter.setIdService(idService);
			List<ParsedLogModel> data = logService.findLogs(logFilter);
			
			return ResponseEntity.status(HttpStatus.OK).body(data);
		}catch(Exception e) {
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST);
		}
	}
}
