package quizovichok.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import quizovichok.config.SessionFactoryConfig;
import quizovichok.entities.OnlineUser;
import quizovichok.entities.User;

import java.io.File;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class UserDaoImpl implements UserDao {

    private final SessionFactory sessionFactory;


    @Override
    public void addUser(User user) {
        sessionFactory.inTransaction(session -> {
            session.persist(user);
        });
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("FROM User", User.class);
            return query.list();
        }
    }

    @Override
    public void removeUser(UUID userId) {
        sessionFactory.inTransaction(session -> {
            User user = session.find(User.class, userId);
            session.remove(user);
        });
    }

    public void updateUser(User user) {
        sessionFactory.inTransaction(session -> {
            session.merge(user);
        });
    }

    @Override
    public void addOnlineUser(OnlineUser onlineUser) {
        sessionFactory.inTransaction(session -> {
            session.persist(onlineUser);
        });
    }

    @Override
    public void removeOnlineUser(String login) {
        sessionFactory.inTransaction(session -> {
            OnlineUser onlineUser = session.find(OnlineUser.class, login);
            session.remove(onlineUser);
        });
    }

    @Override
    public List<OnlineUser> getOnlineUsers() {
        try (Session session = sessionFactory.openSession()) {
            Query<OnlineUser> query = session.createQuery("FROM OnlineUser", OnlineUser.class);
            return query.list();
        }
    }
}
