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
import com.boyaa.mf.entity.data.EndHandRate;
import com.boyaa.mf.entity.data.FapaiRate;
import com.boyaa.mf.entity.data.GreatRate;
import com.boyaa.mf.entity.data.ShoupaiRate;
import com.boyaa.mf.entity.data.StartHandRate;
import com.boyaa.mf.service.data.EndHandRateService;
import com.boyaa.mf.service.data.FapaiRateService;
import com.boyaa.mf.service.data.GreatRateService;
import com.boyaa.mf.service.data.ShoupaiRateService;
import com.boyaa.mf.service.data.StartHandRateService;
import com.boyaa.mf.util.CommonUtil;
import com.boyaa.mf.web.controller.BaseController;
import com.boyaa.mf.web.dto.AjaxObj;

/**
 * 牌局概率
 * @author darcy
 */
@Controller
@RequestMapping(value="paijuRate/getRate")
public class PaijuRateController extends BaseController {
	static Logger logger = Logger.getLogger(PaijuRateController.class);
	
	@Autowired
	private FapaiRateService fapaiRateService;
	@Autowired
	private ShoupaiRateService shoupaiRateService;
	@Autowired
	private StartHandRateService startHandRateService;
	@Autowired
	private EndHandRateService endHandRateService;
	@Autowired
	private GreatRateService greatRateService;
	
	@RequestMapping(value="fapaiRate")
	@ResponseBody
	public AjaxObj getShoupaiRate(String plat,String stm,String etm,String type) throws ParseException{
		
		if(StringUtils.isBlank(plat) || StringUtils.isBlank(stm) || StringUtils.isBlank(etm) || StringUtils.isBlank(type)){
			return new AjaxObj(AjaxObj.FAILURE,"请求参数不正确");
		}
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("plat", plat);
		params.put("stm", DateUtil.convertPattern(stm, "yyyy-MM-dd", "yyyyMMdd"));
		params.put("etm", DateUtil.convertPattern(etm, "yyyy-MM-dd", "yyyyMMdd"));
		params.put("type", type);
		
		List<FapaiRate> gongpaiRates = fapaiRateService.findScrollDataList(params);
		return new AjaxObj(AjaxObj.SUCCESS, "请求成功", CommonUtil.toJSONString(gongpaiRates));
	}
	
	@RequestMapping(value="shoupaiRate")
	@ResponseBody
	public AjaxObj shoupaiRate(String plat,String stm,String etm,String type) throws ParseException{
		
		if(StringUtils.isBlank(plat) || StringUtils.isBlank(stm) || StringUtils.isBlank(etm) || StringUtils.isBlank(type)){
			return new AjaxObj(AjaxObj.FAILURE,"请求参数不正确");
		}
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("plat", plat);
		params.put("stm", DateUtil.convertPattern(stm, "yyyy-MM-dd", "yyyyMMdd"));
		params.put("etm", DateUtil.convertPattern(etm, "yyyy-MM-dd", "yyyyMMdd"));
		params.put("type", type);
		
		List<ShoupaiRate> shoupaiRates = shoupaiRateService.findScrollDataList(params);
		return new AjaxObj(AjaxObj.SUCCESS, "请求成功", CommonUtil.toJSONString(shoupaiRates));
	}
	
	@RequestMapping(value="startHandRate")
	@ResponseBody
	public AjaxObj startHandRate(String plat,String stm,String etm) throws ParseException{
		
		if(StringUtils.isBlank(plat) || StringUtils.isBlank(stm) || StringUtils.isBlank(etm)){
			return new AjaxObj(AjaxObj.FAILURE,"请求参数不正确");
		}
		
		//除了手牌概率和发牌概率 其他的请求默认给一个type
		String type = "0";
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("plat", plat);
		params.put("stm", DateUtil.convertPattern(stm, "yyyy-MM-dd", "yyyyMMdd"));
		params.put("etm", DateUtil.convertPattern(etm, "yyyy-MM-dd", "yyyyMMdd"));
		params.put("type", type);
		
		List<StartHandRate> startHandRates = startHandRateService.findScrollDataList(params);
		if(startHandRates!=null && startHandRates.size()>0){
			long count = Long.parseLong(startHandRates.get(0).getCount());
			long sumNum = 0;
			for(StartHandRate rate : startHandRates){
				sumNum += Long.parseLong(rate.getNum());
			}
			StartHandRate rate = new StartHandRate();
			rate.setType("5");//杂牌
			rate.setNum(""+(count-sumNum));
			rate.setCount(count+"");
			startHandRates.add(rate);
		}
		return new AjaxObj(AjaxObj.SUCCESS, "请求成功", CommonUtil.toJSONString(startHandRates));
	}
	
	@RequestMapping(value="endHandRate")
	@ResponseBody
	public AjaxObj endHandRate(String plat,String stm,String etm) throws ParseException{
		
		if(StringUtils.isBlank(plat) || StringUtils.isBlank(stm) || StringUtils.isBlank(etm)){
			return new AjaxObj(AjaxObj.FAILURE,"请求参数不正确");
		}
		
		//除了手牌概率和发牌概率 其他的请求默认给一个type
		String type = "0";
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("plat", plat);
		params.put("stm", DateUtil.convertPattern(stm, "yyyy-MM-dd", "yyyyMMdd"));
		params.put("etm", DateUtil.convertPattern(etm, "yyyy-MM-dd", "yyyyMMdd"));
		params.put("type", type);
		
		List<EndHandRate> endHandRates = endHandRateService.findScrollDataList(params);
		return new AjaxObj(AjaxObj.SUCCESS, "请求成功", CommonUtil.toJSONString(endHandRates));
	}
	
	@RequestMapping(value="greatRate")
	@ResponseBody
	public AjaxObj greatRate(String plat,String stm,String etm,String dt) throws ParseException{
		
		if(StringUtils.isBlank(plat) || StringUtils.isBlank(stm) || StringUtils.isBlank(etm)|| StringUtils.isBlank(dt)){
			return new AjaxObj(AjaxObj.FAILURE,"请求参数不正确");
		}
		
		//除了手牌概率和发牌概率 其他的请求默认给一个type
		String type = "0";
		
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("plat", plat);
			params.put("stm", DateUtil.convertPattern(stm, "yyyy-MM-dd", "yyyyMMdd"));
			params.put("etm", DateUtil.convertPattern(etm, "yyyy-MM-dd", "yyyyMMdd"));
			params.put("type", type);
			
			params.put("dt", dt);
			List<GreatRate> greatRates = greatRateService.findScrollDataList(params);
			return new AjaxObj(AjaxObj.SUCCESS, "请求成功", CommonUtil.toJSONString(greatRates));
	}
}
