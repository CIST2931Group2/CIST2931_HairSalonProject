package com.example.cist2931_hairsalon_grouptwo.servlets;

/* SearchAvailabilityServlet
 * Implements FR-C-04: Search available appointments.
 *
 * Responsibilities:
 * 1. Load booking/search page with hairdresser list (GET)
 * 2. Process availability search (POST)
 * 3. Call AppointmentService.getAvailableSlots(...)
 * 4. Return available slots to the same JSP
 *
 * Flow:
 * customer/dashboard.jsp --> Click "Book Appointment" --> /searchAvailability (GET) --> SearchAvailabilityServlet.doGet()  -->
 * book-appointments.jsp --> Customer fills form --> Click "Search Availability" --> /searchAvailability (POST) -->
 * SearchAvailabilityServlet.doPost() --> AppointmentService.getAvailableSlots(...) --> book-appointments.jsp reloaded with results
 *
 * Author: Maria Ravid
 */

import com.example.cist2931_hairsalon_grouptwo.dao.*;
import com.example.cist2931_hairsalon_grouptwo.model.Hairdresser;
import com.example.cist2931_hairsalon_grouptwo.service.AppointmentService;
import com.example.cist2931_hairsalon_grouptwo.service.HairdresserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet("/searchAvailability")
public class SearchAvailabilityServlet extends HttpServlet {

    private HairdresserService hairdresserService;
    private AppointmentService appointmentService;

    @Override
    public void init() {
        // Create DAO objects once when the servlet loads
        AppointmentDAO appointmentDAO = new AppointmentDAO();
        HairdresserDAO hairdresserDAO = new HairdresserDAO();
        CustomerDAO customerDAO = new CustomerDAO();
        UserDAO userDAO = new UserDAO();
        ScheduleDAO scheduleDAO = new ScheduleDAO();
        ScheduleBlockDAO scheduleBlockDAO = new ScheduleBlockDAO();

        // Inject DAOs into service layer objects
        hairdresserService = new HairdresserService(
                hairdresserDAO,
                appointmentDAO,
                customerDAO,
                userDAO
        );

        appointmentService = new AppointmentService(
                appointmentDAO,
                scheduleDAO,
                scheduleBlockDAO,
                hairdresserDAO,
                userDAO
        );
    }

    /*
     * doGet()
     * Loads the booking/search page and populates the hairdresser dropdown.
     */
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        try {
            List<Hairdresser> hairdressers = hairdresserService.listAllHairdressers();
            request.setAttribute("hairdressers", hairdressers);

            request.getRequestDispatcher("/customer/book-appointments.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/customer/book-appointments.jsp")
                    .forward(request, response);
        }
    }

    /*
     * doPost()
     * Handles the availability search form submission.
     */
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        try {
            // Load dropdown data again so page can be re-rendered after search
            List<Hairdresser> hairdressers = hairdresserService.listAllHairdressers();
            request.setAttribute("hairdressers", hairdressers);

            // Read form inputs
            String hairdresserIdParam = request.getParameter("hairdresserId");
            String dateParam = request.getParameter("date");
            String serviceType = request.getParameter("serviceType");

            if (hairdresserIdParam == null || hairdresserIdParam.isBlank()
                    || dateParam == null || dateParam.isBlank()
                    || serviceType == null || serviceType.isBlank()) {

                request.setAttribute("error",
                        "Hairdresser, date, and service type are required.");

                request.getRequestDispatcher("/customer/book-appointments.jsp")
                        .forward(request, response);
                return;
            }

            int hairdresserId = Integer.parseInt(hairdresserIdParam);
            LocalDate date = LocalDate.parse(dateParam);

            // Convert service type to slot duration
            int slotMinutes = getSlotMinutes(serviceType);

            // Get available slots from AppointmentService
            List<LocalDateTime> availableSlots =
                    appointmentService.getAvailableSlots(hairdresserId, date, slotMinutes);

            // Preserve selected values for redisplay in JSP
            request.setAttribute("selectedHairdresserId", hairdresserId);
            request.setAttribute("selectedDate", dateParam);
            request.setAttribute("selectedServiceType", serviceType);

            // Search result
            request.setAttribute("availableSlots", availableSlots);

            request.getRequestDispatcher("/customer/book-appointments.jsp")
                    .forward(request, response);

        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid hairdresser ID.");
            request.getRequestDispatcher("/customer/book-appointments.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/customer/book-appointments.jsp")
                    .forward(request, response);
        }
    }

    /*
     * Helper method:
     * converts service type to slot duration in minutes.
     */
    private int getSlotMinutes(String serviceType) {
        switch (serviceType) {
            case "Haircut":
                return 30;
            case "Hair Styling":
                return 60;
            case "Hair Dying":
                return 60;
            case "Spa Service":
                return 60;
            case "Nail Coloring":
                return 30;
            default:
                throw new RuntimeException("Unknown service type.");
        }
    }
}

