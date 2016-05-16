package com.boyaa.entity.common;

import java.util.List;

/**
 *<p>Title: TableJSONResult.java<p>
 *<p>Description: null</p>
 *<p>Company: boyaa</p>
 *<p>Date: Jun 25, 2015 3:55:11 PM</p>
 * @author Joakun Chen
 */
public class TableJSONResult extends JSONResult {

	private static final long serialVersionUID = 6656593335473343129L;
	
	private List<Object> data = null;
	
	private int iTotalRecords = 0;
	
	private int iTotalDisplayRecords = 0;

	public TableJSONResult() {
		super();
	}

	public TableJSONResult(int result, String msg, String other, String loop) {
		super(result, msg, other, loop);
	}

	public TableJSONResult(List<Object> data, int iTotalRecords,
			int iTotalDisplayRecords) {
		super();
		this.data = data;
		this.iTotalRecords = iTotalRecords;
		this.iTotalDisplayRecords = iTotalDisplayRecords;
	}
	
	public TableJSONResult(int result, String msg, String other, String loop,List<Object> data, int iTotalRecords,
			int iTotalDisplayRecords) {
		this(result, msg, other, loop);
		this.data = data;
		this.iTotalRecords = iTotalRecords;
		this.iTotalDisplayRecords = iTotalDisplayRecords;
	}

	public List<Object> getData() {
		return data;
	}

	public void setData(List<Object> data) {
		this.data = data;
	}

	public int getITotalRecords() {
		return iTotalRecords;
	}

	public void setITotalRecords(int iTotalRecords) {
		this.iTotalRecords = iTotalRecords;
	}

	public int getITotalDisplayRecords() {
		return iTotalDisplayRecords;
	}

	public void setITotalDisplayRecords(int iTotalDisplayRecords) {
		this.iTotalDisplayRecords = iTotalDisplayRecords;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
