package com.boyaa.mf.util;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

public class JsonUtils {
	
	/**
	 * 获取json中某个key的值,如果不存在,返回null
	 * @param jsonObject
	 * @param key
	 * @return
	 * @throws JSONException
	 */
	public static Object getValue(JSONObject jsonObject,String key) throws JSONException{
		return getValue(jsonObject, key, null);
	}
	
	/**
	 * 获取json中某个key的值
	 * @param jsonObject
	 * @param key
	 * @param defaultValue
	 * @return
	 * @throws JSONException 
	 */
	public static Object getValue(JSONObject jsonObject,String key,Object defaultValue) throws JSONException{
		if(jsonObject.containsKey(key)){
			return jsonObject.get(key);
		}
		return defaultValue;
	}
	
	
}
