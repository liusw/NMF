<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html lang="en-us">
<head>
<%@ include file="/WEB-INF/jsp/common/meta.jsp"%>
<style type="text/css">
#example thead  th,#example tbody td{
	text-align: center;
}
.autoContent{
	overflow-x: auto; 
	padding: 10px;
}
table td,table th{word-break: keep-all;white-space:nowrap;}
</style>
<title>玩偶兑奖</title>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/top.jsp">
        <jsp:param name="nav" value="data"/>
        <jsp:param name="showPlatSelect" value="false" />
	</jsp:include>
	
	<jsp:include page="/WEB-INF/jsp/common/dataMenu.jsp">
		<jsp:param name="nav" value="businessData" />
		<jsp:param name="subnav" value="dollTicket" />
	</jsp:include>
	
		<div class="main">
			<jsp:include page="/WEB-INF/jsp/common/mainNav.jsp">
				<jsp:param name="actionName" value="玩偶兑奖" />
				<jsp:param name="helpId" value="103" />
			</jsp:include>
			
			<div class="content-body autoContent">
				<table id="example" class="table table-striped table-bordered display"  cellspacing="0" width="100%">
					<thead>
						<tr id="head_tr"></tr>
					</thead>
				</table>
			</div>
		</div>
		<%@ include file="/WEB-INF/jsp/common/foot.jsp"%>

<!-- Bootstrap core JavaScript navbar-fixed-bottom
   ================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="static/js/basic.js"></script>
<script src="static/js/dataTable.js"></script>
<script src="static/js/multiselect.js"></script>
<script src="static/js/daterangepicker.js"></script>
<script src="static/js/daterange.by.js"></script>

<script src="static/js/common.js"></script>
<script type="text/javascript">
$(function(){
	navForm = navForm.init({'showPlat':true,'showSid':true,'sidType':'MultiSelect','showDate':{'isShow':true,'singleDatePicker':true},
		platChangeCallback:updateNavMultiSid,'showBtn':true,'showExportBtn':true
	});
	
	commonData.oLanguage.sInfo = "";
	commonData.oLanguage.sInfoEmpty = "";
	
	$("#navSubmitBtn").trigger("click");
});

var columns=[];
function getColumn(){
	columns = [];
	$("#head_tr").html('');
	$("#example").find("tbody").remove();
	$("#head_tr").append('<th>站点</th><th>日期</th>');

	columns.push({"data":"sname"});
	columns.push({"data":"tm"});
	
	for(var i=1;i<=40;i++){
		$("#head_tr").append('<th>玩偶' + i +'兑奖次数</th><th>玩偶' + i +'兑奖人数</th><th>玩偶' + i +'兑奖总数</th>');
		columns.push({"data":"clid" + i + "_count"});
		columns.push({"data":"clid" + i + "_persons"});
		columns.push({"data":"clid" + i + "_amount"});
	}
}

$('#navSubmitBtn').click(function(){
	var bpid = $("#navMultiSid").attr("selectbpidvalues");
	var sTime = navForm.date.getStartDate("#navdate");
	
	if(isEmpty(bpid)){
		alert("站点不能为空!");
		return false;
	}
		
	loadTable("data/dollTicket/getDatas.htm?bpid="+bpid+"&tm="+sTime);
});

function loadTable(url){
	getColumn();
		
	$("#example").DataTable({
     "bFilter": true,
     "sServerMethod": "POST",
     "pagingType" : "full_numbers",
     "bDestroy" : true,
     "bProcessing" : false,
     "sAjaxSource" : url,
     "bServerSide" : true,
     "ordering": false,
     "searching":false,
     "columns" : columns,
     "oLanguage": commonData.oLanguage,
     "dom": 't<"bottom fl"l><"bottom"p>',
	});
}

$("#navExportBtn").bind("click",function exportData() {
	var bpid = $("#navMultiSid").attr("selectbpidvalues");
	var sTime = navForm.date.getStartDate("#navdate");
	
	if(isEmpty(bpid)){
		alert("站点不能为空!");
		return false;
	}
	
	if(isEmpty(sTime)){
		alert("日期不能为空!");
		return false;
	}
	
	window.location.href = "data/dollTicket/exportData.htm?bpid=" + bpid + "&tm=" + sTime;
});
</script>
</body>
</html>
