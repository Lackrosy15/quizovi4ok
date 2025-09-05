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

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import quizovichok.dao.QuizDao;
import quizovichok.entities.*;
import quizovichok.exceptions.ExistQuizCategoryException;

import static java.util.stream.Collectors.toList;

@AllArgsConstructor
public class QuizService {
    private final QuizDao quizDao;

    public List<Quiz> findAllQuizzesByUserId(UUID userId) {
        return quizDao.getAllQuizzes().stream()
                .filter((quiz) -> quiz.getUserId().equals(userId))
                .collect(toList());
    }


    public List<Quiz> findAllQuizzes() {
        return quizDao.getAllQuizzes();
    }


    public Quiz findQuizById(String uuid) {
        return quizDao.getQuiz(UUID.fromString(uuid));
    }


    public List<QuizCategory> findAllQuizCategories() {
        return quizDao.getAllQuizCategories().stream()
                .collect(toList());
    }


    public void addQuiz(Quiz quiz) {
        this.quizDao.addQuiz(quiz);
    }


    public void addQuizResult(PassQuiz passQuiz) {
        this.quizDao.addQuizResult(passQuiz);
    }


    public List<PassQuiz> findAllPassQuizzesGropingByQuizIdOfUser(UUID userId) {
        List<PassQuiz> allPassQuizzesOfUser = quizDao.getAllQuizResults().stream()
                .filter(passQuiz -> passQuiz.getUserId().equals(userId))
                .collect(Collectors.groupingBy(PassQuiz::getQuizId))
                .values()
                .stream()
                .map(group -> group.get(0))
                .collect(toList());

        return allPassQuizzesOfUser;
    }


    public List<PassQuiz> findAllPassQuizzesGroupingByQuizId() {
        List<PassQuiz> allPassQuizzes = quizDao.getAllQuizResults().stream()
                .collect(Collectors.groupingBy(PassQuiz::getQuizId))
                .values()
                .stream()
                .map(group -> group.get(0))
                .collect(toList());

        return allPassQuizzes;
    }


    public PassQuiz findPassQuizById(UUID passQuizId) {
        return quizDao.getQuizResultById(passQuizId);
    }


    public List<PassQuiz> findAllPassOfQuizByIdOfUser(UUID userId, UUID quizId) {
        List<PassQuiz> allPassQuizOfUser = quizDao.getAllQuizResults().stream()
                .filter(passQuiz -> passQuiz.getUserId().equals(userId) && passQuiz.getQuizId().equals(quizId))
                .collect(toList());

        return allPassQuizOfUser;
    }


    public List<PassQuiz> findAllPassOfQuizByQuizId(UUID quizId) {
        List<PassQuiz> allPassingQuizByQuizId = quizDao.getAllQuizResults().stream()
                .filter(passQuiz -> passQuiz.getQuizId().equals(quizId))
                .collect(toList());
        return allPassingQuizByQuizId;
    }


    public void removeQuiz(UUID quizId) {
        this.quizDao.removeQuiz(quizId);
    }


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
        quizDao.addQuizCategory(QuizCategory.builder().name(categoryName).build());
    }


    public void removeQuizCategory(String categoryName) throws ExistQuizCategoryException {
        quizDao.removeQuizCategory(categoryName);
    }
}
