package com.boyaa.mf.util;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;

import com.boyaa.mf.constants.Constants;
import com.boyaa.mf.entity.privilege.Resources;
import com.boyaa.mf.vo.LoginUserInfo;

public class PrivilegeTag extends TagSupport {
	private static final long serialVersionUID = 1L;
	private String url;
	
	@Override
	public int doStartTag() throws JspException {
		HttpSession session = this.pageContext.getSession();
		if(havePermission(session,url)){
			return EVAL_PAGE;
		}
		return SKIP_BODY;
	}
	
	private boolean havePermission(HttpSession session,String url) {
		LoginUserInfo loginUserInfo = (LoginUserInfo)session.getAttribute(Constants.USER_LOGIN_SESSION_NAME);
		List<Resources> resources = loginUserInfo.getResources();
		for (Resources resource : resources) {
			if (StringUtils.equals(resource.getUrl(),url)) {
				return true;
			}
		}
		return false;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}