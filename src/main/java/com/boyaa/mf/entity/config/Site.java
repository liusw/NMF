package com.boyaa.mf.entity.config;

/**
 * @类名 : site.java
 * @作者 : MarsHuang
 * @部门 : 德州扑克业务中心-开拓者工作室-公共组
 * @公司 : 博雅互动
 * @日期 : 2014-7-31  下午6:16:49
 * @描述 : 站点实体类
 */
public class Site {
	private int sid;
	private String sname;
	private String bpid;
	private int ismobile;
	
	public int getIsmobile() {
		return ismobile;
	}

	public void setIsmobile(int ismobile) {
		this.ismobile = ismobile;
	}

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public String getBpid() {
		return bpid;
	}

	public void setBpid(String bpid) {
		this.bpid = bpid;
	}

}
