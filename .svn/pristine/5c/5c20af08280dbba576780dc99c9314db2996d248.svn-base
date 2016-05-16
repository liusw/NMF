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
<title>返利统计-数据魔方</title>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/top.jsp">
       <jsp:param name="nav" value="data" />
	</jsp:include>
	
	<jsp:include page="/WEB-INF/jsp/common/dataMenu.jsp">
		<jsp:param name="nav" value="businessData" />
		<jsp:param name="subnav" value="rebateLog" />
	</jsp:include>
	
	<div class="main">
		<jsp:include page="/WEB-INF/jsp/common/mainNav.jsp">
			<jsp:param name="actionName" value="返利统计" />
		</jsp:include>
		
		<div class="content-body">
			<ul class="nav nav-tabs" id="nav-tabs">
			  <li class="active"><a href="javascript:void(0)" toId="tab1-content">积分汇总统计</a></li>
			  <li><a href="javascript:void(0)" toId="tab2-content">用户当前星级分布</a></li>
			  <li><a href="javascript:void(0)" toId="tab3-content">用户礼品积分情况(点击导出)</a></li>
			</ul>
			
			<div id="myTabContent" class="tab-content">
				<div id="tab1-content" class="content-form" style="display: none;">
					<jsp:include page="rebateLog_tj.jsp"/>
				</div>
				<div id="tab2-content" class="content-form" style="display: none;">
					<jsp:include page="rebateLog_star.jsp"/>
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
		   e.preventDefault();
		   var showTabId = $(this).attr("toId");
		   if("tab1-content" == showTabId){
			   navForm.hideAll();
			   $("#navPlat").show();
	        	$("#navdaterange").show();
	        	
	        	$("#tab1-content").show();
	        	$("#tab2-content").hide();
	        	$("#tab3-content").hide();
	        	
	        	$("#navSubmit").show();
	        	document.getElementById("navSubmitBtn").onclick=navSubmitBtn_tj;
	        	$("#navSubmitBtn").trigger("click");
			}else if("tab2-content" == showTabId){
				navForm.hideAll();
				 $("#navPlat").show();
				 $("#navdaterange").show();
				 $("#navSubmit").show();
				 
				 $("#tab1-content").hide();
				 $("#tab2-content").show();
				 $("#tab3-content").hide();
				 document.getElementById("navSubmitBtn").onclick=navSubmitBtn_star;
				 $("#navSubmitBtn").trigger("click");
			}else if("tab3-content" == showTabId){
				var msg = "确定不是手误直接导出用户礼品积分情况?";   
				if (!confirm(msg)){
					return;
				}
				
				var start = navForm.daterange.getStartDate("#navdaterange","YYYYMMDD");
				var end = navForm.daterange.getEndDate("#navdaterange","YYYYMMDD");
				
				var sql = "select tm,`_uid`,giftPoint "+
							 "from("+
									 "select `_plat`,tm,`_uid`,giftPoint,row_number() over (),rank() over(partition by `_plat`,`_uid` order by `_tm` asc,row_number() over ()) as rank "+
									 "from rebateLog where `_plat`="+$("#navPlat").val()+" and tm between "+start +" and "+end+
							 ")subquery where subquery.rank=1";
				var title = "日期,用户ID,礼品积分剩余";
				var taskJson = {
					taskName :$("#navPlat").val()+"平台"+"("+start+"至"+end+")用户礼品积分情况",
					'process' : [ {
							'tmpId' : 'p0',
							'type' : '1',
							'operation' : sql,
							'columnName' : title,
							'title' : title
						} ]
				};
				$.post("task/process/addByJson.htm", {
					taskJson : JSON.stringify(taskJson)
				},
				function(data) {
					commonDo.addedTaskSuccess(data);
				},
				"json").error(function(){
					commonDo.addedTaskError();
				});
				
				 return;
			}
		   $(this).tab("show");
		});
		
		$("#nav-tabs a:eq(0)").trigger("click");
		//$("#navSubmitBtn").trigger("click");
	});
	</script>
</body>
</html>
