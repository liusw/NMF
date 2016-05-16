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
            		<li><a href="<%=(request.getContextPath())%>/data/doubleEndLogin.htm">双端登录</a></li>
            		<li><a href="<%=(request.getContextPath())%>/data/lostUser.htm">流失</a></li>
            		<li class="active"><a href="<%=(request.getContextPath())%>/data/loginx.jsp">多端登录</a></li>
          		</ul>
		    </div>
		    
		    <div class="panel panel-primary tableArticle" style="margin-top: 10px;">
				  <div class="panel-body">
				  		功能点:登录了指定站点,同时登录(不登录)其他站点的用户ID
				  </div>
				</div>
		    
			<form class="form-inline">
			
				<div class="form-group">
					<label class="pdl">登录站点:</label>
					<select class="multiselect" multiple="multiple" id="loginSid"></select>
					&nbsp;&nbsp;&nbsp;
				</div>
				<div class="form-group">
					<select class="form-control" id="andCondition">
						<option value="0">并且登录</option>
						<option value="1">并且不登录</option>
					</select>
					&nbsp;&nbsp;&nbsp;
				</div>
				<div class="form-group">
					<label class="pdl">站点:</label>
					<select class="multiselect" multiple="multiple" id="otherLoginSid"></select>
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
			navForm = navForm.init({'showPlat':true,'showDate':{'isShow':true},
				platChangeCallback:updateMultiSid
			});
			getMultiSid("#navPlat",$("#loginSid"),false,true);
			getMultiSid("#navPlat",$("#otherLoginSid"),false,true);
		});
		
		function updateMultiSid(){
			getMultiSid("#navPlat",$("#loginSid"),true,true);
			getMultiSid("#navPlat",$("#otherLoginSid"),true,true);
		}
		
		$("#addtask").click(function() {
			var plat = $("#navPlat").val();
			var lstime = navForm.daterange.getStartDate("#navdaterange","YYYYMMDD");
			var letime = navForm.daterange.getEndDate("#navdaterange","YYYYMMDD");
			
			var loginSid = $("#loginSid").attr("selectBpidValues");
			var otherLoginSid = $("#otherLoginSid").attr("selectBpidValues");
			
			if(isEmpty(loginSid) || isEmpty(otherLoginSid)){
				alert('请选择站点');
				return;
			}
			
			var sql = "select a.`_uid` from "
				+" (select `_uid` from user_login where plat="+plat+" and tm between " + lstime + " and " + letime + " and `_bpid` in ("+loginSid+")  group by `_uid`) a "
				+" left outer join "
				+" (select `_uid` from user_login where plat="+plat+" and tm between " + lstime + " and " + letime + " and `_bpid` in ("+otherLoginSid+")  group by `_uid`) b "
				+" on (a.`_uid`=b.`_uid`) where b.`_uid` is "+($("#andCondition").val()==0?"not null":"null");
			
			var taskJson = {
				taskName : "多端登录用户",
				'process':[{
					'tmpId' : 'p0',								
					'type' : '1',
					'operation' : sql,
					'columnName' : '_uid',
					'title' : '用户ID'
				}]
			};
			
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
