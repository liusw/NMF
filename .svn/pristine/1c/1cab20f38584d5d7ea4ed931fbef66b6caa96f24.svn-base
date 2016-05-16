package com.boyaa.mf.quartz;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.boyaa.mf.constants.Constants;
import com.boyaa.service.CommonService;

/**
 * 每天统计一次任务数
 *
 * @作者 : huangyineng
 * @日期 : 2015-7-13  下午6:08:25
 */
public class TongjiTaskJob implements Job {
	static Logger taskLogger = Logger.getLogger("taskLogger");
	
	@Override
	public void execute(JobExecutionContext job) throws JobExecutionException {
		if(Constants.AUTO_RUNNING_SERVER!=null && Constants.AUTO_RUNNING_SERVER.equals(Constants.ip)){
			taskLogger.info("每天统计一次任务数开始");
			CommonService commonService = new CommonService();
			commonService.insertMySQL("10000006");
			
			commonService.insertMySQL("10000009");
		}
	}
}