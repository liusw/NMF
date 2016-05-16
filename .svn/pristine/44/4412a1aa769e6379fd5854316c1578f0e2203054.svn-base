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
import com.boyaa.mf.entity.config.FeedConf;
import com.boyaa.mf.entity.config.FeedPlat;
import com.boyaa.mf.service.config.FeedConfService;
import com.boyaa.mf.service.config.FeedPlatService;
import com.boyaa.mf.util.PageUtil;
import com.boyaa.mf.web.controller.BaseController;

/**
 *<p>Title: 平台Feed配置的接口类<p>
 *<p>Description: null</p>
 *<p>Company: boyaa</p>
 *<p>Date: May 8, 2015</p>
 * @author Joakun Chen
 */
@Controller
@RequestMapping(value="config/feedPlat")
public class FeedPlatController extends BaseController{
	private static Logger logger = Logger.getLogger(FeedPlatController.class);
	
	@Autowired
	private FeedPlatService feedPlatService;
	@Autowired
	private FeedConfService feedConfService;
	
	/**
	 * 获取平台下feed配置信息
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value="getData")
	@ResponseBody
	public String getData(String plat,String sSearch_1,String sSearch_2,String sSearch_3,String sSearch_4,String sSearch_5,String sSearch_6,String sSearch_7){
		
		Map<String, Object> params = new HashMap<String,Object>();
		if(StringUtils.isNotEmpty(plat)){
			params.put("plat", Long.parseLong(plat));
		}
		if(StringUtils.isNotBlank(sSearch_1)){
			params.put("likeId", sSearch_1);
		}
		if(StringUtils.isNotBlank(sSearch_2)){
			params.put("likePlat", sSearch_2);
		}
		if(StringUtils.isNotBlank(sSearch_3)){
			params.put("likeFeedId", sSearch_3);
		}
		if(StringUtils.isNotBlank(sSearch_4)){
			params.put("likeFeedName", sSearch_4);
		}
		if(StringUtils.isNotBlank(sSearch_5)){
			if(-1 != Integer.parseInt(sSearch_5)){
				params.put("likeFeedType", sSearch_5);
			}
		}
		if(StringUtils.isNotBlank(sSearch_6)){
			if(-1 != Integer.parseInt(sSearch_6)){
				params.put("likeStatus", sSearch_6);
			}
		}
		if(StringUtils.isNotBlank(sSearch_7)){
			params.put("likeOperator", sSearch_7);
		}
		PageUtil<FeedPlat> datas = feedPlatService.getPageList(params);
		
		return getDataTableJson(datas.getDatas(),datas.getTotalRecord()).toJSONString();
	}
	
	/**
	 * 将Feed添加到平台下
	 * @param request
	 * @param response
	 * @param operator 执行此操作人的工号
	 * @throws IOException
	 * @throws JSONException 
	 */
	@RequestMapping(value="create")
	@ResponseBody
	public JSONObject addFeedPlat(HttpServletRequest request, HttpServletResponse response){
		JSONObject json = JSONUtil.getJSONObject();

		String plat = request.getParameter("data[plat]");
		String feedId = request.getParameter("data[feedId]");
		String feedType = request.getParameter("data[feedType]");
		String status = request.getParameter("data[status]");
		
		Integer operator = getLoginUserInfo().getCode();
		
		/*检查传入参数完整性*/
		if(StringUtils.isEmpty(plat) || StringUtils.isEmpty(feedId) || StringUtils.isEmpty(feedType) || StringUtils.isEmpty(status)){
			json.put("error", "参数不正确");
			json.put("result", "操作失败");
			logger.warn("Parameters sent from employee " + operator + " is not incorrect.");
			return json;
		}
		
		plat = plat.trim();
		feedId = feedId.trim();
		
		Integer platLong = null;
		try {
			platLong = Integer.parseInt(plat);
		} catch (NumberFormatException e) {
			json.put("error", "平台ID必须为数字");
			json.put("result", "操作失败");
			logger.warn("Plat id " + plat + " sent from employee " + operator + " is not a number.");
			return json;
		}
		
		FeedPlat feedPlat = new FeedPlat();
		feedPlat.setPlat(platLong);
		String[] feedIds = feedId.split(",");
		FeedConf[] feeds = new FeedConf[feedIds.length];
		
		int existRecordCount = 0;
		for (int i = 0; i < feedIds.length; i++) {
			/*获取Feed的名称*/
			FeedConf feed = feedConfService.findById(Integer.parseInt(feedIds[i]));
			
			feedPlat.setFeedId(Integer.parseInt(feedIds[i]));
			
			if(null == feed){
				json.put("error", "Feed ID(" + feedIds[i] + ")不存在");
				json.put("result", "操作失败");
				logger.warn("Feed ID(" + feedIds[i] + ")不存在");
				return json;
			}
			
			if(null != feedPlatService.findByPlatFId(feedPlat)){
				logger.info("Feed ID(" + feedIds[i] + ")已存在配置表里");
				existRecordCount ++;
				continue;
			}
			
			feeds[i] = feed;
		}
		
		/*需放在查询配置表是否已经存在代码的后面，因为如果添加类状态，sql会过滤掉*/
		feedPlat.setStatus(Integer.parseInt(status));
		feedPlat.setFeedType(Integer.parseInt(feedType));
		
		feedPlat.setOperator(operator);
		
		int resultInt = 0;

		for (int i = 0; i < feeds.length; i++) {
			if(null != feeds[i]){
				feedPlat.setFeedId(feeds[i].getId());
				feedPlat.setFeedName(feeds[i].getName());
				resultInt = resultInt + feedPlatService.insert(feedPlat);
			}
		}
		
		if(resultInt > 0){
			if(existRecordCount > 0){
				json.put("error", existRecordCount + "条Feed已经存在，未重复更新");
			}
			json.put("result", "操作成功添加" + resultInt + "条记录");
		}else{
			if(existRecordCount > 0){
				json.put("error", "Feed已经存在，未重复更新");
				json.put("result", "操作失败");
			}else{
				json.put("error", "数据库操作异常");
				json.put("result", "操作失败");
			}
		}
		return json;
		
	}
	
