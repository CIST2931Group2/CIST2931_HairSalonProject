package com.example.cist2931_hairsalon_grouptwo.servlets;

/* RegisterCustomerServlet
 * Implements FR-C-01: Customer Account Creation.
 *
 * Responsibilities:
 * 1. Display the registration page (GET request)
 * 2. Process the registration form (POST request)
 * 3. Call AuthService to create User + Customer records
 * 4. Automatically log the customer in by creating a session
 * 5. Redirect to the customer dashboard after successful registration
 *
 * Flow:
 * index.jsp --> Register button/link --> /register.jsp --> POST to /registerCustomer --> auto login --> /customer/dashboard.jsp (via CustomerDashboardServlet)
 *
 * Author: Maria Ravid
 */

import com.example.cist2931_hairsalon_grouptwo.dao.CustomerDAO;
import com.example.cist2931_hairsalon_grouptwo.dao.UserDAO;
import com.example.cist2931_hairsalon_grouptwo.service.AuthService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/registerCustomer")
public class RegisterCustomerServlet extends HttpServlet {

    private AuthService authService;

    @Override
    public void init() {
        UserDAO userDAO = new UserDAO();
        CustomerDAO customerDAO = new CustomerDAO();
        authService = new AuthService(userDAO, customerDAO);
    }

    /*
     * doGet()
     * Handles GET requests. forwards the user to the registration page.
     */
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/register.jsp").forward(request, response);
    }

    /*
     * doPost()
     * Handles form submission from register.jsp.
     * Reads form parameters, validates them, calls the service layer,
     * creates a session for the new user, and redirects to customer dashboard.
     */
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        // Read form parameters from register.jsp
        String firstName = trim(request.getParameter("firstName"));
        String lastName  = trim(request.getParameter("lastName"));
        String phone     = trim(request.getParameter("phone"));
        String email     = trim(request.getParameter("email"));
        String password  = request.getParameter("password");

        // Basic validation for required fields
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty()
                || password == null || password.isBlank()) {

            request.setAttribute("error",
                    "First name, last name, email, and password are required.");

            // Return user back to registration page with error message
            request.getRequestDispatcher("/register.jsp")
                    .forward(request, response);
            return;
        }

        try {

            /*
             * Call AuthService to create: a User record and a Customer profile linked to that user
             * The service returns the generated userId.
             */
            int userId = authService.registerCustomer(
                    email,
                    password,   // plain password for now
                    firstName,
                    lastName,
                    phone
            );

            // Create session so the user is automatically logged in after registration.
            HttpSession session = request.getSession(true);
            session.setAttribute("userId", userId);
            session.setAttribute("role", "CUSTOMER");
            session.setAttribute("email", email);


             // Redirect to the Customer Dashboard.
            response.sendRedirect(
                    request.getContextPath() + "/customerDashboard"
            );

        } catch (Exception e) {

            // If an error occurs such as duplicate email, show message
            request.setAttribute("error", e.getMessage());

            request.getRequestDispatcher("/register.jsp")
                    .forward(request, response);
        }
    }

    /*
     * Helper method to safely trim request parameters.
     * Prevents NullPointerException when parameter is missing.
     */
    private String trim(String value) {
        return value == null ? "" : value.trim();
    }
}
