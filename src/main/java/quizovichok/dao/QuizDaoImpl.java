package quizovichok.dao;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import quizovichok.entities.PassQuiz;
import quizovichok.entities.Quiz;
import quizovichok.entities.QuizCategory;
import quizovichok.exceptions.ExistQuizCategoryException;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class QuizDaoImpl implements QuizDao {

    private final SessionFactory sessionFactory;

    @Override
    public void addQuiz(Quiz quiz) {
        sessionFactory.inTransaction(session -> {
                    session.persist(quiz);
                }
        );
    }

    @Override
    public void addQuizResult(PassQuiz passQuiz) {
        sessionFactory.inTransaction(session -> {
                    session.persist(passQuiz);
                }
        );
    }

    @Override
    public List<PassQuiz> getAllQuizResults() {
        try (Session session = sessionFactory.openSession()) {
            Query<PassQuiz> query = session.createQuery("FROM PassQuiz", PassQuiz.class);
            return query.list();
        }
    }

    @Override
    public PassQuiz getQuizResultById(UUID passQuizId) {
        try (Session session = sessionFactory.openSession()) {
            PassQuiz passQuiz = session.find(PassQuiz.class, passQuizId);
            return passQuiz;
        }
    }

    @Override
    public Quiz getQuiz(UUID quizId) {
        try (Session session = sessionFactory.openSession()) {
            Quiz quiz = session.find(Quiz.class, quizId);
            return quiz;
        }
    }

    @Override
    public List<Quiz> getAllQuizzes() {
        try (Session session = sessionFactory.openSession()) {
            Query<Quiz> query = session.createQuery("FROM Quiz", Quiz.class);
            return query.list();
        }
    }


    @Override
    public void removeQuiz(UUID quizId) {
        sessionFactory.inTransaction(session -> {
            Quiz quiz = session.find(Quiz.class, quizId);
            session.remove(quiz);
        });
    }

    @Override
    public void addQuizCategory(QuizCategory quizCategory) {
        sessionFactory.inTransaction(session -> {
            QuizCategory findQuizCategory = session.createQuery(
                            "FROM QuizCategory WHERE LOWER(name) = :name", QuizCategory.class)
                    .setParameter("name", quizCategory.getName().trim().toLowerCase())
                    .uniqueResult();

            if (findQuizCategory == null) {
                session.persist(quizCategory);
            } else {
                throw new ExistQuizCategoryException();
            }
        });
    }


    @Override
    public void removeQuizCategory(String quizCategory) {
        sessionFactory.inTransaction(session -> {
            boolean isQuizWithCategory = getAllQuizzes().stream()
                    .map(quiz -> quiz.getCategory())
                    .anyMatch(categoryName -> categoryName.equals(quizCategory));
            if (isQuizWithCategory) {
                throw new ExistQuizCategoryException();
            }

            Query<QuizCategory> query = session.createQuery("FROM QuizCategory where name = :quizCategory", QuizCategory.class);
            query.setParameter("quizCategory", quizCategory);

            QuizCategory QC = query.uniqueResult();
            session.remove(QC);
        });
    }


    @Override
    public List<QuizCategory> getAllQuizCategories() {
        try (Session session = sessionFactory.openSession()) {
            Query<QuizCategory> query = session.createQuery("FROM QuizCategory", QuizCategory.class);
            return query.list();
        }
    }
}
