package com.boyaa.mf.service.task;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.boyaa.mf.constants.Constants;
import com.boyaa.mf.entity.task.ProcessInfo;
import com.boyaa.mf.entity.task.ProcessStatusEnum;
import com.boyaa.mf.entity.task.ProcessTypeEnum;
import com.boyaa.mf.entity.task.Task;
import com.boyaa.service.FileService;
import com.boyaa.servlet.ContextServlet;
import com.boyaa.servlet.ResultState;

/**
 * @类名 : ProcessService.java
 * @作者 : MarsHuang
 * @部门 : 德州扑克业务中心-开拓者工作室-公共组
 * @公司 : 博雅互动
 * @日期 : 2014-11-6  上午10:50:16
 * @描述 : 流程操作接口
 */
public abstract class ProcessService extends ProcessInfoService{
	
	@Autowired
	private TaskService taskService;
	
	private String uuid = "";
	//执行流程
	public abstract ResultState execute(ProcessInfo processInfo);
	
	protected void executeBefore(ProcessInfo processInfo){
		Map<ProcessInfo,Object> processInstance = ContextServlet.runningTaskInstance.get(processInfo.getTaskId());
		if(processInstance==null){
			processInstance = new HashMap<ProcessInfo, Object>();
		}
		processInstance.put(processInfo, processInfo);
		ContextServlet.runningTaskInstance.put(processInfo.getTaskId(), processInstance);
	}
	
	protected void executeAfter(ProcessInfo processInfo){
		ContextServlet.runningTaskInstance.remove(processInfo.getTaskId());
	}
	
	public ResultState process(ProcessInfo processInfo) {
		executeBefore(processInfo);
		
		try{
			ProcessInfo updateProcess = new ProcessInfo();
			updateProcess.setId(processInfo.getId());
			updateProcess.setStatus(ProcessStatusEnum.EXECUTING.getValue());
			updateProcess.setStartTime("now");
			updateProcess.setEndTime("");
			updateProcess.setLogInfo("");
			
			if(processInfo.getType()!=null && processInfo.getType().intValue()!=ProcessTypeEnum.FILE_UPLOAD.getValue()){
				updateProcess.setPath("");
			}else{
				updateProcess.setFileSize(FileService.getFileSize(Constants.FILE_DIR+processInfo.getPath()));
			}
			update(updateProcess);
			
			return execute(processInfo);
		}finally{
			executeAfter(processInfo);
		}
	}
	
	protected ResultState otherExecute(ProcessInfo processInfo){
		ProcessInfo updateProcess = new ProcessInfo();
		updateProcess.setStatus(ProcessStatusEnum.EXECUTION_END.getValue());
		updateProcess.setEndTime("now");
		updateProcess.setLogInfo("未知执行类型,直接返回结束");
		updateProcess.setId(processInfo.getId());
		
		update(updateProcess);
		
		return new ResultState(ResultState.SUCCESS,"未知执行类型,直接返回结束",null);
	}
	
	/**
	 * 根据流程ID,获取他输出文件的标题,输出标题要符合以下几个条件:1.没被其他流程依懒,2,自己有标题,所属的任务不是合并任务
	 * @param id
	 * @return
	 */
	public String getTitle(int id){
		ProcessInfo processInfo = findById(id);
		if(processInfo==null) 
			return null;
		if(StringUtils.isBlank(processInfo.getTitle())) 
			return null;
		if(beDepend(id,processInfo.getTaskId())) 
			return null;
		Task task = taskService.findById(processInfo.getTaskId());
		if(task.getOutputType()==1) 
			return null;
		
		return processInfo.getTitle();
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
}
