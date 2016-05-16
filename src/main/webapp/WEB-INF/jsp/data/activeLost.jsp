<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html lang="en-us">
<head>
<%@ include file="/WEB-INF/jsp/common/meta.jsp"%>
<style type="text/css">
#activeLost thead  th,#activeLost tbody td{
	text-align: center;
}
.autoContent{
	overflow-x: auto; 
	padding: 10px;
}
table{width:100%;}
table td,table th{word-break: keep-all;white-space:nowrap;}
</style>
<title>流失统计</title>
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
				<jsp:param name="actionName" value="活跃流失统计" />
				<jsp:param name="helpId" value="129" />
			</jsp:include>
			<div class="autoContent content-body">
				<jsp:include page="/WEB-INF/jsp/common/tabMenu.jsp">
					<jsp:param name="tab" value="active" />
					<jsp:param name="subnav" value="activeLost" />
				</jsp:include>
				<br/>
				<table id="activeLost"
					class="table table-striped table-bordered display" cellspacing="0" width="100%">
					<thead>
						<tr>
							<th align="center">日期</th>
							<th align="center">星期</th>
							<th align="center">昨日活跃流失</th>
							<th align="center">3日活跃流失</th>
							<th align="center">7日活跃流失</th>
							<th align="center">15日活跃流失</th>
							<th align="center">30日活跃流失</th>
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
	loadTable("active/activeLost.htm?stm="+sTime+"&etm="+eTime+"&plat="+plat+"&sid="+sid);
});

function loadTable(url){
	$("#activeLost").DataTable({
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
                 { "data": "yesterdayActiveLost"},
                 { "data": "threeActiveLost" },
                 { "data": "sevenActiveLost" },
                 { "data": "fiftyActiveLost" },
                 { "data": "thirtyActiveLost" }
      ],
     "oLanguage": commonData.oLanguage,
     "dom": 't<"bottom fl"l><"bottom"p>'
	});
}
</script>
</body>
</html>
