package com.boyaa.mf.constants;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.boyaa.base.utils.IPUtil;
import com.boyaa.base.utils.JSONUtil;
import com.boyaa.servlet.ContextServlet;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Constants extends PropertiesResource{
	static Logger logger = Logger.getLogger(Constants.class);
	static Logger errorLogger = Logger.getLogger("errorLogger");
	static Logger fatalLogger = Logger.getLogger("fatalLogger");
	
	//默认编码
	public static final String DEFAULT_ENCODING="UTF-8";
	//跨域请求的默认回调名称
	public static final String DEFAULT_CALLBACK_NAME="callback";
	//默认请求表名参数
	public static final String DEFAULT_TABLENAME_PARAM="t";
	//请求的页号参数
	public static final String DEFAULT_PAGE_PARAM="page";
	//每页显示的记录数参数
	public static final String DEFAULT_PAGESIZE_PARAM="pagesize";
	
	/**
	 * 如果流程要依懒上一步创建表的临时表名
	 */
	public static final String tempTableName = "#tmpTable#";
	//plat,sid的远程调用接口
	public static final String GET_PLAT_URL="http://cms.oa.com/api/rest.php?cmd=mfplatInfo&retkey=plat,pname&s_group=plat,pname";
	public static final String GET_SID_URL="http://cms.oa.com/api/rest.php?cmd=mfInfo&retkey=sid,vname,fbpid,plat&plat=#plat#";
	//权限系统的token ID
	public static final String AUTH_TOKEN_ID="by_auth_token_20";
	public static final int AUTH_ID=20;
	public static final String downAllFile ="/log/task?dir=list&pageMsg=download";
	
	//文件保存地址
	public static final String FILE_DIR = ContextServlet.tomcat_webapps_home + File.separator + "hiveExport" + File.separator;
	//图片保存地址
	public static final String IMAGES_DIR = ContextServlet.tomcat_webapps_home + File.separator + "uploadImages" + File.separator;
	//文件在hdfs保存的目录
	public static final String TASK_HDFS_DIR = "taskfile";
	
	//反刷分文件目录
	public static final String BRUSH_DIR = FILE_DIR + "brush" + File.separator;
	
	public static final String USER_LOGIN_SESSION_NAME="loginUserInfo";
	public static final String NOTICE_ARTICLE_NAME="noticeArticle";
	
	public static String ip;
	static{
		File fileDir = new File(FILE_DIR);
		if(!fileDir.exists()){
			fileDir.mkdir();
		}
		File brushDir = new File(BRUSH_DIR);
		if(!brushDir.exists()){
			brushDir.mkdir();
		}
		
		try {
			ip=IPUtil.getRealIp();
		} catch (SocketException e) {
			errorLogger.error(e.getMessage());
		}
	}
	
	//public static String HIVE_DB_URL,HIVE_DB_USER,HIVE_DB_PWD,HIVE_SERVER_URL,HIVE_JDBC_DRIVER="org.apache.hive.jdbc.HiveDriver";
	//public static String HIVE_DB_URL,HIVE_DB_USER,HIVE_DB_PWD,HIVE_SERVER_URL,HIVE_JDBC_DRIVER="org.apache.hadoop.hive.jdbc.HiveDriver";
	public static String HIVE_SERVER_URL,HIVE_JDBC_DRIVER,HIVE_DB_URL,HIVE_DB_USER,HIVE_DB_PWD;
	static{
//		HIVE_SERVER_URL = com.boyaa.base.utils.Constants.hive_url;
//		HIVE_JDBC_DRIVER = com.boyaa.base.utils.Constants.hive_driver;
//		HIVE_DB_URL = com.boyaa.base.utils.Constants.hive_meta_url;
//		HIVE_DB_USER =com.boyaa.base.utils.Constants.hive_meta_username;
//		HIVE_DB_PWD =com.boyaa.base.utils.Constants.hive_meta_password;
		
		try {
			InputStream in = Constants.class.getClassLoader().getResourceAsStream("jdbc.properties");
			Properties properties = new Properties();
			properties.load(in);
			String env = com.boyaa.base.utils.Constants.env;
			
			HIVE_JDBC_DRIVER = properties.getProperty("hive.driver");
			HIVE_DB_URL = properties.getProperty("hive.url");
			HIVE_DB_USER = properties.getProperty("hive.username");
			HIVE_DB_PWD = properties.getProperty("hive.password");
			if(env!=null && env.equals("server")){
				HIVE_SERVER_URL = "jdbc:hive2://" + ip + ":10000/default";
			}else{
				HIVE_SERVER_URL = properties.getProperty("hiveserver.url");
			}
		} catch (IOException e) {
			errorLogger.error(e.getMessage());
		}
	}
	
	// meta文件对应的配置
	public static String trashMetaTips="[已废弃]";
	public static Map<String, String> trash_meta_values_mapping = new HashMap<String, String>();
	static{
		initTrashConfig();
	}
	public static void initTrashConfig() {
		BufferedReader reader = null;
		JSONArray plats = null;
		String path = Constants.TRASH_META_MONITOR_PATH;
		String[] names = Constants.TRASH_META_MONITOR_FILES.split(com.boyaa.base.utils.Constants.COMMA_SEPERATE);

		for (String name : names) {
			File file = new File(path, name);
			plats = JSONUtil.getJSONArray();
			if (file.exists()) {
				try {
					reader = new BufferedReader(new FileReader(file));
					String line = null;
					if ((line = reader.readLine()) != null) {
						JSONObject json = JSONUtil.parseObject(line);
						JSONArray arr = json.getJSONArray("loop");
						int len = arr.size();
						for (int i = 0; i < len; i++) {
							JSONObject obj = arr.getJSONObject(i);
							obj.put("title", trashMetaTips+obj.getString("title"));
							obj.put("vname", trashMetaTips+obj.getString("vname"));
							plats.add(obj);
						}
					}
					trash_meta_values_mapping.put(name, plats.toString());
				} catch (FileNotFoundException e) {
					logger.error(e.getMessage());
				} catch (IOException e) {
					logger.error(e.getMessage());
				} catch (JSONException e) {
					logger.error(e.getMessage());
				} finally {
					try {
						if (reader != null)
							reader.close();
					} catch (IOException e) {
						logger.error(e.getMessage());
					}
				}
			}
		}
	}

	
	
	public static final String SIGN_KEY="LcyFGX3u";
	
	public static final int BRUSH_TEMPLATE_VERSION = 3;//模板版本号
	public static final int BRUSH_TYPE_SINGUP = 1;//注册类型
	public static final int BRUSH_TYPE_HUOYUE = 2;//活跃类型
	public static final String BRUSH_LAST_TIME_FORMAT = "yyyyMMdd HH:mm:ss";//上一次执行时间格式
	public static final String BRUSH_RUN_HOUR_FORMAT = "HHmm";
	
	public static final int STATUS_EFFACTIVE = 0;//有效状态
	public static final int STATUS_UNEFFACTIVE = 1;//无效状态
	public static final String START_TIME = "#startTime#";//开始时间
	public static final String END_TIME = "#endTime#";//结束时间
	public static final String YESTERDAY = "#yesterday#";//昨天
	public static final String THREE_DAY = "#threeDay#";//3天前
	public static final String SEVEN_DAY = "#sevenDay#";//7天前
	public static final String FIFTY_DAY = "#fiftyDay#";//15天前
	public static final String THIRTY_DAY = "#thirtyDay#";//30天前
	public static final String LAST_WEEK_0 = "#lastWeek0#";//上周的第一天
	public static final String LAST_WEEK_1 = "#lastWeek1#";//上周的最后一天
	public static final String TWO_WEEK_0 = "#twoWeek0#";//2周前那周的第一天
	public static final String TWO_WEEK_1 = "#twoWeek1#";//2周前那周的最后一天
	public static final String THREE_WEEK_0 = "#threeWeek0#";//3周前那周的第一天
	public static final String THREE_WEEK_1 = "#threeWeek1#";//3周前那周的最后一天
	public static final String FOUR_WEEK_0 = "#fourWeek0#";//4周前那周的第一天
	public static final String FOUR_WEEK_1 = "#fourWeek1#";//4周前那周的最后一天
	public static final String LAST_MONTH_0 = "#lastMonth0#";//上个月的第一天
	public static final String LAST_MONTH_1 = "#lastMonth1#";//上个月的最后一天
	public static final String THREE_MONTH_0 = "#threeMonth0#";//3个月前那个月的第一天
	public static final String THREE_MONTH_1 = "#threeMonth1#";//3个月前那个月的最后一天
	public static final String SIX_MONTH_0 = "#sixMonth0#";//6个月前那个月的第一天
	public static final String SIX_MONTH_1 = "#sixMonth1#";//6个月前那个月的最后一天
	public static final String TWELVE_MONTH_0 = "#twelveMonth0#";//12个月前那个月的第一天
	public static final String TWELVE_MONTH_1 = "#twelveMonth1#";//12个月前那个月的最后一天
	public static final String RUN_HOUR_TIME = "0500";//默认执行时间
	
	public static int TASK_TYPE_DEFAULT = 0;//默认执行到任务类型
	public static int TASK_TYPE_AUTORUN = 1;//自动执行到任务类型
	public static int TASK_TYPE_DELETE = 2;//删除状态
	
	public static int HIVE_META_PARTITION_TYPE = 1;//hive元数据分区类型
	public static int HIVE_META_COLUMN_TYPE = 0;//hive元数据字段类型
	
	public static String MYSQL_IMPORT_TYPE = "import";//mysql导入类型
	public static String MYSQL_EXPORT_TYPE = "export";//mysql草出类型
	public static String MYSQL_UPDATE_TYPE = "modify";//mysql更新类型
	public static String MYSQL_COMMON_IMPORT_TYPE = "common";//mysql通用导入类型,直接执行,没有删除
	public static String MYSQL_TRUNCATE_TYPE = "truncate";//mysql通用导入类型,直接执行,没有删除
	public static String MYSQL_SAVEORUPDATE_TYPE = "saveOrUpdate";//mysql通用导入类,如果有主键冲突,update 字段

	public static String defaultNamespace="commonSQL.";
	
	/**
	 * 后端返回给前端执行结果：执行失败
	 */
	public static int RESULT_FAILURE = 0;
	
	/**
	 * 后端返回给前端执行结果：执行成功
	 */
	public static int RESULT_SUCCESS = 1;
	
	//hive分区的几个备注值
	public static String HIVE_PARTITION_COMMENT_PLAT="平台号(比如泰语为604)";
	public static String HIVE_PARTITION_COMMENT_SVID="平台号(比如泰语为604)";
	public static String HIVE_PARTITION_COMMENT_TM="日期(格式为yyyyMMdd)";
}
