package com.example.cist2931_hairsalon_grouptwo.servlets;

/* AdminManageHairdressersServlet
 * Implements the GET controller for the Admin "Manage Hairdressers" page.
 *
 * Responsibilities:
 * 1. Verify the logged-in user is an ADMIN
 * 2. Load all hairdressers through AdminService
 * 3. Forward the list to /admin/manage-hairdressers.jsp
 *
 * Flow:
 *
 * Author: Maria Ravid
 */

import com.example.cist2931_hairsalon_grouptwo.dao.*;
import com.example.cist2931_hairsalon_grouptwo.model.Hairdresser;
import com.example.cist2931_hairsalon_grouptwo.service.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/adminManageHairdressers")
public class AdminManageHairdressersServlet extends HttpServlet {

    private AdminService adminService;

    @Override
    public void init() {
        // Create DAO objects once when the servlet loads
        HairdresserDAO hairdresserDAO = new HairdresserDAO();
        UserDAO userDAO = new UserDAO();
        ScheduleDAO scheduleDAO = new ScheduleDAO();
        ScheduleBlockDAO scheduleBlockDAO = new ScheduleBlockDAO();

        // ScheduleService required by AdminService constructor
        ScheduleService scheduleService = new ScheduleService(
                scheduleDAO,
                scheduleBlockDAO
        );

        // Inject DAOs into AdminService
        adminService = new AdminService(
                userDAO,
                hairdresserDAO,
                scheduleDAO,
                scheduleBlockDAO,
                scheduleService
        );
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        Integer userId = (Integer) request.getSession().getAttribute("userId");
        String role = (String) request.getSession().getAttribute("role");

        // Block unauthorized access
        if (userId == null || role == null || !"ADMIN".equalsIgnoreCase(role)) {
            response.sendRedirect(request.getContextPath() + "/login.jsp?error=notAuthorized");
            return;
        }

        try {
            List<Hairdresser> hairdressers = adminService.getAllHairdressers();

            request.setAttribute("hairdressers", hairdressers);

            request.getRequestDispatcher("/admin/manage-hairdressers.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/admin/manage-hairdressers.jsp")
                    .forward(request, response);
        }
    }

}
