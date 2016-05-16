package com.boyaa.mf.web.controller.data;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boyaa.base.utils.DateUtil;
import com.boyaa.mf.entity.data.LoginCoinsDistribute;
import com.boyaa.mf.entity.data.MonthRetainStat;
import com.boyaa.mf.entity.task.ProcessInfo;
import com.boyaa.mf.entity.task.ProcessTypeEnum;
import com.boyaa.mf.entity.task.Task;
import com.boyaa.mf.service.data.LoginCoinsService;
import com.boyaa.mf.service.data.MonthRetainStatService;
import com.boyaa.mf.service.task.ProcessInfoService;
import com.boyaa.mf.service.task.ProcessQueue;
import com.boyaa.mf.service.task.TaskService;
import com.boyaa.mf.util.HbaseProcess;
import com.boyaa.mf.util.PageUtil;
import com.boyaa.mf.util.ServletUtil;
import com.boyaa.mf.util.SpringBeanUtils;
import com.boyaa.mf.vo.LoginUserInfo;
import com.boyaa.mf.web.controller.BaseController;
import com.boyaa.servlet.ResultState;

/** 
 * @author huangwx 
 * @version 创建时间：May 27, 2015 2:38:51 PM 
 */
@Controller
@RequestMapping(value="data/login")
public class LoginController extends BaseController{
	static Logger logger = Logger.getLogger(LoginController.class);
	
	@Autowired
	private LoginCoinsService service;
	@Autowired
	private MonthRetainStatService monthRetainStatService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private ProcessInfoService processInfoService;
	@Autowired
	private ProcessQueue processQueue;
	
	
	@RequestMapping(value="coinsDistribute")
	@ResponseBody
	public String getCoinsDistributeList(String plat,String stm,String etm,String sid) throws ParseException{
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("plat", Integer.parseInt(plat));
		params.put("sid", Integer.parseInt(sid));
		params.put("stm", DateUtil.convertPattern(stm, "yyyy-MM-dd", "yyyyMMdd"));
		params.put("etm", DateUtil.convertPattern(etm, "yyyy-MM-dd", "yyyyMMdd"));
		PageUtil<LoginCoinsDistribute> page = service.getPageList(params);
		
		return getDataTableJson(page.getDatas(),page.getTotalRecord()).toJSONString();
	}
	
	@RequestMapping(value="monthRetainStats")
	@ResponseBody
	public String getMonthRetainStats(String plat,String stm,String etm,String sid) throws ParseException{
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("plat", Integer.parseInt(plat));
		params.put("sid", Integer.parseInt(sid));
		params.put("stm", DateUtil.convertPattern(stm, "yyyy-MM-dd", "yyyyMMdd"));
		params.put("etm", DateUtil.convertPattern(etm, "yyyy-MM-dd", "yyyyMMdd"));
		PageUtil<MonthRetainStat> page = monthRetainStatService.getPageList(params);
		return getDataTableJson(page.getDatas(),page.getTotalRecord()).toJSONString();
	}
	
