package com.boyaa.mf.web.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.boyaa.base.utils.JSONUtil;
import com.boyaa.mf.constants.Constants;
import com.boyaa.mf.entity.chart.Bag;
import com.boyaa.mf.entity.chart.OnlineCount;
import com.boyaa.mf.service.data.BagService;
import com.boyaa.mf.util.ServletUtil;
import com.boyaa.mf.util.SpringBeanUtils;
import com.boyaa.service.ApiService;
import com.boyaa.service.CommonService;
import com.boyaa.servlet.ResultState;

public class ApiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static Logger taskLogger = Logger.getLogger("apiLogger");
	static Logger errorLogger = Logger.getLogger("errorLogger");
	static Logger fatalLogger = Logger.getLogger("fatalLogger");
	private static final String CONTENT_TYPE = "application/x-javascript";
	
	public void init() throws ServletException {
	}

	public void destroy() {
		super.destroy();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		taskLogger.info("request ip:" + ServletUtil.getRemortIP(request));
		request.setCharacterEncoding(Constants.DEFAULT_ENCODING);
		response.setContentType(CONTENT_TYPE);
		response.setCharacterEncoding(Constants.DEFAULT_ENCODING);
		
		ResultState resultState = null;
		
		String action = request.getParameter("action");
		if("bag".equals(action)){
			String playTime = request.getParameter("playTime");
			String playCount = request.getParameter("playCount");
			String time = request.getParameter("time");
			String sign = request.getParameter("sign");
			
			BagService bagService = SpringBeanUtils.getBean("bagService",BagService.class);
			
			resultState = bagService.checkParam(playTime, playCount, time, sign);
			if(resultState.getOk()==0){
				ServletUtil.responseRecords(response, null, resultState);
				return;
			}
			
			Bag bag = new Bag();
			bag.setPlayCount(Integer.parseInt(playCount));
			bag.setPlayTime(Long.parseLong(playTime));
			int count = bagService.insert(bag);
			if(count>0){
				resultState = new ResultState(ResultState.SUCCESS, "上报成功");
			}else{
				resultState = new ResultState(ResultState.FAILURE, "上报失败");
			}
		}else if("onlineCount".equals(action)){
			String playTime = request.getParameter("playTime");
			String playCount = request.getParameter("playCount");
			String time = request.getParameter("time");
			String sign = request.getParameter("sign");
			String plat = request.getParameter("p");
			
			taskLogger.info("playTime:"+playTime+",playCount="+playCount+",time="+time+",sign="+sign+",plat="+plat);
			
			BagService bagService = SpringBeanUtils.getBean("bagService",BagService.class);
			resultState = bagService.checkParam(playTime, playCount, time, sign);
			if(resultState.getOk()==0){
				ServletUtil.responseRecords(response, null, resultState);
				return;
			}
			
			if(StringUtils.isBlank(plat)){
				resultState = new ResultState(ResultState.FAILURE, "平台参数不正确");
				ServletUtil.responseRecords(response, null, resultState);
				return;
			}
			
			OnlineCount onlineCount = new OnlineCount();
			onlineCount.setPlayCount(Integer.parseInt(playCount));
			onlineCount.setPlayTime(Long.parseLong(playTime));
			onlineCount.setPlat(plat);
			
			int count = bagService.insertOnlineCount(onlineCount);
			
			if(count>0){
				resultState = new ResultState(ResultState.SUCCESS, "上报成功");
			}else{
				resultState = new ResultState(ResultState.FAILURE, "上报失败");
			}
			
		}else if("checkserver".equals(action)){
			ApiService apiService = new ApiService();
			response.getWriter().write(apiService.checkServer().toString());
			return;
		}else if("netstatus".equals(action)){
			
			String ip = request.getParameter("ip");
			String addr = request.getParameter("addr");
			String loss = request.getParameter("loss");
			String delay = request.getParameter("delay");
			String dTime = request.getParameter("dTime");
			String time = request.getParameter("time");
			String sign = request.getParameter("sign");
			
			if(StringUtils.isBlank(ip) || StringUtils.isBlank(addr) || StringUtils.isBlank(loss) || 
					StringUtils.isBlank(delay) || StringUtils.isBlank(dTime) ||StringUtils.isBlank(time) ||StringUtils.isBlank(sign)){
				ServletUtil.responseRecords(response, null, new ResultState(ResultState.FAILURE, "请求参数不正确,必须包含以下参数[ip、addr、loss、delay、dTime、time、sign]"));
				return;
			}
			
			JSONObject jsonObject = JSONUtil.getJSONObject();
			try {
				jsonObject.put("ip", ip);
				jsonObject.put("addr", addr);
				jsonObject.put("loss", loss);
				jsonObject.put("delay", delay);
				jsonObject.put("dTime", dTime);
				
				String dataEc = URLEncoder.encode(jsonObject.toString(),"UTF-8");
				resultState = commonPush(dataEc,"d_netstatus",time,sign);
				ServletUtil.responseRecords(response, null, resultState);
				return;
			} catch (JSONException e) {
				errorLogger.error(e.getMessage());
				ServletUtil.responseRecords(response, null, new ResultState(ResultState.FAILURE, "上报出错!"));
				return;
			}
		}else if("commonpush".equals(action)){//通用上报接口
			String data = request.getParameter("data");
			String t = request.getParameter("t");
			String time = request.getParameter("time");
			String sign = request.getParameter("sign");
			resultState = commonPush(data,t,time,sign);
			ServletUtil.responseRecords(response, null, resultState);
			return;
		}else{
			resultState = new ResultState(ResultState.FAILURE, "请求路径不正确");
		}
		ServletUtil.responseRecords(response, null, resultState);
		return;
	}

	/**
	 * 通用上报接口
	 * @param data 上报数据
	 * @param t 上报的表名
	 * @param time 上报时间
	 * @param sign 加密串
	 * @return
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	private ResultState commonPush(String data,String t,String time,String sign) throws IOException,
			UnsupportedEncodingException {
		if(StringUtils.isBlank(data) || StringUtils.isBlank(t) ||StringUtils.isBlank(time) ||StringUtils.isBlank(sign)){
			return new ResultState(ResultState.FAILURE, "请求参数不正确,必须包含以下参数[data、t、time、sign]");
		}
		
		try{
			//较验
			if(!DigestUtils.md5Hex(time+"|"+Constants.SIGN_KEY).equals(sign)){
				return new ResultState(ResultState.FAILURE, "验证sign不正确");
			}
			if((new Date().getTime()/1000-60*60)>Long.parseLong(time)){
				return new ResultState(ResultState.FAILURE, "请求时间已过期,time过期时间为1个小时");
			}
			
			//封装入库
			data = URLDecoder.decode(data, "UTF-8");
			JSONObject dataJson = JSONUtil.parseObject(data);
			Iterator<String> it = dataJson.keySet().iterator();
			
			Map<String,Object> values = new HashMap<String,Object>();
			StringBuffer columns = new StringBuffer();
			
			while(it.hasNext()) {
				String key = it.next();
				Object value = dataJson.get(key);
				
				values.put(key, value);
				columns.append(key).append(it.hasNext()?",":"");
			}
			if(columns.length()<=0){
				return new ResultState(ResultState.FAILURE, "请求参数不正确[Json格式的数据为空]");
			}
			
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("table",t);
			params.put("columns",columns.toString());
			params.put("values",values);
			
			CommonService commonService = new CommonService();
			return commonService.insertMySQL("10000005", params);				
		}catch(NumberFormatException e){
			errorLogger.error(e.getMessage());
			return new ResultState(ResultState.FAILURE, "请求参数不正确[数字类型的参数所传的值不正确]");
		} catch (JSONException e) {
			errorLogger.error(e.getMessage());
			return new ResultState(ResultState.FAILURE, "请求参数不正确[data不是正确的json格式数据]");
		}
	}
}