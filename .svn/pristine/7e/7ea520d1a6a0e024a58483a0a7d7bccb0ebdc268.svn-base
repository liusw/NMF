package com.boyaa.mf.service.task;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.boyaa.base.mail.Mail;
import com.boyaa.base.utils.CsvUtil;
import com.boyaa.base.utils.HDFSUtil;
import com.boyaa.mf.constants.Constants;
import com.boyaa.mf.entity.task.ProcessInfo;
import com.boyaa.mf.entity.task.ProcessStatusEnum;
import com.boyaa.mf.entity.task.ProcessTypeEnum;
import com.boyaa.mf.entity.task.Task;
import com.boyaa.mf.entity.task.TaskAudit;
import com.boyaa.mf.mapper.task.ProcessInfoMapper;
import com.boyaa.mf.mapper.task.TaskMapper;
import com.boyaa.mf.service.AbstractService;
import com.boyaa.mf.service.common.HiveJdbcService;
import com.boyaa.mf.util.CommonUtil;
import com.boyaa.mf.util.HiveUtils;
import com.boyaa.mf.util.SpringBeanUtils;
import com.boyaa.mf.util.ZipUtils;
import com.boyaa.mf.web.dto.AjaxObj;
import com.boyaa.servlet.ResultState;

@Service
public class TaskService extends AbstractService<Task,Integer> {
	static Logger taskLogger = Logger.getLogger("taskLogger");
	static Logger errorLogger = Logger.getLogger("errorLogger");
	
	private String dataTime = null;
	public String getDataTime() {
		return dataTime;
	}
	public void setDataTime(String dataTime) {
		this.dataTime = dataTime;
	}
	
	@Autowired
	private TaskMapper taskMapper;
	@Autowired
	private ProcessInfoMapper processInfoMapper;
	
	@Autowired
	private CreateHiveProcessService createHiveProcessService;
	@Autowired
	private CustomProcessSerivce customProcessSerivce;
	@Autowired
	private DefaultProcessSerivce defaultProcessSerivce;
	@Autowired
	private HbaseProcessSerivce hbaseProcessSerivce;
	@Autowired
	private HiveProcessService hiveProcessService;
	@Autowired
	private MysqlProcessService mysqlProcessService;
	@Autowired
	private ProcessInfoService processInfoService;
	@Autowired
	private HiveJdbcService hiveService;
	
	@Autowired
	private ProcessQueue processQueue;
	

