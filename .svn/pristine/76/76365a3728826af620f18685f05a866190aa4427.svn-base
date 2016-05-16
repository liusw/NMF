package com.boyaa.mf.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.boyaa.mf.mapper.BaseMapper;
import com.boyaa.mf.util.PageUtil;
import com.boyaa.mf.util.SystemContext;

public abstract class AbstractService<T,ID extends Serializable> {
//	private BaseMapper<T, ID> baseMapper;
//
//	public void setBaseMapper(BaseMapper<T, ID> baseMapper) {
//		this.baseMapper = baseMapper;
//	}
	protected static Logger errorLogger = Logger.getLogger("errorLogger");
	/**
	 * 使用spring4泛型注入
	 */
	@Autowired
	protected BaseMapper<T, ID> mapper;
	
	/**
	 * 保存实体
	 * 
	 * @param entity
	 *            实体id
	 */
	public int insert(T t) {
		return mapper.insert(t);
	}

	/**
	 * 更新实体
	 * 
	 * @param entity
	 *            实体id
	 */
	public int update(T t) {
		return mapper.update(t);
	}

	/**
	 * 删除实体
	 * 
	 * @param entityClass
	 *            实体类
	 * @param entityids
	 *            实体id数组
	 */
	public int delete(ID id) {
		return mapper.delete(id);
	}

	/**
	 * 获取实体
	 * 
	 * @param <T>
	 * @param entityId
	 *            实体id
	 * @return
	 */
	public T findById(ID id) {
		return mapper.findById(id);
	}
	
	/**
	 * 获取所有数据列表
	 * 
	 * @param params
	 * @return
	 */
	public List<T> findScrollDataList() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("start",0);
		params.put("size", Integer.MAX_VALUE);
		
		return findScrollDataList(params);
	}

	/**
	 * 根据参数获取记录数
	 * 
	 * @param params
	 * @return
	 */
	public int findScrollDataCount() {
		return findScrollDataCount(null);
	}

	/**
	 * 根据参数获取数据列表
	 * 
	 * @param params
	 * @return
	 */
	public List<T> findScrollDataList(Map<String, Object> params) {
		if(params==null || params.size()<=0){
			params = new HashMap<String, Object>();
			params.put("start",0);
			params.put("size", Integer.MAX_VALUE);
		}
		if(!params.containsKey("start")){
			params.put("start",0);
		}
		if(!params.containsKey("size")){
			params.put("size",Integer.MAX_VALUE);
		}
		
		return mapper.findScrollDataList(params);
	}

	/**
	 * 根据参数获取记录数
	 * 
	 * @param params
	 * @return
	 */
	public int findScrollDataCount(Map<String, Object> params) {
		return mapper.findScrollDataCount(params);
	}

	/**
	 * 根据参数获取分页数据
	 * 
	 * @param params
	 * @return
	 */
	public PageUtil<T> getPageList(Map<String, Object> params) {
		int totalCount = findScrollDataCount(params);
		PageUtil<T> page = new PageUtil<T>(SystemContext.getPageSize(),totalCount, SystemContext.getPageIndex());

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", page.getStart());
		map.put("size", page.getPageRecord());

		if (params != null && !params.isEmpty())
			map.putAll(params);

		List<T> objList = findScrollDataList(map);

		page.setDatas(objList);

		return page;
	}
	
	public PageUtil<T> getPageList() {
		return getPageList(null);
	}

}
