package com.log.processor.service.impl;

import java.lang.reflect.Type;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.log.processor.model.LogModel;
import com.log.processor.model.ParsedLogModel;
import com.log.processor.service.KafkaRecivedLogs;

@Component
public class KafkaRecivedLogsImpl implements KafkaRecivedLogs{
	
	private Gson gson = new Gson();
	private LogParserService parserService = new LogParserService();
	
	@Autowired
	private LogService logService;

	@KafkaListener(topics = "my-topic", groupId = "my-group")
	@Override
	public void revicedLog(String message) {
		try {
			
			Type listTypes = new TypeToken<List<LogModel>>() {
				private static final long serialVersionUID = 1L;}.getType();
			
			List<LogModel> data = gson.fromJson(message, listTypes);
			
			data.forEach(model->{
				ParsedLogModel parsedLogModel = parserService.parse(model);
				
				ParsedLogModel newLog = logService.createNew(parsedLogModel,model);
				if(newLog != null) {
					System.out.println("Thanh cong: "+newLog.getServiceName());
				}else {
					System.out.println("Oang cho");
				}
			});
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
