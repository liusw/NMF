<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<% 
String path = request.getContextPath(); 
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; 
%> 

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href=" <%=basePath%>"> 
	<title>访问异常</title>
	<style type="text/css">
		body { font:15px "Microsoft Yahei", Arial, Helvetica, sans-serif, "宋体"; color:#333; background-color:#e7e8e9; letter-spacing: 0.05em; }
		#content { border:1px solid #DCDDE2; background-color:#fff; margin:40px 50px; padding:30px 40px 50px 40px; }
		h1 { margin:3px 0; height:32px; font-size: 19px; color: #1f1f1f; line-height: 35px; }
		p { margin:8px 1px; }
		.warning { background: url(static/images/warning.gif) no-repeat left; padding-left: 35px; }
		.partition { background-color:#00AEEF; height:2px; margin-bottom:8px; font-size:1px; }
		.partition_left { background-color:#005AAB; height:2px; border-right:#FFF solid 1px; display:block }
		.b_distance { padding-bottom:12px; }
	</style>
</head>

<body>
<div id="content">
  <h1 class="warning">${title}</h1>
  <div class="partition"> <span class="partition_left" style="width:150px;"></span> </div>
  <p class="b_distance">${info}</p>
</div>
</body>
</html>