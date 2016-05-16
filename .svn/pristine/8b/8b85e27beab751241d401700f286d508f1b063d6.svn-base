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
.main{
	overflow-x: auto; 
	padding: 50px 15px 30px;
	min-height: 400px;
}
thead th{
	text-align: center;
}
#example thead  th,#example tbody td{
	text-align: center;
}
#example_filter{
	display: none;
}
 
.myWell{
	padding-bottom: 50px;
	overflow: auto;
}

</style>
<title>事件分析-数据魔方</title>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/top.jsp">
		<jsp:param name="nav" value="data" />
	</jsp:include>

	<jsp:include page="/WEB-INF/jsp/common/dataMenu.jsp">
		<jsp:param name="nav" value="lc" />
		<jsp:param name="subnav" value="eventAnalysis" />
	</jsp:include>
	<div class="main">
		<jsp:include page="/WEB-INF/jsp/common/mainNav.jsp">
			<jsp:param name="actionName" value="事件分析" />
			<jsp:param name="helpId" value="127" />
		</jsp:include>
		<form class="form">
		<!-- <div class="row"> 
				<div class="col-xs-3">
					<div class="row">
						<div class="form-group col-xs-12">
							<label>主事件名：</label>
							<select id="eventCat" class="selectpicker form-control" data-live-search="true" data-size="6"></select>
						</div>
					</div>
				</div>-->
				<div class="form-group col-xs-3">
					<div class="input-group col-xs-12">
						<label>主事件名：</label>
					    <select id="eventCat" class="form-control"></select>
				    </div>
				 </div>
				<div class="col-xs-1">
					<div class="row">
						<div class="form-group col-xs-12">
							<label>&nbsp;</label>
							<button type="button" class="form-control btn btn-default" id="submitBtn" data-loading-text="查询中...">查询</button>
						</div>
					</div>
				</div>
			<!--</div> -->
		</form>
		<table id="example" class="table table-bordered" style="padding:0px; margin:0px;">
			<thead>
				<tr id="head_tr"></tr>
			</thead>
			<tbody id="tbody"></tbody>
		</table>
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
	<script type="text/javascript" src="static/select2/js/select2.min.js"></script>
	<script src="static/js/multiselect.js"></script>
	<script src="static/js/daterangepicker.js"></script>
	<script src="static/js/daterange.by.js"></script>
	<script src="static/js/dateUtils.js"></script>
	<script src="static/js/common.js"></script>
	
	<script type="text/javascript">
	$(function() {
		var std = new Date();
		std.addDays(-10);
		std.format('yyyy-MM-dd');
		navForm = navForm.init({'showPlat':true,'showSid':true,'sidType':'Select','showDate':{'isShow':true,'startDate':std.toFormatDate()},
			platChangeCallback:updateNavMultiSid,'showBtn':false
		});
		setEvent();
		$("#navSid").change(function() { 
			setEvent();
		}); 
		
		$("#navPlat").change(function() { 
			setEvent();
		}); 
		
		$("#submitBtn").trigger("click");
	} );
	
	function selectpicker(){
		//$("#eventCat").selectpicker();
		//$("#eventCat").selectpicker('refresh');
		$("#eventCat").select2();
	}
	
	function setEvent(){
		$("#eventCat").html(''); 
		var sid = $("#navSid").val();
		if(isEmpty(sid)){
			return;
		}
		sid = sid.split("_")[1];
		var params ={
				id:"10000001",args:{bpid:sid,table:"event_analysis"}
			};
		var data = getJsonData("data/common/mysqlQuery.htm",{params:JSON.stringify(params)});
		if(data!=null){
			$.each(data.loop, function(index, value) {
				$("#eventCat").append("<option value=" + value.event + ">" + value.event + "</option>");
			});
			selectpicker();
		}
	}
	var page=1;
	
	$('#submitBtn').click(function(){
		var plat = $("#navPlat").val();
		var sTime = navForm.daterange.getStartDate("#navdaterange","YYYYMMDD");
		var eTime = navForm.daterange.getEndDate("#navdaterange","YYYYMMDD");
		var sid = $("#navSid").val();
		var event = $("#eventCat").val();
		if(isEmpty(sid)){
			alert("站点不能为空!");
			return false;
		}
		if(isEmpty(event)){
			alert("主事件不能为空!");
			return false;
		}
		sid = sid.split("_")[1];
		var sql = "select * from event_analysis where _bpid='"+sid+"' and eventCat='"+event+"' and _tm between "+sTime+" and "+eTime;
		var rowkey = "_bpid,eventCat,_tm";
		$('#submitBtn').button("loading");
		$("#head_tr").html('');
		$("#tbody").html('');
		$(".pagination").html('');
		$.ajax({
	        type: "get",
	        async: false,
	        url: "data/hbase/query.htm",
	        data:{sql:sql,pageIndex:page,rowkey:rowkey},
	        contentType: "application/json; charset=utf-8",
	        dataType: "json",
	        cache: false,
	        success: function (obj) {
        			if(obj.status==1 && obj.obj){
            		var json = obj.obj;
            		if(json){
            			$("#head_tr").append('<th>日期</th>');
                		$.each(json.title, function(key, value) {
                				if(key != 'rowkey'){
                					$("#head_tr").append('<th colspan="2">'+value+'</th>');
                				}
    	    				});
                		var nodes = document.getElementById("head_tr").childNodes;
                		if(nodes.length==1){
                			$("#head_tr").html('');
                			alert("没有查询到数据!");
                			return;
                		}
                		var trHead = $("<tr></tr>");
                		trHead.append("<td>每天的情况</td>");
                		$.each(json.title, function(key, value) {
                			if(key != 'rowkey'){
                				trHead.append("<td>数量</td>");
                    			trHead.append("<td>用户数</td>");
                			}
    	    				});
                		$("#tbody").append(trHead);
                		$.each(json.o, function(index, value) {
                			var tr = getRowData(json.title,this);
                			$("#tbody").append(tr);
    	    				});
                		getPage(json.totalPage,json.pageIndex,json.pageNumStart,json.pageNumEnd);
        				}else{
        					showMsg(obj.msg);
        				}
            		}
	        },error: function (err) {
	            alert("请求出错!");
	           }
	    });
		$('#submitBtn').button("reset");
	});
	
	function getRowData(title,o){
		var tr = $("<tr></tr>");
		tr.append("<td>"+o.rowkey.substr(96)+"</td>");
		$.each(title, function(tkey, tvalue) {
			if(tkey != 'rowkey'){
				var hasVal=false;
				$.each(o, function(okey, ovalue) {
					if(tkey==okey){
						var arr = ovalue.split("-");
						tr.append("<td>"+arr[0]+"</td>");
						tr.append("<td>"+arr[1]+"</td>");
						hasVal = true;
						return;
					}
				})
				if(!hasVal){
					tr.append("<td>0</td>");
					tr.append("<td>0</td>");
				}
			}
		});
		return tr;
	}
	
	function getPage(totalPage,pageIndex,pageNumStart,pageNumEnd){
		var pageHtml = "";
		if(totalPage>1){
			if(pageIndex!=1){
				pageHtml+='<li><a href="javascript:void(0);" onclick=toPage('+(pageIndex-1)+');>«</a></li>';
			}
			for(var i=pageNumStart;i<=pageNumEnd;i++){
				if(i==pageIndex){
					pageHtml+='<li class="active"><a href="javascript:void(0);">'+i+'</a></li>';
				}else{
					pageHtml+='<li><a href="javascript:void(0);" onclick=toPage('+i+');>'+i+'</a></li>';
				}
			}
			if(pageIndex!=totalPage){
				pageHtml+='<li><a href="javascript:void(0);" onclick=toPage('+(pageIndex+1)+');>»</a></li>';
			}
			$(".pagination").html(pageHtml);
		}else{
			$(".pagination").html('');
		}
	}
	
	function toPage(p){
		page = p;
		$('#submitBtn').click();
	}
	</script>
</body>
</html>
