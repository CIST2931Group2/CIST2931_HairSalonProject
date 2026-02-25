package com.example.cist2931_hairsalon_grouptwo.servlets;

import com.example.cist2931_hairsalon_grouptwo.dao.*;
import com.example.cist2931_hairsalon_grouptwo.service.AppointmentService;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

import java.io.IOException;

@WebServlet("/customerDashboard")
public class CustomerDashboardServlet extends HttpServlet {

    private AppointmentService appointmentService;

    @Override
    public void init() {
        appointmentService = new AppointmentService(
                new AppointmentDAO(),
                new ScheduleDAO(),
                new ScheduleBlockDAO(),
                new HairdresserDAO(),
                new UserDAO()
        );
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        Integer userId = (Integer) request.getSession().getAttribute("userId");

        request.setAttribute("appointments",
                appointmentService.getCustomerAppointments(userId));

        request.getRequestDispatcher("/customer/dashboard.jsp")
                .forward(request, response);
    }
}