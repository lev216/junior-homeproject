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
    <form action="clientInterface" method="post" enctype="application/x-www-form-urlencoded"></form>
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
            </tr>

        </tbody>
    </table>
    <form action="clientInterface" method="post" enctype="application/x-www-form-urlencoded">
        <p>
            <input value="Create new request for credit" type="submit" name="newCreditRequestButton">
        </p>
        <p>
            <input value="Log out" type="submit" name="logOutButton">
        </p>
    </form>
</body>
</html>
