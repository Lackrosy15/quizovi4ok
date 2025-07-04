package testovichok.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import testovichok.dao.JsonUserDao;
import testovichok.dao.UserDao;
import testovichok.entityes.ChangePasswordCredentials;
import testovichok.entityes.LoginAttempt;
import testovichok.entityes.LoginCredentials;
import testovichok.entityes.User;
import testovichok.exceptions.LoginOrPasswordInvalidException;
import testovichok.exceptions.UserBlockedException;
import testovichok.servlets.QuizServlet;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@RequiredArgsConstructor
public class AuthenticateService {
    private final UserDao userDao;
    @Getter
    private final Map<String, String> onlineUsersMap = new ConcurrentHashMap<>();
    private final LoginAttemptService loginAttemptService;

    public User authenticate(LoginCredentials loginCredentials, HttpServletRequest req) {
        User foundUser = userDao.getAllUsers().stream()
                .filter(user -> user.getLogin().equals(loginCredentials.getLogin()))
                .findFirst()
                .orElseThrow(() -> new LoginOrPasswordInvalidException());

        LoginAttempt loginAttempt = loginAttemptService.checkUserAccess(loginCredentials);

        boolean isCorrectPassword = SecurityService.passwordEncoder.matches(loginCredentials.getPassword(), foundUser.getPassword());

        if (isCorrectPassword) {
            req.getSession().setAttribute("user", foundUser);
            onlineUsersMap.put(foundUser.getLogin(), foundUser.getName());
            loginAttempt.setCountOfAttempts(new AtomicInteger(0));
            return foundUser;
        } else {
            loginAttempt.incrementAttempts();
            throw new LoginOrPasswordInvalidException();
        }
    }

    public void logoutUser(HttpServletRequest req) {
        User user = (User) req.getSession().getAttribute("user");
        onlineUsersMap.remove(user.getLogin());
        req.getSession().removeAttribute("user");
    }
}
