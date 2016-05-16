<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en-us">
<head>
<%@ include file="/WEB-INF/jsp/common/meta.jsp"%>
<title>历史查询-数据魔方</title>
<style>
#msgColContent .checkbox-inline + .checkbox-inline{
	margin-left:0;
}

#msgColContent .checkbox-inline{
	width:200px;
}
</style>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/top.jsp">
		<jsp:param name="nav" value="data" />
	</jsp:include>

	<jsp:include page="/WEB-INF/jsp/common/dataMenu.jsp">
		<jsp:param name="nav" value="customQuery" />
		<jsp:param name="subnav" value="hql" />
	</jsp:include>
	
	<div class="main">
		<jsp:include page="/WEB-INF/jsp/common/mainNav.jsp">
			<jsp:param name="actionName" value="历史查询" />
			<jsp:param name="helpId" value="82" />
		</jsp:include>
		
		<div class="panel panel-primary">
		  <div class="panel-body">
		  	<p style="color: red;font-weight: bold;font-size: 20px;">除了已有语句直接粘贴执行,生成语句请到通用导出操作:<a href="http://mf.oa.com/task/commonExport.htm" target="_blank">http://mf.oa.com/task/commonExport.jsp</a></p>
		  </div>
		</div>
		
		<form role="form">
			<div class="row">
				<div class="form-group col-xs-12">
					<input type="text" id="taskName" class="form-control" placeholder="任务名称,可选填,系统会有默认名称">
				</div>
			</div>
			<div class="row">
				<div class="form-group col-xs-12">
					<label class="sr-only" for="hql">hql</label>
					<textarea class="form-control" rows="5" id="hql" placeholder="请输入正确的hql,为了提高查询速度,时间范围尽量控制在最短" style="border-radius: 5px 5px 0 5px;"></textarea>
					<button class="btn btn-default" type="button" onclick="genHSql();" style="float:right;border-top:none;border-radius: 0pt 0pt 5px 5px;font-size:13px;"
						data-toggle="tooltip" data-placement="left" title='如果已经有语句，则不用操作这一步!'>生成语句</button>
				</div>
			</div>
			<div class="row">
				<div class="form-group col-xs-12">
					<input type="text" id="fieldsName" class="col-md-10 form-control" placeholder="列标题,用英文逗号分隔（如需选择用户信息，必须包含_plat,_uid两个字段）[不填系统会直接截取hql中的字段作为标题]">
				</div>
			</div>
			<div class="row">
				<div class="form-group col-xs-12">
					<%@include file="/WEB-INF/jsp/common/ucuser.jsp"%>
				</div>
			</div>
			<div class="row">
				<div class="form-group col-xs-12">
					<button type="button" class="btn btn-default" id="addtask" data-loading-text="提交中...">提交任务</button>
				</div>
			</div>
		</form>
	</div>
	<div class="modal fade" role="dialog" aria-hidden="true" id="showColMsg">
		<div class="modal-dialog modal-lg">
		   <div class="modal-content" style="max-height: 600px;overflow-y: scroll;">
		     <div class="modal-header">
		      <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
		      <h5 class="modal-title" id="msgColTitle">生成语句</h5></div>
		      <div class="modal-body">
			      	<form role="form">
			      		<div class="row">
					      	<div class="form-group col-md-6">
									<input type="checkbox" style="margin:2px 2px 3px 5px;;vertical-align:middle;" onclick="checkSite(0,this)" autocomplete="off">
									<label>PC</label>
									<input type="checkbox" style="margin:2px 2px 3px 5px;;vertical-align:middle;" onclick="checkSite(1,this)" autocomplete="off">
									<label>移动</label>
								</div>
							</div>
							
			      	<div class="row">
					      <div class="form-group col-md-6">
								<select id="plats" name="plat" class="form-control"></select>
							</div>
							<div class="form-group col-md-6">
								<select id="sid" name="sid" class="form-control multiselect" multiple="multiple" style="width: 20px;"></select>
							</div>
						</div>
						<div class="row">
							<div class="form-group col-md-6">
								<input type="text" id="stime" class="form-control time" placeholder="开始时间" autocomplete="off">
							</div>
							<div class="form-group col-md-6">
								<input type="text" id="etime" class="form-control time" placeholder="结束时间" autocomplete="off">
							</div>
						</div>
						<div class="row">
							<div class="form-group col-md-12">
								 <select id="tableNames" class="form-control" style="width: 100%"></select>
							</div>
						</div>
						<div class="row">
							<div class="form-group col-md-12">
								<label>请选择字段:</label>
								<div id="msgColContent"></div>
							</div>
						</div>
						<hr>
						<div class="row">
							<div class="form-group col-md-12">
								<input type="checkbox" value="1" id="distinctData"><span style="color: red;font-weight: bold;">去重数据(如果只导用户ID这种需求必须给勾上,可以减少好多数据量)</span>
							</div>
						</div>
						<div class="row">
							<div class="form-group col-md-12">
								<label>设置过滤条件:</label>
								<div id="">
									<div class="form-group col-md-2">
										<select id="filterColumns" class="form-control"></select>
									</div>
									<div class="form-group col-md-2">
										<select id="compare" class="form-control">
											<option value="str=">等于</option>
											<option value="<">小于</option>
											<option value="<=">小于或等于</option>
											<option value="!=">不等于</option>
											<option value=">">大于</option>
											<option value=">=">大于或等于</option>
											<option value="in">in</option>
											<option value="like">模糊匹配</option>
										</select>
									</div>
									<div class="form-group col-md-6">
										<input id="compareValue" type="text" class="form-control" placeholder="参数，如果使用in，多个参数请用英文逗号分隔">
									</div>
									<div class="form-group col-md-2">
										<button type="button" class="btn btn-default" onclick="addFilter();">添加</button>
									</div>
								</div>
								<div id="filterDiv"></div>
							</div>
						</div>
						<div class="row">
							<div class="form-group col-md-12">
								<label>设置数据格式:</label>
								<div id="">
									<div class="form-group col-md-2">
										<select id="formatColumns" class="form-control"></select>
									</div>
									<div class="form-group col-md-2">
										<select id="formatType" class="form-control" onchange="formatTypeChange();">
											<option value="0">日期格式化</option>
											<option value="1">ip格式化</option>
										</select>
									</div>
									<div class="form-group col-md-6">
										<div class="form-group col-md-8" id="timeFormatTypeDiv">
											<select id="timeFormatType" class="form-control">
												<option value="yyyy-MM-dd">yyyy-MM-dd</option>
												<option value="yyyy-MM-dd HH:mm:ss">yyyy-MM-dd HH:mm:ss</option>
											</select>											
										</div>
										<div class="form-group col-md-4">
											<label class="checkbox-inline">
												<input type="checkbox" value="保留原数据" id="keepOldData">保留原数据
											</label>											
										</div>
									</div>
									<div class="form-group col-md-2">
										<button type="button" class="btn btn-default" onclick="addFormat();">添加</button>
									</div>
								</div>
								<div id="formatDiv"></div>
							</div>
						</div>
					</form>
		      </div>
		      <div class="modal-footer">
		      	<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
		      	<button type="button" class="btn btn-primary" onclick="showHql();">确定</button>
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
	<script type="text/javascript" src="static/select2/js/select2.min.js"></script>
	<script src="static/js/dateUtils.js"></script>
	<script src="static/js/common.js"></script>
	<script src="static/js/singleHsql.js"></script>
	<script type="text/javascript">
		$.fn.modal.Constructor.prototype.enforceFocus =function(){};
		$(function(){
			$("#hsqlMenu").addClass("active");
			initDatetimepicker();
			MyHive.initSelect("tableNames");
			
			//getMultiPlat("#plats", "#sid");
			$("#plats").change(function() {
				getMultiSid("#plats","#sid",true,true);
			});
			$("#tableNames").change(function(){
				showColumns();
				$("#filterDiv").html("");
				$("#formatDiv").html("");
			});
			formatTypeChange();
			
			$("#taskName").val('历史查询 | '+new Date().format('yyyy-MM-dd hh:mm:ss'));
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
		
		$("#addtask").click(
			function() {
				var hql = $("#hql").val();
				if(!hql){
					showMsg("请输入hql语句！");
					return false;
				}
				
				var items = $('input:checkbox[name=items]:checked').map(function(){
					return this.value;
				}).get().join();
				var itemsName = $('input:checkbox[name=items]:checked').map(function(){
					return $(this).attr("valueName");
				}).get().join();
				
				var fieldsNameValue = $("#fieldsName").val();
				
				if(items){
					if(isEmpty(fieldsNameValue)){
						showMsg("标题不能为空!");
						return false;
					}else{
						var title = getSelectColumn(hql);
						if(isEmpty(title)){
							showMsg("语句不正确!");
							return false;
						}
						var oldTitles = fieldsNameValue.split(',');
						var values = title.split(',');
						
						if(oldTitles.length!=values.length){
							//showMsg("查询的列数和标题个数不对应,较正后再提交");
							//return false;
						}
					}
				
					if(fieldsNameValue.indexOf('_plat') == -1 || fieldsNameValue.indexOf('_uid') == -1){
						showMsg("选择用户信息，列标题必须包含_plat,_uid两个字段");
						return false;
					}
				}
				
				var taskName = $("#taskName").val();
				if(isEmpty(taskName)){
					showMsg("任务名称不能为空!");
					return false;
				}
				
				$('#addtask').button("loading");
				$.post("data/hql/hqlQuery.htm", {
					hql : $("#hql").val(),
					fieldsName : fieldsNameValue.replace(/ /g, ""),
					items : items,
					itemsName : itemsName,
					taskName : taskName,
				},
				function(data) {
					commonDo.addedTaskSuccess(data);
				},
				"json").error(function() { 
					commonDo.addedTaskError(); 
				});
			});
		
		$("#hql").blur(function(){
			var hql = $("#hql").val();
			var title = getSelectColumn(hql);
			
			if(!isEmpty(hql)){
				var fieldsNameValue = $("#fieldsName").val();
				if(!isEmpty(fieldsNameValue)){
					var oldTitles = fieldsNameValue.split(',');
					var values = title.split(',');
					if(oldTitles.length<values.length){
						for(var i=0;i<values.length;i++){
							if(oldTitles.length<(i+1)){
								var vs = values[i].trim().split(' ');
								var v = vs[vs.length-1].replaceAll('`',"");
								fieldsNameValue += ","+v;
							}
						}
						$("#fieldsName").val(fieldsNameValue);
					}else if(oldTitles.length>values.length){
						showMsg("填写的标题比查询出来的列数多:"+(oldTitles.length-values.length)+"个");
						return false;
					}
				}else{
					$("#fieldsName").val(title);
				}
			}
		});
		
		function getSelectColumn(hql){
			if(isEmpty(hql)){
				return '';
			}
			var lowerHQL = hql.toLowerCase();
			var selectIndexOf = lowerHQL.indexOf('select');
			var fromNext = lowerHQL.charAt(lowerHQL.indexOf(' from')+5);
			if(fromNext=='('||fromNext=='\n'){
			}else{
				fromNext=" ";
			}
			
			var fromIndexOf = lowerHQL.indexOf(' from'+fromNext);
			if(selectIndexOf==-1 || fromIndexOf==-1 || selectIndexOf>fromIndexOf){
				return '';
			}
			lowerHQL = lowerHQL.substring(selectIndexOf+6,fromIndexOf).trim();
			if(lowerHQL==''){
				return '';
			}
			if(lowerHQL=='*'){
				return '';
			}
			
			lowerHQL = removeSpecialCharacters(lowerHQL);
			
			var values = lowerHQL.split(',');
			
			var titleJson={};
			var title = '';
			for(var i=0;i<values.length;i++){
				var vs = values[i].trim().split(' ');
				var v = vs[vs.length-1].replaceAll('`',"");
				if(v in titleJson){
					v += "_0";
				}
				titleJson[v]=1;
				title += v+(i==values.length-1?"":",");
			}
			return title;
		}
		
		function removeSpecialCharacters(str){
			var replaceStart=0,replaceEnd=0;
			for(var i = 0; i < str.length; i++){
				if(str.charAt(i)=='('){
					replaceStart=i;
				}
				if(str.charAt(i)==')'){
					replaceEnd=i;
					
					var replaceStr = str.substring(replaceStart,replaceEnd+1);
					str = str.replace(replaceStr,'');
					return removeSpecialCharacters(str);
				}
			}
			return str;
		}
	</script>
</body>
</html>
