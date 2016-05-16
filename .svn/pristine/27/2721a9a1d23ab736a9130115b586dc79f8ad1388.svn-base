<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html lang="en-us">
<head>
<%@ include file="/WEB-INF/jsp/common/meta.jsp"%>
<title>道具售出</title>
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
				<jsp:param name="tabActive" value="order" />
			</jsp:include>
			<jsp:include page="/WEB-INF/jsp/common/mainNav2.jsp" />

			<div class="main2Body">
				<jsp:include page="/WEB-INF/jsp/common/tabMenu.jsp">
					<jsp:param name="tab" value="pay" />
					<jsp:param name="subnav" value="toolSale" />
				</jsp:include>
			
				<table id="table" class="table table-striped table-bordered display" cellspacing="0" width="100%">
					<thead>
						<tr>
							<th align="center">日期</th>
							<th align="center">卡类型</th>
							<th align="center">售出数量</th>
							<th align="center">充值金额</th>
							<th align="center">获取游戏币</th>
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
	
	navForm = navForm.init({'showPlat':true,'showSid':true,'sidType':'MultiSelect','showDate':{'isShow':true,'startDate':std.toFormatDate()},
		platChangeCallback:updateNavMultiSid,'showBtn':true
	});
	
	$("#navSubmitBtn").trigger("click");
});

$('#navSubmitBtn').click(function(){
	var plat = $("#navPlat").val();
	var sTime = navForm.daterange.getStartDate("#navdaterange");
	var eTime = navForm.daterange.getEndDate("#navdaterange");
	
	var sid = $("#navMultiSid").val();
	
	if(isEmpty(sid)){
		alert("站点不能为空!");
		return false;
	}
	sid = (sid + "").split("_")[0];
	loadTable("data/order/toolSale.htm?stm="+sTime+"&etm="+eTime+"&plat="+plat+"&sid="+sid);
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
 					}
                 },
                 { "data": "pcard" ,
                	 "render":function(data, type, row){
                		 if(data==0){
                			 return '金币直充';
                		 }else if(data==1){
                			 return '铜牌会员';
                		 }else if(data==2){
                			 return '银牌会员';
                		 }else if(data==3){
                			 return '金牌会员';
                		 }else if(data==5){
                			 return '钻石会员';
                		 }else if(data==6){
                			 return '白金会员';
                		 }else if(data==7){
                			 return '精英会员';
                		 }else if(data==10){
                			 return '紫钻';
                		 }else if(data==12){
                			 return '蓝钻';
                		 }else if(data==13){
                			 return '红钻';
                		 }else if(data==15){
                			 return '黄钻';
                		 }else if(data==16){
                			 return '花';
                		 }else if(data==17){
                			 return '束花';
                		 }else if(data==18){
                			 return '月钻';
                		 }else if(data==19){
                			 return '月钻*2';
                		 }else if(data==9999){
                			 return '博雅币';
                		 }else{
                			 return 'unknown';
                		 }
 					}},
                 { "data": "num" },
                 { "data": "money" },
                 { "data": "chips" }
      ],
     "oLanguage": commonData.oLanguage,
     "dom": 't<"bottom fl"l><"bottom"p>',
	});
}
</script>
</body>
</html>
