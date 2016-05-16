package com.boyaa.mf.web.controller.data;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boyaa.mf.entity.data.IntegrationStats;
import com.boyaa.mf.service.data.IntegrationStatsService;
import com.boyaa.mf.util.PageUtil;
import com.boyaa.mf.web.controller.BaseController;

/**
 *<p>Title: 积分分布的接口类<p>
 *<p>Description: null</p>
 *<p>Company: boyaa</p>
 *<p>Date: May 19, 2015 4:41:23 PM</p>
 * @author Joakun Chen
 */
@Controller
public class IntegrationStatsController extends BaseController{
	
	@Autowired
	private IntegrationStatsService integrationStatsService;
	
	/**
	 * 获取积分分布
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value="data/itgStats/getData")
	@ResponseBody
	public String getItgStatsData(String bpid,String stm,String etm){
		if(StringUtils.isBlank(bpid) || StringUtils.isBlank(stm) || StringUtils.isBlank(etm)){
			return getDataTableJson(null, 0).toJSONString();
		}
		
		Map<String, Object> params = new HashMap<String,Object>();
		params.put("bpid", bpid);
		params.put("startTime", stm.replaceAll("-", ""));
		params.put("endTime", etm.replaceAll("-", ""));
		
		PageUtil<IntegrationStats> page = integrationStatsService.getPageList(params);
		return getDataTableJson(page.getDatas(),page.getTotalRecord()).toJSONString();
	}
	
}
