<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en-us">
<head>
<%@ include file="/WEB-INF/jsp/common/meta.jsp"%>
<link rel="stylesheet" href="static/kindeditor-4.1.10/themes/default/default.css" />
<link rel="stylesheet" href="static/kindeditor-4.1.10/plugins/code/prettify.css" />
<title>提交需求-数据魔方</title>
<style>
#formDiv{
	margin-bottom: 50px;
}
.form-horizontal .form-group{
	margin-left: 0;
	margin-right:0;
}
</style>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/top.jsp">
		<jsp:param name="nav" value="data" />
	</jsp:include>

	<jsp:include page="/WEB-INF/jsp/common/dataMenu.jsp">
		<jsp:param name="nav" value="data" />
		<jsp:param name="subnav" value="myList" />
	</jsp:include>

	<div class="main">
		<jsp:include page="/WEB-INF/jsp/common/mainNav.jsp">
			<jsp:param name="actionName" value="提交需求" />
		</jsp:include>
		<div id="formDiv" class="myWell col-md-8 col-md-offset-2">
			<form class="form-horizontal">
				<div class="form-group col-md-12">
					<label class="col-md-2 control-label"> 需求标题: </label>
					<div class="col-md-10">
		            	<input type="text" id="dataTtile" class="form-control content" placeholder="简短的描述你的需求内容和目的。什么叫简短？就是描述内容不超出这一行"></input>
					</div>
				</div>
				<div class="form-group col-md-12">
					<div class="row">
		            	<div class="col-md-6">
		            		<label class="col-md-4 control-label"> 提交人: </label>
							<div class="col-md-8">
				            	<input type="text" class="form-control content" id="userName" placeholder="你的名字" value="<%=request.getSession().getAttribute("cname") %>"></input>
							</div>
						</div>
						<div class="col-md-6">
							<label class="col-md-4 control-label"> 要求时间: </label>
							<div class="col-md-8">
				            	<input type="text" class="form-control time content" id="needTime" placeholder="截止完成时间"></input>
							</div>
						</div>
					</div>
				</div>
				<div class="form-group col-md-12">
					<label class="col-md-2 control-label"> 数据用途: </label>
					<div class="col-md-10">
		            	<input type="text" class="form-control content" id="dataPurpose" placeholder="导出数据的目的，不要只说数据分析，要说清楚为了什么分析什么做什么用"></input>
					</div>
				</div>
				<div class="form-group col-md-12">
					<label class="col-md-2 control-label"> 需技术协助的原因: </label>
					<div class="col-md-10">
						<div class="row">
		                   <div class="col-md-12">
		                     <select class="form-control content" id="needReason" placeholder="写明改需求无法自助导出的原因">
		                     	<option value="不熟悉mf的使用">不熟悉mf的使用</option>
		                     	<option value="mf没有这样的定制功能">mf没有这样的定制功能</option>
		                     	<option value="数据需要特殊处理">数据需要特殊处理</option>
		                     	<option value="其他">其他</option>
		                     </select>
		                   </div>
	                	</div>
					</div>
				</div>
				<div class="form-group col-md-12">
					<label class="col-md-2 control-label"> 平台站点: </label>
					<div class="col-md-10">
						<div class="row">
		                   <div class="col-md-6">
		                     <select id="plats" class="form-control content"></select>
		                   </div>
		                   <div class="col-md-6">
		                     <select id="sid" class="form-control multiselect content" multiple="multiple"></select>
		                   </div>
		                </div>
					</div>
				</div>
				<div class="form-group col-md-12">
					<label class="col-md-2 control-label"> 数据时间范围: </label>
					<div class="col-md-10">
						<div class="row">
							<div class="col-md-6">
								<input type="text" id="stime" class="form-control time content" placeholder="开始时间" autocomplete="off">
							</div>
	                  <div class="col-md-6">
								<input type="text" id="etime" class="form-control time content" placeholder="结束时间" autocomplete="off">
							</div>
						</div>
					</div>
				</div>
				<div class="form-group col-md-12">
					<label class="col-md-2 control-label"> 目标用户: </label>
					<div class="col-md-10">
						<textarea class="form-control content" rows="3" placeholder="你需要获取什么条件的用户" id="resultUser"></textarea>
					</div>
				</div>
				<div class="form-group col-md-12">
					<label class="col-md-2 control-label"> 参数类型: </label>
					<div class="col-md-10">
						<div class="row">
		                   <div class="col-md-12">
		                     <input type="text" class="form-control content" placeholder="参数是哪一类型的" id="itemType"></input>
		                   </div>
	                	</div>
					</div>
				</div>
				<div class="form-group col-md-12">
					<label class="col-md-2 control-label"> 参数链接: </label>
					<div class="col-md-10">
						<div class="row">
		                   <div class="col-md-12">
		                     <input type="text" class="form-control content" placeholder="参数的参考链接，比如cms金币明细的地址" id="itemLink"></input>
		                   </div>
	                	</div>
					</div>
				</div>
				
				<div class="form-group col-md-12">
					<label class="col-md-2 control-label"> 需求数据项: </label>
					<div class="col-md-10">
						<textarea class="form-control content" name="content" rows="6" placeholder="你需要导出的数据项，注意！数据项需与魔方后台数据项一致，如果魔方后台没有该项数据，就说明该数据没有上报或者数据库根本没有这项数据指标。
而D后台数据与数据库字段不完全一致，可能导致要求数据项与实际导出数据项不一致，或者浪费沟通时间用于确认。" id="dataItem"></textarea>
					</div>
				</div>
				<div class="form-group col-md-12">
					<label class="col-md-2 control-label"> 备注: </label>
					<div class="col-md-10">
						<div class="row">
		                   <div class="col-md-12">
		                     <input type="text" class="form-control content" placeholder="写特殊注意事项，如果没有写“无”，或者什么也不写" id="remark"></input>
		                   </div>
	                	</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-5 control-label"></label>
					<div class="col-md-7">
						<div class="row">
							<div class="col-md-4">
								<button type="button" class="btn btn-default" id="addDataRqmt" data-loading-text="提交中...">提交</button>
							</div>
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>

	<%@ include file="/WEB-INF/jsp/common/foot.jsp"%>

<!-- Bootstrap core JavaScript navbar-fixed-bottom
   ================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="static/js/basic.js"></script>
<script src="static/js/datetimepicke.js"></script>
<script charset="utf-8" src="static/kindeditor-4.1.10/kindeditor-min.js"></script>
<script charset="utf-8" src="static/kindeditor-4.1.10/lang/zh_CN.js"></script>
<script src="static/kindeditor-4.1.10/plugins/code/prettify.js" type="text/javascript"></script> 
<script src="static/js/multiselect.js"></script>
<script src="static/js/dateUtils.js"></script>
<script src="static/js/common.js"></script>
<script src="static/js/dataRqmt.js"></script>
<script type="text/javascript">
	$(function() {
		getMultiPlat("#plats", "#sid");
		$("#plats").change(function() {
			getMultiSid("#plats", "#sid", true);
		});
		initDatetimepicker();
		initEditor('<%=basePath%>');
		setDefaultDate();
	});
</script>
</body>
</html>
