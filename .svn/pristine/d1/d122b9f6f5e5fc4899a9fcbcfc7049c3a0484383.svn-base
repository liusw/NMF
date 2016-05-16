package com.boyaa.mf.web.controller.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.boyaa.base.utils.JSONUtil;
import com.boyaa.mf.entity.config.Event;
import com.boyaa.mf.service.config.EventCateService;
import com.boyaa.mf.service.config.EventService;
import com.boyaa.mf.util.PageUtil;
import com.boyaa.mf.web.controller.BaseController;

/**
 *<p>Title: 事件的接口类<p>
 *<p>Description: null</p>
 *<p>Company: boyaa</p>
 *<p>Date: May 12, 2015 11:21:16 AM</p>
 * @author Joakun Chen
 */
@Controller
@RequestMapping(value="config/event")
public class EventController extends BaseController{
	private static Logger logger = Logger.getLogger(EventController.class);
	
	@Autowired
	private EventService eventService;
	@Autowired
	private EventCateService eventCateService;
	
	/**
	 * 获取事件
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value="getData")
	@ResponseBody
	public String getEventData(String plat,String sSearch_1,String sSearch_2,String sSearch_3,String sSearch_4,String sSearch_6,String sSearch_7) throws IOException{
		logger.info("Plat received the request of getting event information successfully.");
		
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
		if(StringUtils.isNotBlank(sSearch_4)){
			/*检查事件ID是否存在*/
			params.put("likeEventCateId", sSearch_4);
		}
		if(StringUtils.isNotBlank(sSearch_6)){
			if(-1 != Long.parseLong(sSearch_6)){
				params.put("likeSid", sSearch_6);
			}
		}
		if(StringUtils.isNotBlank(sSearch_7)){
			if(-1 != Integer.parseInt(sSearch_7)){
				params.put("likeStatus", sSearch_7);
			}
		}
		
		PageUtil<Event> datas = eventService.getPageList(params);
		return getDataTableJson(datas.getDatas(),datas.getTotalRecord()).toJSONString();
	}
	
	/**
	 * 编辑事件，包括添加，编辑及删除操作
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value="edit")
	@ResponseBody
	public void editEvent(HttpServletRequest request, HttpServletResponse response) throws IOException{
		logger.info("Plat received the request of editing event successfully.");
		
		JSONObject json = JSONUtil.getJSONObject();
		String action = request.getParameter("action");
		
		try {
			
			if("create".equalsIgnoreCase(action) || "edit".equalsIgnoreCase(action)|| "remove".equalsIgnoreCase(action)){
				if("remove".equalsIgnoreCase(action)){
					String[] ids = request.getParameterValues("id[]");
					
					int resultInt = -1;
					if(null != ids && ids.length > 0){
						for(int i=0;i<ids.length;i++){
							resultInt = eventService.delete(Integer.parseInt(ids[i]));
						}
					}
					
					json.put("result", resultInt>0?"操作成功":"操作失败");
					response.getWriter().write(json.toString());
					return;
				}
				
				String id = request.getParameter("data[id]");
				String ename = request.getParameter("data[ename]");
				String name = request.getParameter("data[name]");
				String eventCateId = request.getParameter("data[eventCateId]");
				String plat = request.getParameter("data[plat]");
				String sid = request.getParameter("data[sid]");
				String status = request.getParameter("data[status]");
				
				/*检查传入参数完整性*/
				boolean paramCorrent = true;
				if("create".equalsIgnoreCase(action)){
					if(StringUtils.isEmpty(ename) ||StringUtils.isEmpty(name)|| StringUtils.isEmpty(eventCateId) || StringUtils.isEmpty(plat) || StringUtils.isEmpty(sid)){
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
					logger.warn("参数不正确");
					return;
				}
				
				Event event = new Event();

				if(StringUtils.isNotBlank(ename)){
					event.setEname(ename.trim());
				}
				
				if(StringUtils.isNotBlank(name)){
					event.setName(name.trim());
				}
				
				if(StringUtils.isNotBlank(eventCateId)){
					try {
						int eventCateIdLong = Integer.parseInt(eventCateId.trim());
						
						if(!checkEventCateId(eventCateIdLong)){
							json.put("error", "事件类型ID" + eventCateIdLong + "不存在");
							json.put("result", "操作失败");
							response.getWriter().write(json.toString());
							logger.warn("事件类型ID" + eventCateIdLong + "不存在");
							return;
						}

						event.setEventCateId(eventCateIdLong);
					} catch (NumberFormatException e) {
						json.put("error", "事件类型ID必须为数字");
						json.put("result", "操作失败");
						response.getWriter().write(json.toString());
						logger.warn("事件类型ID必须为数字");
						return;
					}
				}
				
				if(StringUtils.isNotBlank(plat)){
					try {
						event.setPlat(Integer.parseInt(plat.trim()));
					} catch (NumberFormatException e) {
						json.put("error", "平台ID必须为数字");
						json.put("result", "操作失败");
						response.getWriter().write(json.toString());
						logger.warn("平台ID必须为数字");
						return;
					}
				}
				
				if(StringUtils.isNotBlank(sid)){
					try {
						event.setSid(Integer.parseInt(sid.trim()));
					} catch (NumberFormatException e) {
						json.put("error", "站点ID（ " + sid + "）必须为数字");
						json.put("result", "操作失败");
						response.getWriter().write(json.toString());
						logger.warn("站点ID（ " + sid + "）必须为数字");
						return;
					}
				}
				
				if(StringUtils.isNotBlank(id)){
					event.setId(Integer.parseInt(String.valueOf(id)));
				}
				
				event.setStatus(Integer.parseInt(status));
				
				int resultInt = -1;
				if("create".equalsIgnoreCase(action)){
					resultInt = eventService.insert(event);
				}else if("edit".equalsIgnoreCase(action)){
					resultInt = eventService.update(event);
				}
				
				if(resultInt > 0){
					json.put("result", "操作成功");
				}else{
					json.put("error", "数据库操作失败");
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
			errorLogger.error("The operation of " +  action + "ing event failed:",e);
		}
		
	}
	
	/**
	 * 检查事件类型是否存在
	 * @param eventCateId 事件类型ID
	 * @return  存在为true,否则为false
	 */
	public boolean checkEventCateId(int eventCateId){
		return null != eventCateService.findById(eventCateId);
	}
	
}
