<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en-us">
<head>
<%@ include file="/WEB-INF/jsp/common/meta.jsp"%>
<link rel="stylesheet" type="text/css" href="static/css/reset.css">
<link rel="stylesheet" type="text/css" href="static/css/modal-form.css">
<style type="text/css">
.dataTables_filter {
	display: none; 
}
.form-horizontal .form-group {
    margin:0px;
    border-top:none;
}
</style>
<title>浏览器规则-数据魔方</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/top.jsp">
	<jsp:param name="nav" value="data" />
</jsp:include>

<jsp:include page="../common/sysconfigMenu.jsp">
	<jsp:param name="nav" value="config" />
	<jsp:param name="subnav" value="browser" />
</jsp:include>

<div class="main">
	<jsp:include page="/WEB-INF/jsp/common/mainNav.jsp">
		<jsp:param name="actionName" value="浏览器规则" />
	</jsp:include>
	
		<table id="example" class="table table-striped table-bordered display"  cellspacing="0" width="100%">
			<thead>
				<tr>
<!-- 					<th style="width:15px"><input type="checkbox" id='checkAll'></th> -->
					<th></th>
					<th>ID</th>
					<th>名称</th>
					<th>规则</th>
				</tr>
			</thead>
			<tfoot><tr><th></th><th></th><th></th><th></th></tr></tfoot>
		</table>
		
		<!-- Modal -->
		<div class="modal fade mf_modal" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog modal-sg">
			    <div class="modal-content">
			      <div class="mf_modal_header">
		        	 <div id="myModalLabel" class="mf_modal_header_content">添加记录</div>
		        	 <div class="mf_modal_close" data-dismiss="modal"></div>
			      </div>
			      <div class="modal-body mf_modal_body">
			      	<div class="edit_area">
				      	<form class="form-horizontal" id="resForm">
			             	<input type="hidden" id="inputId" name="id"/>
			      	 		<div class="form-group">
			      	 			<label class="col-sm-2 control-label" for="inputName">名称:</label>
			             		<div class="col-sm-9">
			             			<input type="text" id="inputName" name="name" class="form-control"/>
			             		</div>
			      	 		</div>
			      	 		<div class="form-group">
			      	 			<label class="col-sm-2 control-label" for="inputRule">匹配规则:</label>
			             		<div class="col-sm-9">
			             			<input type="text" id="inputRule" name="rule" class="form-control"/>
			             		</div>
			      	 		</div>
			          </form>
			       </div>
			   </div>
		      <div class="modal-footer mf_modal_footer">
		        	<button class="mf_form_btn" data-dismiss="modal" aria-hidden="true">取消</button>
		        	<button class="mf_form_btn" id="btnSave">确定</button>
		        	<button class="mf_form_btn" id="btnEdit">保存</button>
		      </div>
			  </div>
			</div>
		</div>
		<div class="modal fade" id="removeModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog modal-lg">
			    <div class="modal-content">
			      <div class="mf_modal_header">
		        	 <div id="myModalLabel" class="mf_modal_header_content">删除记录</div>
		        	 <div class="mf_modal_close" data-dismiss="modal"></div>
			      </div>
			      <div class="modal-body mf_modal_body">
			      	<div class="edit_area">
				      	<form class="form-horizontal">
				      	 	<div class="control-field">
				      	 		<span id="removeTips"></span>
				      	 	</div>
			          </form>
			       </div>
			   </div>
		      <div class="modal-footer mf_modal_footer">
		      	<button class="mf_form_btn" data-dismiss="modal" aria-hidden="true">取消</button>
		       	<button class="mf_form_btn" id="removeBtn">确定</button>
		      </div>
			  </div>
			</div>
		</div>
</div>
<br/><br/><br/>
<%@ include file="/WEB-INF/jsp/common/foot.jsp"%>

<!-- Bootstrap core JavaScript navbar-fixed-bottom
   ================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="static/js/basic.js"></script>
<script src="static/js/dataTable.js"></script>
<script src="static/js/bootstrap-select.min.js"></script>
<script src="static/js/common.js"></script>
<script type="text/javascript">
var qparams ={
	id:"10000001|10000002","dataType":"dataTable",
	args:{
		table:"browserRule",sort:'id',order:'desc'
	}
};
	
