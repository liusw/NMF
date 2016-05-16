package com.boyaa.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface BaseDao<T> {
	/**
	 * 保存实体
	 * @param entity 实体id
	 */
	public int insert(T t);
	
	/**
	 * 更新实体
	 * @param entity 实体id
	 */
	public int update(T t);
	
	/**
	 * 删除实体
	 * @param entityClass 实体类
	 * @param id 实体id
	 */
	public int delete(Serializable id);
	
	/**
	 * 获取实体
	 * @param <T>
	 * @param entityClass 实体类
	 * @param entityId 实体id
	 * @return
	 */
	public T findById(Serializable entityId);
	
	/**
	 * 根据参数获取数据列表
	 * @param params
	 * @return
	 */
	public List<T> findScrollDataList(Map<Object,Object> params);
	
	/**
	 * 根据参数获取记录数
	 * @param params
	 * @return
	 */
	public int findScrollDataCount(Map<Object,Object> params);
}
