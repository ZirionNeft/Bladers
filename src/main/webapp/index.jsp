<%@ page import="zirioneft.bladers.Utils" %>
<%@ page import="zirioneft.bladers.controllers.PlayerController" %><%--
  Created by IntelliJ IDEA.
  User: Midnight
  Date: 01.10.2018
  Time: 21:40
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%
    Utils.startTime(session);
    PlayerController.resetQueriesInfo(session);
    if(session.getAttribute("isLogged") != null){
        if((boolean)session.getAttribute("isLogged"))
        {
            request.getRequestDispatcher("/WEB-INF/views/menu.jsp").forward(request, response);
            return;
        }
    }

%>
<t:genericpage pageName="Auth">

    <div class="login-block">
        <form action="${pageContext.request.contextPath}/menu" name="login" method="post">
            <input type="text" name="login_name" placeholder="Login" value="${login}" class="login-name" required>
            <input type="password" name="login_pass" placeholder="Password" class="login-pass" required>
            <input type="submit" name="login_submit" value="Sign In" class="button padding">
        </form>
    </div>

    <c:if test="${pageContext.request.getAttribute('errors') != null}">
        <p class="error-block">
            ${pageContext.request.getAttribute('errors')}
        </p>
    </c:if>

</t:genericpage>
