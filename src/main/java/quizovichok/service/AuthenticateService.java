package quizovichok.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.Session;
import org.springframework.security.crypto.password.PasswordEncoder;
import quizovichok.dao.UserDao;
import quizovichok.entities.LoginAttempt;
import quizovichok.entities.LoginCredentials;
import quizovichok.entities.OnlineUser;
import quizovichok.entities.User;
import quizovichok.exceptions.LoginOrPasswordInvalidException;
import quizovichok.exceptions.NoUserInDataBase;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@RequiredArgsConstructor
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticateService {
    final UserDao userDao;
    final LoginAttemptService loginAttemptService;
    final PasswordEncoder passwordEncoder;

    public User authenticate(LoginCredentials loginCredentials, HttpServletRequest req) {
        User foundUser = userDao.getAllUsers().stream()
                .filter(user -> user.getLogin().equals(loginCredentials.getLogin()))
                .findFirst()
                .orElseThrow(() -> new NoUserInDataBase());

        LoginAttempt loginAttempt = loginAttemptService.checkUserAccess(loginCredentials);

        boolean isCorrectPassword = passwordEncoder.matches(loginCredentials.getPassword(), foundUser.getPassword());

        if (isCorrectPassword) {
            req.getSession().setAttribute("user", foundUser);
            userDao.addOnlineUser(OnlineUser.builder().login(foundUser.getLogin()).name(foundUser.getName()).build());
            loginAttemptService.resetLoginAttempt(foundUser.getLogin());
            return foundUser;
        } else {
            loginAttemptService.incrementAttempts(foundUser.getLogin());
            throw new LoginOrPasswordInvalidException();
        }
    }

    public List<OnlineUser> getOnlineUsers() {
        return userDao.getOnlineUsers();
    }

    public void logoutUser(HttpSession session) {
        User user = (User) session.getAttribute("user");
        userDao.removeOnlineUser(user.getLogin());
        session.removeAttribute("user");
    }
}
