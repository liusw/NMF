package com.boyaa.servlet;

import com.alibaba.fastjson.JSONObject;
import com.boyaa.base.utils.JSONUtil;

/**
 * @Department 德州扑克业务中心-德州PC工作室-泰语组
 * @Date 2013-04-6
 * @Author Jack
 * 
 */
public class ResultState {
	// 最后一页的页号
	private Integer maxPage;
	
	// max num of select condition
	private Integer num;

	// 表名
	private String table;

	// 返回的数据
	private String data;

	// 保存错误信息
	private String msg = "";

	// 返回1表示成功；0表示失败
	private Integer ok = SUCCESS;

	private JSONObject other = JSONUtil.getJSONObject();

	public final static int FAILURE = 0;

	public final static int SUCCESS = 1;

	public ResultState() {

	}

	public ResultState(Integer ok) {
		this.ok = ok;
	}

	public ResultState(Integer ok, String msg) {
		this.ok = ok;
		this.msg = msg;
	}
	
	public ResultState(Integer ok,String msg,String data) {
		this.data = data;
		this.msg = msg;
		this.ok = ok;
	}

	public Integer getMaxPage() {
		return maxPage;
	}

	public void setMaxPage(Integer maxPage) {
		this.maxPage = maxPage;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Integer getOk() {
		return ok;
	}

	public void setOk(Integer ok) {
		this.ok = ok;
	}

	public JSONObject getOther() {
		return other;
	}

	public void setOther(JSONObject other) {
		this.other = other;
	}
}
