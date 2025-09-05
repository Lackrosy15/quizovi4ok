package quizovichok.servlets.auth;


import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import quizovichok.entities.RegistrationCredentials;
import quizovichok.exceptions.IncorrectDataFormatException;

@WebServlet(urlPatterns = "/registration", initParams = {@WebInitParam(name = "resourceName", value = "/registration.jsp")})
public class RegistrationServlet extends BaseAuthenticateServlet {

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        RegistrationCredentials registrationCredentials = parametersExtractor.getRegistrationCredentials(req);

        try {
            if (userService.hasUser(registrationCredentials.getLogin())) {
                req.getRequestDispatcher("/isRegistration.html").forward(req, resp);
            } else if (registrationService.isValidateData(registrationCredentials.getLogin(), registrationCredentials.getPassword())) {
                registrationService.registerUser(registrationCredentials);
                req.getRequestDispatcher("/registration-success.html").forward(req, resp);
            } else {
                req.getRequestDispatcher("/registration.jsp").forward(req, resp);
            }
        } catch (IncorrectDataFormatException e) {
            req.setAttribute("errorMessageCode422", " ❗Неверный формат данных. Логин должен быть почтой, а пароль содержать буквы, цифры и быть не менее 8 символов.");
        }
        req.getRequestDispatcher("/registration.jsp").forward(req, resp);
    }
}
