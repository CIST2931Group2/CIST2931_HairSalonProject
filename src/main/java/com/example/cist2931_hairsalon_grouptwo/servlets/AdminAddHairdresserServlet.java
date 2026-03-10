package com.example.cist2931_hairsalon_grouptwo.servlets;

/* AdminAddHairdresserServlet
 * Implements FR-A-03: Add Hair-Dressers
 *
 * Responsibilities:
 * 1. Verify the logged-in user is an ADMIN
 * 2. Read add-hairdresser form fields
 * 3. Build User + Hairdresser objects
 * 4. Call AdminService.addHairdresserAccount(...)
 * 5. Redirect back to AdminManageHairdressersServlet
 *
 * Flow:
 *
 * Author: Maria Ravid
 */

import com.example.cist2931_hairsalon_grouptwo.dao.*;
import com.example.cist2931_hairsalon_grouptwo.model.Hairdresser;
import com.example.cist2931_hairsalon_grouptwo.model.User;
import com.example.cist2931_hairsalon_grouptwo.service.AdminService;
import com.example.cist2931_hairsalon_grouptwo.service.ScheduleService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/adminAddHairdresser")
public class AdminAddHairdresserServlet extends HttpServlet {

    private AdminService adminService;

    @Override
    public void init() {
        UserDAO userDAO = new UserDAO();
        HairdresserDAO hairdresserDAO = new HairdresserDAO();
        ScheduleDAO scheduleDAO = new ScheduleDAO();
        ScheduleBlockDAO scheduleBlockDAO = new ScheduleBlockDAO();

        ScheduleService scheduleService = new ScheduleService(
                scheduleDAO,
                scheduleBlockDAO
        );

        adminService = new AdminService(
                userDAO,
                hairdresserDAO,
                scheduleDAO,
                scheduleBlockDAO,
                scheduleService
        );
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        Integer userId = (Integer) request.getSession().getAttribute("userId");
        String role = (String) request.getSession().getAttribute("role");

        // Block unauthorized access
        if (userId == null || role == null || !"ADMIN".equalsIgnoreCase(role)) {
            response.sendRedirect(request.getContextPath() + "/login.jsp?error=notAuthorized");
            return;
        }

        try {
            String firstName   = trim(request.getParameter("firstName"));
            String lastName    = trim(request.getParameter("lastName"));
            String email       = trim(request.getParameter("email"));
            String phone       = trim(request.getParameter("phone"));
            String password    = request.getParameter("password");
            String specialties = trim(request.getParameter("specialties"));
            String bio         = trim(request.getParameter("bio"));

            if (firstName.isEmpty() || lastName.isEmpty()
                    || email.isEmpty() || password == null || password.isBlank()) {
                throw new RuntimeException("First name, last name, email, and password are required.");
            }

            // Build User account object
            User userAccount = new User();
            userAccount.setEmail(email);
            userAccount.setPasswordHash(password);   // plain for now
            userAccount.setCreatedAt(LocalDateTime.now());

            // Build Hairdresser profile object
            Hairdresser hairdresserProfile = new Hairdresser();
            hairdresserProfile.setFirstName(firstName);
            hairdresserProfile.setLastName(lastName);
            hairdresserProfile.setPhone(phone);
            hairdresserProfile.setSpecialties(specialties);
            hairdresserProfile.setBio(bio);

            adminService.addHairdresserAccount(userAccount, hairdresserProfile);

            response.sendRedirect(request.getContextPath() + "/adminManageHairdressers");

        } catch (Exception e) {
            // return to manage page with error in query string
            response.sendRedirect(
                    request.getContextPath() +
                            "/adminManageHairdressers?error=" +
                            java.net.URLEncoder.encode(e.getMessage(), "UTF-8")
            );
        }
    }

    private String trim(String value) {
        return value == null ? "" : value.trim();
    }

}
