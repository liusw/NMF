package com.boyaa.dao.mybatis;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.boyaa.dao.BaseDao;
import com.boyaa.mf.util.GenericsUtils;
@SuppressWarnings("all")
public class MySqlDaoImpl<T> implements BaseDao<T>{
	static Logger logger = Logger.getLogger(MySqlDaoImpl.class);
	static Logger errorLogger = Logger.getLogger("errorLogger");
	static Logger fatalLogger = Logger.getLogger("fatalLogger");
	
	@SuppressWarnings("unchecked")
	protected Class<T> entityClass = GenericsUtils.getSuperClassGenricType(this.getClass());
	protected String mapperId = StringUtils.uncapitalize(this.entityClass.getSimpleName());
	
	@Override
	public int insert(T t){
		int result=0;
		SqlSession session = null;
		try {
			session = MyBatisUtil.createSession();
			result = session.insert(mapperId+".insert",t);
			session.commit();
		} catch (Exception e) {
			errorLogger.error(e.getMessage());
			session.rollback();
			throw new RuntimeException(e.getMessage());
		} finally {
			MyBatisUtil.closeSession(session);
		}
		return result;
	}
	
	@Override
	public int update(T t) {
		int result=0;
		SqlSession session = null;
		try {
			session = MyBatisUtil.createSession();
			result = session.update(mapperId+".update", t);
			session.commit();
		} catch (Exception e) {
			errorLogger.error(e.getMessage());
			session.rollback();
			throw new RuntimeException(e.getMessage());
		} finally {
			MyBatisUtil.closeSession(session);
		}
		return result;
	}

	@Override
	public int delete(Serializable id) {
		int count=0;
		SqlSession session = null;
		try {
			session = MyBatisUtil.createSession();
			count = session.delete(mapperId+".delete", id);
			session.commit();
		} catch (Exception e) {
			errorLogger.error(e.getMessage());
			session.rollback();
			throw new RuntimeException(e.getMessage());
		} finally {
			MyBatisUtil.closeSession(session);
		}
		return count;
	}

	@Override
	public T findById(Serializable entityId) {
		if(entityId==null) 
			throw new RuntimeException(this.entityClass.getName()+ ":传入的实体id不能为空");
		
		SqlSession session = null;
		T t = null;
		try {
			session = MyBatisUtil.createSession();
			t = (T)session.selectOne(mapperId+".findById",entityId);
		}catch(Exception e){
			errorLogger.error(e.getMessage());
			throw new RuntimeException(e.getMessage());
		} finally {
			MyBatisUtil.closeSession(session);
		}
		return t;
	}

	@Override
	public List<T> findScrollDataList(Map<Object, Object> params) {
		List<T> list = null;
		SqlSession session = null;
		try{
			session = MyBatisUtil.createSession();
			list = session.selectList(mapperId+".findScrollDataList",params);
		} catch (Exception e) {
			errorLogger.error(e.getMessage());
			throw new RuntimeException(e.getMessage());
		} finally {
			MyBatisUtil.closeSession(session);
		}
		return list;
	}

	@Override
	public int findScrollDataCount(Map<Object, Object> params){
		SqlSession session = null;
		try{
			session = MyBatisUtil.createSession();
			return (Integer)session.selectOne(mapperId+".findScrollDataCount",params);
		} catch (Exception e) {
			errorLogger.error(e.getMessage());
			throw new RuntimeException(e.getMessage());
		} finally {
			MyBatisUtil.closeSession(session);
		}
	}
}