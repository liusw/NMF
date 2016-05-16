package com.boyaa.mf.service.config;

import java.io.File;
import java.io.FileFilter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.hbase.TableName;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.boyaa.base.hbase.HBaseAdminUtil;
import com.boyaa.mf.entity.config.HbaseMeta;
import com.boyaa.mf.mapper.config.HbaseMetaMapper;
import com.boyaa.mf.service.AbstractService;
import com.boyaa.mf.util.CommonUtils;
import com.boyaa.mf.web.dto.HbColumnMeta;
import com.google.common.io.Files;

/**
 * Created by liusw 创建时间：16-3-22.
 */
@Service
public class HBaseMetaService extends AbstractService<HbaseMeta, Integer> {

    @Value("${hbtales.config.path}")
    private String hbtablesPath;

    @Value("${trash_meta_monitor_path}")
    private String rowKeyFilePath;

    @Autowired
    private HbaseMetaMapper hbaseMetaMapper;

    public void addHbTable(String tableName, String tableAlias, String fields, String rowkeyFields, String systemPro) throws Exception {

        // 追加rowkey 规则
        if (!Boolean.parseBoolean(systemPro)) {// 不追加rowkey规则文件
            File rowKeyFile = new File(getRowKeyFilePath());
            String appending = tableName + "=" + rowkeyFields;
            Files.append(appending, rowKeyFile, Charset.forName("utf-8"));
        }
        HbaseMeta newMeta = assembleHbaseMeta(tableName,tableAlias,fields);
        hbaseMetaMapper.insert(newMeta);
        // create hbase table
        HBaseAdminUtil hbUtil = new HBaseAdminUtil();
        hbUtil.findOrCreateTable(TableName.valueOf(tableName));
        //TODO 同步到 zookeeper

    }

    private HbaseMeta assembleHbaseMeta(String tableName, String tableAlias, String fields) {
        HbaseMeta newMeta = new HbaseMeta();
        newMeta.setTableName(tableName);
        newMeta.setAlias(tableAlias);
        List<HbColumnMeta> columns = parseColumnMetaByFields(fields);
        newMeta.setColumns(columns);
        return newMeta;
    }

    private List<HbColumnMeta> parseColumnMetaByFields(String fields) {
        String[] colsArr = fields.split("#");
        List<HbColumnMeta> metas = new ArrayList<HbColumnMeta>();
        for (int i = 0; i < colsArr.length; i++) {
            HbColumnMeta columnMeta = new HbColumnMeta();
            if (colsArr[i] != null && colsArr[i].split(",").length > 0) {
                String[] cs = colsArr[i].split(",");
                columnMeta.setColName(cs[0]);
                columnMeta.setColType(cs[1]);
                columnMeta.setColValue(cs[2]);
                columnMeta.setDesc(cs[3]);
            }
            metas.add(columnMeta);
        }
        return metas;
    }

    public void initHbaseMetaConfig() throws Exception {

        File dir = new File(hbtablesPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File[] files = dir.listFiles(new FileFilter() {
            public boolean accept(File pathname) {
                return pathname.getName().endsWith(".xml");
            }
        });
        hbaseMetaMapper.removeAll();
        for (int i = 0; i < files.length; i++) {
            File f = files[i];
            if (f == null)
                continue;
            HbaseMeta hbaseMeta = parseHbaseMeta(f);
            hbaseMetaMapper.insert(hbaseMeta);
        }
    }

    public List<HbaseMeta> getHbTables(String tableName) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("tableName", tableName);
        return hbaseMetaMapper.findScrollDataList(param);
    }

