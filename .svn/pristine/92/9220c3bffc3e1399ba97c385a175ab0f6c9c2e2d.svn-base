package com.boyaa.mf.web.controller.privilege;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.boyaa.mf.entity.privilege.Resources;
import com.boyaa.mf.entity.privilege.RoleResource;
import com.boyaa.mf.service.privilege.ResourcesService;
import com.boyaa.mf.vo.ResourcesTree;
import com.boyaa.mf.web.controller.BaseController;
import com.boyaa.mf.web.dto.AjaxObj;

/**
 * Created by liusw 创建时间：16-3-29. 资源管理
 */
@Controller
@RequestMapping("privilege")
public class ResourcesController extends BaseController {

	static Logger logger = Logger.getLogger(ResourcesController.class);

	@Autowired
	private ResourcesService resourcesService;

	@RequestMapping("resources/getData")
	@ResponseBody
	public JSONArray getData() {
		List<ResourcesTree> trees = resourcesService.getTree();
		return getJSONArray(trees);
	}

	@RequestMapping("resources/setRoleResourceData")
	@ResponseBody
	public JSONArray setRoleResourceData(String roleId) {

		List<ResourcesTree> trees = resourcesService.getTree();
		if (StringUtils.isNotBlank(roleId)) {
			List<Resources> roleResources = resourcesService
					.findResourcesByRoleId(Integer.parseInt(roleId));
			if (roleResources != null && roleResources.size() > 0) {
				for (ResourcesTree resourcesTree : trees) {
					for (Resources resources : roleResources) {
						if (resources.getId() == resourcesTree.getId()) {
							resourcesTree.setChecked(true);
							break;
						}
					}
				}
			}
		}
		return getJSONArray(trees);
	}

	@RequestMapping("resources/setRoleResources")
	@ResponseBody
	public AjaxObj setRoleResources(String roleId, String check,
			String resourceId) {
		if (StringUtils.isBlank(check) || StringUtils.isBlank(resourceId)
				|| StringUtils.isBlank(roleId)) {
			return new AjaxObj(AjaxObj.FAILURE, "参数不正确");
		}
		RoleResource roleResource = new RoleResource();
		roleResource.setResourceId(Integer.parseInt(resourceId));
		roleResource.setRoleId(Integer.parseInt(roleId));

		// 如果取消父，即还要把子全取消，如果添加子结点，要把父结点全加上
		if (Boolean.parseBoolean(check)) {
			resourcesService.insertRoleResource(roleResource);
		} else {
			resourcesService.deleteRoleResource(roleResource);
		}
		return new AjaxObj(AjaxObj.SUCCESS);
	}

	@RequestMapping("resources/add")
	@ResponseBody
	public AjaxObj add(String roleId, String check, String resourceId,
			Integer pid, String name, String url) {
		if (pid == null) {
			pid = 0;
		}

		if (StringUtils.isNotBlank(name) && StringUtils.isNotBlank(url)) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("url", url);
			int urlCount = resourcesService.findScrollDataCount(params);
			if (urlCount > 0) {
				return new AjaxObj(AjaxObj.FAILURE, "url已经存在");
			}

			Resources resources = new Resources();
			resources.setName(name);
			resources.setUrl(url);

			if (pid > 0) {
				Resources pResources = new Resources();
				pResources.setId(pid);
				resources.setParent(pResources);
			}

			int result = resourcesService.insert(resources);
			if (result > 0) {
				return new AjaxObj(AjaxObj.SUCCESS);
			}
		}
		return new AjaxObj(AjaxObj.FAILURE, "添加出错");
	}

	@RequestMapping("resources/update")
	@ResponseBody
	public AjaxObj update(Integer id, String name, String url) {

		if (id == null) {
			return new AjaxObj(AjaxObj.FAILURE, "ID不能为空");
		}

		if (StringUtils.isNotBlank(name) && StringUtils.isNotBlank(url)) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("url", url);
			params.put("nqid", id);
			int urlCount = resourcesService.findScrollDataCount(params);
			if (urlCount > 0) {
				return new AjaxObj(AjaxObj.FAILURE, "url已经存在");
			}

			Resources resources = new Resources();
			resources.setName(name);
			resources.setUrl(url);
			resources.setId(id);

			int result = resourcesService.update(resources);
			if (result > 0) {
				return new AjaxObj(AjaxObj.SUCCESS);
			}
		}
		return new AjaxObj(AjaxObj.FAILURE, "更新出错");
	}

	@RequestMapping("resources/getById")
	@ResponseBody
	public AjaxObj getById(Integer id) {
		if (id == null) {
			return new AjaxObj(AjaxObj.FAILURE, "ID不能为空");
		}
		Resources resources = resourcesService.findById(id);
		return new AjaxObj(AjaxObj.SUCCESS, "", resources);
	}

	@RequestMapping("resources/delete")
	@ResponseBody
	public AjaxObj delete(Integer id) {
		if (null == id) {
			return new AjaxObj(AjaxObj.FAILURE, "ID不能为空");
		}
		int result = resourcesService.delete(id);
		if (result == -1) {
			return new AjaxObj(AjaxObj.FAILURE, "有子结点，不能删除");
		}
		if (result > 0) {
			return new AjaxObj(AjaxObj.SUCCESS);
		}
		return new AjaxObj(AjaxObj.FAILURE, "删除出错");
	}
}
