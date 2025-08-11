package quizovichok.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import quizovichok.entityes.PassQuizParameters;
import quizovichok.entityes.Quiz;
import quizovichok.service.QuizService;
import quizovichok.utils.ParametersExtractor;

import java.io.IOException;

@WebServlet("/quizzes/*")
public class PassQuizServlet extends HttpServlet {

    private QuizService quizService;
    private ParametersExtractor parametersExtractor;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        quizService = (QuizService) config.getServletContext().getAttribute("QuizService");
        parametersExtractor = (ParametersExtractor) config.getServletContext().getAttribute("ParametersExtractor");
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var quizId = req.getPathInfo().replace("/", "");
        Quiz quiz = quizService.findQuizById(quizId);
        req.setAttribute("quiz", quiz);
        req.getRequestDispatcher("/pass-quiz.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Quiz quiz = quizService.findQuizById(req.getPathInfo().replace("/", ""));
        PassQuizParameters passQuizParameters = parametersExtractor.getPassQuizParameters(req, quiz);
        quizService.addQuizResult(quizService.createPassQuiz(passQuizParameters));
        resp.sendRedirect("/quizzes");
    }
}
