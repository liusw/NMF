package com.boyaa.mf.web.controller.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boyaa.mf.constants.Constants;
import com.boyaa.mf.entity.config.GenSql;
import com.boyaa.mf.service.config.GenSqlService;
import com.boyaa.mf.util.CommonUtil;
import com.boyaa.mf.util.PageUtil;
import com.boyaa.mf.web.controller.BaseController;

/**
 * 生成sql表管理
 * @author darcy
 */
@Controller
@RequestMapping(value="config/genSql")
public class GenSqlController extends BaseController {
	
	@Autowired
	private GenSqlService genSqlService;

	@RequestMapping(value="getGenSql")
	@ResponseBody
	public String getGenSql(String tableName,String showName,String hasLog){

		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(tableName)){
			map.put("tableName", tableName);
		}
		if(StringUtils.isNotBlank(showName)){
			map.put("showName", showName);
		}
		int hasLogNum = CommonUtil.isNum(hasLog) ? Integer.parseInt(hasLog) : 0;
		if(hasLogNum > 0){
			map.put("hasLog", hasLogNum);
		}
		map.put("status", Constants.STATUS_EFFACTIVE);
		
		PageUtil<GenSql> page = genSqlService.getPageList(map);
		return getDataTableJson(page.getDatas(),page.getTotalRecord()).toJSONString();
	}
	
	@RequestMapping(value="insertGenSql")
	@ResponseBody
	public int insert(String tableName,String showName,String sqlStr,String sort,String hasLog){
		int sortValue = StringUtils.isNotBlank(sort) ? Integer.parseInt(sort) : 0;
		int hasLogValue = CommonUtil.isNum(hasLog) ? Integer.parseInt(hasLog) : 1;
		
		GenSql genSql = new GenSql();
		genSql.setTableName(tableName);
		genSql.setShowName(showName);
		genSql.setSqlStr(sqlStr);
		genSql.setSort(sortValue);
		genSql.setStatus(Constants.STATUS_EFFACTIVE);
		genSql.setHasLog(hasLogValue);
		return genSqlService.insert(genSql);
	}
	
	@RequestMapping(value="updateGenSql")
	@ResponseBody
	public String update(String tableName,String showName,String sqlStr,String sort,String id,String hasLog){
		GenSql genSql = new GenSql();
		int hasLogTmp = CommonUtil.isNum(hasLog) ? Integer.parseInt(hasLog) : 0;
		
		if(CommonUtil.isNum(id)){
			genSql.setId(Integer.parseInt(id));
			genSql.setTableName(tableName);
			genSql.setShowName(showName);
			genSql.setSqlStr(sqlStr);
			if(CommonUtil.isNum(sort)){
				genSql.setSort(Integer.parseInt(sort));
			}
			if(hasLogTmp > 0){
				genSql.setHasLog(hasLogTmp);
			}
			genSql.setStatus(Constants.STATUS_EFFACTIVE);
			genSqlService.update(genSql);
			
			return id;
		}else{
			return "0";
		}
	}
	
	@RequestMapping(value="deleteGenSql")
	@ResponseBody
	public int delete(Integer id){
		GenSql genSql = new GenSql();
		genSql.setId(id);
		genSql.setStatus(Constants.STATUS_UNEFFACTIVE);
		genSqlService.update(genSql);
		return id;
	}
	
}
