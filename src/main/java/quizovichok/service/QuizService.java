//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package quizovichok.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.SneakyThrows;
import quizovichok.dao.QuizDao;
import quizovichok.entityes.*;
import quizovichok.exceptions.ExistQuizCategoryException;

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
        return quizDao.getAllQuizzes();
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
        return Quiz.builder()
                .category(quizParameters.getCategory())
                .name(quizParameters.getName())
                .imgUrl(quizParameters.getImgUrl())
                .userId(quizParameters.getUser().getId())
                .createdAt(quizParameters.getCreatedAt())
                .questionList(quizParameters.getQuestionList())
                .build();
    }

    @SneakyThrows
    public PassQuiz createPassQuiz(PassQuizParameters passQuizParameters) {

        return PassQuiz.builder()
                .quizId(passQuizParameters.getQuizId())
                .quizName(passQuizParameters.getQuizName())
                .quizCategory(passQuizParameters.getQuizCategory())
                .quizImgUrl(passQuizParameters.getQuizImgUrl())
                .userId(passQuizParameters.getUserId())
                .userName(passQuizParameters.getUserName())
                .userLogin(passQuizParameters.getUserLogin())
                .questionList(passQuizParameters.getQuestionList())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public void addQuizCategory(String categoryName) throws ExistQuizCategoryException {
        List<QuizCategory> quizCategories = quizDao.getAllQuizCategories();
        QuizCategory quizCategory = quizCategories.stream().filter(QC -> QC.getName().equalsIgnoreCase(categoryName.trim())).findFirst().orElse(null);
        if (quizCategory == null) {
            quizDao.addQuizCategory(QuizCategory.builder().name(categoryName).build());
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
