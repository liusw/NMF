<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en-us">
<head>
<%@ include file="/WEB-INF/jsp/common/meta.jsp"%>
<title>多流程查询-数据魔方</title>
<style type="text/css">
.task{
	background: #fff;
}
.processTitle{
	background: #454c58;
	height: 35px;
	line-height: 35px;
	padding: 0 10px;
	font-size: 16px;
	color: #fff;
	font-weight: 100;
}
.checkbox-label{
	line-height: 20px;
	height: 20px;
	width: 150px;
	overflow: hidden;
}

.first{
	margin-left: 10px;
   margin-top: 0;
}

.removeLable{
	height: 35px;
	line-height: 35px;
	float: right;
   font-size: 14px;
}
.removeLable a{
	color: #fff;
}
.pl10{
	padding-left: 10px;
}
</style>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/top.jsp">
        <jsp:param name="nav" value="data"/>
	</jsp:include>
	
	<jsp:include page="/WEB-INF/jsp/common/dataMenu.jsp">
		<jsp:param name="nav" value="customQuery" />
		<jsp:param name="subnav" value="task" />
	</jsp:include>
	
	<div class="main">
		<jsp:include page="/WEB-INF/jsp/common/mainNav.jsp">
			<jsp:param name="actionName" value="多流程查询" />
		</jsp:include>
		
		<div class="panel panel-primary">
		  <div class="panel-body">
		  	<p style="color: red;font-weight: bold;font-size: 20px;">此功能已经完全转到:<a href="http://mf.oa.com/task/commonExport.htm" target="_blank">http://mf.oa.com/task/commonExport.jsp</a></p>
			上传用户ID导出用户信息,请认真阅读帮助文档:&nbsp;&nbsp;<a href="http://mf.oa.com/docs/article/detail.htm?id=86" target="_blank">http://mf.oa.com/docs/article/detail.mf?id=86</a>
		  </div>
		</div>
		
		<div id="processInfo"></div>
		<form class="form-inline" id="taskInfo">
			<div class="form-group" style="padding-top: 8px;">
				输出方式:&nbsp;&nbsp;<label class="checkbox-inline"><input type="checkbox" id="outType" name="outType" value="1"> 多流程合并文件输出</label>(如果不选中些项,则输出最后一个流程的文件)
				<div id="outTypeParam">
				</div>
			</div><br/>
			<div class="form-group" style="padding-top: 5px;">
				接收邮件:&nbsp;&nbsp;<input type="text" style="width: 300px;" id="email" name="email" class="form-control" placeholder="接收邮件" autocomplete="off" value="${sessionScope.email}">(多个邮箱使用';'(英文逗号)分开,只有收件人才能看到下载文件)
			</div><br/>
			<div class="form-group" style="padding-top: 5px;">
				任务名称:&nbsp;&nbsp;<input type="text" style="width: 300px;" id="taskName" name="taskName" class="form-control" placeholder="任务名称" autocomplete="off">
			</div>
			
		</form>
		<br/>
		<button type="button" class="btn btn-default" id="addProcess">增加流程</button>&nbsp;&nbsp;<button type="button" class="btn btn-primary" id="submitTask">提交任务</button>
		
		<div class="modal fade myModal" style="display: none;"></div>
		<input type="hidden" id="processIndex" autocomplete="off" value="0">
	</div>	
