package com.boyaa.mf.service.task;

import com.boyaa.base.db.MySQLService;
import com.boyaa.base.utils.CsvUtil;
import com.boyaa.base.utils.DateUtil;
import com.boyaa.mf.constants.Constants;
import com.boyaa.mf.entity.task.ProcessInfo;
import com.boyaa.mf.entity.task.ProcessStatusEnum;
import com.boyaa.mf.service.common.HiveJdbcService;
import com.boyaa.mf.service.common.HiveMysqlService;
import com.boyaa.mf.util.HiveUtils;
import com.boyaa.mf.util.SpringBeanUtils;
import com.boyaa.service.FileService;
import com.boyaa.servlet.ResultState;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

/**
 * @描述 : mysql操作的业务处理类
 * @作者 : DarcyZeng
 * @日期 : 2014-12-22
 */
@SuppressWarnings("all")
@Service
public class MysqlProcessService extends ProcessService{
	static Logger taskLogger = Logger.getLogger("taskLogger");
	static Logger errorLogger = Logger.getLogger("errorLogger");
	static Logger fatalLogger = Logger.getLogger("fatalLogger");
	
	@Autowired
	private ProcessInfoService processInfoService;

	@Autowired
	private HiveJdbcService hiveJdbcService;
	@Autowired
	private HiveMysqlService hiveMysqlService;

	@Override
	public ResultState execute(ProcessInfo processInfo) {
		if(processInfo != null){
			String operation = processInfo.getOperation();
			String[] operations = operation.split("\\|");
			if(operations.length < 2){
				return new ResultState(ResultState.FAILURE,"operation的参数设置错误：import(导入处理)|tableName(表名) or export(导出处理)|sql(语句) or update(更新mysql)|sql(语句)",null);
			}
			if(StringUtils.isNotBlank(operations[1])){
				if(Constants.MYSQL_IMPORT_TYPE.equals(operations[0])){
					return importData(processInfo,operations[1]);
				}else if(Constants.MYSQL_EXPORT_TYPE.equals(operations[0])){
					return exportData(processInfo,operations[1]);
				}else if(Constants.MYSQL_COMMON_IMPORT_TYPE.equals(operations[0])){
					return commonImport(processInfo,operations[1]);
				}else if(Constants.MYSQL_TRUNCATE_TYPE.equals(operations[0])){
					return truncateTable(processInfo,operations[1]);
				}else if(Constants.MYSQL_SAVEORUPDATE_TYPE.equals(operations[0])){
					return saveOrUpdate(processInfo,operations[1]);
				}else{
					return modifyData(processInfo,operations[1]);
				}
			}else{
				return new ResultState(ResultState.FAILURE,"operation的参数设置错误：import(导入处理)|tableName(表名) or export(导出处理)|sql(语句)",null);
			}
		}
		return new ResultState(ResultState.FAILURE,"执行流程出错",null);
	}

	/**
	 * mysql insert into ... ON DUPLICATE KEY update 方式导入数据
	 * @param processInfo
	 * @param operation
     * @return
     */
	private ResultState saveOrUpdate(ProcessInfo processInfo, String operation) {

		MySQLService mService = null;
		try {
			ProcessInfo dependProcess = processInfoService.findById(processInfo.getDependId());
			String htableName = "temp_process_"+processInfo.getId();
			String hcolumns = dependProcess.getColumnName();
			String loadDataPath = HiveUtils.getLoadPath(Constants.FILE_DIR + dependProcess.getPath());
			hiveJdbcService.createTable(htableName,HiveUtils.getHiveCreateTableColumn(hcolumns),loadDataPath);
			String mcolumns = processInfo.getColumnName();
			String tableName = operation.split("#")[0];
			String updateColumns = operation.split("#")[1];
			hiveMysqlService.hive2mysql2(htableName, tableName, hcolumns, mcolumns,updateColumns);

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
		} catch(Exception e){
			errorLogger.error(e.getMessage());

			ProcessInfo updateProcess = new ProcessInfo();
			updateProcess.setStatus(ProcessStatusEnum.EXECUTION_ERROR.getValue());
			updateProcess.setEndTime("now");
			updateProcess.setLogInfo(e.getMessage());
			updateProcess.setId(processInfo.getId());
			update(updateProcess);

			return new ResultState(ResultState.FAILURE,e.getMessage(),null);
		}finally{
			if(hiveJdbcService!=null) {
				hiveJdbcService.close();
			}
			if(hiveMysqlService != null) {
				hiveMysqlService.close();
			}
			if(mService != null){
				mService.close();
			}
		}
	}

