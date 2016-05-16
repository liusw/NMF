<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en-us">
<head>
<%@ include file="/WEB-INF/jsp/common/meta.jsp"%>
<title>数据魔方</title>
<link rel="stylesheet" href="static/kindeditor-4.1.10/themes/default/default.css" />
<style type="text/css">
.popover {
	max-width: none;
}

.dropdown-menu>li>a {
	clear: both;
	color: #333333;
	display: block;
	font-weight: 400;
	line-height: 1.42857;
	padding: 3px 8px;
	white-space: nowrap;
}

#example thead  th {
	text-align: center;
}

td.details-control {
	background: url('static/images/details_open.png') no-repeat center center;
	cursor: pointer;
}

tr.shown td.details-control {
	background: url('static/images/details_close.png') no-repeat center
		center;
}
</style>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/top.jsp">
        <jsp:param name="nav" value="list"/>
        <jsp:param name="action" value="mylist"/>
	</jsp:include>
	
	<jsp:include page="/WEB-INF/jsp/task/userMenu.jsp">
		<jsp:param name="nav" value="mylist" />
	</jsp:include>
	
	<div class="main">
	<jsp:include page="/WEB-INF/jsp/common/mainNav.jsp">
			<jsp:param name="actionName" value="我的任务列表" />
		</jsp:include>
		
		<table id="example" class="table table-striped table-bordered display"  cellspacing="0" width="100%">
			<thead>
				<tr>
					<th></th><th>ID</th><th>任务名称</th><th>操作用户</th><th>执行状态</th><th>日志信息</th><th>执行结果</th>
					<th>文件大小</th><th>开始执行时间</th><th>结束执行时间</th><th>操作</th><th>等待任务数</th>
				</tr>
			</thead>
			<tfoot>
	            <tr>
	            	<th></th><th></th><th></th><th></th><th></th><th></th><th></th>
					<th></th><th></th><th></th><th></th><th></th>
	            </tr>
       		</tfoot>
		</table>
	</div>

	<br/><br/>
	<%@ include file="/WEB-INF/jsp/common/foot.jsp"%>

	<!-- Bootstrap core JavaScript navbar-fixed-bottom
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="static/js/basic.js"></script>
	<script src="static/js/dataTable.js"></script>
	<script src="static/js/common.js"></script>
	<script charset="utf-8" src="static/kindeditor-4.1.10/kindeditor-min.js"></script>
	<script charset="utf-8" src="static/kindeditor-4.1.10/lang/zh_CN.js"></script>
	<script src="http://tool.oa.com/api/?id=uCheck"></script>
	<script src="static/js/taskList.js.jsp" data="mylist" id="taskJs"></script>
	<script type="text/javascript">
	$.fn.modal.Constructor.prototype.enforceFocus = function () {};
	</script>
</body>
</html>