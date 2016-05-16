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
</style>
<title>平台Feed配置-数据魔方</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/top.jsp">
	<jsp:param name="nav" value="data" />
	<jsp:param name="showPlatSelect" value="true" />
</jsp:include>

<jsp:include page="../common/sysconfigMenu.jsp">
	<jsp:param name="nav" value="config" />
	<jsp:param name="subnav" value="feedPlat" />
</jsp:include>

<div class="main">
	<jsp:include page="/WEB-INF/jsp/common/mainNav.jsp">
		<jsp:param name="actionName" value="平台Feed配置" />
	</jsp:include>
		
		<table id="example" class="table table-striped table-bordered display"  cellspacing="0" width="100%">
			<thead>
				<tr>
					<th style="width:15px"><input type="checkbox" id='checkAll'></th>
					<th>ID</th>
					<th>平台</th>
					<th>Feed ID</th>
					<th>Feed名称</th>
					<th>类型</th>
					<th>状态</th>
					<th>更新者</th>
					<th>创建时间</th>
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
					<th></th>
	            </tr>
       		</tfoot>
		</table>
		<!-- Modal -->
		<div class="modal fade mf_modal" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog modal-lg">
			    <div class="modal-content">
			      <div class="mf_modal_header">
		        	 <div id="myModalLabel" class="mf_modal_header_content">添加记录</div>
		        	 <div class="mf_modal_close" data-dismiss="modal"></div>
			      </div>
			      <div class="modal-body mf_modal_body">
			      	<div class="edit_area">
				      	<form class="form-horizontal" id="resForm">
				      		<div class="control-field">
				      	 		<div class="field_body">
				      	 			<label class="field_label" for="inputPlat">平台:</label>
				             		<div class="mf_field_input">
				             			<select id="inputPlat" name="plat" class="feild_value">
				             			</select>
				             			<div class="msg-tip"></div>
				             		</div>
				      	 		</div>
				      	 	</div>
				      	 	<div class="control-field">
				      	 		<div class="field_body">
				      	 			<label class="field_label" for="inputFeedId">Feed ID:</label>
				             		<div class="mf_field_input">
				             			<input type="text" id="inputFeedId" name="feedId" class="feild_value"/>
				             			<div class="msg-tip"></div>
				             		</div>
				      	 		</div>
				      	 	</div>
				      	 	<div class="control-field">
				      	 		<div class="field_body">
				      	 			<label class="field_label" for="inputType">类型:</label>
				             		<div class="mf_field_input">
				             			<select id="inputType" name="type" class="feild_value">
				             				<option value="1">Feed</option>
				             				<option value="2">FbSource来源明细</option>
				             				<option value="3">FbRef来源明细</option>
				             			</select>
				             			<div class="msg-tip"></div>
				             		</div>
				      	 		</div>
				      	 	</div>
				      	 	<div class="control-field">
				      	 		<div class="field_body">
				      	 			<label class="field_label" for="inputStatus">状态:</label>
				             		<div class="mf_field_input">
				             			<select id="inputStatus" name="status" class="feild_value">
				             				<option value="0">启用</option>
				             				<option value="1">停用</option>
				             			</select>
				             			<div class="msg-tip"></div>
				             		</div>
				      	 		</div>
				      	 	</div>
			          </form>
			       </div>
			   </div>
		      <div class="modal-footer mf_modal_footer">
		        	<button class="mf_form_btn" data-dismiss="modal"
		                aria-hidden="true">取消
		        	</button>
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
		      		<button class="mf_form_btn" data-dismiss="modal"
		                aria-hidden="true">取消
		        	</button>
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
<script src="static/js/common.js"></script>
<script type="text/javascript">
var table;

