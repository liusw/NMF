package com.boyaa.mf.entity.config;

import java.io.Serializable;
import java.util.Date;
/**
 *<p>Title: Feed信息实体类<p>
 *<p>Description: null</p>
 *<p>Company: boyaa</p>
 *<p>Date: May 6, 2015</p>
 * @author Joakun Chen
 */
public class FeedConf implements Serializable {

	private static final long serialVersionUID = -1324647987406126342L;
	
	private int id;
	
	private String name;
	
	private Integer operator; //更新记录者工号
	
	private int status;	//是否启用，0为启用，1为停用
	
	private Date createTime;  //记录生成时间

	public FeedConf() {
		super();
	}

	public FeedConf(int id, String name, Integer operator, int status, Date createTime) {
		super();
		this.id = id;
		this.name = name;
		this.operator = operator;
		this.status = status;
		this.createTime = createTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getOperator() {
		return operator;
	}

	public void setOperator(Integer operator) {
		this.operator = operator;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
