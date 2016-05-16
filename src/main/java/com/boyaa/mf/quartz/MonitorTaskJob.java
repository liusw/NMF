package com.boyaa.mf.quartz;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.boyaa.mf.constants.Constants;
import com.boyaa.mf.entity.task.Task;
import com.boyaa.mf.service.task.ProcessQueue;
import com.boyaa.mf.service.task.TaskService;

/**
 * 监控任务
 *
 * @作者 : huangyineng
 * @日期 : 2016-1-28  上午10:04:56
 */
public class MonitorTaskJob implements Job {
	static Logger taskLogger = Logger.getLogger("taskLogger");
	
	@Autowired
	private TaskService taskService;
	@Autowired
	private ProcessQueue processQueue;
	
	@Override
	public void execute(JobExecutionContext job) throws JobExecutionException {
		if(Constants.AUTO_RUNNING_SERVER!=null && Constants.AUTO_RUNNING_SERVER.equals(Constants.ip)){
			taskLogger.info("监控任务 开始");
			
			//检查某个执行任务的操作时间是否超过一个钟,如果是则杀掉
			List<Task> tasks = taskService.queryExecuteLongTimeTask();
			if(tasks!=null && tasks.size()>0){
				for(Task task:tasks){
					boolean result = taskService.stopExcute(task.getId());
					if(result){
						processQueue.stopRunningByTaskId(task.getId());
					}
					taskLogger.info("任务监控到<"+task.getId()+"_"+task.getTaskName()+">执行时间过长,已被停止.startTime="+task.getStartTime()+",endTime"+task.getEndTime());
				}
			}
			
			//如果没有正在执行的任务,清空执行队列
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("status",1);
			params.put("type", 0);
			int runningCount = taskService.findScrollDataCount(params);
			if(runningCount<=0 && processQueue.getQueueSize()>0){
				int taskId = processQueue.stopRunning();
				taskLogger.info("任务监控到没有任务在执行,停止队列中的流程.taskId="+taskId);
			}
			
		}
	}
}