package com.boyaa.mf.service.config;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boyaa.mf.entity.config.EventCate;
import com.boyaa.mf.mapper.config.EventCateMapper;
import com.boyaa.mf.service.AbstractService;

/**
 *<p>Title:  事件类型业务服务类<p>
 *<p>Description: null</p>
 *<p>Company: boyaa</p>
 *<p>Date: May 12, 2015 9:11:53 AM</p>
 * @author Joakun Chen
 */
@Service
public class EventCateService extends AbstractService<EventCate, Integer> {
	@Autowired
	private EventCateMapper eventCateMapper;
	
	/**
	 * 获取给点平台站点下的所有事件类型（事件类型只包含ID及英文名称）
	 * @param params
	 * @return
	 */
	public List<EventCate> findAllBySid(Map<Object, Object> params) {
		return eventCateMapper.findAllBySid(params);
	}
	
}
