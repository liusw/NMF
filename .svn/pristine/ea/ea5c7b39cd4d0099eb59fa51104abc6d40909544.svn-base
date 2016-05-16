<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en-us">
<head>
<%@ include file="/WEB-INF/jsp/common/meta.jsp"%>
<title>金币流水－数据魔方</title>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/top.jsp">
        <jsp:param name="nav" value="data"/>
	</jsp:include>
	
	<jsp:include page="/WEB-INF/jsp/common/dataMenu.jsp">
		<jsp:param name="nav" value="businessData" />
		<jsp:param name="subnav" value="tableData" />
	</jsp:include>
	<div class="main2">
		<jsp:include page="/WEB-INF/jsp/common/topTabMenu.jsp">
			<jsp:param name="tabActive" value="coinsFlow" />
		</jsp:include>
		
		<jsp:include page="/WEB-INF/jsp/common/mainNav2.jsp" />
		
		<div class="main2Body">
	    	<div id="sub-nav">
          		<ul id="nav-tab" class="nav nav-tabs">
            		<li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" href="javascript:void(0);">查询<b class="caret"></b></a>
            			<ul id="query-by" class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu">
		                  <li><a href="<%=(request.getContextPath())%>/data/gamecoins.htm?type=by-user" >按用户</a></li>
		                  <li><a href="<%=(request.getContextPath())%>/data/gamecoins.htm?type=by-day">按天</a></li>
		                  <li><a href="<%=(request.getContextPath())%>/data/gamecoins.htm?type=by-day-use">按天和用户</a></li>
		                </ul>
            		</li>
            		<li class="active"><a href="<%=(request.getContextPath())%>/data/cgCoinsTop.htm">金币排行</a></li>
            		<li><a href="<%=(request.getContextPath())%>/data/vmoneyTop.htm">台费排行</a></li>
            		<li><a href="<%=(request.getContextPath())%>/data/actCoins.htm">各渠道金流汇总</a></li>
          		</ul>
		    </div>
			<table id="example" class="table table-striped table-bordered display"  cellspacing="0" width="100%">
				<thead>
					<tr id="head_tr">
						<th>排名</th><th>日期</th><th>用户ID</th><th>昵称</th><th>赢钱总数</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>	

	<!-- Bootstrap core JavaScript navbar-fixed-bottom
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="static/js/basic.js"></script>
	<script src="static/js/datetimepicke.js"></script>
	<script src="static/js/dataTable.js"></script>
	<script src="static/js/multiselect.js"></script>
	<script src="static/js/daterangepicker.js"></script>
	<script src="static/js/daterange.by.js"></script>	
	
	<script src="static/js/common.js"></script>
	<script type="text/javascript">
	$(function(){
		navForm.init({'showPlat':true,'showSid':true,'sidType':'Select','showDate':{'isShow':true,'singleDatePicker':true},
			platChangeCallback: updateNavMultiSid,'showBtn':true,'showExportBtn':true
		});
		
		$("#navSubmitBtn").trigger("click");
	});
	
	var columns=[];
	function getColumn(){
		columns = [];
 		$("#head_tr").html('');
		$("#example").find("tbody").remove();
 		$("#head_tr").append('<th>排名</th><th>日期</th><th>用户ID</th><th>昵称</th><th>赢钱总数</th>');
		columns.push({"data":null});
		columns.push({"data":"tm"});
		columns.push({"data":"uid"});
		columns.push({"data":"mnick"});
		columns.push({"data":"cgCoinsTotal"});
		
		$([{blindmin:150},{blindmin:50},{blindmin:25},{blindmin:5},{blindmin:2},{blindmin:1}]).each(function(){
			$("#head_tr").append('<th>'+this.blindmin+'W局数</th><th>'+this.blindmin+'W赢钱数</th>');
			columns.push({"data":"bgCount_"+this.blindmin});
			columns.push({"data":"cgCoins_"+this.blindmin});
		});
	}
	
	$('#navSubmitBtn').click(function(){
		var plat = $("#navPlat").val();
		var sid = $("#navSid").val();
		var sTime = navForm.date.getStartDate("#navdate");
		
		if(isEmpty(sid)){
			alert("站点不能为空!");
			return false;
		}
		sid = (sid + "").split("_")[0];
		
		loadTable("data/cgCoinsTop/getDatas.htm?startTime="+sTime+"&plat="+plat+"&sid="+sid);
	});
	
	function getIndex(index){
		var info = $('#example').DataTable().page.info();
		return (parseInt(info.page)*parseInt(info.length))+index;
	}
	
	function loadTable(url){
		getColumn();
		
		$("#example").DataTable({
	     "bFilter": true,
	     "pagingType" : "full_numbers",
	     "bDestroy" : true,
	     "bProcessing" : false,
	     "sAjaxSource" : url,
	     "bServerSide" : true,
	     "ordering": true,
	     "searching":false,
	     "columns" : columns,
	     "oLanguage": commonData.oLanguage,
	     "dom": 't<"bottom fl"l><"bottom"p>',
	     "fnRowCallback":function(nRow,aData,iDataIndex){
				$('td:eq(0)',nRow).html(getIndex(parseInt(iDataIndex)+1));
            }
		});
	}

	$("#navExportBtn").click(function(){
		var plat = $("#navPlat").val();
		var sid = $("#navSid").val();
		var sTime = navForm.date.getStartDate("#navdate");
		
		if(isEmpty(sid)){
			alert("站点不能为空!");
			return false;
		}
		
		sid = (sid + "").split("_")[0];
		
		var vcolumn = JSON.stringify([{blindmin:150},{blindmin:50},{blindmin:25},{blindmin:5},{blindmin:2},{blindmin:1}]);
		window.location.href = "data/cgCoinsTop/exportData.htm?startTime="
				+ sTime + "&plat="+plat+"&sid="+sid + "&column=" + vcolumn;
	});
	</script>
</body>
</html>