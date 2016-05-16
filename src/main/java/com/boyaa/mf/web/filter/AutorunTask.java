package com.boyaa.mf.web.filter;

import java.util.TimerTask;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;

import com.boyaa.mf.service.task.AutoRunningService;
import com.boyaa.mf.util.SpringBeanUtils;

@SuppressWarnings("all")
public class AutorunTask extends TimerTask {
	static Logger logger = Logger.getLogger(AutorunTask.class);
	static Logger errorLogger = Logger.getLogger("errorLogger");
	private static boolean isRunning = false;
	private ServletContext context = null;

	public AutorunTask() {
		
	}

	public AutorunTask(ServletContext context) {
		this.context = context;
	}

	public void run() {
		logger.info("定时添加任务!");
		if (!isRunning) {
			isRunning = true;
			AutoRunningService autoRunningService = null;
			autoRunningService = SpringBeanUtils.getBean("autoRunningService",AutoRunningService.class);
			autoRunningService.run();
			isRunning = false;
		}
	}

}