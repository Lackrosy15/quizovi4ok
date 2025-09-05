package quizovichok.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import quizovichok.entities.PassQuiz;
import quizovichok.entities.User;
import quizovichok.service.QuizService;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@WebServlet("/results/*")
public class QuizResultsServlet extends HttpServlet {
    private QuizService quizService;


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        quizService = (QuizService) config.getServletContext().getAttribute("QuizService");
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UUID userId = ((User) req.getSession().getAttribute("user")).getId();
        UUID quizId = UUID.fromString(req.getPathInfo().replace("/", ""));
        List<PassQuiz> passingTheQuizByUser = quizService.findAllPassOfQuizByIdOfUser(userId, quizId);
        req.getServletContext().setAttribute("passingTheQuizByUser", passingTheQuizByUser);
        req.getRequestDispatcher("/results-quiz.jsp").forward(req, resp);
    }
}
