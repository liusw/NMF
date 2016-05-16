
package com.boyaa.mf.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.boyaa.base.utils.AppContext;
import com.boyaa.base.utils.CsvUtil;
import com.boyaa.base.utils.DateUtil;
import com.boyaa.base.utils.FileUtil;
import com.boyaa.base.utils.JSONUtil;
import com.boyaa.servlet.ResultState;

/**
 * 
 * @类名 : HiveUtils.java
 * @作者 : MarsHuang
 * @部门 : 德州扑克业务中心-开拓者工作室-公共组
 * @公司 : 博雅互动
 * @日期 : 2014-9-9  上午10:37:18
 * @描述 : hive相关的工具类
 */
public class HiveUtils {
	static Logger logger = Logger.getLogger(HiveUtils.class);
	static Logger errorLogger = Logger.getLogger("errorLogger");
	
	/**
	 * 由于有些文件有写入UTF-8 BOM头，所以在load文件到hive的时候，要做个判断，如果带有，则重新写个临时文件代替
	 * @param path
	 * @return
	 */
	public static String getLoadPath(String path){
		InputStream in = null;
		InputStream newIn = null;
		try {
			in = new FileInputStream(new File(path));
			boolean result = CsvUtil.containsBOM(in);
			if(result){
				int pathIndex = path.lastIndexOf(File.separator);
				int nameIndex = path.lastIndexOf(".");
				int separatorLength = File.separator.length();
				
				String _name = path.substring(0,nameIndex>(pathIndex+separatorLength)?nameIndex:path.length());
				_name += "_"+new Date().getTime();
				
				if(in!=null) in.close();
				in = new FileInputStream(new File(path));
				newIn = CsvUtil.removeUTF8BOM(in);
				FileUtil.copyFile(newIn,_name);
				return _name;
			}
		} catch (FileNotFoundException e) {
			errorLogger.error(e.getMessage());
		} catch (IOException e) {
			errorLogger.error(e.getMessage());
		} finally{
			if(in!=null)
				try {
					in.close();
				} catch (IOException e) {
					errorLogger.error(e.getMessage());
				}
			if(newIn!=null)
				try {
					newIn.close();
				} catch (IOException e) {
					errorLogger.error(e.getMessage());
				}
		}
		return path;
	}
	
	private static String getOpFunction(String func,String str,String caseWhen,String tableName){
		str = str.replaceAll("#","");
		StringBuilder sb = new StringBuilder();
		if(StringUtils.isNotBlank(caseWhen)){
			String column = caseWhen.substring(0,caseWhen.indexOf("#"));
			String value = caseWhen.substring(caseWhen.lastIndexOf("#")+1);
			String opvalue = caseWhen.substring(caseWhen.indexOf("#")+1,caseWhen.lastIndexOf("#"));
			String op =CommonUtil.op(opvalue);
			
			if(func.equals("min")){
				sb.append("case when ").append(column).append(op).append(value).append(" then ").append(str).append(" end ");
			}else if(func.equals("max")){
				sb.append("case when ").append(column).append(op).append(value).append(" then ").append(str).append(" end ");
			}else if(func.equals("s")){
				sb.append("case when ").append(column).append(op).append(value).append(" then ").append(str).append(" end ");
			}else if(func.equals("c")){
				sb.append("case when ").append(column).append(op).append(value).append(" then ").append(1).append(" end ");
			}else{
				sb.append(str);
			}
		}else{
			sb.append(str);
		}
		
		if(func.equals("min")){
			return "round(min("+sb.toString()+"),2) ";
		}else if(func.equals("max")){
			return "round(max("+sb.toString()+"),2)";
		}else if(func.equals("s")){
			if(StringUtils.isNotBlank(tableName) && tableName.startsWith("user_order") && sb.toString().contains("prate") || sb.toString().contains("pamount")){
				return "round(sum(cast(("+sb.toString()+") as double)),2)";
			}
			return "round(sum(cast(("+sb.toString()+") as bigint)),2)";
		}else if(func.equals("c")){
			return "count("+sb.toString()+")";
		}else if(func.equals("dc")){
			return "count(distinct "+sb.toString()+")";
		}else{
			return sb.toString();
		}
	}
	
