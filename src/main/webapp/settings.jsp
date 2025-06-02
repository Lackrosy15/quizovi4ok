<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Настройки | Тестовичок</title>
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@300;400;500;600;700&display=swap"
          rel="stylesheet">
    <link rel="stylesheet" href="/settings.css">
    <link rel="stylesheet" href="menu.css">
</head>
<body>
<%@ include file="menu.jsp" %>

<main class="main-content">
    <div class="header">
        <h1 class="page-title">Настройки</h1>
    </div>

    <div class="settings-container">
        <div class="settings-sidebar">
            <div class="settings-section">
                <h3 class="section-title">Категории квизов</h3>

                <ul class="category-list">
                    <c:forEach var="quizCategory" items="${quizCategories}">
                        <li class="category-item">
                            <span>${quizCategory.name}</span>
                            <div class="category-actions">
                                <form action="/menu/settings" method="POST">
                                    <input type="hidden" name="quiz_category_name_for_remove" value="${quizCategory.name}"/>
                                    <button class="action-btn" type="submit">
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
                        </li>
                    </c:forEach>
                </ul>
                <form action="/menu/settings" method="POST">
                    <div class="add-category-form">
                        <div class="form-group">
                            <label for="new-category">Новая категория</label>
                            <input type="text" name="quizCategory" id="new-category" class="form-control"
                                   placeholder="Введите название категории">
                        </div>
                        <button class="btn btn-sm" type="submit">Добавить</button>
                    </div>
                </form>
            </div>
            <div class="error-container">
                <c:out value="${quizCategoryExist}"/>
                <c:out value="${quizWithThisCategoryExist}"/>
            </div>
        </div>

        <div class="settings-main">
            <div class="settings-section">
                <h3 class="section-title">Основные настройки</h3>
                <!-- Здесь могут быть другие настройки -->
                <p>Другие настройки будут отображаться здесь</p>
            </div>
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
</script>
</body>
</html>