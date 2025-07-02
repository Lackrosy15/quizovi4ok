package testovichok.dao;

import lombok.SneakyThrows;
import testovichok.entityes.PassQuiz;
import testovichok.entityes.Quiz;
import testovichok.entityes.QuizCategory;
import testovichok.entityes.User;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface QuizDao {
    void addQuiz(Quiz quiz) throws IOException;
    Quiz getQuiz (UUID uuid);
    List<Quiz> getAllQuizzes() throws IOException;
    void removeQuiz(UUID quiz) throws IOException;
    List<QuizCategory> getAllQuizCategories();
    void addQuizCategory(QuizCategory quizCategory);
    void removeQuizCategory(String quizCategory);
    void addQuizResult(PassQuiz quiz) throws IOException;
    List<PassQuiz> getAllQuizResults();
    PassQuiz getQuizResultById(UUID passQuizId);
}
