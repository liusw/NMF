package com.boyaa.mf.service.task;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.boyaa.base.utils.CsvUtil;
import com.boyaa.base.utils.DateUtil;
import com.boyaa.mf.constants.Constants;
import com.boyaa.mf.entity.task.ProcessInfo;
import com.boyaa.mf.entity.task.ProcessStatusEnum;
import com.boyaa.mf.service.common.HiveJdbcService;
import com.boyaa.mf.util.CommonUtil;
import com.boyaa.mf.util.HiveUtils;
import com.boyaa.mf.util.SpringBeanUtils;
import com.boyaa.service.FileService;
import com.boyaa.servlet.ContextServlet;
import com.boyaa.servlet.ResultState;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @作者 : MarsHuang
 * @部门 : 德州扑克业务中心-开拓者工作室-公共组
 * @公司 : 博雅互动
 * @日期 : 2014-11-6  上午11:02:06
 * @描述 :hive操作的业务处理类
 */
@SuppressWarnings("all")
@Service
public class HiveProcessService extends ProcessService {
	static Logger taskLogger = Logger.getLogger("taskLogger");
	static Logger errorLogger = Logger.getLogger("errorLogger");
	static Logger fatalLogger = Logger.getLogger("fatalLogger");
	private static Map<String,String> hiveTime = new HashMap<String,String>();

