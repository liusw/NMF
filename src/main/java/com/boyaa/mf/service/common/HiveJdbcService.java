package com.boyaa.mf.service.common;

import com.boyaa.base.utils.CsvUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

/**
 * Created by liusw
 * 创建时间：16-4-29.
 */
@Service
public class HiveJdbcService extends JdbcService{

    static Logger logger = Logger.getLogger(HiveJdbcService.class);
    static Logger errorLogger = Logger.getLogger("errorLogger");
    static Logger fatalLogger = Logger.getLogger("fatalLogger");


    @Autowired
    private DataSource hiveDataSource;


    @Override
    public Connection getConnection() {
        try{
            return hiveDataSource.getConnection();
        }catch (Exception e){
            errorLogger.error("get hiveMetaConnection error.",e);
            return null;
        }
    }

    public boolean findAndSave(String sql, PrintWriter print) throws SQLException, IOException {
        conn = getConnection();
        if (conn == null || conn.isClosed()) {
            errorLogger.error("connection is null or closed!");
            return false;
        }

        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = null;
        try {
            rs = pstmt.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int count = rsmd.getColumnCount();

            while (rs.next()) {
                StringBuffer sb = new StringBuffer();
                for (int i = 1; i <= count; i++) {
                    if(rsmd.getColumnName(i).toLowerCase().equals("processuniquekey")){
                        continue;
                    }
                    try {
                        Object value = rs.getObject(i);
                        if(value == null){
                            sb.append(defaultValue).append(",");
                        }else{
                            //sb.append(value).append(",");
                            String v = value.toString().replaceAll(",", "，");
                            sb.append(CsvUtil.replaceRN(v)).append(",");
                        }
                    } catch (SQLException e) {
                        errorLogger.error(e.getMessage());
                    }
                }
                // 写内容
                print.println(sb.substring(0, sb.length() - 1));
            }
            print.flush();
        } catch (SQLException e) {
            logger.error("could not execute sql=<" + sql + ">: " + e.getMessage());
            throw new SQLException(e);
        } finally {
            if (rs != null)
                rs.close();
            if (pstmt != null)
                pstmt.close();
            if(conn!=null)
                conn.close();
        }
        return true;
    }


    /**
     * @描述 : 导入数据
     * @param tableName : 表名
     * @param loadDataPath : 需要导入数据的完整文件地址
     * @throws SQLException
     * @作者 : DarcyZeng
     * @日期 : Dec 5, 2014
     */
    public void loadData(String tableName,String loadDataPath) throws SQLException{
        if(StringUtils.isBlank(tableName) || StringUtils.isBlank(loadDataPath)){
            throw new SQLException("tableName or loadDataPath can't be null!");
        }
        this.conn = getConnection();
        this.execute("load data local inpath '" + loadDataPath + "' into table "+tableName);
        logger.info("execute:load data local inpath '" + loadDataPath + "' into table "+tableName);
    }

    /**
     * @描述 : 删除表
     * @param tableName：表名
     * @throws SQLException
     * @作者 : DarcyZeng
     * @日期 : Dec 5, 2014
     */
    public void dropTable(String tableName) throws SQLException{
        if(StringUtils.isBlank(tableName)){
            throw new SQLException("tableName can't be null!");
        }
        this.conn = getConnection();
        execute("drop table if exists "+tableName);
        logger.info("execute:drop table if exists "+tableName);
    }
    public static String getColumnName(ResultSetMetaData rsmd) throws SQLException {
        int count = rsmd.getColumnCount();
        StringBuffer sb = new StringBuffer();

        for(int i = 1; i <= count; ++i) {
            sb.append(rsmd.getColumnName(i)).append(",");
        }

        return sb.length() > 0?sb.substring(0, sb.length() - 1):"";
    }
    /**
     * @描述 : 创建表并导入数据
     * @param tableName : 表名
     * @param columns 	: 字段信息，包括字段和字段类型信息（用“，”分隔）
     * @param loadDataPath : 需要导入数据的完整文件地址,如果为空则不导入数据
     * @throws SQLException
     * @作者 : DarcyZeng
     * @日期 : Dec 5, 2014
     */
    public void createTable(String tableName,String columns,String loadDataPath) throws SQLException{
        if(StringUtils.isBlank(tableName) || StringUtils.isBlank(columns)){
            throw new SQLException("tableName or columns can't be null!");
        }
        this.dropTable(tableName);
        StringBuilder sb = new StringBuilder();
        sb.append("create table ").append(tableName).append("(").append(columns).append(") ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' STORED AS TEXTFILE");
        this.conn = getConnection();
        this.execute(sb.toString());
        logger.info("execute:"+sb.toString());
        if(StringUtils.isNotBlank(loadDataPath)){
            loadData(tableName,loadDataPath);
        }
    }
}
