package com.boyaa.mf.web.controller.task;

import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boyaa.mf.constants.BaseData;
import com.boyaa.mf.entity.task.ProcessStatusEnum;
import com.boyaa.mf.entity.task.Task;
import com.boyaa.mf.entity.task.TaskAudit;
import com.boyaa.mf.service.task.ProcessQueue;
import com.boyaa.mf.service.task.TaskAuditService;
import com.boyaa.mf.service.task.TaskService;
import com.boyaa.mf.util.CommonUtil;
import com.boyaa.mf.util.PageUtil;
import com.boyaa.mf.vo.LoginUserInfo;
import com.boyaa.mf.web.controller.BaseController;
import com.boyaa.mf.web.dto.AjaxObj;
import com.boyaa.servlet.ResultState;

/**
 * Created by liusw
 * 创建时间：16-4-11.
 */
@Controller
@RequestMapping("task/audit")
public class TaskAuditController extends BaseController {

    @Autowired
    private TaskAuditService taskAuditService;
    @Autowired
    private TaskService taskService;
    @Autowired
	 private ProcessQueue processQueue;


    @RequestMapping(value = "applyTaskAudit")
    @ResponseBody
    public AjaxObj applyTaskAudit(String taskId) {

        //获取当前登录的用户
        LoginUserInfo userInfo = getLoginUserInfo();
        try {
            if (StringUtils.isEmpty(taskId)) return new AjaxObj(AjaxObj.FAILURE, "任务Id不能为空");
            Task task = taskService.findById(Integer.parseInt(taskId));
            if (task == null || (task.getStatus().intValue() != ProcessStatusEnum.NEED_AUDIT.getValue() && task.getStatus().intValue() != ProcessStatusEnum.SEND_AUDIT.getValue())) {
                return new AjaxObj(AjaxObj.FAILURE, "任务不存在，或者任务当前状态不需要发审批邮件");
            }

            AjaxObj resultState = taskAuditService.apply(getRequest(), task, userInfo);

            if (resultState.getStatus() == AjaxObj.SUCCESS) {
                //更新状态
                Task _task = new Task();
                _task.setId(task.getId());
                _task.setStatus(ProcessStatusEnum.SEND_AUDIT.getValue());
                taskService.update(_task);
                //taskAudit log
                String processInfo = taskService.getProcessInfo(task.getId());
                TaskAudit taskAudit = new TaskAudit(taskId + "", userInfo.getCode(), userInfo.getUsername() + "(" + userInfo.getRealName() + ")", processInfo, "", "", BaseData.TaskAuditType.AUDITING.getValue(), null,"");
                taskAudit.setApplyTime(new Timestamp(System.currentTimeMillis()));
                taskAuditService.insert(taskAudit);
            }
            return resultState;
        } catch (Exception e) {
            errorLogger.error(e.getMessage());
            return new AjaxObj(ResultState.FAILURE, "发送审批申请出错：" + e.getMessage());
        }
    }

    @RequestMapping(value = "passAudit")
    @ResponseBody
    public AjaxObj auditPass() {
        int id = Integer.parseInt(getRequest().getParameter("id"));
        String content = getRequest().getParameter("content");
        String sendType = getRequest().getParameter("sendType");
        String enNames = getRequest().getParameter("enNames");
        AjaxObj resultState = null;
        try {
            int result = taskService.updateStatusById(id, 0);
            if (result > 0) {
                processQueue.offer(id);
                resultState = new AjaxObj(AjaxObj.SUCCESS);
                resultState.setMsg("操作成功");

                LoginUserInfo userInfo = getLoginUserInfo();
                TaskAudit taskAudit = new TaskAudit();
                taskAudit.setTaskId(id+"");
                taskAudit.setAuditUser(userInfo.getUsername()+"("+userInfo.getRealName()+")");
                taskAudit.setAuditResult(BaseData.TaskAuditType.PASS.getValue());
                taskAudit.setResultNotice(Byte.valueOf(sendType));
                taskAudit.setResultReciever(taskAuditService.parseEnNamesToEmail(enNames,userInfo));
                taskAudit.setAuditRemark(content);
                taskAudit.setAuditTime(new Timestamp(System.currentTimeMillis()));
                taskAuditService.update(taskAudit);
                return resultState;
            }else{
                return new AjaxObj(AjaxObj.FAILURE,"更新任务状态失败");
            }
        } catch (Exception e) {
            errorLogger.error(e.getMessage());
            return new AjaxObj(AjaxObj.FAILURE, e.getMessage());
        }
    }
    @RequestMapping(value = "getAuditList")
    @ResponseBody
    public String getAuditList() {
        PageUtil<TaskAudit> page  = taskAuditService.getPageList();
        List<TaskAudit> data = page.getDatas();
        return getDataTableJson(data, data.size()).toJSONString();
    }
    @RequestMapping(value = "getDetail")
    @ResponseBody
    public String getDetail() {
        String id = getRequest().getParameter("id");
        TaskAudit detail  = taskAuditService.findDetail(id);
        return CommonUtil.toJSONString(detail);
    }
}
