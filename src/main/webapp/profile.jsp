<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Профиль | Тестовичок</title>
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="/profile.css">
    <link rel="stylesheet" href="menu.css">
</head>
<body>

<%@ include file="menu.jsp" %>

<main class="main-content">
    <div class="header">
        <h1 class="page-title">Профиль пользователя</h1>
    </div>

    <div class="profile-card">
        <div class="profile-header">
            <div class="profile-info">
                <h2><c:out value="${user.name}"/></h2>
                <p><c:out value="${user.login}"/></p>
            </div>
        </div>


        <div class="profile-section">
            <h3 class="section-title">Смена пароля</h3>
            <form id="changePasswordForm" action="/menu/profile" method="POST">
                <div class="form-group">
                    <label for="currentPassword">Текущий пароль</label>
                    <div class="password-container">
                        <input type="password" id="currentPassword" name="currentPassword" class="form-control" placeholder="Введите текущий пароль" required>
                        <span class="toggle-password">️‍‍🫣️️</span>
                    </div>
                </div>

                <div class="form-group">
                    <label for="newPassword">Новый пароль</label>
                    <div class="password-container">
                        <input type="password" id="newPassword" name="newPassword" class="form-control" placeholder="Введите новый пароль" required>
                        <span class="toggle-password">🫣</span>
                    </div>
                </div>
                <div class="actions">
                    <button type="submit" class="btn">Сохранить изменения</button>
                </div>
            </form>
        </div>
    </div>
</main>

<script>
    // Переключение видимости пароля
    document.querySelectorAll('.toggle-password').forEach(function(element) {
        element.addEventListener('click', function() {
            const input = this.previousElementSibling;
            const type = input.getAttribute('type') === 'password' ? 'text' : 'password';
            input.setAttribute('type', type);
            this.textContent = type === 'password' ? '🫣' : '🔒';
        });
    });

    // Анимация при наведении на кнопки
    document.querySelectorAll('.btn').forEach(function(btn) {
        btn.addEventListener('mouseenter', function() {
            this.style.transform = 'translateY(-2px)';
        });
        btn.addEventListener('mouseleave', function() {
            this.style.transform = 'translateY(0)';
        });
    });
</script>
</body>
</html>