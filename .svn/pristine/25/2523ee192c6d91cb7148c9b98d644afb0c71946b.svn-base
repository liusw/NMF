package com.boyaa.mf.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.boyaa.base.utils.FileUtil;
import com.boyaa.mf.constants.Constants;
import com.boyaa.mf.util.ServletUtil;
import com.boyaa.service.AuthService;

public class AuthServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(AuthServlet.class);
	static Logger errorLogger = Logger.getLogger("errorLogger");
	static Logger fatalLogger = Logger.getLogger("fatalLogger");
	private static final String CONTENT_TYPE = "application/x-javascript";
	
	public void init() throws ServletException {
	}

	public void destroy() {
		super.destroy();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		logger.info("request ip:" + ServletUtil.getRemortIP(request));
		request.setCharacterEncoding(Constants.DEFAULT_ENCODING);
		response.setContentType(CONTENT_TYPE);
		response.setCharacterEncoding(Constants.DEFAULT_ENCODING);
		
		String action = request.getParameter("action");
		if("api".equals(action)){
			String json = FileUtil.read(AuthServlet.class.getClassLoader().getResource("auth.config").getPath(), "");
			response.getWriter().write(json.toString());
			return;
		}else if("logout".equals(action)){
			//清除session
			request.getSession().invalidate();
			
			Cookie cookie = new Cookie(Constants.AUTH_TOKEN_ID, null);  
			cookie.setMaxAge(-1);  
			cookie.setPath("/");//根据你创建cookie的路径进行填写      
			response.addCookie(cookie);  
			
			String backUrl = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
			AuthService authService = new AuthService();
			response.sendRedirect(authService.url(backUrl, "logout"));
		}
	}
}