package quizovichok.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import quizovichok.entityes.QuizCategory;
import quizovichok.service.QuizService;
import quizovichok.utils.ParametersExtractor;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/create-quiz")
public class CreateQuizServlet extends HttpServlet {

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
        List<QuizCategory> quizCategories = quizService.findAllQuizCategories();
        req.getServletContext().setAttribute("quizCategories", quizCategories);
        req.getRequestDispatcher("/admin/create-quiz.jsp").forward(req, resp);
    }
}
