package quizovichok.context;

import org.hibernate.SessionFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import quizovichok.config.PasswordEncoderConfig;
import quizovichok.config.SessionFactoryConfig;
import quizovichok.dao.*;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import quizovichok.service.*;
import quizovichok.utils.ParametersExtractor;

@WebListener
public class AppListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        SessionFactory sessionFactory = SessionFactoryConfig.buildSessionFactory();
        BCryptPasswordEncoder bCryptPasswordEncoder = PasswordEncoderConfig.passwordEncoder();
        UserDao userDao = new UserDaoImpl(sessionFactory);
        UserService userService = new UserService(userDao);
        QuizDao jsonQuizDao = new QuizDaoImpl(sessionFactory);
        QuizService quizService = new QuizService(jsonQuizDao);
        ParametersExtractor parametersExtractor = new ParametersExtractor();
        LoginAttemptDao loginAttemptDao = new LoginAttemptDao(sessionFactory);
        LoginAttemptService loginAttemptService = new LoginAttemptService(loginAttemptDao);
        AuthenticateService authenticateService = new AuthenticateService(userDao, loginAttemptService, bCryptPasswordEncoder);
        RegistrationService registrationService = new RegistrationService(userDao, bCryptPasswordEncoder);

        sce.getServletContext().setAttribute("UserService", userService);
        sce.getServletContext().setAttribute("QuizService", quizService);
        sce.getServletContext().setAttribute("ParametersExtractor", parametersExtractor);
        sce.getServletContext().setAttribute("AuthenticateService", authenticateService);
        sce.getServletContext().setAttribute("RegistrationService", registrationService);
        sce.getServletContext().setAttribute("LoginAttemptService", loginAttemptService);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        SessionFactoryConfig.shutdown();
    }
}
