<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en-us">
<head>
	<%@ include file="/WEB-INF/jsp/common/meta.jsp"%>
	<title>数据魔方</title>
	<link rel="stylesheet" href="static/kindeditor-4.1.10/themes/default/default.css" />
	<link rel="stylesheet" href="static/kindeditor-4.1.10/plugins/code/prettify.css" />
	<link rel="stylesheet" type="text/css" href="static/daterangepicker/daterangepicker-bs3.css">
	<link href="static/bootstrap-switch/dist/css/bootstrap3/bootstrap-switch.min.css" rel="stylesheet">
	<style type="text/css">
	.panel-footer {
	    background-color: #F5F5F5;
	    border-bottom-left-radius: 3px;
	    border-bottom-right-radius: 3px;
	    border-top: 1px solid #DDDDDD;
	    padding: 5px;
	}
	.addListWarning{
		display: none;
	}
	.task{
		background: #ccc;
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
	
	.removeLable{
		height: 35px;
		line-height: 35px;
		float: right;
	   font-size: 14px;
	}
	.removeLable a{
		color: #fff;
	}
	.labelColumn{
		margin-top: 5px;
	}
	</style>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/top.jsp">
        <jsp:param name="nav" value=""/>
	</jsp:include>
	
	<jsp:include page="/WEB-INF/jsp/common/dataMenu.jsp">
		<jsp:param name="nav" value="index" />
	</jsp:include>
	
	<div class="main">
		<jsp:include page="/WEB-INF/jsp/common/mainNav.jsp">
			<jsp:param name="actionNames" value="" />
		</jsp:include>
		
		<div class=".container-fluid">

			<div class="widget-box">
				<div class="widget-title">
					<h5>list需求</h5>
				</div>
				<div class="widget-content nopadding">
					
					<div class="alert alert-danger addListWarning" role="alert">
						<a href="http://mf.oa.com/docs/article/detail.htm?id=363">众里寻它千百度，可否点我试一试</a><br/>
                                                距离排期时间：<span id="planTime"></span>天<br/>
                                                距离开发时间：7天<br/>
                                                答疑时间：每天11:30-12:30,17:30-18:30
					</div>
					<form class="form-inline">
							<div class="form-group">
								<div class="switch switch-large">
									<label>类型:</label>
								    <input type="checkbox" checked id="listType" autocomplete="off"/>
								</div>
							</div><br><br>
							<div class="form-group">
								<label>平台:</label>
								<select id="plats" name="plat" class="form-control"></select>
							</div>
							<div class="form-group">
								<label>&nbsp;&nbsp;站点:</label>
								<select id="sid" name="sid" class="form-control multiselect" multiple="multiple"></select>
							</div><br><br>
							<div class="form-group mf_ucuser">
								<label>接收:</label><div id="uc_test" style="float: right;"></div>
							</div>
							<br><br>
							<div class="form-group">
								<label>标题:</label>
								<input type="text" style="width: 480px;" id="title" name="title" class="form-control" placeholder="标题" autocomplete="off">
							</div><br><br>
							<div class="panel panel-default">
							  <div class="panel-body">
								<div id="processInfo"></div>
								</div>
							  <div class="panel-footer"><button type="button" class="btn btn-primary" onclick="addTable()">增加业务表</button></div>
							</div>
							<div class="modal fade myModal" style="display: none;"></div>
							<textarea id="content" name="content" class="form-control" style="height:300px;width: 100%;">描述你要补充的内容</textarea>
						<br>
						<div class="form-group">
							<button type="button" class="btn btn-primary" id="addList" data-loading-text="操作中...">提交</button>
						</div>
					</form>
				
					<div class="panel panel-info">
					  <div class="panel-body">
<p>1、普通需求需经过日志分析组审核和排期。<br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;需求方发起需求后，日志分析组会统一在当周星期五进行审核和排期，排期后的需求将在下周进入开发环节，需求按照申请时间顺序排期，需求方在收到交付成果后，请在两天内进行验收，并反馈使用情况，超过验收时间将不再处理相关问题。
<p>2、紧急需求与特殊数据申请仍需邮件申请，邮件内容需包含需求、申请原因、需求价值、需求时限等信息；<br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;紧急需求指在排期计划外但需要在<span style="color: red;">本周内</span>紧急完成的需求。申请需发送申请邮件至申请人所在组负责人，抄送德州产品线研发部负责人（周坚）和日志分析组负责人（郑壮杰）；<br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;特殊数据需求指涉及到游戏业务关键信息数据（例如用户邮箱/平台ID/手机号码）的需求，申请需发送申请邮件至康骞文邮箱，抄送日志分析组负责人（郑壮杰）和申请人所在组的负责人。<br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;需求邮件申请通过后，日志分析组将安排开发，若需求邮件申请不通过，不能进行相关数据提取或功能开发。</p>
3、数据需求建议优先使用魔方平台自行处理，原则上只接受相对复杂的数据分析需求，普通需求需要排期处理，详情请关注list上的排期计划。
					  </div>
					</div>
				</div>
			</div>

			<div class="widget-box">
          <div class="widget-title">
            <h5>快速导航</h5>
          </div>
          <div class="widget-content nopadding">
            <table class="table table-bordered">
              <thead>
                <tr>
                  <th width="200px;">name</th>
                  <th>URL</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>List需求</td>
                  <td><a href="http://list.oa.com/task.php" target="_blank">http://list.oa.com/task.php</a></td>
                </tr>
                <tr>
                  <td>历史数据导出</td>
                  <td><a href="http://mf.oa.com/data/hql.htm" target="_blank">http://mf.oa.com/data/hql.htm</a></td>
                </tr>
                <tr>
                  <td>多流程导出</td>
                  <td><a href="http://mf.oa.com/log/task" target="_blank">http://mf.oa.com/log/task</a></td>
                </tr>
                <tr>
                  <td>锦标赛</td>
                  <td><a href="http://mf.oa.com/data/jinbiaosai.htm" target="_blank">http://mf.oa.com/data/jinbiaosai.htm</a></td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
		
		<!--Chart-box-->    
      <div class="widget-box">
        <div class="widget-title">
          <h5>网站统计</h5>
          <div style="float: right;height: 36px;line-height: 36px;margin-top: -2px;" id="tongjiDate">
          	<div id="lgdaterange" class="date-input">
					<i class="glyphicon glyphicon-calendar fa fa-calendar"></i>
					<span></span> <b class="caret"></b>
			</div>
          </div>
        </div>
        <div class="widget-content" >
          <div class="row">
            <div class="col-md-9">
              <div style="width: 100%;height: auto;"  id="tonjiChart"></div>
            </div>
            <div class="col-md-3">
              <ul class="site-stats">
                <li class="bg_dy"><strong id="dataSizeTm"></strong> <small>时间段导出数据量</small></li>
                <li class="bg_ls"><strong id="dataSize"></strong> <small>历史导出数据量</small></li>
                <li class="bg_dy"><strong id="execCountTm"></strong> <small>时间段导出次数</small></li>
                <li class="bg_ls"><strong id="execCount"></strong> <small>历史导出次数</small></li>
                <li class="bg_dy"><strong id="userCountTm"></strong> <small>时间段导出人次</small></li>
                <li class="bg_ls"><strong id="userCount"></strong> <small>历史段导出人次</small></li>
              </ul>
            </div>
          </div>
        </div>
      </div>
		<!--End-Chart-box--> 
	
	</div>
		
	</div>
	
	<%@ include file="/WEB-INF/jsp/common/foot.jsp"%>

	<!-- Bootstrap core JavaScript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="static/js/basic.js"></script>
	<script src="static/js/multiselect.js"></script>
	<script charset="utf-8" src="static/kindeditor-4.1.10/kindeditor-min.js"></script>
	<script charset="utf-8" src="static/kindeditor-4.1.10/lang/zh_CN.js"></script>
	<script src="static/kindeditor-4.1.10/plugins/code/prettify.js" type="text/javascript"></script> 
	<script src="static/js/daterangepicker.js"></script>
	<script src="static/bootstrap-switch/dist/js/bootstrap-switch.min.js"></script>
	<script src="static/js/daterange.by.js"></script>
	<script src="static/js/common.js"></script>
	<script src="static/Highcharts-4.0.4/js/highcharts.js"></script>
	<script src="static/Highcharts-4.0.4/js/modules/exporting.js"></script>
	<script type="text/javascript" src="static/select2/js/select2.min.js"></script>
	<script src="static/js/dateUtils.js"></script>
	<script src="http://tool.oa.com/api/?id=uCheck"></script>
	<script type="text/javascript">
	var std;
	$(function() {
		$("#listType").bootstrapSwitch({
			onText:'普通需求',
			offText:'紧急需求',
			offColor:'danger',
			readonly:true,
			onSwitchChange:function(){
				//alert('暂时只支持添加普通需求');
			}
		});
		
		var nowDay =  new Date().getDay();
		$("#planTime").html(nowDay==6?6:5-nowDay);
		
		$('#uc_test').uCheck('test',"1226,751",{limit:5});
		
		getMultiPlat("#plats", "#sid");
		$("#plats").change(function() {
			getMultiSid("#plats","#sid",true);
		});
		
		std = new Date();
		std.addDays(-30);
		createDateRgDom({'dom':'#tongjiDate','isShow':true,startDate:std.format('yyyyMMdd'),applyClickCallBack:tongJiTask});
		
		addTable();
		
		tongJiTask();
	});
	
	var index=0;
	function addTable(){
		var form = '';
		form+='<div id="process_'+index+'" class="form-inline task" index="'+index+'">'+
		form+'	<div class="processTitle">'+
		form+'		表'+(index+1)+'<span class="removeLable"><a href="javascript:void(0);"> x</a></span>'+
		form+'	</div>'+
		form+'	<div class="form-group">'+
		form+'		<div id="table'+index+'" class="date-input">'+
		form+'			<i class="glyphicon glyphicon-calendar fa fa-calendar"></i>'+
		form+'			<span></span> <b class="caret"></b>'+
		form+'		</div>'+
		form+'	</div>'+
		form+'	<div class="form-group">'+
		form+'		<select id="tableNames'+index+'" class="form-control" style="width: 100%"></select>'+
		form+'	</div>'+
		form+'	<div class="form-group">'+
		form+'	<button type="button" class="btn btn-danger" onclick="addColumns('+index+')">添加列值</button></div><br>'+
		form+'	<div class="form-group">'+
		form+'	<div class="lableDive'+index+'">'+
		form+'	</div>'+
		form+'	</div>'+
		form+'</div>';
		$("#processInfo").append(form);
		
		createDateRgDom({'dom':'#table'+index,'isShow':true,startDate:std.format('yyyyMMdd'),'opens':'right'});
		
	 	var tables = getTables("hive",false);
	 	var form='';
	 	$.each(tables, function(index, value) {
	 		form += "<option title='"+value.desc+"' value=" + value.name + ">" + value.desc+"</option>";
		});
		$("#tableNames"+index).append(form);
		$("#tableNames"+index).select2();
		
		$('#process_'+index).find(".removeLable").unbind('click').click(function(){
			$(this).parent().parent().remove();
		});
		
		index++;
	}
	
	function addColumns(index){
    	var tableName = $("#tableNames"+index).val();
    	var _this=this;
		var html = 
	    '<div class="modal-dialog modal-lg"><div class="modal-content">'+
	      '<div class="modal-header">'+
	        '<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>'+
	        '<h4 class="modal-title">设置参数</h4>'+
	      '</div>'+
	      '<div class="modal-body">'+
	      '<form class="form-inline">';
  	
	      var options =getOptionHtml(tableName,"hive");
  	
	      html += '<span class="mylabel">条件:</span><select id="filter_c" class="form-control" style="width: 200px;">'+options+"</select>";
	      html += '<select id="filter_op" class="form-control">'+
							'<option value="0">等于</option>'+
							'<option value="1">小于</option>'+
							'<option value="2">小于或等于</option>'+
							'<option value="3">不等于</option>'+
							'<option value="4">大于</option>'+
							'<option value="5">大于或等于</option>'+
							'<option value="6">in</option><option value="7">like</option>';
			html += '</select>';
			html += '<input id="filter_v" type="text" class="form-control" style="width: 80px;">&nbsp;&nbsp;';
			html += '(in条件使用英文分号;分开各个值)';
			
			html+='</form>'+
      	'</div>'+
      	'<div class="modal-footer">'+
        		'<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>';
        	html+='<button type="button" class="btn btn-primary" id="addParams">提交</button>';
     		html+='</div>'+
		  '</div></div>';
		  
			createModal(html);
			$("#filter_c").select2();
			$("#addParams").unbind('click').click(function(){
				_this.addParams(index);
			});
	}
	
	function addParams(index){
		var value="",lable="";
		var dom;
		if(isEmpty($("#filter_v").val())){
			alert("请输入值");
			return;
		}
		
		lable=$("#filter_c").val()+" "+op($("#filter_op").val())+" "+$("#filter_v").val();
		$(".lableDive"+index).append('<div class="labelColumn"><p><span class="columnValue">'+lable+'</span> <span class="labelClose" onclick="labelClose(this)">×</span></p></div>');
	};
	
	function createModal(html){
		$(".myModal").html("");
		$(".myModal").html(html);
      $(".myModal").modal({
    	  backdrop:"static"
      });
	}
	
	$("#title").click(function(){
		if($(".addListWarning").is(":hidden")){
			$(".addListWarning").show();
		}
	});
	
	var editor;
	KindEditor.ready(function(K) {
		editor = K.create('textarea[name="content"]', {
			cssPath: '<%=basePath%>static/kindeditor-4.1.10/plugins/code/prettify.css', 
			resizeType : 1,
			allowPreviewEmoticons : false,
			allowImageUpload : false,
			uploadJson : 'upload?action=uploadImages',
			allowImageRemote : true,
	      filterMode : false,
	      urlType:'domain',
			items : [
				'source','code','|','fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
				'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
				'insertunorderedlist', '|', 'emoticons', 'image', 'link'],
			afterCreate : function() {
                this.sync();
            },
         afterBlur : function() {
        	 if (editor.html() ==''){editor.html('描述你要补充的内容')};
            this.sync();
            },
         afterFocus:function(){
            if(editor.html()=='描述你要补充的内容'){editor.html('')};
            }
		});
		 editor.sync();
	});
	
	$("#addList").click(function(){
		var plat = $("#plats").val();
		var sid = $("#sid").attr("selectSidValues");
		var bpid = $("#sid").attr("selectBpidValues");
		var title =  $("#title").val();
		var content =  $("#content").val();
		
		var checkUserList = $(".chkd-usr").find('.badge-info');
		var rtxs='';
		$.each(checkUserList,function(i,n){
			var userId = $(n).attr('data-id');
			var userName = $(n).text();
			rtxs += userId+(i==checkUserList.length-1?"":",");
		});
		
		if(isEmpty(plat) || isEmpty(title) ||isEmpty(content)){
			alert("标题/内容/平台都不能为空");
			return;
		}
		
		var tableParams = [];
		$("#processInfo").find(".task").each(function(){
			var index = $(this).attr('index');			
			var start = DaterangeUtil.getStartDate("#table"+index,"YYYYMMDD");
			var end = DaterangeUtil.getEndDate("#table"+index,"YYYYMMDD");
			var tableName = $("#tableNames"+index).val();
			
			var param = '';
			$(".lableDive"+index).find(".columnValue").each(function(){
				param+=($(this).text()+",");
			});
			if(param.length>0){
				param = param.substring(0, param.length-1);
			}
			tableParams.push({tm:start+"至"+end,tableName:tableName,column:param});
		});
		
		$.post("addList.htm", {
			plat : plat,
			sid : sid,
			bpid : bpid,
			title :title,
			rtxs:rtxs,
			content : content,
			tableParams:JSON.stringify(tableParams)
		},function(data) {
			if(data.ok==1){
				showMsg("<a href='http://list.oa.com/task.php?tkid="+data.msg+"' target='_blank'>http://list.oa.com/task.php?tkid="+data.msg+"</a>","添加成功");
			}else{
				alert(data.msg);
			}
		},"json").error(function(){
			alert("添加出错");
		});
	}); 
	function labelClose(o) {
		$(o).parent().parent().remove();
	}
	function tongJiTask(){
		var start = DaterangeUtil.getStartDate("#tongjiDate","YYYYMMDD");
		var end = DaterangeUtil.getEndDate("#tongjiDate","YYYYMMDD");
		
		var params ={id:"12000001",args:{stm:start,etm:end}};
		$.post("data/common/mysqlQuery.htm",{params:JSON.stringify(params)},function(data, textStatus) {
			
			var xAxis = [];
			var yExecCountAxis = [];
			var yDataSizeAxis = [];
			var yUserCountAxis = [];
			
			if(data && data.loop){
				$.each(data.loop,function(key,obj){
					xAxis.push(obj.xaxis);
					yExecCountAxis.push(obj.execCount);
					yDataSizeAxis.push(getFlow2M(obj.dataSize));
					yUserCountAxis.push(obj.userCount);
				});
			}
			
			$('#tonjiChart').highcharts({
		      chart: {
		            zoomType: 'xy'
		        },
		       title: {
		            text: '魔方数据统计'
		        },
		       xAxis: [{
		            categories:xAxis,
		            labels:{
		            	 enabled: false
		            	}
		        }],
		      yAxis: [{
					labels: {
						format: '{value} 次',
						style: {color: Highcharts.getOptions().colors[2]}
		            },
					title: {
						text: '导出次数',
		            style: {color: Highcharts.getOptions().colors[2]}
		            },
					opposite: true
				}, {
					gridLineWidth: 0,
						title: {
						text: '数据量',
						style: {color: Highcharts.getOptions().colors[0]}
		            },
		         labels: {
						format: '{value} MB',
						style: {color: Highcharts.getOptions().colors[0]}
		            }
		        }, {
		         gridLineWidth: 0,
		         title: {
		         	text: '导出人次',
		            style: {color: Highcharts.getOptions().colors[1]}
		            },
		         labels: {
		             format: '{value} 人',
		             style: {color: Highcharts.getOptions().colors[1]}
		            },
		         opposite: true
		        }],
		        tooltip: {
		            shared: true
		        },
		        legend: {
		            layout: 'vertical',
		            align: 'left',
		            x: 80,
		            verticalAlign: 'top',
		            y: 55,
		            floating: true,
		            backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF'
		        },
		        series: [{
		            name: '导出次数',
		            type: 'column',
		            yAxis: 0,
		            data: yExecCountAxis,
		            tooltip: {valueSuffix: ' (次数)'}

		        }, {
		            name: '导出数据量',
		            type: 'spline',
		            yAxis: 1,
		            data: yDataSizeAxis,
		            marker: {enabled: false},
		            dashStyle: 'shortdot',
		            tooltip: {
		                valueSuffix: ' MB'
		            	}
		        }, {
		            name: '导出人次',
		            type: 'spline',
		            yAxis: 2,
		            data: yUserCountAxis,
		            tooltip: {
		                valueSuffix: ' (人数)'
		            }
		        }]
		    });
		},"json");
		
		var params ={id:"12000002",args:{stm:start,etm:end},dataType:'singleObj'};
		var data = getJsonData("data/common/mysqlQuery.htm",{params:JSON.stringify(params)});
		if(data && data.result==1){
			$("#dataSizeTm").html(getFlow(data.loop.dataSizeTm));
			$("#dataSize").html(getFlow(data.loop.dataSize));
			$("#execCountTm").html(data.loop.execCountTm);
			$("#execCount").html(data.loop.execCount);
			$("#userCountTm").html(data.loop.userCountTm);
			$("#userCount").html(data.loop.userCount);
		}
	}
	
	function getFlow2M(flowVlueBytes){
		 var flow = flowVlueBytes/1024/1024;
		 return  parseFloat(flow.toFixed(4));
	}
	</script>
</body>
</html>