	@Override
	public ResultState execute(ProcessInfo processInfo) {

		HiveJdbcService hiveService=null;
		PrintWriter print = null;
		Map<Object,Object> obj = null;
		try {
			hiveService = SpringBeanUtils.getBean("hiveJdbcService",HiveJdbcService.class);
			
			List<String> tmpTables = new ArrayList<String>();
			
			//创建临时表
			if(processInfo.getPreTempTable()!=null && processInfo.getPreTempTable()==1){
				String tableName = "temp_process_"+processInfo.getId();
				createTempTable(tableName,processInfo.getDependId(),hiveService,processInfo.getId());
				processInfo.setOperation(processInfo.getOperation().replace(Constants.tempTableName,tableName));
				tmpTables.add(tableName);
			}
			
			if(StringUtils.isNotBlank(processInfo.getDepend())){
				//#temp__process__21148#
				String[] depends = processInfo.getDepend().split(",");
				for(int i=0;i<depends.length;i++){
					String tableName = depends[i];
					if(!tableName.startsWith("temp__process__")){
						continue;
					}
					int dependProcessId = Integer.parseInt(tableName.split("__")[2]);
					
					createTempTable(tableName,dependProcessId,hiveService,processInfo.getId());
					
					tmpTables.add(tableName);
				}
			}
			
			//如果多条语句,分成多次执行
			String operation = processInfo.getOperation();
			if(StringUtils.isNotBlank(operation)){
				String taskId = String.valueOf(processInfo.getTaskId());
				operation = replace(operation, taskId);
				if(operation.contains("#dataTime#")){
					String key = "#dataTime#" + taskId;
					if(hiveTime.containsKey(key)){//如果设置了startTime，则使用设置的，否则默认是昨天
						operation = operation.replace("#dataTime#", hiveTime.get(key));
					}else{
						operation = operation.replace("#dataTime#", processInfo.getDescription());
					}
				}
			}
			
			if(operation.contains(";")){
				String[] operationArr = operation.split(";");
				for(int i=0;i<operationArr.length-1;i++){
					hiveService.execute(operationArr[i]);
					taskLogger.info("execute:"+processInfo.getId()+","+operationArr[i]);
				}
				operation = operationArr[operationArr.length-1];
			}
		
			String fileName = "process_"+processInfo.getId() + "_" + new Date().getTime()+".csv";
			String path = Constants.FILE_DIR + fileName;
			print = CsvUtil.getPrint(path);
			taskLogger.info("process start:"+processInfo.getId()+","+operation);
			
			if(StringUtils.isNotBlank(getTitle(processInfo.getId()))){
				print.println(processInfo.getTitle());
			}
			
			//判断是否含有user_gambling这个表,如果有,添加UDF
			List<String> tables = HiveUtils.getTables(operation);
			if(tables!=null && tables.contains("user_gambling")){
				//hiveService.execute("add jar /data/soft/new/hive/auxlib/hb-base.jar");
				if(operation.contains("get_stand")){
					hiveService.execute("add jar /data/soft/new/hive/auxlib/hb-base.jar");
					hiveService.execute("create temporary function get_stand as 'com.boyaa.base.hive.udf.GamblingDetailStandUDF'");
				}
				if(operation.contains("gd_time")){
					hiveService.execute("add jar /data/soft/new/hive/auxlib/hb-base.jar");
					hiveService.execute("create temporary function gd_time as 'com.boyaa.base.hive.udf.GamblingDetailTimeUDF'");
				}
			}
			if(operation.contains("geoip")){
				hiveService.execute("add jar /data/soft/new/hive/auxlib/hb-base.jar");
				hiveService.execute("add file /data/soft/new/hive/auxlib/GeoLite2-City.mmdb");
				hiveService.execute("create temporary function geoip as 'com.boyaa.base.hive.udf.GeoIP2UDF'");
//				hiveService.execute("add jar /data/soft/hive/auxlib/hb-base.jar");
//				hiveService.execute("add file /data/soft/hive/auxlib/GeoLiteCity.dat");
//				hiveService.execute("create temporary function geoip as 'com.boyaa.base.hive.udf.GeoIPUDF'");
			}
//			if(operation.contains("geoiptype")){
//				hiveService.execute("add jar /data/soft/hive/auxlib/hb-base.jar");
//				hiveService.execute("add file /data/soft/hive/auxlib/GeoLiteCity.dat");
//				hiveService.execute("create temporary function geoiptype as 'com.boyaa.base.hive.udf.GeoIPTypeUDF'");
//			}
			if(operation.contains("gambling_detail_view")){
				hiveService.execute("add jar /data/soft/new/hive/auxlib/hb-base.jar");
				hiveService.execute("create temporary function gambling_parser as 'com.boyaa.base.hive.udtf.GamblingDetailParserUDTF'");
			}
			if(operation.contains("gambling_detail")){
				hiveService.execute("add jar /data/soft/new/hive/auxlib/hb-base.jar");
				hiveService.execute("create temporary function gd_action_list as 'com.boyaa.base.hive.udtf.GamblingDetailUidTimeActionListUDTF'");
			}
			
			//给语句加上特殊标识
			String uniqueKey = processInfo.getTaskId()+"_"+processInfo.getId()+"_"+System.currentTimeMillis();
			operation= CommonUtil.replaceIgnoreCase(operation, "select");
			operation = "select \""+uniqueKey+"\" processUniqueKey,"+operation.substring(operation.indexOf("select")+6);
			taskLogger.info("process add uniqueKey:"+processInfo.getId()+","+operation);
			
			//记录正在运行的实例
			Map<ProcessInfo,Object> processInstance = ContextServlet.runningTaskInstance.get(processInfo.getTaskId());
			if(processInstance==null){
				processInstance = new HashMap<ProcessInfo, Object>();
			}
			processInstance.put(processInfo, uniqueKey);
			ContextServlet.runningTaskInstance.put(processInfo.getTaskId(), processInstance);
			hiveService.findAndSave(operation, print);
			
			ProcessInfo updateProcess = new ProcessInfo();
			updateProcess.setStatus(ProcessStatusEnum.EXECUTION_END.getValue());
			updateProcess.setEndTime("now");
			updateProcess.setLogInfo("执行完成");
			updateProcess.setPath(fileName);
			updateProcess.setFileSize(FileService.getFileSize(Constants.FILE_DIR+fileName));
			updateProcess.setId(processInfo.getId());
			update(updateProcess);
			
			for(String tableName :tmpTables){
				if(!tableName.startsWith("temp_")){
					continue;
				}
				hiveService.execute("drop table if exists "+tableName);
			}
			
			if(processInstance.containsKey(processInfo)){
				processInstance.remove(processInfo);
			}
			return new ResultState(ResultState.SUCCESS,"执行成功",fileName);
		} catch (SQLException e) {
			errorLogger.error(e.getMessage());
			
			ProcessInfo updateProcess = new ProcessInfo();
			updateProcess.setStatus(ProcessStatusEnum.EXECUTION_ERROR.getValue());
			updateProcess.setEndTime("now");
			updateProcess.setLogInfo("hive查询出错:"+e.getMessage());
			updateProcess.setId(processInfo.getId());
			update(updateProcess);
			
			return new ResultState(ResultState.FAILURE,"hive查询出错:"+e.getMessage(),null);
		} catch (IOException e) {
			errorLogger.error(e.getMessage());
			
			ProcessInfo updateProcess = new ProcessInfo();
			updateProcess.setStatus(ProcessStatusEnum.EXECUTION_ERROR.getValue());
			updateProcess.setEndTime("now");
			updateProcess.setLogInfo("写入文件出错:"+e.getMessage());
			updateProcess.setId(processInfo.getId());
			update(updateProcess);
			
			return new ResultState(ResultState.FAILURE,"写入文件出错:"+e.getMessage(),null);
		} catch (ParseException e) {
			errorLogger.error(e.getMessage());
			ProcessInfo updateProcess = new ProcessInfo();
			updateProcess.setStatus(ProcessStatusEnum.EXECUTION_ERROR.getValue());
			updateProcess.setEndTime("now");
			updateProcess.setLogInfo("解析日期出错:"+e.getMessage());
			updateProcess.setId(processInfo.getId());
			update(updateProcess);
			
			return new ResultState(ResultState.FAILURE,"解析日期出错:"+e.getMessage(),null);
		}finally{
			if(print!=null) print.close();
			if(hiveService!=null) hiveService.close();
		}
	}
	
