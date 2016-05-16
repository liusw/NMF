
package com.boyaa.mf.util;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.boyaa.base.utils.JSONUtil;
import com.boyaa.mf.constants.Constants;

public class CommonUtil {
	static Logger errorLogger = Logger.getLogger("errorLogger");
	
	public static Map<String,Object> param2Map(String str){
		String[] params = str.split("&");
		Map<String,Object> map = new java.util.HashMap<String, Object>();
		for(int j=0;j<params.length;j++){
			String[] param = params[j].split("=");
			if(param==null || param.length!=2){
				continue;
			}
			map.put(param[0], param[1]);
		}
		return map;
	}
	
	public static String replatStr(String str,String tostr,String restr){
		String[] strArray = str.split(",");
		StringBuffer sb = new StringBuffer(); 
		for(int i=0;i<strArray.length;i++){
			if(strArray[i].equals(restr)){
				sb.append(tostr).append(",");
			}else{
				sb.append(strArray[i]).append(",");
			}
		}
		if(sb.length()>0){
			return sb.substring(0,sb.length()-1);
		}
		return null;
	}
	
	/**
	 *  获取新要的字段的标题
	 * @param newColumn 新要的字段
	 * @param column 原有的字段
	 * @param columnName 原有的字段所对应的标题
	 * @return
	 */
	public static String getTitle(String newColumn,String column,String columnName){
		if(StringUtils.isBlank(newColumn) || StringUtils.isBlank(column) || StringUtils.isBlank(columnName)) return null;
		
		String[] columns = column.split(",");
		String[] columnNames = columnName.split(",");
		String[] newColumns = newColumn.split(",");
		
		StringBuilder sb = new StringBuilder();
		boolean first=true;
		for(int i=0;i<newColumns.length;i++){
			for(int j=0;j<columns.length;j++){
				if(newColumns[i].trim().equals(columns[j].trim())){
					sb.append(first?"":",").append(columnNames[j].trim());
					first=false;
					break;
				}
			}
		}
		return sb.toString();
	}
	
	/**
	 * 把多个列名合并到已有的列名中,也就是合并字符串
	 * @param columns
	 * @param column
	 * @return
	 */
	public static String joinColumn(String columns,String ... column){
		if(StringUtils.isNotBlank(columns)){
			return joinColumn(columns.split(","),column);
		}else{
			return joinColumn(new String[]{},column);
		}
	}
	
	/**
	 * 把两个都是使用,号分隔的字符串去重合并
	 * @param columns
	 * @param newColumns
	 * @return
	 */
	public static String joinColumn(String columns,String newColumns){
		if(StringUtils.isBlank(columns) && StringUtils.isBlank(newColumns)){
			return null;
		}
		if(StringUtils.isBlank(columns) && StringUtils.isNotBlank(newColumns)){
			return joinColumn(new String[]{},newColumns.split(","));
		}
		if(StringUtils.isNotBlank(columns) && StringUtils.isBlank(newColumns)){
			return joinColumn(columns.split(","),null);
		}
		return joinColumn(columns.split(","),newColumns.split(","));
	}
	
	/**
	 * 把两个数组合并,按数组中的元素去重
	 * @param columns
	 * @param newColumns
	 * @return
	 */
	public static String joinColumn(String[] columns,String[] newColumns){
		List<String> retColumnList = null;
		if(ArrayUtils.isEmpty(columns) && ArrayUtils.isEmpty(newColumns)){
			return null;
		}
		if(ArrayUtils.isNotEmpty(columns) && ArrayUtils.isEmpty(newColumns)){
			retColumnList = Arrays.asList(columns);
		}
		if(ArrayUtils.isEmpty(columns) && ArrayUtils.isNotEmpty(newColumns)){
			retColumnList = Arrays.asList(newColumns);
		}
		if(ArrayUtils.isNotEmpty(columns) && ArrayUtils.isNotEmpty(newColumns)){
			List<String> columnList = Arrays.asList(columns);
			retColumnList = new ArrayList<String>(columnList);
			for(int i=0;i<newColumns.length;i++){
				if(!retColumnList.contains(newColumns[i])){
					retColumnList.add(newColumns[i]);
				}
			}
		}
		//输出
		StringBuffer sb = new StringBuffer();
		boolean first=true;
		for(int i = 0; i < retColumnList.size(); i++){
			sb.append(first?"":",").append(retColumnList.get(i));
			if(first) first = false;
		}
		return sb.toString();
	}
	
