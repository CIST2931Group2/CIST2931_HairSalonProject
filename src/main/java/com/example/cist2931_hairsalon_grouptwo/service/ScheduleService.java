package com.example.cist2931_hairsalon_grouptwo.service;

/* ScheduleService
 * Service-layer class
 * - Manages weekly schedules and working time blocks for Hair-Dressers.
 * - Enforces business rules related to schedule creation and availability blocks.
 *
 * This layer contains BUSINESS RULES only.
 * All database interaction is in DAO classes.
 * No SQL statements should exist here.
 *
 * Author: Maria Ravid
 * **** Version 2 ****
 * createWeeklySchedule(), addScheduleBlock(), and getBlocks() updated
 * added new method getScheduleByWeek()
 */

import com.example.cist2931_hairsalon_grouptwo.dao.ScheduleBlockDAO;
import com.example.cist2931_hairsalon_grouptwo.dao.ScheduleDAO;
import com.example.cist2931_hairsalon_grouptwo.model.Schedule;
import com.example.cist2931_hairsalon_grouptwo.model.ScheduleBlock;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

public class ScheduleService {

    // DAO for weekly Schedule container (one per hairdresser per week).
    private final ScheduleDAO scheduleDAO;

    // DAO for ScheduleBlock (daily working hour segments).
    private final ScheduleBlockDAO scheduleBlockDAO;

    // Constructor
    public ScheduleService(ScheduleDAO scheduleDAO,
                           ScheduleBlockDAO scheduleBlockDAO) {
        this.scheduleDAO = scheduleDAO;
        this.scheduleBlockDAO = scheduleBlockDAO;
    }

    /* CREATE WEEKLY SCHEDULE
     * FR-A-02 Admin Create/Edit/Delete Hair-Dresser schedule
     * WF-A1 Admin Creates Weekly Schedule
     * UPDATED to V2: used original AdminService version and same method was updated there
     */
    public int createWeeklySchedule(int hairdresserId, LocalDate weekStartDate) {
        if (hairdresserId <= 0)
            throw new ServiceException("Invalid hairdresserId.");
        Objects.requireNonNull(weekStartDate, "weekStartDate");

        Schedule existing = scheduleDAO.getScheduleByWeek(hairdresserId, weekStartDate);
        if (existing != null)
            throw new ServiceException("Schedule already exists for that week.");

        Schedule s = new Schedule();
        s.setHairdresserId(hairdresserId);
        s.setWeekStartDate(weekStartDate);
        s.setActive(true);

        return scheduleDAO.createWeeklySchedule(s);

    }
    // END CREATE WEEKLY SCHEDULE

    /* ADD SCHEDULE BLOCK (Working Hours Block)
     * FR-A-02 Admin Create/Edit/Delete Hair-Dresser schedule
     * WF-A1 Admin Creates Weekly Schedule (add time blocks)
     * Supports WF-C2 (Customer searches availability)
     *
     ** UPDATED to V2: used original AdminService version and same method was updated there
     */
    public int addScheduleBlock(int scheduleId,
                                String dayOfWeek,
                                LocalTime start,
                                LocalTime end) {

        // basic validation
        if (scheduleId <= 0)
            throw new ServiceException("Invalid scheduleId.");
        if (isBlank(dayOfWeek))
            throw new ServiceException("dayOfWeek required.");

        // Ensure time values are provided
        Objects.requireNonNull(start, "start");
        Objects.requireNonNull(end, "end");

        // Business rule: start must be before end (no zero-length or reversed blocks)
        if (!start.isBefore(end))
            throw new ServiceException("Start must be before end.");
        // Validate DB day-of-week format (MON/TUE/.../SUN)
        if (!isValidDay(dayOfWeek))
            throw new ServiceException("Invalid dayOfWeek. Use MON..SUN.");

        // Create ScheduleBlock entity
        ScheduleBlock block = new ScheduleBlock();
        block.setScheduleId(scheduleId);
        block.setDayOfWeek(dayOfWeek.toUpperCase());
        block.setStartTime(start);
        block.setEndTime(end);

        return scheduleBlockDAO.addBlock(block);
    }
    // END ADD SCHEDULE BLOCK

    /* GET ALL BLOCKS FOR A SCHEDULE
     * WF-H2 Hair-Dresser Views Schedule
     * WF-C2 Customer Searches Availability (reads blocks)
     *
     * Returns all working hour blocks for a weekly schedule.
     *
     *UPDATED to V2: used original AdminService version and same method was updated there
     */
    public List<ScheduleBlock> getBlocks(int scheduleId) {
        if (scheduleId <= 0)
            throw new RuntimeException("Invalid scheduleId.");

        return scheduleBlockDAO.listBlocksBySchedule(scheduleId);
    }
    // END GET ALL BLOCKS FOR A SCHEDULE

    /* GET WEEKLY SCHEDULE
     *
     * UPDATED to V2: new method as used in the AdminScheduleServlet
     */
    public Schedule getScheduleByWeek(int hairdresserId, LocalDate weekStart) {
        if (hairdresserId <= 0)
            throw new RuntimeException("Invalid hairdresserId");
        if (weekStart == null)
            throw new RuntimeException("weekStart required");

        return scheduleDAO.getScheduleByWeek(hairdresserId, weekStart);
    }
    // END GET WEEKLY SCHEDULE


    // ----------------------------
    // Helpers (input validation)
    // ----------------------------
    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
    
    /*
     * Validates that the day string matches DB format:
     * MON/TUE/WED/THU/FRI/SAT/SUN
     */
    private boolean isValidDay(String day) {
        if (day == null) return false;

        String d = day.trim().toUpperCase();

        return d.equals("MON") ||
                d.equals("TUE") ||
                d.equals("WED") ||
                d.equals("THU") ||
                d.equals("FRI") ||
                d.equals("SAT") ||
                d.equals("SUN");
    }

    /*
     * ServiceException
     * runtime exception used to represent business-rule failures in service layer.
     * Version 3 - added ServiceException
     */
    public static class ServiceException extends RuntimeException {
        public ServiceException(String message) { super(message); }
    }
}