	private void createTempTable(String tableName,int dependProcessId,HiveJdbcService hiveService,int processId) throws SQLException{
		ProcessInfo dependProcess = findById(dependProcessId);
		
		if(!tableName.startsWith("temp_")){
			return;
		}
		
		hiveService.execute("drop table if exists "+tableName);
		taskLogger.info("execute:"+processId+","+"drop table if exists "+tableName);
		
		String columnName = dependProcess.getColumnName();
		columnName=HiveUtils.getHiveCreateTableColumn(columnName);
		
		StringBuilder sb = new StringBuilder();
		sb.append("create table ").append(tableName).append("(").append(columnName).append(") ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' STORED AS TEXTFILE");
		hiveService.execute(sb.toString());
		taskLogger.info("execute:"+processId+","+sb.toString());
		//hiveService.execute("load data local inpath '" + HiveUtils.getLoadPath(Constants.FILE_DIR+dependProcess.getPath()) + "' into table "+tableName);
		if(StringUtils.isNotBlank(dependProcess.getPath())){
			hiveService.execute("load data local inpath '" + Constants.FILE_DIR+dependProcess.getPath() + "' into table "+tableName);
			taskLogger.info("execute:"+processId+","+"load data local inpath '" + Constants.FILE_DIR+dependProcess.getPath() + "' into table "+tableName);
		}else{
			errorLogger.error("依赖的文件不存在,不能load进临时表");
		}
	}
	
