package com.boyaa.mf.web.controller.data;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boyaa.base.utils.DateUtil;
import com.boyaa.mf.entity.data.ActiveDetail;
import com.boyaa.mf.entity.data.ActiveLost;
import com.boyaa.mf.entity.data.ActiveView;
import com.boyaa.mf.service.data.ActiveDetailService;
import com.boyaa.mf.service.data.ActiveLostService;
import com.boyaa.mf.service.data.ActiveViewService;
import com.boyaa.mf.util.CommonUtils;
import com.boyaa.mf.util.DateUtils;
import com.boyaa.mf.util.PageUtil;
import com.boyaa.mf.web.controller.BaseController;

/**
 * LC活跃
 *
 * @作者 : huangyineng
 * @日期 : 2016-3-10  下午3:20:57
 */
@Controller
@RequestMapping("active")
public class ActiveController extends BaseController{
	static Logger logger = Logger.getLogger(ActiveController.class);
	
	@Resource
	private ActiveViewService activeViewService;
	@Autowired
	private ActiveDetailService activeService;
	@Autowired
	private ActiveLostService activeLostService;
	
	/**
	 * 活跃概况
	 * @throws ParseException 
	 */
	@RequestMapping(value="activeView")
	@ResponseBody
	public String getActiveView(Integer plat,String stm,String etm,Integer sid) throws ParseException{
		if(plat==null || StringUtils.isBlank(stm) || StringUtils.isBlank(etm) || sid==null){
			return getDataTableJson(null, 0).toJSONString();
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("plat", plat);
		params.put("sid", sid);
		params.put("stm",	DateUtil.convertPattern(stm, "yyyy-MM-dd", "yyyyMMdd"));
		params.put("etm",	DateUtil.convertPattern(etm, "yyyy-MM-dd", "yyyyMMdd"));
		
		PageUtil<ActiveView> datas = activeViewService.getPageList(params);
		List<ActiveView> result = datas.getDatas();
		if(result!=null && result.size()>0){
			for(ActiveView view : result){
				view.setTime(DateUtil.convertPattern(view.getTm()+"", "yyyyMMdd", "yyyy-MM-dd"));
				view.setWeek(DateUtils.getWeek(DateUtils.str2Date(view.getTime(), "yyyy-MM-dd")));
				view.setNoplayNum(view.getActive()-view.getActivePlay());
				view.setPlayRate(CommonUtils.getDoubleValue(view.getPlayNum(), view.getPlayNum()+view.getNoplayNum()));
				view.setActiveRate(CommonUtils.getDoubleValue(view.getActive(), view.getTotalRegister()));
				view.setActivePlayRate(CommonUtils.getDoubleValue(view.getActivePlay(), view.getActive()));
				view.setActiveDayRingRate(CommonUtils.getDoubleValue(view.getActive(), view.getYesterdayActive()));
				view.setActiveWeekRingRate(CommonUtils.getDoubleValue(view.getActive(), view.getLastWeekActive()));
				view.setRegisterPlayRate(CommonUtils.getDoubleValue(view.getRegisterPlay(), view.getRegisterNum()));
				view.setYesterdayBackRate(CommonUtils.getDoubleValue(view.getYesterdayBack(), view.getYesterdayRegister()));
				view.setThreeBackRate(CommonUtils.getDoubleValue(view.getThreeBack(), view.getThreeRegister()));
				view.setSevenBackRate(CommonUtils.getDoubleValue(view.getSevenBack(), view.getSevenRegister()));
				view.setFiftyBackRate(CommonUtils.getDoubleValue(view.getFiftyBack(), view.getFiftyRegister()));
				view.setThirtyBackRate(CommonUtils.getDoubleValue(view.getThirtyBack(), view.getThirtyRegister()));
				view.setRegisterDayRingRate(CommonUtils.getDoubleValue(view.getRegisterNum(), view.getYesterdayRegister()));
				view.setRegisterWeekRingRate(CommonUtils.getDoubleValue(view.getRegisterNum(), view.getSevenRegister()));
				view.setActivatingPlayRate(CommonUtils.getDoubleValue(view.getActivatingPlayNum(), view.getActivatingNum()));
			}
		}
		return getDataTableJson(datas.getDatas(),datas.getTotalRecord()).toJSONString();
	}
	
	@RequestMapping(value="activeDetail")
	@ResponseBody
	public String getActiveDetail(Integer plat,String stm,String etm,Integer sid) throws ParseException{
		if(plat==null || StringUtils.isBlank(stm) || StringUtils.isBlank(etm) || sid==null){
			return getDataTableJson(null, 0).toJSONString();
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("plat", plat);
		params.put("sid", sid);
		params.put("stm",	DateUtil.convertPattern(stm, "yyyy-MM-dd", "yyyyMMdd"));
		params.put("etm",	DateUtil.convertPattern(etm, "yyyy-MM-dd", "yyyyMMdd"));
		
		PageUtil<ActiveDetail> list = activeService.getPageList(params);
		List<ActiveDetail> result = list.getDatas();
		if(result!=null && result.size()>0){
			for(ActiveDetail view : result){
				view.setTime(DateUtil.convertPattern(view.getTm()+"", "yyyyMMdd", "yyyy-MM-dd"));
				view.setWeek(DateUtils.getWeek(DateUtils.str2Date(view.getTime(), "yyyy-MM-dd")));
				view.setYesterdayBackRate(CommonUtils.getDoubleValue(view.getYesterdayBack(), view.getYesterdayRegister()));
				view.setOldBackRate(CommonUtils.getDoubleValue(view.getYesterdayActiveNonRegisterBack(), view.getYesterdayActiveNonRegisterNum()));
				view.setPayBackRate(CommonUtils.getDoubleValue(view.getYesterdayActiveHisPayBack(), view.getYesterdayActiveHisPayNum()));
				view.setLostBackRate(CommonUtils.getDoubleValue(view.getYesterdayActivatingBack(), view.getYesterdayActivatingNum()));
			}
		}
		return getDataTableJson(list.getDatas(),list.getTotalRecord()).toJSONString();
	}
	
	@RequestMapping(value="activeLost")
	@ResponseBody
	public String getActiveLost(Integer plat,String stm,String etm,Integer sid) throws ParseException{
		if(plat==null || StringUtils.isBlank(stm) || StringUtils.isBlank(etm) || sid==null){
			return getDataTableJson(null, 0).toJSONString();
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("plat", plat);
		params.put("sid", sid);
		params.put("stm",	DateUtil.convertPattern(stm, "yyyy-MM-dd", "yyyyMMdd"));
		params.put("etm",	DateUtil.convertPattern(etm, "yyyy-MM-dd", "yyyyMMdd"));
		
		PageUtil<ActiveLost> list = activeLostService.getPageList(params);
		List<ActiveLost> result = list.getDatas();
		if(result!=null && result.size()>0){
			for(ActiveLost view : result){
				view.setTime(DateUtil.convertPattern(view.getTm()+"", "yyyyMMdd", "yyyy-MM-dd"));
				view.setWeek(DateUtils.getWeek(DateUtils.str2Date(view.getTime(), "yyyy-MM-dd")));
			}
		}
		
		return getDataTableJson(result,list.getTotalRecord()).toJSONString();
	}
	
}
