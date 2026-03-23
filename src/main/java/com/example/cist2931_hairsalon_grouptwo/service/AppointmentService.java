package com.example.cist2931_hairsalon_grouptwo.service;

/* AppointmentService
 * Service-layer class responsible for appointment-related business logic.
 *
 * This class: enforces scheduling rules
 *
 * This layer contains BUSINESS RULES only.
 * All database interaction is in DAO classes.
 * No SQL statements should exist here.
 *
 *  This service assumes the user is already authenticated (WF-S1 is a precondition for business workflows).
 *
 * Author: Maria Ravid
 *
 * Version 2: added method - getAvailableSlots(...) for FR-C-04
 *
 */

import com.example.cist2931_hairsalon_grouptwo.dao.*;
import com.example.cist2931_hairsalon_grouptwo.dto.CustomerAppointmentView;
import com.example.cist2931_hairsalon_grouptwo.model.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AppointmentService {

    /* DAO for Appointment table operations:
     * - create appointment
     * - fetch appointments by customer
     * - fetch appointments by hairdresser + date (used for overlap checks and schedule views)
     */
    private final AppointmentDAO appointmentDAO;

    /* DAO for Schedule table (weekly schedule container).
     * Used to verify that a hairdresser has an active schedule for the relevant week.
     */
    private final ScheduleDAO scheduleDAO;

    /* DAO for ScheduleBlock table (availability blocks inside a weekly schedule).
     * Used to verify the requested appointment time falls within working hours.
     */
    private final ScheduleBlockDAO scheduleBlockDAO;

    /*
     *  DAO for User and Hairdresser tables
     *  Used to verify hairdresser active check
     */
    private final HairdresserDAO hairdresserDAO;
    private final UserDAO userDAO;


    // Constructor
    public AppointmentService(AppointmentDAO appointmentDAO,
                              ScheduleDAO scheduleDAO,
                              ScheduleBlockDAO scheduleBlockDAO,
                              HairdresserDAO hairdresserDAO,
                              UserDAO userDAO) {
        this.appointmentDAO = appointmentDAO;
        this.scheduleDAO = scheduleDAO;
        this.scheduleBlockDAO = scheduleBlockDAO;
        this.hairdresserDAO = hairdresserDAO;
        this.userDAO = userDAO;
    }

    // Convert MONDAY → MON (DB format)
    private String toDbDayCode(LocalDateTime dt) {
        return dt.getDayOfWeek().name().substring(0, 3);
    }

    /* SEARCH AVAILABLE SLOTS - added on V2
     * FR-C-04 Search available appointments
     * Returns available slot start times for one hairdresser on one date.
     */
    public List<LocalDateTime> getAvailableSlots(int hairdresserId,
                                                 LocalDate date,
                                                 int slotMinutes) {

        if (hairdresserId <= 0)
            throw new RuntimeException("Invalid hairdresserId");

        if (date == null)
            throw new RuntimeException("Date is required");

        if (slotMinutes <= 0)
            throw new RuntimeException("slotMinutes must be positive");

        // ---------------------------------------------------
        // Verify hairdresser exists and is active
        // ---------------------------------------------------
        Hairdresser hairdresser = hairdresserDAO.getHairdresserById(hairdresserId);

        if (hairdresser == null)
            throw new RuntimeException("Hairdresser not found");

        User hairdresserUser = userDAO.findById(hairdresser.getUserId());

        if (hairdresserUser == null || !hairdresserUser.isActive())
            throw new RuntimeException("Hairdresser account is inactive");

        // ---------------------------------------------------
        // Find weekly schedule for the selected date
        // ---------------------------------------------------
        LocalDate weekStart = date.with(DayOfWeek.MONDAY);
        Schedule schedule = scheduleDAO.getScheduleByWeek(hairdresserId, weekStart);

        if (schedule == null || !schedule.isActive())
            return List.of();   // no schedule = no available slots

        // ---------------------------------------------------
        // Get blocks for the selected day
        // ---------------------------------------------------
        List<ScheduleBlock> blocks =
                scheduleBlockDAO.listBlocksBySchedule(schedule.getScheduleId());

        // DB stores day as MON/TUE/... so convert Java enum (MONDAY) --> MON
        String dayCode = date.getDayOfWeek().name().substring(0, 3);

        List<ScheduleBlock> dayBlocks = blocks.stream()
                .filter(b -> b.getDayOfWeek().equalsIgnoreCase(dayCode))
                .toList();

        if (dayBlocks.isEmpty())
            return List.of();

        // ---------------------------------------------------
        // Get already-booked appointments for that day
        // ---------------------------------------------------
        List<Appointment> existing =
                appointmentDAO.listByHairdresserAndDate(hairdresserId, date);

        List<LocalDateTime> available = new java.util.ArrayList<>();
        LocalDateTime now = LocalDateTime.now();

        // ---------------------------------------------------
        // Generate candidate slots inside each block
        // ---------------------------------------------------
        for (ScheduleBlock block : dayBlocks) {

            LocalDateTime slotStart = LocalDateTime.of(date, block.getStartTime());
            LocalDateTime blockEnd  = LocalDateTime.of(date, block.getEndTime());

            while (!slotStart.plusMinutes(slotMinutes).isAfter(blockEnd)) {
                LocalDateTime slotEnd = slotStart.plusMinutes(slotMinutes);

                if (!hasOverlap(existing, slotStart, slotEnd)
                        && !slotStart.isBefore(now)) { // future slots, exactly now - allowed, past - blocked
                    available.add(slotStart);
                }

                slotStart = slotStart.plusMinutes(slotMinutes);
            }
        }

        return available;
    } // END getAvailableSlots


    /* BOOK APPOINTMENT (Business Rule Enforcement)
     * FR-C-05 Schedule appointment
     * WF-C3 Customer Schedules Appointment
     */
    public int bookAppointment(int customerId,
                               int hairdresserId,
                               LocalDateTime start,
                               LocalDateTime end,
                               String serviceType) {

        // Basic validation: time range must be valid
        if (start == null || end == null)
            throw new RuntimeException("Start and end required");

        if (!start.isBefore(end))
            throw new RuntimeException("Start must be before end");

        // Prevent booking in the past
        if (start.isBefore(LocalDateTime.now()))
            throw new RuntimeException("Cannot book appointment in the past");

        // ---------------------------------------------------
        // FULL HAIRDRESSER ACTIVE CHECK
        // ---------------------------------------------------

        Hairdresser hairdresser = hairdresserDAO.getHairdresserById(hairdresserId);

        if (hairdresser == null)
            throw new RuntimeException("Hairdresser not found");

        User hairdresserUser = userDAO.findById(hairdresser.getUserId());

        if (hairdresserUser == null || !hairdresserUser.isActive())
            throw new RuntimeException("Hairdresser account is inactive");

        // ------------------------------------------------------------
        // Validate weekly schedule exists and is active (WF-C3, WF-C2)
        // ------------------------------------------------------------

        // Schedules are stored by week (week_start_date), compute the Monday of that week.
        LocalDate weekStart = start.toLocalDate().with(DayOfWeek.MONDAY);

        // Retrieve the weekly schedule for the hairdresser and week.
        Schedule schedule = scheduleDAO.getScheduleByWeek(hairdresserId, weekStart);

        // If no schedule exists OR it is not active, the hairdresser has no availability that week.
        if (schedule == null || !schedule.isActive())
            throw new RuntimeException("No active schedule for that week");

        // --------------------------------------------------
        // Validate within availability block
        // --------------------------------------------------

        // Retrieve schedule blocks (working hours segments) for the schedule.
        List<ScheduleBlock> blocks =
                scheduleBlockDAO.listBlocksBySchedule(schedule.getScheduleId());

        // Convert Java DayOfWeek (MONDAY) to DB format (MON)
        String dayCode = toDbDayCode(start);

        boolean withinBlock = blocks.stream().anyMatch(b ->
                b.getDayOfWeek().equalsIgnoreCase(dayCode) &&
                        !start.toLocalTime().isBefore(b.getStartTime()) &&
                        !end.toLocalTime().isAfter(b.getEndTime())
        );

        if (!withinBlock)
            throw new RuntimeException("Outside working hours");

        // --------------------------------------------------
        // Prevent overlap
        // --------------------------------------------------

        List<Appointment> existing =
                appointmentDAO.listByHairdresserAndDate(hairdresserId, start.toLocalDate());

        boolean overlap = existing.stream().anyMatch(a ->
                start.isBefore(a.getEndDateTime()) &&
                        end.isAfter(a.getStartDateTime())
        );

        if (overlap)
            throw new RuntimeException("Time slot already booked");

        // --------------------------------------------------
        // Create appointment
        // --------------------------------------------------

        Appointment appt = new Appointment(
                0,
                customerId,
                hairdresserId,
                start,
                end,
                serviceType,
                "SCHEDULED",
                LocalDateTime.now()
        );

        return appointmentDAO.createAppointment(appt);
    }
    // END BOOK APPOINTMENT

    /* HAIRDRESSER VIEW CUSTOMER APPOINTMENTS
     * FR-H-04 View customer profile + appt. history
     * supports: HairdresserCustomerProfileServlet
     */
    public List<Appointment> getCustomerAppointments(int customerId) {
        return appointmentDAO.listByCustomer(customerId);
    }
    // HAIRDRESSER VIEW CUSTOMER APPOINTMENTS

    /* CUSTOMER VIEWS APPOINTMENTS
     * FR-C-06 View past appointments  (date, time, Hair-Dresser, status)
     * FR-C-07 View future appointments (date, time, Hair-Dresser, status)
     * WF-C5 Customer Views Appointments
     *
     * ***V3 - new method for Customer dashboard
     */
    public List<CustomerAppointmentView> getCustomerAppointmentsDashboard(int customerId) {
        if (customerId <= 0)
            throw new RuntimeException("Invalid customerId.");

        List<Appointment> appointments = appointmentDAO.listByCustomer(customerId);
        List<CustomerAppointmentView> result = new ArrayList<>();

        for (Appointment appt : appointments) {

            // Get hairdresser details
            Hairdresser hairdresser =
                    hairdresserDAO.getHairdresserById(appt.getHairdresserId());

            CustomerAppointmentView view = new CustomerAppointmentView();

            // ----- Appointment data -----
            view.setAppointmentId(appt.getAppointmentId());
            view.setStartDateTime(appt.getStartDateTime());
            view.setEndDateTime(appt.getEndDateTime());
            view.setServiceType(appt.getServiceType());   // IMPORTANT (new field)
            view.setStatus(appt.getStatus());

            // ----- Hairdresser data -----
            view.setHairdresserId(appt.getHairdresserId());

            if (hairdresser != null) {
                view.setHairdresserFirstName(hairdresser.getFirstName());
                view.setHairdresserLastName(hairdresser.getLastName());
            }

            result.add(view);
        }

        return result;
    }
    // END CUSTOMER VIEWS APPOINTMENTS

    /* HELPER method - added on V2
     * checks if a candidate slot overlaps with any existing appointment for the hairdresser.
     * Returns true if the candidate slot overlaps with any existing appointment.
     */
    private boolean hasOverlap(List<Appointment> existing,
                               LocalDateTime slotStart,
                               LocalDateTime slotEnd) {

        return existing.stream().anyMatch(a ->
                slotStart.isBefore(a.getEndDateTime()) &&
                        slotEnd.isAfter(a.getStartDateTime())
        );
    }

}