	/**
	 * 此方法由反射接口调用进来com.boyaa.service.HiveProcessService|execute|{sql:"xxx"}
	 * 执行特殊的hive sql 比如insert into select ---
	 * @param processInfo
	 * @param jsonObject
	 * @return
	 */
	public ResultState execute(ProcessInfo processInfo, JSONObject jsonObject){
		if(jsonObject==null || !jsonObject.containsKey("sql")){
			errorLogger.error("没有可执行的sql");
			ProcessInfo updateProcess = new ProcessInfo();
			updateProcess.setStatus(ProcessStatusEnum.EXECUTION_ERROR.getValue());
			updateProcess.setEndTime("now");
			updateProcess.setLogInfo("没有可执行的sql");
			updateProcess.setId(processInfo.getId());
			update(updateProcess);
			return new ResultState(ResultState.FAILURE,"没有可执行的sql",null);
		}
		HiveJdbcService hiveService=null;
		try {
			hiveService = SpringBeanUtils.getBean("hiveJdbcService",HiveJdbcService.class);
			String sql = jsonObject.getString("sql");
			String taskId = String.valueOf(processInfo.getTaskId());
			sql = replace(sql, taskId);
			
			//如果多条语句,分成多次执行
			if(sql.contains(";")){
				String[] sqlArr = sql.split(";");
				for(int i=0;i<sqlArr.length;i++){
					hiveService.execute(sqlArr[i]);
					taskLogger.info("execute:"+processInfo.getId()+","+sqlArr[i]);
				}
			}else{
				hiveService.execute(sql);
			}
			
			ProcessInfo updateProcess = new ProcessInfo();
			updateProcess.setStatus(ProcessStatusEnum.EXECUTION_END.getValue());
			updateProcess.setEndTime("now");
			updateProcess.setLogInfo("执行完成");
			updateProcess.setPath(null);
			updateProcess.setId(processInfo.getId());
			update(updateProcess);
			return new ResultState(ResultState.SUCCESS,"执行成功",null);
		} catch (JSONException e) {
			errorLogger.error(e.getMessage());
			ProcessInfo updateProcess = new ProcessInfo();
			updateProcess.setStatus(ProcessStatusEnum.EXECUTION_ERROR.getValue());
			updateProcess.setEndTime("now");
			updateProcess.setLogInfo("解析sql出错:"+e.getMessage());
			updateProcess.setId(processInfo.getId());
			update(updateProcess);
			return new ResultState(ResultState.FAILURE,"解析日期出错:"+e.getMessage(),null);
		} catch (ParseException e) {
			errorLogger.error(e.getMessage());
			ProcessInfo updateProcess = new ProcessInfo();
			updateProcess.setStatus(ProcessStatusEnum.EXECUTION_ERROR.getValue());
			updateProcess.setEndTime("now");
			updateProcess.setLogInfo("解析日期出错:"+e.getMessage());
			updateProcess.setId(processInfo.getId());
			update(updateProcess);
			return new ResultState(ResultState.FAILURE,"解析日期出错:"+e.getMessage(),null);
		} catch (SQLException e) {
			errorLogger.error(e.getMessage());
			ProcessInfo updateProcess = new ProcessInfo();
			updateProcess.setStatus(ProcessStatusEnum.EXECUTION_ERROR.getValue());
			updateProcess.setEndTime("now");
			updateProcess.setLogInfo("hive查询出错:" + e.getMessage());
			updateProcess.setId(processInfo.getId());
			update(updateProcess);
			return new ResultState(ResultState.FAILURE, "hive查询出错:"+ e.getMessage(), null);
		}
	}
	
	private String replace(String operation, String taskId) throws ParseException {
		String option = operation;
		String date = DateUtil.addDay(DateUtil.DATE_FORMAT, -1);
		Integer time = TaskManager.getInstense().getTime(getUuid());
		Map<String, String> map = getOpts(time);
		for(Map.Entry<String,String> entry : map.entrySet()){
			String timeIndex = entry.getKey();
			if(option.contains(timeIndex)){
				String key = timeIndex + taskId;
				if(hiveTime.containsKey(key)){
					option = option.replace(timeIndex, hiveTime.get(key));
				}else{
					option = option.replace(timeIndex,entry.getValue());
				}
			}
		}
		
		//特殊处理不在上面日期中的数据
		String regEx = "#TM_FROM_([-+]?\\d+)_ADD_([-+]?\\d+)#";
		Pattern pattern = Pattern.compile(regEx,Pattern.CASE_INSENSITIVE); 
		Matcher matcher = pattern.matcher(option);
		
		Date tmpDate = null;
		while(matcher.find()){
			String tmp = matcher.group().trim();
			try {
				tmpDate = DateUtil.addDay(DateUtil.strToDate(matcher.group(1), DateUtil.DATE_FORMAT),Integer.parseInt(matcher.group(2)));
				String tm = DateUtil.get(tmpDate, DateUtil.DATE_FORMAT);
				option=option.replaceAll(tmp, tm);
				matcher = pattern.matcher(option);
			} catch (Exception e) {
				errorLogger.error(e.getMessage());
			}
		}
		
		return option;
	}
	
