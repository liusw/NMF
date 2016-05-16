package com.boyaa.mf.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.boyaa.base.utils.JSONUtil;
import com.boyaa.mf.constants.Constants;
import com.boyaa.mf.support.DateEditor;
import com.boyaa.mf.util.CommonUtil;
import com.boyaa.mf.vo.LoginUserInfo;

public class BaseController {
	protected static Logger errorLogger = Logger.getLogger("errorLogger");
	protected static Logger fatalLogger = Logger.getLogger("fatalLogger");
	
	protected HttpServletRequest getRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}
	
	protected HttpServletResponse getResponse(){
		return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
	}

	protected HttpSession getSession() {
		return getRequest().getSession();
	}

	@InitBinder
	protected void initBinder(HttpServletRequest request,ServletRequestDataBinder binder) throws Exception {
		// 对于需要转换为Date类型的属性，使用DateEditor进行处理
		binder.registerCustomEditor(Date.class, new DateEditor());
	}

	/**
	 * 封装返回datatable的json格式数据
	 * @param datas
	 * @param totalRecord
	 * @return
	 */
	protected JSONObject getDataTableJson(List<?> datas,int totalRecord){
		JSONObject json = JSONUtil.getJSONObject();
		json.put("iTotalRecords", totalRecord);
		json.put("iTotalDisplayRecords", totalRecord);
		json.put("data", JSONUtil.parseArray(CommonUtil.toJSONString(datas==null?new ArrayList<String>():datas)));
		return json;
	}
	
	protected String getNullDataTable(){
		return getDataTableJson(null, 0).toJSONString();
	}
	
	protected JSONArray getJSONArray(List<?> datas) {
		return JSONUtil.parseArray(CommonUtil.toJSONString(datas==null?new ArrayList<String>():datas));
	}

	
	/**
	 * 获取登录用户信息
	 * @return
	 */
	protected LoginUserInfo getLoginUserInfo(){
		return (LoginUserInfo) getRequest().getSession().getAttribute(Constants.USER_LOGIN_SESSION_NAME);
	}
	
	protected String errorPage(String title, String msg,Model model) {
		model.addAttribute("title", title);
		model.addAttribute("info", msg);
		return "forward:/WEB-INF/jsp/common/error.jsp";
	}
	
	protected void dealOrder(HttpServletRequest request,JSONObject paramJson) throws JSONException{
		String sortIndex = request.getParameter("iSortCol_0");//排序的列索引
		if(StringUtils.isBlank(sortIndex)){
			return;
		}
		String sortColumn = request.getParameter("mDataProp_"+sortIndex);//排序的列索引
		if(StringUtils.isBlank(sortColumn)){
			return;
		}
		String order = request.getParameter("sSortDir_0");//排序的顺序
		if(StringUtils.isBlank(order)){
			order = "desc";
		}
		paramJson.put("sort", sortColumn);
		paramJson.put("order", order);

	}
}
