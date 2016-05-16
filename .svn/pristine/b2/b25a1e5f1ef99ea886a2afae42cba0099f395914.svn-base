<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/tld/privilege" prefix="privilege"%>
<%
	String nav = request.getParameter("nav");
%>
<div id="sidebar">
	<ul class="sidebar_ul">
		<li class="cutover_li <c:if test="${param.nav=='index'}">active</c:if>"><a href="config/index.htm" class="li_a"><i class="glyphicon glyphicon-home"></i> 首页</a>
		<li class="cutover_li <c:if test="${param.nav=='config'}">active</c:if>"><a href="javascript:void(0);" class="li_a"><i class="glyphicon glyphicon-cog"></i> 配置管理<span class="glyphicon glyphicon-collapse-up"></span></a>
			<ul <c:if test="${param.nav!='config'}">style="display: none;"</c:if>>
				<li <c:if test="${param.subnav=='siteDetails'}">class="active"</c:if>> <a href="config/siteDetail.htm"><i class="glyphicon glyphicon-globe"></i> 站点查询</a> </li>
				<li <c:if test="${param.subnav=='hbaseMeta'}">class="active"</c:if>><a href="config/hbaseMeta.htm"><i class="glyphicon glyphicon-th-large"></i> hbase 表管理</a></li>
				<li <c:if test="${param.subnav=='hiveMeta'}">class="active"</c:if>><a href="config/hiveMeta.htm"><i class="glyphicon glyphicon-th-large"></i> Hive表管理</a></li>
				<li <c:if test="${param.subnav=='hbrowkey'}">class="active"</c:if>><a href="config/hbrowkey.htm"><i class="glyphicon glyphicon-th-large"></i> hbase rowkey规则</a></li>
				<li <c:if test="${param.subnav=='feedConf'}">class="active"</c:if>><a href="config/feedConf.htm"><i class="glyphicon glyphicon-gift"></i> Feed管理</a></li>
				<li <c:if test="${param.subnav=='feedPlat'}">class="active"</c:if>><a href="config/feedPlat.htm"><i class="glyphicon glyphicon-cog"></i> Feed配置</a></li>
				<li <c:if test="${param.subnav=='eventCate'}">class="active"</c:if>><a href="config/eventCate.htm"><i class="glyphicon glyphicon-book"></i> 事件类型</a></li>
				<li <c:if test="${param.subnav=='event'}">class="active"</c:if>><a href="config/event.htm"><i class="glyphicon glyphicon-th-list"></i> 事件管理</a></li>
				<li <c:if test="${param.subnav=='mttJbsConf'}">class="active"</c:if>><a href="config/mttJbsConf.htm"><i class="glyphicon glyphicon-th-list"></i> 锦标赛配置</a></li>
				<li <c:if test="${param.subnav=='browser'}">class="active"</c:if>><a href="config/browserConf.htm"><i class="glyphicon glyphicon-th-list"></i> 浏览器规则</a></li>
	    	</ul>
		</li>
		<privilege:operate url="authIndex">
		<li class="cutover_li <c:if test="${param.nav=='privilege'}">active</c:if>"><a href="javascript:void(0);" class="li_a"><i class="glyphicon glyphicon-cog"></i> 权限管理<span class="glyphicon glyphicon-collapse-up"></span></a>
			<ul <c:if test="${param.nav!='privilege'}">style="display: none;"</c:if>>
				<li <c:if test="${param.subnav=='user'}">class="active"</c:if>> <a href="privilege/user.htm"><i class="glyphicon glyphicon-user"></i> <span>用户管理</span></a> </li>
				<li <c:if test="${param.subnav=='role'}">class="active"</c:if>> <a href="privilege/role.htm"><i class="glyphicon glyphicon-user"></i> <span>角色管理</span></a> </li>
				<li <c:if test="${param.subnav=='resources'}">class="active"</c:if>> <a href="privilege/resources.htm"><i class="glyphicon glyphicon-user"></i> <span>资源管理</span></a> </li>
	    	</ul>
		</li>
		</privilege:operate>
	</ul>
</div>
<script src="static/js/jquery-1.9.1.js"></script>
<script type="text/javascript">
	$(function(){
		//$("#sidebar").height($("#sidebar").height()-80);
		$("#sidebar").find(".cutover_li").each(function(){
			if(!$(this).hasClass("active")){
				$(this).find("ul").hide();
			}
		});
		$("#sidebar").find(".active").each(function(){
			if($(this).hasClass('cutover_li') && !$(this).hasClass("showul")){
				$(this).find("ul").show();
				$(this).addClass("showul");
				$(this).find("span:first").removeClass("glyphicon-collapse-up").addClass("glyphicon-collapse-down");
			}
		});

		$(".popoverTipLi").mouseenter(function(){
			$(this).popover('show');
		}).mouseleave(function(){
			$(this).popover('hide');
		});
	});

	$(".li_a").click(function(){
		$("#sidebar").find(".cutover_li").each(function(){
			$(this).find("ul").hide();
			$(this).removeClass("showul");
		});
		if($(this).parent().hasClass('showul')){
			$(this).parent().find("ul").hide();
			$(this).parent().removeClass("showul");
			$(this).find("span").removeClass("glyphicon-collapse-down").addClass("glyphicon-collapse-up");
		}else{
			$(this).parent().find("ul").show();
			$(this).parent().addClass("showul");
			$(this).find("span").removeClass("glyphicon-collapse-up").addClass("glyphicon-collapse-down");
		}
	});
</script>