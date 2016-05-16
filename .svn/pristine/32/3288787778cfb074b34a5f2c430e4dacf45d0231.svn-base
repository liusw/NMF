<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en-us">
<head>
<%@ include file="/WEB-INF/jsp/common/meta.jsp"%>
<title>牌局-数据魔方</title>
<style type="text/css">
	.col-xs-3>label{
		display: block;
	}
</style>
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
			<jsp:param name="tabActive" value="gambling" />
		</jsp:include>
		<jsp:include page="/WEB-INF/jsp/common/mainNav2.jsp" />
		
		<div class="main2Body">
			<jsp:include page="/WEB-INF/jsp/common/tabMenu.jsp">
				<jsp:param name="tab" value="gambling" />
				<jsp:param name="subnav" value="gambling" />
			</jsp:include>
			
			<div id="myTabContent" class="tab-content">
				<div class="tab-pane fade content-form main-content active in">
					<form class="form-inline">
						<div class="row">
							<div class="col-xs-2">
								<label>牌局人数：</label>
								<select id="pcount" name="pcount" class="form-control multiselect" multiple="multiple">
									<option value="2">2人</option>
									<option value="3">3人</option>
									<option value="4">4人</option>
									<option value="5">5人</option>
									<option value="6">6人</option>
									<option value="7">7人</option>
									<option value="8">8人</option>
									<option value="9">9人</option>
								</select>
							</div>
							<div class="col-xs-2">
								<label>盲注：</label>
								<select id="blindmin" name="blindmin" class="form-control multiselect" multiple="multiple">
									<option value="1">1注</option>
									<option value="2">2注</option>
									<option value="5">5注</option>
									<option value="10">10注</option>
									<option value="50">50注</option>
									<option value="100">100注</option>
									<option value="200">200注</option>
									<option value="500">500注</option>
									<option value="1000">1000注</option>
									<option value="2000">2000注</option>
									<option value="2500">2500注</option>
									<option value="3000">3000注</option>
									<option value="3500">3500注</option>
									<option value="4000">4000注</option>
									<option value="4500">4500注</option>
									<option value="5000">5000注</option>
									<option value="6000">6000注</option>
									<option value="7000">7000注</option>
									<option value="8000">8000注</option>
									<option value="10000">10000注</option>
									<option value="15000">15000注</option>
									<option value="20000">20000注</option>
									<option value="25000">25000注</option>
									<option value="50000">50000注</option>
									<option value="100000">100000注</option>
									<option value="120000">120000注</option>
									<option value="150000">150000注</option>
									<option value="200000">200000注</option>
									<option value="250000">250000注</option>
									<option value="300000">300000注</option>
									<option value="500000">500000注</option>
									<option value="1000000">1000000注</option>
									<option value="1500000">1500000注</option>
								</select>
							</div>
							<div class="col-xs-2">
								<label>桌子类型：</label>
								<select id="gtype" name="gtype" class="form-control multiselect" multiple="multiple">
									<option value="0,1,2,3,6">普通场</option>
									<option value="38">好牌场</option>
									<option value="4,7,8,9,10,11,14,20,30">比赛场</option>
									<option value="0">新手场</option>
									<option value="1">初级场</option>
									<option value="2">中级场</option>
									<option value="3">高级场</option>
									<option value="4">淘汰场</option>
									<option value="6">快速场</option>
									<option value="7">晋级赛</option>
									<option value="8">淘金日赛</option>
									<option value="9">淘金周赛</option>
									<option value="11">锦标赛</option>
									<option value="14">模拟场</option>
									<option value="20">荷官场</option>
									<option value="30">当面玩</option>
								</select>
							</div>
							<div class="col-xs-2">
								<label>统计项：</label>
								<select id="dataType" name="dataType" class="form-control multiselect" multiple="multiple">
									<option value="1">牌局数</option>
									<option value="2">输钱</option>
									<option value="3">赢钱</option>
									<option value="4">台费</option>
								</select>
							</div>
						</div>
						<br>
						<div id="range">
							<label>盲注区间：</label>
							<button type="button" class="btn btn-default" id="query"  onclick="addCondition()">新增盲注区间</button>
						
						</div>
					
						<br>
						<div class="row">
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
	<script src="static/js/jquery.form.js"></script>
	<script src="static/js/dataTable.js"></script>
	<script src="static/js/common.js"></script>
	<script type="text/javascript">
	
	$(function(){
		navForm.init({'showPlat':true,'showSid':true,'sidType':'MultiSelect','showDate':{'isShow':true},
			platChangeCallback: updateNavMultiSid
		});
    	
		$('#pcount').mymultiselect({
		});
		$('#blindmin').mymultiselect({
		});
				
		$('#gtype').mymultiselect({
	      'multiValues':'',
			'multiTitles':'',
			selectClose:function (val) {
				$('#gtype').attr({'selectValues':val.getValues(),'selectTitles':val.getTitles()});
	        }
		});
		
		$('#dataType').mymultiselect({
	      'multiValues':'',
			'multiTitles':'',
			selectClose:function (val) {
				$('#dataType').attr({'selectValues':val.getValues(),'selectTitles':val.getTitles()});
	        }
		});
	});
	
	$("#addtask").click(
		function() {
			var paras = $("form").serialize();
			
			items = $('input:checkbox[name=items]:checked').map(function(){
				return this.value;
			}).get().join();
			var itemsName = $('input:checkbox[name=items]:checked').map(function(){
				return $(this).attr("valueName");
			}).get().join();
			$('#queryWarning').show();
			var rangeNum = $(".rangeNum").length;
			var params = {
					plat : $("#navPlat").val(),
					sid : $("#navMultiSid").attr("selectSidValues"),
					bpid : $("#navMultiSid").attr("selectbpidvalues"),
					rstime : navForm.daterange.getStartDate("#navdaterange"),
					retime : navForm.daterange.getEndDate("#navdaterange"),
					blindmin : $("#blindmin").val(),
					gtype :$("#gtype").attr("selectValues"),
					dataType :$("#dataType").attr("selectValues"),
					pcount : ($("#pcount").val()?$("#pcount").val().join():""),
					items : items,
					itemsName : itemsName,
					rangeNum : rangeNum
			}
			for(var i=0;i<rangeNum;i++){
				var rsproX = "rsrange"+(i+1);
				var reproX = "rerange"+(i+1);
				var proX = "range"+(i+1);
				params[proX]=$("#"+rsproX).val()+"-"+$("#"+reproX).val();
			}
			
			$.post("data/gambling/cgcoins.htm", params,
			function(data) {
				commonDo.addedTaskSuccess(data);
			},
			"json").error(function(){
				commonDo.addedTaskError();
			});
		}
		);
	
	function addCondition(){
		var num = $(".rangeNum").length+1;
		$("#range").append("<div  class='row rangeNum'>"
			+"<div class=' col-xs-3'>"
				+"<input type='text' id='rsrange"+num+"' class='form-control' placeholder='最小'  autocomplete='off'>"
			+"</div>"
			+"<div class='col-xs-3'>"
				+"<input type='text' id='rerange"+num+"' class='form-control' placeholder='最大' autocomplete='off'>"
			+"</div>"
	   		+"</div><br>");
	}
</script>
</body>
</html>