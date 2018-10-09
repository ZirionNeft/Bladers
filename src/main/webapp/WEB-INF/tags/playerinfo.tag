<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="" pageEncoding="UTF-8" %>
<%@attribute name="playerName" required="true" %>
<%@attribute name="playerHealth" required="true" %>
<%@attribute name="playerDamage" required="true" %>
<%@attribute name="isEnemy" required="true" %>
<%@attribute name="myTurn" required="true" %>

<div class="duel-player-info ${isEnemy}">
    <c:if test="${isEnemy == false}">
        <form action="${pageContext.request.contextPath}/match" method="POST" class="player-attack-form"><button class='player-attack <c:if test="${!myTurn}">disabled</c:if>' type="submit" <c:if test="${!myTurn}">disabled</c:if>></button></form>
    </c:if>
    <div class="player-name">${playerName}</div>
    <div class="health-progress-bar">HP: ${playerHealth}</div>
    <div class="player-damage">DMG: ${playerDamage}</div>
</div>

