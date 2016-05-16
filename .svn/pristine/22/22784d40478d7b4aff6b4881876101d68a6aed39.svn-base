package com.boyaa.mf.entity.data;

import com.boyaa.mf.util.CommonUtil;

/**
 * 被封汇总
 * 
 * @作者 : huangyineng
 * @日期 : 2015-4-22 下午2:11:14
 */
public class BanCount {
	private int tm;
	private int plat;
	private int banCount;
	private long deductCoins;
	private int clearZeroCount;
	private long clearZeroCoins;
	private int yesterdayBanCount;
	private long yesterdayBanCoins;

	private String yesterdayBanCountRate;
	private String yesterdayBanCoinsRate;

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

	public int getBanCount() {
		return banCount;
	}

	public void setBanCount(int banCount) {
		this.banCount = banCount;
	}

	public long getDeductCoins() {
		return deductCoins;
	}

	public void setDeductCoins(long deductCoins) {
		this.deductCoins = deductCoins;
	}

	public int getClearZeroCount() {
		return clearZeroCount;
	}

	public void setClearZeroCount(int clearZeroCount) {
		this.clearZeroCount = clearZeroCount;
	}

	public long getClearZeroCoins() {
		return clearZeroCoins;
	}

	public void setClearZeroCoins(long clearZeroCoins) {
		this.clearZeroCoins = clearZeroCoins;
	}

	public int getYesterdayBanCount() {
		return yesterdayBanCount;
	}

	public void setYesterdayBanCount(int yesterdayBanCount) {
		this.yesterdayBanCount = yesterdayBanCount;
	}

	public long getYesterdayBanCoins() {
		return yesterdayBanCoins;
	}

	public void setYesterdayBanCoins(long yesterdayBanCoins) {
		this.yesterdayBanCoins = yesterdayBanCoins;
	}

	public String getYesterdayBanCountRate() {
		yesterdayBanCountRate = CommonUtil.rateFormat(banCount
				- yesterdayBanCount, yesterdayBanCount);
		return yesterdayBanCountRate;
	}

	public void setYesterdayBanCountRate(String yesterdayBanCountRate) {
		this.yesterdayBanCountRate = yesterdayBanCountRate;
	}

	public String getYesterdayBanCoinsRate() {
		yesterdayBanCoinsRate = CommonUtil.rateFormat(deductCoins-yesterdayBanCoins, yesterdayBanCoins);
		return yesterdayBanCoinsRate;
	}

	public void setYesterdayBanCoinsRate(String yesterdayBanCoinsRate) {
		this.yesterdayBanCoinsRate = yesterdayBanCoinsRate;
	}

}
