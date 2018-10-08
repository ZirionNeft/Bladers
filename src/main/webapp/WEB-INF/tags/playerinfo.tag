<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="" pageEncoding="UTF-8" %>
<%@attribute name="playerName" required="true" %>
<%@attribute name="playerHealth" required="true" %>
<%@attribute name="playerDamage" required="true" %>
<%@attribute name="isEnemy" required="true" %>
<%@attribute name="myTurn" required="true" %>

<div class="duel-player-info">
    <div class="health-progress-bar" data-health="${playerHealth}">${playerHealth}</div>
    <div class="player-damage">${playerDamage}</div>
    <div class="player-name">${playerName}</div>
    <c:if test="${isEnemy == false}">
        <form action="${pageContext.request.contextPath}/match" method="POST"><button class="player-attack" type="submit" <c:if test="${!myTurn}">disabled</c:if>>Attack</button></form>
    </c:if>
</div>

