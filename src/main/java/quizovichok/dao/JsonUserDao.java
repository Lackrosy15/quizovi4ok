package quizovichok.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.hibernate.SessionFactory;
import quizovichok.config.SessionFactoryConfig;
import quizovichok.entityes.User;

import java.io.File;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static quizovichok.dao.DaoConstants.BASE_PATH;

public class JsonUserDao implements UserDao {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final File JsonUsersPathFile = new File(BASE_PATH + "\\users.json");
    private final SessionFactory sessionFactory = SessionFactoryConfig.getSessionFactory();


    @SneakyThrows
    @Override
    public synchronized void addUser(User user) {
        List<User> users = getAllUsers();
        users.add(user);
        objectMapper.writeValue(JsonUsersPathFile, users);
    }

    @SneakyThrows
    @Override
    public synchronized List<User> getAllUsers() {
        return objectMapper.readValue(JsonUsersPathFile, new TypeReference<List<User>>() {
        });
    }

    @SneakyThrows
    @Override
    public void removeUser(UUID userId) {
        List<User> users = getAllUsers().stream().filter(user -> !(user.getId().equals(userId))).collect(Collectors.toList());
        objectMapper.writeValue(JsonUsersPathFile, users);
    }

    @SneakyThrows
    public void updateUser(User user) {
        List<User> UsersList = getAllUsers().stream()
                .map(u -> {
                    if (u.getId().equals(user.getId())) {
                        return user;
                    } else {
                        return u;
                    }
                })
                .toList();
        objectMapper.writeValue(JsonUsersPathFile, UsersList);
    }
}
