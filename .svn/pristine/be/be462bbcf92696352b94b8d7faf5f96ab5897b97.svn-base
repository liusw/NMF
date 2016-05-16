package com.boyaa.mf.service.data;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boyaa.mf.entity.data.GamblingRank;
import com.boyaa.mf.mapper.data.GamblingRankMapper;
import com.boyaa.mf.service.AbstractService;

@Service
public class GamblingRankService extends AbstractService<GamblingRank, Integer> {
	@Autowired
	private GamblingRankMapper gamblingRankMapper;
	
	/**
	 * 删除某天之前的所有排行榜信息
	 * @param params
	 */
	public void deleteByTm(Map<Object, Object> params){
		gamblingRankMapper.deleteByTm(params);
	}

}
