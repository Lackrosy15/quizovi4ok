package quizovichok.servlets.auth;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import quizovichok.service.AuthenticateService;
import quizovichok.utils.ParametersExtractor;
import quizovichok.service.RegistrationService;
import quizovichok.service.UserService;

import java.io.IOException;

public abstract class BaseAuthenticateServlet extends HttpServlet {
    protected UserService userService;
    protected ParametersExtractor parametersExtractor;
    protected AuthenticateService authenticateService;
    protected RegistrationService registrationService;
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        userService = (UserService) config.getServletContext().getAttribute("UserService");
        parametersExtractor = (ParametersExtractor) config.getServletContext().getAttribute("ParametersExtractor");
        authenticateService = (AuthenticateService) config.getServletContext().getAttribute("AuthenticateService");
        registrationService = (RegistrationService) config.getServletContext().getAttribute("RegistrationService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(getInitParameter("resourceName")).forward(req, resp);
    }
}
