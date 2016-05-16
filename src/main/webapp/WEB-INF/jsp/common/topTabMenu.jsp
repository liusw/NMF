<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<ul class="tab-actions">
	<li <c:if test="${param.tabActive=='dailyData'}">class="active"</c:if>><a href="data/dailyData.htm">每日统计</a></li>
	<li <c:if test="${param.tabActive=='order'}">class="active"</c:if>><a href="data/order.htm">付费</a></li>
	<li <c:if test="${param.tabActive=='coinsFlow'}">class="active"</c:if>><a href="data/gamecoins.htm">金币流水</a></li>
	<li <c:if test="${param.tabActive=='gambling'}">class="active"</c:if>><a href="data/gambling.htm">牌局</a></li>
	<li <c:if test="${param.tabActive=='ban'}">class="active"</c:if>><a href="data/ban.htm">禁封</a></li>
	<li <c:if test="${param.tabActive=='userInfo'}">class="active"</c:if>><a href="data/nonmain.htm">用户信息</a></li>
	<li <c:if test="${param.tabActive=='login'}">class="active"</c:if>><a href="data/login.htm">登录</a></li>
	<li <c:if test="${param.tabActive=='incomeSituation'}">class="active"</c:if>><a href="data/incomeSituation.htm">系统结余</a></li>
	<li <c:if test="${param.tabActive=='toollog'}">class="active"</c:if>><a href="data/toollog.htm">道具结余</a></li>
</ul>