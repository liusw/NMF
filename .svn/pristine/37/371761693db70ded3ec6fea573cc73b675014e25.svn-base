package com.boyaa.mf.web.controller.config;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.boyaa.base.utils.Constants;
import com.boyaa.base.utils.JSONUtil;
import com.boyaa.mf.entity.config.Plat;
import com.boyaa.mf.entity.config.Site;
import com.boyaa.mf.service.config.ConfigService;
import com.boyaa.mf.util.CommonUtil;
import com.boyaa.mf.vo.SiteDetailDTO;
import com.boyaa.mf.web.controller.BaseController;
import com.boyaa.mf.web.dto.AjaxObj;
import com.boyaa.servlet.ResultState;

/**
 * 配置文件Controller
 *
 * @作者 : huangyineng
 * @日期 : 2016-3-25  下午5:13:12
 */
@Controller
@RequestMapping("config")
public class ConfigController extends BaseController {
	
	@Autowired
	private ConfigService configService;
	
	@RequestMapping(value = "getRowKeys")
	@ResponseBody
	public JSONObject getRowKeys(){
		Map<String, JSONObject> hbrowkey = Constants.rowKeyRuleMapping;
		if (hbrowkey != null) {
			hbrowkey.put("defaultRowKeyRule", Constants.defaultRowKeyRule);
		}
		
		JSONArray jsonArray = JSONUtil.getJSONArray();
		JSONObject jsonObject = null;
		//遍历Map
		for (Map.Entry<String, JSONObject> entry : hbrowkey.entrySet()) {
			jsonObject = JSONUtil.getJSONObject();
			jsonObject.put("tableName", entry.getKey());
			jsonObject.put("rules", entry.getValue().toString());
			jsonArray.add(jsonObject);
		}
		
		
		JSONObject json = JSONUtil.getJSONObject();
		json.put("draw", 1);
		json.put("recordsTotal", jsonArray.size());
		json.put("recordsFiltered", 10);
		json.put("data", jsonArray);
		
		return json;
	}

	@RequestMapping("getSiteInfo")
	@ResponseBody
	public String getSiteInfo() {
		List<SiteDetailDTO> data = configService.getSiteDetail();
		return getDataTableJson(data, data.size()).toJSONString();
	}
	
	@RequestMapping("getTables")
	@ResponseBody
	public AjaxObj getTables(String tableType,boolean addDefault) {
		AjaxObj data = new AjaxObj(AjaxObj.SUCCESS);
		if(StringUtils.isEmpty(tableType) || "hbase".equals(tableType)){
			data.setObj(configService.getTableNames("hbase",addDefault));
		}else if("hive".equals(tableType)){
			data.setObj(configService.getTableNames("hive",addDefault));
		}
		return data;
	}
	
	@RequestMapping("getHbaseColumns")
	@ResponseBody
	public ResultState getColumn(String tableName,boolean addDefault) {
		return new ResultState(ResultState.SUCCESS,"成功返回",CommonUtil.toJSONString(configService.getHbaseColumns(tableName,addDefault,true)));
	}
	
	/**
	 * 根据平台获取站点信息
	 * @param plat
	 * @return
	 */
	@RequestMapping("getSites")
	@ResponseBody
	public AjaxObj getSitesByPlat(Integer plat) {
		if(null == plat){
			return new AjaxObj(AjaxObj.SUCCESS, "平台ID不能为空");
		}
		
		List<Site> sites = configService.getSites(plat);
		if (sites == null || sites.size() <= 0) {
			return new AjaxObj(AjaxObj.FAILURE, "没有指定的sid");
		}
		
		return new AjaxObj(AjaxObj.SUCCESS,"成功返回",sites);
	}
	
	@RequestMapping("getPlat")
	@ResponseBody
	public List<Plat> getPlat() {
		return configService.getPlat();
	}
}
