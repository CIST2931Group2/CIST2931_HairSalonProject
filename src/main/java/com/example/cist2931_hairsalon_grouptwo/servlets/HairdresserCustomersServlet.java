package com.example.cist2931_hairsalon_grouptwo.servlets;

/* HairdresserCustomersServlet
 * Implements FR-H-05: View a list of assigned Customers.
 *
 * Responsibilities:
 * 1. Verify the logged-in user is a HAIRDRESSER
 * 2. Resolve session userId -> hairdresser profile
 * 3. Load assigned customers through HairdresserService
 * 4. Forward results to /hairdresser/customers.jsp
 *
 * Flow:
 *
 * Author: Maria Ravid
 */

import com.example.cist2931_hairsalon_grouptwo.dao.*;
import com.example.cist2931_hairsalon_grouptwo.dto.AssignedCustomerView;
import com.example.cist2931_hairsalon_grouptwo.model.Hairdresser;
import com.example.cist2931_hairsalon_grouptwo.service.HairdresserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/hairdresserCustomers")
public class HairdresserCustomersServlet extends HttpServlet {

    private HairdresserService hairdresserService;

    @Override
    public void init() {
        HairdresserDAO hairdresserDAO = new HairdresserDAO();
        AppointmentDAO appointmentDAO = new AppointmentDAO();
        CustomerDAO customerDAO = new CustomerDAO();
        UserDAO userDAO = new UserDAO();

        hairdresserService = new HairdresserService(
                hairdresserDAO,
                appointmentDAO,
                customerDAO,
                userDAO
        );
    }

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        Integer userId = (Integer) request.getSession().getAttribute("userId");
        String role = (String) request.getSession().getAttribute("role");

        // Block unauthorized access
        if (userId == null || role == null || !"HAIRDRESSER".equalsIgnoreCase(role)) {
            response.sendRedirect(request.getContextPath() + "/login.jsp?error=notAuthorized");
            return;
        }

        try {
            // Verify session user belongs to an active hairdresser account
            hairdresserService.assertActiveHairdresserUser(userId);

            // Convert session userId -> hairdresser profile
            Hairdresser hairdresser = hairdresserService.getHairdresserByUserId(userId);

            if (hairdresser == null) {
                throw new RuntimeException("Hairdresser profile not found.");
            }

            // Load assigned customers
            List<AssignedCustomerView> customers =
                    hairdresserService.getAssignedCustomers(hairdresser.getHairdresserId());

            // Send data to JSP
            request.setAttribute("hairdresser", hairdresser);
            request.setAttribute("customers", customers);

            request.getRequestDispatcher("/hairdresser/customers.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/hairdresser/customers.jsp")
                    .forward(request, response);
        }
    }

}