	/**
	 * mysql truncate 操作
	 * @param processInfo
	 * @param operation
     * @return
     */
	private ResultState truncateTable(ProcessInfo processInfo, String tableName) {
		MySQLService mService = null;
		try{
			mService = new MySQLService();
			ProcessInfo updateProcess = new ProcessInfo();
			mService.execute("TRUNCATE TABLE logcenter_mf."+tableName);
			updateProcess.setId(processInfo.getId());
			updateProcess.setStatus(ProcessStatusEnum.EXECUTION_END.getValue());
			updateProcess.setEndTime("now");
			updateProcess.setLogInfo("执行完成");
			processInfoService.update(updateProcess);
			return new ResultState(ResultState.SUCCESS,"执行成功","");
		}catch (Exception e){
			errorLogger.error(e.getMessage());

			ProcessInfo updateProcess = new ProcessInfo();
			updateProcess.setStatus(ProcessStatusEnum.EXECUTION_ERROR.getValue());
			updateProcess.setEndTime("now");
			updateProcess.setLogInfo(e.getMessage());
			updateProcess.setId(processInfo.getId());
			update(updateProcess);
			return new ResultState(ResultState.FAILURE,e.getMessage(),null);
		}
	}

	/**
	 * mysql一些常用的执行
	 * @param processInfo
	 * @param tableName
	 * @return
	 */
	private ResultState commonImport(ProcessInfo processInfo,String tableName){
		HiveJdbcService hiveService = null;
		HiveMysqlService hmService = null;
		MySQLService mService = null;
		try {
			hiveService = SpringBeanUtils.getBean("hiveJdbcService",HiveJdbcService.class);
			hmService = new HiveMysqlService();
			ProcessInfo dependProcess = processInfoService.findById(processInfo.getDependId());
			String htableName = "temp_process_"+processInfo.getId();
			String hcolumns = dependProcess.getColumnName();
			String loadDataPath = HiveUtils.getLoadPath(Constants.FILE_DIR + dependProcess.getPath());
			hiveService.createTable(htableName,HiveUtils.getHiveCreateTableColumn(hcolumns),loadDataPath);
			String mcolumns = processInfo.getColumnName();
			
			hmService.hive2mysql(htableName, tableName, hcolumns, mcolumns);
			
			String fileName = "process_"+processInfo.getId() + "_" + new Date().getTime()+".csv";
			String path = Constants.FILE_DIR + fileName;
			StringBuffer hsql =  new StringBuffer();
			hsql.append("select ").append(HiveUtils.columsSpecialSymbols(hcolumns)).append(" from ").append(htableName);
			hiveService.findAndSave(hsql.toString(), CsvUtil.getPrint(path));
			
			ProcessInfo updateProcess = new ProcessInfo();
			updateProcess.setId(processInfo.getId());
			updateProcess.setStatus(ProcessStatusEnum.EXECUTION_END.getValue());
			updateProcess.setEndTime("now");
			updateProcess.setLogInfo("执行完成");
			updateProcess.setPath(fileName);
			updateProcess.setFileSize(FileService.getFileSize(path));
			
			processInfoService.update(updateProcess);
			
			hiveService.dropTable(htableName);
			
			return new ResultState(ResultState.SUCCESS,"执行成功",fileName);
		} catch(Exception e){
			errorLogger.error(e.getMessage());
			
			ProcessInfo updateProcess = new ProcessInfo();
			updateProcess.setStatus(ProcessStatusEnum.EXECUTION_ERROR.getValue());
			updateProcess.setEndTime("now");
			updateProcess.setLogInfo(e.getMessage());
			updateProcess.setId(processInfo.getId());
			update(updateProcess);
			
			return new ResultState(ResultState.FAILURE,e.getMessage(),null);
		}finally{
			if(hiveService!=null) {
				hiveService.close();
			}
			if(hmService != null) {
				hmService.close();
			}
			if(mService != null){
				mService.close();
			}
		}
	}
	
