<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en-us">
<head>
<%@ include file="/WEB-INF/jsp/common/meta.jsp"%>
<title>牌局-数据魔方</title>
<style type="text/css">
	.col-xs-3>label{
		display: block;
	}
</style>
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
			<jsp:param name="tabActive" value="gambling" />
		</jsp:include>
		<jsp:include page="/WEB-INF/jsp/common/mainNav2.jsp" />
		
		<div class="main2Body">
			<jsp:include page="/WEB-INF/jsp/common/tabMenu.jsp">
				<jsp:param name="tab" value="gambling" />
				<jsp:param name="subnav" value="gamblingTimeDistribute" />
			</jsp:include>
			
			<div id="myTabContent" class="tab-content">
				<div class="tab-pane fade content-form main-content active in">
					<table id="gamblingTimeDistribute" class="table table-striped table-bordered display" cellspacing="0" width="100%">
						<thead>
							<tr>
								<th align="center">日期</th>
								<th align="center">1小时以内</th>
								<th align="center">1-2小时</th>
								<th align="center">2-3小时</th>
								<th align="center">3-4小时</th>
								<th align="center">4-5小时</th>
								<th align="center">5-6小时</th>
								<th align="center">6-7小时</th>
								<th align="center">7-8小时</th>
								<th align="center">8-9小时</th>
								<th align="center">9-10小时</th>
								<th align="center">10-11小时</th>
								<th align="center">11-12小时</th>
								<th align="center">12小时以上</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="/WEB-INF/jsp/common/foot.jsp"%>

	<!-- Bootstrap core JavaScript navbar-fixed-bottom
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="static/js/basic.js"></script>
	<script src="static/js/datetimepicke.js"></script>
	<script src="static/js/multiselect.js"></script>
	<script src="static/js/daterangepicker.js"></script>
	<script src="static/js/daterange.by.js"></script>	
	<script src="static/js/dateUtils.js"></script>
	<script src="static/js/jquery.form.js"></script>
	<script src="static/js/dataTable.js"></script>
	<script src="static/js/common.js"></script>
	<script type="text/javascript">
	
	$(function(){
		var std = new Date();
		std.addDays(-10);
		navForm.init({'showPlat':true,'showSid':true,'sidType':'MultiSelect','showDate':{'isShow':true,startDate:std.format('yyyy-MM-dd')},showBtn:true,showExportBtn:true,
			platChangeCallback: updateNavMultiSid
		});
		$("#navSubmitBtn").click(function(){
        	showTimeDist();
        });
		$("#navExportBtn").click(function(){
        	exportTime();
        });
	});
	
	function showTimeDist(){
		var plat = $("#navPlat").val();
		var sTime = navForm.daterange.getStartDate("#navdaterange");
		var eTime = navForm.daterange.getEndDate("#navdaterange");
		var sid = $("#navMultiSid").val();
		
		if(!sid){
			alert("请选择站点");
			return;
		}
		
		sid = (sid + "").split("_")[0];
		loadTmDsbTable("data/gambling/timeDistribute.htm?stm="+sTime+"&etm="+eTime+"&plat="+plat+"&sid="+sid);
	}

	function loadTmDsbTable(url){
		$("#gamblingTimeDistribute").DataTable({
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
	                 { "data": "oneHourNum" },
	                 { "data": "twoHoursNum" },
	                 { "data": "threeHoursNum" },
	                 { "data": "fourHoursNum" },
	                 { "data": "fiveHoursNum" },
	                 { "data": "sixHoursNum" },
	                 { "data": "sevenHoursNum"},
	                 { "data": "eightHoursNum"},
	                 { "data": "nineHoursNum"},
	                 { "data": "tenHoursNum"},
	                 { "data": "elevenHoursNum"},
	                 { "data": "twelveHoursNum"},
	                 { "data": "greatTwelveNum"}
	      ],
	     "oLanguage": commonData.oLanguage,
	     "dom": 't<"bottom fl"l><"bottom"p>'
		});
	}
	
	function exportTime(){
		var plat = $("#navPlat").val();
		var sTime = navForm.daterange.getStartDate("#navdaterange");
		var eTime = navForm.daterange.getEndDate("#navdaterange");
		var sid = $("#navMultiSid").val();
		if(!sid){
			alert("请选择站点");
			return;
		}
		sid = (sid + "").split("_")[0];
		window.location.href = "data/gambling/exportTimeDistribute.htm?stm="+sTime+"&etm="+eTime+"&plat="+plat+"&sid="+sid;
	}
	</script>
</body>
</html>



























