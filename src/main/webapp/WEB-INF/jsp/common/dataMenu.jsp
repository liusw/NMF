<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!--sidebar-menu-->
<div id="sidebar">
  <ul class="sidebar_ul">
  	<li class="cutover_li <c:if test="${param.nav=='index'}">active</c:if>"><a href="data/index.htm" class="li_a"><i class="glyphicon glyphicon-home"></i> 首页</a>
  	</li>
  	<li class="cutover_li <c:if test="${param.nav=='businessData'}">active</c:if>"><a href="javascript:void(0);" class="li_a"><i class="glyphicon glyphicon-th-large"></i> 定制数据<span class="glyphicon glyphicon-collapse-up"></span></a>
    	<ul <c:if test="${param.nav!='businessData'}">style="display: none;"</c:if>>
        	<li <c:if test="${param.subnav=='tableData'}">class="active"</c:if>><a href="data/dailyData.htm"><i class="glyphicon glyphicon-user"></i> 用户数据</a></li>
        	<li <c:if test="${param.subnav=='dollTicket'}">class="active"</c:if>><a href="data/dollTicket.htm"><i class="glyphicon glyphicon-usd"></i> 玩偶兑奖</a></li>
			<li <c:if test="${param.subnav=='monitorActiveUser'}">class="active"</c:if>><a href="data/monitorActiveUser.htm"><i class="glyphicon glyphicon-usd"></i> 活跃小号监控</a></li>
			<%--
			<li <c:if test="${param.subnav=='rebateLog'}">class="active"</c:if>><a href="data/rebateLog.htm"><i class="glyphicon glyphicon-filter"></i> 返利统计</a></li>
			--%>
			<li <c:if test="${param.subnav=='heguan'}">class="active"</c:if>><a href="data/heguan.htm"><i class="glyphicon glyphicon-filter"></i> 美女荷官</a></li>
    	</ul>
    </li>
    <li class="cutover_li <c:if test="${param.nav=='customQuery'}">active</c:if>"><a href="javascript:void(0);" class="li_a"><i class="glyphicon glyphicon-th-large"></i> <font color="#FF6600">数据导出</font><span class="glyphicon glyphicon-collapse-up"></span></a>
    	<ul <c:if test="${param.nav!='customQuery'}">style="display: none;"</c:if>>
        	<li <c:if test="${param.subnav=='commonExport'}">class="active"</c:if>><a href="task/commonExport.htm"><i class="glyphicon glyphicon-user"></i> 通用导出&nbsp;&nbsp;<img src="static/images/new.png" height="10px;"></a></li>
    		<li class="popoverTipLi<c:if test="${param.subnav=='hql'}"> active</c:if>" data-container="body" data-toggle="popover" data-placement="right" data-content="该功能除了不能导出今天未入库的数据以外，其他日期的数据都可以在上面处理，适用于分析大批量数据">
    			<a href="data/hql.htm"><i class="glyphicon glyphicon-wrench"></i> <span> 自定义语句查询</span></a>
    		</li>
    		<li class="popoverTipLi<c:if test="${param.subnav=='task'}"> active</c:if>" data-container="body" data-toggle="popover" data-placement="right" data-content="该功能提供文件上传、自定义SQL等多种方式多个流程关联查询，流程之间的结果互相依赖，后面的流程依赖前面的流程结果,适用于较为复杂的查询。同历史查询一样，该功能除了不能导出今天未入库的数据以外，其他日期的数据都可以在上面处理">
    			<a href="task/task.htm"><i class="glyphicon glyphicon-link"></i> <span> 多流程查询</span></a>
    		</li>
    		<li class="popoverTipLi<c:if test="${param.subnav=='hbsql'}"> active</c:if>" data-container="body" data-toggle="popover" data-placement="right" data-content="该功能仅提供单用户查询，查询时一般需要提供_plat/_uid或者_plat/_uid/_tm等参数,不支持数据导出">
    			<a href="data/hbsql.htm"><i class="glyphicon glyphicon-bold"></i> <span> 单用户查询</span></a>
    		</li>
    	</ul>
    	<ul class="quick-actions">
        <li class="bg_lr">
        	<a target="_blank" style="color: #fff;font-size: 16px;padding-left:5px;line-height: 28px;text-align: center;" href="docs/home.jsp">点击查看操作文档</a> </li>
      </ul>
    </li>
    <li class="cutover_li <c:if test="${param.nav=='lc'}">active</c:if>"><a href="javascript:void(0);" class="li_a"><i class="glyphicon glyphicon-th-large"></i> LC功能<span class="glyphicon glyphicon-collapse-up"></span></a>
    	<ul <c:if test="${param.nav!='lc'}">style="display: none;"</c:if>>
   		 	<li <c:if test="${param.subnav=='userSource'}">class="active"</c:if>><a href="data/userSource.htm"><i class="glyphicon glyphicon-record"></i> 用户来源</a></li>
    		<li <c:if test="${param.subnav=='activeView'}">class="active"</c:if>><a href="data/activeView.htm"><i class="glyphicon glyphicon-usd"></i> 活跃概况</a></li>
    		<li <c:if test="${param.subnav=='integrationStatist'}">class="active"</c:if>><a href="data/integrationStatist.htm"><i class="glyphicon glyphicon-stats"></i> 积分分布</a></li>
    		<li <c:if test="${param.subnav=='loginCoinsDistribute'}">class="active"</c:if>><a href="data/loginCoinsDistribute.htm"><i class="glyphicon glyphicon-stats"></i> 登录金币分布</a></li>
    	    <li <c:if test="${param.subnav=='slotMacStats'}">class="active"</c:if>><a href="data/slotMacStats.htm"><i class="glyphicon glyphicon-stats"></i> 老虎机</a></li>
    	    <li <c:if test="${param.subnav=='monthRetainStat'}">class="active"</c:if>><a href="data/monthRetainStat.htm"><i class="glyphicon glyphicon-stats"></i> 月留存统计</a></li>
    	    <li <c:if test="${param.subnav=='weekRetainStat'}">class="active"</c:if>><a href="data/weekRetainStat.htm"><i class="glyphicon glyphicon-stats"></i> 周留存统计</a></li>
    	    <li <c:if test="${param.subnav=='eventAnalysis'}">class="active"</c:if>><a href="data/eventAnalysis.htm"><i class="glyphicon glyphicon-stats"></i> 事件分析</a></li>
    	</ul>
    </li>
    <li class="cutover_li <c:if test="${param.nav=='bisaichang'}">active</c:if>"><a href="javascript:void(0);" class="li_a"><i class="glyphicon glyphicon-screenshot"></i> 比赛场<span class="glyphicon glyphicon-collapse-up"></span></a>
    <ul <c:if test="${param.nav!='bisaichang'}">style="display: none;"</c:if>>
   		 	<li <c:if test="${param.subnav=='jinbiaosai'}">class="active"</c:if>><a href="data/jinbiaosai.htm"><i class="glyphicon glyphicon-filter"></i> 锦标赛</a></li>
   		 	<li <c:if test="${param.subnav=='taotaisai'}">class="active"</c:if>><a href="data/taotaisai.htm"><i class="glyphicon glyphicon-filter"></i> 淘汰赛</a></li>
   		 	<li <c:if test="${param.subnav=='duobaoqibing2'}">class="active"</c:if>><a href="data/duobaoqibing2.htm"><i class="glyphicon glyphicon-filter"></i> 夺宝奇兵</a></li>
   		 	<li <c:if test="${param.subnav=='duobaoqibing3'}">class="active"</c:if>><a href="data/duobaoqibing3.htm"><i class="glyphicon glyphicon-filter"></i> 夺宝奇兵_分站点</a></li>
    	</ul>
    </li>
    <li class="cutover_li <c:if test="${param.nav=='chart'}">active</c:if>"><a href="javascript:void(0);" class="li_a"><i class="glyphicon glyphicon-signal"></i> 实时图表<span class="glyphicon glyphicon-collapse-up"></span></a>
    	<ul <c:if test="${param.nav!='chart'}">style="display: none;"</c:if>>
    	  <li <c:if test="${param.subnav=='netStatus'}">class="active"</c:if>><a href="chart/netStatus.htm"><i class="glyphicon glyphicon-sort-by-attributes-alt"></i> 网络监控</a></li>
        <li <c:if test="${param.subnav=='pingLog'}">class="active"</c:if>><a href="chart/pinglog.htm"><i class="glyphicon glyphicon-sort-by-attributes-alt"></i> 网络延迟</a></li>
        <li <c:if test="${param.subnav=='pingstalogs'}">class="active"</c:if>><a href="chart/pingstalogs.htm"><i class="glyphicon glyphicon-transfer"></i> 网络连接</a></li>
        <li <c:if test="${param.subnav=='ccutime'}">class="active"</c:if>><a href="chart/ccutime.htm"><i class="glyphicon glyphicon-retweet"></i> 网关延迟</a></li>
        <li <c:if test="${param.subnav=='bag'}">class="active"</c:if>><a href="chart/bag.htm"><i class="glyphicon glyphicon-briefcase"></i> 口袋德州</a></li>
        <li <c:if test="${param.subnav=='onlineCount'}">class="active"</c:if>><a href="chart/onlineCount.htm"><i class="glyphicon glyphicon-retweet"></i> 百度91</a></li>
      </ul>
    </li>
    <li class="cutover_li <c:if test="${param.nav=='rate'}">active</c:if>"><a href="javascript:void(0);" class="li_a"><i class="glyphicon glyphicon-align-justify"></i> 牌局概率<span class="glyphicon glyphicon-collapse-up"></span></a>
    	<ul <c:if test="${param.nav!='rate'}">style="display: none;"</c:if>>
        <li <c:if test="${param.subnav=='shoupaiRate'}">class="active"</c:if>><a href="rate/shoupaiRate.htm"><i class="glyphicon glyphicon-sort-by-attributes-alt"></i> 手牌概率</a></li>
        <li <c:if test="${param.subnav=='fapaiRate'}">class="active"</c:if>><a href="rate/fapaiRate.htm"><i class="glyphicon glyphicon-transfer"></i> 发牌概率</a></li>
        <li <c:if test="${param.subnav=='startHandRate'}">class="active"</c:if>><a href="rate/startHandRate.htm"><i class="glyphicon glyphicon-transfer"></i> 起手牌</a></li>
        <li <c:if test="${param.subnav=='endHandRate'}">class="active"</c:if>><a href="rate/endHandRate.htm"><i class="glyphicon glyphicon-transfer"></i> 成牌牌型概率</a></li>
        <li <c:if test="${param.subnav=='greatARate'}">class="active"</c:if>><a href="rate/greatARate.htm"><i class="glyphicon glyphicon-transfer"></i> 起手遇更好A概率</a></li>
        <li <c:if test="${param.subnav=='greatDuadRate'}">class="active"</c:if>><a href="rate/greatDuadRate.htm"><i class="glyphicon glyphicon-transfer"></i> 起手遇更好对子概率</a></li>
      </ul>
    </li>
    <li class="cutover_li <c:if test="${param.nav=='brush'}">active</c:if>"><a href="javascript:void(0);" class="li_a"><i class="glyphicon glyphicon glyphicon-trash"></i> 反刷分<span class="glyphicon glyphicon-collapse-up"></span></a>
    	<ul  <c:if test="${param.nav!='brush'}">style="display: none;"</c:if>>
			<li <c:if test="${param.subnav=='brush'}">class="active"</c:if>><a href="data/brush.htm"><i class="glyphicon glyphicon glyphicon-file"></i> 规则配置</a></li>
			<li <c:if test="${param.subnav=='black'}">class="active"</c:if>><a href="data/brushData.htm"><i class="glyphicon glyphicon glyphicon-eye-close"></i> 黑名单</a></li>
     </ul>
     </li>
    <%--<li class="cutover_li <c:if test="${param.nav=='dataRqmt'}">active</c:if>"><a href="javascript:void(0);" class="li_a"><i class="glyphicon glyphicon glyphicon-trash"></i> 需求管理<span class="glyphicon glyphicon-collapse-up"></span></a>
    	<ul  <c:if test="${param.nav!='dataRqmt'}">style="display: none;"</c:if>>
    		<li <c:if test="${param.subnav=='myList'}">class="active"</c:if>> <a href="dataRqmt/listMyDataRqmt.htm"><i class="glyphicon glyphicon-user"></i> <span>我的需求</span></a> </li>
   		<li <c:if test="${param.subnav=='list'}">class="active"</c:if>> <a href="dataRqmt/listDataRqmt.htm"><i class="glyphicon glyphicon-globe"></i> <span>所有需求</span></a> </li>
     	</ul>
    </li>--%>
  		<li class="cutover_li <c:if test="${param.nav=='clienterror'}">active</c:if>"><a href="javascript:void(0);" class="li_a"><i class="glyphicon glyphicon glyphicon-trash"></i>客户端异常<span class="glyphicon glyphicon-collapse-up"></span></a>
		  <ul  <c:if test="${param.nav!='clienterror'}">style="display: none;"</c:if>>
			  <li <c:if test="${param.subnav=='history'}">class="active"</c:if>> <a href="clienterror/hisClientError.htm"><i class="glyphicon glyphicon-globe"></i> <span>异常信息</span></a> </li>
			  <%--<li <c:if test="${param.subnav=='today'}">class="active"</c:if>> <a href="clienterror/todayClientError.htm"><i class="glyphicon glyphicon-globe"></i> <span>今日数据</span></a> </li>--%>
		  </ul>
   </li>
  </ul>
