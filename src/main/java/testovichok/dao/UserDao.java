package testovichok.dao;

import testovichok.entityes.User;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface UserDao {
    void addUser(User user);
    List<User> getAllUsers();
    void removeUser(UUID userId);
}
