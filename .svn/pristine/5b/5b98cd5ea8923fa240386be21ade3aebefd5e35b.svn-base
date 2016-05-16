<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@page import="java.util.Map"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/tld/privilege" prefix="privilege"%>
<%
	String nav = request.getParameter("nav");
	String action = request.getParameter("action");
	
	Map auth = (Map)session.getAttribute("auth");
%>

<div id="sidebar">
  <ul>
    <li <c:if test="${param.nav=='mylist'}">class="active"</c:if>> <a href="task/list/myTask.htm"><i class="glyphicon glyphicon-user"></i> <span>我的任务</span></a> </li>
    <li <c:if test="${param.nav=='mylist_auto'}">class="active"</c:if>> <a href="task/list/myAutoTask.htm"><i class="glyphicon glyphicon-user"></i> <span>自动任务</span></a> </li>
    <privilege:operate url="/task/list/allTask.htm">
    <li <c:if test="${param.nav=='list'}">class="active"</c:if>> <a href="task/list/allTask.htm"><i class="glyphicon glyphicon-globe"></i> <span>所有任务</span></a> </li>
    </privilege:operate>
    <privilege:operate url="/task/list/allAutoTask.htm">
    	<li <c:if test="${param.nav=='list_auto'}">class="active"</c:if>> <a href="task/list/allAutoTask.htm"><i class="glyphicon glyphicon-globe"></i> <span>所有自动任务</span></a> </li>
     </privilege:operate>
    <privilege:operate url="/task/audit/passAudit.htm">
        <li <c:if test="${param.nav=='list_audit'}">class="active"</c:if>> <a href="task/listaudit.htm"><i class="glyphicon glyphicon-globe"></i> <span>所有任务审批</span></a> </li>
    </privilege:operate>
  </ul>
</div>
<script src="static/js/jquery-1.9.1.js"></script>
<script type="text/javascript">
$(".coding").click(function(){
	showMsg("此功能正在开发中...","提示");
});
</script>