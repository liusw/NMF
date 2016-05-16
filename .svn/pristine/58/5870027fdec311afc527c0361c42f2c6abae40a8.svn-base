package com.boyaa.mf.entity.task;

import com.boyaa.mf.util.CommonUtil;

/**
 * @作者 : MarsHuang
 * @部门 : 德州扑克业务中心-开拓者工作室-公共组
 * @公司 : 博雅互动
 * @日期 : 2014-7-11 下午3:18:10
 * @描述 : 导数据的流程日志
 */
public class ProcessInfo {
	private int id;
	private Integer type;
	private Integer taskId;
	// 依懒上一个流程的ID
	private Integer dependId;
	private String operation;
	private Integer status;
	private String logInfo;
	private String path;
	private String columnName;
	private String startTime;
	private String endTime;
	private String description;
	// 文件输出的标题,现在是最后一步把标题设置上,其他流程不要设置(后面的流程会优化)
	private String title;
	// 如果依懒上一个流程,并且当前流程要把上一个流程的文件load到hive中查询,必须设置preTempTable=1
	private Integer preTempTable;
	//附件大小
	private Long fileSize;
	private String depend;
	
	private String fileSizeFormat;
	public String getFileSizeFormat() {
		fileSizeFormat = CommonUtil.fileSizeFormat(fileSize);
		return fileSizeFormat;
	}
	
	public String getDepend() {
		return depend;
	}

	public void setDepend(String depend) {
		this.depend = depend;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public Integer getDependId() {
		return dependId;
	}

	public void setDependId(Integer dependId) {
		this.dependId = dependId;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
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

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getPreTempTable() {
		return preTempTable;
	}

	public void setPreTempTable(Integer preTempTable) {
		this.preTempTable = preTempTable;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProcessInfo other = (ProcessInfo) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
