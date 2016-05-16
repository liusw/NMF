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
				<jsp:param name="subnav" value="gblCount" />
			</jsp:include>
			
			<div id="countTab-div" class="content-form" style="min-height:238px;">
				<div style="margin-bottom: 18px;">
					<select id="user-type" multiple="multiple">
						<option value="0" selected="selected">当天活跃</option>
						<option value="1">昨注今活跃</option>
						<option value="2">激活</option>
						<option value="3">昨激活今活跃</option>
					</select>
					<button type="button" class="btn btn-default" style="margin-left:6px;" id="countSubmitBtn" data-loading-text="查询中...">查询</button>
				</div>
				<div>
					<table id="gmbCount-tab"
						class="table table-striped table-bordered display" cellspacing="0" width="100%">
						<thead>
							<tr>
								<th align="center">站点</th>
								<th align="center">用户类型</th>
								<th align="center">日期</th>
								<th align="center">0局</th>
								<th align="center">1局</th>
								<th align="center">2局</th>
								<th align="center">3局</th>
								<th align="center">4局</th>
								<th align="center">5局</th>
								<th align="center">6局</th>
								<th align="center">7局</th>
								<th align="center">8局</th>
								<th align="center">9局</th>
								<th align="center">10局</th>
								<th align="center">11~15局</th>
								<th align="center">16~20局</th>
								<th align="center">21~30局</th>
								<th align="center">31~50局</th>
								<th align="center">51~100局</th>
								<th align="center">超过100局</th>
							</tr>
						</thead>
					</table>
				</div>
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
	<script type="text/javascript">
	$(function(){
		var std = new Date();
		std.addDays(-10);
		navForm.init({'showPlat':true,'showSid':true,'sidType':'MultiSelect','showDate':{'isShow':true,startDate:std.format('yyyy-MM-dd')},
			platChangeCallback: updateNavMultiSid
		});
		
		$('#user-type').multiselect({
	        includeSelectAllOption: true,
	        enableFiltering: false,
	        maxHeight: 190
	    });
	});

	$('#countSubmitBtn').click(function(){
		var para = {};
		para.id = "10000003|10000004";
		para.dataType = "dataTable";
		para.args = {};
		
		para.args.plat = $("#navPlat").val();
		
		para.args.sTm = (navForm.daterange.getStartDate("#navdaterange") + "").replaceAll("-","");
		para.args.eTm = (navForm.daterange.getEndDate("#navdaterange") + "").replaceAll("-","");
		
		var sid = $("#navMultiSid").val() + "";
		
		if(isEmpty($("#user-type").val())){
			alert("用户类型不能为空!");
			return false;
		}
		
		para.args.userType = [];
		$.each($("#user-type").val(),function(index,value){
			para.args.userType.push(value);
		});

		if(isEmpty(sid)){
			alert("站点不能为空!");
			return false;
		}
		
		para.args.sid = [];
		$.each(sid.split(","),function(index,value){
			para.args.sid.push((value + "").split("_")[0]);
		});

		para.args.table = "t_gambling_count";
		
		var sidNames = {};
		
		$.each(getJsonData2("config/getSites.htm",{plat:$("#navPlat").val()},true), function(index, value) {
			sidNames[value.sid] = value.sname + "(" + value.sid + ")";
		});
		
		if(undefined == table){
			var table = $("#gmbCount-tab").DataTable({
			    "bFilter": true,
			    "pagingType" : "full_numbers",
			    "bDestroy" : true,
			    "bProcessing" : false,
			    "sAjaxSource" : "data/common/mysqlQuery.htm?params="+ JSON.stringify(para), 
			    "bServerSide" : true,
			    "ordering": true,
			    "searching":false,
			    "sServerMethod": "POST",
			    "aoColumns": [
	     			{ "data": "sid" },
	                { "data": "usertype"},
	                { "data": "tm"},
	                { "data": "gb0ct"},
	                { "data": "gb1ct" },
	                { "data": "gb2ct" },
	                { "data": "gb3ct" },
	                { "data": "gb4ct" },
	                { "data": "gb5ct" },
	                { "data": "gb6ct" },
	                { "data": "gb7ct" },
	                { "data": "gb8ct" },
	                { "data": "gb9ct" },
	                { "data": "gb10ct" },
	                { "data": "gb11to15ct" },
	                { "data": "gb16to20ct" },
	                { "data": "gb21to30ct" },
	                { "data": "gb31to50ct" },
	                { "data": "gb51to100ct" },
	                { "data": "gbgt100ct" },
		        ],

			    "columnDefs": [
					{
					   "render": function ( data, type, row ) {
					    	return sidNames[data] || data;
					    },
					
					   "targets": 0
					},
		            {
	                "render": function ( data, type, row ) {
		             	var val = "未定义";
		             	switch(data){
		             		case 0:
		             		    val = "当天活跃";
		             		    break;
		             		case 1:
		             		    val = "昨日注册当天活跃";
		             		    break;
		             		case 2:
		             		    val = "当天七日回流";
		             		    break;  
		             		case 3:
		             		    val = "当天活跃且对应的前一天七日回流";
		             		    break;
		             	}

		             	return	val;
	                },

	               "targets": 1
			        },
	        	],

			    "oLanguage": commonData.oLanguage,
		        "dom": 't<"bottom fl"l><"bottom"p>'
			});
		}else{
			table.ajax.url("data/common/mysqlQuery.htm?params="+ JSON.stringify(para)).load();
		}
	});
</script>
</body>
</html>