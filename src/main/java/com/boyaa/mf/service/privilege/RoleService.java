package com.boyaa.mf.service.privilege;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boyaa.mf.entity.privilege.Role;
import com.boyaa.mf.entity.privilege.UserRole;
import com.boyaa.mf.mapper.privilege.UserRoleMapper;
import com.boyaa.mf.service.AbstractService;

@Service
public class RoleService extends AbstractService<Role, Integer> {
	@Autowired
	private UserRoleMapper userRoleMapper;
	
	public List<Role> findRolesByUserId(int userId){
		return userRoleMapper.findRolesByUserId(userId);
	}
	
	public void insertUserRole(UserRole userRole) {
		userRoleMapper.insertUserRole(userRole);
	}

	public void deleteUserRole(UserRole userRole) {
		userRoleMapper.deleteUserRole(userRole);
	}
}
