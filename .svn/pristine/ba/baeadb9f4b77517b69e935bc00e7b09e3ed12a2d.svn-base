<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html lang="en-us">
<head>
<%@ include file="/WEB-INF/jsp/common/meta.jsp"%>
<title>充值统计-道具售出</title>
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
			<jsp:param name="tabActive" value="order" />
		</jsp:include>
		<jsp:include page="/WEB-INF/jsp/common/mainNav2.jsp" />
		
		<div class="main2Body">
			<jsp:include page="/WEB-INF/jsp/common/tabMenu.jsp">
				<jsp:param name="tab" value="pay" />
				<jsp:param name="subnav" value="payDistribute" />
			</jsp:include>
		
			<div id="container" height="100%"></div>
		</div>
	</div>
	<%@ include file="/WEB-INF/jsp/common/foot.jsp"%>

<!-- Bootstrap core JavaScript navbar-fixed-bottom
   ================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="static/js/basic.js"></script>
<script src="static/js/dataTable.js"></script>
<script src="static/js/multiselect.js"></script>
<script src="static/Highcharts-4.0.4/js/highcharts.js"></script>
<script src="static/Highcharts-4.0.4/js/modules/exporting.js"></script>
<script src="static/js/daterangepicker.js"></script>
<script src="static/js/daterange.by.js"></script>
<script src="static/js/common.js"></script>

<script type="text/javascript">

$(function(){
	navForm = navForm.init({'showPlat':true,'showSid':true,'sidType':'Select','showDate':{'isShow':true},
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
	sid = (sid + "").split("_")[0];
	$('#navSubmitBtn').button("loading");
	var json = getJsonData2("data/order/payDistribute.htm",{
		stm:sTime,
		etm:eTime,
		sid:sid,
		plat:plat
	},true);
	
	var xAxis = ['0美元','1美元','2美元','3美元','4美元','5美元','6美元','7美元','8美元','9美元','10美元','11-20美元','21-50美元','51-100美元','101-200美元','200美元以上'];
	var yAxis = [];
	var totalPersons = 0;
	var totalMoney = 0;
	if (json) {
		totalMoney = json.total;
		yAxis.push(json.money0);
		yAxis.push(json.money1);
		yAxis.push(json.money2);
		yAxis.push(json.money3);
		yAxis.push(json.money4);
		yAxis.push(json.money5);
		yAxis.push(json.money6);
		yAxis.push(json.money7);
		yAxis.push(json.money8);
		yAxis.push(json.money9);
		yAxis.push(json.money10);
		yAxis.push(json.money11_20);
		yAxis.push(json.money21_50);
		yAxis.push(json.money51_100);
		yAxis.push(json.money101_200);
		yAxis.push(json.money201);
		totalPersons = json.money0+json.money1+json.money2+json.money3+json.money4+json.money5+json.money6+json.money7+json.money8+json.money9
		+json.money10+json.money11_20+json.money21_50+json.money51_100+json.money101_200+json.money201
	}
	$('#container').highcharts({
		chart: { 
            defaultSeriesType: 'areaspline', //图表类别，可取值有：line、spline、area、areaspline、bar、column等 
        }, 
		title : {
			text : '付费分布图',
			x : -20
		//center
		},
		subtitle: {
		    text: '总人数:'+totalPersons+',总金额:'+totalMoney,
		    x : -20
		},
		xAxis : {
			categories : xAxis,
			labels : {
				// step:4
				enabled : false
			}
		},
		yAxis : {
			title : {
				text : '人数'
			},
			plotLines : [ {
				value : 0,
				width : 1,
				color : '#808080'
			} ]
		},
		tooltip : {
			valueSuffix : '人'
		},
		legend : {
			layout : 'vertical',
			align : 'right',
			verticalAlign : 'middle',
			borderWidth : 0
		},
		series : [ {
			name : '美元',
			data : yAxis
		} ]
	});
	$('#navSubmitBtn').button("reset");
	$('[text-anchor=end]').hide();
});
</script>
</body>
</html>
