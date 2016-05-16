<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en-us">
<head>
<%@ include file="/WEB-INF/jsp/common/meta.jsp"%>
<title>锦标赛-数据魔方</title>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/top.jsp">
       <jsp:param name="nav" value="data" />
	</jsp:include>
	
	<jsp:include page="/WEB-INF/jsp/common/dataMenu.jsp">
		<jsp:param name="nav" value="bisaichang" />
		<jsp:param name="subnav" value="jinbiaosai" />
	</jsp:include>
	
	<div class="main">
		<jsp:include page="/WEB-INF/jsp/common/mainNav.jsp">
			<jsp:param name="actionName" value="锦标赛" />
		</jsp:include>
		
		<div class="content-body autoContent">
			<ul class="nav nav-tabs" id="nav-tabs">
			  <li class="active"><a href="javascript:void(0)" toId="tj-content">统计</a></li>
			  <li ><a href="javascript:void(0)" toId="signup-content">报名流水</a></li>
			  <li><a href="javascript:void(0)" toId="info-content">比赛结果流水</a></li>
			  <li><a href="javascript:void(0)" toId="reward-content">奖励流水</a></li>
			</ul>
			
			<div id="myTabContent" class="tab-content">
				<div id="tj-content" class="content-form" style="display: none;">
					<jsp:include page="jinbiaosai_tj.jsp"/>
				</div>
				<div id="signup-content" class="content-form" style="display: none;">
					<jsp:include page="jinbiaosai_signup.jsp"/>
				</div>
				<div id="info-content" class="content-form" style="display: none;">
					<jsp:include page="jinbiaosai_info.jsp"/>
				</div>
				<div id="reward-content" class="content-form" style="display: none;">
					<jsp:include page="jinbiaosai_reward.jsp"/>
				</div>
			</div>
		</div>
	</div>

	<%@ include file="/WEB-INF/jsp/common/foot.jsp"%>

	<!-- Bootstrap core JavaScript navbar-fixed-bottom
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="static/js/basic.js"></script>
	<script src="static/js/common.js"></script>
	<script src="static/js/dataTable.js"></script>
	<script src="static/js/dateUtils.js"></script>
	<script src="static/js/daterangepicker.js"></script>
	<script src="static/js/daterange.by.js"></script>
	<script type="text/javascript">
	var navForm;
	$(function() {
		var std = new Date();
		std.format('yyyyMMdd');
		navForm = navForm.init({'showPlat':true,'showDate':{'isShow':true,'startDate':std.toFormatDate(),'endDate':std.toFormatDate()},'showBtn':true});
		
		$("#nav-tabs a").click(function(e){
		   navForm.hideAll();
		   e.preventDefault();
			$(this).tab("show");
			
		   var showTabId = $(this).attr("toId");
		   if("tj-content" == showTabId){
			   $("#navPlat").show();
	        	$("#navdaterange").show();
	        	
	        	$("#tj-content").show();
	        	$("#signup-content").hide();
	        	$("#reward-content").hide();
				 $("#info-content").hide();
	        	$("#navSubmit").show();
	        	
	        	document.getElementById("navSubmitBtn").onclick=navSubmitBtn_tj;
			}else if("signup-content" == showTabId){
				 $("#navPlat").show();
				 
				 $("#tj-content").hide();
				 $("#reward-content").hide();
				 $("#info-content").hide();
				 $("#signup-content").show();
			}else if("info-content" == showTabId){
				 $("#navPlat").show();
				 
				 $("#tj-content").hide();
				 $("#reward-content").hide();
				 $("#info-content").show();
				 $("#signup-content").hide();
			}else if("reward-content" == showTabId){
				 $("#navPlat").show();
				 
				 $("#tj-content").hide();
				 $("#reward-content").show();
				 $("#info-content").hide();
				 $("#signup-content").hide();
			}
		});
		
		$("#nav-tabs a:eq(0)").trigger("click");
		$("#navSubmitBtn").trigger("click");
	});
	</script>
</body>
</html>
