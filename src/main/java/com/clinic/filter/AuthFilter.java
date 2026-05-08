package com.clinic.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String path = req.getRequestURI().substring(req.getContextPath().length());

        // السماح بالمرور للوجن والموارد
        if (path.contains("login.xhtml") || path.contains("jakarta.faces.resource")) {
            chain.doFilter(request, response);
            return;
        }

        // التأكد من وجود "علامة الدخول" في السيشين مباشرة
        Object userRole = req.getSession().getAttribute("userRole");

        if (userRole == null) {
            res.sendRedirect(req.getContextPath() + "/login.xhtml");
        } else {
            // حماية صفحة الأطباء
            if (path.contains("doctor.xhtml") && !"ADMIN".equals(userRole.toString())) {
                res.sendRedirect(req.getContextPath() + "/index.xhtml");
            } else {
                chain.doFilter(request, response);
            }
        }
    }
}