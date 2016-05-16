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
.autoContent{
	overflow-x: auto; 
	padding: 10px;
}
table{width:100%;}
table td,table th{word-break: keep-all;white-space:nowrap;}
</style>
<title>活跃概况</title>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/top.jsp">
		<jsp:param name="nav" value="data" />
	</jsp:include>

		<jsp:include page="/WEB-INF/jsp/common/dataMenu.jsp">
		<jsp:param name="nav" value="lc" />
		<jsp:param name="subnav" value="activeView" />
	</jsp:include>
	<div class="content">
		<div class="main">
			<jsp:include page="/WEB-INF/jsp/common/mainNav.jsp">
				<jsp:param name="actionName" value="活跃概况" />
				<jsp:param name="helpId" value="128" />
			</jsp:include>
			<div class="content-body autoContent">
				<jsp:include page="/WEB-INF/jsp/common/tabMenu.jsp">
					<jsp:param name="tab" value="active" />
					<jsp:param name="subnav" value="activeView" />
				</jsp:include>
				<br/>
				<table id="activeView" class="table table-striped table-bordered display" cellspacing="0" width="100%">
					<thead>
						<tr>
							<th align="center">日期</th>
							<th align="center">星期</th>
							<th align="center">牌局人次</th>
							<th align="center">玩牌人数</th>
							<th align="center">没玩牌人数</th>
							<th align="center">玩牌率</th>
							<th align="center">平均在玩</th>
							<th align="center">最高在玩</th>
							<th align="center">平均在线</th>
							<th align="center">最高在线</th>
							<th align="center">日活跃</th>
							<th align="center">日活跃率</th>
							<th align="center">活跃玩牌</th>
							<th align="center">活跃玩牌率</th>
							<th align="center">活跃日环比</th>
							<th align="center">活跃周环比</th>
							<th align="center">日登录</th>
							<th align="center">日注册</th>
							<th align="center">注册玩牌</th>
							<th align="center">注册玩牌率</th>
							<th align="center">昨注回头</th>
							<th align="center">昨注回头率</th>
							<th align="center">三天注册回头</th>
							<th align="center">三天注册回头率</th>
							<th align="center">7天回头</th>
							<th align="center">7天回头率</th>
							<th align="center">15天回头</th>
							<th align="center">15天回头率</th>
							<th align="center">30天回头</th>
							<th align="center">30天回头率</th>
							<th align="center">注册日环比</th>
							<th align="center">注册周环比</th>
							<th align="center">最低在玩</th>
							<th align="center">最低在线</th>
							<th align="center">总注册</th>
							<th align="center">激活</th>
							<th align="center">激活玩牌</th>
							<th align="center">激活玩牌率</th>
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
	/* getPlat("#plats", "#sid",true);
	$("#plats").change(function() {
		getSid("#plats", "#sid", true);
	});
	initDatetimepicker();
	var date = new Date();
	date.addDays(-1);
	$("#stm").val(date.format('yyyy-MM-dd'));
	$("#etm").val(date.format('yyyy-MM-dd'));  */
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
	loadTable("active/activeView.htm?stm="+sTime+"&etm="+eTime+"&plat="+plat+"&sid="+sid);
});

function loadTable(url){
	$("#activeView").DataTable({
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
                 { "data": "gamblingNum"},
                 { "data": "playNum" },
                 { "data": "noplayNum" },
                 { "data": "playRate",
                	"render":function(data, type, row){
						return data+"%";
					}
                 },
                 { "data": "avgPlay" },
                 { "data": "maxPlay" },
                 { "data": "avgOnline" },
                 { "data": "maxOnline" },
                 { "data": "active" },
                 { "data": "activeRate",
                	"render":function(data, type, row){
						return data+"%";
					}
                 },
                 { "data": "activePlay" },
                 { "data": "activePlayRate",
                	"render":function(data, type, row){
						return data+"%";
					}
                 },
                 { "data": "activeDayRingRate",
                	"render":function(data, type, row){
						return (isNaN(data) ? -100 : (data-100)).toFixed(2)+"%";
					}
                 },
                 { "data": "activeWeekRingRate",
                	"render":function(data, type, row){
                		return (isNaN(data) ? -100 : (data-100)).toFixed(2)+"%";
					}
                 },
                 { "data": "loginNum" },
                 { "data": "registerNum" },
                 { "data": "registerPlay" },
                 { "data": "registerPlayRate",
                	"render":function(data, type, row){
						return data+"%";
					}
                 },
                 { "data": "yesterdayRegister" },
                 { "data": "yesterdayBackRate",
                	"render":function(data, type, row){
						return data+"%";
					}
                 },
                 { "data": "threeBack" },
                 { "data": "threeBackRate",
                	"render":function(data, type, row){
						return data+"%";
					}
                 },
                 { "data": "sevenBack" },
                 { "data": "sevenBackRate",
                	"render":function(data, type, row){
						return data+"%";
					}
                 },
                 { "data": "fiftyBack" },
                 { "data": "fiftyBackRate",
                	"render":function(data, type, row){
						return data+"%";
					}
                 },
                 { "data": "thirtyBack" },
                 { "data": "thirtyBackRate",
                	"render":function(data, type, row){
						return data+"%";
					}
                 },
                 { "data": "registerDayRingRate",
                	"render":function(data, type, row){
                		return (isNaN(data) ? -100 : (data-100)).toFixed(2)+"%";
					}
                 },
                 { "data": "registerWeekRingRate",
                	"render":function(data, type, row){
                		return (isNaN(data) ? -100 : (data-100)).toFixed(2)+"%";
					}
                 },
                 { "data": "minPlay" },
                 { "data": "minOnline" },
                 { "data": "totalRegister" },
                 { "data": "activatingNum" },
                 { "data": "activatingPlayNum" },
                 { "data": "activatingPlayRate",
                	"render":function(data, type, row){
						return data+"%";
					}
                 }
      ],
     "oLanguage": commonData.oLanguage,
     "dom": 't<"bottom fl"l><"bottom"p>',
	});
}
</script>
</body>
</html>
