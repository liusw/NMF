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
#example_filter{
	display: none;
}
.checkbox-label{
	line-height: 20px;
	height: 20px;
	width: 150px;
	overflow: hidden;
}

.first{
	margin-left: 10px;
   margin-top: 0;
}
</style>
<title>用户管理-数据魔方</title>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/top.jsp">
        <jsp:param name="nav" value="sysconfig"/>
	</jsp:include>
	
	<jsp:include page="../common/sysconfigMenu.jsp">
		<jsp:param name="nav" value="privilege" />
		<jsp:param name="subnav" value="user" />
	</jsp:include>

	<div class="main">
		<jsp:include page="/WEB-INF/jsp/common/mainNav.jsp">
			<jsp:param name="actionName" value="用户管理" />
		</jsp:include>
		
		<table id="example" class="table table-striped table-bordered display"  cellspacing="0" width="100%">
			<thead>
				<tr>
					<th>工号</th>
					<th>英文名</th>
					<th>中文名</th>
					<th>邮箱</th>
					<th>操作</th>
				</tr>
			</thead>
			<tfoot>
            <tr>
            <th></th><th></th><th></th><th></th><th></th>
            </tr>
       	</tfoot>
		</table>
	</div>
<%@ include file="/WEB-INF/jsp/common/foot.jsp"%>

<!-- Bootstrap core JavaScript navbar-fixed-bottom
   ================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="static/js/basic.js"></script>
<script src="static/js/dataTable.js"></script>
<script src="static/js/common.js"></script>
<script type="text/javascript">
$(function(){
	$('#example tfoot th').each(function (index) {
      var title = $('#example thead th').eq($(this).index()).text();
		if(index==$("#example tfoot th").length-1){
			 $(this).html("");
		}else{
        $(this).html('<input type="text" class="form-control input-sm" style="width:100%;" placeholder="Search ' + title +' " />');
		}
    });
	var table = $("#example").DataTable({
     "bFilter": true,
     "pagingType" : "full_numbers",
     "bDestroy" : true,
     "bProcessing" : false,
     "sAjaxSource" : "privilege/user/getData.htm",
     "bServerSide" : true,
     "ordering": false,
     "columns" : [
			{ "data": "code" },
			{ "data": "username" },
			{ "data": "realName" },
			{ "data": "email" },
			{ "data": null,orderable: false}
         ],
       "fnRowCallback":function(nRow,aData,iDataIndex){
    	   $('td:eq(-1)',nRow).html("<button type='button' class='btn btn-primary' data-loading-text='提交中...' onclick='setRole("+aData.id+")'>分配角色</button>");
         } ,
      "oLanguage":{//汉化
             "sLengthMenu":"显示 _MENU_ 条记录",
             "sZeroRecords":"没有检索到数据",
             "sInfo":"从 _START_ 到 _END_ /共 _TOTAL_ 条数据",
             "sInfoEmpty":"没有数据",
             "sProcessing":"正在加载数据......",
             "sSearch":"查询",
             "sInfoFiltered": "(从 _MAX_ 条数据中检索)",
             "oPaginate":{
                 "sFirst":"首页",
                 "sPrevious":"前页",
                 "sNext":"后页",
                 "sLast":"尾页"
             }
         }
	});
	
	table.columns().eq(0).each( function (colIdx) {
	   $('input',table.column( colIdx ).footer()).on('keyup change', function (){
	   	table.column(colIdx).search(this.value).draw();
	    });
	});
});

function setRole(id){
	var data = getJsonData2("privilege/role/getAllRole.htm",{userId:id},true);
	var html = '<form class="form-inline">';
	if(data){
		html +='<div class="myrow">';
		$.each(data, function(index, value) {
			var check = "";
			if(value.check==true){
				check = ' checked="checked"';
			}
			html += '<label class="checkbox-inline checkbox-label '+(index==0?'first':'')+'" title="'+ value.name+'">'+
					  '<input type="checkbox" name="column" title="'+ value.name+'" value="'+value.id+'" '+check+' onclick="updateRole(this,'+id+')"> '+value.name+'</label>';
		});
		html +="</div>";
	}
	html +=	'</form>';
	showMsg(html,"添加角色");
}

function updateRole(o,userId){
	var check = $(o).is(":checked");
	var roleId = $(o).val();
	var data = getJsonData("privilege/role/setUserRole.htm",{userId:userId,check:check,roleId:roleId});
	if(data && data.status==1){
		alert("设置成功");
	}else{
		alert("设置失败");
		check?$(o).attr("checked",false):$(o).attr("checked",true);
	}
}
</script>
</body>
</html>
