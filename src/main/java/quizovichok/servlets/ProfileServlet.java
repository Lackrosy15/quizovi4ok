package quizovichok.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import quizovichok.entityes.ChangePasswordCredentials;
import quizovichok.exceptions.IncorrectDataFormatException;
import quizovichok.exceptions.LoginOrPasswordInvalidException;
import quizovichok.service.RegistrationService;
import quizovichok.utils.ParametersExtractor;

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
        try {
            registrationService.changePassword(changePasswordCredentials, req);
            resp.sendRedirect("/profile");
        } catch (LoginOrPasswordInvalidException e) {
            req.setAttribute("errorMessageInvalidLoginOrPassword", "❗Вы ввели неправильный пароль");
        } catch (IncorrectDataFormatException e) {
            req.setAttribute("errorMessageCode422", " ❗Неверный формат данных. Пароль должен содержать буквы, цифры и быть не менее 8 символов.");
        }
        req.getRequestDispatcher("/profile.jsp").forward(req, resp);
    }
}
