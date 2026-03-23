package com.example.cist2931_hairsalon_grouptwo.dto;

/* CustomerAppointmentView (DTO - Data Transfer Object)
 *  Purpose:
 * View-ready object for Customer Dashboard.
 * Combines Appointment data with Hairdresser name.
 *
 * combines data from multiple domain models:
 *   - Appointment (id, time, service type, status)
 *   - Hairdresser (first name, last name)
 *
 * Author: Maria Ravid
 */

import java.time.LocalDateTime;

public class CustomerAppointmentView {

    private int appointmentId;

    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    private String serviceType;
    private String status;

    private int hairdresserId;
    private String hairdresserFirstName;
    private String hairdresserLastName;

    // ----- Getters & Setters -----

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getHairdresserId() {
        return hairdresserId;
    }

    public void setHairdresserId(int hairdresserId) {
        this.hairdresserId = hairdresserId;
    }

    public String getHairdresserFirstName() {
        return hairdresserFirstName;
    }

    public void setHairdresserFirstName(String hairdresserFirstName) {
        this.hairdresserFirstName = hairdresserFirstName;
    }

    public String getHairdresserLastName() {
        return hairdresserLastName;
    }

    public void setHairdresserLastName(String hairdresserLastName) {
        this.hairdresserLastName = hairdresserLastName;
    }

}
