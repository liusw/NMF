<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en-us">
<head>
<%@ include file="/WEB-INF/jsp/common/meta.jsp"%>
<title>欺诈用户-数据魔方</title>
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
				<jsp:param name="subnav" value="qizhaData" />
			</jsp:include>
		
			<table id="example" class="table table-striped table-bordered display"  cellspacing="0" width="100%" style="background-color:#FFF;">
				<thead>
					<tr>
						<th align="center">平台</th>
						<th align="center">平台唯一id</th>
						<th align="center">用户ID</th>
						<th align="center">时间</th>
					</tr>
				</thead>
				<tfoot>
		            <tr>
		            	<th></th><th></th><th></th><th></th>
		            </tr>
	       		</tfoot>
			</table>
		</div>
	</div>
	<%@ include file="/WEB-INF/jsp/common/foot.jsp"%>

	<!-- Bootstrap core JavaScript navbar-fixed-bottom
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="static/js/basic.js"></script>
	<script src="static/js/dataTable.js"></script>
	<script src="static/js/datetimepicke.js"></script>
	<script src="static/js/common.js"></script>
	<script type="text/javascript">
	$(function() {
		$('#example tfoot th').each(function (index) {
		      var title = $('#example thead th').eq($(this).index()).text();
		      $(this).html('<input type="text" class="form-control input-sm" style="width:100%;" placeholder="Search ' + title +' " />');
		    });
			var table = $("#example").DataTable({
		     "bFilter": true,
		     "pagingType" : "full_numbers",
		     "bDestroy" : true,
		     "bProcessing" : false,
		     "sAjaxSource" : "data/qizhaData/findQizhaUser.htm",
		     "bServerSide" : true,
		     "ordering": false,
		     "columns" : [
					{ "data": "plat" },
					{ "data": "bpid" },
					{ "data": "uid" },
					{ "data": "tm" }
		         ],
		      "oLanguage":commonData.oLanguage,
		      "dom": 't<"col-sm-6"l><"col-sm-6"p>'
			});
			
			table.columns().eq( 0 ).each( function ( colIdx ) {
	   	        $( 'input', table.column( colIdx ).footer() ).on( 'keyup change', function () {
	   	            table.column(colIdx).search(this.value).draw();
	   	        } );
	   	    });
	} );
	
	function initTable() {
		$('#example tfoot th').each(function (index) {
	      var title = $('#example thead th').eq($(this).index()).text();
	      $(this).html('<input type="text" class="form-control input-sm" style="width:100%;" placeholder="Search ' + title +' " />');
	    });
		var table = $("#example").DataTable({
	     "bFilter": true,
	     "pagingType" : "full_numbers",
	     "bDestroy" : true,
	     "bProcessing" : false,
	     "sAjaxSource" : "data/qizhaData/findQizhaUser.htm",
	     "bServerSide" : true,
	     "ordering": false,
	     "columns" : [
				{ "data": "plat" },
				{ "data": "bpid" },
				{ "data": "uid" },
				{ "data": "tm" }
	         ],
	      "oLanguage":commonData.oLanguage,
	      "dom": 't<"bottom fl"l><"bottom"p>',
		});
		
		table.columns().eq( 0 ).each( function ( colIdx ) {
   	        $( 'input', table.column( colIdx ).footer() ).on( 'keyup change', function () {
   	            table.column(colIdx).search(this.value).draw();
   	        } );
   	    });
    }
	</script>
</body>
</html>
