package testovichok.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import testovichok.entityes.PassQuiz;
import testovichok.entityes.Quiz;
import testovichok.entityes.User;
import testovichok.service.QuizService;
import testovichok.utils.ParametersExtractor;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@WebServlet("/results")
public class ResultsQuizzesServlet extends HttpServlet {

    private QuizService quizService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        quizService = (QuizService) config.getServletContext().getAttribute("QuizService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UUID userId = ((User) req.getSession().getAttribute("user")).getId();
        List<PassQuiz> passQuizzesListOfUser = quizService.findAllPassQuizzesOfUser(userId);
        req.getServletContext().setAttribute("passQuizzesOfUser", passQuizzesListOfUser);
        req.getRequestDispatcher("/results.jsp").forward(req, resp);
    }
}
