<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<jsp:useBean id="login" class="java.lang.String" scope="session"/>
<jsp:useBean id="workerRequests" class="java.util.ArrayList" scope="request"/>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>Interface</title>
    <style>
        table {
            border: solid 1px black;
        }
        th {
            border: solid 1px black;
        }
        td {
            border: solid 1px black;
        }
    </style>
</head>
<body>
    <form action="/jj/workerInterface" method="post" enctype="application/x-www-form-urlencoded">
        <security:csrfInput/>
        <p>
            <label>
                Hello, ${login}!
                Here you can count limits for credit requests and  make decisions for them.
            </label>
        </p>

        <table>
            <caption>Requests for credit</caption>
            <tbody>
                <tr>
                    <th>ID</th>
                    <th>Client</th>
                    <th>Client ITN</th>
                    <th>Liability</th>
                    <th>Type of credit</th>
                    <th>Sum</th>
                    <th>Term</th>
                    <th>Revenue</th>
                    <th>Profit</th>
                    <th>Net assets</th>
                    <th>Total assets</th>

                    <th>Limit</th>
                    <th>Decision</th>

                </tr>
                <c:forEach var="requests" items="${workerRequests}">
                    <tr>
                        <td>
                            <c:out value="${requests.getId()}"/>
                        </td>
                        <td>
                            <c:out value="${requests.getAccountant().getClientName()}"/>
                        </td>
                        <td>
                            <c:out value="${requests.getAccountant().getClientITN()}"/>
                        </td>
                        <td>
                            <c:out value="${requests.getAccountant().getLiability()}"/>
                        </td>
                        <td>
                            <c:out value="${requests.getCreditType()}"/>
                        </td>
                        <td>
                            <c:out value="${requests.getSum()}"/>
                        </td>
                        <td>
                            <c:out value="${requests.getTerm()}"/>
                        </td>
                        <td>
                            <c:out value="${requests.getRevenue()}"/>
                        </td>
                        <td>
                            <c:out value="${requests.getProfit()}"/>
                        </td>
                        <td>
                            <c:out value="${requests.getNetAssets()}"/>
                        </td>
                        <td>
                            <c:out value="${requests.getTotalAssets()}"/>
                        </td>
                        <td>
                            <c:out value="${requests.getLimit()}"/>

                        </td>

                        <td>
                            <c:out value="${requests.getDecision()}"/>
                        </td>

                    </tr>
                </c:forEach>

            </tbody>
        </table>
        <p>
            <input type="submit" name="countLimitButton" value="Count limit">
        </p>
        <p>
            <input type="submit" name="makeDecisionButton" value="Make decision">
        </p>

        <p>
            <input value="Log out" type="submit" name="logOutButtonFromWorker">
        </p>
    </form>

</body>
</html>
