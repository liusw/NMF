package com.boyaa.mf.util;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.boyaa.base.utils.HttpUtil;
import com.boyaa.base.utils.JSONUtil;
import com.boyaa.mf.constants.Constants;

/**
 * 短信工具
 * @author hadoop
 * @date 20150325
 */
public class SMSUtil {
	
	/**
	 * @描述 : 发送短信
	 * @作者 : DarcyZeng
	 * @param msg : 短信内容
	 * @param phone : 手机号码
	 * @日期 : Mar 25, 2015
	 */
	public static void sendSMS(String msg) throws JSONException{
		JSONObject params = JSONUtil.getJSONObject();
		params.put("auth_id", 11);
		params.put("auth_token", "cc60b49748b829ef69572a7cd0c75c0c");
		params.put("content", msg);
		params.put("fbpid", "_c_100003");
		params.put("contact_user", "MarsHuang");
		params.put("time", System.currentTimeMillis());
		params.put("priority", 3);
		HttpUtil.visit(Constants.SMS_ADDR, params);
	}
	
	public static void main(String[] args){
		try {
			sendSMS("just for test!s");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
