package com.boyaa.mf.entity.config;

public class Htable {
	// 表名,英文
	private String name;
	// 表名,中文
	private String desc;
	
	public Htable() {
		super();
	}

	public Htable(String name, String desc) {
		super();
		this.name = name;
		this.desc = desc;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
