package com.boyaa.mf.service.task;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.boyaa.base.utils.JSONUtil;
import com.boyaa.mf.constants.Constants;
import com.boyaa.mf.entity.task.ProcessInfo;
import com.boyaa.mf.entity.task.ProcessStatusEnum;
import com.boyaa.mf.entity.task.ProcessTypeEnum;
import com.boyaa.mf.entity.task.Task;
import com.boyaa.mf.mapper.task.ProcessInfoMapper;
import com.boyaa.mf.service.AbstractService;
import com.boyaa.mf.util.CommonUtil;
import com.boyaa.mf.util.HiveUtils;
import com.boyaa.servlet.ResultState;

@Service
public class ProcessInfoService extends AbstractService<ProcessInfo,Integer> {
	static Logger taskLogger = Logger.getLogger("taskLogger");
	static Logger errorLogger = Logger.getLogger("errorLogger");
	
	@Autowired
	private ProcessInfoMapper processInfoMapper;
	@Autowired
	private TaskService taskService;
	
	/**
	 * 功 能: 添加业务流程
	 * 描 述: 如查添加成功,反加该对象并带有ID,如果添加失败,直接返回NULL
	 * 此方法由于所有的异常都自己给处理了,出现异常情况也不会在前端获取到,所以这个方法即将费掉
	 * @throws SQLException 
	 */
	public ProcessInfo add(ProcessInfo processInfo) throws SQLException{
		if(processInfo.getStatus()==null){
			processInfo.setStatus(ProcessStatusEnum.NOT_EXECUTED.getValue());
		}
		
		String operation = processInfo.getOperation();
		//如果是多线程执行
		if(processInfo.getType()!=null && processInfo.getType().intValue()==ProcessTypeEnum.HBASE_MULTI_QUERY.getValue()){
			try {
				JSONObject json = JSONUtil.parseObject(operation);
				
				if(!json.containsKey("delExists")){
					json.put("delExists", true);
				}
				if(!json.containsKey("threadCount")){
					json.put("threadCount",Constants.QUERY_MULTITHREAD_COUNT);
				}
				
				operation = json.toString();
				processInfo.setOperation(operation);
			} catch (JSONException e) {
				errorLogger.error(operation + " error: " + e.getMessage());
			}
		}
		
		boolean checkStatus = true;//是否需要检查审批
		if(processInfo.getStatus()!=null && processInfo.getStatus().intValue()==ProcessStatusEnum.PASS.getValue()){
			processInfo.setStatus(ProcessStatusEnum.NOT_EXECUTED.getValue());
			checkStatus = false;
		}
		
		boolean needAudit = false;//不检查语句，直接需要审批
		if(processInfo.getStatus() != null && processInfo.getStatus().intValue() == ProcessStatusEnum.NEED_AUDIT.getValue()){
			needAudit = true;
		}
		
		//去除制表符
		processInfo.setOperation(processInfo.getOperation().replaceAll("\t"," "));
		int result = super.insert(processInfo);
		if(result>0){
			//判断是否有查询要审批的字段,如果有,把任务的状态更改为待审核状态
			ResultState auResultState = getAuditStatus(processInfo);
			if(needAudit || (checkStatus && auResultState.getOk().intValue()==ResultState.FAILURE)){
				Task task = taskService.findById(processInfo.getTaskId());
				
				Task _task = new Task();
				_task.setStatus(ProcessStatusEnum.NEED_AUDIT.getValue());
				_task.setId(processInfo.getTaskId());
				String oldLogInfo = task.getLogInfo();
				String msg = (StringUtils.isNotBlank(oldLogInfo)?(oldLogInfo + (StringUtils.isNotBlank(auResultState.getMsg()) ? "|" : "")):"")+auResultState.getMsg();
				_task.setLogInfo("需要审批的信息有:"+msg.replaceAll("需要审批的信息有:", ""));
				taskService.update(_task);
			}
			return processInfo;
		}
		return null;
	}
	
