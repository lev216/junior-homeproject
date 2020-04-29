<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<jsp:useBean id="login" class="java.lang.String" scope="session"/>
<jsp:useBean id="clientRequests" class="java.util.ArrayList" scope="request"/>
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
    <form action="/jj/clientInterface" method="post" enctype="application/x-www-form-urlencoded">
        <security:csrfInput/>
        <p>
            <label>
                Hello, ${login}!
                Here you can send your requests for credit to our Bank.
            </label>
        </p>
        <table>
            <caption>Your requests for credit</caption>
            <tbody>
                <tr>
                    <th>ID</th>
                    <th>Type of credit</th>
                    <th>Sum</th>
                    <th>Term</th>
                    <th>Revenue</th>
                    <th>Profit</th>
                    <th>Net assets</th>
                    <th>Total assets</th>
                    <th>Decision</th>
                </tr>
                <c:forEach var="requests" items="${clientRequests}">
                    <tr>

                        <td>
                            <c:out value="${requests.getId()}"/>
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
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <p>
            <input value="Create new request for credit" type="submit" name="newCreditRequestButton">
        </p>
        <p>
            <input value="Log out" type="submit" name="logOutButtonFromClient">
        </p>
    </form>
</body>
</html>
