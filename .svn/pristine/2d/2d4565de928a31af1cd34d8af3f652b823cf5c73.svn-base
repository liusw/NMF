package com.boyaa.mf.entity.task;

import com.boyaa.mf.util.CommonUtil;

/**
 * 
 * @类名 : Task.java
 * @作者 : MarsHuang
 * @部门 : 德州扑克业务中心-开拓者工作室-公共组
 * @公司 : 博雅互动
 * @日期 : 2014-7-11 下午3:17:49
 * @描述 : 导数据任务
 */
public class Task {
	private int id;
	private String userId;
	private String userName;
	private String taskName;
	private Integer status;
	private String logInfo;
	private String path;
	private String startTime;
	private String endTime;
	private String description;
	private Integer reExecuteCount;
	private String email;
	private String outputColumn;
	private String outputLink;
	private Integer outputType;
	private Long fileSize;
	private Integer type;
	private Integer isSendFile;
	
	private String fileSizeFormat;

	public String getFileSizeFormat() {
		fileSizeFormat = CommonUtil.fileSizeFormat(fileSize);
		return fileSizeFormat;
	}
	
	public Integer getIsSendFile() {
		return isSendFile;
	}



	public void setIsSendFile(Integer isSendFile) {
		this.isSendFile = isSendFile;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getLogInfo() {
		return logInfo;
	}

	public void setLogInfo(String logInfo) {
		this.logInfo = logInfo;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getReExecuteCount() {
		return reExecuteCount;
	}

	public void setReExecuteCount(Integer reExecuteCount) {
		this.reExecuteCount = reExecuteCount;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOutputColumn() {
		return outputColumn;
	}

	public void setOutputColumn(String outputColumn) {
		this.outputColumn = outputColumn;
	}

	public String getOutputLink() {
		return outputLink;
	}

	public void setOutputLink(String outputLink) {
		this.outputLink = outputLink;
	}

	public Integer getOutputType() {
		return outputType;
	}

	public void setOutputType(Integer outputType) {
		this.outputType = outputType;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	//队列的等待数
	private int queueWaitCount;

	public int getQueueWaitCount() {
		return queueWaitCount;
	}

	public void setQueueWaitCount(int queueWaitCount) {
		this.queueWaitCount = queueWaitCount;
	}

	public String LogString() {
		return "Task [id=" + id + ", userId=" + userId + ", userName="
				+ userName + ", taskName=" + taskName + ", status=" + status
				+ ", logInfo=" + logInfo + ", path=" + path + ", startTime="
				+ startTime + ", endTime=" + endTime + ", description="
				+ description + ", reExecuteCount=" + reExecuteCount
				+ ", email=" + email + ", outputColumn=" + outputColumn
				+ ", outputLink=" + outputLink + ", outputType=" + outputType
				+ ", fileSize=" + fileSize + ", type=" + type + "]";
	}
	
}