</div>
<script src="static/js/jquery-1.9.1.js"></script>
<script type="text/javascript">
$(function(){
	//$("#sidebar").height($("#sidebar").height()-80);
	$("#sidebar").find(".cutover_li").each(function(){
		if(!$(this).hasClass("active")){
			$(this).find("ul").hide();
		}
	});
	$("#sidebar").find(".active").each(function(){
		if($(this).hasClass('cutover_li') && !$(this).hasClass("showul")){
			$(this).find("ul").show();
			$(this).addClass("showul");
			$(this).find("span:first").removeClass("glyphicon-collapse-up").addClass("glyphicon-collapse-down");
		}
	});
	
	$(".popoverTipLi").mouseenter(function(){
		$(this).popover('show');
	}).mouseleave(function(){
		$(this).popover('hide');
	});
});

$(".li_a").click(function(){
	$("#sidebar").find(".cutover_li").each(function(){
		$(this).find("ul").hide();
		$(this).removeClass("showul");
	});
	if($(this).parent().hasClass('showul')){
		$(this).parent().find("ul").hide();
		$(this).parent().removeClass("showul");
		$(this).find("span").removeClass("glyphicon-collapse-down").addClass("glyphicon-collapse-up");
	}else{
		$(this).parent().find("ul").show();
		$(this).parent().addClass("showul");
		$(this).find("span").removeClass("glyphicon-collapse-up").addClass("glyphicon-collapse-down");
	}
});
</script>