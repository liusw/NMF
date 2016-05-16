package com.boyaa.mf.web.controller.data;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.boyaa.base.utils.JSONUtil;
import com.boyaa.entity.common.JSONResult;
import com.boyaa.mf.web.controller.BaseController;
import com.boyaa.service.CommonService;

@Controller
public class VmoneyTopController extends BaseController {

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "data/vmoneyTop/exportData")
	@ResponseBody
	public void exportData(String column, String params,
			HttpServletResponse response) throws IOException {

		if (StringUtils.isBlank(params)) {
			response.getWriter().write("参数不正确");
			return;
		}

		OutputStream out = null;
		OutputStreamWriter writer = null;
		PrintWriter print = null;
		try {

			JSONObject paramJson = JSONUtil.parseObject(params);
			if (paramJson == null || !paramJson.containsKey("id")) {
				response.getWriter().write("参数为空或ID不存在");
				return;
			}
			CommonService commonService = new CommonService();
			JSONResult jsonResult = commonService.mysqlQuery(paramJson,
					response);

			if (jsonResult != null && jsonResult.getLoop() != null) {
				List<Map<String, Object>> array = (List<Map<String, Object>>) jsonResult.getLoop();

				StringBuffer titleSb = new StringBuffer(
						"日期,平台,PC／移动,mid,昵称,台费,当天储值($)");
				List<String> columns = new ArrayList<String>();
				if (StringUtils.isNotBlank(column)) {
					try {
						JSONArray jsonArray = JSONUtil.parseArray(column);
						for (int i = 0; i < jsonArray.size(); i++) {
							JSONObject jsonObject = jsonArray.getJSONObject(i);
							String vmoney = jsonObject.getString("vmoney");
							columns.add("s_" + vmoney);
							columns.add("c_" + vmoney);
							titleSb.append(",").append(vmoney).append("台费")
									.append(",").append(vmoney).append("牌局");
						}
					} catch (Exception e) {
						errorLogger.error(e.getMessage());
					}
				}

				response.setContentType("application/octet-stream");
				// 设置response的头信息
				response.setHeader("Content-disposition",
						"attachment;filename=\"" + new Date().getTime()
								+ ".csv\"");

				out = response.getOutputStream();
				writer = new OutputStreamWriter(out, "UTF-8");
				print = new PrintWriter(writer);

				print.println(titleSb.toString());

				StringBuffer valueSb = null;
				// for(VmoneyTop vmoneyTop:list){
				Map<String, Object> map = null;
				for (int i = 0; i < array.size(); i++) {
					map = array.get(i);

					valueSb = new StringBuffer();
					valueSb.append(map.get("tm"))
							.append(",")
							.append(map.get("_plat"))
							.append(",")
							.append((map.get("ismobile").toString().equals("1")) ? "移动"
									: "PC").append(",").append(map.get("_uid"))
							.append(",").append(map.get("mnick")).append(",")
							.append(map.get("total")).append(",")
							.append(map.get("pay"));

					if (columns != null && columns.size() > 0) {
						for (String c : columns) {
							Object value = map.get(c);
							valueSb.append(",").append(
									value == null ? "" : value);
						}
					}
					print.println(valueSb);
				}
				print.flush();
			}
			return;
		} catch (Exception e) {
			errorLogger.error(e.getMessage());
		} finally {
			if (print != null) {
				print.close();
			}
			if (writer != null) {
				writer.close();
			}
			if (out != null) {
				out.close();
			}
		}
		response.getWriter().write("参数不正确");
	}

	public String strDateStr(String dateStr, String oldFormat, String newFormat) {
		try {
			DateFormat df = new SimpleDateFormat(oldFormat);
			Date date = df.parse(dateStr);
			df = new SimpleDateFormat(newFormat);
			return df.format(date);
		} catch (ParseException e) {
			errorLogger.error(e.getMessage());
		}
		return null;
	}
}
