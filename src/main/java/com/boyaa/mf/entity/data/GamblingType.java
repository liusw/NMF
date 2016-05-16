package com.boyaa.mf.entity.data;

import java.io.Serializable;

/**
 *<p>Title: 牌型分布统计实体类<p>
 *<p>Description: 统计牌局中用户最终牌型：如杂牌，高牌，对子等</p>
 *<p>Company: boyaa</p>
 *<p>Date: Jun 8, 2015 3:29:46 PM</p>
 * @author Joakun Chen
 */
public class GamblingType implements Serializable {

	private static final long serialVersionUID = 4602784106293124357L;
	
	private long id;  //自动ID，无义

	private long plat;  //平台ID
	
	private long sid;  //站点ID
	
	private int tableType;  //桌子类型
	
	private int tm;  //时间分区
	
	private long foldCount;  //弃牌总数目
	
	private long hodgeCount;  //杂牌总数目

	private long highCount;  //高牌总数目

	private long pairCount;  //对子总数目

	private long twopairCount;  //两对总数目

	private long threeCount;  //三条总数目

	private long junkoCount;  //顺子总数目

	private long flushCount;  //同花总数目

	private long fullHouseCount;  //葫芦总数目

	private long fourCount;  //四条总数目

	private long straightFlushCount;  //同花顺总数目
	
	private long royalFlushCount;  //皇家同花顺总数目

	public GamblingType() {
		super();
	}

	public GamblingType(long id, long plat, long sid, int tableType, int tm,
			long foldCount, long hodgeCount, long highCount,
			long pairCount, long twopairCount, long threeCount,
			long junkoCount, long flushCount, long fullHouseCount,
			long fourCount, long straightFlushCount, long royalFlushCount) {
		super();
		this.id = id;
		this.plat = plat;
		this.sid = sid;
		this.tableType = tableType;
		this.tm = tm;
		this.foldCount = foldCount;
		this.hodgeCount = hodgeCount;
		this.highCount = highCount;
		this.pairCount = pairCount;
		this.twopairCount = twopairCount;
		this.threeCount = threeCount;
		this.junkoCount = junkoCount;
		this.flushCount = flushCount;
		this.fullHouseCount = fullHouseCount;
		this.fourCount = fourCount;
		this.straightFlushCount = straightFlushCount;
		this.royalFlushCount = royalFlushCount;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getPlat() {
		return plat;
	}

	public void setPlat(long plat) {
		this.plat = plat;
	}

	public long getSid() {
		return sid;
	}

	public void setSid(long sid) {
		this.sid = sid;
	}

	public int getTableType() {
		return tableType;
	}

	public void setTableType(int tableType) {
		this.tableType = tableType;
	}

	public int getTm() {
		return tm;
	}

	public void setTm(int tm) {
		this.tm = tm;
	}

	public long getFoldCount() {
		return foldCount;
	}

	public void setFoldCount(long foldCount) {
		this.foldCount = foldCount;
	}

	public long getHodgeCount() {
		return hodgeCount;
	}

	public void setHodgeCount(long hodgeCount) {
		this.hodgeCount = hodgeCount;
	}

	public long getHighCount() {
		return highCount;
	}

	public void setHighCount(long highCount) {
		this.highCount = highCount;
	}

	public long getPairCount() {
		return pairCount;
	}

	public void setPairCount(long pairCount) {
		this.pairCount = pairCount;
	}

	public long getTwopairCount() {
		return twopairCount;
	}

	public void setTwopairCount(long twopairCount) {
		this.twopairCount = twopairCount;
	}

	public long getThreeCount() {
		return threeCount;
	}

	public void setThreeCount(long threeCount) {
		this.threeCount = threeCount;
	}

	public long getJunkoCount() {
		return junkoCount;
	}

	public void setJunkoCount(long junkoCount) {
		this.junkoCount = junkoCount;
	}

	public long getFlushCount() {
		return flushCount;
	}

	public void setFlushCount(long flushCount) {
		this.flushCount = flushCount;
	}

	public long getFullHouseCount() {
		return fullHouseCount;
	}

	public void setFullHouseCount(long fullHouseCount) {
		this.fullHouseCount = fullHouseCount;
	}

	public long getFourCount() {
		return fourCount;
	}

	public void setFourCount(long fourCount) {
		this.fourCount = fourCount;
	}

	public long getStraightFlushCount() {
		return straightFlushCount;
	}

	public void setStraightFlushCount(long straightFlushCount) {
		this.straightFlushCount = straightFlushCount;
	}

	public long getRoyalFlushCount() {
		return royalFlushCount;
	}

	public void setRoyalFlushCount(long royalFlushCount) {
		this.royalFlushCount = royalFlushCount;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
