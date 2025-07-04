<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Создание квиза | Квизовичок</title>
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@300;400;500;600;700&display=swap"
          rel="stylesheet">
    <link rel="stylesheet" href="/admin/create-quiz.css">
    <link rel="stylesheet" href="/menu/menu.css">
</head>
<body>

<%@ include file="../menu.jsp" %>

<div class="container">
    <h1>Создание нового квиза</h1>

    <form id="quiz-form" action="/quizzes" method="POST">
        <div class="form-group">
            <label for="quiz-title">Название квиза</label>
            <input type="text" id="quiz-title" name="quizTitle" class="form-control" required>
        </div>

        <div class="form-group">
            <label for="quiz-image">Ссылка на изображение</label>
            <input type="url" id="quiz-image" name="imageUrl" class="form-control" placeholder="https://example.com/image.jpg">
        </div>

        <div class="form-group">
            <label for="quiz-category">Категория квиза</label>
            <select id="quiz-category" name="category" class="form-control" required>
                <c:forEach var="category" items="${quizCategories}">
                    <option value="${category.name}">${category.name}</option>
                </c:forEach>
            </select>
        </div>

        <div id="questions-container">
            <!-- Вопросы будут добавляться сюда динамически -->
        </div>

        <div class="actions">
            <button type="button" class="btn btn-outline" onclick="addQuestion()">Добавить вопрос</button>
            <button type="submit" class="btn">Сохранить квиз</button>
        </div>
    </form>
</div>

<script>
    let questionCounter = 0;
    let answerCounters = {};

    // Добавление нового вопроса
    function addQuestion() {
        questionCounter++;
        answerCounters[questionCounter] = 0;

        const questionsContainer = document.getElementById('questions-container');

        const questionDiv = document.createElement('div');
        questionDiv.className = 'question-block';
        questionDiv.id = `question-\${questionCounter}`;
        questionDiv.innerHTML = `
            <div class="question-header">
                <span class="question-title">Вопрос \${questionCounter}</span>
                <button type="button" class="btn btn-danger btn-sm" onclick="removeQuestion(\${questionCounter})">Удалить</button>
            </div>
            <div class="form-group">
                <label for="question-text-\${questionCounter}">Текст вопроса</label>
                <textarea id="question-text-\${questionCounter}" name="questions[\${questionCounter}].text" class="form-control" rows="3" required></textarea>
            </div>
            <div id="answers-container-\${questionCounter}">
                <!-- Варианты ответов будут добавляться сюда -->
            </div>
            <button type="button" class="btn btn-outline btn-sm" onclick="addAnswer(\${questionCounter})">Добавить вариант ответа</button>
        `;

        questionsContainer.appendChild(questionDiv);
        addAnswer(questionCounter);
        addAnswer(questionCounter);
    }

    // Добавление варианта ответа
    function addAnswer(questionId) {
        answerCounters[questionId] = (answerCounters[questionId] || 0) + 1;
        const answerId = answerCounters[questionId];

        const answersContainer = document.getElementById(`answers-container-\${questionId}`);

        const answerDiv = document.createElement('div');
        answerDiv.className = 'answer-option';
        answerDiv.innerHTML = `
            <input type="text" name="questions[\${questionId}].answers[\${answerId}].text" class="form-control answer-input" placeholder="Текст варианта ответа" required>
            <input type="checkbox" class="answer-checkbox" name="questions[\${questionId}].correctAnswers" value="\${answerId}" onchange="toggleCorrect(this)">
            <span class="answer-label">Правильный</span>
            <button type="button" class="btn btn-danger btn-sm" style="margin-left: auto;" onclick="removeAnswer(this, \${questionId})">×</button>
        `;

        answersContainer.appendChild(answerDiv);
    }

    // Удаление вопроса
    function removeQuestion(questionId) {
        if (Object.keys(answerCounters).length > 1) {
            const questionDiv = document.getElementById(`question-\${questionId}`);
            questionDiv.remove();
            delete answerCounters[questionId];
            renumberQuestions();
        } else {
            alert('Квиз должен содержать хотя бы один вопрос!');
        }
    }

    // Удаление варианта ответа
    function removeAnswer(button, questionId) {
        const answerDiv = button.closest('.answer-option');
        const answersContainer = answerDiv.parentElement;

        if (answersContainer.children.length > 1) {
            answerDiv.remove();
            // Обновляем счетчики ответов для этого вопроса
            updateAnswerNumbers(questionId);
        } else {
            alert('Вопрос должен содержать хотя бы один вариант ответа!');
        }
    }

    // Обновление нумерации ответов
    function updateAnswerNumbers(questionId) {
        const answersContainer = document.getElementById(`answers-container-\${questionId}`);
        const answers = answersContainer.querySelectorAll('.answer-option');

        answerCounters[questionId] = answers.length;

        answers.forEach((answer, index) => {
            const answerId = index + 1;
            const textInput = answer.querySelector('.answer-input');
            const checkbox = answer.querySelector('.answer-checkbox');

            textInput.name = `questions[\${questionId}].answers[\${answerId}].text`;
            checkbox.value = answerId;
        });
    }

    // Перенумерация вопросов после удаления
    function renumberQuestions() {
        const questions = document.querySelectorAll('.question-block');
        let newCounter = 0;
        const newAnswerCounters = {};

        questions.forEach(question => {
            newCounter++;
            const oldQuestionId = question.id.split('-')[1];
            const newQuestionId = newCounter;

            // Обновляем ID вопроса
            question.id = `question-\${newQuestionId}`;

            // Обновляем заголовок
            question.querySelector('.question-title').textContent = `Вопрос \${newQuestionId}`;

            // Обновляем кнопки
            question.querySelector('button[onclick^="removeQuestion"]').setAttribute('onclick', `removeQuestion(\${newQuestionId})`);
            question.querySelector('button[onclick^="addAnswer"]').setAttribute('onclick', `addAnswer(\${newQuestionId})`);

            // Обновляем textarea вопроса
            const textarea = question.querySelector('textarea');
            textarea.id = `question-text-\${newQuestionId}`;
            textarea.name = `questions[\${newQuestionId}].text`;

            // Обновляем контейнер ответов
            const answersContainer = question.querySelector(`#answers-container-\${oldQuestionId}`);
            answersContainer.id = `answers-container-\${newQuestionId}`;

            // Обновляем ответы
            const answers = answersContainer.querySelectorAll('.answer-option');
            newAnswerCounters[newQuestionId] = answers.length;

            answers.forEach((answer, index) => {
                const answerId = index + 1;
                const textInput = answer.querySelector('.answer-input');
                const checkbox = answer.querySelector('.answer-checkbox');
                const removeBtn = answer.querySelector('button[onclick^="removeAnswer"]');

                textInput.name = `questions[\${newQuestionId}].answers[\${answerId}].text`;
                checkbox.name = `questions[\${newQuestionId}].correctAnswers`;
                checkbox.value = answerId;
                removeBtn.setAttribute('onclick', `removeAnswer(this, \${newQuestionId})`);
            });
        });

        // Обновляем глобальные счетчики
        answerCounters = newAnswerCounters;
        questionCounter = newCounter;
    }

    // Пометить ответ как правильный/неправильный
    function toggleCorrect(checkbox) {
        const answerDiv = checkbox.closest('.answer-option');
        if (checkbox.checked) {
            answerDiv.classList.add('correct');
        } else {
            answerDiv.classList.remove('correct');
        }
    }

    // Инициализация первого вопроса при загрузке
    document.addEventListener('DOMContentLoaded', function() {
        addQuestion();
    });
</script>
</body>
</html>