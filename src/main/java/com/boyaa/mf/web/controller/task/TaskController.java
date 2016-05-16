package com.boyaa.mf.web.controller.task;

import com.alibaba.fastjson.JSONObject;
import com.boyaa.base.hbase.MultiThreadQuery;
import com.boyaa.base.utils.JSONUtil;
import com.boyaa.mf.constants.Constants;
import com.boyaa.mf.entity.task.ProcessInfo;
import com.boyaa.mf.entity.task.ProcessStatusEnum;
import com.boyaa.mf.entity.task.ProcessTypeEnum;
import com.boyaa.mf.entity.task.Task;
import com.boyaa.mf.service.task.*;
import com.boyaa.mf.util.HiveUtils;
import com.boyaa.mf.util.PageUtil;
import com.boyaa.mf.vo.LoginUserInfo;
import com.boyaa.mf.web.controller.BaseController;
import com.boyaa.mf.web.dto.AjaxObj;
import com.boyaa.service.HbaseService;
import com.boyaa.servlet.ContextServlet;
import com.boyaa.servlet.ResultState;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("task")
public class TaskController extends BaseController {
	
	@Autowired
	private TaskService taskService;
	@Autowired
	private ProcessInfoService processInfoService;
	@Autowired
	private LinkTaskService linkTaskService;
	@Autowired
	private DownloadService downloadService;
	@Autowired
	private AddLinkTaskService addLinkTaskService;
	
	@Autowired
	private ProcessQueue processQueue;
	
	/**
	 * 页面跳转请求
	 */
	@RequestMapping("list/{action}.htm")
	public String page(@PathVariable String action) {
		return "task/list/"+action;
	}
	
	@RequestMapping(value="list/down")
	@ResponseBody
	public void down(){
		downloadService.downFile(this.getRequest(),this.getResponse());
	}
	
	@RequestMapping(value="list/preview")
	@ResponseBody
	public void preview(){
		downloadService.preview(this.getRequest(),this.getResponse());
	}
	
	@RequestMapping(value="list/getAllTaskData")
	@ResponseBody
	public String getAllTaskData(Integer t){
		return getTaskData(t,false);
	}
	
	@RequestMapping(value="list/getUserTaskData")
	@ResponseBody
	public String getUserTaskData(Integer t){
		return getTaskData(t,true);
	}

	private String getTaskData(Integer t,boolean isUserTask) {
		int type = (t!=null && t.intValue()==1)?t:Constants.TASK_TYPE_DEFAULT;
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("type",type);
		
		getSearchParam(getRequest(),params);
		
		if(isUserTask){
			LoginUserInfo userInfo = getLoginUserInfo();
			params.put("userId",userInfo.getCode());
			params.put("email",userInfo.getEmail());
			params.put("byUser",true);
		}
		PageUtil<Task> page = taskService.getPageList(params);
				
		List<Task> tasks = page.getDatas();
		if(tasks!=null){
			for(Task task:tasks){
				task.setQueueWaitCount(processQueue.waitCount(task.getId()));
			}
		}
		
		return getDataTableJson(tasks,page.getTotalRecord()).toJSONString();
	}
	
	private void getSearchParam(HttpServletRequest request,Map<String, Object> params){
		String likeId = request.getParameter("sSearch_1");
		if(StringUtils.isNotBlank(likeId)){
			params.put("likeId", likeId);
		}
		
		String likeTaskName = request.getParameter("sSearch_2");
		if(StringUtils.isNotBlank(likeTaskName)){
			params.put("likeTaskName", likeTaskName);
		}
		
		String likeUserName = request.getParameter("sSearch_3");
		if(StringUtils.isNotBlank(likeUserName)){
			params.put("likeUserName", likeUserName);
		}
		
		String status = request.getParameter("sSearch_4");
		if(StringUtils.isNotBlank(status) && !"-1".equals(status)){
			params.put("status",Integer.parseInt(status));
		}
		
		String likeLogInfo = request.getParameter("sSearch_5");
		if(StringUtils.isNotBlank(likeLogInfo)){
			params.put("likeLogInfo",likeLogInfo);
		}
		
		String likeStartTime = request.getParameter("sSearch_8");
		if(StringUtils.isNotBlank(likeStartTime)){
			params.put("likeStartTime", likeStartTime);
		}
		
		String likeEndTime = request.getParameter("sSearch_9");
		if(StringUtils.isNotBlank(likeEndTime)){
			params.put("likeEndTime", likeEndTime);
		}
	}
	