	/**
	 * @描述 : 导入数据到mysql
	 * @param processInfo : 流程信息
	 * @param tableName	  : mysql数据库表名
	 * @作者 : DarcyZeng
	 * @日期 : Dec 22, 2014
	 */
	private ResultState importData(ProcessInfo processInfo,String tableName){
		HiveJdbcService hiveService = null;
		HiveMysqlService hmService = null;
		MySQLService mService = null;
		try {
			hiveService = SpringBeanUtils.getBean("hiveJdbcService",HiveJdbcService.class);
			hmService = new HiveMysqlService();
			ProcessInfo dependProcess = processInfoService.findById(processInfo.getDependId());
			String htableName = "temp_process_"+processInfo.getId();
			String hcolumns = dependProcess.getColumnName();
			String loadDataPath = HiveUtils.getLoadPath(Constants.FILE_DIR + dependProcess.getPath());
			hiveService.createTable(htableName,HiveUtils.getHiveCreateTableColumn(hcolumns),loadDataPath);
			String mcolumns = processInfo.getColumnName();
			
			//删除旧数据
			Integer tm = TaskManager.getInstense().getTM(getUuid());
			taskLogger.info("删除旧数据start,tm="+tm);
			if(tm!=null){
				boolean hasTM = false;
				String[] arr = mcolumns.split(",");
				for(String str : arr){
					if(str.equals("tm")){
						hasTM = true;
						break;
					}
				}
				if(hasTM){
					mService = new MySQLService();
					mService.execute("delete from logcenter_mf."+tableName+" where tm="+tm);
					taskLogger.info("delete from logcenter_mf."+tableName+" where tm="+tm);
				}
			}
			taskLogger.info("删除旧数据end,tm="+tm);
			hmService.hive2mysql(htableName, tableName, hcolumns, mcolumns);
			
			String fileName = "process_"+processInfo.getId() + "_" + new Date().getTime()+".csv";
			String path = Constants.FILE_DIR + fileName;
			StringBuffer hsql =  new StringBuffer();
			hsql.append("select ").append(HiveUtils.columsSpecialSymbols(hcolumns)).append(" from ").append(htableName);
			hiveService.findAndSave(hsql.toString(), CsvUtil.getPrint(path));
			
			ProcessInfo updateProcess = new ProcessInfo();
			updateProcess.setId(processInfo.getId());
			updateProcess.setStatus(ProcessStatusEnum.EXECUTION_END.getValue());
			updateProcess.setEndTime("now");
			updateProcess.setLogInfo("执行完成");
			updateProcess.setPath(fileName);
			updateProcess.setFileSize(FileService.getFileSize(path));
			
			processInfoService.update(updateProcess);
			
			hiveService.dropTable(htableName);
			
			return new ResultState(ResultState.SUCCESS,"执行成功",fileName);
		} catch(Exception e){
			errorLogger.error(e.getMessage());
			
			ProcessInfo updateProcess = new ProcessInfo();
			updateProcess.setStatus(ProcessStatusEnum.EXECUTION_ERROR.getValue());
			updateProcess.setEndTime("now");
			updateProcess.setLogInfo(e.getMessage());
			updateProcess.setId(processInfo.getId());
			update(updateProcess);
			
			return new ResultState(ResultState.FAILURE,e.getMessage(),null);
		}finally{
			if(hiveService!=null) {
				hiveService.close();
			}
			if(hmService != null) {
				hmService.close();
			}
			if(mService != null){
				mService.close();
			}
		}
	}
	
