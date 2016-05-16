package com.boyaa.mf.service.common;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.boyaa.base.geoip2.GeoIP2;
import com.boyaa.base.hbase.HBaseUtil;
import com.boyaa.base.utils.CommonUtil;
import com.boyaa.base.utils.Constants;
import com.boyaa.base.utils.DateUtil;
import com.boyaa.base.utils.JSONUtil;
import com.boyaa.servlet.ResultState;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HConstants;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by liusw
 * 创建时间：16-5-3.
 */
@Service
public class HbaseService extends HBaseUtil {
    static Logger taskLogger = Logger.getLogger("taskLogger");
    static Logger errorLogger = Logger.getLogger("errorLogger");
    static Logger fatalLogger = Logger.getLogger("fatalLogger");

    private String[] retKey;
    //	private Integer plat;
    private String[] outputColumn;
    private FilterList queryFilterList;
    private JSONObject conditionJson;
    private String ipformat;
    private JSONObject replace;
    private JSONObject substr;
    private JSONObject ipfilter;
    private JSONObject formats;

    private Table table = null;
    private PrintWriter print = null;
    private ResultScanner rs = null;
    private GeoIP2 geoip;

    /**
     *
     * 功 能: 扫描hbase
     */
    public ResultState scanData(JSONObject jsonObject, String path){
        taskLogger.info("start:"+path);
        long start = System.currentTimeMillis();
        try {
            //HConnectionSingleton.conf.setLong(HConstants.HBASE_REGIONSERVER_LEASE_PERIOD_KEY, 1800000);
            //HConnectionSingleton.create().get
//		    Configuration conf = HBaseConfiguration.create()
            Configuration conf = conn.getConfiguration();
            conf.setLong(HConstants.HBASE_CLIENT_SCANNER_TIMEOUT_PERIOD, 1800000);

            tableName = jsonObject.getString(Constants.table_name_key);
            table = getTable(tableName);
            if (table == null) {
                errorLogger.error("could not get hbase table=" + tableName);
                return new ResultState(ResultState.FAILURE,"could not get hbase table=" + tableName);
            }

            timezone = "GMT+8";
            outputColumn =  jsonObject.getString("output_column").split(",");
            retKey = jsonObject.getString("retkey").split(",");
            if(jsonObject.containsKey(Constants.comparator_condition)) {
                conditionJson=jsonObject.getJSONObject(Constants.comparator_condition);
                this.getFilter(conditionJson);
            }

            Scan scan = new Scan();
//			scan.setCacheBlocks(false);
//			scan.setCaching(1000);
            String[] StartEndRowKey = getStartEndRowKey(jsonObject);
            scan.setStartRow(StartEndRowKey[0].getBytes());
            scan.setStopRow(StartEndRowKey[1].getBytes());

            taskLogger.info("startRow:"+StartEndRowKey[0]);
            taskLogger.info("endRow:"+StartEndRowKey[1]);

            taskLogger.info("queryFilterList:"+queryFilterList);
            scan.setFilter(queryFilterList);

            if(jsonObject.containsKey("ipformat")){
                boolean setRetKey = true;
                ipformat = jsonObject.getString("ipformat");
                String[] ips = ipformat.split(",");
                List<String> fList = Arrays.asList(outputColumn);
                for(String ip:ips){
                    if(fList.contains(ip)){
                        setRetKey = false;
                        taskLogger.info("输出的文件中有,不用添加到hbase的retkey:"+ip);
                        break;
                    }
                }

                if(setRetKey){
                    List<String> arrayList = Arrays.asList(retKey);
                    List<String> retKeyList = new ArrayList<String>(arrayList);

                    for(String ip:ips){
                        if(!retKeyList.contains(ip)){
                            retKeyList.add(ip);
                            taskLogger.info("添加到hbase的retkey:"+ip);
                        }
                    }
                    final int size = retKeyList.size();
                    if(size > retKey.length){
                        retKey = (String[]) retKeyList.toArray(new String[size]);
                    }
                }
            }
            if(jsonObject.containsKey("format")){
                formats = jsonObject.getJSONObject("format");
            }

            if(jsonObject.containsKey("replace")){
                replace = jsonObject.getJSONObject("replace");
            }

            if(jsonObject.containsKey("substr")){
                substr = jsonObject.getJSONObject("substr");
            }

            for(int i=0;i<retKey.length;i++){
                scan.addColumn(Constants.COLUMN_FAMILY_BYTE, retKey[i].getBytes());
            }

            File file = new File(path);
            File dir = file.getParentFile();
            if(!dir.exists()){
                dir.mkdirs();
            }
            if(file.exists()){
                file.delete();
            }
            print=new PrintWriter(new BufferedWriter(new FileWriter(file)),true);
            if(jsonObject.containsKey("output_title")){
                print.println(jsonObject.get("output_title"));
            }

            JSONObject json = null;
            rs = table.getScanner(scan);
            Iterator<Result> results = rs.iterator();
            while(results.hasNext()) {

                json = this.getResult(results.next());

                if(filterResult(json)){
                    continue;
                }
                printScan(json, print);
            }
            print.flush();
            taskLogger.info("total time: " + (System.currentTimeMillis() - start));
            return new ResultState(ResultState.SUCCESS);
        } catch (Exception e) {
            errorLogger.error(e.getMessage());
            return new ResultState(ResultState.FAILURE,"hbase查询异常:" + e.getMessage());
        } finally{
            try {
                if(print != null)
                    print.close();
            } catch(Exception e) {
                errorLogger.error(e.getMessage());
            }
            try {
                if(rs != null)
                    rs.close();
            } catch(Exception e) {
                errorLogger.error(e.getMessage());
            }
            try {
                if(table != null)
                    table.close();
            } catch(Exception e) {
                errorLogger.error(e.getMessage());
            }
        }
    }
    public String[] getStartEndRowKey(JSONObject json) throws JSONException {
        // 先计算出总的rowkey范围
        String startRowKey = null;
        String endRowKey = null;
        JSONObject rowkeyRule = this.getRowKeyRule();//{"fields":{"_plat":5,"_uid":10,"_tm":10},"reverse":true,"line_num":true}

        JSONObject fields = rowkeyRule.getJSONObject(Constants.ROWKEY_JSON_FIELDS);
        Iterator<String> it = fields.keySet().iterator();
        String key = null;
        boolean isRemoveField=false;
        while(it.hasNext()) {
            key = it.next();

            if(isRemoveField){
                if(key.equals(Constants.COLUMN_NAME_TM)){
                    json.remove(Constants.COLUMN_NAME_STARTTIME);
                    json.remove(Constants.COLUMN_NAME_ENDTIME);
                }else{
                    json.remove(key);
                }
                continue;
            }

            if(!json.containsKey(key)){
                isRemoveField=true;
                continue;
            }
        }

        if (rowkeyRule.getBoolean(Constants.ROWKEY_JSON_REVERSE)) {
            // 设置开始时间，获取endrowkey
            if(json.containsKey(Constants.COLUMN_NAME_STARTTIME)){
                json.put(Constants.COLUMN_NAME_TM,json.getLong(Constants.COLUMN_NAME_STARTTIME));
            }
            // 生成endrowkey，没有参数时使用9填充
            endRowKey = this.getRangeRowKey(json, 0, "z");

            // 设置结束时间，获取startrowkey。如果为第一页，rowkey由程序产生，以后都是直接在缓存数组中获取
            if(json.containsKey(Constants.COLUMN_NAME_ENDTIME)){
                json.put(Constants.COLUMN_NAME_TM,json.getLong(Constants.COLUMN_NAME_ENDTIME));
            }

            // 生成startrowkey，没有参数时使用0填充
            startRowKey = this.getRangeRowKey(json, 0, "0");
        } else {
            // 设置开始时间，获取endrowkey
            if(json.containsKey(Constants.COLUMN_NAME_STARTTIME)){
                json.put(Constants.COLUMN_NAME_TM,json.getLong(Constants.COLUMN_NAME_STARTTIME));
            }
            // 生成endrowkey，没有参数时使用9填充
            startRowKey = this.getRangeRowKey(json, 0, "0");

            // 设置结束时间，获取startrowkey。如果为第一页，rowkey由程序产生，以后都是直接在缓存数组中获取
            if(json.containsKey(Constants.COLUMN_NAME_ENDTIME)){
                json.put(Constants.COLUMN_NAME_TM,json.getLong(Constants.COLUMN_NAME_ENDTIME));
            }
            endRowKey = this.getRangeRowKey(json, 0, "z");
        }
        return new String[] { startRowKey, endRowKey };
    }

