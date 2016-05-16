package com.boyaa.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.boyaa.dao.mybatis.MyBatisUtil;
import com.boyaa.dao.mybatis.SQLAdapter;
import com.boyaa.dao.mybatis.SqlHelper;


public class CommonDao{
	static Logger errorLogger = Logger.getLogger("errorLogger");
	
	/**
	 * 根据SQL获取List<Map<String,Object>>
	 * @param sql
	 * @return
	 */
	public List<Map<String,Object>> getMapBySql(String sql) {
		List<Map<String,Object>> list = null;
		SqlSession session = null;
		try{
			session = MyBatisUtil.createSession();
			list = session.selectList("common.getMapBySql",new SQLAdapter(sql));
		}catch(Exception e){
			errorLogger.error(e.getMessage());
		} finally {
			MyBatisUtil.closeSession(session);
		}
		return list;
	}
	
	/**
	 * 根据sql获取
	 * @param params
	 * @return
	 */
	public int getCountBySql(String sql) {
		SqlSession session = null;
		try{
			session = MyBatisUtil.createSession();
			return (Integer)session.selectOne("common.getCountBySql",new SQLAdapter(sql));
		}catch(Exception e){
			errorLogger.error(e.getMessage());
		} finally {
			MyBatisUtil.closeSession(session);
		}
		return 0;
	}
	
	public String getSqlByNamespace(String namespace,Object params){
		SqlSession session = null;
		try{
			session = MyBatisUtil.createSession();
			
			return SqlHelper.getNamespaceSql(session,namespace, params);
		}catch(Exception e){
			errorLogger.error(e.getMessage());
		} finally {
			MyBatisUtil.closeSession(session);
		}
		return null;
	}
	
	public String getSqlByNamespace(String namespace){
		return getSqlByNamespace(namespace,null);
	}
	
	/**
	 * 根据SQL获取Map<String,Object>
	 * @param sql
	 * @return
	 */
	public Map<String,Object> getMapByNameSpace(String namespace,Map<String,Object> params) {
		Map<String,Object> map = null;
		SqlSession session = null;
		try{
			session = MyBatisUtil.createSession();
			map = session.selectOne(namespace,params);
		}catch(Exception e){
			errorLogger.error(e.getMessage());
		} finally {
			MyBatisUtil.closeSession(session);
		}
		return map;
	}
	
	/**
	 * 根据SQL获取List<Map<String,Object>>
	 * @param sql
	 * @return
	 */
	public List<Map<String,Object>> getListByNameSpace(String namespace,Map<String,Object> params) {
		List<Map<String,Object>> list = null;
		SqlSession session = null;
		try{
			session = MyBatisUtil.createSession();
			list = session.selectList(namespace,params);
		}catch(Exception e){
			errorLogger.error(e.getMessage());
		} finally {
			MyBatisUtil.closeSession(session);
		}
		return list;
	}
	
	/**
	 * 根据sql获取
	 * @param params
	 * @return
	 */
	public int getCountByNamespace(String namespace,Map<String,Object> params) {
		SqlSession session = null;
		try{
			session = MyBatisUtil.createSession();
			return (Integer)session.selectOne(namespace,params);
		}catch(Exception e){
			errorLogger.error(e.getMessage());
		} finally {
			MyBatisUtil.closeSession(session);
		}
		return 0;
	}
	
	public int insertByNamespace(String namespace,Map<String,Object> params) {
		int result=0;
		SqlSession session = null;
		try {
			session = MyBatisUtil.createSession();
			result = session.insert(namespace,params);
			session.commit();
		} catch (Exception e) {
			errorLogger.error(e.getMessage());
			session.rollback();
			throw new RuntimeException(e);
		} finally {
			MyBatisUtil.closeSession(session);
		}
		return result;
	}

	public int updateByNamespace(String namespace,Map<String,Object> params) {
		int result=0;
		SqlSession session = null;
		try {
			session = MyBatisUtil.createSession();
			result = session.update(namespace,params);
			session.commit();
		} catch (Exception e) {
			errorLogger.error(e.getMessage());
			session.rollback();
			throw new RuntimeException(e);
		} finally {
			MyBatisUtil.closeSession(session);
		}
		return result;
	}

	public int deleteByNamespace(String namespace, Map<String, Object> params) {
		int result=0;
		SqlSession session = null;
		try {
			session = MyBatisUtil.createSession();
			result = session.delete(namespace,params);
			session.commit();
		} catch (Exception e) {
			errorLogger.error(e.getMessage());
			session.rollback();
			throw new RuntimeException(e);
		} finally {
			MyBatisUtil.closeSession(session);
		}
		return result;
	}
}
