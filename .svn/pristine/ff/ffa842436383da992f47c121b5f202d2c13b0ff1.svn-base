package com.boyaa.mf.mapper.privilege;

import java.util.List;

import com.boyaa.mf.annotation.MyBatisRepository;
import com.boyaa.mf.entity.privilege.Role;
import com.boyaa.mf.entity.privilege.UserRole;
import com.boyaa.mf.mapper.BaseMapper;

@MyBatisRepository
public interface UserRoleMapper extends BaseMapper<UserRole,Integer>{
	public List<Role> findRolesByUserId(int userId);
	public int insertUserRole(UserRole userRole);
	public int deleteUserRole(UserRole userRole);
}
