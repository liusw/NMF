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
thead  th,tbody td{
	text-align: center;
}
tr td{
	border-right-width: 2px;
}
.text-red{
	color : #F87E7E;
} 
.text-blue{
	color : #8DD4F2;
}
.text-green{
	color : #99F9B5;
}
.background-red{
	background-color : #F87E7E;
} 
.background-blue{
	background-color : #8DD4F2;
}
.background-green{
	background-color : #99F9B5;
}
div.DTTT_container{
	margin-bottom: 6px;
}
.colorTip{
	float : left;
	margin-left : 30px;
	margin-right: 5px;
}
.colorTipDivs{
	padding-bottom: 30px;
    padding-top: 10px;
    margin-top : 10px;
}
.page-header{
	margin : 0 0 20px 0;
}
.totalDiv{
	margin-left:15px;
	margin-top:-15px;
	padding-bottom: 10px;
}
</style>
<title>起手遇更好A概率</title>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/top.jsp">
		<jsp:param name="nav" value="data" />
	</jsp:include>

	<jsp:include page="/WEB-INF/jsp/common/dataMenu.jsp">
		<jsp:param name="nav" value="rate" />
		<jsp:param name="subnav" value="greatARate" />
	</jsp:include>
	
	<div class="main">
		<jsp:include page="/WEB-INF/jsp/common/mainNav.jsp">
			<jsp:param name="actionName" value="起手遇更好A概率" />
			<jsp:param name="helpId" value="103" />
		</jsp:include>
		
			<form class="form">
				<div class="row">
					<div class="col-xs-3">
						<div class="row">
							<div class="form-group col-xs-12">
								<label>平台站点：</label>
								<select id="plats" name="plat" class="form-control"></select>
							</div>
						</div>
					</div>
					<div class="col-xs-6">
						<div class="row">
							<div class="form-group col-xs-6">
								<label>时间：</label>
								<input type="text" id="stm" class="form-control time" placeholder="开始时间" autocomplete="off">
							</div>
							<div class="form-group col-xs-6">
								<label>&nbsp;</label>
								<input type="text" id="etm" class="form-control time" placeholder="结束时间" autocomplete="off">
							</div>
						</div>
					</div>
					<div class="col-xs-1">
						<div class="row">
							<div class="form-group col-xs-12">
								<label>&nbsp;</label>
								<button type="button" class="form-control btn btn-default" id="searchGreatARate" data-loading-text="查询中...">查询</button>
							</div>
						</div>
					</div>
				</div>
			</form>
			
			<h5 class="page-header">&nbsp;</h5>
		
			<div>
				<table id="greatA" class="table table-bordered display"  cellspacing="0" width="100%">
					<thead>
						<tr>
							<th align="center">起手牌</th>
							<th align="center">1玩家理论概率</th>
							<th align="center">实际概率</th>
							<th align="center">偏差比例</th>
							<th align="center">偏差概率</th>
							<th align="center">偏差数量</th>
							<th align="center">牌型次数</th>
							<th align="center">牌型总数</th>
						</tr>
					</thead>
				</table>
				<div class="totalDiv"><small id="greatATotal"></small></div>
			</div>
		</div>
	<br/><br/><br/>
	 <%@ include file="/WEB-INF/jsp/common/foot.jsp"%>

<!-- Bootstrap core JavaScript navbar-fixed-bottom
   ================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="static/js/basic.js"></script>
<script src="static/js/dataTable.js"></script>
<script src="static/js/datetimepicke.js"></script>
<script src="static/js/dateUtils.js"></script>
<script src="static/js/common.js"></script>
<script src="static/js/pokerRate.js"></script>
<script type="text/javascript">
$(function(){
	getMultiPlat("#plats",null,null,setPlat);
	initDatetimepicker();
	$("#searchGreatARate").click(function(){
		searchGreatARate();
	});
	var date = new Date();
	date.addDays(-1);
	$("#stm").val(date.format('yyyy-MM-dd'));
	$("#etm").val(date.format('yyyy-MM-dd'));
	searchGreatARate();
	 commonData.oLanguage.sInfo = "";
	 commonData.oLanguage.sInfoEmpty = "";
});

