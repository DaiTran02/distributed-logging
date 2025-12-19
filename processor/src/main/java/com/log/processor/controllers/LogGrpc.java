package com.log.processor.controllers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.log.processor.model.LogModel;
import com.log.processor.proto.LogAck;
import com.log.processor.proto.LogBatch;
import com.log.processor.proto.LogCollectorGrpc;
import com.log.processor.proto.LogEntry;
import com.log.processor.service.KafkaSendLogs;

import io.grpc.stub.StreamObserver;

@Service
public class LogGrpc extends LogCollectorGrpc.LogCollectorImplBase{
private static Log log = LogFactory.getLog(LogGrpc.class);
	
	@Autowired
	private KafkaSendLogs kafkaSendLogs;
	
	@Override
	public void pushLog(LogEntry req,StreamObserver<LogAck> responseObserver) {
		log.info(req);
		LogAck logAck = LogAck.newBuilder().setMesssage("success").setSuccess(true).build();
		responseObserver.onNext(logAck);
		responseObserver.onCompleted();
	}
	
	
	@Override
	public void pushLogBatch(LogBatch req,StreamObserver<LogAck> responseObserver) {
		try {
			
			kafkaSendLogs.sendData("my-topic", req.getLogsList().stream().map(LogModel::new).toList());
			
			LogAck logAck = LogAck.newBuilder().setSuccess(true).setMesssage("success").build();
			responseObserver.onNext(logAck);
			responseObserver.onCompleted();
		}catch(Exception e) {
			LogAck logAck = LogAck.newBuilder().setSuccess(false).setMesssage("error").build();
			responseObserver.onNext(logAck);
			responseObserver.onCompleted();
		}
		
	}
		
		
}