    /**
     * 功 能: 根据查询条件获取过滤器
     * @throws JSONException
     */
    private void getFilter(JSONObject conditions) throws JSONException{
        if (conditions.size() != 0) {
            List<String> arrayList = Arrays.asList(retKey);
            List<String> retKeyList = new ArrayList<String>(arrayList);

            Iterator<String> it = conditions.keySet().iterator();
            JSONObject tmp = JSONUtil.getJSONObject();
            JSONObject rangeTmp = JSONUtil.getJSONObject();
            while(it.hasNext()) {
                String key = it.next();
                String[] value = conditions.getString(key).split(",");

                //判断是否为字符串
                if(value.length==2){
                    if(value[1].equals("0") || value[1].equals("3")){
                        tmp.put(key, value);
                    }else{
                        if(isNumberType(key)){
                            tmp.put(key, value);
                        }
                    }
                }else if(value.length==4 && isNumberType(key)){
                    rangeTmp.put(key, value);
                }

                //如果查询列中没有要过滤的列，则把他默认添加到查询列中
                if(!retKeyList.contains(key)){
                    retKeyList.add(key);
                }
            }

            if(tmp.keySet()!=null && tmp.size()>0){
                if(queryFilterList==null){
                    queryFilterList = new FilterList(FilterList.Operator.MUST_PASS_ALL);
                }
                queryFilterList.addFilter(this.getComparatorFilter(tmp));
            }
            if(rangeTmp.keySet()!=null && rangeTmp.size()>0){
                getRangeComparatorFilter(rangeTmp);
            }

            final int size = retKeyList.size();
            if(size > retKey.length){
                retKey = (String[]) retKeyList.toArray(new String[size]);
            }
        }
    }private boolean filterResult(JSONObject data){
        if (conditionJson!=null && conditionJson.size()>0) {
            try {
                Iterator<String> it = conditionJson.keySet().iterator();
                while(it.hasNext()) {
                    String key = it.next();

                    //如果不是数值类型,并且返回值中有那个key
                    if(!isNumberType(key)){
                        String[] _value = conditionJson.getString(key).split(",");
                        if(data.containsKey(key)){
                            String value = data.getString(key);
                            if(_value.length==2){
                                if(!CommonUtil.compareLong(_value[0],value,_value[1])) return true;
                            }else if(_value.length==4){
                                if(!CommonUtil.compareLong(_value[0],value,_value[1]) || !CommonUtil.compareLong(_value[2],value,_value[3])) return true;
                            }
                        }else{
                            if(!CommonUtil.compareLong(_value[0],"0",_value[1])){
                                errorLogger.info("返回结果中,没有返回key:"+key+",取默认值0,json:"+data.toString());
                                return true;
                            }
                        }
                    }

//					String[] _value = conditionJson.getString(key).split(",");
//					if(data.containsKey(key)){
//						String value = data.getString(key);
//
//						if(_value.length==2){
//							if(!CommonUtil.compareValue(_value[0],value,_value[1])) return true;
//						}else if(_value.length==4){
//							if(!CommonUtil.compareValue(_value[0],value,_value[1]) || !CommonUtil.compareValue(_value[2],value,_value[3])) return true;
//						}
//					}else{
//						if(!CommonUtil.compareValue(_value[0],"0",_value[1])){
//							errorLogger.info("返回结果中,没有返回key:"+key+",取默认值0,json:"+data.toString());
//							return true;
//						}
//					}
                }
            } catch (JSONException e) {
                errorLogger.error(e.getMessage());
            }
        }
        return false;
    }

