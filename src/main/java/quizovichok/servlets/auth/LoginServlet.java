package quizovichok.servlets.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import quizovichok.entities.LoginCredentials;
import quizovichok.entities.User;
import quizovichok.exceptions.LoginOrPasswordInvalidException;
import quizovichok.exceptions.NoUserInDataBase;
import quizovichok.exceptions.UserBlockedException;

import java.io.IOException;

@WebServlet(urlPatterns = "/login", initParams = {@WebInitParam(name = "resourceName", value = "/login.jsp")})
public class LoginServlet extends BaseAuthenticateServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        LoginCredentials loginCredentials = parametersExtractor.getLoginCredentials(req);

        try {
            User user = authenticateService.authenticate(loginCredentials, req);
            resp.sendRedirect("/quizzes");
            return;
        } catch (UserBlockedException e) {
            req.setAttribute("errorMessageCode429", " ❗Вы временно заблокированы. Слишком много запросов. Попробуйте снова через 5 минут!");
        } catch (LoginOrPasswordInvalidException e) {
            req.setAttribute("errorMessageInvalidLoginOrPassword", "❗Неправильный логин или пароль");
        } catch (NoUserInDataBase e) {
            resp.sendRedirect("/registration");
            return;
        }

        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }
}
