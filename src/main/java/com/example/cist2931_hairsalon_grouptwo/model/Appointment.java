package com.hairsalon.model;

import java.time.LocalDateTime;

/**
 * Represents a scheduled appointment.
 * Immutable after creation.
 * Version 1, made to be edited ro reflect any future changes. -Jake Key
 * Version 2 - full constructor added, no setters, labels added. -Jake Key
 */
public class Appointment {

    // Attributes
    private int appointmentId;
    private int customerId;
    private int hairdresserId;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String serviceType;
    private String status;
    private LocalDateTime createdAt;

    // Constructors
    public Appointment() { }

    public Appointment(int appointmentId, int customerId, int hairdresserId, LocalDateTime startDateTime, LocalDateTime endDateTime, String serviceType, String status, LocalDateTime createdAt) {
        this.appointmentId = appointmentId;
        this.customerId = customerId;
        this.hairdresserId = hairdresserId;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.serviceType = serviceType;
        this.status = status;
        this.createdAt = createdAt;
    }

    // Getters (immutable)
    public int getAppointmentId() { return appointmentId; }
    public int getCustomerId() { return customerId; }
    public int getHairdresserId() { return hairdresserId; }
    public LocalDateTime getStartDateTime() { return startDateTime; }
    public LocalDateTime getEndDateTime() { return endDateTime; }
    public String getServiceType() { return serviceType; }
    public String getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
