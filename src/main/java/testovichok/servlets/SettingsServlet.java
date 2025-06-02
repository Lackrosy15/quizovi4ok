package testovichok.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import testovichok.entityes.Quiz;
import testovichok.entityes.QuizCategory;
import testovichok.exceptions.ExistQuizCategoryException;
import testovichok.service.AuthenticateService;
import testovichok.service.QuizService;
import testovichok.utils.ParametersExtractor;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@WebServlet("/menu/settings")
public class SettingsServlet extends HttpServlet {

    private QuizService quizService;

    @SneakyThrows
    @Override
    public void init(ServletConfig config) {
        super.init(config);
        quizService = (QuizService) config.getServletContext().getAttribute("QuizService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<QuizCategory> quizCategories = quizService.findAllQuizCategories();
        req.getServletContext().setAttribute("quizCategories", quizCategories);
        req.getRequestDispatcher("/settings.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String category = req.getParameter("quizCategory");
        String categoryForRemove = req.getParameter("quiz_category_name_for_remove");

        if (category != null) {
            try {
                quizService.addQuizCategory(category);
                resp.sendRedirect("/menu/settings");
                return;
            } catch (ExistQuizCategoryException e) {
                req.setAttribute("quizCategoryExist", "Такая категория уже существует✅");
            }
            req.getRequestDispatcher("/settings.jsp").forward(req, resp);
        } else if (categoryForRemove != null) {
            try {
                quizService.removeQuizCategory(categoryForRemove);
                resp.sendRedirect("/menu/settings");
                return;
            } catch (ExistQuizCategoryException e) {
                req.setAttribute("quizWithThisCategoryExist", "❕Вы не можете удалить эту категорию, так как есть квизы с этой категорией");
            }
            req.getRequestDispatcher("/settings.jsp").forward(req, resp);
        }
    }
}
