package com.example.cist2931_hairsalon_grouptwo.servlets;

import com.example.cist2931_hairsalon_grouptwo.model.Customer;
import com.example.cist2931_hairsalon_grouptwo.service.AppointmentService;
import com.example.cist2931_hairsalon_grouptwo.service.CustomerService;
import com.example.cist2931_hairsalon_grouptwo.dao.CustomerDAO;
import com.example.cist2931_hairsalon_gptwo.dao.UserDAO;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/*
 * Handles booking a new appointment.
 * IMPORTANT DESIGN NOTE:
 * We do NOT pass HttpServletRequest into the service layer.
 * The servlet extracts form parameters, converts them into proper types,
 * and passes clean domain data to the service layer.
 */
@WebServlet("/bookAppointment")
public class BookAppointmentServlet extends HttpServlet {

    private AppointmentService appointmentService;
    private CustomerService customerService;

    @Override
    public void init() {
        // Initialize services with required DAOs
        appointmentService = new AppointmentService();
        customerService = new CustomerService(new CustomerDAO(), new UserDAO());
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        // Retrieve logged-in user from session
        Integer userId = (Integer) request.getSession().getAttribute("userId");

        // If no user is logged in, redirect to login
        if (userId == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp?error=notLoggedIn");
            return;
        }

        try {
            // Convert userId (User table) -> customerId (Customer table)
            Customer customer = customerService.getCustomerByUserId(userId);

            if (customer == null) {
                throw new RuntimeException("Customer profile not found.");
            }

            int customerId = customer.getCustomerId();

            // Extract and convert form parameters
            int hairdresserId = Integer.parseInt(request.getParameter("hairdresserId"));
            String serviceType = request.getParameter("serviceType");

            // Parse date and times from form (HTML inputs send strings)
            LocalDate date = LocalDate.parse(request.getParameter("date"));
            LocalTime startTime = LocalTime.parse(request.getParameter("startTime"));
            LocalTime endTime = LocalTime.parse(request.getParameter("endTime"));

            // Combine into LocalDateTime objects for database storage
            LocalDateTime start = LocalDateTime.of(date, startTime);
            LocalDateTime end = LocalDateTime.of(date, endTime);

            // Call service layer with clean, typed values
            appointmentService.bookAppointment(
                    customerId,
                    hairdresserId,
                    start,
                    end,
                    serviceType
            );

            // Redirect prevents form resubmission (Post/Redirect/Get pattern)
            response.sendRedirect(request.getContextPath() + "/customerDashboard");

        } catch (RuntimeException ex) {

            // Forward to shared error page
            request.setAttribute("error", ex.getMessage());
            request.getRequestDispatcher("/errorPage.jsp")
                    .forward(request, response);
        }
    }
}
