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
            		<li><a href="<%=(request.getContextPath())%>/data/login.htm">连续登录</a></li>
            		<li class="active"><a href="<%=(request.getContextPath())%>/data/doubleEndLogin.htm">双端登录</a></li>
            		<li><a href="<%=(request.getContextPath())%>/data/lostUser.htm">流失</a></li>
            		<li><a href="<%=(request.getContextPath())%>/data/loginx.htm">多端登录</a></li>
          		</ul>
		    </div>
		    
		    <div class="panel panel-primary tableArticle" style="margin-top: 10px;">
				  <div class="panel-body">
				  		选择指定站点的登录用户,这些用户中同时登录了PC和移动的(<span style="color: red;">并不是同时登录了你所选择的平台</span>)
				  </div>
				</div>
		    
			<form role="form">
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
				
				var items = $('input:checkbox[name=items]:checked').map(function(){
					return this.value;
				}).get().join();
				var itemsName = $('input:checkbox[name=items]:checked').map(function(){
					return $(this).attr("valueName");
				}).get().join();
				
				var hsql = "select `_uid`,ismobile,sid from" +
				"(select `_uid`,`_bpid` from user_login where plat=#plat# and tm between #lstime# and #letime# and `_bpid` in(#bpid#) group by `_uid`,`_bpid`)t0 left join" +
				"(select ismobile,`_bpid`,sid from game_meta where `_plat`=#plat#)t1 on(t0.`_bpid`=t1.`_bpid`) where ismobile is not null group by `_uid`,ismobile,sid";
				
				if(!sid || sid == "undefined"){
					hsql = hsql.replace(/and sid in\(#sid#\)/g,"");
				}
				if(!bpid || bpid == "undefined"){
					hsql = hsql.replace(/and `_bpid` in\(#bpid#\)/g,"");
				}
				if(sid || bpid){
					hsql = hsql.replace(/and `_plat`=#plat#/g,"");
				}
				
				hsql = hsql.replace(/#plat#/g, platId).replace(/#bpid#/g, bpid).replace(/#sid#/g, sid).replace(/#lstime#/g, date2Num(lstime)).replace(/#letime#/g, date2Num(letime));
				
				var hiveTitle = '_uid,ismobile,sid';
				var hiveColumnName = '_uid,ismobile,sid';
				
				
				var hsql1 = "select #plat#,t0.sid,t0.`_uid`,t2.sid from" +
				"(select `_uid`,ismobile,sid from #tmpTable#)t0 left join" +
				"(select `_uid`,`_bpid` from user_login where plat=#plat# and tm between #lstime# and #letime# and `_bpid` not in(#bpid#) group by `_uid`,`_bpid`)t1 on(t0.`_uid`=t1.`_uid`) left join" +
				"(select ismobile,`_bpid`,sid from game_meta where `_plat`=#plat#)t2 on(t2.`_bpid`=t1.`_bpid`) where t0.ismobile!=t2.ismobile and t1.`_uid` is not null";
				
				if(!sid || sid == "undefined"){
					hsql1 = hsql1.replace(/and sid in\(#sid#\)/g,"");
				}
				if(!bpid || bpid == "undefined"){
					hsql1 = hsql1.replace(/and `_bpid` not in\(#bpid#\)/g,"");
				}
				if(sid || bpid){
					hsql1 = hsql1.replace(/and `_plat`=#plat#/g,"");
				}
				
				hsql1 = hsql1.replace(/#plat#/g, platId).replace(/#bpid#/g, bpid).replace(/#sid#/g, sid).replace(/#lstime#/g, date2Num(lstime)).replace(/#letime#/g, date2Num(letime));
				
				var hiveTitle1 = '平台id,sid,用户id,另一端sid';
				var hiveColumnName1 = '_plat,sid,_uid,otherSid';
				
				var taskJson = {
						taskName : platId + "_" + sid + "_双端登录用户",
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
