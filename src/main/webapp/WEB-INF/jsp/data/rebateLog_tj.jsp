<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<table id="example" class="table table-striped table-bordered display dtable">
	<thead>
		<tr>
			<th>时间</th><th>等级积分发放量</th><th>礼品积分发放量</th><th>礼品积分兑换量</th><th>礼品积分总结余</th>
		</tr>
	</thead>
	<tfoot></tfoot>
</table>
<script type="text/javascript">
	function navSubmitBtn_tj(){
		getTable();
	}
	
	function getTable(){
		var start = navForm.daterange.getStartDate("#navdaterange","YYYYMMDD");
		var end = navForm.daterange.getEndDate("#navdaterange","YYYYMMDD");
		
		if(isEmpty($("#navPlat").val()) || isEmpty(start) || isEmpty(end)){
			alert("平台和时间都不能为空");
			return;
		}
		
		var params ={
			id:"10000001|10000002","dataType":"dataTable",
			args:{
				columns:"id,plat,tm,levelSend,giftSend,giftExchange,giftSurplus",
				plat:$("#navPlat").val(),etm:end,stm:start,table:"d_rebate_total"
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
		     "sServerMethod": "POST",
		     "columns" : [
					{ "data": "tm"},
					{ "data": "levelSend"},
					{ "data": "giftSend"},
					{ "data": "giftExchange"},
					{ "data": "giftSurplus"}
		         ],
		       "oLanguage": commonData.oLanguage,
		       "dom": 't<"bottom fl"l><"bottom"p>'
			});
	};
	</script>
