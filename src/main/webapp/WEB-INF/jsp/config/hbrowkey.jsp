<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en-us">
<head>
<%@ include file="/WEB-INF/jsp/common/meta.jsp"%>
<title>hbase rowkey-数据魔方</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/top.jsp">
	<jsp:param name="nav" value="data" />
</jsp:include>

<jsp:include page="../common/sysconfigMenu.jsp">
	<jsp:param name="nav" value="config" />
	<jsp:param name="subnav" value="hbrowkey" />
</jsp:include>

<div class="main">
	<jsp:include page="/WEB-INF/jsp/common/mainNav.jsp">
		<jsp:param name="actionName" value="rowkey规则" />
	</jsp:include>
	<div class="panel panel-info">
	  <div class="panel-body">
		<p>1.fields对应的值是rowkey的组成部份,如"fields":<span style="color: red;">{"_tm":8,"_plat":5}</span>表示8位时间和5位的平台号组成rowkey</p>
		<p>2.如果某个表没有对应的规则,则使用默认的规则:{"fields":{"_plat":5,"_uid":10,"_tm":10},"reverse":true,"line_num":true}</p>
	  </div>
	</div>
	<div class="content-body">
	<table id="example" class="table table-striped table-bordered display"  cellspacing="0" width="100%">
		<thead>
			<tr>
				<th width="20%">表名</th>
				<th width="80%">规则</th>
			</tr>
		</thead>
		
		<tfoot>
           <tr><th></th><th></th></tr>
        </tfoot>
	</table>
	</div>
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
        url: 'config/getRowKeys.htm',
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data) {
        	 var json = eval(data);
        	 var table = $('#example').DataTable({
        		 "processing": true,
        		 "ordering": false,
    	        data : json.data,
    	        "columns": [{ "data": "tableName" },{ "data": "rules" }
    	          ],
    	       "oLanguage": commonData.oLanguage,
    	    	 "dom": 't<"bottom fl"l><"bottom"p>'
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
