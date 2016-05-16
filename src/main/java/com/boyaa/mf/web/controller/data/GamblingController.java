package com.boyaa.mf.web.controller.data;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boyaa.base.utils.DateUtil;
import com.boyaa.mf.constants.Constants;
import com.boyaa.mf.entity.data.GamblingRank;
import com.boyaa.mf.entity.data.GamblingTimeDistribute;
import com.boyaa.mf.entity.data.GamblingType;
import com.boyaa.mf.entity.task.ProcessInfo;
import com.boyaa.mf.entity.task.ProcessTypeEnum;
import com.boyaa.mf.entity.task.Task;
import com.boyaa.mf.service.data.GamblingRankService;
import com.boyaa.mf.service.data.GamblingTimeDistributeService;
import com.boyaa.mf.service.data.GamblingTypeService;
import com.boyaa.mf.service.task.ProcessInfoService;
import com.boyaa.mf.service.task.ProcessQueue;
import com.boyaa.mf.service.task.TaskService;
import com.boyaa.mf.util.HbaseProcess;
import com.boyaa.mf.util.MyBatisUtil;
import com.boyaa.mf.util.PageUtil;
import com.boyaa.mf.util.ServletUtil;
import com.boyaa.mf.vo.LoginUserInfo;
import com.boyaa.mf.web.controller.BaseController;
import com.boyaa.mf.web.dto.AjaxObj;

/**
 * 盲注输赢情况数据导出
 * @author PeterLiao
 */

@Controller
@RequestMapping("data/gambling")
public class GamblingController extends BaseController {
	static Logger logger = Logger.getLogger(GamblingController.class);
	@Autowired
	private GamblingRankService service;
	@Autowired
	private GamblingTimeDistributeService distributeService;
	@Autowired
	private GamblingTypeService gamblingTypeService;
	@Autowired
	private ProcessInfoService processInfoService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private ProcessQueue processQueue;
	
	@RequestMapping(value="gblType")
	@ResponseBody
	public String getGblTypeData(String plat,String sid,String tableType,String stm,String etm){
		
		if(StringUtils.isBlank(plat) || StringUtils.isBlank(sid) || StringUtils.isBlank(tableType) || StringUtils.isBlank(stm) || StringUtils.isBlank(etm)){
			return getDataTableJson(null, 0).toJSONString();
		}
		
		String[] tableTypeArr = tableType.replaceAll("\"", "").split(",");
		List<String> tableTypes = Arrays.asList(tableTypeArr);
		
		Map<String, Object> params = new HashMap<String,Object>();
		params.put("plat", plat);
		params.put("sid", sid);
		params.put("tableTypes", tableTypes);
		params.put("startTime", stm.replaceAll("-", ""));
		params.put("endTime", etm.replaceAll("-", ""));
		
		PageUtil<GamblingType> page = gamblingTypeService.getPageList(params);

		return getDataTableJson(page.getDatas(),page.getTotalRecord()).toJSONString();
	}
	
	
	@RequestMapping(value="timeDistribute")
	@ResponseBody
	public String getGamblingTimeDistributeDatas(String plat,String stm,String etm,String sid,HttpServletResponse response) throws ParseException{
		if(StringUtils.isBlank(plat) || StringUtils.isBlank(stm) || StringUtils.isBlank(etm) || StringUtils.isBlank(sid)){
			return getDataTableJson(null, 0).toJSONString();
		}
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("plat", Integer.parseInt(plat));
		params.put("sid", Integer.parseInt(sid));
		params.put("stm", DateUtil.convertPattern(stm, "yyyy-MM-dd", "yyyyMMdd"));
		params.put("etm", DateUtil.convertPattern(etm, "yyyy-MM-dd", "yyyyMMdd"));
		PageUtil<com.boyaa.mf.entity.data.GamblingTimeDistribute> page = distributeService.getPageList(params);
		
		return getDataTableJson(page.getDatas(),page.getTotalRecord()).toJSONString();
	}
	
