package com.hairsalon.model;

/**
 * Represents a salon customer.
 * Holds customer-specific personal information.
 * Version 1, made to be edited ro reflect any future changes. -Jake Key
 * Version 2, constructors and setters added/labeled for readability. -Jake Key
 */
public class Customer {

    // Attributes
    private int customerId;
    private int userId; // FK -> User.userId
    private String firstName;
    private String lastName;
    private String phone;

    // Constructors
    public Customer() { }

    public Customer(int customerId, int userId, String firstName, String lastName, String phone) {
        this.customerId = customerId;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
    }

    // Getters
    public int getCustomerId() { return customerId; }
    public int getUserId() { return userId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getPhone() { return phone; }

    // Setters
    public void setCustomerId(int customerId) { this.customerId = customerId; }
    public void setUserId(int userId) { this.userId = userId; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setPhone(String phone) { this.phone = phone; }
}
