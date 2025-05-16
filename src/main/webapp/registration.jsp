<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Регистрация</title>
  <link rel="stylesheet" href="registration.css">
</head>
<body>

<h1>Регистрация</h1>

<form action="/registration" method="POST">
  <input type="text" name="name" placeholder="Nickname" required>
  <input type="text" name="login" placeholder="Email" required>
  <input type="password" name="password" placeholder="Пароль" required>
  <button type="submit">Зарегистрироваться</button>
</form>
<br>
<%-- ошибка валидации --%>
<c:out value="${errorMessageCode422}"/>
</body>
</html>