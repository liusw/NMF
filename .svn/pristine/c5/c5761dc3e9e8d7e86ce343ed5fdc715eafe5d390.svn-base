package com.boyaa.mf.entity.config;

import java.io.Serializable;
import java.util.Date;

/**
 *<p>Title: 平台Feed配置实体类<p>
 *<p>Description: null</p>
 *<p>Company: boyaa</p>
 *<p>Date: May 8, 2015</p>
 * @author Joakun Chen
 */
public class FeedPlat implements Serializable {

	private static final long serialVersionUID = -6145432647965794399L;
	
	private int id;  //自动生成的ID,无实际意义
	
	private int plat; //平台ID	
	
	private int feedId; //feed ID	
	
	private String feedName; //feed名称	
	
	private Integer feedType = null; //feed类型	1:feed,2:FbSource来源明细,3:FbRef来源明细
	
	private Integer operator; //更新记录者工号
	
	private Integer status = null;	//是否启用，0为启用（平台显示指定feed），1为停用(平台取消指定feed)
	
	private Date createTime;  //记录生成时间

	public FeedPlat() {
		super();
	}

	public FeedPlat(int id, int plat, int feedId, String feedName,
			Integer feedType,Integer operator, Integer status, Date createTime) {
		super();
		this.id = id;
		this.plat = plat;
		this.feedId = feedId;
		this.feedName = feedName;
		this.feedType = feedType;
		this.operator = operator;
		this.status = status;
		this.createTime = createTime;
	}

	public long getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getPlat() {
		return plat;
	}

	public void setPlat(int plat) {
		this.plat = plat;
	}

	public long getFeedId() {
		return feedId;
	}

	public void setFeedId(int feedId) {
		this.feedId = feedId;
	}

	public String getFeedName() {
		return feedName;
	}

	public void setFeedName(String feedName) {
		this.feedName = feedName;
	}

	public Integer getFeedType() {
		return feedType;
	}

	public void setFeedType(Integer feedType) {
		this.feedType = feedType;
	}

	public Integer getOperator() {
		return operator;
	}

	public void setOperator(Integer operator) {
		this.operator = operator;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
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
