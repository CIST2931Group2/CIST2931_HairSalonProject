package com.example.cist2931_hairsalon_grouptwo.service;

/* CustomerService
 *  Service-layer class responsible for CUSTOMER business logic related to:
 * - Customer profile lookup
 * - Customer profile updates
 * - Customer authorization guard (active + correct role)
 *
 * This layer contains BUSINESS RULES only.
 * All database interaction is in DAO classes.
 * No SQL statements should exist here.
 * Authorization checks is via assertActiveCustomerUser,
 * while session handling belongs to Servlets later.
 *
 * Author: Maria Ravid
 */

import com.example.cist2931_hairsalon_grouptwo.dao.CustomerDAO;
import com.example.cist2931_hairsalon_grouptwo.dao.UserDAO;
import com.example.cist2931_hairsalon_grouptwo.model.Customer;
import com.example.cist2931_hairsalon_grouptwo.model.User;

import java.util.Objects;

public class CustomerService {

    /* DAO for Customer table operations:
     * - get by customerId
     * - get by userId
     * - update customer profile
     */
    private final CustomerDAO customerDAO;

    /* DAO for User table operations:
     * - used for role/active checks (authorization guard)
     */
    private final UserDAO userDAO;

    // Constructor
    public CustomerService(CustomerDAO customerDAO, UserDAO userDAO) {
        this.customerDAO = Objects.requireNonNull(customerDAO);
        this.userDAO = Objects.requireNonNull(userDAO);
    }

    // ----------------------------
    // Get Customer
    // ----------------------------

    /* GET CUSTOMER BY CUSTOMER_ID
     * - FR-C-03 Customer Dashboard/Profile view
     * - WF-C2 Customer Dashboard
     *
     */
    public Customer getCustomerById(int customerId) {
        if (customerId <= 0)
            throw new ServiceException("Invalid customerId.");

        // DAO call: fetch Customer row by PK
        return customerDAO.getCustomerById(customerId); // DAO method expected
    }
    // END GET CUSTOMER BY CUSTOMER_ID

    /* GET CUSTOMER BY USER_ID
     * Load customer profile by userId (common after login).
     * - FR-C-02 Login continuation
     * - WF-S1 Customer login
     */
    public Customer getCustomerByUserId(int userId) {
        if (userId <= 0)
            throw new ServiceException("Invalid userId.");

        // DAO call: fetch Customer row by user_id FK
        return customerDAO.getCustomerByUserId(userId); // DAO method expected
    }
    // END GET CUSTOMER BY USER_ID

    // ----------------------------
    // Update Customer
    // ----------------------------
    /* UPDATE CUSTOMER PROFILE
     * - FR-C-03 Update customer profile
     * - WF-C4 Customer Updates Profile
     *
     */
    public void updateCustomerProfile(Customer customer) {
        Objects.requireNonNull(customer, "customer");

        // basic validation
        if (customer.getCustomerId() <= 0)
            throw new ServiceException("Invalid customerId.");
        if (isBlank(customer.getFirstName()))
            throw new ServiceException("First name is required.");
        if (isBlank(customer.getLastName()))
            throw new ServiceException("Last name is required.");

        // phone validation
        String phone = customer.getPhone();
        if (phone != null && !phone.isBlank()) {
            String digits = phone.replaceAll("\\D", "");
            if (digits.length() < 7)
                throw new ServiceException("Phone looks too short.");
        }

        // DAO call: update Customer row in DB
        customerDAO.updateCustomer(customer); // DAO method expected
    }
    // END UPDATE CUSTOMER PROFILE

    /* AUTHORIZATION GUARD: ACTIVE CUSTOMER CHECK
     * Confirms that a given userId belongs to an ACTIVE CUSTOMER account.
     * Servlets will manage sessions; this method validates business authorization.
     */
    public void assertActiveCustomerUser(int userId) {
        if (userId <= 0)
            throw new ServiceException("Invalid userId.");

        User u = userDAO.findById(userId);
        if (u == null)
            throw new ServiceException("User not found.");
        if (!u.isActive())
            throw new ServiceException("User inactive.");
        if (!"CUSTOMER".equalsIgnoreCase(u.getRole()))
            throw new ServiceException("User is not a CUSTOMER.");
    }
    // END AUTHORIZATION GUARD

    // ----------------------------
    // Helpers (input validation)
    // ----------------------------
    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    /*
     * ServiceException
     * runtime exception used to represent business-rule failures in service layer.
     */
    public static class ServiceException extends RuntimeException {
        public ServiceException(String message) { super(message); }
    }
}

