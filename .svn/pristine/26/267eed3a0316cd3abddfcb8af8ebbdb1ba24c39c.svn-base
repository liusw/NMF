<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html lang="en-us">
<head>
<%@ include file="/WEB-INF/jsp/common/meta.jsp"%>
<link rel="stylesheet" type="text/css" href="static/daterangepicker/daterangepicker-bs3.css">
<style type="text/css">
#activeDetail thead  th,#activeDetail tbody td{
	text-align: center;
}
.autoContent{
	overflow-x: auto; 
	padding: 10px;
}
table{width:100%;}
table td,table th{word-break: keep-all;white-space:nowrap;}
</style>
<title>活跃详情</title>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/top.jsp">
		<jsp:param name="nav" value="data" />
	</jsp:include>

		<jsp:include page="/WEB-INF/jsp/common/dataMenu.jsp">
		<jsp:param name="nav" value="lc" />
		<jsp:param name="subnav" value="activeView" />
	</jsp:include>
		<div class="main">
			<jsp:include page="/WEB-INF/jsp/common/mainNav.jsp">
				<jsp:param name="actionName" value="活跃详情" />
				<jsp:param name="helpId" value="130" />
			</jsp:include>
			<div class="autoContent content-body">
				<jsp:include page="/WEB-INF/jsp/common/tabMenu.jsp">
					<jsp:param name="tab" value="active" />
					<jsp:param name="subnav" value="activeDetail" />
				</jsp:include>
				<br/>
				<table id="activeDetail"
					class="table table-striped table-bordered display" cellspacing="0" width="100%">
					<thead>
						<tr>
							<th align="center">日期</th>
							<th align="center">星期</th>
							<th align="center">流失回头</th>
							<th align="center">新用户</th>
							<th align="center">老用户</th>
							<th align="center">当日付费用户</th>
							<th align="center">历史付费用户</th>
							<th align="center">新用户一日回头率</th>
							<th align="center">老用户一日回头率</th>
							<th align="center">流失用户一日回头率</th>
							<th align="center">付费用户一日回头率</th>
						</tr>
					</thead>
				</table>
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
	var sTime = navForm.daterange.getStartDate("#navdaterange");
	var eTime = navForm.daterange.getEndDate("#navdaterange");
	var sid = $("#navSid").val();
	
	if(isEmpty(sid)){
		alert("站点不能为空!");
		return false;
	}
	sid = sid.split("_")[0];
	loadTable("active/activeDetail.htm?stm="+sTime+"&etm="+eTime+"&plat="+plat+"&sid="+sid);
});

function loadTable(url){
	$("#activeDetail").DataTable({
     "bFilter": true,
     "pagingType" : "full_numbers",
     "bDestroy" : true,
     "bProcessing" : false,
     "sAjaxSource" : url,
     "bServerSide" : true,
     "ordering": true,
     "searching":false,
     "columns": [
                 { "data": "time" },
                 { "data": "week" },
                 { "data": "activatingNum" },
                 { "data": "registerNum" },
                 { "data": "activeOldNum" },
                 { "data": "activePayNum" },
                 { "data": "activeHisPayNum" },
                 { "data": "yesterdayBackRate",
                	"render":function(data, type, row){
						return data+"%";
					}
                 },
                 { "data": "oldBackRate",
                	"render":function(data, type, row){
						return data+"%";
					}
                 },
                 { "data": "lostBackRate",
                 	"render":function(data, type, row){
 						return data+"%";
 					}
                  },
                 { "data": "payBackRate",
                	"render":function(data, type, row){
						return data+"%";
					}
                 }
      ],
     "oLanguage": commonData.oLanguage,
     "dom": 't<"bottom fl"l><"bottom"p>'
	});
}
</script>
</body>
</html>
