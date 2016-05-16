<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en-us">
<head>
<%@ include file="/WEB-INF/jsp/common/meta.jsp"%>
<style type="text/css">
#example thead  th,#example tbody td{
	text-align: center;
}
</style>
<title>黑名单-数据魔方</title>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/top.jsp">
		<jsp:param name="nav" value="data" />
	</jsp:include>

	<jsp:include page="/WEB-INF/jsp/common/dataMenu.jsp">
		<jsp:param name="nav" value="brush" />
		<jsp:param name="subnav" value="black" />
	</jsp:include>
	
	<div class="main">
		<jsp:include page="/WEB-INF/jsp/common/mainNav.jsp">
			<jsp:param name="actionName" value="黑名单" />
		</jsp:include>
		
		<form class="form" id="queryFrom" method="post">
			<div class="row">
				<div class="form-group col-xs-3">
					<label>平台站点：</label>
					<select id="plat" name="plat" class="form-control">
					</select>
				</div>
				<div class="form-group col-xs-3">
					<label>用户ID：</label>
					<input type="text" id="uid" name="uid" value="${uid}" class="form-control"  autocomplete="off">
					</div>
				<div class="form-group col-xs-3">
						<label>日期：</label>
						<input type="text" id="rstime" name="rstime" value="${rstime}" class="form-control time" placeholder="开始时间" autocomplete="off">
				</div>
				<div class="form-group col-xs-3">
					<label>&nbsp;</label>
					<input type="text" id="retime" name="retime" value="${retime}" class="form-control time" placeholder="结束时间" autocomplete="off">
				</div>
				<div class="form-group col-xs-3">
					<label>状态：</label>
					<select id="status" name="status" class="form-control">
						<option value="">全部</option>
						<option value="0" <c:if test="${status==0 }">selected="selected"</c:if>>标记</option>
						<option value="1" <c:if test="${status==1 }">selected="selected"</c:if>>解除</option>
					</select>
				</div>
				<div class="form-group col-xs-3">
				<label>&nbsp;</label><br>
					<button type="button" class="btn btn-default" id="query" data-loading-text="提交中..."  onclick="queryData()">查询</button>
				<button type="button" class="btn btn-default" id="export" data-loading-text="提交中..."  onclick="exportData();">导出</button>
				<button type="button" class="btn btn-default" id="query" data-loading-text="提交中..."  onclick="markAll()">全部标记</button>
				</div>
			</div>
		</form>
		<section>
			<table id="example" style="font-size: 10px" class="table table-striped table-bordered" cellspacing="0" width="100%">
				<thead>
					<tr>
						<th width="6%" align="center">平台</th>
						<th width="6%" align="center">用户ID</th>
						<th width="7%" align="center">用户名</th>
						<th width="6%" align="center">等级</th>
						<th width="6%" align="center">语言</th>
						<th width="8%" align="center">地区</th>
						<th width="7%" align="center">邮箱</th>
						<th width="9%" align="center">注册时间</th>
						<th width="6%" align="center">台费</th>
						<th width="9%" align="center">登录时间</th>
						<th width="8%" align="center">登录ip</th>
						<th width="8%" align="center">游戏币</th>
						<th width="8%" align="center">消费金额</th>
						<th width="5%" align="center">操作</th> 
					</tr>
				</thead>
				<tbody>
					<tr>
					</tr>
				</tbody>
			</table>
		</section>
	</div>
	<br/><br/><br/>
	 <%@ include file="/WEB-INF/jsp/common/foot.jsp"%>

	<!-- Bootstrap core JavaScript navbar-fixed-bottom
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="static/js/basic.js"></script>
	<script src="static/js/dataTable.js"></script>
	<script src="static/js/datetimepicke.js"></script>
	<script src="static/js/common.js"></script>
	<script type="text/javascript">
	$(function() {
		//初始化日期插件
		initDatetimepicker();
		getPlat("#plat");
		var plat = <%=session.getAttribute("plat")%>;
		if(plat != "" && plat != null){
			$("#plat").val(plat);
		}
		//加载datatable
	        $("#example").dataTable({
	            "bFilter": false,//去掉搜索框
	            "pagingType" : "full_numbers",
	            "bDestroy" : true,
	            "bProcessing" : false,
	            "data": [],
		         "oLanguage":{//汉化
	                    "sLengthMenu":"显示 _MENU_ 条记录",
	                    "sZeroRecords":"没有检索到数据",
	                    "sInfo":"当前数据为从 _START_ to _END_ 条数据；总共有 _TOTAL_ 条记录",
	                    "sInfoEmpty":"没有数据",
	                    "sProcessing":"正在加载数据......",
	                    "sSearch":"查询",
	                    "sInfoFiltered": "(过滤自 _MAX_ 条记录)",
	                    "oPaginate":{
	                        "sFirst":"首页",
	                        "sPrevious":"前页",
	                        "sNext":"后页",
	                        "sLast":"尾页"
	                    }
	                }
	        });
	} );
	
	function markAll(){
		var surl = "log/brushServlet?action=markAll";
		$.ajax({
		    type: "get",
		    async: false,
		    url: surl,
		    contentType: "application/json; charset=utf-8",
		    cache: false,
		    success: function (data) {
		    	initTable();
	   	},error: function (err) {
	       	alert("请求出现异常:"+err);
	   		}
		}); 
	}
	
	function queryData(){
		initTable();
	}
	
	function gettPage(){
		return $('#example').dataTable().api().page();
	}
	
	function mark(id){
		var uid = $("#uid").val();
		var rstime = $("#rstime").val();
		var retime = $("#retime").val();
		
		var surl = "log/brushServlet?action=mark&&id="+id+"&&uid="+uid+"&&rstime="+rstime+"&&retime="+retime;
		 $.ajax({
		    type: "get",
		    async: false,
		    url: surl,
		    contentType: "application/json; charset=utf-8",
		    cache: false,
		    success: function (data) {
		    	$('#example').dataTable().fnPageChange(gettPage());  
	   	},error: function (err) {
	       	alert("请求出现异常:"+err);
	   		}
		}); 
		
	}
	
	function removes(id){
		var uid = $("#uid").val();
		var rstime = $("#rstime").val();
		var retime = $("#retime").val();
		var surl = "log/brushServlet?action=remove&&id="+id+"&&uid="+uid+"&&rstime="+rstime+"&&retime="+retime;
		$.ajax({
		    type: "get",
		    async: false,
		    url: surl,
		    contentType: "application/json; charset=utf-8",
		    cache: false,
		    success: function (data) {
		    	$('#example').dataTable().fnPageChange(gettPage());
	   	},error: function (err) {
	       	alert("请求出现异常:"+err);
	   		}
		});
	}
	
	function exportData(){
		$.ajax({
		    type: "get",
		    async: false,
		    url: "log/brushServlet",
		    data:{action:"exportData",uid:$("#uid").val(),rstime:$("#rstime").val(),retime:$("#retime").val(),status:$("#status").val(),plat:$("#plat").val()},
		    contentType: "application/json; charset=utf-8",
		    dataType: "json",
		    cache: false,
		    success: function (data) {
		    	if(data.ok==1) {
		    		showMsg("<a href='log/brushServlet?action=down&fileName="+data.msg+"' target='_blank'>"+data.msg+"</a>","下载链接");
		    	}else{
		    		showMsg(data.msg,"提示");
		    	}
	   	},error: function (err) {
	       	alert("请求出现异常:"+err);
	   		}
		});
	}
	
	function initTable() {
        var paras = $("form").serialize();
        $("#example").dataTable({
            "bFilter": false,//去掉搜索框
            "pagingType" : "full_numbers",
            "bDestroy" : true,
            "bProcessing" : false,
            "sAjaxSource" : "log/brushServlet?action=findBrushUser" + "&" + paras,
            "bServerSide" : true,
            "columns" : [{
                "data" : "_plat"
	            }, {
	                "data" : "_uid"
	            }, {
	                "data" : "mnick"
	            }, {
	                "data" : "mexplevel"
	            }, {
	                "data" : "lang"
	            }, {
	                "data" : "login_name"
	            }, {
	                "data" : "email"
	            }, {
	                "data" : "mtime"
	            }, {
	                "data" : "vmoney"
	        	}, {
	                "data" : "mactivetime"
	        	}, {
	                "data" : "loginip"
	        	}, {
	                "data" : "mmoney"
	        	}, {
	                "data" : "mpay"
	        	},{ 
	        	 "data": null,orderable: false
	        	}  
	            ],
	         "fnRowCallback":function(nRow,aData,iDataIndex){
	              var aDataId = aData.id;
	              if("0" == aData.status){      
	          		   $('td:eq(-1)',nRow).html("<button type='button' class='btn btn-success' id='remove' data-loading-text='提交中...'  onclick='mark("+aDataId+")'>标记</button>");
	          		   } 
	    	        if("1" == aData.status){
	    	        		$('td:eq(-1)',nRow).html("<button type='button' class='btn btn-danger' id='remove' data-loading-text='提交中...'  onclick='removes("+aDataId+")'>解除</button>");
	          	  	   }
	    	        
	            } ,
	         "oLanguage":{//汉化
                    "sLengthMenu":"显示 _MENU_ 条记录",
                    "sZeroRecords":"没有检索到数据",
                    "sInfo":"当前数据为从 _START_ to _END_ 条数据；总共有 _TOTAL_ 条记录",
                    "sInfoEmpty":"没有数据",
                    "sProcessing":"正在加载数据......",
                    "sSearch":"查询",
                    "sInfoFiltered": "(过滤自 _MAX_ 条记录)",
                    "oPaginate":{
                        "sFirst":"首页",
                        "sPrevious":"前页",
                        "sNext":"后页",
                        "sLast":"尾页"
                    }
                }
        });
    }
	</script>
</body>
</html>
