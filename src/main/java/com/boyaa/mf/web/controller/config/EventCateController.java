package com.boyaa.mf.web.controller.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
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
import com.boyaa.mf.entity.config.EventCate;
import com.boyaa.mf.service.config.EventCateService;
import com.boyaa.mf.util.CommonUtil;
import com.boyaa.mf.util.PageUtil;
import com.boyaa.mf.web.controller.BaseController;

/**
 *<p>Title: 事件类型的接口类<p>
 *<p>Description:无</p>
 *<p>Company: boyaa</p>
 *<p>Date: May 12, 2015 9:43:53 AM</p>
 * @author Joakun Chen
 */
@Controller
@RequestMapping(value="config/eventCate")
public class EventCateController extends BaseController{
	private static Logger logger = Logger.getLogger(EventCateController.class);
	
	@Autowired
	private EventCateService eventCateService;
	
	/**
	 * 获取事件类型
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value="getData")
	@ResponseBody
	public String getEventCateData(String plat,String sSearch_1,String sSearch_2,String sSearch_3,String sSearch_5,String sSearch_6) throws IOException{
		Map<String, Object> params = new HashMap<String,Object>();
		if(StringUtils.isNotEmpty(plat)){
			params.put("plat", Long.parseLong(plat));
		}
		if(StringUtils.isNotBlank(sSearch_1)){
			params.put("likeId", sSearch_1);
		}
		if(StringUtils.isNotBlank(sSearch_2)){
			params.put("likeEname", sSearch_2);
		}
		if(StringUtils.isNotBlank(sSearch_3)){
			params.put("likeName", sSearch_3);
		}
		if(StringUtils.isNotBlank(sSearch_5)){
			if(-1 != Long.parseLong(sSearch_5)){
				params.put("likeSid", sSearch_5);
			}
		}
		if(StringUtils.isNotBlank(sSearch_6)){
			if(-1 != Integer.parseInt(sSearch_6)){
				params.put("likeStatus", sSearch_6);
			}
		}
		PageUtil<EventCate> datas = eventCateService.getPageList(params);
		return getDataTableJson(datas.getDatas(),datas.getTotalRecord()).toJSONString();
	}
	
	/**
	 * 编辑事件类型，包括添加，编辑及删除操作
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value="edit")
	@ResponseBody
	public void editEventCate(HttpServletRequest request, HttpServletResponse response) throws IOException{
		logger.info("Plat received the request of editing event category successfully.");
		
		JSONObject json = JSONUtil.getJSONObject();
		String action = request.getParameter("action");
		
		try {
			if("create".equalsIgnoreCase(action) || "edit".equalsIgnoreCase(action)|| "remove".equalsIgnoreCase(action)){
				if("remove".equalsIgnoreCase(action)){
					String[] ids = request.getParameterValues("id[]");
					
					int resultInt = -1;
					if(null != ids && ids.length > 0){
						for(int i=0;i<ids.length;i++){
							resultInt = eventCateService.delete(Integer.parseInt(ids[i]));
						}
					}
					
					json.put("result", resultInt>0?"操作成功":"操作失败");
					response.getWriter().write(json.toString());
					return;
				}
				
				String id = request.getParameter("data[id]");
				String ename = request.getParameter("data[ename]");
				String name = request.getParameter("data[name]");
				String plat = request.getParameter("data[plat]");
				String sid = request.getParameter("data[sid]");
				String status = request.getParameter("data[status]");
				
				/*检查传入参数完整性*/
				boolean paramCorrent = true;
				if("create".equalsIgnoreCase(action)){
					if(StringUtils.isEmpty(ename) || StringUtils.isEmpty(plat) || StringUtils.isEmpty(sid)){
						paramCorrent = false;
					}
				}else if("edit".equalsIgnoreCase(action)){
					if(StringUtils.isEmpty(id)){
						paramCorrent = false;
					}
				}
				
				if(!paramCorrent){
					json.put("error", "参数不正确");
					json.put("result", "操作失败");
					response.getWriter().write(json.toString());
					logger.warn("Parameters is not incorrect.");
					return;
				}
				
				EventCate eventCate = new EventCate();

				if(StringUtils.isNotBlank(ename)){
					eventCate.setEname(ename.trim());
				}
				
				if(StringUtils.isNotBlank(name)){
					eventCate.setName(name.trim());
				}
				
				if(StringUtils.isNotBlank(plat)){
					try {
						eventCate.setPlat(Integer.parseInt(plat.trim()));
					} catch (NumberFormatException e) {
						json.put("error", "平台ID必须为数字");
						json.put("result", "操作失败");
						response.getWriter().write(json.toString());
						logger.warn("Plat id " + plat + " is not a number.");
						return;
					}
				}
				
				if(StringUtils.isNotBlank(sid)){
					try {
						eventCate.setSid(Integer.parseInt(sid.trim()));
					} catch (NumberFormatException e) {
						json.put("error", "站点ID必须为数字");
						json.put("result", "操作失败");
						response.getWriter().write(json.toString());
						logger.warn("Site id " + sid + " is not a number.");
						return;
					}
				}
				
				if(StringUtils.isNotBlank(id)){
					eventCate.setId(Integer.parseInt(String.valueOf(id)));
				}
				
				eventCate.setStatus(Integer.parseInt(status));
				
				int resultInt = -1;
				if("create".equalsIgnoreCase(action)){
					resultInt = eventCateService.insert(eventCate);
				}else if("edit".equalsIgnoreCase(action)){
					resultInt = eventCateService.update(eventCate);
				}
				
				if(resultInt > 0){
					json.put("result", "操作成功");
				}else{
					json.put("error", "数据库操作异常");
					json.put("result", "操作失败");
				}				
				response.getWriter().write(json.toString());
			}
		} catch (Exception e) {
			try {
				json.put("error", e.getMessage());
				json.put("result", "操作失败");
				response.getWriter().write(json.toString());
			} catch (JSONException e1) {
				logger.warn(e1);;
			}
			errorLogger.error("The operation of " +  action + "ing event category failed:",e);
		}
		
	}
	
	/**
	 * 获取给定站点下所有事件类型（事件类型实体里只包含ID及英文名称）
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value="getDataBySid")
	@ResponseBody
	public void getBySid(HttpServletRequest request, HttpServletResponse response) throws IOException{
		logger.info("后台接收到获取站点下所有事件类型的请求");
				
		JSONObject json = JSONUtil.getJSONObject();
		
		Map<Object, Object> params = new HashMap<Object,Object>();
		
		try {
			String plat = request.getParameter("plat");
			String sid = request.getParameter("sid");
			
			if(StringUtils.isBlank(plat) || StringUtils.isBlank(sid)){
				json.put("error", "必须给定平台及站点参数");
				json.put("result", "操作失败");
				response.getWriter().write(json.toString());
				return;
			}
			
			params.put("plat", Long.parseLong(plat));
			params.put("sid", Long.parseLong(sid));

			List<EventCate> eventCates = eventCateService.findAllBySid(params);

			JSONArray josArray = JSONUtil.parseArray(CommonUtil.toJSONString(eventCates));
			
			json.put("data",josArray);
		} catch (Exception e) {
			errorLogger.error("获取站点下的事件类型失败:",e);
		}
		
		response.getWriter().write(json.toString());
	}
	
}