	public static boolean isNumberType(String typeValue){
		if("int".equalsIgnoreCase(typeValue) || "long".equalsIgnoreCase(typeValue) || "float".equalsIgnoreCase(typeValue)|| "double".equalsIgnoreCase(typeValue)){
			return true;
		}
		return false;
	}
	
	public static String op(String v){
		if(v.equals("0")){
			return "=";
		}else if(v.equals("1")){
			return "<";
		}else if(v.equals("2")){
			return "<=";
		}else if(v.equals("3")){
			return "!=";
		}else if(v.equals("4")){
			return ">";
		}else if(v.equals("5")){
			return ">=";
		}
		return "=";
	}
	
	public static int opStringToInt(String v){
		if(v.equals("=")){
			return 0;
		}else if(v.equals("<")){
			return 1;
		}else if(v.equals("<=")){
			return 2;
		}else if(v.equals("!=")){
			return 3;
		}else if(v.equals(">")){
			return 4;
		}else if(v.equals(">=")){
			return 5;
		}
		return 0;
	}
	
	public static String opValue(String op,String typeValue,String v){
		if(op.equals("7")){
			return "like '%"+v+"%'";
		}else if(op.equals("8")){
			String[] vs = v.split("___");
			if(vs==null || vs.length!=2){
				return "";
			}
			return "between "+vs[0]+" and "+vs[1];
		}else{
//			v=StringAddDoubleQuotes(typeValue,v);
			
			if (op.equals("0")) {
				return "='" + v + "'";
			} else if (op.equals("1")) {
				return "<" + v;
			} else if (op.equals("2")) {
				return "<=" + v;
			} else if (op.equals("3")) {
				return "!='" + v + "'";
			} else if (op.equals("4")) {
				return ">" + v;
			} else if (op.equals("5")) {
				return ">=" + v;
			} else if (op.equals("6")) {
				return "in(" + inOpValue(v) + ")";
			} else if (op.equals("9")) {
				return "not in(" + inOpValue(v) + ")";
			}
			return "='" + v +"'";
		}
	}
	
	/**
	 * 如果是字符串类型，加上双引号
	 * @return
	 */
	public static String inOpValue(String v){
		if(StringUtils.isBlank(v)){
			return "";
		}
		StringBuffer sb = new StringBuffer();
		
		String[] vs = v.split(";");
		for(int i=0;i<vs.length;i++){
			sb.append("\"").append(vs[i]).append("\"");
			if(i!=vs.length-1){
				sb.append(",");
			}
		}
		return sb.toString();
	}
	
	public static String StringAddDoubleQuotes(String typeValue,String v){
		if(StringUtils.isBlank(v)){
			return "";
		}
		StringBuffer sb = new StringBuffer();
		
		String[] vs = v.split(";");
		for(int i=0;i<vs.length;i++){
			if(!isNumberType(typeValue)){
				sb.append("\"").append(vs[i]).append("\"");
			}else{
				sb.append(vs[i]);
			}
			if(i!=vs.length-1){
				sb.append(",");
			}
		}
		return sb.toString();
	}
	
	/**
	 * 判断字符串是否为数字
	 * @param numStr
	 * @return
	 */
	public static boolean isNum(String numStr){
		if(StringUtils.isNotBlank(numStr)){
			if(StringUtils.isNumeric(numStr)){
				return true;
			}else{
				 try {   
				    Double.parseDouble(numStr);
				    return true;
				 } catch (NumberFormatException e) {
				    return false;
				 }
			}
		}else{
			return false;
		}
	}
	
	/**
	 * 重写com.alibaba.fastjson.JSONArray.toJSONString,把为null的设置为""
	 * @param object
	 * @param features
	 * @return
	 */
	public static String toJSONString(Object object,SerializerFeature ...features) {
		SerializeWriter out = new SerializeWriter();
		String s;
		JSONSerializer serializer = new JSONSerializer(out);
		SerializerFeature arr$[] = features;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++) {
			SerializerFeature feature = arr$[i$];
			serializer.config(feature, true);
		}

