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
#userSourceTable thead  th,#userSourceTable tbody td{
	text-align: center;
}
.form-horizontal .form-group{
	border: none;
}
.well{
	background: #FFFFFF;
	border-radius: 0;
	margin-top: 10px;
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
#showUserSourceMsg{
	text-align: center;
	margin: 0 auto;
}
#userSourceDiv{
	overflow : auto;
}
</style>
<title>用户来源-数据魔方</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/top.jsp">
	<jsp:param name="nav" value="data" />
</jsp:include>

<jsp:include page="/WEB-INF/jsp/common/dataMenu.jsp">
	<jsp:param name="nav" value="lc" />
	<jsp:param name="subnav" value="userSource" />
</jsp:include>

<div class="main">
	<jsp:include page="/WEB-INF/jsp/common/mainNav.jsp">
		<jsp:param name="actionName" value="用户来源" />
		<jsp:param name="helpId" value="126" />
	</jsp:include>
	<div class="well">
		<div class="block-title">
			<div class="item-tabs">
	            <a tag="#feed" fn="getFeed()" class="item active" href="javascript:;">Feed</a>
	            <a tag="#fbSource" fn="getFbSource()" class="item" href="javascript:;">FbSource来源明细</a>
	            <a tag="#fbRef" fn="getFbRel()" class="item" href="javascript:;">FbRef来源明细</a>
            </div>
		</div>
		<div id="userSourceDiv">
			<table id="userSourceTable" class="table table-striped table-bordered display" cellspacing="0" width="100%">
				<thead>
					<tr id="head1">
					</tr>
					<tr id="head2">
					</tr>
				</thead>
				<tbody id="tbody">
				
				</tbody>
			</table>
			<div id="showUserSourceMsg"></div>
		</div>
		
		<div id="feed">
		</div>
		
		<div id="fbSource" class="displayNone">
		</div>
		
		<div id="fbRef" class="displayNone">
		</div>
		
		<input type="hidden" id="type" value="1">
	</div>
</div>

 <%@ include file="/WEB-INF/jsp/common/foot.jsp"%>

<!-- Bootstrap core JavaScript navbar-fixed-bottom
   ================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="static/js/basic.js"></script>
<script src="static/js/dataTable.js"></script>
<script src="static/js/daterangepicker.js"></script>
<script src="static/js/daterange.by.js"></script>
<script src="static/js/dateUtils.js"></script>
<script src="static/js/common.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	var std = new Date();
	std.addDays(-10);
	std.format('yyyy-MM-dd');
	
	navForm = navForm.init({'showPlat':true,'showSid':false,'showDate':{'isShow':true,'startDate':std.toFormatDate()},
		platChangeCallback:updateNavMultiSid,'showBtn':true
	});
	
	$("#navSubmitBtn").click(function(){
		searchUserSource();
	});
	commonData.oLanguage.sInfo = "";
	commonData.oLanguage.sInfoEmpty = "";
	initItems();
	
	$("#navSubmitBtn").trigger("click");
});

function searchUserSource(){
	var type = $("#type").val();
	if(type == 1){
		getFeed();
	}else if(type == 2){
		getFbSource();
	}else if(type == 3){
		getFbRel();
	}
}

function getFeed(){
	$("#type").val(1);
	getUserSource();
}

function getFbSource(){
	$("#type").val(2);
	getUserSource();
}

function getFbRel(){
	$("#type").val(3);
	getUserSource();
}

function getUserSource(){
	var param = {};
	var type = $("#type").val();
	var plat = $("#navPlat").val();
	var stm = navForm.daterange.getStartDate("#navdaterange");
	var etm = navForm.daterange.getEndDate("#navdaterange");
	
	$("#head1").html("");
	$("#head2").html("");
	$("#tbody").html("");
	$("#showUserSourceMsg").html("正在查询数据。。。");
	param.type = type;
	param.plat = plat;
	param.stm = stm;
	param.etm = etm;
	var json = getJsonData2("data/userSource/getUserSource.htm", param, true);
	if(!$.isEmptyObject(json)){
		$("#showUserSourceMsg").html("");
		var datas = {};
		var namePosition = {};
		var results = [];
		var j = 0;
		
		//for(var i in json){//同一个日期的放在一起
		for(var i=0;i<json.length;i++){
			var name = json[i].name;
			if(!namePosition[name]){
				namePosition[name] = j++;
			}
			var tm = json[i].tm;
			if(!datas[tm]){
				datas[tm] = [];
			}
			datas[tm].push(json[i]);
		}
		for(var obj in datas){
			var data = datas[obj];
			var result = {};
			var i = 0;
			result[i++] = obj;
			result[i++] = Date.newInstance(obj).chinaDay(true);
			var find = false;
			for(var p in namePosition){//根据name排数据，没有name值的，数据都为0
				find = false;
				for(var d in data){
					var tmpData = data[d];
					if(p == tmpData.name){
						if(type == 1){//只有feed才有feed发送
							result[i++] = tmpData.feedCount;
						}
						result[i++] = tmpData.registerCount;
						result[i++] = tmpData.activeCount;
						find = true;
						break;
					}
				}
				if(!find){
					if(type == 1){
						result[i++] = 0;
					}
					result[i++] = 0;
					result[i++] = 0;
				}
			}
			results.push(result);
		}
		$("#head1").append('<th rowspan="2">时间</th>');
		$("#head1").append('<th rowspan="2">星期</th>');
		for(var p in namePosition){
			if(type == 1){
				$("#head1").append("<th colspan='3'>" + p + "</th>");
			}else{
				$("#head1").append("<th colspan='2'>" + p + "</th>");
			}
			if(type == 1){
				$("#head2").append("<th>feed发送</th>");
			}
			$("#head2").append("<th>注册</th>");
			$("#head2").append("<th>活跃</th>");
		}
		/**
	  	var userSourcetable = $('#userSourceTable').dataTable({
	  		bDestroy		: true,
	        searching		: false,
			paging			: false,
			iCookieDuration	: 0,
			data 			: results,
			"scrollX"		: true,
			"oLanguage": commonData.oLanguage,
		    "tableTools": {
		         "sSwfPath": "css/swf/copy_csv_xls_pdf.swf",
		         "aButtons": [
		                      {
		                          "sExtends": "copy",
		                          "sButtonText": "复制",
		                          "bBomInc": true
		                      },
		                      {
		                          "sExtends": "xls",
		                          "sButtonText": "下载",
		                          "bBomInc": true
		                      }
		                  ]
		     }
	    });
	  	**/
	  	var i = 0;
	  	for(var r in results){
	  		var html = "";
	  		if(i % 2 == 0){
	  			html = '<tr role="row" class="even">';
	  		}else{
	  			html = '<tr role="row" class="odd">';
	  		}
	  		var result = results[r];
	  		for(var d in result){
	  			html += '<td>' + result[d] + '</td>';
	  		}
	  		html += '</tr>';
	  		$("#tbody").append(html);
	  		i++;
	  	}
	}else{
		$("#showUserSourceMsg").html("没有相关数据。。。");
	}
}


</script>
</body>
</html>