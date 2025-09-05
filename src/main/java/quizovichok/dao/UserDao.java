package quizovichok.dao;

import quizovichok.entities.OnlineUser;
import quizovichok.entities.User;

import java.util.List;
import java.util.UUID;

public interface UserDao {
    void addUser(User user);
    List<User> getAllUsers();
    void removeUser(UUID userId);
    void updateUser(User user);
    void addOnlineUser(OnlineUser onlineUser);
    void removeOnlineUser(String login);
    List<OnlineUser> getOnlineUsers();
}
