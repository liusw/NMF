<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en-us">
<head>
<%@ include file="/WEB-INF/jsp/common/meta.jsp"%>
<title>金币流水－数据魔方</title>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/top.jsp">
        <jsp:param name="nav" value="data"/>
	</jsp:include>
	
	<jsp:include page="/WEB-INF/jsp/common/dataMenu.jsp">
		<jsp:param name="nav" value="businessData" />
		<jsp:param name="subnav" value="tableData" />
	</jsp:include>
	
	<div class="main2">
		<jsp:include page="/WEB-INF/jsp/common/topTabMenu.jsp">
			<jsp:param name="tabActive" value="coinsFlow" />
		</jsp:include>
		<jsp:include page="/WEB-INF/jsp/common/mainNav2.jsp" />
		
		<div class="main2Body"> 
			<div id="sub-nav">
        		<ul id="nav-tab" class="nav nav-tabs">
          		<li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" href="javascript:void(0);">查询<b class="caret"></b></a>
          			<ul id="query-by" class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu">
                  <li><a href="<%=(request.getContextPath())%>/log/gamecoins.jsp?type=by-user" >按用户</a></li>
                  <li><a href="<%=(request.getContextPath())%>/log/gamecoins.jsp?type=by-day">按天</a></li>
                  <li><a href="<%=(request.getContextPath())%>/log/gamecoins.jsp?type=by-day-use">按天和用户</a></li>
                </ul>
          		</li>
          		<li><a href="<%=(request.getContextPath())%>/data/cgCoinsTop.htm">金币排行</a></li>
          		<li><a href="<%=(request.getContextPath())%>/data/vmoneyTop.htm">台费排行</a></li>
          		<li class="active"><a href="<%=(request.getContextPath())%>/data/actCoins.htm">各渠道金流汇总</a></li>
        		</ul>
    		</div>
		
			<table id="example" class="table table-striped table-bordered display dtable">
				<thead>
					<tr>
						<th>渠道ID</th><th>发放</th><th>消耗</th>
					</tr>
				</thead>
				<tfoot><tr><th></th><th></th><th></th></tfoot>
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
	<script src="static/Highcharts-4.0.4/js/highcharts.js"></script>
	<script src="static/Highcharts-4.0.4/js/modules/exporting.js"></script>
	<script type="text/javascript">
	var table,navForm;
	$(function() {
		$('#example tfoot th').each( function (index) {
			var readOnly='';
			if(index!=0){
				readOnly='  disabled="disabled"';
			}
        var title = $('#example thead th').eq( $(this).index() ).text();
        $(this).html( '<input '+readOnly+' type="text" class="form-control input-sm" style="width:100%;" placeholder="Search ' + title +' " />' );
	    } );
		
		var std = new Date();
		navForm = navForm.init({'showPlat':true,'showDate':{startDate:std.format('yyyy-MM-dd'),endDate:std.format('yyyy-MM-dd'),'isShow':true,applyClickCallBack:loadData},platChangeCallback:loadData});
		
		$("#navPlat").change(function(){
			loadData();
		});
		loadData();
	});
	
	function loadData(){
		var start = navForm.daterange.getStartDate("#navdaterange","YYYYMMDD");
		var end = navForm.daterange.getEndDate("#navdaterange","YYYYMMDD");
		var plat = $("#navPlat").val();
		
		var params ={
			id:"12000013|12000014","dataType":"dataTable",
			args:{
				plat:plat,etm:end,stm:start,table:"gold_water_info"
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
		     "aaSorting": [[ 0, "asc" ]],
		     "aoColumnDefs": [ { "bSortable": false}],
		     "sServerMethod": "POST",
		     "columns" : [
					{"data": "act_id"},
					{"data": "fafang"},
					{"data": "xiaohao"}
		         ],
		       "oLanguage": commonData.oLanguage,
		       "dom": 't<"bottom fl"l><"bottom"p>'
			});
		table.columns().eq(0).each(function (colIdx) {
   		$( 'input', table.column( colIdx ).footer() ).on( 'keyup change', function () {
   	   	table.column(colIdx).search(this.value).draw();
   	        });
   	    } );
	};
	</script>
</body>
</html>
>