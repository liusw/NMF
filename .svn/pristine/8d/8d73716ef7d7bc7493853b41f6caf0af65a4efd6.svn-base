package com.boyaa.service;

import com.boyaa.base.db.MySQLService;
import com.boyaa.base.utils.CsvUtil;
import com.boyaa.mf.constants.Constants;
import com.boyaa.mf.entity.task.ProcessInfo;
import com.boyaa.mf.entity.task.ProcessStatusEnum;
import com.boyaa.mf.service.common.HiveJdbcService;
import com.boyaa.mf.service.task.ProcessInfoService;
import com.boyaa.mf.util.HiveUtils;
import com.boyaa.mf.util.SpringBeanUtils;
import com.boyaa.servlet.ResultState;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 *hive和mysql的相关操作 
 */
public class HiveMysqlService{
	static Logger taskLogger = Logger.getLogger("taskLogger");
	static Logger errorLogger = Logger.getLogger("errorLogger");
	MySQLService mService = null;
	HiveJdbcService hiveService = null;
	Connection mConn = null;
	Connection hConn = null;
	
	public HiveMysqlService() throws SQLException {
		mService = new MySQLService();
		mConn = mService.getConnection();
		hiveService = SpringBeanUtils.getBean("hiveJdbcService",HiveJdbcService.class);
		hConn = hiveService.getConnection();
	}
	
	public void close(){
		if(mService != null){
			mService.close();
		}
		if(hiveService != null){
			hiveService.close();
		}
	}
	
	/**
	 * @描述 : 将hive的数据导入mysql，hive的select字段顺序必须与mysql的insert字段顺序一样
	 * @param htableName : hive的表名
	 * @param mtableName : mysql的表名
	 * @作者 : DarcyZeng
	 * @日期 : Dec 8, 2014
	 */
	public void hive2mysql(String htableName, String mtableName, String columns) throws Exception{
		hive2mysql(htableName,mtableName,columns,columns);
	}
	
	/**
	 * @描述 : 将hive的数据导入mysql
	 * @param htableName : hive的表名
	 * @param mtableName : mysql的表名
	 * @param hcolumns	 : hive的字段
	 * @param mcolumns 	 : mysql的字段
	 * @作者 : DarcyZeng
	 * @日期 : Dec 8, 2014
	 */
	public void hive2mysql(String htableName, String mtableName, String hcolumns, String mcolumns) throws Exception{
		StringBuffer hsql = new StringBuffer();
		hsql.append("select ").append(HiveUtils.columsSpecialSymbols(hcolumns)).append(" from ").append(htableName);
		taskLogger.info("hsql:" + hsql);
		
		StringBuffer mysql = new StringBuffer();
		mysql.append("insert ignore into logcenter_mf.").append(mtableName).append("(").append(mcolumns).append(") values(").append(genMark(mcolumns)).append(")");
		taskLogger.info("mysql:" + mysql);
		hive2mysql(hsql.toString(),mysql.toString());
	}
	/**
	 * @描述 : 将hive的数据导入mysql
	 * @param htableName : hive的表名
	 * @param mtableName : mysql的表名
	 * @param hcolumns	 : hive的字段
	 * @param mcolumns 	 : mysql的字段
	 * @param mcolumns 	 : mysql更新字段
	 * @作者 : DarcyZeng
	 * @日期 : Dec 8, 2014
	 */
	public void hive2mysql2(String htableName, String mtableName, String hcolumns, String mcolumns,String updaColumns) throws Exception{
		StringBuffer hsql = new StringBuffer();
		hsql.append("select ").append(HiveUtils.columsSpecialSymbols(hcolumns)).append(" from ").append(htableName);
		taskLogger.info("hsql:" + hsql);

		StringBuffer mysql = new StringBuffer();
		mysql.append("insert into logcenter_mf.").append(mtableName).append("(").append(mcolumns).append(") values(").append(genMark(mcolumns)).append(") ");
		mysql.append("ON DUPLICATE KEY ");
		mysql.append("UPDATE ").append(genUpdateMark(updaColumns));
		taskLogger.info("mysql:" + mysql);
		hive2mysql2(hsql.toString(),mysql.toString(),updaColumns);
	}

	private String genUpdateMark(String updaColumns) {
		StringBuffer result = new StringBuffer();
		if(StringUtils.isNotBlank(updaColumns)){
			String[] tmp = updaColumns.split(",");
			for(int i=0;i < tmp.length;i++){
				String col = tmp[i].split("%")[0];
				String colType = tmp[i].split("%")[1];
				if(StringUtils.equalsIgnoreCase("1",colType)){
					result.append(col + " = "+col+"+ ?,");
				}else{
					result.append(col + " = ?,");
				}
			}
			result.setLength(result.length() - 1);
		}
		return result.toString();
	}