	/**
	 * @param task
	 * @param processId 如果processId不为NULL,就从此流程开始导
	 * @return
	 */
	public boolean taskExecute(Task task,String processId,String uuid) {
		if(task!=null){
			taskLogger.info("流程执行开始,id="+task.getId());
			
			List<ProcessInfo> processInfos = processInfoService.findByTaskId(task.getId());
			
			if(processInfos==null || processInfos.size()<=0){
				Task _task = new Task();
				_task.setStatus(ProcessStatusEnum.EXECUTION_END.getValue());
				_task.setEndTime("now");
				_task.setLogInfo("没有可执行的流程");
				_task.setId(task.getId());
				
				super.update(_task);
			}else{
				Task _task = new Task();
				if(task.getStatus()!=null && task.getStatus()==ProcessStatusEnum.EXECUTION_ERROR.getValue()){
					_task.setReExecuteCount(task.getReExecuteCount()==null?1:task.getReExecuteCount()+1);
				}
				_task.setStatus(ProcessStatusEnum.EXECUTING.getValue());
				_task.setStartTime("now");
				_task.setEndTime("");
				_task.setPath("");
				_task.setFileSize(0l);
				_task.setLogInfo("");
				_task.setId(task.getId());
				super.update(_task);
				
				if(StringUtils.isBlank(processId)){
					Map<Object, Object> params = new HashMap<Object, Object>();
					params.put("status",ProcessStatusEnum.NOT_EXECUTED.getValue());
					params.put("taskId",task.getId());
					params.put("notype",ProcessTypeEnum.FILE_UPLOAD.getValue());
					
					processInfoService.updateProcessByTaskId(params);
				}
				
				for(int i=0;i<processInfos.size();i++){
					taskLogger.info("流程开始执行到第几个："+i);
					//每次执行前都判断一下这个任务是否被强制停止了
					if(isForcedStop(task.getId())){
						break;
					}
					
					ProcessInfo processInfo=processInfos.get(i);
					processInfo.setDescription(getDataTime());
					taskLogger.info("流程开始执行：id="+processInfo.getId()+",operation="+processInfo.getOperation()+",type="+processInfo.getType());
					
					if(StringUtils.isNotBlank(processId)){
						if(Integer.parseInt(processId)!=processInfo.getId()){
							taskLogger.info("带ID流程执行，ID不一致，跳过："+processInfo.getId()+","+processId);
							continue;
						}else{
							processId = null;
						}
					}
					//执行
					ResultState rs = executeProcess(processInfo,uuid);
					
					if(rs.getOk().intValue()==ResultState.FAILURE){
						//任务有可能是被停止造成执行出错,所以在返回出错之前,先判断当前任务的状态是否为停止状态,如果是则不更新直接返回
						if(isForcedStop(task.getId())){
							break;
						}
						
						_task = new Task();
						_task.setStatus(ProcessStatusEnum.EXECUTION_ERROR.getValue());
						_task.setEndTime("now");
						_task.setLogInfo(rs.getMsg().replace("\'","\""));
						_task.setId(task.getId());
						super.update(_task);
						TaskManager.getInstense().remove(uuid);
						return false;
					}
					if(i==processInfos.size()-1){
						String fileName = null;
						//判断是否为合并多个流程输出
						if(task.getOutputType()==1){
							fileName = joinFile(task);
							if(StringUtils.isBlank(fileName)){
								_task = new Task();
								_task.setStatus(ProcessStatusEnum.EXECUTION_ERROR.getValue());
								_task.setEndTime("now");
								_task.setLogInfo("合并文件出错");
								_task.setId(task.getId());
								super.update(_task);
								TaskManager.getInstense().remove(uuid);
								return false;
							}
						}else{
							fileName = rs.getData();
						}
						
						try{
							if(isForcedStop(task.getId())){
								break;
							}
							
							_task = new Task();
							_task.setStatus(ProcessStatusEnum.EXECUTION_END.getValue());
							_task.setEndTime("now");
							_task.setId(task.getId());
							//上传到hdfs
							if(StringUtils.isNotBlank(fileName)){
								String fullPath = fileName;
								if(processInfo.getType().intValue()!=ProcessTypeEnum.REAL_TIME_LOG.getValue()){
									String uploadFilePath = Constants.FILE_DIR + fileName;
									if(Constants.COMPRESS_TASK_FILE){
										ZipUtils.compressGzip(uploadFilePath,uploadFilePath+".gz");
										uploadFilePath = uploadFilePath+".gz";
									}
									
									fullPath = HDFSUtil.upload(new File(uploadFilePath), Constants.TASK_HDFS_DIR);
								}
								_task.setFileSize(HDFSUtil.getSize(fullPath));
								_task.setPath(fullPath);
							}
							
							super.update(_task);



							//发邮件
							String url = "";
							if(StringUtils.isNotBlank(task.getEmail())){
								StringBuilder sb = new StringBuilder();
								String attachment = null;
								sb.append("魔方(mf.oa.com)数据导出通知:《").append(task.getTaskName()).append("》已导出,任务id为:"+task.getId()).append("<br/>");
								if(task.getIsSendFile()!=null && task.getIsSendFile()==1){
									attachment = Constants.FILE_DIR + fileName;
								}else{
									url = Constants.DOMAIN+"log/task?action=down&tid="+task.getId();
									sb.append("下载地址为:").append("<a href=\"").append(url).append("\">").append(url).append("</a><br/>");
								}
								sb.append("问题反馈：黄奕能(MarsHuang@boyaa.com)");
								new Mail().send("导数据邮件提示:"+task.getTaskName(),task.getEmail(),sb.toString(),attachment);
							}
							//如果是被审批通过到任务，任务结束以后的处理
							TaskAuditService taskAuditService = SpringBeanUtils.getBean("taskAuditService",TaskAuditService.class);
							TaskAudit taskAudit = taskAuditService.findById(task.getId());
							if(taskAudit!= null){
								taskAuditService.taskFinishAudit(taskAudit,task.getTaskName(),fileName,url);
							}
						}catch (Exception e){
							_task = new Task();
							_task.setStatus(ProcessStatusEnum.EXECUTION_ERROR.getValue());
							_task.setEndTime("now");
							_task.setLogInfo("更新状态出错");
							_task.setId(task.getId());
							super.update(_task);
							errorLogger.error(e.getMessage());
						}
					}
				}
			}
			taskLogger.info("流程执行结束,id="+task.getId());
			TaskManager.getInstense().remove(uuid);

			return true;
		}
		TaskManager.getInstense().remove(uuid);
		return false;
	}
	
