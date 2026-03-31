package com.example.cist2931_hairsalon_grouptwo.servlets;

import com.example.cist2931_hairsalon_grouptwo.dao.*;
import com.example.cist2931_hairsalon_grouptwo.dto.CustomerAppointmentView;
import com.example.cist2931_hairsalon_grouptwo.model.Customer;
import com.example.cist2931_hairsalon_grouptwo.service.AppointmentService;
import com.example.cist2931_hairsalon_grouptwo.service.CustomerService;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

import java.io.IOException;
import java.util.List;

@WebServlet("/customerDashboard")
public class CustomerDashboardServlet extends HttpServlet {

    private AppointmentService appointmentService;
    private CustomerService customerService;

    @Override
    public void init() {
        // ----- DAOs for AppointmentService -----
        AppointmentDAO appointmentDAO = new AppointmentDAO();
        ScheduleDAO scheduleDAO = new ScheduleDAO();
        ScheduleBlockDAO scheduleBlockDAO = new ScheduleBlockDAO();
        HairdresserDAO hairdresserDAO = new HairdresserDAO();
        UserDAO userDAO = new UserDAO();

        appointmentService = new AppointmentService(
                appointmentDAO,
                scheduleDAO,
                scheduleBlockDAO,
                hairdresserDAO,
                userDAO
        );

        // ----- DAOs for CustomerService -----
        CustomerDAO customerDAO = new CustomerDAO();
        customerService = new CustomerService(customerDAO, userDAO);
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        // ----- Retrieve logged-in user -----
        Integer userId = (Integer) request.getSession().getAttribute("userId");

        if (userId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // ----- Optional: enforce active customer -----
        try {
            customerService.assertActiveCustomerUser(userId);
        } catch (CustomerService.ServiceException e) {
            request.setAttribute("loginError", e.getMessage());
            request.getRequestDispatcher("/LoginError.jsp")
                    .forward(request, response);
            return;
        }

        // ----- Convert userId → Customer -----
        Customer customer = customerService.getCustomerByUserId(userId);

        request.setAttribute("customer", customer);

        // ----- Retrieve customer appointments -----
        // V2 - updated to include DTO CustomerAppointmentView
        List<CustomerAppointmentView> appointments =
                appointmentService.getCustomerAppointmentsDashboard(customer.getCustomerId());

        request.setAttribute("appointments", appointments);

        // ----- Forward to JSP -----
        request.getRequestDispatcher("/customer/dashboard.jsp")
                .forward(request, response);
    }
}