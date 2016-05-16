package com.boyaa.mf.service.task;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.boyaa.base.hbase.HConnectionSingleton;
import com.boyaa.base.hbase.MultiThreadQuery;
import com.boyaa.base.utils.CsvUtil;
import com.boyaa.base.utils.JSONUtil;
import com.boyaa.mf.constants.Constants;
import com.boyaa.mf.entity.task.ProcessInfo;
import com.boyaa.mf.entity.task.ProcessStatusEnum;
import com.boyaa.mf.entity.task.ProcessTypeEnum;
import com.boyaa.mf.service.common.HbaseService;
import com.boyaa.mf.service.common.HiveHbaseService;
import com.boyaa.mf.service.common.HiveJdbcService;
import com.boyaa.mf.util.CommonUtil;
import com.boyaa.mf.util.HiveUtils;
import com.boyaa.service.FileService;
import com.boyaa.servlet.ContextServlet;
import com.boyaa.servlet.ResultState;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Row;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

@Service
public class HbaseProcessSerivce extends ProcessService {
	static Logger taskLogger = Logger.getLogger("taskLogger");
	static Logger errorLogger = Logger.getLogger("errorLogger");
	static Logger fatalLogger = Logger.getLogger("fatalLogger");
	
	@Autowired
	private ProcessInfoService processInfoService;
	@Autowired
	private HiveJdbcService hiveJdbcService;

	@Autowired
	private HiveHbaseService hiveHbaseService;

	@Autowired
	private HbaseService hbaseService;

	@Override
	public ResultState execute(ProcessInfo processInfo) {
		taskLogger.info("hbase查询流程开始执行,type="+processInfo.getType());
		if(processInfo.getType()!=null && processInfo.getType().intValue()==ProcessTypeEnum.HBASE_MULTI_QUERY.getValue()){
			return executeHbaseMultiQueryProcess(processInfo);
		}else if(processInfo.getType()!=null && processInfo.getType().intValue()==ProcessTypeEnum.HBASE_SCAN.getValue()){
			return executeHbaseScanProcess(processInfo);
		}else if(processInfo.getType()!=null && processInfo.getType().intValue()==ProcessTypeEnum.HBASE_PUT.getValue()){
			return executeHbaseInputProcess(processInfo);
		}
		return otherExecute(processInfo);
	}
	
