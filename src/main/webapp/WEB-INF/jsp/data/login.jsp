<%@page import="org.apache.zookeeper.Login"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en-us">
<head>
<%@ include file="/WEB-INF/jsp/common/meta.jsp"%>
<title>登录-数据魔方</title>
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
            		<li class="active"><a href="<%=(request.getContextPath())%>/log/login.jsp">连续登录</a></li>
            		<li class=""><a href="<%=(request.getContextPath())%>/data/doubleEndLogin.htm">双端登录</a></li>
            		<li class=""><a href="<%=(request.getContextPath())%>/data/lostUser.htm">流失</a></li>
            		<li><a href="<%=(request.getContextPath())%>/data/loginx.htm">多端登录</a></li>
          		</ul>
		    </div>
		
			<form role="form">
				<div class="row">
					<div class="form-group col-xs-3">
						<label>连续登录次数：</label>
						<input type="text" id="loginTimes" class="form-control" placeholder="连续登录次数">
					</div>
				</div>
				<br/>
				<div class="row">
					<div class="form-group col-xs-12 ucuser">
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
	<script src="static/js/datetimepicke.js"></script>
	<script src="static/js/multiselect.js"></script>
	<script src="static/js/daterangepicker.js"></script>
	<script src="static/js/daterange.by.js"></script>
	<script src="static/js/common.js"></script>
	<script type="text/javascript">
		$(function() {
			navForm = navForm.init({'showPlat':true,'showSid':true,'sidType':'MultiSelect','showDate':{'isShow':true},
				platChangeCallback:updateNavMultiSid
			});
		});
		
		$("#addtask").click(
			function() {
				var lstime = navForm.daterange.getStartDate("#navdaterange");
				var letime = navForm.daterange.getEndDate("#navdaterange");
				
				var platId = $("#navPlat").val();
				var bpid = $("#navMultiSid").attr("selectBpidValues");
				var sid = $("#navMultiSid").attr("selectSidValues");
				var num = $("#loginTimes").val();
				if(!num || isNaN(num)){
					showMsg("请填写连续登录天数!");
					return false;
				}
				
				var items = $('input:checkbox[name=items]:checked').map(function(){
					return this.value;
				}).get().join();
				var itemsName = $('input:checkbox[name=items]:checked').map(function(){
					return $(this).attr("valueName");
				}).get().join();
				
				var leadTm = "t1"; 
				if(num>1){
					for(var i=0;i<num-1;i++){
						leadTm += ",lead(t1," + (i+1) + ") over (partition by `_uid` order by t1) t" + (i+2);
					}
				}
				
				var diffTm = "";
				if(num>1){
					for(var i=0;i<num-1;i++){
						diffTm += " and datediff(t" + (i+2) + ",t1)=" + (i+1);
					}
				}
				
				var hsql = "select #plat#,`_uid`,t1 from (" +
					"select  `_uid`," + leadTm + " from" +
					"(select `_uid`,from_unixtime(cast(`_tm` as bigint), 'yyyy-MM-dd') t1 from user_login where plat=#plat# and tm between #lstime# and #letime# and `_bpid` in(#bpid#) group by `_uid`,from_unixtime(cast(`_tm` as bigint), 'yyyy-MM-dd'))a )b" + 
					" where 1=1 ";
				
				if(diffTm){
					hsql += diffTm;
				}
				
				hsql += " group by `_uid`,t1";
				
				if(!sid || sid == "undefined"){
					hsql = hsql.replace(/and sid in\(#sid#\)/g,"");
				}
				if(!bpid || bpid == "undefined"){
					hsql = hsql.replace(/and `_bpid` in\(#bpid#\)/g,"");
				}
				if(sid || bpid){
					hsql = hsql.replace(/and `_plat`=#plat#/g,"");
				}
				
				hsql = hsql.replace(/#plat#/g, platId).replace(/#bpid#/g, bpid).replace(/#sid#/g, sid).replace(/#num#/g, num).replace(/#lstime#/g, date2Num(lstime)).replace(/#letime#/g, date2Num(letime));
				
				var hiveTitle = '平台id,用户id,时间';
				var hiveColumnName = 'plat,_uid,tm';
				
				
				var hsql1 = "select plat,a.`_uid`,a.tm,b.app_ver,c.coins,loginnum from" + 
					"(select plat,`_uid`,tm from #tmpTable#)a left join" +
					"(select app_ver,`_uid` from" +
					"(select app_ver,`_uid`,rank() over(partition by `_uid` order by `_tm` desc) as rank from mb_device_login_log where `_plat`=#plat# and `_bpid` in(#bpid#))t0 where t0.rank=1)b on(a.`_uid`=b.`_uid`) left join" +
					"(select `_uid`,sum(act_num) as coins from gamecoins_stream where plat=#plat# and tm between #lstime# and #letime# and sid in(#sid#) and act_id=285 group by `_uid`)c on(a.`_uid`=c.`_uid`) left join" +
					"(select `_uid`,count(1) loginnum from user_login where plat=#plat# and tm between #lstime# and #letime# and `_bpid` in(#bpid#) group by `_uid`)d on(d.`_uid`=a.`_uid`)";
				
				if(!sid || sid == "undefined"){
					hsql1 = hsql1.replace(/and sid in\(#sid#\)/g,"");
				}
				if(!bpid || bpid == "undefined"){
					hsql1 = hsql1.replace(/and `_bpid` in\(#bpid#\)/g,"");
				}
				if(sid || bpid){
					hsql1 = hsql1.replace(/and `_plat`=#plat#/g,"");
				}
				
				hsql1 = hsql1.replace(/#plat#/g, platId).replace(/#bpid#/g, bpid).replace(/#sid#/g, sid).replace(/#num#/g, num).replace(/#lstime#/g, date2Num(lstime)).replace(/#letime#/g, date2Num(letime));
				
				var hiveTitle1 = '平台id,用户id,时间,版本号,领取连续登录奖励数值,登录次数';
				var hiveColumnName1 = '_plat,_uid,tm,app_ver,coins,loginnum';
				
				var taskJson = {
						taskName : platId + "用户连续"+ num +"天登录数据",
						'process':[												
									{
										'tmpId' : 'p0',								
										'type' : '1',
										'operation' : hsql,
										'columnName' : hiveColumnName,
										'title' : hiveTitle
									},
									{
										'tmpId' : 'p1',								
										'type' : '1',
										'dependOn':'p0',	
										'preTempTable':'1',		
										'operation' : hsql1,
										'columnName' : hiveColumnName1,
										'title' : hiveTitle1
									}
								]
				};
				
				if(items){
					var hbaseOperate = {
							"_tnm":"user_ucuser",
							"retkey": items,
							"rowkey":false,
							"file_column": hiveColumnName1,  
							"output_column": hiveColumnName1 + ',' + items, 
							"delExists":true,
							"retFilter":true,
							"ipformat":"loginip",
							"format":{"mtime":"yyyy-MM-dd","mactivetime":"yyyy-MM-dd","startTime":"yyyy-MM-dd","lastTime":"yyyy-MM-dd","thisTime":"yyyy-MM-dd"},
							"threadCount":"3"
					};
					var hbase = {
							'tmpId':'p2',								
							'dependOn':'p1',				
							'type':'2',
							'operation':hbaseOperate,
							'columnName':hiveColumnName1 + ',' + items,
							'title':hiveTitle1 + ',' + itemsName
					};
					taskJson.process.push(hbase);
				}
				
				$('#addtask').button("loading");
				$.post("task/process/addByJson.htm", {
					taskJson : JSON.stringify(taskJson)
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
