<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<form class="form-inline">
	<div class="form-group">
		<label>开场时间:</label>
		<input type="text" class="form-control " name="infoSigTime" id="infoSigTime"/>
	</div>
	<div class="form-group">
		<label>&nbsp;&nbsp;svid:</label>
		<input type="text" class="form-control " name="infoSvid" id="infoSvid"/>
	</div>
	<div class="form-group">
		<label>&nbsp;&nbsp;状态:</label>
		<select id="infoStatus" name="infoStatus" class="form-control">
			<option value="">--所有--</option>
			<option value="0">开场</option>
			<option value="1">未开场</option>
			<option value="2">结束</option>
			<option value="3">rebuy</option>
			<option value="4">addon</option>
			<option value="5">进入钱圈盲注</option>
			<option value="6">比赛结束盲注</option>
		</select>
	</div>
	<div class="form-group">
		&nbsp;&nbsp;&nbsp;&nbsp;<button type="button" class="btn btn-default" id="infoSubmitBtn" data-loading-text="查询中...">查询</button>
		<button type="button" class="btn btn-default" data-loading-text="提交中..."  onclick="exportInfoData();">导出</button>
	</div>
</form>

<table class="table table-bordered" style="padding:0px; margin:0px;">
	<thead>
		<th>序号</th><th>用户id</th><th>开赛时间</th><th>svid</th><th>状态</th><th>类型</th><th>保底奖池</th><th>rebuy/addon收取的服务费总数</th>
		<th>rebuy/addon消耗的参赛券总数</th><th>成功rebuy/addon的总游戏币</th><th>比赛结束时间</th><th>成功rebuy/addon的总次数</th><th>盲注</th><th>时间</th>
	</thead>
	<tbody id="tbodyInfo"></tbody>
</table>

<div class="text-center">
 <ul class="pagination pagination-lg"></ul>
</div>

