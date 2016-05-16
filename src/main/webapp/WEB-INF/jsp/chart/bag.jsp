<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>  
<!DOCTYPE html>
<html lang="en-us">
<head>
<%@ include file="/WEB-INF/jsp/common/meta.jsp"%>
<title>口袋德州实时在线-数据魔方</title>
</head>
<body>

	<jsp:include page="/WEB-INF/jsp/common/top.jsp">
       <jsp:param name="nav" value="data"/>
	</jsp:include>
	
	<jsp:include page="/WEB-INF/jsp/common/dataMenu.jsp">
		<jsp:param name="nav" value="chart" />
		<jsp:param name="subnav" value="bag" />
	</jsp:include>
	
	<div class="main">
		<jsp:include page="/WEB-INF/jsp/common/mainNav.jsp">
			<jsp:param name="actionName" value="口袋德州实时在线" />
		</jsp:include>
		
		<form class="form-inline" role="form">
				<div class="form-group ">
					<label>开始时间</label>
					<input type="text" id="stime" class="form-control time" readonly="readonly" placeholder="开始时间" autocomplete="off">
				</div>
				<div class="form-group ">
					<label>&nbsp;&nbsp;结束时间</label>
					<input type="text" id="etime" class="form-control time" readonly="readonly" placeholder="结束时间" autocomplete="off">
				</div>
				<div class="form-group">
					<label>&nbsp;&nbsp;图表显示方式</label>
					<select id="showType" name="showType" class="form-control">
						<option value="0">按分钟显示</option>
						<option value="1">按小时显示</option>
						<option value="2">按天显示</option>
					</select>
				</div>
				<div class="form-group">
					&nbsp;&nbsp;&nbsp;&nbsp;<button type="button" class="btn btn-default" id="submitBtn" data-loading-text="查询中...">查询</button>
				</div>
		</form>
		<div class="row">
			<br/>
			<div id="container" height="100%"></div>
		</div>
	</div>
	
	<br/><br/>
	<%@ include file="/WEB-INF/jsp/common/foot.jsp"%>

	<!-- Bootstrap core JavaScript navbar-fixed-bottom
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="static/js/basic.js"></script>
	<script src="static/js/datetimepicke.js"></script>
	<script src="static/js/common.js"></script>
	<script src="static/Highcharts-4.0.4/js/highcharts.js"></script>
	<script src="static/Highcharts-4.0.4/js/modules/exporting.js"></script>
	<script type="text/javascript">
	$(function() {
		$("#bagMenu").addClass("active");
		$(".time").datetimepicker({
			language : 'zh-CN',
			format: 'yyyy-mm-dd hh:ii',
			minuteStep:1,
			autoclose : true,
		});
		
		var data = getJsonData2("chart/initBag.htm");
		if(data!=null && data.status==1){
			$.each(data, function(index, value) {
				var startTime = value.startTime;
				var endTime = value.endTime;
				$("#stime").val(startTime);
				$("#etime").val(endTime);
				return;
			});
		}
		$('#submitBtn').click();
	});
	
	$("#showType").change(function(){
		if(!isEmpty($("#stime").val()) && !isEmpty($("#etime").val())){
			$('#submitBtn').click();
		}
	});
	
	$('#submitBtn').click(function(){
		var sTime = $("#stime").val();
		var eTime = $("#etime").val();
		
		if(isEmpty(sTime) || isEmpty(eTime)){
			alert("开始和结束时间不能为空!");
			return false;
		}
		
		$('#submitBtn').button("loading");
		var json = getJsonData2("chart/bagdata.htm",{
			startTime:sTime,
			endTime:eTime,
			showType:$('#showType').val()
		},true);
		
		var xAxis = [];
		var yAxis = [];
		if(json){
			$.each(json,function(key,obj){
				xAxis.push(obj.xaxis);
				yAxis.push(obj.yaxis);
			});
			
			$('#container').highcharts({
		        title: {
		            text: '口袋德州实时在线图',
		            x: -20 //center
		        },
		        
		      xAxis: {
		            categories: xAxis,
		            labels:{
		               // step:4
		            	  enabled: false
		            	}
		            	//tickPixelInterval: 1000
		            	//tickInterval:200
		        },
		      yAxis: {
		            title: {
		                text: '人数'
		            },
		            plotLines: [{
		                value: 0,
		                width: 1,
		                color: '#808080'
		            }]
		        },
		        tooltip: {
		            valueSuffix: '人'
		        },
		        legend: {
		            layout: 'vertical',
		            align: 'right',
		            verticalAlign: 'middle',
		            borderWidth: 0
		        },
		        series: [{
		            name: '时间',
		            data: yAxis
		        }]
		    });
		}
		$('#submitBtn').button("reset");
		$('[text-anchor=end]').hide();
	});
	</script>
</body>
</html>