package com.boyaa.mf.mapper.data;

import java.util.Map;

import com.boyaa.mf.annotation.MyBatisRepository;
import com.boyaa.mf.entity.data.GamblingRank;
import com.boyaa.mf.mapper.BaseMapper;


@MyBatisRepository 
public interface GamblingRankMapper extends BaseMapper<GamblingRank, Integer>{
	public void deleteByTm(Map<Object, Object> params);
}