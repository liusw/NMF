package com.boyaa.mf.service.data;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boyaa.mf.entity.data.PayDistribute;
import com.boyaa.mf.mapper.data.PayDistributeMapper;
import com.boyaa.mf.service.AbstractService;

/** 
 * @author huangwx 
 * @version 创建时间：Jun 9, 2015 2:35:48 PM 
 */
@Service
public class PayDistributeService extends AbstractService<PayDistribute, Integer> {
	
	@Autowired
	private PayDistributeMapper payDistributeMapper;
	
	public PayDistribute getPayDistribute(Map<Object, Object> params){
		return payDistributeMapper.getPayDistribute(params);
	}
}
