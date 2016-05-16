package com.boyaa.mf.service.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.boyaa.base.listener.HbtablesFileListener;
import com.boyaa.base.utils.JSONUtil;
import com.boyaa.mf.constants.Constants;
import com.boyaa.mf.entity.config.Column;
import com.boyaa.mf.entity.config.GenSql;
import com.boyaa.mf.entity.config.Htable;
import com.boyaa.mf.entity.config.Plat;
import com.boyaa.mf.entity.config.Site;
import com.boyaa.mf.util.SpringBeanUtils;
import com.boyaa.mf.vo.SiteDetailDTO;

@Service
public class ConfigService {
	static Logger logger = Logger.getLogger(ConfigService.class);
	static Logger errorLogger = Logger.getLogger("errorLogger");
	static Logger fatalLogger = Logger.getLogger("fatalLogger");

	private static String defaultFileName = "htabledefault";
	
	public List<Plat> getPlat(){
		return getPlat(null);
	}
	
	/**
	 * 获取平台列表
	 */
	public List<Plat> getPlat(Integer siteId) {
		List<Plat> plats = null;
		
		String data = getMetaData(siteId);
		if (StringUtils.isNotBlank(data)) {
			try {
				plats = new ArrayList<Plat>();
				Plat plat = null;

				JSONArray jsonArray = JSONUtil.parseArray(data);
				for (int i = 0; i < jsonArray.size(); i++) {
					
					JSONObject jsonObject = jsonArray.getJSONObject(i);

					if (jsonObject.containsKey("_plat") && jsonObject.containsKey("title") && jsonObject.get("_plat") != null && jsonObject.get("title") != null && 
						 jsonObject.containsKey("msid") && jsonObject.containsKey("sid") && jsonObject.getIntValue("msid")==jsonObject.getIntValue("sid")) {
						plat = new Plat();
						plat.setPlat(jsonObject.getIntValue("_plat"));
						plat.setPlatName(jsonObject.getString("title"));

						if (!jsonObject.containsKey("svid") || jsonObject.get("svid") == null) {
							plat.setSvid(jsonObject.getIntValue("_plat"));
						} else {
							plat.setSvid(jsonObject.getIntValue("svid"));
						}
						
						if(plats.contains(plat)){
							continue;
						}
						plats.add(plat);
					}
				}
			} catch (JSONException e) {
				errorLogger.info(e.getMessage());
			}
		}
		
		//把作废的排在后面
		Collections.sort(plats);
		
		return plats;
	}
	
	
	public Map<Integer, String> getPlatNameMap(){
		Map<Integer, String> map = new HashMap<Integer, String>();
		List<Plat> list = getPlat();
		if(list!=null && list.size()>0){
			for(Plat plat : list){
				map.put(plat.getPlat(), plat.getPlatName());
			}
		}
		return map;
	}
	
	/**
	 * 根据平台ID获取sid列表
	 */
	public List<Site> getSites(int plat) {
		return getSites(plat,null);
	}
	
	/**
	 * 根据平台ID和平台编号获取站点信息
	 * @param plat
	 * @param siteId
	 * @return
	 */
	public List<Site> getSites(int plat,Integer siteId) {
		List<Site> sites = null;
		
		String data = getMetaData(siteId);
		if (StringUtils.isNotBlank(data)) {
			try {
				sites = new ArrayList<Site>();
				Site site = null;

				JSONArray jsonArray = JSONUtil.parseArray(data);
				for (int i = 0; i < jsonArray.size(); i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					
					if(jsonObject.containsKey("_plat") && jsonObject.get("_plat")!=null){
						try{
							if(jsonObject.getIntValue("_plat")!=plat){
								continue;
							}
						}catch (Exception e){
							continue;
						}
					}else{
						continue;
					}

					if (jsonObject.containsKey("sid") && jsonObject.containsKey("vname")
							&& jsonObject.containsKey("_bpid")
							&& jsonObject.get("sid") != null 
							&& jsonObject.get("vname") != null
							&& jsonObject.get("_bpid") != null) {

						site = new Site();
						site.setSid(jsonObject.getIntValue("sid"));
						site.setSname(jsonObject.getString("vname"));
						site.setBpid(jsonObject.getString("_bpid"));
						site.setIsmobile(jsonObject.containsKey("ismobile")?jsonObject.getIntValue("ismobile"):0);

						sites.add(site);
					}
				}
			} catch (JSONException e) {
				errorLogger.info(e.getMessage());
			}
		}
		return sites;
	}

