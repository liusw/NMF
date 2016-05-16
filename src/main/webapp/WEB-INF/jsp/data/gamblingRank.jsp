<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en-us">
<head>
<%@ include file="/WEB-INF/jsp/common/meta.jsp"%>
<title>牌局-数据魔方</title>
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
				<jsp:param name="subnav" value="gamblingRank" />
			</jsp:include>
			
			<div id="myTabContent" class="tab-content">
				<div class="tab-pane fade content-form main-content active in">
					<table id="table" class="table table-striped table-bordered display" cellspacing="0" width="100%">
						<thead>
							<tr>
								<th align="center">玩家ID</th>
								<th align="center">总牌局数/非法牌局数</th>
								<th align="center">输筹码总量</th>
								<th align="center">赢筹码总量</th>
								<th align="center">上周总牌局数/上周非法牌局数</th>
								<th align="center">上周数筹码数</th>
								<th align="center">上周赢筹码数</th>
								<th align="center">上月总牌局数/上月非法牌局数</th>
								<th align="center">上月输筹码数</th>
								<th align="center">上月赢筹码数</th>
								<th align="center">IP</th>
								<th align="center">非法牌局数</th>
								<th align="center">上周非法牌局数</th>
								<th align="center">上月非法牌局数</th>
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
		navForm.init({'showDate':{'isShow':true,singleDatePicker:true},showBtn:true,showExportBtn:true
		});
    	
		$("#navSubmitBtn").click(function(){
        	showGmbRank();
        });
		
		$("#navExportBtn").click(function(){
        	exportRank();
        });;
	});
	
	function showGmbRank(){
		var tm = navForm.date.getStartDate("#navdate");
		loadGmbTable("data/gambling/ranks.htm?tm="+tm);
	};

	function loadGmbTable(url){
		$("#table").DataTable({
	     "bFilter": true,
	     "pagingType" : "full_numbers",
	     "bDestroy" : true,
	     "bProcessing" : false,
	     "sAjaxSource" : url,
	     "bServerSide" : true,
	     "aaSorting": [[ 2, "asc" ]],
	     "ordering": true,
	     "searching":false,
	     "columns": [
	                 { "data": "uid" },
	                 { "data": "yesterdayGamblings" ,
	                	 "render":function(data, type, row){
	                		 data = data+"/<span style='color: red;'>"+row.yesterdayIllegalGamblings+"</span>";
	     					return data;
	     			   } 
	                 },
	                 { "data": "yesterdayLoseChips" },
	                 { "data": "yesterdayWinChips" },
	                 { "data": "lastWeekGamblings" ,
	                	 "render":function(data, type, row){
	                		 data = data+"/<span style='color: red;'>"+row.lastWeekIllegalGamblings+"</span>";
	     					return data;
	     			   }	 
	                 },
	                 { "data": "lastWeekLoseChips"},
	                 { "data": "lastWeekWinChips"},
	                 { "data": "lastMonthGamblings" ,
	                	 "render":function(data, type, row){
	                		 data = data+"/<span style='color: red;'>"+row.lastMonthIllegalGamblings+"</span>";
	     					return data;
	     			   }	 
	                 },
	                 { "data": "lastMonthLoseChips"},
	                 { "data": "lastMonthWinChips"},
	                 { "data": "ipStr"},
	                 { "data": "yesterdayIllegalGamblings", "visible": false},
	                 { "data": "lastWeekIllegalGamblings" , "visible": false},
	                 { "data": "lastMonthIllegalGamblings" , "visible": false}
	      ],
	     "oLanguage": commonData.oLanguage,
	     "dom": 't<"bottom fl"l><"bottom"p>'
		});
		
	}

	function exportRank(){
		var tm = navForm.daterange.getStartDate();
		window.location.href = "data/gambling/exportRanks.htm?tm="+tm;
	}
</script>
</body>
</html>