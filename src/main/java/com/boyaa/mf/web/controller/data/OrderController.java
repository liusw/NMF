package com.boyaa.mf.web.controller.data;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.boyaa.base.utils.DateUtil;
import com.boyaa.base.utils.JSONUtil;
import com.boyaa.mf.constants.Constants;
import com.boyaa.mf.entity.data.PayDistribute;
import com.boyaa.mf.entity.data.ToolSale;
import com.boyaa.mf.entity.task.ProcessTypeEnum;
import com.boyaa.mf.service.data.PayDistributeService;
import com.boyaa.mf.service.data.ToolSaleService;
import com.boyaa.mf.service.task.TaskService;
import com.boyaa.mf.util.PageUtil;
import com.boyaa.mf.util.ServletUtil;
import com.boyaa.mf.util.SpringBeanUtils;
import com.boyaa.mf.vo.LoginUserInfo;
import com.boyaa.mf.web.controller.BaseController;
import com.boyaa.mf.web.dto.AjaxObj;
import com.boyaa.servlet.ResultState;

/** 
 * @author huangwx 
 * @version 创建时间：May 22, 2015 3:23:25 PM 
 */
@Controller
@RequestMapping(value="data/order")
public class OrderController extends BaseController{
	
	@Autowired
	private ToolSaleService service;
	@Autowired
	private PayDistributeService payDistributeService;
	@Autowired
	private TaskService taskService;
	
	@RequestMapping(value="toolSale")
	@ResponseBody
	public String getToolSaleList(String plat,String stm,String etm,String sid) throws ParseException{
		
		if(StringUtils.isBlank(plat) || StringUtils.isBlank(stm) || StringUtils.isBlank(etm) || StringUtils.isBlank(sid)){
			return getNullDataTable();
		}
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("plat", Integer.parseInt(plat));
		params.put("sid", Integer.parseInt(sid));
		params.put("stm", DateUtil.convertPattern(stm, "yyyy-MM-dd", "yyyyMMdd"));
		params.put("etm", DateUtil.convertPattern(etm, "yyyy-MM-dd", "yyyyMMdd"));
		PageUtil<ToolSale> page = service.getPageList(params);
		
		return getDataTableJson(page.getDatas(),page.getTotalRecord()).toJSONString();
	}
	
	@RequestMapping(value="payDistribute")
	@ResponseBody
	public AjaxObj getPayDistribute(String plat,String stm,String etm,String sid) throws ParseException{
		if (StringUtils.isBlank(plat) || StringUtils.isBlank(stm) || StringUtils.isBlank(etm) || StringUtils.isBlank(sid)) {
			return new AjaxObj(AjaxObj.FAILURE, "传入参数不正确");
		}
		Map<Object,Object> params = new HashMap<Object,Object>();
		params.put("plat", Integer.parseInt(plat));
		params.put("sid", Integer.parseInt(sid));
		params.put("stm", DateUtil.convertPattern(stm, "yyyy-MM-dd", "yyyyMMdd"));
		params.put("etm", DateUtil.convertPattern(etm, "yyyy-MM-dd", "yyyyMMdd"));
		PayDistribute distribute = payDistributeService.getPayDistribute(params);
		return new AjaxObj(AjaxObj.SUCCESS, "成功",distribute);
	}
	
