<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en-us">
<head>
<%@ include file="/WEB-INF/jsp/common/meta.jsp"%>
<title>通用导出-数据魔方</title>
<link rel="stylesheet" type="text/css" href="static/daterangepicker/daterangepicker-bs3.css">
<link href="static/bootstrap-switch/dist/css/bootstrap3/bootstrap-switch.min.css" rel="stylesheet">
<style type="text/css">
#hiveColumns .checkbox-inline + .checkbox-inline,#tableParam .checkbox-inline + .checkbox-inline{
    margin-left: 0;
}
#hiveColumns .checkbox-inline,#tableParam .checkbox-inline{
    width: 200px;
    height:20px;
    line-height:20px;
    overflow: hidden;
}
.maxWidth1{
	max-width: 200px;
	min-width: 150px;
}
.param {
    margin: 5px 0 5px 52px;
}
.input-group-btn:last-child > .btn, .input-group-btn:last-child > .btn-group {
    height: 34px;
    margin-left: -1px;
}
.task {
    border:none;
    padding-bottom:0px;
}
.glyphicon-question-sign{
	color: #ff6600;
}
.content-body form {
	padding: 0px;
}
.pdl{
	padding-left: 16px;
}
</style>
<script type="text/javascript">
function autoHeight(id){
    var iframe = document.getElementById(id);//这里括号内的"runtime"其实就是上面的ID，要改成自己上面填写的ID。
    if(iframe.Document){//ie自有属性
        //iframe.style.height = iframe.Document.documentElement.scrollHeight+20;
    		iframe.style.height="auto";
    }else if(iframe.contentDocument){//ie,firefox,chrome,opera,safari
        //iframe.height = iframe.contentDocument.body.offsetHeight+20;
        iframe.height="auto";
    }
}
</script>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/top.jsp">
        <jsp:param name="nav" value="data"/>
	</jsp:include>
	
	<jsp:include page="/WEB-INF/jsp/common/dataMenu.jsp">
		<jsp:param name="nav" value="customQuery" />
		<jsp:param name="subnav" value="commonExport" />
	</jsp:include>
	
	<div class="main">
		<jsp:include page="/WEB-INF/jsp/common/mainNav.jsp">
			<jsp:param name="actionName" value="通用导出" />
		</jsp:include>
		
		<div id="processInfo" class="content-body">
		<form id="process_1" processId="1" processType="3" processName="上传文件流程" class="form-inline task" method="post" enctype="multipart/form-data">
			<div class="form-group">
				<div class="switch switch-large">
					<label class="pdl">是否上传:</label>
				    <input type="checkbox" checked id="uploadStatus" autocomplete="off"/>
				</div>
			</div>
			
			<div id="uploadDiv" style="display: none;margin-top: 15px;">
				<div class="form-group">
					<label class="pdl">上传文件:</label>
				</div>
				<div class="form-group">
					<input type="file" autocomplete="off" name="file" id="file_1">
				</div>
				<button class="btn btn-default upload_btn" type="button">点击上传</button><br><br>
				<input type="hidden" autocomplete="off" name="fileName">
				<input type="hidden" autocomplete="off" name="columns">
				<input type="hidden" value="1" name="getFileColumn">
				<div class="form-group">
					<label class="pdl">文件列名:</label>
					<input type="text" autocomplete="off" placeholder="列名称" class="form-control" style="width: 400px;" name="columnNames">
				</div>
			</div>
		</form>
		<hr>
		<form id="process_2" processId="2" processType="1" processName="日志数据流程" class="form-inline task">
			<div class="form-group">
				<div class="switch switch-large">
					 <span class="glyphicon glyphicon-question-sign" aria-hidden="true" data-toggle="tooltip" data-placement="right" title="除了用户信息多外,都算日志数据.如果只要导用户信息,选择'不需要获取'"></span>
					 <label>日志数据:</label>
				    <input type="checkbox" checked id="dataStatus" autocomplete="off"/>
				</div>
				<div id="dataForm">
					<div class="form-group myrow">
						<label class="pdl">选择站点:</label>
						<select class="multiselect" multiple="multiple" name="sid" id="sid"></select>
						<input type="checkbox" style="margin:2px 2px 3px 5px;;vertical-align:middle;" onclick="checkSite(0,this)" autocomplete="off">
						<label>PC</label>
						<input type="checkbox" style="margin:2px 2px 3px 5px;;vertical-align:middle;" onclick="checkSite(1,this)" autocomplete="off">
						<label>移动</label>
					</div>
					<div class="form-group myrow">
						<label class="pdl">时间:</label>
						<div id="dataRg" class="form-group">
			          	<div id="lgdaterange" class="date-input">
								<i class="glyphicon glyphicon-calendar fa fa-calendar"></i>
								<span></span> <b class="caret"></b>
							</div>
			          </div>
						<input type="checkbox" style="margin:2px 2px 3px 5px;vertical-align:middle;" class="realTime" autocomplete="off">
						<label>实时数据</label>
		         </div>
					<br/>
					<div class="form-group myrow">
						<label class="pdl">数据表名:</label>
						<select name="tableName" id="tableName" class="form-control"></select>
					</div>
					<div class="form-group myrow pdl" id="hiveColumns"></div>
					<div class="pdl" id="tableParam"></div>
					<div class="form-group pdl" id="getCount" style="margin-left: -6px"></div>
					
					<div class="hiveCondition">
					<div class="form-group" style="margin-top: 15px;">
						<label><span class="glyphicon glyphicon-question-sign" aria-hidden="true" data-toggle="tooltip" data-placement="right" title="按照某个用户/日期汇总或去重导出,最早或最晚一条数据等(可选项)">
						</span>去重汇总:</label>
						<select id="distinctType" class="form-control">
							<option value="-1">---------------</option>
							<option title="_uid" column="_uid" value="1">根据用户ID去重/汇总</option><option title="tm" column="tm" value="5">根据日期去重/汇总</option>
							<option title="_uid,日期" column="_uid,tm" value="2">根据用户ID和日期去重/汇总</option>
							<option value="3">根据时间获取用户最近一条记录</option><option value="4">根据时间获取用户最早一条记录</option>
						</select>
						<span class="distinctFlag">
						<input type="checkbox" style="margin:2px 2px 3px 5px;;vertical-align:middle;" class="distinctFlagName" autocomplete="off">
						<label>删除重复数据</label>
						</span>
					</div><br/>
					<div name="distinctParam" class="param"></div>
					<div id="hiveFormat">
						<div class="form-group myrow">
							<label><span class="glyphicon glyphicon-question-sign" aria-hidden="true" data-toggle="tooltip" data-placement="right" title="例如你导出的日期是1445934816这样的,把该字段格式化为日期(可选项)">
							</span>数据格式:</label>
							<select class="form-control maxWidth1 format_c" id="format_c"></select>
							<select id="format_t" class="form-control format_t"><option value="format">日期</option><option value="ipformat">IP转为国家</option></select>
							<select class="form-control format_v" id="format_v"></select>
							&nbsp;&nbsp;<input type="checkbox" class="format_v_outori" value="1">&nbsp;输出格式化前数据&nbsp;&nbsp;
							<button id="addFormat" class="btn btn-default" type="button">添加</button>
							<span style="color: red;">【tm是已经格式化的日期,通用情况下格式化时间都是选择_tm】</span>
						</div><br>
						<div name="formatParam" class="param"></div>
					</div>
					<div class="form-group">
						<label><span class="glyphicon glyphicon-question-sign" aria-hidden="true" data-toggle="tooltip" data-placement="right" title="导出某个wmode的金币/付费金额为多少等,在这里设置过滤,记得点击'添加'(可选项)"></span>
						过滤数据:</label>
						<select class="form-control maxWidth1 filter_c" id="filter_c"></select>
						<select class="form-control filter_op">
							<option value="0">等于</option><option value="1">小于</option><option value="2">小于或等于</option><option value="3">不等于</option>
							<option value="4">大于</option><option value="5">大于或等于</option><option value="6">in</option><option value="9">not in</option>
							<option value="7">like</option><option value="8">between</option></select>
						<div class="form-group input_group_div" style="display: none;"></div>
						<button id="addFilter" class="btn btn-default" type="button">添加</button><span style="color: red;">【in条件使用英文分号（;）分开各个值】</span>
					</div><br/>
					<div name="filterParam" class="param"></div>
					<div class="form-group myrow hiveDepend">
						<label><span class="glyphicon glyphicon-question-sign" aria-hidden="true" data-toggle="tooltip" data-placement="right" title="这里是和上传文件进行关联,如果没有上传文件就不用管,一般是_uid和上传文件中的某个列关联,记得点击'添加'"></span>
						关联条件:</label>
						<select class="form-control maxWidth1 depend_c" id="depend_c"></select>
						<select class="form-control depend_op" id="depend_op">
							<option value="0">等于</option>
						</select>
						<select id="depend_t" class="form-control depend_t"></select>
						<button id="addDepend" class="btn btn-default" type="button">添加</button>
						<br/>
					</div>
					<div name="dependParam" class="param"></div>
					</div>
				</div>
			</div>
		</form>
		<hr>
		<div class="switch switch-large">
			 <span class="glyphicon glyphicon-question-sign" aria-hidden="true" data-toggle="tooltip" data-placement="right" title="如果要获取用户信息,请打开!'"></span>
			 <label>用户信息:</label>
		    <input type="checkbox" checked id="userStatus" autocomplete="off"/>
		</div>
		<form id="process_3" processId="3" processType="2" processName="获取用户信息流程" class="form-inline task" style="display: none;">
			<div class="form-group">
				<%@include file="/WEB-INF/jsp/common/ucuser_default.jsp"%>
			</div>
			<div class="form-group" style="margin-top: 20px;">
				<label><span class="glyphicon glyphicon-question-sign" aria-hidden="true" data-toggle="tooltip" data-placement="right" title="导出付费金额为多少/台费大于某个值等,在这里设置过滤,记得点击'添加'(可选项)"></span>
				过滤数据:</label>
				<select class="form-control maxWidth1 filter_c"></select>
				<select class="form-control filter_op"><option value="0">等于</option><option value="1">小于</option><option value="2">小于或等于</option><option value="3">不等于</option><option value="4">大于</option><option value="5">大于或等于</option><option value="8">between</option></select>
				<div class="form-group input_group_div" style="display: none;"></div>
				<button id="uaddFilter" class="btn btn-default" type="button">添加</button>
			</div><br/>
			<div name="filterParam" class="param"></div>
			<div id="hbaseFormat" style="display: none;">
				<div class="form-group myrow" >
					<label><span class="glyphicon glyphicon-question-sign" aria-hidden="true" data-toggle="tooltip" data-placement="right" title="例如你导出的最后登录日期是1445934816这样的,把该字段格式化为日期(可选项)">
					</span>数据格式:</label>
					<select class="form-control maxWidth1 format_c" id="uformat_c"></select>
					<select id="uformat_t" class="form-control format_t"><option value="format">日期</option><option value="ipformat">IP转为国家</option></select>
					<select class="form-control format_v" id="uformat_date"></select>
					<span class="format_v_outori_span">&nbsp;&nbsp;<input type="checkbox" class="format_v_outori" value="1">&nbsp;输出格式化前数据&nbsp;&nbsp;</span>
					<button id="uaddFormat" class="btn btn-default" type="button">添加</button>
				</div><br>
				<div name="formatParam" class="param"></div>
			</div>
			<div class="form-group myrow">
				<label><span class="glyphicon glyphicon-question-sign" aria-hidden="true" data-toggle="tooltip" data-placement="right" title="关联上个流程,一盘是_uid与上个流程的某个字段关联,记得点击添加"></span>关联条件:</label>
				<select class="form-control maxWidth1 depend_c"></select>
				<select class="form-control depend_op" id="udepend_op"><option value="0">等于</option></select>
				<select class="form-control depend_t"></select>
				<button id="uaddDepend" class="btn btn-default" type="button">添加</button>
				<br/>
			</div>
			<div name="dependParam" class="param"></div>
		</form>
		<hr>
		<form class="form-inline">
			<div class="form-group">
				<span class="glyphicon glyphicon-question-sign" aria-hidden="true" data-toggle="tooltip" data-placement="right" title="合并多个流程的文件,哪个在前以它为准"></span>
				输出方式:&nbsp;&nbsp;<label class="checkbox-inline"><input type="checkbox" id="outType" name="outType" value="1" autocomplete="off"> 合并多个流程的文件输出</label>(如果不选中些项,则输出最后一个流程的文件)
				<div id="outTypeParam">
				</div>
			</div><br/>
			<div class="form-group" style="padding-top: 10px;">
				接收邮件:&nbsp;&nbsp;<input type="text" style="width: 300px;" id="email" name="email" class="form-control" placeholder="接收邮件" autocomplete="off" value="${sessionScope.email}">(多个邮箱使用';'(英文分号)分开,只有收件人才能看到下载文件)
			</div><br/>
			<div class="form-group" style="padding-top: 5px;">
				任务名称:&nbsp;&nbsp;<input type="text" style="width: 300px;" id="taskName" name="taskName" class="form-control" placeholder="任务名称" autocomplete="off">
			</div>
		</form>
		<button type="button" class="btn btn-primary" id="submitTask">提交任务</button>
		</div>
		<div class="panel panel-primary tableArticle" style="margin-top: 10px;">
		  <div class="panel-heading tableArticleHead"></div>
		  <div class="panel-body">
		    <iframe id="tableArticleIframe" name="tableArticleIframe" frameborder="0" scrolling="no" style="border:0px;width:100%;background: #fff;" onload="autoHeight('tableArticleIframe');"></iframe>
		  </div>
		</div>
	</div>
	
	<div class="modal fade myModal" style="display: none;"></div>
	<%@ include file="/WEB-INF/jsp/common/foot.jsp"%>

	<!-- Bootstrap core JavaScript navbar-fixed-bottom
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="static/js/basic.js"></script>
	<script src="static/js/dataTable.js"></script>
	<script src="static/js/daterangepicker.js"></script>
	<script src="static/bootstrap-switch/dist/js/bootstrap-switch.min.js"></script>
	<script src="static/select2/js/select2.min.js"></script>
	<script src="static/js/multiselect.js"></script>
	<script src="static/js/daterange.by.js"></script>
	<script src="static/js/dateUtils.js"></script>
	<script src="static/js/common.js"></script>
	<script src="static/js/jquery.form.js"></script>
	<script src="static/js/ucuser.js"></script>
	
	<script type="text/javascript">
	/*var tableArticle = {'user_gambling':397,'user_order2':395,
			'user_login':396,'user_signup':398,'bankrupt':399,'user_uid':400,'admin_act_logs':401,'winlog_mode':402,'event_logs':403,
			'mb_device_login_log':404,'act_mb_contact':405,'mtt_jbs_signup':406,'mtt_jbs_reward':407,'mtt_jbs_info':408,
			'ipk_gamecoins_stream':409,'ipk_bycoins_stream':410,'ipk_user_gambling':411};*/
	var tableArticle = {'user_gambling':397,'gamecoins_stream':394};
	
	$.fn.modal.Constructor.prototype.enforceFocus = function () {};
	var navForm;
	$(function() {
		$("#uploadStatus").bootstrapSwitch({
			onText:'上传文件',
			offText:'不上传文件',
			offColor:'danger',
			state:false,
			onSwitchChange:function(event, state){
				if(state){
					$("#uploadDiv").show();
					$("#process_2").find('.hiveDepend').show();
				}else{
					$("#uploadDiv").hide();
					$("#process_2").find('.hiveDepend').hide();
				}
			Task.updateHbaseDepend();
			}
		});
		$("#dataStatus").bootstrapSwitch({
			onText:'获取数据',
			offText:'不需要获取',
			offColor:'danger',
			onSwitchChange:function(event, state){
				if(state){
					$("#dataForm").show();
				}else{
					$("#dataForm").hide();
				}
				Task.updateHbaseDepend();
			}
		});
		$("#userStatus").bootstrapSwitch({
			onText:'获取数据',
			offText:'不需要获取',
			offColor:'danger',
			state:false,
			onSwitchChange:function(event, state){
				if(state){
					$("#process_3").show();
				}else{
					$("#process_3").hide();
				}
			}
		});
		
		Task.init();
		navForm = navForm.init({'showPlat':true,platChangeCallback:function(){Task.platChange();}});
		getMultiSid("#navPlat",$("#sid"),false,true);
		var std = new Date();
		std.addDays(-1);
		var yesterday = std.format('yyyyMMdd');
		
		var dateRange = createDateRgDom({'dom':'#dataRg',opens:'right','isShow':true,startDate:yesterday,maxDate:yesterday});
		
		$("#outType").unbind('click').click(function(){
			Task.setOutType(this);
		});
		
		$(".realTime").click(function(){
			Task.clickRealTime(true);
		/*	std = new Date();
			var today=std.format('yyyyMMdd');
			
			if(this.checked){
				dateRange= createDateRgDom({'dom':'#dataRg',opens:'right','isShow':true,startDate:today,endDate:today,minDate:yesterday,maxDate:today});
				Task.showOrHideHiveColumn(1,false);
			}else{
				dateRange= createDateRgDom({'dom':'#dataRg',opens:'right','isShow':true,startDate:yesterday,endDate:yesterday,maxDate:yesterday});
				Task.showOrHideHiveColumn(1,true);
			}
			
			;*/
		})
	});
	
	function checkSite(ismobile,obj){
		if($(obj).is(":checked")){
			$('#sid option[ismobile="'+ismobile+'"]').attr("selected","selected");
			$('#sid').html($('#sid').html());
		}else{
			$('#sid option[ismobile="'+ismobile+'"]').attr("selected",false);
		}
		$('#sid').mymultiselect("refresh");
		$('#sid').multiselectfilter('updateCache');
		$('#sid').mymultiselect("close");
	}
	
	var Task={
			formatIndex:0,
			init:function(){
				var _this =  this;
				_this.loadTable();
				
				$("#process_1 .upload_btn").click(function(){_this.upload();});
				$("#process_1").find("[name=columnNames]").on("keyup",function(){_this.changeUploadTitle();});
				
				$("#tableName").change(function(){_this.changeTable();});
				$("#format_t").change(function(){_this.changeFormatType('format_t','format_v');});
				$("#addFilter").click(function(){_this.addParams('filter','process_2');});
				$("#addFormat").click(function(){_this.addParams('format','process_2');});
				$("#addDepend").click(function(){_this.addParams('depend','process_2');});
				
				_this.changeTable();
				_this.changeFormatType('format_t','format_v');
				$("#process_2").find('.hiveDepend').hide();
				$("#distinctType").change(function(){
					_this.changeDistinctType();
				});
				
				$(".filter_op").change(function(){_this.filterChange(this);});
				$(".filter_op").change();
				
				//hbase
				if(ucuser){
					var options;
					$(ucuser).each(function(){
						options += '<option value="'+this.value+'">'+this.valueName+"("+this.value+")"+'</option>';
					});
					$("#process_3").find('.filter_c').html(options);
					//$("#process_3").find('.depend_c').html(options);
					$("#process_3").find('.depend_c').html('<option value="_uid">用户ID(_uid)</option>');
				};
				$("#process_3").find('.filter_c').select2();
				$("#process_3").find('.depend_c').select2();
				
				$("#ucuser [name=items]").click(function(){_this.userCheck();});
				$("#uformat_t").change(function(){_this.changeFormatType('uformat_t','uformat_date');});
				
				$("#uaddFilter").click(function(){_this.addParams('filter','process_3');});
				$("#uaddFormat").click(function(){_this.addParams('format','process_3');});
				$("#uaddDepend").click(function(){_this.addParams('depend','process_3');});
				
				$("#submitTask").unbind('click').click(function(){
					Task.submit();
				});
				
				$("#taskName").val('数据导出 | '+new Date().format('yyyy-MM-dd hh:mm:ss'));
			},
			filterChange:function(o){
				var html = '<div class="input-group">'+
							      '<input type="text" placeholder="in条件使用英文分号;分开各个值" class="form-control filter_v">'+
							      '<div class="input-group-btn">'+
							        '<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">特殊格式 <span class="caret"></span></button>'+
							        '<ul class="dropdown-menu dropdown-menu-right">'+
							          '<li><a href="javascript:void(0)" class="date2filter">日期转时间戳</a></li>'+
							        '</ul>'+
							      '</div>'+
						    '</div>';
				if($(o).val()==8){
					html += ' and ';
					html += '<div class="input-group">'+
						      '<input type="text" placeholder="in条件使用英文分号;分开各个值" class="form-control filter_v">'+
						      '<div class="input-group-btn">'+
						        '<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">特殊格式 <span class="caret"></span></button>'+
						        '<ul class="dropdown-menu dropdown-menu-right">'+
						          '<li><a href="javascript:void(0)" class="date2filter">日期转时间戳</a></li>'+
						        '</ul>'+
						      '</div>'+
						    '</div>';
				}
				$(o).next('.input_group_div').html(html);
				$(o).next('.input_group_div').show();
				
				$(".date2filter").click(function(){
					var _this = this;
					var html='<form class="form-inline">'+
						'<div id="tmpDateRg" class="form-group">'+
						'<div id="lgdaterange" class="date-input">'+
								'<i class="glyphicon glyphicon-calendar fa fa-calendar"></i>'+
								'<span></span> <b class="caret"></b>'+
							'</div>'+
					'</div>'+
					'</form>';
				
					showMsg(html,"请选择日期");
					
					createDateRgDom({'dom':'#tmpDateRg',singleDatePicker:true,'isShow':true,'timePicker':true,'format':'YYYY/MM/DD HH:mm:ss',applyClickCallBack:function(){
						var _tm = $('#tmpDateRg').data('daterangepicker').startDate.format('YYYY/MM/DD HH:mm:ss');
						_tm = new Date(_tm).getTime()/1000;
						$(_this).parents('.input-group').children('.filter_v').val(_tm);
						//closeMsg();
					}});
				});
			},
			userCheck:function(){
				$("#uformat_c").html('');
				var data = this.getSelectColumns(2);
				if(!this.selectColumnsIsEmpty(data)){
					var title = data.selectTitles.split(',');
					var columns = data.selectValues.split(',');
					for(var i=0;i<columns.length;i++){
						$("#uformat_c").append('<option value="'+columns[i]+'">'+title[i]+"("+columns[i]+")"+'</option>');
					}
					$("#hbaseFormat").show();
					$("#uformat_c").select2();
					this.changeFormatType('uformat_t','uformat_date');
				}else{
					$("#hbaseFormat").hide();
				}
			},
			hiveCheck:function(){
				$("#format_c").html("");
				var data = this.getSelectColumns_setCondition();
				if(!this.selectColumnsIsEmpty(data)){
					var title = data.selectTitles.split(',');
					var columns = data.selectValues.split(',');
					for(var i=0;i<columns.length;i++){
						$("#format_c").append('<option value="'+columns[i]+'">'+title[i]+'</option>');
					}
					this.changeFormatType('format_t','format_date');
					$("#hiveFormat").show();
				}else{
					$("#hiveFormat").hide();
				}
				$("#format_c").select2();
				this.updateHbaseDepend();
				
				this.changeDistinctType(true,$("#distinctType").val());
			},
			platChange:function(){
				getMultiSid("#navPlat",$("#sid"),true,true);
		     },
			loadTable:function(){
				var tables = getTables("hive",false);
				$.each(tables, function(index, value) {
			 		$("#tableName").append("<option title='"+value.desc+"' value=" + value.name + ">" + value.desc+"</option>");
				});
				$("#tableName").select2();
			},
			changeTable:function(){
				var _this =  this;
				
				$("#hiveColumns").html("加载中......");
				var columns = getConfigColumns($("#tableName").val(),'hive',true); 
				var content="";
				var options = "";
				var columnsObj = JSON.parse(columns);
				if(columnsObj && !isEmpty(columnsObj)){
					$.each(columnsObj, function(index, value) {
						var column_name = value.column_name;				
						if(column_name!='_tm' && column_name!='tm' && column_name !='plat' && column_name!='_plat' && column_name!='_uid'){
							column_name = '`'+value.column_name+'`';
						}
						
						content += '<label class="checkbox-inline" title="'+value.comment+'('+ value.column_name+ ')">'+
							'<input type="checkbox" ctype="'+value.type+'" name="column" titleName="'+value.comment+'('+ value.column_name+')" value="' + column_name + '" ';
						if(value.column_name == "_uid"){
							content += " checked ";
						}
						content += ' >' + value.comment+'('+ value.column_name+ ')</input></label>';
						options += '<option value="' + column_name + '" title="' + value.comment +'('+ value.column_name + ')">' + value.comment +'('+ value.column_name + ')</option>';
					});
				}
				$("#hiveColumns").html(content);
				$("#filter_c").html(options);
				$("#depend_c").html(options);
				$("#filter_c").select2();
				$("#depend_c").select2();
				
				$("#hiveColumns [name=column]").click(function(){_this.hiveCheck();});
				
				//加载每个表不同的字段
				var tableName = $("#tableName").val();
				if(tableName=='user_order2'){
					$("#tableParam").load('task/user_order.htm?index=-1');
				}else if(tableName=='user_gambling'){
					$("#tableParam").load('task/user_gambling.htm?index=-1');
				}else if(tableName=='gamecoins_stream'){
					$("#tableParam").load('task/gamecoins_stream.htm?index=-1');
				}else if(tableName=='winlog_mode'){
					$("#tableParam").load('task/winlog_mode.htm?index=-1');
				}else if(tableName=='user_login'){
					$("#tableParam").load('task/user_login.htm?index=-1');
				}else if(tableName=='banklogs'){
					$("#tableParam").load('task/banklogs.htm?index=-1');
				}else{
					$("#tableParam").html('');
				}
				
				$("#distinctType").val(-1);
				this.changeDistinctType();
				
				this.clickRealTime(false);
				
				this.getTableDesc(tableName,$("#tableName option:selected").text());
			},
			getTableDesc:function(tableName,tableDesc){
				$(".tableArticleHead").html('<<'+tableDesc+'>>相关描述');
				var articleId = tableArticle[tableName];
				if(!isNaN(articleId)){
					$(".tableArticle").show();
					$("#tableArticleIframe").attr('src',"http://mf.oa.com/docs/article/findById.htm?id="+articleId);
				}else{
					$(".tableArticle").hide();
				}
			},
			changeDistinctType:function(notAlert,oldValue){//去重/汇总
				var realTime = $(".realTime").is(":checked");			
				
				var _this=this;
				var _newValue = $("#distinctType").val();
				if(oldValue && _newValue==oldValue){
					return;
				}
				$("#distinctType").val(-1);
				var data = this.getSelectColumns_setCondition();
				$("#distinctType").val(_newValue);
				var uid=false,tm=false,_tm=false;
				
				
				if(!this.selectColumnsIsEmpty(data)){
					var selectValue = data.selectValues.split(',');
					for(var i=0;i<selectValue.length;i++){
						if(selectValue[i]=='_uid') uid=true;
						if(selectValue[i]=='tm') tm=true;
						if(selectValue[i]=='_tm') _tm=true;
					}
				}
				
				if($("#distinctType").val()==1 && !uid){
					$("#distinctType").val(-1);this.getOtherParam();
					if(!notAlert) alert("根据用户ID去重/汇总必须选择_uid");
					return false;
				}
				
				var tmFlag = realTime?_tm:tm;
				if($("#distinctType").val()==5 && !tmFlag){
					$("#distinctType").val(-1);this.getOtherParam();this.showOrHideHiveColumn(2);
					if(!notAlert) alert("根据日期去重/汇总必须选择tm(实时选择_tm)");
					return false;
				}
				if($("#distinctType").val()==2 && (!uid || !tmFlag)){
					$("#distinctType").val(-1);this.getOtherParam();this.showOrHideHiveColumn(2);
					if(!notAlert) alert("根据用户ID和日期去重/汇总必须选择_uid,tm(实时选择_tm)");
					return false;
				}
				if(($("#distinctType").val()==3 || $("#distinctType").val()==4) && (!uid || !_tm)){
					$("#distinctType").val(-1);this.getOtherParam();this.showOrHideHiveColumn(2);
					if(!notAlert) alert("获取用户最早/最晚一条记录必须选择_uid,_tm");
					return false;
				}
				this.getOtherParam();
				$("#getCountColumn").click(function(){_this.hiveCheck();});
				
				this.hiveCheck();
				this.showOrHideHiveColumn(2);
			},
			showOrHideHiveColumn:function(type,isShow){//type=1 显示/隐藏分区字段  type=2 显示/隐藏未汇总字段
				$("#process_2").find("[name=column]").each(function(index,data){
					if(type==1 && $(this).attr('ctype')==1){
						if(isShow){
							$(this).removeAttr("disabled");
						}else{
							$(this).removeAttr("checked");
							$(this).attr("disabled","disabled");
							Task.hiveCheck();
						}
					}
					if(type==2 && ($(this).attr('ctype')==0 || $(this).attr('ctype')==1)){
						var tm = $(".realTime").is(":checked")?"_tm":"tm";
						var noHide = $("#distinctType").val()==1?new Array('_uid'):($("#distinctType").val()==5?new Array(tm):($("#distinctType").val()==2?new Array('_uid',tm):null));
						if($("#distinctType").val()!=1 && $("#distinctType").val()!=2 && $("#distinctType").val()!=5){
							if($(this).attr('ctype')==0 || (!$(".realTime").is(":checked") && $(this).attr('ctype')==1)){
								$(this).removeAttr("disabled");
							}
						}else{
							if(noHide && !noHide.contains($(this).val())){
								$(this).removeAttr("checked");
								$(this).attr("disabled","disabled");
								Task.hiveCheck();
							}
						}
					}
				});
			},
			clickRealTime:function(createDate){
				var _this = this;				
				var std = new Date();
				var today=std.format('yyyyMMdd');
				std.addDays(-1);
				var yesterday = std.format('yyyyMMdd');
				
				if($(".realTime").is(":checked")){
					if(createDate)
						createDateRgDom({'dom':'#dataRg',opens:'right','isShow':true,startDate:today,endDate:today,minDate:yesterday,maxDate:today});
					_this.showOrHideHiveColumn(1,false);
				}else{
					if(createDate)
						dateRange= createDateRgDom({'dom':'#dataRg',opens:'right','isShow':true,startDate:yesterday,endDate:yesterday,maxDate:yesterday});
					Task.showOrHideHiveColumn(1,true);
				}
			},
			getOtherParam:function(){
				var tableName = $("#tableName").val();
				if($("#distinctType").val()==1 || $("#distinctType").val()==2 || $("#distinctType").val()==5){
					$("#getCount").html('&nbsp;&nbsp;<label class="checkbox-inline"><input type="checkbox" id="getCountColumn" value="1" title="统计'+tableName+'记录数">统计'+tableName+'记录数');
				}else{
					$("#getCount").html('');
				}
				if(tableName=='user_order2' && typeof(getOrderTableParam) != 'undefined' && isFunction(getOrderTableParam)){
					getOrderTableParam(-1);
				}else if(tableName=='user_gambling' && typeof(getUserGamblingTableParam) != 'undefined' && isFunction(getUserGamblingTableParam)){
					getUserGamblingTableParam(-1);
				}else if(tableName=='gamecoins_stream' && typeof(getGamecoinsStreamTableParam) != 'undefined' && isFunction(getGamecoinsStreamTableParam)){
					getGamecoinsStreamTableParam(-1);
				}else if(tableName=='winlog_mode' && typeof(getWinlogModeTableParam) != 'undefined' && isFunction(getWinlogModeTableParam)){
					getWinlogModeTableParam(-1);
				}else if(tableName=='user_login' && typeof(getLoginTableParam) != 'undefined' && isFunction(getLoginTableParam)){
					getLoginTableParam(-1);
				}else if(tableName=='banklogs' && typeof(getBankLogsTableParam) != 'undefined' && isFunction(getBankLogsTableParam)){
					getBankLogsTableParam(-1);
				}
				
				if($("#distinctType").val()==-1){
					$(".distinctFlag").show();
				}else{
					$(".distinctFlag").hide();
				}
			},
			changeFormatType:function(format_t,format_v){
				$("#"+format_v).html('');
				if($("#"+format_t).val()=='format'){
					$("#"+format_v).show();
					$("#"+format_v).append('<option value="yyyy-MM-dd">yyyy-MM-dd</option><option value="yyyy-MM-dd HH:mm:ss">yyyy-MM-dd HH:mm:ss</option>');
					$("#"+format_v).prop("disabled", false);
					
					if(format_t=='uformat_t'){
						$("#process_3").find('.format_v_outori_span').hide();
					}
				}else{
					$("#"+format_v).hide();
					$("#"+format_v).prop("disabled", true);
					
					if(format_t=='uformat_t'){
						$("#process_3").find('.format_v_outori_span').show();
					}
				}
				$("#"+format_v).select2();
			},
			changeUploadTitle:function(){
				$("#depend_t").html('');
				
     			var data = this.getSelectColumns(3);
     			var title = data.selectTitles.split(',');
				var columns = data.selectValues.split(',');
				for(var i=0;i<columns.length;i++){
					$("#depend_t").append('<option value="'+columns[i]+'">[上传文件] '+title[i]+'</option>');
				}
			   this.updateHbaseDepend();
			},
			updateHbaseDepend:function(){
				$("#process_3").find('.depend_t').html('');
				var data;
				if($("#dataStatus").bootstrapSwitch('state')){
					data = this.getSelectColumns_out();
				}else if($("#uploadStatus").bootstrapSwitch('state')){
					data = this.getSelectColumns(3);
				}
				if(!this.selectColumnsIsEmpty(data)){
					var title = data.selectTitles.split(',');
					var columns = data.selectValues.split(',');
					for(var i=0;i<columns.length;i++){
						$("#process_3").find('.depend_t').append('<option value="'+columns[i]+'">'+title[i]+'</option>');
					}
				}
			},
			upload:function(){
				var _this = this;
				var fileVal=$("#file_1").val();
				if(fileVal!=null && fileVal.length>0){
					var fileExt = fileVal.substring(fileVal.lastIndexOf('.') + 1);
					if(fileExt!='txt' && fileExt!='csv'){
						alert("目录只允许上传.txt和.csv文件");
						return;
					}
									
					$(function(){  
						var options = {
				           	url : "upload",  
				           	type : "POST",  
				           	dataType : "json",
				           	success : function(msg) {
				          		if(msg && msg.ok==1){
				          			$("#process_1").find("[name=fileName]").val(msg.other.fileName);
				          			$("#process_1").find("[name=columns]").val(msg.other.fileColumn);
				          			$("#process_1").find("[name=columnNames]").val(msg.other.fileColumn);
				          			$("#process_1").find("[name=upload-sucess]").remove();
				          			$("#process_1 .upload_btn").before('<span class="glyphicon glyphicon-ok" name="upload-sucess" style="color: #3c763d" title="上传成功">上传成功</span>&nbsp;&nbsp;&nbsp;');
				          			
				          			_this.changeUploadTitle();
				          		}else{
				          			alert(msg.msg);
				          			}
				           		},
				           	error:function(msg){
				           		alert(msg);
				           		}
				      	};
						$("#process_1").ajaxSubmit(options);  
			       	return false;  
					});  
				}else{
					alert("请选择要上传的文件");
				}
			},
			addParams:function(type,processId){
				this.formatIndex ++ ;
				var dom,value,lable;
				if(type=='filter'){
					var filter_op = $("#"+processId).find('.filter_op');
					var filter_c = $("#"+processId).find('.filter_c');
					dom=$("#"+processId).find('[name="filterParam"]');
					
					var filter_v='';
					var exitFlag = false;
					filter_op.next('.input_group_div').find('.input-group').each(function(index){
						var _filter_v = $(this).find('.filter_v').val();
						
						if(isEmpty(_filter_v)){
							exitFlag=true;
							alert("过滤值不能为空");
							return;
						}
						filter_v += (index!=0?"___":"")+_filter_v;
					});
					if(exitFlag){
						return;
					}
					
					value=filter_c.val()+'#'+filter_op.val()+"#"+filter_v;
					lable=filter_c.val()+" "+op(filter_op.val())+" "+filter_v;
				}else if(type=='format'){
					var formatType = $("#"+processId).find('.format_t').val();
					var format_c = $("#"+processId).find('.format_c').val();
					var format_v = $("#"+processId).find('.format_v').val();
					var format_v_outori = $("#"+processId).find('.format_v_outori');
					
					if(formatType=='format'){
						value=(processId=='process_3'?"":"format"+this.formatIndex+"#")+formatType+"#"+format_c+"#"+format_v+"#"+(format_v_outori.is(":checked")?1:0);
						lable=formatType+"#"+format_c+'#'+format_v+"#"+(format_v_outori.is(":checked")?"输出格式化前数据":"不输出格式化前数据");
					}else if(formatType=='ipformat'){
						value=(processId=='process_3'?"":"format"+this.formatIndex+"#")+formatType+"#"+format_c+'#'+(format_v_outori.is(":checked")?1:0);
						lable=formatType+"#"+format_c+'#'+(format_v_outori.is(":checked")?"输出格式化前数据":"不输出格式化前数据");
					}
					dom=$("#"+processId).find('[name="formatParam"]');
				}else if(type=='depend'){
					var depend_t = $("#"+processId).find('.depend_t').val();
					var depend_c = $("#"+processId).find('.depend_c').val();
					var depend_op = $("#"+processId).find('.depend_op').val();
					
					if(isEmpty(depend_t)){
						alert(processId=='process_2'?"标题不能为空,请确认是否上传成功!":"上一个流程无效,请确认上个流程是否设置正确!");
						return;
					}
					
					value=depend_c+'#'+depend_op+'#'+depend_t;
					lable=depend_c+" "+op(depend_op)+" "+depend_t;
					dom=$("#"+processId).find('[name="dependParam"]');
				}
				dom.append('<div class="labelColumn"><p><span class="columnValue" value="'+value+'">'+lable+'</span> <span class="labelClose" onclick="labelClose(this)">×</span></p></div>');
				
				this.updateHbaseDepend();
			},
			getDefaultColumn:function(columnNames){
				var column = "";
				if(!isEmpty(columnNames)){
					var array = columnNames.split(",");
					for(var i=0;i<=array.length-1;i++){
						column+= ("c"+i+(i==array.length-1?"":","));
					}
				}
				return column;
			},
			getSelectColumns_tableColumn:function(){//一个表所选择的字段
				return this.getSelectColumns(1,true,true,false);
			},
			getSelectColumns_setCondition:function(){//
				return this.getSelectColumns(1,false,true,false);
			},
			getSelectColumns_out:function(){//最终输出或者给其他流程引用的
				if($("#dataStatus").bootstrapSwitch('state')){
					return this.getSelectColumns(1,true,false,true);
				}
				return {};
			},
			getSelectColumns:function(processType,isFilterFormat,isNotAddFormat,addTableParamFlag){
				var data={},_this=this;
				if(processType==3){
					var columnNames = $("#process_1").find("[name=columnNames]").val();
					if(!isEmpty(columnNames)){
						data.selectTitles= columnNames;
						data.selectValues= _this.getDefaultColumn(columnNames);
					}
				}else if(processType==1){
					if($("#process_2").find("[name=column]:checked").length>0){
						var column='',columnName='';
						//判断是否有去重
						var distinctType = $("#distinctType");
						if(distinctType.val()==1 || distinctType.val()==2 || distinctType.val()==5){
							var _data = this.getHiveSelectColumn();
							
							var _selectTitles = distinctType.find("option:selected").attr('title');
							var _selectValues = distinctType.find("option:selected").attr('column');
							if($(".realTime").is(":checked")){
								_selectValues =_selectValues.replaceAll("tm","_tm");
							}
							
							var selectTitles = '';
							var selectValues = '';
							
							var sv1=_data.selectValues.split(',');
							var sv2=_selectValues.split(',');
							
							var flag = true;
							for(var i=0;i<sv2.length;i++){
								if(!sv1.contains(sv2[i])){
									flag = false;
									return;
								}
							}
							if(flag){
								selectTitles=_selectTitles;
								selectValues=_selectValues;
							}
							
							if($("#getCountColumn").is(":checked") && addTableParamFlag){
								selectTitles += ","+$("#getCountColumn").attr('title');
								selectValues += ",getCount";
							}
							
							columnName= selectTitles;
							column= selectValues;
						}else{
							var first = true;
							//格式化
							var notInColumns = [];
							if(isFilterFormat){
								$("#process_2").find("[name=formatParam] .columnValue").each(function(){
									var c = $(this).attr("value");
									
									if(!isNotAddFormat){
										column += (first?'':',')+c.split("#")[0];
										columnName += (first?'':',')+$(this).html();
										first=false;
									}
									
									if(c.substring(c.length-1)==0){
										notInColumns.push(c.split("#")[2]);
									}
								});
							}
							
							$("#process_2").find("[name=column]").each(function(index,data){
								if($(this).is(":checked")){
									var _column = $(this).val(),_columnName=$(this).attr("titleName")?$(this).attr("titleName"):$(this).val();
									
									if(!notInColumns.contains(_column)){
										column += (first?'':',')+_column;
										columnName += (first?'':',')+_columnName;	
										first=false;
									}
								}
							});
						}
						
						if(addTableParamFlag && $("#tableParam_-1").length>0 && !isEmpty($("#tableParam_-1").attr("column"))){
							var tmpTitle = $("#tableParam_-1").attr("title");
							columnName += (","+_this.getTableParamTV(tmpTitle,0));
							column += (","+_this.getTableParamTV(tmpTitle,1));
						}
						
						data.selectTitles= columnName;
						data.selectValues= column;
					}
				}else if(processType==2){
					if($("#userStatus").bootstrapSwitch('state')){					
						if($("#ucuser").find("[name=items]:checked").length>0){
							var first = true,column='',columnName='';
							var formatParam = _this.getLabelParam($("#process_3"),'formatParam');
								
							$("#ucuser").find("[name=items]").each(function(index,data){
								if($(this).is(":checked")){
									var _column = $(this).val(),_columnName=$(this).attr("valuename")?$(this).attr("valuename"):$(this).val();
									//格式化
									var ipFormatData =_this.getIpFormatSelectColumns(formatParam,_column,_columnName);
									if(ipFormatData && !isEmpty(ipFormatData.title)){
										column += (first?'':',')+ipFormatData.column;
										columnName += (first?'':',')+ipFormatData.title;	
									}else{
										column += (first?'':',')+_column;
										columnName += (first?'':',')+_columnName;	
									}
									first=false;
								}
							});
							data.selectTitles= columnName;
							data.selectValues= column;
						}
					}
				}
				return data;
			},
			getIpFormatSelectColumns:function(formatParam,column,columnName){//ipformat#sitemid#1,format#_uid#yyyy-MM-dd
				var data={};
				//对IP格式化做特殊处理
				//if(!isEmpty(formatParam) && formatParam.contains("ipformat")){
				if(!isEmpty(formatParam) && formatParam.indexOf("ipformat#")>=0){
					var formatArray = formatParam.split(",");
					for(var i=0;i<formatArray.length;i++){
						var formatItems = formatArray[i].split("#");
						if(formatItems[0]=="ipformat" && formatItems[1]==column){
							if(formatItems[2]==1){
								data.title=columnName+","+columnName+"(IP格式化)";
								data.column = column+",ipformat__"+column;
							}else{
								data.title=columnName+"(IP格式化)";
								data.column = "ipformat__"+column;
							}
							break;
						}
					}
				}
				return data;
			},
			getHiveSelectColumn:function(){//获取现在正在被选择中的hive列
				var data={},column='',columnName='',first=true;
				$("#process_2").find("[name=column]").each(function(index,data){
					if($(this).is(":checked")){
						var _column = $(this).val(),_columnName=$(this).attr("titleName")?$(this).attr("titleName"):$(this).val();
						
						column += (first?'':',')+_column;
						columnName += (first?'':',')+_columnName;	
						first=false;
					}
				});
				data.selectTitles= columnName;
				data.selectValues= column;
				return data;
			},
			getTableParamTV:function(title,index){//
				var rTitle = "";
				if(!isEmpty(title)){
					var array = title.split(",");
					for(var i=0;i<=array.length-1;i++){
						var tmpTitle ="";
						if(index==1){
							tmpTitle = array[i].substring(array[i].indexOf("|")+1);
						}else{
							tmpTitle = array[i].substring(0,array[i].indexOf("|"));
						}
						rTitle+= (tmpTitle+(i==array.length-1?"":","));
					}
				}
				return rTitle;
			},
			selectColumnsIsEmpty:function(data){
				if(!data || isEmpty(data.selectValues)){
					return true;
				}
				return false;
			},
			getLabelParam:function(o,name){
				var param = '';
				o.find("[name="+name+"] .columnValue").each(function(){
					param+=($(this).attr("value")+",");
				});
				if(param.length>0){
					param = param.substring(0, param.length-1);
				}
				return param;
			},
			getDependOn:function(processInfo,processId){
				if(processId==3){
					if($("#dataStatus").bootstrapSwitch('state')){
						processInfo.dependOn='2';
					}else if($("#uploadStatus").bootstrapSwitch('state')){
						processInfo.dependOn='1';
					}else{
						processInfo.dependOn='';
					}
					processInfo.linkParam=this.getLabelParam($("#process_3"),'dependParam');
				}else if(processId==2 && $("#uploadStatus").bootstrapSwitch('state')){
					processInfo.dependOn='1';
					processInfo.linkParam=this.getLabelParam($("#process_2"),'dependParam');
				}
			},
			checkForm:function(){
				if(isEmpty($("#taskName").val())){
					alert("任务名称不能为空");
					return false;
				}
				
				var process3Depend = this.getLabelParam($("#process_3"),'dependParam');
				var process2Depend = this.getLabelParam($("#process_2"),'dependParam');
				var data = this.getSelectColumns(2);
				
				if($("#uploadStatus").bootstrapSwitch('state') && $("#dataStatus").bootstrapSwitch('state') && isEmpty(process2Depend)){
					alert('获取日志数据必须设置与上传文件之间的关联条件');
					return false;
				}
				if(($("#uploadStatus").bootstrapSwitch('state') || $("#dataStatus").bootstrapSwitch('state')) && isEmpty(process3Depend) && 
						data && !isEmpty(data.selectValues) && data.selectValues.length>0){
					alert('获取用户数据必须设置与上一步流程之间的关联条件');
					return false;
				}
				return true;
			},
			setOutType:function(v){//合并流程
				var _this=this;
				if(v.checked){
					var form = '';
					//获取所有文件的输出列
					$("#processInfo").find(".task").each(function(){
						var processId= $(this).attr('processId');
						var processType = $(this).attr('processType');
						
						var data;
						if(processId==2){
							data = _this.getSelectColumns_out();
						}else{
							data = _this.getSelectColumns(processType);
						}
						
						if(data.selectValues && data.selectValues.length>0){
							form +='<div class="myrow processColumn" id="outputTypeColumn_'+processId+'" processId="'+processId+'" processType="'+processType+'" processName="'+$(this).attr('processName')+'">'+
							'<span style="color: green;font-weight: bold;">'+$(this).attr('processName')+'</span><br/>';
							
							var values = data.selectValues.split(',');
							var titles = data.selectTitles.split(',');
							for(var i=0;i<values.length;i++){
								form += _this.checkBoxHtml(values[i],titles[i],i==0);
							}
							form+='</div>';
						}
					});
					
					var html = 
					    '<div class="modal-dialog modal-lg"><div class="modal-content">'+
					      '<div class="modal-header">'+
					        '<button type="button" class="close closeOutPutModal" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>'+
					        '<h4 class="modal-title">合并多个文件输出</h4>'+
					      '</div>'+
					      '<div class="modal-body">';
					      	
					if(form!=''){
						html += '<form class="form-inline" id="outputForm">';
						html += '<div class="myrow" style="font-size:18px;font-weight:blod;">选择输出文件的列:</div>';
						html +=form;
						html += '<br/><div class="myrow" style="font-size:18px;font-weight:blod;">选择合并文件的关系字段:</div>';
						html += '<div id="outputFormLink"></div>';
						html +=	'</form>';
					}else{
						html +='没有可输出的列';
						v.checked=false;
					}
				  
				  	html+='</div>'+
				      '<div class="modal-footer">'+
				        '<button type="button" class="btn btn-default closeOutPutModal" data-dismiss="modal">关闭</button>';
				   if(form!=''){
				  		html+='<button type="button" class="btn btn-primary" id="addOutputTypt">提交</button>';
				    }
				  	html+='</div>'+
				  		'</div></div>';
				  
				  	_this.createModal(html);
				  	
				  	$(".checkColumn").unbind('click').click(function(){
				  		_this.checkColumn(this);
				  	});
				  	
				  	$("#addOutputTypt").unbind('click').click(function(){
				  		_this.addOutputTypt();
				  	});
					
					$(".closeOutPutModal").unbind('click').click(function(){
				  		_this.closeOutPutModal();
				  	});
					
					$("#outTypeParam").show();
				}else{
					$("#outTypeParam").html('');
					$("#outTypeParam").hide();
				}
			},
			checkBoxHtml:function(value,name,first){
				return '<label class="checkbox-inline checkbox-label '+(first?'first':'')+'" title="'+ name+'('+value+')">'+
				  '<input type="checkbox" name="column" class="checkColumn" titleName="'+name+'" title="'+ name+'('+value+')" value="'+value+'"> '+name+'('+value+')</label>';
			},
			addOutputTypt:function(){
				var html='<br/><div class="myrow">输出文件的列:</div>';
				//获取输出的列
				$("#outputForm").find(".processColumn").each(function(){
					var processId = $(this).attr("processId");
					if($("#outputTypeColumn_"+processId).find('[name=column]:checked').length>0){
						var first = true,column='',columnName='',pname = $(this).attr('processName');
						$("#outputTypeColumn_"+processId).find('[name=column]:checked').each(function(index,data){
							if($(this).is(":checked")){
								column += (first?'':',')+$(this).val();
								columnName += (first?'':',')+$(this).attr("titleName");		
								first=false;
							}
						});
						html += '<div class="myrow outputTypeColumn" processId="'+processId+'">'+pname+':<div class="labelColumn"><p><span value="'+column+'" class="columnValue">'+columnName+'</span><span onclick="labelClose(this)" class="labelClose">×</span></p></div></div>';
					}
				});
				
				//获取关联条件
				var first = true,l='',v='';
				$("#outputFormLink").find('[name=outputLink]').each(function(index,data){
					var processId = $(this).attr("processId");
					
					var s = $(this).find("option:selected");
					 
					l+=(first?'':'=')+$(this).attr('processName')+'.'+s.text();
					v+=(first?'':'=')+processId+'.'+$(this).val();
					first=false;
				});
				html += '<div class="myrow outputTypeLink">合并条件:<div class="labelColumn"><p><span value="'+v+'" class="columnValue">'+l+'</span><span onclick="labelClose(this)" class="labelClose">×</span></p></div>';
				
				$("#outTypeParam").html(html);
			},
			checkColumn:function(o){
				var processColumn = $(o).parent().parent();
				
				var processId = processColumn.attr('processId');
				var processType = processColumn.attr('processType');

				if(o.checked && $("#outputTypeLink_"+processId).length<=0){
					var data;
					if(processId==2){
						data = this.getSelectColumns_out();
					}else{
						data = this.getSelectColumns(processType);
					}
					var values = data.selectValues.split(',');
					var titles = data.selectTitles.split(',');
					
					var form = '<span id="outputTypeLink_'+processId+'">'+processColumn.attr("processName")+':<select class="form-control outputLink" processId="'+processId+'" processName="'+processColumn.attr("processName")+'" name="outputLink">';
					
					for(var i=0;i<values.length;i++){
						form += '<option value="'+values[i]+'" title="'+titles[i]+'">'+titles[i]+'('+values[i]+')</option>';
					}	
					form += '</select><span>';
					
					$("#outputFormLink").append(form);
				}
				if(!o.checked){
					//判断是否还有选择中,如果没有,把下拉框去掉
					if($("#outputTypeColumn_"+processId).find('[name=column]:checked').length==0){
						$('#outputTypeLink_'+processId).remove();
					}
				}
			},
			closeOutPutModal:function(){
				if(isEmpty($("#outTypeParam").html())){
					document.getElementById("outType").checked=false;
				}
			},
			createModal:function(html){
				$(".myModal").html("");
				$(".myModal").html(html);
		      $(".myModal").modal({
		    	  backdrop:"static"
		      });
			},
			submit:function(){
				var _this = this,plat = $("#navPlat").val(),svid = $("#navPlat").find("option:selected").attr('svid');
				
				var checkStatus = _this.checkForm();
				if(!checkStatus){
					return;
				}
				
				var taskInfo ={
					taskName:$("#taskName").val(),
					plat:plat,
					svid:svid,email:$("#email").val()
				};
				
				//获取输出文件的方式
				if(document.getElementById("outType").checked){
					var outputColumn = '',first=true;
					$("#outTypeParam").find(".outputTypeColumn").each(function(){
						outputColumn += (first?"":"|")+$(this).attr('processId')+":"+$(this).find(".columnValue").attr("value");
						first=false;
					});
					var outputTypeLink =$("#outTypeParam").find(".outputTypeLink .columnValue").attr("value");
					if(isEmpty(outputColumn) || isEmpty(outputTypeLink)){
						alert("选择按条件多文件合并,必须选输出的列和合并条件");
						return false;
					}
					taskInfo.outputColumn=outputColumn;
					taskInfo.outputLink=outputTypeLink;
					taskInfo.outputType=1;
				}else{
					taskInfo.outputColumn='';
					taskInfo.outputLink='';
					taskInfo.outputType=0;
				}
				
				var processList = [];
				//上传文件
				if($("#uploadStatus").bootstrapSwitch('state')){
					if(isEmpty($("#process_1").find("[name=file]").val())){
						alert("请点击上传文件按钮,并上传成功后再提交任务!");
						return false;
					}
					
					var data = _this.getSelectColumns(3);
					if(isEmpty(data.selectTitles)){
						alert("文件内容对应的列别名不能为空");
						return false;
					}
					
					var processInfo = {processId:1,processType:3};
					
					processInfo.fileName= $("#process_1").find("[name=fileName]").val();
					processInfo.columnNames = data.selectTitles;
					processInfo.columns = data.selectValues;
					
					processList.push(processInfo);
				}
				
				if($("#dataStatus").bootstrapSwitch('state')){
					var processInfo = {processId:2,processType:1};
					
					var data = _this.getSelectColumns_tableColumn();
					var selectValues = data.selectValues;
					if (isEmpty(selectValues)) {
		 				alert("日志数据必须选择要导出的字段");
						return false;
					}
					var startTime = DaterangeUtil.getStartDate("#dataRg","YYYY-MM-DD");
					var endTime = DaterangeUtil.getEndDate("#dataRg","YYYY-MM-DD");
					if (isEmpty(startTime)|| isEmpty(endTime)) {
						alert("日志数据必须选择要导出的开始与结束时间");
						return false;
					}
					
					processInfo.plat= plat;
					processInfo.svid= svid;
					processInfo.sid= $("#sid").attr("selectValues");
					processInfo.startTime= startTime;
					processInfo.endTime= endTime;
					processInfo.tableName = $("#tableName").val()+($(".realTime").is(":checked")?"_text":"");
					processInfo.realTimeData = $(".realTime").is(":checked")?"1":"0";
					processInfo.columns=selectValues;
					processInfo.columnNames=data.selectTitles;
					
					
					//条件
					processInfo.filter=_this.getLabelParam($("#process_2"),'filterParam');
					processInfo.format=_this.getLabelParam($("#process_2"),'formatParam');
					_this.getDependOn(processInfo,2);
					
					var distinctType = $("#distinctType").val();
					processInfo.distinctType=distinctType;
					processInfo.filterDuplicateData=$(".distinctFlagName").is(":checked")?"1":"0";
					
					if($("#getCountColumn").is(":checked")){
						processInfo.getCount=$("#getCountColumn").val();
						processInfo.getCountTitle=$("#getCountColumn").attr('title');
					}
					
					if($("#tableParam_-1").length>0 && !isEmpty($("#tableParam_-1").attr("column"))){
						processInfo.tableColumn=$("#tableParam_-1").attr("column");
						processInfo.tableTitle=$("#tableParam_-1").attr("title");
					}
					
					processList.push(processInfo);
				}
				
				var data = _this.getSelectColumns(2);
				if(data && !isEmpty(data.selectValues) && data.selectValues.length>0){
					var processInfo = {processId:3,processType:2};
					processInfo.tableName = "user_ucuser";
					processInfo.plat= plat;
					processInfo.columns=data.selectValues;
					processInfo.columnNames=data.selectTitles;
					
					_this.getDependOn(processInfo,3);
					processInfo.filter=_this.getLabelParam($("#process_3"),'filterParam');
					processInfo.formatParam=_this.getLabelParam($("#process_3"),'formatParam');
					
					processList.push(processInfo);
				}
				
				taskInfo.processList=processList;
				if(taskInfo.processList.length<=0){
					alert("至少要添加一个导出流程才能提交");
					return;
				}
				
				$.post("task/process/addLinkTask.htm", {taskInfo:JSON.stringify(taskInfo)}, function(data, textStatus) {
					if(data.ok==0){
						alert(data.msg);
		    		}else{
						alert("添加成功!");
		    		}
				}, "json");
			}
		};
		function labelClose(o) {
			$(o).parent().parent().remove();
		}
	</script>
</body>
</html>