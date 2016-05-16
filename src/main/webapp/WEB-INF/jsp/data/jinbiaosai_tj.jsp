<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<table id="example" class="table table-striped table-bordered display dtable">
	<thead>
		<tr>
			<th></th><th>平台</th><th>时间</th><th>开场次数</th><th>未开场次</th><th>人次</th><th>人数</th><th>总局数</th><th>金币流水</th><th>金币发放</th>
			<th>金币消耗</th><th>实物发放</th><th>总手数</th><th>Rebuy次数</th><th>Addon次数</th><th>奖励人数</th><th>游戏总时长</th><th>游戏币买入次数</th><th>资格券买入次数</th>
			<th>机器人参赛人次</th><th>机器人参赛人数</th><th>机器人金币流水</th><th>机器人金币发放</th><th>机器人金币消耗</th>
		</tr>
	</thead>
	<tfoot></tfoot>
</table>
<div class="panel panel-primary">
  <div class="panel-body">
	当参赛人数>=(保底奖池/参赛费）时，盈余=参赛人数*服务费；<br/>
	当参赛人数<(保底奖池/参赛费）时，盈余=参赛人数*（参赛费+服务费）-保底奖池<br/>
	当参赛人数>=(保底奖池/参赛费）时，ROI=（参赛费+服务费）/参赛费<br/>
	当参赛人数<(保底奖池/参赛费）时，ROI=参赛人数*（参赛费+服务费）/保底奖池<br/>
  </div>
</div>
	<script type="text/javascript">
	var table;
	$(document).ready(function() {
		$("#example").click(function(e){
			if($(e.target).is(".details-control")){
				showDetail($(e.target));
			};
		});
	});
	
	function navSubmitBtn_tj(){
		getTable();
	}
	
	function getTable(){
		var start = navForm.daterange.getStartDate("#navdaterange","YYYYMMDD");
		var end = navForm.daterange.getEndDate("#navdaterange","YYYYMMDD");
		
		if(isEmpty($("#navPlat").val()) || isEmpty(start) || isEmpty(end)){
			alert("平台和时间都不能为空");
			return;
		}
		
		var params ={
			id:"10000001|10000002","dataType":"dataTable",
			args:{
				columns:"id,plat,tm,gameCount,cancelCount,userSum,userCount,totalPlayCount,coinsStream,sendCoins,sendPhysical,rebuyCount,addonCount,rewardCount,gameTime,coinsByCount,ticketByCount,"+
				"robotrenci,robotrenshu,robotcoinsStream,robotSendCoins",
				plat:$("#navPlat").val(),etm:end,stm:start,table:"d_jinbiaosai"
			}
		};
		
		table = $("#example").DataTable({
		     "bFilter": true,
		     "pagingType" : "full_numbers",
		     "bDestroy" : true,
		     "bProcessing" : false,
		     "sAjaxSource" : "data/common/mysqlQuery.htm?params="+JSON.stringify(params),
		     "bServerSide" : true,
		     "ordering": true,
		     "aaSorting": [[ 2, "desc" ]],
		     "aoColumnDefs": [ { "bSortable": false, "aTargets": [ 10 , 12 ] }],
		      "sServerMethod": "POST",
		     "columns" : [
					{
		            "className":'details-control',
		            "orderable":false,
		            "data":null,
		            "defaultContent": ''
		        	},
		        	{ "data": "plat","visible": false},
					{ "data": "tm"},
					{ "data": "gameCount"},
					{ "data": "cancelCount"},
					{ "data": "userSum"},
					{ "data": "userCount"},
					{ "data": "totalPlayCount"},
					{ "data": "coinsStream"},
					{ "data": "sendCoins"},
					{ "data": "consumeCoins",
						"render":function(data, type, row){
							return row.coinsStream-row.sendCoins;
						}
					},
					{ "data": "sendPhysical"},
					/*{ "data": "sendPhysical",
						"render":function(data, type, row){
							return "";
						}
					},*/
					{ "data": "totalHandCount",
						"render":function(data, type, row){
							return row.userSum+row.rebuyCount+row.addonCount;
						}
					},
					{ "data": "rebuyCount"},
					{ "data": "addonCount"},
					{ "data": "rewardCount"},
					{ "data": "gameTime"},
					{ "data": "coinsByCount"},
					{ "data": "ticketByCount"},
					{ "data": "robotrenci"},
					{ "data": "robotrenshu"},
					{ "data": "robotcoinsStream"},
					{ "data": "robotSendCoins"},
					{ "data": "robotconsumeCoins",
						"render":function(data, type, row){
							return row.robotcoinsStream-row.robotSendCoins;
						}
					}
		         ],
		       "oLanguage": commonData.oLanguage,
		       "dom": 'Tt<"bottom fl"l><"bottom"p>',
	    	    "tableTools": {
						"sSwfPath": "css/swf/copy_csv_xls_pdf.swf",
						"aButtons": [{
							"sExtends":    "text",
							"sButtonText": "下载",
							"fnClick": function ( nButton, oConfig, oFlash ) {
								location.href = "data/jinbiaosai/down.htm?stm="+start+"&etm="+end+"&plat="+$("#navPlat").val();
	                    	}
						}]
				  }
			});
	};
	
	function showDetail(o){
		var tr = $(o).closest('tr');
		var row = table.row(tr);
		
		if ( row.child.isShown() ) {
			row.child.hide();
			tr.removeClass('shown');
		}else {
			var subTableId = "subtable_"+row.data().tm;
		
			var params ={
				id:"10000008",args:{plat:row.data().plat,tm:row.data().tm,table:"d_jinbiaosaiDetail",sort:"sigTime"}
			};
			
			var data = getJsonData("data/common/mysqlQuery.htm",{params:JSON.stringify(params)});
			if(data!=null && data.result==1) {
				var t = '<table id="'+subTableId+'" class="table table-bordered" style="margin-bottom: 0px;">'+
				'<thead>'+
					'<tr>'+
					 	'<td>名称</td>'+
						'<td>场次</td>'+
						'<td>svid</td>'+
						'<td>状态</td>'+
						//'<td>解散时已报名人数</td>'+
						'<td>总报名人数</td>'+
						'<td>退赛人数</td>'+
						'<td>参加人数</td>'+
						'<td>总手数</td>'+
						'<td>Rebuy次数</td>'+
						'<td>Addon次数</td>'+
						'<td>金币流水</td>'+
						'<td>金币发放</td>'+
						'<td>金币消耗</td>'+
						'<td>实物发放</td>'+
						'<td>奖励人数</td>'+
						'<td>游戏总时长</td>'+
						'<td>结束时盲注</td>'+
						'<td>游戏币买入次数</td>'+
						'<td>资格券买入次数</td>'+
						'<td>ROI</td>'+
						'<td>盈余</td>'+
						'<td>保底奖池</td>'+
						'<td>参赛费</td>'+
						'<td>服务费</td>'+
						'<td>机器人总报名人数</td>'+
						'<td>机器人金币流水</td>'+
						'<td>机器人金币发放</td>'+
						'<td>机器人金币消耗</td>'+
					'</tr>'+
				'</thead>'+
				'<tbody>';
				
				$.each(data.loop, function(index, value) {
					var playCount = value.applyCount-value.leaveCount;
					var fee = value.entry_fee-value.service_fee;
					var ROI = '';
					var profit='';//盈余
					if(playCount>=(value.guarantee/fee)){//参赛人数>=(保底奖池/参赛费）
						profit = playCount*value.service_fee;//盈余=参赛人数*服务费
						ROI=getPercent(value.entry_fee,fee);//ROI=（参赛费+服务费）/参赛费
					}else{//参赛人数<(保底奖池/参赛费）
						profit = playCount*value.entry_fee-value.guarantee;//盈余=参赛人数*（参赛费+服务费）-保底奖池
						ROI=getPercent(playCount*value.entry_fee,value.guarantee);//ROI=参赛人数*（参赛费+服务费）/保底奖池
					}
					
					t += '<tr>'+
					'<td>'+(!value.showName?(!value.subname?'':value.subname):value.showName)+'</td>'+
						'<td>'+new Date(value.sigTime*1000).format('hh:mm')+'</td>'+
						'<td>'+value.svid+'</td>'+
						'<td>'+(value.status==0?"开场":"未开场")+'</td>'+
						//'<td>'+ value.applyCount_Cancel+ '</td>'+
						'<td>'+value.applyCount+'</td>'+
						'<td>'+value.leaveCount+'</td>'+
						//'<td>'+value.playCount+'</td>'+
						'<td>'+(value.applyCount-value.leaveCount)+'</td>'+
						//'<td>'+value.totalHandCount+'</td>'+
						'<td>'+(value.applyCount-value.leaveCount+value.rebuyCount+value.addonCount)+'</td>'+
						'<td>'+value.rebuyCount+'</td>'+
						'<td>'+value.addonCount+'</td>'+
						'<td>'+value.coinsStream+'</td>'+
						'<td>'+value.sendCoins+'</td>'+
						'<td>'+(value.coinsStream-value.sendCoins)+'</td>'+
						'<td>'+(!value.sendPhysical?"":value.sendPhysical)+'</td>'+
						'<td>'+value.rewardCount+'</td>'+
						'<td>'+value.gameTime+'</td>'+
						'<td>'+value.endBlind+'</td>'+
						'<td>'+value.coinsByCount+'</td>'+
						'<td>'+value.ticketByCount+'</td>'+
						'<td>'+ROI+'</td>'+
						'<td>'+profit+'</td>'+
						'<td>'+value.guarantee+'</td>'+
						'<td>'+fee+'</td>'+
						'<td>'+value.service_fee+'</td>'+
						
						'<td>'+value.robotApplyCount+'</td>'+
						'<td>'+value.robotCoinsStream+'</td>'+
						'<td>'+value.robotSendCoins+'</td>'+
						'<td>'+(value.robotCoinsStream-value.robotSendCoins)+'</td>'+
					'</tr>';
				});
				t +='</tbody></table>';
				row.child(t).show();
				subtable = $("#"+subTableId).DataTable({
					 "ordering": false,
					"dom": 't<"bottom fl"l><"bottom"p>'
				});
			}
			tr.addClass('shown');
		}
    }
	</script>
</body>
</html>
