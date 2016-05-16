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
<title>网络连接情况-数据魔方</title>
<link rel="stylesheet" type="text/css" href="static/daterangepicker/daterangepicker-bs3.css">
</head>
<body>

	<jsp:include page="/WEB-INF/jsp/common/top.jsp">
       <jsp:param name="nav" value="data"/>
	</jsp:include>
	
	<jsp:include page="/WEB-INF/jsp/common/dataMenu.jsp">
		<jsp:param name="nav" value="chart" />
		<jsp:param name="subnav" value="pingstalogs" />
	</jsp:include>
	
	<div class="main">
		<jsp:include page="/WEB-INF/jsp/common/mainNav.jsp">
			<jsp:param name="actionName" value="网络连接情况" />
		</jsp:include>
		
		<form class="form">
			<div class="row">
				<div class="form-group col-xs-3">
					<select id="serverIp" name="serverIp" class="form-control" style="margin-top: 6px;">
						<option value="">所有服务器</option>
					</select>
				</div>
				<div id="daterange" class="form-group col-xs-3 date-input" style="margin-top: 6px;max-height: 34px;">
					<i class="glyphicon glyphicon-calendar fa fa-calendar"></i>
					<span></span> 
					<b class="caret"></b>
				</div>
				<div class="form-group col-xs-3" style="margin-top: 6px;">
					<button type="button" class="btn btn-default" id="submitBtn" data-loading-text="查询中...">查询</button>
				</div>
			</div>
			<div class="row">
				<div class="form-group col-xs-12" id="chartDiv"></div>
			</div>
		</form>
	</div>
	
	
	<br/><br/>
	<%@ include file="/WEB-INF/jsp/common/foot.jsp"%>

	<!-- Bootstrap core JavaScript navbar-fixed-bottom
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="static/js/basic.js"></script>
	<script src="static/js/dateUtils.js"></script>
	<script src="static/js/daterangepicker.js"></script>
	<script src="static/js/daterange.by.js"></script>
	<script src="static/js/common.js"></script>
	<script src="static/Highcharts-4.0.4/js/highcharts.js"></script>
	<script src="static/Highcharts-4.0.4/js/modules/exporting.js"></script>
	<script type="text/javascript">
		$(function() {
		var std = new Date();
		std.addMinu(-60);
		var startTime = std.format('yyyy-MM-dd hh:mm:ss');
		std.addMinu(60);
		var endTime = std.format('yyyy-MM-dd hh:mm:ss');
		
		createDateRgDom({'dom':'#daterange','isShow':true,'startDate':startTime,'endDate':endTime,'timePicker':true,'format':'YYYY-MM-DD HH:mm:ss'});
		
		getServerIp();

		getData();
	});

	
	$('#submitBtn').click(function(){
		//$('#chartDiv').html(commonMsg.loading);
		$('#chartDiv').html("");
		getData();
	});

	function getData(ip){
		ip = ip ? ip : $("#serverIp").val();
		var sTime = DaterangeUtil.getStartDate("#daterange");
		var eTime = DaterangeUtil.getEndDate("#daterange");
		
		if(!sTime){
			alert("请填写开始时间!");
			return false;
		}
		if(!eTime){
			alert("请填写结束时间!");
			return false;
		}
		if(!ip){
			$("#serverIp option").each(function(){
				var val = $(this).val();
				if(val){
					getData(val);
				}
			});
			$(window).trigger("resize");//解决多个图表加载后，出现滚动条导致第一个图表的宽度不准确的问题
			return false;
		}
		
		$('#submitBtn').button("loading");
		var json = getJsonData("chart/getPingData.htm",{
			svip:ip,
			startTime:sTime,
			endTime:eTime,
			t:'pingstalogs_inc',
			page:1,
			pagesize:1500,
			rowkey:1
		},true);
		$('#submitBtn').button("reset");
		var labels=[];
		var data0 = [];
		var data1 = [];
		var data2 = [];
		
		if(json){
			$.each(json,function(key,obj){
				var tmp = obj.rowkey;
				labels.push(tmp.substr(12,4) + '/' + tmp.substr(16,2) + '/' + tmp.substr(18,2) + ' ' + tmp.substr(20));
				data0.push(parseInt(obj.c_sta));
				if(obj.c_sta_suc){
					data1.push(parseInt(obj.c_sta_suc));
				}else{
					data1.push(0);
				}
				if(obj.c_sta_fail){
					data2.push(parseInt(obj.c_sta_fail));
				}else{
					data2.push(0);
				}
				
			});
			
			var $charDiv = $("<div></div>");
			$charDiv.appendTo('#chartDiv');
			$charDiv.highcharts({
		        title: {
		        	useHTML: true,
		            text: '<small style="font-size:12px;">' + ip + '</small>'
		        },
		        xAxis: {
		            categories: labels,
		            labels:{
		            	enabled: false
	            	}
		        },
		        yAxis: {
		            title: {
		                text: '连接 (个)'
		            },
		            plotLines: [{
		                value: 0,
		                width: 1,
		                color: '#808080'
		            }]
		        },
		        tooltip: {
		            valueSuffix: ' 个',
		            shared: true,
		            useHTML: true,
		            headerFormat: '<table><tr><td>时间:</td><td>{point.key}</td></tr>',
		            pointFormat: '<tr><td style="color: {series.color}">{series.name}:</td>' +
		                '<td style="text-align: right"><b>{point.y}</b></td></tr>',
		            footerFormat: '</table>'
		        },
		        series: [{
		            name: '连接总数',
		            data: data0
		        },
		        {
		            name: '连接成功数',
		            data: data1
		        },
		        {
		            name: '连接失败数',
		            data: data2
		        }]
			});
		}
	}

	function getServerIp(){
		var data = getJsonData2("/chart/pingstalogIps.htm",{},true);

		if(data!=null){
			$.each(data, function(index, value) {
				$("#serverIp").append("<option value=" + value.svip + (index==0?" selected='selected'":"") + ">" + value.svip + "</option>");
			});
		}else{
			alert("不能获取到服务器ip!");
		}
	}
	</script>
</body>
</html>