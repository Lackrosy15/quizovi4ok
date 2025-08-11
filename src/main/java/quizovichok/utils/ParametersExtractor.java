package quizovichok.utils;

import jakarta.servlet.http.HttpServletRequest;
import quizovichok.entityes.*;

import java.time.LocalDateTime;
import java.util.*;

public class ParametersExtractor {
    public LoginCredentials getLoginCredentials(HttpServletRequest req) {
        return new LoginCredentials(req.getParameter("login"), req.getParameter("password"));
    }

    public RegistrationCredentials getRegistrationCredentials(HttpServletRequest req) {
        return new RegistrationCredentials(req.getParameter("name"), req.getParameter("login"), req.getParameter("password"));
    }

    public PassQuizParameters getPassQuizParameters(HttpServletRequest req, Quiz quiz) {
        List<PassQuizQuestion> passQuizQuestionList = new ArrayList<>();
        int questionId = 1;
        while (true) {
            String questionText = req.getParameter("questions[" + questionId + "].text");
            if (questionText == null) break;

            List<Integer> checkedAnswers = Optional.ofNullable(req.getParameterMap().get("questions[" + questionId + "].correctAnswers"))
                    .map(array -> Arrays.stream(array).map(Integer::parseInt)
                            .toList())
                    .orElse(List.of());

            int answerId = 1;
            List<String> answerOptions = new ArrayList<>();
            while (true) {
                String answer = req.getParameter("questions[" + questionId + "].answers[" + answerId + "].text");
                if (answer == null) break;
                answerOptions.add(answer);
                answerId++;
            }

            passQuizQuestionList.add(new PassQuizQuestion(questionText, answerOptions, checkedAnswers, quiz.getQuestionList().get(questionId - 1).getCorrectAnswers()));
            questionId++;
        }
        return new PassQuizParameters(UUID.randomUUID(), quiz.getQuizId(), quiz.getName(), quiz.getCategory(), quiz.getImgUrl(), ((User) req.getSession().getAttribute("user")).getId(),
                ((User) req.getSession().getAttribute("user")).getName(), ((User) req.getSession().getAttribute("user")).getLogin(), passQuizQuestionList, LocalDateTime.now());
    }

    public QuizParameters getQuizParameters(HttpServletRequest req) {
        List<Question> questionList = new ArrayList<>();
        int questionId = 1;
        while (true) {
            String questionText = req.getParameter("questions[" + questionId + "].text");
            if (questionText == null) break;

            List<Integer> correctAnswers = Optional.ofNullable(req.getParameterMap().get("questions[" + questionId + "].correctAnswers"))
                    .map(array -> Arrays.stream(array).map(Integer::parseInt)
                            .toList())
                    .orElse(List.of());

            int answerId = 1;
            List<String> answerOptions = new ArrayList<>();
            while (true) {
                String answer = req.getParameter("questions[" + questionId + "].answers[" + answerId + "].text");
                if (answer == null) break;
                answerOptions.add(answer);
                answerId++;
            }

            questionList.add(new Question(questionText, answerOptions, correctAnswers));
            questionId++;
        }

        return new QuizParameters(req.getParameter("quizTitle"), req.getParameter("category"),
                req.getParameter("imageUrl"), (User) req.getSession().getAttribute("user"), LocalDateTime.now(), questionList);
    }

    public ChangePasswordCredentials getChangePasswordParameters(HttpServletRequest req) {
        return new ChangePasswordCredentials(req.getParameter("currentPassword"), req.getParameter("newPassword"));
    }
}