	/**
	 *
	 * @param hsql hivesql
	 * @param msql mysql
	 * @param updaColumns 更新字段
     */
	private void hive2mysql2(String hsql, String msql, String updaColumns) throws SQLException {
		taskLogger.info("导入数据到mysql：");
		taskLogger.info("mysql:" + msql);
		taskLogger.info("hsql:" + hsql);
		if (mConn == null || mConn.isClosed()) {
			errorLogger.error("mysql connection is null or closed!");
			throw  new SQLException("mysql connection is null or closed!");
		}
		if (hConn == null || hConn.isClosed()) {
			errorLogger.error("hive connection is null or closed!");
			throw  new SQLException("hive connection is null or closed!");
		}
		mConn.setAutoCommit(false);

		PreparedStatement mpst = null;
		HiveJdbcService hiveService = null;
		PreparedStatement hPstmt = null;
		ResultSet hRs = null;
		try{
			mpst = (PreparedStatement) mConn.prepareStatement(msql);

			hiveService = SpringBeanUtils.getBean("HiveJdbcService",HiveJdbcService.class);
			hConn = hiveService.getConnection();

			hPstmt = hConn.prepareStatement(hsql);

			hRs = hPstmt.executeQuery();
			int count = hRs.getMetaData().getColumnCount();
			String[] updateColumns = updaColumns.split(",");
			while (hRs.next()) {
				int i = 1;
				for (; i <= count; i++) {
					try {
						Object value = hRs.getString(i);
						if(value == null){
							value = "";
						}
						mpst.setObject(i, value.toString());
					} catch (SQLException e) {
						errorLogger.error(e.getMessage());
					}
				}
				for (int j = 0; j < updateColumns.length; j++) {
					String uCname = updateColumns[j];
					if(StringUtils.isNotBlank(uCname)){
						String uName = uCname.split("%")[0]; //更新字段名
						String valName = hRs.getString(uName);
						mpst.setObject(i+j, valName);
					}
				}
				mpst.addBatch();
			}
			mpst.executeBatch();
			mConn.commit();
		}catch (Exception e){
			errorLogger.error(e.getStackTrace());
			e.printStackTrace();
			throw e;
		}finally {
			if (hRs != null){
				hRs.close();
			}
			if (hPstmt != null){
				hPstmt.close();
			}
			if(mpst != null){
				mpst.close();
			}
			if(mConn != null){
				mConn.setAutoCommit(true);
			}
		}
	}

	/**
	 * @描述 : 根据字段信息获取insert语句中的？
	 * @作者 : DarcyZeng
	 * @日期 : Dec 8, 2014
	 */
	public static String genMark(String columns){
		StringBuffer result = new StringBuffer();
		if(StringUtils.isNotBlank(columns)){
			String[] tmp = columns.split(",");
			for(int i=0;i < tmp.length;i++){
				result.append("?,");
			}
			result.setLength(result.length() - 1);
		}
		return result.toString();
	}
	
	/**
	 * 将hive的数据导入mysql，hive的select字段顺序必须与mysql的insert字段顺序一样
	 * @param hsql：hive的select语句
	 * @param msql：mysql的insert语句
	 * @throws Exception 
	 */
	public void hive2mysql(String hsql, String msql) throws Exception {
		taskLogger.info("导入数据到mysql：");
		taskLogger.info("mysql:" + msql);
		taskLogger.info("hsql:" + hsql);
		if (mConn == null || mConn.isClosed()) {
			errorLogger.error("mysql connection is null or closed!");
			throw  new SQLException("mysql connection is null or closed!");
		}
		if (hConn == null || hConn.isClosed()) {
			errorLogger.error("hive connection is null or closed!");
			throw  new SQLException("hive connection is null or closed!");
		}
		mConn.setAutoCommit(false); 
		
		PreparedStatement mpst = null;
		HiveJdbcService hiveService = null;
		PreparedStatement hPstmt = null;
		ResultSet hRs = null;
		try{
			mpst = (PreparedStatement) mConn.prepareStatement(msql);  

			hiveService = SpringBeanUtils.getBean("hiveJdbcService",HiveJdbcService.class);
			hConn = hiveService.getConnection();
			
			hPstmt = hConn.prepareStatement(hsql);
		
			hRs = hPstmt.executeQuery();
			int count = hRs.getMetaData().getColumnCount();
			while (hRs.next()) {
				for (int i = 1; i <= count; i++) {
					try {
						Object value = hRs.getString(i);
						if(value == null){
							value = "";
						}
						mpst.setObject(i, value.toString());
					} catch (SQLException e) {
						errorLogger.error(e.getMessage());
					}
				}
				mpst.addBatch();
			}
			mpst.executeBatch();
			mConn.commit();
		}catch (Exception e){
			errorLogger.error(e.getMessage());
			throw e;
		}finally {
			if (hRs != null){
				hRs.close();
			}
			if (hPstmt != null){
				hPstmt.close();
			}
			if(mpst != null){
				mpst.close();
			}
			if(mConn != null){
				mConn.setAutoCommit(true);
			}
		}
	}
	
