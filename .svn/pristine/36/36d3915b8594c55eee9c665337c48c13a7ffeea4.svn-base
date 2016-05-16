<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en-us">
<head>
	<%@ include file="/WEB-INF/jsp/common/meta.jsp"%>
	<title>数据魔方</title>
	
	<link href="static/css/matrix-style.css" rel="stylesheet">
	<style type="text/css">
		.page-header {
	    border-bottom: 1px solid #dadada;
	    padding-bottom: 2px;
	    margin: 20px 0 0 0;
		}
		hr {
			border-top-color: #dadada;
		}
		.quick-actions_homepage {
			width: 100%;
			position: relative;
			float: left;
			margin-top: 10px;
		}
		.quick-actions{
			display: block;
			list-style: none outside none;
			text-align: center;
			float: left;
			padding:0;
			width: 100%;
		}
		.quick-actions li{
			float: left;
			line-height: 18px;
			margin: 0 10px 10px 0px;
			min-width: 14%;
			min-height: 70px;
			position: relative;
			padding: 0;
		}
		.quick-actions li a:hover,.quick-actions li:hover{
			background: #2E363F;
		}
		.quick-actions li a {
			padding: 10px 30px;
			display: block;
			color: #fff;
			font-size: 14px;
			font-weight: lighter;
		}
		.quick-actions li a i[class^="glyphicon-"], .quick-actions li a i[class*=" glyphicon-"] {
			font-size: 30px;
			display: block;
			margin: 0 auto 5px;
		}
		.bg_lb{ background:#27a9e3;}
		.bg_db{ background:#2295c9;}
		.bg_lg{ background:#28b779;}
		.bg_dg{ background:#28b779;}
		.bg_ly{ background:#ffb848;}
		.bg_dy{ background:#da9628;}
		.bg_ls{ background:#2255a4;}
		.bg_lo{ background:#da542e;}
		.bg_lr{ background:#f74d4d;}
		.bg_lv{ background:#603bbc;}
		.bg_lh{ background:#b6b3b3;}
	</style>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/top.jsp">
        <jsp:param name="nav" value=""/>
	</jsp:include>
	
	<div class="main" style="padding-left: 0px;">
		<h1 id="overview" class="page-header">功能概览</h1>
		<div class="quick-actions_homepage">
			<h3>数据查询/导出</h3>
	      <ul class="quick-actions">
	        <li class="bg_lb"> <a href="data/order.htm"> <i class="glyphicon glyphicon-usd"></i> 订单查询</a> </li>
	        <li class="bg_lg"> <a href="data/ban.htm"> <i class="glyphicon glyphicon-remove"></i> 用户查封</a> </li>
	        <li class="bg_ly"> <a href="data/nonmain.htm"> <i class="glyphicon glyphicon-user"></i> 用户信息 </a> </li>
	        <li class="bg_lr"> <a href="data/lostUser.htm"> <i class="glyphicon glyphicon-log-out"></i> 用户流失</a> </li>
	        <li class="bg_ls"> <a href="log/adminHql.jsp"> <i class="glyphicon glyphicon-adjust"></i> 广告</a> </li>
	        <li class="bg_lo"> <a href="log/hql.jsp"> <i class="glyphicon glyphicon-wrench"></i> HQL查询</a> </li>
	        <li class="bg_ls"> <a href="log/task"> <i class="glyphicon glyphicon-link"></i> 关联查询</a> </li>
	        <li class="bg_lb"> <a href="data/brush.htm"> <i class="glyphicon glyphicon glyphicon-file"></i>规则配置</a> </li>
	        <li class="bg_lg"> <a href="data/brushData.htm"> <i class="glyphicon glyphicon glyphicon-eye-close"></i> 黑名单</a> </li>
	      </ul>
	      <h3>图表</h3>
	      <ul class="quick-actions">
	        <li class="bg_lo"> <a href="chart/pinglog.htm"> <i class="glyphicon glyphicon-sort-by-attributes-alt"></i> 网络延迟</a> </li>
	        <li class="bg_lb"> <a href="chart/pingstalogs.htm"> <i class="glyphicon glyphicon-transfer"></i> 网络连接</a> </li>
	        <li class="bg_lr"> <a href="chart/bag.htm"> <i class="glyphicon glyphicon-briefcase"></i> 口袋德州</a> </li>
	      </ul>
	      <h3>配置</h3>
	      <ul class="quick-actions">
	        <li class="bg_ly"> <a href="log/config?action=siteDetails"> <i class="glyphicon glyphicon-globe"></i> 站点查询</a> </li>
	      </ul>
	      <div style="clear: both;"></div>
	   	<hr>
	    </div>
	</div>
	<%@ include file="/WEB-INF/jsp/common/foot.jsp"%>

	<!-- Bootstrap core JavaScript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="static/js/jquery-1.9.1.js"></script>
	<script src="static/js/jquery-ui.js"></script>
	<script src="static/js/bootstrap.min.js"></script>
</body>
</html>
