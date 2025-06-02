package testovichok.utils;

import jakarta.servlet.http.HttpServletRequest;
import testovichok.entityes.*;

public class ParametersExtractor {
    public LoginCredentials getLoginCredentials(HttpServletRequest req) {
        return new LoginCredentials(req.getParameter("login"), req.getParameter("password"));
    }

    public RegistrationCredentials getRegistrationCredentials(HttpServletRequest req) {
        return new RegistrationCredentials(req.getParameter("name"), req.getParameter("login"), req.getParameter("password"));
    }

    public QuizParameters getQuizParameters(HttpServletRequest req) {
        return new QuizParameters(req.getParameter("quizName"), req.getParameter("category"), req.getParameter("imgUrl"), (User) req.getSession().getAttribute("user"));
    }

    public ChangePasswordCredentials getChangePasswordParameters(HttpServletRequest req) {
        return new ChangePasswordCredentials(req.getParameter("currentPassword"), req.getParameter("newPassword"));
    }
}
