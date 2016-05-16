package com.boyaa.mf.mapper.privilege;

import java.util.List;

import com.boyaa.mf.annotation.MyBatisRepository;
import com.boyaa.mf.entity.privilege.Resources;
import com.boyaa.mf.entity.privilege.RoleResource;
import com.boyaa.mf.mapper.BaseMapper;



@MyBatisRepository
public interface RoleResourceMapper extends BaseMapper<RoleResource,Integer>{
	List<Resources> findResourcesByRoleId(int roleId);
	int insertRoleResource(RoleResource roleResource);
	int deleteRoleResource(RoleResource roleResource);
	int isSet(RoleResource roleResource);
}
