<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Результаты квиза | Тестовичок</title>
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@300;400;500;600;700&display=swap"
          rel="stylesheet">
    <link rel="stylesheet" href="/admin/create-quiz.css">
    <link rel="stylesheet" href="/menu/menu.css">
</head>
<body>

<%@ include file="../menu.jsp" %>


<div class="container">
    <h1>${passQuiz.quizName}</h1>
    <div class="form-group">
        <h2>Категория</h2>
        <label>${passQuiz.quizCategory}</label>
        <h2>Кто проходил</h2>
        <label>${passQuiz.userName} (${passQuiz.userLogin})</label>
    </div>

    <div id="questions-container">
        <c:forEach var="passQuizQuestion" items="${passQuiz.questionList}" varStatus="questionLoop">
            <div class="question-block" id="question-${questionLoop.count}">
                <div class="question-header">
                    <span class="question-title">Вопрос ${questionLoop.count}</span>
                </div>
                <div class="form-group">
                    <input id="question-text-${questionLoop.count}" name="questions[${questionLoop.count}].text" class="form-control" rows="3" value="${passQuizQuestion.question}" style="font-weight: bold;" readonly>
                </div>
                <div id="answers-container-${questionLoop.count}">
                    <c:forEach var="quizQuestionAnswer" items="${passQuizQuestion.answerOptions}" varStatus="quizQuestionAnswerLoop">
                        <c:if test="${fn:contains(passQuizQuestion.userAnswers, quizQuestionAnswerLoop.count) and fn:contains(passQuizQuestion.correctAnswers, quizQuestionAnswerLoop.count)}">
                            <div class="answer-option correct">
                                <input type="text" name="questions[${questionLoop.count}].answers[${quizQuestionAnswerLoop.count}].text" class="form-control answer-input" placeholder="Текст варианта ответа" required value="${quizQuestionAnswer}" readonly>
                                <input type="checkbox" class="answer-checkbox" name="questions[${questionLoop.count}].correctAnswers" value="${quizQuestionAnswerLoop.count}" disabled checked>
                            </div>
                        </c:if>
                        <c:if test="${fn:contains(passQuizQuestion.userAnswers, quizQuestionAnswerLoop.index + 1) and not fn:contains(passQuizQuestion.correctAnswers, quizQuestionAnswerLoop.index + 1)}">
                            <div class="answer-option incorrect">
                                <input type="text" name="questions[${questionLoop.count}].answers[${quizQuestionAnswerLoop.count}].text" class="form-control answer-input" placeholder="Текст варианта ответа" required value="${quizQuestionAnswer}" readonly>
                                <input type="checkbox" class="answer-checkbox" name="questions[${questionLoop.count}].correctAnswers" value="${quizQuestionAnswerLoop.count}" disabled>
                            </div>
                        </c:if>
                        <c:if test="${not fn:contains(passQuizQuestion.userAnswers, quizQuestionAnswerLoop.count) and not fn:contains(passQuizQuestion.correctAnswers, quizQuestionAnswerLoop.count)}">
                            <div class="answer-option">
                                <input type="text" name="questions[${questionLoop.count}].answers[${quizQuestionAnswerLoop.count}].text" class="form-control answer-input" placeholder="Текст варианта ответа" required value="${quizQuestionAnswer}" readonly>
                                <input type="checkbox" class="answer-checkbox" name="questions[${questionLoop.count}].correctAnswers" value="${quizQuestionAnswerLoop.count}" disabled>
                            </div>
                        </c:if>
                        <c:if test="${not fn:contains(passQuizQuestion.userAnswers, quizQuestionAnswerLoop.count) and fn:contains(passQuizQuestion.correctAnswers, quizQuestionAnswerLoop.count)}">
                            <div class="answer-option correct-not-answered">
                                <input type="text" name="questions[${questionLoop.count}].answers[${quizQuestionAnswerLoop.count}].text" class="form-control answer-input" placeholder="Текст варианта ответа" required value="${quizQuestionAnswer}" readonly>
                                <input type="checkbox" class="answer-checkbox" name="questions[${questionLoop.count}].correctAnswers" value="${quizQuestionAnswerLoop.count}" disabled checked>
                            </div>
                        </c:if>
                    </c:forEach>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

</body>
</html>