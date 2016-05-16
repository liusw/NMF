package com.boyaa.service;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.boyaa.base.db.MySQLService;
import com.boyaa.base.hbase.HConnectionSingleton;
import com.boyaa.base.utils.JSONUtil;
import com.boyaa.mf.service.common.HiveJdbcService;
import com.boyaa.mf.util.SpringBeanUtils;
import org.apache.hadoop.hbase.ClusterStatus;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.SQLException;


public class ApiService {
	static Logger logger = Logger.getLogger(ApiService.class);
	static Logger errorLogger = Logger.getLogger("errorLogger");

	public JSONObject checkServer() {
		boolean hiveStatus = false;
		boolean mysqlStatus = false;
		boolean hbaseStatus = false;

		HiveJdbcService hiveService = null;
		try {
			hiveService = SpringBeanUtils.getBean("hiveJdbcService",HiveJdbcService.class);
			if (hiveService.getConnection() != null
					&& !hiveService.getConnection().isClosed())
				hiveStatus = true;
		} catch (SQLException e) {
			errorLogger.error(e.getMessage());
		} finally {
			if (hiveService != null) {
				hiveService.close();
			}
		}

		MySQLService mySQLService = null;
		try {
			mySQLService = new MySQLService();
			if (mySQLService.getConnection() != null
					&& !mySQLService.getConnection().isClosed())
				mysqlStatus = true;
		} catch (SQLException e) {
			errorLogger.error(e.getMessage());
		} finally {
			if (mySQLService != null) {
				mySQLService.close();
			}
		}

		Connection conn = null;
		Admin admin = null;
		try {
			conn = HConnectionSingleton.create();
			conn.getConfiguration().set("zookeeper.session.timeout", (1000*10)+"");
			conn.getConfiguration().set("zookeeper.recovery.retry", Integer.toString(1));
			admin = conn.getAdmin();

			ClusterStatus clusterStatus = admin.getClusterStatus();
			int liveServers = clusterStatus.getServersSize();

			if (liveServers > 0) {
				hbaseStatus = true;
			}
		} catch (Exception e) {
			errorLogger.error(e.getMessage());
		} finally {
			try {
				if (admin != null)
					admin.close();
//				if (conn != null)
//					conn.close();
			} catch (IOException e) {
				errorLogger.error(e.getMessage());
			}
		}

		JSONObject json = JSONUtil.getJSONObject();
		try {
			json.put("mysql", mysqlStatus ? "ok" : "mysql连接异常");
			json.put("hbase", hbaseStatus ? "ok" : "hbase连接异常");
			json.put("hive", hiveStatus ? "ok" : "hive连接异常");
		} catch (JSONException e) {
			errorLogger.error(e.getMessage());
		}

		return json;
	}
}