package com.boyaa.servlet.brush;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.boyaa.base.utils.JSONUtil;
import com.boyaa.entity.BrushTemplate;
import com.boyaa.mf.constants.Constants;
import com.boyaa.mf.util.ServletUtil;
import com.boyaa.service.BrushTemplateService;
import com.boyaa.servlet.ResultState;

/**
 * 数分用户模板
 */
public class BrusheTemplateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(BrusheTemplateServlet.class);
	static Logger errorLogger = Logger.getLogger("errorLogger");
	static Logger fatalLogger = Logger.getLogger("fatalLogger");
	private static final String CONTENT_TYPE = "application/x-javascript";
       
    public BrusheTemplateServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding(Constants.DEFAULT_ENCODING);
		response.setContentType(CONTENT_TYPE);
		response.setCharacterEncoding(Constants.DEFAULT_ENCODING);
		logger.info("request ip:" + ServletUtil.getRemortIP(request));
		
		String action = request.getParameter("action");
		if("save".equals(action)){
			this.save(request, response);
		}else if("query".equals(action)){
			this.query(request,response);
		}
	}
	
	/**
	 * 插入或保存模板
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	private void save(HttpServletRequest request, HttpServletResponse response) throws IOException{
		ResultState data = null;
		//获取用户的基本信息
		String userId = (String) request.getSession().getAttribute("code");
		String userName = (String) request.getSession().getAttribute("cname");
		String formData = request.getParameter("formData");
		JSONObject formDataJson = null;
		if(StringUtils.isNotBlank(formData)){
			try {
				formDataJson = JSONUtil.parseObject(formData);
				if(formDataJson != null){
					if(StringUtils.isNotBlank((String) formDataJson.get("plats"))){
						BrushTemplate bt = new BrushTemplate();
						bt.setPlat(formDataJson.getIntValue("plats"));
						bt.setSid(0);
						bt.setName(formDataJson.getString("name"));
						bt.setContent(formDataJson.toString());
						bt.setVersion(Constants.BRUSH_TEMPLATE_VERSION);
						bt.setId(formDataJson.getIntValue("templateId"));
						try{
							bt.setUid(Integer.parseInt(userId));
						}catch(Exception e){
							bt.setUid(0);
							errorLogger.error(e.getMessage());
						}
						bt.setUname(userName);
						BrushTemplateService btService = null;
						try {
							btService = new BrushTemplateService();
							int id = 0;
							if(bt.getId() > 0){
								btService.update(bt);
							}else{
								id = btService.insert(bt);
							}
							data = new ResultState(ResultState.SUCCESS);
							data.setData(String.valueOf(id));
						} catch (SQLException e) {
							errorLogger.error(e.getMessage());
							data = new ResultState(ResultState.FAILURE);
							data.setMsg(e.getMessage());
						}finally{
							btService.close();
						}
					}
				}
			} catch (JSONException e) {
				errorLogger.error(e.getMessage());
				data = new ResultState(ResultState.FAILURE);
				data.setMsg(e.getMessage());
			}
		}else{
			data = new ResultState(ResultState.FAILURE);
			data.setMsg("没有参数!");
		}
		ServletUtil.responseRecords(response, null, data);
	}
	
	private void query(HttpServletRequest request,HttpServletResponse response) throws IOException{
		ResultState data = null;
		String plat = request.getParameter("plat");
		if(StringUtils.isNotBlank(plat)){
			BrushTemplate bt = new BrushTemplate();
			try{
				bt.setPlat(Integer.parseInt(plat));
			}catch(Exception e){
				errorLogger.error(e.getMessage());
			}
			BrushTemplateService btService = null;
			try {
				btService = new BrushTemplateService();
				JSONArray result= btService.query(bt);
				data = new ResultState(ResultState.SUCCESS);
				if(result != null && result.size()>0){
					data.setData(result.toString());
				}else{
					data.setMsg("没有找到相关数据!");
				}
			} catch (SQLException e) {
				errorLogger.error(e.getMessage());
				data = new ResultState(ResultState.FAILURE);
				data.setMsg(e.getMessage());
			}finally{
				btService.close();
			}
		}else{
			data = new ResultState(ResultState.FAILURE);
			data.setMsg("plat不能为空!");
		}
		ServletUtil.responseRecords(response, null, data);
	}
}
