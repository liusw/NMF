<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en-us">
<head>
<%@ include file="/WEB-INF/jsp/common/meta.jsp"%>
<title>牌局-数据魔方</title>
<style type="text/css">
.pdl{
	padding-left: 20px;
}
</style>
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
			<jsp:param name="tabActive" value="gambling" />
		</jsp:include>
		<jsp:include page="/WEB-INF/jsp/common/mainNav2.jsp" />
		
		<div class="main2Body">
			<jsp:include page="/WEB-INF/jsp/common/tabMenu.jsp">
				<jsp:param name="tab" value="gambling" />
				<jsp:param name="subnav" value="daobi" />
			</jsp:include>
			
			<div id="myTabContent" class="tab-content">
				<div class="tab-pane fade content-form main-content active in">
					<div id="processInfo">
						<div class="panel panel-default">
						  <div class="panel-body">
						  	需求:上传一批用户,导出他的牌局游戏币流向(通过倒币的玩家ID，导出倒币的去向)<br/>
						  	1、类型说明,输：上传用户在牌局中是输的,赢：上传用户在牌局中是赢的;<br/>
						  	2、导出文件中的"获取游戏币"是在牌局中赢取的用户币<br/>
						  	3、上传的文件请不要带标题,并且文件只有一列(用户ID),只允许上传txt或者csv格式文件;导出文件标题为:<span style="color: red;">上传uid,对家uid,获取游戏币,日期</span>
						  </div>
						</div>
						<form id="process_1" processId="1" processType="3" processName="上传文件流程" class="form-inline" method="post" enctype="multipart/form-data">
							<div id="uploadDiv" style="margin-top: 15px;">
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
							</div>
						</form>
						<hr>
						<form id="process_2" processId="2" processType="1" processName="日志数据流程" class="form-inline">
							<div class="form-group">
								<label>类型:</label>
								<select id="getType" class="form-control" autocomplete="off">
									<option value="1">输</option>
									<option value="2">赢</option>
								</select>
							</div>
							<div class="form-group">
								<label>牌局人数:</label>
								<select id="pcount1" name="pcount1" class="form-control multiselect" multiple="multiple" autocomplete="off">
									<option value="2">2人</option>
									<option value="3">3人</option>
									<option value="4">4人</option>
									<option value="5">5人</option>
									<option value="6">6人</option>
									<option value="7">7人</option>
									<option value="8">8人</option>
									<option value="9">9人</option>
								</select>
							</div>
							<div class="form-group">
								<label class="pdl">盲注:</label>
								<select id="blindmin1" name="blindmin1" class="form-control multiselect" multiple="multiple" autocomplete="off">
									<option value="1">1注</option>
									<option value="2">2注</option>
									<option value="5">5注</option>
									<option value="10">10注</option>
									<option value="50">50注</option>
									<option value="100">100注</option>
									<option value="200">200注</option>
									<option value="500">500注</option>
									<option value="1000">1000注</option>
									<option value="2000">2000注</option>
									<option value="2500">2500注</option>
									<option value="3000">3000注</option>
									<option value="3500">3500注</option>
									<option value="4000">4000注</option>
									<option value="4500">4500注</option>
									<option value="5000">5000注</option>
									<option value="6000">6000注</option>
									<option value="7000">7000注</option>
									<option value="8000">8000注</option>
									<option value="10000">10000注</option>
									<option value="15000">15000注</option>
									<option value="20000">20000注</option>
									<option value="25000">25000注</option>
									<option value="50000">50000注</option>
									<option value="100000">100000注</option>
									<option value="120000">120000注</option>
									<option value="150000">150000注</option>
									<option value="200000">200000注</option>
									<option value="250000">250000注</option>
									<option value="300000">300000注</option>
									<option value="500000">500000注</option>
									<option value="1000000">1000000注</option>
									<option value="1500000">1500000注</option>
									<option value="2000000">2000000注</option>
									<option value="5000000">5000000注</option>
									<option value="10000000">10000000注</option>
									<option value="20000000">20000000注</option>
									<option value="50000000">50000000注</option>
									<option value="100000000">100000000注</option>
									<option value="250000000">250000000注</option>
								</select>
							</div>
							<div class="form-group">
								<label class="pdl">桌子类型:</label>
								<select id="gtype1" name="gtype1" class="form-control multiselect" autocomplete="off">
									<option value="0">新手场</option>
									<option value="1">初级场</option>
									<option value="2">中级场</option>
									<option value="3">高级场</option>
									<option value="6">快速场</option>
									<option value="4">淘汰场</option>
									<option value="7">晋级赛</option>
									<option value="8">淘金日赛</option>
									<option value="9">淘金周赛</option>
									<option value="11">锦标赛</option>
									<option value="14">模拟场</option>
									<option value="20">荷官场</option>
									<option value="30">当面玩</option>
									<option value="43">好友房</option>
								</select>
							</div>
						</form>
						<hr>
						<button type="button" class="btn btn-primary" id="submitTask">提交任务</button>
						<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
					</div>
				</div>
			</div>
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
	<script src="static/js/dateUtils.js"></script>
	<script src="static/js/jquery.form.js"></script>
	<script src="static/js/dataTable.js"></script>
	<script src="static/js/common.js"></script>
	<script type="text/javascript">
	var navForm;
	$(function() {
		navForm.init({'showPlat':true,'showSid':true,'sidType':'MultiSelect','showDate':{'isShow':true},
			platChangeCallback: updateNavMultiSid
		});
		
		$('#pcount1').mymultiselect({
			  'multiValues':'',
				selectClose:function (val) {
					$('#pcount1').attr({'selectValues':val.getValues()});
		        }
		});
		$('#blindmin1').mymultiselect({
			  'multiValues':'',
				selectClose:function (val) {
					$('#blindmin1').attr({'selectValues':val.getValues()});
		        }
		});
		$('#gtype1').mymultiselect({
			  'multiValues':'',
				selectClose:function (val) {
					$('#gtype1').attr({'selectValues':val.getValues()});
		        }
		});
		Task.init();
	});
	
	var Task={
			init:function(){
				var _this =  this;
				$("#process_1 .upload_btn").click(function(){_this.upload();});
				$("#submitTask").unbind('click').click(function(){Task.submit();});
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
			submit:function(){
				var _this = this,plat = $("#navPlat").val(),svid = $("#navPlat").find("option:selected").attr('svid');
				var startTime = navForm.daterange.getStartDate("#navdaterange","YYYYMMDD");
				var endTime = navForm.daterange.getEndDate("#navdaterange","YYYYMMDD");
				if (isEmpty(startTime)|| isEmpty(endTime)) {
					alert("必须选择要导出的开始与结束时间");
					return false;
				}
				
				if(isEmpty($("#process_1").find('[name=fileName]').val()) || isEmpty($("#process_1").find('[name=fileName]').val())){
					alert("请上传文件后再提交");
					return false;
				}
				
				var taskInfo ={
					taskName:"导出用户",
					plat:plat,
					svid:svid
				};
				
				var processList = [];
				var processInfo = {processId:"process_1",processType:3};
				
				processInfo.fileName= $("#process_1").find("[name=fileName]").val();
				processInfo.columnNames = "_uid";
				processInfo.columns = "c0";
				processList.push(processInfo);
				
				var pcount = $("#pcount1").attr("selectValues");
				var blindmin = $("#blindmin1").attr("selectValues");
				var gtype = $("#gtype1").attr("selectValues");
				
				var tableName = " user_gambling ";
				var on = "a.tid=b.tid and a.bid=b.bid and a.starttime=b.starttime";
				if(parseInt(plat)>=100 && parseInt(plat)<=200){
					tableName = " ipk_user_gambling ";
					on = "a.tid=b.tid and a.bid=b.bid";
				}
				
				var processInfo2 = {processId:"process_2",processType:4};
				
				var where = "where plat="+plat+" and tm between "+startTime+" and "+endTime+" ";
				if(pcount && !isEmpty(pcount)){
					where += " and pcount in ("+pcount+") ";
				}
				if(blindmin && !isEmpty(blindmin)){
					where += " and blindmin in ("+blindmin+") ";
				}
				if(gtype && !isEmpty(gtype)){
					where += " and gtype in ("+gtype+") ";
				}
				
				if($("#getType").val()==2){
					processInfo2.hql= "select t1.c0,b.`_uid`,sum(cast(a.cgcoins as bigint)) cgcoins,a.tm from "+
					"temp__process__process_1 t1 left outer join "+
					"(select cgcoins,`_uid`,tid,bid,starttime,tm from "+ tableName + where +" and cast(cgcoins as bigint)>0) a on(a.`_uid`=t1.c0) left outer join "+ 
					"(select cgcoins,`_uid`,tid,bid,starttime from " + tableName + where +" and cast(cgcoins as bigint)<0) b on("+on+") "+
					" where a.`_uid`!=b.`_uid` and b.`_uid` is not null group by t1.c0,b.`_uid`,a.tm";
				}else{
					processInfo2.hql= "select t1.c0,b.`_uid`,sum(cast(b.cgcoins as bigint)) cgcoins,a.tm from "+
						"temp__process__process_1 t1 left outer join "+
						"(select cgcoins,`_uid`,tid,bid,starttime,tm from " + tableName + where +" and cast(cgcoins as bigint)<0) a on(a.`_uid`=t1.c0) left outer join "+ 
						"(select cgcoins,`_uid`,tid,bid,starttime from "+ tableName + where +" and cast(cgcoins as bigint)>0) b on("+on+") "+
						" where a.`_uid`!=b.`_uid` and b.`_uid` is not null group by t1.c0,b.`_uid`,a.tm";
				}
				
				processInfo2.columns= "_uid,buid,cgcoins,tm";
				processInfo2.columnNames="上传uid,对家uid,获取游戏币,日期";
				processInfo2.dependOn='1';
				processInfo2.depend="process_1___temp__process__process_1";
				processList.push(processInfo2);
				
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
</script>
</body>
</html>
</body>
</html>