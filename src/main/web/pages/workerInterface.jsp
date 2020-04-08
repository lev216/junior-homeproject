<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>
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
                <th>Count limit</th>
                <th>Make decision</th>
            </tr>
            <tr>
                <td>1</td>
                <td>2</td>
                <td>3</td>
                <td>4</td>
                <td>5</td>
                <td>6</td>
                <td>7</td>
                <td>8</td>
                <td>9</td>
                <td>10</td>
                <td>11</td>
                <td>
                    <c:set var="limit"/>
                    <c:out value="${limit}"/>

                </td>

                <td>
                    <c:set var="decision" value="${0}"/>

                    ${decision}
                </td>
                <td>
                    <c:choose>
                        <c:when test="${decision == 0}">
                            <input type="submit" name="countLimitButton" value="Count limit">
                        </c:when>
                    </c:choose>
                </td>
                <td>
                    <c:choose>
                        <c:when test="${decision == 0}">
                            <input type="submit" name="makeDecisionButton" value="Make decision">
                        </c:when>
                    </c:choose>

                </td>

            </tr>

        </tbody>
    </table>
    <form action="workerInterface" method="post" enctype="application/x-www-form-urlencoded">

        <p>
            <input value="Log out" type="submit" name="logOutButton">
        </p>
    </form>

</body>
</html>
