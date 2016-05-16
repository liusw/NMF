package com.boyaa.mf.entity.data;

import java.io.Serializable;

/**
 *<p>幸运玩偶兑奖统计实体类<p>
 *<p>Description: 包含各类玩偶兑奖次数，人数，总数等信息</p>
 *<p>Company: boyaa</p>
 *<p>Date: Apr 20, 2015</p>
 * @author Joakun Chen
 */
public class DollTicket implements Serializable{
	private static final long serialVersionUID = -525546589978355638L;

	private int id;		

	/*平台相关信息*/
	private String bpid;
	private String sname; //站点名称
	private int tm;  //时间
		
	/*各类玩偶统计项目*/
	private long clid1_count;  //玩偶1兑换次数
	private long clid1_persons;  //玩偶1兑换人数
	private long clid1_amount;  //玩偶1兑换总数

	private long clid2_count;  //玩偶2兑换次数
	private long clid2_persons;  //玩偶2兑换人数
	private long clid2_amount;  //玩偶2兑换总数

	private long clid3_count;  //玩偶3兑换次数
	private long clid3_persons;  //玩偶3兑换人数
	private long clid3_amount;  //玩偶3兑换总数

	private long clid4_count;  //玩偶4兑换次数
	private long clid4_persons;  //玩偶4兑换人数
	private long clid4_amount;  //玩偶4兑换总数

	private long clid5_count;  //玩偶5兑换次数
	private long clid5_persons;  //玩偶5兑换人数
	private long clid5_amount;  //玩偶5兑换总数

	private long clid6_count;  //玩偶6兑换次数
	private long clid6_persons;  //玩偶6兑换人数
	private long clid6_amount;  //玩偶6兑换总数

	private long clid7_count;  //玩偶7兑换次数
	private long clid7_persons;  //玩偶7兑换人数
	private long clid7_amount;  //玩偶7兑换总数

	private long clid8_count;  //玩偶8兑换次数
	private long clid8_persons;  //玩偶8兑换人数
	private long clid8_amount;  //玩偶8兑换总数

	private long clid9_count;  //玩偶9兑换次数
	private long clid9_persons;  //玩偶9兑换人数
	private long clid9_amount;  //玩偶9兑换总数

	private long clid10_count;  //玩偶10兑换次数
	private long clid10_persons;  //玩偶10兑换人数
	private long clid10_amount;  //玩偶10兑换总数

	private long clid11_count;  //玩偶11兑换次数
	private long clid11_persons;  //玩偶11兑换人数
	private long clid11_amount;  //玩偶11兑换总数

	private long clid12_count;  //玩偶12兑换次数
	private long clid12_persons;  //玩偶12兑换人数
	private long clid12_amount;  //玩偶12兑换总数

	private long clid13_count;  //玩偶13兑换次数
	private long clid13_persons;  //玩偶13兑换人数
	private long clid13_amount;  //玩偶13兑换总数

	private long clid14_count;  //玩偶14兑换次数
	private long clid14_persons;  //玩偶14兑换人数
	private long clid14_amount;  //玩偶14兑换总数

	private long clid15_count;  //玩偶15兑换次数
	private long clid15_persons;  //玩偶15兑换人数
	private long clid15_amount;  //玩偶15兑换总数

	private long clid16_count;  //玩偶16兑换次数
	private long clid16_persons;  //玩偶16兑换人数
	private long clid16_amount;  //玩偶16兑换总数

	private long clid17_count;  //玩偶17兑换次数
	private long clid17_persons;  //玩偶17兑换人数
	private long clid17_amount;  //玩偶17兑换总数

	private long clid18_count;  //玩偶18兑换次数
	private long clid18_persons;  //玩偶18兑换人数
	private long clid18_amount;  //玩偶18兑换总数

	private long clid19_count;  //玩偶19兑换次数
	private long clid19_persons;  //玩偶19兑换人数
	private long clid19_amount;  //玩偶19兑换总数

	private long clid20_count;  //玩偶20兑换次数
	private long clid20_persons;  //玩偶20兑换人数
	private long clid20_amount;  //玩偶20兑换总数

	private long clid21_count;  //玩偶21兑换次数
	private long clid21_persons;  //玩偶21兑换人数
	private long clid21_amount;  //玩偶21兑换总数

	private long clid22_count;  //玩偶22兑换次数
	private long clid22_persons;  //玩偶22兑换人数
	private long clid22_amount;  //玩偶22兑换总数

