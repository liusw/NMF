<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en-us">
<head>
<%@ include file="/WEB-INF/jsp/common/meta.jsp"%>
<title>每日统计-数据魔方</title>
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
			<jsp:param name="tabActive" value="dailyData" />
		</jsp:include>
		
		<jsp:include page="/WEB-INF/jsp/common/mainNav2.jsp" />
		
		<div class="main2Body"> 
				<table id="example" class="table table-striped table-bordered display dtable">
					<thead>
						<tr>
							<th>日期</th><th>日活跃</th><th>日注册</th><th>日付费</th><th>日付费人数</th><th>昨注回流</th><th>3天回流</th><th>7天回流</th><th>玩牌人数</th>
						</tr>
					</thead>
					<tfoot></tfoot>
				</table>
				
			
			<div class="widget-box">
				<div class="widget-content nopadding">
					<div style="width: 100%;height: auto;" id="mfcountChart"></div>
				</div>
			</div>
			
			<div class="widget-box">
				<div class="widget-content nopadding">
					<div style="width: 100%;height: auto;" id="browserDataChart"></div>
				</div>
			</div>
		</div>
	</div>

	<%@ include file="/WEB-INF/jsp/common/foot.jsp"%>

	<!-- Bootstrap core JavaScript navbar-fixed-bottom
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="static/js/basic.js"></script>
	<script src="static/js/common.js"></script>
	<script src="static/js/dataTable.js"></script>
	<script src="static/js/dateUtils.js"></script>
	<script src="static/js/daterangepicker.js"></script>
	<script src="static/js/daterange.by.js"></script>
	<script src="static/Highcharts-4.0.4/js/highcharts.js"></script>
	<script src="static/Highcharts-4.0.4/js/modules/exporting.js"></script>
	<script type="text/javascript">
	var table,navForm;
	$(document).ready(function() {
		var std = new Date();
		navForm = navForm.init({'showPlat':true,'showSid':true,'sidType':'Select','showDate':{'isShow':true,applyClickCallBack:loadData},platChangeCallback:loadData});
		
		$("#navPlat").change(function(){
			loadData();
		});
		$("#navSid").change(function(){
			loadData();
		});
		loadData();
		
		var data = [{
			v:'activeCount',
			name : '#tm#_日活跃',
			title : '_plat,bpid,_uid',
			sql:"select plat,`_bpid`,`_uid` from user_uid where tm=#tm# and plat=#plat# and `_bpid`='#bpid#'"
		},{
			v:'regCount',
			name : '#tm#_日注册',
			title : '_plat,bpid,_uid',
			sql:"select `_plat`,`_bpid`,`_uid` from user_signup where tm=#tm# and `_plat`=#plat# and `_bpid`='#bpid#'"
		},{
			v:'paySum',
			name : '#tm#_日付费',
			title : '_plat,bpid,_uid,付费金额',
			sql:"select plat,`_bpid`,`_uid`,cast(pamount as double)*cast(prate as double) paySum from user_order2 where tm=#tm# and `_plat`=#plat# and `_bpid`='#bpid#'"
		},{
			v:'loginBack1',
			name : '#tm#_昨注回流',
			title : '_plat,bpid,_uid',
			sql:"select `_plat`,`_bpid`,`_uid` from tmp_nu_back where tm=#tm# and `_plat`=#plat# and `_bpid`='#bpid#'"
		},{
			v:'loginBack3',
			name : '#tm#_3天回流',
			title : '_plat,bpid,_uid',
			sql:"select `_plat`,`_bpid`,`_uid` from tmp_login_3back where tm=#tm# and `_plat`=#plat# and `_bpid`='#bpid#'"
		},{
			v:'loginBack7',
			name : '#tm#_7天回流',
			title : 'plat,bpid,_uid',
			sql:"select `_bpid`,`_plat`,`_uid` from tmp_login_7back where tm=#tm# and `_plat`=#plat# and `_bpid`='#bpid#'"
		},{
			v:'playGameCount_dz',
			name : '#tm#_玩牌人数',
			title : 'plat,bpid,_uid',
			sql:"select game_meta.`_plat`,game_meta.`_bpid`,`_uid` from user_gambling join game_meta on user_gambling.sid = game_meta.sid where user_gambling.tm=#tm# and plat=#plat# and game_meta.`_bpid`='#bpid#'"
		},{
			v:'playGameCount_ipk',
			name : 'ipk_#tm#_玩牌人数',
			title : 'plat,bpid,_uid',
			sql:"select game_meta.`_plat`,game_meta.`_bpid`,`_uid` from ipk_user_gambling x join  game_meta on x.sid = game_meta.sid where x.tm=#tm# and plat=#plat# and game_meta.`_bpid`='#bpid#' group by game_meta.`_plat`,game_meta.`_bpid`,`_uid`"
		} ];
		
		$("#example").click(function(e){
			if($(e.target).is(".glyphicon")){
				var v = $(e.target).attr('v');
				$(data).each(function(){
					if(v==this.v || ($("#navPlat").val()<600 && (v+"_ipk"==this.v)) || ($("#navPlat").val()>600 && (v+"_dz"==this.v))){
						return addTask(e.target,this);
					}
				});
			};
		});
	});
	
	function loadData(){
		getTable();
		createChart();
		browserDataChart();
	}
	
	function addTask(o,d){
		var tr = $(o).closest('tr');
		var row = table.row(tr);
		
		var msg = "你确定要添加任务导出《"+d.name.replace(/#tm#/g, row.data().tm)+"》";   
		if (!confirm(msg)){
			return;
		}
		
		var sid = $("#navSid").val();
		var bpid = sid.split("_")[1];
		
		var title = d.title;
		var sql = d.sql.replace(/#tm#/g, row.data().tm).replace(/#plat#/g,$("#navPlat").val()).replace(/#bpid#/g,bpid);
		var taskJson = {
			taskName :d.name.replace(/#tm#/g, row.data().tm),
			'process' : [ {
					'tmpId' : 'p0',
					'type' : '1',
					'operation' : sql,
					'columnName' : title,
					'title' : title
				} ]
		};
		$.post("task/process/addByJson.htm", {
			taskJson : JSON.stringify(taskJson)
		},
		function(data) {
			commonDo.addedTaskSuccess(data);
		},
		"json").error(function(){
			commonDo.addedTaskError();
		});
	};
	
	function getTable(){
		var start = navForm.daterange.getStartDate("#navdaterange","YYYYMMDD");
		var end = navForm.daterange.getEndDate("#navdaterange","YYYYMMDD");
		var plat = $("#navPlat").val();
		var sid = $("#navSid").val();
		var bpid = sid.split("_")[1];
		
		var params ={
			id:"10000001|10000002","dataType":"dataTable",
			args:{
				plat:plat,etm:end,stm:start,bpid:bpid,table:"d_day_data"
			}
		};
		
		table = $("#example").DataTable({
		     "bFilter": true,
		     "pagingType" : "full_numbers",
		     "bDestroy" : true,
		     "bProcessing" : false,
		     "sAjaxSource" : "data/common/mysqlQuery.htm?params="+JSON.stringify(params),
		     "bServerSide" : true,
		     "ordering": false,
		     "aaSorting": [[ 0, "desc" ]],
		     "aoColumnDefs": [ { "bSortable": false}],
		     "sServerMethod": "POST",
		     "columns" : [
					{ "data": "tm"},
					{ 
						"data": "activeCount",
						"render":function(data, type, row){
							return row.activeCount+'<span v="activeCount" style="float:right;cursor: pointer;" class="glyphicon glyphicon-download" aria-hidden="true"></span>';
						}
					},
					{
						"data": "regCount",
						"render":function(data, type, row){
							return row.regCount+'<span v="regCount" style="float:right;cursor: pointer;" class="glyphicon glyphicon-download" aria-hidden="true"></span>';
						}
					},
					{
						"data": "paySum",
						"render":function(data, type, row){
							return row.paySum+'<span v="paySum" style="float:right;cursor: pointer;" class="glyphicon glyphicon-download" aria-hidden="true"></span>';
						}
					},
					{
						"data": "payCount"
					},
					{
						"data": "loginBack1",
						"render":function(data, type, row){
							return row.loginBack1+'<span v="loginBack1" style="float:right;cursor: pointer;" class="glyphicon glyphicon-download" aria-hidden="true"></span>';
						}
					},
					{
						"data": "loginBack3",
						"render":function(data, type, row){
							return row.loginBack3+'<span v="loginBack3" style="float:right;cursor: pointer;" class="glyphicon glyphicon-download" aria-hidden="true"></span>';
						}
					},
					{
						"data": "loginBack7",
						"render":function(data, type, row){
							return row.loginBack7+'<span v="loginBack7" style="float:right;cursor: pointer;" class="glyphicon glyphicon-download" aria-hidden="true"></span>';
						}
					},
					{
						"data": "playGameCount",
						"render":function(data, type, row){
							return row.playGameCount+'<span v="playGameCount" style="float:right;cursor: pointer;" class="glyphicon glyphicon-download" aria-hidden="true"></span>';
						}
					}
		         ],
		       "oLanguage": commonData.oLanguage,
		       "dom": 't<"bottom fl"l><"bottom"p>'
			});
	};
	
	function createChart(){
		var start = navForm.daterange.getStartDate("#navdaterange","YYYYMMDD");
		var end = navForm.daterange.getEndDate("#navdaterange","YYYYMMDD");
		
		var params ={id:"12000010",args:{stm:start,etm:end,plat:$("#navPlat").val(),type:1}};
		$.post("data/common/mysqlQuery.htm",{params:JSON.stringify(params)},function(data, textStatus) {
			var xAxis = [];
			var yAxis = [];
			var zeroMfcount = '';
			if(data && data.loop){
				$.each(data.loop,function(key,obj){
					if(obj.dCount==0){
						zeroMfcount='没有好友的用户数为:'+obj.userCount;
					}else{
						yAxis.push(obj.userCount);
						xAxis.push(obj.dCount);
					}
				});
			}
			
			$('#mfcountChart').highcharts({
				title: {
					text: $("#navPlat").val() +"平台活跃用户好友分布",
					x: -20 //center
				},
				subtitle: {
		         text: zeroMfcount,
		      	x: -20
		        },
				xAxis: {
					categories: xAxis,
					labels:{
	            	 enabled: false
	            	}
		        },
				yAxis: {
					title: {
						text: '人'
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
		        	name: '用户数',
		      	data: yAxis
		     	}]
		    });
		}, "json");
	}
	
	function browserDataChart(){
		//先取出数据
		var start = navForm.daterange.getStartDate("#navdaterange","YYYYMMDD");
		var end = navForm.daterange.getEndDate("#navdaterange","YYYYMMDD");
		
		var params ={id:"10000001",args:{stm:start,etm:end,plat:$("#navPlat").val(),table:'d_browser_data',sort:'tm',order:"asc"}};
		$.post("data/common/mysqlQuery.htm",{params:JSON.stringify(params)},function(data, textStatus) {
			
			var xAxis = [];//x轴
			if(data && data.loop){
				$.each(data.loop,function(key,obj){
					if (!xAxis.contains(obj.tm)) {
						xAxis.push(obj.tm);
					}
				});
				$.each(data.loop,function(key,obj){
					if (!xAxis.contains(obj.tm)) {
						xAxis.push(obj.tm);
					}
				});
				if (xAxis && xAxis.length > 0) {
					var yData = {};
					$.each(xAxis, function(index, label) {
						$.each(data.loop,function(key,obj){
							if(obj.tm==label){
								var objs = [];
								if(!yData[obj.name]){
									objs.push(obj.uacount);
									yData[obj.name] = objs; 
								}else{
									objs = yData[obj.name];
									objs.push(obj.uacount);
									yData[obj.name] = objs; 
								}
							}
						});
						
						//判断哪个key没有数据,直接给他push 0
						$.each(yData, function(i, o) {
							if(o.length!=index+1){
								o.push(0);
								yData[i]=o;
							}
						});
					});
					
					var series = [];
					$.each(yData, function(i, o) {
						series.push({
							name : i,
							data : o,
						});
					});
					
					$('#browserDataChart').highcharts({
						chart: {
							type: 'column'
						},
				      title: {
				      	text: $("#navPlat").val() +"平台用户使用浏览器分布"
				       	},
				      xAxis: {
				      	categories: xAxis
				       	},
				      credits: {
				      	enabled: false
				       	},
				      series: series
				   });
				}
			}
		}, "json");
	}
	</script>
</body>
</html>
