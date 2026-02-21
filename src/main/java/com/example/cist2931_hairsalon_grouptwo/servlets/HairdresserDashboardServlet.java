package com.example.cist2931_hairsalon_grouptwo.servlets;

import com.example.cist2931_hairsalon_grouptwo.service.HairdresserService;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

import java.io.IOException;

@WebServlet("/hairdresserDashboard")
public class HairdresserDashboardServlet extends HttpServlet {

    private HairdresserService hairdresserService;

    @Override
    public void init() {
        hairdresserService = new HairdresserService();
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        Integer userId = (Integer) request.getSession().getAttribute("userId");

        request.setAttribute("schedule",
                hairdresserService.getScheduleForHairdresser(userId));

        request.getRequestDispatcher("/hairdresser/dashboard.jsp")
                .forward(request, response);
    }
}