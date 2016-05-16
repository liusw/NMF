package com.boyaa.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.boyaa.base.db.MySQLService;
import com.boyaa.base.utils.CsvUtil;
import com.boyaa.base.utils.DateUtil;
import com.boyaa.entity.Brush;
import com.boyaa.mf.constants.Constants;
import com.boyaa.mf.entity.task.ProcessInfo;
import com.boyaa.mf.entity.task.ProcessStatusEnum;
import com.boyaa.mf.service.common.HiveJdbcService;
import com.boyaa.mf.service.task.ProcessInfoService;
import com.boyaa.mf.util.HiveUtils;
import com.boyaa.mf.util.SpringBeanUtils;
import com.boyaa.servlet.ResultState;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class BrushService extends MySQLService {
	static Logger logger = Logger.getLogger(BrushService.class);
	static Logger errorLogger = Logger.getLogger("errorLogger");
	
	public BrushService() throws SQLException {
		super();
	}
	
	public String exportData(Map<String,String> params) throws SQLException, IOException{
		StringBuilder sb = new StringBuilder();
		sb.append("select v3.id,v3._plat,v3._uid,v3.mnick,v3.mexplevel,v3.lang,v3.login_name,v3.email, v3.mtime,v3.vmoney,v3.status,v3.mactivetime,v3.loginip,v3.mmoney,round(v3.mpay,2) mpay ")
		  .append(" from logcenter_mf.brush_v3 v3")
		  .append(" join (select max(id) id from logcenter_mf.brush_v3  group by _plat,_uid)v3ids on  v3.id=v3ids.id")
		  .append(" left join logcenter_mf.template te on v3.template_id = te.id ")
		  .append(" where 1=1  ");
		
		if(StringUtils.isNotEmpty(params.get("status"))){
			sb.append(" and v3.status ='").append(params.get("status")).append("'");
		}
		if(StringUtils.isNotEmpty(params.get("uid"))){
			sb.append(" and v3._uid like'%").append(params.get("uid")).append("%'");
		}
		if(StringUtils.isNotEmpty(params.get("rstime"))){
			sb.append(" and from_unixtime(unix_timestamp(v3._tm),'%Y-%m-%d')>='").append(params.get("rstime")).append("'");
		}
		if(StringUtils.isNotEmpty(params.get("retime"))){
			sb.append(" and from_unixtime(unix_timestamp(v3._tm),'%Y-%m-%d')<='").append(params.get("retime")).append("'");
		}
		if(StringUtils.isNotEmpty(params.get("plat"))){
			sb.append(" and v3._plat='").append(params.get("plat")).append("'");
		}
		
		sb.append(" order by template_id,v3._tm desc");
		
		
		String fileName = "brush_v3_"+new Date().getTime()+".csv"; 
		
		PrintWriter print = CsvUtil.getPrint(Constants.FILE_DIR+fileName);
		print.println("模板,平台,用户ID,用户名,等级,语言,地区,邮箱,注册IP,最后登录IP,注册时间,最后登录时间,当前牌局总数,正常牌局数,非正常牌局数,赢牌局数,输牌局数,台费,消费总金额,状态,是否粉丝,"
				+ "最大持有游戏币数量,当前持有游戏币数量,当前持有免费游戏币数量,登陆次数,移动好友赠送筹码(获得),移动每日任务(获得),四叶草赠(获得),幸运筹码(获得),粉丝奖励(获得),移动好友赠送筹码(获得),"
				+ "移动每日任务(获得),四叶草赠(获得),幸运筹码(获得),粉丝奖励(获得),记录生成时间");
		
		this.findAndSave(sb.toString(), print);
		return fileName;
	}
	
	private void buildParams(Map<String,String> params,StringBuilder sb){
		if(StringUtils.isNotEmpty(params.get("status"))){
			sb.append(" and status ='").append(params.get("status")).append("'");
		}
		if(StringUtils.isNotEmpty(params.get("uid"))){
			sb.append(" and _uid like'%").append(params.get("uid")).append("%'");
		}
		if(StringUtils.isNotEmpty(params.get("rstime"))){
			sb.append(" and from_unixtime(unix_timestamp(_tm),'%Y-%m-%d')>='").append(params.get("rstime")).append("'");
		}
		if(StringUtils.isNotEmpty(params.get("retime"))){
			sb.append(" and from_unixtime(unix_timestamp(_tm),'%Y-%m-%d')<='").append(params.get("retime")).append("'");
		}
		if(StringUtils.isNotEmpty(params.get("plat"))){
			sb.append(" and _plat='").append(params.get("plat")).append("'");
		}
	}
	
	public JSONArray findByPage(int iDisplayStart, int iDisplayLength, Map<String,String> params){
		StringBuilder sb = new StringBuilder();
		sb.append("select v3.id,v3._plat,v3._uid,v3.mnick,v3.mexplevel,v3.lang,v3.login_name,v3.email, v3.mtime,v3.vmoney,v3.status,v3.mactivetime,v3.loginip,v3.mmoney,round(v3.mpay,2) mpay ")
		.append("from logcenter_mf.brush_v3 v3 join (select max(id) id from logcenter_mf.brush_v3  group by _plat,_uid order by _uid)v3ids on  v3.id=v3ids.id where 1=1 ");
		
		if(StringUtils.isNotEmpty(params.get("status"))){
			sb.append(" and v3.status ='").append(params.get("status")).append("'");
		}
		if(StringUtils.isNotEmpty(params.get("uid"))){
			sb.append(" and v3._uid like'%").append(params.get("uid")).append("%'");
		}
		if(StringUtils.isNotEmpty(params.get("rstime"))){
			sb.append(" and from_unixtime(unix_timestamp(v3._tm),'%Y-%m-%d')>='").append(params.get("rstime")).append("'");
		}
		if(StringUtils.isNotEmpty(params.get("retime"))){
			sb.append(" and from_unixtime(unix_timestamp(v3._tm),'%Y-%m-%d')<='").append(params.get("retime")).append("'");
		}
		if(StringUtils.isNotEmpty(params.get("plat"))){
			sb.append(" and v3._plat='").append(params.get("plat")).append("'");
		}
		sb.append(" order by v3._tm desc ");
		
		sb.append(" limit ").append(iDisplayStart).append(",").append(iDisplayLength);
		try {
			this.setDefaultValue("");
			return this.find(sb.toString());
		} catch (SQLException e) {
			errorLogger.error("sql:"+sb.toString()+","+e.getMessage());
		}
		return null;
	}
	
	public int count(int iDisplayStart, int iDisplayLength, Map<String,String> params) throws JSONException{
		StringBuilder sb = new StringBuilder();
		sb.append("select COUNT(*) total from logcenter_mf.brush_v3 where 1=1 " );
		
		buildParams(params,sb);
		sb.append(" group by _plat,_uid,_tm");
		try {
			this.setDefaultValue("");
			JSONArray countNum = this.find(sb.toString());
			if(countNum != null && countNum.size()>0){
				return countNum.size();
			}else{
				return 0;
			}
		} catch (SQLException e) {
			errorLogger.error("sql:"+sb.toString()+","+e.getMessage());
		}
		return 0;
	}
	
	public void updateStatus(Brush brush) throws SQLException{
		StringBuilder sb = new StringBuilder();
		sb.append("update logcenter_mf.brush_v3 set status ='").append(brush.getStatus()).append("' where 1=1");
		sb.append(" and id=").append(brush.getId());
		this.execute(sb.toString());
	}
	
	public void markAll()throws SQLException{
		StringBuilder sb = new StringBuilder();
		sb.append("update logcenter_mf.brush_v3 set status ='1'");
		this.execute(sb.toString());
	}
	
	public Brush query(int id) throws SQLException, JSONException{
		JSONArray json = null;
		StringBuilder sb = new StringBuilder();
		sb.append("select id,_plat,_uid,mnick,mexplevel,lang,login_name,email, mtime,vmoney,status from logcenter_mf.brush_v3 where 1=1");
		sb.append(" and id=").append(id);
		json = this.find(sb.toString());
		if(json != null){
			return json2Brush(json.getJSONObject(0));
		}
		return null;
	}
	
	public List<Brush> json2Brush(JSONArray objs) throws JSONException{
		List<Brush> brushs = new ArrayList<Brush>();
		for(int i=0;i<objs.size();i++){
			brushs.add(json2Brush(objs.getJSONObject(i)));
		}
		return brushs;
	}
	
	public Brush json2Brush(JSONObject jSONObject) throws JSONException{
		Brush brush = new Brush();
		brush.setId(jSONObject.getIntValue("id"));
		brush.setPlat(jSONObject.getIntValue("_plat"));
		brush.setUid(jSONObject.getIntValue("_uid"));
		brush.setMnick(jSONObject.getString("mnick"));
		brush.setMexplevel(jSONObject.getIntValue("mexplevel"));
		brush.setLang(jSONObject.getString("lang"));
		brush.setLoginName(jSONObject.getString("login_name"));
		brush.setEmail(jSONObject.getString("email"));
		brush.setMtime(jSONObject.getString("mtime"));
		brush.setVmoney(jSONObject.getLong("vmoney"));
		brush.setStatus(jSONObject.getString("status"));
		return brush;
	}
	
	/**
	 * 插入数据
	 * @return
	 */
	public ResultState brushProcess(ProcessInfo processInfo, JSONObject json){
		HiveJdbcService hiveService = null;
//		Map<Object,Object> obj = null;
		ProcessInfo updateProcessInfo = null;
		ProcessInfoService processInofoService = null;
		
		try {
			hiveService = SpringBeanUtils.getBean("hiveJdbcService",HiveJdbcService.class);
			processInofoService = SpringBeanUtils.getBean("processInofoService",ProcessInfoService.class);
			String tableName = "temp_process_"+processInfo.getId();
			//创建临时表
			if(processInfo.getPreTempTable()!=null && processInfo.getPreTempTable()==1){
				ProcessInfo dependProcess = processInofoService.findById(processInfo.getDependId());
				
				hiveService.execute("drop table if exists "+tableName);
				logger.info("execute:"+processInfo.getId()+","+"drop table if exists "+tableName);
				
				String columnName = dependProcess.getColumnName();
				columnName=HiveUtils.getHiveCreateTableColumn(columnName);
				
				StringBuilder sb = new StringBuilder();
				sb.append("create table ").append(tableName).append("(").append(columnName).append(") ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' STORED AS TEXTFILE");
				hiveService.execute(sb.toString());
				logger.info("execute:"+processInfo.getId()+","+sb.toString());
				hiveService.execute("load data local inpath '" + HiveUtils.getLoadPath(Constants.FILE_DIR+dependProcess.getPath()) + "' into table "+tableName);
				logger.info("execute:"+processInfo.getId()+","+"load data local inpath '" + HiveUtils.getLoadPath(Constants.FILE_DIR+dependProcess.getPath()) + "' into table "+tableName);
				
				processInfo.setOperation(processInfo.getOperation().replace(Constants.tempTableName,tableName));
			}
			
			String operation = processInfo.getOperation();
			logger.info("process start:"+processInfo.getId()+","+operation);
			
			String hsql = "select `_plat`,`_uid`,mnick,mfcount,mexplevel,lang,regipRegion,email,regip,loginip,mtime,mactivetime,mpcount,playnormal,playunnormal,winCount,loseCount" +
					",vmoney,mpay,mprivilege,maxOwnChips,mmoney,mfree,mentercount," +
					"inc_gc_s_374,inc_gc_s_248, inc_gc_s_2, inc_gc_s_32, inc_gc_s_33, inc_gc_c_374, inc_gc_c_248, inc_gc_c_2, inc_gc_c_32, inc_gc_c_33,template_id,type" +
					" from  " + tableName;
			
			String msql = "insert ignore into logcenter_mf.brush_v3(_plat, _uid, mnick, mfcount, mexplevel, lang, login_name, " +
					"email, regip, loginip, mtime, mactivetime, mpcount, playnormal, playunnormal, winCount, " +
					"loseCount, vmoney, mpay, mprivilege, maxOwnChips, mmoney, mfree, mentercount, inc_gc_s_374, " +
					"inc_gc_s_248, inc_gc_s_2, inc_gc_s_32, inc_gc_s_33, inc_gc_c_374, inc_gc_c_248, inc_gc_c_2, inc_gc_c_32, inc_gc_c_33,template_id,type) " +
					"values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			hive2mysql(hsql,msql);//写入到mysql
			
			//写入到文件
			String fileName = "process_"+processInfo.getId() + "_" + new Date().getTime()+".csv";
			String path = Constants.BRUSH_DIR + fileName;
			PrintWriter print = getPrint(path);
			hsql = "select '" + DateUtil.addDay(DateUtil.DEFAULT_DATE_FORMAT, -1) + "',`_plat`,`_uid`,type" +
					" from  " + tableName;
			hiveService.findAndSave(hsql, print);
		
			logger.info("hsql:" + hsql);
			
			String downDirectory = Constants.FILE_DIR;
			FileUtils.copyFileToDirectory(new File(path), new File(downDirectory));
			
			updateProcessInfo = new ProcessInfo();
			updateProcessInfo.setStatus(ProcessStatusEnum.EXECUTION_END.getValue());
			updateProcessInfo.setEndTime("now");
			updateProcessInfo.setLogInfo("执行完成");
			updateProcessInfo.setPath(fileName);
			updateProcessInfo.setFileSize(FileService.getFileSize(path));
			processInofoService.update(updateProcessInfo);
			
			if(processInfo.getPreTempTable()!=null && processInfo.getPreTempTable()==1){
				hiveService.execute("drop table if exists "+tableName);
			}
			
			return new ResultState(ResultState.SUCCESS,"执行成功",fileName);
		} catch (SQLException e) {
			errorLogger.error(e.getMessage());
			
			updateProcessInfo = new ProcessInfo();
			updateProcessInfo.setStatus(ProcessStatusEnum.EXECUTION_ERROR.getValue());
			updateProcessInfo.setEndTime("now");
			updateProcessInfo.setLogInfo("hive查询出错:"+e.getMessage());
			processInofoService.update(updateProcessInfo);
			
			return new ResultState(ResultState.FAILURE,"hive查询出错:"+e.getMessage(),null);
		} catch (IOException e) {
			errorLogger.error(e.getMessage());
			
			updateProcessInfo = new ProcessInfo();
			updateProcessInfo.setStatus(ProcessStatusEnum.EXECUTION_ERROR.getValue());
			updateProcessInfo.setEndTime("now");
			updateProcessInfo.setLogInfo("写入文件出错:"+e.getMessage());
			processInofoService.update(updateProcessInfo);
			
			return new ResultState(ResultState.FAILURE,"写入文件出错:"+e.getMessage(),null);
		}finally{
			if(hiveService!=null){
				hiveService.close();
			}
		}
	}
	
	/**
	 * 将hive的数据导入mysql，hive的select字段顺序必须与mysql的insert字段顺序一样
	 * @param hsql：hive的select语句
	 * @param msql：mysql的insert语句
	 * @throws SQLException
	 * @throws IOException
	 */
	public boolean hive2mysql(String hsql, String msql) throws SQLException, IOException {
		logger.info("导入数据到mysql：");
		logger.info("mysql:" + msql);
		logger.info("hsql:" + hsql);
		if (conn == null || conn.isClosed()) {
			errorLogger.error("connection is null or closed!");
			return false;
		}
		conn.setAutoCommit(false); 
		PreparedStatement mpst = (PreparedStatement) conn.prepareStatement(msql);

		HiveJdbcService hiveService = SpringBeanUtils.getBean("hiveJdbcService", HiveJdbcService.class);
		Connection hConn = hiveService.getConnection();
		
		PreparedStatement hPstmt = hConn.prepareStatement(hsql);
		ResultSet hRs = null;
		try {
			hRs = hPstmt.executeQuery();
			int count = hRs.getMetaData().getColumnCount();
			while (hRs.next()) {
				for (int i = 1; i <= count; i++) {
					try {
						Object value = hRs.getObject(i);
						mpst.setString(i, value.toString());
					} catch (SQLException e) {
						errorLogger.error(e.getMessage());
					}
				}
				mpst.addBatch();
			}
			mpst.executeBatch();
			conn.commit();
		} catch (SQLException e) {
			errorLogger.error("could not execute sql=<" + hsql + ">: " + e.getMessage());
			throw new SQLException(e);
		} finally {
			if (hRs != null){
				hRs.close();
			}
			if (hPstmt != null){
				hPstmt.close();
			}
			if(mpst != null){
				mpst.close();
			}
			if(hiveService != null){
				hiveService.close();
			}
		}
		return true;
	}
	
	public static PrintWriter getPrint(String path) throws IOException {
		OutputStream os = new FileOutputStream(path, true);
		return new PrintWriter(new OutputStreamWriter(os, "UTF-8"), true);
	}
}