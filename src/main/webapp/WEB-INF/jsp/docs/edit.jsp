<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib  uri ="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html lang="en-us">
<head>
<%@ include file="/WEB-INF/jsp/common/meta.jsp"%>
<title>帮助文档-数据魔方</title>
	<link href="static/css/matrix-style.css" rel="stylesheet">
	<link rel="stylesheet" href="static/kindeditor-4.1.10/themes/default/default.css" />
	<link rel="stylesheet" href="static/kindeditor-4.1.10/plugins/code/prettify.css" />
	<link rel="stylesheet" href="static/zTree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css">
	<style type="text/css">
		.page-header {
	    border-bottom: 1px solid #dadada;
	    padding-bottom: 2px;
	    margin: 20px 0 0 0;
		}
		hr {
			border-top-color: #dadada;
		}
		ul.ztree {
			margin-top: 10px;
			border: 1px solid #617775;
			background: #f0f6e4;
			width: 220px;
			height:200px;
			overflow-y: scroll;
			overflow-x: auto;
		}
	</style>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/top.jsp">
		<jsp:param name="nav" value="docs" />
	</jsp:include>
	
		<div class="main" style="padding-left: 0px;">
		<h1 id="overview" class="page-header">编辑
		<p style="float: right;margin-right: 10px;cursor: pointer;" onclick="javascript :history.back(-1);" title="返回"><span class="glyphicon glyphicon-share"></span></p>
		</h1>
		
		<form class="form-horizontal" id="form" action="<%=basePath%>docs/article/addOrUpdate.htm" method="POST">
			<input type="hidden" name="id" value="${article.id }">
			<div class="form-group first_fg">
				<label class="col-md-1 control-label">类别</label>
				<div class="col-md-11">
					<div class="row">
						<div class="col-md-4">
							<input id="categoryId" name="categoryId" type="hidden" value="${article.category.id }">
							<input id="categorySel" type="text" readonly="readonly" style="width:120px;" value="${article.category.name}"/>&nbsp;<a id="menuBtn" href="javascript:void(0)" onclick="showMenu(); return false;">选择</a></li>
						</div>
						<div class="col-md-4">
							<input type="checkbox" name="isNotice" value="1" <c:if test="${article.isNotice==1 }"> checked="checked"</c:if>>是否设置为公告
						</div>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-md-1 control-label">标题</label>
				<div class="col-md-11">
					<div class="row">
						<div class="col-md-4">
							<input type="text" id="title" name="title" class="form-control" placeholder="标题" autocomplete="off" value="${article.title }">
						</div>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-md-1 control-label">内容</label>
				<div class="col-md-11">
					<div class="row">
						<div class="col-md-8">
                     <textarea id="content" name="content" class="form-control" style="height:300px;">
                    ${fn:escapeXml(article.content)}
                     </textarea>
                	</div>
                </div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-md-1 control-label"></label>
				<div class="col-md-11">
					<div class="row">
						<div class="col-md-4">
							<button type="submit" class="btn btn-default" data-loading-text="提交中...">提交任务</button>
						</div>
					</div>
				</div>
			</div>
		</form>
		<div id="menuContent" class="menuContent" style="display:none; position: absolute;">
			<ul id="treeDemo" class="ztree" style="margin-top:0; width:160px;"></ul>
		</div>
	  	<div style="clear: both;"></div>
	</div>
	<script src="static/js/basic.js"></script>
	<script src="static/js/common.js"></script>
	<script charset="utf-8" src="static/kindeditor-4.1.10/kindeditor-min.js"></script>
	<script charset="utf-8" src="static/kindeditor-4.1.10/lang/zh_CN.js"></script>
	<script src="static/kindeditor-4.1.10/plugins/code/prettify.js" type="text/javascript"></script> 
	<script type="text/javascript" src="static/zTree_v3/js/jquery.ztree.core-3.5.js"></script>
	
	<script type="text/javascript">
	var selectNodeId = '${article.category.id}';
	if('${cId}'>0){
		selectNodeId = '${cId}';
		$("#categorySel").attr("value", '${category.name}');
		$("#categoryId").attr("value", '${category.id}');
	}
	
	function showMenu() {
		var categoryObj = $("#categorySel");
		var categoryOffset = $("#categorySel").offset();
		$("#menuContent").css({left:categoryOffset.left + "px", top:categoryOffset.top + categoryObj.outerHeight() + "px"}).slideDown("fast");

		$("body").bind("mousedown", onBodyDown);
		zTree.expandAll(true);
		zTree.selectNode(zTree.getNodeByParam("id",selectNodeId, null));
	}
	function hideMenu() {
		$("#menuContent").fadeOut("fast");
		$("body").unbind("mousedown", onBodyDown);
	}
	function onBodyDown(event) {
		if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
			hideMenu();
		}
	}
	function beforeClick(treeId, treeNode) {
		var check = (treeNode && !treeNode.isParent);
		if (!check) alert("只能子分类...");
		return check;
	}
	
	function onClick(e, treeId, treeNode) {
		nodes = zTree.getSelectedNodes(),
		v = "",n="";
		nodes.sort(function compare(a,b){return a.id-b.id;});
		for (var i=0, l=nodes.length; i<l; i++) {
			n += nodes[i].name + ",";
			v += nodes[i].id + ",";
		}
		if (v.length > 0 ) v = v.substring(0, v.length-1);
		if (n.length > 0 ) n = n.substring(0, n.length-1);
		$("#categorySel").attr("value", n);
		$("#categoryId").attr("value", v);
	}
	
	var setting = {
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
				url:"docs/category/getDatas.htm?id="+selectNodeId
			},
			callback:{
				beforeClick: beforeClick,
				onClick: onClick
			}
			
		};
	
	var zTree;
	$(function(){
		tree = $.fn.zTree.init($("#treeDemo"), setting);
		zTree = $.fn.zTree.getZTreeObj("treeDemo");
		
			
		
	});
	
	var editor;
	KindEditor.ready(function(K) {
		editor = K.create('textarea[name="content"]', {
			cssPath: '<%=basePath%>static/kindeditor-4.1.10/plugins/code/prettify.css', 
			resizeType : 1,
			allowPreviewEmoticons : false,
			allowImageUpload : true,
			uploadJson : 'upload?action=uploadImages',
			allowImageRemote : true,
	      filterMode : false,
	      urlType:'domain',
			items : [
				'source','code','|','fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
				'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
				'insertunorderedlist', '|', 'emoticons', 'image', 'link'],
			afterCreate : function() {
                this.sync();
            },
            afterBlur : function() {
                this.sync();
            }
		});
		 editor.sync();
		//prettyPrint(); 
	});
	
	</script>
	
	<%@ include file="/WEB-INF/jsp/common/foot.jsp"%>
</body>
</html>
