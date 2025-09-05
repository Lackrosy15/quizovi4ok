package quizovichok.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import quizovichok.dao.UserDao;
import quizovichok.entities.ChangePasswordCredentials;
import quizovichok.entities.RegistrationCredentials;
import quizovichok.entities.Roles;
import quizovichok.entities.User;
import quizovichok.exceptions.IncorrectDataFormatException;
import quizovichok.exceptions.LoginOrPasswordInvalidException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegistrationService {

    final UserDao userDao;
    final PasswordEncoder passwordEncoder;
    static final Pattern emailPattern = Pattern.compile("^[A-Z0-9a-z+_.-]+@[A-Za-z0-9._-]+$");
    static final Pattern passwordPattern = Pattern.compile("^(?=.*[a-zA-Z])(?=.*\\d).{8,}$");

    public boolean isValidateData(String email, String password) {
        Matcher emailMatcher = emailPattern.matcher(email);
        Matcher passwordMatcher = passwordPattern.matcher(password);

        if (emailMatcher.matches()) {
            if (passwordMatcher.matches()) {
                return true;
            } else {
                throw new IncorrectDataFormatException();
            }
        } else {
            throw new IncorrectDataFormatException();
        }
    }

    public void registerUser(RegistrationCredentials registrationCredentials) {
        User user = User.builder()
                .login(registrationCredentials.getLogin())
                .password(passwordEncoder.encode(registrationCredentials.getPassword()))
                .name(registrationCredentials.getName())
                .role(Roles.USER).build();

        userDao.addUser(user);
    }

    public void changePassword(ChangePasswordCredentials changePasswordCredentials, HttpServletRequest req) {
        User user = (User) req.getSession().getAttribute("user");
        boolean isCorrectPassword = passwordEncoder.matches(changePasswordCredentials.getCurrentPassword(), user.getPassword());

        if (isCorrectPassword) {
            Matcher passwordMatcher = passwordPattern.matcher(changePasswordCredentials.getNewPassword());
            if (passwordMatcher.matches()) {
                user.setPassword(passwordEncoder.encode(changePasswordCredentials.getNewPassword()));
                userDao.updateUser(user);
            } else {
                throw new IncorrectDataFormatException();
            }
        } else {
            throw new LoginOrPasswordInvalidException();
        }
    }
}
