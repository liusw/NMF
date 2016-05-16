<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html lang="en-us">
<head>
	<%@ include file="/WEB-INF/jsp/common/meta.jsp"%>
	<title>访问异常</title>
	<style type="text/css">
		.content { border:1px solid #DCDDE2; background-color:#fff; padding: 40px 50px 30px 50px;margin: 0 auto;width: 80%;}
		h1 { margin:3px 0; height:32px; font-size: 19px; color: #1f1f1f; line-height: 35px; }
		p { margin:8px 1px; }
		.warning { background: url(static/images/warning.gif) no-repeat left; padding-left: 35px; }
		.partition { background-color:#00AEEF; height:2px; margin-bottom:8px; font-size:1px; }
		.partition_left { background-color:#005AAB; height:2px; border-right:#FFF solid 1px; display:block }
		.b_distance { padding-bottom:12px;padding-top: 20px;}
	</style>
</head>

<body>
	<jsp:include page="top.jsp">
	   <jsp:param name="nav" value=""/>
	</jsp:include>
	<div style="position: absolute;width: 100%;display: block;padding-top: 200px;">
	<div class="content">
	  <h1 class="warning">${title}</h1>
	  <div class="partition"> <span class="partition_left" style="width:150px;"></span> </div>
	  <p>${info}</p>
	   <p class="b_distance"><a href="index.jsp">返回主页</a></p>
	</div>
	</div>
	<script src="static/js/basic.js"></script>
</body>
</html>