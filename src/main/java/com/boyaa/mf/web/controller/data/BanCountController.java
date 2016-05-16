package com.boyaa.mf.web.controller.data;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.boyaa.base.utils.DateUtil;
import com.boyaa.base.utils.JSONUtil;
import com.boyaa.mf.entity.data.BanCount;
import com.boyaa.mf.entity.task.ProcessTypeEnum;
import com.boyaa.mf.service.data.BanCountService;
import com.boyaa.mf.service.task.TaskService;
import com.boyaa.mf.util.PageUtil;
import com.boyaa.mf.util.ServletUtil;
import com.boyaa.mf.web.controller.BaseController;
import com.boyaa.servlet.ResultState;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class BanCountController extends BaseController {

	@Autowired
	private BanCountService banCountService;
	@Autowired
	private TaskService taskService;

	/**
	 * 获取数据
	 */
	@RequestMapping(value = "data/banCount/getDatas")
	@ResponseBody
	public String getDatas(String tm) {
		if (StringUtils.isBlank(tm)) {
			return getDataTableJson(null, 0).toJSONString();
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tm", tm.replaceAll("-", ""));

		PageUtil<BanCount> page = banCountService.getPageList(map);

		return getDataTableJson(page.getDatas(), page.getTotalRecord()).toJSONString();
	}
	/**
	 * 获取数据
	 */
	@RequestMapping(value = "data/banQuery/getDatas")
	@ResponseBody
	public ResultState banQueryDatas(HttpServletResponse response) throws UnsupportedEncodingException {
		HttpServletRequest request = getRequest();


		String plat = request.getParameter("plat");
		String sid = request.getParameter("sid");
		String bpid = request.getParameter("bpid");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String title = "用户ID,备注,事由,付费用户,操作人ID,时间";
		String taskName = ServletUtil.getTaskName("禁封导出", plat, sid + "_" + bpid, startTime, endTime);
		try {
			String userId = (String) request.getSession().getAttribute("code");
			String userName = (String) request.getSession().getAttribute("cname");
			String email = (String) request.getSession().getAttribute("email");

			JSONObject jsonObject = JSONUtil.getJSONObject();
			jsonObject.put("taskName", taskName);
			jsonObject.put("userId", userId);
			jsonObject.put("userName", userName);
			jsonObject.put("receiveEmail", email);

			StringBuffer sb = new StringBuffer();
			sb.append("select `_plat`,`_uid`,content,remark,from_unixtime(cast(`_tm` as bigint), \"yyyy-MM-dd HH:mm:ss\"),aid from admin_act_logs where 1=1 ");
			if (StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)) {
				sb.append(" and tm between "
						+ DateUtil.convertPattern(startTime, DateUtil.DEFAULT_DATE_FORMAT, DateUtil.DATE_FORMAT)
						+ " and "
						+ DateUtil.addOneDay(endTime, DateUtil.DEFAULT_DATE_FORMAT, DateUtil.DATE_FORMAT));
				sb.append(" and from_unixtime(cast(`_tm` as bigint), \"yyyyMMdd\") between "
						+ DateUtil.convertPattern(startTime, DateUtil.DEFAULT_DATE_FORMAT, DateUtil.DATE_FORMAT)
						+ " and "
						+ DateUtil.convertPattern(endTime, DateUtil.DEFAULT_DATE_FORMAT, DateUtil.DATE_FORMAT));
			} else if (StringUtils.isNotBlank(startTime) && StringUtils.isBlank(endTime)) {
				sb.append(" and tm >= "
						+ DateUtil.convertPattern(startTime, DateUtil.DEFAULT_DATE_FORMAT, DateUtil.DATE_FORMAT)
						+ " and from_unixtime(cast(`_tm` as bigint), \"yyyyMMdd\")>= "
						+ DateUtil.convertPattern(startTime, DateUtil.DEFAULT_DATE_FORMAT, DateUtil.DATE_FORMAT));
			} else if (StringUtils.isBlank(startTime) && StringUtils.isNotBlank(endTime)) {
				sb.append(" and tm <= "
						+ DateUtil.addOneDay(endTime, DateUtil.DEFAULT_DATE_FORMAT, DateUtil.DATE_FORMAT)
						+ " and from_unixtime(cast(`_tm` as bigint), \"yyyyMMdd\")<= "
						+ DateUtil.convertPattern(endTime, DateUtil.DEFAULT_DATE_FORMAT, DateUtil.DATE_FORMAT));
			}

			sb.append(" and isban=1 ");

			if (StringUtils.isNotBlank(plat)) {
				sb.append(" and `_plat`=").append(plat);
			}
			if (StringUtils.isNotBlank(bpid)) {
				sb.append(" and `_bpid` in(" + bpid + ")");
			}

			JSONArray jsonArray = JSONUtil.getJSONArray();
			//hive
			JSONObject processJson = JSONUtil.getJSONObject();
			processJson.put("tmpId", "p0");
			processJson.put("type", ProcessTypeEnum.HIVE_QUERY.getValue());
			processJson.put("operation", sb.toString());
			jsonArray.add(processJson);

			// 查询hbase
			String fcolumn = "_uid,content,remark,mpay,tm,aid";
			String jsonStr = "{\"_tnm\":\"user_ucuser\"," + "\"retkey\":\"mpay\","
					+ "\"rowkey\":\"false\","
					+ "\"file_column\":\"_plat,_uid,content,remark,tm,aid\","
					+ "\"output_column\":\"_uid,content,remark,mpay,tm,aid\"}";

			//hbase
			JSONObject processJson2  = JSONUtil.getJSONObject();
			processJson2.put("tmpId", "p1");
			processJson2.put("type", ProcessTypeEnum.HBASE_MULTI_QUERY.getValue());
			processJson2.put("operation",jsonStr);
			processJson2.put("columnName",fcolumn);
			processJson2.put("dependOn","p0");
			jsonArray.add(processJson2);

			//把数据load到hive再次处理
			JSONObject processJson3 = JSONUtil.getJSONObject();

			processJson3.put("tmpId", "p2");
			processJson3.put("type", ProcessTypeEnum.HIVE_QUERY.getValue());
			processJson3.put("operation","select `_uid`,content,remark,case when cast(mpay as int)>0 then \"是\" else \"否\" end,aid,tm from #tmpTable#");
			processJson3.put("dependOn","p1");
			processJson3.put("preTempTable","1");
			processJson3.put("title",title);
			jsonArray.add(processJson3);

			jsonObject.put("process", jsonArray);

			ResultState resultState = taskService.addTask(jsonObject);
			return resultState;
		} catch(Exception e) {
			errorLogger.error(e.getMessage());
			return new ResultState(ResultState.FAILURE);
		}
	}
}
