package quizovichok.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import quizovichok.entities.Quiz;
import quizovichok.entities.QuizParameters;
import quizovichok.service.*;
import quizovichok.utils.ParametersExtractor;

import java.util.List;
import java.util.UUID;


@WebServlet("/quizzes")
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
        List<Quiz> quizzes = quizService.findAllQuizzes();
        req.getServletContext().setAttribute("quizzes", quizzes);
        req.getServletContext().setAttribute("onlineUsers", authenticateService.getOnlineUsers());
        req.getRequestDispatcher("/quizzes.jsp").forward(req, resp);
    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        QuizParameters quizParameters = parametersExtractor.getQuizParameters(req);
        quizService.addQuiz(quizService.createQuiz(quizParameters));
        resp.sendRedirect("/quizzes");
    }


    @SneakyThrows
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        UUID quizId = UUID.fromString(req.getParameter("quiz_id"));
        quizService.removeQuiz(quizId);
        resp.sendRedirect("/quizzes");
    }
}
