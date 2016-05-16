package com.boyaa.mf.util;

import java.net.URLEncoder;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.boyaa.base.utils.Base64Coder;
import com.boyaa.base.utils.HttpUtil;
import com.boyaa.base.utils.IPUtil;
import com.boyaa.base.utils.JSONUtil;
import com.boyaa.base.utils.MD5Util;

/**
 * 拉取其他接口的工具类
 *
 * @作者 : huangyineng
 * @日期 : 2015-9-17  上午11:09:33
 */
public class InterfaceUtil {
	static Logger logger = Logger.getLogger(InterfaceUtil.class);
	static Logger errorLogger = Logger.getLogger("errorLogger");
	static Logger taskLogger = Logger.getLogger("taskLogger");
	
	private final static String LIST_SECRET="6fe7f10342bfae481c09665dbb86f5e7";
	private final static String RTX_SECRET="%&Kmk3_@7`sd";
	
	/**
	 * 调用php接口发list
	 * @param title 标题
	 * @param content 内容
	 * @param rtxs 接收人
	 * @param rtx 发送人
	 * @return
	 */
	public static String addList(String title,String content,String rtxs,String rtx){
		if(StringUtils.isBlank(title) || StringUtils.isBlank(content)|| StringUtils.isBlank(rtx)){
			return null;
		}
		
		try {
			title = URLEncoder.encode(title, "utf-8");
			content =URLEncoder.encode(content.toString(), "utf-8");
			if(StringUtils.isNotBlank(rtxs)){
				rtxs = URLEncoder.encode(rtxs.replace("，", ","), "utf-8");
			}
			
			long time = System.currentTimeMillis() / 1000;
			String _str = "time="+time+"&param[type]=1&param[tktype]=39&param[tktitle]="+title+"&param[tkcontent]="+content+
				"&param[sid]=10&param[rtxs]="+rtxs+"&param[rtx]="+rtx+"&param[pjid]=2&param[mid]=20&param[clientip]="+IPUtil.getRealIp()+
				"&method=Task.Add&appid=1";
			String sig = MD5Util.getVal_UTF8(LIST_SECRET + _str + LIST_SECRET);
			String param = _str + "&sig=" + sig;
			taskLogger.info("添加list:url-->http://api.ifere.com:58080/list/api/rest.php,param-->"+param);
			String responseString = HttpUtil.sendPost("http://api.ifere.com:58080/list/api/rest.php", param);
			return responseString;
		} catch (Exception e) {
			errorLogger.error("添加list出错:"+e.getMessage());
		}
		return null;
	}
	
	public static String sendRTX(String title,String content,String rtxs){
		String api = "http://cmslib.boyaagame.com/api/msg.php";
		if(StringUtils.isBlank(title) || StringUtils.isBlank(content)|| StringUtils.isBlank(rtxs)){
			return null;
		}
		
		try {
			title = URLEncoder.encode(title, "utf-8");
			content =URLEncoder.encode(content, "utf-8");
			
			JSONObject jsonObject = JSONUtil.getJSONObject();
			jsonObject.put("type", "rtx");
			jsonObject.put("to", rtxs);
			jsonObject.put("title", title);
			jsonObject.put("content", content);
			
			long time = System.currentTimeMillis()/1000;
			String data = Base64Coder.encode(jsonObject.toString());
			taskLogger.info("发送RTX:url-->"+api+",param-->data="+jsonObject.toString()+"&by_key="+MD5Util.getVal_UTF8(data+RTX_SECRET+time)+"&time="+time);
			String result = HttpUtil.sendGet(api, "data="+data+"&by_key="+MD5Util.getVal_UTF8(data+RTX_SECRET+time)+"&time="+time);
			return result;
		} catch (Exception e) {
			errorLogger.error("发送rtx出错:"+e.getMessage());
		}
		return null;
	}
}
