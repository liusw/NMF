package com.boyaa.mf.mapper.privilege;

import com.boyaa.mf.annotation.MyBatisRepository;
import com.boyaa.mf.entity.privilege.User;
import com.boyaa.mf.mapper.BaseMapper;

@MyBatisRepository
public interface UserMapper extends BaseMapper<User,Integer>{

}