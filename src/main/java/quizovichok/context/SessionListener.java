package quizovichok.context;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import quizovichok.dao.UserDao;
import quizovichok.entities.User;
import quizovichok.service.AuthenticateService;
import quizovichok.service.LoginAttemptService;

@WebListener
public class SessionListener implements HttpSessionListener {

//    @Override
//    public void sessionCreated(HttpSessionEvent se) {
//        System.out.println("Session created: " + se.getSession().getId());
//        se.getSession().setAttribute("lastActivity", System.currentTimeMillis());
//    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        AuthenticateService authenticateService = (AuthenticateService) session.getServletContext().getAttribute("AuthenticateService");
        authenticateService.logoutUser(session);
    }

}