    public HbaseMeta getHbTableByTableName(String tableName) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("tableName", tableName);
        List<HbaseMeta> tables = hbaseMetaMapper.findScrollDataList(params);
        if (tables != null && tables.size() > 0)
            return tables.get(0);
        return null;
    }

    public List<HbColumnMeta> getHbaseMetaInfo(String hbTableName) throws DocumentException {
        HbaseMeta hbTableMeta = getHbTableByTableName(hbTableName);
        return hbTableMeta.getColumns();
    }

    public void updateHbaseMeta(final String tableName, String colName, String colType, String colValue, String comment) throws Exception {

        HbaseMeta hbMeta = getHbTableByTableName(tableName);
        if (hbMeta == null) throw new IllegalArgumentException("hbmeta:" + tableName + " is not exist.");

        List columnsInfo = hbMeta.getColumns();
        for (int i = 0; i < columnsInfo.size(); i++) {
            HbColumnMeta columnMeta = JSONObject.parseObject(columnsInfo.get(i).toString(), HbColumnMeta.class);
            String columnName = columnMeta.getColName();
            if (StringUtils.equalsIgnoreCase(colName, columnName)) {
                columnsInfo.remove(columnsInfo.get(i));
                columnMeta.setColValue(colValue);
                columnMeta.setColType(colType);
                columnMeta.setDesc(comment);
                columnsInfo.add(columnMeta);
                break;
            }
        }
        hbaseMetaMapper.update(hbMeta);
        //TODO 同步修改到 zoompeeker
    }

    public void updateConfig(File file) throws Exception {
        HbaseMeta hbaseMeta = parseHbaseMeta(file);
        String tableName = file.getName().replace(".xml", "");
        HbaseMeta hbTable = getHbTableByTableName(tableName);
        if (hbTable == null) {
            hbaseMetaMapper.insert(hbaseMeta);
        } else {
            hbTable.setColumns(hbaseMeta.getColumns());
            update(hbTable);
        }
    }

    private List<HbColumnMeta> parseHbColumnMeta(String columnContent)
            throws DocumentException {

        // 去掉xml字符串到头部命名空间
        String xmlContent = preHandleXmlString(columnContent);
        List<Map<String, String>> props = CommonUtils
                .readStringXmlOut(xmlContent);
        List<HbColumnMeta> columnMetas = new ArrayList<HbColumnMeta>();
        for (int i = 0; i < props.size(); i++) {
            HbColumnMeta columnMeta = new HbColumnMeta();
            Map<String, String> column = props.get(i);
            if (column == null)
                continue;
            columnMeta.setColName(column.get("name"));

            String value = column.get("value");
            if (value != null && value.split(",").length > 1
                    && !(value.contains("{"))) {
                String colValue = value.split(",")[1];
                String colType = value.split(",")[0];
                columnMeta.setColValue(colValue);
                columnMeta.setColType(colType);
            } else {
                columnMeta.setColValue("");
                columnMeta.setColType(value);
            }
            columnMeta.setDesc(column.get("description"));
            columnMetas.add(columnMeta);
        }
        return columnMetas;
    }

    private String preHandleXmlString(String columnContent) {
        int startIndex = columnContent.indexOf("\n");
        return columnContent.substring(startIndex);
    }

    public String getRowKeyFilePath() {
        return rowKeyFilePath + "/hbrowkey.ini";
    }

    public String getHbTableFileName(String tableName) {
        return hbtablesPath + "/" + tableName + ".xml";
    }

    private HbaseMeta parseHbaseMeta(File f) throws Exception {
        String tableName = f.getName().replace(".xml", "");
        String alias = "";
        String remark = "";
        List<String> strs = Files.readLines(f, Charset.forName("utf-8"));
        String content = StringUtils.join(strs, "\n");
        String correctContent = preHandleXmlString(content);
        List<Map<String, String>> columns = CommonUtils.readStringXmlOut(correctContent);
        List<HbColumnMeta> metaList = new ArrayList<HbColumnMeta>();
        for (int i = 0; i < columns.size(); i++) {
            Map<String, String> column = columns.get(i);
            String colName = column.get("name");
            String value = column.get("value");
            //解析xml文件中到value 字段
            String colType = "";
            String defaultVal = "";
            if (value != null) {
                if (value.contains("{")) {//处理文件中特殊到value值
                    colType = value;
                } else {
                    String[] cells = value.split(",");
                    if (cells.length > 1) {
                        colType = cells[0];
                        defaultVal = cells[1];
                    } else {
                        colType = value;
                    }
                }
            }
            String desc = column.get("description");
            HbColumnMeta columnMeta = new HbColumnMeta(tableName, colName, defaultVal, colType, desc);
            metaList.add(columnMeta);
        }
        return new HbaseMeta(tableName, alias, remark, metaList);
    }

    public String getHbtablesPath() {
        return hbtablesPath;
    }

    public void setHbtablesPath(String hbtablesPath) {
        this.hbtablesPath = hbtablesPath;
    }

}
