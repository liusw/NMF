<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en-us">
<head>
<%@ include file="/WEB-INF/jsp/common/meta.jsp"%>
<title>每日统计-数据魔方</title>
<style type="text/css">
.showSubTable{
font-weight: bold;
}
</style>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/top.jsp">
		<jsp:param name="nav" value="data" />
	</jsp:include>
	
	<jsp:include page="/WEB-INF/jsp/common/dataMenu.jsp">
		<jsp:param name="nav" value="businessData" />
		<jsp:param name="subnav" value="monitorActiveUser" />
	</jsp:include>
	
	<div class="main">
		<jsp:include page="/WEB-INF/jsp/common/mainNav.jsp">
			<jsp:param name="actionName" value="活跃小号监控" />
		</jsp:include>
		
		<div class="content-body">
				<table id="example" style="" class="table table-striped table-bordered display dtable">
					<thead>
						<tr>
							<th width="10%">日期</th><th width="10%">用户数</th><th width="10%">当天累积获得的免费筹码总额</th>
							<th width="10%">当天获得的付费筹码总额</th><th width="10%">已转移筹码</th><th width="10%">转移比例</th><th width="10%">详情</th>
						</tr>
					</thead>
					<tfoot></tfoot>
				</table>
		</div>
	</div>

	<%@ include file="/WEB-INF/jsp/common/foot.jsp"%>

	<!-- Bootstrap core JavaScript navbar-fixed-bottom
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="static/js/basic.js"></script>
	<script src="static/js/common.js"></script>
	<script src="static/js/dataTable.js"></script>
	<script src="static/js/dateUtils.js"></script>
	<script src="static/js/daterangepicker.js"></script>
	<script src="static/js/daterange.by.js"></script>
	<script type="text/javascript">
	var table,navForm;
	$(document).ready(function() {
		var std = new Date();
		navForm = navForm.init({'showPlat':true,'showSid':true,'sidType':'Select','showDate':{'isShow':true,'endDate':std.format('yyyyMMdd')},'showBtn':true});
		$("#navPlat").val(108);
		$("#navPlat").change();
		getSid("#navPlat", "#navSid", true);
		
		$("#navSubmitBtn").click(function(){
			getTable();
		});
		$("#navSubmitBtn").trigger("click");
		
		$("#example").click(function(e){
			if($(e.target).is(".showDetail")){
				showDetail($(e.target));
			};
		});
	});
	
	function getTable(){
		var start = navForm.daterange.getStartDate("#navdaterange","YYYYMMDD");
		var end = navForm.daterange.getEndDate("#navdaterange","YYYYMMDD");
		var plat = $("#navPlat").val();
		var sid = $("#navSid").val();
		var bpid = sid.split("_")[1];
		
		var params ={
			id:"10000001|10000002","dataType":"dataTable",
			args:{
				_plat:plat,etm:end,stm:start,_bpid:bpid,table:"d_monitor_total"
			}
		};
		
		table = $("#example").DataTable({
		     "bFilter": true,
		     "pagingType" : "full_numbers",
		     "bDestroy" : true,
		     "bProcessing" : false,
		     "sAjaxSource" : "data/common/mysqlQuery.htm?params="+JSON.stringify(params),
		     "bServerSide" : true,
		     "ordering": false,
		     "aaSorting": [[ 0, "desc" ]],
		     "aoColumnDefs": [ { "bSortable": false}],
		     "sServerMethod": "POST",
		     "columns" : [
					{ "data": "tm"},
					{ 
						"data": "userCount",
						"render":function(data, type, row){
							return toThousands(row.userCount);
						}
					},
					{ 
						"data": "freeCoins",
						"render":function(data, type, row){
							return toThousands(row.freeCoins);
						}
					},
					{ 
						"data": "payCoins",
						"render":function(data, type, row){
							return toThousands(row.payCoins);
						}
					},
					{ 
						"data": "lostCoins",
						"render":function(data, type, row){
							return toThousands(row.lostCoins);
						}
					},
					{ 
						"render":function(data, type, row){
							return getPercent(row.lostCoins,row.freeCoins);
						}	
					},
					{
						"render":function(data, type, row){
							var html = '<a href="javascript:void(0)" class="showDetail" v="all">所有用户</a>&nbsp;&nbsp;&nbsp;&nbsp;'+
										  '<a href="javascript:void(0)" class="showDetail" v="multi">多人牌局</a>&nbsp;&nbsp;&nbsp;&nbsp;'+
										  '<a href="javascript:void(0)" class="showDetail" v="two">二人牌局</a>';
							return html;
						}
					}
		         ],
		       "oLanguage": commonData.oLanguage,
		       "dom": 't<"bottom fl"l><"bottom"p>'
			});
	};
	
	function showDetail(o){
		var v = $(o).attr('v');
		var tr = $(o).closest('tr');
		var row = table.row(tr);
		
		if(row.child.isShown()){
			row.child.hide();
			if($(o).hasClass('showSubTable')){
				$(o).removeClass('showSubTable');
				return;
			}else{
				tr.find('.showDetail').removeClass('showSubTable');
			}
		}
		$(o).addClass('showSubTable');
		var subTableId = "subtable_"+row.data().tm;
		var tip ='<span class="label label-primary" style="float: left;margin: 0 0 8px;font-size: 16px;">'+$(o).text()+'</span>';
		
		var plat = $("#navPlat").val();
		var sid = $("#navSid").val();
		var bpid = sid.split("_")[1];
		var params ={
			id:"10000001|10000002","dataType":"dataTable"
		};
		var downloadParams={
			id:"10000001"
		}
		var tableTitle = '',tableName='',columns=[];
		if(v=='all'){
			tableTitle = '<th>排名</th><th>用户名</th><th>用户ID</th><th></th><th></th><th>胜率</th><th>今日牌局</th><th>牌局总数</th><th>登陆IP</th><th>注册时间</th><th>历史付费总额</th>'+
							 '<th>好友数</th><th>当前身上筹码</th><th>当天玩牌所赢的筹码数</th><th>当天通过两人牌局赢的筹码数</th><th>当天输掉筹码数额</th>';
			tableName='d_monitor_base_user';
			var args = {
				_plat:plat,tm:row.data().tm,_bpid:bpid,table:tableName,sort:"lostCoins"
			};
			params.args=args;
			downloadParams.args=args;
			downloadParams.name="所有用户下载";
			downloadParams.title="用户名,用户ID,胜牌局数,输牌局数,今日牌局,登陆IP,注册时间,历史付费总额,好友数,当前身上筹码,当天玩牌所赢的筹码数,当天通过两人牌局赢的筹码数,当天输掉筹码数额";
			downloadParams.column="mnick,_uid,winCount,loseCount,playCount,loginip,mtime,mpay,mfcount,mmoney,winCoins,winCoins2,lostCoins";
			columns=[
				{"data":null},
				{ "data": "mnick"},
				{ "data": "_uid"},
				{ "data": "winCount","visible": false},
				{ "data": "loseCount","visible": false},
				{ 
					"render":function(data, type, row){
						return getPercent(row.winCount,row.winCount+row.loseCount);
					}	
				},
				{ "data": "playCount"},
				{ 
					"render":function(data, type, row){
						return row.winCount+row.loseCount;
					}	
				},
				{ "data": "loginip"},
				{ "data": "mtime"},
				{ "data": "mpay"},
				{ "data": "mfcount"},
				{
					"data": "mmoney",
					"render":function(data, type, row){
						return toThousands(row.mmoney);
					}
				},
				{
					"data": "winCoins",
					"render":function(data, type, row){
						return toThousands(row.winCoins);
					}
				},
				{
					"data": "winCoins2",
					"render":function(data, type, row){
						return toThousands(row.winCoins2);
					}
				},
				{
					"data": "lostCoins",
					"render":function(data, type, row){
						return toThousands(row.lostCoins);
					}
				}
	         ];
		}else{
			tableTitle = '<th>排名</th><th>用户名</th><th>用户ID</th><th></th><th></th><th>胜率</th><th>今日牌局数</th><th>牌局总数</th><th>登陆IP</th><th>注册时间</th>'+
							 '<th>历史付费总额</th><th>好友数</th><th>当前身上筹码数</th><th>当天所赢筹码数</th>';
			tableName='d_monitor_win_user';
			var args = {
				_plat:plat,tm:row.data().tm,_bpid:bpid,table:tableName,pcount:(v=='two'?2:0),sort:"winCoins"
			};
			params.args=args;
			downloadParams.args=args;
			downloadParams.name=(v=='two'?'二人牌局':'所有牌局')+"下载";
			downloadParams.title="用户名,用户ID,胜牌局数,输牌局数,今日牌局,登陆IP,注册时间,历史付费总额,好友数,当前身上筹码,当天所赢筹码数";
			downloadParams.column="mnick,_uid,winCount,loseCount,playCount,loginip,mtime,mpay,mfcount,mmoney,winCoins";
			columns=[
			   {"data":null},
				{ "data": "mnick"},
				{ "data": "_uid"},
				{ "data": "winCount","visible": false},
				{ "data": "loseCount","visible": false},
				{ 
					"render":function(data, type, row){
						return getPercent(row.winCount,row.winCount+row.loseCount);
					}	
				},
				{ "data": "playCount"},
				{ 
					"render":function(data, type, row){
						return row.winCount+row.loseCount;
					}	
				},
				{ "data": "loginip"},
				{ "data": "mtime"},
				{ "data": "mpay"},
				{ "data": "mfcount"},
				{
					"data": "mmoney",
					"render":function(data, type, row){
						return toThousands(row.mmoney);
					}
				},
				{
					"data": "winCoins",
					"render":function(data, type, row){
						return toThousands(row.winCoins);
					}
				}
	         ];
		}
		
		var t = tip+'<table id="'+subTableId+'" class="table table-bordered" style="margin-bottom: 0px;"><thead><tr>'+tableTitle+'</tr></thead></table>';
		row.child(t).show();
		
		function getIndex(index){
			var info = $('#'+subTableId).DataTable().page.info();
			return (parseInt(info.page)*parseInt(info.length))+index;
		}
		
		subtable = $("#"+subTableId).DataTable({
		     "bFilter": true,
		     "pagingType" : "full_numbers",
		     "bDestroy" : true,
		     "bProcessing" : false,
		     "sAjaxSource" : "data/common/mysqlQuery.htm?params="+JSON.stringify(params),
		     "bServerSide" : true,
		     "ordering": false,
		     "aaSorting": [[ 0, "desc" ]],
		     "aoColumnDefs": [ { "bSortable": false}],
		     "sServerMethod": "POST",
		     "columns" : columns,
		     "oLanguage": commonData.oLanguage,
		     "fnRowCallback":function(nRow,aData,iDataIndex){
					$('td:eq(0)',nRow).html(getIndex(parseInt(iDataIndex)+1));
	            },
	         "dom": 'Tt<"bottom fl"l><"bottom"p>',
    	    	"tableTools": {
					"sSwfPath": "css/swf/copy_csv_xls_pdf.swf",
					"aButtons": [{
						"sExtends":    "text",
						"sButtonText": "下载",
						"fnClick": function ( nButton, oConfig, oFlash ) {
							location.href = "data/common/download.htm?params="+JSON.stringify(downloadParams);
                    	}
					}]
			  	}
			});
	}
	</script>
</body>
</html>
