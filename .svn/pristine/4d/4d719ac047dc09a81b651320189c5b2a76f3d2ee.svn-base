<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/tld/privilege" prefix="privilege"%>
<!DOCTYPE html>
<html lang="en-us">
<head>
<%@ include file="/WEB-INF/jsp/common/meta.jsp"%>
<title>需求详情-数据魔方</title>
<link rel="stylesheet" href="static/kindeditor-4.1.10/themes/default/default.css" />
<link rel="stylesheet" href="static/kindeditor-4.1.10/plugins/code/prettify.css" />
<style>
#tableDiv{
	margin-bottom:50px;
}
.table{
	margin-bottom:0
}
.myWell{
	 border: 1px solid #e3e3e3;
}
.form-horizontal .form-group{
	margin-left:0;
	margin-right:0;
}
.navRight {
    color: #FF6600;
    cursor: pointer;
    float: right;
    height: 38px;
    line-height: 38px;
    padding-right: 20px;
}
#formDiv .content{
	height: auto;
	min-height:30px;
}
.background-red{
	background-color : #FABDBD;
} 
.background-blue{
	background-color : #8DD4F2;
}
.background-green{
	background-color : #D7EEDE;
}
#remarkTable thead{
	background: #e8e8e8;
}
.dataTables_wrapper{
	overflow: auto;
}
</style>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/top.jsp">
		<jsp:param name="nav" value="data" />
	</jsp:include>

	<jsp:include page="/WEB-INF/jsp/common/dataMenu.jsp">
		<jsp:param name="nav" value="data" />
		<jsp:param name="subnav" value="dataRqmtDetail" />
	</jsp:include>

	<div class="main">
		<div id="breadcrumb">
			<a href="/"><i class="glyphicon glyphicon-home"></i> 首页</a> <a class="current">需求详情</a>
			<span class="navRight">
				<button type="button" class="btn btn-primary btn-sm" onclick="window.location.href='dataRqmt/addDataRqmt.htm';">
				    <span class="glyphicon glyphicon-plus-sign" ></span>&nbsp;&nbsp;添加需求
				</button>
				<privilege:operate url="data/dataRqmtAdmin">
				  	<button type="button" class="btn btn-primary btn-sm" onclick="forAllotDataRqmt();">
					    <span class="glyphicon glyphicon-share-alt" ></span>&nbsp;分配
					</button>
				</privilege:operate>
			</span>
		</div>
		<div id="formDiv" class="myWell col-md-8 col-md-offset-2">
			<form class="form-horizontal">
				<div class="form-group col-md-12">
					<label class="col-md-2 control-label"> 需求标题: </label>
					<div class="col-md-10">
		            	<div id="dataTtile" class="form-control content"></div>
					</div>
				</div>
				<div class="form-group col-md-12">
					<div class="row">
		            	<div class="col-md-6">
		            		<label class="col-md-4 control-label"> 提交人: </label>
							<div class="col-md-8">
				            	<div class="form-control content" id="userName"></div>
							</div>
						</div>
						<div class="col-md-6">
							<label class="col-md-4 control-label"> 要求时间: </label>
							<div class="col-md-8">
				            	<div class="form-control content" id="needTime"></div>
							</div>
						</div>
					</div>
				</div>
				<div class="form-group col-md-12">
					<label class="col-md-2 control-label"> 数据用途: </label>
					<div class="col-md-10">
		            	<div class="form-control content" id="dataPurpose"></div>
					</div>
				</div>
				<div class="form-group col-md-12">
					<label class="col-md-2 control-label"> 需技术协助的原因: </label>
					<div class="col-md-10">
						<div class="row">
		                   <div class="col-md-12">
		                     <div class="form-control content" id="needReason" ></div>
		                   </div>
	                	</div>
					</div>
				</div>
				<div class="form-group col-md-12">
					<label class="col-md-2 control-label"> 平台站点: </label>
					<div class="col-md-10">
						<div class="row">
		                   <div class="col-md-6">
		                     <div id="plats" class="form-control content"></div>
		                   </div>
		                   <div class="col-md-6">
		                     <div id="sid" class="form-control content" ></div>
		                   </div>
		                </div>
					</div>
				</div>
				<div class="form-group col-md-12">
					<label class="col-md-2 control-label"> 数据时间范围: </label>
					<div class="col-md-10">
						<div class="row">
							<div class="col-md-6">
								<div id="stime" class="form-control content"></div>
							</div>
	                  		<div class="col-md-6">
								<div id="etime" class="form-control content" ></div>
							</div>
						</div>
					</div>
				</div>
				<div class="form-group col-md-12">
					<label class="col-md-2 control-label"> 目标用户: </label>
					<div class="col-md-10">
						<div class="form-control content" id="resultUser"></div>
					</div>
				</div>
				<div class="form-group col-md-12">
					<label class="col-md-2 control-label"> 参数类型: </label>
					<div class="col-md-10">
						<div class="row">
		                   <div class="col-md-12">
		                     <div class="form-control content" id="itemType"></div>
		                   </div>
	                	</div>
					</div>
				</div>
				<div class="form-group col-md-12">
					<label class="col-md-2 control-label"> 参数链接: </label>
					<div class="col-md-10">
						<div class="row">
		                   <div class="col-md-12">
		                     <div class="form-control content" id="itemLink"></div>
		                   </div>
	                	</div>
					</div>
				</div>
				
				<div class="form-group col-md-12">
					<label class="col-md-2 control-label"> 需求数据: </label>
					<div class="col-md-10">
						<div class="form-control content" id="dataItem"></div>
					</div>
				</div>
				<div class="form-group col-md-12">
					<label class="col-md-2 control-label"> 备注: </label>
					<div class="col-md-10">
						<div class="row">
		                   <div class="col-md-12">
		                     <div class="form-control content" id="remark"></div>
		                   </div>
	                	</div>
					</div>
				</div>
			</form>
		</div>
		
		
		<div class="myWell col-md-12">
			<form class="form-horizontal">
				<div class="form-group col-md-12">
					<label class="col-md-2 control-label"> 操作类型: </label>
					<div class="col-md-10">
		            	<label class="radio-inline">
						  <input type="radio" name="operateType" value="1"> 变更需求
						</label>
						<label class="radio-inline">
						  <input type="radio" name="operateType" value="2"> 交付
						</label>
						<label class="radio-inline">
						  <input type="radio" name="operateType" value="3"> 评论
						</label>
						<label class="radio-inline">
						  <input type="radio" name="operateType" value="4"> 停止
						</label>
						<label class="radio-inline">
						  <input type="radio" name="operateType" value="5"> 其他
						</label>
					</div>
				</div>
				<div class="form-group col-md-12">
					<label class="col-md-2 control-label"> 备注: </label>
					<div class="col-md-8">
						<textarea class="form-control content" name="content" rows="10" placeholder="" id="optRemark"></textarea>
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-5 control-label"></label>
					<div class="col-md-7">
						<div class="row">
							<div class="col-md-4">
								<button type="button" class="btn btn-default" id="addDataRqmtOpt" data-loading-text="提交中...">提交</button>
							</div>
						</div>
					</div>
				</div>
			</form>
		</div>
		
		<div class="myWell col-md-12" id="tableDiv">
			<table id="remarkTable" class="table table-bordered display"  cellspacing="0" width="100%">
				<thead>
					<tr>
						<th align="center" width="10%">操作人员</th>
						<th align="center" width="20%">操作类型@时间</th>
						<th align="center" width="70%">备注信息</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	<input type="hidden" id="dataRqmtId">
	<input type="hidden" id="dataRqmtCreateTime">
	<input type="hidden" id="dataRqmtCreater">
	
	
	<div class="modal fade" tabindex="-1" role="dialog" aria-hidden="true" id="allotDataRqmtMsg">
		<div class="modal-dialog modal-lg">
		   <div class="modal-content">
		     <div class="modal-header">
		      <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
		      <h5 class="modal-title" id="msgColTitle">分配需求</h5></div>
		      <div class="modal-body">
		      	<div>
			      	<form role="form">
							<div class="row">
								<div class="form-group col-md-6">
									<h6>处理时间:</h6>
									<input type="text" id="stm" class="form-control time" placeholder="开始时间" autocomplete="off">
								</div>
								<div class="form-group col-md-6">
									<h6>&nbsp;</h6>
									<input type="text" id="etm" class="form-control time" placeholder="结束时间" autocomplete="off">
								</div>
							</div>
							<div class="row">
								<div class="form-group col-md-12">
									 <h6>日志处理人员:</h6>
									 <label class="checkbox-inline">
									 	<input type="checkbox" name="logger" value="黄奕能"/>黄奕能
									 </label>
									  <label class="checkbox-inline">
									 	<input type="checkbox" name="logger" value="郑壮杰"/>郑壮杰
									 </label>
									  <label class="checkbox-inline">
									 	<input type="checkbox" name="logger" value="黄万新"/>黄万新
									 </label>
								</div>
							</div>
							<div class="row">
								<div class="form-group col-md-12">
									  <h6>备注:</h6>
									 <textarea id="otherRemark" class="form-control"></textarea>
								</div>
							</div>
					</form>
				</div>
		      </div>
		      <div class="modal-footer">
		      	<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
		      	<button type="button" class="btn btn-primary" onclick="allotDataRqmt();">确定</button>
		      </div>
		   </div>
		</div>
	</div>

	<%@ include file="/WEB-INF/jsp/common/foot.jsp"%>

