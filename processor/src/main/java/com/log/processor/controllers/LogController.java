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
import com.log.processor.dtos.Response;
import com.log.processor.model.ParsedLogModel;
import com.log.processor.service.impl.LogService;

@RestController
@RequestMapping("api/v1/")
public class LogController {
	
	@Autowired
	private LogService logService;
	
	@GetMapping(path = "logs")
	public Object getLogs(	@RequestParam String idService,
							@RequestParam int page, 
							@RequestParam int size,
							@RequestParam(required = false) String level,
							@RequestParam(required = false) String keySearch,
							@RequestParam(required = false) Long logTime) {
		Response<Object> response = new Response<Object>();
		try {
			LogFilter logFilter = new LogFilter();
			logFilter.setIdService(idService);
			logFilter.setLevel(level);
			logFilter.setPage(page);
			logFilter.setSize(size);
			logFilter.setKeySearch(keySearch == null ? null : keySearch);
			logFilter.setLogTime(logTime);
			
			
			List<ParsedLogModel> data = logService.findLogs(logFilter);
			response.setStatus(202);
			response.setMessage("Thành công");
			response.setResult(data);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}catch(Exception e) {
			response.setStatus(HttpStatus.BAD_GATEWAY.hashCode());
			response.setMessage("Không thành công");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}
	
	
	@GetMapping(path = "services")
	public Object getServices() {
		Response<Object> response = new Response<Object>();
		try {
			response.setStatus(202);
			response.setMessage("Thành công");
			response.setResult(logService.getListInfoServices());
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}catch(Exception e) {
			response.setStatus(HttpStatus.BAD_GATEWAY.hashCode());
			response.setMessage("Không thành công");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}
	
	
}
