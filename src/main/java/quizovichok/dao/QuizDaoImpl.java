package quizovichok.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.SneakyThrows;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import quizovichok.config.SessionFactoryConfig;
import quizovichok.entityes.PassQuiz;
import quizovichok.entityes.Quiz;
import quizovichok.entityes.QuizCategory;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static quizovichok.dao.DaoConstants.BASE_PATH;

public class QuizDaoImpl implements QuizDao {
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    private final String jsonQuizPath = BASE_PATH + "\\quizzes";
    private final File JsonQuizCategoriesPathFile = new File(BASE_PATH + "\\categories.json");
    private final File JsonQuizResultsPathFile = new File(BASE_PATH + "\\results.json");
    private final SessionFactory sessionFactory = SessionFactoryConfig.getSessionFactory();

    @SneakyThrows
    @Override
    public synchronized void addQuiz(Quiz quiz) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(quiz);
            transaction.commit();
        }
        //objectMapper.writeValue(getQuizFile(quiz.getQuizId()), quiz);
    }

    @Override
    public synchronized void addQuizResult(PassQuiz passQuiz) throws IOException {
//        List<PassQuiz> passQuizList = getAllQuizResults();
//        passQuizList.add(passQuiz);
//        objectMapper.writeValue(JsonQuizResultsPathFile, passQuizList);
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(passQuiz);
            transaction.commit();
        }
    }

    @SneakyThrows
    @Override
    public synchronized List<PassQuiz> getAllQuizResults() {
//        return objectMapper.readValue(JsonQuizResultsPathFile, new TypeReference<List<PassQuiz>>() {
//        });
        try (Session session = sessionFactory.openSession()) {
            Query<PassQuiz> query = session.createQuery("FROM PassQuiz", PassQuiz.class);
            List<PassQuiz> passQuizzes = query.list();
            return passQuizzes;
        }
    }

    @SneakyThrows
    @Override
    public synchronized PassQuiz getQuizResultById(UUID passQuizId) {
//        PassQuiz passQuiz = getAllQuizResults().stream().filter(quizResult -> quizResult.getPassId().equals(passQuizId)).findFirst().get();
//        return passQuiz;
        try (Session session = sessionFactory.openSession()) {
            PassQuiz passQuiz = session.find(PassQuiz.class, passQuizId);
            return passQuiz;
        }
    }

    public synchronized Quiz getQuiz(UUID quizId) {
//        return readQuiz(getQuizFile(uuid));
        try (Session session = sessionFactory.openSession()) {
            Quiz quiz = session.find(Quiz.class, quizId);
            return quiz;
        }
    }

    @Override
    public synchronized List<Quiz> getAllQuizzes() {
//        return Arrays.stream(new File(jsonQuizPath).listFiles())
//                .map(file -> readQuiz(file))
//                .collect(Collectors.toList());
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Query<Quiz> query = session.createQuery("FROM Quiz", Quiz.class);
            List<Quiz> quizzes = query.list();
            return quizzes;
        }
    }

    @SneakyThrows
    @Override
    public synchronized void removeQuiz(UUID quizId) {
//        getQuizFile(quizId).delete();
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Quiz quiz = session.find(Quiz.class, quizId);
            session.remove(quiz);
            transaction.commit();
        }
    }

    @SneakyThrows
    @Override
    public synchronized void addQuizCategory(QuizCategory quizCategory) {
//        List<QuizCategory> quizCategories = getAllQuizCategories();
//        quizCategories.add(quizCategory);
//        objectMapper.writeValue(JsonQuizCategoriesPathFile, quizCategories);
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(quizCategory);
            transaction.commit();
        }
    }

    @SneakyThrows
    @Override
    public synchronized void removeQuizCategory(String quizCategory) {
//        List<QuizCategory> quizCategories = getAllQuizCategories().stream().filter(QC -> !(QC.getName().equals(quizCategory))).collect(Collectors.toList());
//        objectMapper.writeValue(JsonQuizCategoriesPathFile, quizCategories);
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Query<QuizCategory> query = session.createQuery("FROM QuizCategory where name = ':quizCategory'", QuizCategory.class);
            query.setParameter("quizCategory", quizCategory);

            QuizCategory QC = query.uniqueResult();
            session.remove(QC);
            transaction.commit();
        }
    }

    @SneakyThrows
    @Override
    public synchronized List<QuizCategory> getAllQuizCategories() {
//        return objectMapper.readValue(JsonQuizCategoriesPathFile, new TypeReference<List<QuizCategory>>() {
//        });
        try (Session session = sessionFactory.openSession()) {
            Query<QuizCategory> query = session.createQuery("FROM QuizCategory", QuizCategory.class);
            List<QuizCategory> quizCategories = query.list();
            return quizCategories;
        }
    }

//    private File getQuizFile(UUID uuid) {
//        return new File(jsonQuizPath + "\\" + uuid + ".json");
//    }
//
//    @SneakyThrows
//    private Quiz readQuiz(File file) {
//        return objectMapper.readValue(file, Quiz.class);
//    }
}
