package com.boyaa.mf.service.privilege;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boyaa.mf.entity.privilege.Resources;
import com.boyaa.mf.entity.privilege.User;
import com.boyaa.mf.entity.privilege.UserRole;
import com.boyaa.mf.service.AbstractService;
import com.boyaa.mf.vo.LoginUserInfo;

@Service
public class UserService extends AbstractService<User, Integer> {
	@Autowired
	private ResourcesService resourcesService;
	
	@Override
	public int insert(User user) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("code", user.getCode());
		int count = findScrollDataCount(params);
		if(count==0){
			int result = super.insert(user);
			if(result>0){
				//设置角色为普通运营
				UserRole userRole = new UserRole(user.getId(), 3);
				RoleService roleService = new RoleService();
				roleService.insertUserRole(userRole);
			}
			
			return result;
		}
		return 0;
	}

	public LoginUserInfo setLoginUserInfo(String realName, String email, int code,String username) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("code", code);
		List<User> users = findScrollDataList(params);
		
		if(users!=null && users.size()>0){
			User user = users.get(0);
			LoginUserInfo userInfo = new LoginUserInfo();
			userInfo.setCode(user.getCode());
			userInfo.setUsername(user.getUsername());
			userInfo.setRealName(user.getRealName());
			userInfo.setEmail(user.getEmail());
			
			List<Resources> list = resourcesService.getUserResources(user.getId());
			userInfo.setResources(list);
			
			return userInfo;
		}
		return null;
	}
}
