package com.boyaa.mf.web.controller.privilege;

import com.boyaa.mf.entity.privilege.Role;
import com.boyaa.mf.entity.privilege.UserRole;
import com.boyaa.mf.service.privilege.RoleService;
import com.boyaa.mf.util.PageUtil;
import com.boyaa.mf.vo.RoleDTO;
import com.boyaa.mf.web.controller.BaseController;
import com.boyaa.mf.web.dto.AjaxObj;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色管理
 *
 * @类名 : RoleServlet.java
 * @作者 : MarsHuang
 * @部门 : 德州扑克业务中心-开拓者工作室-公共组
 * @公司 : 博雅互动
 * @日期 : 2014-12-16  下午4:41:32
 */
@Controller
@RequestMapping("privilege")
public class RoleController extends BaseController {

    static Logger logger = Logger.getLogger(RoleController.class);
    @Autowired
    private RoleService roleService;

    @RequestMapping("role/getData")
    @ResponseBody
    public String getData() {
        PageUtil<Role> page = roleService.getPageList();
        return getDataTableJson(page.getDatas(), page.getTotalRecord()).toJSONString();
    }

    @RequestMapping("role/getAllRole")
    @ResponseBody
    public AjaxObj getAllRole(Integer userId) {
        List<Role> roles = roleService.findScrollDataList();
        List<Role> userRoles = null;

        if (userId != null) {
            userRoles = roleService.findRolesByUserId(userId);
        }

        List<RoleDTO> roleDTOs = new ArrayList<RoleDTO>();
        RoleDTO roleDTO = null;

        try {
            for (Role role : roles) {
                roleDTO = new RoleDTO();
                BeanUtils.copyProperties(roleDTO, role);
                if (userRoles != null) {
                    if (userRoles.contains(role)) {
                        roleDTO.setCheck(true);
                    }
                }
                roleDTOs.add(roleDTO);
            }
            return new AjaxObj(AjaxObj.SUCCESS, "", roleDTOs);
        } catch (Exception e) {
            errorLogger.error(e.getMessage());
            return new AjaxObj(AjaxObj.FAILURE, e.getMessage());
        }
    }

    @RequestMapping("role/setUserRole")
    @ResponseBody
    public AjaxObj setUserRole(String check, String userId, String roleId) {
        if (StringUtils.isBlank(check) || StringUtils.isBlank(userId) || StringUtils.isBlank(roleId)) {
            return new AjaxObj(AjaxObj.FAILURE, "参数不正确");
        }

        UserRole userRole = new UserRole();
        userRole.setRoleId(Integer.parseInt(roleId));
        userRole.setUserId(Integer.parseInt(userId));
        try {
            if (Boolean.parseBoolean(check)) {
                roleService.insertUserRole(userRole);
            } else {
                roleService.deleteUserRole(userRole);
            }
            return new AjaxObj(AjaxObj.SUCCESS);
        } catch (Exception e) {
            errorLogger.error(e.getMessage());
            return new AjaxObj(AjaxObj.FAILURE, e.getMessage());
        }
    }
}