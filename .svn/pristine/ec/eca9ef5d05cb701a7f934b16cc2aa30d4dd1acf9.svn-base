package com.boyaa.mf.web.controller.privilege;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.boyaa.mf.entity.privilege.User;
import com.boyaa.mf.service.privilege.UserService;
import com.boyaa.mf.util.PageUtil;
import com.boyaa.mf.web.controller.BaseController;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 用户管理
 *
 * @作者 : huangyineng
 * @日期 : 2016-3-28  上午11:45:50
 */
@Controller
@RequestMapping("privilege")
public class UserController extends BaseController {
	@Resource
	private UserService userService;
	
	/**
	 * 
	 * @param sSearch_0	code
	 * @param sSearch_1	username
	 * @param sSearch_2	realName
	 * @param sSearch_3	email
	 * @return
	 */
	@RequestMapping("user/getData")
	@ResponseBody
	public String getData(String sSearch_0,String sSearch_1,String sSearch_2,String sSearch_3){
		Map<String, Object> map = new HashMap<String, Object>();
		
		if (StringUtils.isNotBlank(sSearch_0)) {
			map.put("likeCode", sSearch_0);
		}
		if (StringUtils.isNotBlank(sSearch_1)) {
			map.put("likeUserName", sSearch_1);
		}
		if (StringUtils.isNotBlank(sSearch_2)) {
			map.put("likeRealName", sSearch_2);
		}
		if (StringUtils.isNotBlank(sSearch_3)) {
			map.put("likeEmail", sSearch_3);
		}
		
		PageUtil<User> page = userService.getPageList(map);
		return getDataTableJson(page.getDatas(),page.getTotalRecord()).toJSONString();
	}
}
