package com.boyaa.mf.mapper;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface BaseMapper<T,ID extends Serializable> {
	
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
	 * @param id 实体id数组
	 */
	public int delete(ID id);
	
	/**
	 * 获取实体
	 * @param id 实体id
	 * @return
	 */
	public T findById(ID id);
	
	/**
	 * 根据参数获取数据列表
	 * @param params
	 * @return
	 */
	public List<T> findScrollDataList(Map<String,Object> params);
	
	/**
	 * 根据参数获取记录数
	 * @param params
	 * @return
	 */
	public int findScrollDataCount(Map<String,Object> params);

	/**
	 * 清空记录
     */
	public void removeAll();
}
