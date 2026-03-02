package com.example.cist2931_hairsalon_grouptwo.servlets;

import com.example.cist2931_hairsalon_grouptwo.model.Customer;
import com.example.cist2931_hairsalon_grouptwo.model.Appointment;
import com.example.cist2931_hairsalon_grouptwo.service.AppointmentService;
import com.example.cist2931_hairsalon_grouptwo.service.CustomerService;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

import java.io.IOException;
import java.util.List;

/*
 * Responsibilities:
 * - Retrieve logged-in user from session
 * - Convert userId (User table) → customerId (Customer table)
 * - Retrieve customer appointments
 * - Forward results to JSP for rendering
 *
 * MVC Design:
 * - Servlet = Controller (handles HTTP request/response)
 * - Service = Business Logic layer
 * - JSP = View layer
 *
 * IMPORTANT:
 * Login is based on the User entity.
 * Appointments are linked to Customer.
 * Therefore, we must convert userId → customerId before retrieving appointments.
 */
@WebServlet("/customerDashboard")
public class CustomerDashboardServlet extends HttpServlet {

    private AppointmentService appointmentService;
    private CustomerService customerService;

    @Override
    public void init() {
        // Initialize service layer dependencies
        appointmentService = new AppointmentService();
        customerService = new CustomerService();
    }

    /*
     * Handles loading the customer dashboard.
     * Retrieves appointment data and forwards to JSP.
     */
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        // Retrieve logged-in userId from session
        Integer userId = (Integer) request.getSession().getAttribute("userId");

        /*
         * Convert userId (authentication layer)
         * to Customer domain object (business layer).
         */
        Customer customer = customerService.getCustomerByUserId(userId);

        /*
         * Retrieve all appointments associated with this customer.
         * We pass customerId, NOT userId.
         */
        List<Appointment> appointments =
                appointmentService.getCustomerAppointments(
                        customer.getCustomerId()
                );

        // Make appointments available to JSP
        request.setAttribute("appointments", appointments);

        /*
         * Forward to dashboard view.
         * Forward keeps request attributes intact.
         */
        request.getRequestDispatcher("/customer/dashboard.jsp")
                .forward(request, response);
    }
}
