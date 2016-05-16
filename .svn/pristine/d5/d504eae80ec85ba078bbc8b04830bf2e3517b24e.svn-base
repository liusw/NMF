<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en-us">
<head>
<%@ include file="/WEB-INF/jsp/common/meta.jsp"%>
<title>反刷分-数据魔方</title>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/top.jsp">
		<jsp:param name="nav" value="data" />
	</jsp:include>
	
	<jsp:include page="/WEB-INF/jsp/common/dataMenu.jsp">
		<jsp:param name="nav" value="brush" />
		<jsp:param name="subnav" value="brush" />
	</jsp:include>
	
	<div class="main">
		<jsp:include page="/WEB-INF/jsp/common/mainNav.jsp">
			<jsp:param name="actionName" value="反刷分" />
		</jsp:include>
		
		<form class="form" id="brushForm">
			<div class="row">
				<div class="col-xs-6">
					<div class="row">
						<div class="form-group col-xs-6">
							<label>平台站点：</label>
							<select id="plats" name="plats" class="form-control">
							</select>
						</div>
						<div class="form-group col-xs-6">
							<label>&nbsp;</label>
							<select id="sid" name="sid" class="form-control multiselect" multiple="multiple"></select>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-xs-6">
					<div class="row">
						<div class="form-group col-xs-6">
							<label>选择模板：</label>
							<select id="templateId" name="templateId" class="form-control">
								<option value="0">空模板</option>
							</select>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-xs-6">
					<div class="row">
						<div class="form-group col-xs-6">
							<label>时间类型：</label>
							<label class="radio-inline"><input type="radio" name="time" id="registerTime" value="registerTime" checked=checked referId="stime">注册时间</label>&nbsp;
 							<label class="radio-inline"><input type="radio" name="time" id="loginTime" value="loginTime" referId="stime">活跃时间</label>
 							<input type="hidden" id="stime" name="stime"/>
							<input type="text" id="rstime" name="rstime" class="form-control time" placeholder="开始时间" autocomplete="off">
						</div>
						<div class="form-group col-xs-6">
							<label>&nbsp;</label>
 							<input type="text" id="retime" name="retime" class="form-control time" placeholder="结束时间" autocomplete="off">
						</div>
					</div>
				</div>
			</div>
			
			<div class="row">
				<div class="col-xs-6">
					<div class="row">
						<div class="form-group col-xs-6">
							<label>好友数：</label>
							<input type="text" id="smfcount" name="smfcount" class="form-control"  autocomplete="off">
						</div>
						<div class="form-group col-xs-6">
							<label>&nbsp;</label>
							<input type="text" id="emfcount" name="emfcount" class="form-control"  autocomplete="off">
						</div>
					</div>
				</div>
			</div>
			
			<div class="row">
				<div class="col-xs-6">
					<div class="row">
						<div class="form-group col-xs-6">
							<label>等级：</label>
							<input type="text" id="smexplevel" name="smexplevel" class="form-control"  autocomplete="off">
						</div>
						<div class="form-group col-xs-6">
							<label>&nbsp;</label>
							<input type="text" id="emexplevel" name="emexplevel" class="form-control"  autocomplete="off">
						</div>
					</div>
				</div>
			</div>
			
			<div class="row">
				<div class="form-group col-xs-12">
					<label>邮箱后缀：</label>
					<br/>
					<label class="checkbox-inline">
						<input type="checkbox" name="email" value='hotmail.com' referId="emails">hotmail.com
					</label>
					<label class="checkbox-inline">
						<input type="checkbox" name="email" value='yahoo.com.hk' referId="emails">yahoo.com.hk
					</label>
					<label class="checkbox-inline">
						<input type="checkbox" name="email" value='hotmail.com.tw' referId="emails">hotmail.com.tw
					</label>
					<label class="checkbox-inline">
						<input type="checkbox" name="email" value='outlook.com' referId="emails">outlook.com
					</label>
						<label class="checkbox-inline">
						<input type="checkbox" name="email" value='msn.com' referId="emails">msn.com
					</label>
					<label class="checkbox-inline">
						<input type="checkbox" name="email" value='null' referId="emails">无邮箱
					</label>
					<label class="checkbox-inline">
						<input type="checkbox" name="email" value='other' referId="emails">其它
					</label>
					<input type="hidden" id="emails" name="emails"/>
				</div>
			</div>
			
			<div class="row">
				<div class="col-xs-6">
					<div class="row">
						<div class="form-group col-xs-6">
							<label>ip段前缀：</label>
							<select id="loginip" name="loginip" class="form-control">
								<option value="">请选择</option>
								<option value="1">1段</option>
								<option value="2">2段</option>
								<option value="3">3段</option>
								<option value="4">4段</option>
							</select>
						</div>
						<div class="form-group col-xs-6">
							<label>&nbsp;</label>
						</div>
					</div>
				</div>
			</div>
			
			<div class="row">
				<div class="col-xs-6">
					<div class="row">
						<div class="form-group col-xs-6">
							<label>登录数：</label>
							<input type="text" id="smentercount" name="smentercount" class="form-control"  autocomplete="off">
						</div>
						<div class="form-group col-xs-6">
							<label>&nbsp;</label>
							<input type="text" id="ementercount" name="ementercount" class="form-control"  autocomplete="off">
						</div>
					</div>
				</div>
			</div>
			
			
			<div class="row">
				<div class="form-group col-xs-6">
					<label>语言：</label>
					<br/>
					<label class="checkbox-inline">
						<input type="checkbox" name="language" value='zh_TW' referId="languages">繁体
					</label>
					<label class="checkbox-inline">
						<input type="checkbox" name="language" value='zh_CN' referId="languages">简体
					</label>
					<label class="checkbox-inline">
						<input type="checkbox" name="language" value='en_US' referId="languages">美国英语
					</label>
					<label class="checkbox-inline">
						<input type="checkbox" name="language" value='th_TH' referId="languages">泰语
					</label>
					<label class="checkbox-inline">
						<input type="checkbox" name="language" value='null' referId="languages">空
					</label>
					<label class="checkbox-inline">
						<input type="checkbox" name="language" value='other' referId="languages">其它
					</label>
					<input type="hidden" name="languages" id="languages"/>
				</div>
			</div>
			
			<div class="row">
				<div class="form-group col-xs-6">
					<label>最后登录地区：</label>
					<br/>
					<label class="checkbox-inline">
						<input type="checkbox" name="loginipRegion" value='China' referId="loginipRegions">中国
					</label>
					<label class="checkbox-inline">
						<input type="checkbox" name="loginipRegion" value='Thailand' referId="loginipRegions">泰国
					</label>
					<label class="checkbox-inline">
						<input type="checkbox" name="loginipRegion" value='Taiwan' referId="loginipRegions">台湾
					</label>
					<label class="checkbox-inline">
						<input type="checkbox" name="loginipRegion" value='Hong Kong' referId="loginipRegions">香港
					</label>
					<label class="checkbox-inline">
						<input type="checkbox" name="loginipRegion" value='other' referId="loginipRegions">其它
					</label>
					<input type="hidden" name="loginipRegions" id="loginipRegions"/>
				</div>
			</div>
			
			<div class="row">
				<div class="col-xs-6">
					<div class="row">
						<div class="form-group col-xs-6">
							<label>当前游戏币：</label>
							<input type="text" id="smmoney" name="smmoney" class="form-control"  autocomplete="off">
						</div>
						<div class="form-group col-xs-6">
							<label>&nbsp;</label>
							<input type="text" id="emmoney" name="emmoney" class="form-control"  autocomplete="off">
						</div>
					</div>
				</div>
			</div>
			
			<div class="row">
				<div class="col-xs-6">
					<div class="row">
						<div class="form-group col-xs-6">
							<label>历史台费：</label>
							<input type="text" id="svmoney" name="svmoney" class="form-control"  autocomplete="off">
						</div>
						<div class="form-group col-xs-6">
							<label>&nbsp;</label>
							<input type="text" id="evmoney" name="evmoney" class="form-control"  autocomplete="off">
						</div>
					</div>
				</div>
			</div>
			
			<div class="row">
				<div class="col-xs-6">
					<div class="row">
						<div class="form-group col-xs-6">
							<label>赢牌局数：</label>
							<input type="text" id="swinCount" name="swinCount" class="form-control"  autocomplete="off">
						</div>
						<div class="form-group col-xs-6">
							<label>&nbsp;</label>
							<input type="text" id="eloseCount" name="eloseCount" class="form-control"  autocomplete="off">
						</div>
					</div>
				</div>
			</div>
			
			
			<div class="row">
				<div class="col-xs-6">
					<div class="row">
						<div class="form-group col-xs-6">
							<label>牌局总数：</label>
							<input type="text" id="smpcount" name="smpcount"  class="form-control"  autocomplete="off">
						</div>
						<div class="form-group col-xs-6">
							<label>&nbsp;</label>
							<input type="text" id="empcount" name="empcount" class="form-control"  autocomplete="off">
						</div>
					</div>
				</div>
			</div>
			
			<div class="row">
				<div class="col-xs-6">
					<div class="row">
						<div class="form-group col-xs-6">
							<label>2人牌局占比：</label>
							<input type="text" id="s2gRate" name="s2gRate" class="form-control"  autocomplete="off">
						</div>
						<div class="form-group col-xs-6">
							<label>&nbsp;</label>
							<input type="text" id="e2gRate" name="e2gRate" class="form-control"  autocomplete="off">
						</div>
					</div>
				</div>
			</div>
			
			<div class="row">
				<div class="col-xs-6">
					<div class="row">
						<div class="form-group col-xs-6">
							<label>玩牌胜率：</label>
							<input type="text" id="splayRate" name="splayRate" class="form-control"  autocomplete="off">
						</div>
						<div class="form-group col-xs-6">
							<label>&nbsp;</label>
							<input type="text" id="eplayRate" name="eplayRate" class="form-control"  autocomplete="off">
						</div>
					</div>
				</div>
			</div>
			
			<div class="row">
				<div class="col-xs-6">
					<div class="row">
						<div class="form-group col-xs-6">
							<label>免费游戏币：</label>
							<input type="text" id="smfree" name="smfree" class="form-control"  autocomplete="off">
						</div>
						<div class="form-group col-xs-6">
							<label>&nbsp;</label>
							<input type="text" id="emfree" name="emfree" class="form-control"  autocomplete="off">
						</div>
					</div>
				</div>
			</div>
			
			<div class="row">
				<div class="col-xs-6">
							<label>是否付费：</label><br/>
							<label class="radio-inline"><input type="radio" name="pay" referId="mpay" value="true">是&nbsp;</label>
 							<label class="radio-inline"><input type="radio" name="pay" referId="mpay" value="false">否&nbsp;</label>
 							<label class="radio-inline"><input type="radio" name="pay" referId="mpay" value="all">所有&nbsp;</label>
 							<input type="hidden" name="mpay" id="mpay"/>
				</div>
			</div>
			<br/>
			<div class="row">
				<div class="col-xs-6">
							<label>是否有头像：</label><br/>
							<label class="radio-inline"><input type="radio" name="hasicon" referId="mhasicon" value="true">是&nbsp;</label>
 							<label class="radio-inline"><input type="radio" name="hasicon" referId="mhasicon" value="false">否&nbsp;</label>
 							<label class="radio-inline"><input type="radio" name="hasicon" referId="mhasicon" value="all">所有&nbsp;</label>
 							<input type="hidden" name="mhasicon" id="mhasicon"/>
				</div>
			</div>
			<br/>
			
			<div class="row" id="nameDiv" style="display:none;">
				<div class="col-xs-6">
					<div class="row">
						<div class="form-group col-xs-6">
							<label>模板名称：</label>
							<input type="text" id="name" name="name" class="form-control" placeholder="模板名称">
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="form-group col-xs-12">
					<button type="button" class="btn btn-default" id="addtask" data-loading-text="提交中...">提交任务</button>&nbsp;&nbsp;
					<button type="button" class="btn btn-default" id="saveTemplate" data-loading-text="保存中...">保存模板</button>
					&nbsp;&nbsp;<label class="checkbox-inline"><input type="checkbox" name="ifAutorun" id="ifAutorun" referId="autorun">每天自动执行</label>
					<input type="hidden" name="autorun" id="autorun"/>
				</div>
			</div>
		</form>
	</div>
	
	<div class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true" id="confirmBrushTask">
		<div class="modal-dialog modal-lg">
		   <div class="modal-content">
		     <div class="modal-header">
		      <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
		      <h5 class="modal-title">确认</h5></div>
		      <div class="modal-body" id="brushMsgContent"></div>
		      <div class="modal-footer">
		      	<button type="button" class="btn btn-default" id="replaceBrushBtn" onclick="addTask('replace');">替换</button>
		      	<button type="button" class="btn btn-default" id="addBrushBtn" onclick="addTask('add');">新增</button>
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
	<script src="static/js/common.js"></script>
	<script src="static/js/brush.js"></script>
	<script type="text/javascript">
		$(function() {
			initBrush();
		});
	</script>
</body>
</html>
