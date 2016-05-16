<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/tld/privilege" prefix="privilege"%>
<!DOCTYPE html>
<html lang="en-us">
<head>
<%@ include file="/WEB-INF/jsp/common/meta.jsp"%>
<title>帮助文档-数据魔方</title>
	<link href="static/css/matrix-style.css" rel="stylesheet">
	<link rel="stylesheet" href="static/kindeditor-4.1.10/themes/default/default.css" />
	<link rel="stylesheet" href="static/kindeditor-4.1.10/plugins/code/prettify.css" />
	<style type="text/css">
		.page-header {
	    border-bottom: 1px solid #dadada;
	    padding-bottom: 2px;
	    margin: 20px 0 0 0;
		}
		.content{
			margin-top:10px;
			font-size: 18px;
		}
		.commentDiv{
			background: #fff;
			margin-bottom: 20px;
			clear: both;
		}
		.titleTip{
			background: #454c58;
			height: 35px;
			line-height: 35px;
			padding: 0 10px;
			font-size: 16px;
			color: #fff;
			font-weight: 100;
		}
		
	 #commentHolder{border-bottom:1px solid #aaa;margin: 5px 0;}
    .comment{padding:5px 8px;background:#ffe;border:1px solid #aaa;font-size:14px;border-bottom:none;color:#1f3a87;}
    
    .navRight {
	    color: #FF6600;
	    cursor: pointer;
	    float: right;
	    height: 38px;
	    line-height: 38px;
	    padding-right: 20px;
	}
	</style>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/top.jsp">
		<jsp:param name="nav" value="docs" />
	</jsp:include>
	
	<jsp:include page="helpMenu.jsp">
		<jsp:param name="cid" value="${article.category.id }" />
	</jsp:include>
	<div class="main">
		<div id="breadcrumb">
			<a href="/"><i class="glyphicon glyphicon-home"></i> 首页</a> <a class="current">帮助中心</a>
			<span class="navRight">
				<privilege:operate url="/docs/article/search.htm">
					<input id="article_search" style="float: left;margin-bottom: 0px;" placeholder="请输入关键字" maxlength="255" onMouseOut="hideTip();">
					<div class="bdsug" style="height: auto; display: none;" onMouseOver="showTip();" onMouseOut="hideTip();">
						<ul>
						</ul>
					</div>
				</privilege:operate>
				<p style="float:left;margin: 0px 10px 0 10px;cursor: pointer;color:rgb(102, 102, 102);font-size: 20px;line-height: 40px;" onclick="javascript:history.back(-1);" title="返回">
					<span class="glyphicon glyphicon-share"></span>
				</p>
				<privilege:operate url="/docs/article/edit.htm">
					<a style="float: left;margin: 0px 10px 0px 0px;padding:0px;cursor: pointer;color: #428bca;background: none;font-size: 20px;line-height: 40px;" title="编辑" href="docs/article/edit.htm?id=${article.id }"><span class="glyphicon glyphicon-edit editDocs"></span></a>
				</privilege:operate>
			</span>
		</div>
	
		<h1 id="overview" class="page-header">${article.title }
		</h1>
		<div class="content">
			${article.content }
		</div>

		<div class="commentDiv">
			<c:if test="${!empty comments}">
			<div class="titleTip">相关评论</div>
			<div id="commentHolder">
				<c:forEach items="${comments}" var="comment">
				<div class='comment'>
					<p class='title'>
						${comment.realName }&nbsp;&nbsp;&nbsp;&nbsp;<fmt:formatDate value="${comment.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</p>
					${comment.content }
				</div>
				</c:forEach>
			</div>
			</c:if>
			<div class="titleTip">发表评论</div>
			<form class="form-horizontal" id="form" action="<%=basePath%>docs/comment/add.htm" method="POST">
				<input type="hidden" name="articleId" value="${article.id }">
				<div class="form-group first_fg">
					<label class="col-md-1 control-label">内容</label>
					<div class="col-md-11">
						<div class="row">
							<div class="col-md-8">
	                     <textarea id="content" name="content" class="form-control" style="height:300px;"></textarea>
	                	</div>
	                </div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-1 control-label"></label>
					<div class="col-md-11">
						<div class="row">
							<div class="col-md-4">
								<button type="submit" class="btn btn-default" data-loading-text="提交中...">发布评论</button>
							</div>
						</div>
					</div>
				</div>
			</form>
		</div>
		<div style="clear: both;"></div>
	</div>
	<script src="static/js/basic.js"></script>
	<script src="static/js/common.js"></script>
	<script charset="utf-8" src="static/kindeditor-4.1.10/kindeditor-min.js"></script>
	<script charset="utf-8" src="static/kindeditor-4.1.10/lang/zh_CN.js"></script>
	<script src="static/kindeditor-4.1.10/plugins/code/prettify.js" type="text/javascript"></script> 
	<script type="text/javascript">
	$(function(){
		prettyPrint();
		
		var swd = getUrlParam("wd")||"";
		if("" != swd){
			$("#article_search").val(decodeURI(swd));
		}
		
		$("#article_search").on('keyup up',function(){
			showSearchRst();
		});
		
		$("#article_search").mousedown(function(){
			showSearchRst();
		});
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
	});
	
	function hideTip(){
		$(".bdsug").hide();
	}	
	
	function showTip(){
		$(".bdsug").css("left",$("#article_search").offset().left);
		$(".bdsug").css("top",$("#article_search").offset().top + 23);
		$(".bdsug").show();
	}	
	
	function goDetail(e){
		var jthis = $(e);
		window.location.href = "docs/article/detail.htm?id=" + jthis.attr("value") + "&wd=" + encodeURI(encodeURI(jthis.html()));
	}
	
	function showSearchRst(){
		var keyWord = $("#article_search").val().trim();
		if("" == keyWord){
			return;
		}
		
		$.ajax({
	        type: "get",
	        async: false,
			url: "docs/article/search.htm",
	        data: {"keyWord": keyWord},
	        contentType: "application/json; charset=utf-8",
	        dataType: "json",
	        cache: false,
	        success: function (data) {
	        	$(".bdsug ul").empty();

	        	var result = data.result;
	        	if(0 == result){
	        		alert("操作失败" + (null != data.msg && "" != data.msg)?"," + data.msg:"" + (null != data.other && "" != data.other)?"," + data.other:"");
	        		return;
	        	}
	        	var mainData = data.loop;
	        	
	        	if(null == mainData || mainData.length == 0){
	        		$(".bdsug ul").append('<li class="bdsug-overflow">未找到相关文档</li>');
		    		$(".bdsug").show();
	        		return;
	        	}
	        	
	        	$.each(mainData,function(index,value){
	        		$(".bdsug ul").append('<li onclick="goDetail(this);" class="bdsug-overflow" value="' + value.id + '">' + value.title + '</li>');
	        	});
	        	
	        	showTip();
	        },error: function (err) {
	            alert("请求出错!");
	            if(console){
	            	console.log(err);
	            }
	        }
	    });
		
	}
	</script>
	
	<%@ include file="/WEB-INF/jsp/common/foot.jsp"%>
</body>
</html>
