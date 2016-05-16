<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<form class="form-inline">
	<div class="form-group">
		<label>开场时间:</label>
		<input type="text" class="form-control " name="rewardSigTime" id="rewardSigTime"/>
	</div>
	<div class="form-group">
		<label>&nbsp;&nbsp;svid:</label>
		<input type="text" class="form-control " name="rewardSvid" id="rewardSvid"/>
	</div>
	<div class="form-group">
		&nbsp;&nbsp;&nbsp;&nbsp;<button type="button" class="btn btn-default" id="rewardSubmitBtn" data-loading-text="查询中...">查询</button>
		<button type="button" class="btn btn-default" data-loading-text="提交中..."  onclick="exportRewardData();">导出</button>
	</div>
</form>

<table class="table table-bordered" style="padding:0px; margin:0px;">
	<thead>
		<th>序号</th><th>用户id</th><th>开赛时间</th><th>svid</th><th>类型</th><th>实物发放数量</th><th>奖励的游戏币</th><th>操作时间</th>
	</thead>
	<tbody id="tbodyReward"></tbody>
</table>
<div class="text-center">
 <ul class="pagination pagination-lg"></ul>
</div>

<script type="text/javascript">
var pageReward=1;
$(function() {
	$('#rewardSigTime').daterangepicker({
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

$('#rewardSubmitBtn').click(function(){
	pageReward=1;
	loadRewardData();
});

function loadRewardData(){
	var plat = $("#navPlat").val();
	var rewardSigTime = $('#rewardSigTime').data('daterangepicker').startDate.format('YYYY/MM/DD HH:mm:ss');
	
	rewardSigTime = new Date(Date.parse(rewardSigTime)).getTime()/1000;
	var svid=$("#rewardSvid").val();
	
	if(isEmpty(plat) || isEmpty(rewardSigTime) || isEmpty(svid)){
		alert("站点/开赛时间/svid都不能为空!");
		return false;
	}
	var sql = "select * from mtt_jbs_reward where _plat="+plat+" and sigTime="+rewardSigTime+" and svid="+svid;
	
	$('#rewardSubmitBtn').button("loading");
	
	$("#tbodyReward").html('');
	$(".pagination").html('');
	$.ajax({
	    type: "get",
	    async: false,
	    url: "data/hbase/query.htm",
	    data:{sql:sql,pageIndex:pageReward,pageSize:1000},
	    contentType: "application/json; charset=utf-8",
	    dataType: "json",
	    cache: false,
	    success: function (obj) {
	    	if(obj.status==1 && obj.obj && obj.obj.o){
        		var json = obj.obj;
	    		if(json){
	        		$.each(json.o, function(index, value) {
	        			var tr = getRowDataReward(this,index);
	        			$("#tbodyReward").append(tr);
	    			});
	        		getPageReward(json.totalPage,json.pageIndex,json.pageNumStart,json.pageNumEnd);
					}else{
						showMsg(obj.msg);
					}
	    		}
	    },error: function (err) {
	        alert("请求出错!");
	       }
	});
	$('#rewardSubmitBtn').button("reset");
}

function getRowDataReward(o,index){
	var columns=['_uid','sigTime','svid','mtype','goods','coins','_tm'];
	var tr = $("<tr></tr>");
	tr.append("<td>"+(index+1)+"</td>");
	for(var i=0;i<columns.length;i++){
			var hasVal=false;
			$.each(o, function(okey, ovalue) {
				if(columns[i]==okey){
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

function getPageReward(totalPage,pageIndex,pageNumStart,pageNumEnd){
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
	pageReward=p;
	loadRewardData();
}

function exportRewardData(){
	var plat = $("#navPlat").val();
	var rewardSigTime = $('#rewardSigTime').data('daterangepicker').startDate.format('YYYY/MM/DD HH:mm:ss');
	rewardSigTime = new Date(rewardSigTime).getTime()/1000;
	var svid=$("#rewardSvid").val();
	
	if(isEmpty(plat) || isEmpty(rewardSigTime) || isEmpty(svid)){
		alert("站点/开赛时间/svid都不能为空!");
		return false;
	}
	var sql = "select * from mtt_jbs_reward where _plat="+plat+" and sigTime="+rewardSigTime+" and svid="+svid;
	
	window.location.href="data/hbase/download.htm?sql="+sql+"&pageIndex=1&pageSize=100000"+
			"&title=用户id,开赛时间,svid,类型,实物发放数量,奖励的游戏币,操作时间"+
			"&column=_uid,sigtime,svid,mtype,goods,coins,_tm";
}

</script>
</body>
</html>