	/**
	 * 判断是否被强制停止掉
	 * @param taskId
	 * @return
	 */
	public boolean isForcedStop(int taskId){
		Task checkTask = findById(taskId);
		if(checkTask.getStatus()!=null && checkTask.getStatus()==ProcessStatusEnum.FORCED_STOP.getValue()){
			return true;
		}
		return false;
	}
	
	/**
	 * 判断流程类型,并且执行流程
	 * @param processInfo
	 * @return
	 */
	public ResultState executeProcess(ProcessInfo processInfo, String uuid){
		ProcessService processService = null;
		if(processInfo.getType()!=null && processInfo.getType().intValue()==ProcessTypeEnum.HIVE_QUERY.getValue()){//直接执行hive
			//processService = new HiveProcessService();
			processService = hiveProcessService;
		}else if(processInfo.getType()!=null && (processInfo.getType().intValue()==ProcessTypeEnum.HBASE_MULTI_QUERY.getValue() || 
				processInfo.getType().intValue()==ProcessTypeEnum.HBASE_SCAN.getValue() || processInfo.getType().intValue()==ProcessTypeEnum.HBASE_PUT.getValue())){
			//processService = new HbaseProcessSerivce();
			processService = hbaseProcessSerivce;
		}else if(processInfo.getType()!=null && processInfo.getType().intValue()==ProcessTypeEnum.CUSTOM_EXEC.getValue()){//通过反谢执行
			//processService = new CustomProcessSerivce();
			processService = customProcessSerivce;
		}else if(processInfo.getType()!=null && processInfo.getType().intValue()==ProcessTypeEnum.MYSQL_EXEC.getValue()){//mysql处理
			//processService = new MysqlProcessService();
			processService = mysqlProcessService;
		}else if(processInfo.getType()!=null && processInfo.getType().intValue()==ProcessTypeEnum.CREATE_HIVE.getValue()){//创建hive表流程
			//processService = new CreateHiveProcessService();
			processService = createHiveProcessService;
		}else{
			//processService = new DefaultProcessSerivce();
			processService = defaultProcessSerivce;
		}
		processService.setUuid(uuid);
		return processService.process(processInfo);
	}
	
