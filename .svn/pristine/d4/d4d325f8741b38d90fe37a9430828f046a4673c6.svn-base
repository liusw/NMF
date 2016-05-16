package com.boyaa.mf.web.controller.data;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boyaa.mf.entity.data.DollTicket;
import com.boyaa.mf.service.config.ConfigService;
import com.boyaa.mf.service.data.DollTicketService;
import com.boyaa.mf.util.CommonUtil;
import com.boyaa.mf.util.PageUtil;
import com.boyaa.mf.web.controller.BaseController;

/**
 *<p>Title: 获取玩偶统计信息的Action类<p>
 *<p>Description: 包括玩偶兑换次数，人数及总数</p>
 *<p>Company: boyaa</p>
 *<p>Date: Apr 20, 2015</p>
 * @author Joakun Chen
 */
@Controller
@RequestMapping(value="data/dollTicket")
public class DollTicketController extends BaseController{
	private static Logger errorLogger = Logger.getLogger("errorLogger");
	
	@Autowired
	private DollTicketService dollTicketService;
	@Autowired
	private ConfigService configService;
	
		/**
	 * 获取数据
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value="getDatas")
	@ResponseBody
	public String getDatas(String bpid,String tm){
		
		if(StringUtils.isBlank(bpid) || StringUtils.isBlank(tm)){
			return getDataTableJson(null, 0).toJSONString();
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		String[] bpidArr = bpid.replaceAll("\"", "").split(",");
		List<String> bpids = Arrays.asList(bpidArr);
		map.put("bpids",bpids);
		map.put("tm", tm.replaceAll("-", ""));
		
		PageUtil<DollTicket> page = dollTicketService.getPageList(map);
		List<DollTicket> dollTickets = page.getDatas();

		if(null != dollTickets && dollTickets.size() != 0){
			Map<String, String> snameMap = configService.getSnamesByBpids(bpids);
			
			for(int i=0; i<dollTickets.size(); i++){
				dollTickets.get(i).setSname(snameMap.get(dollTickets.get(i).getBpid()));
			}
		}
		return getDataTableJson(dollTickets,page.getTotalRecord()).toJSONString();
	}
	
	@RequestMapping(value="exportData")
	@ResponseBody
	public void exportData(String bpid,String tm, HttpServletResponse response) throws IOException{
		
		if(StringUtils.isBlank(bpid) || StringUtils.isBlank(tm)){
			response.getWriter().write("{}");
			return;
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		String[] bpidArr = bpid.replaceAll("\"", "").split(",");
		List<String> bpids = Arrays.asList(bpidArr);
		map.put("bpids",bpids);
		map.put("tm", tm.replaceAll("-", ""));

		List<DollTicket> list = dollTicketService.findScrollDataList(map);
		
		if(list!=null && list.size()>0){
			StringBuffer titleSb = new StringBuffer("站点,日期");
			String[] columns = new String[120];
			
			int tag = 0;
			for(int i=0; i<40; i++){
				columns[tag++] = "clid" + (i + 1) + "_count";
				columns[tag++] = "clid" + (i + 1) + "_persons";
				columns[tag++] = "clid" + (i + 1) + "_amount";
				titleSb.append(",").append("玩偶" + (i + 1) + "兑奖次数").append(",").append("玩偶" + (i + 1) + "兑奖人数").append(",").append("玩偶" + (i + 1) + "兑奖总数");
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
				Map<String, String> snameMap = configService.getSnamesByBpids(bpids);
				
				for(DollTicket dollTicket:list){
					valueSb = new StringBuffer();
					valueSb.append(snameMap.get(dollTicket.getBpid())).append(",").append(dollTicket.getTm());
					
					for(String c : columns){
						Object value = CommonUtil.getFieldValue(dollTicket,c);
						valueSb.append(",").append(value==null?"":value);
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
		response.getWriter().write("参数不正确");
	}
	
}
