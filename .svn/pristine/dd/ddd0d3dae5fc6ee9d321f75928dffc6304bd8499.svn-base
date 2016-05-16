<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ page isELIgnored="false"%>
<%@ taglib uri="/tld/privilege" prefix="privilege"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en-us">
<head>
<%@ include file="/WEB-INF/jsp/common/meta.jsp"%>
<style type="text/css">
#example thead  th,#example tbody td,#genSqlTable thead  th,#genSqlTable tbody td{
	text-align: center;
}
.form-horizontal .form-group{
	border: none;
}
.well{
	background: #FFFFFF;
	border-radius: 0;
}
.block-title {
   	border-bottom: 1px solid #C9CCD3;
    height: 40px;
    line-height: 39px;
    margin: -20px -20px 20px;
    padding-left: 20px;
}
.block-title .help {
    border-bottom: 1px solid #c9ccd3;
    height: 40px;
    line-height: 39px;
    margin-bottom: 8px;
}
.block-title h4 {
    color: #28aee9;
    display: inline-block;
    float: left;
    font-size: 15px;
    font-weight: bold;
    line-height: 40px;
    margin: 0 15px 0 0;
    padding: 0;
}

.block-title .item-tabs a {
    display: inline-block;
    float: left;
}

.block-title .item-tabs a.active,.block-title .item-tabs a.active:hover {
    background-color: #3389d4;
    box-shadow: 0 0 5px rgba(9, 52, 89, 0.7) inset;
    color: #fff;
    font-weight: bold;
}
.block-title .item-tabs a:first-child {
    border-left: 1px solid #ddd;
}
.block-title .item-tabs > a {
    border-right: 1px solid #ddd;
}
.block-title .item-tabs a {
    padding: 0 12px;
}
.block-title .item-tabs,.block-title .item-tabs a {
    display: inline-block;
    float: left;
}
#genSqlMain{
	margin-bottom:40px;
}
#fields{
	margin-bottom:40px;
}
.spanTip{
	color : red;
}
</style>
<title>hive表管理-数据魔方</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/top.jsp">
	<jsp:param name="nav" value="data" />
</jsp:include>

<jsp:include page="../common/sysconfigMenu.jsp">
	<jsp:param name="nav" value="config" />
	<jsp:param name="subnav" value="hiveMeta" />
</jsp:include>

