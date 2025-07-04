package testovichok.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import testovichok.entityes.ChangePasswordCredentials;
import testovichok.entityes.User;
import testovichok.service.AuthenticateService;
import testovichok.service.QuizService;
import testovichok.service.RegistrationService;
import testovichok.utils.ParametersExtractor;

import java.io.IOException;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
    private ParametersExtractor parametersExtractor;
    private RegistrationService registrationService;
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        parametersExtractor = (ParametersExtractor) config.getServletContext().getAttribute("ParametersExtractor");
        registrationService = (RegistrationService) config.getServletContext().getAttribute("RegistrationService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/profile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ChangePasswordCredentials changePasswordCredentials = parametersExtractor.getChangePasswordParameters(req);
        registrationService.changePassword(changePasswordCredentials, req);
    }
}
