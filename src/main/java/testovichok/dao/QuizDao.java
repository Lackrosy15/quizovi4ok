package testovichok.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import testovichok.entityes.Quiz;
import testovichok.entityes.QuizCategory;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface QuizDao {
    public void addQuiz(Quiz quiz) throws IOException;
    public List<Quiz> getAllQuizzes() throws IOException;
    public void removeQuiz(UUID quiz) throws IOException;
    List<QuizCategory> getAllQuizCategories();
    void addQuizCategory(QuizCategory quizCategory);
    void removeQuizCategory(String quizCategory);
}
