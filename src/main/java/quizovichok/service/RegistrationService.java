package quizovichok.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import quizovichok.dao.UserDao;
import quizovichok.entityes.ChangePasswordCredentials;
import quizovichok.entityes.RegistrationCredentials;
import quizovichok.entityes.Roles;
import quizovichok.entityes.User;
import quizovichok.exceptions.IncorrectDataFormatException;
import quizovichok.exceptions.LoginOrPasswordInvalidException;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public class RegistrationService {

    private final UserDao userDao;
    private static final Pattern emailPattern = Pattern.compile("^[A-Z0-9a-z+_.-]+@[A-Za-z0-9._-]+$");
    private static final Pattern passwordPattern = Pattern.compile("^(?=.*[a-zA-Z])(?=.*\\d).{8,}$");

    @SneakyThrows
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
                .password(SecurityService.hashPassword(registrationCredentials.getPassword()))
                .name(registrationCredentials.getName())
                .role(Roles.USER).build();

        userDao.addUser(user);
    }

    public void changePassword(ChangePasswordCredentials changePasswordCredentials, HttpServletRequest req) {
        User user = (User) req.getSession().getAttribute("user");
        boolean isCorrectPassword = SecurityService.passwordEncoder.matches(changePasswordCredentials.getCurrentPassword(), user.getPassword());

        if (isCorrectPassword) {
            Matcher passwordMatcher = passwordPattern.matcher(changePasswordCredentials.getNewPassword());
            if (passwordMatcher.matches()) {
                user.setPassword(SecurityService.hashPassword(changePasswordCredentials.getNewPassword()));
                userDao.updateUser(user);
            }
            else {
                throw new IncorrectDataFormatException();
            }
        } else {
            throw new LoginOrPasswordInvalidException();
        }
    }
}
