package com.example.cist2931_hairsalon_grouptwo.servlets;

import com.example.cist2931_hairsalon_grouptwo.service.AdminService;
import com.example.cist2931_hairsalon_grouptwo.service.ScheduleService;
import com.example.cist2931_hairsalon_grouptwo.dao.*;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

import java.io.IOException;

/*
 * Design Notes:
 * - Validates input before parsing
 * - Delegates business logic to AdminService
 * - Uses redirect to prevent duplicate form submissions
 */
@WebServlet("/adminDeactivateHairdresser")
public class AdminDeactivateHairdresserServlet extends HttpServlet {

    private AdminService adminService;

    @Override
    public void init() {

        ScheduleDAO scheduleDAO = new ScheduleDAO();
        ScheduleBlockDAO scheduleBlockDAO = new ScheduleBlockDAO();

        adminService = new AdminService(
                new UserDAO(),
                new HairdresserDAO(),
                scheduleDAO,
                scheduleBlockDAO,
                new ScheduleService(scheduleDAO, scheduleBlockDAO)
        );
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException {

        /*
         * Validate that hairdresserId parameter exists
         */
        String idStr = request.getParameter("hairdresserId");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");

        if (idStr == null || idStr.isBlank()) {
            response.sendRedirect(request.getContextPath()
                    + "/adminSchedule?error=missingHairdresserId");
            return;
        }

        // Convert string to integer ID
        int hairdresserId = Integer.parseInt(idStr);

        // Delegate business logic to service layer
        adminService.deactivateHairdresser(hairdresserId);

        // Redirect to refresh admin manage-hairdresser page (stay)
        response.sendRedirect(request.getContextPath()
                + "/adminManageHairdressers?success=deactivated"
                + "&firstName=" + java.net.URLEncoder.encode(firstName, "UTF-8")
                + "&lastName=" + java.net.URLEncoder.encode(lastName, "UTF-8"));
    }
}
