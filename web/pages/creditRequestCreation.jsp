<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<jsp:useBean id="login" class="java.lang.String" scope="session"/>
<html>
<head>
    <title>Creation of credit request</title>
</head>
<body>
    <form action="/jj/request" method="post" enctype="application/x-www-form-urlencoded">
        <p>
            <label>
                ${login}, fill all the fields for creating your credit request
            </label>
        </p>
        <p>
            <label>
                Type of credit:
                <select name="typeCreditField">

                    <option>Working</option>
                    <option>Investment</option>
                </select>
            </label>
        </p>

        <p>
            <label>
                Sum of credit:
                <input type="text" name="sumClientField">
            </label>
        </p>
        <p>
            <label>
                Term of credit:
                <input type="text" name="termClientField">
            </label>
        </p>
        <p>
            <label>
                Your revenue for the last year:
                <input type="text" name="revenueClientField">
            </label>
        </p>
        <p>
            <label>
                Your profit for the last year:
                <input type="text" name="profitClientField">
            </label>
        </p>
        <p>
            <label>
                Your net assets:
                <input type="text" name="netAssetsClientField">
            </label>
        </p>
        <p>
            <label>
                Your total assets:
                <input type="text" name="totalAssetsClientField">
            </label>
        </p>
        <p>
            <input type="submit" value="Send request to the Bank" name="sendRequestButton">
        </p>
        <p>
            <input type="submit" value="Cancel" name="cancelClientButton">
        </p>
    </form>


</body>
</html>
