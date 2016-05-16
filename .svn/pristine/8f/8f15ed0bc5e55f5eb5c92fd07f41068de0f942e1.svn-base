package com.boyaa.service.hbase.query;

import java.io.IOException;
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
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.boyaa.base.utils.Constants;
import com.boyaa.base.utils.JSONUtil;
import com.boyaa.mf.util.CommonUtil;
import com.boyaa.service.hbase.parser.SelectSqlVisitor;

public class HBaseDataQuery{

	static Logger taskLogger = Logger.getLogger("taskLogger");
	static Logger errorLogger = Logger.getLogger("errorLogger");
	static Logger fatalLogger = Logger.getLogger("fatalLogger");
	
	private final static int FORWARD_PAGE = 5;
	
	public JSONObject selectQuery(SelectSqlVisitor sqlVisitor,long pageIndex,long pageSize){
		HBaseClient client = new HBaseClient();
		Table table = null;
		ResultScanner rs = null;
		
		JSONObject rJson = JSONUtil.getJSONObject();
		try {
			JSONObject rowkeyJson = sqlVisitor.getRowkeyJson();
			List<String> returnColumns = sqlVisitor.getReturnColumns();
			

			client.setTableName(sqlVisitor.getTable());
			String splitTableName = client.getSplitTableName(rowkeyJson);
			
			table = client.getTable(splitTableName);
			if (table == null) {
				errorLogger.error("could not get hbase table=" + splitTableName);
				return null;
			}
			
			//额外多取FORWARD_PAGE页
			long baseTotal = (pageIndex + FORWARD_PAGE) * pageSize;
			long total = baseTotal;
			//开始的行
			Long startRow = 0L;
			if(sqlVisitor.getOffset()!=null && sqlVisitor.getRowCount()!=null){
				startRow = sqlVisitor.getOffset() + (pageIndex - 1) * pageSize;
				baseTotal = baseTotal >sqlVisitor.getRowCount()?sqlVisitor.getRowCount():baseTotal;
				total = baseTotal + sqlVisitor.getOffset();
			}else{
				startRow = (pageIndex - 1) * pageSize;
			}
			
			FilterList filterList = sqlVisitor.getFilterList();
			filterList.addFilter(new PageFilter(total));
			
			Scan query = null;
			if(StringUtils.isNotBlank(sqlVisitor.getRowKey())){
				query = client.getScan(sqlVisitor.getScan(),filterList,sqlVisitor.getRowKey(),sqlVisitor.getRowKey()+"1");
			}else{
				String[] startEndRowKey = client.getStartEndRowKey(rowkeyJson);
				taskLogger.info("scan rowkey --> "+startEndRowKey[0]+","+startEndRowKey[1]+"1");
				query = client.getScan(sqlVisitor.getScan(),filterList, startEndRowKey[0], startEndRowKey[1]+"1");
			}
			rs = table.getScanner(query);
			
			//设置返回的列
			String retKeys = CommonUtil.listToString(returnColumns);
			JSONObject jsonObject = JSONUtil.getJSONObject();
			if(StringUtils.isNotBlank(retKeys)){
				jsonObject.put(Constants.FILTER_KEYS, retKeys);
			}
			jsonObject.put(Constants.COLUMN_NAME_ROWKEY, 1);
			
			//跳过startRow行
			if(startRow != 0){
				rs.next(startRow.intValue());
			}
			
			List<Result> result = new ArrayList<Result>();
			// 存储真实的能够获取到的记录数量
			int count = 0;
			long showData = (total-startRow)>pageSize?pageSize:total-startRow;
			for (Result r : rs) {
				//if(count >= 0 && count < pageSize) {
				if(count >= 0 && count < showData) {
					result.add(r);
				}
				count++;
			}
			
			JSONArray data = client.getResult(result,jsonObject);
			JSONObject title = getTitle(data);
			
			long totalPage = 1;
			if((startRow + count)<total){//查出的下标，小于当前最大值
				totalPage = getPageSize(startRow + count,pageSize);
			}else{
				totalPage = getPageSize(baseTotal,pageSize);
			}
			
			rJson.put("o", data);
			rJson.put("title", title);
			rJson.put("pageIndex", pageIndex);
			rJson.put("totalPage", totalPage);
			rJson.put("pageNumStart", getPageNumStart(5,pageIndex,totalPage));
			rJson.put("pageNumEnd", getPageNumEnd(5,pageIndex,totalPage));
			
			return rJson;
		} catch (JSONException e) {
			errorLogger.error(e.getMessage());
		} catch (IOException e) {
			errorLogger.error(e.getMessage());
		}finally {
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
//				client.close();
//			} catch(Exception e) {
//				errorLogger.error(e.getMessage());
//			}
		}
		return null;
	}
	
	private JSONObject getTitle(JSONArray data) throws JSONException {
		JSONObject jsonObject = JSONUtil.getJSONObject();
		if(data!=null && data.size()>0){
			for(int i=0;i<data.size();i++){
				JSONObject json = data.getJSONObject(i);
				Iterator<String> keys = json.keySet().iterator();
				String key=null;
				while(keys.hasNext()){  
					key=keys.next();  
					if(!jsonObject.containsKey(key)){
						jsonObject.put(key, key);
		         	}
				}
			}
		}
		return jsonObject;
	}
	
	private long getPageNumStart(long showPageNum,long currentPage,long totalPage) {
		// 显示页数的一半
		int halfPage = (int) Math.ceil((double) showPageNum / 2);
		if (halfPage >= currentPage) {
			return 1;
		} else {
			if (currentPage + halfPage > totalPage) {
				return (totalPage - showPageNum + 1) <= 0 ? 1 : (totalPage - showPageNum + 1);
			} else {
				return currentPage - halfPage + 1;
			}
		}
	}

	private long getPageNumEnd(long showPageNum,long currentPage,long totalPage) {
		// 显示页数的一半
		int halfPage = (int) Math.ceil((double) showPageNum / 2);
		if (halfPage >= currentPage) {
			return showPageNum > totalPage ? totalPage : showPageNum;
		} else {
			if (currentPage + halfPage >= totalPage) {
				return totalPage;
			} else {
				return currentPage + halfPage;
			}
		}
	}
	
	private long getPageSize(long totalRecord,long pageRecord){
		return totalRecord % pageRecord == 0 ? totalRecord	/ pageRecord : totalRecord / pageRecord + 1;
	}
}
