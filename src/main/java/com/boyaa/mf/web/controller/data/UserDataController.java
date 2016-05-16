package com.boyaa.mf.web.controller.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.boyaa.base.utils.HttpUtil;
import com.boyaa.base.utils.JSONUtil;
import com.boyaa.mf.constants.Constants;
import com.boyaa.mf.entity.task.ProcessInfo;
import com.boyaa.mf.entity.task.ProcessTypeEnum;
import com.boyaa.mf.entity.task.Task;
import com.boyaa.mf.service.task.ProcessInfoService;
import com.boyaa.mf.service.task.ProcessQueue;
import com.boyaa.mf.service.task.TaskService;
import com.boyaa.mf.util.HbaseProcess;
import com.boyaa.mf.util.ServletUtil;
import com.boyaa.mf.vo.LoginUserInfo;
import com.boyaa.mf.web.controller.BaseController;
import com.boyaa.servlet.ResultState;

/**
 * 非主要IP地区数据导出
 * 
 * @author Jack
 * 
 */

@Controller
@RequestMapping(value="data/user")
public class UserDataController extends BaseController {
	static Logger logger = Logger.getLogger(UserDataController.class);
	static Logger taskLogger = Logger.getLogger("taskLogger");
	
	@Autowired
	private ProcessInfoService processInfoService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private ProcessQueue processQueue;
	
