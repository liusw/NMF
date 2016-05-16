<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en-us">
<head>
<%@ include file="/WEB-INF/jsp/common/meta.jsp"%>
<title>登录-数据魔方</title>
<link rel="stylesheet" type="text/css" href="static/daterangepicker/daterangepicker-bs3.css">
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/top.jsp">
		<jsp:param name="nav" value="data" />
	</jsp:include>

	<jsp:include page="/WEB-INF/jsp/common/dataMenu.jsp">
		<jsp:param name="nav" value="businessData" />
		<jsp:param name="subnav" value="tableData" />
	</jsp:include>
	
	<div class="main2">
		<jsp:include page="/WEB-INF/jsp/common/topTabMenu.jsp">
			<jsp:param name="tabActive" value="login" />
		</jsp:include>
		<jsp:include page="/WEB-INF/jsp/common/mainNav2.jsp" />
		
		<div class="main2Body">
	    	<div id="sub-nav">
          		<ul id="nav-tab" class="nav nav-tabs">
            		<li><a href="<%=(request.getContextPath())%>/data/login.htm">连续登录</a></li>
            		<li><a href="<%=(request.getContextPath())%>/data/doubleEndLogin.htm">双端登录</a></li>
            		<li class="active"><a href="<%=(request.getContextPath())%>/data/lostUser.htm">流失</a></li>
            		<li><a href="<%=(request.getContextPath())%>/data/loginx.htm">多端登录</a></li>
          		</ul>
		    </div>
			<form role="form">
				<div class="row">
					<div class="form-group col-xs-3">
						<label>付费情况：</label>
						<select id="ifPay" onchange="ifPayChange();" class="form-control">
							<option value="0">全部</option>
							<option value="true">付费</option>
							<option value="false">非付费</option>
						</select>
					</div>
					<div class="form-group col-xs-3" id="minPayDiv">
						<label>最小付费金额:</label>
						<input type="text" id="minPay" class="form-control" placeholder="最小付费金额">
					</div>
				</div>
				
				<div class="row">
					<div class="form-group col-xs-3">
						<label>登录时间:</label>
			          	<div id="loginDateRg" class="date-input">
								<i class="glyphicon glyphicon-calendar fa fa-calendar"></i>
								<span></span> <b class="caret"></b>
							</div>
					</div>
					<div class="form-group col-xs-3">
						<label>流失时间:</label>
			          	<div id="lostDateRg" class="date-input">
								<i class="glyphicon glyphicon-calendar fa fa-calendar"></i>
								<span></span> <b class="caret"></b>
							</div>
					</div>
				</div>
				
				<div class="row">
					<div class="form-group col-xs-12 ucuser">
						<%@include file="/WEB-INF/jsp/common/ucuser.jsp"%>
						<label class="checkbox-inline">
							<input type="checkbox" name="tableItems" value="countLostDayBankrupt" disabled checked>流失当天破产次数
						</label>
						
						<label class="checkbox-inline">
							<input type="checkbox" name="tableItems" value="countHBankrupt"  disabled checked>历史破产次数
						</label>
						
						<label class="checkbox-inline">
							<input type="checkbox" name="tableItems" value="countHOrder" disabled checked>历史付费次数
						</label>
						
						<label class="checkbox-inline">
							<input type="checkbox" name="tableItems" value="sumHPay" disabled checked>历史付费金额
						</label>
						
						<label class="checkbox-inline">
							<input type="checkbox" name="tableItems" value="hLastOrderTm" disabled checked>历史最后付费时间
						</label>
					</div>
				</div>
				<div class="row">
					<div class="form-group col-xs-12">
						<br/>
						<button type="button" class="btn btn-default" id="addtask" data-loading-text="提交中...">提交任务</button>
					</div>
				</div>
			</form>
		</div>
	</div>
	<%@ include file="/WEB-INF/jsp/common/foot.jsp"%>

	<!-- Bootstrap core JavaScript navbar-fixed-bottom
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="static/js/basic.js"></script>
	<script src="static/js/datetimepicke.js"></script>
	<script src="static/js/multiselect.js"></script>
	<script src="static/js/daterangepicker.js"></script>
	<script src="static/js/daterange.by.js"></script>
	<script src="static/js/dateUtils.js"></script>
	<script src="static/js/common.js"></script>
	<script type="text/javascript">
		$(function() {
			navForm = navForm.init({'showPlat':true,'showSid':true,'sidType':'MultiSelect',
				platChangeCallback:updateNavMultiSid
			});
			
			$("#navdaterange").attr("title","最后登录时间");
			
			if($("#ifPay").val()!="true"){
				$("#minPayDiv").hide();
			}
			
			var std = new Date();
			std.addDays(-2);
			var loginDate = std.format('yyyy-MM-dd');
			createDateRgDom({'dom':'#loginDateRg',opens:'right','isShow':true,startDate:loginDate,endDate:loginDate});
			
			std.addDays(1);
			var lostDate = std.format('yyyy-MM-dd');
			createDateRgDom({'dom':'#lostDateRg',opens:'right','isShow':true,startDate:lostDate,endDate:lostDate});
		});
		
		function ifPayChange(){
			console.log($("#ifPay").val());
			if($("#ifPay").val()=="true"){
				$("#minPayDiv").show();
			}else{
				$("#minPayDiv").hide();
			}
		}
		
		$("#addtask").click(
			function() {
				var lstime = DaterangeUtil.getStartDate("#loginDateRg","YYYY-MM-DD");
				var letime = DaterangeUtil.getEndDate("#loginDateRg","YYYY-MM-DD");
				
				var loststime = DaterangeUtil.getStartDate("#lostDateRg","YYYY-MM-DD");
				var lostetime = DaterangeUtil.getEndDate("#lostDateRg","YYYY-MM-DD");
				
				var platId = $("#navPlat").val();
				var bpid = $("#navMultiSid").attr("selectBpidValues");
				var sid = $("#navMultiSid").attr("selectSidValues");
				
				var items = $('input:checkbox[name=items]:checked').map(function(){
					return this.value;
				}).get().join();
				var itemsName = $('input:checkbox[name=items]:checked').map(function(){
					return $(this).attr("valueName");
				}).get().join();
				var tableItems = $('input:checkbox[name=tableItems]:checked').map(function(){
					return this.value;
				}).get().join();
				
				$('#addtask').button("loading");
				$.post("data/login/lostUserQuery.htm", {
					plat : platId,
					sid : sid,
					bpid : bpid,
					lstime : lstime,
					letime : letime,
					loststime : loststime,
					lostetime : lostetime,
					ifPay : $('#ifPay').val(),
					minPay : $("#minPay").val(),
					items : items,
					itemsName : itemsName,
					tableItems : tableItems
				},
				function(data) {
					commonDo.addedTaskSuccess(data);
				},
				"json").error(function(){
					commonDo.addedTaskError();
				});
			});
	</script>
</body>
</html>