<script type="text/javascript">
var pageInfo=1;
$(function() {
	$('#infoSigTime').daterangepicker({
		  timePicker: true,
		  timePickerIncrement: 1,
		  timePicker12Hour:false,
		  timePickerSeconds:true,
		  singleDatePicker: true,
		  format:'YYYY-MM-DD HH:mm:ss',
		  locale: {
	        applyLabel: '确定',
	        cancelLabel: '取消',
	        fromLabel: '开始日期',
	        toLabel: '结束日期',
	        customRangeLabel: '时间段选择',
	        daysOfWeek: ['日', '一', '二', '三', '四', '五','六'],
	        monthNames: ['一月', '二月', '三月', '四月', '五月','六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
	        firstDay: 1
	        }
		 });
});

$('#infoSubmitBtn').click(function(){
	pageInfo=1;
	loadInfoData();
});

function loadInfoData(){
	var plat = $("#navPlat").val();
	var infoSigTime = $('#infoSigTime').data('daterangepicker').startDate.format('YYYY/MM/DD HH:mm:ss');
	infoSigTime = new Date(infoSigTime).getTime()/1000;
	var svid=$("#infoSvid").val();
	
	if(isEmpty(plat) || isEmpty(infoSigTime) || isEmpty(svid)){
		alert("站点/开赛时间/svid都不能为空!");
		return false;
	}
	var sql = "select * from mtt_jbs_info where _plat="+plat+" and sigTime="+infoSigTime+" and svid="+svid;
	if(!isEmpty($("#infoStatus").val())){
		sql += " and status="+$("#infoStatus").val();
	}
	
	$('#infoSubmitBtn').button("loading");
	
	$("#tbodyInfo").html('');
	$(".pagination").html('');
	$.ajax({
	    type: "get",
	    async: false,
	    url: "data/hbase/query.htm",
	    data:{sql:sql,pageIndex:pageInfo,pageSize:1000},
	    contentType: "application/json; charset=utf-8",
	    dataType: "json",
	    cache: false,
	    success: function (obj) {
	    	if(obj.status==1 && obj.obj){
            var json = obj.obj;
	    		if(json){
	        		$.each(json.o, function(index, value) {
	        			var tr = getRowDataInfo(this,index);
	        			$("#tbodyInfo").append(tr);
	    			});
	        		getPageInfo(json.totalPage,json.pageIndex,json.pageNumStart,json.pageNumEnd);
					}else{
						showMsg(obj.msg);
					}
	    		}
	    },error: function (err) {
	        alert("请求出错!");
	       }
	});
	$('#infoSubmitBtn').button("reset");
}

function getRowDataInfo(o,index){
	var columns=['_uid','sigTime','svid','status','mtype','guarantee','servicefee','ticket','money','lts_at','count','coins','_tm'];
	var tr = $("<tr></tr>");
	tr.append("<td>"+(index+1)+"</td>");
	for(var i=0;i<columns.length;i++){
			var hasVal=false;
			$.each(o, function(okey, ovalue) {
				if(columns[i]==okey){
					if(okey=='status'){
						if(ovalue=='0'){
							ovalue='开场';
						}else if(ovalue=='1'){
							ovalue='未开场';
						}else if(ovalue=='2'){
							ovalue='结束';
						}else if(ovalue=='3'){
							ovalue='rebuy';
						}else if(ovalue=='4'){
							ovalue='addon';
						}else if(ovalue=='5'){
							ovalue='进入钱圈盲注';
						}else if(ovalue=='6'){
							ovalue='比赛结束盲注';
						}
					}
					if(okey=='sigTime'){
						ovalue=new Date(ovalue*1000).format('yyyy-MM-dd hh:mm:ss');
					}
					if(okey=='_tm'){
						ovalue=new Date(ovalue*1000).format('yyyy-MM-dd hh:mm:ss');
					}
					
					tr.append("<td>"+ovalue+"</td>");
					hasVal = true;
					return;
				}
			});
			if(!hasVal){
				tr.append("<td></td>");
			}
	}
	return tr;
}

function getPageInfo(totalPage,pageIndex,pageNumStart,pageNumEnd){
	var pageHtml = "";
	if(totalPage>1){
		if(pageIndex!=1){
			pageHtml+='<li><a href="javascript:void(0);" onclick=toPage('+(pageIndex-1)+');>«</a></li>';
		}
		for(var i=pageNumStart;i<=pageNumEnd;i++){
			if(i==pageIndex){
				pageHtml+='<li class="active"><a href="javascript:void(0);">'+i+'</a></li>';
			}else{
				pageHtml+='<li><a href="javascript:void(0);" onclick=toPage('+i+');>'+i+'</a></li>';
			}
		}
		if(pageIndex!=totalPage){
			pageHtml+='<li><a href="javascript:void(0);" onclick=toPage('+(pageIndex+1)+');>»</a></li>';
		}
		$(".pagination").html(pageHtml);
	}else{
		$(".pagination").html('');
	}
}

function toInfoPage(p){
	pageInfo=p;
	loadInfoData();
}

function exportInfoData(){
	var plat = $("#navPlat").val();
	var infoSigTime = $('#infoSigTime').data('daterangepicker').startDate.format('YYYY/MM/DD HH:mm:ss');
	infoSigTime = new Date(infoSigTime).getTime()/1000;
	var svid=$("#infoSvid").val();
	
	if(isEmpty(plat) || isEmpty(infoSigTime) || isEmpty(svid)){
		alert("站点/开赛时间/svid都不能为空!");
		return false;
	}
	var sql = "select * from mtt_jbs_info where _plat="+plat+" and sigTime="+infoSigTime+" and svid="+svid;
	if(!isEmpty($("#infoStatus").val())){
		sql += " and status="+$("#infoStatus").val();
	}
	
	window.location.href="data/hbase/download.htm?sql="+sql+"&pageIndex=1&pageSize=100000"+
			"&title=用户id,开赛时间,svid,状态,类型,保底奖池,rebuy/addon收取的服务费总数,rebuy/addon消耗的参赛券总数,成功rebuy/addon的总游戏币,比赛结束时间,成功rebuy/addon的总次数,盲注,操作时间"+
			"&column=_uid,sigTime,svid,status,mtype,guarantee,servicefee,ticket,money,lts_at,count,coins,_tm";
}
</script>
</body>
</html>