    public void printScan(JSONObject data, PrintWriter print) throws JSONException{

        if(StringUtils.isNotBlank(ipformat)){
            geoip = new GeoIP2();
        }

        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < outputColumn.length; i++) {
            if(data != null && data.containsKey(outputColumn[i])) {
                //sb.append(data.getString(retKey[i])).append(",");
                String value = data.getString(outputColumn[i]);

                if(ipfilter!=null && ipfilter.containsKey(outputColumn[i])){
                    boolean filter = true;
                    String[] kv = ipfilter.getString(outputColumn[i]).split(",");
                    for(String v:kv){
                        if(value.startsWith(v)){
                            filter = false;
                            break;
                        }
                    }
                    if(filter) return;
                }

                //格式化数据
                if(formats!=null && formats.containsKey(outputColumn[i])){
                    try {
                        long obj = data.getLong(outputColumn[i]);
                        SimpleDateFormat sdf = new SimpleDateFormat(formats.getString(outputColumn[i]));
                        value= DateUtil.get(obj*1000, sdf, timezone);
                    } catch (Exception e) {
                        errorLogger.error(e.getMessage());
                    }
                }

                if(replace!=null && replace.containsKey(outputColumn[i])){
                    if(StringUtils.isNotBlank(value) && !value.equals("\\N")){
                        String[] kv = replace.getString(outputColumn[i]).split(",");
                        int replaceLength = Integer.parseInt(kv[1]);
                        String oldval = null;
                        if(value.length()<=Math.abs(replaceLength)){
                            oldval = value;
                        }else{
                            oldval = replaceLength>0?value.substring(0,replaceLength):value.substring(value.length()-(Math.abs(replaceLength)+1));
                        }
                        value=value.replace(oldval, kv[0]);
                    }
                }

                if(substr!=null && substr.containsKey(outputColumn[i])){
                    if(StringUtils.isNotBlank(value) && !value.equals("\\N")){
                        String[] kv = substr.getString(outputColumn[i]).split(",");

                        int subFrom = Integer.parseInt(kv[1]);
                        if(subFrom>0 && value.indexOf(kv[0])!=-1){
                            value = value.substring(0, value.indexOf(kv[0]));
                        }else if(value.lastIndexOf(kv[0])!=-1){
                            value = value.substring(value.lastIndexOf(kv[0])+1,value.length());
                        }
                    }
                }

                sb.append(value).append(",");
            } else {
                sb.append(",");
            }
        }
        if(StringUtils.isNotBlank(ipformat)){
            String[] ips = ipformat.split(",");
            for(String ip:ips){
                if(data.containsKey(ip)){
                    String value = data.getString(ip);
                    try {
                        geoip.setIp(ip);
                        value = String.format("%s", geoip.getCountryName().replace(",", ""));
                        sb.append(value).append(",");
                    } catch (Exception e) {
                        sb.append("").append(",");
                    }
                }else{
                    sb.append("").append(",");
                }
            }
        }

