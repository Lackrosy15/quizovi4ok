<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Вход</title>
    <link rel="stylesheet" href="login.css">
</head>
<body>
<c:out value="${errorMessageCode429}"/>
<h1>Вход в аккаунт</h1>
<form action="/login" method="POST">
    <input type="text" name="login" placeholder="Логин" required value="rodionova.viktoriya.99@bk.ru">
    <input type="password" name="password" placeholder="Пароль" required value="12345qwe">
    <button type="submit">Войти</button>
</form>
<br>
<c:out value="${errorMessageInvalidLoginOrPassword}"/>
</body>
</html>