	public List<SiteDetailDTO> getSiteDetail(){
		List<SiteDetailDTO> sitDetailDTOs = new ArrayList<SiteDetailDTO>();
		
		List<Plat> plats = getPlat();
		
		for(Plat plat : plats){
			for(Site site : getSites(plat.getPlat())){
				SiteDetailDTO dTO = new SiteDetailDTO();
				dTO.setPlat(plat.getPlat());
				dTO.setpName(plat.getPlatName());
				dTO.setBpid(site.getBpid());
				dTO.setSid(site.getSid());
				dTO.setSname(site.getSname());
				dTO.setSvid(plat.getSvid());
				dTO.setIsmobile(site.getIsmobile()==1?"是":"否");
				sitDetailDTOs.add(dTO);
			}
		}
		
		return sitDetailDTOs;
	}

	/**
	 * 根据bpid集合获取站点名称集合
	 * @param bpids bpid List集合
	 * @return Map<String, String> key为bpid,value为站点名称
	 */
	public Map<String, String> getSnamesByBpids(List<String> bpids) {
		if(null == bpids || bpids.size() == 0){
			logger.warn("Cant get site names because bpids is null.");
			return null;
		}
		
		Map<String, String> bpidSNMap = null;
		
		String data = getMetaData(null);
		if (StringUtils.isNotBlank(data)) {
			bpidSNMap = new HashMap<String, String>();
			try {
				JSONArray jsonArray = JSONUtil.parseArray(data);
					
				for (int i = 0; i < jsonArray.size(); i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);

					if (jsonObject.containsKey("_bpid") && null != jsonObject.get("_bpid")){
						String fbpid = String.valueOf(jsonObject.get("_bpid"));
						if(bpids.contains(fbpid)){
							bpidSNMap.put(fbpid, jsonObject.getString("vname"));
						}
					}
				}
			} catch (JSONException e) {
				errorLogger.info(e.getMessage());
			}
		}
		
