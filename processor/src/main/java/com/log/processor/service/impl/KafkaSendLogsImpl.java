package com.log.processor.service.impl;

import java.util.List;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.log.processor.model.LogModel;
import com.log.processor.service.KafkaSendLogs;

@Service
public class KafkaSendLogsImpl implements KafkaSendLogs{
	
	private final KafkaTemplate<String, String> kafkaTemplate;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	public KafkaSendLogsImpl(KafkaTemplate<String, String> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}
	

	@Override
	public void sendData(String topic, List<LogModel> data) {
		try {
			System.out.println("<-------- Send log to kafka -------->");
			String stringJson = objectMapper.writeValueAsString(data);
			System.out.println(kafkaTemplate.send(topic, stringJson).get().getRecordMetadata());
		} catch(Exception e) {
			System.out.println("can't send log");
			e.printStackTrace();
		}
		
	}

}
