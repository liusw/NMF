<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en-us">
<head>
<%@ include file="/WEB-INF/jsp/common/meta.jsp"%>
<title>单用户查询-数据魔方</title>
<style type="text/css">
.main{
	overflow-x: auto; 
	padding: 50px 15px 30px;
}
thead th{
	text-align: center;
}
</style>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/top.jsp">
		<jsp:param name="nav" value="data" />
	</jsp:include>

	<jsp:include page="/WEB-INF/jsp/common/dataMenu.jsp">
		<jsp:param name="nav" value="customQuery" />
		<jsp:param name="subnav" value="hbsql" />
	</jsp:include>
	
	<div class="main">
		<jsp:include page="/WEB-INF/jsp/common/mainNav.jsp">
			<jsp:param name="actionName" value="单用户查询" />
			<jsp:param name="helpId" value="107" />
		</jsp:include>
		
		<form role="form">
			<div class="row">
				<div class="form-group col-xs-8">
					<textarea class="form-control" rows="3" id="sql" placeholder="示例：select * from tablename where _plat=604 and _uid=23 and _tm<=20150309 and _tm>=20150306（只支持简单单表查询）" style="border-radius: 5px 5px 0 5px;"  autocomplete="off"></textarea>
				</div>
			</div>
			<div class="row">
				<div class="form-group col-xs-12">
					<button type="button" class="btn btn-default" id="submitBtn" data-loading-text="提交中..."  autocomplete="off">查询</button>
				</div>
			</div>
		</form>
		<table class="table table-bordered" style="padding:0px; margin:0px;">
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
	<script src="static/js/common.js"></script>
	<script type="text/javascript">
		var page=1;
		
		$('#submitBtn').click(function(){
			$('#submitBtn').button("loading");
			
			page=1;
			loadDate();
			
			$('#submitBtn').button("reset");
		});
		
		function loadDate(){
			if(isEmpty($("#sql").val())){
				showMsg("请输入sql语句！");
				return false;
			}
			
			$("#head_tr").html('');
			$("#tbody").html('');
			$(".pagination").html('');
			$.ajax({
		        type: "get",
		        async: false,
		        url: "data/hbase/query.htm",
		        data:{sql:$("#sql").val(),pageIndex:page},
		        contentType: "application/json; charset=utf-8",
		        dataType: "json",
		        cache: false,
		        success: function (obj) {
	        			if(obj.status==1 && obj.obj){
	            		var json = obj.obj;
	            		$.each(json.title, function(key, value) {
		    					$("#head_tr").append('<th>'+value+'</th>');
		    				});
	            		$.each(json.o, function(index, value) {
	            			var tr = getRowData(json.title,this);
	            			$("#tbody").append(tr);
		    				});
	            		getPage(json.totalPage,json.pageIndex,json.pageNumStart,json.pageNumEnd);
	    				}else{
	    					showMsg(obj.msg);
	    				}
		        },error: function (err) {
		            alert("请求出错!");
		           }
		    });
		}
		
		function getRowData(title,o){
			var tr = $("<tr></tr>");
			$.each(title, function(tkey, tvalue) {
				var hasVal=false;
				$.each(o, function(okey, ovalue) {
					if(tkey==okey){
						tr.append("<td>"+ovalue+"</td>");
						hasVal = true;
						return;
					}
				})
				if(!hasVal){
					tr.append("<td></td>");
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
			loadDate();
		}
	</script>
</body>
</html>
