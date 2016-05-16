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
<title>淘汰赛-数据魔方</title>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/top.jsp">
       <jsp:param name="nav" value="data" />
	</jsp:include>
	
	<jsp:include page="/WEB-INF/jsp/common/dataMenu.jsp">
		<jsp:param name="nav" value="bisaichang" />
		<jsp:param name="subnav" value="taotaisai" />
	</jsp:include>
	
	<div class="main">
		<jsp:include page="/WEB-INF/jsp/common/mainNav.jsp">
			<jsp:param name="actionName" value="淘汰赛" />
		</jsp:include>
		
		<div class="content-body">
				<table id="example" class="table table-striped table-bordered display dtable">
					<thead>
						<tr>
							<th></th><th>平台</th><th>日期</th><th>开场次数</th><th>参赛独立人数</th><th>参赛人次</th><th>消耗真人的金币</th><th>发放真人的金币</th>
							<th>消耗机器人的金币</th><th>发放机器人的金币</th><th>消耗参赛券张数</th><th>机器人参赛人次</th><th>机器人参赛人数</th><th>总等待时间/秒</th><th>局数</th><th>总玩牌时长/秒</th>
						</tr>
					</thead>
					<tfoot></tfoot>
				</table>
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
	<script type="text/javascript">
	var table,navForm;
	$(document).ready(function() {
		var std = new Date();
		navForm = navForm.init({'showPlat':true,'showDate':{'isShow':true,'startDate':std.format('yyyyMMdd'),'endDate':std.format('yyyyMMdd')},'showBtn':true});
		
		$("#navSubmitBtn").click(function(){
			getTable();
		});
		
		$("#example").click(function(e){
			if($(e.target).is(".details-control")){
				showDetail($(e.target));
			};
		});
		
		$("#navSubmitBtn").trigger("click");
	});
	
	function getTable(){
		var start = navForm.daterange.getStartDate("#navdaterange","YYYYMMDD");
		var end = navForm.daterange.getEndDate("#navdaterange","YYYYMMDD");
		
		if(isEmpty($("#navPlat").val()) || isEmpty(start) || isEmpty(end)){
			alert("平台和时间都不能为空");
			return;
		}
		
		var params ={
			id:"12000003|12000004","dataType":"dataTable",
			args:{
				plat:$("#navPlat").val(),etm:end,stm:start,table:"d_taotaisai"
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
					{
		            "className":'details-control',
		            "orderable":false,
		            "data":null,
		            "defaultContent": ''
		        	},
		        	{ "data": "plat","visible": false},
					{ "data": "tm"},
					{ "data": "ccount"},
					{ "data": "tmDistinctUserCount"},
					{ "data": "tmUserCount"},
					{ "data": "userMoney"},
					{ "data": "userBonus"},
					{ "data": "robotMoney"},
					{ "data": "robotBonus"},
					{ "data": "ticket"},
					{ "data": "tmRobotCount"},
					{ "data": "tmDistinctRobotCount"},
					{ "data": "wait"},
					{ "data": "jcount"},
					{ "data": "ptime"}
		         ],
		       "oLanguage": commonData.oLanguage,
		       "dom": 't<"bottom fl"l><"bottom"p>'
			});
	};
	
	function showDetail(o){
		var tr = $(o).closest('tr');
		var row = table.row(tr);
		
		if ( row.child.isShown() ) {
			row.child.hide();
			tr.removeClass('shown');
		}else {
			var subTableId = "subtable_"+row.data().tm;
		
			var params ={
				id:"12000005",args:{plat:row.data().plat,tm:row.data().tm,table:"d_taotaisai"}
			};
			
			var data = getJsonData("data/common/mysqlQuery.htm",{params:JSON.stringify(params)});
			if(data!=null && data.result==1) {
				var t = '<table id="'+subTableId+'" class="table table-bordered" style="margin-bottom: 0px;">'+
				'<thead>'+
					'<tr>'+
					 	'<td>名称</td>'+
						'<td>开场次数</td>'+
						'<td>参赛独立人数</td>'+
						'<td>参赛人次</td>'+
						'<td>消耗真人的金币</td>'+
						'<td>发放真人的金币</td>'+
						'<td>消耗机器人的金币</td>'+
						'<td>发放机器人的金币</td>'+
						'<td>消耗参赛券张数</td>'+
						'<td>机器人参数人次</td>'+
						'<td>机器人参数人数</td>'+
						'<td>总等待时间</td>'+
						'<td>局数</td>'+
						'<td>总玩牌时长</td>'+
					'</tr>'+
				'</thead>'+
				'<tbody>';
				
				$.each(data.loop, function(index, value) {
					t += '<tr>'+
						'<td>'+value.name+'</td>'+
						'<td>'+value.ccount+'</td>'+
						'<td>'+value.distinctUserCount+'</td>'+
						'<td>'+value.userCount+'</td>'+
						'<td>'+value.userMoney+'</td>'+
						'<td>'+value.userBonus+'</td>'+
						'<td>'+value.robotMoney+'</td>'+
						'<td>'+value.robotBonus+'</td>'+
						'<td>'+value.ticket+'</td>'+
						'<td>'+value.robotCount+'</td>'+
						'<td>'+value.distinctRobotCount+'</td>'+
						'<td>'+value.wait+'</td>'+
						'<td>'+value.jcount+'</td>'+
						'<td>'+value.ptime+'</td>'+
					'</tr>';
				});
				t +='</tbody></table>';
				row.child(t).show();
				subtable = $("#"+subTableId).DataTable({
					 "ordering": false,
					"dom": ''
				});
			}
			tr.addClass('shown');
		}
    }
	</script>
</body>
</html>
