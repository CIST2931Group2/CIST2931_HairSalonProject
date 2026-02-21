package com.example.cist2931_hairsalon_grouptwo.servlets;

import com.example.cist2931_hairsalon_grouptwo.model.User;
import com.example.cist2931_hairsalon_grouptwo.service.AuthService;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private AuthService authService;

    @Override
    public void init() {
        authService = new AuthService();
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            User user = authService.login(email, password);

            HttpSession session = request.getSession(true);
            session.setAttribute("userId", user.getId());
            session.setAttribute("role", user.getRole());
            session.setAttribute("email", user.getEmail());

            switch (user.getRole()) {

                case "CUSTOMER":
                    response.sendRedirect(request.getContextPath() + "/customerDashboard");
                    break;

                case "HAIRDRESSER":
                    response.sendRedirect(request.getContextPath() + "/hairdresserDashboard");
                    break;

                case "ADMIN":
                    response.sendRedirect(request.getContextPath() + "/adminSchedule");
                    break;

                default:
                    response.sendRedirect("login.jsp?error=invalidRole");
            }

        } catch (Exception e) {
            response.sendRedirect("login.jsp?error=authFailed");
        }
    }
}