	/**
	 * 获取要查询的列信息
	 * @param columns 表字段
	 * @param columnsName 表列名
	 * @param jsonParam 参数
	 * @return new String[]{要查询的字段,包括函数等其他字段,最终输出所对应的列名}
	 */
	public static Map<String,Object> getQueryColumn(String columns,String columnsName,JSONObject jsonParam) {
		
		Map<String,Object> map = new HashMap<String, Object>();
//		if(!jsonParam.has("tableColumn") || !jsonParam.has("tableTitle")){
//			map = new HashMap<String, Object>();
//			map.put("queryColumn", columns);
//			return map;
//		}
		
		String group_column = "",group_title = "",group_asName = "",common_column = "",common_title = "",common_asName = "";
		try {
			List<String> retColumnList = null;
			List<String> queryColumnList = null;//所有查询到的列
			String queryColumn = columns;
			Map<String,String> paramMap = null;
			
			if(StringUtils.isNotBlank(columns)){
				List<String> columnList = Arrays.asList(columns.split(","));
				retColumnList = new ArrayList<String>(columnList);
			}
			
			String tableColumn = (String)JsonUtils.getValue(jsonParam, "tableColumn","");
			String tableTitle = (String)JsonUtils.getValue(jsonParam, "tableTitle","");
			
			if(StringUtils.isNotBlank(tableColumn) && StringUtils.isNotBlank(tableTitle)){
				String[] tempColums = tableColumn.split(",");
				String[] title_asNames = tableTitle.split(",");
				
				String regEx = "#[\\*,+,\\-,/]#";
				Pattern pattern = Pattern.compile(regEx,Pattern.CASE_INSENSITIVE); 
				Matcher matcher = null;
				
				map = new HashMap<String, Object>();
				
				boolean first=true;
				for(int i=0;i<tempColums.length;i++){
					String[] title_asName = title_asNames[i].split("\\|");
					String tempColumNames = title_asName[0].split(",")[0];
					String tempAsNames = title_asName[1].split(",")[0];
					
					String[] tempArray = tempColums[i].split("___");
					String type = tempArray[0];
					String func = tempArray[1];
					String columnLink = tempArray[2];
					String caseWhen =null;
					if(tempArray.length>=4){
						caseWhen = tempArray[3];
					}
					
					matcher = pattern.matcher(columnLink);
					StringBuilder tmpSb = new StringBuilder();
					while(matcher.find()){
						String tmp = matcher.group().trim();
						String _c = columnLink.substring(0,columnLink.indexOf(tmp));
						//tmpSb.append("%asName%.").append(_c).append(tmp);
						tmpSb.append(_c).append(tmp);
						columnLink=columnLink.substring(columnLink.indexOf(tmp)+3);
						
						if(!retColumnList.contains(_c) && type.equals("g")){
							retColumnList.add(_c);
						}
					}
					tmpSb.append(columnLink);
					if(!"*".equals(columnLink) && !retColumnList.contains(columnLink)){
						if(type.equals("g")){
							retColumnList.add(columnLink);
						}
					}
					
					//列函数  列别名  标题
					String tmpColumn = getOpFunction(func,tmpSb.toString(),caseWhen,jsonParam.getString("tableName"));
					String tmpAsName = tempAsNames;
					String tmpColumnName=tempColumNames;
					
					//获取第一个字符
					if(type.equals("g")){
						group_column += ((first?"":",")+tmpColumn+" as " + tmpAsName);
						group_asName += ((first?"":",")+tmpAsName);
						group_title += ((first?"":",")+tmpColumnName);
					}else{
						common_column += ((first?"":",")+tmpColumn+" as " + tmpAsName);
						common_asName += ((first?"":",")+tmpAsName);
						common_title += ((first?"":",")+tmpColumnName);
					}
					if(first) first = false;
				}
				
				if(StringUtils.isNotBlank(group_column)){
					paramMap = new HashMap<String, String>();
					paramMap.put("column", group_column);
					paramMap.put("title", group_title);
					paramMap.put("asName", group_asName);
					map.put("group", paramMap);
				}
				if(StringUtils.isNotBlank(common_column)){
					paramMap = new HashMap<String, String>();
					paramMap.put("column", common_column);
					paramMap.put("title", common_title);
					paramMap.put("asName", common_asName);
					map.put("common", paramMap);
				}
			}
			
			//格式化处理
			String format = (String) JsonUtils.getValue(jsonParam,"format", "");
			if(StringUtils.isNotBlank(format)){
				String format_column = "",format_title = "",format_asName = "";
				boolean first=true;
				
				if(retColumnList==null){
					retColumnList = new ArrayList<String>();
				}
				queryColumnList = new ArrayList<String>(retColumnList);
				String[] formats = format.split(",");
				for(int i=0;i<formats.length;i++){
					String _format = formats[i];
					
					String[] conditions = _format.split("#");
					
					String formatColumnId = conditions[0];
					String fcolumn = HiveUtils.columsSpecialSymbols(conditions[2]);
					
					if(conditions[1].equals("format")){
						format_column += ((first?"":",")+"from_unixtime(cast("+fcolumn+" as bigint),'"+conditions[3]+"')"+" as "+formatColumnId);
						format_asName += ((first?"":",")+formatColumnId);
						format_title += ((first?"":",")+conditions[2]+"日期格式化");
						
						if(!retColumnList.contains(fcolumn)){
							queryColumnList.add(fcolumn);
							if(Integer.parseInt(conditions[4])==1){
								retColumnList.add(fcolumn);
							}
						}
					}else if(conditions[1].equals("ipformat")){
						format_column += ((first?"":",")+"geoip("+fcolumn+")"+" as "+formatColumnId);
						format_asName += ((first?"":",")+formatColumnId);
						format_title += ((first?"":",")+conditions[2]+"(ip转国家)");
						
						if(!retColumnList.contains(fcolumn)){
							queryColumnList.add(fcolumn);
							if(Integer.parseInt(conditions[3])==1){
								retColumnList.add(fcolumn);
							}
						}
					}
					first = false;
				}
				if(StringUtils.isNotBlank(format_column)){
					paramMap = new HashMap<String, String>();
					paramMap.put("column", format_column);
					paramMap.put("title", format_title);
					paramMap.put("asName", format_asName);
					map.put("format", paramMap);
				}
			}else{
				queryColumnList = new ArrayList<String>(retColumnList);
			}
			StringBuffer sb = new StringBuffer();
			boolean first=true;
			for(int i = 0; i < retColumnList.size(); i++){
				sb.append(first?"":",").append(retColumnList.get(i));
				if(first) first = false;
			}
			queryColumn = sb.toString();
			map.put("queryColumn", queryColumn);
			
			sb = new StringBuffer();
			first=true;
			for(int i = 0; i < queryColumnList.size(); i++){
				sb.append(first?"":",").append(queryColumnList.get(i));
				if(first) first = false;
			}
			map.put("queryTableColumn", sb.toString());
		} catch (JSONException e) {
			errorLogger.error(e.getMessage());
		}
		
		return map;
	}
	
