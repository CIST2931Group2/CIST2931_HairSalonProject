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
 */

import com.example.cist2931_hairsalon_grouptwo.dao.ScheduleBlockDAO;
import com.example.cist2931_hairsalon_grouptwo.dao.ScheduleDAO;
import com.example.cist2931_hairsalon_grouptwo.model.Schedule;
import com.example.cist2931_hairsalon_grouptwo.model.ScheduleBlock;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

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
     */
    public int createWeeklySchedule(int hairdresserId, LocalDate weekStart) {

        if (weekStart == null)
            throw new RuntimeException("Week start date required");

        // Prevent duplicate weekly schedules
        if (scheduleDAO.getScheduleByWeek(hairdresserId, weekStart) != null)
            throw new RuntimeException("Schedule already exists");

        Schedule schedule = new Schedule();
        schedule.setHairdresserId(hairdresserId);
        schedule.setWeekStartDate(weekStart);
        schedule.setActive(true); // default new schedule is active

        return scheduleDAO.createWeeklySchedule(schedule);
    }
    // END CREATE WEEKLY SCHEDULE

    /* ADD SCHEDULE BLOCK (Working Hours Block)
     * FR-A-02 Admin Create/Edit/Delete Hair-Dresser schedule
     * WF-A1 Admin Creates Weekly Schedule (add time blocks)
     * Supports WF-C2 (Customer searches availability)
     */
    public int addScheduleBlock(int scheduleId,
                                String dayOfWeek,
                                LocalTime start,
                                LocalTime end) {

        // basic validation
        if (scheduleId <= 0)
            throw new RuntimeException("Invalid schedule ID");

        if (start == null || end == null)
            throw new RuntimeException("Start and end times required");

        if (!start.isBefore(end))
            throw new RuntimeException("Invalid time block : start must be before end");

        if (!isValidDay(dayOfWeek))
            throw new RuntimeException("Invalid day of week");

        ScheduleBlock block = new ScheduleBlock();
        block.setScheduleId(scheduleId);

        // Store day in DB-required format (uppercase 3-letter)
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
     */
    public List<ScheduleBlock> getBlocks(int scheduleId) {
        return scheduleBlockDAO.listBlocksBySchedule(scheduleId);
    }
    // END GET ALL BLOCKS FOR A SCHEDULE

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

}

