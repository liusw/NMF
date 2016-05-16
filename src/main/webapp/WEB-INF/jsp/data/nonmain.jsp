<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en-us">
<head>
<%@ include file="/WEB-INF/jsp/common/meta.jsp"%>
<title>用户信息-数据魔方</title>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/top.jsp">
		<jsp:param name="nav" value="data" />
	</jsp:include>
	
	<jsp:include page="/WEB-INF/jsp/common/dataMenu.jsp">
		<jsp:param name="nav" value="businessData" />
		<jsp:param name="subnav" value="tableData" />
	</jsp:include>

	<div class="main2">
		<jsp:include page="/WEB-INF/jsp/common/topTabMenu.jsp">
			<jsp:param name="tabActive" value="userInfo" />
		</jsp:include>
		
		<jsp:include page="/WEB-INF/jsp/common/mainNav2.jsp" />
		
		<div class="main2Body">
			<ul class="nav nav-tabs" id="nav-tabs">
			  <li class="active"><a href="javascript:void(0)" toId="query-content">导出数据</a></li>
			  <li ><a href="javascript:void(0)" toId="update-content">同步数据</a></li>
			</ul>
			<div id="myTabContent" class="tab-content" style="margin-top: 5px;">
			</div>
		</div>
	</div>
	<%@ include file="/WEB-INF/jsp/common/foot.jsp"%>

	<!-- Bootstrap core JavaScript navbar-fixed-bottom
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="static/js/basic.js"></script>
	<script src="static/js/datetimepicke.js"></script>
	<script src="static/js/multiselect.js"></script>
	<script src="static/js/daterangepicker.js"></script>
	<script type="text/javascript" src="static/js/daterange.by.js"></script>
	<script type="text/javascript" src="static/js/common.js"></script>
	
	<script type="text/javascript">
	var navForm;
	$(function() {
		navForm = navForm.init({'showPlat':true});
		
		$("#nav-tabs a").click(function(e){
			$(this).tab("show");
			
		   var showTabId = $(this).attr("toId");
		   if("query-content" == showTabId){
			   $("#navPlat").hide();
	        	$("#myTabContent").load('data/nonmainQuery.htm');
			}else if("update-content" == showTabId){
				 $("#navPlat").show();
				$("#myTabContent").load('data/syncUserInfo.htm');
			}
		});
		$("#nav-tabs a:eq(0)").trigger("click");
	});
	</script>
</body>
</html>