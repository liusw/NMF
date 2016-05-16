package com.boyaa.mf.service.task;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boyaa.base.mail.Mail;
import com.boyaa.mf.constants.Constants;
import com.boyaa.mf.entity.task.Task;
import com.boyaa.mf.entity.task.TaskAudit;
import com.boyaa.mf.mapper.task.TaskAuditMapper;
import com.boyaa.mf.service.AbstractService;
import com.boyaa.mf.util.InterfaceUtil;
import com.boyaa.mf.vo.LoginUserInfo;
import com.boyaa.mf.web.dto.AjaxObj;
import com.boyaa.servlet.ResultState;

/**
 * Created by liusw
 * 创建时间：16-4-11.
 */
@Service
public class TaskAuditService extends AbstractService<TaskAudit, Integer> {


    @Autowired
    private TaskAuditMapper taskAuditMapper;

    public AjaxObj apply(HttpServletRequest request, Task task, LoginUserInfo userInfo) {

        AjaxObj resultState = null;
        String enName = request.getParameter("enName");
        final String title = request.getParameter("title");
        String content = request.getParameter("content");
        String rtxs = request.getParameter("rtxs");
        String type = request.getParameter("type");


        String cname = (String) request.getSession().getAttribute("cname");
        String _email = (String) request.getSession().getAttribute("email");

        String email = parseEnNamesToEmail(enName, userInfo);
        if (StringUtils.isBlank(rtxs) || StringUtils.isBlank(email) || StringUtils.isBlank(title) || StringUtils.isBlank(content)) {
            return new AjaxObj(AjaxObj.FAILURE, "参数不正确");
        }
        StringBuffer sb = new StringBuffer();
        if (type.equals("rtx")) {
            sb.append("任务编号：").append(task.getId()).append("\n");
            sb.append("申请人：").append(cname).append("(" + _email + ")").append("\n");
            sb.append("申请内容：").append(content).append("\n");
            InterfaceUtil.sendRTX("[魔方申请审批消息]" + title, sb.toString(), rtxs + "," + String.valueOf(userInfo.getCode()));
            resultState = new AjaxObj(ResultState.SUCCESS, "发送Rtx成功");
        } else {
            sb.append("任务名称：").append(task.getTaskName()).append("	<br/>");
            sb.append("任务编号：").append(task.getId()).append("<br/>");
            sb.append("申请人：").append(cname).append("(" + _email + ")").append("<br/>");
            sb.append("申请内容：").append(content).append("<br/>");
            sb.append("<br/><br/><br/>------------------------------------------------------------------------------<br/>");
            sb.append("本邮件为数据魔方发送的申请审批邮件；问题反馈：黄奕能(MarsHuang@boyaa.com)");
            final String sendContent = sb.toString();
            final String sendEmail = email;
            new Thread() {
                public void run() {
                    new Mail().send(title, sendEmail, sendContent, null);
                }
            }.start();
            resultState = new AjaxObj(ResultState.SUCCESS, "发送邮件成功");
        }
        return resultState;
    }

    public void taskFinishAudit(TaskAudit taskAudit, final String taskName, final String fileName, String dataUrl) {
        try {
            taskAudit.setDataUrl(dataUrl);
            taskAuditMapper.update(taskAudit);
            if(taskAudit.getResultNotice() == 0){
                final String email = taskAudit.getResultReciever();
                final StringBuffer table = new StringBuffer();
                table.append("魔方(mf.oa.com)数据导出通知:《").append(taskName).append("》已导出,任务id为:" + taskAudit.getTaskId()).append("<br/>");
                table.append("<table style='border: solid 1px #B4B4B4;'>");
                table.append("<tr><td align='center' valign='middle' stype='border: solid 1px #B4B4B4'>申请者:</td><td stype='border: solid 1px #B4B4B4'>"+taskAudit.getApplyName()+"</td></tr>" +
                        "<tr><td align='center' valign='middle' stype='border: solid 1px #B4B4B4'>申请日期:</td><td stype='border: solid 1px #B4B4B4'>"+taskAudit.getApplyTime()+"</td></tr>" +
                        "<tr><td align='center' valign='middle' stype='border: solid 1px #B4B4B4'>申请理由:</td><td stype='border: solid 1px #B4B4B4'>"+taskAudit.getApplyCause()+"</td></tr>" +
                        "<tr><td align='center' valign='middle' stype='border: solid 1px #B4B4B4'>审批时间:</td><td stype='border: solid 1px #B4B4B4'>"+taskAudit.getAuditTime()+"</td></tr>" +
                        "<tr><td align='center' valign='middle' stype='border: solid 1px #B4B4B4'>审批备注:</td><td stype='border: solid 1px #B4B4B4'>"+taskAudit.getAuditRemark()+"</td></tr>" +
                        "<tr><td align='center' valign='middle' stype='border: solid 1px #B4B4B4'>结果地址:</td><td stype='border: solid 1px #B4B4B4'>"+taskAudit.getDataUrl()+"</td></tr>");

                table.append("</table>");
                table.append("<br/>");
                table.append("问题反馈：黄奕能(MarsHuang@boyaa.com)");
                final String attachment = Constants.FILE_DIR + fileName;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        new Mail().send("导数据邮件提示:" + taskName, email, table.toString(), attachment);
                    }
                }).start();
            }
        } catch (Exception e) {
            errorLogger.error("更新TaskAudit失败:", e.getCause());
        }
    }

    public String parseEnNamesToEmail(String enNames, LoginUserInfo userInfo) {
        String email = "";
        if (org.apache.commons.lang.StringUtils.isNotBlank(enNames)) {
            String[] users = enNames.split(",");
            for (String en : users) {
                email += (en + "@boyaa.com;");
            }
        }
        if (!email.startsWith(userInfo.getEmail()) && !email.contains(";" + userInfo.getEmail())) {
            email += (userInfo.getEmail());
        }
        return email;
    }

    public TaskAudit findDetail(String id){
        return taskAuditMapper.findDetail(id);
    }
}
