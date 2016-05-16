package com.boyaa.mf.service.config;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.boyaa.mf.constants.Constants;
import com.boyaa.mf.entity.config.Column;
import com.boyaa.mf.entity.config.HiveMeta;
import com.boyaa.mf.service.common.JdbcService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by liusw
 * 创建时间：16-4-29.
 */
@Service
public class HiveMetaService extends JdbcService{

    static Logger logger = Logger.getLogger(HiveMetaService.class);
    static Logger errorLogger = Logger.getLogger("errorLogger");
    static Logger fatalLogger = Logger.getLogger("fatalLogger");


    @Autowired
    private DataSource hiveMetaSource;
    @Autowired
    private ConfigService configService;

    @Override
    public Connection getConnection() {
        try{
            return hiveMetaSource.getConnection();
        }catch (Exception e){
            errorLogger.error("get hiveMetaConnection error.",e);
            return null;
        }
    }

    private static boolean cache = true;//是否从缓存获取数据

    private static HashMap<String,JSONArray> columnInfoMap = new HashMap<String,JSONArray>();


    public static boolean isCache() {
        return cache;
    }

    public static void setCache(boolean cache) {
        HiveMetaService.cache = cache;
    }



    public void syncXml2Hive(String tableName) throws SQLException {
        boolean updateFlag = false;

        JSONArray jsonArray = getColumnInfo(tableName.toLowerCase());

            //获取配置文件中的备注
            List<Column> columns = configService.getHbaseColumns(tableName,true,false);

            if(jsonArray!=null && jsonArray.size()>0 && columns!=null && columns.size()>0){

            HiveMeta hiveMeta = null;
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                if(jsonObject.getString("comment").trim().equals("from deserializer") || StringUtils.isBlank(jsonObject.getString("comment"))
                        || jsonObject.getString("comment").trim().contains("??")
                        || (jsonObject.getIntValue("type")== Constants.HIVE_META_COLUMN_TYPE && jsonObject.getString("comment").trim().equals(jsonObject.getString("column_name")))){

                    String columnName = jsonObject.getString("column_name");

                    //分区字段
                    if(jsonObject.getIntValue("type")== Constants.HIVE_META_PARTITION_TYPE){
                        String comment = null;
                        if(columnName.equals("plat")){
                            comment= Constants.HIVE_PARTITION_COMMENT_PLAT;
                        }else if(columnName.equals("svid")){
                            comment= Constants.HIVE_PARTITION_COMMENT_SVID;
                        }else if(columnName.equals("tm")){
                            comment= Constants.HIVE_PARTITION_COMMENT_TM;
                        }

                        if(comment==null){
                            continue;
                        }

                        hiveMeta = new HiveMeta();
                        hiveMeta.setCdId(jsonObject.getIntValue("id"));
                        hiveMeta.setType(jsonObject.getIntValue("type"));
                        hiveMeta.setComment(comment);
                        hiveMeta.setColumnName(jsonObject.getString("column_name"));
                        this.updateComment(hiveMeta);

                        updateFlag = true;
                    }else if(jsonObject.getIntValue("type")==Constants.HIVE_META_COLUMN_TYPE){//普通字段
                        for(int j=0;j<columns.size();j++){
                            Column column = columns.get(j);

                            if(column.getName().toLowerCase().equals(jsonObject.getString("column_name"))){
                                hiveMeta = new HiveMeta();
                                hiveMeta.setCdId(jsonObject.getIntValue("id"));
                                hiveMeta.setType(jsonObject.getIntValue("type"));
                                //hiveMeta.setComment(StringUtils.isNotBlank(column.getDesc()) && column.getDesc().contains("from deserializer")?column.getDesc():jsonObject.getString("column_name"));
                                hiveMeta.setComment(StringUtils.isNotBlank(column.getDesc())?column.getDesc():jsonObject.getString("column_name"));
                                hiveMeta.setColumnName(jsonObject.getString("column_name"));
                                this.updateComment(hiveMeta);
                                updateFlag = true;

                                columns.remove(column);
                                break;
                            }
                        }
                    }
                }
            }
        }

        if(updateFlag && HiveMetaService.isCache()){//如果获取元数据使用了缓存，则更新缓存的信息
            setCache(false);
            getColumnInfo(tableName);
            setCache(true);
        }
    }

    public static HashMap<String, JSONArray> getColumnInfoMap() {
        return columnInfoMap;
    }

    public synchronized JSONArray setColumnInfoMap(String tableName) {
        if(!cache || (columnInfoMap != null && !columnInfoMap.containsKey(tableName))){
            try {
                JSONArray columnInfo = find(tableName, 0, 0);
                columnInfoMap.put(tableName, columnInfo);
                return columnInfo;
            }finally{
                close();
            }
        }else{
            if(columnInfoMap != null){
                return columnInfoMap.get(tableName);
            }
            return null;
        }
    }

    private JSONArray find(String tableName, int start, int size) {

        if(StringUtils.isNotBlank(tableName)){
            tableName = tableName.toLowerCase();

            JSONArray json = null;
            StringBuilder sb = new StringBuilder();
            sb.append("select '").append(Constants.HIVE_META_COLUMN_TYPE)
                    .append("' as type,type_name as valueType,c.cd_id as id,c.column_name,c.comment from TBLS a join SDS b ON(a.SD_ID=b.SD_ID) join COLUMNS_V2 c ON(b.CD_ID=c.CD_ID) where a.TBL_NAME='")
                    .append(tableName)
                    .append("' union  select '").append(Constants.HIVE_META_PARTITION_TYPE)
                    .append("' as type,pkey_type as valueType,d.tbl_id as id,d.pkey_name,d.pkey_comment from TBLS f join  PARTITION_KEYS d on(d.tbl_id=f.tbl_id) where f.TBL_NAME='")
                    .append(tableName).append("'");

            if(size == 0){
                size = Integer.MAX_VALUE;
            }
            sb.append(" order by column_name desc  limit ").append(start).append(",").append(size);
            this.setDefaultValue("");
            try {
                json = this.find(sb.toString());
                return json;
            } catch (SQLException e) {
                errorLogger.error("sql:"+sb.toString()+","+e.getMessage());
            }
        }
        return null;
    }
    public JSONArray getColumnInfo(String tableName) throws SQLException {
        if(StringUtils.isNotEmpty(tableName)){
            JSONArray columnInfo = columnInfoMap.get(tableName);
            if(columnInfo == null || !cache){
                columnInfo = setColumnInfoMap(tableName);
            }
            return columnInfo;
        }else{
            return null;
        }
    }

    public void updateComment(HiveMeta hiveMeta) throws SQLException{
        if(hiveMeta != null){
            PreparedStatement mpst = null;
            try{
                conn = getConnection();
                if(hiveMeta.getType() == Constants.HIVE_META_COLUMN_TYPE){
                    mpst = (PreparedStatement) conn.prepareStatement("update COLUMNS_V2 set comment=? where column_name=? and cd_id=?");
                }else {
                    mpst = (PreparedStatement) conn.prepareStatement("update PARTITION_KEYS set PKEY_COMMENT=? where PKEY_NAME=? and TBL_ID=?");
                }
                mpst.setString(1, hiveMeta.getComment());
                mpst.setString(2, hiveMeta.getColumnName());
                mpst.setInt(3, hiveMeta.getCdId());
                mpst.execute();
            }catch(SQLException e){
                throw new SQLException(e.getMessage());
            }finally{
                if(mpst != null){
                    mpst.close();
                }
                if(conn!=null){
                    conn.close();
                }
            }
        }
    }
}
