package com.boyaa.mf.constants;

import java.util.EnumMap;

/**
 * 数据字典（暂时由程序处理，不存数据库）
 * 
 * @类名 : BaseData.java
 * @作者 : MarsHuang
 * @部门 : 德州扑克业务中心-开拓者工作室-公共组
 * @公司 : 博雅互动
 * @日期 : 2015-1-15 上午11:46:19
 */
public class BaseData {
	private static BaseData instance;
	public static EnumMap<OperationType, String> mapData = new EnumMap<OperationType, String>(OperationType.class);

	private BaseData() {
	}

	public static BaseData getInstance() {
		if (instance == null) {
			init();
			instance = new BaseData();
		}
		return instance;
	}

	public static void init() {
		mapData.put(OperationType.ADD_TASK, "添加任务");
	}

	public enum OperationType {
		ADD_TASK(10011), UPDATE_TASK(10012), STOP_TASK(100013), ADD_PROCESS(10021), UPDATE_PROCESS(10022),
		SEND_AUDIT(20001),PASS_AUDIT(20002);
		private final int value;

		private OperationType(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}
	public enum TaskAuditType {
		AUDITING(new Byte("0")),PASS(new Byte("1")),NOT_PASS(new Byte("2"));

		private final Byte value;
		private TaskAuditType(Byte value){
			this.value = value;
		}
		public Byte getValue(){
			return value;
		}
	}

}
