package quizovichok.service;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import quizovichok.dao.LoginAttemptDao;
import quizovichok.dao.QuizDao;
import quizovichok.entities.LoginAttempt;
import quizovichok.entities.LoginCredentials;
import quizovichok.exceptions.UserBlockedException;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
@RequiredArgsConstructor
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginAttemptService {
    final LoginAttemptDao loginAttemptDao;

    public LoginAttempt checkUserAccess(LoginCredentials loginCredentials) {
        LoginAttempt loginAttempt = loginAttemptDao.getLoginAttempt(loginCredentials.getLogin());

        if (isBlockedExpired(loginAttempt)) {
            loginAttemptDao.resetLoginAttempts(loginAttempt);
        }

        if (isBlocked(loginAttempt)) {
            throw new UserBlockedException();
        }
        return loginAttempt;
    }

    public void resetLoginAttempt(String login) {
        loginAttemptDao.resetLoginAttempts(login);
    }

    public void incrementAttempts(String login) {
        loginAttemptDao.incrementAttempts(login);
    }

    public boolean isBlocked(LoginAttempt loginAttempt) {
        return loginAttempt.getCountOfAttempts() > 3;
    }

    public boolean isBlockedExpired(LoginAttempt loginAttempt) {
        return LocalDateTime.now().isAfter(loginAttempt.getLastAttemptsTime().plus(5, ChronoUnit.MINUTES));
    }

}
