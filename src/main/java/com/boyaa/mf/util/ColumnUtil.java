package com.boyaa.mf.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.boyaa.base.listener.HbtablesFileListener;
import com.boyaa.base.utils.AppContext;
import com.boyaa.base.utils.Constants;
import com.boyaa.base.utils.DateUtil;
import com.boyaa.base.utils.JSONUtil;
import com.boyaa.base.utils.TypeUtil;

/**
 * 列工具类,除了和列相关的工具方法,不要加在这里面
 *
 * @作者 : huangyineng
 * @日期 : 2015-3-18  下午4:40:05
 */
public class ColumnUtil {
	static Logger errorLogger = Logger.getLogger("errorLogger");
	
	/**
	 * 获取分区的配置信息
	 * @return
	 * @throws JSONException
	 */
	protected static JSONObject getPartitionConfig(String tableName) throws JSONException {
		String config = AppContext.partition.get(tableName);
		if(StringUtils.isEmpty(config)){
			config = AppContext.partition.get("default");
		}
		if(StringUtils.isEmpty(config)) {
			errorLogger.error("get partition from config with key=" + tableName + " is null!");
			return null;
		}
		return JSONUtil.parseObject(config);
	}
	
	/**
	 * 获取hive表的所有分区
	 * @param tableName
	 * @return
	 */
	public static List<String> getPartitionList(String tableName){
		List<String> partitions = null;
		try {
			JSONObject partition = getPartitionConfig(tableName);
			if(partition == null) return null;
			
			partitions = new ArrayList<String>();
			
			Iterator<String> columns = partition.keySet().iterator();
			while(columns.hasNext()) {
				String column = columns.next();
				String value = partition.getString(column);
				String[] values = value.split("#");
				for(String v : values) {
					String[] items = v.split(":");
					if("rename".endsWith(items[0])) {
						partitions.add(items[1]);
					}
				}
			}
		} catch (JSONException e) {
			errorLogger.error(e.getMessage());
		}
		return partitions;
	}
	
	
	
	/**
	 * 获取rowkey
	 * @param tableName
	 * @return
	 * @throws JSONException
	 */
	public static List<String> getRowKeyRule(String tableName){
		JSONObject rowKeyRule = Constants.rowKeyRuleMapping.get(tableName);
		if(rowKeyRule == null)
			rowKeyRule = Constants.defaultRowKeyRule;
		
		List<String> keys = new ArrayList<String>();
		try {
			JSONObject fields = rowKeyRule.getJSONObject(Constants.ROWKEY_JSON_FIELDS);
			Iterator<String> it = fields.keySet().iterator();
			
			while(it.hasNext()) {
				keys.add(it.next());
			}
		} catch (JSONException e) {
			errorLogger.error(e.getMessage());
		}
		return keys;
	}
	
	/**
	 * 获取指定表中各列的类型
	 * @return
	 */
	public static Map<String, String[]> getColumnTypeMapping(String tableName) {
		if(HbtablesFileListener.hbtableFiles!=null){
			return HbtablesFileListener.hbtableFiles.get(tableName);
		}
		return null;
	}
	
	/**
	 * 把指定列的值转化为设定类型的byte数组，默认类型为字符串类型
	 * @param qualifier
	 * @param value
	 * @param columnTypeMapping
	 * @return
	 */
	public static byte[] convertType(String qualifier, String value, Map<String, String[]> columnTypeMapping) {
		String[] typeValue = getTypeValue(qualifier, columnTypeMapping, Constants.DEFAULT_TYPE);
		byte[] result = null;
		try {
			result = TypeUtil.convertTo(typeValue[0], value, null);
		} catch (IllegalArgumentException e) {
			result = TypeUtil.convertTo(Constants.DEFAULT_TYPE, value, null);
		}
		return result;
	}
	
	/**
	 * 获取列对应的类型：先从表度应的配置文件读取配置，如果没有找到，则从默认配置文件中查找，也就是表配置覆盖默认配置
	 * @param qualifier
	 * @param columnTypeMapping
	 * @param defaultType
	 * @return
	 */
	public static String[] getTypeValue(String qualifier, Map<String, String[]> columnTypeMapping, String defaultType) {
		String[] typeValue = null;
		if(columnTypeMapping != null) {
			typeValue = columnTypeMapping.get(qualifier);
			if(typeValue == null) {
				typeValue = HbtablesFileListener.hbtableDefaultFile.get(qualifier);
			}
		}
		// 添加动态列类型，比如user_dau各盲注场牌局数：inc_bmpc_为int表示所有以这个字段开头的列类型都是int
		if(typeValue == null && columnTypeMapping!=null) {
			Iterator<String> it = columnTypeMapping.keySet().iterator();
			while(it.hasNext()) {
				String key = it.next();
				if(qualifier.startsWith(key) && qualifier.substring(key.length()).matches("\\d+")) {
					typeValue = columnTypeMapping.get(key);
					break;
				}
			}
		}
		if(typeValue == null && defaultType != null) {
			typeValue = new String[]{defaultType};
		}
		return typeValue;
	}
	
	/**
	 * 判断字段在数据库中的类型是否为数字
	 * @param columnTypeMapping
	 * @param key
	 * @return
	 */
	public static boolean isNumberType(Map<String, String[]> columnTypeMapping,String key){
		String[] typeValue = getTypeValue(key, columnTypeMapping, "string");
		if(typeValue!=null && typeValue.length>0 && ("int".equalsIgnoreCase(typeValue[0]) || "long".equalsIgnoreCase(typeValue[0])|| 
				"float".equalsIgnoreCase(typeValue[0])|| "double".equalsIgnoreCase(typeValue[0]))){
			return true;
		}
		return false;
	}

	/**
	 * 获取指定类型的最大值
	 * @param qualifier
	 * @param columnTypeMapping
	 * @return
	 */
	public static byte[] getMaxValue(String qualifier, Map<String, String[]> columnTypeMapping) {
		String[] typeValue = getTypeValue(qualifier, columnTypeMapping, "int");
		byte[] result = null;
		try {
			result = TypeUtil.getMaxValue(typeValue[0]);
		} catch (IllegalArgumentException e) {
			
		}
		return result;
	}
	
	/**
	 * 获取指定类型的最小值
	 * @param qualifier
	 * @param columnTypeMapping
	 * @return
	 */
	public static byte[] getMinValue(String qualifier, Map<String, String[]> columnTypeMapping) {
		String[] typeValue = getTypeValue(qualifier, columnTypeMapping, "int");
		byte[] result = null;
		try {
			result = TypeUtil.getMinValue(typeValue[0]);
		} catch (IllegalArgumentException e) {
			
		}
		return result;
	}
	
	/**
	 * 获取时间参数,主要是给rowkey用
	 * @param value
	 * @param type
	 * @return
	 * @throws ParseException
	 */
	public static long getTmValue(String value,int type) throws ParseException{
		SimpleDateFormat sdf = null;
		if(DateUtil.defaultDatePattern.matcher(value.trim()).matches()) {
			sdf = new SimpleDateFormat(DateUtil.DEFAULT_DATE_FORMAT);
		}else if(DateUtil.datePattern.matcher(value.trim()).matches()){
			sdf = new SimpleDateFormat(DateUtil.DATE_FORMAT);
		}else{
			//return -1;
			return Integer.parseInt(value);
		}
		
		if(type==0){
			return DateUtil.getStartTimestamp(value, sdf, null);
		}else if(type==1){
			return DateUtil.getDayEnd(sdf.parse(value)).getTime()/1000;
		}
		return -1;
	}
}
