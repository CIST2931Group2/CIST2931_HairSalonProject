package com.hairsalon.model;

import java.time.LocalDateTime;

/**
 * Represents a scheduled appointment.
 * Immutable after creation.
 * Version 1, made to be edited ro reflect any future changes. -Jake Key
 * Version 2 - full constructor added, no setters, labels added. -Jake Key
 * Version 3 - added setters. - Jake Key (have to double check with DAO for customerId, setter placed until I can confirm with DAO)
 */
public class Appointment {

    // Attributes
    private int appointmentId;            //AutoNumber
    private int customerId;               //Required
    private int hairdresserId;            //Required
    private LocalDateTime startDateTime;  //Required
    private LocalDateTime endDateTime;    //Required
    private String serviceType;           //Required
    private String status;                //Required
    private LocalDateTime createdAt;      //Required

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

    //Setters
    public void setAppointmentId(int appointmentId) { this.appointmentId = appointmentId; }           //May change
    public void setCustomerId(int customerId) { this customerId = customerId; }
    public void setHairdresserId(int hairdresserId) { this.hairdresserId = hairdresserId; }
    public void setStartDateTime(LocalDateTime startDateTime) { this.startDateTime = startDateTime; }
    public void setEndDateTime(LocalDateTime endDateTime) { this.endDateTime = endDateTime; }
    public void setServiceType(String serviceType) { this.serviceType = serviceType; }
    public void setStatus(String status) { this.status = status; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}

