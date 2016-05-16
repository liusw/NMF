package com.boyaa.mf.service.task;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONException;
import com.boyaa.mf.constants.Constants;
import com.boyaa.mf.entity.task.AutoRunning;
import com.boyaa.mf.entity.task.Task;
import com.boyaa.mf.mapper.task.AutoRunningMapper;
import com.boyaa.mf.service.AbstractService;

@Service
public class AutoRunningService extends AbstractService<AutoRunning,Integer> {
	static Logger logger = Logger.getLogger(AutoRunningService.class);
	
	@Autowired
	private AutoRunningMapper autoRunningMapper;
	@Autowired
	private TaskService taskService;
	@Autowired
	private AutorunQueue autorunQueue;

	public int getSameTemplateIdCount(Integer templateId) {
		return autoRunningMapper.getSameTemplateIdCount(templateId);
	}
	
	public void setTaskAutorun(AutoRunning autorunInfo){
		Task task = new Task();
		task.setId(autorunInfo.getTaskId());
		task.setType(Constants.TASK_TYPE_AUTORUN);
		int result = taskService.update(task);
		
		if(result>0){
			autoRunningMapper.deleteByTaskId(autorunInfo.getTaskId());
			super.insert(autorunInfo);
		}
	}

	public void cancelTaskAutorun(AutoRunning autorunInfo) {
		Task task = new Task();
		task.setId(autorunInfo.getTaskId());
		task.setType(Constants.TASK_TYPE_DEFAULT);
		int result = taskService.update(task);
		
		if(result>0){
			autoRunningMapper.deleteByTaskId(autorunInfo.getTaskId());
		}
	}
	
	/**
	 * 执行每天要自动执行到任务
	 * @throws JSONException 
	 * @throws SQLException 
	 */
	public void run(){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("status", Constants.STATUS_EFFACTIVE);
		
		List<AutoRunning> autorunInfos = super.findScrollDataList(params);
		if(autorunInfos != null){
			logger.info("获取所有自动执行任务:" + autorunInfos.size() + "个.");
		}else{
			logger.info("获取所有自动执行任务:0个");
		}
		for(AutoRunning autorunInfo : autorunInfos){
			autorunQueue.offer(autorunInfo);
		}
	}
	
	public void replaceAutorunInfo(AutoRunning autorunInfo){
		if(autorunInfo != null && autorunInfo.getTaskId() > 0){
			
			Map<String,Object> params = new HashMap<String, Object>();
			params.put("type", Constants.TASK_TYPE_DELETE);
			params.put("templateId",autorunInfo.getTemplateId());
			taskService.updateByAutoTask(params);
			
			params.clear();
			params.put("status",Constants.STATUS_UNEFFACTIVE);
			params.put("templateId",autorunInfo.getTemplateId());
			autoRunningMapper.updateByTemplateId(params);
		}
	}
	
	public void updateLastTime(AutoRunning autorunInfo){
		AutoRunning autoRunning = new AutoRunning();
		autoRunning.setId(autorunInfo.getId());
		autoRunning.setLastTime(autorunInfo.getLastTime());
		
		super.update(autoRunning);
	}
	
}