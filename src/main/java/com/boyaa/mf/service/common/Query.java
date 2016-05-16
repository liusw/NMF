package com.boyaa.mf.service.common;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.PageFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.boyaa.base.hbase.HBaseUtil;
import com.boyaa.base.utils.ArithmeticUtil;
import com.boyaa.base.utils.Constants;
import com.boyaa.servlet.ResultState;

public class Query extends HBaseUtil {
	static Logger logger = Logger.getLogger(Query.class);
	static Logger errorLogger = Logger.getLogger("errorLogger");
	static Logger fatalLogger = Logger.getLogger("fatalLogger");
	//cms分页，从当前页向前查5页，如果没有，说明已经到最后一页了，旧可以把最大页数传给客户端
	private final static int FORWARD_PAGE = 5;

	public ResultState scanRecords(JSONObject json) throws JSONException, IOException {
		ResultScanner rs = null;
		Table table = null;
		String msg = null;
		ResultState failure = new ResultState(ResultState.FAILURE);
		ResultState success = new ResultState(ResultState.SUCCESS);
		JSONObject rowkeyRule = this.getRowKeyRule();
		
		msg = checkRowKey(rowkeyRule, json);
		
		if(msg != null) {
			errorLogger.error(msg);
			failure.setMsg(msg);
			return failure;
		}
		
		try {
			String splitTableName = this.getSplitTableName(json);
			table = getTable(splitTableName);
			if (table == null) {
				msg = "could not get hbase table=" + splitTableName;
				errorLogger.error(msg);
				failure.setMsg(msg);
				return failure;
			}
	
			// 先计算出总的rowkey范围
			String startRowKey = null;
			String endRowKey = null;
			if(rowkeyRule.getBoolean(Constants.ROWKEY_JSON_REVERSE)) {
				// 设置开始时间，获取endrowkey
				json.put(Constants.COLUMN_NAME_TM, json.getLong(Constants.COLUMN_NAME_STARTTIME));
				// 生成endrowkey，没有参数时使用9填充
				endRowKey = this.getRangeRowKey(json, 0, "z");
				// endRowKey必须加1，因为hbase查询时不包括该rowkey值
				// endRowKey = addOne(endRowKey);
	
				// 设置结束时间，获取startrowkey。如果为第一页，rowkey由程序产生，以后都是直接在缓存数组中获取
				json.put(Constants.COLUMN_NAME_TM, json.getLong(Constants.COLUMN_NAME_ENDTIME));
				// 生成startrowkey，没有参数时使用0填充
				startRowKey = this.getRangeRowKey(json, 0, "0");
			} else {
				// 设置开始时间，获取endrowkey
				json.put(Constants.COLUMN_NAME_TM, json.getLong(Constants.COLUMN_NAME_STARTTIME));
				// 生成endrowkey，没有参数时使用9填充
				startRowKey = this.getRangeRowKey(json, 0, "0");
				// endRowKey必须加1，因为hbase查询时不包括该rowkey值
				// endRowKey = addOne(endRowKey);
	
				// 设置结束时间，获取startrowkey。如果为第一页，rowkey由程序产生，以后都是直接在缓存数组中获取
				json.put(Constants.COLUMN_NAME_TM, json.getLong(Constants.COLUMN_NAME_ENDTIME));
				// 生成startrowkey，没有参数时使用0填充
				endRowKey = this.getRangeRowKey(json, 0, "z");
			}
			
			// 当前页
			Integer currentPage = json.getIntValue(com.boyaa.mf.constants.Constants.DEFAULT_PAGE_PARAM);
			// 每页大小
			int pageSize = json.getIntValue(com.boyaa.mf.constants.Constants.DEFAULT_PAGESIZE_PARAM);
			JSONObject conditions = json.getJSONObject(Constants.comparator_condition);
			// 获取当前显示页面中第一条和最后一条记录之间的偏移量
			int total = getTotal(json);

			FilterList fl = this.getComparatorFilter(conditions);
			fl.addFilter(new PageFilter(total));
			Scan query = getScan(fl, startRowKey, endRowKey);
//			if(currentPage<=5 && pageSize <= 200)
//				query.setSmall(true);
			rs = table.getScanner(query);
			
			//跳过startRow行
			int startRow = (currentPage - 1) * pageSize;
			if(startRow != 0)
				rs.next(startRow);
			
			List<Result> result = new ArrayList<Result>();
			// 存储真实的能够获取到的记录数量
			int count = 0;
			for (Result r : rs) {
				if(count >= 0 && count < pageSize) {
					result.add(r);
//					result[count%pageSize] = r;
				}
				count++;
			}
			
			// 如果计算的值比实际查出的数量大，说明已经到最后一页了
			if (count < (FORWARD_PAGE + 1)*pageSize) {
				if (count % pageSize == 0)
					success.setMaxPage(((startRow + count) / pageSize));
				else
					success.setMaxPage((startRow + count) / pageSize + 1);
				success.setNum(startRow + count);
			}
			
			JSONArray data = this.getResult(result, json);
			if(json.containsKey("debug")) {
				success.getOther().put("startRow", startRow);
				success.getOther().put("count", count);
				success.getOther().put("forward", (FORWARD_PAGE + 1)*pageSize);
				success.getOther().put("total", total);
				success.getOther().put(Constants.COLUMN_NAME_STARTROW, startRowKey);
				success.getOther().put(Constants.COLUMN_NAME_ENDROW, endRowKey);
			}
			success.setData(data.toString());
			success.setMsg(msg);
			logger.info("scan records with table=" + splitTableName + ", startRowKey=" + startRowKey + ", endRowKey=" + endRowKey + ", pageSize=" + pageSize + ", currentPage" + currentPage + ", total=" + total + ", count=" + count + "data.length=" + data.size() + ", fl=" + fl.toString());
			logger.info("json=" + json.toString());
			logger.info("conditions=" + conditions.toString());
		} catch(Exception e) {
			errorLogger.error(e.getMessage());
		} finally {
			try {
				if(rs != null)
					rs.close();
			}catch(Exception e) {
				errorLogger.error(e.getMessage());
			}
			try {
				if(table != null)
					table.close();
			} catch(Exception e) {
				errorLogger.error(e.getMessage());
			}
//			try {
//				this.close();
//			} catch(Exception e) {
//				errorLogger.error(e.getMessage());
//			}
		}
		return success;
	}
	
