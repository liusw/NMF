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
<title>数据魔方</title>
<style type="text/css">
.main {
	margin-left: 170px;
	padding: 40px 10px;
}

.page-header {
	border-bottom: 1px solid #dadada;
	padding-bottom: 2px;
	margin: 20px 0 0 0;
}

hr {
	border-top-color: #dadada;
}

.quick-actions {
	display: block;
	list-style: none outside none;
	text-align: center;
	float: left;
	padding: 0;
	width: 100%;
}

.quick-actions li {
	float: left;
	line-height: 18px;
	margin: 0 10px 10px 0px;
	width:150px;
	overflow:hidden;
	height: 56px;
	position: relative;
	padding: 0;
}

.quick-actions li a:hover,.quick-actions li:hover {
	background: #2E363F;
}

.quick-actions li a {
	padding: 10px;
	display: block;
	color: #fff;
	font-size: 14px;
	font-weight: lighter;
}

.quick-actions li a p{
	margin-bottom: 2px;
	height: 18px;
	line-height: 18px;
	overflow: hidden;
}
.tableMsg{
	display: none;
}

.quick-actions .active{
 background:#27a9e3;
}

.export-actions li{
	line-height: 40px;
	margin: 0 10px 10px 0px;
	width:100px;
	height: 40px;
	position: relative;
	padding: 0;
}
.export-actions li a {
	padding: 0px;
	display: block;
	color: #fff;
	font-size: 14px;
	font-weight: lighter;
}

