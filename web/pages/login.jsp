<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
    <form action="/jj/login" method="post" enctype="application/x-www-form-urlencoded">
        <security:csrfInput/>
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
            <input value="Login" type="submit" name="loginButton">
        </p>
        <p>
            <input value="Go to registration form" type="submit" name="registrationButton">
        </p>
    </form>
</body>
</html>
