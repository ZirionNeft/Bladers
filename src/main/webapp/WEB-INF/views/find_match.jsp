<%--
  Created by IntelliJ IDEA.
  User: Midnight
  Date: 06.10.2018
  Time: 23:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:genericpage pageName="Menu">
    <p class="game-info">Your rating points: <b>${pageContext.request.getAttribute("rating")}</b></p>

    <ul class="game-menu">
        <li class="button"><a href="${pageContext.request.contextPath}/match">Find An Opponent</a></li>
        <li class="button"><a href="${pageContext.request.contextPath}/menu">Back</a></li>
    </ul>

</t:genericpage>