var table;
$(function() {
	 $("#btnSave").click(_addFun);
	 $("#btnEdit").click(_editFunAjax);
	 $("#removeBtn").click(_deleteFun);
	/*
    $("#btnEdit").hide();
    */
	//checkbox全选
	$("#checkAll").on("click", function () {
		$("input[name='checkList']").prop("checked", this.checked);
		
		if(this.checked){
			$("#example tr").addClass('selected');
		}else{
			table.$('tr.selected').removeClass('selected');
		}
	});
  
	table = $('#example').DataTable({
		"processing": true,
	 	"sServerMethod": "POST",
		"sAjaxSource" : "data/common/mysqlQuery.htm?params="+JSON.stringify(qparams),
		"bServerSide" : true,
		"bSort": false,
	 	"filter": {visibility: "hidden"},
	 	"oLanguage": commonData.oLanguage,
		"aoColumns" : [{
			"data" : "id",
			"fnCreatedCell" : function(nTd, sData,oData, iRow, iCol) {
				$(nTd).html("<input type='checkbox' name='checkList' value='" + sData + "'>");
			}
		},{
			"data" : "id"
		},{
			"data" : "name"
		},{
			"data" : "rule"
		}],
		"fnCreatedRow" : function(nRow, aData, iDataIndex) {
			//add selected class
			$(nRow).click(function() {
				if ($(this).hasClass('selected')) {
					$(this).removeClass('selected');
					$(this).find("input[name='checkList']").prop("checked",false);
				} else {
					table.$('tr.selected').find("input[name='checkList']").prop("checked",false);
					table.$('tr.selected').removeClass('selected');
					$(this).find("input[name='checkList']").prop("checked",true);
					$(this).addClass('selected');
				}
			});
		},
		"fnInitComplete" : function(oSettings, json) {
			$('<a href="#myModal" id="addFun" class="mf_button_a" data-toggle="modal"><span>新增</span></a>'
				+ '&nbsp;'
				+ '<a href="javascript:void(0);" class="mf_button_a" id="editFun"><span>修改</span></a> '
				+ '&nbsp;'
				+ '<a href="javascript:void(0);" class="mf_button_a" id="deleteFun"><span>删除</span></a>'
				+ '&nbsp;').appendTo($('.myBtnBox'));
			
			$("#addFun").click(_init);
			$("#editFun").click(_value);
			$("#deleteFun").click(_deleteList);
		},
		"sDom" : "<'row-fluid'<'span6 myBtnBox'><'span6'f>r>t<'row-fluid'<'span6'i><'span6 'p>>",
	});
});

	/**
	 * 初始化
	 */
	function _init() {
		$("#myModalLabel").html("添加记录");
		resetFrom();
		$("#btnEdit").hide();
		$("#btnSave").show();
	}

	/**
	 * 为弹出框赋值
	 */
	function _value() {
		var select = table.$('tr.selected');
		if (select.length==1) {
			$("#myModalLabel").html("编辑记录");

			$("#btnEdit").show();

			var selected = table.row(select.get(0)).data();
			$("#inputId").val(selected.id);
			$("#inputName").val(selected.name);
			$("#inputRule").val(selected.rule);

			$("#myModal").modal("show");
			$("#btnSave").hide();
		} else {
			alert(select.length==0?'请点击选择一条记录后操作。':'每次只能选择一条记录进行更新');
		}
	}

	/**
	 * 删除
	 * @private
	 */
	function _deleteList() {
		var select = table.$('tr.selected');
		if (select.length==1) {
			$("#removeTips").html("删除后没法恢复,你确定要删除选择的数据吗?");
			$("#removeModal").modal("show");
		} else {
			alert(select.length==0?'请点击选择一条记录后操作。':'每次只能选择一条记录进行操作');
		}
	}

	/**
	 * 添加数据
	 */
	function _addFun() {
		var params ={
			id:"10000005",
			args:{
				columns:'name,rule',values:{'name':$("#inputName").val(),'rule':$("#inputRule").val()},table:"browserRule"
			}
		};
		
		$.ajax({
			url : "data/common/mysqlInsert.htm?params="+JSON.stringify(params),
			type : "post",
			dataType : "json",
			success : function(data) {
				if (data.ok==1) {
					reloadTable();
				}else{
					alert(data.msg);
				}
			},error : function(error) {
				alert("添加失败");
			}
		});
	}

	/**
	 * 编辑数据
	 */
	function _editFunAjax() {
		var params ={
			id:"10000010",
			args:{
				set:{'name':$("#inputName").val(),'rule':$("#inputRule").val()},
				where:{id:$("#inputId").val()},
				table:"browserRule"
			}
		};
		
		$.ajax({
			url : "data/common/mysqlUpdate.htm?params="+JSON.stringify(params),
			type : "post",
			dataType : "json",
			success : function(data) {
				if (data.ok==1) {
					reloadTable();
				}else{
					alert(data.msg);
				}
			},error : function(error) {
				alert("添加失败");
			}
		});
	}

	/**
	 * 删除
	 */
	function _deleteFun(id) {
		var select = table.$('tr.selected');
		var selected = table.row(select.get(0)).data();
		var params ={
			id:"10000011",
			args:{
				where:{id:selected.id},
				table:"browserRule"
			}
		};
		
		$.ajax({
			url : "data/common/mysqlDelete.htm?params="+JSON.stringify(params),
			type : "post",
			dataType : "json",
			success : function(data) {
				if (data.ok==1) {
					$("#removeModal").modal("hide");
					reloadTable();
				}else{
					alert(data.msg);
				}
			},error : function(error) {
				alert("添加失败");
			}
		});
	}

	/*
	 add this plug in
	 // you can call the below function to reload the table with current state
	 Datatables刷新方法
	 table.fnReloadAjax(table.fnSettings());
	 */
	$.fn.dataTableExt.oApi.fnReloadAjax = function(oSettings) {
		//oSettings.sAjaxSource = sNewSource;
		this.fnClearTable(this);
		this.oApi._fnProcessingDisplay(oSettings, true);
		var that = this;

		$.getJSON(oSettings.sAjaxSource, null, function(json) {
			/* Got the data - add it to the table */
			for ( var i = 0; i < json.aaData.length; i++) {
				that.oApi._fnAddData(oSettings, json.aaData[i]);
			}
			oSettings.aiDisplay = oSettings.aiDisplayMaster.slice();
			that.fnDraw(that);
			that.oApi._fnProcessingDisplay(oSettings, false);
		});
	}

	/**
	 * 重置表单
	 */
	function resetFrom() {
		$('#resForm').each(function(index) {
			$('#resForm')[index].reset();
		});
	}
	
	function reloadTable(){
		$("#myModal").modal("hide");
		resetFrom();
		table.ajax.url("data/common/mysqlQuery.htm?params="+JSON.stringify(qparams)).load();
	}
</script>
</body>
</html>
