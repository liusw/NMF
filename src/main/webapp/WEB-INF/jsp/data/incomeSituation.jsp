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
#example_filter{
	display: none;
}
 
.myWell{
	padding-bottom: 50px;
	overflow: auto;
}

</style>
<title>系统结余-数据魔方</title>
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
			<jsp:param name="tabActive" value="incomeSituation" />
		</jsp:include>
		<jsp:include page="/WEB-INF/jsp/common/mainNav2.jsp" />
		
		<div class="main2Body">
			<div class="myTable"></div>
		</div>
		</div>
	      <div class="text-center">
		      <ul class="pagination pagination-lg">
	          	
		      </ul>
	      </div>
	
	<br/><br/><br/>
	 <%@ include file="/WEB-INF/jsp/common/foot.jsp"%>

	<!-- Bootstrap core JavaScript navbar-fixed-bottom
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="static/js/basic.js"></script>
	<script src="static/js/dataTable.js"></script>
	<script src="static/js/multiselect.js"></script>
	<script src="static/js/daterangepicker.js"></script>
	<script src="static/js/daterange.by.js"></script>
	<script src="static/js/common.js"></script>
	
	<script type="text/javascript">
	$(function() {
		navForm = navForm.init({'showPlat':true,'showSid':false,'showDate':{'isShow':true,'singleDatePicker':true},
			platChangeCallback:updateNavMultiSid,'showBtn':true
		});
		
		$("#navSubmitBtn").trigger("click");
	} );
	
	var page=1;
	
	$('#navSubmitBtn').click(function(){
		var plat = $("#navPlat").val();
		var tm = navForm.date.getStartDate("#navdate");
		
		var querySql = 'select * from income_situation where _plat=' + plat + ' and _tm=' + tm.replace(/-/g,"");
		$('#navSubmitBtn').button("loading");
		$("#head_tr").html('');
		$("#tbody").html('');
		$(".pagination").html('');
		$.ajax({
	        type: "get",
	        async: false,
	        url: "data/hbase/query.htm",
	        data:{
	        	sql:querySql,pageSize:1000000
	        },
	        contentType: "application/json; charset=utf-8",
	        dataType: "json",
	        cache: false,
	        success: function (obj) {
        			if(obj.status==1 && obj.obj && obj.obj.o && obj.obj.o.length>0){
        				var $table = $("<table class='table table-striped table-bordered display' id='example'></table>");
        				var $thead = $("<thead></thead>");
        				var $theadTr = $("<tr id='head_tr'></tr>");
						var $tbody = $("<tbody id='tbody'></tbody>");
						$table.append($thead);
						$thead.append($theadTr);
						$table.append($tbody);
						$(".myTable").html($table);
	            		var json = obj.obj;
	            		var columns = [];
	            		var headName = {};
	        			headName['by_surplus']='博雅币结余';
	        			headName['_bpid']='子站点';
	        			headName['_gid']='游戏id';
	        			headName['_plat']='平台id';
	        			headName['_tm']='时间戳';
	        			headName['_uid']='用户id';
	        			headName['_ver']='版本';
	        			headName['buybybnum']='购买获得的博雅币数量';
	        			headName['consumebyb']='博雅币消耗';
	        			headName['hztotal']='黄钻用户成功总金额';
	        			headName['hzusers']='黄钻用户成功总人数';
	        			headName['left_10']='紫钻使用中剩余次数';
	        			headName['left_12']='蓝钻使用中剩余次数';
	        			headName['left_13']='红钻使用中剩余次数';
	        			headName['left_15']='黄钻使用中剩余次数';
	        			headName['left_18']='18使用中剩余次数';
	        			headName['left_33']='救济卡使用中剩余次数';
	        			headName['not_used_10']='紫钻未使用个数';
	        			headName['not_used_12']='蓝钻未使用个数';
	        			headName['not_used_13']='红钻未使用个数';
	        			headName['not_used_15']='黄钻未使用个数';
	        			headName['not_used_18']='18未使用个数';
	        			headName['not_used_33']='救济卡未使用个数';
	        			headName['pay_total']='支付成功总金额';
	        			headName['paybyb']='购买博雅币收入';
	        			headName['paychips']='购买游戏币收入';
	        			headName['paycount']='支付成功总单数';
	        			headName['paytool']='购买道具';
	        			headName['payusers']='支付人数';
	        			headName['refundmount']='退款金额';
	        			headName['sendbyb']='博雅币发放';
	        			headName['used_10']='紫钻使用中个数';
	        			headName['used_12']='蓝钻使用中个数';
	        			headName['used_13']='红钻使用中个数';
	        			headName['used_15']='黄钻使用中个数';
	        			headName['used_18']='18使用中个数';
	        			headName['used_33']='救济卡使用中个数';
	            		$.each(json.title, function(key, value) {
	            			if(value != "_gid" && value != "_plat" && value != "_tm" && value != "_uid" && value!="_ver" && value != "rowkey"){
		            			var hname = headName[value];
		            			if(!hname){
		            				hname = value;
		            			}
		            			columns.push({ "data" : value});
			    			//	$("#head_tr").append('<th>'+hname+'</th>');
		            			$theadTr.append('<th>'+hname+'</th>');
	            			}
		    			});
	            		
	            		var sidNames = {};
	            		
	            		$.each(getJsonData2("config/getSites.htm",{plat:$("#navPlat").val()},true), function(index, value) {
	            			sidNames[value.bpid] = value.sname + "(" + value.sid + ")";
						});
	            		
	            		var datas = initIncomeSituationTable(null,json.title,json.o,sidNames);
	            		
	            		var table = $("#example").DataTable({
	            		     "bDestroy" 	: true,
	            		     "bProcessing" 	: false,
	            		     "ordering"		: false,
	            		     "searching"	: false,
	                		 "paging"		: false,
	                		 "data"			: datas,
	                		 "columns" 		: columns,
	            	    	 "oLanguage": {
	            	    		 	"sInfo" : ""
	            	    	 },
	            	    	  "dom": 'T<"clear">lfrtip',
	            	          "tableTools": {
	            	              "sSwfPath": "css/swf/copy_csv_xls_pdf.swf",
	            	              "aButtons": [
	            	                           {
	            	                               "sExtends": "copy",
	            	                               "sButtonText": "复制"
	            	                           },
	            	                           {
	            	                               "sExtends": "xls",
	            	                               "sButtonText": "下载",
	            	                               "bBomInc": true
	            	                           }
	            	                       ]
	            	          }
	           			});
	            		//initIncomeSituationTable(table,json.title,json.o,sidNames);
    				}else{
    					showMsg(isEmpty(obj.msg)?"没有检索到数据":obj.msg);
    				}
	        },error: function (err) {
	            alert("请求出错!");
	           }
	    });
		$('#navSubmitBtn').button("reset");
	});
	
	function initIncomeSituationTable(table,titles,datas,sidNames){
		var find = false;
		var resultJson = new Array();
		$.each(datas, function(index, value) {
			var row = new Array();
			var rowJson = {};
			$.each(titles, function(tkey, tvalue) {
				if(tkey != "_gid" && tkey != "_plat" && tkey != "_tm" && tkey != "_uid" && tkey!="_ver" && tkey != "rowkey"){
					find = false;
					$.each(datas[index], function(okey, ovalue) {
						if(tkey==okey){
							if(okey == "_bpid"){
								ovalue = sidNames[ovalue];
							}
							rowJson[tkey] = ovalue;
							row.push(ovalue);
							find = true;
							return;
						}
					});
					if(!find){
						rowJson[tkey] = "0";
						row.push("0");
					}
				}
			});
			resultJson.push(rowJson);
			//table.row.add(row).draw();
		});
		return resultJson;
	}
	</script>
</body>
</html>
