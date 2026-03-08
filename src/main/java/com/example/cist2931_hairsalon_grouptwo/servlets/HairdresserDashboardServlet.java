package com.example.cist2931_hairsalon_grouptwo.servlets;

import com.example.cist2931_hairsalon_grouptwo.dto.DailyAppointmentView;
import com.example.cist2931_hairsalon_grouptwo.model.Hairdresser;
import com.example.cist2931_hairsalon_grouptwo.model.Appointment;
import com.example.cist2931_hairsalon_grouptwo.service.HairdresserService;
import com.example.cist2931_hairsalon_grouptwo.dao.*;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/*
 * Hairdresser can view appointments filtered by date.
 *
 * Design Notes:
 * - Converts userId (User table) → hairdresserId (Hairdresser table)
 * - Defaults to today's date if no date is selected
 * - Sends typed domain objects to JSP for rendering
 * - Follows strict MVC separation (Servlet = Controller only)
 *
 * * Version 2 - updated method getDailyAppointments() in HairdresserService
 */
@WebServlet("/hairdresserDashboard")
public class HairdresserDashboardServlet extends HttpServlet {

    private HairdresserService hairdresserService;

    @Override
    public void init() {
        // Inject required DAOs into service layer
        hairdresserService = new HairdresserService(
                new HairdresserDAO(),
                new AppointmentDAO(),
                new CustomerDAO(),
                new UserDAO()
        );
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        // Retrieve logged-in user from session
        Integer userId = (Integer) request.getSession().getAttribute("userId");

        // Prevent unauthorized access
        if (userId == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp?error=notLoggedIn");
            return;
        }

        // Convert userId -> hairdresser profile
        Hairdresser hairdresser =
                hairdresserService.getHairdresserByUserId(userId);

        // If profile missing, redirect safely
        if (hairdresser == null) {
            response.sendRedirect(request.getContextPath()
                    + "/login.jsp?error=hairdresserProfileMissing");
            return;
        }

        /*
         * Date Filtering Logic
         * If no date parameter is supplied, default to today.
         * Expected format from HTML input: yyyy-MM-dd
         */
        LocalDate selectedDate;
        String dateStr = request.getParameter("date");

        if (dateStr == null || dateStr.isBlank()) {
            selectedDate = LocalDate.now();
        } else {
            selectedDate = LocalDate.parse(dateStr);
        }

        /* Retrieve appointments for this hairdresser on selected date
         * V2 - updated to change the type of the list
         */
        List<DailyAppointmentView> appointments =
                hairdresserService.getDailyAppointments(
                        hairdresser.getHairdresserId(),
                        selectedDate
                );

        // Send data to JSP (View layer)
        request.setAttribute("hairdresser", hairdresser);
        request.setAttribute("selectedDate", selectedDate);
        request.setAttribute("appointments", appointments);

        // Forward keeps request attributes intact
        request.getRequestDispatcher("/hairdresser/dashboard.jsp")
                .forward(request, response);
    }
}