$(document).ready(function() {
	initModal();
	
	initTopPlats(function(){
		if(table){
			table.ajax.url("config/feedPlat/getData.htm?plat=" + $("#topPlats").val()).load();
		}
	});
	
	$("#btnSave").click(_addFun);
    $("#btnEdit").hide();
    $("#btnEdit").click(_editFunAjax);
    $("#removeBtn").click(_deleteFun);
	
    //checkbox全选
    $("#checkAll").on("click", function () {
    	$("input[name='checkList']").prop("checked", this.checked);
    	
    	if(this.checked){
    		$("#example tr").addClass('selected');
    	}else{
    		table.$('tr.selected').removeClass('selected');
    	}
    });	
			
	$('#example tfoot th').each( function (index) {
        var title = $('#example thead th').eq( $(this).index() ).text();
        if(0 == index || 8 == index){
        	return;
        }
        
        if(5 == index){
        	$(this).html('<select class="form-control" style="width:100%;" >' + 
    		     	'<option value="-1">全部</option>' + 
    			     '<option value="1">Feed</option>' + 
    			     '<option value="2">FbSource来源明细</option>' +
    			     '<option value="3">FbRef来源明细</option>' +
    		     '</select>'); 
        	return;
        }
        
        if(6 == index){
        	$(this).html('<select class="form-control" style="width:100%;" >' + 
    		     	'<option value="-1">全部</option>' + 
    			     '<option value="0">启用</option>' + 
    			     '<option value="1">停用</option>' +
    		     '</select>'); 
        	return;
        }
        
        $(this).html( '<input type="text" class="form-control input-sm" style="width:100%;" placeholder="Search ' + title +' " />' );
    } );
	
	table = $('#example').DataTable({
		 dom: "Tfrtip",
		 "processing": true,
	     "sServerMethod": "POST",
	     "sAjaxSource" : "config/feedPlat/getData.htm?plat=" + $("#topPlats").val(),
	     "bServerSide" : true,
	     "bSort": false,	   
	      
	      "columnDefs": [
			{
			    "render": function ( data, type, row ) {
				   	 var val = "未定义";
				   	 if(1 == data){
				   		 val = "Feed";
				   	 }else if(2 == data){
				   		 val = "FbSource来源明细";
				   	 }else if(3 == data){
				   		 val = "FbRef来源明细";
				   	 }
			   	 
			        return val;
			    },
			    "targets": 5
			 },
             {
              "render": function ( data, type, row ) {
             	 var val = "未定义";
             	 if(0 == data){
             		 val = "启用";
             	 }else if(1 == data){
             		 val = "停用";
             	 }
             	 
                 return val;
              },
              "width": "12%",
              "targets": 6
             },
             
             {
                 "targets"  : [0,1,2,3,4,5,6,7,8],
                 "orderable": false,
	          },
	          
	          { "width": "15%", "targets": 8 }
	       ],
	      
		 "oLanguage":{//汉化
               "sLengthMenu":"显示 _MENU_ 条记录",
               "sZeroRecords":"没有检索到数据",
               "sInfo":"显示 _START_ to _END_ 条记录；共有 _TOTAL_ 条记录",
               "sInfoEmpty":"没有数据",
               "sProcessing":"正在加载数据......",
               "sSearch":"查询",
               "sInfoFiltered": "(过滤自 _MAX_ 条记录)",
               "oPaginate":{
                   "sFirst":"首页",
                   "sPrevious":"前页",
                   "sNext":"后页",
                   "sLast":"尾页"
             },
	       },
	       
	       "aoColumns": [
                 {
                     "data": "id",
                     "fnCreatedCell": function (nTd, sData, oData, iRow, iCol) {
                         $(nTd).html("<input type='checkbox' name='checkList' value='" + sData + "'>");
                     }
                 },
                 { "data": "id"},
                 { "data": "plat" },
                 { "data": "feedId" },
                 { "data": "feedName" },
                 { "data": "feedType" },
                 { "data": "status"},
                 { "data": "operator"},
                 { "data": "createTime"},
             ], 
             
              "fnCreatedRow": function (nRow, aData, iDataIndex) {
                 //add selected class
                 $(nRow).click(function () {
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
             
  	      "fnInitComplete": function (oSettings, json) {
  	            $('<a href="#myModal" id="addFun" class="mf_button_a" data-toggle="modal"><span>新增</span></a>' + '&nbsp;' +
  	            '<a href="javascript:void(0);" class="mf_button_a" id="editFun"><span>修改</span></a> ' + '&nbsp;' +
  	            '<a href="javascript:void(0);" class="mf_button_a" id="deleteFun"><span>删除</span></a>' + '&nbsp;').appendTo($('.myBtnBox'));
  	            $("#addFun").click(_init);
  	            $("#editFun").click(_value);
  	            $("#deleteFun").click(_deleteList);

  	       },
             
  	      "sDom": "<'row-fluid'<'span6 myBtnBox'><'span6'f>r>t<'row-fluid'<'span6'i><'span6 'p>>",
	});
	
	table.columns().eq( 0 ).each( function ( colIdx ) {
		var domName = "input";
		if(colIdx == 5 || colIdx == 6){
			domName = "select";
		}
		
		$(domName,table.column(colIdx).footer()).on(domName=='input'?'input':'change', function (){
				table.column(colIdx).search(this.value).draw();
		 });
	 });	
		
});

/**
 * 初始化
 */
function _init() {
	$("#myModalLabel").html("添加记录");
    resetFrom();
    addSelectContent();
    $("#btnEdit").hide();
    $("#btnSave").show();
}

/**
 * 为弹出框赋值
 */
function _value() {
    if (table.$('tr.selected').get(0)) {
    	$("#myModalLabel").html("编辑记录");

        $("#btnEdit").show();
        addSelectContent();
        
        var selected = table.row(table.$('tr.selected').get(0)).data();
        $("#inputPlat").val(selected.plat);
        $("#inputFeedId").val(selected.feedId);
        $("#inputType").val(selected.feedType);
        $("#inputStatus").val(selected.status);
               
        $("#myModal").modal("show");
        $("#btnSave").hide();
    } else {
        alert('请点击选择一条记录后操作。');
    }
}
 
/**
 * 删除
 * @private
 */
function _deleteList() {
    var str = '';
    $("input[name='checkList']:checked").each(function (i, o) {
        str += $(this).val();
        str += ",";
    });
    if (str.length > 0) {
        var IDS = str.substr(0, str.length - 1);
        $("#removeTips").html("确定要删除的数据集id为" + IDS + "?");
        
        $("#removeModal").modal("show");
    } else {
        alert("至少选择一条记录操作");
    }
}
 
/**
 * 添加数据
 * @private
 */
function _addFun() {
    var jsonData = {
        'data[plat]': $("#inputPlat").val(),
        'data[feedId]': $("#inputFeedId").val(),
        'data[feedType]': $("#inputType").val(),
        'data[status]': $("#inputStatus").val(),
    };
    $.ajax({
        url: "config/feedPlat/create.htm",
        data: jsonData,
        type: "post",
        dataType:"json",
        success: function (backdata) {
            if (!backdata.error) {
            	alert(backdata.result);
                $("#myModal").modal("hide");
                resetFrom();
                table.ajax.url("config/feedPlat/getData.htm?plat=" + $("#topPlats").val()).load();
            } else{
                alert(backdata.result + ": " + backdata.error);
            }
        }, error: function (error) {
            console.log(error);
        }
    });
}

/**
 * 编辑数据
 */
function _editFunAjax() {
	 var jsonData = {
        'data[id]': $("input[name='checkList']:checked:eq(0)").val(),
        'data[plat]': $("#inputPlat").val(),
        'data[feedId]': $("#inputFeedId").val(),
        'data[feedType]': $("#inputType").val(),
        'data[status]': $("#inputStatus").val(),
	};
    $.ajax({
        type: 'POST',
        url: 'config/feedPlat/edit.htm',
        data: jsonData,
        dataType:"json",
        success: function (backdata) {
        	if (!backdata.error) {
            	alert(backdata.result);
                $("#myModal").modal("hide");
                resetFrom();
                table.ajax.url("config/feedPlat/getData.htm?plat=" + $("#topPlats").val()).load();
            } else{
                alert(backdata.result + ": " + backdata.error);
            }
        }
    });
}

/**
 * 删除
 * @param id
 * @private
 */
function _deleteFun(id) {
	var ids = [];
	$("input[name='checkList']:checked").each(function (i, o) {
		ids[i] = $(this).val();
    });
	
	var jsonData = {
	        'id[]': ids,
	};
	
    $.ajax({
        url: "config/feedPlat/remove.htm",
        data: jsonData,
        type: "post",
        dataType:"json",
        success: function (backdata) {
        	if (!backdata.error) {
            	alert(backdata.result);
                $("#removeModal").modal("hide");
                resetFrom();
                table.ajax.url("config/feedPlat/getData.htm?plat=" + $("#topPlats").val()).load();
            } else{
                alert(backdata.result + ": " + backdata.error);
            }
        }, error: function (error) {
            console.log(error);
        }
    });
}

/*
 add this plug in
 // you can call the below function to reload the table with current state
 Datatables刷新方法
 table.fnReloadAjax(table.fnSettings());
 */
$.fn.dataTableExt.oApi.fnReloadAjax = function (oSettings) {
    //oSettings.sAjaxSource = sNewSource;
    this.fnClearTable(this);
    this.oApi._fnProcessingDisplay(oSettings, true);
    var that = this;
 
    $.getJSON(oSettings.sAjaxSource, null, function (json) {
        /* Got the data - add it to the table */
        for (var i = 0; i < json.aaData.length; i++) {
            that.oApi._fnAddData(oSettings, json.aaData[i]);
        }
        oSettings.aiDisplay = oSettings.aiDisplayMaster.slice();
        that.fnDraw(that);
        that.oApi._fnProcessingDisplay(oSettings, false);
    });
}

/**
 * 初始化弹出层
 */
function initModal() {
    $('#myModal').on('show', function () {
        $('body', document).addClass('modal-open');
        $('<div class="modal-backdrop fade in"></div>').appendTo($('body', document));
    });
    $('#myModal').on('hide', function () {
        $('body', document).removeClass('modal-open');
        $('div.modal-backdrop').remove();
    });
}
 
/**
 * 重置表单
 */
function resetFrom() {
    $('#resForm').each(function (index) {
        $('#resForm')[index].reset();
    });
}

/*为弹出框中的select组件添加内容并进行初始化*/
function addSelectContent(){
	var data = getJsonData("config/getPlat.htm");
	if(data!=null){
		$.each(data, function(index, value) {
			var label = value.platName + "(" + value.plat + ")";
			$("#inputPlat").append('<option value="' + value.plat + '">' + label + '</option>');
		});
	}

	$("#inputPlat").val($("#topPlats").val());
}
</script>
</body>
</html>
