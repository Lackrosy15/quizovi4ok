package quizovichok.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import quizovichok.entities.Quiz;
import quizovichok.entities.QuizCategory;
import quizovichok.entities.QuizParameters;
import quizovichok.service.QuizService;
import quizovichok.utils.ParametersExtractor;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@WebServlet("/admin/quiz-edit/*")
public class EditQuizServlet extends HttpServlet {

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
        List<QuizCategory> quizCategories = quizService.findAllQuizCategories();
        req.getServletContext().setAttribute("quizCategories", quizCategories);
        req.setAttribute("quiz", quiz);

        req.getRequestDispatcher("/admin/edit-quiz.jsp").forward(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var quizId = req.getPathInfo().replace("/", "");
        QuizParameters quizParameters = parametersExtractor.getQuizParameters(req);
        quizService.removeQuiz(UUID.fromString(quizId));
        quizService.addQuiz(quizService.createQuiz(quizParameters));
        resp.sendRedirect("/quizzes");
    }
}
