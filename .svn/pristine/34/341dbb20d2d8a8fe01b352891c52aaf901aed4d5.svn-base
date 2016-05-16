package com.boyaa.mf.web.controller.data;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boyaa.mf.entity.data.SlotMachineStats;
import com.boyaa.mf.service.data.SlotMachineStatsService;
import com.boyaa.mf.util.PageUtil;
import com.boyaa.mf.web.controller.BaseController;

/**
 *<p>Title: 老虎机投注统计的接口类<p>
 *<p>Description: null</p>
 *<p>Company: boyaa</p>
 *<p>Date: May 27, 2015 3:54:05 PM</p>
 * @author Joakun Chen
 */
@Controller
@RequestMapping(value="data/slotMacStats")
public class SlotMachineStatsAction extends BaseController{
	private static Logger logger = Logger.getLogger(SlotMachineStatsAction.class);
	
	@Autowired
	private SlotMachineStatsService slotMachineStatsService;

	/**
	 * 获取老虎机投注统计数据
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value="getData")
	@ResponseBody
	public String getSlotMacStatsData(String plat,String sid,String stm,String etm){
		logger.info("后台接收到获取老虎机投注统计数据的请求");
		
		if(StringUtils.isBlank(plat) || StringUtils.isBlank(sid) || StringUtils.isBlank(stm) || StringUtils.isBlank(etm)){
			return getDataTableJson(null, 0).toJSONString();
		}
				
		Map<String, Object> params = new HashMap<String,Object>();
		params.put("plat", plat);
		params.put("sid", sid);
		params.put("startTime", stm.replaceAll("-", ""));
		params.put("endTime", etm.replaceAll("-", ""));
		
		PageUtil<SlotMachineStats> page = slotMachineStatsService.getPageList(params);
		return getDataTableJson(page.getDatas(),page.getTotalRecord()).toJSONString();
	}
	
}
