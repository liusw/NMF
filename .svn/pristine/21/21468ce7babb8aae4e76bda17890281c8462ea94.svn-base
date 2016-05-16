<%@page import="java.util.Map"%>
<%@ page isELIgnored="false" %>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/tld/privilege" prefix="privilege"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();  
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";    
%>

<%
	String nav = request.getParameter("nav");
	String showPlatSelect = request.getParameter("showPlatSelect");
	String action = request.getParameter("action");
	
	Map auth = (Map)session.getAttribute("auth");
%>
<div class="header-container">
	<div id="header">
	  <h1><img alt="" src="static/images/logo.png" width="30px" height="30px"/>&nbsp;&nbsp;<a href="">数据魔方</a></h1>
	</div>
	<div id="top-nav">
	 		<div id="user-nav" class="navbar navbar-inverse">
			  <ul class="nav" style="width: auto; margin: 0px;">
			      <li class="dropdown"><a href="javascript:void(0);" class="dropdown-toggle" data-toggle="dropdown"><i class="glyphicon glyphicon-user"></i>  <%=request.getSession().getAttribute("cname") %><b class="caret"></b></a>
				      <ul class="dropdown-menu">
							<li><a href="<%=basePath%>auth?action=logout">退出系统</a></li>
						</ul>
				    </li>
				   <li <% if(nav.equals("data")){ %>class="active"<%} %>><a href="<%=basePath%>task/commonExport.htm">数据运营</a></li>
			    	<li <% if(nav.equals("list")){ %>class="active"<%} %>><a href="<%=basePath%>task/list/myTask.htm">任务管理</a></li>
			    	<li <% if(nav.equals("docs")){ %>class="active"<%} %>><a href="<%=basePath%>docs/home.htm">帮助中心</a></li>
			    	<privilege:operate url="authIndex">
			    	<li <% if(nav.equals("sysconfig")){ %>class="active"<%} %>><a href="<%=basePath%>config/index.htm">系统管理</a></li>
			    	</privilege:operate>
			    	<!-- <li><a href="privilege/user?action=index">RTX交流群 <i style="color: #ff6600;" class="glyphicon glyphicon-bullhorn"></i></a></li> -->
			  </ul>
			</div>
			<!-- 
			<div class="notice">
				<div style="float: left;color: #ff6600;" title="公告">
					<span class="glyphicon glyphicon-hand-right"></span>
				</div>
				<div style="float: left;">
				<div id="demo" style="overflow:hidden;height:50px;">
					<div id=demo1>
					<c:forEach items="${applicationScope.noticeArticle }" var="article">
						<a target="_blank" href="docs/article/detail.htm?id=${article.id }">&nbsp;&nbsp;${article.title }</a><br/>
					</c:forEach>
					</div>
					<div id=demo2></div>
				</div>
				<script>
				var speed=40;
				var demo=document.getElementById("demo");
				var demo2=document.getElementById("demo2");
				var demo1=document.getElementById("demo1");
				demo2.innerHTML=demo1.innerHTML;
				function Marquee(){
					if(demo2.offsetTop-demo.scrollTop<=0){
						demo.scrollTop-=demo1.offsetHeight;
					}else{
						demo.scrollTop++;
					}
				}
				var MyMar=setInterval(Marquee,speed);
				demo.onmouseover=function() {clearInterval(MyMar);}
				demo.onmouseout=function() {MyMar=setInterval(Marquee,speed);}
				</script>
				</div>
			</div>
		 -->
	</div>
	
	<% if(showPlatSelect!=null && showPlatSelect.equals("true")){ %>
	<div id="right-nav">
		 <form class="form-inline">
			 <div class="form-group">平台:&nbsp;&nbsp;<select class="form-control" id="topPlats"></select></div>
		</form>
	</div>
	<%} %>
</div>
<div style="clear: both;"></div>

