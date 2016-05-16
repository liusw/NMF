<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en-us">
<head>
<%@ include file="/WEB-INF/jsp/common/meta.jsp"%>
<title>牌局-数据魔方</title>
<link rel="stylesheet" type="text/css" href="static/css/bootstrap-multiselect.css"/>
<style type="text/css">
#activeView thead  th,#activeView tbody td{
	text-align: center;
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
				<jsp:param name="subnav" value="gblType" />
			</jsp:include>
			
			<div id="myTabContent" class="tab-content">
				<div class="tab-pane fade content-form main-content active in">
						<div style="margin-bottom: 18px;">
							<select id="table-type" multiple="multiple">
								<option value="0" selected="selected">新手入门(0)</option>
								<option value="1">初级房间(1)</option>
								<option value="2">中级房间(2)</option>
								<option value="3">高级房间(3)</option>
								<option value="4">淘汰赛场(4)</option>
								<option value="6">快速房间(6)</option>
								<option value="7">晋级赛场(7)</option>
								<option value="8">淘金赛场(8)</option>
								<option value="11">锦标赛(11)</option>
								<option value="12">团队赛(12)</option>
								<option value="13">擂主赛(13)</option>
								<option value="14">单人AI对战场(14)</option>
								<option value="15">竞技赛(15)</option>
								<option value="17">闪电房(17)</option>
								<option value="18">周六夜赛(18)</option>
								<option value="19">必下桌(19)</option>
								<option value="20">红利赛/荷官场(20)</option>
								<option value="30">当面玩(移动)(30)</option>
								<option value="31">百万大奖赛(31)</option>
								<option value="32">TV淘汰赛(32)</option>
								<option value="34">巡回赛(34)</option>
								<option value="35">公会战海选赛(35)</option>
								<option value="36">公会战预选赛(36)</option>
								<option value="37">公会战决赛(37)</option>
								<option value="38">好牌场(38)</option>
								<option value="39">多人坐满即玩(39)</option>
								<option value="40">幸运转盘赛(40)</option>
							</select>
							<button type="button" class="btn btn-default" style="margin-left:6px;" id="submitBtn" data-loading-text="查询中...">查询</button>
						</div>
						<div style="min-height:238px;">
							<table id="example"	class="table table-striped table-bordered display" cellspacing="0" width="100%">
								<thead>
									<tr>
										<th align="center">桌子类型</th>
										<th align="center">日期</th>
										<th align="center">弃牌</th>
										<th align="center">杂牌</th>
										<th align="center">高牌</th>
										<th align="center">对子</th>
										<th align="center">两对</th>
										<th align="center">三条</th>
										<th align="center">顺子</th>
										<th align="center">同花</th>
										<th align="center">葫芦</th>
										<th align="center">四条</th>
										<th align="center">同花顺</th>
										<th align="center">皇家同花顺</th>
									</tr>
								</thead>
							</table>
						</div>
					</div>
					<div id="container" style="width:83%"></div> 
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
	<script src="static/js/bootstrap-multiselect.js"></script>
	<script type="text/javascript" src="static/Highcharts-4.0.4/js/highcharts.js"></script>
	<script type="text/javascript" src="static/Highcharts-4.0.4/js/modules/exporting.js"></script>
	<script type="text/javascript">
	var typeArr ={"0":"新手入门","1":"初级房间","2":"中级房间","3":"高级房间","4":"淘汰赛场","6":"快速房间","7":"晋级赛场","8":"淘金赛场","11":"锦标赛","12":"团队赛","13":"擂主赛","14":"单人AI对战场","15":"竞技赛","17":"闪电房","18":"周六夜赛","19":"必下桌","20":"红利赛\/荷官场","30":"当面玩(移动)","31":"百万大奖赛","32":"TV淘汰赛","34":"巡回赛","35":"公会战海选赛","36":"公会战预选赛","37":"公会战决赛","38":"好牌场","39":"多人坐满即玩","40":"幸运转盘赛"};
	$(function(){
		var std = new Date();
		std.addDays(-10);
		navForm.init({'showPlat':true,'showSid':true,'sidType':'Select','showDate':{'isShow':true,startDate:std.format('yyyy-MM-dd')},
			platChangeCallback: updateNavMultiSid
		});
		
		$('#table-type').multiselect({
	        includeSelectAllOption: true,
	        enableFiltering: true,
	        maxHeight: 190
	    });

		$("#chart-btn").click(function(event){
			event.stopPropagation();
			$("#table-div").hide();
			$("#container").show();
		});

		$("#table-btn").click(function(event){
			event.stopPropagation();
			$("#container").hide();
			$("#table-div").show();
		});
	});

	$('#submitBtn').click(function(){
		var platId = $("#navPlat").val();
		var sTime = navForm.daterange.getStartDate("#navdaterange");
		var eTime = navForm.daterange.getEndDate("#navdaterange");
		var sid = $("#navSid").val();
		
		var tableType = $("#table-type").val();

		if(isEmpty(tableType)){
			alert("桌子类型不能为空!");
			return false;
		}

		if(isEmpty(sid)){
			alert("站点不能为空!");
			return false;
		}
		
		var sid = sid.split("_")[0];
		
		if(undefined == table){
			var table = $("#example").DataTable({
			    "bFilter": true,
			    "pagingType" : "full_numbers",
			    "bDestroy" : true,
			    "bProcessing" : false,
			    "sAjaxSource" : "data/gambling/gblType.htm?stm="+sTime+"&etm="+eTime+"&plat="+platId+"&sid="+sid+"&tableType="+tableType,
			    "bServerSide" : true,
			    "ordering": true,
			    "searching":false,
			    "sServerMethod": "POST",
			    "columns": [
	     			{ "data": "tableType" },
	                { "data": "tm" },
	                { "data": "foldCount"},
	                { "data": "hodgeCount"},
	                { "data": "highCount" },
	                { "data": "pairCount" },
	                { "data": "twopairCount" },
	                { "data": "threeCount" },
	                { "data": "junkoCount" },
	                { "data": "flushCount" },
	                { "data": "fullHouseCount" },
	                { "data": "fourCount" },
	                { "data": "straightFlushCount" },
	                { "data": "royalFlushCount" },
		        ],

			    "columnDefs": [
		            {
	                "render": function ( data, type, row ) {
		             	var val = "未定义";
		             	return typeArr[data];
	                },
	               "targets": 0
			        },
	        	],

				"fnDrawCallback": function(oSettings) {
					var seriesData = [];

					$.each(oSettings.aoData,function(index,value){
						var serie = {};
						serie.name = typeArr[value._aData.tableType] + " " + value._aData.tm;
						serie.data = [];
						serie.data.push(value._aData.foldCount);
						serie.data.push(value._aData.hodgeCount);
						serie.data.push(value._aData.highCount);
						serie.data.push(value._aData.pairCount);
						serie.data.push(value._aData.twopairCount);
						serie.data.push(value._aData.threeCount);
						serie.data.push(value._aData.junkoCount);
						serie.data.push(value._aData.flushCount);
						serie.data.push(value._aData.fullHouseCount);
						serie.data.push(value._aData.fourCount);
						serie.data.push(value._aData.straightFlushCount);
						serie.data.push(value._aData.royalFlushCount);
						seriesData.push(serie);
					});

					$('#container').highcharts({
				        chart: {
				            type: 'column'
				        },
				        title: {
				            text: '牌型分布'
				        },
				        subtitle: {
				            text: ''
				        },
				        xAxis: {
				            categories: ['弃牌', '杂牌', '高牌', '对子', '两对', '三条','顺子', '同花', '葫芦', '四条', '同花顺', '皇家同花顺']
				        },
				        yAxis: {
				            min: 0,
				            title: {
				                text: '出现的总次数'
				            }
				        },
				        tooltip: {
			            	headerFormat: '<span style="font-size:10px;z-index:9999;">{point.key}</span><table>',
	            			pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
	                			'<td style="padding:0"><b>{point.y} 次</b></td></tr>',
	            			footerFormat: '</table>',
	            			shared: true,
	            			useHTML: true
				        },
				        plotOptions: {
				            column: {
				                pointPadding: 0.2,
				                borderWidth: 0
				            }
				        },
				        series: seriesData
				    });
				},

			    "oLanguage": commonData.oLanguage,
		        "dom": 't<"bottom fl"l><"bottom"p>'
			});
		}else{
			table.ajax.url("data/gambling/gblType.htm?stm="+sTime+"&etm="+eTime+"&plat="+platId+"&sid="+sid+"&tableType="+tableType).load();
		}
	});
</script>
</body>
</html>
