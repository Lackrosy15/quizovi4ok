//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package testovichok.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Generated;
import lombok.SneakyThrows;
import testovichok.dao.QuizDao;
import testovichok.entityes.Quiz;
import testovichok.entityes.QuizParameters;

@AllArgsConstructor
public class QuizService {
    private final QuizDao quizDao;

    @SneakyThrows
    public List<Quiz> findAllQuizzesByUserId(UUID userId) {
        return quizDao.getAllQuizzes().stream()
                .filter((quiz) -> quiz.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    @SneakyThrows
    public void addQuiz(Quiz quiz) {
        this.quizDao.addQuiz(quiz);
    }

    @SneakyThrows
    public void removeQuiz(UUID quizId) {
        this.quizDao.removeQuiz(quizId);
    }

    @SneakyThrows
    public Quiz createQuiz(QuizParameters quizParameters) {
        return new Quiz(UUID.randomUUID(), quizParameters.getName(), quizParameters.getCategory(), quizParameters.getImgUrl(), quizParameters.getUser().getId());
    }
}
