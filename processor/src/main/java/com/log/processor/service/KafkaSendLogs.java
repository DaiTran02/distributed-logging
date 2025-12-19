package com.log.processor.service;

import java.util.List;

import com.log.processor.model.LogModel;

public interface KafkaSendLogs {
	void sendData(String topic, List<LogModel> data);
}