	private String joinFile(Task task) {
		String outputColumn = task.getOutputColumn();
		String outputLink = task.getOutputLink();
		
		Map<Integer,String> processColumn = new HashMap<Integer, String>();
		String[] outputColumns = outputColumn.split("\\|");
		for(String column:outputColumns){
			String[] columns = column.split(":");
			processColumn.put(Integer.parseInt(columns[0].trim()), columns[1].trim());
		}

		PrintWriter print = null;
		try {
			Map<Integer,String> tempTableMap = new HashMap<Integer, String>();
			Map<Integer,String> tempTitleMap = new HashMap<Integer, String>();
			Iterator<Integer> it = processColumn.keySet().iterator();
			while (it.hasNext()) {
				int processId = it.next();
				String oColumn = processColumn.get(processId);
				
				ProcessInfo processInfo = processInfoService.findById(processId);
				String pcolumn = processInfo.getColumnName();
				String ptitle = processInfo.getTitle();
				
				//创建表,并加载数据
				String tableName = "temp_task_process_"+processId;
				hiveService.execute("drop table if exists "+tableName);
				taskLogger.info("execute:"+processId+","+"drop table if exists "+tableName);
				
				String columnName = HiveUtils.getHiveCreateTableColumn(pcolumn);
				StringBuilder sb = new StringBuilder();
				sb.append("create table ").append(tableName).append("(").append(columnName).append(") ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' STORED AS TEXTFILE");
				hiveService.execute(sb.toString());
				taskLogger.info("execute:"+processInfo.getId()+","+sb.toString());
				
				hiveService.execute("load data local inpath '" + HiveUtils.getLoadPath(Constants.FILE_DIR+processInfo.getPath()) + "' into table "+tableName);
				taskLogger.info("execute:"+processInfo.getId()+","+"load data local inpath '" + Constants.FILE_DIR+processInfo.getPath() + "' into table "+tableName);
				
				String tempTable = "select " + HiveUtils.columsSpecialSymbols(pcolumn) + " from " + tableName;
				tempTableMap.put(processId, tempTable);
				tempTitleMap.put(processId, CommonUtil.getTitle(oColumn,pcolumn,ptitle));
			}
			
			StringBuilder sqlColumn = new StringBuilder();
			StringBuilder sqlTable = new StringBuilder();
			StringBuilder title = new StringBuilder();
			String[] links = outputLink.split("=");
			String baseTable=null;
			boolean first=true;
			for(int i=0;i<links.length;i++){
				String link = links[i];
				String[] tempLink = link.split("\\.");
				title.append(first?"":",").append(tempTitleMap.get(Integer.parseInt(tempLink[0])));
				sqlColumn.append(first?"":",").append(HiveUtils.asName2Column("t"+i, processColumn.get(Integer.parseInt(tempLink[0]))));
				sqlTable.append(first?"":" left outer join ").append("(").append(tempTableMap.get(Integer.parseInt(tempLink[0]))).append(") ").append("t"+i).append(" ");
				if(!first){
					String[] tempBase = baseTable.split("\\.");
					sqlTable.append(" on (").append("t0.").append(HiveUtils.columsSpecialSymbols(tempBase[1]))
							  .append("=")
							  .append("t"+i).append(".").append(HiveUtils.columsSpecialSymbols(tempLink[1])).append(") ");
				}else{
					baseTable = link;
				}
				first=false;
			}
			
			StringBuilder sql = new StringBuilder();
			sql.append("select ").append(sqlColumn).append(" from ").append(sqlTable).append(" group by ").append(sqlColumn);
			
			String fileName = "task_"+task.getId() + "_" + new Date().getTime()+".csv";
			String path = Constants.FILE_DIR + fileName;
			print = CsvUtil.getPrint(path);
			if(StringUtils.isNotBlank(title.toString())){
				print.println(title.toString());
			}
			taskLogger.info("execute:"+task.getId()+","+sql.toString());
			hiveService.findAndSave(sql.toString(), print);
			
			return fileName;
		} catch (SQLException e) {
			errorLogger.error(e.getMessage());
		} catch (IOException e) {
			errorLogger.error(e.getMessage());
		}finally{
			if(hiveService!=null) hiveService.close();
			if(print!=null) print.close();
		}
		return null;
	}

	public List<Task> findTargetTask(int size){
		return findTargetTask(0,size);
	}

