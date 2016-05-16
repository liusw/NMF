<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<div class="panel panel-warning">
  <div class="panel-body">
	报名流水数据是经过去重的，即同一个用户在同一场比赛有多次报名退赛情况时，只记录其最终的状态<br/>
	例如：某用户报名某场比赛后，选择退赛，之后又重新报名了该场比赛，最终又退赛了，则该用户在流水中属于退赛的用户，报名状态中不会查询出该用户；所以实际上流水查出来的数据量比统计中少，因为统计是不做去重的
  </div>
</div>

<form class="form-inline">
	<div class="form-group">
		<label>开场时间:</label>
		<input type="text" class="form-control " name="sigTime" id="sigTime"/>
	</div>
	<div class="form-group">
		<label>&nbsp;&nbsp;svid:</label>
		<input type="text" class="form-control " name="svid" id="svid"/>
	</div>
	<div class="form-group">
		<label>&nbsp;&nbsp;状态:</label>
		<select id="status" name="status" class="form-control">
			<option value="">--所有--</option>
			<option value="0">参赛</option>
			<option value="1">被动退赛</option>
			<option value="2">主动退赛</option>
		</select>
	</div>
	<div class="form-group">
		&nbsp;&nbsp;&nbsp;&nbsp;<button type="button" class="btn btn-default" id="submitBtn" data-loading-text="查询中...">查询</button>
		<button type="button" class="btn btn-default" id="export" data-loading-text="提交中..."  onclick="exportData();">导出</button>
	</div>
</form>

<table id="example" class="table table-bordered" style="padding:0px; margin:0px;">
	<thead>
		<th>序号</th><th>名称</th><th>用户id</th><th>开赛时间</th><th>报名费</th><th>服务费</th><th>道具ID</th><th>svid</th><th>状态</th><th>锦标赛类型</th><th>操作时间</th>
	</thead>
	<tbody id="tbody"></tbody>
</table>
<div class="text-center">
 <ul class="pagination pagination-lg"></ul>
</div>

<script type="text/javascript">
var page=1;
$(function() {
	$('#sigTime').daterangepicker({
		  timePicker: true,
		  timePickerIncrement: 1,
		  timePicker12Hour:false,
		  timePickerSeconds:true,
		  singleDatePicker: true,
		  format:'YYYY-MM-DD HH:mm:ss',
		  //startDate: moment().format('YYYY-MM-DD HH:mm'),
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

$('#submitBtn').click(function(){
	page=1;
	loadData();
});

function loadData(){
	var plat = $("#navPlat").val();
	var sigTime = $('#sigTime').data('daterangepicker').startDate.format('YYYY/MM/DD HH:mm:ss');
	sigTime = new Date(sigTime).getTime()/1000;
	var svid=$("#svid").val();
	
	if(isEmpty(plat) || isEmpty(sigTime) || isEmpty(svid)){
		alert("站点/开赛时间/svid都不能为空!");
		return false;
	}
	var sql = "select * from mtt_jbs_signup where _plat="+plat+" and sigTime="+sigTime+" and svid="+svid;
	if(!isEmpty($("#status").val())){
		sql += " and status="+$("#status").val();
	}
	
	$('#submitBtn').button("loading");
	
	$("#tbody").html('');
	$(".pagination").html('');
	$.ajax({
	    type: "get",
	    async: false,
	    url: "data/hbase/query.htm",
	    data:{sql:sql,pageIndex:page,pageSize:1000},
	    contentType: "application/json; charset=utf-8",
	    dataType: "json",
	    cache: false,
	    success: function (obj) {
				if(obj.status==1 && obj.obj){
	    		var json = obj.obj;
	    		if(json){
	        		$.each(json.o, function(index, value) {
	        			var tr = getRowData(this,index);
	        			$("#tbody").append(tr);
	    			});
	        		getPage(json.totalPage,json.pageIndex,json.pageNumStart,json.pageNumEnd);
					}else{
						showMsg(obj.msg);
					}
	    		}
	    },error: function (err) {
	        alert("请求出错!");
	       }
	});
	$('#submitBtn').button("reset");
}

function getRowData(o,index){
	var columns=['subname','_uid','sigTime','entry_fee','service_fee','ticket','svid','status','mtype','_tm'];
	var tr = $("<tr></tr>");
	tr.append("<td>"+(index+1)+"</td>");
	for(var i=0;i<columns.length;i++){
			var hasVal=false;
			$.each(o, function(okey, ovalue) {
				if(columns[i]==okey){
					if(okey=='status'){
						if(ovalue=='0'){
							ovalue='参赛';
						}else if(ovalue=='1'){
							ovalue='被动退赛';
						}else if(ovalue=='2'){
							ovalue='主动退赛';
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

function getPage(totalPage,pageIndex,pageNumStart,pageNumEnd){
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

function toPage(p){
	page=p;
	loadData();
}

function exportData(){
	var plat = $("#navPlat").val();
	var sigTime = $('#sigTime').data('daterangepicker').startDate.format('YYYY/MM/DD HH:mm:ss');
	sigTime = new Date(sigTime).getTime()/1000;
	var svid=$("#svid").val();
	
	if(isEmpty(plat) || isEmpty(sigTime) || isEmpty(svid)){
		alert("站点/开赛时间/svid都不能为空!");
		return false;
	}
	
	var sql = "select * from mtt_jbs_signup where _plat="+plat+" and sigTime="+sigTime+" and svid="+svid;
	if(!isEmpty($("#status").val())){
		sql += " and status="+$("#status").val();
	}
	
	window.location.href="data/hbase/download.htm?sql="+sql+"&pageIndex=1&pageSize=100000"+
			"&title=名称,用户id,开赛时间,报名费,服务费,道具ID,svid,状态,锦标赛类型,操作时间"+
			"&column=subname,_uid,sigTime,entry_fee,service_fee,ticket,svid,status,mtype,_tm";
}
</script>
</body>
</html>
