<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
<div class="main" style="margin-top: -45px;">
<div class="commentDiv">
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
	<div class="titleTip">发表评论</div>
	<form class="form-horizontal" method="POST">
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
							<button type="button" onclick="addComment()" class="btn btn-default" data-loading-text="提交中...">发布评论</button>
						</div>
					</div>
				</div>
			</div>
		</form>
	</div>
</div>
<script charset="utf-8" src="static/kindeditor-4.1.10/kindeditor-min.js"></script>
<script charset="utf-8" src="static/kindeditor-4.1.10/lang/zh_CN.js"></script>
<script src="static/kindeditor-4.1.10/plugins/code/prettify.js" type="text/javascript"></script> 
<script type="text/javascript">
$(function(){
	prettyPrint();
	
	getCommnet();
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
	
function addComment(){
	$.post("docs/comment/add.mf", {
		type:1,oId:1,resultMethod:true,content:$("#content").val()
	},
	function(data) {
		getCommnet();
		editor.html('');
	},
	"json").error(function(){
		alert('提交失败');
	});
}

	function getCommnet() {
		$.ajax({
			type : "get",
			async : false,
			url : "docs/comment/ajaxGetComment.mf",
			data : {
				"type" : 1,
				"oId" : 1
			},
			success : function(data) {
				$("#commentHolder").html('');
				data = eval('(' + data + ')');
				$.each(data, function(index, value) {
					var html = "<div class='comment'><p class='title'>"+value.realName+"&nbsp;&nbsp;&nbsp;&nbsp;"+value.createTime+"</p>"+value.content+"</div>";
					$("#commentHolder").append(html);
				});
			}
		});
	}
</script>