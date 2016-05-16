package com.boyaa.mf.web.filter;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.boyaa.base.hadoop.FileSystemSingleton;
import com.boyaa.base.hbase.HConnectionSingleton;

public class TomcatListener implements ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		HConnectionSingleton.close();
		FileSystemSingleton.close();
		System.out.println("tomcat关闭了..........");
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		System.out.println("tomcat启动了..............");
	}

}
