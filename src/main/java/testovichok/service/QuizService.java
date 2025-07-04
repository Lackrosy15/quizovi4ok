//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package testovichok.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Generated;
import lombok.SneakyThrows;
import testovichok.dao.QuizDao;
import testovichok.entityes.*;
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
    public Quiz findQuizById(String uuid) {
        return quizDao.getQuiz(UUID.fromString(uuid));
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
    public void addQuizResult(PassQuiz passQuiz) {
        this.quizDao.addQuizResult(passQuiz);
    }

    @SneakyThrows
    public List<PassQuiz> findAllPassQuizzesOfUser(UUID userId) {
        List<PassQuiz> allPassQuizzesOfUser = quizDao.getAllQuizResults().stream()
                .filter(passQuiz -> passQuiz.getUserId().equals(userId))
                .collect(Collectors.groupingBy(PassQuiz::getQuizId))
                .values()
                .stream()
                .map(group -> group.get(0))
                .collect(Collectors.toList());

        return allPassQuizzesOfUser;
    }

    @SneakyThrows
    public List<PassQuiz> findAllPassQuizzes() {
        List<PassQuiz> allPassQuizzes = quizDao.getAllQuizResults().stream()
                .collect(Collectors.groupingBy(PassQuiz::getQuizId))
                .values()
                .stream()
                .map(group -> group.get(0))
                .collect(Collectors.toList());

        return allPassQuizzes;
    }

    @SneakyThrows
    public PassQuiz findPassQuizById(UUID passQuizId) {
        PassQuiz passQuiz = quizDao.getQuizResultById(passQuizId);
        return passQuiz;
    }

    @SneakyThrows
    public List<PassQuiz> findAllPassQuizOfUser(UUID userId, UUID quizId) {
        List<PassQuiz> allPassQuizOfUser = quizDao.getAllQuizResults().stream()
                .filter(passQuiz -> passQuiz.getUserId().equals(userId))
                .filter(passQuiz -> passQuiz.getQuizId().equals(quizId))
                .collect(Collectors.toList());

        return allPassQuizOfUser;
    }

    @SneakyThrows
    public List<PassQuiz> findAllPassingQuizByQuizId(UUID quizId) {
        List<PassQuiz> allPassingQuizByQuizId = quizDao.getAllQuizResults().stream()
                .filter(passQuiz -> passQuiz.getQuizId().equals(quizId))
                .collect(Collectors.toList());
        return allPassingQuizByQuizId;
    }

    @SneakyThrows
    public void removeQuiz(UUID quizId) {
        this.quizDao.removeQuiz(quizId);
    }

    @SneakyThrows
    public Quiz createQuiz(QuizParameters quizParameters) {
        return createQuiz(UUID.randomUUID(), quizParameters);
    }

    @SneakyThrows
    public Quiz createQuiz(UUID uuid, QuizParameters quizParameters) {
        return new Quiz(uuid, quizParameters.getName(), quizParameters.getCategory(), quizParameters.getImgUrl(), quizParameters.getUser().getId(), quizParameters.getCreatedAt(), quizParameters.getQuestionList());
    }

    @SneakyThrows
    public PassQuiz createPassQuiz(PassQuizParameters passQuizParameters) {
        return new PassQuiz(passQuizParameters.getPassId(), passQuizParameters.getQuizId(), passQuizParameters.getQuizName(),
                passQuizParameters.getQuizCategory(), passQuizParameters.getQuizImgUrl(), passQuizParameters.getUserId(),
                passQuizParameters.getUserName(), passQuizParameters.getUserLogin(), passQuizParameters.getQuestionList(), LocalDateTime.now());
    }

    public void addQuizCategory(String categoryName) throws ExistQuizCategoryException {
        List<QuizCategory> quizCategories = quizDao.getAllQuizCategories();
        QuizCategory quizCategory = quizCategories.stream().filter(QC -> QC.getName().equalsIgnoreCase(categoryName.trim())).findFirst().orElse(null);
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
