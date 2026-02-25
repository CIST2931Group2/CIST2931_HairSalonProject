package com.hairsalon.model;

/**
 * Represents a professional hairdresser.
 * Contains professional and profile information only.
 * Version 1, made to be edited ro reflect any future changes. -Jake Key
 * Version 2, constructors and setters added/labeled for readability. -Jake Key
 */
public class Hairdresser {

    // Attributes
    private int hairdresserId;
    private int userId; // FK -> User.userId
    private String firstName;
    private String lastName;
    private String phone;
    private String specialties;
    private String bio;

    // Constructors
    public Hairdresser() { }

    public Hairdresser(int hairdresserId, int userId, String firstName, String lastName, String phone, String specialties, String bio) {
        this.hairdresserId = hairdresserId;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.specialties = specialties;
        this.bio = bio;
    }

    // Getters
    public int getHairdresserId() { return hairdresserId; }
    public int getUserId() { return userId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getPhone() { return phone; }
    public String getSpecialties() { return specialties; }
    public String getBio() { return bio; }

    // Setters
    public void setHairdresserId(int hairdresserId) { this.hairdresserId = hairdresserId; }
    public void setUserId(int userId) { this.userId = userId; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setSpecialties(String specialties) { this.specialties = specialties; }
    public void setBio(String bio) { this.bio = bio; }
}
