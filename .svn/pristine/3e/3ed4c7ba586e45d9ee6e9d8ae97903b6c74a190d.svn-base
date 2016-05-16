package com.boyaa.mf.service.common;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.boyaa.base.utils.JSONUtil;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liusw
 * 创建时间：16-4-29.
 */
public abstract class JdbcService {

    static Logger logger = Logger.getLogger(JdbcService.class);

    protected Connection conn;
    protected SimpleDateFormat sdf;
    protected Object defaultValue = Integer.valueOf(0);

    public void setDefaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Object getDefaultValue() {
        return this.defaultValue;
    }

	public void close() {
		try {
			if (this.conn != null && !this.conn.isClosed()) {
				this.conn.close();
				logger.info("close db connect success!");
			} else {
				logger.info("db connect is null or closed!");
			}
		} catch (SQLException var2) {
			logger.error("close db connection error!", var2);
		}
	}

    public Connection getConnection() {
        return conn;
    }
    public void setConnection(Connection conn) {
        this.conn = conn;
    }

    public boolean execute(String sql) throws SQLException {
        if(this.conn != null && !this.conn.isClosed()) {
            PreparedStatement pstmt = this.conn.prepareStatement(sql);
            boolean flag = false;

            try {
                pstmt.execute();
                int e = pstmt.getUpdateCount();
                if(e != -1) {
                    flag = true;
                }
            } catch (SQLException var8) {
                logger.error(sql, var8);
                throw new SQLException(var8);
            } finally {
                if(pstmt != null) {
                    pstmt.close();
                }
                if(conn!=null){
                    conn.close();
                }
            }

            return flag;
        } else {
            logger.error("connection is null or closed!");
            return false;
        }
    }

    public boolean execute(String sql, String[] paramName, JSONObject json) throws SQLException {
        if(paramName == null) {
            return false;
        } else {
            String[] var7 = paramName;
            int var6 = paramName.length;

            for(int var5 = 0; var5 < var6; ++var5) {
                String param = var7[var5];
                if(json.containsKey(param)) {
                    sql = sql.replaceFirst("\\?", json.getString(param));
                } else {
                    sql = sql.replaceFirst("\\?", this.defaultValue.toString());
                }
            }

            return this.execute(sql);
        }
    }

    public boolean execute(String sql, String[] paramValue) throws SQLException {
        if(paramValue == null) {
            return false;
        } else {
            String[] var6 = paramValue;
            int var5 = paramValue.length;

            for(int var4 = 0; var4 < var5; ++var4) {
                String value = var6[var4];
                sql = sql.replaceFirst("\\?", value);
            }

            return this.execute(sql);
        }
    }

    public long count(String sql) throws SQLException {
        conn = getConnection();
        if(this.conn != null && !this.conn.isClosed()) {
            PreparedStatement pstmt = this.conn.prepareStatement(sql);
            ResultSet rs = null;
            long count = 0L;

            try {
                rs = pstmt.executeQuery();
                if(rs.next()) {
                    count = rs.getLong(1);
                }
            } catch (SQLException var10) {
                logger.error(sql, var10);
                throw new SQLException(var10);
            } finally {
                if(rs != null) {
                    rs.close();
                }

                if(pstmt != null) {
                    pstmt.close();
                }
                if(conn!=null)
                    conn.close();

            }

            return count;
        } else {
            logger.error("connection is null or closed!");
            return 0L;
        }
    }

    public JSONArray find(String sql) throws SQLException {
        conn = getConnection();
        if(this.conn != null && !this.conn.isClosed()) {
            PreparedStatement pstmt = this.conn.prepareStatement(sql);
            JSONArray array = null;
            ResultSet rs = null;

            try {
                rs = pstmt.executeQuery();
                array = this.convertTo(rs);
            } catch (SQLException var9) {
                logger.error(sql, var9);
                throw new SQLException(var9);
            } finally {
                if(rs != null) {
                    rs.close();
                }

                if(pstmt != null) {
                    pstmt.close();
                }
                if(conn!=null){
                    conn.close();
                }

            }

            return array;
        } else {
            logger.error("connection is null or closed!");
            return null;
        }
    }