</style>
<script type="text/javascript" language="javascript">  
function autoHeight(id){
    var iframe = document.getElementById(id);//这里括号内的"runtime"其实就是上面的ID，要改成自己上面填写的ID。
    if(iframe.Document){//ie自有属性
        iframe.style.height = iframe.Document.documentElement.scrollHeight+20;
    }else if(iframe.contentDocument){//ie,firefox,chrome,opera,safari
        iframe.height = iframe.contentDocument.body.offsetHeight+20;
    }
}
</script>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/top.jsp">
		<jsp:param name="nav" value="docs" />
	</jsp:include>
	<jsp:include page="../docs/helpMenu.jsp">
		<jsp:param name="nav" value="index" />
		<jsp:param name="cid" value="" />
	</jsp:include>

	<div class="main">
		<h3>数据导出</h3>
		<ul class="quick-actions export-actions">
			<li class="bg_lg"><a href="task/commonExport.htm" target="_blank">通用导出</a></li>
			<li class="bg_lg"><a href="data/login.htm" target="_blank">流失</a></li>
		</ul>
		<iframe id="introIframe" name="introIframe" src="docs/article/findById.htm?id=412" frameborder="0" scrolling="no" style="border:0px;width:100%;background: #fff;" onload="autoHeight('introIframe');"></iframe>
		<h3>业务表</h3>
		<ul class="quick-actions">
			<li class="tc bg_lo" name="user_gambling" articleId="397"><a href="javascript:void(0)"><p>牌局</p><p>(user_gambling)</p></a></li>
			<li class="tc bg_lo" name="gamecoins_stream" articleId="394"><a href="javascript:void(0)"><p>金币流水</p><p>(gamecoins_stream)</p></a></li>
			<li class="tc bg_lo" name="user_order2" articleId="395"><a href="javascript:void(0)"><p>订单</p><p>(user_order2)</p></a></li>
			<li class="tc bg_lo" name="user_login" articleId="396"><a href="javascript:void(0)"><p>登录</p><p>(user_login)</p></a></li>
			<li class="tc bg_lo" name="user_signup" articleId="398"><a href="javascript:void(0)"><p>注册</p><p>(user_signup)</p></a></li>
			<li class="tc bg_lo" name="bankrupt" articleId="399"><a href="javascript:void(0)"><p>破产</p><p>(bankrupt)</p></a></li>
			<li class="tc bg_lo" name="user_uid" articleId="400"><a href="javascript:void(0)"><p>活跃</p><p>(user_uid)</p></a></li>
			<li class="tc bg_lo" name="admin_act_logs" articleId="401"><a href="javascript:void(0)"><p>管理员操作日志(查封)</p><p>(admin_act_logs)</p></a></li>
			<li class="tc bg_lo" name="winlog_mode" articleId="402"><a href="javascript:void(0)"><p>金币明细</p><p>(winlog_mode)</p></a></li>
			<li class="tc bg_lo" name="event_logs" articleId="403"><a href="javascript:void(0)"><p>事件</p><p>(event_logs)</p></a></li>
			<li class="tc bg_lo" name="mb_device_login_log" articleId="404"><a href="javascript:void(0)"><p>登录设备</p><p>(mb_device_login_log)</p></a></li>
			<li class="tc bg_lo" name="act_mb_contact" articleId="405"><a href="javascript:void(0)"><p>移动活动中心用户联系信息表</p><p>(act_mb_contact)</p></a></li>
			<li class="tc bg_lo" name="mtt_jbs_signup" articleId="406"><a href="javascript:void(0)"><p>锦标赛报名</p><p>(mtt_jbs_signup)</p></a></li>
			<li class="tc bg_lo" name="mtt_jbs_reward" articleId="407"><a href="javascript:void(0)"><p>锦标赛获奖</p><p>(mtt_jbs_reward)</p></a></li>
			<li class="tc bg_lo" name="mtt_jbs_info" articleId="408"><a href="javascript:void(0)"><p>锦标赛比赛结果</p><p>(mtt_jbs_info)</p></a></li>
			<li class="tc bg_lo" name="ipk_gamecoins_stream" articleId="409"><a href="javascript:void(0)"><p>ipk金币流水</p><p>(ipk_gamecoins_stream)</p></a></li>
			<li class="tc bg_lo" name="ipk_bycoins_stream" articleId="410"><a href="javascript:void(0)"><p>ipk博雅币流水</p><p>(ipk_bycoins_stream)</p></a></li>
			<li class="tc bg_lo" name="ipk_user_gambling" articleId="411"><a href="javascript:void(0)"><p>ipk牌局日志</p><p>(ipk_user_gambling)</p></a></li>
		</ul>
		<div style="clear: both;"></div>
		<div class="tableArticle tableMsg">
			<h3>详情</h3>
			<hr>
			<iframe id="tableArticleIframe" name="tableArticleIframe" frameborder="0" scrolling="no" style="border:0px;width:100%;background: #fff;" onload="autoHeight('tableArticleIframe');"></iframe>
		</div>
		<div class="tableDesc tableMsg">
			<h3>表结构说明</h3>
			<hr>
			<table id="example" class="table table-striped table-bordered display dtable" style="background: #fff;" cellspacing="0" width="100%">
        <thead>
            <tr>
                <th>字段</th>
                <th>描述</th>
                <th>类型</th>
                <th>HBase类型</th>
            </tr>
        </thead>
        <tbody id="tableDescId"></tbody>
    </table>
		</div>
	</div>
	<%@ include file="/WEB-INF/jsp/common/foot.jsp"%>

	<!-- Bootstrap core JavaScript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="static/js/basic.js"></script>
	<script src="static/js/common.js"></script>
	<script src="static/js/dataTable.js"></script>
	<script type="text/javascript">
		$(".tc").click(function(){
			var name = $(this).attr("name");
			var articleId = $(this).attr("articleId");


			$(".quick-actions").find("li").each(function(){
				$(this).removeClass("active");
			});
			$(this).addClass("active");
			
			$(".tableArticle").show();
			$("#tableArticleIframe").attr('src',"docs/article/findById.htm?id="+articleId);
			
			$(".tableDesc").show();
			$("#tableDescId").html('');
			var columns = getConfigColumns(name,'hive',true);
			if(columns && !isEmpty(columns)){
				$.each(columns, function(index, value) {
					var tr = "<tr><td>"+value.column_name+"</td><td>"+value.comment+"</td><td>"+(value.type==1?"分区字段":"普通字段")+"</td><td>"+value.valueType+"</td></tr>";
					$("#tableDescId").append(tr);
				});
			}
		});
	</script>
</body>
</html>
