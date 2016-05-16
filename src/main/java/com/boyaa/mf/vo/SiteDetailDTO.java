package com.boyaa.mf.vo;

/**
 * @类名 : siteDetailDTO.java
 * @作者 : PeterLiao
 * @部门 : 德州扑克业务中心-开拓者工作室-公共组
 * @公司 : 博雅互动
 * @日期 : 2014-11-06  下午19:30:11
 * @描述 : 站点详细数据传输类
 */
public class SiteDetailDTO {
	private int sid;
	/**版本名称*/
	private String sname;
	/**平台名称*/
	private String pName;
	/**平台ID*/
	private Integer plat;
	/**唯一索引值*/
	private String bpid;
	/** svid **/
	private Integer svid;
	private String ismobile;
	
	public String getIsmobile() {
		return ismobile;
	}
	public void setIsmobile(String ismobile) {
		this.ismobile = ismobile;
	}
	public SiteDetailDTO() {
		super();
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
	public String getpName() {
		return pName;
	}
	public void setpName(String pName) {
		this.pName = pName;
	}
	public Integer getPlat() {
		return plat;
	}
	public void setPlat(Integer plat) {
		this.plat = plat;
	}
	public String getBpid() {
		return bpid;
	}
	public void setBpid(String bpid) {
		this.bpid = bpid;
	}
	public Integer getSvid() {
		return svid;
	}
	public void setSvid(Integer svid) {
		this.svid = svid;
	}
}