    public boolean findAndSave(String sql, PrintWriter print) throws SQLException, IOException {
        conn = getConnection();
        if(this.conn != null && !this.conn.isClosed()) {
            PreparedStatement pstmt = this.conn.prepareStatement(sql);
            ResultSet rs = null;

            try {
                rs = pstmt.executeQuery();
                int e = rs.getMetaData().getColumnCount();

                while(rs.next()) {
                    StringBuffer sb = new StringBuffer();

                    for(int i = 1; i <= e; ++i) {
                        try {
                            Object e1 = rs.getObject(i);
                            if(e1 == null) {
                                sb.append(this.defaultValue).append(",");
                            } else {
                                sb.append(e1).append(",");
                            }
                        } catch (SQLException var13) {
                            logger.error(var13);
                        }
                    }

                    print.println(sb.substring(0, sb.length() - 1));
                }

                print.flush();
            } catch (SQLException var14) {
                logger.error(sql, var14);
                throw new SQLException(var14);
            } finally {
                if(rs != null) {
                    rs.close();
                }

                if(pstmt != null) {
                    pstmt.close();
                }
                if(conn!=null){
                    conn.close();
                }

            }

            return true;
        } else {
            logger.error("connection is null or closed!");
            return false;
        }
    }

    public int getKey(String sql) throws SQLException {
        conn = getConnection();
        if(this.conn != null && !this.conn.isClosed()) {
            PreparedStatement pstmt = null;
            ResultSet rs = null;

            try {
                pstmt = this.conn.prepareStatement(sql, 1);
                pstmt.executeUpdate();
                rs = pstmt.getGeneratedKeys();
                if(rs.next()) {
                    int var6 = rs.getInt(1);
                    return var6;
                }
            } catch (SQLException var9) {
                logger.error(sql, var9);
                throw new SQLException(var9);
            } finally {
                if(rs != null) {
                    rs.close();
                }

                if(pstmt != null) {
                    pstmt.close();
                }
                if(conn!=null){
                    conn.close();
                }

            }

            return 0;
        } else {
            logger.error("connection is null or closed!");
            return 0;
        }
    }

    public List<String> getTableNames(String dbNamePrefix, String tableNamePattern) throws SQLException {
        conn = getConnection();
        if(this.conn != null && !this.conn.isClosed()) {
            ResultSet dbs = null;
            ArrayList names = null;

            try {
                DatabaseMetaData e = this.conn.getMetaData();
                dbs = e.getCatalogs();
                names = new ArrayList();

                while(true) {
                    String dbName;
                    do {
                        if(!dbs.next()) {
                            return names;
                        }

                        dbName = dbs.getString("TABLE_CAT");
                    } while(!dbName.startsWith(dbNamePrefix));

                    ResultSet tables = e.getTables(dbName, (String)null, tableNamePattern, (String[])null);

                    while(tables.next()) {
                        String tableName = tables.getString(3);
                        names.add(dbName + "." + tableName);
                    }

                    tables.close();
                }
            } catch (SQLException var12) {
                logger.error(var12);
                throw new SQLException(var12);
            } finally {
                if(dbs != null) {
                    dbs.close();
                }
                if(conn!=null){
                    conn.close();
                }
            }
        } else {
            logger.error("connection is null or closed!");
            return null;
        }
    }

    public List<String> getDBNames() throws SQLException {
        conn = getConnection();
        if(this.conn != null && !this.conn.isClosed()) {
            ResultSet dbs = null;
            ArrayList names = null;

            try {
                DatabaseMetaData e = this.conn.getMetaData();
                dbs = e.getCatalogs();
                names = new ArrayList();

                while(dbs.next()) {
                    names.add(dbs.getString("TABLE_CAT"));
                }

                dbs.close();
                return names;
            } catch (SQLException var7) {
                logger.error(var7);
                throw new SQLException(var7);
            } finally {
                if(dbs != null) {
                    dbs.close();
                }
                if(conn!=null){
                    conn.close();
                }

            }
        } else {
            logger.error("connection is null or closed!");
            return null;
        }
    }

    public JSONArray convertTo(ResultSet rs) throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        JSONArray array = JSONUtil.getJSONArray();

        while(rs.next()) {
            JSONObject json = JSONUtil.getJSONObject();

            for(int i = 1; i <= count; ++i) {
                try {
                    Object e = rs.getObject(i);
                    if(e == null) {
                        json.put(rsmd.getColumnName(i), this.defaultValue);
                    } else {
                        json.put(rsmd.getColumnName(i), e);
                    }
                } catch (Exception var8) {
                    logger.error(var8);
                }
            }

            array.add(json);
        }

        return array;
    }

    public static String getColumnName(ResultSetMetaData rsmd) throws SQLException {
        int count = rsmd.getColumnCount();
        StringBuffer sb = new StringBuffer();

        for(int i = 1; i <= count; ++i) {
            sb.append(rsmd.getColumnName(i)).append(",");
        }

        return sb.length() > 0?sb.substring(0, sb.length() - 1):"";
    }


}
