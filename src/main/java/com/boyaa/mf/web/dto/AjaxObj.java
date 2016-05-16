package com.boyaa.mf.web.dto;

/**
 * ajax返回对象
 * 
 * @作者 : huangyineng
 * @日期 : 2016-3-15 下午5:14:43
 */
public class AjaxObj {
	private int status = 0;// 是否成功
	private String msg = "";// 提示信息
	private Object obj = null;// 其他信息
	
	public final static int FAILURE = 0;
	public final static int SUCCESS = 1;
	
	public AjaxObj() {
		super();
	}

	public AjaxObj(int status) {
		super();
		this.status = status;
	}

	public AjaxObj(int status, String msg) {
		super();
		this.status = status;
		this.msg = msg;
	}

	public AjaxObj(int status, String msg, Object obj) {
		super();
		this.status = status;
		this.msg = msg;
		this.obj = obj;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

}