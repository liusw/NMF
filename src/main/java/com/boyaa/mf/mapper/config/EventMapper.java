package com.boyaa.mf.mapper.config;

import com.boyaa.mf.annotation.MyBatisRepository;
import com.boyaa.mf.entity.config.Event;
import com.boyaa.mf.mapper.BaseMapper;

/**
 * Created by liusw
 * 创建时间：16-3-22.
 */
@MyBatisRepository
public interface EventMapper extends BaseMapper<Event,Integer>{
}
