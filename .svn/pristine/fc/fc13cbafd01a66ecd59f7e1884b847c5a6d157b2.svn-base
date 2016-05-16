package com.boyaa.mf.executor;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;

/** 
 * @author huangwx 
 * @version 创建时间：Jun 17, 2015 9:39:01 AM 
 */
@SuppressWarnings("all")
public class ExecutorManager {
	static Logger logger = Logger.getLogger(ExecutorManager.class);
	private static long THREE_HOURS = 3*60*60*1000;
	private static ExecutorManager instense;
	private static ExecutorService executor = Executors.newFixedThreadPool(3);
	private static Map<Integer, FutureBean> futureMap = new ConcurrentHashMap<Integer, FutureBean>();
	private static Lock lock = new ReentrantLock();
	static{
		new Thread(new Monitor()).start();
	}
	public static ExecutorManager getInstense(){
		if(instense==null){
			instense = new ExecutorManager();
		}
		return instense;
	}
	
	public void execute(int taskId, String uuid){
		Future future = executor.submit(new Executor(this,taskId,uuid));
		setBean(taskId, future, 0);
	}
	
	public void setBean(int taskId,Future future,int pos){
		try {
			lock.lock();
			FutureBean bean = null;
			if(pos==0){//主线程调用
				if(futureMap.containsKey(taskId)){//子线程先执行了 所以存在
					bean = futureMap.get(taskId);
					bean.setFuture(future);
					bean.setDate(new Date());
					bean.setStatus(1);
				}else{//主线程先执行 所以不存在
					bean = new FutureBean(future, new Date(), 0);
				}
			}else{//子线程调用
				if(futureMap.containsKey(taskId)){//主线程先执行了 所以存在
					bean = futureMap.get(taskId);
					bean.setDate(new Date());
					bean.setStatus(1);
				}else{//子线程先执行 所以不存在
					bean = new FutureBean(future, new Date(), 0);
				}
			}
			futureMap.put(taskId, bean);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			lock.unlock();
		}
		
	}
	
	//监听线程
	static class Monitor implements Runnable{
		@Override
		public void run() {
			while(true){
				try {
					if(!futureMap.isEmpty()){
						logger.info("监听线程中有"+futureMap.size()+"个结果");
						Iterator<Integer> it = futureMap.keySet().iterator();
						Date date = new Date();
						while(it.hasNext()){
							int taskId = it.next();
							FutureBean bean = futureMap.get(taskId);
							if(bean.getStatus()!=1){//子线程还没执行 先不处理
								continue;
							}
							Future future = bean.getFuture();
							if(future!=null){
								if(future.isDone()){//任务已经完成了 从map里清除
									logger.info("任务已经处理完成,移除taskId="+taskId+"的监听bean!!");
									it.remove();
									continue;
								}
								if(!future.isDone() && (date.getTime()-bean.getDate().getTime()>=THREE_HOURS)){//任务还没完成 并且已经超过3小时了 中断任务
									boolean result = future.cancel(true);
									logger.info("任务执行超时,移除taskId="+taskId+"的监听bean!!,结果:"+result);
									it.remove();
									continue;
								}
							}
						}
					}
					Thread.sleep(30*1000);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
