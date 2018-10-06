<%--
  Created by IntelliJ IDEA.
  User: Midnight
  Date: 01.10.2018
  Time: 21:40
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:genericpage pageName="Auth">

    <div class="login-block">
        <form action="${pageContext.request.contextPath}/menu" name="login" method="post">
            <input type="text" name="login_name" placeholder="Login" value="${pageContext.request.getAttribute(\"login\")}" class="login-name" required>
            <input type="password" name="login_pass" placeholder="Password" class="login-pass" required>
            <input type="submit" name="login_submit" value="Go!" class="button">
        </form>
    </div>

    <c:if test="${pageContext.request.getAttribute(\"errors\")}">
        <p class="error-block">
            ${pageContext.request.getAttribute(\"errors\")}
        </p>
    </c:if>

</t:genericpage>