		serializer.getValueFilters().add(new ValueFilter() {
			public Object process(Object obj, String s, Object value) {
				if(null!=value) {
					if(value instanceof java.util.Date) {
						return String.format("%1$tF %1tT", value);
					}
					return value;
				}else {
					return "";
				}
			}
		});
		serializer.write(object);
		s = out.toString();
		out.close();
		return s;
	}
	
	/**
	 * 获取一个类指定字段的get值
	 * @param obj 类对象
	 * @param fieldName 字段名
	 * @return
	 */
	public static Object getFieldValue(Object obj,String fieldName){
		String firstLetter = fieldName.substring(0,1).toUpperCase(); //获得字段第一个字母大写 
		String getMethodName = "get"+firstLetter+fieldName.substring(1); //转换成字段的get方法 
		
		Object value = null;
		try {
			Method getMethod = obj.getClass().getMethod(getMethodName, new Class[] {}); 
			value = getMethod.invoke(obj, new Object[] {});
		} catch (Exception e) {
			errorLogger.error(e.getMessage());
		}
		return value;
	}
	
	public static String fileSizeFormat(Long fileSize) {
		if(fileSize==null || fileSize==0){
			return "";
		}
		
		String value = "";
		float size = 0f;
		String unit = "";// 单位
		if (fileSize < 1024) {
			size = (float) (fileSize);
			unit = " 字节";
		} else {
			float k = (float) (fileSize / 1024);
			if (k < 1024) {
				size = (float) (k);
				unit = " KB";
			} else {
				float m = (float) (k / 1024);
				if (m < 1024) {
					size = (float) (m);
					unit = " MB";
				} else {
					size = (float) (m / 1024);
					unit = " GB";
				}
			}
		}
		DecimalFormat formater = new DecimalFormat("#0.##");
		value = formater.format(size).toString();
		if ((value.indexOf(".00")) != -1) {
			value = value.substring(0, value.indexOf(".00"));
		}
		value += unit;
		
		return value;
	}
	
	public static String rateFormat(long count1,long count2) {
		if(count2==0){
			return count1==0?"-":"100%";
		}
		float size = (float)count1/count2;
		DecimalFormat formater = new DecimalFormat("#0.##");
		String value = formater.format(size*100).toString();
		if ((value.indexOf(".00")) != -1) {
			value = value.substring(0, value.indexOf(".00"));
		}
		return value+"%";
	}
	
	public static String listToString(List<String> list){
		if(list==null || list.size()<=0){
			return null;
		}
		StringBuffer columns = new StringBuffer();
		for (int i = 0; i < list.size(); i++) {
			columns.append(i == 0 ? "" : ",").append(list.get(i));
		}
		return columns.toString();
	}
	
	public static String replaceIgnoreCase(String content,String word){
		String wordReg = "(?i)"+word+" ";
		Matcher matcher = Pattern.compile(wordReg).matcher(content);
		StringBuffer sb = new StringBuffer();  
		while(matcher.find()){
			 matcher.appendReplacement(sb,word+" ");  
		}
		matcher.appendTail(sb); 
		return sb.toString();  
	}
	
	/**
	 * 获取某个表的审批信息
	 * @param type
	 * @param tableName
	 * @return
	 */
	public static JSONObject getAuditInfo(String type,String tableName){
		if (StringUtils.isNotBlank(Constants.AUDIT_COLUMN)) {
			try {
				JSONObject json = JSONUtil.parseObject(new String(Constants.AUDIT_COLUMN.getBytes("ISO-8859-1"), "UTF-8"));
				
				if(json.containsKey(type)){
					JSONObject typeJson = json.getJSONObject(type);
					
					if(typeJson.containsKey(tableName)){
						return typeJson.getJSONObject(tableName);
					}
				}
			} catch (JSONException e) {
				errorLogger.error(e.getMessage());
			} catch (UnsupportedEncodingException e) {
				errorLogger.error(e.getMessage());
			}
		}
		return null;
	}
	
	/**
	 * 根据正则表达式获取想要的内容
	 * @param regex
	 * @param content
	 * @param num
	 * @return
	 */
	public static String getByRegex(String regex, String content, int num) {
		String matchString = null;
		if (content != null) {
			Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
			Matcher matcher = pattern.matcher(content);
			if (matcher.find()) {
				if (num == 0) {
					matchString = matcher.group();
				} else {
					matchString = matcher.group(num);
				}
			}
		}
		return matchString;
	}
	
}
