package com.boyaa.mf.util;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.boyaa.base.db.MySQLService;
import com.boyaa.base.hive.HiveService;


public abstract class HiveQueryTemplate {
	static Logger logger = Logger.getLogger(HiveQueryTemplate.class);
	static Logger errorLogger = Logger.getLogger("errorLogger");
	static Logger fatalLogger = Logger.getLogger("fatalLogger");
	private MySQLService mysqlService;
	private HiveService hiveService;
	
	public void init() {
		try {
			mysqlService = new MySQLService();
			hiveService = new HiveService();
		} catch (SQLException e) {
			errorLogger.error(e.getMessage());
		}
	}
	
	public MySQLService getMysqlService() {
		return mysqlService;
	}

	public HiveService getHiveService() {
		return hiveService;
	}

	public abstract void process(String sql);
	
	public void destroy() {
		if(mysqlService != null)
			mysqlService.close();
		if(hiveService != null)
			hiveService.close();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
