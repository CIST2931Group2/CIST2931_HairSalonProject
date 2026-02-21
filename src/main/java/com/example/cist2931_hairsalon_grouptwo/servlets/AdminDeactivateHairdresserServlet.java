package com.example.cist2931_hairsalon_grouptwo.servlets;

import com.example.cist2931_hairsalon_grouptwo.service.AdminService;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

import java.io.IOException;

@WebServlet("/adminDeactivateHairdresser")
public class AdminDeactivateHairdresserServlet extends HttpServlet {

    private AdminService adminService;

    @Override
    public void init() {
        adminService = new AdminService();
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException {

        Integer hairdresserId =
                Integer.parseInt(request.getParameter("hairdresserId"));

        adminService.deactivateHairdresser(hairdresserId);

        response.sendRedirect(request.getContextPath() + "/adminSchedule");
    }
}