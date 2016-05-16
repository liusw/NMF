<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html lang="en-us">
<head>
<%@ include file="/WEB-INF/jsp/common/meta.jsp"%>
<style type="text/css">
#activeView thead  th,#activeView tbody td{
	text-align: center;
}
table{width:100%;}
table td,table th{word-break: keep-all;white-space:nowrap;}
</style>
<title>积分分布</title>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/top.jsp">
		<jsp:param name="nav" value="data" />
	</jsp:include>

	<jsp:include page="/WEB-INF/jsp/common/dataMenu.jsp">
		<jsp:param name="nav" value="lc" />
		<jsp:param name="subnav" value="integrationStatist" />
	</jsp:include>
		<div class="main">
			<jsp:include page="/WEB-INF/jsp/common/mainNav.jsp">
				<jsp:param name="actionName" value="积分分布" />
				<jsp:param name="helpId" value="130" />
			</jsp:include>
			<div class="autoContent content-body">
			<br/>
				<table id="itgStatsTab"
					class="table table-striped table-bordered display" cellspacing="0" width="100%">
					<thead>
						<tr>
							<th align="center">日期</th>
							<th align="center">星期</th>
							<th align="center">不超过500</th>
							<th align="center">501~1000</th>
							<th align="center">1001~3000</th>
							<th align="center">3001~9000</th>
							<th align="center">9001~18000</th>
							<th align="center">18001~36000</th>
							<th align="center">36001~54000</th>
							<th align="center">54001~72000</th>
							<th align="center">大于72000</th>
							<th align="center">总计</th>
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
	var sTime = navForm.daterange.getStartDate("#navdaterange");
	var eTime = navForm.daterange.getEndDate("#navdaterange");
	var sid = $("#navSid").val();
	
	if(isEmpty(sid)){
		alert("站点不能为空!");
		return false;
	}
	
	var bpid = sid.split("_")[1];
	
	if(undefined == table){
		var table = $("#itgStatsTab").DataTable({
		     "bFilter": true,
		     "pagingType" : "full_numbers",
		     "bDestroy" : true,
		     "bProcessing" : false,
		     "sAjaxSource" : "data/itgStats/getData.htm?stm="+sTime+"&etm="+eTime+"&bpid="+bpid,
		     "bServerSide" : true,
		     "ordering": true,
		     "searching":false,
		     "sServerMethod": "POST",
		     "columns": [
		                 { "data": "tm" },
		                 { "data": "tm","render": function(data, type, row){
		                	 data = data + "";
		                	 var xingqi = null;
		                	 switch(new Date(data.substring(0,4) + "/" + data.substring(4,6) + "/" + data.substring(6,8)).getDay()) 
		                	 { 
			                	 case 0:xingqi="星期日";break; 
			                	 case 1:xingqi="星期一";break; 
			                	 case 2:xingqi="星期二";break; 
			                	 case 3:xingqi="星期三";break; 
			                	 case 4:xingqi="星期四";break; 
			                	 case 5:xingqi="星期五";break; 
			                	 case 6:xingqi="星期六";break; 
			                	 default:xingqi="系统错误！" 
		                	 } 
		                	 return xingqi;
							}
		                 },
		                 { "data": "notGt500" },
		                 { "data": "interval501_1000" },
		                 { "data": "interval1001_3000" },
		                 { "data": "interval3001_9000" },
		                 { "data": "interval9001_18000" },
		                 { "data": "interval18001_36000" },
		                 { "data": "interval36001_54000" },
		                 { "data": "interval54001_72000" },
		                 { "data": "gt72000" },
		                 { "data": "totalCount"},
		      ],
		     "oLanguage": commonData.oLanguage,
		     "dom": 't<"bottom fl"l><"bottom"p>'
			});
	}else{
		table.ajax.url("data/itgStats/getData.htm?stm="+sTime+"&etm="+eTime+"&bpid="+bpid).load();
	}
});

</script>
</body>
</html>
