package com.boyaa.mf.entity.task;

/**
 * 自动执行实体类
 * 
 * @author darcy
 * @date 2014.11.13
 */
public class AutoRunning {
	private Integer id;
	private Integer status;// 状态，0：有效；1：无效
	private String name;// 名称
	private Integer taskId;// 自动执行的任务id
	private Integer uid;// 用户id
	private String username;// 用户名
	private String runHour;// 执行时间
	private String lastTime;// 上次执行时间
	private Integer templateId;// 模板id
	private Integer hours;// 隔多长时间执行一次
	private Integer days;// 执行时 所有时间的变化量 默认是1 表示执行的是当天的数据 昨天是0 前天是-1 以此类推

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRunHour() {
		return runHour;
	}

	public void setRunHour(String runHour) {
		this.runHour = runHour;
	}

	public String getLastTime() {
		return lastTime;
	}

	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}

	public Integer getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}

	public Integer getHours() {
		return hours;
	}

	public void setHours(Integer hours) {
		this.hours = hours;
	}

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

}
