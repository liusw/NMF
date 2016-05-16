<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en-us">
<head>
<%@ include file="/WEB-INF/jsp/common/meta.jsp"%>
<link rel="stylesheet" href="static/zTree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css">
<style type="text/css">
#example thead  th,#example tbody td{
	text-align: center;
}
#example_filter{
	display: none;
}
</style>
<title>角色管理-数据魔方</title>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/top.jsp">
        <jsp:param name="nav" value="sysconfig"/>
	</jsp:include>
	
	<jsp:include page="../common/sysconfigMenu.jsp">
		<jsp:param name="nav" value="privilege" />
		<jsp:param name="subnav" value="role" />
	</jsp:include>

	<div class="main">
		<jsp:include page="/WEB-INF/jsp/common/mainNav.jsp">
				<jsp:param name="actionName" value="角色管理" />
			</jsp:include>
			
		<section>
			<table id="example" class="table table-striped table-bordered display"  cellspacing="0" width="100%">
				<thead>
					<tr>
						<th>名称</th>
						<th>描述</th>
						<th width="100">操作</th>
					</tr>
				</thead>
				
				<tfoot>
	            <tr>
					<th></th>
					<th></th>
	            </tr>
	       	</tfoot>
			</table>
		</section>
	</div>
 <%@ include file="/WEB-INF/jsp/common/foot.jsp"%>

<!-- Bootstrap core JavaScript navbar-fixed-bottom
   ================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="static/js/basic.js"></script>
<script src="static/js/dataTable.js"></script>
<script type="text/javascript" src="static/zTree_v3/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="static/zTree_v3/js/jquery.ztree.excheck-3.5.js"></script>
<script src="static/js/common.js"></script>
<script type="text/javascript">
$(function(){
	$("#example").DataTable({
     "bFilter": true,
     "pagingType" : "full_numbers",
     "bDestroy" : true,
     "bProcessing" : false,
     "sAjaxSource" : "privilege/role/getData.htm",
     "bServerSide" : true,
     "ordering": false,
     "columns" : [
			{ "data": "name" },
			{ "data": "description" },
			{ "data": null,orderable: false}
         ],
       "fnRowCallback":function(nRow,aData,iDataIndex){
    	   $('td:eq(-1)',nRow).html("<button type='button' class='btn btn-primary' data-loading-text='提交中...' onclick='setResources("+aData.id+")'>设置权限</button>");
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
});

var zTree,rid;
function setResources(roleId){
	rid = roleId;
	$.ajaxSetup({async:false});
	showMsg('<div id="treeDemo" class="ztree"></div>',"设置权限");
	$.ajaxSetup({async:false});
	var setting = {
		check: {
			enable: true,
		},
		data: {
			simpleData: {
				enable: true,
				idKey: "id",
				pIdKey: "parentId",
				rootPId: 0
			}
		},
		async: {
			enable: true,
			url:"privilege/resources/setRoleResourceData.htm?roleId="+roleId
		},
		callback: {
			onCheck: zTreeOnCheck
		}
	};
	$.fn.zTree.init($("#treeDemo"), setting);
	zTree = $.fn.zTree.getZTreeObj("treeDemo");
	zTree.setting.check.chkboxType = { "Y" : "p", "N" : "s" };
	zTree.expandAll(true);
}

function zTreeOnCheck(event, treeId, treeNode) {
	var data = getJsonData2("privilege/resources/setRoleResources.htm",{resourceId:treeNode.id,check:treeNode.checked,roleId:rid});
	if(data && data.status==1){
		alert("设置成功");
	}else{
		alert("设置失败");
	}
};
</script>
</body>
</html>
