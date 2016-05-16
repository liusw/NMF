package com.boyaa.mf.web.controller.data;

import com.boyaa.mf.entity.task.ProcessInfo;
import com.boyaa.mf.entity.task.ProcessTypeEnum;
import com.boyaa.mf.entity.task.Task;
import com.boyaa.mf.service.task.ProcessInfoService;
import com.boyaa.mf.service.task.ProcessQueue;
import com.boyaa.mf.service.task.TaskService;
import com.boyaa.mf.util.HbaseProcess;
import com.boyaa.mf.util.ServletUtil;
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
@RequestMapping("data/gamecoins")
public class GameCoinsController extends BaseController {

    @Autowired
    private TaskService taskService;
    @Autowired
    private ProcessInfoService processInfoService;

    @Autowired
    private ProcessQueue processQueue;

    @RequestMapping(value="coinsFlow")
    @ResponseBody
    public ResultState getCoinsFlow(){

        HttpServletRequest request = getRequest();
        String plat = request.getParameter("plat");
        String sid = request.getParameter("sid");
//		String bpid = request.getParameter("bpid");
        String taskname = ServletUtil.getTaskName("盲注输赢情况数据导出", plat, "", null, null);
        String rstime = request.getParameter("rstime");
        String retime = request.getParameter("retime");

        String itemsName = request.getParameter("itemsName");
        String items = request.getParameter("items");

        //查询分组的类型，包括按用户，按天及按天和用户分组三类
        String groupBy = request.getParameter("groupBy");
        try {
            String userId = (String) request.getSession().getAttribute("code");
            String userName = (String) request.getSession().getAttribute("cname");
            String email = (String) request.getSession().getAttribute("email");

            ResultState failure = new ResultState(ResultState.FAILURE);
            Task task = new Task();
            task.setTaskName(taskname);
            task.setUserId(userId);
            task.setUserName(userName);
            task.setEmail(email);
            task = taskService.add(task);

            if (task == null) {
                failure.setMsg("添加任务失败");
                return failure;
            }

            ProcessInfo p1 = null;
            StringBuffer pre_sql = new StringBuffer();

            String selectColumns = "plat,`_uid`,act_id,sum(act_num) as suma ";
            String groupBySql = " group by plat,`_uid`,act_id";
            String colNames = "_plat,_uid,act_id,suma";
            String titleCols = "plat,uid,act_id,suma";
            if("by-day".equals(groupBy)){
                selectColumns = "plat,tm,act_id,sum(act_num) as suma ";
                groupBySql = " group by plat,tm,act_id";
                colNames = "_plat,tm,act_id,suma";
                titleCols = "plat,tm,act_id,suma";
            }else if("by-day-user".equals(groupBy)){
                selectColumns = "plat,tm,`_uid`,act_id,sum(act_num) as suma ";
                groupBySql = " group by plat,tm,`_uid`,act_id";
                colNames = "_plat,tm,_uid,act_id,suma";
                titleCols = "plat,tm,uid,act_id,suma";
            }

            pre_sql.append("select ")
                    .append(selectColumns)
                    .append("from gamecoins_stream " )
                    .append("where plat=").append(plat);

            if(StringUtils.isNotEmpty(rstime) && StringUtils.isNotEmpty(retime)){
                pre_sql .append(    " and tm between ").append(rstime.replace("-", "")).append(" and ").append(retime.replace("-", ""));
            }else if(StringUtils.isNotEmpty(rstime) && StringUtils.isEmpty(retime)){
                pre_sql .append(    " and tm between ").append(rstime.replace("-", "")).append(" and ").append(rstime.replace("-", ""));
            }else if(StringUtils.isEmpty(rstime) && StringUtils.isNotEmpty(retime)){
                pre_sql .append(    " and tm between ").append(retime.replace("-", "")).append(" and ").append(retime.replace("-", ""));
            }

            if(StringUtils.isNotEmpty(sid)){
                pre_sql.append(" and sid in(").append(sid).append(")");
            }

            pre_sql.append(groupBySql);

            p1 = new ProcessInfo();
            p1.setType(ProcessTypeEnum.HIVE_QUERY.getValue());
            p1.setOperation(pre_sql.toString());
            p1.setTaskId(task.getId());
            p1.setColumnName(colNames);
            p1.setTitle(titleCols);
            p1 = processInfoService.add(p1);


            if(StringUtils.isNotEmpty(items) && !"by-day".equals(groupBy)){
                HbaseProcess hp = new HbaseProcess();
                hp.createProcess(items, itemsName, p1, task.getId(),null,false,0);
            }

            // 把该任务活加到处理队列中
            processQueue.offer(task.getId());

            ResultState success = new ResultState(ResultState.SUCCESS);
            success.setMsg("添加任务成功");
            return success;
        } catch(Exception e) {
            errorLogger.error(e.getMessage());
            return new ResultState(ResultState.FAILURE);
        }
    }
}
