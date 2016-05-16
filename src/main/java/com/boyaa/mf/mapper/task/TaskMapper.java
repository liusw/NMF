package com.boyaa.mf.mapper.task;

import com.boyaa.mf.annotation.MyBatisRepository;
import com.boyaa.mf.entity.task.Task;
import com.boyaa.mf.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * Created by liusw 创建时间：16-4-12.
 */
@MyBatisRepository
public interface TaskMapper extends BaseMapper<Task, Integer> {
	/**
	 * 更新任务状态
	 * 
	 * @param params
	 * @return
	 */
	public int updateStatusById(Map<String, Object> params);

	List<Task> queryExecuteLongTimeTask();

	public Object updateByAutoTask(Map<String, Object> params);
}