	public ResultState saveRecords(JSONObject json, PrintWriter print) throws JSONException, IOException {
		ResultState failure = new ResultState(ResultState.FAILURE);
		ResultState success = new ResultState(ResultState.SUCCESS);
		Table table = null;
		String msg = null;
		ResultScanner rs = null;
		try {
			String splitTableName = this.getSplitTableName(json);
			table = getTable(splitTableName);
			if (table == null) {
				msg = "could not get hbase table=" + splitTableName;
				errorLogger.error(msg);
				failure.setMsg(msg);
				return failure;
			}
	
			// 先计算出总的rowkey范围
			String startRowKey = null;
			String endRowKey = null;
			JSONObject rowkeyRule = this.getRowKeyRule();
			if(rowkeyRule.getBoolean(Constants.ROWKEY_JSON_REVERSE)) {
				// 设置开始时间，获取endrowkey
				json.put(Constants.COLUMN_NAME_TM, json.getLong(Constants.COLUMN_NAME_STARTTIME));
				// 生成endrowkey，没有参数时使用9填充
				endRowKey = this.getRangeRowKey(json, 0, "z");
	
				// 设置结束时间，获取startrowkey。如果为第一页，rowkey由程序产生，以后都是直接在缓存数组中获取
				json.put(Constants.COLUMN_NAME_TM, json.getLong(Constants.COLUMN_NAME_ENDTIME));
				// 生成startrowkey，没有参数时使用0填充
				startRowKey = this.getRangeRowKey(json, 0, "0");
			} else {
				// 设置开始时间，获取endrowkey
				json.put(Constants.COLUMN_NAME_TM, json.getLong(Constants.COLUMN_NAME_STARTTIME));
				// 生成endrowkey，没有参数时使用9填充
				startRowKey = this.getRangeRowKey(json, 0, "0");
	
				// 设置结束时间，获取startrowkey。如果为第一页，rowkey由程序产生，以后都是直接在缓存数组中获取
				json.put(Constants.COLUMN_NAME_TM, json.getLong(Constants.COLUMN_NAME_ENDTIME));
				// 生成startrowkey，没有参数时使用0填充
				endRowKey = this.getRangeRowKey(json, 0, "z");
			}
	
			JSONObject conditions = json.getJSONObject(Constants.comparator_condition);
			FilterList fl = null;
			if(conditions.size() > 0)
				fl = this.getComparatorFilter(conditions);
	
			String[] filterColumns = json.getString(Constants.FILTER_KEYS).split(",");
			rs = table.getScanner(getScan(fl, startRowKey, endRowKey));
			this.writeResult(rs, print, filterColumns, true);
			print.flush();
			success.getOther().put(Constants.COLUMN_NAME_STARTROW, startRowKey);
			logger.info("save records with table=" + splitTableName + ", startRowKey=" + startRowKey + ", endRowKey=" + endRowKey);
			logger.info("json=" + json.toString());
		} catch(Exception e) {
			errorLogger.error(e.getMessage());
		} finally {
			try {
				if(rs != null)
					rs.close();
			}catch(Exception e) {
				errorLogger.error(e.getMessage());
			}
			try {
				if(table != null)
					table.close();
			} catch(Exception e) {
				errorLogger.error(e.getMessage());
			}
//			try {
//				this.close();
//			} catch(Exception e) {
//				errorLogger.error(e.getMessage());
//			}
		}
		return success;
	}
	
