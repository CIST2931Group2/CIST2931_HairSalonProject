package com.example.cist2931_hairsalon_grouptwo.dto;

/* AssignedCustomerView (DTO - Data Transfer Object)
 *  Purpose:
 * DTO used for displaying customers assigned to a hairdresser.
 * Combines fields from Customer and User tables so the UI
 * can display contact information without exposing DAO logic.
 *
 * Author: Maria Ravid
 */

public class AssignedCustomerView {

    private int customerId;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;

    public AssignedCustomerView(int customerId,
                                String firstName,
                                String lastName,
                                String phone,
                                String email) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
    }

    public int getCustomerId() { return customerId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }

}
