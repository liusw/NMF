<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/tld/privilege" prefix="privilege"%>
<!DOCTYPE html>
<html lang="en-us">
<head>
<%@ include file="/WEB-INF/jsp/common/meta.jsp"%>
<title>我的需求-数据魔方</title>
	<link href="static/css/matrix-style.css" rel="stylesheet">
	<link rel="stylesheet" href="static/kindeditor-4.1.10/themes/default/default.css" />
	<link rel="stylesheet" href="static/kindeditor-4.1.10/plugins/code/prettify.css" />
	<style type="text/css">
	#sidebar > ul li ul li a {
		border-bottom:1px dashed #303030;
	}
	
	.navRight {
	    color: #FF6600;
	    cursor: pointer;
	    float: right;
	    height: 38px;
	    line-height: 38px;
	    padding-right: 20px;
	}
	#example,#example thead  th {
		text-align: center;
	}
		.font-default,.font-default a{
		color : #333333;
	}
	.font-grey,.font-grey a{
		color : #CCCCCC;
	}
	.font-pink,.font-pink a{
		color : pink;
	}
	.font-green,.font-green a{
		color : #2E05E2;
	}
</style>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/top.jsp">
		<jsp:param name="nav" value="data" />
	</jsp:include>
	
	<jsp:include page="/WEB-INF/jsp/common/dataMenu.jsp">
		<jsp:param name="nav" value="dataRqmt" />
		<jsp:param name="subnav" value="myList" />
	</jsp:include>
	
	<div class="main">
		<div id="breadcrumb">
			<a href="/"><i class="glyphicon glyphicon-home"></i> 首页</a> <a class="current">我的需求</a>
			<span class="navRight">
				<button type="button" class="btn btn-primary btn-sm" onclick="window.location.href='dataRqmt/addDataRqmt.htm';">
				    <span class="glyphicon glyphicon-plus-sign" ></span>&nbsp;&nbsp;添加需求
				</button>
			</span>
		</div>
		
		<table id="example" class="table table-striped table-bordered display"  cellspacing="0" width="100%">
			<thead>
				<tr>
					<th>id</th>
					<th>标题</th>
					<th>创建人</th>
					<th>更新时间</th>
					<th>操作人</th>
					<th>状态</th>
					<th>处理人员</th>
					<th>排期时间</th>
				</tr>
			</thead>
		</table>
	</div>
	<div style="clear: both;"></div>
	<br/><br/>
	<%@ include file="/WEB-INF/jsp/common/foot.jsp"%>
	<script src="static/js/basic.js"></script>
	<script src="static/js/common.js"></script>
	<script src="static/js/dataTable.js"></script>
	<script src="static/js/dataRqmt.js"></script>
	<script type="text/javascript">
	$(function() {
		listDataRqmt("myList");
	});
	</script>
</body>
</html>