	private long clid23_count;  //玩偶23兑换次数
	private long clid23_persons;  //玩偶23兑换人数
	private long clid23_amount;  //玩偶23兑换总数

	private long clid24_count;  //玩偶24兑换次数
	private long clid24_persons;  //玩偶24兑换人数
	private long clid24_amount;  //玩偶24兑换总数

	private long clid25_count;  //玩偶25兑换次数
	private long clid25_persons;  //玩偶25兑换人数
	private long clid25_amount;  //玩偶25兑换总数

	private long clid26_count;  //玩偶26兑换次数
	private long clid26_persons;  //玩偶26兑换人数
	private long clid26_amount;  //玩偶26兑换总数

	private long clid27_count;  //玩偶27兑换次数
	private long clid27_persons;  //玩偶27兑换人数
	private long clid27_amount;  //玩偶27兑换总数

	private long clid28_count;  //玩偶28兑换次数
	private long clid28_persons;  //玩偶28兑换人数
	private long clid28_amount;  //玩偶28兑换总数

	private long clid29_count;  //玩偶29兑换次数
	private long clid29_persons;  //玩偶29兑换人数
	private long clid29_amount;  //玩偶29兑换总数

	private long clid30_count;  //玩偶30兑换次数
	private long clid30_persons;  //玩偶30兑换人数
	private long clid30_amount;  //玩偶30兑换总数

	private long clid31_count;  //玩偶31兑换次数
	private long clid31_persons;  //玩偶31兑换人数
	private long clid31_amount;  //玩偶31兑换总数

	private long clid32_count;  //玩偶32兑换次数
	private long clid32_persons;  //玩偶32兑换人数
	private long clid32_amount;  //玩偶32兑换总数

	private long clid33_count;  //玩偶33兑换次数
	private long clid33_persons;  //玩偶33兑换人数
	private long clid33_amount;  //玩偶33兑换总数

	private long clid34_count;  //玩偶34兑换次数
	private long clid34_persons;  //玩偶34兑换人数
	private long clid34_amount;  //玩偶34兑换总数

	private long clid35_count;  //玩偶35兑换次数
	private long clid35_persons;  //玩偶35兑换人数
	private long clid35_amount;  //玩偶35兑换总数

	private long clid36_count;  //玩偶36兑换次数
	private long clid36_persons;  //玩偶36兑换人数
	private long clid36_amount;  //玩偶36兑换总数

	private long clid37_count;  //玩偶37兑换次数
	private long clid37_persons;  //玩偶37兑换人数
	private long clid37_amount;  //玩偶37兑换总数

	private long clid38_count;  //玩偶38兑换次数
	private long clid38_persons;  //玩偶38兑换人数
	private long clid38_amount;  //玩偶38兑换总数

	private long clid39_count;  //玩偶39兑换次数
	private long clid39_persons;  //玩偶39兑换人数
	private long clid39_amount;  //玩偶39兑换总数

	private long clid40_count;  //玩偶40兑换次数
	private long clid40_persons;  //玩偶40兑换人数
	private long clid40_amount;  //玩偶40兑换总数
	
	public DollTicket() {
		super();
	}

