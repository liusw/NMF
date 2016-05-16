package com.boyaa.mf.web.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.boyaa.base.utils.JSONUtil;
import com.boyaa.mf.constants.Constants;
import com.boyaa.mf.entity.data.OperationLog;
import com.boyaa.mf.entity.privilege.Resources;
import com.boyaa.mf.entity.privilege.User;
import com.boyaa.mf.service.data.BagService;
import com.boyaa.mf.service.data.OperationLogService;
import com.boyaa.mf.service.privilege.UserService;
import com.boyaa.mf.util.SpringBeanUtils;
import com.boyaa.mf.vo.LoginUserInfo;
import com.boyaa.service.AuthService;
import com.boyaa.servlet.ResultState;

public class AuthFilter implements Filter {
	static Logger logger = Logger.getLogger(AuthFilter.class);
	static Logger errorLogger = Logger.getLogger("errorLogger");
	static Logger fatalLogger = Logger.getLogger("fatalLogger");
	private static final String CONTENT_TYPE = "application/x-javascript";
	
	@Autowired
	private UserService userService;

	public void destroy() {

	}

	@SuppressWarnings("all")
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filter) throws IOException, ServletException {
		
		request.setCharacterEncoding(Constants.DEFAULT_ENCODING);
		response.setContentType(CONTENT_TYPE);
		response.setCharacterEncoding(Constants.DEFAULT_ENCODING);
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		
		//请求url相关
		String contextPath = req.getContextPath();
		String requestUri = req.getRequestURI();
		String queryString = req.getQueryString();
		
		String action = req.getParameter("action");
		
		if(requestUri.contains("/log/hiveQuery") || requestUri.contains("/log/template") || requestUri.contains("log/system")){
			resp.sendRedirect(req.getContextPath()+"/task/list/myTask.htm");
			return;
		}
		
//		if(requestUri.contains("/log/task") && StringUtils.isNotBlank(queryString) 
//				&& queryString.contains("action=list") && !queryString.contains("dir=list&action=list")
//				&& !queryString.contains("action=list&listType=1")){
//			resp.sendRedirect(req.getContextPath()+"/log/task?dir=list&action=list");
//			return;
//		}
		
		//判断是否为auth那边返回
		String tokenData = request.getParameter(Constants.AUTH_TOKEN_ID);
		if(StringUtils.isNotBlank(tokenData)){
			try {
				JSONObject json = JSONUtil.parseObject(tokenData);
				
				String code = json.getString("wid");
				String username = json.getString("username");
				String realName = json.getString("cname");
				String email = json.getString("email");
				
				Map<String,Object> params = new HashMap<String, Object>();
				params.put("code", json.getString("wid"));
				if(userService.findScrollDataCount(params)==0){
					User user = new User();
					user.setCode(Integer.parseInt(code));
					user.setStatus(1);
					user.setUsername(username);
					user.setRealName(realName);
					user.setEmail(email);
					userService.insert(user);
				}
				
				JSONArray permiss = json.getJSONArray("permiss");
				
				if(permiss==null || permiss.size()<=0){
					req.setAttribute("title", "访问被拒绝");
					req.setAttribute("info", "由于你没有访问本系统的权限,访问被拒绝。若要开通访问权限，请联系郑壮杰/黄奕能。");
					//req.getRequestDispatcher(req.getContextPath()+"/common/error.jsp").forward(request,response);  
					req.getRequestDispatcher("/WEB-INF/jsp/common/error.jsp").forward(request,response);  
					return;
				}
				
				//获取权限
				AuthService authService = new AuthService();
				Map<String,Object> resourceMap = authService.getResources(permiss);
				
				Iterator<String> it = resourceMap.keySet().iterator();
				while (it.hasNext()) {
					String key = (String) it.next();
				}
				
				String sig = json.getString("sig");
				//验证sig
				
				req.getSession().setAttribute("cname",realName);
				req.getSession().setAttribute("email",email);
				req.getSession().setAttribute("code",code);
				req.getSession().setMaxInactiveInterval(60*60*24);
				
				//设置用户权限
				LoginUserInfo userInfo = userService.setLoginUserInfo(realName,email,Integer.parseInt(code),username);
				req.getSession().setAttribute(Constants.USER_LOGIN_SESSION_NAME,userInfo);
				
				//把用户的权限放在session中
				req.getSession().setAttribute("auth",resourceMap);
			} catch (Exception e) {
				errorLogger.error(e.getMessage());
				req.setAttribute("title", "异常提示");
				req.setAttribute("info", "系统出现异常，暂时没法访问");
				req.getRequestDispatcher("/WEB-INF/jsp/common/error.jsp").forward(request,response);  
				return;
			}
		}
		
		LoginUserInfo userInfo = (LoginUserInfo) req.getSession().getAttribute(Constants.USER_LOGIN_SESSION_NAME);
		if(userInfo == null){
			// 如果是ajax请求响应头会有，x-requested-with；  
			if (req.getHeader("x-requested-with") != null && req.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")){  
            resp.setHeader("sessionstatus", "timeout");//在响应头设置session状态  
            return;  
        	}  
		
			String backUrl = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+requestUri+(StringUtils.isNotBlank(queryString)?"?"+queryString:"");
			AuthService authService = new AuthService();
			resp.sendRedirect(authService.url(backUrl, "login"));
			
			return;
		}
		
		//权限控制
		if(StringUtils.isNotBlank(req.getQueryString())){
			requestUri+= "?"+req.getQueryString();
		}
		requestUri = requestUri.replaceFirst(contextPath, "");
//		requestUri=requestUri.replaceAll("/HBServlet", "");
		
		//要拦截的路径,可改为配置文件的方式
		List<String> filterURLs = getAuthMapping();
		if(filterURLs!=null && filterURLs.size()>0){
			boolean authFlag = false;//是否要权限控制
			for(String url:filterURLs){
				if(requestUri.startsWith(url)){
//				if(requestUrl.matches("^"+url)){//使用正则
					authFlag = true;
					break;
				}
			}
			
			if(authFlag){
				//判断是否有权限
				boolean passAuth = false;
				List<Resources> resources = userInfo.getResources();
				for (Resources resource : resources) {
					if (StringUtils.isNotBlank(resource.getUrl()) && requestUri.startsWith(resource.getUrl())) {
						passAuth = true;
						break;
					}
				}
				if(!passAuth){
					String accept = req.getHeader("Accept");
					PrintWriter out = response.getWriter();  
					StringBuilder builder = new StringBuilder();
					if(accept!=null && accept.startsWith("application/json")){
						ResultState resultState = new ResultState(ResultState.FAILURE,"没权限操作");
						builder.append(jsonStr(resultState));
					}else{
	//					builder.append("<script type=\"text/javascript\" charset=\"UTF-8\">\n");   
	//					builder.append("window.top.location.href=\"");  
	//					builder.append("/xxx/\";");  
	//					builder.append("//Notlogged \n");
	//					builder.append("</script>");
						req.setAttribute("title", "访问被拒绝");
						req.setAttribute("info", "你没有访问此功能权限，请联系相关人员开通或者访问其他功能！");
						req.getRequestDispatcher("/WEB-INF/jsp/common/defaultError.jsp").forward(request,response);  
					}
					  
					out.print(builder.toString());  
					out.close();
					return;
				}
			}
		}
		
		OperationLogService operationLogService = SpringBeanUtils.getBean("operationLogService",OperationLogService.class);
		OperationLog operationLog = new OperationLog("操作日志",userInfo.getCode(),userInfo.getRealName(),0,requestUri);
		operationLogService.insert(operationLog);
		
		filter.doFilter(request, response);
	}

	public void init(FilterConfig arg0) throws ServletException {

	}
	
	public String jsonStr(ResultState resultState){
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("\"ok\":").append(resultState.getOk()).append(",");
		sb.append("\"msg\":\"").append(resultState.getMsg()).append("\",");
		sb.append("\"other\":").append(resultState.getOther().toString()).append(",");
		sb.append("\"data\":").append(resultState.getData());
		sb.append("}");
		return sb.toString();
	}
	
	private List<String> getAuthMapping(){
		String autoMapping = Constants.AUTH_MAPPING;
		if(StringUtils.isNotBlank(autoMapping)){
			String[] mappingArray = autoMapping.split(",");
			return Arrays.asList(mappingArray);
		}
		return null;
	}
}
