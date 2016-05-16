package com.boyaa.mf.entity.data;

/**
 *<p>Title: 赢钱排行实体类<p>
 *<p>Description: null</p>
 *<p>Company: boyaa</p>
 *<p>Date: Apr 13, 2015</p>
 * @author Joakun Chen
 */
public class CgCoinsTop {
	/*平台相关信息*/
	private int id;		
	private int tm;  //时间
	private int plat;
	private int sid; //plat下的站点号
	
	/*用户相关信息*/
	private int uid;
	private String mnick;  //昵称
	
	/*赢钱总数*/
	private long cgCoinsTotal;
	
	/*各盲注牌局数及赢钱统计*/
	private int bgCount_150;
	private long cgCoins_150;
	private int bgCount_50;
	private long cgCoins_50;
	private int bgCount_25;
	private long cgCoins_25;
	private int bgCount_5;
	private long cgCoins_5;
	private int bgCount_2;
	private long cgCoins_2;
	private int bgCount_1;
	private long cgCoins_1;
	
	public CgCoinsTop() {
		super();
	}

	public CgCoinsTop(int id, int tm, int plat, int sid, int uid,
			String mnick, long cgCoinsTotal, int bgCount_150, long cgCoins_150,
			int bgCount_50, long cgCoins_50, int bgCount_25, long cgCoins_25,
			int bgCount_5, long cgCoins_5, int bgCount_2, long cgCoins_2,
			int bgCount_1, long cgCoins_1) {
		super();
		this.id = id;
		this.tm = tm;
		this.plat = plat;
		this.sid = sid;
		this.uid = uid;
		this.mnick = mnick;
		this.cgCoinsTotal = cgCoinsTotal;
		this.bgCount_150 = bgCount_150;
		this.cgCoins_150 = cgCoins_150;
		this.bgCount_50 = bgCount_50;
		this.cgCoins_50 = cgCoins_50;
		this.bgCount_25 = bgCount_25;
		this.cgCoins_25 = cgCoins_25;
		this.bgCount_5 = bgCount_5;
		this.cgCoins_5 = cgCoins_5;
		this.bgCount_2 = bgCount_2;
		this.cgCoins_2 = cgCoins_2;
		this.bgCount_1 = bgCount_1;
		this.cgCoins_1 = cgCoins_1;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTm() {
		return tm;
	}

	public void setTm(int tm) {
		this.tm = tm;
	}

	public int getPlat() {
		return plat;
	}

	public void setPlat(int plat) {
		this.plat = plat;
	}

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getMnick() {
		return mnick;
	}

	public void setMnick(String mnick) {
		this.mnick = mnick;
	}

	public long getCgCoinsTotal() {
		return cgCoinsTotal;
	}

	public void setCgCoinsTotal(long cgCoinsTotal) {
		this.cgCoinsTotal = cgCoinsTotal;
	}

	public int getBgCount_150() {
		return bgCount_150;
	}

	public void setBgCount_150(int bgCount_150) {
		this.bgCount_150 = bgCount_150;
	}

	public long getCgCoins_150() {
		return cgCoins_150;
	}

	public void setCgCoins_150(long cgCoins_150) {
		this.cgCoins_150 = cgCoins_150;
	}

	public int getBgCount_50() {
		return bgCount_50;
	}

	public void setBgCount_50(int bgCount_50) {
		this.bgCount_50 = bgCount_50;
	}

	public long getCgCoins_50() {
		return cgCoins_50;
	}

	public void setCgCoins_50(long cgCoins_50) {
		this.cgCoins_50 = cgCoins_50;
	}

	public int getBgCount_25() {
		return bgCount_25;
	}

	public void setBgCount_25(int bgCount_25) {
		this.bgCount_25 = bgCount_25;
	}

	public long getCgCoins_25() {
		return cgCoins_25;
	}

	public void setCgCoins_25(long cgCoins_25) {
		this.cgCoins_25 = cgCoins_25;
	}

	public int getBgCount_5() {
		return bgCount_5;
	}

	public void setBgCount_5(int bgCount_5) {
		this.bgCount_5 = bgCount_5;
	}

	public long getCgCoins_5() {
		return cgCoins_5;
	}

	public void setCgCoins_5(long cgCoins_5) {
		this.cgCoins_5 = cgCoins_5;
	}

	public int getBgCount_2() {
		return bgCount_2;
	}

	public void setBgCount_2(int bgCount_2) {
		this.bgCount_2 = bgCount_2;
	}

	public long getCgCoins_2() {
		return cgCoins_2;
	}

	public void setCgCoins_2(long cgCoins_2) {
		this.cgCoins_2 = cgCoins_2;
	}

	public int getBgCount_1() {
		return bgCount_1;
	}

	public void setBgCount_1(int bgCount_1) {
		this.bgCount_1 = bgCount_1;
	}

	public long getCgCoins_1() {
		return cgCoins_1;
	}

	public void setCgCoins_1(long cgCoins_1) {
		this.cgCoins_1 = cgCoins_1;
	}
	
}
