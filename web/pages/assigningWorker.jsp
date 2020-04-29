<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<jsp:useBean id="boss" class="java.lang.String" scope="session"/>
<jsp:useBean id="allWorkerLogins" class="java.util.ArrayList" scope="request"/>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>Assignment</title>
</head>
<body>
<form action="/jj/assigningWorker" method="post" enctype="application/x-www-form-urlencoded">
    <security:csrfInput/>
    <p>
        <label>
            ${boss}, choose worker for  request
        </label>
    </p>
    <p>
        <label>
            Write request ID:
            <input type="text" name="requestIDField">
        </label>
    </p>
    <p>
        <label>
            Worker's login:

            <select name="workerLogins">
                <c:forEach var="workerLogin" items="${allWorkerLogins}">
                    <option>${workerLogin}</option>
                </c:forEach>
            </select>

        </label>
    </p>
    <p>
        <input type="submit" name="assignWorkerButton" value="Assign worker">
    </p>
    <p>
        <input type="submit" name="cancelButton" value="Cancel">
    </p>
</form>
</body>
</html>