	/**
	 * 判断是否要审核
	 * @param processInfo
	 * @return
	 */
	private ResultState getAuditStatus(ProcessInfo processInfo) {
		int processType = processInfo.getType();
		
		StringBuffer sb = new StringBuffer();//收集需要申批的字段
		if(processType==ProcessTypeEnum.HBASE_MULTI_QUERY.getValue() || processType==ProcessTypeEnum.HBASE_SCAN.getValue()){
			try {
				JSONObject json = JSONUtil.parseObject(processInfo.getOperation());
				String outputColumn = json.getString("output_column");
				String[] columns = outputColumn.split(",");
				
				
				JSONObject tableJson = CommonUtil.getAuditInfo("hbase","user_ucuser");
				if(tableJson!=null){
					String[] auditColumn = tableJson.getString("column").split(",");
					String[] auditColumnName = tableJson.getString("columnName").split(",");
					String name = tableJson.getString("name");
					
					for(int i=0;i<columns.length;i++){
						for(int j=0;j<auditColumn.length;j++){
							if(auditColumn[j].equals(columns[i].trim())){
								
								if(columns[i].trim().equals("email")&&(json.containsKey("replace") || json.containsKey("substr"))){
									//获取replace和substr,如果这里有处理email,就通过
									if((json.containsKey("replace") && hasKey(json,"email","replace")) || (json.containsKey("substr") && hasKey(json,"email","substr"))){
										continue;
									}
								}
								sb.append(sb.length()>0?",":name+"(user_ucuser)-->").append(auditColumnName[j]).append("("+auditColumn[j].trim()+")");
							}
						}
					}
				}
			} catch (Exception e) {
				errorLogger.error(processInfo.getOperation() + " error: " + e.getMessage());
			}
		}
		
		if(processType==ProcessTypeEnum.HIVE_QUERY.getValue()){
			List<String> tables = HiveUtils.getTables(processInfo.getOperation());
			if(tables!=null && tables.size()>0){
				try {
					StringBuffer tsb = null;
					for(String table:tables){
						tsb = new StringBuffer();
						
						if(table.endsWith("_text")){
							table = table.substring(0,table.length()-5);
						}
						
						JSONObject tableJson = CommonUtil.getAuditInfo("hive",table);
						if(tableJson!=null){
							String[] auditColumn = tableJson.getString("column").split(",");
							String[] auditColumnName = tableJson.getString("columnName").split(",");
							String name = tableJson.getString("name");
							
							for(int j=0;j<auditColumn.length;j++){
								if(processInfo.getOperation().contains(auditColumn[j])){
									sb.append(tsb.length()>0?",":name+"("+table+")-->").append(auditColumnName[j]).append("("+auditColumn[j].trim()+")");
								}
							}
						}
					}
				} catch (JSONException e) {
					errorLogger.error(e.getMessage());
				}
			}
			ResultState validatePartitionResultState = HiveUtils.validatePartition(processInfo.getOperation());
			if(validatePartitionResultState.getOk().intValue()==ResultState.FAILURE){
				sb.append(validatePartitionResultState.getMsg());
			}
		}
		
		if(StringUtils.isNotBlank(sb.toString())){
			return new ResultState(ResultState.FAILURE, sb.toString());
		}
		
		return new ResultState(ResultState.SUCCESS);
	}
	
	/**
	 * 判断hbase的执行语句中,根据类型判断是否含有指定的key,如"replace":{"email":"***,3"},调用方式是hasKey(json,"email","replace")
	 * @param json
	 * @param _key
	 * @param type
	 * @return
	 * @throws JSONException
	 */
	@SuppressWarnings("all")
	private boolean hasKey(JSONObject json,String _key,String type) throws JSONException{
		boolean flag = false;
		JSONObject jsonObject = json.getJSONObject(type);
		Iterator<String> it = jsonObject.keySet().iterator();
		while(it.hasNext()) {
			String key = it.next();
			if(key.equals(_key)){
				flag = true;
				break;
			}
		}
		return flag;
	}

