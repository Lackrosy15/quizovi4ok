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

@WebServlet("/admin/stats")
public class StatisticServlet extends HttpServlet {
    private QuizService quizService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        quizService = (QuizService) config.getServletContext().getAttribute("QuizService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<PassQuiz> passQuizzesList = quizService.findAllPassQuizzesGroupingByQuizId();
        req.getServletContext().setAttribute("passQuizzesList", passQuizzesList);
        req.getRequestDispatcher("/admin/stats.jsp").forward(req, resp);
    }
}
