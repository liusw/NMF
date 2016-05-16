package com.boyaa.mf.service.task;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.boyaa.mf.entity.task.ProcessInfo;
import com.boyaa.servlet.ResultState;

@Service
public class DefaultProcessSerivce extends ProcessService {
	static Logger logger = Logger.getLogger(DefaultProcessSerivce.class);
	static Logger errorLogger = Logger.getLogger("errorLogger");
	static Logger fatalLogger = Logger.getLogger("fatalLogger");

	@Override
	public ResultState execute(ProcessInfo processInfo) {
		return otherExecute(processInfo);
	}
}