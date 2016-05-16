package com.boyaa.mf.entity.data;

import java.io.Serializable;

/**
 *<p>Title: 老虎机投注统计实体类<p>
 *<p>Description: null</p>
 *<p>Company: boyaa</p>
 *<p>Date: May 27, 2015 3:11:40 PM</p>
 * @author Joakun Chen
 */
public class SlotMachineStats implements Serializable {

	private static final long serialVersionUID = -1898391512243892678L;
	
	private long id;  //自动ID，无义
	
	private int tm;  //时间分区

	private long plat;  //平台ID
	
	private long sid;  //站点ID
	
	private long totalBet;  //投注总额
	
	private long reward;  //玩家获得总额
	
	private long ntGt200BetCount;  //不超过200注点投注人次
	private long ntGt200RwdCount;  //不超过200下注点中奖人次
	  
	private long ntGt2000BetCount;  //201-2000注点投注人次
	private long ntGt2000RwdCount;  //201-2000下注点中奖人次
	  
	private long ntGt20000BetCount;  //2001-20000注点投注人次
	private long ntGt20000RwdCount;  //2001-20000下注点中奖人次
	  
	private long ntGt200000BetCount;  //20001-200000注点投注人次
	private long ntGt200000RwdCount;  //20001-200000下注点中奖人次
	  
	private long gt200000BetCount;  //超过200000注点投注人次
	private long gt200000RwdCount;  //超过200000下注点中奖人次
	
	public SlotMachineStats() {
		super();
	}

	public SlotMachineStats(long id, int tm, long plat, long sid,
			long totalBet, long reward, long ntGt200BetCount,
			long ntGt200RwdCount, long ntGt2000BetCount, long ntGt2000RwdCount,
			long ntGt20000BetCount, long ntGt20000RwdCount,
			long ntGt200000BetCount, long ntGt200000RwdCount,
			long gt200000BetCount, long gt200000RwdCount) {
		super();
		this.id = id;
		this.tm = tm;
		this.plat = plat;
		this.sid = sid;
		this.totalBet = totalBet;
		this.reward = reward;
		this.ntGt200BetCount = ntGt200BetCount;
		this.ntGt200RwdCount = ntGt200RwdCount;
		this.ntGt2000BetCount = ntGt2000BetCount;
		this.ntGt2000RwdCount = ntGt2000RwdCount;
		this.ntGt20000BetCount = ntGt20000BetCount;
		this.ntGt20000RwdCount = ntGt20000RwdCount;
		this.ntGt200000BetCount = ntGt200000BetCount;
		this.ntGt200000RwdCount = ntGt200000RwdCount;
		this.gt200000BetCount = gt200000BetCount;
		this.gt200000RwdCount = gt200000RwdCount;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getTm() {
		return tm;
	}

	public void setTm(int tm) {
		this.tm = tm;
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

	public long getTotalBet() {
		return totalBet;
	}

	public void setTotalBet(long totalBet) {
		this.totalBet = totalBet;
	}

	public long getReward() {
		return reward;
	}

	public void setReward(long reward) {
		this.reward = reward;
	}

	public long getNtGt200BetCount() {
		return ntGt200BetCount;
	}

	public void setNtGt200BetCount(long ntGt200BetCount) {
		this.ntGt200BetCount = ntGt200BetCount;
	}

	public long getNtGt200RwdCount() {
		return ntGt200RwdCount;
	}

	public void setNtGt200RwdCount(long ntGt200RwdCount) {
		this.ntGt200RwdCount = ntGt200RwdCount;
	}

	public long getNtGt2000BetCount() {
		return ntGt2000BetCount;
	}

	public void setNtGt2000BetCount(long ntGt2000BetCount) {
		this.ntGt2000BetCount = ntGt2000BetCount;
	}

	public long getNtGt2000RwdCount() {
		return ntGt2000RwdCount;
	}

	public void setNtGt2000RwdCount(long ntGt2000RwdCount) {
		this.ntGt2000RwdCount = ntGt2000RwdCount;
	}

	public long getNtGt20000BetCount() {
		return ntGt20000BetCount;
	}

	public void setNtGt20000BetCount(long ntGt20000BetCount) {
		this.ntGt20000BetCount = ntGt20000BetCount;
	}

	public long getNtGt20000RwdCount() {
		return ntGt20000RwdCount;
	}

	public void setNtGt20000RwdCount(long ntGt20000RwdCount) {
		this.ntGt20000RwdCount = ntGt20000RwdCount;
	}

	public long getNtGt200000BetCount() {
		return ntGt200000BetCount;
	}

	public void setNtGt200000BetCount(long ntGt200000BetCount) {
		this.ntGt200000BetCount = ntGt200000BetCount;
	}

	public long getNtGt200000RwdCount() {
		return ntGt200000RwdCount;
	}

	public void setNtGt200000RwdCount(long ntGt200000RwdCount) {
		this.ntGt200000RwdCount = ntGt200000RwdCount;
	}

	public long getGt200000BetCount() {
		return gt200000BetCount;
	}

	public void setGt200000BetCount(long gt200000BetCount) {
		this.gt200000BetCount = gt200000BetCount;
	}

	public long getGt200000RwdCount() {
		return gt200000RwdCount;
	}

	public void setGt200000RwdCount(long gt200000RwdCount) {
		this.gt200000RwdCount = gt200000RwdCount;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
