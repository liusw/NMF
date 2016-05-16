package com.boyaa.mf.service.privilege;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.boyaa.mf.mapper.privilege.ResourcesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boyaa.mf.entity.privilege.Resources;
import com.boyaa.mf.entity.privilege.Role;
import com.boyaa.mf.entity.privilege.RoleResource;
import com.boyaa.mf.mapper.privilege.RoleResourceMapper;
import com.boyaa.mf.service.AbstractService;
import com.boyaa.mf.vo.ResourcesTree;

@Service
public class ResourcesService extends AbstractService<Resources, Integer> {
	
	@Autowired
	private RoleService roleService;
	@Autowired
	private RoleResourceMapper roleResourceMapper;
	@Autowired
	private ResourcesMapper resourcesMapper;
	
	@Override
	public int delete(Integer id) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("parentId",id);
		int count = findScrollDataCount(map);
		if(count>0){
			return -1;
		}
		
		int result = resourcesMapper.delete(id);
		if(result>0){
			//删光所有所依懒的权限
			RoleResource roleResource = new RoleResource();
			roleResource.setResourceId((Integer) id);
			this.deleteRoleResource(roleResource);
		}
		
		return result;
	}
	
	/**
	 * 获取用户权限
	 * @param userId
	 * @return
	 */
	public List<Resources> getUserResources(int userId){
		List<Resources> resources = new ArrayList<Resources>();
		
		List<Role> roles = roleService.findRolesByUserId(userId);
		for (Role role : roles) {
			resources.addAll(this.findResourcesByRoleId(role.getId()));
		}
		resources = new ArrayList<Resources>(new HashSet<Resources>(resources));// 去重(这里包含了当前用户可访问的所有资源)
		
		return resources;
	}

	public List<Resources> findResourcesByRoleId(int roleId) {
		return roleResourceMapper.findResourcesByRoleId(roleId);
	}
	public void insertRoleResource(RoleResource roleResource) {
		//判断是否已经存在，是如是，则直接返回
		int r = roleResourceMapper.isSet(roleResource);
		if(r>=1){
			return;
		}

		roleResourceMapper.insert(roleResource);
		
		//获取所有的父结点
		Resources resources = findById(roleResource.getResourceId());
		if(resources.getParent()!=null){
			roleResource.setResourceId(resources.getParent().getId());
			insertRoleResource(roleResource);
		}
	}
	public void deleteRoleResource(RoleResource roleResource) {
		roleResourceMapper.deleteRoleResource(roleResource);
		
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("parentId", roleResource.getResourceId());
		
		List<Resources> childList = findScrollDataList(maps);
		if(childList!=null && childList.size()>0){
			for(Resources resources:childList){
				RoleResource cResource = new RoleResource();
				cResource.setRoleId(roleResource.getRoleId());
				cResource.setResourceId(resources.getId());
				
				deleteRoleResource(cResource);
			}
		}
	}
	
	public List<ResourcesTree> getTree() {
		return resourcesMapper.getTree();
	}
}
