package com.boyaa.service.hbase.query;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.boyaa.base.hbase.HBaseUtil;
import com.boyaa.base.utils.Constants;
import com.boyaa.base.utils.JSONUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HBaseClient extends HBaseUtil{


	public HBaseClient() {
	}

	public HBaseClient(String htableName) {
		super(htableName);
	}

	static Logger logger = Logger.getLogger(HBaseClient.class);
	static Logger errorLogger = Logger.getLogger("errorLogger");
	static Logger fatalLogger = Logger.getLogger("fatalLogger");



	/**
	 * 获取Scan对象
	 * @param filterList
	 * @param startRowKey
	 * @param endRowKey
	 * @return
	 */
	public Scan getScan(Scan scan,FilterList filterList, String startRowKey, String endRowKey) {
		if (StringUtils.isNotEmpty(startRowKey)) {
			scan.setStartRow(Bytes.toBytes(startRowKey));
		}
		if (StringUtils.isNotEmpty(endRowKey)) {
			scan.setStopRow(Bytes.toBytes(endRowKey));
		}
		if(filterList != null) {
			scan = scan.setFilter(filterList);
		}
		return scan;
	}
	
	public String[] getStartEndRowKey(JSONObject json) throws JSONException {
		// 先计算出总的rowkey范围
		String startRowKey = null;
		String endRowKey = null;
		JSONObject rowkeyRule = this.getRowKeyRule();//{"fields":{"_plat":5,"_uid":10,"_tm":10},"reverse":true,"line_num":true}
		
		JSONObject fields = rowkeyRule.getJSONObject(Constants.ROWKEY_JSON_FIELDS);
		Iterator<String> it = fields.keySet().iterator();
		String key = null;
		boolean isRemoveField=false;
		while(it.hasNext()) {
			key = it.next();
			
			if(isRemoveField){
				if(key.equals(Constants.COLUMN_NAME_TM)){
					json.remove(Constants.COLUMN_NAME_STARTTIME);
					json.remove(Constants.COLUMN_NAME_ENDTIME);
				}else{
					json.remove(key);
				}
				continue;
			}
			
			if(!json.containsKey(key)){
				isRemoveField=true;
				continue;
			}
		}
		
		if (rowkeyRule.getBoolean(Constants.ROWKEY_JSON_REVERSE)) {
			// 设置开始时间，获取endrowkey
			if(json.containsKey(Constants.COLUMN_NAME_STARTTIME)){
				json.put(Constants.COLUMN_NAME_TM,json.getLong(Constants.COLUMN_NAME_STARTTIME));
//			}else{
//				json.put(Constants.COLUMN_NAME_TM,0);
			}
			// 生成endrowkey，没有参数时使用9填充
			endRowKey = this.getRangeRowKey(json, 0, "z");

			// 设置结束时间，获取startrowkey。如果为第一页，rowkey由程序产生，以后都是直接在缓存数组中获取
			if(json.containsKey(Constants.COLUMN_NAME_ENDTIME)){
				json.put(Constants.COLUMN_NAME_TM,json.getLong(Constants.COLUMN_NAME_ENDTIME));
//			}else{
//				json.put(Constants.COLUMN_NAME_TM,new Date().getTime()/1000);
			}
			
			// 生成startrowkey，没有参数时使用0填充
			startRowKey = this.getRangeRowKey(json, 0, "0");
		} else {
			// 设置开始时间，获取endrowkey
			if(json.containsKey(Constants.COLUMN_NAME_STARTTIME)){
				json.put(Constants.COLUMN_NAME_TM,json.getLong(Constants.COLUMN_NAME_STARTTIME));
//			}else{
//				json.put(Constants.COLUMN_NAME_TM,0);
			}
			// 生成endrowkey，没有参数时使用9填充
			startRowKey = this.getRangeRowKey(json, 0, "0");

			// 设置结束时间，获取startrowkey。如果为第一页，rowkey由程序产生，以后都是直接在缓存数组中获取
			if(json.containsKey(Constants.COLUMN_NAME_ENDTIME)){
				json.put(Constants.COLUMN_NAME_TM,json.getLong(Constants.COLUMN_NAME_ENDTIME));
//			}else{
//				json.put(Constants.COLUMN_NAME_TM,new Date().getTime()/1000);
			}
			// 生成startrowkey，没有参数时使用0填充
			endRowKey = this.getRangeRowKey(json, 0, "z");
		}
		return new String[] { startRowKey, endRowKey };
	}
	
	/**
	 * 输出一个结果
	 */
	public JSONArray getResult(List<Result> result, JSONObject json) throws JSONException {
		JSONArray data = null;
		//如果没有指定输出的列,则输出所有列
		if(!json.containsKey(Constants.FILTER_KEYS) || StringUtils.isEmpty(json.getString(Constants.FILTER_KEYS))) {
			//是否要输出rowkey
			if(!json.containsKey(Constants.COLUMN_NAME_ROWKEY) || json.getIntValue(Constants.COLUMN_NAME_ROWKEY) == 0){
				data = this.getResult(result, false);
			}else{
				data = this.getResult(result,true);
			}
		} else {
			String[] filterColumns = json.getString(Constants.FILTER_KEYS).split(",");
			if(!json.containsKey(Constants.COLUMN_NAME_ROWKEY) || json.getIntValue(Constants.COLUMN_NAME_ROWKEY) == 0){
				data = this.getResult(result, false, filterColumns);
			}else{
				data = this.getResult(result,filterColumns);
			}
		}
		return data;
	}
	
	/**
	 *  返回JSONArray
	 * @param result 结果集
	 * @param withRowKey 是否需要输出rowkey
	 */
	public JSONArray getResult(List<Result> result, boolean withRowKey) throws JSONException {
		JSONArray array = JSONUtil.getJSONArray();
		Map<String, String[]> columnTypeMapping = getColumnTypeMapping();
		for (Result r : result) {
			JSONObject json = convertType(r, columnTypeMapping);
			if(withRowKey){
				json.put(Constants.COLUMN_NAME_ROWKEY, new String(r.getRow()));
			}
			array.add(json);
		}
		return array;
	}
	
	/**
	 * @param result 结果集
	 * @param withRowKey 是否要输出rowkey
	 * @param filterColumns 过滤列名数组
	 */
	public JSONArray getResult(List<Result> result, boolean withRowKey, String[] filterColumns) throws JSONException {
		JSONArray array = JSONUtil.getJSONArray();
		Map<String, String[]> columnTypeMapping = getColumnTypeMapping();
		for (Result r : result) {
			JSONObject json = convertType(r, filterColumns, columnTypeMapping);
			if(withRowKey){
				json.put(Constants.COLUMN_NAME_ROWKEY, new String(r.getRow()));
			}
			array.add(json);
		}
		return array;
	}
	public void close(){
		if(conn!=null){
			try {
				conn.close();
			} catch (IOException e) {
				e.printStackTrace();
				errorLogger.error("close hbase connnection error:",e);
			}
		}
	}
}