	/**
	 * 修改指定平台下Feed配置
	 * @param request
	 * @param response
	 * @param operator 执行此操作人的工号
	 * @throws IOException
	 * @throws JSONException
	 */
	@RequestMapping(value="edit")
	@ResponseBody
	public JSONObject modifyFeedPlat(HttpServletRequest request, HttpServletResponse response) throws IOException, JSONException{
		JSONObject json = JSONUtil.getJSONObject();

		String id = request.getParameter("data[id]");
		String plat = request.getParameter("data[plat]");
		String feedId = request.getParameter("data[feedId]");
		String feedType = request.getParameter("data[feedType]");
		String status = request.getParameter("data[status]");
		
		Integer operator = getLoginUserInfo().getCode();
		
		/*检查传入参数完整性*/
		if(StringUtils.isEmpty(id) || StringUtils.isEmpty(plat) || StringUtils.isEmpty(feedId) || StringUtils.isEmpty(status)){
			json.put("error", "参数不正确");
			json.put("result", "操作失败");
			logger.warn("Parameters sent from employee " + operator + " is not incorrect.");
			return json;
		}
		
		plat = plat.trim();
		feedId = feedId.trim();
		
		Integer platLong = null;
		try {
			platLong = Integer.parseInt(plat);
		} catch (NumberFormatException e) {
			json.put("error", "平台ID必须为数字");
			json.put("result", "操作失败");
			logger.warn("Plat id " + plat + " sent from employee " + operator + " is not a number.");
			return json;
		}
		
		Integer feedIdLong = null;
		try {
			feedIdLong = Integer.parseInt(feedId);
		} catch (NumberFormatException e) {
			json.put("error", "Feed ID必须为数字");
			json.put("result", "操作失败");
			logger.warn("Feed id " + feedId + " sent from employee " + operator + " is not a number.");
			return json;
		}
		
		FeedPlat feedPlat = new FeedPlat();
		
		if(StringUtils.isNotBlank(id)){
			feedPlat.setId(Integer.parseInt(String.valueOf(id)));
		}
		feedPlat.setPlat(platLong);
		feedPlat.setFeedId(feedIdLong);
		
		/*获取Feed的名称*/
		FeedConf feed = feedConfService.findById(Integer.parseInt(feedId));
		if(null == feed){
			json.put("error", "Feed ID(" + feedId + ")不存在");
			json.put("result", "操作失败");
			logger.warn("Feed ID(" + feedId + ")不存在");
			return json;
		}
		feedPlat.setStatus(Integer.parseInt(status));
		feedPlat.setFeedType(Integer.parseInt(feedType));

		if(null != feedPlatService.findByPlatFId(feedPlat)){
			json.put("error", "Feed ID(" + feedId + ")已存在配置表里");
			json.put("result", "操作失败");
			logger.warn("Feed ID(" + feedId + ")已存在配置表里");
			return json;
		}
		
		feedPlat.setFeedName(feed.getName());
		feedPlat.setOperator(operator);
		
		int resultInt = -1;
		resultInt = feedPlatService.update(feedPlat);
		
		if(resultInt > 0){
			json.put("result", "操作成功");
		}else{
			json.put("error", "数据库操作异常");
			json.put("result", "操作失败");
		}
		
		return json;
	}

	/**
	 * 从平台下移除Feed
	 * @param request
	 * @param response
	 * @param operator 执行此操作人的工号
	 * @throws IOException
	 * @throws JSONException
	 */
	@RequestMapping(value="remove")
	@ResponseBody
	public JSONObject removeFeedPlat(HttpServletRequest request, HttpServletResponse response) throws IOException, JSONException{
		String[] ids = request.getParameterValues("id[]");
		
		int resultInt = -1;
		if(null != ids && ids.length > 0){
			for(int i=0;i<ids.length;i++){
				resultInt = feedPlatService.delete(Integer.parseInt(ids[i]));
			}
			
		}
		
		JSONObject json = JSONUtil.getJSONObject();
		json.put("result", resultInt>0?"操作成功":"操作失败");
		
		return json;
	}
	
}
