package com.example.cist2931_hairsalon_grouptwo.servlets;

import com.example.cist2931_hairsalon_grouptwo.model.*;
import com.example.cist2931_hairsalon_grouptwo.service.*;
import com.example.cist2931_hairsalon_grouptwo.dao.*;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/*
 * Design Pattern:
 * Uses "action" parameter routing in doPost() to determine which
 * business operation to perform.
 *
 * MVC Notes:
 * - Servlet extracts parameters
 * - Service handles business logic
 * - JSP renders results
 */
@WebServlet("/adminSchedule")
public class AdminScheduleServlet extends HttpServlet {

    private ScheduleService scheduleService;
    private HairdresserService hairdresserService;

    @Override
    public void init() {

        // Initialize shared DAOs
        ScheduleDAO scheduleDAO = new ScheduleDAO();
        ScheduleBlockDAO scheduleBlockDAO = new ScheduleBlockDAO();

        // Inject into services
        scheduleService = new ScheduleService(scheduleDAO, scheduleBlockDAO);

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

        
        // Always load hairdresser list for dropdown selection.
        List<Hairdresser> hairdressers = hairdresserService.listAllHairdressers();

        request.setAttribute("hairdressers", hairdressers);

        /*
         * If hairdresserId + weekStartDate are provided,
         * load that specific weekly schedule.
         */
        String hairdresserIdStr = request.getParameter("hairdresserId");
        String weekStartStr = request.getParameter("weekStartDate");

        if (hairdresserIdStr != null && weekStartStr != null &&
                !hairdresserIdStr.isBlank() && !weekStartStr.isBlank()) {

            int hairdresserId = Integer.parseInt(hairdresserIdStr);
            LocalDate weekStartDate = LocalDate.parse(weekStartStr);

            Schedule schedule =
                    scheduleService.getScheduleByWeek(hairdresserId, weekStartDate);

            request.setAttribute("selectedHairdresserId", hairdresserId);
            request.setAttribute("selectedWeekStartDate", weekStartDate);

            if (schedule != null) {

                request.setAttribute("schedule", schedule);

                // Load all blocks (time slots) for that schedule
                List<ScheduleBlock> blocks =
                        scheduleService.getBlocks(schedule.getScheduleId());

                request.setAttribute("blocks", blocks);
            }
        }

        request.getRequestDispatcher("/admin/weekly-schedule.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        /*
         * Action Routing:
         * Determines which admin operation to execute.
         */
        String action = request.getParameter("action");

        try {

            /*
             * Create weekly schedule
             */
            if ("createSchedule".equalsIgnoreCase(action)) {

                int hairdresserId =
                        Integer.parseInt(request.getParameter("hairdresserId"));
                LocalDate weekStartDate =
                        LocalDate.parse(request.getParameter("weekStartDate"));

                scheduleService.createWeeklySchedule(hairdresserId, weekStartDate);

                // Redirect prevents duplicate submissions
                response.sendRedirect(request.getContextPath()
                        + "/adminSchedule?hairdresserId=" + hairdresserId
                        + "&weekStartDate=" + weekStartDate);
                return;
            }

            /*
             * Add time block to existing schedule
             */
            if ("addBlock".equalsIgnoreCase(action)) {

                int scheduleId =
                        Integer.parseInt(request.getParameter("scheduleId"));

                String dayOfWeek = request.getParameter("dayOfWeek");
                LocalTime start =
                        LocalTime.parse(request.getParameter("startTime"));
                LocalTime end =
                        LocalTime.parse(request.getParameter("endTime"));

                scheduleService.addScheduleBlock(scheduleId, dayOfWeek, start, end);

                // Stay on same schedule view
                String hairdresserId = request.getParameter("hairdresserId");
                String weekStartDate = request.getParameter("weekStartDate");

                response.sendRedirect(request.getContextPath()
                        + "/adminSchedule?hairdresserId=" + hairdresserId
                        + "&weekStartDate=" + weekStartDate);
                return;
            }

            // Unknown action to reload page safely
            response.sendRedirect(request.getContextPath() + "/adminSchedule");

        } catch (RuntimeException ex) {

            // Centralized error handling
            request.setAttribute("error", ex.getMessage());
            request.getRequestDispatcher("/errorPage.jsp")
                    .forward(request, response);
        }
    }
}
