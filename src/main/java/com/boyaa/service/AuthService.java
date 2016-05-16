package com.boyaa.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.boyaa.base.utils.FileUtil;
import com.boyaa.base.utils.JSONUtil;
import com.boyaa.mf.constants.Constants;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AuthService {
	static Logger logger = Logger.getLogger(AuthService.class);
	static Logger errorLogger = Logger.getLogger("errorLogger");
	static Logger fatalLogger = Logger.getLogger("fatalLogger");
	
	//获取能访问的资源
	public Map<String,Object> getResources(JSONArray permiss){
		String authConfig = FileUtil.read(AuthService.class.getClassLoader().getResource("auth.config").getPath(),"");
		try {
			JSONObject authJson = JSONUtil.parseObject(authConfig);
			JSONObject resources = (JSONObject) authJson.get("resources");
			return getResoucesByJson(resources,null,"resources",permiss);
		} catch (JSONException e) {
			errorLogger.info(e.getMessage());
		}
		return null;
	}
	
	@SuppressWarnings("all")
	private Map<String,Object> getResoucesByJson(JSONObject json,Map<String,Object> resourceMap,String parentName,JSONArray permiss){
		if(resourceMap==null){
			resourceMap = new HashMap<String, Object>();
		}
		try {
			Iterator<String> it = json.keySet().iterator();
			
			boolean isAll=false;//是否为全局
			for(int i=0;i<permiss.size();i++){
				String p = (String) permiss.get(i);
				if(p.equals("*") || (StringUtils.isNotBlank(parentName) && (parentName+".*").equals(p))){
					isAll = true;
					break;
				}
			}
			
			while(it.hasNext()) {
				String key = it.next();
				if(key.equals("title")){
					continue;
				}
				if(key.equals("child")){
					//递归获取
					getResoucesByJson((JSONObject) json.get(key),resourceMap,parentName,permiss);
					continue;
				}
				
				JSONObject node = (JSONObject) json.get(key);
				
				//如果没子节点,并且是全局,说明有权限
				if(isAll && !node.containsKey("child")){
					resourceMap.put(key,true);
				}else{
					//判断当前节点是否有权限
					if(hasPermissions(key,permiss)){
						resourceMap.put(key,true);
					}
				}
				
				if(node.containsKey("child")){
					getResoucesByJson(node,resourceMap,key,permiss);
				}
			}
		} catch (JSONException e) {
			errorLogger.error(e.getMessage());
		}
		return resourceMap;
	}
	
	private boolean hasPermissions(String key,JSONArray permiss){
		boolean isTrue=false;
		for(int i=0;i<permiss.size();i++){
			try {
				String p = (String) permiss.get(i);
				if((key).equals(p)){
					isTrue = true;
					break;
				}
			} catch (JSONException e) {
				errorLogger.error(e.getMessage());
			}
		}
		if(isTrue)
			return isTrue;
		
		for(int i=0;i<permiss.size();i++){
			try {
				String p = (String) permiss.get(i);
				if((key+".*").equals(p)){
					isTrue = true;
					break;
				}
			} catch (JSONException e) {
				errorLogger.error(e.getMessage());
			}
		}
		return isTrue;
	}
	
	public String url(String backUrl,String act){
		String sid = DigestUtils.md5Hex("805e3e2af817d0b1e9c2b0258bd029cd"+Constants.AUTH_ID+backUrl);
		
		StringBuilder sb = new StringBuilder();
		try {
			sb.append("act=").append(act)
			  .append("&ssoid=").append(Constants.AUTH_ID)
			  .append("&sig=").append(sid)
			  .append("&type_").append(Constants.AUTH_ID).append("=json")
			  .append("&redirect=").append(URLEncoder.encode(backUrl,"utf-8"));
		} catch (UnsupportedEncodingException e) {
			errorLogger.error(e.getMessage());
		}
		
		return "http://auth.oa.com/api/sso.php?"+ sb.toString();
	}
}