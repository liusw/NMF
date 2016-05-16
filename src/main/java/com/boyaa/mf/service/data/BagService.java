package com.boyaa.mf.service.data;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.boyaa.base.utils.JSONUtil;
import com.boyaa.mf.constants.Constants;
import com.boyaa.mf.entity.chart.Bag;
import com.boyaa.mf.entity.chart.OnlineCount;
import com.boyaa.mf.mapper.data.BagMapper;
import com.boyaa.mf.service.AbstractService;
import com.boyaa.mf.vo.LineChart;
import com.boyaa.servlet.ResultState;

@Service
public class BagService extends AbstractService<Bag, Integer> {
	static Logger errorLogger = Logger.getLogger("errorLogger");
	
	@Autowired
	private BagMapper bagMapper;
	
	public JSONObject initChart() {
		Map<String,Object> map = bagMapper.initChart();
		try {
			return JSONUtil.convertTo(map);
		} catch (JSONException e) {
			errorLogger.error(e.getMessage());
		}
		return null;
	}

	public List<LineChart> chartData(String startTime, String endTime, String showType) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("showType", showType);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		
		return bagMapper.chartData(map);
	}
	
	public String initOnlineCountChart(String plat) {
		Map<String,Object> map = bagMapper.initOnlineCountChart(plat);
		try {
			if(map!=null && map.size()>0){
				return JSONUtil.convertTo(map).toString();
			}
			return "";
		} catch (JSONException e) {
			errorLogger.error(e.getMessage());
		}
		return "";
	}
	
	public List<LineChart> onlineCountChartData(String startTime,String endTime, String showType, String plat) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("showType", showType);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("plat", plat);
		
		return bagMapper.onlineCountChartData(map);
	}
	
	public int insertOnlineCount(OnlineCount onlineCount) {
		return bagMapper.insertOnlineCount(onlineCount);
	}
	
	public ResultState checkParam(String playTime,String playCount,String time,String sign){
		if(StringUtils.isBlank(playTime) || StringUtils.isBlank(playCount) || StringUtils.isBlank(time) || StringUtils.isBlank(sign)){
			return new ResultState(ResultState.FAILURE, "请求参数不正确");
		}
		
		try{
			if(Long.parseLong(playTime)<=0 || Integer.parseInt(playCount)<0 || Long.parseLong(time)<=0 ){
				return new ResultState(ResultState.FAILURE, "请求参数不正确");
			}
			if(!DigestUtils.md5Hex(time+"|"+Constants.SIGN_KEY).equals(sign)){
				return new ResultState(ResultState.FAILURE, "验证key不正确");
			}
			if((new Date().getTime()/1000-60*60)>Long.parseLong(time)){
				return new ResultState(ResultState.FAILURE, "请求时间已过期");
			}
		}catch(NumberFormatException e){
			errorLogger.error(e.getMessage());
			return new ResultState(ResultState.FAILURE, "请求参数不正确");
		}
		return new ResultState(ResultState.SUCCESS);
	}

	public JSONArray pinglogIps() {
		return bagMapper.pinglogIps();
	}

	public JSONArray pingstalogIps() {
		return bagMapper.pingstalogIps();
	}
	
}