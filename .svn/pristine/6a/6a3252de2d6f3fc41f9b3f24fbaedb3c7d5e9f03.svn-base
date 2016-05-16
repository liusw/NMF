package com.boyaa.mf.mapper.task;

import java.util.Map;

import com.boyaa.mf.annotation.MyBatisRepository;
import com.boyaa.mf.entity.task.AutoRunning;
import com.boyaa.mf.mapper.BaseMapper;

/**
 * Created by liusw 创建时间：16-4-12.
 */
@MyBatisRepository
public interface AutoRunningMapper extends BaseMapper<AutoRunning, Integer> {

	int getSameTemplateIdCount(Integer templateId);

	void deleteByTaskId(int taskId);

	void updateByTemplateId(Map<String, Object> params);
	
}
