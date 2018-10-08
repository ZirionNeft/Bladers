<%--
  Created by IntelliJ IDEA.
  User: Midnight
  Date: 03.10.2018
  Time: 18:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:genericpage pageName="Menu">
    <p class="game-info">You logged as: <b>${pageContext.session.getAttribute("playerName")}</b></p>

    <ul class="game-menu">
        <li class="button"><a href="${pageContext.request.contextPath}/find_match">Duel</a></li>
        <li class="button"><a href="${pageContext.request.contextPath}/logout">Logout</a></li>
    </ul>

</t:genericpage>
