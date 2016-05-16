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
<link rel="stylesheet" type="text/css" href="static/daterangepicker/daterangepicker-bs3.css">
<title>网络监控-数据魔方</title>
</head>
<body>

	<jsp:include page="/WEB-INF/jsp/common/top.jsp">
       <jsp:param name="nav" value="data"/>
	</jsp:include>
	
	<jsp:include page="/WEB-INF/jsp/common/dataMenu.jsp">
		<jsp:param name="nav" value="chart" />
		<jsp:param name="subnav" value="netStatus" />
	</jsp:include>
	
	<div class="main">
		<jsp:include page="/WEB-INF/jsp/common/mainNav.jsp">
			<jsp:param name="actionName" value="网络监控" />
		</jsp:include>
		
		<form class="form">
			<div class="row">
				<div class="form-group col-xs-2">
					<select id="serverIp" name="serverIp" class="form-control" style="margin-top: 6px;"></select>
				</div>
				<div class="form-group col-xs-2">
					<select id="addr" name="addr" class="form-control" style="margin-top: 6px;"></select>
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
				<div class="col-xs-12" style="height: auto;" id="chartDiv"></div>
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
	<script src="static/js/multiselect.js"></script>
	<script src="static/js/daterange.by.js"></script>
	<script src="static/js/common.js"></script>
	<script src="static/Highcharts-4.0.4/js/highcharts.js"></script>
	<script src="static/Highcharts-4.0.4/js/modules/exporting.js"></script>
	<script type="text/javascript">
		$(function() {
			var std = new Date();
			std.addHour(-1);
			var startTime = std.format('yyyy-MM-dd hh:mm:ss');
			var std2 = new Date();
			var endTime = std2.format('yyyy-MM-dd hh:mm:ss');
			
			createDateRgDom({'dom':'#daterange','isShow':true,'startDate':startTime,endDate:endTime,'timePicker':true,'format':'YYYY-MM-DD HH:mm:ss'});

			getServerIp();
			$("#serverIp").change(function(){changeIp();});
			$("#serverIp").change();
			getChart();
		});

		$('#submitBtn').click(function() {
			getChart();
		});

		function getChart() {
			var ipVals = $("#serverIp").val();
			var addrVals = $("#addr").val();
			var startTime = $('#daterange').data('daterangepicker').startDate.format('YYYY/MM/DD HH:mm:ss');
			startTime = new Date(startTime).getTime()/1000;
			var endTime = $('#daterange').data('daterangepicker').endDate.format('YYYY/MM/DD HH:mm:ss');
			endTime = new Date(endTime).getTime()/1000;
			
			
			var params = {
				id : "12000006",
				args : {
					ip : $("#serverIp").val(),addr:$("#addr").val(),table : "d_netstatus",sTime:startTime,eTime:endTime,sort:'dTime',order:'asc'
				}
			};
			$.post("data/common/mysqlQuery.htm",{params:JSON.stringify(params)},function(data, textStatus) {
				
				var xAxis = [];
				var yLoss = [];
				var yDelay = [];
				
				if(data && data.loop){
					$.each(data.loop,function(key,obj){
						xAxis.push(obj.dTime);
						yLoss.push(obj.loss);
						yDelay.push(obj.delay);
					});
				}
				
				$('#chartDiv').highcharts({
			      chart: {
			            zoomType: 'xy'
			        },
			       title: {
			            text: '网络监控'
			        },
			       xAxis: [{
			            categories:xAxis,
			            labels:{
			            	 enabled: false
			            	}
			        }],
			      yAxis: [{
						gridLineWidth: 0,
							title: {
							text: '丢包',
							style: {color: Highcharts.getOptions().colors[0]}
			            },
			         labels: {
							format: '{value} %',
							style: {color: Highcharts.getOptions().colors[0]}
			            }
			        }, {
			         gridLineWidth: 0,
			         title: {
			         	text: '延迟',
			            style: {color: Highcharts.getOptions().colors[1]}
			            },
			         labels: {
			             format: '{value} ms',
			             style: {color: Highcharts.getOptions().colors[1]}
			            },
			         opposite: true
			        }],
			        tooltip: {
			            shared: true
			        },
			        legend: {
			            layout: 'vertical',
			            align: 'left',
			            x: 80,
			            verticalAlign: 'top',
			            y: 55,
			            floating: true,
			            backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF'
			        },
			       series: [{
			            name: '丢包',
			            type: 'spline',
			            yAxis: 0,
			            data: yLoss,
			            marker: {enabled: false},
			            dashStyle: 'shortdot',
			            tooltip: {
			                valueSuffix: ' %'
			            	}
			        }, {
			            name: '延迟',
			            type: 'spline',
			            yAxis: 1,
			            data: yDelay,
			            tooltip: {
			                valueSuffix: ' (ms)'
			            }
			        }]
			    });
			},"json");
		}

		function getServerIp() {
			var params = {
				id : "10000001",
				args : {
					table : "d_netstatus_ip_addr",
					group : 'ip'
				}
			};
			var data = getJsonData("data/common/mysqlQuery.htm", {
				params : JSON.stringify(params)
			});

			if (data && data.loop && data.loop.length > 0) {
				$.each(data.loop,function(index, value) {
					$("#serverIp").append("<option value="+ value.ip+ (index == 0 ? " selected='selected'": "") + ">"+ value.ip+ "</option>");
				});
			}
		}
		function changeIp() {
			var params = {
				id : "10000001",
				args : {
					columns : "addr",ip : $("#serverIp").val(),table : "d_netstatus_ip_addr",group : 'addr'
				}
			};
			var data = getJsonData("data/common/mysqlQuery.htm", {
				params : JSON.stringify(params)
			});
			$("#addr").html('');
			if (data && data.loop && data.loop.length > 0) {
				$.each(data.loop,function(index, value) {
					$("#addr").append("<option value="+ value.addr+ (index == 0 ? " selected='selected'": "") + ">"	+ value.addr+ "</option>");
				});
			}
		}
	</script>
</body>
</html>
