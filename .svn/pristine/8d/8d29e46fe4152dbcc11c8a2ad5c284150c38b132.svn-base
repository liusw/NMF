package com.boyaa.mf.service.common;

import com.alibaba.fastjson.JSONObject;
import com.boyaa.base.utils.JSONUtil;
import com.boyaa.mf.util.HiveUtils;
import com.boyaa.service.hbase.query.HBaseClient;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Row;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by liusw
 * 创建时间：16-5-3.
 */
@Service
public class HiveHbaseService {
    static Logger taskLogger = Logger.getLogger("taskLogger");
    static Logger errorLogger = Logger.getLogger("errorLogger");

    @Autowired
    private HiveJdbcService hiveJdbcService;


    private Connection hConn = null;
    private String table;
    private HBaseClient hbaseClient;

    public void close(){
        if(hiveJdbcService != null){
            hiveJdbcService.close();
        }
        if(hbaseClient!=null){
            hbaseClient.close();
        }
    }

    /**
     *
     * @param htableName hive表名
     * @param hcolumns hive表字段
     * @param hbcolumns hbase表字段 必须和hive表字段顺序保持一致
     * @throws Exception
     */
    public void hive2hbase(String htableName, String hcolumns, String hbcolumns) throws Exception {
        StringBuffer hsql = new StringBuffer();
        hsql.append("select ").append(HiveUtils.columsSpecialSymbols(hcolumns)).append(" from ").append(htableName);
        taskLogger.info("hsql:" + hsql);
        taskLogger.info("导入数据到hbase：");
        taskLogger.info("hbase columns："+hbcolumns);
        hConn = hiveJdbcService.getConnection();
        if (hConn == null || hConn.isClosed()) {
            errorLogger.error("hive connection is null or closed!");
            throw  new SQLException("hive connection is null or closed!");
        }
        PreparedStatement hPstmt = null;
        ResultSet hRs = null;
        HBaseClient hbaseClient = null;
        try{
            hPstmt = hConn.prepareStatement(hsql.toString());
            hRs = hPstmt.executeQuery();
            int count = hRs.getMetaData().getColumnCount();
            String[] hbColumnArr = hbcolumns.split(",");//hbase列数组
            if(hbColumnArr.length!=count){//hbase列数和hive列数不一样
                errorLogger.error("hive columns count not match hbase columns count!");
                throw  new SQLException("hive columns count not match hbase columns count!");
            }
            List<Row> list = new ArrayList<Row>();
            // 主表的字段配置信息，如果multi中有其他表，也是使用此配置
            hbaseClient = new HBaseClient(htableName);
            Map<String, String[]> columnTypeMapping = hbaseClient.getColumnTypeMapping();
            while (hRs.next()) {
                JSONObject json = JSONUtil.getJSONObject();//列数据
                for (int i = 1; i <= count; i++) {
                    try {
                        Object value = hRs.getString(i);
                        if(value == null){
                            value = "";
                        }
                        json.put(hbColumnArr[i-1], value);
                    } catch (SQLException e) {
                        errorLogger.error(e.getMessage());
                    }
                }
                // 获取rowkey
                String rowKey = hbaseClient.getRowKey(json, 0);
                // 组装put对象
                Put put = hbaseClient.get(json, rowKey, columnTypeMapping);
                list.add(put);
            }
            if(list.size()>0){
                hbaseClient.batch(hbaseClient.getTableName(),list);
            }
        }catch (Exception e){
            errorLogger.error(e.getMessage());
            throw e;
        }finally {
            if (hRs != null){
                hRs.close();
            }
            if (hPstmt != null){
                hPstmt.close();
            }
            close();
        }
    }
}
