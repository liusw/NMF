<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en-us">
<head>
<%@ include file="/WEB-INF/jsp/common/meta.jsp"%>
<title>通用导出-数据魔方</title>
<link rel="stylesheet" type="text/css" href="static/daterangepicker/daterangepicker-bs3.css">
<link href="static/bootstrap-switch/dist/css/bootstrap3/bootstrap-switch.min.css" rel="stylesheet">
<style type="text/css">
#hiveColumns .checkbox-inline + .checkbox-inline,#tableParam .checkbox-inline + .checkbox-inline{
    margin-left: 0;
}
#hiveColumns .checkbox-inline,#tableParam .checkbox-inline{
    width: 200px;
    height:20px;
    line-height:20px;
    overflow: hidden;
}
.table_border{
	border: solid 1px #B4B4B4;
	border-collapse: collapse;     --折叠样式.
}
.table_border tr th{
	background:url("../../images/gray/bg_table_th.gif") repeat;
	padding-left:4px;
	height:27px;
	border: solid 1px #B4B4B4;
}
.table_border tr td{
	height:25px;
	padding:4px;
	border: solid 1px #B4B4B4;
}
.t_area{
	width:100%;
	overflow-y:visible
}
.maxWidth1{
	max-width: 200px;
	min-width: 150px;
}
.param {
    margin: 5px 0 5px 52px;
}
.input-group-btn:last-child > .btn, .input-group-btn:last-child > .btn-group {
    height: 34px;
    margin-left: -1px;
}
.task {
    border:none;
    padding-bottom:0px;
}
.glyphicon-question-sign{
	color: #ff6600;
}
.content-body form {
	padding: 0px;
}
.pdl{
	padding-left: 16px;
}
</style>
<script type="text/javascript">
function autoHeight(id){
    var iframe = document.getElementById(id);//这里括号内的"runtime"其实就是上面的ID，要改成自己上面填写的ID。
    if(iframe.Document){//ie自有属性
        //iframe.style.height = iframe.Document.documentElement.scrollHeight+20;
    		iframe.style.height="auto";
    }else if(iframe.contentDocument){//ie,firefox,chrome,opera,safari
        //iframe.height = iframe.contentDocument.body.offsetHeight+20;
        iframe.height="auto";
    }
}
</script>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/top.jsp">
		<jsp:param name="nav" value="list"/>
		<jsp:param name="action" value="list_audit"/>
	</jsp:include>

	<jsp:include page="/WEB-INF/jsp/task/userMenu.jsp">
		<jsp:param name="nav" value="list_audit" />
	</jsp:include>

	<div class="main">
		<jsp:include page="/WEB-INF/jsp/common/mainNav.jsp">
			<jsp:param name="actionName" value="所有任务列表" />
		</jsp:include>
		<table id="example" class="table table-striped table-bordered display"  cellspacing="0" width="100%">
			<thead>
			<tr>
				<th width="5%">ID</th>
				<th width="10%">任务ID</th>
				<th>申请者</th>
				<th>审批者</th>
				<th>审批时间</th>
				<th>审批结果</th>
				<th>结果通知</th>
				<th>详情</th>
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
				<th></th>
			</tr>
			</tfoot>
		</table>

	</div>

	<br/><br/>
	<%@ include file="/WEB-INF/jsp/common/foot.jsp"%>

	<!-- Bootstrap core JavaScript navbar-fixed-bottom
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="static/js/basic.js"></script>
	<script src="static/js/dataTable.js"></script>
	<script src="static/js/daterangepicker.js"></script>
	<script src="static/bootstrap-switch/dist/js/bootstrap-switch.min.js"></script>
	<script src="static/select2/js/select2.min.js"></script>
	<script src="static/js/multiselect.js"></script>
	<script src="static/js/daterange.by.js"></script>
	<script src="static/js/dateUtils.js"></script>
	<script src="static/js/common.js"></script>
	<script src="static/js/jquery.form.js"></script>
	<script src="static/js/ucuser.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$('#example tfoot th').each( function () {
				var index = $(this).index();
				if(index == 0 || index ==1 || index == 2){
					var title = $('#example thead th').eq( index ).text();
					$(this).html( '<input type="text" class="form-control input-sm" style="width:100%;" placeholder="Search ' + title +' " />' );
				}
			} );

			$.ajax({
				type: "get",
				url: 'task/audit/getAuditList.htm',
				contentType: "application/json; charset=utf-8",
				dataType: "json",
				success: function (data) {
					var json = eval(data);
					var table = $('#example').DataTable({
						"processing": true,
						data : json.data,
						"columns": [
							{ "data": "id" },
							{ "data": "taskId" },
							{ "data": "applyName" },
							{ "data": "auditUser" },
							{ "data": "auditTime" },
							{ "data": "auditResult" },
							{ "data": "resultNotice" },
							{ "data": null }
						],
						"fnRowCallback":function(nRow,aData,iDataIndex){
							if(aData.auditResult == 1){
								$('td:eq(5)',nRow).html("<span>审批通过</span>");
							}else{
								$('td:eq(5)',nRow).html("<span>审批中</span>");
							}
							if(aData.resultNotice == 0){
								$('td:eq(6)',nRow).html("<span>发送结果邮件</span>");
							}else{
								$('td:eq(6)',nRow).html("<span>不发送结果邮件</span>");
							}
							$('td:eq(-1)',nRow).html("<button class='btn btn-primary'  onclick='openDetail("+aData.id+")'>详情</button>");
						} ,
						"oLanguage": commonData.oLanguage,
						"dom": 't<"col-sm-6"l><"col-sm-6"p>'

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

		function openDetail(id){
			var taskAudit = getJsonData("task/audit/getDetail.htm",{id:id});
			var html = '<table class="table_border" width="100%">';
			if(taskAudit){
				html +="<tr><td align='center' valign='middle' class='.table-b'>申请者:</td><td>"+taskAudit.applyName+"</td></tr>" +
				"<tr><td align='center' valign='middle'>申请日期:</td><td>"+taskAudit.applyTime+"</td></tr>" +
				"<tr><td align='center' valign='middle'>申请理由:</td><td>"+taskAudit.applyCause+"</td></tr>" +
				"<tr><td align='center' valign='middle'>执行语句:</td><td><textarea class='t_area'>"+taskAudit.processInfo+"</textarea></td></tr>" +
				"<tr><td align='center' valign='middle'>审批者:</td><td>"+taskAudit.auditUser+"</td></tr>" +
				"<tr><td align='center' valign='middle'>审批时间:</td><td>"+taskAudit.auditTime+"</td></tr>" +
				"<tr><td align='center' valign='middle'>审批备注:</td><td>"+taskAudit.auditRemark+"</td></tr>" +
				"<tr><td align='center' valign='middle'>结果地址:</td><td><a href='"+taskAudit.dataUrl+"'>"+taskAudit.dataUrl+"</a></td></tr>"
			}
			html +=	'</table>';
			showMsg(html,"审批详情");
		}
	</script>
</body>
</html>