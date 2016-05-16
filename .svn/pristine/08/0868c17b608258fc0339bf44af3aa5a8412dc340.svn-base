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
            		<li><a href="<%=(request.getContextPath())%>/data/cgCoinsTop.htm">金币排行</a></li>
            		<li class="active"><a href="<%=(request.getContextPath())%>/data/vmoneyTop.htm">台费排行</a></li>
            		<li><a href="<%=(request.getContextPath())%>/data/actCoins.htm">各渠道金流汇总</a></li>
          		</ul>
		    </div>
			<form class="form-inline" style="margin-top: 10px;">
				<div class="form-group">
					<label>&nbsp;</label>
					<select id="ismobile" name="ismobile" class="form-control">
						<option value="0">PC</option>
						<option value="1">移动</option>
					</select>
				</div>
				<div class="form-group">
					&nbsp;&nbsp;&nbsp;&nbsp;<button type="button" class="btn btn-default" id="submitBtn" data-loading-text="查询中...">查询</button>
					<button type="button" class="btn btn-default" id="export" data-loading-text="提交中..."  onclick="exportData();">导出</button>
				</div>
			</form>
			<table id="example" class="table table-striped table-bordered display dtable">
				<thead>
					<tr id="head_tr"></tr>
				</thead>
			</table>
		</div>
	</div>	

	<!-- Bootstrap core JavaScript navbar-fixed-bottom
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="static/js/basic.js"></script>
	<script src="static/js/dataTable.js"></script>
	<script src="static/js/daterangepicker.js"></script>
	<script src="static/js/daterange.by.js"></script>
	<script src="static/js/common.js"></script>
	<script src="static/js/vmoneyTop.js"></script>
	<script type="text/javascript">
	var navForm;;
	$(function(){
		navForm = navForm.init({'showPlat':true,'showDate':{'isShow':true}});
		
		initTopPlats(function(){
			$('#submitBtn').click();
		});
	});
	
	var columns=[];
	function getColumn(){
		columns = [];
		$("#head_tr").html('');
		$("#example").find("tbody").remove();
		$("#head_tr").append('<th>日期</th><th>mid</th><th>昵称</th><th>台费</th><th>当天储值($)</th>');
		columns.push({"data":"tm"});
		columns.push({"data":"_uid"});
		columns.push({"data":"mnick"});
		columns.push({"data":"total"});
		columns.push({"data":"pay"});
		
		$(vmoneyData).each(function(){
			if(this.plat==$("#navPlat").val()){
				$(this.value).each(function(){
					$("#head_tr").append('<th>'+this.vmoney+'台费</th><th>'+this.vmoney+'局数</th>');
					columns.push({"data":"s_"+this.vmoney});
					columns.push({"data":"c_"+this.vmoney});
				});
			}
		});
	}
	
	$('#submitBtn').click(function(){
		var sTime = navForm.daterange.getStartDate("#navdaterange","YYYYMMDD");
		var eTime = navForm.daterange.getEndDate("#navdaterange","YYYYMMDD");
		
		var plat = $("#navPlat").val();
		var ismobile = $("#ismobile").val();
		
		if(isEmpty(sTime) || isEmpty(eTime)){
			alert("开始和结束时间不能为空!");
			return false;
		}
		
		var params ={
			id:"10000001|10000002","dataType":"dataTable",
			args:{
				stm:sTime,etm:eTime,_plat:plat,ismobile:ismobile,table:"all_vmoney_top"
			}
		};
		loadTable("data/common/mysqlQuery.htm?params="+JSON.stringify(params));
 	});
	
	function loadTable(url){
		getColumn();
		
		$("#example").DataTable({
	     "bFilter": true,
	     "pagingType" : "full_numbers",
	     "bDestroy" : true,
	     "bProcessing" : false,
	     "sAjaxSource" : url,
	     "bServerSide" : true,
	     "ordering": false,
	     "searching":false,
	     "columns" : columns,
	     "oLanguage": commonData.oLanguage,
	     "dom": 't<"bottom fl"l><"bottom"p>'
		});
	}
	
	function exportData(){
		var sTime = navForm.daterange.getStartDate("#navdaterange","YYYYMMDD");
		var eTime = navForm.daterange.getEndDate("#navdaterange","YYYYMMDD");
		
		var plat = $("#navPlat").val();
		var ismobile = $("#ismobile").val();
		
		if(isEmpty(sTime) || isEmpty(eTime)){
			alert("开始和结束时间不能为空!");
			return false;
		}
		
		var params ={id:"10000001",args:{stm:sTime,etm:eTime,_plat:plat,ismobile:ismobile,table:"all_vmoney_top"}};
		
		var vcolumn='';
		$(vmoneyData).each(function(){
			if(this.plat==$("#navPlat").val()){
				vcolumn=JSON.stringify(this.value);
			}
		});
		window.location.href="data/vmoneyTop/exportData.htm?params="+JSON.stringify(params)+"&column="+vcolumn;
	}
	</script>
</body>
</html>