//查询牌局概率
function searchGreatARate(){
	var plat = $("#plats").val();
	var stm = $("#stm").val();
	var etm = $("#etm").val();
	if(!stm){
		showMsg("请填写开始日期！");
		return false;
	}
	if(!etm){
		showMsg("请填写结束日期！");
		return false;
	}
	$("#searchGreatARate").button("loading");
	$.ajax({
        type: "get",
        url: 'paijuRate/getRate/greatRate.htm',
        data :{
        	stm		: stm,
        	etm		: etm,
        	plat	: plat,
        	dt		: 1
        },
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data) {
        	 var table = $('#greatA').DataTable({
        		 destroy		: true,
        		 searching		: false,
        		 ordering		: false,
        		 processing		: true,
        		 paging			: false,
        		 data 			: {},
    	         "tabIndex" 	: 1,
    	    	 "oLanguage": commonData.oLanguage,
    	    	  "dom": 'T<"clear">lfrtip',
    	          "tableTools": {
    	              "sSwfPath": "css/swf/copy_csv_xls_pdf.swf",
    	              "aButtons": [
    	                           {
    	                               "sExtends": "copy",
    	                               "sButtonText": "复制"
    	                           },
    	                           {
    	                               "sExtends": "xls",
    	                               "sButtonText": "下载",
    	                               "bBomInc": true
    	                           }
    	                       ]
    	          }
    	    });
        	if(!$.isEmptyObject(data.obj)){
        		showGreatARate(table,data.obj);
        	}
        	$("#searchGreatARate").button("reset");
        },
        error: function (err) {
            showMsg("请求出错!");
            myLog(err);
            $("#searchGreatARate").button("reset");
        }
    });
}

function showGreatARate(table,data){
	var json = eval(data);
	var kv = convertGreatAData(json);
	var showPai = new Array('AK','AQ','AJ','AT','A9','A8','A7','A6','A5','A4','A3','A2');
	var pai = new Array('D','C','B','A','9','8','7','6','5','4','3','2');
	for(var i in showPai){
		var key = pai[i];
		var row = new Array();
		row.push(showPai[i]);
		var dataTmp = kv.getValue(key);
		if(dataTmp != null){
			row.push(dataTmp.theoryRate + "%");
			row.push(dataTmp.trueRate + "%");
			row.push("<span data-toggle='tooltip' data-placement='top' title='实际概率/理论概率'>" + dataTmp.offsetMul + "</span>");
			row.push("<span data-toggle='tooltip' data-placement='top' title='实际概率-理论概率'>" + dataTmp.offsetPer + "%</span>");
			row.push("<span data-toggle='tooltip' data-placement='top' title='(实际概率-理论概率)*牌型次数'>" + dataTmp.offset + "</span>");
			row.push(dataTmp.num);
			row.push(dataTmp.count);
			table.row.add(row).draw();
		}
	}
}

function convertGreatAData(json){
	var data = new Array();
	for(var i in json){
		var type = json[i].type;
		var theoryRate = 0;
		if(type=='D'){
			theoryRate = 0.002449;
		}else if(type=='C'){
			theoryRate = 0.012245;
		}else if(type=='B'){
			theoryRate = 0.022041;
		}else if(type=='A'){
			theoryRate = 0.031837;
		}else if(type=='9'){
			theoryRate = 0.041633;
		}else if(type=='8'){
			theoryRate = 0.051429;
		}else if(type=='7'){
			theoryRate = 0.061224;
		}else if(type=='6'){
			theoryRate = 0.071020;
		}else if(type=='5'){
			theoryRate = 0.080816;
		}else if(type=='4'){
			theoryRate = 0.090612;
		}else if(type=='3'){
			theoryRate = 0.100408;
		}else if(type=='2'){
			theoryRate = 0.110204;
		}
		var d = {
			"type"  : json[i].type,
			"theoryRate" : theoryRate,
			"trueRate"	: json[i].num/json[i].count,
			"offsetMul" : theoryRate==0?0:(json[i].num/json[i].count)/theoryRate,
			"offsetPer" : json[i].num/json[i].count-theoryRate,
			"offset" : parseInt(json[i].num*(json[i].num/json[i].count-theoryRate)),
			"num"	: json[i].num,
			"count"	: json[i].count
		};
		data.push(d);
	}
	return dealRate(data);
}
</script>
</body>
</html>
