package com.boyaa.mf.web.dto;

/**
 * Created by liusw
 * 创建时间：16-3-23.
 */
public class HbColumnMeta {
    private String tableName;
    private String colName;
    private String colValue;
    private String colType;
    private String desc;

    public HbColumnMeta() {
    }

    public HbColumnMeta(String tableName,String colName, String colValue, String colType, String desc) {
        this.tableName = tableName;
        this.colName = colName;
        this.colValue = colValue;
        this.colType = colType;
        this.desc = desc;
    }

    public String getColName() {
        return colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }

    public String getColValue() {
        return colValue;
    }

    public void setColValue(String colValue) {
        this.colValue = colValue;
    }

    public String getColType() {
        return colType;
    }

    public void setColType(String colType) {
        this.colType = colType;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public String toString() {
        return "HbColumnMeta{" +
                "tableName='" + tableName + '\'' +
                ", colName='" + colName + '\'' +
                ", colValue='" + colValue + '\'' +
                ", colType='" + colType + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
