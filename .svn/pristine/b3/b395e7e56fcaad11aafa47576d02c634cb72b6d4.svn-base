package com.boyaa.mf.service.task;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.boyaa.base.utils.Constants;
import com.boyaa.mf.entity.task.ProcessStatusEnum;
import com.boyaa.mf.entity.task.Task;

/**
 * @类名 : ProcessQueue.java
 * @作者 : MarsHuang
 * @部门 : 德州扑克业务中心-开拓者工作室-公共组
 * @公司 : 博雅互动
 * @日期 : 2014-8-1 上午11:56:07
 * @描述 : 流程处理的队列
 */
@Component
public class ProcessQueue {
	static Logger taskLogger = Logger.getLogger("taskLogger");
	static Logger errorLogger = Logger.getLogger("errorLogger");

	private static BlockingQueue<Integer> queue;
	private static volatile boolean isRunning;
	private static Integer currentTaskId;
	private static Thread exceteProcess = null;
	
	@Autowired
	private TaskService taskService;
	
//	private static class SingletonHolder {
//		private static final ProcessQueue INSTANCE = new ProcessQueue();
//	}
//
//	public final static ProcessQueue getInstance() {
//		return SingletonHolder.INSTANCE;
//	}

//	private ProcessQueue() {
//		queue = new LinkedBlockingQueue<Integer>();
//		isRunning = false;
//		
//		exceteProcess = new Thread(new ExceteProcess());
//		exceteProcess.start();
//
//		taskLogger.info("ProcessQueue:auto_running_server:"+com.boyaa.constants.Constants.AUTO_RUNNING_SERVER+",ip:"+Constants.ip);
//		if(com.boyaa.constants.Constants.AUTO_RUNNING_SERVER!=null && com.boyaa.constants.Constants.AUTO_RUNNING_SERVER.equals(Constants.ip)){
//			taskLogger.info("生产环境，把需要执行的任务加入到队列中");
//			offerTasks(true);
//		}
//	}
	
	@SuppressWarnings("unused")
	private void init() {
		queue = new LinkedBlockingQueue<Integer>();
		isRunning = false;
		
		exceteProcess = new Thread(new ExceteProcess());
		exceteProcess.start();
	
		taskLogger.info("ProcessQueue:auto_running_server:"+com.boyaa.mf.constants.Constants.AUTO_RUNNING_SERVER+",ip:"+Constants.ip);
		if(com.boyaa.mf.constants.Constants.AUTO_RUNNING_SERVER!=null && com.boyaa.mf.constants.Constants.AUTO_RUNNING_SERVER.equals(Constants.ip)){
			taskLogger.info("生产环境，把需要执行的任务加入到队列中");
			offerTasks(true);
		}
	}
	
	/**
	 * 把所有要执行的任务加入到队列中
	 */
	public void offerTasks(boolean isCleanOld){
		if(isCleanOld){
			isRunning = false;
			queue.clear();
		}
		
		List<Task> tasks = null;
		tasks = taskService.findTargetTask(Integer.MAX_VALUE);
		if(tasks!=null && tasks.size()>0){
			for (Task task : tasks) {
				this.offer(task.getId());
			}
		}
	}
	
	class ExceteProcess implements Runnable {
		@Override
		public void run() {
			while (!isRunning) {
				isRunning = true;
				try {
					Integer taskId = queue.poll(2, TimeUnit.SECONDS);
					currentTaskId = taskId;
					if (null != taskId) {
						taskLogger.info("任务(" + taskId + ")正被执行,现在执行队列中的任务数为:" + queue.size());

						// 处理
						Task task = null;
						boolean result = false;
						task = taskService.findById(taskId);
						
						if((task.getReExecuteCount()==null || task.getReExecuteCount() <=3) && 
								(task.getStatus()!=null && (task.getStatus()==ProcessStatusEnum.NOT_EXECUTED.getValue() || task.getStatus()==ProcessStatusEnum.EXECUTION_ERROR.getValue()))){
							result = taskService.taskExecute(task,null,TaskManager.getInstense().getUUID());
						}else{
							taskLogger.info("任务(" + taskId + ")状态为不可执行的状态,直接返回,现在执行队列中的任务数为:" + queue.size());
							result = true;
						}

						if (!result) {// 如果执行失败,重新添加到队列中
							queue.offer(taskId);
							taskLogger.info("任务(" + taskId + ")执行失败,重新加入队列中,现在执行队列中的任务数为:" + queue.size());
						}
					}
					currentTaskId = null;
					isRunning = false;
				} catch (InterruptedException e) {
					errorLogger.error(e.getMessage());
				}
			}
		}
	}

	public Integer stopRunning(){
		Integer stopTaskId = null;
		if(currentTaskId!=null){
			stopTaskId = currentTaskId.intValue();
			taskService.stopExcute(currentTaskId);
			remove(currentTaskId);
			currentTaskId=null;
		}
		isRunning = false;
		exceteProcess.interrupt();
		exceteProcess = new Thread(new ExceteProcess());
		exceteProcess.start();
		
		return stopTaskId;
	}

	public void stopRunningByTaskId(int taskId) {
		if(currentTaskId!=null && taskId==currentTaskId){
			isRunning = false;
			remove(taskId);
			currentTaskId=null;
			
			exceteProcess.interrupt();
			exceteProcess = new Thread(new ExceteProcess());
			exceteProcess.start();
		}
	}
	
	public int getQueueSize(){
		return queue.size();
	}
	
	public void offer(int id) {
		taskLogger.info("任务(" + id + ")添加入队列,现在执行队列中的任务数为:" + queue.size());
		queue.offer(id);
	}
	
	public void remove(int id) {
		queue.remove(id);
		taskLogger.info("删除后队列中的任务(" + id + ")现在执行队列中的任务数为:" + queue.size());
	}
	
	public int waitCount(int taskId){
		Object[] taskIds = queue.toArray();
		int index=1;
		boolean inQueue=false;
		if(taskIds!=null){
			for(int i=0;i<taskIds.length;i++){
				int id = (Integer)taskIds[i];
				if(id==taskId){
					inQueue=true;
					break;
				}
				index ++;
			}
		}
		return inQueue?index:0;
	}
	
	/**
	 * 把队列中的某个ID移至头部,优先执行
	 * @param taskId
	 */
	public void move2Head(int taskId){
		taskLogger.info("把(" + taskId + ")移至头部优先执行");
		Object[] taskIds = queue.toArray();
		
		if(taskIds!=null){
			queue.clear();
			offer(taskId);
			for(int i=0;i<taskIds.length;i++){
				int id = (Integer)taskIds[i];
				
				if(id!=taskId){
					offer(id);
				}
			}
		}else{
			offer(taskId);
		}
	}
	
	/**
	 * 把队列中的某个ID移动尾部,延迟执行
	 * @param taskId
	 */
	public void move2Tail(int taskId){
		taskLogger.info("把(" + taskId + ")移至尾部延迟执行");
		Object[] taskIds = queue.toArray();
		
		if(taskIds!=null){
			queue.clear();
			for(int i=0;i<taskIds.length;i++){
				int id = (Integer)taskIds[i];
				if(id!=taskId){
					offer(id);
				}
			}
			offer(taskId);
		}else{
			offer(taskId);
		}
	}
}
