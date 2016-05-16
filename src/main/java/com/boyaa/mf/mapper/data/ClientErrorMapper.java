package com.boyaa.mf.mapper.data;

import com.boyaa.mf.annotation.MyBatisRepository;
import com.boyaa.mf.entity.data.ClientError;
import com.boyaa.mf.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * Created by liusw
 * 创建时间：16-4-21.
 */
@MyBatisRepository
public interface ClientErrorMapper extends BaseMapper<ClientError,Integer> {
    ClientError findErrorDetails(Map<String,Object> params);

    List<Map<String,Object>> findErrorTerminalInfos(Map<String,Object> desc_md5);

    int updateErrorStatus(Map<String,Object> params);
}
