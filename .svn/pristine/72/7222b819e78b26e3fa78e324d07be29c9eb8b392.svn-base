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
<title>手牌概率</title>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/top.jsp">
		<jsp:param name="nav" value="data" />
	</jsp:include>

	<jsp:include page="/WEB-INF/jsp/common/dataMenu.jsp">
		<jsp:param name="nav" value="rate" />
		<jsp:param name="subnav" value="shoupaiRate" />
	</jsp:include>
	
	<div class="main">
		<jsp:include page="/WEB-INF/jsp/common/mainNav.jsp">
			<jsp:param name="actionName" value="牌局概率" />
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
				<div class="col-xs-2">
					<div class="row">
						<div class="form-group col-xs-12">
							<label>类型：</label>
							<select class="form-control" id="type">
								<option value="xinshou">新手场</option>
								<option value="czgk">初中高快速场</option>
								<option value="10Wblindmax">10万大盲</option>
								<option value="20Wblindmax">20万大盲</option>
								<option value="40Wblindmax">40万大盲</option>
								<option value="100Wblindmax">100万大盲</option>
							</select>
						</div>
					</div>
				</div>
				<div class="col-xs-1">
					<div class="row">
						<div class="form-group col-xs-12">
							<label>&nbsp;</label>
							<button type="button" class="form-control btn btn-default" id="searchPaijuRate" data-loading-text="查询中...">查询</button>
						</div>
					</div>
				</div>
			</div>
		</form>
		
		<h5 class="page-header">&nbsp;</h5>
	
		<div>
			<table id="shoupai" class="table table-bordered display"  cellspacing="0" width="100%">
				<thead>
					<tr>
						<th align="center">手牌概率</th>
						<th align="center">A</th>
						<th align="center">K</th>
						<th align="center">Q</th>
						<th align="center">J</th>
						<th align="center">T</th>
						<th align="center">9</th>
						<th align="center">8</th>
						<th align="center">7</th>
						<th align="center">6</th>
						<th align="center">5</th>
						<th align="center">4</th>
						<th align="center">3</th>
						<th align="center">2</th>
					</tr>
				</thead>
			</table>
			<div class="totalDiv"><small id="shoupaiTotal"></small></div>
		</div>
		
		<div class="colorTipDivs">
			<div class="colorTip"><div class="text-red colorTip">●</div>排名前十名</div>
			<div class="colorTip"><div class="text-green colorTip">●</div>排名后十名</div>
			<div class="colorTip"><div class="text-blue colorTip">●</div>没有数据</div>
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
<script src="static/js/dataTables.tableTools.min.js"></script>
<script src="static/js/pokerRate.js"></script>
<script type="text/javascript">
$(function(){
	getMultiPlat("#plats",null,null,setPlat);
	initDatetimepicker();
	$("#searchPaijuRate").click(function(){
		searchShoupaiRate();
	});
	var date = new Date();
	date.addDays(-1);
	$("#stm").val(date.format('yyyy-MM-dd'));
	$("#etm").val(date.format('yyyy-MM-dd'));
	searchShoupaiRate();
	commonData.oLanguage.sInfo = "";
	commonData.oLanguage.sInfoEmpty = "";
});
</script>
</body>
</html>
