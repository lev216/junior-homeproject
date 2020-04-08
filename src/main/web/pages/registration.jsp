<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>

<html>
<head>
    <title>Sign up</title>
</head>
<body>
    <c:out value="${sessionScope['login']}"/>

    <c:set var="test" value="${requestScope.login}"/>
    <c:out value="${test}"/>

    ${applicationScope.login}


    <form action="registration" method="post" enctype="application/x-www-form-urlencoded">

        <p>
            <label>
                Login:
                <input type="text" name="loginField">
            </label>
        </p>
        <p>
            <label>
                Password:
                <input type="password" name="passwordField">
            </label>
        </p>
        <p>
            <label>
                User's type:
                <select name="typeField">

                    <option>Client</option>
                    <option>Worker</option>
                </select>
            </label>
        </p>
        <p>
            <label>
                Fields for Client
            </label>
        </p>
        <p>
            <label>
                Your ITN:
                <input type="text" name="clientITNField" maxlength="10">
            </label>
        </p>
        <p>
            <label>
                Your company's name:
                <input type="text" name="clientNameField">
            </label>
        </p>


        <p>

            <input type="submit" name="registrationButton" value="Send">

        </p>
        <p>
            <input value="Already registered" type="submit" name="toLoginButton">
        </p>
    </form>
</body>
</html>
