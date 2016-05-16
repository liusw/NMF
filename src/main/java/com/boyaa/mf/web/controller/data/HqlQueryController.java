package com.boyaa.mf.web.controller.data;

import com.boyaa.mf.entity.task.ProcessInfo;
import com.boyaa.mf.entity.task.ProcessStatusEnum;
import com.boyaa.mf.entity.task.ProcessTypeEnum;
import com.boyaa.mf.entity.task.Task;
import com.boyaa.mf.service.task.ProcessInfoService;
import com.boyaa.mf.service.task.ProcessQueue;
import com.boyaa.mf.service.task.TaskService;
import com.boyaa.mf.util.HbaseProcess;
import com.boyaa.mf.util.HiveUtils;
import com.boyaa.mf.util.ServletUtil;
import com.boyaa.mf.util.SpringBeanUtils;
import com.boyaa.mf.web.controller.BaseController;
import com.boyaa.servlet.ResultState;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by liusw
 * 创建时间：16-5-3.
 */
@Controller
@RequestMapping(value="data/hql")
public class HqlQueryController extends BaseController{

    @Autowired
    private TaskService taskService;

    @Autowired
    private ProcessInfoService processInfoService;

    @RequestMapping(value="hqlQuery")
    @ResponseBody
    public ResultState hqlQuery(){

        HttpServletRequest request = getRequest();
        String hql = request.getParameter("hql");
        String items = request.getParameter("items");//用户数据项
        String itemsName = request.getParameter("itemsName");//用户数据项对应中文
        String fieldsName = request.getParameter("fieldsName");
        String taskName = request.getParameter("taskName");
        taskName =  StringUtils.isNotEmpty(taskName) ? taskName : ServletUtil.getTaskName("HQL导出", null, null, null, null);
        String receiveEmail = request.getParameter("email");//接受人员邮件地址

        ResultState failure = HiveUtils.validateHQL(hql);
        if(failure != null && failure.getOk().intValue()==ResultState.FAILURE) {
            return failure;
        }

        try {
            String userId = (String) request.getSession().getAttribute("code");
            String userName = (String) request.getSession().getAttribute("cname");
            String email = (String) request.getSession().getAttribute("email");

//			if(StringUtils.isNotEmpty(receiveEmail)){
//				boolean checkEmail = AdEmailService.checkEmail(receiveEmail);
//				if(checkEmail){
//					email = receiveEmail + ";ZhuangjieZheng@boyaa.com";
//				}else{
//					ResultState fail = new ResultState(ResultState.FAILURE);
//					fail.setMsg("接受人员到邮件在系统中不存在，请联系管理员!");
//					ServletUtil.responseRecords(response, null, fail);
//					return;
//				}
//			}

            Task task = new Task();
            task.setTaskName(taskName);
            task.setUserId(userId);
            task.setUserName(userName);
            task.setEmail(email);

            task = taskService.add(task);

            ProcessInfo processInfo = new ProcessInfo();
            processInfo.setType(ProcessTypeEnum.HIVE_QUERY.getValue());

            hql = hql.trim().replaceAll("\'","\"");
            processInfo.setOperation(hql);
            processInfo.setTaskId(task.getId());
            processInfo.setColumnName(fieldsName);
            processInfo.setTitle(fieldsName);
            if(StringUtils.isNotEmpty(receiveEmail)){
                processInfo.setStatus(ProcessStatusEnum.PASS.getValue());
            }

//			failure = ServletUtil.validPlatTm(hql);//检查是否把有tm或plat，没有的需要审批
//			if(failure != null){
//				processInfo.setStatus(ProcessStatusEnum.NOT_AUDIT.getValue());
//			}

            processInfo = processInfoService.add(processInfo);

            if(processInfo == null) {
                ResultState success = new ResultState(ResultState.FAILURE);
                success.setMsg("添加任务失败");
                return success;
            }

            if(StringUtils.isNotEmpty(items)){
                HbaseProcess hp = new HbaseProcess();
                if(StringUtils.isNotEmpty(receiveEmail)){
                    hp.createProcess(items, itemsName, processInfo, task.getId(),null,true,0);
                }else{
                    hp.createProcess(items, itemsName, processInfo, task.getId());
                }
            }

            // 把该任务活加到处理队列中
            SpringBeanUtils.getBean("processQueue",ProcessQueue.class).offer(task.getId());

            ResultState success = new ResultState(ResultState.SUCCESS);
            if(failure == null || failure.getOk().intValue()==ResultState.SUCCESS){
                success.setMsg("添加任务成功");
            }else{
                success.setMsg("添加任务失败," + failure.getMsg());
            }
            return success;
        } catch(Exception e) {
            errorLogger.error(e.getMessage());
            ResultState fail = new ResultState(ResultState.FAILURE);
            fail.setMsg("添加任务失败");
            return fail;
        }
    }
}
