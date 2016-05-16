package com.boyaa.mf.service.task;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


/** 
 * @author huangwx 
 * @version 创建时间：Jul 9, 2015 5:01:20 PM
 */
public class TaskManager {
	private static TaskManager manager;
	private static Map<String, Integer> timeMap = new HashMap<String, Integer>();//key=taskId value=autorunInfo.days
	private static Map<String, Integer> tmMap = new HashMap<String, Integer>();//key=taskId value=autorunInfo.days
	public static TaskManager getInstense(){
		if(manager==null){
			manager = new TaskManager();
		}
		return manager;
	}
	
	public void putTime(String key, Integer value){
		timeMap.put(key, value);
	}
	
	
	public Integer getTime(String key){
		Integer time = timeMap.get(key);
		if(time==null){
			return 0;
		}
		return time;
	}
	
	public void remove(String key){
		timeMap.remove(key);
		tmMap.remove(key);
	}
	
	public void putTM(String key, Integer value){
		tmMap.put(key, value);
	}
	
	
	public Integer getTM(String key){
		return tmMap.get(key);
	}
	
	/**
	 * 获得一个随机32位字符串标识执行任务的那个调度
	 * @return
	 */
	public String getUUID(){
		return UUID.randomUUID().toString().replace("-", "");
	}
}
