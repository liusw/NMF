package com.boyaa.mf.web.controller.data;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.boyaa.base.utils.JSONUtil;
import com.boyaa.mf.entity.data.CgCoinsTop;
import com.boyaa.mf.service.data.CgCoinsTopService;
import com.boyaa.mf.util.CommonUtil;
import com.boyaa.mf.util.PageUtil;
import com.boyaa.mf.web.controller.BaseController;

/**
 *<p>Title: 处理赢钱排行榜的Action<p>
 *<p>Description: 包括查询默认日期，指定日期的排行榜；导出数据</p>
 *<p>Company: boyaa</p>
 *<p>Date: Apr 10, 2015</p>
 * @author Joakun Chen
 */

@Controller
@RequestMapping(value="data/cgCoinsTop")
public class CgCoinsTopController extends BaseController{
	static Logger logger = Logger.getLogger(CgCoinsTopController.class);
	
	@Autowired
	private CgCoinsTopService cgCoinsTopService;

	/**
	 * 获取数据
	 */
	@RequestMapping(value="getDatas")
	@ResponseBody
	public String getDatas(String startTime,Integer plat,Integer sid){
		
		if(StringUtils.isBlank(startTime) || plat==null || sid==null){
			return getDataTableJson(null, 0).toJSONString();
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startTime", startTime.replaceAll("-", ""));
		map.put("plat", plat);
		map.put("sid", sid);
		
		PageUtil<CgCoinsTop> page = cgCoinsTopService.getPageList(map);
		return getDataTableJson(page.getDatas(), page.getTotalRecord()).toJSONString();
			
	}
	
	@RequestMapping(value="exportData")
	@ResponseBody
	public void exportData(String startTime,Integer plat,Integer sid,String column,HttpServletResponse response) throws IOException{
		
		if(StringUtils.isBlank(startTime) || plat==null || sid==null){
			response.getWriter().write("参数不正确");
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startTime", startTime.replaceAll("-", ""));
		map.put("plat",plat);
		map.put("sid", sid);
		
		List<CgCoinsTop> list = cgCoinsTopService.findScrollDataList(map);
		
		if(list!=null && list.size()>0){
			StringBuffer titleSb = new StringBuffer("排名,日期,平台,站点,用户ID,昵称,赢钱总数");
			List<String> columns = new ArrayList<String>();
			if(StringUtils.isNotBlank(column)){
				try {
					JSONArray jsonArray = JSONUtil.parseArray(column);
					for(int i=0;i<jsonArray.size();i++){
						JSONObject jsonObject = jsonArray.getJSONObject(i);
						String blindmin = jsonObject.getString("blindmin");
						columns.add("bgCount_"+blindmin);
						columns.add("cgCoins_"+blindmin);
						titleSb.append(",").append(blindmin).append("局数").append(",").append(blindmin).append("赢钱数");
					}
				} catch (Exception e) {
					errorLogger.error(e.getMessage());
				}
			}
			
			response.setContentType("application/octet-stream");
			// 设置response的头信息
			response.setHeader("Content-disposition", "attachment;filename=\"" + new Date().getTime() + ".csv\"");
			
			OutputStream out = null;
			OutputStreamWriter writer = null;
			PrintWriter print = null;
			try {
				out = response.getOutputStream();
				writer = new OutputStreamWriter(out, "UTF-8");
				print = new PrintWriter(writer);
				
				print.println(titleSb.toString());
				
				StringBuffer valueSb = null;
				int rankNo = 1;
				for(CgCoinsTop cgCoinsTop:list){
					valueSb = new StringBuffer();
					valueSb.append(rankNo++).append(",").append(cgCoinsTop.getTm()).append(",").append(cgCoinsTop.getPlat()).append(",").append(cgCoinsTop.getSid())
							.append(",").append(cgCoinsTop.getUid()).append(",").append(cgCoinsTop.getMnick()).append(",").append(cgCoinsTop.getCgCoinsTotal());
					
					if(columns!=null && columns.size()>0){
						for(String c : columns){
							Object value = CommonUtil.getFieldValue(cgCoinsTop,c);
							valueSb.append(",").append(value==null?"":value);
						}
					}
					print.println(valueSb);
				}
				print.flush();
			} catch (Exception e) {
				errorLogger.error(e.getMessage());
			}finally{
				if(print!=null){
					print.close();
				}
				if(writer!=null){
					writer.close();
				}
				if(out!=null){
					out.close();
				}
			}
			return;
		}
		response.getWriter().write("没数据");
	}
		
}
