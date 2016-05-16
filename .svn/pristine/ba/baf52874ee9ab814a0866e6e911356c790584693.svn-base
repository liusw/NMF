package com.boyaa.service;

import com.alibaba.fastjson.JSONObject;
import com.boyaa.base.utils.CsvUtil;
import com.boyaa.base.utils.DateUtil;
import com.boyaa.dao.BaseDao;
import com.boyaa.dao.ChannelsDao;
import com.boyaa.dao.ChannelsDaoImpl;
import com.boyaa.entity.Channels;
import com.boyaa.mf.constants.Constants;
import com.boyaa.mf.entity.task.ProcessInfo;
import com.boyaa.mf.entity.task.ProcessStatusEnum;
import com.boyaa.mf.entity.task.ProcessTypeEnum;
import com.boyaa.mf.entity.task.Task;
import com.boyaa.mf.service.common.HiveJdbcService;
import com.boyaa.mf.service.task.ProcessInfoService;
import com.boyaa.mf.service.task.ProcessQueue;
import com.boyaa.mf.service.task.TaskService;
import com.boyaa.mf.util.HbaseProcess;
import com.boyaa.mf.util.HiveUtils;
import com.boyaa.mf.util.SpringBeanUtils;
import com.boyaa.servlet.ResultState;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 渠道商业务层
 */
public class ChannelsService extends BaseService<Channels> {
	static Logger logger = Logger.getLogger(ChannelsService.class);
	static Logger errorLogger = Logger.getLogger("errorLogger");
	static Logger fatalLogger = Logger.getLogger("fatalLogger");

	private ChannelsDao channelDao;

	public ChannelsService() {
		channelDao = new ChannelsDaoImpl();
	}

	@Override
	public BaseDao<Channels> getDao() {
		return channelDao;
	}

	/**
	 * @描述 : 渠道商数据监控处理
	 * @作者 : DarcyZeng
	 * @日期 : Dec 9, 2014
	 */
	public ResultState processChannels(ProcessInfo processInfo, JSONObject json) {
		Map<String,Object> obj = new HashMap<String,Object>();
		List<Integer> plats = channelDao.findPlats(obj);
		if (plats != null && plats.size() > 0) {
			importChannels();
			for (int plat : plats) {
				addChannelsTask(plat);
			}
		}
		return new ResultState(ResultState.SUCCESS,"执行成功",null);
	}
	
	/**
	 * @描述 : 将channels数据导入hive
	 * @作者 : DarcyZeng
	 * @日期 : Dec 24, 2014
	 */
	private void importChannels(){
		HiveMysqlService hmService = null;
		try {
			String msql = "select _plat,_uid,sid,level,higher from logcenter_mf.channels where del=0";
			hmService = new HiveMysqlService();
			hmService.mysql2hive("channels", msql, true);
		}catch(Exception e){
			errorLogger.error(e.getMessage());
		}finally{
			if(hmService != null){
				hmService.close();
			}
		}
	}