<!-- Bootstrap core JavaScript navbar-fixed-bottom
   ================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="static/js/basic.js"></script>
<script src="static/js/datetimepicke.js"></script>
<script src="static/js/dataTable.js"></script>
<script charset="utf-8" src="static/kindeditor-4.1.10/kindeditor-min.js"></script>
<script charset="utf-8" src="static/kindeditor-4.1.10/lang/zh_CN.js"></script>
<script src="static/kindeditor-4.1.10/plugins/code/prettify.js" type="text/javascript"></script> 
<script src="static/js/multiselect.js"></script>
<script src="static/js/common.js"></script>
<script src="static/js/dataRqmt.js"></script>
<script type="text/javascript">
$(function() {
	var content = <%= request.getAttribute("content")%>;
	//content = eval("("+content+")");
	//content = eval('(' + content + ')'); 
	//console.info(typeof(content));
	var id = <%= request.getAttribute("id")%>;
	var createTime = '<%= request.getAttribute("createTime")%>';
	var creater = '<%= request.getAttribute("creater")%>';
	
	setDataRqmtContent(content,id,createTime,creater);
	commonData.oLanguage.sInfo = "";
    commonData.oLanguage.sInfoEmpty = "";
    listDataRqmtOpt(id);
    initEditor('<%=basePath%>');
    initDatetimepicker();
});

function forAllotDataRqmt(){
	$("#allotDataRqmtMsg").modal('show');
}

function allotDataRqmt(){
	var loggers = $('input:checkbox[name=logger]:checked').map(function(){
		return this.value;
	}).get().join();
	var stm = $("#stm").val();
	var etm = $("#etm").val();
	if(!loggers){
		alert("请选择日志处理人员!");
		return false;
	}
	if(!stm){
		alert("请选择处理开始时间!");
		return false;
	}
	if(!etm){
		alert("请选择处理结束时间!");
		return false;
	}
	var finishTime = stm + "-" + etm;
	var remark = "分配任务给： " + loggers;
	var otherRemark = $("#otherRemark").val();
	if(otherRemark){
		remark += "\n" + otherRemark;
	}
	addDataRqmtOpt(6,remark,finishTime,loggers);
}
</script>
</body>
</html>