	/**
	 * 执行多线程查询hbase任务
	 * @param processInfo
	 * @return
	 */
	private ResultState executeHbaseMultiQueryProcess(ProcessInfo processInfo) {
		taskLogger.info("多流程执行:"+processInfo.getId());
		JSONObject json=null;
		String hbaseWFile = "process_"+processInfo.getId() + "_" + new Date().getTime()+".csv";
		
		Map<ProcessInfo,Object> processInstance = ContextServlet.runningTaskInstance.get(processInfo.getTaskId());
		if(processInstance==null){
			processInstance = new HashMap<ProcessInfo, Object>();
		}
		
		try {
			ProcessInfo pi = findById(processInfo.getDependId());
			String pcolumnName = pi.getColumnName();
			String operation = processInfo.getOperation();
			
			
			json = JSONUtil.parseObject(operation);
			String fileColumn = json.getString("file_column");
			String newFileColumn = "";
			if(fileColumn.indexOf("|")!=-1){
				String[] fileColumns = fileColumn.split("\\|");
				newFileColumn = fileColumns[0].replace("#pcolumns#",pcolumnName);
				String[] linkParams = fileColumns[1].split(",");
				
				for(int i=0;i<linkParams.length;i++){
					String[] links = linkParams[i].split("#");
					if(links!=null && links.length==3){
						newFileColumn = CommonUtil.replatStr(newFileColumn, links[0], links[2]);
					}
				}
			}else{
				newFileColumn = fileColumn.replace("#pcolumns#",pcolumnName);
			}
			json.put("file_column", newFileColumn);
			
			if(StringUtils.isNotBlank(getTitle(processInfo.getId()))){
				json.put("output_title", processInfo.getTitle());
			}
			
			taskLogger.info("多线程执行json:"+json);
			
			MultiThreadQuery mtq = new MultiThreadQuery(HiveUtils.getLoadPath(Constants.FILE_DIR+pi.getPath()),Constants.FILE_DIR+hbaseWFile,json);
			processInstance.put(processInfo, mtq);
			ContextServlet.runningTaskInstance.put(processInfo.getTaskId(), processInstance);
			
			mtq.query();
			
			//update
			ProcessInfo updateProcess = new ProcessInfo();
			updateProcess.setStatus(ProcessStatusEnum.EXECUTION_END.getValue());
			updateProcess.setEndTime("now");
			updateProcess.setLogInfo("执行完成");
			updateProcess.setPath(hbaseWFile);
			updateProcess.setFileSize(FileService.getFileSize(Constants.FILE_DIR+hbaseWFile));
			updateProcess.setId(processInfo.getId());
			update(updateProcess);
			
			if(processInstance.containsKey(processInfo)){
				processInstance.remove(processInfo);
			}
			
			return new ResultState(ResultState.SUCCESS,"执行成功",hbaseWFile);
		} catch (JSONException e) {
			errorLogger.error(processInfo.getOperation() + " error: " + e.getMessage());
			
			ProcessInfo updateProcess = new ProcessInfo();
			updateProcess.setStatus(ProcessStatusEnum.EXECUTION_ERROR.getValue());
			updateProcess.setEndTime("now");
			updateProcess.setLogInfo("json参数出错:"+e.getMessage());
			updateProcess.setId(processInfo.getId());
			update(updateProcess);
			
			return new ResultState(ResultState.FAILURE,"json参数出错:"+e.getMessage(),null);
		}
	}
	
	/**
	 * 执行hbase scan查询任务
	 * @param processInfo
	 * @return
	 */
	private ResultState executeHbaseScanProcess(ProcessInfo processInfo) {
		JSONObject json = null;
		try {
			String fileName = "process_"+processInfo.getId() + "_" + new Date().getTime()+".csv";
			String path = Constants.FILE_DIR + fileName;
			json = JSONUtil.parseObject(processInfo.getOperation());
			if(StringUtils.isNotBlank(getTitle(processInfo.getId()))){
				json.put("output_title", processInfo.getTitle());
			}
			
			Map<ProcessInfo,Object> processInstance = ContextServlet.runningTaskInstance.get(processInfo.getTaskId());
			if(processInstance==null){
				processInstance = new HashMap<ProcessInfo, Object>();
			}
			processInstance.put(processInfo, hbaseService);
			ContextServlet.runningTaskInstance.put(processInfo.getTaskId(), processInstance);
			
			ResultState rs = hbaseService.scanData(json,path);
			if(rs.getOk().intValue()==ResultState.SUCCESS){
				//update
				ProcessInfo updateProcess = new ProcessInfo();
				updateProcess.setStatus(ProcessStatusEnum.EXECUTION_END.getValue());
				updateProcess.setEndTime("now");
				updateProcess.setLogInfo("执行完成");
				updateProcess.setPath(fileName);
				updateProcess.setFileSize(FileService.getFileSize(Constants.FILE_DIR+fileName));
				updateProcess.setId(processInfo.getId());
				update(updateProcess);
			}else{//这个判断暂时没进行
				ProcessInfo updateProcess = new ProcessInfo();
				updateProcess.setStatus(ProcessStatusEnum.EXECUTION_ERROR.getValue());
				updateProcess.setEndTime("now");
				updateProcess.setLogInfo(rs.getMsg());
				updateProcess.setId(processInfo.getId());
				update(updateProcess);
			}
			return new ResultState(ResultState.SUCCESS,"执行成功",fileName);
		} catch (JSONException e) {
			errorLogger.error(json + " error: " + e.getMessage());
			
			ProcessInfo updateProcess = new ProcessInfo();
			updateProcess.setStatus(ProcessStatusEnum.EXECUTION_ERROR.getValue());
			updateProcess.setEndTime("now");
			updateProcess.setLogInfo("json参数解析出错:"+e.getMessage());
			updateProcess.setId(processInfo.getId());
			update(updateProcess);
			return new ResultState(ResultState.FAILURE,"hbase导出出现异常",null);
		}
	}
	
