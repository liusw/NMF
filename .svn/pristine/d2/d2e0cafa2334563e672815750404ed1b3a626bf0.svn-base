package com.boyaa.mf.web.controller.data;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.select.Select;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.boyaa.mf.web.controller.BaseController;
import com.boyaa.mf.web.dto.AjaxObj;
import com.boyaa.service.hbase.parser.SelectSqlVisitor;
import com.boyaa.service.hbase.parser.SqlParserManager;
import com.boyaa.service.hbase.query.HBaseDataQuery;

@Controller
@RequestMapping(value="data/hbase")
public class HBaseController extends BaseController{
	static Logger taskLogger = Logger.getLogger("taskLogger");
	
	private AjaxObj getDate(HttpServletRequest request){
		String sql = request.getParameter("sql");
		String pageIndex = request.getParameter("pageIndex");
		String pageSize = request.getParameter("pageSize");
		if(StringUtils.isBlank(sql)){
			return new AjaxObj(AjaxObj.FAILURE, "sql不能为空");
		}
		
		try {
			CCJSqlParserManager parserManager = SqlParserManager.getInstance();
			Select select = (Select) parserManager.parse(new StringReader(sql));
			SelectSqlVisitor sqlVisitor = new SelectSqlVisitor(select);
			if(StringUtils.isBlank(sqlVisitor.getTable()) || sqlVisitor.getRowkeyJson()==null){
				return new AjaxObj(AjaxObj.FAILURE, "查询语句不正确");
			}
			
			HBaseDataQuery hbaseDataQuery = new HBaseDataQuery();
			int page = 1,size=10;
			if(StringUtils.isNotBlank(pageIndex) && Integer.parseInt(pageIndex)>0){
				page = Integer.parseInt(pageIndex);
			}
			if(StringUtils.isNotBlank(pageSize) && Integer.parseInt(pageSize)>0){
				size = Integer.parseInt(pageSize);
			}
			JSONObject json = hbaseDataQuery.selectQuery(sqlVisitor,page,size);
			
			return new AjaxObj(AjaxObj.SUCCESS,"",json);
		} catch (JSQLParserException e) {
			errorLogger.error(e.getMessage());
			return new AjaxObj(AjaxObj.FAILURE, "语句解析失败");
		}
	}

	@RequestMapping(value="query")
	@ResponseBody
	public AjaxObj query() throws IOException, JSONException{
		return getDate(getRequest());
	}
	
	@RequestMapping(value="download")
	public void download(HttpServletResponse response,String title,String column) throws IOException, JSONException{
		
		AjaxObj ajaxObj = getDate(this.getRequest());
		if(ajaxObj.getStatus()==AjaxObj.SUCCESS){
			//JSONObject jsonObject = JSONUtil.parseObject(resultState.getData());
			JSONObject jsonObject = (JSONObject) ajaxObj.getObj();
			
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
				
				if(jsonObject!=null && jsonObject.containsKey("o") && jsonObject.containsKey("title") && jsonObject.getJSONObject("title").size()>0){
					JSONArray array = jsonObject.getJSONArray("o");
					if(StringUtils.isBlank(column)){
						column = getDefaultColumn(jsonObject.getJSONObject("title"));
					}
					if(StringUtils.isBlank(title)){
						title = getDefaultColumn(jsonObject.getJSONObject("title"));
					}
					print.println(title);
					
					if(array!=null && array.size()>0){
						String[] titleArray = column.split(",");//按标题遍历
						
						JSONObject json = null;
						StringBuffer sb = null;
						for(int i=0;i<array.size();i++){
							sb = new StringBuffer();
							json = array.getJSONObject(i);
							
							int length = titleArray.length;
							for(int j=0;j<length;j++){
								String _column = titleArray[j];
								if(json.containsKey(_column)){
									sb.append(json.getString(_column));
								}else{
									sb.append("");
								}
								sb.append(j==length-1?"":",");
							}
							print.println(sb.toString());
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
				if(writer!=null){
					writer.close();
				}
				if(out!=null){
					out.close();
				}
			}
		}
	}

	private String getDefaultColumn(JSONObject jsonObject) throws JSONException {
		String title;
		StringBuffer sb = new StringBuffer();
		Iterator<String> it = jsonObject.keySet().iterator();
		while(it.hasNext()) {
			sb.append(it.next()).append(",");
		}
		title = sb.substring(0, sb.length()-1);
		return title;
	}
}
