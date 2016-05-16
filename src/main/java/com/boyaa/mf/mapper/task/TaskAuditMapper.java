package com.boyaa.mf.mapper.task;

import com.boyaa.mf.annotation.MyBatisRepository;
import com.boyaa.mf.entity.task.TaskAudit;
import com.boyaa.mf.mapper.BaseMapper;

/**
 * Created by liusw
 * 创建时间：16-4-11.
 */
@MyBatisRepository
public interface TaskAuditMapper extends BaseMapper<TaskAudit,Integer> {
    public TaskAudit findDetail(String id);
}
