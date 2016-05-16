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
<title>起手牌</title>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/top.jsp">
		<jsp:param name="nav" value="data" />
	</jsp:include>

	<jsp:include page="/WEB-INF/jsp/common/dataMenu.jsp">
		<jsp:param name="nav" value="rate" />
		<jsp:param name="subnav" value="startHandRate" />
	</jsp:include>
	
	<div class="main">
		<jsp:include page="/WEB-INF/jsp/common/mainNav.jsp">
			<jsp:param name="actionName" value="起手牌" />
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
								<button type="button" class="form-control btn btn-default" id="searchStartHandRate" data-loading-text="查询中...">查询</button>
							</div>
						</div>
					</div>
				</div>
			</form>
			
			<h5 class="page-header">&nbsp;</h5>
		
			<div>
				<table id="startHand" class="table table-bordered display"  cellspacing="0" width="100%">
					<thead>
						<tr>
							<th align="center">起手牌</th>
							<th align="center">理论概率</th>
							<th align="center">实际概率</th>
							<th align="center">偏差比例</th>
							<th align="center">偏差概率</th>
							<th align="center">偏差数量</th>
							<th align="center">牌型次数</th>
							<th align="center">牌型总数</th>
						</tr>
					</thead>
				</table>
				<div class="totalDiv"><small id="startHandTotal"></small></div>
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
	$("#searchStartHandRate").click(function(){
		searchStartHandRate();
	});
	var date = new Date();
	date.addDays(-1);
	$("#stm").val(date.format('yyyy-MM-dd'));
	$("#etm").val(date.format('yyyy-MM-dd'));
	searchStartHandRate();
	 commonData.oLanguage.sInfo = "";
	 commonData.oLanguage.sInfoEmpty = "";
});
</script>
</body>
</html>