	public List<ProcessInfo> findByTaskId(int taskId){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("taskId",taskId);
		params.put("sort", "dependId");
		params.put("order", "asc");
		params.put("start", 0);
		params.put("size", Integer.MAX_VALUE);
		
		List<ProcessInfo> processInfos = super.findScrollDataList(params);
		return orderProcess(processInfos);
	}
	
	public boolean updateDependOn(Integer id, Integer dependId) throws SQLException {
		ProcessInfo processInfo = new ProcessInfo();
		processInfo.setDependId(dependId);
		processInfo.setId(id);
		
		return super.update(processInfo)>0?true:false;
	}
	
	public boolean updateDependOn(Integer id, String depend,String tempDepend) throws SQLException {
		ProcessInfo pi = this.findById(id);
		
		String operation = pi.getOperation();
		String[] depends = depend.split(",");
		String[] tempdepends = tempDepend.split(",");
		for(int i=0;i<tempdepends.length;i++){
			operation = operation.replaceAll(tempdepends[i], depends[i]);
		}
		
		ProcessInfo processInfo = new ProcessInfo();
		processInfo.setDepend(depend);
		processInfo.setId(id);
		processInfo.setOperation(operation);
		
		return super.update(processInfo)>0?true:false;
	}
	
	
	public int updateProcessByTaskId(Map<Object, Object> params){
		return processInfoMapper.updateProcessByTaskId(params);
	}
	
//	/**
//	 * 对流程按依懒关系进行排序
//	 * 
//	 * @param psList
//	 * @param newPsList
//	 * @return
//	 */
//	private static List<ProcessInfo> orderProcess(List<ProcessInfo> psList,List<ProcessInfo> newPsList){
//		if(newPsList==null){
//			newPsList = new ArrayList<ProcessInfo>();
//		}
//		
//		boolean flag = false;
//		for(int i=0;i<psList.size();i++){
//			ProcessInfo ps = psList.get(i);
//			for(int j=0;j<newPsList.size();j++){
//				ProcessInfo newPs = newPsList.get(j);
//				if(newPs.getDependId()!=null && ps.getId()==newPs.getDependId()){
//					flag=true;
//					newPsList.add(j, ps);
//					break;
//				}
//			}
//			if(!flag){
//				newPsList.add(ps);
//			}
//			psList.remove(i);
//			--i;
//		}
//		return  newPsList;
//	}
	
	private List<ProcessInfo> orderProcess(List<ProcessInfo> psList){
		//求出流程ID间的关系
		Map<Integer,List<Integer>> ids = new HashMap<Integer, List<Integer>>();
		List<Integer> tempList = null;
		for(ProcessInfo processInfo:psList){
			tempList =  new ArrayList<Integer>();
			if(processInfo.getDependId()!=null){
				tempList.add(processInfo.getDependId());
			}
			if(StringUtils.isNotBlank(processInfo.getDepend())){
				String[] depends = processInfo.getDepend().split(",");
				for(int i=0;i<depends.length;i++){
					int dependProcessId = Integer.parseInt(depends[i].split("__")[2]);
					tempList.add(dependProcessId);
				}
			}
			ids.put(processInfo.getId(), tempList);
		}
		
		List<Integer> orderIds = new ArrayList<Integer>();
		for (Map.Entry<Integer, List<Integer>> entry : ids.entrySet()) {
			Integer id = entry.getKey();
			List<Integer> values = entry.getValue();
			if(values==null || values.size()==0){
				if(!orderIds.contains(id)){
					orderIds.add(id);
				}
				continue;
			}
			
			if(orderIds.contains(id)){//首先判断自己是否存在列表中,如果存在则把当前的加在自己的前面
				for(Integer dId:values){
					if(!orderIds.contains(dId)){
						int index = orderIds.indexOf(id);
						orderIds.add(index,dId);
					}else{
						int index = orderIds.indexOf(id);
						int dIndex = orderIds.indexOf(dId);
						if(index<dIndex){
//							orderIds.remove(dIndex);
//							orderIds.add(index,dId);
							orderIds = moveList(orderIds,ids,index,dIndex);
						}
					}
				}
			}else{
				orderIds.add(id);
				for(Integer dId:values){
					if(!orderIds.contains(dId)){
						int index = orderIds.indexOf(id);
						orderIds.add(index,dId);
					}
				}
			}
		}
		
		List<ProcessInfo> sortProcess = new ArrayList<ProcessInfo>();
		for(Integer id:orderIds){
			for(int i=0;i<psList.size();i++){
				ProcessInfo processInfo = psList.get(i);
				if(processInfo.getId()==id){
					sortProcess.add(processInfo);
					break;
				}
			}
		}
		return sortProcess;
	}
	