	@RequestMapping(value="nonmainQueryData")
	@ResponseBody
	public ResultState nonmainQuery(String plat,String sid,String bpid,String rstime,String retime,String lstime,String letime,String zone,String svmoney
			,String evmoney,String items,String gstime,String getime,String gamecoins,String itemsName,String rRelation) throws Exception{
		
		String taskname = ServletUtil.getTaskName("用户信息导出", plat, sid + "_" + bpid, null, null);
		StringBuffer title = new StringBuffer("_plat,_uid");
		
		LoginUserInfo userInfo = this.getLoginUserInfo();
			
		Task task = new Task();
		task.setTaskName(taskname);
		task.setUserId(String.valueOf(userInfo.getCode()));
		task.setUserName(userInfo.getRealName());
		task.setEmail(userInfo.getEmail());
		task = taskService.add(task);

		if (task == null) {
			return new ResultState(ResultState.FAILURE,"添加任务失败");
		}
		
		ProcessInfo p1 = null;
		StringBuffer pre_sql = new StringBuffer();
		if(StringUtils.isNotEmpty(rstime) && StringUtils.isNotEmpty(retime) && StringUtils.isNotEmpty(lstime) && StringUtils.isNotEmpty(letime)) {
			pre_sql.append("select a.`_plat`, a.`_uid` from (").append("select `_plat`, `_uid`, ip from user_signup where tm between ")
			.append(rstime.replace("-", "")).append(" and ").append(retime.replace("-", "")).append(" and `_plat`=").append(plat);
			if(StringUtils.isNotEmpty(bpid)){
				pre_sql.append(" and `_bpid` in(").append(bpid).append(")");
			}
			if("and".equals(rRelation)){
				pre_sql.append(")b join (");
			}else{
				pre_sql.append(" union all ");
			}
			pre_sql.append("select `_plat`, `_uid`, ip from user_login where plat=").append(plat).
				append(" and tm between ").append(lstime.replace("-", "")).append(" and ").append(letime.replace("-", "")).append(" and `_plat`=").append(plat);
			if(StringUtils.isNotEmpty(zone)){
				pre_sql.append(" and geoip(ip) not in(").append(zone).append(")");
			}
			if(StringUtils.isNotEmpty(bpid)){
				pre_sql.append(" and `_bpid` in(").append(bpid).append(")");
			}
			if("and".equals(rRelation)){
				pre_sql.append(")a on(a.`_uid`=b.`_uid`)");
			}else{
				pre_sql.append(")a ");
			}
			pre_sql.append(" group by a.`_plat`, a.`_uid`");
		} else if(StringUtils.isNotEmpty(rstime) && StringUtils.isNotEmpty(retime)) {
			pre_sql.append("select `_plat`, `_uid` from user_signup where tm between ").append(rstime.replace("-", "")).append(" and ").append(retime.replace("-", "")).append(" and `_plat`=").append(plat);
			if(StringUtils.isNotEmpty(bpid))
				pre_sql.append(" and `_bpid` in(").append(bpid).append(")");
		} else if(StringUtils.isNotEmpty(lstime) && StringUtils.isNotEmpty(letime)) {
			pre_sql.append("select `_plat`, `_uid` from user_login where plat=").append(plat).
					append(" and tm between ").append(lstime.replace("-", "")).append(" and ").append(letime.replace("-", "")).append(" and `_plat`=").append(plat);
			if(StringUtils.isNotEmpty(zone))
				pre_sql.append(" and geoip(ip) not in(").append(zone).append(")");
			if(StringUtils.isNotEmpty(bpid))
				pre_sql.append(" and `_bpid` in(").append(bpid).append(")");
			pre_sql.append(" group by `_plat`, `_uid`");
		}
		
		p1 = new ProcessInfo();
		p1.setType(ProcessTypeEnum.HIVE_QUERY.getValue());
		p1.setOperation(pre_sql.toString());
		p1.setTaskId(task.getId());
		p1.setColumnName(title.toString());
		p1.setTitle(title.toString());
		p1 = processInfoService.add(p1);
		
		logger.info(pre_sql.toString());
		
		StringBuffer gamecoins_sql = new StringBuffer();
		ProcessInfo gamecoins_process = null;
		StringBuffer dynamic_columns = new StringBuffer("a.`_plat`, a.`_uid`");
		if(StringUtils.isNotEmpty(gstime) && StringUtils.isNotEmpty(getime) && StringUtils.isNotEmpty(gamecoins)) {
			gamecoins_sql.append("select /*dynamic_columns*/ from (select * from #tmpTable#)a left outer join (select plat, `_uid`");
			String[] acts = gamecoins.split(",");
			for(String act_id : acts) {
				title.append(",").append("act_").append(act_id);
				dynamic_columns.append(",").append(" b.act_").append(act_id);
				gamecoins_sql.append(",").append(" sum(case when act_id=").append(act_id).append(" then act_num end) as act_").append(act_id);
			}
			gamecoins_sql.append(" from gamecoins_stream where plat=").append(plat).append(" and tm between ").append(gstime.replace("-", "")).append(" and ").append(getime.replace("-", ""));
			if(StringUtils.isNotEmpty(sid))
				gamecoins_sql.append(" and sid in(").append(sid).append(")");
			gamecoins_sql.append(" group by plat, `_uid`)b on(a.`_uid`=b.`_uid`)");
			
			String sql = gamecoins_sql.toString().replace("/*dynamic_columns*/", dynamic_columns.toString());
			gamecoins_process = new ProcessInfo();
			gamecoins_process.setType(ProcessTypeEnum.HIVE_QUERY.getValue());
			gamecoins_process.setOperation(sql);
			gamecoins_process.setTaskId(task.getId());
			gamecoins_process.setColumnName(title.toString());
			gamecoins_process.setTitle(title.toString());
			if(p1 != null){
				gamecoins_process.setDependId(p1.getId());
				gamecoins_process.setPreTempTable(1);
			}
			gamecoins_process = processInfoService.add(gamecoins_process);
		}
		logger.info(gamecoins_sql.toString());
		
		if(StringUtils.isNotEmpty(items)){
			HbaseProcess hp = new HbaseProcess();
			JSONObject comparator = null;
			
			
			if(StringUtils.isNotBlank(svmoney) && StringUtils.isNotBlank(evmoney)) {
				comparator = JSONUtil.getJSONObject();
				comparator.put("vmoney", svmoney + ",5," + evmoney + ",2");
			}else if(StringUtils.isNotBlank(svmoney) && StringUtils.isBlank(evmoney)){
				comparator = JSONUtil.getJSONObject();
				comparator.put("vmoney", svmoney + ",5");
			}else if(StringUtils.isBlank(svmoney) && StringUtils.isNotBlank(evmoney)){
				comparator = JSONUtil.getJSONObject();
				comparator.put("vmoney",evmoney + ",2");
			}
			
			if(gamecoins_process != null){
				hp.createProcess(items, itemsName, gamecoins_process, task.getId(),comparator,false,0);
			}
			else{
				hp.createProcess(items, itemsName, p1, task.getId(),comparator,false,0);
			}
		}
		
		// 把该任务活加到处理队列中
		processQueue.offer(task.getId());
		
		return new ResultState(ResultState.SUCCESS,"添加任务成功");
	}
	
	
	@RequestMapping(value="uploadSyncUser")
	@ResponseBody
	public ResultState getUserSource(@RequestParam MultipartFile file,String syncUrl) throws IllegalStateException, IOException{
		if(!file.isEmpty()){
			String ext = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
			String fileName = UUID.randomUUID()+ext;
			
			File toFile = new File(Constants.IMAGES_DIR,fileName);
			
			file.transferTo(toFile);
			
			if (toFile.exists() && toFile.canRead()) {
				
				List<String> datas = new ArrayList<String>();
				int line = 0;
				
				InputStreamReader read = null;
				BufferedReader buffer = null;
				try {
					read = new InputStreamReader(new FileInputStream(toFile),"UTF-8");
					buffer = new BufferedReader(read);
					String lineTxt = null;
					while ((lineTxt = buffer.readLine()) != null) {
						datas.add(lineTxt);
						line++;
					}
				} catch (Exception e) {
					errorLogger.error(e.getMessage());
				} finally {
					if(buffer!=null){
						try {
							buffer.close();
						} catch (IOException e) {
							errorLogger.error(e.getMessage());
						}
					}
					if(read!=null){
						try {
							read.close();
						} catch (IOException e) {
							errorLogger.error(e.getMessage());
						}
					}
				}
				
				if(datas!=null && datas.size()>0){
					final List<String> fdatas = datas;
					final String furl = syncUrl;
					
					for(int i=fdatas.size()-1;i>=0;i--){
						final int index = i;
						new Thread(){
							@SuppressWarnings("unused")
							@Override
							public void run() {
								String data = fdatas.get(index);
								taskLogger.info(furl+data+",request index:"+index+",size="+fdatas.size());
								String responseData = HttpUtil.sendGet(furl+data);
							}
						}.start();
						
						if(i%100==0){
							taskLogger.info("休息一下3s");
							try {
								Thread.sleep(3000);
							} catch (InterruptedException e) {
								errorLogger.error(e.getMessage());
							}
						}
					}
				}
				
				ResultState success = new ResultState(ResultState.SUCCESS);
				success.setMsg("更新成功");
				JSONObject json = JSONUtil.getJSONObject();
				json.put("line", line);
				
				success.setOther(json);
				return success;
			}
		}
			
		return new ResultState(ResultState.FAILURE,"上传同步失败");
	}
}
