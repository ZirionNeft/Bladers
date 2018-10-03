<%--
  Created by IntelliJ IDEA.
  User: Midnight
  Date: 01.10.2018
  Time: 21:40
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <title>BLADERS - Authorisation</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <div class="container">
        <header>
            <div class="banner">
                <span class="logo">Bladers</span>
            </div>
        </header>

        <div class="login-block">
            <form action="${pageContext.request.contextPath}/login" name="login" method="post">
                <input type="text" name="login_name" placeholder="Login" value="${login}" class="login-name" required>
                <input type="password" name="login_pass" placeholder="Password" class="login-pass" required>
                <input type="submit" name="login_submit" value="Go!" class="login-btn">
            </form>
        </div>

        <c:if test="${errors != ''}">
            <p class="error-block">
                ${errors}
            </p>
        </c:if>

    </div>
</body>
</html>
