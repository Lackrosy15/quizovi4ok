package testovichok.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebFilter("/*")
public class HttpRequestFilter extends HttpFilter {

    private final List<String> httpMethods = new ArrayList<>(List.of("DELETE", "PUT", "PATCH"));
    @SneakyThrows
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) {
        String method = req.getParameter("method");

        if (httpMethods.contains(method)) {
            HttpServletRequestWrapper httpServletRequestWrapper = new HttpServletRequestWrapper(req) {
                @Override
                public String getMethod() {
                    return method;
                }
            };
            chain.doFilter(httpServletRequestWrapper, res);
        } else {
            chain.doFilter(req, res);
        }
    }
}
