package com.boyaa.mf.executor;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONException;
import com.boyaa.base.mail.Mail;
import com.boyaa.mf.entity.task.ProcessStatusEnum;
import com.boyaa.mf.entity.task.Task;
import com.boyaa.mf.service.task.TaskService;
import com.boyaa.mf.util.SMSUtil;

/** 
 * @author huangwx 
 * @version 创建时间：Jun 17, 2015 9:52:55 AM 
 */
public class Executor implements Callable<String> {
	
	static Logger logger = Logger.getLogger(Executor.class);
	static Logger errorLogger = Logger.getLogger("errorLogger");
	
	@Autowired
	private TaskService taskService;
	
	private Integer taskId;
	private ExecutorManager manager;
	private String uuid;//执行的标识
	public Executor(ExecutorManager manager, Integer taskId, String uuid) {
		super();
		this.manager = manager;
		this.taskId = taskId;
		this.uuid = uuid;
	}
	@Override
	public String call() {
		logger.info("自动执行任务(" + taskId + ")正被执行");
		Task task = null;
		boolean result = false;
		try {
			task = taskService.findById(taskId);
			manager.setBean(taskId, null, 1);
			if(task != null){
				if(task.getStatus()!=null && (task.getStatus()==ProcessStatusEnum.NOT_EXECUTED.getValue() || task.getStatus()==ProcessStatusEnum.EXECUTION_ERROR.getValue() || task.getStatus()==ProcessStatusEnum.EXECUTION_END.getValue())){
					logger.info(Thread.currentThread().getName()+"现在已经在执行任务:"+taskId+"了\n");
					result = taskService.taskExecute(task,null,uuid);
				}else{
					logger.info("自动执行任务(" + taskId + ")状态为不可执行的状态,直接返回");
					result = true;
				}
			}
			if (!result) {//执行失败
				logger.info("自动执行任务(" + taskId + ")执行失败");
				StringBuilder sb = new StringBuilder();
				sb.append(task.getTaskName()).append("自动执行任务(" + taskId + ")执行失败").append("<br/><br/>");
				sb.append("问题反馈：黄万新(WatsonHuang@boyaa.com)");
				String email = "WatsonHuang@boyaa.com";
				new Mail().send("自动任务错误提示:"+task.getTaskName(),email,sb.toString(),null);
				SMSUtil.sendSMS("请注意，mf自动执行任务(" + taskId + ")执行失败");
			}
		}catch(Exception e){
			errorLogger.error(e.getMessage());
			if(e instanceof InterruptedException){
				//超时中断
				errorLogger.error("自动执行任务(" + taskId + ")执行失败!超时中断!");
				try {
					SMSUtil.sendSMS("请注意，mf自动执行任务(" + taskId + ")执行失败!原因:超时中断");
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
			}
		}
		return result+"";
	}
}
