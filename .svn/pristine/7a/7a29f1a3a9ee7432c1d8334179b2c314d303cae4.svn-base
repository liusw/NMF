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
<title>资源管理-数据魔方</title>
<style type="text/css">
div#rMenu {position:absolute; visibility:hidden; top:0; background-color: #555;text-align: left;padding: 2px;}
div#rMenu ul{
margin: 0;
padding: 0;
}
div#rMenu ul li{
	margin: 1px 0;
	padding: 0 5px;
	cursor: pointer;
	list-style: none outside none;
	background-color: #DFDFDF;
}
.errorTip{
	color: red;
	font-weight: bold;
	padding-left: 20px;
}
.labelColumn > p{
    margin-bottom: 0;
}

.labelClose {
    color: #000000;
    font-size: 18px;
    font-weight: bold;
    line-height: 1;
    opacity: 0.2;
    text-shadow: 0 1px 0 #FFFFFF;
    margin-left: 10px;
    cursor: pointer;
}
</style>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/top.jsp">
        <jsp:param name="nav" value="sysconfig"/>
	</jsp:include>
	
	<jsp:include page="../common/sysconfigMenu.jsp">
		<jsp:param name="nav" value="privilege" />
		<jsp:param name="subnav" value="resources" />
	</jsp:include>

	<div class="main">
			<jsp:include page="/WEB-INF/jsp/common/mainNav.jsp">
				<jsp:param name="actionName" value="资源管理" />
			</jsp:include>
			
		<div id="treeDemo" class="ztree"></div>
		<div id="rMenu">
			<ul>
				<li id="m_add" onclick="addTreeNode();">增加节点</li>
				<li id="m_update" onclick="updateTreeNode();">编辑节点</li>
				<li id="m_del" onclick="removeTreeNode();">删除节点</li>
			</ul>
		</div>
	</div>
 <%@ include file="/WEB-INF/jsp/common/foot.jsp"%>

<!-- Bootstrap core JavaScript navbar-fixed-bottom
   ================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="static/js/basic.js"></script>
<script src="static/js/common.js"></script>
<script type="text/javascript" src="static/zTree_v3/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="static/zTree_v3/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript">
$.ajaxSetup({async:false});
var setting = {
	/*edit: {
		enable: true,
		drag: {
			isCopy: false,
			isMove: true
		}
	},*/
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
		url:"privilege/resources/getData.htm"
	},
	callback:{
		//beforeClick: zTreeBeforeClick,
		onRightClick: OnRightClick
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
	
	var html = "";
	html += '<form class="form-inline">';
	if(!isEmpty(pname)){
		html += '父节点：<div class="labelColumn"><p><span id="pid" value="'+pid+'">'+pname+'</span> <span class="labelClose" onclick="labelClose(this)">×</span></p></div>';
	}
	html += '<div class="myrow"><div class="form-group">名称<input type="text" id="name" name="name" class="form-control" placeholder="任务名称" autocomplete="off"><span id="error_name" class="errorTip"></span></div></div>';
	html += '<div class="myrow"><div class="form-group">路径<input type="text" id="url" name="url" class="form-control" placeholder="任务名称" autocomplete="off"><span id="error_url" class="errorTip"></span></div></div>';
	html +=	'</form>';
	
	showMsg(html,"添加资源",function(){//确认
		if(isEmpty($("#name").val())){
			$("#error_name").html("名称不能为空");
			return;
		}else{
			$("#error_name").html("");
		}
	
		if(isEmpty($("#url").val())){
			$("#error_url").html("URL不能为空");
			return;
		}else{
			$("#error_url").html("");
		}
		
		//提交
		var data = getJsonData2("privilege/resources/add.htm",{pid:$("#pid").attr("value"),name:$("#name").val(),url:$("#url").val()});
		if(data){
			if(data.status==1){
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

function updateTreeNode(){
	hideRMenu();
	var id = zTree.getSelectedNodes()[0].id;
	
	//加载
	var data = getJsonData2("privilege/resources/getById.htm",{id:id},true);
	if(data!=null) {
		var html = "";
		html += '<form class="form-inline">';
		html += '<div class="myrow"><div class="form-group">名称<input type="text" id="name" name="name" value="'+data.name+'" class="form-control" placeholder="任务名称" autocomplete="off"><span id="error_name" class="errorTip"></span></div></div>';
		html += '<div class="myrow"><div class="form-group">路径<input type="text" id="url" name="url" value="'+data.url+'" class="form-control" placeholder="任务名称" autocomplete="off"><span id="error_url" class="errorTip"></span></div></div>';
		html +=	'</form>';
		
		showMsg(html,"添加资源",function(){//确认
			if(isEmpty($("#name").val())){
				$("#error_name").html("名称不能为空");
				return;
			}else{
				$("#error_name").html("");
			}
		
			if(isEmpty($("#url").val())){
				$("#error_url").html("URL不能为空");
				return;
			}else{
				$("#error_url").html("");
			}
			
			//提交
			var data = getJsonData("privilege/resources/update.htm",{id:id,name:$("#name").val(),url:$("#url").val()});
			if(data){
				if(data.status==1){
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
	
	var msg = "删除与其相关的授权会全部删除，您确定要删除？";   
	if (confirm(msg)==true){
		var data = getJsonData("privilege/resources/delete.htm",{id:id});
		if(data){
			if(data.status==1){
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
	zTree.setting.check.chkboxType = { "Y" : "ps", "N" : "ps" };
	zTree.expandAll(true);
	rMenu = $("#rMenu");
});

function labelClose(o) {
	$(o).parent().parent().remove();
}
</script>
</body>
</html>