<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/tld/privilege" prefix="privilege"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%response.setContentType("text/javascript;charset=utf-8");%>

var dir='';
var taskJs = document.getElementById('taskJs');
if(taskJs){
	var data = document.getElementById('taskJs').getAttribute('data');
	if(data && data != null && $.trim(data) != '') {
		dir=data;
	}
}

$(function(){
	var table = $("#example").DataTable({
     "bFilter": true,
     "pagingType" : "full_numbers",
     "bDestroy" : true,
     "bProcessing" : false,
     "sAjaxSource" : "task/list/"+((dir=='mylist' || dir=='myautolist')?'getUserTaskData':'getAllTaskData')+'.htm'+((dir=='autolist' || dir=='myautolist')?'?t=1':''),
     "bServerSide" : true,
     "ordering": false,
      "sServerMethod": "POST",
     "columns" : [
			{
            "className":'details-control',
            "orderable":false,
            "data":null,
            "defaultContent": ''
        	},
			{ "data": "id"},
			{ "data": "taskName",
				"render":function(data, type, row){
					if(data.length>20){
						data = "<span title='"+data+"'>"+ data.substring(0,20)+"</span>";
					}
					return data;
				}
			},
			{ "data": "userName"},
			{ "data": "status",
			  "render":function(data, type, row){
					if(data==0){
						data = "排队中"+(row.queueWaitCount>0?"[前面还有<span style='color: red;'>"+row.queueWaitCount+"</span>个]":"");
					}else if(data==1){
						data = "执行中...";
					}else if(data==2){
						data = "出现错误";
					}else if(data==3){
						data = "执行结束";
					}else if(data==4){
						data = "添加出错";
					}else if(data==5){
						data = "<span style='color: red;'>待发送审批申请</span>";
					}else if(data==8){
						data = "<span style='color: blue;'>已发送审批申请，待审核</span>";
					}else if(data==7){
						data = "强制停止";
					}
					return data;
			   }
			},
			{ "data": "logInfo",
				"render":function(data, type, row){
					if(data.length>20){
						data = "<span title='"+data+"'>"+ data.substring(0,20)+"</span>";
					}
					return data;
				}
			},
			{ "data": "path",
				"render":function(data, type, row){
					if(data && data.length>0){
						return "<a href='task/list/down.htm?tid="+row.id+"' target='_blank' >下载</a>&nbsp;&nbsp;|&nbsp;&nbsp;"+
								"<a href='task/list/preview.htm?tid="+row.id+"' target='_blank' >预览</a>";
					}
					return "";
				}
			},
			{ "data": "fileSizeFormat" },
			{ "data": "startTime" },
			{ "data": "endTime" },
			{ "data": null,orderable: false,render:function(data, type, row){
				var html = '<div class="btn-group btn-group-xs">' +
					'<button type="button" class="btn btn-primary btn-sm dropdown-toggle" data-toggle="dropdown"> 操作 <span class="caret"></span></button>'+
					'<ul class="dropdown-menu" role="menu" style="min-width: 10px;">'+
					((row.status==5||row.status==8)?'<li><a href="javascript:void(0);" onclick="sendEmailDialog('+row.id+')">申请审批</a></li>':'');
				if(dir=='mylist' || dir=='myautolist'){
					html += '<li><a href="javascript:void(0);" onclick="showTask('+row.id+')">编辑任务</a></li>';
					if(row.status!=0&&row.status!=1&&row.status!=5&&row.status!=8){
						html +='<li><a href="javascript:void(0);" onclick="_confirm(\'确定要重新执行此任务？\',\'task/process/userExecuteTask.htm?taskId='+row.id+'\')">立刻执行</a></li>';
					}
					if(row.status!=5 && row.status!=8){
						html +='<li><a href="javascript:void(0);" onclick="stopTask('+row.id+')">停止执行</a></li>';
					}
					html +='<li><a href="javascript:void(0);" onclick="_confirm(\'确定要删除此任务？\',\'task/process/deleteTask.htm?id='+row.id+'\')">删除任务</a></li>';
				}else if(dir=='list' || dir=='autolist'){
					if(row.status!=5 && row.status!=8){
						html +='<privilege:operate url="/task/process/startTask.htm"><li><a href="javascript:void(0);" onclick="_confirm(\'确定要重新执行此任务？\',\'task/process/startTask.htm?taskId='+row.id+'\')">立刻执行</a></li></privilege:operate>';
					}
					if(row.status!=5 && row.status!=8){
						html +='<privilege:operate url="/task/process/stopTask.htm"><li><a href="javascript:void(0);" onclick="stopTask('+row.id+')">停止执行</a></li></privilege:operate>';
					}
					html +='<privilege:operate url="/task/process/getTaskById.htm"><li><a href="javascript:void(0);" onclick="showTask('+row.id+')">编辑任务</a></li></privilege:operate>';
					if(row.status==5 ||row.status==8){
						html +='<privilege:operate url="/task/audit/passAudit.htm"><li><a href="javascript:void(0);" onclick="auditPass('+row.id+')">审批通过</a></li></privilege:operate>';
					}
					if(dir == 'list'){
						html +='<privilege:operate url="/task/auto/setTaskAutorun.htm"><li><a href="javascript:void(0);" onclick="_confirm(\'确定要设置为自动任务？\',\'/task/auto/setTaskAutorun.htm?taskId='+row.id+'\')">设为自动任务</a></li></privilege:operate>';
					}
					if(dir == 'autolist'){
						html +='<privilege:operate url="/task/auto/cancelTaskAutorun.htm"><li><a href="javascript:void(0);" onclick="_confirm(\'确定要取消自动任务？\',\'/task/auto/cancelTaskAutorun.htm?taskId='+row.id+'\')">取消自动任务</a></li></privilege:operate>';

						//编辑自动任务
						html +='<privilege:operate url="/log/autorunInfoServlet?action=setTaskAutorun"><li><a href="javascript:void(0);" onclick="getAutorunInfo('+row.id+');">任务设置</a></li></privilege:operate>';
					}
				}
				if(dir=='list'){
					html +='<li><a href="javascript:void(0);" onclick="_confirm(\'确定要优先执行此任务？\',\'task/process/moveQueue.htm?taskId='+row.id+'&type=1\')">优先执行</a></li>';
					html +='<li><a href="javascript:void(0);" onclick="_confirm(\'确定要延迟执行此任务？\',\'task/process/moveQueue.htm?taskId='+row.id+'&type=2\')">延迟执行</a></li>';
				}
				return html;
			}},
			{ "data": "queueWaitCount","visible": false}
         ],
       "oLanguage": commonData.oLanguage,
       "dom": 't<"col-sm-6"l><"col-sm-6"p>'
	});

	$('#example tbody').on('click', 'td.details-control', function () {
		var tr = $(this).closest('tr');
		var row = table.row( tr );
		var taskId = $(this).siblings().html();

		if ( row.child.isShown() ) {
			row.child.hide();
			tr.removeClass('shown');
		}else {
			var data = getPostJsonData2("task/process/findProcessByTaskId.htm",{taskId:taskId},true);
			if(data!=null) {
				var t = '<table class="table table-bordered" style="margin-bottom: 0px;">'+
				'<thead>'+
					'<tr>'+
						'<td width="5%">id</td>'+
						'<td width="10%">执行状态</td>'+
						'<td width="20%">异常信息</td>'+
						'<td width="20%">执行结果</td>'+
						'<td width="10%">文件大小</td>'+
						'<td width="15%">开始执行时间</td>'+
						'<td width="15%">结束执行时间</td>'+
						'<td width="5%">操作</td>'+
					'</tr>'+
				'</thead>'+
				'<tbody>';
				$.each(data, function(index, value) {
					var status='';
					if(value.status==0){
						status='排队中...';
					}else if(value.status==1){
						status='执行中...';
					}else if(value.status==2){
						status='出现错误';
					}else if(value.status==3){
						status='执行结束';
					}else if(value.status==4){
						status='添加出错';
					}else if(value.status==5){
						status='审批中...';
					}else if(value.status==7){
						status='强制停止';
					}

					var log='';
					if(!isEmpty(value.logInfo) && value.logInfo.length>20){
						log=value.logInfo.substring(0,20);
					}else{
						log=value.logInfo;
					}

					var downDom = '';
					if(!isEmpty(value.path)){
						downDom = '<a href="log/task?action=down&tid='+value.taskId+'&pid='+value.id+'" target="_blank" >点击下载</a>';
					}

					t += '<tr>'+
						'<td>'+value.id+'</td>'+
						'<td>'+status+'</td>'+
						'<td title=\''+value.logInfo+'\'>'+log+'</td>'+
						'<td>'+ downDom+ '</td>'+
						'<td>'+value.fileSizeFormat+'</td>'+
						'<td>'+value.startTime+'</td>'+
						'<td>'+value.endTime+'</td>'+
						'<td><div class="btn-group btn-group-xs">'+
							'<button type="button" class="btn btn-primary btn-sm dropdown-toggle" data-toggle="dropdown"> 操作 <span class="caret"></span>'+
							'</button>'+
							'<ul class="dropdown-menu" role="menu" style="min-width: 10px;">'+
								'<li><a href="javascript:void(0);" onclick="showProcess('+value.id+')">编辑</a></li>';
								if(dir == 'list'){
								t += '<li class="divider"></li>'+
								'<li><a href="javascript:void(0);" onclick="_confirm(\'确定要从此流程开始执行整个任务吗？\',\'task/process/startTask.htm?taskId='+taskId+'&processId='+value.id+'\')">执行流程</a></li>';
								}
							t += '</ul>'+
						'</div></td>'+
					'</tr>';
				});
				t +='</tbody></table>';
				row.child(t).show();
			}
			tr.addClass('shown');
		}
    });

   $('#example tfoot th').each(function (index) {
      var title = $('#example thead th').eq($(this).index()).text();
	   if(index == 4){
		     $(this).html('<select class="form-control" style="width:100%;" >' +
		     	'<option value="-1">全部</option>' +
			     '<option value="0">排队中</option>' +
			     '<option value="1">执行中</option>' +
			     '<option value="2">出现错误</option>' +
			     '<option value="3">执行结束</option>' +
			     '<option value="4">添加出错</option>' +
			     '<option value="5">未发送审批申请</option>' +
			     '<option value="7">强制停止</option>' +
			     '<option value="8">已发送审批申请</option>' +
		     '</select>');
		}else if(index ==2 || index ==3){
        	$(this).html('<input type="text" class="form-control input-sm" style="width:100%;" placeholder="Search ' + title +' " />');
		}
	});

	table.columns().eq(0).each( function (colIdx) {
		var domName = "input";
		if(colIdx == 4){
			domName = "select";
		}

		$(domName,table.column(colIdx).footer()).on(domName=='input'?'input':'change', function (){
				table.column(colIdx).search(this.value).draw();
		 });

	 });
});

	function showProcess(id){
		var data = getPostJsonData2("task/process/getProcessById.htm",{id:id});
    	if(data!=null) {
    		if(data.status==1){
    		data=data.obj;
    		var html='<form id="taskInfo" class="form-inline">'+
    			'列名:'+
    				'<div class="form-group myrow">'+
    					'<input type="text" autocomplete="off" style="width:600px;" class="form-control" id="pColumnName" value=\''+data.columnName+'\'>'+
    				'</div><br/>'+
    				 '标题:'+
    				 '<div class="form-group myrow">'+
    				 '<input type="text" autocomplete="off" style="width:600px;" class="form-control" id="pTitle" value=\''+data.title+'\'>'+
    				'</div><br/>'+
    				 '语句:'+
    				 '<div class="form-group myrow">'+
    					'<textarea class="form-control myrow" id="updateOperation" rows="5" style="width:800px;">'+data.operation+'</textarea>'+
    				'</div>'+
    			'</form>';

    			showMsg(html,"编辑",updateProcess,id);
    		}else{
    			showMsg(data.msg,"提示");
    		}
		}else{
			showMsg("没有操作语句","提示");
		}
	}

	function showTask(id){
		var data = getPostJsonData2("task/process/getTaskById.htm",{dir:dir,id:id});
		if(data!=null) {
			if(data.status==1){
			data=data.obj;
			var check = data.isSendFile==1?'checked':'';
    		var html='<form id="taskInfo" class="form-inline">'+
    			'任务名称:'+
    				'<div class="form-group myrow">'+
    					'<input type="text" autocomplete="off" style="width:600px;" class="form-control" id="m_task_name" value='+data.taskName+'>'+
    				'</div><br/>'+
    				<privilege:operate url="/task/audit/passAudit.htm">
    			'执行状态:<div class="form-group myrow"><select id="status" class="form-control">'+
					'<option value="-1">----------</option><option value="3" '+(data.status==3?"selected='selected'":"")+'>执行完成</option></select></div><br/>'+
					</privilege:operate>
    			'接收邮件:'+
    				'<div class="form-group myrow">'+
    					'<input type="text" autocomplete="off" style="width:600px;" class="form-control" id="email" value='+data.email+'>'+
    				'</div><br/>'+
    			'邮件带附近:'+
    				'<div class="form-group myrow">'+
    					'<input type="checkbox" '+check+' id="isSendFile" value="1" autocomplete="off"/>'+
    				'</div><br/>'+
    				'是否合并输出:<div class="form-group myrow"><select id="outputType" class="form-control">'+
					'<option value="0" '+(data.outputType==0?"selected='selected'":"")+'>输出最后一个流程的文件</option><option value="1" '+(data.outputType==1?"selected='selected'":1)+'>合并多个流程文件</option></select></div><br/>'+
    				 '合并输出字段:'+
    				 '<div class="form-group myrow">'+
    				 '<input type="text" autocomplete="off" style="width:600px;" class="form-control" id="outputColumn" value='+data.outputColumn+'>'+
    				'</div><br/>'+
    				 '合并关联条件:'+
    				 '<div class="form-group myrow">'+
    				 '<input type="text" autocomplete="off" style="width:600px;" class="form-control" id="outputLink" value='+data.outputLink+'>'+
    				'</div>'+
    			'</form>';

    		showMsg(html,"编辑",updateTask,id);
			}else{
				showMsg(data.msg,"提示");
			}
		}else{
			showMsg("没有操作语句","提示");
		}
	}

	function updateProcess(id){
		var data = getPostJsonData("task/process/updateProcess.htm",{id:id,operation:$("#updateOperation").val(),title:$("#pTitle").val(),columnName:$("#pColumnName").val()});
		if(data){
			showMsg(data.msg,"提示");
		}else{
			showMsg("更新出现异常","提示");
		}
	}

	function updateTask(id){
		var data = getPostJsonData("task/process/updateTask.htm",{
			id:id,taskName:$("#m_task_name").val(),email:$("#email").val(),outputType:$("#outputType").val(),status:$("#status").val(),
			outputColumn:$("#outputColumn").val(),outputLink:$("#outputLink").val(),isSendFile:$("#isSendFile").is(":checked")?1:0});
		if(data){
			showMsg(data.msg,"提示");
			window.location.reload();
		}else{
			showMsg("更新出现异常","提示");
		}
	}
	function auditPass(id){
	   		var html='<form id="taskInfo" class="form-inline">'+
	   				 '<label>是否发送结果邮件: </label><select id="sendType" name="sendType" class="form-control">'+
	   				 '<option value="0">发送结果</option>'+
	   				 '<option value="1">不发送结果</option>'+
	   				 '</select><br>'+
                     '<div class="form-group mf_ucuser">'+
					 '<label>接收人员</label><div id="uc_test" style="float: right;">ddd</div>'+
					 '</div><br/>'+
	   				 '</form>'+
	   				 '<span>备 注:</span>'+
	   				 '<div class="form-group myrow">'+
	   				 '<textarea style="width:600px;height:200px;" name="content" id="content"></textarea>'+
	   				'</div>'+
	   			'</form>';

	   		$("#confirmBtn").text('审批通过');
            showMsg(html,"申请审批",passAudit,id);

	   		$.getScript('/static/kindeditor-4.1.10/kindeditor-min.js', function() {
					KindEditor.create('textarea[name="content"]',{
						cssPath: 'static/kindeditor-4.1.10/plugins/code/prettify.css',
						resizeType : 2,
						allowPreviewEmoticons : true,
						allowImageUpload : false,
						allowImageRemote : true,
						items : [
							'source','|','fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
							'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
							'insertunorderedlist', '|', 'emoticons', 'image', 'link'],
						afterBlur: function(){this.sync();}
					});
			});
            $('#uc_test').uCheck('test',"2054",{'value_en':1});
	}

	function passAudit(id){
		var content = $("#content").val();
		var sendType = $("#sendType").val();

        var checkUserList = $(".chkd-usr").find('.badge-info');
        var rtxs='';
        $.each(checkUserList,function(i,n){
        var userId = $(n).attr('data-id');
        var userName = $(n).text();
        rtxs += userId+(i==checkUserList.length-1?"":",");
        });
        if(isEmpty(rtxs)){
        alert('必要要选择审批人员');
        return;
        }
        var enName = $("#uc_test").find("[name=test]").val();
		if(isEmpty(content)){
			alert("审批理由不能为空!");
			return;
		}
		var data = getPostJsonData("task/audit/passAudit.htm",{dir:dir,id:id,content:content,sendType:sendType,enNames:enName});
		if(data){
			//showMsg("审批通过,请刷新页面查看结果","提示");
			window.location.reload();
		}else{
			showMsg("出现异常","提示");
		}
   }

	function stopTask(taskId){
		showMsg("确定要停止此任务？","提示",confirmStop,taskId);
	}
	function confirmStop(taskId){
		closeMsg();
		var data = getPostJsonData("task/process/stopTask.htm",{dir:dir,taskId:taskId});
		if(data){
			window.location.reload();
		}
	}

	function getAutorunInfo(taskId){
		var params ={id:"13000001",dataType:'singleObj',args:{taskId:taskId,table:"t_autorun_info"}};
		var data = getPostJsonData("data/common/mysqlQuery.htm",{params:JSON.stringify(params)});
		if(data!=null && data.result==1) {
			var status = '';
			if(data.loop.name==0){
				status =' selected="selected"';
			}

			var html = '<div class="alert alert-info">'+
				'<p><span style="color: #a94442">隔多少小时执行一次-->0表示一天执行一次</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;执行哪天的数据-->1:今天,0:昨天,-1:前天...</p>'+
				'<p>如果要重新执行,例如你2个小时执行一次,把"上次执行时间"改为比现在小两个小时即可,不要点击重新执行</p>'+
				'</div>';

			html +='<form id="autorunInfo" class="form-horizontal">'+
    				'<div class="form-group">'+
    					'<label class="col-sm-2 control-label">名称</label>'+
    					'<div class="col-sm-10">'+
    					'<input type="text" autocomplete="off" readonly="readonly" class="form-control" id="name" value='+data.loop.name+'>'+
    					'</div>'+
    				'</div>'+
    				'<div class="form-group">'+
    					'<label class="col-sm-2 control-label">状态</label>'+
    					'<div class="col-sm-10">'+
    					'<select id="status" class="form-control"><option value="0" '+status+'>有效</option><option value="1" '+status+'>无效</option></select>'+
    					'</div>'+
    				'</div>'+
    				'<div class="form-group">'+
    					'<label class="col-sm-2 control-label">上次执行时间</label>'+
    					'<div class="col-sm-10">'+
    					'<input type="text" autocomplete="off" class="form-control" id="last_time" value="'+data.loop.last_time+'">'+
    					'</div>'+
    				'</div>'+
    				'<div class="form-group">'+
    					'<label class="col-sm-2 control-label">指定运行小时</label>'+
    					'<div class="col-sm-10">'+
    					'<input type="text" autocomplete="off" class="form-control" id="run_hour" value="'+data.loop.run_hour+'">'+
    					'</div>'+
    				'</div>'+
    				'<div class="form-group">'+
    					'<label class="col-sm-2 control-label">隔多少小时执行一次</label>'+
    					'<div class="col-sm-10">'+
    					'<input type="text" autocomplete="off" class="form-control" id="hours" value='+data.loop.hours+'>'+
    					'</div>'+
    				'</div>'+
    				'<div class="form-group">'+
    					'<label class="col-sm-2 control-label">执行哪天的数据</label>'+
    					'<div class="col-sm-10">'+
    					'<input type="text" autocomplete="off" class="form-control" id="days" value='+data.loop.days+'>'+
    					'</div>'+
    				'</div>'+
    			'</form>';
    		showMsg(html,"编辑",updateAutoRunInfo,data.loop.id);
		}
	}

	function updateAutoRunInfo(autoRunId){
		var data = getPostJsonData("task/auto/updateAutoRunInfo.htm",{
			autoRunId:autoRunId,status:$("#status").val(),run_hour:$("#run_hour").val(),
			hours:$("#hours").val(),days:$("#days").val(),last_time:$("#last_time").val()});
		if(data){
			showMsg(data.msg,"提示");
			//window.location.reload();
		}else{
			showMsg("更新出现异常","提示");
		}
	}

	$("#stopTask").click(function(){
		$.getJSON("task/process/stopExecutingTask.htm",{dir:dir},
			function(data) {
				alert(data.msg);
			});
	});

	function _confirm(msg,url){
		if(!msg) msg='一定要执行此操作？';
		showMsg(msg,"提示",urlReq,url);
    }
	function urlReq(url){
		closeMsg();
	   $.getJSON(url);
	   setTimeout(refresh,3000);
    }


    function sendEmailDialog(id){
		   var data = getPostJsonData2("task/process/getTaskById.htm",{id:id},true);
			if(data!=null) {
	   		var html='<form id="taskInfo" class="form-inline"><span style="color: red;font-weight: bold;">申请时加入你的上级领导，导出电话号码/邮箱/平台ID要把坚哥[周坚]加上</span><br/>'+
	   		'<div class="form-group mf_ucuser">'+
					'<label>审批人员</label><div id="uc_test" style="float: right;"></div>'+
				'</div><br/>'+
	   				 '邮件标题:'+
	   				 '<div class="form-group myrow">'+
	   				 '<input type="text" autocomplete="off" style="width:600px;" class="form-control" id="title" value="申请审批［'+data.taskName+'］数据导出">'+
	   				'</div><br/>'+
	   				 '邮件内容:'+
	   				 '<div class="form-group myrow">'+
	   				 '<textarea style="width:600px;height:200px;" name="content" id="content">'+data.logInfo+'</textarea>'+
	   				'</div>'+
	   			'</form>';

	   		$("#closeBtn").text('发送邮件');
	   		$("#confirmBtn").text('发送RTX消息');
	   		showMsg(html,"申请审批",sendAudit,{'id':id,'type':'rtx'},sendAudit,{'id':id,'type':'emial'});

	   		$.getScript('static/kindeditor-4.1.10/kindeditor-min.js', function() {
					KindEditor.create('textarea[name="content"]',{
						cssPath: 'static/kindeditor-4.1.10/plugins/code/prettify.css',
						resizeType : 2,
						allowPreviewEmoticons : true,
						allowImageUpload : false,
						allowImageRemote : true,
						items : [
							'source','|','fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
							'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
							'insertunorderedlist', '|', 'emoticons', 'image', 'link'],
						afterBlur: function(){this.sync();}
					});
				});
				$('#uc_test').uCheck('test',"1226,751",{'value_en':1});
			}else{
				showMsg("获取不到相关数据","提示");
			}
	   }

	  function sendAudit(obj){
	   var checkUserList = $(".chkd-usr").find('.badge-info');
		var rtxs='';
		$.each(checkUserList,function(i,n){
			var userId = $(n).attr('data-id');
			var userName = $(n).text();
			rtxs += userId+(i==checkUserList.length-1?"":",");
		});
		if(isEmpty(rtxs)){
			alert('必要要选择审批人员');
			return;
		}
		var enName = $("#uc_test").find("[name=test]").val();

	  	$('#confirmBtn').button("loading");
	   $('#confirmBtn').attr("disabled", true);
		var data = getPostJsonData("task/audit/applyTaskAudit.htm",{
		  rtxs:rtxs,enName:enName,taskId:obj.id,title:$("#title").val(),content:$("#content").val(),type:obj.type});
		if(data){
			if(data.status==1){
				window.location.href=window.location.href;
			}else{
				showMsg(data.msg,"提示");
			}
		}else{
			showMsg("发送出现异常","提示");
		}
		$('#confirmBtn').button("reset");
   }