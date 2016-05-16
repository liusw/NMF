package com.boyaa.mf.service.task;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.boyaa.base.utils.MRUtil;

/**
 * 停止job队列
 *
 * @作者 : huangyineng
 * @日期 : 2015-4-8  下午6:05:02
 */
public class StopJobQueue {
	static Logger taskLogger = Logger.getLogger("taskLogger");
	static Logger errorLogger = Logger.getLogger("errorLogger");

	private static BlockingQueue<String> queue;
	private static volatile boolean isRunning;
	private Thread executeProcess = null;

	private static class SingletonHolder {
		private static final StopJobQueue INSTANCE = new StopJobQueue();
	}

	public final static StopJobQueue getInstance() {
		return SingletonHolder.INSTANCE;
	}

	private StopJobQueue() {
		queue = new LinkedBlockingQueue<String>();
		isRunning = false;
		
		executeProcess = new Thread(new ExecuteProcess());
		executeProcess.start();
	}
	
	class ExecuteProcess implements Runnable {
		@Override
		public void run() {
			while (!isRunning) {
				isRunning = true;
				try {
					String uniqueKeyObject = queue.poll(2, TimeUnit.SECONDS);
					if (null != uniqueKeyObject) {
						String uniqueKey = uniqueKeyObject.split(",")[0];
//						long startTime =  Long.parseLong(uniqueKeyObject.split(",")[1]);
						
						//干掉job
						String qUniqueKey = "select \""+uniqueKey+"\" processUniqueKey,";
						MRUtil.stopRunningJobId(qUniqueKey);
						
						//判断些job是否小于半个钟,如果是,再加入队列中找他
//						if ((System.currentTimeMillis()-startTime)/1000<300) {// 如果执行失败,重新添加到队列中
//							queue.offer(uniqueKeyObject);
//							taskLogger.info("由于job可能还会运行,加入队列中重新判断,startTime="+startTime+",uniqueKey:"+qUniqueKey);
//							
//							Thread.sleep(1000*30);
//						}
					}
					isRunning = false;
				} catch (InterruptedException e) {
					errorLogger.error(e.getMessage());
				}
			}
		}
	}
	
	public void offer(String uniqueKey) {
		taskLogger.info("添加停止Job(" + uniqueKey + ")");
		queue.offer(uniqueKey);
	}
}