<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="" pageEncoding="UTF-8" %>
<%@attribute name="pageName" required="true" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>BLADERS - ${pageName}</title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/styles.css" />">
</head>
<body>
<div class="container">
    <header>
        <div class="banner">
            <span class="logo">Bladers</span>
        </div>
    </header>

    <jsp:doBody />

</div>
</body>
</html>