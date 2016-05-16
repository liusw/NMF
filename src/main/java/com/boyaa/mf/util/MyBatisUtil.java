package com.boyaa.mf.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MyBatisUtil {
	private static SqlSessionFactory factory;
	static {
		try {
			InputStream is = Resources.getResourceAsStream("mybatis.xml");
			factory = new SqlSessionFactoryBuilder().build(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static SqlSession createSession() {
		return factory.openSession();
	}
	
	public static void closeSession(SqlSession session) {
		if(session!=null) session.close();
	}
	
	/**
	 * 获取某个mapper文件的resultMap
	 * @param resultMapId
	 * @return
	 */
	public static String getColumnPropertyMap(HttpServletRequest request, String resultMapId){
		String orderIndex = request.getParameter("iSortCol_0");//排序的列索引
		if(StringUtils.isBlank(orderIndex)){
			return null;
		}
		String orderProperty = request.getParameter("mDataProp_"+orderIndex);//排序的列索引
		if(StringUtils.isBlank(orderProperty)){
			return null;
		}
		String order = request.getParameter("sSortDir_0");//排序的顺序
		if(StringUtils.isBlank(order)){
			order = "asc";
		}
		ResultMap resultMap = factory.getConfiguration().getResultMap(resultMapId);
		if(resultMap!=null){
			List<ResultMapping> mappings = resultMap.getResultMappings();
			for (ResultMapping mp : mappings) {
				if(mp.getProperty().equals(orderProperty)){
					return "order by "+mp.getColumn()+" "+order;
				}
	        }
		}
		return null;
	}
}
