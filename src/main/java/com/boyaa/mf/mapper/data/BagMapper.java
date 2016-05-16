package com.boyaa.mf.mapper.data;


import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.boyaa.mf.annotation.MyBatisRepository;
import com.boyaa.mf.entity.chart.Bag;
import com.boyaa.mf.entity.chart.OnlineCount;
import com.boyaa.mf.mapper.BaseMapper;
import com.boyaa.mf.vo.LineChart;

@MyBatisRepository 
public interface BagMapper extends BaseMapper<Bag, Integer>{
	Map<String, Object> initChart();
	List<LineChart> chartData(Map<String, Object> map);
	Map<String, Object> initOnlineCountChart(String plat);
	List<LineChart> onlineCountChartData(Map<String, Object> map);
	int insertOnlineCount(OnlineCount onlineCount);
	JSONArray pinglogIps();
	JSONArray pingstalogIps();
}
