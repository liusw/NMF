package com.boyaa.mf.service.task;

import com.boyaa.mf.entity.task.ProcessInfo;
import com.boyaa.mf.entity.task.ProcessStatusEnum;
import com.boyaa.mf.service.common.HiveJdbcService;
import com.boyaa.servlet.ResultState;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Map;

/**
 * @描述 : 创建hive表操作的业务处理类
 * @作者 : DarcyZeng
 * @日期 : 2015-01-08 
 */
@SuppressWarnings("all")
@Service
public class CreateHiveProcessService extends ProcessService{
	static Logger taskLogger = Logger.getLogger("taskLogger");
	static Logger errorLogger = Logger.getLogger("errorLogger");
	static Logger fatalLogger = Logger.getLogger("fatalLogger");

	@Autowired
	private HiveJdbcService hiveService;

	@Override
	public ResultState execute(ProcessInfo processInfo) {
		Map<Object,Object> obj = null;
		try {
			//如果多条语句,分成多次执行
			String operation = processInfo.getOperation();
			if(operation.contains(";")){
				String[] operationArr = operation.split(";");
				for(int i=0;i<operationArr.length;i++){
					hiveService.execute(operationArr[i]);
					taskLogger.info("execute:"+processInfo.getId()+","+operationArr[i]);
				}
			}
		
			ProcessInfo updateProcess = new ProcessInfo();
			updateProcess.setStatus(ProcessStatusEnum.EXECUTION_END.getValue());
			updateProcess.setEndTime("now");
			updateProcess.setLogInfo("执行完成");
			updateProcess.setPath(null);
			updateProcess.setId(processInfo.getId());
			update(updateProcess);
			
			return new ResultState(ResultState.SUCCESS,"执行成功",null);
		} catch (SQLException e) {
			errorLogger.error(e.getMessage());
			
			ProcessInfo updateProcess = new ProcessInfo();
			updateProcess.setStatus(ProcessStatusEnum.EXECUTION_ERROR.getValue());
			updateProcess.setEndTime("now");
			updateProcess.setLogInfo("hive查询出错:"+e.getMessage());
			updateProcess.setId(processInfo.getId());
			update(updateProcess);
			
			return new ResultState(ResultState.FAILURE,"hive查询出错:"+e.getMessage(),null);
		}finally{
			if(hiveService!=null) {
				hiveService.close();
			}
		}
	}
}