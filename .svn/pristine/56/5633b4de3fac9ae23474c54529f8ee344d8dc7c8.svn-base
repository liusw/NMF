package com.boyaa.mf.web.controller.task;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boyaa.mf.entity.task.AutoRunning;
import com.boyaa.mf.entity.task.Task;
import com.boyaa.mf.service.task.AutoRunningService;
import com.boyaa.mf.service.task.TaskService;
import com.boyaa.mf.web.controller.BaseController;
import com.boyaa.mf.web.dto.AjaxObj;
import com.boyaa.servlet.ResultState;

@Controller
@RequestMapping("task/auto")
public class AutoRunningController extends BaseController {
	
	@Autowired
	private AutoRunningService autoRunningService;
	@Autowired
	private TaskService taskService;
	
	@RequestMapping(value="setTaskAutorun")
	@ResponseBody
	public ResultState getSameTmpIdCount(Integer taskId,String runHour){
		Task task = taskService.findById(taskId);
		
		if(task==null){
			return new ResultState(ResultState.FAILURE,"不存在该任务");
		}
		
		AutoRunning autorunInfo = new AutoRunning();
		autorunInfo.setTaskId(taskId);
		if(StringUtils.isBlank(runHour)){
			runHour = "0500";
		}
		autorunInfo.setRunHour(runHour);
		autorunInfo.setUid(Integer.parseInt(task.getUserId()));
		autorunInfo.setUsername(task.getUserName());
		autorunInfo.setName(task.getTaskName());
		autoRunningService.setTaskAutorun(autorunInfo);
		
		return new ResultState(ResultState.SUCCESS);
	}
	
	@RequestMapping(value="cancelTaskAutorun")
	@ResponseBody
	public ResultState cancelTaskAutorun(Integer taskId){
		AutoRunning autorunInfo = new AutoRunning();
		autorunInfo.setTaskId(taskId);
		autoRunningService.cancelTaskAutorun(autorunInfo);
		return new ResultState(ResultState.SUCCESS,"");
	}
	
	@RequestMapping(value="updateAutoRunInfo")
	@ResponseBody
	public ResultState updateAutoRunInfo(Integer autoRunId,Integer status,String run_hour,Integer hours,Integer days,String last_time){
		
		if(autoRunId==null || autoRunId<=0){
			return new ResultState(ResultState.FAILURE,"id传入不正确");
		}
		
		AutoRunning autorunInfo = new AutoRunning();
		autorunInfo.setId(autoRunId);
		autorunInfo.setStatus(status);
		autorunInfo.setRunHour(run_hour);
		autorunInfo.setHours(hours);
		autorunInfo.setDays(days);
		autorunInfo.setLastTime(last_time);
		
		int result = autoRunningService.update(autorunInfo);
		if(result>0){
			return new ResultState(ResultState.SUCCESS);
		}
		
		return new ResultState(ResultState.FAILURE,"更新失败");
	}
	
	@RequestMapping(value="getSameTmpIdCount")
	@ResponseBody
	public AjaxObj getSameTmpIdCount(Integer templateId){
		int num = 0;
		if(templateId!=null && templateId > 0 ){
			num = autoRunningService.getSameTemplateIdCount(templateId);
		}
		return new AjaxObj(AjaxObj.SUCCESS,"获取相同模板id数",num);
	}
}
