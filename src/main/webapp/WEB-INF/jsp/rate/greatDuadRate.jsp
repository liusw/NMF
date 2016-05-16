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
<title>起手遇更好对子概率</title>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/top.jsp">
		<jsp:param name="nav" value="data" />
	</jsp:include>

	<jsp:include page="/WEB-INF/jsp/common/dataMenu.jsp">
		<jsp:param name="nav" value="rate" />
		<jsp:param name="subnav" value="greatDuadRate" />
	</jsp:include>
	
	<div class="main">
		<jsp:include page="/WEB-INF/jsp/common/mainNav.jsp">
			<jsp:param name="actionName" value="起手遇更好对子概率" />
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
								<button type="button" class="form-control btn btn-default" id="searchGreatDuadRate" data-loading-text="查询中...">查询</button>
							</div>
						</div>
					</div>
				</div>
			</form>
			
			<h5 class="page-header">&nbsp;</h5>
		
			<div>
				<table id="greatDuad" class="table table-bordered display"  cellspacing="0" width="100%">
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
				<div class="totalDiv"><small id="greatDuadTotal"></small></div>
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
	$("#searchGreatDuadRate").click(function(){
		searchGreatDuadRate();
	});
	var date = new Date();
	date.addDays(-1);
	$("#stm").val(date.format('yyyy-MM-dd'));
	$("#etm").val(date.format('yyyy-MM-dd'));
	searchGreatDuadRate();
	 commonData.oLanguage.sInfo = "";
	 commonData.oLanguage.sInfoEmpty = "";
});

//查询牌局概率
function searchGreatDuadRate(){
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
	$("#searchGreatDuadRate").button("loading");
	$.ajax({
        type: "get",
        url: 'paijuRate/getRate/greatRate.htm',
        data :{
        	stm		: stm,
        	etm		: etm,
        	plat	: plat,
        	dt		: 2
        },
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data) {
        	 var table = $('#greatDuad').DataTable({
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
        		showGreatDuadRate(table,data.obj);
        	}
        	$("#searchGreatDuadRate").button("reset");
        },
        error: function (err) {
            showMsg("请求出错!");
            myLog(err);
            $("#searchGreatDuadRate").button("reset");
        }
    });
}

function showGreatDuadRate(table,data){
	var json = eval(data);
	var kv = convertGreatDuadData(json);
	var showPai = new Array('KK','QQ','JJ','TT','99','88','77','66','55','44','33','22');
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

function convertGreatDuadData(json){
	var data = new Array();
	for(var i in json){
		var type = json[i].type;
		var theoryRate = 0;
		if(type=='D'){
			theoryRate = 0.004898;
		}else if(type=='C'){
			theoryRate = 0.009796;
		}else if(type=='B'){
			theoryRate = 0.014694;
		}else if(type=='A'){
			theoryRate = 0.019592;
		}else if(type=='9'){
			theoryRate = 0.024490;
		}else if(type=='8'){
			theoryRate = 0.029388;
		}else if(type=='7'){
			theoryRate = 0.034286;
		}else if(type=='6'){
			theoryRate = 0.039184;
		}else if(type=='5'){
			theoryRate = 0.044082;
		}else if(type=='4'){
			theoryRate = 0.048980;
		}else if(type=='3'){
			theoryRate = 0.053878;
		}else if(type=='2'){
			theoryRate = 0.058776;
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