	@RequestMapping(value="orderQuery")
	@ResponseBody
	public ResultState orderQuery(String plat,String sid,String startTime,String endTime,String payType,String firstPay,String ptype,String pcard,String bpid){
		String taskName = ServletUtil.getTaskName("订单导出", plat, sid + "_" + bpid, startTime, endTime);
		
		StringBuffer sb = new StringBuffer();
		sb.append(
				"select a.`_plat`,a.payDate,a.paytime,a.fsid,a.pid,a.`_uid`,a.pmoneynow,a.paymoney,a.pmode,a.pdealno,a.payresult,a.payip,a.isfirst,a.pchips,b.paycount,b.firstime,b.pamount,b.paysum,round((b.maxtime-b.firstime)/86400,2),c.ufrom,a.pcard from ")
				.append("(select `_plat`,from_unixtime(cast(`_tm` as bigint), \"yyyy-MM-dd\") payDate,from_unixtime(cast(`_tm` as bigint), \"HH:mm:ss\") paytime,fsid,pid,`_uid`,pmoneynow,round(cast(pamount as double)*cast(prate as double),2) paymoney,pmode,")
				// .append("pdealno,\"成功\" payresult,payip,case when lag(`_tm`,1) over (partition by `_uid` order by `_tm`) is null then \"是\" else \"否\" end isfirst,pchips from user_order ")
				//.append("pdealno,case when pstatus=2 then \"成功\" when pstatus=0 then \"未成功\" when pstatus=3 then \"退款\" else pstatus end payresult,payip,case when cast(pay_before as int)=0 then \"是\" else \"否\" end isfirst,pchips from user_order ")
				.append("pdealno,\"成功\" payresult,payip,case when cast(pay_before as int)=0 then \"是\" else \"否\" end isfirst,pchips,pcard from user_order2 ")
				.append("where 1=1 ");
		
		try {
			if (StringUtils.isNotBlank(startTime)
					&& StringUtils.isNotBlank(endTime)) {
				sb.append(" and tm between "
						+ DateUtil.convertPattern(startTime, DateUtil.DEFAULT_DATE_FORMAT, DateUtil.DATE_FORMAT)
						+ " and "
						+ DateUtil.addOneDay(endTime, DateUtil.DEFAULT_DATE_FORMAT, DateUtil.DATE_FORMAT));
				sb.append(" and from_unixtime(cast(`_tm` as bigint), \"yyyyMMdd\") between "
						+ DateUtil.convertPattern(startTime, DateUtil.DEFAULT_DATE_FORMAT, DateUtil.DATE_FORMAT)
						+ " and "
						+ DateUtil.convertPattern(endTime, DateUtil.DEFAULT_DATE_FORMAT, DateUtil.DATE_FORMAT));
			} else if (StringUtils.isBlank(startTime)
					|| StringUtils.isBlank(endTime)) {
				sb.append(" and tm="
						+ DateUtil.addDay(DateUtil.DATE_FORMAT, -1)
						+ " and "
						+ DateUtil.addOneDay(DateUtil.addDay(DateUtil.DATE_FORMAT, -1), DateUtil.DEFAULT_DATE_FORMAT, DateUtil.DATE_FORMAT));
				sb.append(" and from_unixtime(cast(`_tm` as bigint), \"yyyyMMdd\")="
						+ DateUtil.addDay(DateUtil.DATE_FORMAT, -1));
			}
		} catch (ParseException e) {
			errorLogger.error(e.getMessage());
		}

		sb.append(" and plat=" + plat + " and `_bpid` in(" + bpid + ") ").append(ptype2pcard(ptype));

		if (StringUtils.isNotBlank(payType) && Integer.parseInt(payType) >= 0) {
			sb.append(" and pmode= " + payType);
		}
		if(StringUtils.isNotBlank(pcard) && Integer.parseInt(pcard) >= 0){
			sb.append(" and pcard= " + pcard);
		}

		sb.append(") a ")
				.append("left outer join (select `_uid`,count(`_uid`) paycount,min(`_tm`) firstime,max(`_tm`) maxtime,sum(pamount) pamount,round(sum(cast(pamount as double)*cast(prate as double)),2) paysum from user_order2 ")
				.append("where plat="
						+ plat
						+ " and `_bpid` in("
						+ bpid
						+ ") group by `_uid`) b on(a.`_uid`=b.`_uid`) left outer join (select `_uid`,max(ufrom) ufrom from user_signup where `_plat`="
						+ plat + " and `_bpid` in(" + bpid
						+ ") group by `_uid`) c on(a.`_uid`=c.`_uid`)");

		if (StringUtils.isNotBlank(firstPay) && Integer.parseInt(firstPay) == 1) {
			sb.append(" where a.isfirst=\"是\" ");
		}

		String hiveSql = sb.toString();
		if(StringUtils.isEmpty(sid)){
			hiveSql = hiveSql.replaceAll(" and `_bpid` in\\(" + bpid + "\\)", "").replaceAll("sid in\\(" + sid + "\\)", "");
		}
		
		LoginUserInfo userInfo = getLoginUserInfo();
		
		JSONObject jsonObject = JSONUtil.getJSONObject();
		jsonObject.put("taskName", taskName);
		jsonObject.put("userId", userInfo.getCode());
		jsonObject.put("userName", userInfo.getRealName());
		jsonObject.put("receiveEmail", userInfo.getEmail());
		
		JSONArray jsonArray = JSONUtil.getJSONArray();
		//hive
		JSONObject processJson = JSONUtil.getJSONObject();
		processJson.put("tmpId", "p0");
		processJson.put("type", ProcessTypeEnum.HIVE_QUERY.getValue());
		processJson.put("operation", hiveSql.toString());
		jsonArray.add(processJson);
		//hbase
		JSONObject processJson2  = JSONUtil.getJSONObject();
		String fcolumn = "payDate,paytime,fsid,`_gid`,`_uid`,pmoneynow,mmoney,paymoney,pmode,pdealno,payresult,mnick,mpcount,paycount,payip,loginip,mgender,mtime,regip,isfirst,pamount,paysum,pchips,firstime,p1,ufrom,mactivetime,pcard,ipformat,regipFormat";
		String jsonStr = "{\"_tnm\":\"user_ucuser\","
				+ "\"retkey\":\"mmoney,mnick,mpcount,loginip,mgender,mtime,regip,mactivetime\","
				+ "\"rowkey\":\"false\","
				+ "\"file_column\":\"_plat,payDate,paytime,fsid,_gid,_uid,pmoneynow,paymoney,pmode,pdealno,payresult,payip,isfirst,pchips,paycount,firstime,pamount,paysum,p1,ufrom,pcard\","
				+ "\"output_column\":\"payDate,paytime,fsid,_gid,_uid,pmoneynow,mmoney,paymoney,pmode,pdealno,payresult,mnick,mpcount,paycount,payip,loginip,mgender,mtime,regip,isfirst,pamount,paysum,pchips,firstime,p1,ufrom,mactivetime,pcard\","
				+ "\"format\":{\"logintime\":\"yyyy-MM-dd\",\"mactivetime\":\"yyyy-MM-dd\"},\"ipformat\":\"loginip,regip\"}";
		processJson2.put("tmpId", "p1");
		processJson2.put("type", ProcessTypeEnum.HBASE_MULTI_QUERY.getValue());
		processJson2.put("operation",jsonStr);
		processJson2.put("columnName",fcolumn);
		processJson2.put("dependOn","p0");
		jsonArray.add(processJson2);
		//把数据load到hive再次处理
		JSONObject processJson3 = JSONUtil.getJSONObject();
		String title = "日期,时间,站点ID,订单号,下单者游戏id,当时游戏币,现有游戏币,卡型号,金额,支付方式,交易号,状态,用户名,牌局数,支付次数,支付周期,首次支付周期,支付IP,最後登錄IP,性別,註冊時間,註冊IP,是否首付,历史付费金额,折算金额,获得,注册来源,最後登錄時間,最後登錄国家,注册地区";
		String orderSql = "select payDate,paytime,fsid,`_gid`,`_uid`,pmoneynow,mmoney,"+pcardCaseWhen()+
				",paymoney,pmode,pdealno,payresult,mnick,mpcount,paycount,p1,round((cast(firstime as int)-cast(mtime as int))/86400,2),payip,loginip,mgender,from_unixtime(cast(mtime as bigint), \"yyyy-MM-dd\"),regip,isfirst,pamount,paysum,pchips,ufrom,mactivetime,ipformat,regipFormat from #tmpTable#";
		processJson3.put("tmpId", "p2");
		processJson3.put("type", ProcessTypeEnum.HIVE_QUERY.getValue());
		processJson3.put("operation",orderSql);
		processJson3.put("dependOn","p1");
		processJson3.put("preTempTable","1");
		processJson3.put("title",title);
		jsonArray.add(processJson3);
		
		jsonObject.put("process", jsonArray);
		
		return taskService.addTask(jsonObject);
	
	}
		
