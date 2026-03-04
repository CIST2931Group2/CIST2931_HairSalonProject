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

            // ----- Parse form parameters -----
            int hairdresserId = Integer.parseInt(request.getParameter("hairdresserId"));
            String serviceType = request.getParameter("serviceType");

            LocalDate date = LocalDate.parse(request.getParameter("date"));
            LocalTime startTime = LocalTime.parse(request.getParameter("startTime"));
            LocalTime endTime = LocalTime.parse(request.getParameter("endTime"));

            LocalDateTime start = LocalDateTime.of(date, startTime);
            LocalDateTime end = LocalDateTime.of(date, endTime);

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
}