		return bpidSNMap;
	}
	
	/**
	 * 
	 * @param tableType
	 * @param addDefault
	 *            是否需求输出默认表
	 * @return
	 */
	public List<Htable> getTableNames(String tableType,boolean addDefault) {
		if (StringUtils.isBlank(tableType)) {
			return null;
		}
		
		List<Htable> htables = new ArrayList<Htable>();
		Htable htable = null;
		
		if(tableType.equals("hive")) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("status", Constants.STATUS_EFFACTIVE);
			GenSqlService genSqlService = SpringBeanUtils.getBean("genSqlService",GenSqlService.class);
			List<GenSql> list = genSqlService.findScrollDataList(map);
			
			if(list!=null && list.size()>0){
				for(GenSql genSql : list){
					htable = new Htable(genSql.getTableName(), genSql.getShowName()+"["+genSql.getTableName()+"]");
					htables.add(htable);
				}
			}
			
		}else if(tableType.equals("hbase")){
			Map<String, String> tables = HbtablesFileListener.tableNames;
			if (addDefault) {
				htable = new Htable(defaultFileName, "默认表");
				htables.add(htable);
			}
			Iterator<String> it = tables.keySet().iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				htable = new Htable(key, tables.get(key));
				htables.add(htable);
			}
		}
		return htables;
	}

	/**
	 * 根据类型查看表结构
	 * 
	 * @param tableType
	 *            hbase,hive
	 */
	public List<Htable> getTableNames(String tableType) {
		return getTableNames(tableType, true);
	}

	/**
	 * 获取列信息
	 * 
	 * @param tableName
	 *            表名
	 * @param addDefault
	 *            是否把默认表的字段添加到其他表中
	 */
	public List<Column> getHbaseColumns(String tableName,boolean addDefault,boolean filterNotShowColumn) {
		Map<String, Map<String, String[]>> tablesFiles = null;
		Map<String, String[]> tablesDefaultFile = null;
		Map<String, Map<String, String>> analysisDescs = null;
		Map<String, String> analysisDefaultDesc = null;
		
		//要过滤掉的字段
		List<String> filterColumn = null;

		tablesFiles = HbtablesFileListener.hbtableFiles;
		tablesDefaultFile = HbtablesFileListener.hbtableDefaultFile;
		analysisDescs = HbtablesFileListener.hbtableDescs;
		analysisDefaultDesc = HbtablesFileListener.hbtableDefaultDesc;
		if(filterNotShowColumn){
			filterColumn = getFilterColumn("hbase",tableName);
		}

		if (tablesFiles != null) {
			tablesFiles.put(defaultFileName, tablesDefaultFile);
		}
		if (analysisDescs != null) {
			analysisDescs.put(defaultFileName, analysisDefaultDesc);
		}

		List<Column> rcolumns = new ArrayList<Column>();
		Column column = null;

		Map<String, String[]> columns = tablesFiles.get(tableName);
		Map<String, String> columnsdesc = analysisDescs.get(tableName);
		
		if(columns == null){
			return rcolumns;
		}

		if (addDefault)
			columns.putAll(tablesDefaultFile);
		if (columnsdesc != null && addDefault)
			columnsdesc.putAll(analysisDefaultDesc);

		Iterator<String> it = columns.keySet().iterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			String[] typeValue = columns.get(key);
			
			if(filterColumn!=null && filterColumn.contains(key)){
				continue;
			}

			column = new Column();
			column.setName(key);
			if (columnsdesc != null) {
				column.setDesc(columnsdesc.get(key));
			}

			if (typeValue != null && typeValue.length > 0) {
				column.setTypeValue(typeValue[0]);
				column.setDefaultValue(typeValue.length >= 2 ? typeValue[1]: "");
			}

			rcolumns.add(column);
		}
		return rcolumns;
	}
	
	/**
	 *  获取指定表的过滤字段
	 * @param tableType
	 * @param tableName
	 * @return
	 */
	private List<String> getFilterColumn(String tableType,String tableName) {
			try {
				String data = Constants.FILTER_COLUMN;
				if (StringUtils.isNotBlank(data)) {
					JSONObject json = JSONUtil.parseObject(data);
					if(json.containsKey(tableType)){
						List<String> list = new ArrayList<String>();
						JSONObject tables = json.getJSONObject(tableType);
						
						if(tables.containsKey("htabledefault")){
							String columns = tables.getString("htabledefault");
							list.addAll(Arrays.asList(columns.split(",")));
						}
						
						if(tables.containsKey(tableName)){
							String columns = tables.getString(tableName);
							list.addAll(Arrays.asList(columns.split(",")));
						}
						return list;
					}
				}
			} catch (Exception e) {
				errorLogger.info(e.getMessage());
			}
		return null;
	}
	
	/**
	 * 获取站点配置文件
	 * @param siteId
	 * @return
	 */
	public String getMetaData(Integer siteId){
		//Map<String,String> meta_values_mapping = com.boyaa.base.utils.Constants.meta_values_mapping;
		Map<String,String> meta_values_mapping = initMapping();
		
		if(meta_values_mapping==null || meta_values_mapping.size()==0){
			return null;
		}
		
		if(siteId!=null){
			return meta_values_mapping.get("meta"+siteId+".ini");
		}else{
			JSONArray plats = JSONUtil.getJSONArray();
			JSONArray jsonArray = null;
			JSONObject jsonObject = null;
			Iterator<String> it = meta_values_mapping.keySet().iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				String ps = meta_values_mapping.get(key);
				
				try {
					jsonArray = JSONUtil.parseArray(ps);
					for(int i=0;i<jsonArray.size();i++){
						jsonObject = jsonArray.getJSONObject(i);
						plats.add(jsonObject);
					}
				} catch (JSONException e) {
					errorLogger.error(e.getMessage());
				}
			}
			
			return plats.toString();
		}
	}

	/**
	 * 把线上的正在用的配置文件和回收站的配置文件合并
	 * @return
	 */
	private Map<String, String> initMapping() {
		Map<String,String> meta_mapping= new HashMap<String,String>();
		Map<String,String> meta_values_mapping = com.boyaa.base.utils.Constants.meta_values_mapping;
		Map<String,String> trash_meta_values_mapping = Constants.trash_meta_values_mapping;
		
		if(meta_values_mapping==null || meta_values_mapping.size()==0){
			return meta_mapping;
		}
		meta_mapping.putAll(meta_values_mapping);
		
		if(trash_meta_values_mapping==null || trash_meta_values_mapping.size()==0){
			return meta_mapping;
		}
		
		JSONArray jsonArray = null;
		JSONArray tempJsonArray = null;
		JSONObject jsonObject = null;
		Iterator<String> it = trash_meta_values_mapping.keySet().iterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			String ps = trash_meta_values_mapping.get(key);
			
			try {
				jsonArray = JSONUtil.parseArray(ps);
				
				String name = key.replaceAll("_old", "");
				if(!meta_values_mapping.containsKey(name)){
					meta_values_mapping.put(key, ps);
					continue;
				}
				
				tempJsonArray = JSONUtil.parseArray(meta_values_mapping.get(name));
				
				for(int i=0;i<jsonArray.size();i++){
					jsonObject = jsonArray.getJSONObject(i);
					
					//判断此平台是否有这个回收的站点,如果不存在,再加进去
					boolean isAddTrashSite = true;
					for(int j=0;j<tempJsonArray.size();j++){
						if(jsonObject.getString("_bpid").equals(tempJsonArray.getJSONObject(i).getString("_bpid"))){
							isAddTrashSite = false;
							break;
						}
					}
					
					if(isAddTrashSite){
						tempJsonArray.add(jsonObject);
					}
				}
				meta_mapping.put(name, tempJsonArray.toJSONString());
			} catch (JSONException e) {
				errorLogger.error(e.getMessage());
			}
		}
		
		return meta_mapping;
	}
}