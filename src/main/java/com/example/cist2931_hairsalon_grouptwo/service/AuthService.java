package com.example.cist2931_hairsalon_grouptwo.service;

/* AuthService
 * Service-layer class responsible for authentication and account-related business logic.
 *
 * This class:
 * - Validates user credentials during login
 * - Handles customer registration workflow
 * - Controls user activation/deactivation
 *
 * This layer contains BUSINESS RULES only.
 * All database interaction is in DAO classes.
 * No SQL statements should exist here.
 *
 * Author: Maria Ravid
 */

import com.example.cist2931_hairsalon_grouptwo.dao.CustomerDAO;
import com.example.cist2931_hairsalon_grouptwo.dao.UserDAO;
import com.example.cist2931_hairsalon_grouptwo.model.Customer;
import com.example.cist2931_hairsalon_grouptwo.model.User;

import java.time.LocalDateTime;

public class AuthService {
    // DAO dependency for user-level activities (login, activate/deactivate, user creation).
    // Used by multiple roles (CUSTOMER, HAIR_DRESSER, ADMIN).
    private final UserDAO userDAO;

    // DAO dependency for CUSTOMER profile creation (separate table from User).
    // Used only when registering a new CUSTOMER.
    private final CustomerDAO customerDAO;

    // Constructor
    public AuthService(UserDAO userDAO, CustomerDAO customerDAO) {
        this.userDAO = userDAO;
        this.customerDAO = customerDAO;
    }

    /* Login logic
    FR-C-02 Customer login, FR-H-01 Login for existing employees, FR-H-01 Login for existing employees
    WF-S1 User Login
     */
    public User login(String email, String passwordHash) {
        // Basic validation
        if (email == null || email.isBlank())
            throw new RuntimeException("Email required");

        // Retrieve user by email
        User user = userDAO.findByEmail(email);

        if (user == null)
            throw new RuntimeException("User not found");

        // Ensure account is active
        if (!user.isActive())
            throw new RuntimeException("Account inactive");

        // Validate password (currently plain comparison)
        if (!user.getPasswordHash().equals(passwordHash))
            throw new RuntimeException("Invalid password");

        // Successful authentication
        return user;
    }

    /* Register CUSTOMER only
    FR-C-01 Account creation
    WF-C1 Customer Account Creation
    Notes:
    * Creates a User record (role=CUSTOMER) + a Customer profile record
    * Returns the generated userId as the primary identifier for authentication
    */
    public int registerCustomer(String email, String passwordHash,
                                String firstName, String lastName, String phone) {

        // Prevent duplicate email accounts
        if (userDAO.findByEmail(email) != null)
            throw new RuntimeException("Email already exists");

        // Create User entity
        User user = new User();
        user.setEmail(email);
        user.setPasswordHash(passwordHash);
        user.setRole("CUSTOMER");
        user.setActive(true);
        user.setCreatedAt(LocalDateTime.now());

        int userId = userDAO.createUser(user);

        // Create linked Customer profile
        Customer customer = new Customer();
        customer.setUserId(userId);
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setPhone(phone);

        customerDAO.createCustomer(customer);

        return userId;
    }

    /*
     * FR-H-04 Admin Deactivate Hairdresser
     * WF-A3 Admin Manage Employees
     * current method deactivates ANY userId
     * TODO:
     * Add role-based guard (ADMIN-only, HAIRDRESSER target only)
     * after LoginServlet and session tracking are completed.
     */
    public void deactivateUser(int userId) {
        userDAO.setActive(userId, false);
    }
}

