<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Главная</title>
    <link rel="stylesheet" href="menu/menu.css">
</head>
<body>
<ul id="navbar">
    <li><a href="#">Профиль</a></li>
    <li><a href="/menu/quizzes">Мои квизы</a></li>
    <li><a href="#">Помощь</a></li>
    <div class="online-users">
        <p><b>Пользователи онлайн <span>&#127759</span></b></p>
        <c:forEach var="user" items="${onlineUsers}">
            <p>${user}</p>
        </c:forEach>
    </div>
</ul>
</ul>
</ul>
</body>
</html>