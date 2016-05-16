<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div id="sub-nav">
	<ul id="nav-tab" class="nav nav-tabs">
		<c:if test="${param.tab=='gambling'}">
		<li <c:if test="${param.subnav=='gambling'}">class="active"</c:if>><a href="<%=(request.getContextPath())%>/data/gambling.htm">统计</a></li>
		<li <c:if test="${param.subnav=='gamblingTimeDistribute'}">class="active"</c:if>><a href="<%=(request.getContextPath())%>/data/gamblingTimeDistribute.htm">时长分布</a></li>
		<li <c:if test="${param.subnav=='gblType'}">class="active"</c:if>><a href="<%=(request.getContextPath())%>/data/gblType.htm">牌型分布</a></li>
		<li <c:if test="${param.subnav=='gblCount'}">class="active"</c:if>><a href="<%=(request.getContextPath())%>/data/gblCount.htm">牌数分布</a></li>
		<li <c:if test="${param.subnav=='gamblingRank'}">class="active"</c:if>><a href="<%=(request.getContextPath())%>/data/gamblingRank.htm">2人牌局排行[泰语]</a></li>
		<li <c:if test="${param.subnav=='daobi'}">class="active"</c:if>><a href="<%=(request.getContextPath())%>/data/daobi.htm">倒币去向</a></li>
		<li <c:if test="${param.subnav=='growthcurve'}">class="active"</c:if>><a href="<%=(request.getContextPath())%>/data/growthcurve.htm">成长曲线</a></li>
		</c:if>
		<c:if test="${param.tab=='active'}">
		<li <c:if test="${param.subnav=='activeView'}">class="active"</c:if>><a href="<%=(request.getContextPath())%>/data/activeView.htm">活跃概况</a></li>
		<li <c:if test="${param.subnav=='activeDetail'}">class="active"</c:if>><a href="<%=(request.getContextPath())%>/data/activeDetail.htm">活跃详情</a></li>
		<li <c:if test="${param.subnav=='activeLost'}">class="active"</c:if>><a href="<%=(request.getContextPath())%>/data/activeLost.htm">流失统计</a></li>
		</c:if>
		<c:if test="${param.tab=='pay'}">
		<li <c:if test="${param.subnav=='order'}">class="active"</c:if>><a href="<%=(request.getContextPath())%>/data/order.htm">订单查询</a></li>
		<li <c:if test="${param.subnav=='qizhaData'}">class="active"</c:if>><a href="<%=(request.getContextPath())%>/data/qizhaData.htm">欺诈查询</a></li>
		<li <c:if test="${param.subnav=='toolSale'}">class="active"</c:if>><a href="<%=(request.getContextPath())%>/data/toolSale.htm">道具售出</a></li>
		<li <c:if test="${param.subnav=='payDistribute'}">class="active"</c:if>><a href="<%=(request.getContextPath())%>/data/payDistribute.htm">付费分布</a></li>
		</c:if>
	</ul>
</div>
<script src="static/js/jquery-1.9.1.js"></script>
<script type="text/javascript">
</script>