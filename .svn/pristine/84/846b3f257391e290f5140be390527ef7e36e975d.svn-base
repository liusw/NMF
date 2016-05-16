package com.boyaa.mf.service.task;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.boyaa.base.utils.AppContext;
import com.boyaa.base.utils.JSONUtil;
import com.boyaa.mf.constants.Constants;
import com.boyaa.mf.entity.task.ProcessTypeEnum;
import com.boyaa.mf.service.config.HiveMetaService;
import com.boyaa.mf.util.CommonUtil;
import com.boyaa.mf.util.HiveUtils;
import com.boyaa.mf.util.JsonUtils;
import com.boyaa.mf.util.ServletUtil;
import com.boyaa.servlet.ResultState;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * 添加关联查询的业务类，主要是拼相关的参数
 * @类名 : AddLinkTaskService.java
 * @作者 : MarsHuang
 * @部门 : 德州扑克业务中心-开拓者工作室-公共组
 * @公司 : 博雅互动
 * @日期 : 2014-11-28  上午9:49:33
 */
@SuppressWarnings("all")
@Service
public class AddLinkTaskService {
	static Logger taskLogger = Logger.getLogger("taskLogger");
	static Logger errorLogger = Logger.getLogger("errorLogger");
	static Logger fatalLogger = Logger.getLogger("fatalLogger");
	
	@Autowired 
	private TaskService taskService;
	@Autowired
	private HiveMetaService hiveMetaService;
	
	private String errMsg;
	
	public ResultState param2TaskJson(HttpServletRequest request){
		
		String taskInfo = request.getParameter("taskInfo");
		if(StringUtils.isBlank(taskInfo)){
			return new ResultState(ResultState.FAILURE,"添加任务失败");
		}
		
		//获取用户的基本信息
		String userId = (String) request.getSession().getAttribute("code");
		String userName = (String) request.getSession().getAttribute("cname");
		
		try{
			JSONObject paramJson = JSONObject.parseObject(taskInfo);
			JSONArray paramJsonArray = (JSONArray) paramJson.get("processList");
			String outputColumn = paramJson.getString("outputColumn");
			
			JSONObject jsonObject = JSONUtil.getJSONObject();
			jsonObject.put("taskName", paramJson.getString("taskName"));
			jsonObject.put("userId", userId);
			jsonObject.put("userName", userName);
			jsonObject.put("receiveEmail", paramJson.getString("email"));
			jsonObject.put("outputType",paramJson.get("outputType"));
			jsonObject.put("outputColumn",outputColumn);
			jsonObject.put("outputLink", paramJson.get("outputLink"));
			
			boolean haveErrorProcess = false;
			if(paramJsonArray!=null && paramJsonArray.size()>0){
				JSONArray jsonArray = JSONUtil.getJSONArray();
				JSONObject processJsonObject = null;
				for(int i=0;i<paramJsonArray.size();i++){
					JSONObject paramProcessJson = paramJsonArray.getJSONObject(i);
					
					int processType = paramProcessJson.getIntValue("processType");
					if(processType==1){//hive
						processJsonObject = hiveProcessService(paramProcessJson);
					}else if(processType==2){//hbase,user_ucuser
						if(StringUtils.isNotBlank(outputColumn)){
							String[] links = outputColumn.split("\\|");
							StringBuffer sb = new StringBuffer();
							int linksLength = links.length;
							for(int j=0;j<linksLength;j++){
								boolean ifFullEmail = false;
								
								String[] link = links[j].split(":");
								String processId = link[0];
								String column = link[1];
								if(link[0].equals(paramProcessJson.getString("processId"))){
									String columns = paramProcessJson.getString("columns");
									if(columns.contains("fullEmail")){
										ifFullEmail = true;
										if(!column.contains("email")){
											column = column.replaceAll("fullEmail","email");
										}else{
											column = column.replaceAll(",*fullEmail","");
										}
									}
								}
								if(ifFullEmail){
									sb.append(processId+":"+column);
								}else{
									sb.append(links[j]);
								}
								sb.append(j!=linksLength-1?"|":"");
							}
							jsonObject.put("outputColumn", sb.toString());
						}
						//处理IP格式化
						processJsonObject = hbaseProcessSerivce(paramProcessJson);
					}else if(processType==3){//upload file
						processJsonObject = uploadProcessSerivce(paramProcessJson);
					}else if(processType==4){//hql
						processJsonObject = hqlProcessSerivce(paramProcessJson);
					}else if(processType==5 || processType==6){
						processJsonObject = ortherProcessSerivce(paramProcessJson);
					}else if(processType==10){
						processJsonObject = HasePutProcessSerivce(paramProcessJson);
					}
					
					if(processJsonObject==null){
						haveErrorProcess=true;
						break;
					}
					
					jsonArray.add(processJsonObject);
				}
				
				if(haveErrorProcess){
					return new ResultState(ResultState.FAILURE,errMsg);
				}
				
				jsonObject.put("process", jsonArray);
			}
			
			return taskService.addTask(jsonObject);
		} catch (JSONException e) {
			errorLogger.error(e.getMessage());
			return new ResultState(ResultState.FAILURE,"添加任务失败");
		} catch (ParseException e) {
			errorLogger.error(e.getMessage());
			return new ResultState(ResultState.FAILURE,"添加任务失败");
		} catch (IOException e) {
			errorLogger.error(e.getMessage());
			return new ResultState(ResultState.FAILURE,"添加任务失败");
		}
	}
	
