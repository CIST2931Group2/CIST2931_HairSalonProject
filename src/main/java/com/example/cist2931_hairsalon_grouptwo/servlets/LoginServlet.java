package com.example.cist2931_hairsalon_grouptwo.servlets;

import com.example.cist2931_hairsalon_grouptwo.model.User;
import com.example.cist2931_hairsalon_grouptwo.service.AuthService;
import com.example.cist2931_hairsalon_grouptwo.dao.UserDAO;
import com.example.cist2931_hairsalon_grouptwo.dao.CustomerDAO;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private AuthService authService;

    @Override
    public void init() {
        // Create DAO objects
        UserDAO userDAO = new UserDAO();
        CustomerDAO customerDAO = new CustomerDAO();

        // Inject DAOs into AuthService
        authService = new AuthService(userDAO, customerDAO);
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
            session.setAttribute("userId", user.getUserId());
            session.setAttribute("role", user.getRole());
            session.setAttribute("email", user.getEmail());

            // Redirect based on role
            switch (user.getRole().toUpperCase()) {
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
                    response.sendRedirect(request.getContextPath() + "/login.jsp?error=invalidRole");
            }

        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/login.jsp?error=authFailed");
        }
    }
}