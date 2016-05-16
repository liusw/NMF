package com.boyaa.mf.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.boyaa.service.FileService;

public class ScanFileJob implements Job {
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		FileService fileService = new FileService();
		fileService.scanFile();
	}
}