	public JSONObject hiveProcessService(JSONObject jsonParam) throws JSONException, ParseException{
		String processId=jsonParam.getString("processId");
		String tableName=jsonParam.getString("tableName");
		String columns = jsonParam.getString("columns");
		String columnNames = jsonParam.getString("columnNames");
		String plat=jsonParam.getString("plat");
		String svid = jsonParam.getString("svid");
		//String sid_bpid = jsonParam.getString("sid");
		String sid_bpid = null;
		String startTime = jsonParam.getString("startTime");
		String endTime = jsonParam.getString("endTime");
		String filter = jsonParam.getString("filter");
		String dependOn = jsonParam.getString("dependOn");
		String linkParam = jsonParam.getString("linkParam");
		String distinctType = jsonParam.getString("distinctType");
		
		if(jsonParam.containsKey("sid")){
			sid_bpid = jsonParam.getString("sid");
		}
		
		JSONObject jsonObject = JSONUtil.getJSONObject();
		jsonObject.put("tmpId",processId);
		jsonObject.put("type", ProcessTypeEnum.HIVE_QUERY.getValue());
		
		String hplat=plat;//如果是游戏相关的,就是svid,否则是plat
//		String partitionPlat = "`_plat`";
		
//		if(tableName.equals("user_gambling") || tableName.equals("gambling_detail") ||tableName.equals("gamecoins_stream") || tableName.equals("gambling_detail_view")){
//			hplat=svid;
//			partitionPlat="plat";
//		}
		//处理特殊符号:_uid-->`_uid`
		columns = HiveUtils.columsSpecialSymbols(columns);
//		if(partitionPlat.equals("plat")){
//			columns=columns.replaceAll("`_plat`", partitionPlat);
//		}
		//查询列信息
		Map<String,Object> columnMap = HiveUtils.getQueryColumn(columns,columnNames,jsonParam);
		String queryColumn = (String) columnMap.get("queryColumn");
		//输出的列名
		String outputColumn = columns;
		String outputTitle = columnNames;
		if(columnMap.containsKey("common")){
			Map<String,String> paramMap = (Map<String, String>) columnMap.get("common");
			queryColumn +=(","+paramMap.get("column"));
			outputColumn +=(","+paramMap.get("asName"));
			outputTitle +=(","+paramMap.get("title"));
		}
	   
	   	//获取分区信息
	   String partition = AppContext.partition.get(tableName);
	   JSONObject partitionjson = null;
	   if(StringUtils.isNotBlank(partition)){
		   partitionjson = JSONUtil.parseObject(partition);
	   	}
	   
	   StringBuffer sb = new StringBuffer();
	   if(StringUtils.isNotBlank(dependOn)){
		   jsonObject.put("dependOn",dependOn);
		   jsonObject.put("preTempTable","1");
			
		   sb.append("select b.* from ").append(Constants.tempTableName).append(" a left outer join (" );
	   	}
	   
	   sb.append("select ").append(queryColumn).append(" from ").append(tableName).append(" where 1=1 ");
	   if(partitionjson!=null && partitionjson.containsKey("_plat")){
		   sb.append(" and plat=").append(hplat);
		   //partitionPlat="plat";
	   	}
	   String wheretm = HiveUtils.getWhereBetweenTm(startTime, endTime, tableName);
	   sb.append(wheretm);
	   if(partitionjson==null || !partitionjson.containsKey("_plat")){
		   sb.append(" and `_plat`=").append(hplat);
	    }
	   sb.append(HiveUtils.getWhereSidOrBpid(tableName, sid_bpid));
	   
	   	//添加过滤条件
	   if(StringUtils.isNotBlank(filter)){
		   String[] hiveFilter = hiveFilter(filter,tableName);
		   if(hiveFilter!=null && hiveFilter.length>0){
			   sb.append(hiveFilter[1]);
		   }
		}
	   
	   if(StringUtils.isNotBlank(dependOn)){
			sb.append(") b on(").append(HiveUtils.onCondition("b","a",linkParam)).append(") where b.").append(columns.split(",")[0]).append(" is not null");
		}
	   
	   StringBuffer uniqueSb = new StringBuffer();
	   //去重操作
	  if(StringUtils.equals(distinctType, "3") || StringUtils.equals(distinctType, "4")){//获取最后一条记录
		  uniqueSb.append("select ").append(outputColumn).append(" from(");
		  uniqueSb.append("select ").append(queryColumn)
			  .append(",row_number() over (),rank() over(partition by `_uid` order by `_tm` ")
			  .append(StringUtils.equals(distinctType, "3")?"desc":"asc").append(",row_number() over ()) as rank from (").append(sb.toString()).append(") t ");
		  uniqueSb.append(" ) subquery where subquery.rank=1 ");
	   
	  }else if(StringUtils.equals(distinctType, "1") || StringUtils.equals(distinctType, "2")){
		  queryColumn = StringUtils.equals(distinctType, "1")?"`_uid`":("`_uid`,"+HiveUtils.getTmDate(tableName));
		  outputColumn = StringUtils.equals(distinctType, "1")?"`_uid`":("`_uid`,"+HiveUtils.getTmDate(tableName));
		  outputTitle = StringUtils.equals(distinctType, "1")?"用户ID":"用户ID,时间";
		  if(columnMap.containsKey("group")){
				Map<String,String> paramMap = (Map<String, String>) columnMap.get("group");
				queryColumn +=(","+paramMap.get("column"));
				outputColumn +=(","+paramMap.get("asName"));
				outputTitle +=(","+paramMap.get("title"));
		  }
		  if(jsonParam.containsKey("getCount")){
			  	queryColumn += ",count(1) as getCount";
			  	outputColumn +=",getCount";
				outputTitle += ","+(jsonParam.containsKey("getCountTitle")?jsonParam.getString("getCountTitle"):",统计记录数");
		  }
		  if(StringUtils.equals(distinctType, "1")){
			  uniqueSb.append("select "+queryColumn+" from (").append(sb.toString()).append(" )t group by "+"`_uid`");
		  }else if(StringUtils.equals(distinctType, "2")){
			  uniqueSb.append("select "+queryColumn+" from( ").append(sb.toString()).append(" )t group by "+"`_uid`,").append(HiveUtils.getTmDate(tableName));
		  }
	  }else{
		  uniqueSb.append(sb.toString());
	   }
		
	  jsonObject.put("columnName",HiveUtils.removeSpecialSymbols(outputColumn));
	  jsonObject.put("title",outputTitle);
	  jsonObject.put("operation",uniqueSb.toString());
		
	  return jsonObject;
	}
	
	
	/**
	 * 获取hbase的流程设置,暂时只处理ucuser
	 * @param jsonParam 
	 * 	{"processId":"process_2","processType":2,"column":"isbank,mlevel,addmoney","columnName":"是否有银行(isbank),等级(mlevel),addmoney(addmoney)",
	 * 	"filter":"","dependOn":"process_1","linkParam":"curi#0#_ver,curi#0#_ver"
	 * @param dependParam 
	 * @return
	 * @throws JSONException 
	 * @throws ParseException 
	 */
	public JSONObject hbaseProcessSerivce(JSONObject jsonParam) throws JSONException, ParseException {
		String tableName=jsonParam.getString("tableName");
		String processId=jsonParam.getString("processId");
		String columns = jsonParam.getString("columns");
		String columnNames = jsonParam.getString("columnNames");
		String plat=jsonParam.getString("plat");
		String sid_bpid = jsonParam.getString("sid");
		String filter = jsonParam.getString("filter");
		String dependOn = jsonParam.getString("dependOn");
		String linkParam = jsonParam.getString("linkParam");
		String format = jsonParam.getString("formatParam");
		
		boolean ifFullEmail = false;//是否获取完整邮箱
		if(columns.contains("fullEmail")){
			ifFullEmail = true;
			if(!columns.contains("email")){
				columns = columns.replaceAll("fullEmail","email");
			}else{
				columns = columns.replaceAll(",*fullEmail","");
				columnNames = columnNames.replaceAll(",*完整邮箱","");
				columnNames = columnNames.replaceAll("加密邮箱","完整邮箱");
			}
		}
		
		JSONObject json = JSONUtil.getJSONObject();
		json.put("_tnm", tableName);
		json.put("_plat", plat);
		json.put("rowkey", "false");
		
		//按顺序把IP格式化调用最后
		Map<String, String> data = this.getFormatData(columns,columnNames);
		columns = data.get("columns");
		columnNames = data.get("title");
		String outputColumn = data.get("outputColumn");
		String ipFormat = data.get("format");
		if(StringUtils.isNotBlank(ipFormat)){
			json.put("ipformat", ipFormat);
		}
		
		json.put("retkey", outputColumn);
		json.put("output_column", outputColumn);
		
		if(outputColumn.contains("email") && !ifFullEmail){
			JSONObject replace = JSONUtil.getJSONObject();
			replace.put("email", "***,3");
			json.put("replace", replace);
		}
		
		JSONObject comparatorCondition = JSONUtil.getJSONObject();
		if(StringUtils.isNotBlank(sid_bpid)){
			if(tableName.equals("user_ucuser")){//如果是ucuser,查某个子平台用unid来查
				String sid = sid_bpid.substring(0,sid_bpid.indexOf("_")) ;
				comparatorCondition.put("sid", sid+",0");
			}else{
				String bpid = sid_bpid.substring(sid_bpid.indexOf("_")+1);
				comparatorCondition.put("_bpid", bpid+",0");
			}
		}
		comparatorCondition = getFilterJson(comparatorCondition,filter);
		//hbase 添加json格式化字符串
		hbaseFormat(json, format);
		
		JSONObject jsonObject = JSONUtil.getJSONObject();
		jsonObject.put("tmpId",processId);
		
		//判断使用get还是scan
		boolean hasuid=false,hasplat=false;
		if(StringUtils.isNotBlank(dependOn)){
			jsonObject.put("dependOn",dependOn);
			json.put("file_column","#pcolumns#|"+linkParam);
			//linkParam:curi#0#_ver,curi#0#_ver
			String[] links = linkParam.split(",");
			for(int i=0;i<links.length;i++){
				if(links[i].startsWith("_uid#0#")) hasuid=true;
				if(links[i].startsWith("_plat#0#") || (jsonParam.containsKey("plat") && jsonParam.getIntValue("plat")>0)) hasplat=true;
			}
		}

		if(hasuid&&hasplat){
			jsonObject.put("type", ProcessTypeEnum.HBASE_MULTI_QUERY.getValue());
		}else{
			ServletUtil.setTime(json, null, null);
			jsonObject.put("type", ProcessTypeEnum.HBASE_SCAN.getValue());
		}
		if(comparatorCondition.size()>0){
			json.put("comparator__condition", comparatorCondition);
		}
			
		jsonObject.put("columnName",columns);
		jsonObject.put("title",columnNames);
		jsonObject.put("operation",json.toString());	
			
		return jsonObject;
	}
	
