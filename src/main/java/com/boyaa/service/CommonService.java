package com.boyaa.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.boyaa.dao.CommonDao;
import com.boyaa.entity.common.JSONResult;
import com.boyaa.entity.common.TableJSONResult;
import com.boyaa.mf.constants.Constants;
import com.boyaa.mf.util.PageUtil;
import com.boyaa.mf.util.SystemContext;
import com.boyaa.servlet.ResultState;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class CommonService {
	static Logger logger = Logger.getLogger(CommonService.class);
	static Logger errorLogger = Logger.getLogger("errorLogger");
	static Logger fatalLogger = Logger.getLogger("fatalLogger");
	
	public Map<String,Object> getMapByNamespace(String namespace,Map<String, Object> params){
		CommonDao commonDao = new CommonDao();

		if(params==null || params.size()<=0){
			params = new HashMap<String, Object>();
		}
		if(!params.containsKey("start")){
			params.put("start",0);
		}
		if(!params.containsKey("size")){
			params.put("size", 1);
		}
		return commonDao.getMapByNameSpace(Constants.defaultNamespace+namespace,params);
	}
	
	public List<Map<String,Object>> getListByNamespace(String listNamespace,Map<String, Object> params){
		CommonDao commonDao = new CommonDao();
		
		if(params==null || params.size()<=0){
			params = new HashMap<String, Object>();
		}
		if(!params.containsKey("start")){
			params.put("start",0);
		}
		if(!params.containsKey("size")){
			params.put("size", Integer.MAX_VALUE);
		}
		List<Map<String,Object>> list = commonDao.getListByNameSpace(Constants.defaultNamespace+listNamespace,params);
		
		return list;
	}
	
	@SuppressWarnings("all")
	public TableJSONResult getDataTabelJson(String listNamespace,String countNamespace,Map<String, Object> params) throws JSONException{
		CommonDao commonDao = new CommonDao();
		
		int totalCount = commonDao.getCountByNamespace(Constants.defaultNamespace+countNamespace,params);
		
		PageUtil page = new PageUtil(SystemContext.getPageSize(),totalCount, SystemContext.getPageIndex());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", page.getStart());
		map.put("size", page.getPageRecord());
		if (params != null && !params.isEmpty()){
			map.putAll(params);
		}
		List<Map<String,Object>> objList = commonDao.getListByNameSpace(Constants.defaultNamespace+listNamespace,map);
		page.setDatas(objList);
		
		TableJSONResult tableJSONResult = new TableJSONResult();
		tableJSONResult.setResult(Constants.RESULT_SUCCESS);
		tableJSONResult.setData(page.getDatas());
		tableJSONResult.setITotalRecords(page.getTotalRecord());
		tableJSONResult.setITotalDisplayRecords(page.getTotalRecord());
		
		return tableJSONResult;
	}

	public JSONResult mysqlQuery(JSONObject paramJson,HttpServletResponse response) throws IOException, JSONException{
		Map<String, Object> params = null;
		//拼接参数
		if(paramJson.containsKey("args")){
			JSONObject args = paramJson.getJSONObject("args");
			params = new HashMap<String, Object>();
			
			Iterator<String> it = args.keySet().iterator();
			while(it.hasNext()) {
				String key = it.next();
				
				/*处理一个字段有多个取值*/
				Object obj = args.get(key);
				if(obj instanceof JSONArray){
					JSONArray valArr = args.getJSONArray(key);
					List<Object> valList = new ArrayList<Object>();
					if(null != valArr){
						for (int i=0; i<valArr.size(); i++) {
							String valObj = valArr.get(i).toString();
							try {
								valList.add(Integer.parseInt(valObj));
							} catch (NumberFormatException e) {
								try {
									valList.add(Long.parseLong(valObj));
								} catch (NumberFormatException e2) {
									valList.add(valObj);
								}
							}
						}
						
						params.put(key,valList);
						continue;
					}
				}
				String sglVal = args.get(key).toString();
				try {
					params.put(key,Integer.parseInt(sglVal));
				} catch (NumberFormatException e) {
					try {
						params.put(key,Long.parseLong(sglVal));
					} catch (NumberFormatException e2) {
						params.put(key,sglVal);
					}
				}
			}
		}
		
		String paramId = paramJson.getString("id");
		if(paramJson.containsKey("dataType") && "dataTable".equals(paramJson.getString("dataType"))){
			String[] ids = paramId.split("\\|");
			if(ids.length!=2){
				TableJSONResult tableJSONResult = new TableJSONResult();
				tableJSONResult.setResult(Constants.RESULT_FAILURE);
				tableJSONResult.setMsg("Id包含的数字数目应为2,以|分开");
				return tableJSONResult;
			}
			String sort = paramJson.getString("sort");
			String order = paramJson.getString("order");
			if(StringUtils.isNotBlank(sort)){
				params.put("sort",sort);
				if(StringUtils.isNotBlank(order)){
					params.put("order",order);
				}else{
					params.put("order","desc");
				}
			}
			return getDataTabelJson(ids[0],ids[1],params);
		}else if(paramJson.containsKey("dataType") && "singleObj".equals(paramJson.getString("dataType"))){
			return new JSONResult(Constants.RESULT_SUCCESS,"正确返回",getMapByNamespace(paramId, params));
		}else{//直接返回一个mysql查询json
			return new JSONResult(Constants.RESULT_SUCCESS,"正确返回",getListByNamespace(paramId,params));
		}
	}

	/**
	 * 插入MySQL
	 * 
	 * @param params
	 *            参数params的key一般有以下几个 
	 *            columns:插入的列,以,号分开 此参数可选
	 *            table:插入的表名,此参数必带
	 *            values:插入值的key-value对,此参数必带.此key对应的value必须是Map<String,Object>类型,key为数据库字段名称,或者和columns的值相对应,value为插入值
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ResultState insertMySQL(String namespaceId,Map<String, Object> params) {
		if(params==null || !params.containsKey("table") || !params.containsKey("values") || params.get("table")==null || params.get("values")==null){
			return new ResultState(ResultState.FAILURE, "传入参数不正确");
		}
		
		List<Object> vs = new ArrayList<Object>();
		
		Map<String,Object> values = (Map<String, Object>) params.get("values");
		
		if(params.containsKey("columns") && StringUtils.isNotBlank((String) params.get("columns"))){//如果包含列,要把values按照columns的顺序设置为List
			String columns = (String) params.get("columns");
			String[] carray = columns.split(",");
			
			for(int i=0;i<carray.length;i++){
				Iterator<String> it = values.keySet().iterator();
				while (it.hasNext()) {
					String key = (String) it.next();
					
					if(key.equals(carray[i])){
						vs.add(values.get(key));
						break;
					}
				}
			}
		}else{
			Iterator<String> it = values.keySet().iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				vs.add(values.get(key));
			}
		}
		
		Map<String,Object> insertParams = new HashMap<String, Object>();
		insertParams.put("table",params.get("table"));
		insertParams.put("columns",params.get("columns"));
		insertParams.put("values",vs);
		
		CommonDao commonDao = new CommonDao();
		
		int result=0;
		try {
			result = commonDao.insertByNamespace(Constants.defaultNamespace+namespaceId,insertParams);
		} catch (Exception e) {
			errorLogger.error(e.getMessage());
			return new ResultState(ResultState.FAILURE, "添加失败", e.getMessage());
		}
		
		return result>0?new ResultState(ResultState.SUCCESS):new ResultState(ResultState.FAILURE, "添加失败");
	}
	
	/**
	 * 这种方法提供给insert .... select ... from ....[where ...]
	 * @param namespaceId
	 * @return
	 */
	public ResultState insertMySQL(String namespaceId) {
		CommonDao commonDao = new CommonDao();
		int result = commonDao.insertByNamespace(Constants.defaultNamespace+namespaceId,null);
		return result>0?new ResultState(ResultState.SUCCESS):new ResultState(ResultState.FAILURE, "添加失败");
	}
	
	public ResultState updateMySQL(String namespaceId,Map<String,Object> params) {
		CommonDao commonDao = new CommonDao();
		
//		Map<String,Object> param = new HashMap<String, Object>();
//		
//		Map<String,Object> set = new HashMap<String, Object>();
//		set.put("name", "1111");
//		param.put("set", set);
//		
//		Map<String,Object> where = new HashMap<String, Object>();
//		where.put("id", 9);
//		param.put("where", where);
//		
//		param.put("table", "browserRule");
		
		int result = commonDao.updateByNamespace(Constants.defaultNamespace+namespaceId,params);
		return result>0?new ResultState(ResultState.SUCCESS,"更新成功"):new ResultState(ResultState.FAILURE, "更新失败");
	}
	
	public ResultState deleteMySQL(String namespaceId,Map<String,Object> params) {
		CommonDao commonDao = new CommonDao();
		
		if(params==null || params.get("where")==null){
			return new ResultState(ResultState.FAILURE, "参数不正确");
		}
		
		int result = commonDao.deleteByNamespace(Constants.defaultNamespace+namespaceId,params);
		return result>0?new ResultState(ResultState.SUCCESS,"删除成功"):new ResultState(ResultState.FAILURE, "删除失败");
	}
	
}
