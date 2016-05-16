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
	#sidebar > ul li ul li a {
		border-bottom:1px dashed #303030;
	}
	
	.navRight {
	    color: #FF6600;
	    cursor: pointer;
	    float: right;
	    height: 38px;
	    line-height: 38px;
	    padding-right: 20px;
	}
	#example thead  th {
		text-align: center;
	}
</style>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/top.jsp">
		<jsp:param name="nav" value="docs" />
	</jsp:include>
	
	<jsp:include page="helpMenu.jsp">
		<jsp:param name="cid" value="${cId}" />
	</jsp:include>
	<div class="main">
		<div id="breadcrumb">
			<a href="/"><i class="glyphicon glyphicon-home"></i> 首页</a> <a class="current">帮助中心</a>
			<span class="navRight">
				<input id="article_search" style="float: left;margin-bottom: 0px;" placeholder="请输入关键字" maxlength="255" onMouseOut="hideTip();">
				<div class="bdsug" style="height: auto; display: none;" onMouseOver="showTip();" onMouseOut="hideTip();">
					<ul>
					</ul>
				</div>
				<privilege:operate url="/docs/article/addOrUpdate.htm">
					<p style="float: left;margin-bottom: 0px;"><a href="docs/article/edit.htm?cId=${cId}" class="glyphicon glyphicon-plus-sign" style="background: none;">添加文档</a></p>
				</privilege:operate>
				
				<privilege:operate url="/docs/category/index.htm">
					<p style="float: left;margin-bottom: 0px;"><a href="docs/category/index.htm" class="glyphicon glyphicon-th" style="background: none;">分类管理</a></p>
				</privilege:operate>
			</span>
		</div>
		
		<table id="example" class="table table-striped table-bordered display"  cellspacing="0" width="100%">
			<thead>
				<tr>
					<th>id</th><th width="50%">标题</th><th width="10%">创建人</th><th width="20%">创建时间</th><th width="20%">更新时间</th>
				</tr>
			</thead>
		</table>
	</div>
	<div style="clear: both;"></div>
	<input type="text" id="cid" autocomplete="off" value="${cId}">
	<br/><br/>
	<%@ include file="/WEB-INF/jsp/common/foot.jsp"%>
	<script src="static/js/basic.js"></script>
	<script src="static/js/common.js"></script>
	<script src="static/js/dataTable.js"></script>
	
	<script type="text/javascript">
	function initTable(){
		var categoryId=$("#cid").val();
		
		$("#example").DataTable({
		     "bFilter": true,
		     "pagingType" : "full_numbers",
		     "bDestroy" : true,
		     "bProcessing" : false,
		     "sAjaxSource" : "docs/article/findArticlesByCategoryId.htm"+(categoryId&&categoryId>0?"?categoryId="+categoryId:"?categoryId=" + $("#sidebar ul li:eq(1) a").attr("href").split("=")[1]),
		     "bServerSide" : true,
		     "ordering": false,
		     "columns" : [
					{ "data": "id","visible": false},
					{ "data": "title",
						"render":function(data, type, row){
							data = '<a href="docs/article/detail.htm?id='+row.id+'" target="_blank">'+ data+'</span>';
							return data;
						}
					},
					{ "data": "userName"},
					{ "data": "createTime"},
					{ "data": "updateTime"}
		         ],
		       "oLanguage": commonData.oLanguage,
		       "dom": 't<"bottom fl"l><"bottom"p>'
			});
	}
	
	function hideTip(){
		$(".bdsug").hide();
	}	
	
	function showTip(){
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
	        	
	    		$(".bdsug").show();
	        },error: function (err) {
	            alert("请求出错!");
	            if(console){
	            	console.log(err);
	            }
	        }
	    });
		
	}
	
	$(function() {
		initTable();
				
		$("#article_search").on('keyup up',function(){
			showSearchRst();
		});
		
		$("#article_search").mousedown(function(){
			showSearchRst();
		});
	});
	</script>
</body>
</html>
