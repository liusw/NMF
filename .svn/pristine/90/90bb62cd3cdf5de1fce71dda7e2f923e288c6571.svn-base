package com.boyaa.mf.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @类名 : CookieUtil.java
 * @作者 : MarsHuang
 * @部门 : 德州扑克业务中心-开拓者工作室-公共组
 * @公司 : 博雅互动
 * @日期 : 2015-1-16 下午3:24:21
 */
public class CookieUtil {
	/**
	 * 获取指定键的Cookie
	 * 
	 * @param cookieName
	 * @return 如果找到Cookie则返回 否则返回null
	 */
	public static Cookie getCookie(String cookieName, HttpServletRequest request) {
		if (StringUtils.isEmpty(cookieName) || request == null) {
			return null;
		}
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				Cookie cookie = cookies[i];
				if (cookieName.equals(cookie.getName())) {
					return cookie;
				}
			}
		}
		return null;
	}

	/**
	 * 获取指定键的Cookie值
	 * 
	 * @param cookieName
	 * @return 如果找到Cookie则返回 否则返回null
	 */
	public static String getCookieValue(String cookieName,
			HttpServletRequest request) {
		Cookie cookie = getCookie(cookieName, request);
		return cookie == null ? null : cookie.getValue();
	}

	/**
	 * 删除指定的Cookie
	 * 
	 * @param cookieName
	 */
	public static void removeCookie(String cookieName, String domain,
			HttpServletRequest request, HttpServletResponse response) {
		Cookie cookie = getCookie(cookieName, request);
		if (cookie != null) {
			cookie.setMaxAge(0);
			cookie.setPath("/");
			cookie.setDomain(domain);
			response.addCookie(cookie);
		}
	}

	/**
	 * 设置Cookie
	 * 
	 * @param cookieName
	 * @param cookieValue
	 * @param host
	 * @param expiry
	 * @param response
	 */
	public static void setCookie(String cookieName, String cookieValue,
			String domain, int expiry, HttpServletRequest request,
			HttpServletResponse response) {
		Cookie cookie = new Cookie(cookieName, cookieValue); // 保存用户名到Cookie
		cookie.setPath("/");
		cookie.setDomain(domain);
		cookie.setMaxAge(expiry);
		response.addCookie(cookie);
	}
	
	public static void setCookie(String cookieName, String cookieValue,HttpServletResponse response) {
		Cookie cookie = new Cookie(cookieName, cookieValue);
		cookie.setPath("/");
		cookie.setMaxAge(-1);
		response.addCookie(cookie);
	}
}
