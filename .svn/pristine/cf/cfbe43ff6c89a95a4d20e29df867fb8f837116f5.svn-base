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
<title>夺宝奇兵二期</title>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/top.jsp">
       <jsp:param name="nav" value="data" />
	</jsp:include>
	
		<jsp:include page="/WEB-INF/jsp/common/dataMenu.jsp">
		<jsp:param name="nav" value="bisaichang" />
		<jsp:param name="subnav" value="duobaoqibing2" />
	</jsp:include>
	
	<div class="main">
		<jsp:include page="/WEB-INF/jsp/common/mainNav.jsp">
			<jsp:param name="actionName" value="夺宝奇兵二期" />
		</jsp:include>
		
		<div class="content-body">
			<table id="example" class="table table-striped table-bordered display dtable">
				<thead>
					<tr>
						<th></th><th>日期</th><th>每日下注人数</th><th>每日下注总次数</th><th>系统坐庄局数</th><th>玩家坐庄局数</th><th>系统坐庄每日盈亏</th><th>玩家坐庄每日盈亏</th><th>闲家下注总金额</th>
						<th>官方账户当日抽水总金额</th><th>奖池账户当日抽水总金额</th><th>奖池当日返还金额</th>
						<th>风控池剩余总量</th>
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
	$(function() {
		var std = new Date();
		std.addDays(-1);
		navForm = navForm.init({'showPlat':true,'showDate':{'isShow':true,'startDate':std.format('yyyyMMdd'),'endDate':std.format('yyyyMMdd'),applyClickCallBack:getTable},platChangeCallback:getTable});

		$("#example").click(function(e){
			if($(e.target).is(".details-control")){
				showDetail($(e.target));
			};
		});
		
		getTable();
	});
	
	function getTable(){
		var start = navForm.daterange.getStartDate("#navdaterange","YYYYMMDD");
		var end = navForm.daterange.getEndDate("#navdaterange","YYYYMMDD");
		
		if(isEmpty($("#navPlat").val()) || isEmpty(start) || isEmpty(end)){
			alert("平台和时间都不能为空");
			return;
		}
		
		var params ={
			id:"12000011|12000012","dataType":"dataTable",
			args:{
				plat:$("#navPlat").val(),etm:end,stm:start,table:"d_duobaoqibing2"
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
					{ "data": "tm"},
					{ "data": "xzrs"},
					{ "data": "xzcs"},
					{ "data": "xtzzjs"},
					{ "data": "wjzzjs"},
					{ "data": "zjzzmryk"},
					{ "data": "wjzzmryk"},
					{ "data": "xjxzzje"},
					{ "data": "gfdrcsze"},
					{ "data": "jcdrcsze"},
					{ "data": "jcfhje"},
					{ "data": "fkcsyzl"}
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
				id:"10000001",args:{plat:row.data().plat,tm:row.data().tm,table:"d_duobaoqibing2"}
			};
			
			var data = getJsonData("data/common/mysqlQuery.htm",{params:JSON.stringify(params)});
			if(data!=null && data.result==1) {
				var t = '<table id="'+subTableId+'" class="table table-bordered" style="margin-bottom: 0px;">'+
				'<thead>'+
					'<tr>'+
					 	'<td>tid</td>'+
						'<td>每日下注人数</td>'+
						'<td>每日下注总次数</td>'+
						'<td>系统坐庄局数</td>'+
						'<td>玩家坐庄局数</td>'+
						'<td>系统坐庄每日盈亏</td>'+
						'<td>玩家坐庄每日盈亏</td>'+
						'<td>闲家下注总金额</td>'+
						'<td>官方账户当日抽水总金额</td>'+
						'<td>奖池账户当日抽水总金额</td>'+
						'<td>奖池当日返还金额</td>'+
						'<td>风控池剩余总量</td>'+
					'</tr>'+
				'</thead>'+
				'<tbody>';
				
				$.each(data.loop, function(index, value) {
					t += '<tr>'+
						'<td>'+value.tid+'</td>'+
						'<td>'+value.xzrs+'</td>'+
						'<td>'+value.xzcs+'</td>'+
						'<td>'+value.xtzzjs+'</td>'+
						'<td>'+value.wjzzjs+'</td>'+
						'<td>'+value.zjzzmryk+'</td>'+
						'<td>'+value.wjzzmryk+'</td>'+
						'<td>'+value.xjxzzje+'</td>'+
						'<td>'+value.gfdrcsze+'</td>'+
						'<td>'+value.jcdrcsze+'</td>'+
						'<td>'+value.jcfhje+'</td>'+
						'<td>'+value.fkcsyzl+'</td>'+
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
