package com.boyaa.mf.web.controller.data;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.boyaa.base.utils.CsvUtil;
import com.boyaa.base.utils.JSONUtil;
import com.boyaa.entity.common.JSONResult;
import com.boyaa.mf.web.controller.BaseController;
import com.boyaa.service.CommonService;

@Controller
public class JinbiaosaiController extends BaseController {
	static Logger logger = Logger.getLogger(JinbiaosaiController.class);
	
	@RequestMapping(value="data/jinbiaosai/down")
	@ResponseBody
	public void down(String stm,String etm,String plat,HttpServletResponse response) throws IOException{
		
		response.setContentType("application/octet-stream");
		response.setHeader("Content-disposition", "attachment;filename=\"锦标赛数据导出[" + stm +"-"+ etm+ "].csv\"");
		
		CommonService commonService = new CommonService();
		
		OutputStream out = null;
		PrintWriter print = null;
		try {
			out = response.getOutputStream();
			print = CsvUtil.getPrint(out);
		
			JSONObject args = JSONUtil.getJSONObject();
			args.put("plat", plat);
			args.put("stm", stm);
			args.put("etm", etm);
			args.put("table", "d_jinbiaosai");
			
			JSONObject paramJson = JSONUtil.getJSONObject();
			paramJson.put("id", 10000001);
			paramJson.put("args", args);
			
			JSONResult jsonResult = commonService.mysqlQuery(paramJson, response);
			if(jsonResult!=null && jsonResult.getLoop()!=null&&jsonResult.getResult()==1){
				List<Map<String,Object>> datas = (List<Map<String, Object>>) jsonResult.getLoop();
				if(datas!=null && datas.size()>0){
					StringBuffer line = null;
					for(int i=0;i<datas.size();i++){
						print.println("时间,开场次数,未开场次,人次,人数,总局数,金币流水,金币发放,金币消耗,实物发放,总手数,Rebuy次数,Addon次数,奖励人数,游戏总时长,游戏币买入次数,资格券买入次数");
						Map<String,Object> map = datas.get(i);
						
						line = new StringBuffer();
						
						double coinsStream = (Double) map.get("coinsStream");
						double sendCoins = (Double) map.get("sendCoins");
						
						int userSum = (Integer) map.get("userSum");
						int rebuyCount = (Integer) map.get("rebuyCount");
						int addonCount = (Integer) map.get("addonCount");
						
						line.append(map.get("tm")).append(",");
						line.append(map.get("gameCount")).append(",");
						line.append(map.get("cancelCount")).append(",");
						line.append(map.get("userSum")).append(",");
						line.append(map.get("userCount")).append(",");
						line.append(map.get("totalPlayCount")).append(",");
						line.append(coinsStream).append(",");
						line.append(sendCoins).append(",");
						line.append(coinsStream-sendCoins).append(",");
						line.append(map.get("sendPhysical")).append(",");
						line.append(userSum+rebuyCount+addonCount).append(",");
						line.append(rebuyCount).append(",");
						line.append(addonCount).append(",");
						line.append(map.get("rewardCount")).append(",");
						line.append(map.get("gameTime")).append(",");
						line.append(map.get("coinsByCount")).append(",");
						line.append(map.get("ticketByCount"));
						
						print.println(line.toString());
						
						//输出详情
						print.println("名称,场次,svid,状态,总报名人数,退赛人数,参加人数,总手数,Rebuy次数,Addon次数,金币流水,金币发放,金币消耗,实物发放,奖励人数,游戏总时长,结束时盲注,游戏币买入次数,资格券买入次数");
						
						args = JSONUtil.getJSONObject();
						args.put("plat", plat);
						args.put("tm", map.get("tm"));
						args.put("table", "d_jinbiaosaiDetail");
						args.put("sort", "sigTime");
						
						paramJson = JSONUtil.getJSONObject();
						paramJson.put("id", 10000001);
						paramJson.put("args", args);
						
						JSONResult _jsonResult = commonService.mysqlQuery(paramJson, response);
						if(_jsonResult!=null && _jsonResult.getLoop()!=null&&_jsonResult.getResult()==1){
							List<Map<String,Object>> _datas = (List<Map<String, Object>>) _jsonResult.getLoop();
							if(_datas!=null && _datas.size()>0){
								for(int j=0;j<_datas.size();j++){
									Map<String,Object> _map = _datas.get(j);
									
									line = new StringBuffer();
									
									Date date = new Date(Long.parseLong(_map.get("sigTime")+"")*1000);
									DateFormat df = new SimpleDateFormat("HH:mm");
									String sigTime = df.format(date);
									
									int _applyCount = (Integer) _map.get("applyCount");
									int _leaveCount = (Integer) _map.get("leaveCount");
									int _rebuyCount = (Integer) _map.get("rebuyCount");
									int _addonCount = (Integer) _map.get("addonCount");
									
									double _coinsStream = (Double) _map.get("coinsStream");
									double _sendCoins = (Double) _map.get("sendCoins");
									
									line.append(_map.get("subname")).append(",");
									line.append(sigTime).append(",");
									line.append(_map.get("svid")).append(",");
									line.append((Integer)_map.get("status")==0?"开场":"未开场").append(",");
									line.append(_applyCount).append(",");
									line.append(_leaveCount).append(",");
									line.append(_applyCount-_leaveCount).append(",");
									line.append(_applyCount-_leaveCount+_rebuyCount+_addonCount).append(",");
									line.append(_map.get("rebuyCount")).append(",");
									line.append(_map.get("addonCount")).append(",");
									line.append(_coinsStream).append(",");
									line.append(_sendCoins).append(",");
									line.append(_coinsStream-_sendCoins).append(",");
									line.append(_map.get("sendPhysical")).append(",");
									line.append(_map.get("rewardCount")).append(",");
									line.append(_map.get("gameTime")).append(",");
									line.append(_map.get("endBlind")).append(",");
									line.append(_map.get("coinsByCount")).append(",");
									line.append(_map.get("ticketByCount"));
									
									print.println(line.toString());
								}
							}
						}
						print.println("");
						print.println("");
					}
					
				}
			}
			
			print.flush();
		} catch (Exception e) {
			errorLogger.error(e.getMessage());
		}finally{
			if(print!=null){
				print.close();
			}
			if(out!=null){
				out.close();
			}
		}
	}
}
