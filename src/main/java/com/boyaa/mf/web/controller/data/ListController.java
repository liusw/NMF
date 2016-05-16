package com.boyaa.mf.web.controller.data;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.boyaa.base.utils.JSONUtil;
import com.boyaa.mf.util.InterfaceUtil;
import com.boyaa.mf.util.PHPSerializer;
import com.boyaa.mf.vo.LoginUserInfo;
import com.boyaa.mf.web.controller.BaseController;
import com.boyaa.mf.web.dto.AjaxObj;

@Controller
public class ListController extends BaseController {
	static Logger logger = Logger.getLogger(ListController.class);
	
	
	@RequestMapping(value="addList")
	@ResponseBody
	public AjaxObj addList(Integer plat,String sid,String bpid,String title,String content,String rtxs,String tableParams){
		
		if(plat==null || StringUtils.isBlank(title) || StringUtils.isBlank(content)){
			return new AjaxObj(AjaxObj.FAILURE,"参数不正确");
		}
		
		LoginUserInfo userInfo = getLoginUserInfo();
		String rtx = userInfo.getUsername();
		
		StringBuffer table = new StringBuffer();
		table.append("<table border='1' cellspacing='0' cellpadding='0' width='100%'>");
		table.append("<tr><td width='10%'>平台</td><td>"+plat+"</td></tr>");
		if(StringUtils.isNotBlank(sid)){
			String[] sidArr = sid.split(",");
			bpid=bpid.replaceAll("\"", "");
			String[] bpidArr = bpid.split(",");
			if(sidArr.length!=bpidArr.length){
				table.append("<tr><td>sid</td><td>"+sid+"</td></tr>");
				table.append("<tr><td>bpid</td><td>"+bpid+"</td></tr>");
			}else{
				table.append("<tr><td>sid(bpid)</td><td>");
				for(int i=0;i<sidArr.length;i++){
					table.append(sidArr[i]+"("+bpidArr[i]+")<br/>");
				}
				table.append("</td></tr>");
			}
		}
		
		
		JSONArray paramJsonArray = JSONUtil.parseArray(tableParams);
		if(paramJsonArray!=null && paramJsonArray.size()>0){
			table.append("<tr><td>业务表相关信息</td><td>");
			for(int i=0;i<paramJsonArray.size();i++){
				JSONObject paramJson = paramJsonArray.getJSONObject(i);
				if(paramJson.containsKey("tableName")){
					table.append("表名:"+paramJson.getString("tableName")+"<br/>");
				}
				if(paramJson.containsKey("tm")){
					table.append("时间段:"+paramJson.getString("tm")+"<br/>");
				}
				if(paramJson.containsKey("column")){
					table.append("条件:"+paramJson.getString("column")+"<br/><hr>");
				}
			}
			table.append("</td></tr>");
		}
		table.append("<tr><td colspan='2'>"+content+"</td></tr>");
		table.append("</table>");
		
		String responseString = InterfaceUtil.addList(title, table.toString(), rtxs+","+rtx, rtx);
		
		if(StringUtils.isNotBlank(responseString)){
			try {
				Object text = PHPSerializer.unserialize(responseString.getBytes());
				
				if(StringUtils.isNotBlank(text.toString())){
					JSONObject jsonObject = JSONUtil.parseObject(text.toString().replaceAll("=",":"));//{ret=283212, time=1442299121, errno=0}
					if(jsonObject.containsKey("errno") && jsonObject.getIntValue("errno")==0){
						String listId = (jsonObject.containsKey("ret")?jsonObject.getString("ret"):"");
						InterfaceUtil.sendRTX(title, "http://list.oa.com/task.php?tkid="+listId, rtxs+","+userInfo.getCode());
						
						return new AjaxObj(AjaxObj.SUCCESS,(jsonObject.containsKey("ret")?jsonObject.getString("ret"):""));
					}
				}
			} catch (IllegalAccessException e) {
				errorLogger.error(e.getMessage());
			}
		}
		
		return new AjaxObj(AjaxObj.FAILURE,"新增失败");
	}
}
