<%--
  Created by IntelliJ IDEA.
  User: Midnight
  Date: 08.10.2018
  Time: 19:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<t:genericpage pageName="Error" withHeader="false">
    <div class="errors-container">
        ${pageContext.request.getAttribute("errors")}
    </div>
    <div class="button"><a href="${pageContext.request.contextPath}/find_match">Back to duel menu</a></div>
</t:genericpage>