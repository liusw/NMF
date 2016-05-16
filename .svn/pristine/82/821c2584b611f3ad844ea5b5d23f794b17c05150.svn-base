package com.boyaa.mf.entity.task;

/**
 * 流程执行类型的枚举
 * 
 * @类名 : ProcessTypeEnum.java
 * @作者 : MarsHuang
 * @部门 : 德州扑克业务中心-开拓者工作室-公共组
 * @公司 : 博雅互动
 * @日期 : 2014-7-22 下午3:05:19
 * @描述 :
 */
public enum ProcessTypeEnum {
	// 直接查询hive
	HIVE_QUERY(1),
	// 使用hbase多线程导出工具类查询
	HBASE_MULTI_QUERY(2),
	// 上传文件,此流程不作执行
	FILE_UPLOAD(3),
	//扫描hbase
	HBASE_SCAN(5),
	//自定义执行，在operation中要保存Class|method;
	CUSTOM_EXEC(6),
	//mysql导入和导出处理，在operation中保存：import(导入处理)|tableName(表名) or export(导出处理)|sql(语句)
	MYSQL_EXEC(7),
	//创建hive表
	CREATE_HIVE(8),
	//实时日志查询
	REAL_TIME_LOG(9),
	//把数据插入到hbase表
	HBASE_PUT(10);
	
	private final int value;

	ProcessTypeEnum(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}