package com.boyaa.mf.web.controller.config;

import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.boyaa.mf.entity.config.HiveMeta;
import com.boyaa.mf.entity.task.ProcessInfo;
import com.boyaa.mf.entity.task.ProcessStatusEnum;
import com.boyaa.mf.entity.task.ProcessTypeEnum;
import com.boyaa.mf.entity.task.Task;
import com.boyaa.mf.service.config.HiveMetaService;
import com.boyaa.mf.service.task.ProcessInfoService;
import com.boyaa.mf.service.task.ProcessQueue;
import com.boyaa.mf.service.task.TaskService;
import com.boyaa.mf.vo.LoginUserInfo;
import com.boyaa.mf.web.controller.BaseController;
import com.boyaa.mf.web.dto.AjaxObj;
import com.boyaa.servlet.ResultState;

/**
 * Created by liusw 创建时间：16-4-29.
 */
@Controller
@RequestMapping("hiveMeta")
public class HiveMetaController extends BaseController {

	@Autowired
	private HiveMetaService hiveMetaService;

	@Autowired
	private TaskService taskService;
	@Autowired
	private ProcessInfoService processInfoService;
	@Autowired
	private ProcessQueue processQueue;

	@RequestMapping(value = "createHivaTable")
	@ResponseBody
	public AjaxObj createHivaTable() {
		AjaxObj data = null;
		String hsql = getRequest().getParameter("hsql");
		String tableName = getRequest().getParameter("tableName");
		if (StringUtils.isNotEmpty(hsql)) {
			LoginUserInfo userInfo = getLoginUserInfo();

			Task task = new Task();
			task.setTaskName("创建hive表:" + tableName);
			task.setUserId(String.valueOf(userInfo.getCode()));
			task.setUserName(userInfo.getRealName());
			task.setEmail(userInfo.getEmail());
			task.setLogInfo("创建hive表需要审批");

			task = taskService.add(task);

			ProcessInfo processInfo = new ProcessInfo();
			processInfo.setType(ProcessTypeEnum.CREATE_HIVE.getValue());
			processInfo.setOperation(hsql);
			processInfo.setTaskId(task.getId());
			processInfo.setStatus(ProcessStatusEnum.NEED_AUDIT.getValue());
			try {
				processInfo = processInfoService.add(processInfo);
				// 把该任务活加到处理队列中
				processQueue.offer(task.getId());

				data = new AjaxObj(ResultState.SUCCESS);
			} catch (SQLException e) {
				errorLogger.error(e.getMessage());
				data = new AjaxObj(ResultState.FAILURE);
				data.setMsg(e.getMessage());
			}

		} else {
			data = new AjaxObj(ResultState.FAILURE);
			data.setMsg("语句不能为空!");
		}
		return data;
	}

	@RequestMapping(value = "syncXml2Hive")
	@ResponseBody
	public AjaxObj syncXml2Hive() {

		String tableName = getRequest().getParameter("tableName");
		if (StringUtils.isNotBlank(tableName)) {
			try {
				hiveMetaService.syncXml2Hive(tableName);
			} catch (Exception e) {
				errorLogger.error(e.getMessage());
			} finally {
				if (hiveMetaService != null) {
					hiveMetaService.close();
				}
			}
		}
		return new AjaxObj(AjaxObj.SUCCESS);
	}

	@RequestMapping(value = "updateComment")
	@ResponseBody
	public AjaxObj updateComment() {
		String id = getRequest().getParameter("id");
		String type = getRequest().getParameter("type");
		String columnName = getRequest().getParameter("columnName");
		String comment = getRequest().getParameter("comment");
		String tableName = getRequest().getParameter("tableName");
		AjaxObj data = new AjaxObj();
		if (StringUtils.isNotBlank(id) && StringUtils.isNotBlank(type)
				&& StringUtils.isNotBlank(columnName)
				&& StringUtils.isNotBlank(comment)) {
			try {
				HiveMeta hiveMeta = new HiveMeta();
				hiveMeta.setCdId(Integer.parseInt(id));
				hiveMeta.setType(Integer.parseInt(type));
				hiveMeta.setComment(comment);
				hiveMeta.setColumnName(columnName);
				hiveMetaService.updateComment(hiveMeta);
				data.setStatus(AjaxObj.SUCCESS);
				data.setMsg("更新数据成功！");

				if (HiveMetaService.isCache()) {// 如果获取元数据使用了缓存，则更新缓存的信息
					HiveMetaService.setCache(false);
					hiveMetaService.getColumnInfo(tableName);
					HiveMetaService.setCache(true);
				}
			} catch (Exception e) {
				errorLogger.error(e.getMessage());
				data.setStatus(ResultState.FAILURE);
				data.setMsg("更新数据失败！");
			} finally {
				if (hiveMetaService != null) {
					hiveMetaService.close();
				}
			}
		} else {
			data.setStatus(ResultState.FAILURE);
			data.setMsg("更新数据失败！");
		}
		return data;
	}

	@RequestMapping(value = "setCache")
	@ResponseBody
	public void setCache() {
		String value = getRequest().getParameter("cache");
		if ("true".equals(value)) {
			HiveMetaService.setCache(true);
		} else {
			HiveMetaService.setCache(false);
		}
	}

	@RequestMapping(value = "getColumnInfo")
	@ResponseBody
	public AjaxObj getColumnInfo(String tableName) {
		AjaxObj data = null;
		if (StringUtils.isBlank(tableName)) {
			return new AjaxObj(AjaxObj.FAILURE, "表名不能为空!");
		}
		
		try {
			JSONArray result = hiveMetaService.getColumnInfo(tableName);
			data = new AjaxObj(AjaxObj.SUCCESS);
			if (result != null) {
				data.setObj(result.toString());
			} else {
				data.setMsg("没有找到相关数据!");
			}
		} catch (SQLException e) {
			errorLogger.error(e.getMessage());
			return new AjaxObj(AjaxObj.FAILURE, e.getMessage());
		}
		return data;
	}

}