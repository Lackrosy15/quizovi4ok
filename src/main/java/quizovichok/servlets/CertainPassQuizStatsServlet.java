package quizovichok.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import quizovichok.entities.PassQuiz;
import quizovichok.service.QuizService;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/admin/stat/*")
public class CertainPassQuizStatsServlet extends HttpServlet {
    private QuizService quizService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        quizService = (QuizService) config.getServletContext().getAttribute("QuizService");
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UUID quizPassId = UUID.fromString(req.getPathInfo().replace("/", ""));
        PassQuiz passQuiz = quizService.findPassQuizById(quizPassId);
        req.getServletContext().setAttribute("passQuiz", passQuiz);
        req.getRequestDispatcher("/admin/certain-quiz-stat.jsp").forward(req, resp);
    }
}
