<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<head>
    <meta charset="UTF-8">
    <title>Квизы</title>
    <link rel="stylesheet" href="menu.css">
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
<div class="add-quiz">
    <form action="/menu/quizzes" method="POST">
        <div class="quiz-field">
            <label for="quiz">Название квиза:</label>
            <input type="text" id="quiz" name="quizName" required>
        </div>
        <div class="quiz-field">
            <label for="imgUrl">Img URL:</label>
            <input type="text" id="imgUrl" name="imgUrl">
        </div>
        <div class="quiz-field">
            <label for="categories">Категория:</label>
            <select id="categories" name="category">
                <option value="Без категории">Без категории</option>
            </select>
        </div>
        <button type="submit" style="margin-top: 20px;">Создать</button>
    </form>
    <div>
        <c:forEach var="quiz" items="${quizzes}">
            <div>
                <p><b>${quiz.name}</b></p>
                <p>${quiz.category}</p>
                <c:choose>
                    <c:when test="${not empty quiz.imgUrl}">
                        <img src="${quiz.imgUrl}" width="150" height="100"/>
                    </c:when>
                    <c:otherwise>
                        <img src="https://healthyway.com.ua/wp-content/uploads/2024/07/%D1%82%D0%B5%D1%81%D1%82.jpg"
                             width="150" height="100"/>
                    </c:otherwise>
                </c:choose>
                <div style="align-content: start">
                    <form style="width: fit-content; text-align: center; float: left;">
                        <input type="hidden" name="quiz_id" value="${quiz.quizId}"/>
                        <button type="submit">Изменить</button>
                    </form>
                    <form action="/menu/quizzes" method="POST"
                          style="width: fit-content; text-align: center; float: left;">
                        <input type="hidden" name="method" value="DELETE"/>
                        <input type="hidden" name="quiz_id" value="${quiz.quizId}"/>
                        <button type="submit" class="delete-icon">
                            <img src="/images/delete.png" alt="Удалить" width="25" height="25">
                        </button>
                    </form>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
</body>
</html>
