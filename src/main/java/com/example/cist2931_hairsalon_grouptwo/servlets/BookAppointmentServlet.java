package com.example.cist2931_hairsalon_grouptwo.servlets;

import com.example.cist2931_hairsalon_grouptwo.model.Customer;
import com.example.cist2931_hairsalon_grouptwo.service.AppointmentService;
import com.example.cist2931_hairsalon_grouptwo.service.CustomerService;
import com.example.cist2931_hairsalon_grouptwo.dao.*;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/*
 * Handles booking a new appointment.
 *
 * Version 2 - UPDATED DESIGN:
 * The customer first searches availability and then selects one returned slot.
 * So this servlet now expects:
 *   - hairdresserId
 *   - serviceType
 *   - slotStart   (selected available slot as LocalDateTime string)
 *
 * The servlet calculates the appointment end time based on service duration.
 *
 * The servlet extracts parameters and passes clean domain objects to the service layer.
 */
@WebServlet("/bookAppointment")
public class BookAppointmentServlet extends HttpServlet {

    private AppointmentService appointmentService;
    private CustomerService customerService;

    @Override
    public void init() {
        // ----- Create DAO instances -----
        AppointmentDAO appointmentDAO = new AppointmentDAO();
        ScheduleDAO scheduleDAO = new ScheduleDAO();
        ScheduleBlockDAO scheduleBlockDAO = new ScheduleBlockDAO();
        HairdresserDAO hairdresserDAO = new HairdresserDAO();
        UserDAO userDAO = new UserDAO();
        CustomerDAO customerDAO = new CustomerDAO();

        // ----- Inject DAOs into service layers -----
        appointmentService = new AppointmentService(
                appointmentDAO,
                scheduleDAO,
                scheduleBlockDAO,
                hairdresserDAO,
                userDAO
        );
        customerService = new CustomerService(customerDAO, userDAO);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        // ----- Retrieve logged-in user -----
        Integer userId = (Integer) request.getSession().getAttribute("userId");

        if (userId == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp?error=notLoggedIn");
            return;
        }

        try {
            // ----- Convert userId -> Customer -----
            Customer customer = customerService.getCustomerByUserId(userId);

            if (customer == null) {
                throw new RuntimeException("Customer profile not found.");
            }

            int customerId = customer.getCustomerId();

            // ----- Parse form parameters ------ REVISED for V2 (slotStartParam added)
            int hairdresserId = Integer.parseInt(request.getParameter("hairdresserId"));
            String serviceType = request.getParameter("serviceType");
            String slotStartParam = request.getParameter("slotStart");

            // logic added for V2
            if (serviceType == null || serviceType.isBlank()) {
                throw new RuntimeException("Service type is required.");
            }

            if (slotStartParam == null || slotStartParam.isBlank()) {
                throw new RuntimeException("Please select an available slot.");
            }

            // Parse selected slot start time
            LocalDateTime start = LocalDateTime.parse(slotStartParam);

            // Calculate end time based on service duration
            int durationMinutes = getSlotMinutes(serviceType);
            LocalDateTime end = start.plusMinutes(durationMinutes);

            // ----- Book appointment via service -----
            appointmentService.bookAppointment(
                    customerId,
                    hairdresserId,
                    start,
                    end,
                    serviceType
            );

            // ----- Redirect to dashboard (Post/Redirect/Get) -----
            response.sendRedirect(request.getContextPath() + "/customerDashboard");

        } catch (RuntimeException ex) {
            // ----- Forward to error page -----
            request.setAttribute("error", ex.getMessage());
            request.getRequestDispatcher("/errorPage.jsp")
                    .forward(request, response);
        }
    }

    /* Helper method added at V2
     * Converts service type to appointment duration in minutes.
     */
    private int getSlotMinutes(String serviceType) {
        switch (serviceType) {
            case "Haircut":
                return 30;
            case "Hair Styling":
                return 60;
            case "Hair Dying":
                return 60;
            case "Spa Service":
                return 60;
            case "Nail Coloring":
                return 30;
            default:
                throw new RuntimeException("Unknown service type.");
        }
    }
}