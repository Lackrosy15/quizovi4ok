<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Вход | Тестовичок</title>
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@300;400;500;600;700&display=swap"
          rel="stylesheet">
    <link rel="stylesheet" href="login.css">
</head>
<body>
<div class="container">
    <div class="login-card">
        <div class="logo">
            <h1>Вход в аккаунт</h1>
        </div>

        <form action="/login" method="POST">
            <div class="form-group">
                <label for="email">Email</label>
                <input type="email" name="login" id="email" class="form-control" placeholder="Введите ваш email"
                       required value="rodionova.viktoriya.99@bk.ru">
            </div>

            <div class="form-group">
                <label for="password">Пароль</label>
                <div class="password-container">
                    <input type="password" name="password" id="password" class="form-control"
                           placeholder="Введите ваш пароль" required value="12345qwe">
                    <span class="toggle-password">🫣</span>
                </div>
            </div>

            <button type="submit" class="btn">Войти</button>
        </form>

        <div class="register-link">
            Нет аккаунта? <a href="/registration">Зарегистрироваться</a>
        </div>
    </div>
    <div class="error-container">
        <c:out value="${errorMessageInvalidLoginOrPassword}"/>
        <c:out value="${errorMessageCode429}"/>
    </div>
</div>

<script>
    // Переключение видимости пароля
    document.querySelectorAll('.toggle-password').forEach(function (element) {
        element.addEventListener('click', function () {
            const input = this.previousElementSibling;
            const type = input.getAttribute('type') === 'password' ? 'text' : 'password';
            input.setAttribute('type', type);
            this.textContent = type === 'password' ? '‍🫣️' : '🔒';
        });
    });
</script>
</body>
</html>