	@RequestMapping(value="exportTimeDistribute")
	@ResponseBody
	public void exportTime(String plat,String stm,String etm,String sid,HttpServletResponse response) throws IOException{

		if(StringUtils.isBlank(plat) || StringUtils.isBlank(stm) || StringUtils.isBlank(etm) || StringUtils.isBlank(sid)){
			response.getWriter().write("{}");
			return;
		}
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("plat", Integer.parseInt(plat));
		params.put("sid", Integer.parseInt(sid));
		try {
			params.put("stm", DateUtil.convertPattern(stm, "yyyy-MM-dd", "yyyyMMdd"));
			params.put("etm", DateUtil.convertPattern(etm, "yyyy-MM-dd", "yyyyMMdd"));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		List<GamblingTimeDistribute> list = distributeService.findScrollDataList(params);
		if(list!=null && list.size()>0){
			StringBuffer titleSb = new StringBuffer("日期,一小时以内,1-2小时,2-3小时,3-4小时,4-5小时,5-6小时,6-7小时,7-8小时,8-9小时,9-10小时,10-11小时,11-12小时,12小时以上");
			response.setContentType("application/octet-stream");
			// 设置response的头信息
			response.setHeader("Content-disposition", "attachment;filename=\"" + new Date().getTime() + ".csv\"");
			
			OutputStream out = null;
			OutputStreamWriter writer = null;
			PrintWriter print = null;
			try {
				out = response.getOutputStream();
				writer = new OutputStreamWriter(out, "UTF-8");
				print = new PrintWriter(writer);
				
				print.println(titleSb.toString());
				
				StringBuffer valueSb = null;
				
				for(GamblingTimeDistribute time:list){
					valueSb = new StringBuffer();
					valueSb.append(time.getTm()).append(",").append(time.getOneHourNum()).append(",").append(time.getTwoHoursNum()).append(",").append(time.getThreeHoursNum()).append(",").append(time.getFourHoursNum())
					.append(",").append(time.getFiveHoursNum()).append(",").append(time.getSixHoursNum()).append(",").append(time.getSevenHoursNum()).append(",").append(time.getEightHoursNum())
					.append(",").append(time.getNineHoursNum()).append(",").append(time.getTenHoursNum()).append(",").append(time.getElevenHoursNum()).append(",").append(time.getTwelveHoursNum()).append(",").append(time.getGreatTwelveNum());
					print.println(valueSb);
				}
				print.flush();
			} catch (Exception e) {
				errorLogger.error(e.getMessage());
			}finally{
				if(print!=null){
					print.close();
				}
				if(writer!=null){
					writer.close();
				}
				if(out!=null){
					out.close();
				}
			}
			return;
		}
		response.getWriter().write("无数据可导");
	}
	
	@RequestMapping(value="ranks")
	@ResponseBody
	public String getGamblingRanks(String tm) throws ParseException{
		if(StringUtils.isBlank(tm)){
			return getDataTableJson(null, 0).toJSONString();
		}
		
		Map<String,Object> params = new HashMap<String,Object>();
		String orderString = MyBatisUtil.getColumnPropertyMap(this.getRequest(), "GamblingRankMap");
		if(StringUtils.isNotBlank(orderString)){
			params.put("order", orderString);
		}
		params.put("tm", DateUtil.convertPattern(tm, "yyyy-MM-dd", "yyyyMMdd"));
		
		PageUtil<GamblingRank> page = service.getPageList(params);
		List<GamblingRank> result = page.getDatas();
		if(result!=null && result.size()>0){
			for(GamblingRank rank : result){
				rank.setIpStr(rank.getIp()+(StringUtils.isBlank(rank.getCountry())?"":("("+rank.getCountry()+")")));
			}
		}
		return getDataTableJson(result,page.getTotalRecord()).toJSONString();
	}
	
	@RequestMapping(value="exportRanks")
	@ResponseBody
	public void exportRanks(String tm,HttpServletResponse response) throws IOException, ParseException{
		if(StringUtils.isBlank(tm)){
			response.getWriter().write("{}");
			return;
		}
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("order", "order by yesterday_lose_chips asc");
		params.put("tm", DateUtil.convertPattern(tm, "yyyy-MM-dd", "yyyyMMdd"));

		List<GamblingRank> list = service.findScrollDataList(params);
		if(list!=null && list.size()>0){
			StringBuffer titleSb = new StringBuffer("玩家ID,总牌局数/非法牌局数,输筹码总量,赢筹码总量,上周总牌局数/上周非法牌局数,上周输筹码数,上周赢筹码数,上月总牌局数/上月非法牌局数,上月输筹码数,上月赢筹码数,IP");
			response.setContentType("application/octet-stream");
			// 设置response的头信息
			response.setHeader("Content-disposition", "attachment;filename=\"" + new Date().getTime() + ".csv\"");
			OutputStream out = null;
			OutputStreamWriter writer = null;
			PrintWriter print = null;
			try {
				out = response.getOutputStream();
				writer = new OutputStreamWriter(out, "UTF-8");
				print = new PrintWriter(writer);
				print.println(titleSb.toString());
				StringBuffer valueSb = null;
				for(GamblingRank rank : list){
					valueSb = new StringBuffer();
					valueSb.append(rank.getUid()).append(",").append(rank.getYesterdayGamblings()+"/"+rank.getYesterdayIllegalGamblings()).append(",").append(rank.getYesterdayLoseChips()).append(",").append(rank.getYesterdayWinChips())
					.append(",").append(rank.getLastWeekGamblings()+"/"+rank.getLastWeekIllegalGamblings()).append(",").append(rank.getLastWeekLoseChips()).append(",").append(rank.getLastWeekWinChips())
					.append(",").append(rank.getLastMonthGamblings()+"/"+rank.getLastMonthIllegalGamblings()).append(",").append(rank.getLastMonthLoseChips()).append(",").append(rank.getLastMonthWinChips())
					.append(",").append(rank.getIp()+(StringUtils.isBlank(rank.getCountry())?"":("("+rank.getCountry()+")")));
					print.println(valueSb);
				}
				print.flush();
			} catch (Exception e) {
				errorLogger.error(e.getMessage());
			}finally{
				if(print!=null){
					print.close();
				}
				if(writer!=null){
					writer.close();
				}
				if(out!=null){
					out.close();
				}
			}
		}else{
			response.getWriter().write("无数据可导");
		}
	}
	
	@RequestMapping(value="cgcoins")
	@ResponseBody
	public AjaxObj cgcoins(String plat,String sid,String bpid,String rstime,String retime,String pcount,
			String dataType,String gtype,String itemsName,String items,int rangeNum) throws Exception {//,String[] blindmin
		String taskname = ServletUtil.getTaskName("盲注输赢情况数据导出", plat, sid + "_" + bpid, null, null);
		String[] blindmins = (String[]) getRequest().getParameterMap().get("blindmin[]");
		
		List<String> ranges = new ArrayList<String>();
		if(rangeNum > 0){
			for(int i=0;i<rangeNum;i++){
				if(StringUtils.isNotEmpty(getRequest().getParameter("range"+(i+1)))){
					ranges.add(getRequest().getParameter("range"+(i+1)));	
				}
			}
		}
		StringBuffer colName = new StringBuffer("_uid,_plat");
		StringBuffer titleName = new StringBuffer("用户ID,平台");
		
		LoginUserInfo userInfo = (LoginUserInfo) getSession().getAttribute(Constants.USER_LOGIN_SESSION_NAME);
			
		Task task = new Task();
		task.setTaskName(taskname);
		task.setUserId(String.valueOf(userInfo.getCode()));
		task.setUserName(userInfo.getRealName());
		task.setEmail(userInfo.getEmail());
		task = taskService.add(task);

		if (task == null) {
			return new AjaxObj(AjaxObj.FAILURE,"添加任务失败");
		}
		
		ProcessInfo p1 = null;
		StringBuffer pre_sql = new StringBuffer();
		pre_sql.append("select `_uid`,plat"); 
		if(blindmins != null && StringUtils.isNotEmpty(dataType)){
			for(String blindmin : blindmins){
				if(dataType.contains("1")){
					pre_sql.append(",count(case when blindmin=")
					.append(Integer.valueOf(blindmin)).append(" then 1 end)");
					titleName.append(",盲注").append(blindmin).append("数量");
					colName.append(",blindmin").append(blindmin).append("num");
				}
				
				if(dataType.contains("2")){
					pre_sql.append(",sum(case when blindmin=")
					   .append(Integer.valueOf(blindmin)).append(" and cgcoins<0 then cgcoins end)");
				    titleName.append(",盲注").append(blindmin).append("输");
				    colName.append(",blindmin").append(blindmin).append("lose");
				}
				
				if(dataType.contains("3")){
					pre_sql.append(",sum(case when blindmin=")
					   .append(Integer.valueOf(blindmin)).append(" and cgcoins>0 then cgcoins end)");
					titleName.append(",盲注").append(blindmin).append("赢");
					colName.append(",blindmin").append(blindmin).append("win");
				}
				
				if(dataType.contains("4")){
					pre_sql.append(",sum(case when blindmin=")
					   .append(Integer.valueOf(blindmin)).append(" then vmoney end)");
					titleName.append(",台费");
					colName.append(",vmoney").append(blindmin);
				}
		    }
			
			
		}else if((blindmins==null || blindmins.length==0) && StringUtils.isNotEmpty(dataType)){
			if(dataType.contains("1")){
				pre_sql.append(",count(1)");
				titleName.append(",牌局数");
				colName.append(",pcountTotal");
			}
			
			if(dataType.contains("2")){
				pre_sql.append(",sum(case when cgcoins<0 then cgcoins end)");
			    titleName.append(",输局数");
			    colName.append(",pcountLose");
			}
			
			if(dataType.contains("3")){
				pre_sql.append(",sum(case when cgcoins>0 then cgcoins end)");
				titleName.append(",赢局数");
				colName.append(",pcountWin");
			}
			
			if(dataType.contains("4")){
				pre_sql.append(",sum(vmoney)");
				titleName.append(",总台费");
				colName.append(",vmoneyTotal");
			}
		}
		
		if(ranges.size()>0){
			for(String ran : ranges){
				String[] rans = ran.split("-");
				String rans0="";
				String rans1="";
				if(rans.length == 1){
					rans0=rans1=rans[0];
				}else{
					rans0 = rans[0];
					rans1 = rans[1];
				}
				
				if(StringUtils.isNotBlank(dataType)){
					if(dataType.contains("1")){
						pre_sql.append(",count(case  when blindmin between ")
						.append(rans0)
						.append(" and ")
						.append(rans1)
						.append(" then 1 end)");
						
						titleName.append(",盲注").append(ran).append("牌局数");
						colName.append(",blindmin").append(ran).append("pcount");
					}
					
					if(dataType.contains("2")){
						pre_sql.append(",sum(case when blindmin between ")
						   .append(Integer.valueOf(rans0))
						   .append(" and ")
						   .append(rans1)
						   .append(" and cgcoins<0 then cgcoins end)");
						titleName.append(",盲注").append(ran).append("输");
						colName.append(",blindmin").append(ran).append("lose");
					}
					
					if(dataType.contains("3")){
						pre_sql.append(",sum(case when blindmin between ")
						   .append(Integer.valueOf(rans0))
						   .append(" and ")
						   .append(rans1)
						   .append(" and cgcoins>0 then cgcoins end)");
						titleName.append(",盲注").append(ran).append("赢");
						colName.append(",blindmin").append(ran).append("win");
					}
					
					if(dataType.contains("4")){
						pre_sql.append(",sum(case when blindmin between ")
						   .append(Integer.valueOf(rans0))
						   .append(" and ")
						   .append(rans1)
						   .append(" then vmoney end)");
						titleName.append(",台费");
						colName.append(",vmoney").append(ran);
					}
				}
			}
		}
		
		pre_sql.append(" from user_gambling " )
			    .append("where plat=").append(plat);

		
		
		if(StringUtils.isNotEmpty(rstime) && StringUtils.isNotEmpty(retime)){
			 pre_sql .append(" and tm between ").append(rstime.replace("-", "")).append(" and ").append(retime.replace("-", ""));
		}else if(StringUtils.isNotEmpty(rstime) && StringUtils.isEmpty(retime)){
			 pre_sql .append(" and tm between ").append(rstime.replace("-", "")).append(" and ").append(rstime.replace("-", ""));
		}else if(StringUtils.isEmpty(rstime) && StringUtils.isNotEmpty(retime)){
			 pre_sql .append(" and tm between ").append(retime.replace("-", "")).append(" and ").append(retime.replace("-", ""));
		}
		
		if(StringUtils.isNotEmpty(sid)){
			pre_sql.append(" and sid in(").append(sid).append(")");
		}
		if(StringUtils.isNotEmpty(pcount)){
			pre_sql .append(" and pcount in(").append(pcount).append(")");
		}
		
		if(StringUtils.isNotEmpty(gtype)){
			pre_sql.append(" and gtype in(").append(gtype.toString()).append(")");
		}
		
		if(blindmins != null){
			pre_sql .append(" and ");
			if(ranges.size()>0){
				pre_sql .append("(");
			}
			pre_sql .append(" blindmin in(");
			for(String bli : blindmins){
				pre_sql.append(bli).append(",");
			}
			
			pre_sql = new StringBuffer(pre_sql.substring(0, pre_sql.length()-1));
			pre_sql.append(")");
		}
		
		if(ranges.size()>0){
			for(int i=0;i<ranges.size();i++){
				String[] rans = ranges.get(i).split("-");
				String rans0="";
				String rans1="";
				if(rans.length == 1){
					rans0=rans1=rans[0];
				}else{
					rans0 = rans[0];
					rans1 = rans[1];
				}
				if(blindmins == null ){
					if(ranges.size() == 1){
						pre_sql.append(" and blindmin between ").append(rans0).append(" and ").append(rans1);
						break;
					}
					
					if(i==0){
						pre_sql.append(" and (blindmin between ").append(rans0).append(" and ").append(rans1);
					}else if(i==ranges.size()-1){
						pre_sql.append(" or cast(blindmin as bigint) between ").append(rans0).append(" and ").append(rans1).append(")");
					}else{
						pre_sql.append(" or blindmin between ").append(rans0).append(" and ").append(rans1);
					}
				}else{
					if(i==ranges.size()-1){
						pre_sql.append(" or blindmin between ").append(rans0).append(" and ").append(rans1).append(")");
					}else{
						pre_sql.append(" or blindmin between ").append(rans0).append(" and ").append(rans1);
					}
				}
			}
			
		}
		pre_sql.append(" group by plat,`_uid`");
		
		p1 = new ProcessInfo();
		p1.setType(ProcessTypeEnum.HIVE_QUERY.getValue());
		p1.setOperation(pre_sql.toString());
		p1.setTaskId(task.getId());
		
		
		p1.setColumnName(colName.toString());
		p1.setTitle(titleName.toString());
		p1 = processInfoService.add(p1);
		
		logger.info(pre_sql.toString());
		
		if(StringUtils.isNotEmpty(items)){
			HbaseProcess hp = new HbaseProcess();
			hp.createProcess(items, itemsName, p1, task.getId(),null,false,0);
		}
		
		// 把该任务活加到处理队列中
		processQueue.offer(task.getId());
		
		return new AjaxObj(AjaxObj.SUCCESS,"添加任务成功");
	}
	
}
