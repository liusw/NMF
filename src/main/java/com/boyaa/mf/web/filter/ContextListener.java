package com.boyaa.mf.web.filter;

import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServlet;

/**
 * 
 * @类名 : ContextListener.java
 * @作者 : MarsHuang
 * @部门 : 德州扑克业务中心-开拓者工作室-公共组
 * @公司 : 博雅互动
 * @日期 : 2014-8-1  下午1:55:22
 * @描述 : 现在已经不使用定时器
 */
public class ContextListener extends HttpServlet implements ServletContextListener {
	private static final long serialVersionUID = 1L;

	public ContextListener() {
	}

	private Timer timer = null;

	public void contextInitialized(ServletContextEvent event) {
		/*ServletContext servletContext = event.getServletContext();
		timer = new Timer(true);
		servletContext.log("定时器已启动");
		timer.schedule(new FileTask(event.getServletContext()), 10*1000, 5 * 60 * 1000);//10 * 60 * 1000
		servletContext.log("ProcessTask已经添加任务调度表");
		*/
		
	}

	public void contextDestroyed(ServletContextEvent event) {
		if(timer != null){
			timer.cancel();
		}
		event.getServletContext().log("定时器销毁");
	}

}