package com.boyaa.mf.web.controller.data;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boyaa.base.utils.DateUtil;
import com.boyaa.mf.entity.data.UserSource;
import com.boyaa.mf.service.data.UserSourceService;
import com.boyaa.mf.web.controller.BaseController;
import com.boyaa.mf.web.dto.AjaxObj;

/**
 * 用户来源
 * @author darcy
 */

@Controller
public class UserSourceController extends BaseController{
	static Logger logger = Logger.getLogger(UserSourceController.class);
	
	@Autowired
	private UserSourceService userSourceService;

	@RequestMapping(value="data/userSource/getUserSource")
	@ResponseBody
	public AjaxObj getUserSource(String plat,String stm,String etm,Integer type){
		
		if(StringUtils.isBlank(plat) || StringUtils.isBlank(stm) || StringUtils.isBlank(etm) || type==null){
			return new AjaxObj(AjaxObj.FAILURE, "请求参数不正确");
		}
		
		try {
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("plat", plat);
			params.put("stm", DateUtil.convertPattern(stm, "yyyy-MM-dd", "yyyyMMdd"));
			params.put("etm", DateUtil.convertPattern(etm, "yyyy-MM-dd", "yyyyMMdd"));
			params.put("type", type);
			List<UserSource> userSources = userSourceService.findScrollDataList(params);
			
			return new AjaxObj(AjaxObj.SUCCESS, "请求成功",userSources);
		} catch (ParseException e) {
			errorLogger.error(e.getMessage());
			return new AjaxObj(AjaxObj.FAILURE, "日期格式不对!");
		}
	}
	
}