	private List<Integer> moveList(List<Integer> orderIds, Map<Integer, List<Integer>> ids, int index, int dIndex){
		List<Integer> newOrderIds= new ArrayList<Integer>();
		if(index!=0){
			for(int i=0;i<index;i++){
				newOrderIds.add(orderIds.get(i));
			}
		}
		int moveIdex = dIndex;
		moveIdex = recursiveMove(orderIds,ids,index,moveIdex,orderIds.get(dIndex));
		moveIdex = moveIdex==index?index+1:moveIdex;
		for(int i=moveIdex;i<dIndex+1;i++){
			newOrderIds.add(orderIds.get(i));
		}
		if(moveIdex>index){
			for(int i=index;i<moveIdex;i++){
				newOrderIds.add(orderIds.get(i));
			}
		}
		if(dIndex!=orderIds.size()-1){
			for(int i=dIndex+1;i<orderIds.size();i++){
				newOrderIds.add(orderIds.get(i));
			}
		}
		return newOrderIds;
	}
	
	private int recursiveMove(List<Integer> orderIds,Map<Integer, List<Integer>> ids, int index,int moveIndex,Integer obj){
		List<Integer> dependIds = ids.get(obj);
		if(dependIds!=null && dependIds.size()>0){
			for(int j=0;j<dependIds.size();j++){
				Integer depObj = dependIds.get(j);
				if(!orderIds.contains(depObj)){
					break;
				}
				
				int tempIndex = orderIds.indexOf(depObj);
				if(tempIndex<index){//所要依懒的在现在的前面,就不要移动了
					break;
				}
				if(tempIndex<moveIndex){
					moveIndex = tempIndex;
				}
				
				//递归查询
				moveIndex = recursiveMove(orderIds,ids,index,moveIndex,depObj);
			}
		}
		return moveIndex;
	}
	
	public boolean beDepend(int id,int taskId){
		if(processInfoMapper.beDepend(id)>0){
			return true;
		}
		
		boolean flag = false;
		//有时要依赖多个流程
		List<String> depends = processInfoMapper.findDependByTaskId(taskId);
		if(depends!=null && depends.size()>0){
			for(String depend:depends){
				if(StringUtils.isNotBlank(depend) && (depend.contains("__"+id+",")|| depend.endsWith("__"+id))){
					flag = true;
					break;
				}
			}
		}
		return flag;
	}

	public int update(ProcessInfo processInfo,boolean checkAudit) {
		//去除制表符
		processInfo.setOperation(processInfo.getOperation().replace("\t"," "));
		int result = super.update(processInfo);
		if(result>0 && processInfo.getType()!=null && checkAudit){
			//判断是否有查询要审批的字段,如果有,把任务的状态更改为待审核状态
			ResultState auResultState = getAuditStatus(processInfo);
			if(auResultState.getOk().intValue()==ResultState.FAILURE){
				Task task = taskService.findById(processInfo.getTaskId());
				
				Task _task = new Task();
				_task.setStatus(ProcessStatusEnum.NEED_AUDIT.getValue());
				_task.setId(processInfo.getTaskId());
				String oldLogInfo = task.getLogInfo();
				_task.setLogInfo((StringUtils.isNotBlank(oldLogInfo)?(oldLogInfo + (StringUtils.isNotBlank(auResultState.getMsg()) ? "|" : "")):"")+auResultState.getMsg());
				taskService.update(_task);
			}
			return 1;
		}
		return 0;
	}
	
	
}