package com.example.cist2931_hairsalon_grouptwo.dto;

/* DailyAppointmentView (DTO - Data Transfer Object)
 *  Purpose:
 * This class represents a simplified view of appointment information
 * required by the Hairdresser Dashboard.
 *
 * combines data from multiple domain models:
 *   - Appointment (time, service type, status)
 *   - Customer (first name, last name)
 *
 * Author: Maria Ravid
 */

import java.time.LocalDateTime;

public class DailyAppointmentView {

    private int appointmentId;
    private int customerId;

    private LocalDateTime startDateTime;

    private String serviceType;
    private String status;

    private String customerFirstName;
    private String customerLastName;

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
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

    public String getCustomerFirstName() {
        return customerFirstName;
    }

    public void setCustomerFirstName(String customerFirstName) {
        this.customerFirstName = customerFirstName;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }
}