	/**
	 * @param start 开始查找的记录
	 * @param size 查多少条
	 * @return
	 */
	public List<Task> findTargetTask(int start,int size){
		List<Integer> instatus = new ArrayList<Integer>();
		instatus.add(ProcessStatusEnum.NOT_EXECUTED.getValue());
		instatus.add(ProcessStatusEnum.EXECUTION_ERROR.getValue());
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("inStatus",instatus);
		params.put("ltMaxReExecuteCount", 3);
		params.put("type", Constants.TASK_TYPE_DEFAULT);
		params.put("sort", "id");
		params.put("order", "asc");
		params.put("start", start);
		params.put("size", size);
		
		return super.findScrollDataList(params);
	}
	
	/**
	 * 功 能: 添加任务
	 * 描 述: 如查添加成功,反加该对象并带有ID,如果添加失败,直接返回NULL
	 */
	public Task add(Task task){
		if(task.getStatus()==null){
			task.setStatus(ProcessStatusEnum.NOT_EXECUTED.getValue());
		}
		if(task.getType()==null){
			task.setType(Constants.TASK_TYPE_DEFAULT);
		}
		super.insert(task);
		
		return task;
	}
	
	public int findWaitCount(int taskId) {
		List<Integer> instatus = new ArrayList<Integer>();
		instatus.add(ProcessStatusEnum.NOT_EXECUTED.getValue());
		instatus.add(ProcessStatusEnum.EXECUTION_ERROR.getValue());
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("inStatus",instatus);
		params.put("ltMaxReExecuteCount", 3);
		params.put("type", Constants.TASK_TYPE_DEFAULT);
		
		return super.findScrollDataCount(params);
	}

	public boolean stopExcute(Integer taskId) {
		Task task = this.findById(taskId);
		if(task!=null && task.getStatus().intValue()!=ProcessStatusEnum.NEED_AUDIT.getValue() && task.getStatus().intValue()!=ProcessStatusEnum.SEND_AUDIT.getValue()){
			Task _task = new Task();
			_task.setStatus(ProcessStatusEnum.FORCED_STOP.getValue());
			_task.setEndTime("now");
			_task.setLogInfo("停止执行");
			_task.setId(taskId);
			return super.update(_task)>0?true:false;
		}
		return false;
	}
	