	@RequestMapping(value="process/userExecuteTask")
	@ResponseBody
	public ResultState userExecuteTask(Integer taskId) throws IOException{
		
		if(taskId==null){
			return new ResultState(ResultState.FAILURE,"任务ID不能为空");
		}
		
		Task task = taskService.findById(taskId);
		if(task.getStatus()==null || task.getStatus()==ProcessStatusEnum.NOT_EXECUTED.getValue() 
				|| task.getStatus()==ProcessStatusEnum.EXECUTING.getValue()||task.getStatus()==ProcessStatusEnum.NEED_AUDIT.getValue()){
			return new ResultState(ResultState.FAILURE,"此任务的状态不允许执行");
		}
		
		LoginUserInfo userInfo = getLoginUserInfo();
		
		List<String> emailList = Arrays.asList(task.getEmail().split(","));
		if(!emailList.contains(userInfo.getEmail()) && userInfo.getCode().equals(task.getEmail())){
			return new ResultState(ResultState.FAILURE,"你没有权限执行些任务",null);
		}
		
		Task _task = new Task();
		_task.setStatus(ProcessStatusEnum.NOT_EXECUTED.getValue());
		_task.setReExecuteCount(1);
		_task.setId(task.getId());
		
		int result = taskService.update(_task);
		if(result>0){
			processQueue.offer(task.getId());
			return new ResultState(ResultState.SUCCESS,"操作成功");
		}
		
		return new ResultState(ResultState.FAILURE,"操作出现异常");
	}
	
	@RequestMapping(value="process/stopTask")
	@ResponseBody
	public ResultState stopTask(Integer taskId){
		if(taskId==null){
			return new ResultState(ResultState.FAILURE,"任务ID不能为空");
		}
		
		Map<ProcessInfo,Object> runningProcess = ContextServlet.runningTaskInstance.get(taskId);
		if(runningProcess!=null && runningProcess.size()>0){
			Iterator<ProcessInfo> it = runningProcess.keySet().iterator();
			while (it.hasNext()) {
				ProcessInfo ps = it.next();
				
				if(ps.getType()!=null){
					if(ps.getType().intValue()==ProcessTypeEnum.HBASE_MULTI_QUERY.getValue()){
						MultiThreadQuery mtq = (MultiThreadQuery) runningProcess.get(ps);
						mtq.stop();
					}else if(ps.getType().intValue()==ProcessTypeEnum.HIVE_QUERY.getValue()){
						String uniqueKey = (String) runningProcess.get(ps);
						StopJobQueue.getInstance().offer(uniqueKey+","+System.currentTimeMillis());
					}else if(ps.getType().intValue()==ProcessTypeEnum.HBASE_SCAN.getValue()){
						HbaseService hbaseService = (HbaseService) runningProcess.get(ps);
						hbaseService.close();
					}
				}
			}
		}
		
		boolean result = taskService.stopExcute(taskId);
		if(result){
			processQueue.stopRunningByTaskId(taskId);
			return new ResultState(ResultState.SUCCESS,"任务停止成功");
		}else{
			return new ResultState(ResultState.FAILURE,"任务停止失败");
		}
	}
	
	@RequestMapping(value="process/getProcessById")
	@ResponseBody
	public AjaxObj getProcessById(Integer id){
		ProcessInfo processInfo = processInfoService.findById(id);
		return new AjaxObj(AjaxObj.SUCCESS,"",processInfo);
	}
	
