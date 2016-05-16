<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en-us">
<head>
<%@ include file="/WEB-INF/jsp/common/meta.jsp"%>
<title>禁封-数据魔方</title>
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
			<jsp:param name="tabActive" value="ban" />
		</jsp:include>
		<jsp:include page="/WEB-INF/jsp/common/mainNav2.jsp" />
		
		<div class="main2Body">
	    	<div id="sub-nav">
          		<ul id="nav-tab" class="nav nav-tabs">
            		<li class="active"><a href="#query-form">查询</a></li>
            		<li class=""><a href="#ban-stats">统计</a></li>
          		</ul>
		    </div>
			<div id="myTabContent" class="tab-content">
				<form id="query-form" class="form-inline tab-pane fade in active" style="margin-top: 10px;">
					<div class="form-group">
						<div class="col-md-11">
							<div class="row">
								<div class="alert alert-success" style="width: 298px;" role="alert">点击上层导航条右方组件选择站点和日期。</div>
							</div>
							<div class="row">
								<button type="button" class="btn btn-default" id="addtask" data-loading-text="提交中...">提交任务</button>
							</div>
						</div>
					</div>
				</form>
				
				<div id="ban-stats" class="tab-pane fade content-form">
					<table id="example" class="table table-striped table-bordered display"  cellspacing="0" width="100%">
						<thead>
							<tr>
								<th>时间</th><th>平台</th><th>被封用户数</th><th>扣除游戏币总数</th><th>清0用户数</th><th>清0游戏币</th><th>查封用户数日环比增长</th><th>查封游戏币日环比增长</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="/WEB-INF/jsp/common/foot.jsp"%>

	<!-- Bootstrap core JavaScript navbar-fixed-bottom
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="static/js/basic.js"></script>
	<script src="static/js/multiselect.js"></script>
	<script src="static/js/daterangepicker.js"></script>
	<script src="static/js/daterange.by.js"></script>
	<script src="static/js/dataTable.js"></script>
	<script src="static/js/dateUtils.js"></script>
	<script src="static/js/common.js"></script>
	<script type="text/javascript">
		$(function() {
			$("#nav-tab a").click(function(e){
		        e.preventDefault();
		        $(this).tab("show");
		        
		        var showTabId = $(this).attr("href");
		        if("#query-form" == showTabId){
		        	navForm.init({'showPlat':true,'showSid':true,'sidType':'MultiSelect','showDate':{'isShow':true,'singleDatePicker':false},
						platChangeCallback: updateNavMultiSid
					});
		        	
		        }if("#ban-stats" == showTabId){
		        	navForm.init({'showPlat':false,'showSid':false,'sidType':'MultiSelect',
		        		'showDate':{'isShow':true,'singleDatePicker':true,applyClickCallBack:banCountQuery},
						platChangeCallback:updateNavMultiSid
					});
		        	
		        	banCountQuery();
		        }
		        
		    });
			
			$("#bankMenu").addClass("active");
			
			$("#nav-tab a:eq(0)").trigger("click");
		});
		
		$("#addtask").click(
			function() {
				var sid = $("#navMultiSid").attr("selectSidValues");
				if(!sid){
					alert("请选择站点");
					return;
				}
				
				$('#queryWarning').show();

				$.getJSON("data/banQuery/getDatas.htm", {
					plat : $("#navPlat").val(),
					sid : sid,
					bpid : $("#navMultiSid").attr("selectBpidValues"),
					startTime : navForm.daterange.getStartDate("#navdaterange"),
					endTime : navForm.daterange.getEndDate("#navdaterange"),
				},
				function(data) {
					commonDo.addedTaskSuccess(data);
				},
				"json").error(function(){
					commonDo.addedTaskError();
				});
			});
		
		function banCountQuery(){
			
			/*var sTime = navForm.daterange.getStartDate("#navdate","YYYYMMDD");
			var params ={
					id:"10000001|10000002","dataType":"dataTable",
					args:{tm:sTime,table:"d_user_logs"}
				};*/
			
			var sTime = navForm.date.getStartDate("#navdate");
			loadTable("data/banCount/getDatas.htm?tm="+sTime);
		};
		
		function loadTable(url){
			$("#example").DataTable({
		     "bFilter": true,
		     "pagingType" : "full_numbers",
		     "paging": true,
		     "bDestroy" : true,
		     "bProcessing" : false,
		     "pageLength": 50,
		     "sAjaxSource" :url,
		     "bServerSide" : true,
		     "ordering": false,
		     "searching":false,
		     "columns": [
		                 { "data": "tm" },
		                 { "data": "plat" },
		                 { "data": "banCount"},
		                 { "data": "deductCoins" },
		                 { "data": "clearZeroCount" },
		                 { "data": "clearZeroCoins" },
		                 { "data": "yesterdayBanCountRate" },
		                 { "data": "yesterdayBanCoinsRate" }
		      ],
		     "oLanguage": commonData.oLanguage,
			"dom": ''
			});
		}
	</script>
</body>
</html>
