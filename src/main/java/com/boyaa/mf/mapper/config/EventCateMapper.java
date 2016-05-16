package com.boyaa.mf.mapper.config;

import java.util.List;
import java.util.Map;

import com.boyaa.mf.annotation.MyBatisRepository;
import com.boyaa.mf.entity.config.EventCate;
import com.boyaa.mf.mapper.BaseMapper;

/**
 * Created by liusw
 * 创建时间：16-3-22.
 */
@MyBatisRepository
public interface EventCateMapper extends BaseMapper<EventCate,Integer>{
	public List<EventCate> findAllBySid(Map<Object,Object> params);
}
