<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html lang="en-us">
<head>
<%@ include file="/WEB-INF/jsp/common/meta.jsp"%>
<style type="text/css">
#table thead  th,#table tbody td{
	text-align: center;
}
.autoContent{
	overflow-x: auto; 
	padding: 10px;
}
table{width:100%;}
table td,table th{word-break: keep-all;white-space:nowrap;}
</style>
<title>周留存统计</title>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/top.jsp">
		<jsp:param name="nav" value="data" />
	</jsp:include>

	<jsp:include page="/WEB-INF/jsp/common/dataMenu.jsp">
		<jsp:param name="nav" value="lc" />
		<jsp:param name="subnav" value="weekRetainStat" />
	</jsp:include>
	<div class="content">
		<div class="main">
			<jsp:include page="/WEB-INF/jsp/common/mainNav.jsp">
				<jsp:param name="actionName" value="周留存统计" />
				<jsp:param name="helpId" value="178" />
			</jsp:include>
			<div class="autoContent content-body">
				<br/>
				<table id="table" class="table table-striped table-bordered display" cellspacing="0" width="100%">
					<thead>
						<tr>
							<th align="center">日期</th>
							<th align="center">日活跃</th>
							<th align="center">上周留存</th>
							<th align="center">2周留存</th>
							<th align="center">3周留存</th>
							<th align="center">4周留存</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
	</div>
	<br/><br/><br/>
	<%@ include file="/WEB-INF/jsp/common/foot.jsp"%>

<!-- Bootstrap core JavaScript navbar-fixed-bottom
   ================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="static/js/basic.js"></script>
<script src="static/js/dataTable.js"></script>
<script src="static/js/multiselect.js"></script>
<script src="static/js/daterangepicker.js"></script>
<script src="static/js/daterange.by.js"></script>
<script src="static/js/dateUtils.js"></script>
<script src="static/js/common.js"></script>
<script type="text/javascript">

$(function(){
	var std = new Date();
	std.addDays(-10);
	std.format('yyyy-MM-dd');
	
	navForm = navForm.init({'showPlat':true,'showSid':true,'sidType':'Select','showDate':{'isShow':true,'startDate':std.toFormatDate()},
		platChangeCallback:updateNavMultiSid,'showBtn':true
	});
	
	$("#navSubmitBtn").trigger("click");
});

$('#navSubmitBtn').click(function(){
	var plat = $("#navPlat").val();
	var sTime = navForm.daterange.getStartDate("#navdaterange","YYYYMMDD");
	var eTime = navForm.daterange.getEndDate("#navdaterange","YYYYMMDD");
	var sid = $("#navSid").val();
	
	if(isEmpty(sid)){
		alert("站点不能为空!");
		return false;
	}
	sid = sid.split("_")[0];
	var params ={
			id:"10000001|10000002","dataType":"dataTable",
			args:{
				plat:plat,sid:sid,etm:eTime,stm:sTime,table:"week_retain_stat"
			}
		};
	loadTable("data/common/mysqlQuery.htm?params="+JSON.stringify(params));
});

function loadTable(url){
	$("#table").DataTable({
     "bFilter": true,
     "pagingType" : "full_numbers",
     "bDestroy" : true,
     "bProcessing" : false,
     "sAjaxSource" : url,
     "bServerSide" : true,
     "ordering": true,
     "searching":false,
     "columns": [
                 { "data": "tm" ,
                	 "render":function(data, type, row){
                		 var str = data+""; 
 						 return str.substr(0,4)+"-"+str.substr(4,2)+"-"+str.substr(6,2);
 					}},
                 { "data": "day_active" },
                 { "data": "last_week_retain" },
                 { "data": "two_week_retain" },
                 { "data": "three_week_retain" },
                 { "data": "four_week_retain" }
      ],
     "oLanguage": commonData.oLanguage,
     "dom": 't<"bottom fl"l><"bottom"p>'
	});
}
</script>
</body>
</html>
