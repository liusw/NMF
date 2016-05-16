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
<title>登录金币分布</title>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/top.jsp">
		<jsp:param name="nav" value="data" />
	</jsp:include>

	<jsp:include page="/WEB-INF/jsp/common/dataMenu.jsp">
		<jsp:param name="nav" value="lc" />
		<jsp:param name="subnav" value="loginCoinsDistribute" />
	</jsp:include>
	<div class="content">
		<div class="main">
			<jsp:include page="/WEB-INF/jsp/common/mainNav.jsp">
				<jsp:param name="actionName" value="登录金币分布" />
				<jsp:param name="helpId" value="148" />
			</jsp:include>
			<div class="autoContent content-body">
				<br/>
				<table id="table" class="table table-striped table-bordered display" cellspacing="0" width="100%">
					<thead>
						<tr>
							<th align="center">日期</th>
							<th align="center">0金币</th>
							<th align="center">1~2000</th>
							<th align="center">2001~5000</th>
							<th align="center">5001~10000</th>
							<th align="center">10001~50000</th>
							<th align="center">50001~10w</th>
							<th align="center">100001~50w</th>
							<th align="center">50w~100w</th>
							<th align="center">100w~500w</th>
							<th align="center">500w~1000w</th>
							<th align="center">1000w~5000w</th>
							<th align="center">5000w以上</th>
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
	var sTime = navForm.daterange.getStartDate("#navdaterange");
	var eTime = navForm.daterange.getEndDate("#navdaterange");
	var sid = $("#navSid").val();
	
	if(isEmpty(sid)){
		alert("站点不能为空!");
		return false;
	}
	sid = sid.split("_")[0];
	loadTable("data/login/coinsDistribute.htm?stm="+sTime+"&etm="+eTime+"&plat="+plat+"&sid="+sid);
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
                 { "data": "coins0" },
                 { "data": "coins1" },
                 { "data": "coins2" },
                 { "data": "coins3" },
                 { "data": "coins4" },
                 { "data": "coins5" },
                 { "data": "coins6" },
                 { "data": "coins7" },
                 { "data": "coins8" },
                 { "data": "coins9" },
                 { "data": "coins10" },
                 { "data": "coins11" }
      ],
     "oLanguage": commonData.oLanguage,
     "dom": 't<"bottom fl"l><"bottom"p>'
	});
}
</script>
</body>
</html>
