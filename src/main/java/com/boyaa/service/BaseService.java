package com.boyaa.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.boyaa.dao.BaseDao;
import com.boyaa.mf.util.PageUtil;
import com.boyaa.mf.util.SystemContext;

public abstract  class BaseService<T> {
	
	public abstract BaseDao<T> getDao();
	
	/**
	 * 保存实体
	 * 
	 * @param entity
	 *            实体id
	 */
	public int insert(T t) {
		return getDao().insert(t);
	}

	/**
	 * 更新实体
	 * 
	 * @param entity
	 *            实体id
	 */
	public int update(T t) {
		return getDao().update(t);
	}

	/**
	 * 删除实体
	 * 
	 * @param entityClass
	 *            实体类
	 * @param entityids
	 *            实体id数组
	 */
	public int delete(Serializable entityids) {
		return getDao().delete(entityids);
	}

	/**
	 * 获取实体
	 * 
	 * @param <T>
	 * @param entityClass
	 *            实体类
	 * @param entityId
	 *            实体id
	 * @return
	 */
	public T findById(Serializable entityId) {
		return getDao().findById(entityId);
	}

	/**
	 * 根据参数获取数据列表
	 * 
	 * @param params
	 * @return
	 */
	public List<T> findScrollDataList(Map<Object, Object> params) {
		if(params==null || params.size()<=0){
			params = new HashMap<Object, Object>();
		}
		if(!params.containsKey("start")){
			params.put("start",0);
		}
		if(!params.containsKey("size")){
			params.put("size", Integer.MAX_VALUE);
		}
		
		return getDao().findScrollDataList(params);
	}

	/**
	 * 根据参数获取记录数
	 * 
	 * @param params
	 * @return
	 */
	public int findScrollDataCount(Map<Object, Object> params) {
		return getDao().findScrollDataCount(params);
	}

	/**
	 * 根据参数获取分页数据
	 * 
	 * @param params
	 * @return
	 */
	public PageUtil<T> getPageList(Map<Object, Object> params) {
		int totalCount = findScrollDataCount(params);
		PageUtil<T> page = new PageUtil<T>(SystemContext.getPageSize(),totalCount, SystemContext.getPageIndex());

		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("start", page.getStart());
		map.put("size", page.getPageRecord());

		if (params != null && !params.isEmpty())
			map.putAll(params);

		List<T> objList = findScrollDataList(map);

		page.setDatas(objList);

		return page;
	}
}