	@RequestMapping(value="lostUserQuery")
	@ResponseBody
	public ResultState lostUserQuery(String plat,String sid,String bpid,String lstime,String letime,String loststime,String lostetime,
			String ifPay,String minPay,String items,String itemsName){
		
		String taskname = ServletUtil.getTaskName("流失用户数据导出", plat, sid + "_" + bpid, lstime, letime);
		
		if(StringUtils.isEmpty(minPay) || !StringUtils.isNumeric(minPay)){
			minPay = "0";
		}
		
		LoginUserInfo userInfo = getLoginUserInfo();
		
		try {
			Task task = new Task();
			task.setTaskName(taskname);
			task.setUserId(String.valueOf(userInfo.getCode()));
			task.setUserName(userInfo.getRealName());
			task.setEmail(userInfo.getEmail());
			task = taskService.add(task);
	
			if (task == null) {
				return new ResultState(ResultState.FAILURE,"添加任务失败");
			}
			
			ProcessInfo hiveProcess = null;
			StringBuffer tmpSql = null;
			processInfoService = SpringBeanUtils.getBean("processInofoService",ProcessInfoService.class);
			if(StringUtils.isNotEmpty(lstime) && StringUtils.isNotEmpty(letime)) {
				tmpSql = new StringBuffer();
				tmpSql.append("select #plat#,t0.`_uid`,t1.orderNum,t1.sumPamount,t2.bankruptNun1,t3.bankruptNun2,t4.lastOrderTm from"+ 
								//"(select `_uid`,max(tm) lastTm from user_login where plat=#plat# and tm>=#startTime# and `_bpid` in(#bpid#) group by `_uid` having max(tm) between #startTime# and #endTime1#)t0 left join"+
								"(select `_uid`,max(tm) lastTm from user_login where plat=#plat# and tm between #startTime# and #endTime1# and `_bpid` in(#bpid#) group by `_uid`)t0 left join"+
								"(select `_uid` from user_login where plat=#plat# and tm between #lostStartTime# and #lostEtartTime# and `_bpid` in(#bpid#) group by `_uid`)tx  on(t0.`_uid`=tx.`_uid`) left join"+
								"(select `_uid`,count(`_uid`) as orderNum,sum(cast(pamount as double)*cast(prate as double)) as sumPamount from user_order2 where plat=#plat# group by `_uid`)t1 on(t0.`_uid`=t1.`_uid`) left join"+
								"(select `_uid`,tm,count(1) as bankruptNun1 from bankrupt where tm between #startTime# and #endTime# and from_unixtime(cast(`_tm` as bigint), \"yyyyMMdd\") between #startTime# and #endTime1# and `_bpid` in(#bpid#) group by `_uid`,tm)t2 on(t0.`_uid`=t2.`_uid` and t2.tm=t0.lastTm) left join"+
								"(select `_uid`,count(1) as bankruptNun2 from bankrupt where `_bpid` in(#bpid#) group by `_uid`)t3 on(t0.`_uid`=t3.`_uid`) left join"+
								"(select `_uid`,from_unixtime(max(cast(`_tm` as bigint)), \"yyyy-MM-dd\") as lastOrderTm from user_order2 where plat=#plat# group by `_uid`)t4 on(t0.`_uid`=t4.`_uid`) where tx.`_uid` is null");
				
				if("true".equals(ifPay)){
					tmpSql.append(" and  sumPamount>=" + minPay);
				}else if("false".equals(ifPay)){
					tmpSql.append(" and (sumPamount=0 or sumPamount is null)");
				}
				
				String hiveSql = tmpSql.toString();
				hiveSql = hiveSql.replaceAll("#startTime#", DateUtil.convertPattern(lstime, DateUtil.DEFAULT_DATE_FORMAT, DateUtil.DATE_FORMAT))
						.replaceAll("#endTime#", DateUtil.addOneDay(letime, DateUtil.DEFAULT_DATE_FORMAT, DateUtil.DATE_FORMAT))
						.replaceAll("#endTime1#",DateUtil.convertPattern(letime, DateUtil.DEFAULT_DATE_FORMAT, DateUtil.DATE_FORMAT))
						.replaceAll("#lostStartTime#",DateUtil.convertPattern(loststime, DateUtil.DEFAULT_DATE_FORMAT, DateUtil.DATE_FORMAT))
						.replaceAll("#lostEtartTime#",DateUtil.convertPattern(lostetime, DateUtil.DEFAULT_DATE_FORMAT, DateUtil.DATE_FORMAT))
						;
				
				if(StringUtils.isEmpty(sid)){
					hiveSql = hiveSql.replaceAll("#plat#", plat).replaceAll("`_bpid` in\\(#bpid#\\)", "`_plat`=" + plat).replaceAll("sid in\\(#sid#\\)", "`_plat`=" + plat);
				}else{
					 hiveSql = hiveSql.replaceAll("#plat#", plat).replaceAll("#bpid#", bpid).replaceAll("#sid", sid);
				}
				
				logger.info(hiveSql);
				
				hiveProcess = new ProcessInfo();
				hiveProcess.setType(ProcessTypeEnum.HIVE_QUERY.getValue());
				hiveProcess.setOperation(hiveSql);
				hiveProcess.setTaskId(task.getId());
				hiveProcess.setColumnName("_plat,_uid,orderNum,sumPamount,bankruptNun1,bankruptNun2,lastOrderTm");
				hiveProcess.setTitle("平台id,用户id,历史付费次数,历史付费金额,流失当天破产次数,历史破产次数,历史最后付费时间");
				hiveProcess = processInfoService.add(hiveProcess);
			
				if(StringUtils.isNotEmpty(items)){
					HbaseProcess hp = new HbaseProcess();
					hp.createProcess(items, itemsName, hiveProcess, task.getId());
				}
				
				// 把该任务活加到处理队列中
				processQueue.offer(task.getId());
				return new ResultState(ResultState.SUCCESS,"添加任务成功");
			}
		}catch(Exception e) {
			errorLogger.error(e.getMessage());
			return new ResultState(ResultState.FAILURE,e.getMessage());
		}
		return new ResultState(ResultState.FAILURE,"添加任务失败");
	}
}