	public String checkRowKey(JSONObject rowkeyRule, JSONObject json) throws JSONException {
		JSONObject fields = rowkeyRule.getJSONObject("fields");
		Iterator<String> it = fields.keySet().iterator();
		StringBuffer sb = new StringBuffer();
		boolean flag = false;
		while(it.hasNext()) {
			String key = it.next();
			if(json.containsKey(key)) {
				sb.append("1");
				if(flag) {
					JSONObject conditions = json.getJSONObject(Constants.comparator_condition);
					//把未设置成过滤条件的字段转成等于条件
					if(!conditions.containsKey(key)) {
						conditions.put(key, new String[]{json.getString(key), "0"});
					}
				}
			} else {
				if(Constants.COLUMN_NAME_TM.equals(key) && json.containsKey(Constants.COLUMN_NAME_STARTTIME) && json.containsKey(Constants.COLUMN_NAME_ENDTIME)) {
					sb.append("1");
				} else {
					sb.append("0");
					flag = true;
				}
			}
		}
		if(sb.toString().contains("01")) {
			String msg = "查询主键rowkey=" + fields.toString().replaceAll("\"", "\\\\\\\"") + "中间缺失字段，请补齐！";
			errorLogger.error(msg);
			return msg;
		}
		return null;
	}
	
	public ResultState getRecord(JSONObject json) throws JSONException, IOException {
		ResultState failure = new ResultState(ResultState.FAILURE);
		ResultState success = new ResultState(ResultState.SUCCESS);
		String msg = null;
		Table table = null;
		ResultScanner rs = null;
		
		JSONObject rowkeyRule = this.getRowKeyRule();
		msg = checkRowKey(rowkeyRule, json);
		
		try {
			String splitTableName = this.getSplitTableName(json);
			table = getTable(splitTableName);
			if (table == null) {
				msg = "could not get hbase table=" + splitTableName;
				errorLogger.error(msg);
				failure.setMsg(msg);
				return failure;
			}
			String startRowKey = this.getRowKey(json, 0);// json.getString(ServletConstants.servlet_rowkey);
			String endRowKey = ArithmeticUtil.addNum(startRowKey, Integer.valueOf(Constants.record_addnum));
			Scan scan = new Scan();
			scan.setCacheBlocks(false);
			if (StringUtils.isNotEmpty(startRowKey)) {
				scan.setStartRow(Bytes.toBytes(startRowKey));
			}
			if (StringUtils.isNotEmpty(endRowKey)) {
				scan.setStopRow(Bytes.toBytes(endRowKey));
			}
			scan.setFilter(new PageFilter(1));
			JSONObject data = null;
			rs = table.getScanner(scan);
			for(Result r : rs) {
				data = this.getResult(r, json);
			}
			if(json.containsKey("debug")) {
				success.getOther().put(Constants.COLUMN_NAME_STARTROW, startRowKey);
				success.getOther().put(Constants.COLUMN_NAME_ENDROW, endRowKey);
			}
			if (data == null) {
				success.setMsg("no data");
				return success;
			}
			success.setMsg(msg);
			success.setData(data.toString());
			logger.info("get record with table=" + splitTableName + ", startRowKey=" + startRowKey + ", endRowKey=" + endRowKey);
		} catch(Exception e) {
			errorLogger.error(e.getMessage());
		} finally {
			try {
				if(rs != null)
					rs.close();
			} catch(Exception e){
				errorLogger.error(e.getMessage());
			}
			try {
				if(table != null)
					table.close();
			}catch(Exception e) {
				errorLogger.error(e.getMessage());
			}
//			try {
//				this.close();
//			} catch(Exception e){
//				errorLogger.error(e.getMessage());
//			}
		}
		return success;
	}

	/**
	 * 总共需要获取的数据量，即最大页数需要的数据总量
	 * @param json
	 * @return
	 * @throws JSONException
	 */
	private int getTotal(JSONObject json) throws JSONException {
		int pageSize = json.getIntValue(com.boyaa.mf.constants.Constants.DEFAULT_PAGESIZE_PARAM);
		int pageNo = json.getIntValue(com.boyaa.mf.constants.Constants.DEFAULT_PAGE_PARAM);
//		int pageRange = json.getInt(ServletConstants.servlet_rangepage);
//		int offset = 0;
		// 如果当前页小于或等于1/2的显示页范围，那么offset为所有显示页的数据量
//		if (pageNo <= 5) {
//			offset = pageRange * pageSize;
//		} else {
//			// 前面保留4页
//			offset = (pageRange + pageNo - 5) * pageSize;
//		}
		int offset = (pageNo + FORWARD_PAGE) * pageSize;
		return offset;
	}

	public Scan getScan(FilterList list, String startRowKey, String endRowKey) {
		Scan scan = new Scan();
//		scan.setCacheBlocks(false);
		if (StringUtils.isNotEmpty(startRowKey)) {
			scan.setStartRow(Bytes.toBytes(startRowKey));
		}
		if (StringUtils.isNotEmpty(endRowKey)) {
			scan.setStopRow(Bytes.toBytes(endRowKey));
		}
		if(list != null) {
			scan = scan.setFilter(list);
		}
		return scan;
	}
}
