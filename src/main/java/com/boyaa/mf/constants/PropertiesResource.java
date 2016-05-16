package com.boyaa.mf.constants;
import java.util.ResourceBundle;

/**
 * 这个类直接读取properties配置文件
 * @类名 : PropertiesResource.java
 * @作者 : MarsHuang
 * @部门 : 德州扑克业务中心-开拓者工作室-公共组
 * @公司 : 博雅互动
 * @日期 : 2014-12-9  上午10:17:42
 */
public class PropertiesResource {
	//审批人的邮件
	public static final String AUDIT_EMAIL;
	public static final String DOMAIN;
	public static final String UPLOAD_IMAGES_DOMAIN;
	public static final String UPLOAD_IMAGES_PREFIX;
	
	public static final String AUTH_MAPPING;
	public static final String FILTER_COLUMN;
	public static final String AUDIT_COLUMN;//需要申批的字段
	
	public static final String ACTION_SCAN_PACKAGE;
	
	public static final String AVRO_GAMBLING_SERVER;//avro的牌局服务器
	
	public static final String AVRO_OTHER_SERVER;//avro其他原始日志的服务器地址
	
	public static final String SMS_PHONE;//接受短信的手机号
	
	public static final String SMS_ADDR;//发送短信的接口
	
	public static final String MAP_REDUCE_ADDR;//Map/Reduce Administration addr
	
	public static final String BAN_COUNT_EMAIL;
	
	public static final boolean COMPRESS_TASK_FILE;
	
	public static final String HIVE_SERVER_USERNAME;
	public static final String HIVE_SERVER_PASSWORD;
	public static final String AUTO_RUNNING_SERVER;
	public static final String QUERY_MULTITHREAD_COUNT;
	public static final String WEBAPPS_HOME;
	public static final String TRASH_META_MONITOR_PATH;
	public static final String TRASH_META_MONITOR_FILES;
	
	static {
		ResourceBundle rb = ResourceBundle.getBundle("mfconfig");
		
		AUDIT_EMAIL = rb.getString("audit_email");
		BAN_COUNT_EMAIL = rb.getString("ban_count_email");
		DOMAIN = rb.getString("domain");
		UPLOAD_IMAGES_DOMAIN = rb.getString("upload_images_domain");
		UPLOAD_IMAGES_PREFIX = rb.getString("upload_images_prefix");
		AUTH_MAPPING = rb.getString("auth_mapping");
		FILTER_COLUMN = rb.getString("filter_column");
		AUDIT_COLUMN = rb.getString("audit_column");
		ACTION_SCAN_PACKAGE = rb.getString("actoin_scan_package");
		AVRO_GAMBLING_SERVER = rb.getString("avro_gambling_server");
		AVRO_OTHER_SERVER = rb.getString("avro_other_server");
		SMS_PHONE = rb.getString("sms_phone");
		SMS_ADDR = rb.getString("sms_addr");
		
		COMPRESS_TASK_FILE = Boolean.valueOf(rb.getString("compress_task_file"));
		
		HIVE_SERVER_USERNAME=rb.getString("hive_server_username");
		HIVE_SERVER_PASSWORD=rb.getString("hive_server_password");
		
		String env = com.boyaa.base.utils.Constants.env;
		if(env!=null && env.equals("server")){
			MAP_REDUCE_ADDR = rb.getString("map_reduce_production_addr");
		}else{
			MAP_REDUCE_ADDR = rb.getString("map_reduce_development_addr");
		}
		
		AUTO_RUNNING_SERVER=rb.getString("auto_running_server");
		QUERY_MULTITHREAD_COUNT=rb.getString("query_multithread_count");
		WEBAPPS_HOME=rb.getString("tomcat_webapps_home");
		
		TRASH_META_MONITOR_PATH = rb.getString("trash_meta_monitor_path");
		TRASH_META_MONITOR_FILES = rb.getString("trash_meta_monitor_files");
	}
}