	private String ptype2pcard(String ptype){
		String where = "";
		try{
			if(StringUtils.isNotBlank(ptype) && Integer.parseInt(ptype)>=0){
				switch (Integer.parseInt(ptype)) {
				case 1:
					where = " and cast(pcard as int)<10 ";
					break;
				case 2:
					where = " and cast(pcard as int)>=10 and cast(pcard as int)<9910 ";
					break;
				case 3:
					where = " and cast(pcard as int)>=9910 and cast(pcard as int)!=9999 ";
					break;
				case 4:
					where = " and cast(pcard as int)=9999 ";
					break;
				}
			}
		}catch(NumberFormatException e){
			errorLogger.error(ptype+","+e.getMessage());
		}
		return where;
	}
	
	private String pcardCaseWhen(){
		StringBuilder sb = new StringBuilder();
		sb.append(" case when cast(pcard as int)=1 then \"铜牌会员\" ")
			.append(" when cast(pcard as int)=2 then \"银牌会员\"  ")
			.append(" when cast(pcard as int)=3 then \"金牌会员\"  ")
			.append(" when cast(pcard as int)=5 then \"钻石会员\"  ")
			.append(" when cast(pcard as int)=6 then \"白金会员\"  ")
			.append(" when cast(pcard as int)=7 then \"精英会员\"  ")
			.append(" when cast(pcard as int)=8 then \"66大礼包\"  ")
			.append(" when cast(pcard as int)=10 then \"紫钻\"  ")
			.append(" when cast(pcard as int)=12 then \"蓝钻\"  ")
			.append(" when cast(pcard as int)=13 then \"红钻\"  ")
			.append(" when cast(pcard as int)=15 then \"黄钻\"  ")
			.append(" when cast(pcard as int)=16 then \"花\"  ")
			.append(" when cast(pcard as int)=17 then \"束花\"  ")
			.append(" when cast(pcard as int)=18 then \"月钻\"  ")
			.append(" when cast(pcard as int)=19 then \"月钻*2\"  ")
			.append(" when cast(pcard as int)=20 then \"复活券\"  ")
			.append(" when cast(pcard as int)=21 then \"复活券*7\"  ")
			.append(" when cast(pcard as int)=22 then \"复活券*20\"  ")
			.append(" when cast(pcard as int)=38 then \"IFP1\"  ")
			.append(" when cast(pcard as int)=39 then \"IFP2\"  ")
			.append(" when cast(pcard as int)=9910 then \"(活)紫钻\"  ")
			.append(" when cast(pcard as int)=9912 then \"活)蓝钻\"  ")
			.append(" when cast(pcard as int)=9913 then \"(活)红钻\"  ")
			.append(" when cast(pcard as int)=9915 then \"(活)黄钻\"  ")
			.append(" when cast(pcard as int)=100000 then \"批发\" else pcard end ");
		return sb.toString();
	}
}
