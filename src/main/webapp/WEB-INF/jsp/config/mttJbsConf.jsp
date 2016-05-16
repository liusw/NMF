<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en-us">
<head>
<%@ include file="/WEB-INF/jsp/common/meta.jsp"%>
<title>锦标寒配置-数据魔方</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/top.jsp">
	<jsp:param name="nav" value="data" />
</jsp:include>

<jsp:include page="../common/sysconfigMenu.jsp">
	<jsp:param name="nav" value="config" />
	<jsp:param name="subnav" value="mttJbsConf" />
</jsp:include>

<div class="main">
	<jsp:include page="/WEB-INF/jsp/common/mainNav.jsp">
		<jsp:param name="actionName" value="锦标赛配置" />
	</jsp:include>
	
	<div class="content-body">
		<table id="example" class="table table-striped table-bordered display dtable">
			<thead>
				<tr>
					<th>ID</th><th>平台</th><th>名称</th><th>展示名称</th><th>操作</th>
				</tr>
			</thead>
			<tfoot></tfoot>
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
<script src="static/js/multiselect.js"></script>
<script src="static/js/common.js"></script>
<script type="text/javascript">
var table,navForm;
$(document).ready(function() {
	navForm = navForm.init({'showPlat':true,platChangeCallback:loadTable});
	loadTable();
});

function loadTable(){
	var params ={
		id:"10000001|10000002","dataType":"dataTable",
		args:{
			plat:$("#navPlat").val(),table:"d_mtt_jbs_config",sort:'id',order:'desc'
		}
	};
	
	table = $("#example").DataTable({
	     "bFilter": true,
	     "pagingType" : "full_numbers",
	     "bDestroy" : true,
	     "bProcessing" : false,
	     "sAjaxSource" : "data/common/mysqlQuery.htm?params="+JSON.stringify(params),
	     "bServerSide" : true,
	     "ordering": true,
	     "sServerMethod": "POST",
	     "columns" : [
	        	{ "data": "id","visible": false},
				{ "data": "plat",sortable:false},
				{ "data": "name",sortable:false},
				{ "data": "showName",sortable:false},
				{ "data": null,
					orderable: false,
					render:function(data, type, row){
						return '<button type="button" class="btn btn-primary btn-xs" onclick="showUpdate('+row.id+')">修改</button>';
					}
				}
	         ],
	       "oLanguage": commonData.oLanguage,
	       "dom": 't<"bottom fl"l><"bottom"p>'
		});
}

function showUpdate(id){
	var params ={id:"10000001",dataType:'singleObj',args:{id:id,table:"d_mtt_jbs_config"}};
	var data = getJsonData("data/common/mysqlQuery.htm",{params:JSON.stringify(params)});
	if(data!=null && data.result==1) {
		var html ='<form id="autorunInfo" class="form-horizontal">'+
				'<div class="form-group">'+
					'<label class="col-sm-2 control-label">平台</label>'+
					'<div class="col-sm-10">'+
					'<input type="text" autocomplete="off" readonly="readonly" class="form-control" id="plat" value="'+data.loop.plat+'">'+
					'</div>'+
				'</div>'+
				'<div class="form-group">'+
				'<label class="col-sm-2 control-label">名称</label>'+
				'<div class="col-sm-10">'+
				'<input type="text" autocomplete="off" readonly="readonly" class="form-control" id="name" value="'+data.loop.name+'">'+
				'</div>'+
			'</div>'+
				'<div class="form-group">'+
					'<label class="col-sm-2 control-label">显示名称</label>'+
					'<div class="col-sm-10">'+
					'<input type="text" autocomplete="off" class="form-control" id="showName" value="'+data.loop.showName+'">'+
					'</div>'+
				'</div>'+
			'</form>';
		showMsg(html,"编辑",update,data.loop.id);
	}
}

function update(id){
	var params ={id:"10000007",args:{id:id,showName:$("#showName").val()}};
	var data = getJsonData("data/common/mysqlUpdate.htm",{params:JSON.stringify(params)});
	if(data){
		if(data.ok==1){
			refresh();
		}else{
			showMsg(data.msg,"提示");
		}
	}else{
		showMsg("更新出现异常","提示");
	}
}
</script>
</body>
</html>