	/**
	 * 把数据插入到hbase中
	 * @param processInfo
	 * @return
	 */
	private ResultState executeHbaseInputProcess(ProcessInfo processInfo) {
		taskLogger.info("插入hbase执行:"+processInfo.getId());
		
		Map<ProcessInfo,Object> processInstance = ContextServlet.runningTaskInstance.get(processInfo.getTaskId());
		if(processInstance==null){
			processInstance = new HashMap<ProcessInfo, Object>();
		}
		
		try {
			ProcessInfo pi = findById(processInfo.getDependId());
			if(pi==null || StringUtils.isBlank(pi.getPath())){
				return new ResultState(ResultState.FAILURE,"所依赖的流程不存在或者没有数据",null);
			}
			
			//{'table':'test_putdata','rowkey':'c0_5,c1_10,c2_10',column:'c0_int,c1_long,c2_string,c3_int'}
			JSONObject operJson = JSONUtil.parseObject(processInfo.getOperation());
			String tableName = operJson.getString("table");
				
			BufferedReader br = null;
			String line = null;
			String path = HiveUtils.getLoadPath(Constants.FILE_DIR+pi.getPath());
			try {
				br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "utf-8"));
				List<Row> rows = new ArrayList<Row>();
				while((line = br.readLine()) != null) {
					String[] values = line.split(",");
					String[] columns = pi.getColumnName().split(",");
					
					String rowkey = getRowKey(operJson.getString("rowkey").split(","),columns,values);
					Put put = new Put(rowkey.getBytes());
					
					String[] inputColumns = operJson.getString("column").split(",");
					for(int i=0;i<inputColumns.length;i++){
						String[] columnInfo = inputColumns[i].split("\\|");
						String value = getValue(columnInfo[0],columns,values);
						put.addColumn(com.boyaa.base.utils.Constants.COLUMN_FAMILY_BYTE,Bytes.toBytes(columnInfo[0]),convertTo(columnInfo[1],value,"0"));
					}
					rows.add(put);
					
					if(rows.size()==1000){
						putData(tableName,rows);
						rows.clear();
						rows = new ArrayList<Row>();
					}
				}
				
				if(rows.size()>0){
					putData(tableName,rows);
					rows.clear();
				}
			} catch (Exception e) {
				errorLogger.error("插入数据异常: " + e.getMessage());
				return new ResultState(ResultState.FAILURE,"插入数据异常: " + e.getMessage());
			} finally {
				try {
					if(br != null)
						br.close();
				} catch (IOException e) {
					errorLogger.error("could not close the reader object: " + e.getMessage());
				}
			}
			
