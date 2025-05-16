package testovichok.service;

import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.crypto.password.PasswordEncoder;
import testovichok.dao.UserDao;
import testovichok.entityes.User;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@RequiredArgsConstructor
public class UserService {
    private final UserDao userDao;

    @SneakyThrows
    public boolean hasUser(String login) {
        List<User> users = userDao.getAllUsers();
        return users.stream().anyMatch(user -> user.getLogin().equals(login));
    }
}
