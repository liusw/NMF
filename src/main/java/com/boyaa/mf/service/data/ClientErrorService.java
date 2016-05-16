package com.boyaa.mf.service.data;

import com.boyaa.mf.entity.data.ClientError;
import com.boyaa.mf.mapper.data.ClientErrorMapper;
import com.boyaa.mf.service.AbstractService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liusw
 * 创建时间：16-4-21.
 */
@Service
public class ClientErrorService extends AbstractService<ClientError, Integer>{

    @Autowired
    private ClientErrorMapper clientErrorMapper;

    public ClientError findErrorDetails(String desc_md5) {
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("tableName","d_client_error_log");
        params.put("desc_md5",desc_md5);
        return clientErrorMapper.findErrorDetails(params);
    }

    public List<Map<String,Object>> findErrorTerminalInfos(String desc_md5) {
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("tableName","d_client_error_log");
        params.put("desc_md5",desc_md5);
        return clientErrorMapper.findErrorTerminalInfos(params);
    }

    public int updateErrorStatus(String stauts,String desc_md5) {
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("tableName","d_client_error_log");
        params.put("status",stauts);
        params.put("desc_md5",desc_md5);
        return clientErrorMapper.updateErrorStatus(params);
    }
}
