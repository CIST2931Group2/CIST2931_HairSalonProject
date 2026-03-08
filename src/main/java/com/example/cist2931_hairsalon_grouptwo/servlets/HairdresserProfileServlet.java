package com.example.cist2931_hairsalon_grouptwo.servlets;

/* HairdresserProfileServlet
 * Implements FR-H-02: View / Edit hairdresser profile.
 *
 * Responsibilities:
 * 1. Load the logged-in hairdresser profile (GET)
 * 2. Process profile updates (POST)
 * 3. Forward back to hairdresser-profile.jsp with success or error message
 *
 * Flow:
 *
 * Author: Maria Ravid
 */

import com.example.cist2931_hairsalon_grouptwo.dao.AppointmentDAO;
import com.example.cist2931_hairsalon_grouptwo.dao.CustomerDAO;
import com.example.cist2931_hairsalon_grouptwo.dao.HairdresserDAO;
import com.example.cist2931_hairsalon_grouptwo.dao.UserDAO;
import com.example.cist2931_hairsalon_grouptwo.model.Hairdresser;
import com.example.cist2931_hairsalon_grouptwo.service.HairdresserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/hairdresserProfile")
public class HairdresserProfileServlet extends HttpServlet {

    private HairdresserService hairdresserService;

    @Override
    public void init() {

        HairdresserDAO hairdresserDAO = new HairdresserDAO();
        AppointmentDAO appointmentDAO = new AppointmentDAO();
        CustomerDAO customerDAO = new CustomerDAO();
        UserDAO userDAO = new UserDAO();

        hairdresserService = new HairdresserService(
                hairdresserDAO,
                appointmentDAO,
                customerDAO,
                userDAO
        );
    }

    /*
     * Handles GET requests.
     * Loads the profile of the currently logged-in hairdresser.
     */
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

            Hairdresser hairdresser = hairdresserService.getHairdresserByUserId(userId);

            if (hairdresser == null) {
                throw new RuntimeException("Hairdresser profile not found.");
            }

            request.setAttribute("hairdresser", hairdresser);

            request.getRequestDispatcher("/hairdresser/hairdresser-profile.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/hairdresser/hairdresser-profile.jsp")
                    .forward(request, response);
        }
    }

    /*
     * Handles POST requests.
     * Updates editable profile fields for the logged-in hairdresser.
     */
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        Integer userId = (Integer) request.getSession().getAttribute("userId");
        String role = (String) request.getSession().getAttribute("role");

        if (userId == null || role == null || !"HAIRDRESSER".equalsIgnoreCase(role)) {
            response.sendRedirect(request.getContextPath() + "/login.jsp?error=notAuthorized");
            return;
        }

        try {
            hairdresserService.assertActiveHairdresserUser(userId);

            // Load the existing hairdresser profile
            Hairdresser hairdresser = hairdresserService.getHairdresserByUserId(userId);

            if (hairdresser == null) {
                throw new RuntimeException("Hairdresser profile not found.");
            }

            // Update only editable fields from the form
            hairdresser.setFirstName(trim(request.getParameter("firstName")));
            hairdresser.setLastName(trim(request.getParameter("lastName")));
            hairdresser.setPhone(trim(request.getParameter("phone")));
            hairdresser.setSpecialties(trim(request.getParameter("specialties")));
            hairdresser.setBio(trim(request.getParameter("bio")));

            // Persist updates through service layer
            hairdresserService.updateHairdresserProfile(hairdresser);

            request.setAttribute("hairdresser", hairdresser);
            request.setAttribute("success", "Profile updated successfully.");

            request.getRequestDispatcher("/hairdresser/hairdresser-profile.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            try {
                Hairdresser hairdresser = hairdresserService.getHairdresserByUserId(userId);
                request.setAttribute("hairdresser", hairdresser);
            } catch (Exception ignored) {
                // If reloading fails, just show the error
            }

            request.setAttribute("error", e.getMessage());

            request.getRequestDispatcher("/hairdresser/hairdresser-profile.jsp")
                    .forward(request, response);
        }
    }

    /*
     * Helper method to trim request parameters.
     */
    private String trim(String value) {
        return value == null ? "" : value.trim();
    }

}
