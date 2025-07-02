<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Регистрация | Тестовичок</title>
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="registration.css">
</head>
<body>
<div class="container">
    <div class="registration-card">
        <div class="logo">
            <h1>Создать аккаунт</h1>
        </div>

        <form action="/registration" method="POST">
            <div class="form-group">
                <label for="name">Имя</label>
                <input type="text" id="name" name="name" class="form-control" placeholder="Введите ваше имя" required>
            </div>

            <div class="form-group">
                <label for="email">Email</label>
                <input type="email" id="email" name="login" class="form-control" placeholder="Введите ваш email" required>
            </div>

            <div class="form-group">
                <label for="password">Пароль</label>
                <div class="password-container">
                    <input type="password" id="password" name="password" class="form-control" placeholder="Создайте пароль" required>
                    <span class="toggle-password">🔒</span>
                </div>
            </div>
            <button type="submit" class="btn">Зарегистрироваться</button>
        </form>

        <div class="login-link">
            Уже есть аккаунт? <a href="/login">Войти</a>
        </div>
    </div>
</div>
<br>
<%-- ошибка валидации --%>
<c:out value="${errorMessageCode422}"/>

<script>
    // Переключение видимости пароля
    document.querySelectorAll('.toggle-password').forEach(function(element) {
        element.addEventListener('click', function() {
            const input = this.previousElementSibling;
            const type = input.getAttribute('type') === 'password' ? 'text' : 'password';
            input.setAttribute('type', type);
            this.textContent = type === 'password' ? '🔒' : '🫣';
        });
    });
</script>
</body>
</html>