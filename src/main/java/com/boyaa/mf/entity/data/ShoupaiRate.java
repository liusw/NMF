package com.boyaa.mf.entity.data;

/**
 * 牌局概率
 * @author darcy
 * @date 20150113
 */
public class ShoupaiRate {
	private int id;
	private int plat;
	private int tm;
	private String type;
	private String daxiao1;
	private String daxiao2;
	private double rate;//和理论值偏差
	private int num;//出现次数

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPlat() {
		return plat;
	}

	public void setPlat(int plat) {
		this.plat = plat;
	}

	public int getTm() {
		return tm;
	}

	public void setTm(int tm) {
		this.tm = tm;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDaxiao1() {
		return daxiao1;
	}

	public void setDaxiao1(String daxiao1) {
		this.daxiao1 = daxiao1;
	}

	public String getDaxiao2() {
		return daxiao2;
	}

	public void setDaxiao2(String daxiao2) {
		this.daxiao2 = daxiao2;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

}