	/**
	 * @描述 : 将mysql数据导入到hive
	 * @param htableName : hive的table名
	 * @param msql		 : mysql的select语句
	 * @param isOverwrite:是否覆盖hive原有数据
	 * @作者 : DarcyZeng
	 * @日期 : Dec 10, 2014
	 */
	public void mysql2hive(String htableName,String msql,boolean isOverwrite) throws Exception{
		try{
			if(StringUtils.isBlank(htableName)){
				errorLogger.error("htableName can't be null!");
				throw  new SQLException("htableName can't be null!");
			}
			if(StringUtils.isBlank(msql)){
				errorLogger.error("msql can't be null!");
				throw  new SQLException("msql can't be null!");
			}
			if (mConn == null || mConn.isClosed()) {
				errorLogger.error("mysql connection is null or closed!");
				throw  new SQLException("mysql connection is null or closed!");
			}
			if (hConn == null || hConn.isClosed()) {
				errorLogger.error("hive connection is null or closed!");
				throw  new SQLException("hive connection is null or closed!");
			}
			
			String path = mysql2file(msql,null);//先将数据导入文件
			
			StringBuffer hsql = new StringBuffer();
			hsql.append("load data local inpath '").append(path).append("' ");
			if(isOverwrite){
				hsql.append("overwrite");
			}
			hsql.append(" into table ").append(htableName);
			hiveService.execute(hsql.toString());
			
		}catch(Exception e){
			errorLogger.error("could not execute mysql2hive: " + e.getMessage());
			throw new Exception(e);
		}finally{
			
		}
	}
	
	/**
	 * @描述 : 将mysql数据导入文件
	 * @param mysql			:	mysql语句
	 * @param fullFileName 	:	文件完整路径,如果为空，自动生成文件路径
	 * @return 返回文件完整路径
	 * @作者 : DarcyZeng
	 * @日期 : Dec 22, 2014
	 */
	public String mysql2file(String mysql, String fullFileName) throws Exception{
		if(StringUtils.isBlank(mysql)){
			errorLogger.error("msql can't be null!");
			throw  new SQLException("msql can't be null!");
		}
		if (mConn == null || mConn.isClosed()) {
			errorLogger.error("mysql connection is null or closed!");
			throw  new SQLException("mysql connection is null or closed!");
		}
		if(StringUtils.isBlank(fullFileName)){
			fullFileName = Constants.FILE_DIR + "mysql2fiel_" + new Date().getTime()+".csv";
		}
		OutputStream os = new FileOutputStream(fullFileName, true);
		PrintWriter print = new PrintWriter(new OutputStreamWriter(os, "UTF-8"), true);
		mService.findAndSave(mysql, print);
		return fullFileName;
	}
	
	public ResultState hive2mysqlProcess(ProcessInfo processInfo){
		HiveJdbcService hiveService = null;
		HiveMysqlService hmService = null;
		ProcessInfoService processInfoService = null;
		try {
			hiveService = SpringBeanUtils.getBean("hiveJdbcService",HiveJdbcService.class);
			processInfoService = new ProcessInfoService();
			hmService = new HiveMysqlService();
			ProcessInfo dependProcess = processInfoService.findById(processInfo.getDependId());
			String htableName = "temp_process_"+processInfo.getId();
			String hcolumns = processInfo.getColumnName();
			String loadDataPath = HiveUtils.getLoadPath(Constants.FILE_DIR + dependProcess.getPath());
			hiveService.createTable(htableName,HiveUtils.getHiveCreateTableColumn(hcolumns),loadDataPath);
			String mcolumns = "sid,_plat,_uid,level,higher,win_coins0,win_coins1,lost_coins0,lost_coins1,win_games,lost_games,order_num,order_coins,last_week_order1,last_week_order0,last_month_order0,last_month_order1,deal_num,mmoney,mbank,mfree,mactivetime";
			hmService.hive2mysql(htableName, "channels_info", hcolumns,mcolumns);
			
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
			
			return new ResultState(ResultState.SUCCESS,"执行成功", fileName);
			
		} catch (Exception e) {
			errorLogger.error(e.getMessage());
			return new ResultState(ResultState.FAILURE,"执行失败", e.getMessage());
		}finally{
			if(hiveService != null){
				hiveService.close();
			}
			if(hmService != null){
				hmService.close();
			}
		}
	}

}
