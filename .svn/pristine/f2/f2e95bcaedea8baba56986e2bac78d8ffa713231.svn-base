package com.boyaa.mf.executor;

import java.util.Date;
import java.util.concurrent.Future;

/** 
 * @author huangwx 
 * @version 创建时间：Jun 17, 2015 10:18:21 AM 
 */
@SuppressWarnings("all")
public class FutureBean {
	private Future future;
	private Date date;
	private int status;//0等待状态,1执行状态
	public FutureBean(Future future, Date date, int status) {
		super();
		this.future = future;
		this.date = date;
		this.status = status;
	}
	public Future getFuture() {
		return future;
	}
	public void setFuture(Future future) {
		this.future = future;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}