	private Map<String, String> getOpts(int days) throws ParseException{
		String date = DateUtil.addDay(DateUtil.DATE_FORMAT, -1+days);
		Map<String, String> map = new HashMap<String, String>();
		map.put(Constants.START_TIME, DateUtil.addDay(DateUtil.DATE_FORMAT, -1+days));
		map.put(Constants.END_TIME, DateUtil.addDay(DateUtil.DATE_FORMAT, -1+days));
		map.put(Constants.YESTERDAY, DateUtil.addDay(DateUtil.DATE_FORMAT, -2+days));
		map.put(Constants.THREE_DAY, DateUtil.addDay(DateUtil.DATE_FORMAT, -4+days));
		map.put(Constants.SEVEN_DAY, DateUtil.addDay(DateUtil.DATE_FORMAT, -8+days));
		map.put(Constants.FIFTY_DAY, DateUtil.addDay(DateUtil.DATE_FORMAT, -16+days));
		map.put(Constants.THIRTY_DAY, DateUtil.addDay(DateUtil.DATE_FORMAT, -31+days));
		map.put(Constants.LAST_MONTH_0, DateUtil.getFirstDayOfMonth(date, DateUtil.DATE_FORMAT, -1));
		map.put(Constants.LAST_MONTH_1, DateUtil.getLastDayOfMonth(date, DateUtil.DATE_FORMAT, -1));
		map.put(Constants.THREE_MONTH_0, DateUtil.getFirstDayOfMonth(date, DateUtil.DATE_FORMAT, -3));
		map.put(Constants.THREE_MONTH_1, DateUtil.getLastDayOfMonth(date, DateUtil.DATE_FORMAT, -3));
		map.put(Constants.SIX_MONTH_0, DateUtil.getFirstDayOfMonth(date, DateUtil.DATE_FORMAT, -6));
		map.put(Constants.SIX_MONTH_1, DateUtil.getLastDayOfMonth(date, DateUtil.DATE_FORMAT, -6));
		map.put(Constants.TWELVE_MONTH_0, DateUtil.getFirstDayOfMonth(date, DateUtil.DATE_FORMAT, -12));
		map.put(Constants.TWELVE_MONTH_1, DateUtil.getLastDayOfMonth(date, DateUtil.DATE_FORMAT, -12));
		map.put(Constants.LAST_WEEK_0, DateUtil.getFirstDayOfWeek(date, DateUtil.DATE_FORMAT, -1));
		map.put(Constants.LAST_WEEK_1, DateUtil.getLastDayOfWeek(date, DateUtil.DATE_FORMAT, -1));
		map.put(Constants.TWO_WEEK_0, DateUtil.getFirstDayOfWeek(date, DateUtil.DATE_FORMAT, -2));
		map.put(Constants.TWO_WEEK_1, DateUtil.getLastDayOfWeek(date, DateUtil.DATE_FORMAT, -2));
		map.put(Constants.THREE_WEEK_0, DateUtil.getFirstDayOfWeek(date, DateUtil.DATE_FORMAT, -3));
		map.put(Constants.THREE_WEEK_1, DateUtil.getLastDayOfWeek(date, DateUtil.DATE_FORMAT, -3));
		map.put(Constants.FOUR_WEEK_0, DateUtil.getFirstDayOfWeek(date, DateUtil.DATE_FORMAT, -4));
		map.put(Constants.FOUR_WEEK_1, DateUtil.getLastDayOfWeek(date, DateUtil.DATE_FORMAT, -4));
		
		return map;
	}
	
	/**
	 * @描述 : 设置hsql的tm的执行时间
	 * @作者 : DarcyZeng
	 * @日期 : Apr 29, 2015
	 */
	public static void setHiveTime(String key,String value){
		if(StringUtils.isNotBlank(key) && StringUtils.isNotBlank(value)){
			hiveTime.put(key, value);
		}
	}
	
	/**
	 * @描述 : 取消hsql的tm的执行时间
	 * @作者 : DarcyZeng
	 * @日期 : Apr 29, 2015
	 */
	public static void cancelHiveTime(String key){
		if(StringUtils.isNotBlank(key)){
			if(hiveTime.containsKey(key)){
				hiveTime.remove(key);
			}
		}
	}
}