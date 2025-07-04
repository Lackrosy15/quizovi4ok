package testovichok.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.SneakyThrows;
import testovichok.entityes.Roles;
import testovichok.entityes.User;


@WebFilter("/*")
public class LoginFilter extends HttpFilter {
    @SneakyThrows
    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) {
        String uri = req.getRequestURI();

        if (uri.endsWith(".css") || uri.endsWith(".ico")) {
            chain.doFilter(req, res);
        } else if (!uri.equals("/registration") && !uri.equals("/login") && !uri.equals("/main")) {
            HttpSession session = req.getSession();
            if (session.getAttribute("user") == null) {
                res.sendRedirect("/login");
            } else {
                if (uri.startsWith("/admin/") && ((User) session.getAttribute("user")).getRole().equals(Roles.USER)) {
                    req.getRequestDispatcher("/403.html").forward(req, res);
                } else {
                    chain.doFilter(req, res);
                }
            }
        } else chain.doFilter(req, res);
    }
}
