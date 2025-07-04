<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Прохождение квиза | Квизовичок</title>
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@300;400;500;600;700&display=swap"
          rel="stylesheet">
    <link rel="stylesheet" href="/admin/create-quiz.css">
    <link rel="stylesheet" href="/menu/menu.css">
</head>
<body>

<%@ include file="menu.jsp" %>


<div class="container">
    <h1>${quiz.name}</h1>

    <form id="quiz-form" action="/quizzes/${quiz.quizId}" method="POST">

        <div class="form-group">
            <h2>Категория</h2>
            <label>${quiz.category}</label>
        </div>

        <div id="questions-container">
            <c:forEach var="quizQuestion" items="${quiz.questionList}" varStatus="questionLoop">
                <div class="question-block" id="question-${questionLoop.index + 1}">
                    <div class="question-header">
                        <span class="question-title">Вопрос ${questionLoop.index + 1}</span>
                    </div>
                    <div class="form-group">
                        <input id="question-text-${questionLoop.index + 1}" name="questions[${questionLoop.index + 1}].text" class="form-control" rows="3" value="${quizQuestion.question}" style="font-weight: bold;" readonly>
                    </div>
                    <div id="answers-container-${questionLoop.index + 1}">
                        <c:forEach var="quizQuestionAnswer" items="${quizQuestion.answerOptions}" varStatus="quizQuestionAnswerLoop">
                                <div class="answer-option">
                                    <input type="text" name="questions[${questionLoop.index + 1}].answers[${quizQuestionAnswerLoop.index + 1}].text" class="form-control answer-input" placeholder="Текст варианта ответа" required value="${quizQuestionAnswer}" readonly>
                                    <input type="checkbox" class="answer-checkbox correct" name="questions[${questionLoop.index + 1}].correctAnswers" value="${quizQuestionAnswerLoop.index + 1}" onchange="toggleCorrect(this)">
                                </div>
                        </c:forEach>
                    </div>
                </div>
            </c:forEach>
        </div>
        <div class="actions">
            <button type="submit" class="btn">Отправить ответы</button>
        </div>
    </form>
</div>

<script>
    // Пометить ответ как правильный/неправильный
    function toggleCorrect(checkbox) {
        const answerDiv = checkbox.closest('.answer-option');
        if (checkbox.checked) {
            answerDiv.classList.add('correct');
        } else {
            answerDiv.classList.remove('correct');
        }
    }
</script>
</body>
</html>