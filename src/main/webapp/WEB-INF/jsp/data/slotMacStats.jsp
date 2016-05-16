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
.main{
	overflow-x: auto; 
	padding: 60px 15px 30px;
}
table{width:100%;}
table td,table th{word-break: keep-all;white-space:nowrap;}
</style>
<title>老虎机</title>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/top.jsp">
		<jsp:param name="nav" value="data" />
	</jsp:include>

	<jsp:include page="/WEB-INF/jsp/common/dataMenu.jsp">
		<jsp:param name="nav" value="lc" />
		<jsp:param name="subnav" value="slotMacStats" />
	</jsp:include>
		<div class="main">
			<jsp:include page="/WEB-INF/jsp/common/mainNav.jsp">
				<jsp:param name="actionName" value="老虎机" />
				<jsp:param name="helpId" value="130" />
			</jsp:include>
			<div class="autoContent content-body">
			<br/>
				<table id="example"
					class="table table-striped table-bordered display" cellspacing="0" width="100%">
					<thead>
						<tr>
							<th align="center">日期</th>
							<th align="center">投注总额</th>
							<th align="center">回收总额</th>
							<th align="center">玩家获得</th>
							<th align="center">不超过200投注</th>
							<th align="center">不超过200中奖</th>
							<th align="center">201~2000投注</th>
							<th align="center">201~2000中奖</th>
							<th align="center">2001~20000投注</th>
							<th align="center">2001~20000中奖</th>
							<th align="center">20001~200000投注</th>
							<th align="center">20001~200000中奖</th>
							<th align="center">超过200000投注</th>
							<th align="center">超过200000中奖</th>
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
	var platId = $("#navPlat").val();
	var sTime = navForm.daterange.getStartDate("#navdaterange");
	var eTime = navForm.daterange.getEndDate("#navdaterange");
	var sid = $("#navSid").val();
	
	if(isEmpty(sid)){
		alert("站点不能为空!");
		return false;
	}
	
	var sid = sid.split("_")[0];
	
	if(undefined == table){
		var table = $("#example").DataTable({
		     "bFilter": true,
		     "pagingType" : "full_numbers",
		     "bDestroy" : true,
		     "bProcessing" : false,
		     "sAjaxSource" : "data/slotMacStats/getData.htm?stm="+sTime+"&etm="+eTime+"&plat="+platId+"&sid="+sid,
		     "bServerSide" : true,
		     "ordering": true,
		     "searching":false,
		     "sServerMethod": "POST",
		     "columns": [
		                 { "data": "tm" },
		                 { "data": "totalBet"},
		                 { "data": "totalBet"},
		                 { "data": "reward" },
		                 { "data": "ntGt200BetCount" },
		                 { "data": "ntGt200RwdCount" },
		                 { "data": "ntGt2000BetCount" },
		                 { "data": "ntGt2000RwdCount" },
		                 { "data": "ntGt20000BetCount" },
		                 { "data": "ntGt20000RwdCount" },
		                 { "data": "ntGt200000BetCount" },
		                 { "data": "ntGt200000RwdCount" },
		                 { "data": "gt200000BetCount" },
		                 { "data": "gt200000RwdCount" },
		      ],
		      
		     "oLanguage": commonData.oLanguage,
		     
		     "fnRowCallback":function(nRow,aData,iDataIndex){
					$('td:eq(2)',nRow).html(parseInt(aData.totalBet) - parseInt(aData.reward));
	          },
	          "dom": 't<"bottom fl"l><"bottom"p>'
			});
	}else{
		table.ajax.url("data/slotMacStats/getData.htm?stm="+sTime+"&etm="+eTime+"&plat="+platId+"&sid="+sid).load();
	}
});

</script>
</body>
</html>
