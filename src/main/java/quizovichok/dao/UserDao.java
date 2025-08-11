package quizovichok.dao;

import quizovichok.entityes.User;

import java.util.List;
import java.util.UUID;

public interface UserDao {
    void addUser(User user);
    List<User> getAllUsers();
    void removeUser(UUID userId);
    void updateUser(User user);
}