	public DollTicket(int id, String bpid, String sname,int tm,long clid1_count, long clid1_persons,
			long clid1_amount, long clid2_count, long clid2_persons,
			long clid2_amount, long clid3_count, long clid3_persons,
			long clid3_amount, long clid4_count, long clid4_persons,
			long clid4_amount, long clid5_count, long clid5_persons,
			long clid5_amount, long clid6_count, long clid6_persons,
			long clid6_amount, long clid7_count, long clid7_persons,
			long clid7_amount, long clid8_count, long clid8_persons,
			long clid8_amount, long clid9_count, long clid9_persons,
			long clid9_amount, long clid10_count, long clid10_persons,
			long clid10_amount, long clid11_count, long clid11_persons,
			long clid11_amount, long clid12_count, long clid12_persons,
			long clid12_amount, long clid13_count, long clid13_persons,
			long clid13_amount, long clid14_count, long clid14_persons,
			long clid14_amount, long clid15_count, long clid15_persons,
			long clid15_amount, long clid16_count, long clid16_persons,
			long clid16_amount, long clid17_count, long clid17_persons,
			long clid17_amount, long clid18_count, long clid18_persons,
			long clid18_amount, long clid19_count, long clid19_persons,
			long clid19_amount, long clid20_count, long clid20_persons,
			long clid20_amount, long clid21_count, long clid21_persons,
			long clid21_amount, long clid22_count, long clid22_persons,
			long clid22_amount, long clid23_count, long clid23_persons,
			long clid23_amount, long clid24_count, long clid24_persons,
			long clid24_amount, long clid25_count, long clid25_persons,
			long clid25_amount, long clid26_count, long clid26_persons,
			long clid26_amount, long clid27_count, long clid27_persons,
			long clid27_amount, long clid28_count, long clid28_persons,
			long clid28_amount, long clid29_count, long clid29_persons,
			long clid29_amount, long clid30_count, long clid30_persons,
			long clid30_amount, long clid31_count, long clid31_persons,
			long clid31_amount, long clid32_count, long clid32_persons,
			long clid32_amount, long clid33_count, long clid33_persons,
			long clid33_amount, long clid34_count, long clid34_persons,
			long clid34_amount, long clid35_count, long clid35_persons,
			long clid35_amount, long clid36_count, long clid36_persons,
			long clid36_amount, long clid37_count, long clid37_persons,
			long clid37_amount, long clid38_count, long clid38_persons,
			long clid38_amount, long clid39_count, long clid39_persons,
			long clid39_amount, long clid40_count, long clid40_persons,
			long clid40_amount) {
		super();
		this.id = id;
		this.bpid = bpid;
		this.sname = sname;
		this.tm = tm;
		this.clid1_count = clid1_count;
		this.clid1_persons = clid1_persons;
		this.clid1_amount = clid1_amount;
		this.clid2_count = clid2_count;
		this.clid2_persons = clid2_persons;
		this.clid2_amount = clid2_amount;
		this.clid3_count = clid3_count;
		this.clid3_persons = clid3_persons;
		this.clid3_amount = clid3_amount;
		this.clid4_count = clid4_count;
		this.clid4_persons = clid4_persons;
		this.clid4_amount = clid4_amount;
		this.clid5_count = clid5_count;
		this.clid5_persons = clid5_persons;
		this.clid5_amount = clid5_amount;
		this.clid6_count = clid6_count;
		this.clid6_persons = clid6_persons;
		this.clid6_amount = clid6_amount;
		this.clid7_count = clid7_count;
		this.clid7_persons = clid7_persons;
		this.clid7_amount = clid7_amount;
		this.clid8_count = clid8_count;
		this.clid8_persons = clid8_persons;
		this.clid8_amount = clid8_amount;
		this.clid9_count = clid9_count;
		this.clid9_persons = clid9_persons;
		this.clid9_amount = clid9_amount;
		this.clid10_count = clid10_count;
		this.clid10_persons = clid10_persons;
		this.clid10_amount = clid10_amount;
		this.clid11_count = clid11_count;
		this.clid11_persons = clid11_persons;
		this.clid11_amount = clid11_amount;
		this.clid12_count = clid12_count;
		this.clid12_persons = clid12_persons;
		this.clid12_amount = clid12_amount;
		this.clid13_count = clid13_count;
		this.clid13_persons = clid13_persons;
		this.clid13_amount = clid13_amount;
		this.clid14_count = clid14_count;
		this.clid14_persons = clid14_persons;
		this.clid14_amount = clid14_amount;
		this.clid15_count = clid15_count;
		this.clid15_persons = clid15_persons;
		this.clid15_amount = clid15_amount;
		this.clid16_count = clid16_count;
		this.clid16_persons = clid16_persons;
		this.clid16_amount = clid16_amount;
		this.clid17_count = clid17_count;
		this.clid17_persons = clid17_persons;
		this.clid17_amount = clid17_amount;
		this.clid18_count = clid18_count;
		this.clid18_persons = clid18_persons;
		this.clid18_amount = clid18_amount;
		this.clid19_count = clid19_count;
		this.clid19_persons = clid19_persons;
		this.clid19_amount = clid19_amount;
		this.clid20_count = clid20_count;
		this.clid20_persons = clid20_persons;
		this.clid20_amount = clid20_amount;
		this.clid21_count = clid21_count;
		this.clid21_persons = clid21_persons;
		this.clid21_amount = clid21_amount;
		this.clid22_count = clid22_count;
		this.clid22_persons = clid22_persons;
		this.clid22_amount = clid22_amount;
		this.clid23_count = clid23_count;
		this.clid23_persons = clid23_persons;
		this.clid23_amount = clid23_amount;
		this.clid24_count = clid24_count;
		this.clid24_persons = clid24_persons;
		this.clid24_amount = clid24_amount;
		this.clid25_count = clid25_count;
		this.clid25_persons = clid25_persons;
		this.clid25_amount = clid25_amount;
		this.clid26_count = clid26_count;
		this.clid26_persons = clid26_persons;
		this.clid26_amount = clid26_amount;
		this.clid27_count = clid27_count;
		this.clid27_persons = clid27_persons;
		this.clid27_amount = clid27_amount;
		this.clid28_count = clid28_count;
		this.clid28_persons = clid28_persons;
		this.clid28_amount = clid28_amount;
		this.clid29_count = clid29_count;
		this.clid29_persons = clid29_persons;
		this.clid29_amount = clid29_amount;
		this.clid30_count = clid30_count;
		this.clid30_persons = clid30_persons;
		this.clid30_amount = clid30_amount;
		this.clid31_count = clid31_count;
		this.clid31_persons = clid31_persons;
		this.clid31_amount = clid31_amount;
		this.clid32_count = clid32_count;
		this.clid32_persons = clid32_persons;
		this.clid32_amount = clid32_amount;
		this.clid33_count = clid33_count;
		this.clid33_persons = clid33_persons;
		this.clid33_amount = clid33_amount;
		this.clid34_count = clid34_count;
		this.clid34_persons = clid34_persons;
		this.clid34_amount = clid34_amount;
		this.clid35_count = clid35_count;
		this.clid35_persons = clid35_persons;
		this.clid35_amount = clid35_amount;
		this.clid36_count = clid36_count;
		this.clid36_persons = clid36_persons;
		this.clid36_amount = clid36_amount;
		this.clid37_count = clid37_count;
		this.clid37_persons = clid37_persons;
		this.clid37_amount = clid37_amount;
		this.clid38_count = clid38_count;
		this.clid38_persons = clid38_persons;
		this.clid38_amount = clid38_amount;
		this.clid39_count = clid39_count;
		this.clid39_persons = clid39_persons;
		this.clid39_amount = clid39_amount;
		this.clid40_count = clid40_count;
		this.clid40_persons = clid40_persons;
		this.clid40_amount = clid40_amount;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBpid() {
		return bpid;
	}

	public void setBpid(String bpid) {
		this.bpid = bpid;
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public int getTm() {
		return tm;
	}

	public void setTm(int tm) {
		this.tm = tm;
	}

	public long getClid1_count() {
		return clid1_count;
	}

	public void setClid1_count(long clid1_count) {
		this.clid1_count = clid1_count;
	}

	public long getClid1_persons() {
		return clid1_persons;
	}

	public void setClid1_persons(long clid1_persons) {
		this.clid1_persons = clid1_persons;
	}

	public long getClid1_amount() {
		return clid1_amount;
	}

	public void setClid1_amount(long clid1_amount) {
		this.clid1_amount = clid1_amount;
	}

	public long getClid2_count() {
		return clid2_count;
	}

	public void setClid2_count(long clid2_count) {
		this.clid2_count = clid2_count;
	}

	public long getClid2_persons() {
		return clid2_persons;
	}

	public void setClid2_persons(long clid2_persons) {
		this.clid2_persons = clid2_persons;
	}

	public long getClid2_amount() {
		return clid2_amount;
	}

	public void setClid2_amount(long clid2_amount) {
		this.clid2_amount = clid2_amount;
	}

	public long getClid3_count() {
		return clid3_count;
	}

	public void setClid3_count(long clid3_count) {
		this.clid3_count = clid3_count;
	}

	public long getClid3_persons() {
		return clid3_persons;
	}

	public void setClid3_persons(long clid3_persons) {
		this.clid3_persons = clid3_persons;
	}

	public long getClid3_amount() {
		return clid3_amount;
	}

	public void setClid3_amount(long clid3_amount) {
		this.clid3_amount = clid3_amount;
	}

	public long getClid4_count() {
		return clid4_count;
	}

	public void setClid4_count(long clid4_count) {
		this.clid4_count = clid4_count;
	}

	public long getClid4_persons() {
		return clid4_persons;
	}

	public void setClid4_persons(long clid4_persons) {
		this.clid4_persons = clid4_persons;
	}

	public long getClid4_amount() {
		return clid4_amount;
	}

	public void setClid4_amount(long clid4_amount) {
		this.clid4_amount = clid4_amount;
	}

	public long getClid5_count() {
		return clid5_count;
	}

	public void setClid5_count(long clid5_count) {
		this.clid5_count = clid5_count;
	}

	public long getClid5_persons() {
		return clid5_persons;
	}

	public void setClid5_persons(long clid5_persons) {
		this.clid5_persons = clid5_persons;
	}

	public long getClid5_amount() {
		return clid5_amount;
	}

	public void setClid5_amount(long clid5_amount) {
		this.clid5_amount = clid5_amount;
	}

	public long getClid6_count() {
		return clid6_count;
	}

	public void setClid6_count(long clid6_count) {
		this.clid6_count = clid6_count;
	}

	public long getClid6_persons() {
		return clid6_persons;
	}

	public void setClid6_persons(long clid6_persons) {
		this.clid6_persons = clid6_persons;
	}

	public long getClid6_amount() {
		return clid6_amount;
	}

	public void setClid6_amount(long clid6_amount) {
		this.clid6_amount = clid6_amount;
	}

	public long getClid7_count() {
		return clid7_count;
	}

	public void setClid7_count(long clid7_count) {
		this.clid7_count = clid7_count;
	}

	public long getClid7_persons() {
		return clid7_persons;
	}

	public void setClid7_persons(long clid7_persons) {
		this.clid7_persons = clid7_persons;
	}

	public long getClid7_amount() {
		return clid7_amount;
	}

	public void setClid7_amount(long clid7_amount) {
		this.clid7_amount = clid7_amount;
	}

	public long getClid8_count() {
		return clid8_count;
	}

	public void setClid8_count(long clid8_count) {
		this.clid8_count = clid8_count;
	}

	public long getClid8_persons() {
		return clid8_persons;
	}

	public void setClid8_persons(long clid8_persons) {
		this.clid8_persons = clid8_persons;
	}

	public long getClid8_amount() {
		return clid8_amount;
	}

	public void setClid8_amount(long clid8_amount) {
		this.clid8_amount = clid8_amount;
	}

	public long getClid9_count() {
		return clid9_count;
	}

	public void setClid9_count(long clid9_count) {
		this.clid9_count = clid9_count;
	}

	public long getClid9_persons() {
		return clid9_persons;
	}

	public void setClid9_persons(long clid9_persons) {
		this.clid9_persons = clid9_persons;
	}

	public long getClid9_amount() {
		return clid9_amount;
	}

	public void setClid9_amount(long clid9_amount) {
		this.clid9_amount = clid9_amount;
	}

	public long getClid10_count() {
		return clid10_count;
	}

	public void setClid10_count(long clid10_count) {
		this.clid10_count = clid10_count;
	}

	public long getClid10_persons() {
		return clid10_persons;
	}

	public void setClid10_persons(long clid10_persons) {
		this.clid10_persons = clid10_persons;
	}

	public long getClid10_amount() {
		return clid10_amount;
	}

	public void setClid10_amount(long clid10_amount) {
		this.clid10_amount = clid10_amount;
	}

	public long getClid11_count() {
		return clid11_count;
	}

	public void setClid11_count(long clid11_count) {
		this.clid11_count = clid11_count;
	}

	public long getClid11_persons() {
		return clid11_persons;
	}

	public void setClid11_persons(long clid11_persons) {
		this.clid11_persons = clid11_persons;
	}

	public long getClid11_amount() {
		return clid11_amount;
	}

	public void setClid11_amount(long clid11_amount) {
		this.clid11_amount = clid11_amount;
	}

	public long getClid12_count() {
		return clid12_count;
	}

	public void setClid12_count(long clid12_count) {
		this.clid12_count = clid12_count;
	}

	public long getClid12_persons() {
		return clid12_persons;
	}

	public void setClid12_persons(long clid12_persons) {
		this.clid12_persons = clid12_persons;
	}

	public long getClid12_amount() {
		return clid12_amount;
	}

	public void setClid12_amount(long clid12_amount) {
		this.clid12_amount = clid12_amount;
	}

	public long getClid13_count() {
		return clid13_count;
	}

	public void setClid13_count(long clid13_count) {
		this.clid13_count = clid13_count;
	}

	public long getClid13_persons() {
		return clid13_persons;
	}

	public void setClid13_persons(long clid13_persons) {
		this.clid13_persons = clid13_persons;
	}

	public long getClid13_amount() {
		return clid13_amount;
	}

	public void setClid13_amount(long clid13_amount) {
		this.clid13_amount = clid13_amount;
	}

	public long getClid14_count() {
		return clid14_count;
	}

	public void setClid14_count(long clid14_count) {
		this.clid14_count = clid14_count;
	}

	public long getClid14_persons() {
		return clid14_persons;
	}

	public void setClid14_persons(long clid14_persons) {
		this.clid14_persons = clid14_persons;
	}

	public long getClid14_amount() {
		return clid14_amount;
	}

	public void setClid14_amount(long clid14_amount) {
		this.clid14_amount = clid14_amount;
	}

	public long getClid15_count() {
		return clid15_count;
	}

	public void setClid15_count(long clid15_count) {
		this.clid15_count = clid15_count;
	}

	public long getClid15_persons() {
		return clid15_persons;
	}

	public void setClid15_persons(long clid15_persons) {
		this.clid15_persons = clid15_persons;
	}

	public long getClid15_amount() {
		return clid15_amount;
	}

	public void setClid15_amount(long clid15_amount) {
		this.clid15_amount = clid15_amount;
	}

	public long getClid16_count() {
		return clid16_count;
	}

	public void setClid16_count(long clid16_count) {
		this.clid16_count = clid16_count;
	}

	public long getClid16_persons() {
		return clid16_persons;
	}

	public void setClid16_persons(long clid16_persons) {
		this.clid16_persons = clid16_persons;
	}

	public long getClid16_amount() {
		return clid16_amount;
	}

	public void setClid16_amount(long clid16_amount) {
		this.clid16_amount = clid16_amount;
	}

	public long getClid17_count() {
		return clid17_count;
	}

	public void setClid17_count(long clid17_count) {
		this.clid17_count = clid17_count;
	}

	public long getClid17_persons() {
		return clid17_persons;
	}

	public void setClid17_persons(long clid17_persons) {
		this.clid17_persons = clid17_persons;
	}

	public long getClid17_amount() {
		return clid17_amount;
	}

	public void setClid17_amount(long clid17_amount) {
		this.clid17_amount = clid17_amount;
	}

	public long getClid18_count() {
		return clid18_count;
	}

	public void setClid18_count(long clid18_count) {
		this.clid18_count = clid18_count;
	}

	public long getClid18_persons() {
		return clid18_persons;
	}

	public void setClid18_persons(long clid18_persons) {
		this.clid18_persons = clid18_persons;
	}

	public long getClid18_amount() {
		return clid18_amount;
	}

	public void setClid18_amount(long clid18_amount) {
		this.clid18_amount = clid18_amount;
	}

	public long getClid19_count() {
		return clid19_count;
	}

	public void setClid19_count(long clid19_count) {
		this.clid19_count = clid19_count;
	}

	public long getClid19_persons() {
		return clid19_persons;
	}

	public void setClid19_persons(long clid19_persons) {
		this.clid19_persons = clid19_persons;
	}

	public long getClid19_amount() {
		return clid19_amount;
	}

	public void setClid19_amount(long clid19_amount) {
		this.clid19_amount = clid19_amount;
	}

	public long getClid20_count() {
		return clid20_count;
	}

	public void setClid20_count(long clid20_count) {
		this.clid20_count = clid20_count;
	}

	public long getClid20_persons() {
		return clid20_persons;
	}

	public void setClid20_persons(long clid20_persons) {
		this.clid20_persons = clid20_persons;
	}

	public long getClid20_amount() {
		return clid20_amount;
	}

	public void setClid20_amount(long clid20_amount) {
		this.clid20_amount = clid20_amount;
	}

	public long getClid21_count() {
		return clid21_count;
	}

	public void setClid21_count(long clid21_count) {
		this.clid21_count = clid21_count;
	}

	public long getClid21_persons() {
		return clid21_persons;
	}

	public void setClid21_persons(long clid21_persons) {
		this.clid21_persons = clid21_persons;
	}

	public long getClid21_amount() {
		return clid21_amount;
	}

	public void setClid21_amount(long clid21_amount) {
		this.clid21_amount = clid21_amount;
	}

	public long getClid22_count() {
		return clid22_count;
	}

	public void setClid22_count(long clid22_count) {
		this.clid22_count = clid22_count;
	}

	public long getClid22_persons() {
		return clid22_persons;
	}

	public void setClid22_persons(long clid22_persons) {
		this.clid22_persons = clid22_persons;
	}

	public long getClid22_amount() {
		return clid22_amount;
	}

	public void setClid22_amount(long clid22_amount) {
		this.clid22_amount = clid22_amount;
	}

	public long getClid23_count() {
		return clid23_count;
	}

	public void setClid23_count(long clid23_count) {
		this.clid23_count = clid23_count;
	}

	public long getClid23_persons() {
		return clid23_persons;
	}

	public void setClid23_persons(long clid23_persons) {
		this.clid23_persons = clid23_persons;
	}

	public long getClid23_amount() {
		return clid23_amount;
	}

	public void setClid23_amount(long clid23_amount) {
		this.clid23_amount = clid23_amount;
	}

	public long getClid24_count() {
		return clid24_count;
	}

	public void setClid24_count(long clid24_count) {
		this.clid24_count = clid24_count;
	}

	public long getClid24_persons() {
		return clid24_persons;
	}

	public void setClid24_persons(long clid24_persons) {
		this.clid24_persons = clid24_persons;
	}

	public long getClid24_amount() {
		return clid24_amount;
	}

	public void setClid24_amount(long clid24_amount) {
		this.clid24_amount = clid24_amount;
	}

	public long getClid25_count() {
		return clid25_count;
	}

	public void setClid25_count(long clid25_count) {
		this.clid25_count = clid25_count;
	}

	public long getClid25_persons() {
		return clid25_persons;
	}

	public void setClid25_persons(long clid25_persons) {
		this.clid25_persons = clid25_persons;
	}

	public long getClid25_amount() {
		return clid25_amount;
	}

	public void setClid25_amount(long clid25_amount) {
		this.clid25_amount = clid25_amount;
	}

	public long getClid26_count() {
		return clid26_count;
	}

	public void setClid26_count(long clid26_count) {
		this.clid26_count = clid26_count;
	}

	public long getClid26_persons() {
		return clid26_persons;
	}

	public void setClid26_persons(long clid26_persons) {
		this.clid26_persons = clid26_persons;
	}

	public long getClid26_amount() {
		return clid26_amount;
	}

	public void setClid26_amount(long clid26_amount) {
		this.clid26_amount = clid26_amount;
	}

	public long getClid27_count() {
		return clid27_count;
	}

	public void setClid27_count(long clid27_count) {
		this.clid27_count = clid27_count;
	}

	public long getClid27_persons() {
		return clid27_persons;
	}

	public void setClid27_persons(long clid27_persons) {
		this.clid27_persons = clid27_persons;
	}

	public long getClid27_amount() {
		return clid27_amount;
	}

	public void setClid27_amount(long clid27_amount) {
		this.clid27_amount = clid27_amount;
	}

	public long getClid28_count() {
		return clid28_count;
	}

	public void setClid28_count(long clid28_count) {
		this.clid28_count = clid28_count;
	}

	public long getClid28_persons() {
		return clid28_persons;
	}

	public void setClid28_persons(long clid28_persons) {
		this.clid28_persons = clid28_persons;
	}

	public long getClid28_amount() {
		return clid28_amount;
	}

	public void setClid28_amount(long clid28_amount) {
		this.clid28_amount = clid28_amount;
	}

	public long getClid29_count() {
		return clid29_count;
	}

	public void setClid29_count(long clid29_count) {
		this.clid29_count = clid29_count;
	}

	public long getClid29_persons() {
		return clid29_persons;
	}

	public void setClid29_persons(long clid29_persons) {
		this.clid29_persons = clid29_persons;
	}

	public long getClid29_amount() {
		return clid29_amount;
	}

	public void setClid29_amount(long clid29_amount) {
		this.clid29_amount = clid29_amount;
	}

	public long getClid30_count() {
		return clid30_count;
	}

	public void setClid30_count(long clid30_count) {
		this.clid30_count = clid30_count;
	}

	public long getClid30_persons() {
		return clid30_persons;
	}

	public void setClid30_persons(long clid30_persons) {
		this.clid30_persons = clid30_persons;
	}

	public long getClid30_amount() {
		return clid30_amount;
	}

	public void setClid30_amount(long clid30_amount) {
		this.clid30_amount = clid30_amount;
	}

	public long getClid31_count() {
		return clid31_count;
	}

	public void setClid31_count(long clid31_count) {
		this.clid31_count = clid31_count;
	}

	public long getClid31_persons() {
		return clid31_persons;
	}

	public void setClid31_persons(long clid31_persons) {
		this.clid31_persons = clid31_persons;
	}

	public long getClid31_amount() {
		return clid31_amount;
	}

	public void setClid31_amount(long clid31_amount) {
		this.clid31_amount = clid31_amount;
	}

	public long getClid32_count() {
		return clid32_count;
	}

	public void setClid32_count(long clid32_count) {
		this.clid32_count = clid32_count;
	}

	public long getClid32_persons() {
		return clid32_persons;
	}

	public void setClid32_persons(long clid32_persons) {
		this.clid32_persons = clid32_persons;
	}

	public long getClid32_amount() {
		return clid32_amount;
	}

	public void setClid32_amount(long clid32_amount) {
		this.clid32_amount = clid32_amount;
	}

	public long getClid33_count() {
		return clid33_count;
	}

	public void setClid33_count(long clid33_count) {
		this.clid33_count = clid33_count;
	}

	public long getClid33_persons() {
		return clid33_persons;
	}

	public void setClid33_persons(long clid33_persons) {
		this.clid33_persons = clid33_persons;
	}

	public long getClid33_amount() {
		return clid33_amount;
	}

	public void setClid33_amount(long clid33_amount) {
		this.clid33_amount = clid33_amount;
	}

	public long getClid34_count() {
		return clid34_count;
	}

	public void setClid34_count(long clid34_count) {
		this.clid34_count = clid34_count;
	}

	public long getClid34_persons() {
		return clid34_persons;
	}

	public void setClid34_persons(long clid34_persons) {
		this.clid34_persons = clid34_persons;
	}

	public long getClid34_amount() {
		return clid34_amount;
	}

	public void setClid34_amount(long clid34_amount) {
		this.clid34_amount = clid34_amount;
	}

	public long getClid35_count() {
		return clid35_count;
	}

	public void setClid35_count(long clid35_count) {
		this.clid35_count = clid35_count;
	}

	public long getClid35_persons() {
		return clid35_persons;
	}

	public void setClid35_persons(long clid35_persons) {
		this.clid35_persons = clid35_persons;
	}

	public long getClid35_amount() {
		return clid35_amount;
	}

	public void setClid35_amount(long clid35_amount) {
		this.clid35_amount = clid35_amount;
	}

	public long getClid36_count() {
		return clid36_count;
	}

	public void setClid36_count(long clid36_count) {
		this.clid36_count = clid36_count;
	}

	public long getClid36_persons() {
		return clid36_persons;
	}

	public void setClid36_persons(long clid36_persons) {
		this.clid36_persons = clid36_persons;
	}

	public long getClid36_amount() {
		return clid36_amount;
	}

	public void setClid36_amount(long clid36_amount) {
		this.clid36_amount = clid36_amount;
	}

	public long getClid37_count() {
		return clid37_count;
	}

	public void setClid37_count(long clid37_count) {
		this.clid37_count = clid37_count;
	}

	public long getClid37_persons() {
		return clid37_persons;
	}

	public void setClid37_persons(long clid37_persons) {
		this.clid37_persons = clid37_persons;
	}

	public long getClid37_amount() {
		return clid37_amount;
	}

	public void setClid37_amount(long clid37_amount) {
		this.clid37_amount = clid37_amount;
	}

	public long getClid38_count() {
		return clid38_count;
	}

	public void setClid38_count(long clid38_count) {
		this.clid38_count = clid38_count;
	}

	public long getClid38_persons() {
		return clid38_persons;
	}

	public void setClid38_persons(long clid38_persons) {
		this.clid38_persons = clid38_persons;
	}

	public long getClid38_amount() {
		return clid38_amount;
	}

	public void setClid38_amount(long clid38_amount) {
		this.clid38_amount = clid38_amount;
	}

	public long getClid39_count() {
		return clid39_count;
	}

	public void setClid39_count(long clid39_count) {
		this.clid39_count = clid39_count;
	}

	public long getClid39_persons() {
		return clid39_persons;
	}

	public void setClid39_persons(long clid39_persons) {
		this.clid39_persons = clid39_persons;
	}

	public long getClid39_amount() {
		return clid39_amount;
	}

	public void setClid39_amount(long clid39_amount) {
		this.clid39_amount = clid39_amount;
	}

	public long getClid40_count() {
		return clid40_count;
	}

	public void setClid40_count(long clid40_count) {
		this.clid40_count = clid40_count;
	}

	public long getClid40_persons() {
		return clid40_persons;
	}

	public void setClid40_persons(long clid40_persons) {
		this.clid40_persons = clid40_persons;
	}

	public long getClid40_amount() {
		return clid40_amount;
	}

	public void setClid40_amount(long clid40_amount) {
		this.clid40_amount = clid40_amount;
	}

}