<br/><br/><br/>

	<%@ include file="/WEB-INF/jsp/common/foot.jsp"%>

	<!-- Bootstrap core JavaScript navbar-fixed-bottom
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="static/js/basic.js"></script>
	<script src="static/js/datetimepicke.js"></script>
	<script src="static/js/multiselect.js"></script>
	<script type="text/javascript" src="static/select2/js/select2.min.js"></script>
	<script src="static/js/common.js"></script>
	<script src="static/js/dateUtils.js"></script>
	<script type="text/javascript" src="static/js/jquery.form.js"></script>
	<script src="static/js/ucuser.js"></script>
	<script type="text/javascript">
	var platId = "navPlat",jplatId="#navPlat";
	
	$(function(){
		$("#taskName").val('多流程查询 | '+new Date().format('yyyy-MM-dd hh:mm:ss'));
		
		navForm.init({'showPlat':true,
			platChangeCallback:function(){Task.platChange();}
		});
		
		Task.addProcess();
		$("#addProcess").unbind('click').click(function() {
			Task.addProcess();
		});
		$("#submitTask").unbind('click').click(function(){
			Task.submit();
		});
		$("#outType").unbind('click').click(function(){
			Task.setOutType(this);
		});
	});
	
	var Task={  
	   platId:"navPlat",
	   jplatId:"#navPlat",
	 	platChange:function(){
	 		$("#processInfo").find(".task").each(function(){
				var processType = $(this).find("[name=processType]").val();
				if(processType==1){
					var sid = $(this).find("[name=sid]").attr("id");
					getMultiSid(jplatId,$("#"+sid),true,true);
				}else if(processType==2){
					var sid = $(this).find("[name=sid]").attr("id");
					getSid($(jplatId).val(),"#"+sid);
				}
			});
	     },
	    addProcess:function(){
	    	var _this=this;
	    	var index = parseInt($("#processIndex").val())+1;
	    	var processId = 'process_'+index,jprocessId="#"+processId;
	    	
	    	var html =	'<form pname="流程'+index+'" class="form-inline task" id="'+processId+'">'+
								'<div class="processTitle">流程'+index+'<span class="removeLable"><a href="javascript:void(0);"> x</a></span></div>'+
								'<div class="myrow">'+
									'<div class="pl10">类型:<select name="processType" id="processType_'+index+'" class="form-control">'+
										'<option value="1" selected="selected">日志信息</option><option value="2">用户信息</option><option value="3">上传文件</option>'+
										'<option value="4">自定义查询</option><option value="5">mysql操作</option><option value="6">CUSTOM_EXEC</option><option value="10">hbase插入数据</option>'+
									'</select>&nbsp;&nbsp;&nbsp;<a name="setParams" href="javascript:void(0);">参数设置</a></div></div>';
			
			html +=			'<div class="pl10" id="processDiv_'+index+'"></div>';
			html +=		'</form>';
			$("#processInfo").append(html);
			$("#processIndex").val(index);
			
			//关闭
			$(jprocessId).find(".removeLable").unbind('click').click(function(){
				$(jprocessId).remove();
			});
			//设置参数
			$(jprocessId).find("[name=setParams]").unbind('click').click(function(){
				_this.setParams(index);
			});
			$("#processType_"+index).change(function(){
				_this.processTypeChange(index);
			});
			
			this.processTypeChange(index);
	     },
	    processTypeChange:function(index){
	    	var _this=this;
	    	var jProcessDivId="#processDiv_"+index,jProcessId="#process_"+index,jProcessTypeId="#processType_"+index;
	    	
	    	$(jProcessDivId).html('');
			$(jProcessId).find("[name=setParams]").show();
			$(jProcessId).removeAttr('method').removeAttr('enctype');
			
			var processType = $(jProcessTypeId).val();
			if(processType=='1'){//查询日志
				$(jProcessDivId).html(this.getHiveForm(index));
				getMultiSid(jplatId,"#sid_"+index,false,true);
				getMultiColumns($("#table_"+index).val(),"#column_"+index,"hive",false,{callback:_this.multiCheckCallBack,processId:index});
				//table change
				$('#table_'+index).change(function(){
					_this.changeTable(index,true);
				});
				$(jProcessId).find("[name=distinctType]").change(function(){
					_this.changeDistinctType(index,this);
				});
				
				initDatetimepicker();
				$("#table_"+index).select2();
			}else if(processType=='2'){//用户基本信息
				$(jProcessDivId).html(this.getHbaseForm(index));
				getSid($(jplatId).val(),"#sid_"+index);
				initDatetimepicker();
				$('[data-toggle="tooltip"]').tooltip();
			}else if(processType=='3'){
				$(jProcessId).attr('method','post').attr('enctype','multipart/form-data');
				$(jProcessId).find("[name=setParams]").hide();
				$(jProcessDivId).html(this.getUploadForm(index));
				$("#upload_"+index).click(function(){
					_this.upload(this,index);
				});
			}else if(processType=='4'){
				$(jProcessDivId).html(this.getHqlForm(index));
			}else{
				$(jProcessDivId).html(this.getOtherForm(index));
			}
	     },
	    getHiveForm:function(index){
	    	var form ='<div class="myrow">';
			form +=	'站点:<select class="multiselect" multiple="multiple" name="sid" id="sid_'+index+'"></select>'+
					'<input type="checkbox" style="margin:2px 2px 3px 5px;;vertical-align:middle;" onclick="checkSite(0,this,'+index+')" autocomplete="off">'+
					'<label>PC</label>'+
					'<input type="checkbox" style="margin:2px 2px 3px 5px;;vertical-align:middle;" onclick="checkSite(1,this,'+index+')" autocomplete="off">'+
					'<label>移动</label>&nbsp;';
			form +=	'<div class="form-group"><input type="text" name="stime" class="form-control time" placeholder="开始时间" autocomplete="off"></div>&nbsp;'+
					  	'<div class="form-group"><input type="text" name="etime" class="form-control time" placeholder="结束时间" autocomplete="off"></div>&nbsp;&nbsp;';
		 	form +=	'表名:<select name="tableName" id="table_'+index+'" class="form-control">';
						 	var tables = getTables("hive",false);
						 	$.each(tables, function(index, value) {
						 		form += "<option title='"+value.desc+"' value=" + value.name + ">" + value.desc+"</option>";
							});
			form +=	'</select>&nbsp;&nbsp;'+
						'字段:<select class="multiselect" multiple="multiple" name="column" id="column_'+index+'"></select></div>';
			form +='<div class="myrow">去重:'+
						'<select class="form-control" name="distinctType">'+
							'<option value="-1">不去重</option>'+
							'<option value="1" column="_uid" title="_uid">根据用户ID去重</option>'+
							'<option value="2" column="_uid,tm" title="_uid,日期">根据用户ID和日期去重</option>'+
							'<option value="3">根据时间获取用户最近一条记录</option><option value="4">根据时间获取用户最早一条记录</option>'+
						'</select><span class="getCount"></span></div>';
			form +='<div class="myrow tableParam"></div>';
		
			form +='<div style="display: none;" name="dependOn" class="param">依赖流程</div>'+
					 '<div style="display: none;" name="linkParam" class="param">关联条件</div>'+
					 '<div style="display: none;" name="filterParam" class="param">过滤条件</div>'+
					 '<div style="display: none;" name="formatParam" class="param">格式化</div>';
					 
			return form;
	     },
	    getHbaseForm:function(index){
	    	var form ='<div class="myrow">站点:<select name="sid" id="sid_'+index+'" class="form-control"></select>&nbsp;';
	    	form +='<div class="myrow">';
			form +='<input type="hidden" id="table_'+index+'" name="tableName" value="user_ucuser"></div>';
	    	if(ucuser){
	    		$(ucuser).each(function(index){
					form += '<label class="checkbox-inline checkbox-label '+(index==0?'first':'')+'" title="'+ this.valueName+'('+this.value+')">'+
					  		  '<input type="checkbox" name="column" titleName="'+this.valueName+'" title="'+ this.valueName+'('+this.value+')" value="'+this.value+'"> '+this.show+'</label>';
	    		});
	    	};
			form +="</div>";
			form +='<div style="display: none;" name="dependOn" class="param">依赖流程</div>'+
					 '<div style="display: none;" name="linkParam" class="param">关联条件</div>'+
					 '<div style="display: none;" name="filterParam" class="param">过滤条件</div>'+
					 '<div style="display: none;" name="formatParam" class="param">格式化</div>';
			return form;
	     },
	    getUploadForm:function(index){
	    	var form ='<div class="myrow"><div class="form-group">'+
				'<input type="file" id="file_'+index+'" name="file" autocomplete="off">'+
			'</div>'+
			'<button type="button" class="btn btn-primary" id="upload_'+index+'">点击上传</button>&nbsp;'+
			'<input type="hidden" name="fileName" autocomplete="off">&nbsp;'+
			'<input type="hidden" name="columns" autocomplete="off">'+
			'<input type="hidden" name="getFileColumn" value="1">'+
			'<div class="form-group">'+
				'文件的列别名(可更改):<input type="text" name="columnNames" id="columnName_'+index+'" style="width: 400px;" class="form-control" placeholder="文件的列别名" autocomplete="off">'+
			'</div></div>';
			return form;
	     },
	    getHqlForm:function(index){
	    	var form ='<div class="myrow"><div class="form-group">'+
				'<label class="sr-only" for="hql">hql</label>'+
					'<textarea class="form-control" style="width: 800px;" rows="3" name="hql" placeholder="请输入正确的hql,为了提高查询速度,时间范围尽量控制在最短"></textarea>'+
				'</div></div>';
			form += '<input type="hidden" name="columns" autocomplete="off">'+
						'<div class="myrow" class="form-group">'+
							'文件的列别名(可更改):<input type="text" name="columnNames" id="columnName_'+index+'" style="width: 400px;" class="form-control" placeholder="文件的列别名" autocomplete="off">'+
						'</div>';
			form +='<div style="display: none;" name="dependOn" class="param">依赖流程</div>';
			return form;
	     },
	     getOtherForm:function(index){
		    	var form ='<div class="myrow"><div class="form-group">'+
						'<textarea class="form-control" style="width: 800px;" rows="3" name="operator" placeholder="此功能了解mf业务再使用"></textarea>'+
					'</div></div>';
				form += '<div class="myrow" class="form-group">'+
						'列别名:<input type="text" name="columnNames" id="columnName_'+index+'" style="width: 400px;" class="form-control" placeholder="列别名" autocomplete="off">'+
					'</div>';
				form +='<div style="display: none;" name="dependOn" class="param">依赖流程</div>';
				return form;
		     },
	    setParams:function(index){
	    	var processType = $("#processType_"+index).val();
	    	var _this=this;
			var html = 
			    '<div class="modal-dialog modal-lg"><div class="modal-content">'+
			      '<div class="modal-header">'+
			        '<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>'+
			        '<h4 class="modal-title">设置参数</h4>'+
			      '</div>'+
			      '<div class="modal-body">'+
			      '<form class="form-inline">';
		  	html+='<div class="myrow"><span class="mylabel">参数类型:</span>';
		  	var select = '<select id="paramType" class="form-control">';
		  
		  	var haveParam = false;
		  	if(processType=='1'){
		  		haveParam=true;
		  		select+='<option value="2">过滤条件</option>';
			}
		  	if(processType=='2'){
		  		haveParam=true;
		  		select+='<option value="2">过滤条件</option>';
		  		select+='<option value="3">数据格式化</option>';
		  	}
		  	if($(".task").length>1){
			  	haveParam=true;
			  	select+='<option value="1">依赖流程</option>';
		  	}
		  	
		  	html+=(haveParam?select:'没有可参数可设');
		  
		  	html+='</select></div>'+
		        '<div id="filterParam" class="param" style="display:none;"></div>'+
		        '<div id="formatParam" class="param" style="display:none;"></div>'+
		        '<div id="dependParam" class="param" style="display:none;"></div>'+
		        '</form>'+
		      '</div>'+
		      '<div class="modal-footer">'+
		        '<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>';
		        	if(haveParam){
		        		html+='<button type="button" class="btn btn-primary" id="addParams">提交</button>';
		        	}
		     html+='</div>'+
		  '</div></div>';
		  
			this.createModal(html);
			$("#paramType").change(function(){
				_this.paramTypeChange(this,index,processType);
			});
			this.paramTypeChange($("#paramType"),index,processType);
		},
		paramTypeChange:function(o,index,processType){
			var tableName = $("#table_"+index).val(),_this=this;
			if($(o).val()=='2'){//过滤
				var options =getOptionHtml(tableName,processType==1?"hive":"hbase");
				var form = '<span class="mylabel">条件:</span><select id="filter_c" class="form-control" style="width: 200px;">'+options+"</select>";
				form += '<select id="filter_op" class="form-control">'+
								'<option value="0">等于</option>'+
								'<option value="1">小于</option>'+
								'<option value="2">小于或等于</option>'+
								'<option value="3">不等于</option>'+
								'<option value="4">大于</option>'+
								'<option value="5">大于或等于</option>'+
								(processType==1?'<option value="6">in</option><option value="7">like</option>':'');
				form += '</select>';
				form += '<input id="filter_v" type="text" class="form-control" style="width: 80px;">&nbsp;&nbsp;';
				form += (processType==1?'(in条件使用英文分号;分开各个值)':'');
				
				$("#filterParam").html(form);
				$("#filterParam").show();
				$("#formatParam").hide();
				$("#dependParam").hide();
				$("#filter_c").select2();
			}else if($(o).val()=='3'){//格式化
				//var options =getOptionHtml(tableName,"hbase");
				var options = '';
				var data = _this.getSelectColumns('process_'+index);
				if(data && data.selectValues && data.selectValues.length>0){
					var values = data.selectValues.split(',');
					var titles = data.selectTitles.split(',');
					for(var i=0;i<values.length;i++){
						options += '<option value="'+values[i]+'" title="'+titles[i]+'">'+titles[i]+'('+values[i]+')</option>';
					}
				}
			
				var form = '<span class="mylabel">字段:</span><select class="form-control" id="format_c" style="width: 200px;">'+options+"</select>";
				form += '<div class="myrow">'+
					'<span class="mylabel">操作类型:</span>'+
					'<select class="form-control" id="format_t">'+
						'<option value="-1">请选择</option>'+
						'<option value="format">日期</option>'+
						'<option value="replace">字符串替换</option>'+
						'<option value="substr">字符串截取</option>'+
						'<option value="ipformat">IP格式化</option>'+
					'</select></div>';
				form += '<div id="formatOper"><div>';	
				$("#formatParam").html(form);
				$("#formatParam").show();
				$("#filterParam").hide();
				$("#dependParam").hide();
				$("#format_t").change(function(){
					_this.formatTypeChange(this);
				});
				$("#format_c").select2();
			}else if($(o).val()=='1'){
				//获取现有的所有流程
				var option="";
				$(".task").each(function() {
					if($(this).attr("id")!='process_'+index){
						option+="<option value="+$(this).attr("id")+" title='"+$(this).attr("pname")+"'>" + $(this).attr("pname") + "</option>";
					}
				});
				var form = '<div class="myrow"><span class="mylabel">选择流程:</span><select class="form-control" id="dependProcess">'+option+"</select></div>";
				form += '<div id="link_p"></div>';
				
				$("#dependParam").html(form);
				
				$("#dependParam").show();
				$("#filterParam").hide();
				$("#formatParam").hide();
				
				$("#dependProcess").change(function(){
					_this.dependChange(index);
				});
				this.dependChange(index);
			}
			$("#addParams").unbind('click').click(function(){
				_this.addParams(index);
			});
		},
		dependChange:function(index){
			var processType = $("#process_"+index).find("[name=processType]").val();
			if(processType==4 || processType==5 || processType==6) return;
			
			/*
			if(processType==4){
				var form = '<div class="myrow"><span class="mylabel">指定表名:</span>';
				form +='<input type="text" autocomplete="off" name="tableName" id="tableName" class="form-control">';
				form += "</div>";
				$("#link_p").html(form);
				return;
			}*/
			
			var form = '<div class="myrow"><span class="mylabel">关联条件:</span>';
			
			var data = this.getSelectColumns($("#dependProcess").val());
			if(!data || data.length==0 || !data.selectValues || data.selectValues.length ==0){
				form+='<span style="color:red">依赖流程必须要选择输出的字段</span>';
				form += "</div>";
				$("#link_p").html(form);
				return;
			}
			
			form +='<select name="dep_column" id="dep_column" class="form-control">';
			form += this.getOutputColumn(processType,"process_"+index);
			form += "</select>&nbsp;";
			
			if(processType==2){
				form += '<select id="dep_op" class="form-control"><option value="0">等于</option></select>&nbsp;';
			}else{
				form += '<select id="dep_op" class="form-control"><option value="0">等于</option><option value="1">小于</option><option value="2">小于或等于</option>'+
				'<option value="3">不等于</option><option value="4">大于</option><option value="5">大于或等于</option></select>&nbsp;';
			}
			
			form +='<select name="dep_t_column" id="dep_t_column" class="form-control">';
			var values = data.selectValues.split(',');
			var titles = data.selectTitles.split(',');
			for(var i=0;i<values.length;i++){
				form += '<option value="'+values[i]+'" title="'+titles[i]+'">'+titles[i]+'('+values[i]+')</option>';
			}
			form += "</select>(所依赖流程的查询结果)";
	
			form += "</div>";
			$("#link_p").html(form);
		},
		getOutputColumn:function(processType,processId){
			var form='';
			if(processType==1){
				var tableName = $("#"+processId).find("[name=tableName]").val();
				form += getOptionHtml(tableName,"hive",true);
			}else if(processType==2){
				if(ucuser){
					$(ucuser).each(function(){
						if(this.value=='_uid' || this.value=='_plat'){
							form += '<option title="'+this.valueName+'('+this.value+')" value=' + this.value + '>' + this.valueName+'('+this.value+')</option>';
						}
					});
				};
			}
			return form;
		},
		changeTable:function(index,isRebuild){
			var tableName = $("#table_"+index).val();
			if(tableName=='user_order'){
				$("#process_"+index).find(".tableParam").load('task/user_order.htm?index='+index);
			}else if(tableName=='user_gambling'){
				$("#process_"+index).find(".tableParam").load('task/user_gambling.htm?index='+index);
			}else if(tableName=='user_login'){
				$("#process_"+index).find(".tableParam").load('task/user_login.htm?index='+index);
			}else{
				$("#process_"+index).find(".tableParam").html('');
			}
			getMultiColumns(tableName,"#column_"+index,"hive",isRebuild,{callback:this.multiCheckCallBack,processId:index});
			this.changeDistinctType(index,$("#process_"+index).find("[name=distinctType]"),true);
		},
		getSelectColumns:function(processId,addTableParamFlag){//返回以,号分开的选中字段和列名
			var data={};
			var pObj = $("#"+processId),_this=this;
			var processType = pObj.find("[name=processType]").val();
			if(processType==1){
				//判断是否有去重
				var distinctType = pObj.find("[name=distinctType]");
				if(distinctType.val()==1){
					var selectTitles = distinctType.find("option:selected").attr('title');
					var selectValues = distinctType.find("option:selected").attr('column');
					
					if(pObj.find("[name=getCount]")[0].checked && !addTableParamFlag){
						selectTitles += ","+(pObj.find("[name=getCount]")).attr('title');
						selectValues += ",getCount";
					}
					
					var index = processId.substring(processId.indexOf("_")+1);
					if(!addTableParamFlag && $("#tableParam_"+index).length>0 && !isEmpty($("#tableParam_"+index).attr("column"))){
						var tmpTitle = $("#tableParam_"+index).attr("title");
						selectTitles += (","+_this.getTableParamTV(tmpTitle,0));
						selectValues += (","+_this.getTableParamTV(tmpTitle,1));
					}
					
					data.selectTitles= selectTitles;
					data.selectValues= selectValues;
				}else if(distinctType.val()==2){
					data.selectTitles= distinctType.find("option:selected").attr('title');
					data.selectValues= distinctType.find("option:selected").attr('column');
				}else{
					data.selectTitles= pObj.find("[name=column]").attr('selecttitles');
					data.selectValues= pObj.find("[name=column]").attr('selectvalues');
				}
			}else if(processType==2){
				if(pObj.find("[name=column]:checked").length>0){
					var first = true,column='',columnName='';
					var formatParam = _this.getLabelParam(pObj,'formatParam');
					pObj.find("[name=column]").each(function(index,data){
						if($(this).is(":checked")){
							var _column = $(this).val(),_columnName=$(this).attr("titleName");
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
			}else if(processType==3 || processType==4|| processType==5|| processType==6){
				var columnNames = pObj.find("[name=columnNames]").val();
				if(!isEmpty(columnNames)){
					data.selectTitles= columnNames;
					data.selectValues= (processType==3 || processType==4)?_this.getDefaultColumn(columnNames):columnNames;
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
		multiCheckCallBack:function(index){
			this.changeDistinctType(index,$("#process_"+index).find("[name=distinctType]"),true);
		},
		changeDistinctType:function(index,o,notAlert){
			var selectValue = $("#process_"+index).find("[name=column]").val();
			var uid=false,tm=false,_tm=false;
			if(selectValue && selectValue.length>=1){
				for(var i=0;i<selectValue.length;i++){
					if(selectValue[i]=='_uid') uid=true;
					if(selectValue[i]=='tm') tm=true;
					if(selectValue[i]=='_tm') _tm=true;
				}
			}
			
			if($(o).val()==1 && !uid){
				$(o).val(-1);this.getOtherParam(o,index);
				if(!notAlert) alert("根据用户ID去重必须选择_uid");
				return false;
			}
			if($(o).val()==2 && (!uid || !tm)){
				$(o).val(-1);this.getOtherParam(o,index);
				if(!notAlert) alert("根据用户ID和日期去重必须选择_uid,tm");
				return false;
			}
			
			if(($(o).val()==3 || $(o).val()==4) && (!uid || !_tm)){
				$(o).val(-1);this.getOtherParam(o,index);
				if(!notAlert) alert("必须选择_uid,_tm");
				return false;
			}
			this.getOtherParam(o,index);
		},
		checkDistinctType:function(index,value){
			var selectValue = $("#process_"+index).find("[name=column]").val();
			var uid=false,tm=false,_tm=false;
			if(selectValue && selectValue.length>=1){
				for(var i=0;i<selectValue.length;i++){
					if(selectValue[i]=='_uid') uid=true;
					if(selectValue[i]=='tm') tm=true;
					if(selectValue[i]=='_tm') _tm=true;
				}
			}
			
			if(value==1 && !uid){
				return 1;
			}
			if(value==2 && (!uid || !tm)){
				return 2;
			}
			
			if((value==3 || value==4) && (!uid || !_tm)){
				return 3;
			}
			return 0;
		},
		getOtherParam:function(o,index){
			var tableName = $("#process_"+index).find("[name=tableName]").val();
			if($(o).val()==1 || $(o).val()==2){
				$(o).parent().find(".getCount").html('&nbsp;&nbsp;<label class="checkbox-inline"><input type="checkbox" name="getCount" value="1" title="统计'+tableName+'记录数">统计'+tableName+'记录数');
			}else{
				$(o).parent().find(".getCount").html('');
			}
			if(tableName=='user_order'){
				getOrderTableParam(index);
			}else if(tableName=='user_gambling'){
				getUserGamblingTableParam(index);
			}else if(tableName=='user_login'){
				getLoginTableParam(index);
			}
		},
		addParams:function(index){
			var value="",lable="";
			var dom;
			if($("#paramType").val()==2){
				if(isEmpty($("#filter_v").val())){
					alert("比较的值不能为空");
					return;
				}
				
				value=$("#filter_c").val()+'#'+$("#filter_op").val()+'#'+$("#filter_v").val();
				lable=$("#filter_c").val()+op($("#filter_op").val())+$("#filter_v").val();
				dom = $("#process_"+index).find('[name="filterParam"]');
			}else if($("#paramType").val()==3){
				var type = $("#format_t").val();
				if(isEmpty(type) || type==-1){
					alert("请选择格式化字段");
					return;
				}
				
				if(type=='format'){
					value=type+"#"+$("#format_c").val()+"#"+$("#format_date").val();
					lable=value;
				}else if(type=='replace'){
					value=type+"#"+$("#format_c").val()+"#"+$("#format_rep_val").val()+'#'+$("#format_rep_index").val();
					lable=value;
				}else if(type=='substr'){
					value=type+"#"+$("#format_c").val()+"#"+$("#format_sub_val").val()+'#'+$("#format_sub_index").val();
					lable=value;
				}else if(type=='ipformat'){
					lable=type+"#"+$("#format_c").val()+'#'+($("#format_ip_outori").is(":checked")?"输出原始IP":"不输出原始IP");
					value=type+"#"+$("#format_c").val()+'#'+($("#format_ip_outori").is(":checked")?1:0);
				}
				dom = $("#process_"+index).find('[name="formatParam"]');
			}else if($("#paramType").val()==1){
				var processType = $("#process_"+index).find("[name=processType]").val();
				lable=$("#dependProcess").find('option:selected').text();
				value = $("#dependProcess").val();
				dom = $("#process_"+index).find('[name="dependOn"]');
				
				if(processType==4 || processType==10){
					_value = value;
					
					value = _value+"___temp__process__"+_value;
					lable = lable+"(并指定表名为:temp__process__"+_value+")";
					
					$("#process_"+index).find('[name="dependOn"]').find('.labelColumn').each(function(){
						if($(this).attr('process')==$("#dependProcess").val()){
							$(this).remove();
						}
					});
					
					if(dom && dom.is(":hidden")){
						dom.show();
					}
					dom.append('<div class="labelColumn" process="'+$("#dependProcess").val()+'"><p><span class="columnValue" value="'+value+'">'+lable+'</span> <span class="labelClose" onclick="labelClose(this)">×</span></p></div>');
					return;
				}
				if(processType!=4 && processType!=5 && processType!=6){
					var _value=$("#dep_column").val()+'#'+$("#dep_op").val()+'#'+$("#dep_t_column").val();
					var _lable=$("#dep_column").val()+op($("#dep_op").val())+$("#dep_t_column").val();
					var _dom = $("#process_"+index).find('[name="linkParam"]');
					if(isEmpty($("#dep_column").val()) || isEmpty($("#dep_t_column").val())){
						alert("依赖其他流程必须有关联条件");
						return;
					}
					
					var temp = '<p><span class="columnValue" value="'+value+'">'+lable+'</span> <span class="labelClose" onclick="labelClose(this)">×</span></p>';
					if(dom.find('.labelColumn').html()!=temp){
						_dom.find('.labelColumn').remove();
					}
					
					_dom.append('<div class="labelColumn"><p><span class="columnValue" value="'+_value+'">'+_lable+'</span> <span class="labelClose" onclick="labelClose(this)">×</span></p></div>');
					_dom.show();
				}
				dom.find('.labelColumn').remove();
			}
			
			if(dom && dom.is(":hidden")){
				dom.show();
			}
			dom.append('<div class="labelColumn"><p><span class="columnValue" value="'+value+'">'+lable+'</span> <span class="labelClose" onclick="labelClose(this)">×</span></p></div>');
		},
		formatTypeChange:function (o){
			var v = $(o).val();
			
			var form='<div class="myrow">';
			if(v=='format'){
				form+='<span class="mylabel">格式:</span>';
				form+='<select id="format_date" class="form-control"><option value="yyyy-MM-dd">yyyy-MM-dd</option><option value="yyyy-MM-dd HH:mm:ss">yyyy-MM-dd HH:mm:ss</option></select>';
			}else if(v=='replace'){
				form+='<span class="mylabel">操作:</span>';
				form+='<input type="text" class="form-control" id="format_rep_index" style="width: 80px;">位(负数表示从后开始替换)为<input id="format_rep_val" class="form-control" type="text" style="width: 80px;">字符';
			}else if(v=='substr'){
				form+='<span class="mylabel">操作:</span>';
				form+='按<input id="format_sub_val" class="form-control" type="text" style="width: 80px;">';
				form+='<select id="format_sub_index" class="form-control"><option value="1">从前截取</option><option value="-1">从后截取</option></select>';
			}else if(v='ipFormat'){
				form+='<span>输出原始IP:</span>';
				form+='&nbsp;&nbsp;<input type="checkbox" id="format_ip_outori" value="1">';
			}
			form+='</div>';
			
			$("#formatOper").html(form);
		},
		setOutType:function(v){//合并流程
			var _this=this;
			if(v.checked){
				var form = '';
				//获取所有文件的输出列
				$("#processInfo").find(".task").each(function(){
					var processId= $(this).attr('id');
					
					var data = _this.getSelectColumns(processId);
					if(data.selectValues && data.selectValues.length>0){
						form +='<div class="myrow processColumn" id="outputTypeColumn_'+processId+'" processId="'+processId+'">'+$(this).attr('pname')+'<br/>';
						
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
				        '<h4 class="modal-title">输出文件内容设置</h4>'+
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
			  	
			  	$("#addOutputTypt").unbind('click').click(function(){
			  		_this.addOutputTypt();
			  	});
				$(".checkColumn").unbind('click').click(function(){
			  		_this.checkColumn(this);
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
					var first = true,column='',columnName='',pname = $('#'+processId).attr('pname');
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
				 
				l+=(first?'':'=')+$('#'+processId).attr('pname')+'.'+s.text();
				v+=(first?'':'=')+processId+'.'+$(this).val();
				first=false;
			});
			html += '<div class="myrow outputTypeLink">合并条件:<div class="labelColumn"><p><span value="'+v+'" class="columnValue">'+l+'</span><span onclick="labelClose(this)" class="labelClose">×</span></p></div>';
			
			$("#outTypeParam").html(html);
		},
		checkColumn:function(o){
			var processColumn = $(o).parent().parent();
			var processId = processColumn.attr('processId');

			if(o.checked && $("#outputTypeLink_"+processId).length<=0){
				var data = this.getSelectColumns(processId);
				var values = data.selectValues.split(',');
				var titles = data.selectTitles.split(',');
				
				var form = '<span id="outputTypeLink_'+processId+'">'+$('#'+processId).attr('pname')+':<select class="form-control outputLink" processId="'+processId+'" name="outputLink">';
				
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
		submit:function(){
			var _this = this;
			if(isEmpty($("#taskName").val())){
				alert("任务名称不能为空");
				return;
			}
			
			if($("#processInfo").find(".task").length<=0){
				alert("至少要添加一个导出流程才能提交");
				return;
			}		
			
			var taskInfo ={
				taskName:$("#taskName").val(),email:$("#email").val(),plat:$(jplatId).val(),svid:$(jplatId).attr('svid')
			};
			
			//获取输出文件的方式
			if(document.getElementById("outType").checked){
				var outputColumn = '',first=true;
				$("#outTypeParam").find(".outputTypeColumn").each(function(){
					outputColumn += (first?"":"|")+$(this).attr('processid')+":"+$(this).find(".columnValue").attr("value");
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
			
			var failure=false;
			var warning=false;
			//获取流程
			var processList = [];
			$("#processInfo").find(".task").each(function(){
				var processType = $(this).find("[name=processType]").val();
				var processId=$(this).attr("id");
				var processInfo = {processId:processId};
				
				var data = _this.getSelectColumns($(this).attr("id"),true);
				
				if(processType==1){
					var index = processId.substring(processId.indexOf("_")+1);
					var distinctType = $(this).find("[name=distinctType]").val();
					
					var checkResult = _this.checkDistinctType(index,distinctType);
					if(checkResult==1){
						failure=true;
						alert("根据用户ID去重必须选择_uid");
						return false;
					}else if(checkResult==2){
						failure=true;
						alert("根据用户ID和日期去重必须选择_uid,tm");
						return false;
					}else if(checkResult==3){
						failure=true;
						alert("获取最早一条记录或者最后一第记录必须选择_uid,_tm");
						return false;
					}
					
					var selectValues = data.selectValues;
					var startTime = $(this).find("[name=stime]").val();
					var endTime = $(this).find("[name=etime]").val();
					if (isEmpty(selectValues)) {
						failure=true;
		 				alert($(this).attr("pname")+"要选择查询的字段");
						return false;
					}
					if (isEmpty(startTime)|| isEmpty(endTime)) {
						failure=true;
						alert($(this).attr("pname")+"要选择开始与结束时间");
						return false;
					}
					
					var tableName = $(this).find("[name=tableName]").val();
					processInfo.processType=1;
					processInfo.plat= $(jplatId).val();
					processInfo.svid= $(jplatId).find("option:selected").attr('svid');
					processInfo.sid= $(this).find("[name=sid]").attr('selectvalues');
					processInfo.startTime= startTime;
					processInfo.endTime= endTime;
					processInfo.tableName = tableName;
					processInfo.columns=selectValues;
					processInfo.columnNames=data.selectTitles;
					//过滤条件
					processInfo.filter=_this.getLabelParam($(this),'filterParam');
					processInfo.dependOn=_this.getLabelParam($(this),'dependOn');
					processInfo.linkParam=_this.getLabelParam($(this),'linkParam');
					processInfo.distinctType=distinctType;
					
					if($(this).find("[name=getCount]").length>0 && $(this).find("[name=getCount]")[0].checked){
						processInfo.getCount=$(this).find("[name=getCount]").val();
						processInfo.getCountTitle=$(this).find("[name=getCount]").attr('title');
					}
					
					if($("#tableParam_"+index).length>0 && !isEmpty($("#tableParam_"+index).attr("column"))){
						processInfo.tableColumn=$("#tableParam_"+index).attr("column");
						processInfo.tableTitle=$("#tableParam_"+index).attr("title");
					}
				}else if(processType==2){
					processInfo.processType=2;
					processInfo.tableName = $(this).find("[name=tableName]").val();
					processInfo.plat= $(jplatId).val();
					processInfo.sid= $(this).find("[name=sid]").val();
					
					var column = data.selectValues,columnName=data.selectTitles;
					if(column.length==0){
						failure=true;
		 				alert($(this).attr("pname")+"要选择查询的字段");
						return;
					}
					processInfo.columns=column;
					processInfo.columnNames=columnName;
					processInfo.filter=_this.getLabelParam($(this),'filterParam');
					
					var hbaseDependOn = _this.getLabelParam($(this),'dependOn');
					if(isEmpty(hbaseDependOn)){
						warning=true;
					}
					
					processInfo.dependOn=hbaseDependOn;
					processInfo.linkParam=_this.getLabelParam($(this),'linkParam');
					processInfo.formatParam=_this.getLabelParam($(this),'formatParam');
				}else if(processType==3){
					if(isEmpty($(this).find("[name=file]").val())){
						alert("请点击上传文件按钮,并上传成功后再提交任务!");
						failure=true;
						return false;
					}
					if(isEmpty(data.selectTitles)){
						alert("文件内容对应的列别名不能为空");
						failure=true;
						return false;
					}
					processInfo.processType=3;
					processInfo.fileName= $(this).find("[name=fileName]").val();
					var columnNames = data.selectTitles;
					processInfo.columnNames = columnNames;
					processInfo.columns = data.selectValues;
				}else if(processType==4){
					if(isEmpty($(this).find("[name=hql]").val())){
						alert("hql语句不能为空!");
						failure=true;
						return false;
					}
					if(isEmpty(data.selectTitles)){
						alert("ｈql对应的列别名不能为空");
						failure=true;
						return false;
					}
					processInfo.processType=4;
					processInfo.hql= $(this).find("[name=hql]").val();
					processInfo.columnNames = data.selectTitles;
					processInfo.columns = data.selectValues;
					
					var dependOn = _this.getLabelParam($(this),'dependOn');
					//var dependProcess = dependOn.substring(0,dependOn.indexOf("___"));
					//var dependTable = dependOn.substring(dependOn.indexOf("___")+3);
					processInfo.depend=dependOn;
					//processInfo.dependOn=dependProcess;
				}else if(processType==5 || processType==6){
					var columnNames = $(this).find("[name=columnNames]").val();
					if(isEmpty($(this).find("[name=operator]").val())){
						alert("operator语句不能为空!");
						failure=true;
						return false;
					}
					processInfo.processType=processType;
					processInfo.operator= $(this).find("[name=operator]").val();
					processInfo.columnNames = columnNames;
					processInfo.columns = columnNames;
					processInfo.dependOn=_this.getLabelParam($(this),'dependOn');
				}else if(processType==10){
					var columnNames = $(this).find("[name=columnNames]").val();
					if(isEmpty($(this).find("[name=operator]").val())){
						alert("operator语句不能为空!");
						failure=true;
						return false;
					}
					processInfo.processType=processType;
					processInfo.operator= $(this).find("[name=operator]").val();
					processInfo.columnNames = columnNames;
					processInfo.columns = columnNames;
					processInfo.dependOn=_this.getLabelParam($(this),'dependOn');
				}
				processList.push(processInfo);
			});
			if(failure) return ;
			if(warning){
				if(!confirm("确定查询用户信息的流程不依赖其他流程吗(一般情况下都要设置依赖)?")){
					return;
				}
			}
			
			taskInfo.processList=processList;
			
			$.post("task/process/addTask", {taskInfo:JSON.stringify(taskInfo)}, function(data, textStatus) {
				if(data.ok==0){
					alert(data.msg);
	    		}else{
					alert("添加成功!");
	    		}
			}, "json");	
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
		upload:function(o,index){
			var fileVal=$("#file_"+index).val();
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
			          			$("#process_"+index).find("[name=fileName]").val(msg.other.fileName);
			          			$("#process_"+index).find("[name=columns]").val(msg.other.fileColumn);
			          			$("#process_"+index).find("[name=columnNames]").val(msg.other.fileColumn);
			          			$("#process_"+index).find("[name=upload-sucess]").remove();
			          			$(o).before('<span class="glyphicon glyphicon-ok" name="upload-sucess" style="color: #3c763d" title="上传成功">上传成功</span>&nbsp;&nbsp;&nbsp;');
			          		}else{
			          			alert(msg.msg);
			          			}
			           		},
			           	error:function(msg){
			           		alert(msg);
			           		}
			      	};
					$("#process_"+index).ajaxSubmit(options);  
		       	return false;  
				});  
			}else{
				alert("请选择要上传的文件");
			}
		},
		createModal:function(html){
			$(".myModal").html("");
			$(".myModal").html(html);
	      $(".myModal").modal({
	    	  backdrop:"static"
	      });
		}
	};
	
	function checkSite(ismobile,obj,index){
		if($(obj).is(":checked")){
			$('#sid_'+index+' option[ismobile="'+ismobile+'"]').attr("selected","selected");
			$('#sid_'+index).html($('#sid_'+index).html());
		}else{
			$('#sid_'+index+' option[ismobile="'+ismobile+'"]').attr("selected",false);
		}
		$('#sid_'+index).mymultiselect("refresh");
		$('#sid_'+index).multiselectfilter('updateCache');
		$('#sid_'+index).mymultiselect("close");
	}
		
	function labelClose(o) {
		$(o).parent().parent().remove();
	}
	</script>
</body>
</html>