	/**
	 * @描述 : 导出mysql数据
	 * @param processInfo	: 流程信息
	 * @param mysql 		: mysql语句
	 * @作者 : DarcyZeng
	 * @日期 : Dec 22, 2014
	 */
	private ResultState exportData(ProcessInfo processInfo, String mysql){
		HiveMysqlService hmService = null;
		try {
			hmService = new HiveMysqlService();
			
			String fileName = "process_"+processInfo.getId() + "_" + new Date().getTime()+".csv";
			String path = Constants.FILE_DIR + fileName;
			hmService.mysql2file(mysql, path);
			
			ProcessInfo updateProcess = new ProcessInfo();
			updateProcess.setId(processInfo.getId());
			updateProcess.setStatus(ProcessStatusEnum.EXECUTION_END.getValue());
			updateProcess.setEndTime("now");
			updateProcess.setLogInfo("执行完成");
			updateProcess.setPath(fileName);
			updateProcess.setFileSize(FileService.getFileSize(path));
			
			processInfoService.update(updateProcess);

			return new ResultState(ResultState.SUCCESS,"执行成功",fileName);
		} catch (SQLException e) {
			errorLogger.error(e.getMessage());
			
			ProcessInfo updateProcess = new ProcessInfo();
			updateProcess.setStatus(ProcessStatusEnum.EXECUTION_ERROR.getValue());
			updateProcess.setEndTime("now");
			updateProcess.setLogInfo("mysql语句出错:"+e.getMessage());
			updateProcess.setId(processInfo.getId());
			update(updateProcess);
			
			return new ResultState(ResultState.FAILURE,"mysql语句出错:"+e.getMessage(),null);
		} catch (Exception e) {
			errorLogger.error(e.getMessage());
			
			ProcessInfo updateProcess = new ProcessInfo();
			updateProcess.setStatus(ProcessStatusEnum.EXECUTION_ERROR.getValue());
			updateProcess.setEndTime("now");
			updateProcess.setLogInfo("写入文件出错:"+e.getMessage());
			updateProcess.setId(processInfo.getId());
			update(updateProcess);
			
			return new ResultState(ResultState.FAILURE,"写入文件出错:"+e.getMessage(),null);
		}finally{
			if(hmService != null) {
				hmService.close();
			}
		}
	}
	
	/**
	 * @描述 : 更新mysql数据
	 * @param processInfo	: 流程信息
	 * @param mysql 		: mysql语句
	 */
	private ResultState modifyData(ProcessInfo processInfo, String mysql){
		MySQLService mysqlService = null;
		try {
			mysqlService = new MySQLService();
			String operation = operate(mysql);
			mysqlService.execute(operation);
			ProcessInfo updateProcess = new ProcessInfo();
			updateProcess.setId(processInfo.getId());
			updateProcess.setStatus(ProcessStatusEnum.EXECUTION_END.getValue());
			updateProcess.setEndTime("now");
			updateProcess.setLogInfo("执行完成");
			updateProcess.setPath(null);
			updateProcess.setFileSize(0l);
			processInfoService.update(updateProcess);

			return new ResultState(ResultState.SUCCESS,"执行成功",null);
		} catch (SQLException e) {
			errorLogger.error(e.getMessage());
			ProcessInfo updateProcess = new ProcessInfo();
			updateProcess.setStatus(ProcessStatusEnum.EXECUTION_ERROR.getValue());
			updateProcess.setEndTime("now");
			updateProcess.setLogInfo("mysql语句出错:"+e.getMessage());
			updateProcess.setId(processInfo.getId());
			update(updateProcess);
			return new ResultState(ResultState.FAILURE,"mysql语句出错:"+e.getMessage(),null);
		}finally{
			if(mysqlService != null) {
				mysqlService.close();
			}
		}
	}
	
	private String operate(String sql){
		Integer time = TaskManager.getInstense().getTime(getUuid());
		String date = DateUtil.addDay("yyyyMMdd", -1+time);
		String sqlAfter = sql;
		try {
			if(sqlAfter.contains(Constants.START_TIME)){
				sqlAfter = sqlAfter.replace(Constants.START_TIME, date);
			}
			if (sqlAfter.contains(Constants.LAST_WEEK_0)) {
				sqlAfter = sqlAfter.replace(Constants.LAST_WEEK_0, DateUtil.getFirstDayOfWeek(date, "yyyyMMdd", -1));
			}
			if (sqlAfter.contains(Constants.LAST_WEEK_1)) {
				sqlAfter = sqlAfter.replace(Constants.LAST_WEEK_1, DateUtil.getLastDayOfWeek(date, "yyyyMMdd", -1));
			}
			if (sqlAfter.contains(Constants.LAST_MONTH_0)) {
				sqlAfter = sqlAfter.replace(Constants.LAST_MONTH_0, DateUtil.getFirstDayOfMonth(date, "yyyyMMdd", -1));
			}
			if (sqlAfter.contains(Constants.LAST_MONTH_1)) {
				sqlAfter = sqlAfter.replace(Constants.LAST_MONTH_1, DateUtil.getLastDayOfMonth(date, "yyyyMMdd", -1));
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sqlAfter;
	}
}