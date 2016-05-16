package com.boyaa.mf.support.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.type.JdbcType;

import com.alibaba.fastjson.JSONArray;
import com.boyaa.mf.util.CommonUtil;

/**
 * Created by liusw
 * 创建时间：16-3-30.
 */
public class JsonArrayTypeHandler<T> extends BaseTypeHandler<List<T>>{

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List<T> ts, JdbcType jdbcType) throws SQLException {
        if (ts == null)
            ps.setNull(i, Types.VARCHAR);
        else {
            StringBuffer result = new StringBuffer();
            String columnsJson = CommonUtil.toJSONString(ts);
            ps.setString(i, columnsJson);
        }
    }

    @Override
    public List<T> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String columnValue = rs.getString(columnName);
        return parseJsonArray(columnValue);
    }

    @Override
    public List<T> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String columnValue = rs.getString(columnIndex);
        return parseJsonArray(columnValue);
    }

    @Override
    public List<T> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String columnValue = cs.getString(columnIndex);
        return parseJsonArray(columnValue);
    }

    private List<T> parseJsonArray(String columnValue) {
        JSONArray jsonArray = (JSONArray) JSONArray.parse(columnValue);
        List<T> result = new ArrayList<T>();
        for (int i = 0; i < jsonArray.size(); i++) {
            T cell = (T) jsonArray.getJSONObject(i);
            result.add(cell);
        }
        return  result;
    }
}
