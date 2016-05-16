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
import com.boyaa.mf.constants.Constants;
import com.boyaa.mf.entity.config.FeedConf;
import com.boyaa.mf.service.config.FeedConfService;
import com.boyaa.mf.util.PageUtil;
import com.boyaa.mf.vo.LoginUserInfo;
import com.boyaa.mf.web.controller.BaseController;

/**
 *<p>Title: Feed配置管理的接口类<p>
 *<p>Description: 包括Feed的增删改查</p>
 *<p>Company: boyaa</p>
 *<p>Date: May 6, 2015</p>
 * @author Joakun Chen
 */
@Controller
public class FeedConfController extends BaseController {
	private static Logger logger = Logger.getLogger(FeedConfController.class);
	
	@Autowired
	private FeedConfService feedConfService;
	
	/**
	 * 获取所有的feed配置信息
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value="config/feedConf/getData")
	@ResponseBody
	public String getDatas(HttpServletRequest request, HttpServletResponse response) throws IOException{
		Map<String, Object> params = new HashMap<String,Object>();
		String likeId = request.getParameter("sSearch_1");
		if(StringUtils.isNotBlank(likeId)){
			params.put("likeId", likeId);
		}
		String likeName = request.getParameter("sSearch_2");
		if(StringUtils.isNotBlank(likeName)){
			params.put("likeName", likeName);
		}
		String likeStatus = request.getParameter("sSearch_3");
		if(StringUtils.isNotBlank(likeStatus)){
			if(-1 != Integer.parseInt(likeStatus)){
				params.put("likeStatus", likeStatus);
			}
		}
		
		String likeOperator = request.getParameter("sSearch_4");
		if(StringUtils.isNotBlank(likeOperator)){
			params.put("likeOperator", likeOperator);
		}
		
		PageUtil<FeedConf> datas = feedConfService.getPageList(params);

		return getDataTableJson(datas.getDatas(),datas.getTotalRecord()).toJSONString();
	}
	
	/**
	 * 编辑feed配置信息，包括添加，编辑及删除操作
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value="config/feedConf/edit")
	@ResponseBody
	public JSONObject edit(HttpServletRequest request, HttpServletResponse response) throws IOException{
		logger.info("Plat received the request of editing feed successfully.");
		
		JSONObject json = JSONUtil.getJSONObject();
		String action = request.getParameter("action");
		
		try {
			/*获取工号*/
			LoginUserInfo userInfo = (LoginUserInfo) request.getSession().getAttribute(Constants.USER_LOGIN_SESSION_NAME);
			Integer code = userInfo.getCode();
			
			if("create".equalsIgnoreCase(action) || "edit".equalsIgnoreCase(action)|| "remove".equalsIgnoreCase(action)){
				if("remove".equalsIgnoreCase(action)){
					String[] ids = request.getParameterValues("id[]");
					
					int resultInt = -1;
					if(null != ids && ids.length > 0){
						for(int i=0;i<ids.length;i++){
							resultInt = feedConfService.delete(Integer.parseInt(ids[i]));
						}
					}
					
					json.put("result", resultInt>0?"操作成功":"操作失败");
					return json;
				}
				
				String id = request.getParameter("data[id]");
				String name = request.getParameter("data[name]");
				String status = request.getParameter("data[status]");
	
				//默认情况，添加feed id时，feed name如果没有定义，则跟feed id一致
				if("create".equalsIgnoreCase(action) && StringUtils.isEmpty(name)){
					name = id;
				}
				
				/*检查传入参数完整性*/
				if(null == id  || StringUtils.isEmpty(status) || StringUtils.isEmpty(name)){
					json.put("error", "参数不正确");
					json.put("result", "操作失败");
					logger.warn("Parameters sent from employee " + code + " is not incorrect.");
					return json;
				}
				
				id = id.trim();
				name = name.trim();
				
				Integer idLong = null;
				try {
					idLong = Integer.parseInt(id);
				} catch (NumberFormatException e) {
					json.put("error", "ID必须为数字");
					json.put("result", "操作失败");
					logger.warn("Id " + id + " sent from employee " + code + " is not a number.");
					return json;
				}
				
				FeedConf feed = new FeedConf();
				feed.setId(idLong);
				feed.setName(name);
				feed.setStatus(Integer.parseInt(status));
				feed.setOperator(code);
				
				int resultInt = -1;
				if("create".equalsIgnoreCase(action)){
					resultInt = feedConfService.insert(feed);
				}else if("edit".equalsIgnoreCase(action)){
					resultInt = feedConfService.update(feed);
				}
				
				if(resultInt > 0){
					json.put("result", "操作成功");
				}else{
					json.put("error", "数据库操作失败");
					json.put("result", "操作失败");
				}				
				return json;
			}
		} catch (Exception e) {
			try {
				json.put("error", e.getMessage());
				json.put("result", "操作失败");
			} catch (JSONException e1) {
				logger.warn(e1);;
			}
			errorLogger.error("The operation of " +  action + "ing feed failed:",e);
		}
		
		return json;
	}
	
}
