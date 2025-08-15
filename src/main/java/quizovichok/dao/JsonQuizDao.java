package quizovichok.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.SneakyThrows;
import org.hibernate.SessionFactory;
import quizovichok.config.SessionFactoryConfig;
import quizovichok.entityes.PassQuiz;
import quizovichok.entityes.Quiz;
import quizovichok.entityes.QuizCategory;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static quizovichok.dao.DaoConstants.BASE_PATH;

public class JsonQuizDao implements QuizDao {
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    private final String jsonQuizPath = BASE_PATH + "\\quizzes";
    private final File JsonQuizCategoriesPathFile = new File(BASE_PATH + "\\categories.json");
    private final File JsonQuizResultsPathFile = new File(BASE_PATH + "\\results.json");
    private final SessionFactory sessionFactory = SessionFactoryConfig.getSessionFactory();

    @SneakyThrows
    @Override
    public synchronized void addQuiz(Quiz quiz) {
        objectMapper.writeValue(getQuizFile(quiz.getQuizId()), quiz);
    }

    @Override
    public synchronized void addQuizResult(PassQuiz passQuiz) throws IOException {
        List<PassQuiz> passQuizList = getAllQuizResults();
        passQuizList.add(passQuiz);
        objectMapper.writeValue(JsonQuizResultsPathFile, passQuizList);
    }

    @SneakyThrows
    @Override
    public synchronized List<PassQuiz> getAllQuizResults() {
        return objectMapper.readValue(JsonQuizResultsPathFile, new TypeReference<List<PassQuiz>>() {
        });
    }

    @SneakyThrows
    @Override
    public synchronized PassQuiz getQuizResultById(UUID passQuizId) {
        PassQuiz passQuiz = getAllQuizResults().stream().filter(quizResult -> quizResult.getPassId().equals(passQuizId)).findFirst().get();
        return passQuiz;
    }

    public synchronized Quiz getQuiz(UUID uuid) {
        return readQuiz(getQuizFile(uuid));
    }

    @Override
    public synchronized List<Quiz> getAllQuizzes() {
        return Arrays.stream(new File(jsonQuizPath).listFiles())
                .map(file -> readQuiz(file))
                .collect(Collectors.toList());
    }

    @SneakyThrows
    @Override
    public synchronized void removeQuiz(UUID quizId) {
        getQuizFile(quizId).delete();
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

    private File getQuizFile(UUID uuid) {
        return new File(jsonQuizPath + "\\" + uuid + ".json");
    }

    @SneakyThrows
    private Quiz readQuiz(File file) {
        return objectMapper.readValue(file, Quiz.class);
    }
}
