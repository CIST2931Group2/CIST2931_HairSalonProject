package com.example.cist2931_hairsalon_grouptwo.servlets;

/* HairdresserCustomerProfileServlet
 * Implements FR-H-04: View a single Customer’s profile and their appointment history.
 *
 * Responsibilities:
 * Check session and HAIRDRESSER role
 * 2. Read customerId from request
 * 3. Load customer profile
 * 4. Load customer email from User table
 * 5. Load customer's appointment history
 * 6. Forward to /hairdresser/customer-profile.jsp
 *
 * Flow:
 *
 * Author: Maria Ravid
 */

import com.example.cist2931_hairsalon_grouptwo.dao.*;
import com.example.cist2931_hairsalon_grouptwo.dto.CustomerAppointmentView;
import com.example.cist2931_hairsalon_grouptwo.model.Customer;
import com.example.cist2931_hairsalon_grouptwo.model.User;
import com.example.cist2931_hairsalon_grouptwo.service.AppointmentService;
import com.example.cist2931_hairsalon_grouptwo.service.HairdresserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/hairdresserCustomerProfile")
public class HairdresserCustomerProfileServlet extends HttpServlet {

    private HairdresserService hairdresserService;
    private AppointmentService appointmentService;
    private UserDAO userDAO;

    @Override
    public void init() {

        // Shared DAO objects
        AppointmentDAO appointmentDAO = new AppointmentDAO();
        CustomerDAO customerDAO = new CustomerDAO();
        HairdresserDAO hairdresserDAO = new HairdresserDAO();
        UserDAO userDAO = new UserDAO();
        ScheduleDAO scheduleDAO = new ScheduleDAO();
        ScheduleBlockDAO scheduleBlockDAO = new ScheduleBlockDAO();

        this.userDAO = userDAO;

        // Services
        hairdresserService = new HairdresserService(
                hairdresserDAO,
                appointmentDAO,
                customerDAO,
                userDAO
        );

        appointmentService = new AppointmentService(
                appointmentDAO,
                scheduleDAO,
                scheduleBlockDAO,
                hairdresserDAO,
                userDAO
        );
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        Integer userId = (Integer) request.getSession().getAttribute("userId");
        String role = (String) request.getSession().getAttribute("role");

        if (userId == null || role == null || !"HAIRDRESSER".equalsIgnoreCase(role)) {
            response.sendRedirect(request.getContextPath() + "/login.jsp?error=notAuthorized");
            return;
        }

        try {
            hairdresserService.assertActiveHairdresserUser(userId);

            String customerIdParam = request.getParameter("customerId");
            if (customerIdParam == null || customerIdParam.isBlank()) {
                throw new RuntimeException("Customer ID is required.");
            }

            int customerId = Integer.parseInt(customerIdParam);

            // Load customer profile
            Customer customer = hairdresserService.getCustomerProfile(customerId);
            if (customer == null) {
                throw new RuntimeException("Customer not found.");
            }

            // Load email from User table using customer.userId
            User customerUser = userDAO.findById(customer.getUserId());
            String customerEmail = (customerUser != null) ? customerUser.getEmail() : "";

            // Load appointment history
            List<CustomerAppointmentView> appointments =
                    appointmentService.getCustomerAppointmentsDashboard(customer.getCustomerId());

            // Send data to JSP
            request.setAttribute("customer", customer);
            request.setAttribute("customerEmail", customerEmail);
            request.setAttribute("appointments", appointments);

            request.getRequestDispatcher("/hairdresser/customer-view.jsp")
                    .forward(request, response);

        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid customer ID.");
            request.getRequestDispatcher("/hairdresser/customer-view.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/hairdresser/customer-view.jsp")
                    .forward(request, response);
        }
    }
}
