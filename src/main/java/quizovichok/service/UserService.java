package quizovichok.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import quizovichok.dao.UserDao;
import quizovichok.entityes.User;

import java.util.List;

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
