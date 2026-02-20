package com.hairsalon.model;

import java.time.LocalDate;

/**
 * Weekly schedule container for a hairdresser.
 * Version 1, made to be edited ro reflect any future changes. -Jake Key
 * Version 2 - constructors and setters added, labels added. -Jake Key
 */
public class Schedule {

    private int scheduleId;
    private int hairdresserId;
    private LocalDate weekStartDate;
    private boolean isActive;

    // Constructors
    public Schedule() { }

    public Schedule(int scheduleId, int hairdresserId, LocalDate weekStartDate, boolean isActive) {
        this.scheduleId = scheduleId;
        this.hairdresserId = hairdresserId;
        this.weekStartDate = weekStartDate;
        this.isActive = isActive;
    }

    // Getters
    public int getScheduleId() { return scheduleId; }
    public int getHairdresserId() { return hairdresserId; }
    public LocalDate getWeekStartDate() { return weekStartDate; }
    public boolean isActive() { return isActive; }

    // Setters
    public void setScheduleId(int scheduleId) { this.scheduleId = scheduleId; }
    public void setHairdresserId(int hairdresserId) { this.hairdresserId = hairdresserId; }
    public void setWeekStartDate(LocalDate weekStartDate) { this.weekStartDate = weekStartDate; }
    public void setActive(boolean active) { isActive = active; }
}
