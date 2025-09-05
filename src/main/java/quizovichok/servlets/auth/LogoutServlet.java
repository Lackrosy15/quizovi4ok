package quizovichok.servlets.auth;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import quizovichok.service.AuthenticateService;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    private AuthenticateService authenticateService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        authenticateService = (AuthenticateService) config.getServletContext().getAttribute("AuthenticateService");
    }


    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        authenticateService.logoutUser(req.getSession());
        resp.sendRedirect("/login");
    }
}
