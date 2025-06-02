package testovichok.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import testovichok.entityes.Quiz;
import testovichok.entityes.QuizCategory;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class JsonQuizDao implements QuizDao {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final File JsonQuizPathFile = new File("C:\\Users\\User\\IdeaProjects\\my-webapp\\src\\main\\resources\\quizzes.json");
    private final File JsonQuizCategoriesPathFile = new File("C:\\Users\\User\\IdeaProjects\\my-webapp\\src\\main\\resources\\categories.json");

    @SneakyThrows
    @Override
    public synchronized void addQuiz(Quiz quiz) {
        List<Quiz> quizzes = getAllQuizzes();
        quizzes.add(quiz);
        objectMapper.writeValue(JsonQuizPathFile, quizzes);
    }

    @SneakyThrows
    @Override
    public synchronized List<Quiz> getAllQuizzes() {
        return objectMapper.readValue(JsonQuizPathFile, new TypeReference<List<Quiz>>() {
        });
    }

    @SneakyThrows
    @Override
    public synchronized void removeQuiz(UUID quizId) {
        List<Quiz> quizzes = getAllQuizzes().stream().filter(quiz -> !(quiz.getQuizId().equals(quizId))).collect(Collectors.toList());
        objectMapper.writeValue(JsonQuizPathFile, quizzes);
    }

    @SneakyThrows
    @Override
    public synchronized void addQuizCategory(QuizCategory quizCategory) {
        List<QuizCategory> quizCategories = getAllQuizCategories();
        quizCategories.add(quizCategory);
        objectMapper.writeValue(JsonQuizCategoriesPathFile, quizCategories);
    }

    @SneakyThrows
    @Override
    public synchronized void removeQuizCategory(String quizCategory) {
        List<QuizCategory> quizCategories = getAllQuizCategories().stream().filter(QC -> !(QC.getName().equals(quizCategory))).collect(Collectors.toList());
        objectMapper.writeValue(JsonQuizCategoriesPathFile, quizCategories);
    }

    @SneakyThrows
    @Override
    public synchronized List<QuizCategory> getAllQuizCategories() {
        return objectMapper.readValue(JsonQuizCategoriesPathFile, new TypeReference<List<QuizCategory>>() {
        });
    }
}
