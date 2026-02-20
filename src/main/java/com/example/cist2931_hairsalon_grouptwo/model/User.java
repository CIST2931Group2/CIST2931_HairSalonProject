package com.hairsalon.model;

import java.time.LocalDateTime;

/**
 * Base system user for authentication and authorization.
 * Represents account identity only.
 * Version 1, made to be edited ro reflect any future changes. -Jake Key
 * Version 2, DAO-friendly, sections added/labeled for readability. -Jake Key
 */
public class User {

    // Attributes
    private int userId;
    private String email;
    private String passwordHash;
    private String role; // CUSTOMER, HAIRDRESSER, ADMIN
    private boolean isActive;
    private LocalDateTime createdAt;

    // Constructors
    public User() { }

    public User(int userId, String email, String passwordHash, String role, boolean isActive, LocalDateTime createdAt) {
        this.userId = userId;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
        this.isActive = isActive;
        this.createdAt = createdAt;
    }

    // Getters
    public int getUserId() { return userId; }
    public String getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }
    public String getRole() { return role; }
    public boolean isActive() { return isActive; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    // Setters
    public void setUserId(int userId) { this.userId = userId; }
    public void setEmail(String email) { this.email = email; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public void setRole(String role) { this.role = role; }
    public void setActive(boolean active) { isActive = active; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}