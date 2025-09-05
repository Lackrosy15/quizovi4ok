package quizovichok.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import quizovichok.entities.QuizCategory;
import quizovichok.exceptions.ExistQuizCategoryException;
import quizovichok.service.QuizService;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/settings")
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
        req.getRequestDispatcher("/admin/settings.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String category = req.getParameter("quizCategory");
        String categoryForRemove = req.getParameter("quiz_category_name_for_remove");

        if (category != null) {
            try {
                quizService.addQuizCategory(category);
                resp.sendRedirect("/admin/settings");
                return;
            } catch (ExistQuizCategoryException e) {
                req.setAttribute("quizCategoryExist", "Такая категория уже существует ✅");
            }
            req.getRequestDispatcher("/admin/settings.jsp").forward(req, resp);
        } else if (categoryForRemove != null) {
            try {
                quizService.removeQuizCategory(categoryForRemove);
                resp.sendRedirect("/admin/settings");
                return;
            } catch (ExistQuizCategoryException e) {
                req.setAttribute("quizWithThisCategoryExist", "❕Вы не можете удалить эту категорию, так как есть квизы с этой категорией");
            }
            req.getRequestDispatcher("/admin/settings.jsp").forward(req, resp);
        }
    }
}
