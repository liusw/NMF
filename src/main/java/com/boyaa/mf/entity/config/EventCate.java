package com.boyaa.mf.entity.config;

import java.io.Serializable;

/**
 *<p>Title: 事件类型实体类<p>
 *<p>Description: null</p>
 *<p>Company: boyaa</p>
 *<p>Date: May 11, 2015</p>
 * @author Joakun Chen
 */
public class EventCate implements Serializable {
	
	private static final long serialVersionUID = 6892519696450716105L;
	
	private int id;  //事件类型ID,由数据库自动生成
	
	private String ename;  //事件类型英文名称
	
	private String name;  //事件类型名称
	
	private int plat;  //事件类型所属平台ID
	
	private int sid;  //事件类型所属站点
	
	private int status;  //是否还在启用，0为启用，1为停用

	public EventCate() {
		super();
	}

	public EventCate(int id, String ename, String name, int plat, int sid,
			int status) {
		super();
		this.id = id;
		this.ename = ename;
		this.name = name;
		this.plat = plat;
		this.sid = sid;
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getPlat() {
		return plat;
	}

	public void setPlat(int plat) {
		this.plat = plat;
	}

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
