package testovichok.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import testovichok.entityes.Quiz;
import testovichok.entityes.QuizParameters;
import testovichok.entityes.User;
import testovichok.service.*;
import testovichok.utils.ParametersExtractor;

import java.util.List;
import java.util.UUID;


@WebServlet("/menu/quizzes")
public class QuizServlet extends HttpServlet {
    private QuizService quizService;
    private AuthenticateService authenticateService;
    private ParametersExtractor parametersExtractor;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        authenticateService = (AuthenticateService) config.getServletContext().getAttribute("AuthenticateService");
        quizService = (QuizService) config.getServletContext().getAttribute("QuizService");
        parametersExtractor = (ParametersExtractor) config.getServletContext().getAttribute("ParametersExtractor");
    }

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        User user = (User) req.getSession().getAttribute("user");
        List<Quiz> quizzes = quizService.findAllQuizzesByUserId(user.getId());
        req.getServletContext().setAttribute("quizzes", quizzes);
        req.getServletContext().setAttribute("onlineUsers", authenticateService.getOnlineUsersSet());
        req.getRequestDispatcher("/quizzes.jsp").forward(req, resp);
    }
    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        QuizParameters quizParameters = parametersExtractor.getQuizParameters(req);
        Quiz quiz = quizService.createQuiz(quizParameters);
        quizService.addQuiz(quiz);
        resp.sendRedirect("/menu/quizzes");
    }
    @SneakyThrows
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        UUID quizId = UUID.fromString(req.getParameter("quiz_id"));
        quizService.removeQuiz(quizId);
        resp.sendRedirect("/menu/quizzes");
    }
}
