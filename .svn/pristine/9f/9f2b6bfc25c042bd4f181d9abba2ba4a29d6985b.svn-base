package com.boyaa.entity.common;

import java.io.Serializable;

import com.boyaa.mf.constants.Constants;
import com.boyaa.mf.util.CommonUtil;

/**
 *<p>Title: 通用结果类<p>
 *<p>Description: null</p>
 *<p>Company: boyaa</p>
 *<p>Date: Jun 24, 2015 2:43:43 PM</p>
 * @author Joakun Chen
 */
public class JSONResult implements Serializable{
	
	private static final long serialVersionUID = 3821741719603439292L;
	
	/**
	 * 执行结果：0表示错误，1表示成功
	 */
	private int result = Constants.RESULT_FAILURE;
	
	/**
	 * 提示信息
	 */
	private String msg = null;
	
	/**
	 * 其他附加信息
	 */
	private String other = null;
	
	/**
	 * 主体数据：
	 */
	private Object loop = null;

	public JSONResult() {
		super();
	}
	
	public JSONResult(int result, String msg, Object loop) {
		super();
		this.result = result;
		this.msg = msg;
		this.loop = loop;
	}

	public JSONResult(int result, String msg, String other, Object loop) {
		super();
		this.result = result;
		this.msg = msg;
		this.other = other;
		this.loop = loop;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public Object getLoop() {
		return loop;
	}

	public void setLoop(Object loop) {
		this.loop = loop;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		//return JSON.toJSONString(this);
		return CommonUtil.toJSONString(this);
	}
}
