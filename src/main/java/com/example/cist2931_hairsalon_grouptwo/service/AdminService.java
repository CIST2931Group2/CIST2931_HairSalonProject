package com.example.cist2931_hairsalon_grouptwo.service;

/* AdminService
 * Service-layer class implementing ADMIN business logic for managing:
 * - Hairdresser accounts (create / deactivate)
 * - Weekly schedules (create)
 * - Schedule blocks (add/list)
 *
 * This layer contains BUSINESS RULES only.
 * All database interaction is in DAO classes.
 * No SQL statements should exist here.
 * Role/authorization enforcement is via assertActiveAdminUser(),
 *   but full enforcement is done later once Servlets exist.
 *
 * Author: Maria Ravid
 */

import com.example.cist2931_hairsalon_grouptwo.dao.HairdresserDAO;
import com.example.cist2931_hairsalon_grouptwo.dao.ScheduleBlockDAO;
import com.example.cist2931_hairsalon_grouptwo.dao.ScheduleDAO;
import com.example.cist2931_hairsalon_grouptwo.dao.UserDAO;
import com.example.cist2931_hairsalon_grouptwo.model.Hairdresser;
import com.example.cist2931_hairsalon_grouptwo.model.Schedule;
import com.example.cist2931_hairsalon_grouptwo.model.ScheduleBlock;
import com.example.cist2931_hairsalon_grouptwo.model.User;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;


public class AdminService {

    /* DAO for User table operations
     *  - create user accounts
     * - activate/deactivate users
     * - find user by ID
     */
    private final UserDAO userDAO;

    /* DAO for Hairdresser table operations:
     * - create hairdresser profile
     * - fetch hairdresser profile (to resolve userId)
     */
    private final HairdresserDAO hairdresserDAO;

    /* DAO for Schedule table operations (weekly schedule container):
     * - create schedule
     * - retrieve schedule by hairdresserId + weekStartDate
     */
    private final ScheduleDAO scheduleDAO;

    /* DAO for ScheduleBlock table operations (daily blocks inside a weekly schedule):
     * - add block
     * - list blocks by scheduleId
     */
    private final ScheduleBlockDAO scheduleBlockDAO;

    // Constructor
    public AdminService(UserDAO userDAO,
                        HairdresserDAO hairdresserDAO,
                        ScheduleDAO scheduleDAO,
                        ScheduleBlockDAO scheduleBlockDAO) {
        this.userDAO = Objects.requireNonNull(userDAO);
        this.hairdresserDAO = Objects.requireNonNull(hairdresserDAO);
        this.scheduleDAO = Objects.requireNonNull(scheduleDAO);
        this.scheduleBlockDAO = Objects.requireNonNull(scheduleBlockDAO);
    }

    /* ADD HAIRDRESSER ACCOUNT (Admin Use Case)
     * - FR-A-03 Add Hair-Dressers
     * - WF-A2 Admin Adds Employees
     *
     *  - This method creates two linked records:
     *   1. User account (authentication identity)
     *   2. Hairdresser profile (staff details)
     */
    public int addHairdresserAccount(User userAccount, Hairdresser hairdresserProfile) {
        Objects.requireNonNull(userAccount, "userAccount");
        Objects.requireNonNull(hairdresserProfile, "hairdresserProfile");

        // Basic input validation
        if (isBlank(userAccount.getEmail()))
            throw new ServiceException("Email required.");
        if (isBlank(userAccount.getPasswordHash()))
            throw new ServiceException("Password hash required.");

        // Force role to HAIRDRESSER for safety (admin controls role assignment)
        userAccount.setRole("HAIRDRESSER");
        userAccount.setActive(true);

        // 1) Create User record
        int userId = userDAO.createUser(userAccount);

        // 2) Create Hairdresser profile record linked to User
        hairdresserProfile.setUserId(userId);
        return hairdresserDAO.createHairdresser(hairdresserProfile);
    }
    // END ADD HAIRDRESSER ACCOUNT

    /* DEACTIVATE HAIRDRESSER
     * - FR-H-04 Admin Deactivate Hairdresser
     * - WF-A3 Admin Manage Employees
     */
    public void deactivateHairdresser(int hairdresserId) {
        if (hairdresserId <= 0)
            throw new ServiceException("Invalid hairdresserId.");

        Hairdresser h = hairdresserDAO.getHairdresserById(hairdresserId);
        if (h == null)
            throw new ServiceException("Hairdresser not found.");

        // Deactivate the linked login account
        userDAO.setActive(h.getUserId(), false);
    }
    // END DEACTIVATE HAIRDRESSER

    /* CREATE WEEKLY SCHEDULE (Schedule Container)
     * - FR-A-02 Admin Create/Edit/Delete Hair-Dresser schedule
     * - WF-A1 Admin Creates Weekly Schedule
     */
    public int createWeeklySchedule(int hairdresserId, LocalDate weekStartDate) {
        if (hairdresserId <= 0) throw new ServiceException("Invalid hairdresserId.");
        Objects.requireNonNull(weekStartDate, "weekStartDate");

        Schedule existing = scheduleDAO.getScheduleByWeek(hairdresserId, weekStartDate);
        if (existing != null) throw new ServiceException("Schedule already exists for that week.");

        Schedule s = new Schedule();
        s.setHairdresserId(hairdresserId);
        s.setWeekStartDate(weekStartDate);
        s.setActive(true);

        return scheduleDAO.createWeeklySchedule(s);
    }
    // END CREATE WEEKLY SCHEDULE

    /* ADD SCHEDULE BLOCK (Working hours segment)
     * - FR-A-02 Admin Create/Edit/Delete Hair-Dresser schedule
     * - WF-A1 Admin Creates Weekly Schedule (adds blocks for each day)
     */
    public int addScheduleBlock(int scheduleId, String dayOfWeek, LocalTime start, LocalTime end) {
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

    /* LIST SCHEDULE BLOCKS (Admin view of schedule)
     * - WF-A1 Admin Creates Weekly Schedule (review blocks)
     * - WF-A3 Admin Manage Employees (view schedule status)
     * Returns: list of blocks for a given scheduleId
     *
    */
    public List<ScheduleBlock> listScheduleBlocks(int scheduleId) {
        if (scheduleId <= 0)
            throw new ServiceException("Invalid scheduleId.");

        return scheduleBlockDAO.listBlocksBySchedule(scheduleId);
    }

    /* AUTHORIZATION GUARD (Admin-only)
     * Validates that a given userId corresponds to an ACTIVE ADMIN user.
     * Servlets will manage sessions; this method validates business authorization.
    */
    public void assertActiveAdminUser(int userId) {
        if (userId <= 0)
            throw new ServiceException("Invalid userId.");

        User u = userDAO.findById(userId);
        if (u == null)
            throw new ServiceException("User not found.");
        if (!u.isActive())
            throw new ServiceException("User inactive.");
        if (!"ADMIN".equalsIgnoreCase(u.getRole()))
            throw new ServiceException("User is not an ADMIN.");
    }
    // END AUTHORIZATION GUARD

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
     */
    public static class ServiceException extends RuntimeException {
        public ServiceException(String message) { super(message); }
    }
}