	/**
	 * 获取_tm的日期格式:yyyyMMdd
	 * @param tableName
	 * @return
	 */
	public static String getTmDate(String tableName){
//		if(tableName.equals("gamecoins_stream")){
//			return "from_unixtime(cast(floor(cast(`_tm` as double)) as bigint), \"yyyyMMdd\")";
//		}else if(tableName.equals("user_uid")){
//			return "`_tm`";
//		}else{
//			return "from_unixtime(cast(`_tm` as bigint), \"yyyyMMdd\")";
//		}
		return "tm";
	}
	
	public static String getTmDate(String tableName,boolean isPartitionTm){
		if(isPartitionTm) return " tm ";
		
		if(tableName.startsWith("gamecoins_stream")){
			return " from_unixtime(cast(floor(cast(`_tm` as double)) as bigint), \"yyyyMMdd\") ";
		}else{
			return " from_unixtime(cast(`_tm` as bigint), \"yyyyMMdd\") ";
		}
	}
	
	/**
	 * 生成where语句中时间范围语句数据
	 * @param startTime
	 * @param endTime
	 * @param tableName
	 * @return
	 * @throws ParseException 
	 */
	public static String getWhereBetweenTm(String startTime,String endTime,String tableName) throws ParseException{
		StringBuilder wheretm = new StringBuilder();
		if (StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)) {
			wheretm.append(" and tm between "+ DateUtil.convertPattern(startTime, "yyyy-MM-dd", "yyyyMMdd") + " and " + DateUtil.convertPattern(endTime, "yyyy-MM-dd", "yyyyMMdd"));
		} else if (StringUtils.isNotBlank(startTime)&& StringUtils.isBlank(endTime)) {
			wheretm.append(" and tm >= " + DateUtil.convertPattern(startTime, "yyyy-MM-dd", "yyyyMMdd"));
		} else if (StringUtils.isBlank(startTime)&& StringUtils.isNotBlank(endTime)) {
			wheretm.append(" and tm <= "+ DateUtil.convertPattern(endTime, "yyyy-MM-dd", "yyyyMMdd"));
		}
		return wheretm.toString();
	}
	
	public static String getWhereBetweenTm(String startTime,String endTime,String tableName,boolean isPartitionTm) throws ParseException{
		StringBuilder wheretm = new StringBuilder();
		
		wheretm.append(" and ").append(getTmDate(tableName,isPartitionTm)).append(" ");
		
		if (StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)) {
			wheretm.append(" between "+ DateUtil.convertPattern(startTime, "yyyy-MM-dd", "yyyyMMdd") + " and " + DateUtil.convertPattern(endTime, "yyyy-MM-dd", "yyyyMMdd"));
		} else if (StringUtils.isNotBlank(startTime)&& StringUtils.isBlank(endTime)) {
			wheretm.append(" >= " + DateUtil.convertPattern(startTime, "yyyy-MM-dd", "yyyyMMdd"));
		} else if (StringUtils.isBlank(startTime)&& StringUtils.isNotBlank(endTime)) {
			wheretm.append(" <= "+ DateUtil.convertPattern(endTime, "yyyy-MM-dd", "yyyyMMdd"));
		}
		return wheretm.toString();
	}
	
	/**
	 * 18_06EFCF2DE98EFE7F1D3D849C86DB9F99,39_A830E320188E2B1DBC3837F975557355
	 * @param tableName
	 * @param sid_bpid
	 * @return
	 */
	public static String getWhereSidOrBpid(String tableName,String sid_bpid){
		if(StringUtils.isNotBlank(sid_bpid)){
			StringBuilder sid = new StringBuilder();
			StringBuilder bpid = new StringBuilder();
			
			String[] sidBpids = sid_bpid.split(",");
			boolean first = true;
			for(int i=0;i<sidBpids.length;i++){
				sid.append(first?"":",").append(sidBpids[i].substring(0,sidBpids[i].indexOf("_")));
				bpid.append(first?"":",").append("\""+sidBpids[i].substring(sidBpids[i].indexOf("_")+1)+"\"");
				first=false;
			}
			
			if(tableName.equals("user_gambling") || tableName.equals("gambling_detail") ||tableName.equals("gamecoins_stream")){
				return " and sid in("+sid.toString()+") ";
			}else{
				return " and `_bpid` in ("+bpid.toString()+") " ;
			}
		}
		return "";
	}
	
	/**
	 * 给每个列加上表的别名
	 * @param asName
	 * @param column
	 * @return
	 */
	public static String asName2Column(String asName,String columns){
		if(StringUtils.isBlank(columns)) return null;
		
		StringBuilder sb = new StringBuilder();
		String[] links = columsSpecialSymbols(columns).split(",");
		for(int i=0;i<links.length;i++){
			sb.append(i!=0?",":"").append(asName+".").append(links[i]);
		}
		return sb.toString();
	}
	
	/**
	 * eg:"_uid#0#_uid,_bpid#0#_tm"
	 * @param t1
	 * @param t2
	 * @param originalOn on的关联条件
	 * @return
	 */
	public static String onCondition(String t1,String t2,String linkParam){
		if(StringUtils.isBlank(linkParam)) return null;
		
		StringBuilder sb = new StringBuilder();
		
		String[] links = linkParam.split(",");
		for(int i=0;i<links.length;i++){
			String[] link = links[i].split("#");
			if(link.length!=3){
				continue;
			}
			sb.append(StringUtils.isNotBlank(t1)?t1+".":"").append(link[0])
			  .append(CommonUtil.op(link[1]))
			  .append(StringUtils.isNotBlank(t2)?t2+".":"").append(link[2])
			  .append(" and ");
		}
		if(sb.length()>0){
			String newOn = sb.substring(0,sb.length()-5);
			return whereSpecialSymbols(newOn);
		}
		return "";
	}
	
	/**
	 * 根据字段名获取创表语句的列,现在所有字段都是string类型
	 * @param columns
	 * @return
	 */
	public static String getHiveCreateTableColumn(String columns){
		if(StringUtils.isBlank(columns)) return null;
		
		String[] cs = columns.split(",");
		StringBuffer csb = new StringBuffer(); 
		for(int i=0;i<cs.length;i++){
			if(cs[i].startsWith("_") && !cs[i].contains("`") ){
				csb.append(addSpecial(cs[i])).append(" string").append(",");
			}else{
				csb.append(cs[i]).append(" string").append(",");
			}
		}
		if(csb.length()>0){
			columns = (String) csb.substring(0,csb.length()-1);
		}
		return columns;
	}
	
	/**
	 * 根据查询语句获取其中的表名
	 */
	public static List<String> getTables(String sql){
		List<String>  list = new ArrayList<String>();
		String regEx = " from [a-z_A-Z0-9_]+ ";
		Pattern pattern = Pattern.compile(regEx,Pattern.CASE_INSENSITIVE); 
		Matcher matcher = pattern.matcher(sql);
		while(matcher.find()){
			String from_table = matcher.group().trim();
			from_table=from_table.substring(from_table.indexOf("from ")+5).trim();
			list.add(from_table);
		}
		return list;
	}
	
	/**
	 * 去除语句中的`号
	 * @param columns
	 * @return
	 */
	public static String removeSpecialSymbols(String columns){
		if(StringUtils.isBlank(columns)) return null;
		return columns.replaceAll("`", "");
	}
	
	/**
	 * 以,号分开的字符串添加`号
	 * @param columns
	 * @return
	 */
	public static String columsSpecialSymbols(String columns){
		if(StringUtils.isBlank(columns)) return null;
		
		String[] cs = columns.split(",");
		StringBuffer csb = new StringBuffer(); 
		for(int i=0;i<cs.length;i++){
			if(cs[i].trim().startsWith("_") && !cs[i].trim().contains("`") ){
				csb.append(addSpecial(cs[i].trim())).append(",");
			}else{
				csb.append(cs[i].trim()).append(",");
			}
		}
		if(csb.length()>0){
			columns = (String) csb.subSequence(0,csb.length()-1);
		}
		return columns;
	}
	
	/**
	 * where语句中添加`号
	 * @param sql
	 * @return
	 */
	public static String whereSpecialSymbols(String sql){
		String[] strArray = sql.split(" ");
		StringBuilder sb = new StringBuilder();
		for(String str:strArray){
			str =str.trim();
			
			if(str.startsWith("_") || str.contains("._")||str.contains("=_")){
				if(str.contains("=")){
					String[] eqs = str.split("=");
					for(String eq : eqs){
						if(eq.contains("_")&&!eq.contains("`")){
							String neq = addSpecial(eq);
							str=str.replace(eq, neq);
						}
					}
				}else if(!str.contains("`")){
					str = addSpecial(str);
				}
			}
			sb.append(str).append(" ");
		}
		return sb.toString();
	}
	
	private static String addSpecial(String str){
		return str.contains(".")?str.substring(0,str.indexOf(".")+1)+"`"+str.substring(str.indexOf(".")+1)+"`":"`"+str+"`";
	}
	
	public static boolean isRightOf_(String hql) {
		int index = hql.indexOf("_");
		boolean isTrue=true;
		
		while(index!=-1){
			String beforeStr = hql.substring(index-1,index);
			String afterStr = " ";
			if(hql.length()-1>index){
				afterStr = hql.substring(index+1,index+2);
			}
			if(!beforeStr.equals("\\") && (!beforeStr.matches("[\"`0-9A-Za-z_#-]+") || !afterStr.matches("[\"`0-9A-Za-z_#-]+"))){
				isTrue = false;
				break;
			}
			
			while(afterStr.equals("_")){
				index++;
				if(hql.length()-1>index){
					afterStr = hql.substring(index+1,index+1==hql.length()-1?index+1:index+2);
				}
			}
			hql= hql.substring(index+1);
			index = hql.indexOf("_");
		}
		return isTrue;
	}
	
	/**
	 * 对自定义的语句进行较验,不对分区进行较验
	 * @param hql
	 * @return
	 */
	public static ResultState validateHQL(String hql){
		if (StringUtils.isEmpty(hql)) {
			return new ResultState(ResultState.FAILURE, "查询语句不能为空");
		} 
		
		StringBuffer errorMsg = new StringBuffer();
		hql = hql.trim().toLowerCase();
		if(!hql.startsWith("select ")){
			errorMsg.append("查询语句必须以select开头;");
		}
		
		if(hql.endsWith("from")){
			errorMsg.append("查询语句from后面必须带有表名;");
		}else	if(!hql.contains("from ")){
			errorMsg.append("查询语句必须带有from;");
		}
		
		List<String> notAllowedWords = new ArrayList<String>();
		notAllowedWords.add("drop");
		notAllowedWords.add("update");
		notAllowedWords.add("insert");
		notAllowedWords.add("create");
		notAllowedWords.add("delete");
		notAllowedWords.add("alter");
		notAllowedWords.add("truncate");
		notAllowedWords.add("grant");
		notAllowedWords.add("revoke");
		
		List<String> list = Arrays.asList(hql.split(" "));
		StringBuffer sb = new StringBuffer();
		for(String notAllowedWord:notAllowedWords){
			if(list.contains(notAllowedWord)){
				sb.append("[").append(notAllowedWord).append("]");
			}
		}
		if(sb.length()>0){
			errorMsg.append("语句带有敏感关键字："+sb.toString()+";");
		}
		
		boolean isRightOf_ = HiveUtils.isRightOf_(hql);
		if(!isRightOf_){
			errorMsg.append("此语句带有不正确的_号,例如_uid要写成`_uid`");
		}
		
		if(StringUtils.isNotBlank(errorMsg.toString())){
			return new ResultState(ResultState.FAILURE, errorMsg.toString());
		}
		return new ResultState(ResultState.SUCCESS);
	}
	
	/**
	 * 对语句中的是否带有分区进行较验（暂时只对tm和plat进行较验）
	 * @param hql
	 * @return
	 */
	public static ResultState validatePartition(String hql){
		if (StringUtils.isEmpty(hql)) {
			return new ResultState(ResultState.FAILURE, "查询语句为空，需要审批");
		}
		
		StringBuffer errorMsg = new StringBuffer();
		if (!hql.contains("tm")) {
			errorMsg.append("查询句话没有时间分区［tm］");
		}
		
		try {
			List<String> tables = HiveUtils.getTables(hql);
			JSONObject partitionjson = null;
			for (String str : tables) {
				String partition = AppContext.partition.get(str);
				if (StringUtils.isNotBlank(partition)) {
					partitionjson = JSONUtil.parseObject(partition);
					if (partitionjson.containsKey("_plat")) {
						//gambling_detail={_plat:"index:3#rename:svid",_tm:"index:1#size:8#rename:tm"}
						String plat = partitionjson.getString("_plat");
						plat = plat.substring(plat.indexOf("rename:")+7);
						if (!hql.contains(" "+plat) && !hql.contains("."+plat)) {
							errorMsg.append(errorMsg.length()>0?",":"查询句话没有").append("平台分区［"+plat+"］");
						}
						break;
					}
				}
			}
		} catch (JSONException e) {
			errorLogger.error(e.getMessage());
		}
		if(StringUtils.isNotBlank(errorMsg.toString())){
			return new ResultState(ResultState.FAILURE, errorMsg.toString()+"，需要审批！");
		}
		return new ResultState(ResultState.SUCCESS);
	}
}
