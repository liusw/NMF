package com.boyaa.mf.service.task;
import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.boyaa.base.utils.JSONUtil;
import com.boyaa.mf.entity.task.ProcessInfo;
import com.boyaa.servlet.ResultState;

/**
 * 自定义执行：使用反射执行相关的方法
 * 
 * @类名 : CustomProcessSerivce.java
 * @作者 : MarsHuang
 * @部门 : 德州扑克业务中心-开拓者工作室-公共组
 * @公司 : 博雅互动
 * @日期 : 2014-11-13 下午5:51:55
 */
@Service
public class CustomProcessSerivce extends ProcessService {
	static Logger taskLogger = Logger.getLogger("taskLogger");
	static Logger errorLogger = Logger.getLogger("errorLogger");
	static Logger fatalLogger = Logger.getLogger("fatalLogger");

	@Override
	public ResultState execute(ProcessInfo processInfo) {
		String operation = processInfo.getOperation();
		if (StringUtils.isNotBlank(operation)) {
			String[] arr = operation.split("\\|");
			String clazzName = arr[0];
			String methodName = arr[1];
			String params = arr.length==3?arr[2]:"{}";//参数
			taskLogger.info("clazzName:" + clazzName);
			taskLogger.info("methodName:" + methodName);
			// 反射执行
			Class<?> clazz = null;
			try {
				JSONObject json = JSONUtil.parseObject(params);
				clazz = Class.forName(clazzName);
				Method method = clazz.getMethod(methodName, ProcessInfo.class, JSONObject.class);
				Object obj = clazz.newInstance();
				if(StringUtils.isNotBlank(getUuid()) && clazz.newInstance() instanceof ProcessService){
					((ProcessService)obj).setUuid(getUuid());
				}
				ResultState resultState = (ResultState) method.invoke(obj, processInfo, json);
				return resultState;
			} catch (Exception e) {
				errorLogger.error(e.getMessage());
				return new ResultState(ResultState.FAILURE, "自定义执行异常："
						+ e.getMessage(), null);
			}
		}
		return new ResultState(ResultState.FAILURE, "自定义执行异常：没有执行操作信息", null);
	}
}