	/**
	 * 添加任务
	 * {'taskName':'xxx','receiveEmail':'xxxx','userId':'1226',
	 * 	'process':[{tmpId:'p0',dependOn:'p1',preTempTable:'1','type':'1','operation':'sql','columnName':'xxx,xxx,xxx','title':'xx,xx'},{}]}
	 * @return
	 */
	public ResultState addTask(JSONObject jsonObject){
		ResultState resultState = null;
		String errorMsg = "添加任务出错";
		
		if(jsonObject==null){
			resultState = new ResultState(ResultState.FAILURE, "参数不正确");
			return resultState;
		}
		
		try {
			String userId = jsonObject.getString("userId");
			String userName = jsonObject.getString("userName");
			String email = jsonObject.getString("receiveEmail");
			String taskName = jsonObject.getString("taskName");
	
			Task task = new Task();
			task.setTaskName(taskName);
			task.setUserId(userId);
			task.setUserName(userName);
			task.setEmail(email);
			
			task = add(task);
	
			if (task == null) {
				return new ResultState(ResultState.FAILURE, "添加任务失败");
			}
			
			if(jsonObject.containsKey("process")){
				JSONArray jsonArray = jsonObject.getJSONArray("process");
				JSONObject processJson = null;
				ProcessInfo processInfo = null;
				boolean haveErrorProcess = false;
				
				//流程临时依懒关系
				Map<String,String> dependProcessMap = new HashMap<String, String>();
				//临时依懒Id与插入数据库后Id的对应关系
				Map<String,Integer> processKeyMap = new HashMap<String, Integer>();
				//多依赖流程关系
				Map<String,String> multiDependProcessMap = new HashMap<String, String>();
				
				for(int i=0;i<jsonArray.size();i++){
					processInfo = new ProcessInfo();
					processJson = jsonArray.getJSONObject(i);
					
					//String operation = processJson.getString("operation");
					String operation = getJsonValue(processJson,"operation","");
					String columnName = getJsonValue(processJson,"columnName","");
					String title = getJsonValue(processJson,"title","");
					String tmpId = getJsonValue(processJson,"tmpId","");
					String dependOn = getJsonValue(processJson,"dependOn","");
					String preTempTable = getJsonValue(processJson,"preTempTable","");
					String path = getJsonValue(processJson,"path","");
					String depend = getJsonValue(processJson,"depend","");
					
					processInfo.setColumnName(columnName);
					processInfo.setTitle(title);
					processInfo.setTaskId(task.getId());
					processInfo.setOperation(operation);
					processInfo.setPath(path);
					if(StringUtils.isNotBlank(dependOn)){
						dependProcessMap.put(tmpId, dependOn);
					}
					if(StringUtils.isNotBlank(depend)){
						multiDependProcessMap.put(tmpId, depend);
					}
					
					
					if(processJson.getIntValue("type")==ProcessTypeEnum.HIVE_QUERY.getValue() || 
							processJson.getIntValue("type")==ProcessTypeEnum.MYSQL_EXEC.getValue() || 
							processJson.getIntValue("type")==ProcessTypeEnum.CUSTOM_EXEC.getValue()){//hive查询
//						processInfo.setType(ProcessTypeEnum.HIVE_QUERY.getValue());
						if(StringUtils.isNotBlank(dependOn) && StringUtils.isNotBlank(preTempTable)){
							processInfo.setPreTempTable(1);
						}
//					}else if(processJson.getInt("type")==ProcessTypeEnum.HBASE_MULTI_QUERY.getValue()){//多线程
//						processInfo.setType(ProcessTypeEnum.HBASE_MULTI_QUERY.getValue());
					}
					processInfo.setType(processJson.getIntValue("type"));
					
					processInfo = processInfoService.add(processInfo);
					if (processInfo == null) {
						haveErrorProcess=true;
						errorMsg = "添加流程出错";
						break;
					}
					
					processKeyMap.put(tmpId, processInfo.getId());
				}
				if(!haveErrorProcess){
					try {
						//更新依懒关系
						Iterator<String> it = dependProcessMap.keySet().iterator();
						while (it.hasNext()) {
							String processId = (String) it.next();
							String dependProcessId = dependProcessMap.get(processId);
							
							processInfoService.updateDependOn(processKeyMap.get(processId),processKeyMap.get(dependProcessId));
						}
						
						//更新依懒关系
						Iterator<String> mit = multiDependProcessMap.keySet().iterator();
						while (mit.hasNext()) {
							String processId = (String) mit.next();
							//process_1___#temp__process__process_1__dd#,process_3___#temp__process__process_3__dd#
							String dependProcess = multiDependProcessMap.get(processId);
							String[] dps = dependProcess.split(",");
							
							StringBuffer sb = new StringBuffer();
							StringBuffer sb2 = new StringBuffer();
							for(int i=0;i<dps.length;i++){
								String dependInfo =  dps[i];
								String[] dependProcessIds = dependInfo.split("___");
								int realProcessId = processKeyMap.get(dependProcessIds[0]);
								dependInfo = dependProcessIds[1].replaceAll(dependProcessIds[0], String.valueOf(realProcessId));
								sb.append(i==0?"":",").append(dependInfo);
								sb2.append(i==0?"":",").append(dependProcessIds[1]);
							}
							
							processInfoService.updateDependOn(processKeyMap.get(processId),sb.toString(),sb2.toString());
						}
						
					} catch (SQLException e) {
						haveErrorProcess = true;
						errorMsg = "更新流程依懒出错";
						errorLogger.error(e.getMessage());
					}
				}
				
				//更新输出方式
				if(jsonObject.containsKey("outputType") && jsonObject.containsKey("outputColumn") && jsonObject.containsKey("outputLink") && jsonObject.getIntValue("outputType")==1 ){
					String outputColumn = getOutPutColumn(jsonObject.getString("outputColumn"),processKeyMap);
					String outputLink = outputLink(jsonObject.getString("outputLink"),processKeyMap);
					
					if(StringUtils.isNotBlank(outputColumn) && StringUtils.isNotBlank(outputLink)){
						Map<Object, Object> obj = new HashMap<Object, Object>();
						obj.put("outputType",1);
						obj.put("outputColumn", outputColumn);
						obj.put("outputLink", outputLink);
						
						Task _task = new Task();
						_task.setOutputType(1);
						_task.setOutputColumn(outputColumn);
						_task.setOutputLink(outputLink);
						_task.setId(task.getId());
						
						super.update(_task);
					}
				}
				
				if(haveErrorProcess){
					return new ResultState(ResultState.FAILURE, errorMsg);
				}else{
					processQueue.offer(task.getId());
					return new ResultState(ResultState.SUCCESS, "添加任务成功");
				}
			}
		} catch (JSONException e) {
			errorMsg = "传入参数有误";
			errorLogger.error(e.getMessage());
		} catch (SQLException e) {
			errorMsg = "数据库操作异常";
			errorLogger.error(e.getMessage());
		}
		return new ResultState(ResultState.FAILURE,errorMsg);
	}
	
