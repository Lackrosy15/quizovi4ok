package quizovichok.config;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import quizovichok.entityes.PassQuiz;
import quizovichok.entityes.Quiz;
import quizovichok.entityes.QuizCategory;
import quizovichok.entityes.User;

public class SessionFactoryConfig {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            shutdown();
        }));
    }

    public static void shutdown() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
            System.out.println("SessionFactory closed.");
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    private static SessionFactory buildSessionFactory() {
        try {
//Создание контейнера для всех сервисов Hibernate
            StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
                    .configure("hibernate.cfg.xml")
                    .build();

//Источник метаданных (анализирует аннотации @Entity)
            Metadata metadata = new MetadataSources(standardRegistry)
                    .addAnnotatedClass(User.class)
                    .addAnnotatedClass(QuizCategory.class)
                    .addAnnotatedClass(Quiz.class)
                    .addAnnotatedClass(PassQuiz.class)
                    .getMetadataBuilder()
                    .build();

            return metadata.getSessionFactoryBuilder().build();

        } catch (Exception ex) {
            System.err.println("Initial SessionFactory creation failed: " + ex.getMessage());
            ex.printStackTrace();
            throw new ExceptionInInitializerError(ex);
        }
    }
}
