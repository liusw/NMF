package com.boyaa.mf.entity.privilege;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 资源
 * 
 * @类名 : Resources.java
 * @作者 : MarsHuang
 * @部门 : 德州扑克业务中心-开拓者工作室-公共组
 * @公司 : 博雅互动
 * @日期 : 2014-12-15 下午2:47:44
 */
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
public class Resources implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id; // id
	private String name; // 名称
	private String url; // 链接
	private Integer seq; // 序号
	private String description; // 描述
	private Date createtime; // 创建时间
	private Resources parent; // 父机构
	private List<Resources> children;


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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Resources getParent() {
		return parent;
	}

	public void setParent(Resources parent) {
		this.parent = parent;
	}

	public List<Resources> getChildren() {
		return children;
	}

	public void setChildren(List<Resources> children) {
		this.children = children;
	}
}
