package testovichok.context;


import testovichok.dao.JsonUserDao;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import testovichok.dao.JsonQuizDao;
import testovichok.dao.QuizDao;
import testovichok.dao.UserDao;
import testovichok.service.*;
import testovichok.utils.ParametersExtractor;

@WebListener
public class AppListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        UserDao userDao = new JsonUserDao();
        UserService userService = new UserService(userDao);
        QuizDao jsonQuizDao = new JsonQuizDao();
        QuizService quizService = new QuizService(jsonQuizDao);
        ParametersExtractor parametersExtractor = new ParametersExtractor();
        LoginAttemptService loginAttemptService = new LoginAttemptService();
        AuthenticateService authenticateService = new AuthenticateService(userDao, loginAttemptService);
        RegistrationService registrationService = new RegistrationService(userDao);

        sce.getServletContext().setAttribute("UserService", userService);
        sce.getServletContext().setAttribute("QuizService", quizService);
        sce.getServletContext().setAttribute("ParametersExtractor", parametersExtractor);
        sce.getServletContext().setAttribute("AuthenticateService", authenticateService);
        sce.getServletContext().setAttribute("RegistrationService", registrationService);
    }
}
