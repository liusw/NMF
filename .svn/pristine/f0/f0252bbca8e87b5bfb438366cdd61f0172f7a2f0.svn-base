package com.boyaa.mf.util;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.boyaa.base.utils.JSONUtil;
import com.boyaa.mf.entity.task.ProcessInfo;
import com.boyaa.mf.entity.task.ProcessStatusEnum;
import com.boyaa.mf.entity.task.ProcessTypeEnum;
import com.boyaa.mf.service.task.ProcessInfoService;

/**
 * hbase任务的创建类
 * @author darcy
 * @date 20140929
 */
public class HbaseProcess{
	static Logger logger = Logger.getLogger(HbaseProcess.class);
	static Logger errorLogger = Logger.getLogger("errorLogger");
	static Logger fatalLogger = Logger.getLogger("fatalLogger");

	/**
	 * 创建hbase任务
	 * @param items : hbase子项
	 * @param itemsName ： hbase子项名
	 * @param dependenProcess ： 依赖的任务
	 * @param taskId ： task的id
	 * @param comparator : 比较json字符串
	 * @param passCheck : 是否不用检查
	 * @param threadCount : hbase的线程数，0：使用系统默认的线程数
	 * @return
	 * @throws Exception
	 */
	public ProcessInfo createProcess(String items,String itemsName,ProcessInfo dependenProcess,int taskId,JSONObject comparator,boolean passCheck,int threadCount) throws Exception{
		if(StringUtils.isEmpty(items)){
			throw new Exception("hbase的子项不能空!");
		}
		if(dependenProcess == null){
			throw new RuntimeException("依赖的数据没有执行成功！");
		}
		ProcessInfoService processInfoService = null;
		JSONObject json = JSONUtil.getJSONObject();
		boolean ifFullEmail = false;//是否获取完整邮箱
		if(items.contains("fullEmail")){
			ifFullEmail = true;
			if(!items.contains("email")){
				items = items.replaceAll("fullEmail","email");
			}else{
				items = items.replaceAll(",*fullEmail","");
				itemsName = itemsName.replaceAll(",*完整邮箱","");
				itemsName = itemsName.replaceAll("加密邮箱","完整邮箱");
			}
		}
		
		
		String outputColumn = dependenProcess.getColumnName().trim() + "," + items.trim();
		try {
			json.put("_tnm", "user_ucuser");
			json.put("retkey", items);
			json.put("rowkey", false);
			json.put("file_column", dependenProcess.getColumnName());
			json.put("output_column", outputColumn);
			json.put("delExists", true);
			if(threadCount>0){
				json.put("threadCount", threadCount);
			}
			json.put("retFilter", true);// 是否返回查找不到字段时的记录

			String outputTitle = dependenProcess.getTitle() + "," + itemsName;
			StringBuffer ipStr = new StringBuffer();
			if(items.contains("loginip")){
				outputTitle += ",最后登录地区";
				ipStr.append("loginip");
			}
			if(items.contains("regip")){
				outputTitle += ",注册地区";
				if(ipStr.length() > 0){
					ipStr.append(",regip");
				}else{
					ipStr.append("regip");
				}
			}
			json.put("ipformat",ipStr.toString());
			//json.put("output_title", outputTitle);
			
			if(items.contains("email") && !ifFullEmail){
				JSONObject replace = JSONUtil.getJSONObject();
				replace.put("email", "***,3");
				json.put("replace", replace);
			}
			
			if(items.contains("mtime") || items.contains("mactivetime") || items.contains("startTime") || items.contains("lastTime") || items.contains("thisTime")){
				JSONObject format = JSONUtil.getJSONObject();
				if(items.contains("mtime")){
					format.put("mtime", "yyyy-MM-dd");
				}
				if(items.contains("mactivetime")) {
					format.put("mactivetime", "yyyy-MM-dd");
				}
				if(items.contains("startTime")) {
					format.put("startTime", "yyyy-MM-dd");
				}
				if(items.contains("lastTime")) {
					format.put("lastTime", "yyyy-MM-dd");
				}
				if(items.contains("thisTime")) {
					format.put("thisTime", "yyyy-MM-dd");
				}
				json.put("format", format);
			}
			
			if(comparator != null){
				json.put("comparator__condition", comparator);
			}
			
			logger.info(json.toString());
			
			ProcessInfo hbaseProcess = new ProcessInfo();
			hbaseProcess.setType(ProcessTypeEnum.HBASE_MULTI_QUERY.getValue());
			hbaseProcess.setOperation(json.toString());
			hbaseProcess.setTaskId(taskId);
			String fullColumn = outputColumn;
			String ipTmp = ipStr.toString();
			if(StringUtils.isNotBlank(ipStr.toString())){
				if(ipTmp.contains("regip")){
					ipTmp = ipTmp.replace("regip", "regipRegion");
				}
				if(ipTmp.contains("loginip")){
					ipTmp = ipTmp.replace("loginip", "loginipRegion");
				}
				fullColumn = outputColumn + "," + ipTmp;
			}
			hbaseProcess.setColumnName(fullColumn);
			hbaseProcess.setTitle(outputTitle);
			if(passCheck){
				hbaseProcess.setStatus(ProcessStatusEnum.PASS.getValue());
			}
				
			hbaseProcess.setDependId(dependenProcess.getId());
			
			processInfoService = SpringBeanUtils.getBean("processInofoService",ProcessInfoService.class);
			hbaseProcess = processInfoService.add(hbaseProcess);
			
			return hbaseProcess;
		} catch (JSONException e) {
			errorLogger.error(e.getMessage());
			return null;
		}
	}
	
	/**
	 * 创建hbase任务
	 * @param items : hbase子项
	 * @param itemsName ： hbase子项名
	 * @param dependenProcess ： 依赖的任务
	 * @param taskId ： task的id
	 * @return
	 * @throws Exception
	 */
	public ProcessInfo createProcess(String items,String itemsName,ProcessInfo dependenProcess,int taskId) throws Exception{
		return this.createProcess(items, itemsName, dependenProcess, taskId, null,false,0);
	}
}
