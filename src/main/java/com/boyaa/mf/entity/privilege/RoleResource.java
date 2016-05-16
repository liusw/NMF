package com.boyaa.mf.entity.privilege;

/**
 * 角色资源
 * 
 * @类名 : RoleResource.java
 * @作者 : MarsHuang
 * @部门 : 德州扑克业务中心-开拓者工作室-公共组
 * @公司 : 博雅互动
 * @日期 : 2014-12-15 下午2:59:38
 */
public class RoleResource {
	private Integer resourceId;
	private Integer roleId;

	public RoleResource() {
		super();
	}

	public RoleResource(Integer resourceId, Integer roleId) {
		super();
		this.resourceId = resourceId;
		this.roleId = roleId;
	}

	public Integer getResourceId() {
		return resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

}
