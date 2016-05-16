package com.boyaa.servlet.brush;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.boyaa.base.utils.DateUtil;
import com.boyaa.base.utils.JSONUtil;
import com.boyaa.entity.Brush;
import com.boyaa.mf.constants.Constants;
import com.boyaa.mf.entity.task.AutoRunning;
import com.boyaa.mf.entity.task.ProcessInfo;
import com.boyaa.mf.entity.task.ProcessTypeEnum;
import com.boyaa.mf.entity.task.Task;
import com.boyaa.mf.service.task.AutoRunningService;
import com.boyaa.mf.service.task.AutorunQueue;
import com.boyaa.mf.service.task.DownloadService;
import com.boyaa.mf.service.task.ProcessInfoService;
import com.boyaa.mf.service.task.ProcessQueue;
import com.boyaa.mf.service.task.TaskService;
import com.boyaa.mf.util.CommonUtil;
import com.boyaa.mf.util.HbaseProcess;
import com.boyaa.mf.util.HiveUtils;
import com.boyaa.mf.util.ServletUtil;
import com.boyaa.mf.util.SpringBeanUtils;
import com.boyaa.service.BrushService;
import com.boyaa.servlet.ResultState;

/**
 * 非主要IP地区数据导出
 * 
 * @author Jack
 * 
 */