	public JSONObject uploadProcessSerivce(JSONObject jsonParam) throws JSONException {
		JSONObject jsonObject = JSONUtil.getJSONObject();
		jsonObject.put("tmpId",jsonParam.getString("processId"));
		jsonObject.put("columnName",jsonParam.getString("columns"));
		jsonObject.put("title",jsonParam.getString("columnNames"));
		jsonObject.put("path",jsonParam.getString("fileName"));	
		jsonObject.put("type", ProcessTypeEnum.FILE_UPLOAD.getValue());
		return jsonObject;
	}
	
	public JSONObject HasePutProcessSerivce(JSONObject jsonParam) throws JSONException, IOException {
		int processType = jsonParam.getIntValue("processType");
		
		JSONObject jsonObject = JSONUtil.getJSONObject();
		jsonObject.put("tmpId",jsonParam.getString("processId"));
		jsonObject.put("columnName",jsonParam.getString("columns"));
		jsonObject.put("title",jsonParam.getString("columnNames"));
		jsonObject.put("operation",jsonParam.getString("operator"));
		jsonObject.put("type", processType);
			
		String dependOn = jsonParam.getString("dependOn");
		if(StringUtils.isNotBlank(dependOn)){
			//process_1___temp__process__process_1
			jsonObject.put("dependOn",dependOn.substring(0,dependOn.indexOf("___")));
		}
		return jsonObject;
	}
	