	/**
	 * @描述 : 添加渠道商任务
	 * @作者 : DarcyZeng
	 * @日期 : Dec 24, 2014
	 */
	private void addChannelsTask(int plat) {
		try {
			TaskService taskService = SpringBeanUtils.getBean("taskService",TaskService.class);
			Task task = new Task();
			task.setTaskName(plat + "_渠道商数据");
			task.setUserId("1307");
			task.setUserName("曾崇海");
			task.setEmail("DarcyZeng@boyaa.com");
			task = taskService.add(task);
			
			ProcessInfo hiveProcess = null;
			StringBuffer hiveSql = new StringBuffer();
			hiveSql.append("select sid,#plat#,t0.`_uid`,level,higher,wincoins0,wincoins1,lostcoins0,lostcoins1,wingames,lostgames,orderNum,sumCoins39,lastWeek1,lastWeek0,lastMon0,lastMon1,dealNum from")
			.append("(select `_uid`,sid,level,higher from channels where `_plat`=#plat# group by `_uid`,sid,level,higher)t0 left join")
			.append("(select `_uid`,sum(case when cgcoins>0 then cgcoins end) wincoins0,")
			.append("sum(case when cgcoins>0 and pcount=2 then cgcoins end) wincoins1,")
			.append("sum(case when cgcoins<0 then cgcoins end) lostcoins0,")
			.append("sum(case when cgcoins<0 and pcount=2 then cgcoins end) lostcoins1,")
			.append("count(case when cgcoins>0 and pcount=2 then 1 end) wingames,")
			.append("count(case when cgcoins<0 and pcount=2 then 1 end) lostgames")
			.append(" from user_gambling where plat=#plat# and tm between #startMon1# and #endMon1# group by `_uid`)t1 on(t0.`_uid`=t1.`_uid`) left join")
			.append("(select `_uid`,count(1) orderNum,")
			.append("sum(pchips) sumCoins39,")
			.append("count(case when tm between #startWeek1# and #endWeek1# then 1 end) lastWeek1,")
			.append("count(case when tm between #startWeek0# and #endWeek0# then 1 end) lastWeek0,")
			.append("count(case when tm between #startMon1# and #endMon1# then 1 end) lastMon1,")
			.append("count(case when tm between #startMon0# and #endMon0# then 1 end) lastMon0")
			.append(" from user_order2 where plat=#plat# and pmode=39 group by `_uid`)t2 on(t0.`_uid`=t2.`_uid`) left join")
			.append("(select `_uid`,count(1) dealNum from admin_act_logs where `_plat`=#plat# group by `_uid`)t3 on(t0.`_uid`=t3.`_uid`)");
			String hSql = hiveSql.toString();
			hSql = hSql.replaceAll("#plat#", Integer.toString(plat));
			hSql = hSql.replaceAll("#startMon1#", DateUtil.getFirstDayOfMonth(null, "yyyyMMdd", -1)).replaceAll("#endMon1#", DateUtil.getLastDayOfMonth(null, "yyyyMMdd", -1));
			hSql = hSql.replaceAll("#startMon0#", DateUtil.getFirstDayOfMonth(null, "yyyyMMdd", -2)).replaceAll("#endMon0#", DateUtil.getLastDayOfMonth(null, "yyyyMMdd", -2));
			hSql = hSql.replaceAll("#startWeek1#", DateUtil.getFirstDayOfWeek(null, "yyyyMMdd", -1)).replaceAll("#endWeek1#", DateUtil.getLastDayOfWeek(null, "yyyyMMdd", -1));
			hSql = hSql.replaceAll("#startWeek0#", DateUtil.getFirstDayOfWeek(null, "yyyyMMdd", -2)).replaceAll("#endWeek0#", DateUtil.getLastDayOfWeek(null, "yyyyMMdd", -2));
			ProcessInfoService processInfoService = new ProcessInfoService();
			hiveProcess = new ProcessInfo();
			hiveProcess.setType(ProcessTypeEnum.HIVE_QUERY.getValue());
			hiveProcess.setOperation(hSql);
			hiveProcess.setTaskId(task.getId());
			hiveProcess.setColumnName("sid,_plat,_uid,level,higher,wincoins0,wincoins1,lostcoins0,lostcoins1,wingames,lostgames,orderNum,sumCoins39,lastWeek1,lastWeek0,lastMon0,lastMon1,dealNum");
			hiveProcess.setTitle(hiveProcess.getTitle());
			hiveProcess = processInfoService.add(hiveProcess);
			
			HbaseProcess hp = new HbaseProcess();
			ProcessInfo hbaseProcess = hp.createProcess("mmoney,mbank,mfree,mactivetime", "mmoney,mbank,mfree,mactivetime", hiveProcess, task.getId());
			
			ProcessInfo cProcess = new ProcessInfo();
			cProcess.setType(ProcessTypeEnum.CUSTOM_EXEC.getValue());
			cProcess.setTaskId(task.getId());
			cProcess.setOperation("com.boyaa.service.ChannelsService|importToMysql");
			cProcess.setTitle(hbaseProcess.getTitle());
			cProcess.setColumnName(hbaseProcess.getColumnName());
			cProcess.setDependId(hbaseProcess.getId());
			processInfoService.add(cProcess);
			SpringBeanUtils.getBean("processQueue",ProcessQueue.class).offer(task.getId());	
		} catch (Exception e) {
			errorLogger.error(e.getMessage());
		}
	}
	
	/**
	 * @描述 : 数据导入到mysql
	 * @作者 : DarcyZeng
	 * @日期 : Dec 9, 2014
	 */
	public ResultState importToMysql(ProcessInfo processInfo, JSONObject json){
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