        if(sb.length()>0){
            print.println(sb.substring(0, sb.length()-1));
        }
    }

    private boolean isNumberType(String key){
        Map<String, String[]> columnTypeMapping = getColumnTypeMapping();
        String[] typeValue = getTypeValue(key, columnTypeMapping, "string");
        if(typeValue!=null && typeValue.length>0 &&
                ("int".equalsIgnoreCase(typeValue[0]) || "long".equalsIgnoreCase(typeValue[0]) || "float".equalsIgnoreCase(typeValue[0])|| "double".equalsIgnoreCase(typeValue[0]))){
            return true;
        }
        return false;
    }

    /**
     * 功 能: 获取范围过滤器
     * 描 述: 只支持正数,每一个key对应的value格式为：最小值 小于或小于等于操作符 最大值 大于或大于等于操作符
     */
    public FilterList getRangeComparatorFilter(JSONObject conditions) throws JSONException {
        Iterator<String> it = conditions.keySet().iterator();
        Map<String, String[]> columnTypeMapping = this.getColumnTypeMapping();

        if(queryFilterList==null){
            queryFilterList = new FilterList(FilterList.Operator.MUST_PASS_ALL);
        }

        while(it.hasNext()) {
            String key = it.next();
            String[] value = (String[])conditions.get(key);

            byte[] minObj = convertType(key, value[0], columnTypeMapping);
            byte[] maxObj = convertType(key, value[2], columnTypeMapping);

            FilterList	filters = new FilterList(FilterList.Operator.MUST_PASS_ALL);
            // 大于某个正数
            SingleColumnValueFilter and_greater_equal_positive = new SingleColumnValueFilter(Constants.COLUMN_FAMILY_BYTE, key.getBytes(), CompareOpENUM.get(value[1]), minObj);
            and_greater_equal_positive.setFilterIfMissing(true);
            filters.addFilter(and_greater_equal_positive);

            // 小于最大正数
            SingleColumnValueFilter or_less_positive = new SingleColumnValueFilter(Constants.COLUMN_FAMILY_BYTE, key.getBytes(),CompareOpENUM.get(value[3]), maxObj);
            or_less_positive.setFilterIfMissing(true);
            filters.addFilter(or_less_positive);

            queryFilterList.addFilter(filters);
        }
        return queryFilterList;
    }

    public void close() {
        try {
            if(print != null)
                print.close();
        } catch(Exception e) {
            errorLogger.error(e.getMessage());
        }
        try {
            if(rs != null)
                rs.close();
        } catch(Exception e) {
            errorLogger.error(e.getMessage());
        }
        try {
            if(table != null)
                table.close();
        } catch(Exception e) {
            errorLogger.error(e.getMessage());
        }
    }

}