	public JSONObject ortherProcessSerivce(JSONObject jsonParam) throws JSONException, IOException {
		int processType = jsonParam.getIntValue("processType");
		
		JSONObject jsonObject = JSONUtil.getJSONObject();
		jsonObject.put("tmpId",jsonParam.getString("processId"));
		jsonObject.put("columnName",jsonParam.getString("columns"));
		jsonObject.put("title",jsonParam.getString("columnNames"));
		jsonObject.put("operation",jsonParam.getString("operator"));
		if(processType==5){
			jsonObject.put("type", ProcessTypeEnum.MYSQL_EXEC.getValue());
		}else if(processType==6){
			jsonObject.put("type", ProcessTypeEnum.CUSTOM_EXEC.getValue());
		}
			
		String dependOn = jsonParam.getString("dependOn");
		if(StringUtils.isNotBlank(dependOn)){
			jsonObject.put("dependOn",dependOn);
			jsonObject.put("preTempTable","1");
		}
		return jsonObject;
	}
	
	public JSONObject hqlProcessSerivce(JSONObject jsonParam) throws JSONException, IOException {
		String hql = jsonParam.getString("hql");
		ResultState resultState = HiveUtils.validateHQL(hql);
		if(resultState.getOk().intValue()==ResultState.FAILURE) {
			errMsg = resultState.getMsg();
			return null;
		}
		hql = hql.trim().replaceAll("\'","\"");
		if(hql.endsWith(";")){
			hql=hql.substring(0, hql.length()-1);
		}
		
		JSONObject jsonObject = JSONUtil.getJSONObject();
		jsonObject.put("tmpId",jsonParam.getString("processId"));
		jsonObject.put("type", ProcessTypeEnum.HIVE_QUERY.getValue());
		jsonObject.put("columnName",jsonParam.getString("columns"));
		jsonObject.put("title",jsonParam.getString("columnNames"));
		jsonObject.put("operation",hql);
		
			
//		String dependOn = jsonParam.getString("dependOn");
//		if(StringUtils.isNotBlank(dependOn)){
//			jsonObject.put("dependOn",dependOn);
//			jsonObject.put("preTempTable","1");
//		}
		
		String depend = (String) JsonUtils.getValue(jsonParam, "depend", "");
		if(StringUtils.isNotBlank(depend)){
			jsonObject.put("depend",depend);
		}
		
		return jsonObject;
	}
	
