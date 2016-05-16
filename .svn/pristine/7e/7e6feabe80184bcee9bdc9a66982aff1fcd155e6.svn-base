<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/tld/privilege" prefix="privilege"%>
<!DOCTYPE html>
<html lang="en-us">
<head>
<%@ include file="/WEB-INF/jsp/common/meta.jsp"%>
<title>帮助文档-数据魔方</title>
<link rel="stylesheet" href="static/zTree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css">
	<style type="text/css">
		.page-header {
	    border-bottom: 1px solid #dadada;
	    padding-bottom: 2px;
	    margin: 20px 0 0 0;
		}
		div#rMenu {position:absolute; visibility:hidden; top:0; background-color: #555;text-align: left;padding: 2px;}
		div#rMenu ul{margin: 0;padding: 0;}
		div#rMenu ul li{margin: 1px 0;padding: 0 5px;cursor: pointer;list-style: none outside none;background-color: #DFDFDF;}
		.ztree{margin-top: 20px;}
	</style>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/top.jsp">
		<jsp:param name="nav" value="docs" />
	</jsp:include>
	
	<div class="main" style="padding-left: 0px;">
		<h1 id="overview" class="page-header">分类管理</h1>
		<div id="treeDemo" class="ztree"></div>
		<div id="rMenu">
			<ul>
				<privilege:operate url="/docs/category/add.htm">
				<li id="m_add" onclick="addTreeNode();">增加节点</li>
				</privilege:operate>
				<privilege:operate url="/docs/category/update.htm">
				<li id="m_update" onclick="updateTreeNode();">编辑节点</li>
				</privilege:operate>
				<privilege:operate url="/docs/category/delete.htm">
				<li id="m_del" onclick="removeTreeNode();">删除节点</li>
				</privilege:operate>
			</ul>
		</div>
	</div>
	<%@ include file="/WEB-INF/jsp/common/foot.jsp"%>
	<privilege:operate url="/docs/category/updateOrderNo.htm">
	<input type="hidden" id="moveTree" value="1">
	</privilege:operate>
	
	<script src="static/js/basic.js"></script>
	<script src="static/js/common.js"></script>
	<script type="text/javascript" src="static/zTree_v3/js/jquery.ztree.core-3.5.js"></script>
	<script type="text/javascript" src="static/zTree_v3/js/jquery.ztree.excheck-3.5.js"></script>
	<script type="text/javascript" src="static/zTree_v3/js/jquery.ztree.exedit-3.5.js"></script>
	<script type="text/javascript">
var editEnable=false;
if($("#moveTree").val()){
	editEnable = true;
}
$.ajaxSetup({async:false});
var setting = {
	edit: {
		enable: editEnable,
		drag: {
			isCopy: false,
			isMove: true,
			prev: true,
			next: true,
			inner: false
		},
		showRemoveBtn: false,
		showRenameBtn: false
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
		url:"docs/category/getDatas.htm"
	},
	callback:{
		onRightClick: OnRightClick,
		beforeDrag: beforeDrag,
		beforeDrop: beforeDrop
	}
	
};

function zTreeBeforeClick(treeId, treeNode, clickFlag) {
	if (treeNode.isParent) {
		if (!treeNode.open) {
			tree.expandNode(treeNode, true);
		} else {
			tree.expandNode(treeNode, false);
		}
	}
	return false;
};

function OnRightClick(event, treeId, treeNode) {
	if (!treeNode && event.target.tagName.toLowerCase() != "button" && $(event.target).parents("a").length == 0) {
		zTree.cancelSelectedNode();
		showRMenu("root", event.clientX, event.clientY);
	} else if (treeNode && !treeNode.noR) {
		zTree.selectNode(treeNode);
		showRMenu("node", event.clientX, event.clientY);
	}
}

function beforeDrag(treeId, treeNodes) {
	for (var i=0,l=treeNodes.length; i<l; i++) {
		if (treeNodes[i].drag === false) {
			return false;
		}
	}
	return true;
}
function beforeDrop(treeId, treeNodes, targetNode, moveType) {
	var paramData = {orderId:treeNodes[0].orderNo,targetOrderId:targetNode.orderNo,moveType:moveType};
	var data = getJsonData("docs/category/updateOrderNo.htm",paramData);
	var result = false;
	if(data){
		if(data.ok==1){
			result = true;
		}else{
			alert(data.msg);
		}
	}else{
		alert("操作失败");
	}
	return result;
}

