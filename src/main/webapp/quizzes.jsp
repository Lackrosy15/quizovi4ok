<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ page session = "true" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="testovichok.entityes.Roles"%>


<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Квизы | Тестовичок</title>
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@300;400;500;600;700&display=swap"
          rel="stylesheet">
    <link rel="stylesheet" href="/quizzes.css">
    <link rel="stylesheet" href="/menu/menu.css">
</head>
<body>
<%@ include file="menu.jsp" %>

<main class="main-content">
    <div class="header">
        <h1 class="page-title">Квизы</h1>
    </div>

    <div class="content-wrapper">
        <div class="quiz-list">
            <div class="quiz-list-header">
                <h2 class="section-title">Список квизов</h2>
            </div>
            <c:forEach var="quiz" items="${quizzes}">
                <div class="quiz-item">
                    <div class="quiz-info">
                        <h3>${quiz.name}</h3>
                        <div class="quiz-meta">
                            <span>${quiz.category}</span>
                            <span>
                            <c:choose>
                                <c:when test="${not empty quiz.imgUrl}">
                                    <img src="${quiz.imgUrl}" width="200" height="150"/>
                                </c:when>
                                <c:otherwise>
                        <img src="https://healthyway.com.ua/wp-content/uploads/2024/07/%D1%82%D0%B5%D1%81%D1%82.jpg"
                             width="200" height="150"/>
                                </c:otherwise>
                            </c:choose>
                        </span>
                            <form action="/quizzes/${quiz.quizId}" method="GET">
                                <button type="submit" class="btn-pass">Пройти тест</button>
                            </form>
                        </div>
                    </div>
                    <c:if test="${sessionScope.user.role eq Roles.ADMIN}">
                    <div class="quiz-actions">
                        <form action="/quiz-edit/${quiz.quizId}" method="GET">
                            <button type="submit" class="action-btn">
                                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none"
                                     stroke="currentColor" stroke-width="2" stroke-linecap="round"
                                     stroke-linejoin="round">
                                    <path d="M11 4H4a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-7"></path>
                                    <path d="M18.5 2.5a2.121 2.121 0 0 1 3 3L12 15l-4 1 1-4 9.5-9.5z"></path>
                                </svg>
                            </button>
                        </form>
                        <form action="/quizzes" method="POST">
                            <input type="hidden" name="method" value="DELETE"/>
                            <input type="hidden" name="quiz_id" value="${quiz.quizId}"/>
                            <button type="submit" class="action-btn">
                                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none"
                                     stroke="currentColor" stroke-width="2" stroke-linecap="round"
                                     stroke-linejoin="round">
                                    <polyline points="3 6 5 6 21 6"></polyline>
                                    <path d="M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2"></path>
                                    <line x1="10" y1="11" x2="10" y2="17"></line>
                                    <line x1="14" y1="11" x2="14" y2="17"></line>
                                </svg>
                            </button>
                        </form>
                    </div>
                    </c:if>
                </div>
            </c:forEach>
            <c:if test="${sessionScope.user.role eq Roles.ADMIN}">
            <div class="empty-state">
                <p>У вас пока нет других квизов. Создайте новый!</p>
            </div>
            </c:if>
        </div>

        <c:if test="${sessionScope.user.role eq Roles.ADMIN}">
        <form action="/create-quiz" method="GET">
            <div class="add-quiz-form">
                <h2 class="form-title">Создать новый квиз</h2>
                <button type="submit" class="btn">Создать</button>
            </div>
        </form>
        </c:if>
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