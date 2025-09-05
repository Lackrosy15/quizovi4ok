package quizovichok.dao;

import quizovichok.entities.PassQuiz;
import quizovichok.entities.Quiz;
import quizovichok.entities.QuizCategory;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface QuizDao {
    void addQuiz(Quiz quiz);
    Quiz getQuiz (UUID uuid);
    List<Quiz> getAllQuizzes();
    void removeQuiz(UUID quiz);
    List<QuizCategory> getAllQuizCategories();
    void addQuizCategory(QuizCategory quizCategory);
    void removeQuizCategory(String quizCategory);
    void addQuizResult(PassQuiz quiz);
    List<PassQuiz> getAllQuizResults();
    PassQuiz getQuizResultById(UUID passQuizId);
}
