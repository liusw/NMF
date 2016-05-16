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
<title>网络延迟-数据魔方</title>
</head>
<body>

	<jsp:include page="/WEB-INF/jsp/common/top.jsp">
       <jsp:param name="nav" value="data"/>
	</jsp:include>
	
	<jsp:include page="/WEB-INF/jsp/common/dataMenu.jsp">
		<jsp:param name="nav" value="chart" />
		<jsp:param name="subnav" value="pingLog" />
	</jsp:include>
	
	<div class="main">
		<jsp:include page="/WEB-INF/jsp/common/mainNav.jsp">
			<jsp:param name="actionName" value="网络延迟" />
		</jsp:include>
		
		<form class="form">
			<div class="row">
				<div class="form-group col-xs-2" style="margin-top: 6px;">
					<select class="multiselect" multiple="multiple" id="serverIp" name="serverIp" class="form-control" style="margin-top: 6px;"></select>
				</div>
				<div id="daterange" class="form-group col-xs-3 date-input" style="margin-top: 6px;max-height: 34px;">
					<i class="glyphicon glyphicon-calendar fa fa-calendar"></i>
					<span></span> 
					<b class="caret"></b>
				</div>
				<div class="form-group col-xs-3" style="margin-top: 6px;">
					<button type="button" class="btn btn-default" id="submitBtn" data-loading-text="查询中...">查询</button>
					<button type="button" class="btn btn-default" id="addTaskBtn" data-loading-text="提交中...">性能分析</button>
				</div>
			</div>
			<div class="row">
				<!-- 
				<div class="form-group col-xs-12" id="chartDiv"></div>
				 -->
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
			std.addMinu(-60);
			var startTime = std.format('yyyy-MM-dd hh:mm:ss');
			std.addMinu(60);
			var endTime = std.format('yyyy-MM-dd hh:mm:ss');
			
			createDateRgDom({'dom':'#daterange','isShow':true,'startDate':startTime,'endDate':endTime,'timePicker':true,'format':'YYYY-MM-DD HH:mm:ss'});

			getServerIp();

			getChart();
		});
		
		$('#addTaskBtn').click(function(){
			var ipVals = $("#serverIp").val();
			var sTime = DaterangeUtil.getStartDate('#daterange','YYYY/MM/DD HH:mm:ss');
			var eTime = DaterangeUtil.getEndDate('#daterange','YYYY/MM/DD HH:mm:ss');
			
			if(isEmpty(ipVals) || isEmpty(sTime) || isEmpty(eTime)){
				alert("至少要选择一个IP/日期");
				return;
			}
			
			var dsTime = new Date(Date.parse(sTime));
			var deTime = new Date(Date.parse(eTime));
			
			var _tmStrart = dsTime.getTime();
			var _tmEnd = deTime.getTime();
			var tmStrart = dsTime.format("yyyyMMdd");
			var tmEnd = deTime.format("yyyyMMdd");
			
			var ip="";
			for(var i=0;i<ipVals.length;i++){
				ip += "'"+ipVals[i] + "'"+(i==ipVals.length-1?"":",");
			}
			
			var title = 'ip,最小值,10%位置值,20%位置值,30%位置值,40%位置值,中位数值,平均值,60%位置值,70%位置值,80%位置值,90%位置值,最大值,<=300数量,300<N<=400数量,400<N<=500数量,N>=500数量';


			var sql = "select a.ip, b.zhongwei[0], b.zhongwei[1], b.zhongwei[2], b.zhongwei[3], b.zhongwei[4], b.zhongwei[5], b.pingjun, b.zhongwei[6], "
				+ "b.zhongwei[7], b.zhongwei[8], b.zhongwei[9], b.zhongwei[10], a.c_300, a.c_300_400, a.c_400_500, a.c_500 from "
				+ "(select ip, count(case when cast(nLoop as int)<=300 then 1 end) as c_300, "
				+ "count(case when cast(nLoop as int)>300 and cast(nLoop as int)<=400 then 1 end) as c_300_400, "
				+ "count(case when cast(nLoop as int)>400 and cast(nLoop as int)<=500 then 1 end) as c_400_500, "
				+ "count(case when cast(nLoop as int)>500 then 1 end) as c_500 from pinglog where tm between "+tmStrart+" and "+tmEnd+" and "
				+ "ip in("+ip+") and cast(`_tm` as bigint) between "+_tmStrart+" and "+_tmEnd+" group by ip)a left outer join"
				+ "(select ip, avg(cast(nLoop as bigint)) as pingjun, percentile(cast(nLoop as bigint),array(0.0,0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9,1.0)) as zhongwei from pinglog "
				+ "where tm between "+tmStrart+" and "+tmEnd+" and ip in("+ip+") "
				+ "and cast(`_tm` as bigint) between "+_tmStrart+" and "+_tmEnd+" group by ip)b on(a.ip=b.ip)";
		

			var taskJson = {
				taskName : tmStrart + "_" + tmEnd + "_网络性能分析",
				'process' : [ {
						'tmpId' : 'p0',
						'type' : '1',
						'operation' : sql,
						'columnName' : title,
						'title' : title
					} ]
				};
		
			$('#addtask').button("loading");
			$.post("task/process/addByJson.htm", {
				taskJson : JSON.stringify(taskJson)
			},
			function(data) {
				commonDo.addedTaskSuccess(data);
			},
			"json").error(function(){
				commonDo.addedTaskError();
			});
		});

		$('#submitBtn').click(function() {
			getChart();
		});

		function getChart() {
			var ipVals = $("#serverIp").val();
			if (isEmpty(ipVals)) {
				alert("至少要选择一个IP");
				return;
			}
			var sTime = DaterangeUtil.getStartDate("#daterange");
			var eTime = DaterangeUtil.getEndDate("#daterange");

			var xAxis = [];//x轴
			var yAxis = [];
			var series = [];

			var objs = [];

			for ( var i = 0; i < ipVals.length; i++) {

				var json = getJsonData("chart/getPingData.htm", {
					ip : ipVals[i],
					startTime : sTime,
					endTime : eTime,
					t : 'pinglog',
					page : 1,
					pagesize : 1500,
					rowkey : 1
				});

				if (json.ok == 1 && json.loop && json.loop.length > 0) {
					var datas = [];
					var labels = [];
					$.each(json.loop, function(key, obj) {
						var tmp = obj.rowkey;
						var label = tmp.substr(12, 4) + "/" + tmp.substr(16, 2)
								+ "/" + tmp.substr(18, 2) + " "
								+ tmp.substr(20, 2) + ":" + tmp.substr(22);

						//判断x输有没有该坐标,如果有忽略,没有加进去
						if (!xAxis.contains(label)) {
							xAxis.push(label);
						}

						var dTmp = obj.nLoop / obj.nCount;
						datas.push({
							"key" : label,
							"value" : parseInt(dTmp)
						});
						labels.push(label);
					});
					objs.push({
						"ip" : ipVals[i],
						"datas" : datas,
						labels : labels
					});
				}
			}

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
							text : obj.ip,
							style : {
								color : Highcharts.getOptions().colors[key]
							}
						},
						labels : {
							format : '{value} (ms)',
							style : {
								color : Highcharts.getOptions().colors[key]
							}
						},
						opposite : true
					});
					series.push({
						name : obj.ip,
						type : 'spline',
						yAxis : 0,
						data : obj.newDatas,
						tooltip : {
							valueSuffix : ' (ms)'
						}
					});
				});
			}

			$('#chartDiv')
					.highcharts(
							{
								chart : {
									zoomType : 'xy'
								},
								title : {
									text : ''
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

		function getServerIp() {
			var data = getJsonData2("/chart/pinglogIps.htm",{},true);

			if (data != null) {
				$
						.each(
								data,
								function(index, value) {
									$("#serverIp")
											.append(
													"<option value="
															+ value.ip
															+ (index == 0 ? " selected='selected'"
																	: "") + ">"
															+ value.ip
															+ "</option>");
								});
				$("#serverIp").mymultiselect();
			} else {
				alert("不能获取到服务器ip!");
			}
		}
	</script>
</body>
</html>
