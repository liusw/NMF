package com.boyaa.mf.entity.data;

import java.util.Date;

/**
 * 操作记录
 * 
 * @类名 : OperationLog.java
 * @作者 : MarsHuang
 * @部门 : 德州扑克业务中心-开拓者工作室-公共组
 * @公司 : 博雅互动
 * @日期 : 2015-1-15 上午10:54:20
 */
public class OperationLog {
	private int id;
	private String title;
	private Integer userId;
	public String userName;
	private Integer type;
	private String operation;
	private String remark;
	private Date createTime;
	
	public OperationLog() {
		super();
	}
	
	public OperationLog(String title, Integer userId, String userName,
			Integer type, String operation) {
		super();
		this.title = title;
		this.userId = userId;
		this.userName = userName;
		this.type = type;
		this.operation = operation;
	}

	public OperationLog(String title, Integer userId, String userName,
			Integer type, String operation, String remark) {
		super();
		this.title = title;
		this.userId = userId;
		this.userName = userName;
		this.type = type;
		this.operation = operation;
		this.remark = remark;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
