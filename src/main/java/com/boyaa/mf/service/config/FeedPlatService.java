package com.boyaa.mf.service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boyaa.mf.entity.config.FeedPlat;
import com.boyaa.mf.mapper.config.FeedPlatMapper;
import com.boyaa.mf.service.AbstractService;

/**
 *<p>Title: 平台Feed配置业务服务类<p>
 *<p>Description: null</p>
 *<p>Company: boyaa</p>
 *<p>Date: May 8, 2015</p>
 * @author Joakun Chen
 */
@Service
public class FeedPlatService extends AbstractService<FeedPlat, Integer> {
	@Autowired
	private FeedPlatMapper feedPlatMapper;
	
	/**
	 * 查询指定Feed是否已经在给定平台下
	 * @param feedPlat 包含plat，状态及feedId
	 * @return 存在为1
	 */
	public Integer findByPlatFId(FeedPlat feedPlat) {
		return feedPlatMapper.findByPlatFId(feedPlat);
	}
	
}
