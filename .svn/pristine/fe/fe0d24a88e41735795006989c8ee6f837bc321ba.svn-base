<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@ page isELIgnored="false" %>
<%
	String actionName = request.getParameter("actionName");
	String helpIdParam = request.getParameter("helpId");
	
	int helpId = 0;
	boolean hasHelp = false;
	if(helpIdParam!=null){
		helpId=Integer.parseInt(helpIdParam);
	}
%>

<div id="breadcrumb">
<a href="/"><i class="glyphicon glyphicon-home"></i> 首页</a> 
<% if(StringUtils.isNotBlank(actionName)){%>
	<a class="current"><%=actionName %></a>
<%} %>
<% if(helpId>0){ %><button id="help-btn" type="button" class="btn btn-help" title="使用说明">?</button><%} %>
<button id="chart-btn" type="button" title="图形" class="show-chart btn glyphicon glyphicon-stats"></button>
<button id="table-btn" type="button" title="表格" class="show-chart btn glyphicon glyphicon-list"></button>
<div id="nav-common-right">
	<form class="form-inline">
		<select class="form-control" id="navPlat" style="display: none;"></select>
		<select class="form-control" id="navSid" style="display: none;"></select>
		<select class="form-control multiselect" multiple="multiple" id="navMultiSid" style="display: none;"></select>
		<div id="navExport" style="float: right !important;margin-left: 5px;display: none;margin-top: -1px;">
			<button type="button" class="form-control btn btn-default" id="navExportBtn" data-loading-text="正在操作中...">导出</button>
		</div>
		<div id="navSubmit" style="float: right !important;margin-left: 5px;display: none;margin-top: -1px;">
			<button type="button" class="form-control btn btn-default" id="navSubmitBtn" data-loading-text="正在操作中...">查询</button>
		</div>
		<div id="navdaterange" class="pull-right" style="display: none;">
			<i class="glyphicon glyphicon-calendar fa fa-calendar"></i>
			<span></span> <b class="caret"></b>
		</div>
		<div id="navdate" class="pull-right" style="display: none;">
			<i class="glyphicon glyphicon-calendar fa fa-calendar"></i>
			<span></span> <b class="caret"></b>
		</div>
	</form>
</div>
</div>

<script type="text/javascript">
$(function(){
	$("#help-btn").click(function(e) {
		var helpIdVal = $("#help-btn").attr("value")||<%=helpId%>;
		
		$("#help-btn").attr("title","<a target='_blank' style='font-weight: bold;' href='docs/article/detail.htm?id="+helpIdVal+"'>查看详细</a><br/>")
	
		var _this = this;

		if(helpIdVal>0){
			$.ajax({
		        type: "get",
		        async: false,
		        url: "docs/article/ajaxGetDetail.mf?id=" + helpIdVal,
		        dataType: "html",
		        success: function (data) {
		        		//data ="<a target='_blank' style='color:red;font-size:18px;font-weight: bold;' href='docs/article/detail.htm?id="+helpId+"'>点击查看详细帮助文档</a><br/>"+data;
		        		showHelp(_this,data);
		        },error: function (err) {
		            alert("请求出错!");
		           }
		    });
			$("body").bind("mousedown", onBodyDown);
		}
	});
	
	function onBodyDown(e) {
		e = window.event || e;
		if (!$(e.target).parents(".popover").length>0 && !$(e.target).siblings(".nav-popover-detail").attr("v")) {
			hidePopover();
		}
		
		// 阻止默认行为并取消冒泡
		if(typeof e.preventDefault === 'function'){
			e.preventDefault();
			e.stopPropagation();
		}else{
			e.returnValue = false;
			e.cancelBubble = true;
		}
	}
	
	function hidePopover() {
		$(".popover").fadeOut("fast");
		$(".btn-help").popover('hide');
		$("body").unbind("mousedown", onBodyDown);
	}
});
</script>