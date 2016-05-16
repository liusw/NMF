<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en-us">
<head>
<%@ include file="/WEB-INF/jsp/common/meta.jsp"%>
<title>金币流水-数据魔方</title>
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
			<jsp:param name="tabActive" value="coinsFlow" />
		</jsp:include>
		<jsp:include page="/WEB-INF/jsp/common/mainNav2.jsp" />
		
		<div class="main2Body" style="min-height: 400px;">
	    	<div id="sub-nav">
          		<ul id="nav-tab" class="nav nav-tabs">
            		<li class="active dropdown"><a class="dropdown-toggle" data-toggle="dropdown" href="javascript:void(0);">查询<b class="caret"></b></a>
            			<ul id="query-by" class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu">
		                  <li class="active"><a href="javascript:void(0);" tag="by-user">按用户</a></li>
		                  <li><a href="javascript:void(0);" tag="by-day">按天</a></li>
		                  <li><a href="javascript:void(0);" tag="by-day-user">按天和用户</a></li>
		                </ul>
            		</li>
            		<li class=""><a href="<%=(request.getContextPath())%>/data/cgCoinsTop.htm">金币排行</a></li>
            		<li class=""><a href="<%=(request.getContextPath())%>/data/vmoneyTop.htm">台费排行</a></li>
            		<li><a href="<%=(request.getContextPath())%>/data/actCoins.htm">各渠道金流汇总</a></li>
          		</ul>
		    </div>
		    
			<form class="form" style="margin-top: 10px;">
				<div id="gc-user-info" class="row">
					<div class="form-group col-xs-12">
						<%@include file="/WEB-INF/jsp/common/ucuser.jsp"%>
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
	<script src="static/js/daterangepicker.js"></script>
	<script src="static/js/daterange.by.js"></script>
	<script src="static/js/multiselect.js"></script>
	<script src="static/js/common.js"></script>
	<script type="text/javascript">
		var navForm;
		$(function() {
			navForm = navForm.init({'showPlat':true,'showSid':true,'sidType':'MultiSelect','showDate':{'isShow':true},
				platChangeCallback:updateNavMultiSid
			});
						
			$("#query-by a").click(function(e){
		        e.preventDefault();
		        var jthis = $(e.currentTarget);
		        
		        var tag = jthis.attr("tag");
	        	$("#gc-user-info").css("display",tag=="by-day"?"none":"block");
	        	
		        $.each($(jthis).parent().siblings(),function(index,value){
		        	$(value).removeClass("active");
		        });
		        
		        $(jthis.parent()).addClass("active");
		    });
			
			var byType = getUrlParam("type");
			if(byType && "" != byType){
				if("by-day"){
					$("#query-by a[tag='" + byType + "']").trigger("click");
				}
			}
		});
		
		$("#addtask").click(
				function() {
					var start = navForm.daterange.getStartDate("#navdaterange");
					var end = navForm.daterange.getEndDate("#navdaterange");
					
					items = $('input:checkbox[name=items]:checked').map(function(){
						return this.value;
					}).get().join();
					var itemsName = $('input:checkbox[name=items]:checked').map(function(){
						return $(this).attr("valueName");
					}).get().join();
					$('#queryWarning').show();
					
					$.post("data/gamecoins/coinsFlow.htm", {
						plat : $("#navPlat").val(),
						sid : $("#navMultiSid").attr("selectSidValues"),
						bpid : $("#navMultiSid").attr("selectBpidValues"),
						rstime : start,
						retime : end,
						items : items,
						itemsName : itemsName,
						groupBy: $("#query-by li.active a").attr("tag")
					},
					function(data) {
						commonDo.addedTaskSuccess(data);
					},
					"json").error(function(){
						commonDo.addedTaskError();
					});
				}
				);
	</script>
</body>
</html>
