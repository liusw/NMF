package com.boyaa.mf.quartz;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.boyaa.base.mail.Mail;
import com.boyaa.base.utils.Constants;
import com.boyaa.base.utils.CsvUtil;
import com.boyaa.base.utils.DateUtil;
import com.boyaa.base.utils.JSONUtil;
import com.boyaa.mf.entity.data.IncomeSituation;
import com.boyaa.mf.service.config.ConfigService;
import com.boyaa.mf.service.data.GamblingRankService;
import com.boyaa.mf.util.ColumnUtil;
import com.boyaa.service.hbase.query.HBaseClient;

/** 
 * @author huangwx 
 * @version 创建时间：May 25, 2015 2:32:55 PM 
 */
public class IncomeSituationJob implements Job {
	static Logger taskLogger = Logger.getLogger("taskLogger");
	static Logger errorLogger = Logger.getLogger("errorLogger");
	static Logger fatalLogger = Logger.getLogger("fatalLogger");
	
	@Autowired
	private GamblingRankService service;
	@Autowired
	private ConfigService configService;
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		String date = DateUtil.addDay("yyyyMMdd", -1);
		HBaseClient client = new HBaseClient();
		String tableName = "income_situation";
		client.setTableName(tableName);
		Table table = null;
		ResultScanner rs = null;
		PrintWriter print = null;
		long startTime;
		long endTime;
		try {
			table = client.getTable(client.getTableName());
			startTime = ColumnUtil.getTmValue(date,0);
			endTime = ColumnUtil.getTmValue(date,1);
			Scan scan = new Scan();
			JSONObject rowkeyJson = JSONUtil.getJSONObject();
			rowkeyJson.put(Constants.COLUMN_NAME_STARTTIME,startTime);
			rowkeyJson.put(Constants.COLUMN_NAME_ENDTIME,endTime);
			String[] startEndRowKey = client.getStartEndRowKey(rowkeyJson);
			Scan query = client.getScan(scan, null, startEndRowKey[0], startEndRowKey[1]);
			rs = table.getScanner(query);
			JSONObject jsonObject = JSONUtil.getJSONObject();
			jsonObject.put(Constants.COLUMN_NAME_ROWKEY, 1);
			List<Result> result = new ArrayList<Result>();
			for (Result r : rs) {
				result.add(r);
			}
			Map<Integer, IncomeSituation> map = new HashMap<Integer, IncomeSituation>();
			JSONArray data = client.getResult(result,jsonObject);
			if(data!=null && data.size()>0){
				for(int i=0;i<data.size();i++){
					JSONObject object = data.getJSONObject(i);
					int plat = object.getIntValue("_plat");
					IncomeSituation situation = null;
					if(map.containsKey(plat)){
						situation = map.get(plat);
						situation.setBySurplus(situation.getBySurplus()+(object.containsKey("by_surplus")?object.getLong("by_surplus"):0l));
						situation.setUsed10(situation.getUsed10()+(object.containsKey("used_10")?object.getLong("used_10"):0l));
						situation.setUsed12(situation.getUsed12()+(object.containsKey("used_12")?object.getLong("used_12"):0l));
						situation.setUsed13(situation.getUsed13()+(object.containsKey("used_13")?object.getLong("used_13"):0l));
						situation.setUsed15(situation.getUsed15()+(object.containsKey("used_15")?object.getLong("used_15"):0l));
						situation.setNotUsed10(situation.getNotUsed10()+(object.containsKey("not_used_10")?object.getLong("not_used_10"):0l));
						situation.setNotUsed12(situation.getNotUsed12()+(object.containsKey("not_used_12")?object.getLong("not_used_12"):0l));
						situation.setNotUsed13(situation.getNotUsed13()+(object.containsKey("not_used_13")?object.getLong("not_used_13"):0l));
						situation.setNotUsed15(situation.getNotUsed15()+(object.containsKey("not_used_15")?object.getLong("not_used_15"):0l));
						situation.setLeft10(situation.getLeft10()+(object.containsKey("left_10")?object.getLong("left_10"):0l));
						situation.setLeft12(situation.getLeft12()+(object.containsKey("left_12")?object.getLong("left_12"):0l));
						situation.setLeft13(situation.getLeft13()+(object.containsKey("left_13")?object.getLong("left_13"):0l));
						situation.setLeft15(situation.getLeft15()+(object.containsKey("left_15")?object.getLong("left_15"):0l));
					}else{
						situation = new IncomeSituation();
						situation.setBySurplus(object.containsKey("by_surplus")?object.getLong("by_surplus"):0l);
						situation.setUsed10(object.containsKey("used_10")?object.getLong("used_10"):0l);
						situation.setUsed12(object.containsKey("used_12")?object.getLong("used_12"):0l);
						situation.setUsed13(object.containsKey("used_13")?object.getLong("used_13"):0l);
						situation.setUsed15(object.containsKey("used_15")?object.getLong("used_15"):0l);
						situation.setNotUsed10(object.containsKey("not_used_10")?object.getLong("not_used_10"):0l);
						situation.setNotUsed12(object.containsKey("not_used_12")?object.getLong("not_used_12"):0l);
						situation.setNotUsed13(object.containsKey("not_used_13")?object.getLong("not_used_13"):0l);
						situation.setNotUsed15(object.containsKey("not_used_15")?object.getLong("not_used_15"):0l);
						situation.setLeft10(object.containsKey("left_10")?object.getLong("left_10"):0l);
						situation.setLeft12(object.containsKey("left_12")?object.getLong("left_12"):0l);
						situation.setLeft13(object.containsKey("left_13")?object.getLong("left_13"):0l);
						situation.setLeft15(object.containsKey("left_15")?object.getLong("left_15"):0l);
					}
					map.put(plat, situation);
				}
			}
			Map<Integer, String> platMap = configService.getPlatNameMap();
			if(map.size()>0){
				String path = com.boyaa.mf.constants.Constants.FILE_DIR + "incomeSituation_"+date+".csv";
				print = CsvUtil.getPrint(path);
				print.println("平台名称,博雅币结余,紫钻未使用个数,紫钻使用中个数,紫钻使用中剩余次数,蓝钻未使用个数,蓝钻使用中个数,蓝钻使用中剩余次数,红钻未使用个数,红钻使用中个数,红钻使用中剩余次数,黄钻未使用个数,黄钻使用中个数,黄钻使用中剩余次数");
				for(Entry<Integer, IncomeSituation> entry : map.entrySet()){
					int key = entry.getKey();
					IncomeSituation is = entry.getValue();
					StringBuffer sb = new StringBuffer();
					sb.append(platMap.containsKey(key)?platMap.get(key):key).append(",");
					sb.append(is.getBySurplus()).append(",");
					sb.append(is.getNotUsed10()).append(",");
					sb.append(is.getUsed10()).append(",");
					sb.append(is.getLeft10()).append(",");
					sb.append(is.getNotUsed12()).append(",");
					sb.append(is.getUsed12()).append(",");
					sb.append(is.getLeft12()).append(",");
					sb.append(is.getNotUsed13()).append(",");
					sb.append(is.getUsed13()).append(",");
					sb.append(is.getLeft13()).append(",");
					sb.append(is.getNotUsed15()).append(",");
					sb.append(is.getUsed15()).append(",");
					sb.append(is.getLeft15());
					print.println(sb.toString());
				}
				print.flush();
				StringBuilder sb = new StringBuilder();
				sb.append("上月结余汇总已导出,请查看附件!").append("<br/>");
				sb.append("问题反馈：黄万新(WatsonHuang@boyaa.com)");
				taskLogger.info("每月结余数据开始发送 timestamp="+DateUtil.addDay("yyyy-MM-dd HH:mm:ss", -1));
				new Mail().send("结余汇总-每月定时发送","ZhuangjieZheng@boyaa.com",sb.toString(),path);
				taskLogger.info("每月结余数据发送完成 timestamp="+DateUtil.addDay("yyyy-MM-dd HH:mm:ss", -1));
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			errorLogger.error(e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			errorLogger.error(e.getMessage());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			errorLogger.error(e.getMessage());
		}finally{
			if(print!=null)
				print.close();
			if(rs!=null)
				rs.close();
			try {
				if(table != null)
					table.close();
			} catch(Exception e) {
				errorLogger.error(e.getMessage());
			}
//			if(client!=null)
//				client.close();
		}
		
		try {
			//每个月的第一天清除掉前一个月的2人牌局排行榜信息
			Map<Object, Object> params = new HashMap<Object, Object>();
			String date1 = DateUtil.addDay("yyyyMMdd", 0);
			String firstDayOfLastMonth = DateUtil.getFirstDayOfMonth(date1, "yyyyMMdd", -1);
			params.put("tm", firstDayOfLastMonth);
			service.deleteByTm(params);
		} catch (ParseException e) {
			e.printStackTrace();
			errorLogger.error(e.getMessage());
		}
	}
}
