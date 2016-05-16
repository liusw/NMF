package com.boyaa.mf.mapper.data;

import java.util.Map;

import com.boyaa.mf.annotation.MyBatisRepository;
import com.boyaa.mf.entity.data.PayDistribute;
import com.boyaa.mf.mapper.BaseMapper;

@MyBatisRepository 
public interface PayDistributeMapper extends BaseMapper<PayDistribute, Integer>{
	public PayDistribute getPayDistribute(Map<Object, Object> params);
}
