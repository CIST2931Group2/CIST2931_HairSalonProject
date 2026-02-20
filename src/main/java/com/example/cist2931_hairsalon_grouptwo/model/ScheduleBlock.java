package com.hairsalon.model;

import java.time.LocalTime;

/**
 * Represents a working time block within a weekly schedule.
 * Version 1, made to be edited ro reflect any future changes. -Jake Key
 * Version 2 - constructors and setters added, labels added. -Jake Key
 */
public class ScheduleBlock {

    private int scheduleBlockId;
    private int scheduleId;
    private String dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;

    // Constructors
    public ScheduleBlock() { }

    public ScheduleBlock(int scheduleBlockId, int scheduleId, String dayOfWeek, LocalTime startTime, LocalTime endTime) {
        this.scheduleBlockId = scheduleBlockId;
        this.scheduleId = scheduleId;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Getters
    public int getScheduleBlockId() { return scheduleBlockId; }
    public int getScheduleId() { return scheduleId; }
    public String getDayOfWeek() { return dayOfWeek; }
    public LocalTime getStartTime() { return startTime; }
    public LocalTime getEndTime() { return endTime; }

    // Setters
    public void setScheduleBlockId(int scheduleBlockId) { this.scheduleBlockId = scheduleBlockId; }
    public void setScheduleId(int scheduleId) { this.scheduleId = scheduleId; }
    public void setDayOfWeek(String dayOfWeek) { this.dayOfWeek = dayOfWeek; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }
}
