package com.boyaa.servlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.boyaa.base.listener.HbrowkeyFileListener;
import com.boyaa.base.listener.HbtablesFileListener;
import com.boyaa.base.listener.LocalFileListener;
import com.boyaa.base.listener.MetaFileListener;
import com.boyaa.base.listener.SQLFileListener;
import com.boyaa.base.listener.SplitTableFileListener;
import com.boyaa.base.utils.AppContext;
import com.boyaa.base.utils.Constants;
import com.boyaa.mf.entity.task.ProcessInfo;
import com.boyaa.mf.service.task.StopJobQueue;
import com.boyaa.mf.web.filter.AutorunTask;

public class ContextServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(ContextServlet.class);
	static Logger errorLogger = Logger.getLogger("errorLogger");
	static Logger fatalLogger = Logger.getLogger("fatalLogger");
	public static Map<Integer,Map<ProcessInfo,Object>> runningTaskInstance;
	public static String tomcat_webapps_home = System.getProperty("catalina.home") + File.separator + "webapps";
	private static Timer autorunTimer = null;
	
	public void init(ServletConfig config) throws ServletException {
		AppContext.init();
		// start the config file listener
		InputStream is = null;
		try {
			logger.info("start file listener...");
			new LocalFileListener().start();
			new MetaFileListener().start();
			new SplitTableFileListener().start();
			new HbrowkeyFileListener().start();
			new SQLFileListener().start();
			new HbtablesFileListener().start();
			logger.info("start file listener success.");
			runningTaskInstance = new HashMap<Integer, Map<ProcessInfo,Object>>();
			
//			//初始化流程队列
//			ProcessQueue.getInstance();
			
			//初始化停止Job队列
			StopJobQueue.getInstance();
			
			//初始化自动执行流程队列
//			AutorunQueue.getInstance();
			
			if(com.boyaa.mf.constants.Constants.AUTO_RUNNING_SERVER!=null && com.boyaa.mf.constants.Constants.AUTO_RUNNING_SERVER.equals(Constants.ip)){
				autorunTimer = new Timer(true);
				autorunTimer.schedule(new AutorunTask(), 3*60*1000, 2 * 60 * 1000);
				logger.info("自动执行任务器已启动!");
			}
//			//公告
//			ArticleService articleService = new ArticleService();
//			List<Article> articles = articleService.findNoticeArticleList();
//			config.getServletContext().setAttribute(com.boyaa.constants.Constants.NOTICE_ARTICLE_NAME, articles);
			
			if(StringUtils.isNotEmpty(com.boyaa.mf.constants.Constants.WEBAPPS_HOME)){
				tomcat_webapps_home=com.boyaa.mf.constants.Constants.WEBAPPS_HOME;
			}
		} catch (Exception e) {
			fatalLogger.error("start file listener error: " + e.getMessage());
		} finally {
			try {
			if(is != null)
				is.close();
			} catch(IOException e) {
				errorLogger.error(e.getMessage());
			}
		}
	}
}
