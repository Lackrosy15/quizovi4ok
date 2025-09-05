package quizovichok.dao;

import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import quizovichok.entities.LoginAttempt;
import quizovichok.entities.PassQuiz;
import quizovichok.entities.Quiz;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicReference;

@RequiredArgsConstructor
public class LoginAttemptDao {
    private final SessionFactory sessionFactory;

    public LoginAttempt getLoginAttempt(String login) {
        AtomicReference<LoginAttempt> loginAttempt = new AtomicReference<>();

        sessionFactory.inTransaction(session -> {
            LoginAttempt existingAttempt = session.find(LoginAttempt.class, login);
            if (existingAttempt != null) {
                loginAttempt.set(existingAttempt);
            } else {
                LoginAttempt newAttempt = LoginAttempt.builder().login(login).countOfAttempts(1).lastAttemptsTime(LocalDateTime.now()).build();

                session.persist(newAttempt);
                loginAttempt.set(newAttempt);
            }
        });

        return loginAttempt.get();
    }

    public void resetLoginAttempts(LoginAttempt loginAttempt) {
        sessionFactory.inTransaction(session -> {
            loginAttempt.setCountOfAttempts(0);
            session.merge(loginAttempt);
        });
    }

    public void resetLoginAttempts(String login) {
        sessionFactory.inTransaction(session -> {
            LoginAttempt loginAttempt = session.find(LoginAttempt.class, login);
            loginAttempt.setCountOfAttempts(0);
            session.merge(loginAttempt);
        });
    }

    public void incrementAttempts(String login) {
        sessionFactory.inTransaction(session -> {
            LoginAttempt loginAttempt = session.find(LoginAttempt.class, login);
            loginAttempt.setCountOfAttempts(loginAttempt.getCountOfAttempts() + 1);
            loginAttempt.setLastAttemptsTime(LocalDateTime.now());
            session.merge(loginAttempt);
        });
    }
}
