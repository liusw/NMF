package com.boyaa.mf.web.controller.data;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.boyaa.base.utils.CsvUtil;
import com.boyaa.base.utils.JSONUtil;
import com.boyaa.entity.common.JSONResult;
import com.boyaa.mf.constants.Constants;
import com.boyaa.mf.util.JsonUtils;
import com.boyaa.mf.web.controller.BaseController;
import com.boyaa.service.CommonService;
import com.boyaa.servlet.ResultState;

/**
 * 通用的请求action
 *
 * @作者 : huangyineng
 * @日期 : 2015-5-20  下午3:47:01
 */
@SuppressWarnings("all")
@Controller
@RequestMapping(value="data/common")
public class CommonController  extends BaseController{
	static Logger logger = Logger.getLogger(CommonController.class);
	
	@RequestMapping(value="mysqlQuery")
	@ResponseBody
	public JSONResult mysqlQuery(String params,HttpServletResponse response) throws JSONException, IOException{
		
		if(StringUtils.isBlank(params)){
			return new JSONResult(Constants.RESULT_FAILURE,"参数为空",null,null);
		}
		JSONObject paramJson = JSONUtil.parseObject(params);
		
		if(paramJson==null || !paramJson.containsKey("id")){
			return new JSONResult(Constants.RESULT_FAILURE,"参数为空或ID不存在",null,null);
		}
		
		dealOrder(this.getRequest(), paramJson);
		
		if(paramJson.containsKey("args")){
			JSONObject args = paramJson.getJSONObject("args");
		
			Enumeration en = this.getRequest().getParameterNames();
			while (en.hasMoreElements()) {
				String el = en.nextElement().toString();
				if(el.startsWith("sSearch_") && StringUtils.isNotBlank(this.getRequest().getParameter(el))){
					args.put(el, this.getRequest().getParameter(el));
				}
			}
		}
		
		CommonService commonService = new CommonService();
		return commonService.mysqlQuery(paramJson,response);
	}
	
	@RequestMapping(value="download")
	@ResponseBody
	public void down(String params,Model model,HttpServletResponse response) throws IOException{
		if(StringUtils.isBlank(params)){
			errorPage("下载提示","传入参数不正确",model);
			return;
		}
		JSONObject paramJson = JSONUtil.parseObject(params);
		if(paramJson==null || !paramJson.containsKey("id")){
			errorPage("下载提示","传入参数不正确",model);
			return;
		}
		
	   String fileName = (String) JsonUtils.getValue(paramJson, "name", "")+"数据下载";
	   fileName = new String(fileName.getBytes("GB2312"), "ISO_8859_1");
		response.setContentType("application/octet-stream");
		response.setHeader("Content-disposition", "attachment;filename=\"" + fileName+".csv\"");
		
		CommonService commonService = new CommonService();
		
		OutputStream out = null;
		PrintWriter print = null;
		try {
			out = response.getOutputStream();
			print = CsvUtil.getPrint(out);
		
			JSONResult jsonResult = commonService.mysqlQuery(paramJson, response);
			if(jsonResult!=null && jsonResult.getLoop()!=null&&jsonResult.getResult()==1){
				List<Map<String,Object>> datas = (List<Map<String, Object>>) jsonResult.getLoop();
				if(datas!=null && datas.size()>0){
					
					String title = (String) JsonUtils.getValue(paramJson, "title", "");
					String column = (String) JsonUtils.getValue(paramJson, "column", "");
					List<String> columns = Arrays.asList(column.split(","));
					StringBuffer line = null;
					
					print.println(title);
					for(int i=0;i<datas.size();i++){
						Map<String,Object> map = datas.get(i);
						line = new StringBuffer();
						
						for(int j=0;j<columns.size();j++){
							String _c = columns.get(j);
							line.append(j==0?"":",").append(map.get(_c));
						}
						print.println(line.toString());
					}
				}
			}
			print.flush();
		} catch (Exception e) {
			errorLogger.error(e.getMessage());
		}finally{
			if(print!=null){
				print.close();
			}
			if(out!=null){
				out.close();
			}
		}
	}
	
	@RequestMapping(value="mysqlInsert")
	@ResponseBody
	public ResultState mysqlInsert(String params) throws IOException, JSONException{
		
		if(StringUtils.isBlank(params)){
			return new ResultState(ResultState.FAILURE,"参数为空");
		}
		JSONObject paramJson = JSONUtil.parseObject(params);
		
		if(paramJson==null || !paramJson.containsKey("id") || !paramJson.containsKey("args")){
			return new ResultState(ResultState.FAILURE,"参数为空或没添加参数");
		}
		
		JSONObject args = paramJson.getJSONObject("args");
		
		CommonService commonService = new CommonService();
		return commonService.insertMySQL(paramJson.getString("id"),JSONUtil.parseMap(args));
	}
	
	@RequestMapping(value="mysqlUpdate")
	@ResponseBody
	public ResultState mysqlUpdate(String params) throws IOException, JSONException{
		
		if(StringUtils.isBlank(params)){
			return new ResultState(ResultState.FAILURE,"参数为空");
		}
		JSONObject paramJson = JSONUtil.parseObject(params);
		
		if(paramJson==null || !paramJson.containsKey("id") || !paramJson.containsKey("args")){
			return new ResultState(ResultState.FAILURE,"参数为空或ID不存在或没更新参数");
		}
		
		JSONObject args = paramJson.getJSONObject("args");
		
		CommonService commonService = new CommonService();
		return commonService.updateMySQL(paramJson.getString("id"),JSONUtil.parseMap(args));
	}
	
	@RequestMapping(value="mysqlDelete")
	@ResponseBody
	public ResultState mysqlDelete(String params) throws IOException, JSONException{
		
		if(StringUtils.isBlank(params)){
			return new ResultState(ResultState.FAILURE,"参数为空");
		}
		JSONObject paramJson = JSONUtil.parseObject(params);
		
		if(paramJson==null || !paramJson.containsKey("id") || !paramJson.containsKey("args")){
			return new ResultState(ResultState.FAILURE,"参数为空或ID不存在或没更新参数");
		}
		
		JSONObject args = paramJson.getJSONObject("args");
		
		CommonService commonService = new CommonService();
		return commonService.deleteMySQL(paramJson.getString("id"),JSONUtil.parseMap(args));
	}
}