package com.boyaa.mf.entity.config;


import java.util.List;

import com.boyaa.mf.web.dto.HbColumnMeta;

/**
 * Created by liusw
 * 创建时间：16-3-22.
 */
public class HbaseMeta {

    private String tableName;
    private String alias;
    private String remark;
    private List<HbColumnMeta> content;

    public HbaseMeta() {
    }

    public HbaseMeta(String tableName, String alias, String remark, List<HbColumnMeta> columns) {
        this.tableName = tableName;
        this.alias = alias;
        this.remark = remark;
        this.content = columns;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }


    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<HbColumnMeta> getColumns() {
        return content;
    }

    public void setColumns(List<HbColumnMeta> columns) {
        this.content = columns;
    }

    @Override
    public String toString() {
        return "HbaseMeta{" +
                "tableName='" + tableName + '\'' +
                ", alias='" + alias + '\'' +
                ", remark='" + remark + '\'' +
                ", content=" + content +
                '}';
    }
}