function showRMenu(type, x, y) {
	$("#rMenu ul").show();
	if (type=="root") {
		$("#m_del").hide();
		$("#m_update").hide();
	} else {
		$("#m_del").show();
		$("#m_update").show();
	}
	rMenu.css({"top":y+"px", "left":x+"px", "visibility":"visible"});
	$("body").bind("mousedown", onBodyMouseDown);
}
function hideRMenu() {
	if (rMenu) rMenu.css({"visibility": "hidden"});
	$("body").unbind("mousedown", onBodyMouseDown);
}
function onBodyMouseDown(event){
	if (!(event.target.id == "rMenu" || $(event.target).parents("#rMenu").length>0)) {
		rMenu.css({"visibility" : "hidden"});
		zTree.cancelSelectedNode();
	}
}

function addTreeNode() {
	hideRMenu();
	
	var pid = 0,pname='';
	if(zTree.getSelectedNodes()!=null && zTree.getSelectedNodes().length>0 && zTree.getSelectedNodes()[0].id>0){
		pid= zTree.getSelectedNodes()[0].id;
		pname = zTree.getSelectedNodes()[0].name;
	}
	
	var html = '<form class="form-inline">';
	if(!isEmpty(pname)){
		html += '父节点：<div class="labelColumn"><p><span id="pid" value="'+pid+'">'+pname+'</span></p></div>';
	}
	
	html += '<div class="myrow"><div class="form-group">名称：<input type="text" id="name" name="name" class="form-control" placeholder="任务名称" autocomplete="off"><span id="error_name" class="errorTip"></span></div></div>';
	html +=	'</form>';
	
	showMsg(html,"添加分类",function(){//确认
		if(isEmpty($("#name").val())){
			$("#error_name").html("名称不能为空");
			return;
		}else{
			$("#error_name").html("");
		}
	
		//提交
		var data = getJsonData("docs/category/add.htm",{name:$("#name").val(),pid:$("#pid").attr("value")});
		if(data){
			if(data.ok==1){
				closeMsg();
				zTree.refresh();
				zTree.reAsyncChildNodes(null, "refresh");
				//zTree.expandAll(true);
			}else{
				alert(data.msg);
			}
		}else{
			alert("操作失败");
		}
	},null,function(){
		zTree.cancelSelectedNode();
		closeMsg();
	});
}

function updateTreeNode(){
	hideRMenu();
	var id = zTree.getSelectedNodes()[0].id;
	
	//加载
	var data = getJsonData("docs/category/getById.htm",{id:id},true);
	if(data!=null) {
		var html = "";
		html += '<form class="form-inline">';
		html += '<div class="myrow"><div class="form-group">名称<input type="text" id="name" name="name" value="'+data.name+'" class="form-control" placeholder="任务名称" autocomplete="off"><span id="error_name" class="errorTip"></span></div></div>';
		html +=	'</form>';
		
		showMsg(html,"更改分类",function(){//确认
			if(isEmpty($("#name").val())){
				$("#error_name").html("名称不能为空");
				return;
			}else{
				$("#error_name").html("");
			}
		
			var data = getJsonData("docs/category/update.htm",{id:id,name:$("#name").val()});
			if(data){
				if(data.ok==1){
					closeMsg();
					zTree.refresh();
					zTree.reAsyncChildNodes(null, "refresh");
					zTree.expandAll(true);
				}else{
					alert(data.msg);
				}
			}else{
				alert("操作失败");
			}
		},null,function(){
			zTree.cancelSelectedNode();
			closeMsg();
		});
	}
}

function removeTreeNode(){
	hideRMenu();
	var id = zTree.getSelectedNodes()[0].id;
	
	var msg = "删除后帮助文档的分类ID就找不到了哦！";   
	if (confirm(msg)==true){
		var data = getJsonData("docs/category/delete.htm",{id:id});
		if(data){
			if(data.ok==1){
				zTree.refresh();
				zTree.reAsyncChildNodes(null, "refresh");
				zTree.expandAll(true);
			}else{
				alert(data.msg);
			}
		}
	}
}

var zTree, rMenu;
$(document).ready(function() {
	tree = $.fn.zTree.init($("#treeDemo"), setting);
	zTree = $.fn.zTree.getZTreeObj("treeDemo");
	//zTree.expandAll(true);
	rMenu = $("#rMenu");
});
</script>
</body>
</html>
