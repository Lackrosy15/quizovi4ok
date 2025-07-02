<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Пройденные квизы | Тестовичок</title>
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@300;400;500;600;700&display=swap"
          rel="stylesheet">
    <link rel="stylesheet" href="/quizzes.css">
    <link rel="stylesheet" href="/menu/menu.css">
</head>
<body>
<%@ include file="menu.jsp" %>

<main class="main-content">
    <div class="header">
        <h1 class="page-title">Результаты</h1>
    </div>

    <div class="content-wrapper">
        <div class="quiz-list">
            <div class="quiz-list-header">
                <h2 class="section-title">Пройденные квизы</h2>
            </div>
            <c:forEach var="passQuiz" items="${passQuizzesOfUser}">
                <div class="quiz-item">
                    <div class="quiz-info">
                        <h3>${passQuiz.quizName}</h3>
                        <div class="quiz-meta">
                            <span>${passQuiz.quizCategory}</span>
                            <span>
                            <c:choose>
                                <c:when test="${not empty passQuiz.quizImgUrl}">
                                    <img src="${passQuiz.quizImgUrl}" width="200" height="150"/>
                                </c:when>
                                <c:otherwise>
                        <img src="https://healthyway.com.ua/wp-content/uploads/2024/07/%D1%82%D0%B5%D1%81%D1%82.jpg"
                             width="200" height="150"/>
                                </c:otherwise>
                            </c:choose>
                        </span>
                        </div>
                    </div>
                    <div class="quiz-actions">
                        <a href="/results/${passQuiz.quizId}" class="form-link">
                            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none"
                                 stroke="currentColor" stroke-width="2" stroke-linecap="round"
                                 stroke-linejoin="round" class="link-icon">
                                <path d="M19 21l-7-5-7 5V5a2 2 0 0 1 2-2h10a2 2 0 0 1 2 2z"></path>
                            </svg>
                            <span>Мои прохождения</span>
                        </a>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</main>

<script>
    // Анимация при наведении на кнопки
    document.querySelectorAll('.btn').forEach(function (btn) {
        btn.addEventListener('mouseenter', function () {
            this.style.transform = 'translateY(-2px)';
        });
        btn.addEventListener('mouseleave', function () {
            this.style.transform = 'translateY(0)';
        });
    });

    // Анимация карточек квизов
    document.querySelectorAll('.quiz-item').forEach(function (item) {
        item.addEventListener('mouseenter', function () {
            this.style.transform = 'translateY(-2px)';
        });
        item.addEventListener('mouseleave', function () {
            this.style.transform = 'translateY(0)';
        });
    });
</script>
</body>
</html>