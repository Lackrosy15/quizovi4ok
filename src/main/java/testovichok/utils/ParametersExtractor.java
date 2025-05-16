package testovichok.utils;

import jakarta.servlet.http.HttpServletRequest;
import testovichok.entityes.LoginCredentials;
import testovichok.entityes.QuizParameters;
import testovichok.entityes.RegistrationCredentials;
import testovichok.entityes.User;

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
}
