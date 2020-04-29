<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<jsp:useBean id="boss" class="java.lang.String" scope="session"/>
<jsp:useBean id="allRequests" class="java.util.ArrayList" scope="request"/>
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
<form action="/jj/bossInterface" method="post" enctype="application/x-www-form-urlencoded">
    <security:csrfInput/>
    <p>
        <label>
            Hello, ${boss}!
            Here you can assign worker to credit requests
        </label>
    </p>
    <table>
        <caption>Credit requests</caption>
        <tbody>
        <tr>
            <th>ID</th>
            <th>Client name</th>
            <th>Client ITN</th>
            <th>Type of credit</th>
            <th>Sum</th>
            <th>Term</th>
            <th>Revenue</th>
            <th>Profit</th>
            <th>Net assets</th>
            <th>Total assets</th>
            <th>Liability</th>
            <th>Limit</th>
            <th>Decision</th>
            <th>Worker</th>

        </tr>
        <c:forEach var="requests" items="${allRequests}">
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
                    <c:out value="${requests.getAccountant().getLiability()}"/>
                </td>
                <td>
                    <c:set var="limit" value="${requests.getLimit()}"/>
                    <c:choose>
                        <c:when test="${limit == null}">
                            <c:out value=""/>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${limit}"/>
                        </c:otherwise>
                    </c:choose>

                </td>
                <td>
                    <c:set var="decision" value="${requests.getDecision()}"/>
                    <c:choose>
                        <c:when test="${decision == null}">
                            <c:out value=""/>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${decision}"/>
                        </c:otherwise>
                    </c:choose>

                </td>
                <td>
                    <c:out value="${requests.getWorker().getLogin()}"/>

                </td>

            </tr>
        </c:forEach>
        </tbody>
    </table>
    <p>
        <input type="submit" name="assignCreditWorker" value="Assign worker">
    </p>
    <p>
        <input value="Log out" type="submit" name="logOutButtonFromBoss">
    </p>
</form>
</body>
</html>