<div class="main">
	<jsp:include page="/WEB-INF/jsp/common/mainNav.jsp">
		<jsp:param name="actionName" value="hive表管理(<span style='color:red'>如果要使用添加、更改表的相关功能,请联系mf的开发开通权限</span>)" />
	</jsp:include>
	<div class="well">
		<div class="block-title">
			<div class="item-tabs">
	            <a tag="#fields" class="item active" href="javascript:;">字段信息</a>
	            <privilege:operate url="/#tmp#/createHiveTable"><a tag="#addTable" class="item" href="javascript:;">新增hive表</a></privilege:operate>
	            <privilege:operate url="/#tmp#/genSql"><a tag="#genSqlInfo" class="item" href="javascript:;">生成语句管理</a></privilege:operate>
            </div>
		</div>
		<div id="fields">
			<form role="form">
				<div class="form-group col-xs-16">
					<div class="input-group">
							<div class="input-group-btn">
				            <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
				              <span class="caret"></span>
				              <span class="sr-only">Toggle Dropdown</span>
				            </button>
				            <ul class="dropdown-menu" role="menu" id="tableMenu" style="max-height:400px;overflow: auto;"></ul>
				          </div>
				          
				          
					      <input type="text" id="hiveMetaTableName" placeholder="表名" class="form-control">
					      <span  onclick="syncXml2Hive()" class="input-group-addon glyphicon glyphicon-refresh" style="cursor: pointer;" title="同步xml注释到hive表"></span>
					      <span class="input-group-btn">
					        <button class="btn btn-default" type="button" onclick="getHiveMetaInfo()">查询</button>
					      </span>
				    </div>
				</div>
			</form>
			<div style="clear:both;">
				<table id="example" class="table table-striped table-bordered display"  cellspacing="0" width="100%">
					<thead>
						<tr>
							<th width="5%">id</th>
							<th width="15%">字段名</th>
							<th>注释</th>
							<th width="10%">类型</th>
							<th width="8%">操作</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
		
		<div id="addTable" class="displayNone">
			<form role="form" id="addForm">
				<h5 class="page-header">表名</h5>
				<div class="form-group">
				  	<input type="text" class="form-control" id="newTableName" placeholder="表名">
				</div>
				<h5 class="page-header">字段</h5>
				<div id="columnsDiv">
					<div id="columnDemo">
						<div class="form-group">
							<div class="row">
			  					<div class="col-sm-3">
									  <input type="text" class="form-control" flag="fieldName" placeholder="字段名" id="uidValue">
								 </div>
								 <div class="col-sm-2">
									  <select class="form-control" flag="fieldType">
									  	<option value="string">string</option>
									  	<option value="int">int</option>
									  	<option value="double">double</option>
									  	<option value="bigint">bigint</option>
									  </select>
								 </div>
								  <div class="col-sm-5">
									  <input type="text" class="form-control" placeholder="中文描述" flag="fieldDescr" id="uidDesc">
								 </div>
								 <div class="col-sm-2">
								 	<button type="button" class="btn btn-default" onclick="addField('column');">添加</button>
								 	<button type="button" class="btn btn-default" onclick="removeField('column',this);">删除</button>
								 </div>
							</div>	
						</div> 
					</div>
					<div class="form-group">
						<div class="row">
		  					<div class="col-sm-3">
								  <input type="text" class="form-control" flag="fieldName" placeholder="字段名" value="`_tm`">
							 </div>
							 <div class="col-sm-2">
								  <select class="form-control" flag="fieldType">
								  	<option value="string">string</option>
								  	<option value="int">int</option>
								  	<option value="double">double</option>
								  	<option value="bigint">bigint</option>
								  </select>
							 </div>
							  <div class="col-sm-5">
								  <input type="text" class="form-control" placeholder="中文描述" flag="fieldDescr" value="时间">
							 </div>
							 <div class="col-sm-2">
							 	<button type="button" class="btn btn-default" onclick="addField('column');">添加</button>
							 	<button type="button" class="btn btn-default" onclick="removeField('column',this);">删除</button>
							 </div>
						</div>	
					</div> 
					
					<div class="form-group">
						<div class="row">
		  					<div class="col-sm-3">
								  <input type="text" class="form-control" flag="fieldName" placeholder="字段名" value="`_plat`">
							 </div>
							 <div class="col-sm-2">
								  <select class="form-control" flag="fieldType">
								  	<option value="string">string</option>
								  	<option value="int">int</option>
								  	<option value="double">double</option>
								  	<option value="bigint">bigint</option>
								  </select>
							 </div>
							  <div class="col-sm-5">
								  <input type="text" class="form-control" placeholder="中文描述" flag="fieldDescr" value="平台id">
							 </div>
							 <div class="col-sm-2">
							 	<button type="button" class="btn btn-default" onclick="addField('column');">添加</button>
							 	<button type="button" class="btn btn-default" onclick="removeField('column',this);">删除</button>
							 </div>
						</div>	
					</div> 
					
					<div class="form-group">
						<div class="row">
		  					<div class="col-sm-3">
								  <input type="text" class="form-control" flag="fieldName" placeholder="字段名" value="`_bpid`">
							 </div>
							 <div class="col-sm-2">
								  <select class="form-control" flag="fieldType">
								  	<option value="string">string</option>
								  	<option value="int">int</option>
								  	<option value="double">double</option>
								  	<option value="bigint">bigint</option>
								  </select>
							 </div>
							  <div class="col-sm-5">
								  <input type="text" class="form-control" placeholder="中文描述" flag="fieldDescr" value="32位唯一id">
							 </div>
							 <div class="col-sm-2">
							 	<button type="button" class="btn btn-default" onclick="addField('column');">添加</button>
							 	<button type="button" class="btn btn-default" onclick="removeField('column',this);">删除</button>
							 </div>
						</div>	
					</div> 
					
					<div class="form-group">
						<div class="row">
		  					<div class="col-sm-3">
								  <input type="text" class="form-control" flag="fieldName" placeholder="字段名" value="`_gid`">
							 </div>
							 <div class="col-sm-2">
								  <select class="form-control" flag="fieldType">
								  	<option value="string">string</option>
								  	<option value="int">int</option>
								  	<option value="double">double</option>
								  	<option value="bigint">bigint</option>
								  </select>
							 </div>
							  <div class="col-sm-5">
								  <input type="text" class="form-control" placeholder="中文描述" flag="fieldDescr" value="游戏id">
							 </div>
							 <div class="col-sm-2">
							 	<button type="button" class="btn btn-default" onclick="addField('column');">添加</button>
							 	<button type="button" class="btn btn-default" onclick="removeField('column',this);">删除</button>
							 </div>
						</div>	
					</div> 
					
				</div>
				
				<h5 class="page-header">分区</h5>
				<div id="partitionDiv">
					<div class="form-group">
						<div class="row">
		  					<div class="col-sm-3">
								  <input type="text" class="form-control" flag="fieldName" placeholder="分区名" id="tmValue">
							 </div>
							 <div class="col-sm-2">
								  <select class="form-control" flag="fieldType">
								  	<option value="int">int</option>
								  	<option value="string">string</option>
								  	<option value="double">double</option>
								  	<option value="bigint">bigint</option>
								  </select>
							 </div>
							  <div class="col-sm-5">
								  <input type="text" class="form-control" placeholder="中文描述" flag="fieldDescr" id="tmDesc">
							 </div>
							 <div class="col-sm-2">
							 	<button type="button" class="btn btn-default" onclick="addField('partition');">添加</button>
							 	<button type="button" class="btn btn-default" onclick="removeField('partition',this);">删除</button>
							 </div>
						</div>	
					</div> 
				</div>
				
				<h5 class="page-header">CLUSTERED</h5>
				<div class="form-group">
					<div class="row">
	  					<div class="col-sm-12">
							  <input type="text" class="form-control" placeholder="" id="clustered">
						 </div>
					</div>	
				</div>
	
				<h5 class="page-header">SORTED</h5>
				<div class="form-group">
					<div class="row">
	  					<div class="col-sm-12">
							  <input type="text" class="form-control" placeholder="" id="sorted">
						 </div>
					</div>	
				</div>
				
				<h5 class="page-header">BUCKETS</h5>
				<div class="form-group">
					<div class="row">
	  					<div class="col-sm-12">
							  <input type="text" class="form-control" placeholder="" id="buckets" value="1">
						 </div>
					</div>	
				</div>
	
				<h5 class="page-header"></h5>
				<div class="form-group">
					<div class="row">
						<div class="col-sm-12">
							<button type="button" class="btn btn-default" id="createTable" data-loading-text="提交中...">创建</button>
						</div>
					</div>
				</div>
			
			</form>
		</div>
		
		<div id="genSqlInfo" class="displayNone">
			<form role="form">
				<div class="form-group col-xs-11">
					<div class="input-group">
					      <input type="text" id="genSqlTableName" placeholder="表名" class="form-control">
					      <input type="text" class="displayNone">
					      <span class="input-group-btn">
					        <button class="btn btn-default" type="button" onclick="getGenSql()">查询</button>
					      </span>
				    </div>
				</div>
				<div class="form-group col-xs-1">
					<button type='button' class='btn btn-primary' onclick='forCreateGenSql()'>增加</button>
				</div>
			</form>
			<div id="genSqlMain">
				<table id="genSqlTable" class="table table-striped table-bordered display"  cellspacing="0" width="100%">
					<thead>
						<tr>
							<th width="5%">id</th>
							<th width="10%">表名</th>
							<th width="10%">显示名</th>
							<th width="50%">语句</th>
							<th width="5%">排序</th>
							<th width="5%">日志</th>
							<th width="10%">修改时间</th>
							<th width="10%">操作</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
	</div>
</div>

 <%@ include file="/WEB-INF/jsp/common/foot.jsp"%>

<!-- Bootstrap core JavaScript navbar-fixed-bottom
   ================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="static/js/basic.js"></script>
<script src="static/js/dataTable.js"></script>
<script src="static/js/common.js"></script>
<script src="static/js/hiveMeta.js.jsp"></script>
<script type="text/javascript">
$(document).ready(function() {
	//获取所有表名
	var tables = getTables("hive",false);
	$("#tableMenu").empty();
 	$.each(tables, function(index, value) {
 		$("#tableMenu").append('<li v="'+value.name+'"><a href="javascript:void(0)">'+value.desc+'</a></li>');
	});
	
	getHiveMetaInfo();
	initItems();
	pressEnter('hiveMetaTableName',getHiveMetaInfo);
	pressEnter('genSqlTableName',getGenSql);
	initCreateHiveTable();
	getGenSql();
	
	$("#tableMenu li").click(function(){
		$('#hiveMetaTableName').val($(this).attr('v'));
	});
	
	
});

</script>
</body>
</html>