	/**
	 * 根据任务ID获取流程列表
	 */
	@RequestMapping(value="process/findProcessByTaskId")
	@ResponseBody
	public AjaxObj findProcessByTaskId(Integer taskId){
		List<ProcessInfo> processInfos = processInfoService.findByTaskId(taskId);
		return new AjaxObj(AjaxObj.SUCCESS,"请求成功",processInfos);
	}
	
	@RequestMapping(value="process/updateProcess")
	@ResponseBody
	private ResultState updateProcess(int id,String operation,String title,String columnName){
		
		ProcessInfo processInfo = new ProcessInfo();
		processInfo.setOperation(operation);
		processInfo.setTitle(title);
		processInfo.setColumnName(columnName);
		processInfo.setId(id);
		
		ProcessInfo info = processInfoService.findById(processInfo.getId());
		processInfo.setType(info.getType());
		processInfo.setTaskId(info.getTaskId());
		
		if(processInfo.getType().intValue()==ProcessTypeEnum.HIVE_QUERY.getValue()){
			ResultState resultState = HiveUtils.validateHQL(operation);
			if(resultState.getOk().intValue()==ResultState.FAILURE) {
				return new ResultState(ResultState.FAILURE,resultState.getMsg());
			}
		}
		
		ResultState state = null;
		int result = processInfoService.update(processInfo,true);
		if(result>0){
			state = new ResultState(ResultState.SUCCESS,"更新成功");
		}else{
			state = new ResultState(ResultState.FAILURE,"更新失败");
		}
		return state;
	}
	
	@RequestMapping(value="process/getTaskById")
	@ResponseBody
	public AjaxObj getTaskById(String id){
		Task task = taskService.findById(Integer.parseInt(id));
		return new AjaxObj(AjaxObj.SUCCESS,"",task);
	}
	
	@RequestMapping(value="process/updateTask")
	@ResponseBody
	private ResultState updateTask(String id,String taskName,String email,Integer outputType,String outputColumn,String outputLink,
			Integer isSendFile,String status){
		
		Task task = new Task();
		task.setTaskName(taskName);
		task.setEmail(email);
		task.setOutputType(outputType);
		task.setOutputColumn(outputColumn);
		task.setOutputLink(outputLink);
		task.setId(Integer.parseInt(id));
		task.setIsSendFile(isSendFile);
		if(StringUtils.isNotBlank(status)&&StringUtils.equals(status, "3")){
			task.setStatus(3);
		}
		
		int result = taskService.update(task);
		ResultState state = null;
		if(result>0){
			state = new ResultState(ResultState.SUCCESS,"更新成功");
		}else{
			state = new ResultState(ResultState.FAILURE,"更新失败");
		}
		return state;
	}
	
	/**
	 * 停止正在执行的任务
	 */
	@RequestMapping(value="process/stopExecutingTask")
	@ResponseBody
	public ResultState stopExecutingTask(){
		Integer taskId = processQueue.stopRunning();
		return new ResultState(ResultState.SUCCESS,taskId!=null?"已停止任务:"+taskId:"没有正在执行的任务",null);
	}
	
	@RequestMapping(value="process/startTask")
	@ResponseBody
	public ResultState startTask(Integer taskId,String processId){
		if(taskId==null){
			return new ResultState(ResultState.FAILURE,"任务ID不能为空");
		}
		
		processQueue.remove(taskId);
		Task task = taskService.findById(taskId);
		boolean result = taskService.taskExecute(task,processId,TaskManager.getInstense().getUUID());
		return result?new ResultState(ResultState.SUCCESS,"任务执行成功",null):new ResultState(ResultState.FAILURE,"任务执行失败",null);
	}
	
	@RequestMapping(value="process/moveQueue")
	@ResponseBody
	public ResultState moveQueue(Integer taskId,Integer type) throws IOException{
		
		if(taskId==null || type==null){
			return new ResultState(ResultState.FAILURE, "参数不正确");
		}
		
		if(type==1){
			processQueue.move2Head(taskId);
		}
		if(type==2){
			processQueue.move2Tail(taskId);
		}
		
		return new ResultState(ResultState.SUCCESS);
	}
	
