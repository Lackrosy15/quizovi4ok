//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package testovichok.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Generated;
import lombok.SneakyThrows;
import testovichok.dao.QuizDao;
import testovichok.entityes.Quiz;
import testovichok.entityes.QuizCategory;
import testovichok.entityes.QuizParameters;
import testovichok.exceptions.ExistQuizCategoryException;

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
    public List<Quiz> findAllQuizzes() {
        return quizDao.getAllQuizzes().stream()
                .collect(Collectors.toList());
    }

    @SneakyThrows
    public List<QuizCategory> findAllQuizCategories() {
        return quizDao.getAllQuizCategories().stream()
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

    public void addQuizCategory(String categoryName) throws ExistQuizCategoryException {
        List<QuizCategory> quizCategories = quizDao.getAllQuizCategories();
        QuizCategory quizCategory = quizCategories.stream().filter(QC -> QC.getName().equalsIgnoreCase(categoryName)).findFirst().orElse(null);
        if (quizCategory == null) {
            quizDao.addQuizCategory(new QuizCategory(categoryName));
        } else {
            throw new ExistQuizCategoryException();
        }
    }

    public void removeQuizCategory(String categoryName) throws ExistQuizCategoryException, IOException {
        boolean isQuizCategory = quizDao.getAllQuizzes().stream().map(quiz -> quiz.getCategory()).anyMatch(quizCategory -> quizCategory.equals(categoryName));
        if (isQuizCategory) {
            throw new ExistQuizCategoryException();
        } else {
            quizDao.removeQuizCategory(categoryName);
        }
    }
}