	/**
	 * @param filter
	 * @param tableName
	 * @return 返回一个长度为2的数组,第一个是过滤的column,第二个是hql语句
	 */
	private String[] hiveFilter(String filter,String tableName) {
//		Map<String,String[]> defaultcoluns = HbtablesFileListener.hbtableDefaultFile;
//		Map<String, Map<String, String[]>> tableDetail = HbtablesFileListener.hbtableFiles;
//		Map<String, String[]> _columns = tableDetail.get(tableName);
//		if(_columns!=null && !_columns.isEmpty()){
//			defaultcoluns.remove("_ver");
//			defaultcoluns.remove("_gid");
//			_columns.putAll(defaultcoluns);
//		}
		
		Map<String, String> _columns = new HashMap<String, String>();
		try {
			JSONArray jsonArray = hiveMetaService.getColumnInfo(tableName);
			if(jsonArray==null || jsonArray.size()<=0){
				return null;
			}
			
			for(int i=0;i<jsonArray.size();i++){
				JSONObject jsonObject = (JSONObject) jsonArray.get(i);
				if(jsonObject.containsKey("column_name") && jsonObject.containsKey("valueType")){
					_columns.put(jsonObject.getString("column_name"), jsonObject.getString("valueType"));
				}
			}
		} catch (SQLException e) {
			errorLogger.error(e.getMessage());
			return null;
		} catch (JSONException e) {
			errorLogger.error(e.getMessage());
			return null;
		}
		
		StringBuilder sb = new StringBuilder();
		StringBuilder filterColumn = new StringBuilder();
		//添加过滤条件
		if(StringUtils.isNotBlank(filter)){
			String[] filters = filter.split(",");
			for(String _filter:filters){
				String[] condition = _filter.split("#");
				if(condition==null || condition.length!=3){
					continue;
				}
				//filter=_bpid#0#d
//				String[] typeValue = _columns.get(condition[0]);
				String typeValue = _columns.get(condition[0]);
				if(StringUtils.isNotBlank(typeValue)){
					String column = HiveUtils.columsSpecialSymbols(condition[0]);
					
					if(CommonUtil.isNumberType(typeValue)){
						sb.append(" and cast(").append(column).append(" as bigint)");
					}else{
						sb.append(" and ").append(column);
					}
					String opValue = CommonUtil.opValue(condition[1],typeValue,condition[2]);
					sb.append(" ").append(opValue).append(" ");
					filterColumn.append(column);
				}
			}
		}
		return new String[]{filterColumn.toString(),sb.toString()};
	}
	
