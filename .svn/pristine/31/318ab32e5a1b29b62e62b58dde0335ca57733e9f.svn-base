package com.boyaa.mf.web.controller.data;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.boyaa.base.utils.JSONUtil;
import com.boyaa.mf.constants.Constants;
import com.boyaa.mf.service.common.Query;
import com.boyaa.mf.service.data.BagService;
import com.boyaa.mf.util.ServletUtil;
import com.boyaa.mf.vo.LineChart;
import com.boyaa.mf.web.controller.BaseController;
import com.boyaa.mf.web.dto.AjaxObj;
import com.boyaa.servlet.ResultState;

@Controller
@RequestMapping("chart")
public class ChartController extends BaseController {
	static Logger logger = Logger.getLogger(ChartController.class);
	
	@Autowired
	private BagService bagService;
	
	@RequestMapping("initBag")
	@ResponseBody
	public AjaxObj initBag(){
		return new AjaxObj(AjaxObj.SUCCESS, "初始化成功",bagService.initChart().toString());
	}
	
	@RequestMapping("bagdata")
	@ResponseBody
	public AjaxObj bagdata(String startTime,String endTime,String showType){
		if (StringUtils.isBlank(startTime) || StringUtils.isBlank(endTime) || StringUtils.isBlank(showType)) {
			return new AjaxObj(AjaxObj.FAILURE,"传入参数不正确");
		}
		List<LineChart> lineCharts = bagService.chartData(startTime, endTime,showType);
		return new AjaxObj(AjaxObj.SUCCESS,"请求成功",lineCharts);
	}
	
	@RequestMapping("initOnlineCount")
	@ResponseBody
	public AjaxObj initOnlineCount(String plat){
		return new AjaxObj(AjaxObj.SUCCESS, "初始化成功",bagService.initOnlineCountChart(plat).toString());
	}
	
	@RequestMapping("onlineCountData")
	@ResponseBody
	public AjaxObj onlineCountData(String startTime,String endTime,String showType,String plat){
		if (StringUtils.isBlank(startTime) || StringUtils.isBlank(endTime) || StringUtils.isBlank(showType)) {
			return new AjaxObj(AjaxObj.FAILURE,"传入参数不正确");
		}

		List<LineChart> lineCharts = bagService.onlineCountChartData(startTime,endTime, showType, plat);
		return new AjaxObj(AjaxObj.SUCCESS,"请求成功",lineCharts);
	}
	
	@RequestMapping("pinglogIps")
	@ResponseBody
	public AjaxObj pinglogIps(){
		JSONArray ips = bagService.pinglogIps();
		return new AjaxObj(AjaxObj.SUCCESS,"请求成功",ips);
	}
	
	@RequestMapping("pingstalogIps")
	@ResponseBody
	public AjaxObj pingstalogIps(){
		JSONArray ips = bagService.pingstalogIps();
		return new AjaxObj(AjaxObj.SUCCESS,"请求成功",ips);
	}
	
	@RequestMapping("getPingData")
	@ResponseBody
	public void getPingData(HttpServletResponse response) throws IOException, JSONException, ParseException{
		String tableName = getRequest().getParameter(Constants.DEFAULT_TABLENAME_PARAM);
		String callback = getRequest().getParameter(Constants.DEFAULT_CALLBACK_NAME);
		ResultState data = null;

		try {
			// 保存请求地址中的参数名和值的映射，值的组成为：[value,
			// condition]，如果condition为null，则值为[value]
			// Map<String, String[]> params = new HashMap<String, String[]>();
			// 保存请求地址中的参数名和值的映射，如果参数名包含有condition，则会拆分成两个对象保存在json中，详见getParams函数
			JSONObject json = JSONUtil.getJSONObject();
			data = ServletUtil.getParams(getRequest(), json);
			if (data != null) {
				ServletUtil.responseRecords(response, callback, data);
				return;
			}
			Query query = new Query();
			
			// 获取表名
			query.setTableName(tableName);
			
			// 为请求参数设置一些默认值
			ServletUtil.setDefaultValue(getRequest(), json);

			data = query.scanRecords(json);
			ServletUtil.responseRecords(response, callback, data);
		} catch (JSONException e) {
			errorLogger.error(e.getMessage());
			data = new ResultState(ResultState.FAILURE, e.getMessage());
			ServletUtil.responseRecord(response, callback, data);
		} catch (ParseException e) {
			errorLogger.error(e.getMessage());
			data = new ResultState(ResultState.FAILURE, e.getMessage());
			ServletUtil.responseRecord(response, callback, data);
		}
	}
}