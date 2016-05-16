package com.boyaa.mf.mapper.task;

import com.boyaa.mf.annotation.MyBatisRepository;
import com.boyaa.mf.entity.task.ProcessInfo;
import com.boyaa.mf.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * Created by liusw 创建时间：16-4-12.
 */
@MyBatisRepository
public interface ProcessInfoMapper extends BaseMapper<ProcessInfo, Integer> {

	public List<ProcessInfo> getProcessOfTask(int taskId);

	int updateProcessByTaskId(Map<Object, Object> params);

	int beDepend(int id);

	List<String> findDependByTaskId(int taskId);
}
