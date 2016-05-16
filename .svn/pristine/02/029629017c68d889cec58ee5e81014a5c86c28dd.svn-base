package com.boyaa.mf.service.common;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * Created by liusw
 * 创建时间：16-5-3.
 */
@Service
public class MySqlService extends JdbcService{

    static Logger logger = Logger.getLogger(HiveJdbcService.class);
    static Logger errorLogger = Logger.getLogger("errorLogger");

    @Autowired
    private DataSource dataSource;

    @Override
    public Connection getConnection() {
        try{
            return dataSource.getConnection();
        }catch (Exception e){
            errorLogger.error("get mysqlDataSourrce error.",e);
            return null;
        }
    }
}
