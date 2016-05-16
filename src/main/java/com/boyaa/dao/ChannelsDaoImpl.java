package com.boyaa.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.boyaa.dao.mybatis.MyBatisUtil;
import com.boyaa.dao.mybatis.MySqlDaoImpl;
import com.boyaa.entity.Channels;

public class ChannelsDaoImpl extends MySqlDaoImpl<Channels> implements ChannelsDao {

	@Override
	public List<Integer> findPlats(Map<String, Object> map) {
		SqlSession session = null;
		try{
			session = MyBatisUtil.createSession();
			return session.selectList("channels.findPlats",map);
		} finally {
			MyBatisUtil.closeSession(session);
		}
	}

}
