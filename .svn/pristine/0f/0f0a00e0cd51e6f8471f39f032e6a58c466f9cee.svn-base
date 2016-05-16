<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en-us">
<head>
<%@ include file="/WEB-INF/jsp/common/meta.jsp"%>
<style type="text/css">
#example thead  th,#example tbody td{
	text-align: center;
}
</style>
<title>站点查询-数据魔方</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/top.jsp">
	<jsp:param name="nav" value="sysconfig" />
</jsp:include>

<jsp:include page="../common/sysconfigMenu.jsp">
	<jsp:param name="nav" value="config" />
	<jsp:param name="subnav" value="siteDetails" />
</jsp:include>

<div class="main">
	<jsp:include page="/WEB-INF/jsp/common/mainNav.jsp">
		<jsp:param name="actionName" value="站点查询" />
	</jsp:include>
	
	<section>
		<table id="example" class="table table-striped table-bordered display"  cellspacing="0" width="100%">
			<thead>
				<tr>
					<th>唯一索引值(bpid)</th>
					<th>平台ID(plat)</th>
					<th>svid</th>
					<th>数据中心ID(pname)</th>
					<th>版本名称(sname)</th>
					<th>sid</th>
					<th>是否为移动站点</th>
				</tr>
			</thead>
			
			<tfoot>
            <tr>
				<th></th>
				<th></th>
				<th></th>
				<th></th>
				<th></th>
				<th></th>
				<th></th>
            </tr>
       		</tfoot>
		</table>
	</section>
</div>
<br/><br/><br/>
<%@ include file="/WEB-INF/jsp/common/foot.jsp"%>

<!-- Bootstrap core JavaScript navbar-fixed-bottom
   ================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="static/js/basic.js"></script>
<script src="static/js/dataTable.js"></script>
<script src="static/js/common.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	$('#example tfoot th').each( function () {
        var title = $('#example thead th').eq( $(this).index() ).text();
        $(this).html( '<input type="text" class="form-control input-sm" style="width:100%;" placeholder="Search ' + title +' " />' );
    } );
	
	$.ajax({
        type: "get",
        url: 'config/getSiteInfo.htm',
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data) {
        	 var json = eval(data);
        	 var table = $('#example').DataTable({
        		 "processing": true,
    	         data : json.data,
    	         "columns": [
    	                     { "data": "bpid" },
    	                     { "data": "plat" },
    	                     { "data": "svid"},
    	                     { "data": "pName" },
    	                     { "data": "sname" },
    	                     { "data": "sid" },
    	                     { "data": "ismobile" }
    	          ],
    	       "oLanguage": commonData.oLanguage,
    	       "dom": 'Tt<"col-sm-6"l><"col-sm-6"p>'
    	    });
        	table.columns().eq( 0 ).each( function ( colIdx ) {
       	        $( 'input', table.column( colIdx ).footer() ).on( 'keyup change', function () {
       	            table.column(colIdx).search(this.value).draw();
       	        } );
       	    } );
        },
        error: function (err) {
            alert("请求出错!");
            myLog(err);
        }
    });
});
</script>
</body>
</html>
