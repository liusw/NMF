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
<title>网关延迟-数据魔方</title>
<style type="text/css">
.ui-multiselect-filter input {
    height: 25px;
}
</style>
</head>
<body>

	<jsp:include page="/WEB-INF/jsp/common/top.jsp">
       <jsp:param name="nav" value="data"/>
	</jsp:include>
	
	<jsp:include page="/WEB-INF/jsp/common/dataMenu.jsp">
		<jsp:param name="nav" value="chart" />
		<jsp:param name="subnav" value="ccutime" />
	</jsp:include>
	
	<div class="main">
		<jsp:include page="/WEB-INF/jsp/common/mainNav.jsp">
			<jsp:param name="actionName" value="网关延迟" />
		</jsp:include>
		
		<form class="form">
			<div class="row">
				<div class="form-group col-xs-2" style="margin-top: 6px;">
					<select class="multiselect" multiple="multiple" id="reqMethod" name="reqMethod" class="form-control" style="margin-top: 6px;"></select>
				</div>
				<div class="form-group col-xs-3" style="margin-top: 6px;">
					<button type="button" class="btn btn-default" id="submitBtn" data-loading-text="查询中...">查询</button>
				</div>
			</div>
			<div class="row">
				<div class="col-xs-12" style="height: auto;" id="chartDiv"></div>
			</div>
			<div class="row">
				<div class="col-xs-12" style="height: auto;" id="chartDiv2"></div>
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
	var navForm;;
	$(function(){
		var std = new Date();
		var endTime = std.format('yyyyMMdd');
		std.addDays(-30);
		var startTime = std.format('yyyyMMdd');
		
		navForm = navForm.init({'showPlat':true,'showDate':{'isShow':true,'startDate':startTime,'endDate':endTime}});
		
		initTopPlats(function(){
			getMethod();
		});
	});
		
	$('#submitBtn').click(function() {
		getChart();
	});
	
	function getChart() {
			var methodValues = $("#reqMethod").attr("values");
			if (isEmpty(methodValues)) {
				alert("至少要选择一个方法名");
				return;
			}
			
			var methods = methodValues.split(",");
			if (methods.length > 20) {
				alert("最多可以选择20个方法名");
				return;
			}

			var sTime = DaterangeUtil.getStartDate("#navdaterange", "YYYYMMDD");
			var eTime = DaterangeUtil.getEndDate("#navdaterange", "YYYYMMDD");

			var xAxis = [];//x轴
			var yAxis = [];
			var objs = [];

			var yAxis1 = [];
			var objs1 = [];

			for ( var i = 0; i < methods.length; i++) {
				var params = {
					id : "10000001",
					args : {
						plat : $("#navPlat").val(),
						stm : sTime,
						etm : eTime,
						'method' : methods[i],
						table : "d_ccutime"
					}
				};
				var data = getJsonData("data/common/mysqlQuery.htm", {
					params : JSON.stringify(params)
				});
				if (data != null && data.result == 1 && data.loop
						&& data.loop.length > 0) {
					var datas = [];
					var labels = [];
					var datas1 = [];
					var labels1 = [];

					$.each(data.loop, function(key, obj) {
						var label = obj.tm;

						//判断x输有没有该坐标,如果有忽略,没有加进去
						if (!xAxis.contains(label)) {
							xAxis.push(label);
						}

						datas.push({
							"key" : label,
							"value" : obj.avgtime
						});
						datas1.push({
							"key" : label,
							"value" : obj.totalCount
						});

						labels.push(label);
						labels1.push(label);
					});
					objs.push({
						"method" : methods[i],
						"datas" : datas,
						labels : labels
					});
					objs1.push({
						"method" : methods[i],
						"datas" : datas1,
						labels : labels1
					});
				}
			}
			getChartData(xAxis, yAxis, objs, "#chartDiv", "ms", "平均时间");
			getChartData(xAxis, yAxis1, objs1, "#chartDiv2", "次数", "调用总数");
		}

		function getChartData(xAxis, yAxis, objs, dom, unit, title) {
			var series = [];
			if (xAxis && xAxis.length > 0) {
				$.each(xAxis, function(index, label) {
					$.each(objs, function(key, obj) {
						var labels = obj.labels;
						var newDatas = obj.newDatas;
						var datas = obj.datas;

						if (!newDatas) {
							newDatas = [];
						}
						if (!labels.contains(label)) {
							newDatas.push(0);
						} else {
							$.each(datas, function(i, o) {
								if (o.key == label) {
									newDatas.push(o.value);
									datas.remove(i);
									return false;
								}
							});
						}
						obj.newDatas = newDatas;
					});
				});
			}

			if (objs && objs.length > 0) {
				$.each(objs, function(key, obj) {
					yAxis.push({
						gridLineWidth : 0,
						title : {
							text : '',
							style : {
								color : Highcharts.getOptions().colors[key]
							}
						},
						labels : {
							format : '{value} (' + unit + ')',
							style : {
								color : Highcharts.getOptions().colors[key]
							}
						},
						opposite : true
					});
					series.push({
						name : obj.method,
						type : 'spline',
						yAxis : 0,
						data : obj.newDatas,
						tooltip : {
							valueSuffix : ' (' + unit + ')'
						}
					});
				});
			}
			$(dom)
					.highcharts(
							{
								chart : {
									zoomType : 'xy'
								},
								title : {
									text : title
								},
								xAxis : [ {
									categories : xAxis,
									labels : {
										enabled : false
									}
								} ],
								yAxis : yAxis,
								tooltip : {
									shared : true
								},
								legend : {
									layout : 'vertical',
									align : 'left',
									x : 80,
									verticalAlign : 'top',
									y : 55,
									floating : true,
									backgroundColor : (Highcharts.theme && Highcharts.theme.legendBackgroundColor)
											|| '#FFFFFF'
								},
								series : series
							});
		}

		function getMethod() {
			var params = {
				id : "10000001",
				args : {
					plat : $("#navPlat").val(),
					table : "d_ccutime_method"
				}
			};
			var data = getJsonData("data/common/mysqlQuery.htm", {
				params : JSON.stringify(params)
			});
			if (data != null && data.result == 1) {
				$.each(data.loop, function(index, value) {
					$("#reqMethod").append(
							"<option value='" + value.method +"'>"
									+ value.method + "</option>");
				})
				$("#reqMethod").mymultiselect({
					selectClose:function (val) {
						$('#reqMethod').attr({'values':val.getValues()});
			        }
				}).multiselectfilter();
			}
		}
	</script>
</body>
</html>
