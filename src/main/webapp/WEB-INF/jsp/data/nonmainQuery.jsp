<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
		<form class="form" style="padding: 0px;">
			<div class="mf-panel">
				<div class="row">
				<!--<label>平台站点：(该功能按此模板设计：<a target="_blank" href="http://list.oa.com/task.php?tkid=163968">http://list.oa.com/task.php?tkid=163968</a>)</label>-->
				<div class="col-xs-6">
					<div class="row">
						<div class="form-group col-xs-6">
							<label>平台站点：</label>
							<select id="plats" name="plat" class="form-control"></select>
						</div>
						<div class="form-group col-xs-6">
							<label>&nbsp;</label>
							<select id="sid" name="sid" class="form-control multiselect" multiple="multiple"></select>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
					<div class="col-xs-6">
					<div class="row">
						<div class="form-group col-xs-6">
							<label>台费区间：</label>
							<input type="text" class="form-control" id="svmoney" placeholder="最小台费(0)">
						</div>
						<div class="form-group col-xs-6">
							<label>&nbsp;</label>
							<input type="text" class="form-control" id="evmoney" placeholder="最大台费(2147483647)">
						</div>
					</div>
				</div>
				</div>
			</div>
			<div class="mf-panel">
				<div class="row">
					<div class="col-xs-6">
						<div class="row">
							<div class="form-group col-xs-6">
								<label>登录时间：</label>
								<div id="lgdaterange" class="date-input">
									<i class="glyphicon glyphicon-calendar fa fa-calendar"></i>
									<span></span> <b class="caret"></b>
								</div>
							</div>
							<div class="form-group col-xs-6">
								<label>注册时间：</label>
								<div id="rgdaterange" class="date-input">
									<i class="glyphicon glyphicon-calendar fa fa-calendar"></i>
									<span></span> <b class="caret"></b>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="form-group col-xs-6">
						<label>登录时间和注册时间的关系：</label>
						<br/>
						<label class="checkbox-inline">
							<input type="radio" name="timeRelation" value='and' checked>
							<span data-toggle="tooltip" data-placement="top" title="获取的数据要同时满足注册时间和登录时间两个条件的限制">与关系</span>
						</label>
						<label class="checkbox-inline">
							<input type="radio" name="timeRelation" value='or'>
							<span data-toggle="tooltip" data-placement="top" title="获取的数据只要满足注册时间和登录时间其中一个条件的限制">或关系</span>
						</label>
					</div>
				</div>
			</div>
			<div class="mf-panel">
				<div class="row">
					<div class="form-group col-xs-6">
						<label>登录IP不属于游戏主要区域：</label>
						<br/>
						<label class="checkbox-inline">
							<input type="checkbox" name="zone" value='"Taiwan"'>台湾
						</label>
						<label class="checkbox-inline">
							<input type="checkbox" name="zone" value='"Hong Kong"'>香港
						</label>
						<label class="checkbox-inline">
							<input type="checkbox" name="zone" value='"Malaysia"'>马来西亚
						</label>
						<label class="checkbox-inline">
							<input type="checkbox" name="zone" value='"Singapore"'>新加坡
						</label>
						<label class="checkbox-inline">
							<input type="checkbox" name="zone" value='"United States"'>美国
						</label>
						<label class="checkbox-inline">
							<input type="checkbox" name="zone" value='"China"'>中国
						</label>
					</div>
				</div>
			</div>
			<div class="mf-panel">
				<div class="row">
					<div class="form-group col-xs-3">
						<label>金币明细分类：</label>
						<div id="gmdaterange" class="date-input">
							<i class="glyphicon glyphicon-calendar fa fa-calendar"></i>
							<span></span> <b class="caret"></b>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="form-group col-xs-12">
						<label class="checkbox-inline">
							<input type="checkbox" name="gamecoins" value="2">四叶草领取次数
						</label>
						<label class="checkbox-inline">
							<input type="checkbox" name="gamecoins" value="32">幸运筹码领取次数
						</label>
						<label class="checkbox-inline">
							<input type="checkbox" name="gamecoins" value="33">粉丝奖励
						</label>
						<label class="checkbox-inline">
							<input type="checkbox" name="gamecoins" value="558">排行榜赠送
						</label>
						<label class="checkbox-inline">
							<input type="checkbox" name="gamecoins" value="89">帮好友开彩蛋领取次数
						</label>
						<label class="checkbox-inline">
							<input type="checkbox" name="gamecoins" value="248">移动每日任务(获得)
						</label>
						<label class="checkbox-inline">
							<input type="checkbox" name="gamecoins" value="374">移动好友赠送筹码(获得)
						</label>
					</div>
				</div>
			</div>
			<div class="mf-panel">
				<div class="row">
					<div class="form-group col-xs-12">
						<%@include file="/WEB-INF/jsp/common/ucuser.jsp"%>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="form-group col-xs-12">
					<br/>
					<button type="button" class="btn btn-default" id="addtask" data-loading-text="提交中...">提交任务</button>
				</div>
			</div>
		</form>

	<!-- Bootstrap core JavaScript navbar-fixed-bottom
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
<script type='text/javascript' src='static/js/jquery-1.9.1.js'></script>
<script type='text/javascript' src='static/jquery-ui/jquery-ui.js'></script>
<script type='text/javascript' src='static/bootstrap/js/bootstrap.min.js'></script>
<script type='text/javascript' src='static/datetimepicker/js/bootstrap-datetimepicker.min.js'></script>
<script type='text/javascript' src='static/datetimepicker/js/bootstrap-datetimepicker.zh-CN.js'></script>
<script type='text/javascript' src='static/daterangepicker/moment.min.js'></script>
<script type='text/javascript' src='static/daterangepicker/daterangepicker.js'></script>
<script type='text/javascript' src='static/jquery-ui-multiselect/src/jquery.multiselect.js'></script>
<script type='text/javascript' src='static/jquery-ui-multiselect/src/jquery.multiselect.extend.js'></script>
<script type='text/javascript' src='static/jquery-ui-multiselect/src/jquery.multiselect.filter.js'></script>
<script type='text/javascript' src='static/jquery-ui-multiselect/i18n/jquery.multiselect.zh-cn.js'></script>
	<script type="text/javascript" src="static/js/daterange.by.js"></script>
	
	<script type="text/javascript" src="static/js/common.js"></script>
	<script type="text/javascript">
	/**
		var items = $('input:checkbox[name=items]:checked').map(function(){
			return this.value;
		}).get();
	**/
		$(function() {
			getMultiPlat("#plats", "#sid");
			$("#plats").change(function() {
				getMultiSid("#plats","#sid",true);
			});
			
			createDateRgDom({'dom':'#rgdaterange','isShow':true});
			createDateRgDom({'dom':'#lgdaterange','isShow':true});
			createDateRgDom({'dom':'#gmdaterange','isShow':true});
		});
	
		$("#addtask").click(
			function() {
				/* var rstime = $('#rstime').val();
				var retime = $('#retime').val();
				var lstime = $('#lstime').val();
				var letime = $('#letime').val();
				
				if(!rstime && !retime && !lstime && !letime){
					showMsg("请选择用户的登录时间或注册时间!");
					return false;
				}else if(!rstime && retime){
					showMsg("请填写用户注册开始时间！");
					return false;
				}else if(rstime && !retime){
					showMsg("请填写用户注册结束时间！");
					return false;
				}else if(!lstime && letime){
					showMsg("请填写用户登录开始时间!");
					return false;
				}else if(lstime && !letime){
					showMsg("请填写用户登录结束时间!");
					return false;
				} */
				
				zone = $('input:checkbox[name=zone]:checked').map(function(){
					return this.value;
				}).get().join();
				items = $('input:checkbox[name=items]:checked').map(function(){
					return this.value;
				}).get().join();
				var itemsName = $('input:checkbox[name=items]:checked').map(function(){
					return $(this).attr("valueName");
				}).get().join();
				gamecoins = $('input:checkbox[name=gamecoins]:checked').map(function(){
					return this.value;
				}).get().join();
				
				$('#queryWarning').show();
				
				$.post("data/user/nonmainQueryData.htm", {
					plat : $("#plats").val(),
					sid : $("#sid").attr("selectSidValues"),
					bpid : $("#sid").attr("selectBpidValues"),
					rstime : DaterangeUtil.getStartDate("#rgdaterange"),
					retime : DaterangeUtil.getEndDate("#rgdaterange"),
					lstime : DaterangeUtil.getStartDate("#lgdaterange"),
					letime : DaterangeUtil.getEndDate("#lgdaterange"),
					zone : zone,
					svmoney : $("#svmoney").val(),
					evmoney : $("#evmoney").val(),
					items : items,
					itemsName : itemsName,
					gstime : DaterangeUtil.getStartDate("#gmdaterange"),
					getime : DaterangeUtil.getEndDate("#gmdaterange"),
					gamecoins : gamecoins,
					rRelation : $("input[name='timeRelation']:checked").val()
				},
				function(data) {
					commonDo.addedTaskSuccess(data);
				},
				"json").error(function(){
					commonDo.addedTaskError();
				});
			});
	</script>
