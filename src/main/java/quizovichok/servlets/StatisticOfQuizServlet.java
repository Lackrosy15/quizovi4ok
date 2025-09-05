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
import java.util.List;
import java.util.UUID;

@WebServlet("/admin/stats/*")
public class StatisticOfQuizServlet extends HttpServlet {
    private QuizService quizService;


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        quizService = (QuizService) config.getServletContext().getAttribute("QuizService");
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UUID quizId = UUID.fromString(req.getPathInfo().replace("/", ""));
        List<PassQuiz> passingQuizzesByQuizId = quizService.findAllPassOfQuizByQuizId(quizId);
        req.getServletContext().setAttribute("passingQuizzesByQuizId", passingQuizzesByQuizId);
        req.getRequestDispatcher("/admin/stats-quiz.jsp").forward(req, resp);
    }
}
