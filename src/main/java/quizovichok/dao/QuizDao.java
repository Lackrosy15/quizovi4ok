package quizovichok.dao;

import quizovichok.entityes.PassQuiz;
import quizovichok.entityes.Quiz;
import quizovichok.entityes.QuizCategory;

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