	/**
	 * 根据json添加任务
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value="process/addByJson")
	@ResponseBody
	public ResultState addByJson(String taskJson, HttpServletResponse response) throws IOException{
		LoginUserInfo userInfo = this.getLoginUserInfo();
		
		JSONObject jsonObject = JSONUtil.parseObject(taskJson);
		jsonObject.put("userId", userInfo.getCode());
		jsonObject.put("userName",userInfo.getRealName());
		if(!jsonObject.containsKey("receiveEmail")){
			jsonObject.put("receiveEmail",userInfo.getEmail());
		}
		return taskService.addTask(jsonObject);
	}
	
	@RequestMapping(value="process/batchDateExe")
	@ResponseBody
	public ResultState batchExeByDate(String stm,String etm,String taskId) throws ParseException{
		if(StringUtils.isBlank(stm) || StringUtils.isBlank(etm) || StringUtils.isBlank(taskId)){
			return new ResultState(ResultState.FAILURE,"参数不正确");
		}
		
		SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");  
		java.util.Date dateBegin=formater.parse(stm);  
		java.util.Date dateEnd=formater.parse(etm);               
		  
		Calendar ca = Calendar.getInstance();   
		
      Task task = taskService.findById(Integer.parseInt(taskId));
         
		while (dateBegin.compareTo(dateEnd) <= 0) {
			ca.setTime(dateBegin);
			String timeStr = formater.format(dateBegin);

			taskService.setDataTime(timeStr);
			taskService.taskExecute(task, null, TaskManager.getInstense().getUUID());
			ca.add(Calendar.DATE, 1);// 把dateBegin加上1天然后重新赋值给date1

			dateBegin = ca.getTime();
		}
		
		return new ResultState(ResultState.SUCCESS);
	}
	
	@RequestMapping(value="process/addLinkTask")
	@ResponseBody
	public ResultState addLinkTask(){
		return linkTaskService.param2TaskJson(getRequest());
	}
	
	@RequestMapping(value="process/deleteTask")
	@ResponseBody
	public ResultState deleteTask(Integer id){
		if(id==null){
			return new ResultState(ResultState.FAILURE,"未传入正确的taskId");
		}
		
      Task task = taskService.findById(id);
		if(!task.getUserId().equals(getLoginUserInfo().getCode().toString())){
			return new ResultState(ResultState.FAILURE,"只能删除自己的任务");
		}
		
		taskService.delete(id);
		
		return new ResultState(ResultState.SUCCESS);
	}
	
	@RequestMapping(value="process/addTask")
	@ResponseBody
	public ResultState addTask(){
		return addLinkTaskService.param2TaskJson(this.getRequest());
	}

	/**
	* @描述 : 设置hsql的tm范围的可替换时间
	* @作者 : DarcyZeng
	* @日期 : Apr 29, 2015
	*/
	@RequestMapping(value="process/setTime")
	@ResponseBody
	public void setTime(String startTime,String endTime,String taskId){
		if(StringUtils.isNotBlank(taskId)){
			if(StringUtils.isNotBlank(startTime)){
				HiveProcessService.setHiveTime(Constants.START_TIME + taskId, startTime);
			}
			if(StringUtils.isNotBlank(endTime)){
				HiveProcessService.setHiveTime(Constants.END_TIME + taskId, endTime);
			}
		}
	}
	
	/**
	* @描述 : 取消hsql的tm范围的可替换时间
	* @作者 : DarcyZeng
	* @日期 : Apr 29, 2015
	*/
	@RequestMapping(value="process/cancelTime")
	@ResponseBody
	public void cancelTime(String taskId){
		if(StringUtils.isNotBlank(taskId)){
			HiveProcessService.cancelHiveTime(Constants.START_TIME + taskId);
			HiveProcessService.cancelHiveTime(Constants.END_TIME + taskId);
		}
	}
		
}