			return new ResultState(ResultState.SUCCESS,"执行成功");
		} catch (JSONException e) {
			errorLogger.error(processInfo.getOperation() + " error: " + e.getMessage());
			
			ProcessInfo updateProcess = new ProcessInfo();
			updateProcess.setStatus(ProcessStatusEnum.EXECUTION_ERROR.getValue());
			updateProcess.setEndTime("now");
			updateProcess.setLogInfo("json参数出错:"+e.getMessage());
			updateProcess.setId(processInfo.getId());
			update(updateProcess);
			
			return new ResultState(ResultState.FAILURE,"json参数出错:"+e.getMessage(),null);
		}
	}
	
	private String getValue(String column, String[] columns, String[] values) {
		String value = null;
		for(int j=0;j<columns.length;j++){
			if(column.trim().equals(columns[j].trim())){
				value = values[j];
				break;
			}
		}
		return value;
	}

	private void putData(String tableName,List<Row> rows){
		Table table = null;
		try {
			table = HConnectionSingleton.create().getTable(TableName.valueOf(tableName));
			table.batch(rows,null);
		} catch (Exception e) {
			errorLogger.error(e.getMessage());
			throw new RuntimeException(e.getMessage());
		} finally{
			if(table!=null){
				try {
					table.close();
				} catch (IOException e) {
					errorLogger.error(e.getMessage());
				}
			}
		}
	}
	
	private String getRowKey(String[] keys, String[] columns, String[] values) {
		String rowkey="";
		for(int i=0;i<keys.length;i++){
			String name = keys[i].split("\\|")[0];
			String size = keys[i].split("\\|")[1];
			for(int j=0;j<columns.length;j++){
				if(name.trim().equals(columns[j].trim())){
					rowkey += StringUtils.leftPad(values[j], Integer.parseInt(size), "0");
					break;
				}
			}
		}
		return rowkey;
	}
	
	public static byte[] convertTo(String type, String value, String defaultValue) {
		//String v = defaultValue == null ? value : defaultValue;
		String v = (value==null?defaultValue:value).trim();
		
		byte[] b = null;
		if("int".equalsIgnoreCase(type))
			b = Bytes.toBytes(Integer.valueOf(v));
		else if("long".equalsIgnoreCase(type))
			b = Bytes.toBytes(Long.valueOf(v));
		else if("float".equalsIgnoreCase(type))
			b = Bytes.toBytes(Float.valueOf(v));
		else if("double".equalsIgnoreCase(type))
			b = Bytes.toBytes(Double.valueOf(v));
		else
			b = Bytes.toBytes(v);
		return b;
	}

	/**
	 * hbase入库流程
	 * @param processInfo
	 * @param json
	 * @return
	 */
	public ResultState insert(ProcessInfo processInfo, JSONObject json){
		try {
			String tableName = null;
			if(json.containsKey("tname")){
				tableName = json.getString("tname");
			}else{
				throw new Exception("param tname is null!");
			}
			
			ProcessInfo dependProcess = processInfoService.findById(processInfo.getDependId());
			String htableName = "temp_process_"+processInfo.getId();
			String hcolumns = dependProcess.getColumnName();
			String loadDataPath = HiveUtils.getLoadPath(Constants.FILE_DIR + dependProcess.getPath());
			hiveJdbcService.createTable(htableName,HiveUtils.getHiveCreateTableColumn(hcolumns),loadDataPath);
			String hbcolumns = processInfo.getColumnName();
			hiveHbaseService.hive2hbase(htableName, hcolumns, hbcolumns);
			
			String fileName = "process_"+processInfo.getId() + "_" + new Date().getTime()+".csv";
			String path = Constants.FILE_DIR + fileName;
			StringBuffer hsql =  new StringBuffer();
			hsql.append("select ").append(HiveUtils.columsSpecialSymbols(hcolumns)).append(" from ").append(htableName);
			hiveJdbcService.findAndSave(hsql.toString(), CsvUtil.getPrint(path));
			
			ProcessInfo updateProcess = new ProcessInfo();
			updateProcess.setId(processInfo.getId());
			updateProcess.setStatus(ProcessStatusEnum.EXECUTION_END.getValue());
			updateProcess.setEndTime("now");
			updateProcess.setLogInfo("执行完成");
			updateProcess.setPath(fileName);
			updateProcess.setFileSize(FileService.getFileSize(path));
			
			processInfoService.update(updateProcess);

			hiveJdbcService.dropTable(htableName);
			
			return new ResultState(ResultState.SUCCESS,"执行成功",fileName);
		} catch (Exception e) {
			errorLogger.error(e.getMessage());
			ProcessInfo updateProcess = new ProcessInfo();
			updateProcess.setStatus(ProcessStatusEnum.EXECUTION_ERROR.getValue());
			updateProcess.setEndTime("now");
			updateProcess.setLogInfo(e.getMessage());
			updateProcess.setId(processInfo.getId());
			update(updateProcess);
			return new ResultState(ResultState.FAILURE, e.getMessage(), null);
		}finally{
			if(hiveJdbcService!=null){
				hiveJdbcService.close();
			}
			if(hbaseService!=null){
				hbaseService.close();
			}
		}
	}
}