@SuppressWarnings("all")
public class BrushServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(BrushServlet.class);
	static Logger errorLogger = Logger.getLogger("errorLogger");
	static Logger fatalLogger = Logger.getLogger("fatalLogger");
	private static final String CONTENT_TYPE = "application/x-javascript";
	
	@Autowired
	private DownloadService downloadService;

	public void init() throws ServletException {

	}

	public void destroy() {
		super.destroy();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding(Constants.DEFAULT_ENCODING);
		response.setContentType(CONTENT_TYPE);
		response.setCharacterEncoding(Constants.DEFAULT_ENCODING);
		logger.info("request ip:" + ServletUtil.getRemortIP(request));
		String action = request.getParameter("action");
		if("importBrushUser".equals(action)){
			this.importBrushUser(request, response);
			return;
		}else if("findBrushUser".equals(action)){
			this.findBrushUser(request, response);
			return;
		}else if("mark".equals(action)){
			this.mark(request, response);
			return;
		}else if("remove".equals(action)){
			this.remove(request, response);
			return;
		}else if("exportData".equals(action)){
			exportData(request, response);
			return;
		}else if("markAll".equals(action)){
			markAll(request, response);
			return;
		}else if("down".equals(action)){
			String fileName = request.getParameter("fileName");
			downloadService.downloadLocalFile(fileName, request, response);
			return;
		}
	}
	
	/**
	 * 导出黑名单
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void exportData(HttpServletRequest request, HttpServletResponse response) throws IOException{
		BrushService brushService = null;
		try{
			brushService = new BrushService();
			Map<String,String> params = new HashMap<String,String>();
			if(StringUtils.isNotEmpty(request.getParameter("plat") )){
				params.put("plat", request.getParameter("plat").trim() );
				request.getSession().setAttribute("plat", request.getParameter("plat").trim());
			}
			if(StringUtils.isNotEmpty(request.getParameter("uid") )){
				params.put("uid", request.getParameter("uid").trim() );
				request.setAttribute("uid", request.getParameter("uid").trim());
			}
			if(StringUtils.isNotEmpty(request.getParameter("rstime") )){
				params.put("rstime", request.getParameter("rstime") );
				request.setAttribute("rstime", request.getParameter("rstime"));
			}
			if(StringUtils.isNotEmpty(request.getParameter("retime") )){
				params.put("retime", request.getParameter("retime") );
				request.setAttribute("retime", request.getParameter("retime"));
			}
			if(StringUtils.isNotBlank(request.getParameter("status"))){
				params.put("status", request.getParameter("status") );
				request.setAttribute("status", request.getParameter("status"));
			}
			
			String fileName = brushService.exportData(params);
			ResultState state = null;
			if(StringUtils.isNotBlank(fileName)){
				state = new ResultState(ResultState.SUCCESS, fileName);
			}else{
				state = new ResultState(ResultState.FAILURE, "导出失败");
			}
			
			ServletUtil.responseRecords(response, null, state);
			return;
		}catch(Exception e){
			errorLogger.error(e.getMessage());
		}finally{
			if(brushService != null) brushService.close();
		}
	}
	
	/**
	 *反刷分用户查询
	 */
	public void findBrushUser(HttpServletRequest request, HttpServletResponse response) throws IOException{
		BrushService brushService = null;
		try{
			brushService = new BrushService();
			Map<String,String> params = new HashMap<String,String>();
			if(StringUtils.isNotEmpty(request.getParameter("plat") )){
				params.put("plat", request.getParameter("plat").trim() );
				request.getSession().setAttribute("plat", request.getParameter("plat").trim());
			}
			if(StringUtils.isNotEmpty(request.getParameter("uid") )){
				params.put("uid", request.getParameter("uid").trim() );
				request.setAttribute("uid", request.getParameter("uid").trim());
			}
			if(StringUtils.isNotEmpty(request.getParameter("status") )){
				params.put("status", request.getParameter("status").trim() );
				request.setAttribute("status", request.getParameter("status").trim());
			}
			if(StringUtils.isNotEmpty(request.getParameter("rstime") )){
				params.put("rstime", request.getParameter("rstime") );
				request.setAttribute("rstime", request.getParameter("rstime"));
			}
			if(StringUtils.isNotEmpty(request.getParameter("retime") )){
				params.put("retime", request.getParameter("retime") );
				request.setAttribute("retime", request.getParameter("retime"));
			}
			
			 String sEcho = request.getParameter("sEcho");
		    int iDisplayStart =Integer.parseInt(request.getParameter("iDisplayStart"));
		    int iDisplayLength = Integer.parseInt(request.getParameter("iDisplayLength"));
		    
		     JSONArray brushs = brushService.findByPage(iDisplayStart, iDisplayLength, params);
	        int count =brushService.count(iDisplayStart, iDisplayLength, params);
	        
	        JSONObject map = JSONUtil.getJSONObject();
	          
	        map.put("data", brushs);
	        map.put("iTotalRecords",count);
	        map.put("iTotalDisplayRecords", count);
	        map.put("sEcho",sEcho);
	        
	        response.getWriter().write(map.toString());
	           
		}catch(Exception e){
			errorLogger.error(e.getMessage());
		}finally{
			if(brushService != null) brushService.close();
		}
	}
	
	/**
	 * 标记该用户
	 */
	public void mark(HttpServletRequest request, HttpServletResponse response) throws IOException{
		BrushService brushService = null;
		try{
			brushService = new BrushService();
			int id = Integer.valueOf(request.getParameter("id").toString());
			Brush brush = brushService.query(id);
			if(brush != null){
				brush.setStatus("1");
				brushService.updateStatus(brush);
			}
			
			String sEcho = request.getParameter("sEcho");
		    int iDisplayStart =Integer.parseInt(request.getParameter("iDisplayStart"));
		    int iDisplayLength = Integer.parseInt(request.getParameter("iDisplayLength"));
			request.setAttribute("sEcho", sEcho);
			request.setAttribute("iDisplayStart", iDisplayStart);
			request.setAttribute("iDisplayLength", iDisplayLength);
			return;
		}catch(Exception e){
			errorLogger.error(e.getMessage());
		}finally{
			if(brushService != null) brushService.close();
		}
	}
	/**
	 * 标记所有
	 */
	public void markAll(HttpServletRequest request, HttpServletResponse response) throws IOException{
		BrushService brushService = null;
		try{
			brushService = new BrushService();
			brushService.markAll();
			return;
		}catch(Exception e){
			errorLogger.error(e.getMessage());
		}finally{
			if(brushService != null) brushService.close();
		}
	}
	
	/**
	 * 移除标记
	 */
	public void remove(HttpServletRequest request, HttpServletResponse response) throws IOException{
		BrushService brushService = null;
		try{
			brushService = new BrushService();
			int id = Integer.valueOf(request.getParameter("id").toString());
			Brush brush = brushService.query(id);
			if(brush != null){
				brush.setStatus("0");
				brushService.updateStatus(brush);
			}
			
			/*request.getRequestDispatcher("/log/brushData.jsp").forward(request, response);*/
			return ;
		}catch(Exception e){
			errorLogger.error(e.getMessage());
			
		}finally{
			if(brushService != null) brushService.close();
		}
	}
	
	private void importBrushUser(HttpServletRequest request, HttpServletResponse response) throws IOException{
		//获取用户的基本信息
		String userId = (String) request.getSession().getAttribute("code");
		String userName = (String) request.getSession().getAttribute("cname");
		String plat = request.getParameter("plat");
		String sid = request.getParameter("sid");
		String bpid = request.getParameter("bpid");
		String taskname = plat + "_"+ sid + "_反刷分_";
		String rstime = request.getParameter("rstime");
		String retime = request.getParameter("retime");
		String lstime = request.getParameter("lstime");
		String letime = request.getParameter("letime");
		String smfcount = request.getParameter("smfcount");
		String emfcount = request.getParameter("emfcount");
		String smexplevel = request.getParameter("smexplevel");
		String emexplevel = request.getParameter("emexplevel");
		String email_suffix = request.getParameter("emails");
		String loginip = request.getParameter("loginip");
		String smentercount = request.getParameter("smentercount");
		String ementercount = request.getParameter("ementercount");
		String lang = request.getParameter("lang");
		String smmoney = request.getParameter("smmoney");
		String emmoney = request.getParameter("emmoney");
		String svmoney = request.getParameter("svmoney");
		String evmoney = request.getParameter("evmoney");
		String swincount = request.getParameter("swincount");
		String ewincount = request.getParameter("ewincount");
		String smpcount = request.getParameter("smpcount");
		String empcount = request.getParameter("empcount");
		String s2gRate = request.getParameter("s2gRate");
		String e2gRate = request.getParameter("e2gRate");
		String splayRate = request.getParameter("splayRate");
		String eplayRate = request.getParameter("eplayRate");
		String smfree = request.getParameter("smfree");
		String emfree = request.getParameter("emfree");
		String mpay = request.getParameter("mpay");
		String mhasicon = request.getParameter("mhasicon");
		String allRegions = request.getParameter("allRegions");//所有最后登录到地区
		String allLangs = request.getParameter("allLangs");//所有语言
		String allEmails = request.getParameter("allEmails");//所有邮箱
		String loginipRegions = request.getParameter("loginipRegions");
		String stime = request.getParameter("stime");//选择的时间类型
		String templateId = request.getParameter("templateId");
		taskname += request.getParameter("templateName");
		String autorun = request.getParameter("autorun");
		String addType = request.getParameter("addType");
		boolean ifAutorun = false;
		if("true".equals(autorun)){
			ifAutorun = true;
		}
		String items = "mnick,mfcount,mexplevel,lang,maxOwnChips,mmoney,mfree,vmoney,playnormal,playunnormal,mtime,mactivetime,mprivilege,mentercount,winCount,loseCount,mpay,regip,mhasicon,inc_mpc,inc_2mpc,loginip,fullEmail,inc_gc_s_374,inc_gc_c_374,inc_gc_s_248,inc_gc_c_248,inc_gc_s_2,inc_gc_c_2,inc_gc_s_32,inc_gc_c_32,inc_gc_s_33,inc_gc_c_33,mpcount";
		//String itemsName = "用户名,好友数,等级,语言,最大持有游戏币,当前游戏币,免费游戏币,历史台费,正常牌局数,非正常牌局数,注册时间,最后登录时间,是否粉丝,历史登录次数,赢牌局数,输牌局数,付费总额,注册IP,是否有头像,总牌局数（新）,2人牌局数（新）,最后登录IP,完整邮箱,移动好友赠送筹码数量,移动好友赠送筹码次数,移动每日任务领取游戏币数量,移动每日任务领取游戏币次数,四叶草筹码数量,四叶草领取次数,幸运筹码数量,幸运筹码领取次数,粉丝奖筹码数量,粉丝奖领取次数,总局数";
		String itemsName = items;
		String ifPay = request.getParameter("ifPay");
		ResultState resultState = null;
		TaskService taskService = SpringBeanUtils.getBean("taskService",TaskService.class);
		try {
			String email = (String) request.getSession().getAttribute("email");
			ResultState failure = new ResultState(ResultState.FAILURE);
			Task task = new Task();
			task.setTaskName(taskname);
			task.setUserId(userId);
			task.setUserName(userName);
			task.setEmail(email);
			if("true".equals(autorun)){
				task.setType(Constants.TASK_TYPE_AUTORUN);
			}
			task = taskService.add(task);
	
			if (task == null) {
				failure.setMsg("添加任务失败");
				ServletUtil.responseRecords(response, null, failure);
				return;
			}
			
			ProcessInfo hiveProcess = null;
			String hiveSql = "";
			ProcessInfoService processInfoService = SpringBeanUtils.getBean("processInfoService",ProcessInfoService.class);
			if((StringUtils.isNotEmpty(rstime) && StringUtils.isNotEmpty(retime)) || ifAutorun) {
				if(StringUtils.isBlank(templateId)){
					templateId = "0";
				}
			
				if("registerTime".equals(stime)){
					hiveSql = "select #plat#,`_uid`," + templateId + "," + Constants.BRUSH_TYPE_SINGUP + 
							" from user_signup where tm between #startTime# and #endTime# and `_bpid` in(#bpid#) group by `_uid`";
				}else{
					hiveSql = "select #plat#,`_uid`," + templateId + "," + Constants.BRUSH_TYPE_HUOYUE + 
							" from user_uid where plat=#plat# and tm between #startTime# and #endTime# and `_bpid` in(#bpid#) group by `_uid`";
				}
				
				if(!"true".equals(autorun)){
					hiveSql = hiveSql.replaceAll("#startTime#", DateUtil.convertPattern(rstime, "yyyy-MM-dd", "yyyyMMdd"))
							.replaceAll("#endTime#", DateUtil.convertPattern(retime, "yyyy-MM-dd", "yyyyMMdd"));
				}
				
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
				hiveProcess.setColumnName("_plat,_uid,template_id,type");
				//hiveProcess.setTitle("平台id,用户id,好友赠送(1),四叶草获得(2),移动每日登入(5),幸运筹码(32),粉丝奖励(33),移动每日任务(248),百宝箱(614),移动好友赠送筹码(374),在线领取(728),移动邀请好友(789)");
				hiveProcess.setTitle("plat,uid,template_id,type");
				hiveProcess = processInfoService.add(hiveProcess);
			
				ProcessInfo hbaseProcess = null;
				if(StringUtils.isNotEmpty(items)){
					if("true".equals(ifPay)){

					}else if("false".equals(ifPay)){

					}
					HbaseProcess hp = new HbaseProcess();
					hbaseProcess = hp.createProcess(items, itemsName, hiveProcess, task.getId(),null,true,4);
				}
				
				ProcessInfo resultProcess = new ProcessInfo();
				if(hbaseProcess != null){
					String columns = hbaseProcess.getColumnName();
					String title = hbaseProcess.getTitle();
					String resultSql = "select " + HiveUtils.columsSpecialSymbols(columns) + " from (select " + HiveUtils.columsSpecialSymbols(columns) + " from " + Constants.tempTableName + " where 1=1";
					
					StringBuffer condition = new StringBuffer();
					genCondition(condition,"mfcount",smfcount,emfcount);
					genCondition(condition,"mexplevel",smexplevel,emexplevel);
					genCondition(condition,"mmoney",smmoney,emmoney);
					genCondition(condition,"vmoney",svmoney,evmoney);
					genCondition(condition,"wincount",swincount,ewincount);
					genCondition(condition,"mpcount",smpcount,empcount);
					genCondition(condition,"mfree",smfree,emfree);
					genCondition(condition,"wincount/mpcount",splayRate,eplayRate);
					genCondition(condition,"inc_2mpc/inc_mpc",s2gRate,e2gRate);
					if("true".equals(mpay)){
						mpay = "0.01";
						genCondition(condition,"mpay",mpay,null);
					}else if("false".equals(mpay)){
						mpay = "0";
						genEqualCondi(condition,"mpay",mpay);
					}
					
					genContainCondi(condition,"email",email_suffix,allEmails,true);
					genContainCondi(condition,"lang",lang,allLangs,false);
					genContainCondi(condition,"loginipRegion",loginipRegions,allRegions,false);
					if(StringUtils.isNotBlank(mhasicon) && !"all".equals(mhasicon)){
						genEqualCondi(condition,"mhasicon",mhasicon);
					}
					resultSql += condition.toString() + ")t0";
					
					StringBuffer subSql = new StringBuffer(); 
					String ip = "#loginip#";
					if(StringUtils.isNotBlank(loginip)){
						if("1".equals(loginip)){
							ip = "regexp_extract(#loginip#,\"(\\\\\\\\d+\\\\\\\\.)(\\\\\\\\d+\\\\\\\\.\\\\\\\\d+\\\\\\\\.\\\\\\\\d+)\",1)";
						}else if("2".equals(loginip)){
							ip = "regexp_extract(#loginip#,\"(\\\\\\\\d+\\\\\\\\.\\\\\\\\d+\\\\\\\\.)(\\\\\\\\d+\\\\\\\\.\\\\\\\\d+)\",1)";
						}else if("3".equals(loginip)){
							ip = "regexp_extract(#loginip#,\"(\\\\\\\\d+\\\\\\\\.\\\\\\\\d+\\\\\\\\.\\\\\\\\d+\\\\\\\\.)(\\\\\\\\d+)\",1)";
						}else{
							ip = "#loginip#";
						}
					}
					
					if(CommonUtil.isNum(smentercount)){
						subSql.append(" left join (select " + ip.replace("#loginip#", "loginip") + " ip,count(1) countip from " + 
								Constants.tempTableName + " group by " + ip.replace("#loginip#", "loginip") +
								")t1 on(" + ip.replace("#loginip#", "t0.loginip") + "=" + "t1.ip" + ") where 1=1");
						StringBuffer con = new StringBuffer();
						this.genCondition(con, "countip", smentercount, ementercount);
						subSql.append(con.toString());
					}
					
					if(subSql.length() > 0){
						resultSql += subSql.toString();
					}
					
					resultProcess.setType(ProcessTypeEnum.HIVE_QUERY.getValue());
					resultProcess.setOperation(resultSql);
					resultProcess.setTaskId(task.getId());
					resultProcess.setColumnName(columns);
					resultProcess.setTitle(title);
					resultProcess.setDependId(hbaseProcess.getId());
					resultProcess.setPreTempTable(1);
					resultProcess = processInfoService.add(resultProcess);
				}
				
				if(hbaseProcess != null){
					ProcessInfo brushProcess = new ProcessInfo();
					brushProcess.setType(ProcessTypeEnum.CUSTOM_EXEC.getValue());
					brushProcess.setOperation("com.boyaa.service.BrushService|brushProcess");
					brushProcess.setTaskId(task.getId());
					brushProcess.setDependId(resultProcess.getId());
					brushProcess.setPreTempTable(1);
					brushProcess = processInfoService.add(brushProcess);
				}
				
				if(ifAutorun){
					AutoRunningService autoService = SpringBeanUtils.getBean("autoRunningService",AutoRunningService.class);
					if("replace".equals(addType)){
						AutoRunning aInfo = new AutoRunning();
						aInfo.setTaskId(task.getId());
						aInfo.setStatus(Constants.STATUS_UNEFFACTIVE);
						aInfo.setUid(Integer.parseInt(userId));
						aInfo.setTemplateId(Integer.parseInt(templateId));
						autoService.replaceAutorunInfo(aInfo);
					}
					AutoRunning autorunInfo = new AutoRunning();
					autorunInfo.setTaskId(task.getId());
					autorunInfo.setName(taskname);
					autorunInfo.setStatus(Constants.STATUS_EFFACTIVE);
					autorunInfo.setUid(Integer.parseInt(userId));
					autorunInfo.setUsername(userName);
					autorunInfo.setRunHour(Constants.RUN_HOUR_TIME);//默认执行时间
					autorunInfo.setTemplateId(Integer.parseInt(templateId));
					
					autorunInfo.setId(autoService.insert(autorunInfo));
					
					SpringBeanUtils.getBean("autorunQueue",AutorunQueue.class).offer(autorunInfo);
				}else{
					SpringBeanUtils.getBean("processQueue",ProcessQueue.class).offer(task.getId());	// 把该任务活加到处理队列中
				}
		
				resultState = new ResultState(ResultState.SUCCESS);
				resultState.setMsg("添加任务成功!");
			}else{
				resultState = new ResultState(ResultState.FAILURE);
				resultState.setMsg("请填写注册日期!");
			}
		}catch(Exception e) {
			errorLogger.error(e.getMessage());
			resultState = new ResultState(ResultState.FAILURE);
			resultState.setMsg("数据处理出错!");
		}
		ServletUtil.responseRecords(response, null, resultState);
	}
	
	private void genCondition(StringBuffer condition,String condiName,String startVal,String endVal){
		if(condition == null){
			condition = new StringBuffer();
		}
		if(CommonUtil.isNum(startVal)){
			if(CommonUtil.isNum(endVal)){
				if(Double.parseDouble(startVal) < Double.parseDouble(endVal)){
					condition.append(" and ").append(condiName).append(" between ").append(startVal).append(" and ").append(endVal);
				}
			}else{
				condition.append(" and ").append(condiName).append(">=").append(startVal);
			}
		}
	}
	
	private void genEqualCondi(StringBuffer condition,String condiName,String val){
		if(condition == null){
			condition = new StringBuffer();
		}
		if(StringUtils.isNotBlank(val)){
			condition.append(" and ").append(condiName).append("=\"").append(val).append("\"");
		}
	}
	
	/**
	 * 生成包含到条件
	 * @param condition 生成的条件
	 * @param condiName 字段名
	 * @param val 条件
	 * @param ifLike 是否使用模糊查询
	 */
	private void genContainCondi(StringBuffer condition,String condiName,String val,String allVal,boolean ifLike){
		if(condition == null){
			condition = new StringBuffer();
		}
		if(StringUtils.isNotBlank(val)){
			String[] vals = val.split(",");
			String[] allVals = allVal.split(",");
			if(val.length()>0){
				if(val.contains("other")){
					for(int i=0;i<allVals.length;i++){
						if(!val.contains(allVals[i])){
							if("null".equals(allVals[i])){
								condition.append(" and (").append(condiName).append(" is not null or trim(").append(condiName).append(")!=\"\" or ").append(condiName).append("!=\"0\")");
							}else if(ifLike){
								condition.append(" and lower(").append(condiName).append(") not like \"%").append(allVals[i].toLowerCase()).append("%\"");
							}else{
								condition.append(" and lower(").append(condiName).append(")!=\"").append(allVals[i].toLowerCase()).append("\"");
							}
						}
					}
				}else{
					condition.append(" and (");
					for(int i=0;i<vals.length;i++){
						if(i > 0){
							condition.append(" or ");
						}
						if("null".equals(vals[i])){
							condition.append(condiName + " is null ").append("or trim(").append(condiName).append(")=\"\" or ").append(condiName).append("=\"0\"");
						}else{
							if(ifLike){
								condition.append("lower(").append(condiName).append(") like \"%").append(vals[i].toLowerCase()).append("%\"");
							}else{
								condition.append("lower(").append(condiName).append(")=\"").append(vals[i].toLowerCase()).append("\"");
							}
						}
					}
					condition.append(") ");
				}
			}
		}
	}
}
