<%@ page contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="login" class="java.lang.String" scope="session"/>
<jsp:useBean id="allWorkerRequests" class="java.util.ArrayList" scope="request"/>
<html>
<head>
    <title>Decision</title>
</head>
<body>
<form action="/jj/makingDecision" method="post" enctype="application/x-www-form-urlencoded">
    <p>
        <label>
            ${login}, choose request for making decision
        </label>
    </p>
    <p>
        <label>
            Choose request ID:
            <select name="idWorkerRequests">
                <c:forEach var="workerRequest" items="${allWorkerRequests}">
                    <option>${workerRequest.getId()}</option>
                </c:forEach>
            </select>
        </label>
    </p>
    <p>
        <input type="submit" name="makingDecisionButton" value="Make decision">
    </p>
    <p>
        <input type="submit" name="cancelButton" value="Cancel">
    </p>
</form>
</body>
</html>
