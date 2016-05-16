package com.boyaa.mf.service.task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.boyaa.base.utils.DateUtil;
import com.boyaa.mf.constants.Constants;
import com.boyaa.mf.entity.task.AutoRunning;
import com.boyaa.mf.executor.ExecutorManager;

/**
 * @描述 : 自动执行处理的队列
 * @作者 : DarcyZeng
 * @日期 : 2014-11-14
 */
@Component
public class AutorunQueue {
	static Logger logger = Logger.getLogger(AutorunQueue.class);
	static Logger errorLogger = Logger.getLogger("errorLogger");
	private static BlockingQueue<String> queue;
	private Thread exceteProcess = null;
	
	@Autowired
	private AutoRunningService autorunService;

//	private static class SingletonHolder {
//		private static final AutorunQueue INSTANCE = new AutorunQueue();
//	}
//
//	public final static AutorunQueue getInstance() {
//		return SingletonHolder.INSTANCE;
//	}
	
//	private AutorunQueue() {
//		queue = new LinkedBlockingQueue<String>();
//		exceteProcess = new Thread(new ExceteProcess());
//		exceteProcess.start();
//	}
	
	@SuppressWarnings("unused")
	private void init() {
		queue = new LinkedBlockingQueue<String>();
		exceteProcess = new Thread(new ExceteProcess());
		exceteProcess.start();
	}
	
	class ExceteProcess implements Runnable {
		@Override
		public void run() {
			while (true) {
				try {
					if(queue.size() > 0){
						logger.info("现在自动队列中的任务数为:" + queue.size());
						String execute = queue.poll(2, TimeUnit.SECONDS);
						logger.info("现在执行自动队列中的:" + execute+"任务");
						if(execute!=null){
							String[] task = execute.split("_");
							ExecutorManager.getInstense().execute(Integer.parseInt(task[0]),task[1]);
						}
					}else{
						Thread.sleep(60 * 1000);
					}
				}catch(Exception e){
					errorLogger.error(e.getMessage());
				}
			}
		}
	}
	
	public void offer(int id,String uuid) {
		if(!queue.contains(id)){
			try {
				boolean add = queue.offer(id+"_"+uuid, 3, TimeUnit.SECONDS);
				if(!add){
					logger.info("加入自动任务("+ id + ")失败.");
				}
			} catch (InterruptedException e) {
				errorLogger.error("加入自动任务("+ id + ")失败:" + e.getMessage());
			}
			logger.info("任务(" + id + ")添加入队列,现在执行队列中的任务数为:" + queue.size());
		}else{
			logger.info("任务(" + id + ")已经在队列中,不再添加.");
		}
	}
	
	public void offer(AutoRunning autorunInfo){
		if(autorunInfo != null){
			TaskManager manager = TaskManager.getInstense();
			int yesterDay = Integer.parseInt(DateUtil.addDay(DateUtil.DATE_FORMAT, -1));
			int hour = Integer.parseInt(DateUtil.addDay(Constants.BRUSH_RUN_HOUR_FORMAT, 0));//当前小时数
			int lastDay = 0;//上一次执行日期
			String lastTime = autorunInfo.getLastTime();
			if(StringUtils.isNotBlank(lastTime) && !"0".equals(lastTime)){
				try{
					lastDay = Integer.parseInt(DateUtil.convertPattern(lastTime, Constants.BRUSH_LAST_TIME_FORMAT, "yyyyMMdd"));
				}catch(Exception e){
					errorLogger.error(e.getMessage());
				}
			}
			String uuid = manager.getUUID();
			logger.info("自动任务（"+ autorunInfo.getId() + "）run_hour=" + autorunInfo.getRunHour() + ",hour=" + hour + ",lastDay=" + lastDay + ",yesterDay=" + yesterDay);
			if(lastDay <= yesterDay){
				if(hour >= Integer.parseInt(autorunInfo.getRunHour())){
					logger.info("自动任务（"+ autorunInfo.getId() + "）准备加入队列中...");
					manager.putTime(uuid, 0);
					manager.putTM(uuid, yesterDay);
					offer(autorunInfo.getTaskId(),uuid);
					autorunInfo.setLastTime(DateUtil.addDay(Constants.BRUSH_LAST_TIME_FORMAT, 0));
					
					autorunService.updateLastTime(autorunInfo);
					logger.info("更新自动任务（"+ autorunInfo.getId() + "）执行时间：" + autorunInfo.getLastTime());
				}
			}else{
				if(hour >= Integer.parseInt(autorunInfo.getRunHour()) && autorunInfo.getHours()!=0){
					try {
						long lastExecTime = DateUtil.getStartTimestamp(autorunInfo.getLastTime(), new SimpleDateFormat(Constants.BRUSH_LAST_TIME_FORMAT), null);
						if(new Date().getTime()-lastExecTime*1000>autorunInfo.getHours()*60*60*1000){//又该执行了
							manager.putTime(uuid, autorunInfo.getDays());
							logger.info("自动任务（"+ autorunInfo.getId() + "）又准备加入队列中...");
							manager.putTM(uuid, Integer.parseInt(DateUtil.addDay(DateUtil.DATE_FORMAT, autorunInfo.getDays()-1)));
							offer(autorunInfo.getTaskId(),uuid);
							autorunInfo.setLastTime(DateUtil.addDay(Constants.BRUSH_LAST_TIME_FORMAT, 0));
							autorunService.updateLastTime(autorunInfo);
							logger.info("再次更新自动任务（"+ autorunInfo.getId() + "）执行时间：" + autorunInfo.getLastTime());
						}
					} catch (ParseException e) {
						logger.error("时间解析错误:"+e.getMessage());
						e.printStackTrace();
					}
				}
			}
		}
	}
}
