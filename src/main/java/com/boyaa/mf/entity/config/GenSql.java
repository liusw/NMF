package com.boyaa.mf.entity.config;

/**
 * 生成hive语句实体类
 * 
 * @author darcy
 * @date 2015-02-05
 */
public class GenSql {
	private int id;
	private String tableName;// 表名
	private String showName;// 显示名
	private String sqlStr;// sql
	private int sort;// 排序
	private String tm;// 创建时间
	private int status;// 状态
	private int hasLog;//是否有日志，有：0；无：1

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getShowName() {
		return showName;
	}

	public void setShowName(String showName) {
		this.showName = showName;
	}

	public String getSqlStr() {
		return sqlStr;
	}

	public void setSqlStr(String sqlStr) {
		this.sqlStr = sqlStr;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getTm() {
		return tm;
	}

	public void setTm(String tm) {
		this.tm = tm;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getHasLog() {
		return hasLog;
	}

	public void setHasLog(int hasLog) {
		this.hasLog = hasLog;
	}
}
