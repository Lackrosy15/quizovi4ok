package quizovichok.config;

import lombok.Getter;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import quizovichok.entities.*;


public class SessionFactoryConfig {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static SessionFactory buildSessionFactory() {
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
                    .addAnnotatedClass(LoginAttempt.class)
                    .addAnnotatedClass(OnlineUser.class)
                    .getMetadataBuilder()
                    .build();

            return metadata.getSessionFactoryBuilder().build();

        } catch (Exception ex) {
            System.err.println("Initial SessionFactory creation failed: " + ex.getMessage());
            ex.printStackTrace();
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static void shutdown() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
            System.out.println("SessionFactory closed.");
        }
    }
}
