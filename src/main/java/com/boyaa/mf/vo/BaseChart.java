package com.boyaa.mf.vo;

public class BaseChart {
	private String xaxis;
	private Object yaxis;

	public BaseChart() {
		super();
	}

	public BaseChart(String xaxis, Object yaxis) {
		super();
		this.xaxis = xaxis;
		this.yaxis = yaxis;
	}

	public String getXaxis() {
		return xaxis;
	}

	public void setXaxis(String xaxis) {
		this.xaxis = xaxis;
	}

	public Object getYaxis() {
		return yaxis;
	}

	public void setYaxis(Object yaxis) {
		this.yaxis = yaxis;
	}

}