	private String getJsonValue(JSONObject json,String key,String defaultValue){
		try {
			if(json.containsKey(key) && json.getString(key)!=null){
				return json.getString(key);
			}
		} catch (JSONException e) {
			errorLogger.error(e.getMessage());
		}
		return defaultValue;
	}
	
	/**
	 * process_1.remark
	 * @param outputLink
	 * @param processKey
	 * @return
	 */
	private String outputLink(String outputLink, Map<String, Integer> processKey) {
		StringBuilder sb = new StringBuilder();
		String[] outputColumns = outputLink.split("=");
		boolean first=true;
		for(String column:outputColumns){
			String[] columns = column.split("\\.");
			if(processKey.containsKey(columns[0].trim())){
				if(!first){
					sb.append("=");
				}else{
					first=false;
				}
				sb.append(processKey.get(columns[0])).append(".").append(columns[1].trim());
			}else{
				return null;
			}
		}
		return sb.toString();
	}

	/**
	 * process_1: aid, _uid|process_1: aid, _uid
	 * @param outputColumn
	 * @param processKey
	 * @return
	 */
	private String getOutPutColumn(String outputColumn,Map<String, Integer> processKey) {
		StringBuilder sb = new StringBuilder();
		String[] outputColumns = outputColumn.split("\\|");
		boolean first=true;
		for(String column:outputColumns){
			String[] columns = column.split(":");
			if(processKey.containsKey(columns[0].trim())){
				if(!first){
					sb.append("|");
				}else{
					first=false;
				}
				sb.append(processKey.get(columns[0])).append(":").append(columns[1].trim());
			}else{
				return null;
			}
		}
		return sb.toString();
	}

	public List<Task> queryExecuteLongTimeTask() {
		return taskMapper.queryExecuteLongTimeTask();
	}
	
	public String getProcessInfo(int taskId) {
		List<ProcessInfo> processes = processInfoMapper
				.getProcessOfTask(taskId);
		JSONArray jsonArray = new JSONArray();
		for (int i = 0; i < processes.size(); i++) {
			ProcessInfo processInfo = processes.get(i);
			JSONObject jsonProcess = new JSONObject();
			jsonProcess.put("processId", processInfo.getId());
			jsonProcess.put("operation", processInfo.getOperation());
			jsonProcess.put("dependId", processInfo.getDependId());
			jsonProcess.put("depend", processInfo.getDepend());
			jsonArray.add(jsonProcess);
		}
		return jsonArray.toJSONString();
	}

	public int updateStatusById(int taskId, int newStatus) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", taskId);
		params.put("status", newStatus);
		return taskMapper.updateStatusById(params);
	}
	public void updateByAutoTask(Map<String, Object> params) {
		taskMapper.updateByAutoTask(params);
	}
}