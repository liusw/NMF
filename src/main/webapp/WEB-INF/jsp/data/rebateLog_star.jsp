<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<table id="example1" class="table table-striped table-bordered display dtable">
	<thead>
		<tr>
			<th>时间</th><th>四星用户数</th><th>三星用户数</th><th>二星用户数</th><th>一星用户数</th>
		</tr>
	</thead>
	<tfoot></tfoot>
</table>
<script type="text/javascript">
	function navSubmitBtn_star(){
		getTable1();
	}
	
	function getTable1(){
		var start = navForm.daterange.getStartDate("#navdaterange","YYYYMMDD");
		var end = navForm.daterange.getEndDate("#navdaterange","YYYYMMDD");
		
		if(isEmpty($("#navPlat").val()) || isEmpty(start) || isEmpty(end)){
			alert("平台和时间都不能为空");
			return;
		}
		
		var params ={
			id:"10000001|10000002","dataType":"dataTable",
			args:{
				columns:"id,plat,tm,star4Count,star3Count,star2Count,star1Count",
				plat:$("#navPlat").val(),etm:end,stm:start,table:"d_rebate_star"
			}
		};
		
		$("#example1").DataTable({
		     "bFilter": true,
		     "pagingType" : "full_numbers",
		     "bDestroy" : true,
		     "bProcessing" : false,
		     "sAjaxSource" : "data/common/mysqlQuery.htm?params="+JSON.stringify(params),
		     "bServerSide" : true,
		     "ordering": false,
		     "sServerMethod": "POST",
		     "columns" : [
					{ "data": "tm"},
					{ "data": "star4Count"},
					{ "data": "star3Count"},
					{ "data": "star2Count"},
					{ "data": "star1Count"}
		         ],
		       "oLanguage": commonData.oLanguage,
		       "dom": 't<"bottom fl"l><"bottom"p>'
			});
	};
	</script>
</body>
</html>
