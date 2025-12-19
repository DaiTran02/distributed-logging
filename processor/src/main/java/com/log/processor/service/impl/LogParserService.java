package com.log.processor.service.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.log.processor.model.LogModel;
import com.log.processor.model.ParsedLogModel;

@Service
public class LogParserService {
	private static final String LOG_PATTERN =
		    "^(\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2},\\d{3})\\s+" + // time
		    "(TRACE|DEBUG|INFO|WARN|ERROR)\\s+" +                     // level
		    "(\\S+)\\s+" +                                           // logger
		    "\\[([^]]+)]\\s*" +                                      // thread
		    "(.*)$";                                                  // message (ANYTHING)
                                               // message
                                            // message
	    private static final Pattern PATTERN = Pattern.compile(LOG_PATTERN);
	    
	    private static final DateTimeFormatter DATE_FORMATTER = 
	            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss,SSS");
	    
	    
	    public ParsedLogModel parse(LogModel logModel) {
	        String raw = logModel.getMessages();
	        Matcher m = PATTERN.matcher(raw);

	        if (!m.find()) {
	            System.err.println("Log không khớp mẫu: " + raw);
	            return null;
	        }

	        ParsedLogModel p = new ParsedLogModel();
	        
	        LocalDateTime ldt = LocalDateTime.parse(m.group(1), DATE_FORMATTER);
	        
	        
	        p.setLogTime(ldt.atZone(ZoneId.of("Asia/Ho_Chi_Minh")).toInstant());
	        p.setLogLevel(m.group(2));
	        p.setLoggerClass(m.group(3));
	        p.setThreadName(m.group(4));
	        p.setMessage(m.group(5));
	        p.setServiceName(logModel.getServiceName());

	        return p;
	    }
}