	private JSONObject getFilterJson(JSONObject comparatorCondition,String filter){
		if(comparatorCondition==null){
			comparatorCondition = JSONUtil.getJSONObject();
		}
		
		if(StringUtils.isNotBlank(filter)){
			String[] filters = filter.split(",");
			for(String _filter:filters){
				String[] condition = _filter.split("#");
				if(condition==null || condition.length!=3){
					continue;
				}
				try {
					String value = condition[2];
					/*if(value.matches("^[0-9]{4}-[0-9]{2}-[0-9]{2}$")){
						int _op = Integer.parseInt(condition[1]);
						switch (_op) {
						case 0:
							comparatorCondition.put(condition[0], ServletUtil.date2Int(value,true)+",5"+ServletUtil.date2Int(value,false)+",2");
							break;
						case 1:
							comparatorCondition.put(condition[0], ServletUtil.date2Int(value,true)+",1");
							break;
						case 2:
							comparatorCondition.put(condition[0], ServletUtil.date2Int(value,false)+",2");
							break;
						case 3:
							if(_compareValue!=_value) return true;
							break;
						case 4:
							if(_compareValue>_value) return true;
							break;
						case 5:
							if(_compareValue>=_value) return true;
							break;
						}
						最小值 小于或小于等于操作符 最大值 大于或大于等于操作符
						comparatorCondition.put(condition[0], value+","+condition[1]);
						swo
						CompareOpENUM.put("0", CompareOp.EQUAL);//=
						CompareOpENUM.put("1", CompareOp.LESS);//<
						CompareOpENUM.put("2", CompareOp.LESS_OR_EQUAL);//<=
						CompareOpENUM.put("3", CompareOp.NOT_EQUAL);//!=
						CompareOpENUM.put("4", CompareOp.GREATER);//>
						CompareOpENUM.put("5", CompareOp.GREATER_OR_EQUAL);//>=
						long d = ServletUtil.date2Int(value,false);
					}else{
						comparatorCondition.put(condition[0], value+","+condition[1]);
					}*/
					comparatorCondition.put(condition[0], value+","+condition[1]);
				} catch (JSONException e) {
					errorLogger.error(e.getMessage());
				}
			}
		}
		return comparatorCondition;
	}
	
