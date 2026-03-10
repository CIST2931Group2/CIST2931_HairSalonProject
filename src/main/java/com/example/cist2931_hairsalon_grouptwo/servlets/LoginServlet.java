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
        UserDAO userDAO = new UserDAO();
        CustomerDAO customerDAO = new CustomerDAO();
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
                    response.sendRedirect(request.getContextPath() + "/adminManageHairdressers");
                    break;
                default:
                    // If role is invalid, go to error page
                    request.setAttribute("errorMessage", "Invalid role assigned. Please contact admin.");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/LoginError.jsp");
                    dispatcher.forward(request, response);
                    break;
            }

        } catch (Exception e) {
            // Forward to LoginError.jsp on failed login
            request.setAttribute("errorMessage", "Login failed. Please check your email and password.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/LoginError.jsp");
            dispatcher.forward(request, response);
        }
    }
}