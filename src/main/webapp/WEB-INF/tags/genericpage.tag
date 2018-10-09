<%@ tag import="zirioneft.bladers.controllers.PlayerController" %>
<%@ tag import="zirioneft.bladers.Utils" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="" pageEncoding="UTF-8" %>
<%@attribute name="pageName" required="true" %>
<%@attribute name="withHeader" required="false" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>BLADERS - ${pageName}</title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/styles.css" />">
    <link rel="icon" type="image/png" href="<c:url value="/img/sword.png" />">
</head>
<body>
<div class="container">
    <c:if test="${withHeader != false}">
    <header>
        <div class="banner">
            <span class="logo">Bladers</span>
        </div>
    </header>
    </c:if>

    <jsp:doBody />

    <%
        request.setAttribute("dbReq", PlayerController.getQueriesCount(session));
        request.setAttribute("dbMs", PlayerController.getQueriesMs(session));
        request.setAttribute("pageMs", Utils.getTime(session)-(PlayerController.getQueriesMs(session)*PlayerController.getQueriesCount(session)));
        PlayerController.resetQueriesInfo(session);
    %>
    <span class="technical-info">page: ${pageMs}ms, db: ${dbReq}req (average: ${dbMs}ms)</span>
</div>
</body>
</html>