<%@ page import="zirioneft.bladers.entity.Match" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.LinkedList" %><%--
  Created by IntelliJ IDEA.
  User: Midnight
  Date: 04.10.2018
  Time: 23:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    String errors = "";
    HashMap<String, String> myInfo = null;
    HashMap<String, String> enemyInfo = null;
    Match m = (Match)session.getAttribute("match");
    LinkedList<String> battleLog = null;
    String who = null;


    if (m == null) {
        errors += "Error: Invalid match! - <i>Match instance not found</i><br>";
    } else {
        HttpSession enemy = m.getEnemySession(session);

        myInfo = m.getPlayerInfo(session);
        enemyInfo = m.getPlayerInfo(enemy);
        battleLog = m.getBattleLog();

        if (!m.isStarted() && m.getSecRemaining() > 0) {
            response.setIntHeader("refresh", 5);
        } else {
            who = m.whoTurn();

            if (who == null) {
                who = "end";
            } else if (who.equals(myInfo.get("name"))) {
                who = "me";
            } else if (who.equals(enemyInfo.get("name"))) {
                who = "enemy";
                response.setIntHeader("refresh", 3);
            }
        }

        request.setAttribute("turn", who);
        request.setAttribute("battleLog", battleLog);
        request.setAttribute("remaining", m.getSecRemaining().toString());
        request.setAttribute("myInfo", myInfo);
        request.setAttribute("enemyInfo", enemyInfo);
    }

    request.setAttribute("errors", errors);
    if (!errors.isEmpty())
        pageContext.getRequest().getRequestDispatcher(request.getContextPath()+"/WEB-INF/views/error.jsp").forward(request, response);
%>

<t:genericpage pageName="Duel" withHeader="false">

    <t:playerinfo playerName="${ enemyInfo.get('name')}" playerHealth="${enemyInfo.get('health')}" playerDamage="${enemyInfo.get('damage')}" isEnemy="true" myTurn="true">
    </t:playerinfo>

    <div class="central-info">
        <span class="text">
            <c:if test="${remaining  > 0}">
                ${remaining} seconds before the start of the duel!
            </c:if>
            <c:if test="${turn.equals('me')}">
                Your turn!
            </c:if>
            <c:if test="${turn.equals('enemy')}">
                Enemy turn!
            </c:if>
            <c:if test="${turn.equals('end')}">
                The match is over!
                <button class="button"><a href="${pageContext.request.contextPath}/find_match"></a></button>
            </c:if>
        </span>
    </div>

    <div class="duel-info">
        <c:forEach items="${battleLog}" var="item">
            ${item}
        </c:forEach>
    </div>

    <t:playerinfo playerName="${ myInfo.get('name')}" playerHealth="${myInfo.get('health')}" playerDamage="${ myInfo.get('damage')}" isEnemy="false" myTurn="${ turn.equals('me')}">
    </t:playerinfo>
</t:genericpage>
