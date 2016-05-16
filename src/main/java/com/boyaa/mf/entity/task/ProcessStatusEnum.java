package com.boyaa.mf.entity.task;

/**
 * @类名 : ProcessTypeEnum.java
 * @作者 : MarsHuang
 * @部门 : 德州扑克业务中心-开拓者工作室-公共组
 * @公司 : 博雅互动
 * @日期 : 2014-7-22 下午3:05:19
 * @描述 : 流程状态枚举
 */
public enum ProcessStatusEnum {
	// 未执行
	NOT_EXECUTED(0),
	// 执行中
	EXECUTING(1),
	// 执行出错
	EXECUTION_ERROR(2),
	// 执行完成
	EXECUTION_END(3),
	//添加任务出错
	INSERT_ERROR(4),
	//未审批[未发送审批申请]
	NEED_AUDIT(5),
	//直接通过审核流程
	PASS(6),
	//强制停止
	FORCED_STOP(7),
	//已发送审批申请
	SEND_AUDIT(8);

	private final int value;

	ProcessStatusEnum(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}