	private void hbaseFormat(JSONObject json, String format) throws JSONException {
		//数据格式化 format=substr#curi#dd#-1,ipformat#curi,replace#curi#dee#1
		if(StringUtils.isNotBlank(format)){
			String[] formats = format.split(",");
			JSONObject formatJson = JSONUtil.getJSONObject();
			JSONObject substrJson = JSONUtil.getJSONObject();
			JSONObject replaceJson = JSONUtil.getJSONObject();
			String ipformat="";
			for(String _format:formats){
				String[] conditions = _format.split("#");
				if(conditions[0].equals("format")){
					formatJson.put(conditions[1], conditions[2]);
				}else if(conditions[0].equals("substr")){
					substrJson.put(conditions[1], conditions[2]+","+conditions[3]);
				}else if(conditions[0].equals("replace")){
					replaceJson.put(conditions[1], conditions[2]+","+conditions[3]);
//				}else if(conditions[0].equals("ipformat")){
//					ipformat+=conditions[1]+",";
				}
			}
			if(formatJson.size()>0){
				json.put("format", formatJson);
			}
			if(substrJson.size()>0){
				json.put("substr", substrJson);
			}
			if(replaceJson.size()>0){
				json.put("replace", replaceJson);
			}
//			if(ipformat.length()>0){
//				ipformat = ipformat.substring(0,ipformat.length()-1);
//				json.put("ipformat", ipformat);
//			}
		}
	}
	
	public Map<String,String> getFormatData(String columns,String columnNames){
		Map<String, String> map = new HashMap<String, String>();
		
		if(StringUtils.isNotBlank(columns) && StringUtils.isNotBlank(columnNames) && columns.contains("ipformat__")){
			String[] columnArray = columns.split(",");
			String[] columnNameArray = columnNames.split(",");

			StringBuffer newColumn = new StringBuffer();
			StringBuffer outputColumn = new StringBuffer();
			StringBuffer title = new StringBuffer();
			StringBuffer ipColumnName = new StringBuffer();
			StringBuffer format = new StringBuffer();
			String _newColumn = null;
			if(columnArray.length==columnNameArray.length){
				for(int i=0;i<columnArray.length;i++){
					if(columnArray[i].startsWith("ipformat__")){
						ipColumnName.append(ipColumnName.length()>0?",":"").append(columnNameArray[i]);
						newColumn.append(newColumn.length()>0?",":"").append(columnArray[i]);
						//IP fromat
						format.append(format.length()>0?",":"").append(columnArray[i].split("__")[1]);
					}else{
						outputColumn.append(outputColumn.length()>0?",":"").append(columnArray[i]);
						title.append(title.length()>0?",":"").append(columnNameArray[i]);
					}
				}
				title.append(ipColumnName.length()>0?",":"").append(ipColumnName.toString());
				_newColumn = outputColumn.toString()+(newColumn.length()>0?",":"")+newColumn.toString();
				
				map.put("columns",_newColumn);
				map.put("outputColumn",outputColumn.toString());
				map.put("title",title.toString());
				map.put("format",format.toString());
				return map;
			}
		}
		
		map.put("columns",columns);
		map.put("outputColumn",columns);
		map.put("title",columnNames);
		return map;
	}
}
