<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/WEB-INF/tags.tld" prefix="mytags" %>


<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Пройденные квизы | Квизовичок</title>
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@300;400;500;600;700&display=swap"
          rel="stylesheet">
    <link rel="stylesheet" href="/quizzes.css">
    <link rel="stylesheet" href="/menu/menu.css">
</head>
<body>
<%@ include file="menu.jsp" %>

<main class="main-content">
    <div class="header">
        <h1 class="page-title">Результаты квиза</h1>
    </div>

    <div class="content-wrapper">
        <div class="quiz-list">
            <div class="quiz-list-header">
                <h2 class="section-title"><c:out value="${passingTheQuizByUser[0].quizName}"/> </h2>
            </div>
            <c:forEach var="passingTheQuiz" items="${passingTheQuizByUser}">
                <div class="quiz-item">
                    <div class="quiz-info">
                        <h3>${mytags:formatDateTime(passingTheQuiz.dateAndTimeOfPassage, "dd.MM.yyyy HH:mm")}</h3>
                    </div>
                    <div class="quiz-actions">
                        <a href="/result/${passingTheQuiz.passId}" class="form-link">
                            <span>Подробнее</span>
                            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none"
                                 stroke="currentColor" stroke-width="2" stroke-linecap="round"
                                 stroke-linejoin="round" class="link-icon">
                                <path d="M5 12h14"></path>
                                <path d="M12 5l7 7-7 7"></path>
                            </svg>
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