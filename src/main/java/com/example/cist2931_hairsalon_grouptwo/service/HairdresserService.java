package com.chattech.hairsalon.hair_salon.service;

/* HairdresserService
 *  Service-layer class responsible for HAIRDRESSER business logic:
 * - Hairdresser profile lookup/update
 * - Hairdresser dashboard data (daily appointments)
 * - Supporting lookups needed by hairdresser UI (customer profile view)
 * - Authorization guard for hairdresser-only endpoints
 *
 * This layer contains BUSINESS RULES only.
 * All database interaction is in DAO classes.
 * No SQL statements should exist here.
 * Authorization checks is via assertActiveCustomerUser,
 * while session handling belongs to Servlets later.
 *
 * Author: Maria Ravid
 */

import com.chattech.hairsalon.hair_salon.dao.AppointmentDAO;
import com.chattech.hairsalon.hair_salon.dao.CustomerDAO;
import com.chattech.hairsalon.hair_salon.dao.HairdresserDAO;
import com.chattech.hairsalon.hair_salon.dao.UserDAO;
import com.chattech.hairsalon.hair_salon.model.Appointment;
import com.chattech.hairsalon.hair_salon.model.Customer;
import com.chattech.hairsalon.hair_salon.model.Hairdresser;
import com.chattech.hairsalon.hair_salon.model.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;


public class HairdresserService {

    /* DAO: Hairdresser table operations
     * - list all hairdressers (for customer booking page)
     * - get hairdresser by id
     * - get hairdresser by userId (after login)
     * - update hairdresser profile
     */
    private final HairdresserDAO hairdresserDAO;

    /* DAO: Appointment operations
     * - list appointments for hairdresser on a given date (schedule view)
     */
    private final AppointmentDAO appointmentDAO;

    /* DAO: Customer operations
     * - used when hairdresser needs to view customer profile details
     */
    private final CustomerDAO customerDAO;

    /* DAO: User operations
     * - used for role/active checks (authorization guard)
     */
    private final UserDAO userDAO;

    // Constructor
    public HairdresserService(HairdresserDAO hairdresserDAO,
                              AppointmentDAO appointmentDAO,
                              CustomerDAO customerDAO,
                              UserDAO userDAO) {
        this.hairdresserDAO = Objects.requireNonNull(hairdresserDAO);
        this.appointmentDAO = Objects.requireNonNull(appointmentDAO);
        this.customerDAO = Objects.requireNonNull(customerDAO);
        this.userDAO = Objects.requireNonNull(userDAO);
    }

    /* LIST ALL HAIRDRESSERS
     * - FR-C-04 Search available appointments (filter by hairdresser)
     * - WF-C2 Customer Searches Availability / Booking Page (customer selects a hairdresser)
     *
     */
    public List<Hairdresser> listAllHairdressers() {
        return hairdresserDAO.listAllHairdressers(); // DAO method expected
    }
    // END LIST ALL HAIRDRESSERS

    /* GET HAIRDRESSER BY HAIRDRESSER_ID
     * - FR-H-02 View/Edit Hairdresser profile (view part)
     * - WF-H1 Hairdresser Dashboard (load profile info)
     * - WF-A3 Admin Manage Employees (admin may view profile)
     *
     */
    public Hairdresser getHairdresserById(int hairdresserId) {
        if (hairdresserId <= 0)
            throw new ServiceException("Invalid hairdresserId.");

        return hairdresserDAO.getHairdresserById(hairdresserId); // DAO method expected
    }
    // END GET HAIRDRESSER BY HAIRDRESSER_ID

    /* GET HAIRDRESSER BY USER_ID (post-login profile lookup)
     * - FR-H-02 View/Edit Hairdresser profile (view part)
     * - WF-S1 User Login (after authentication, load role-specific profile)
     * - WF-H1 Hairdresser Dashboard (requires hairdresserId/profile)
     *
     */
    public Hairdresser getHairdresserByUserId(int userId) {
        if (userId <= 0)
            throw new ServiceException("Invalid userId.");

        return hairdresserDAO.getHairdresserByUserId(userId); // DAO method expected
    }
    // END GET HAIRDRESSER BY USER_ID

    /* UPDATE HAIRDRESSER PROFILE
     * - FR-H-02 View/Edit Hairdresser profile (edit part)
     * - WF-H3 Hairdresser Updates Profile (or profile edit screen)
     *
     */
    public void updateHairdresserProfile(Hairdresser hairdresser) {
        Objects.requireNonNull(hairdresser, "hairdresser");

        if (hairdresser.getHairdresserId() <= 0)
            throw new ServiceException("Invalid hairdresserId.");
        if (isBlank(hairdresser.getFirstName()))
            throw new ServiceException("First name is required.");
        if (isBlank(hairdresser.getLastName()))
            throw new ServiceException("Last name is required.");

        // DAO call to persist profile edits
        hairdresserDAO.updateHairdresser(hairdresser); // DAO method expected
    }
    // END UPDATE HAIRDRESSER PROFILE

    /* GET DAILY APPOINTMENTS (Hairdresser schedule view)
     * - FR-H-03 View appointment schedule by date and time
     * - WF-H2 Hairdresser Views Schedule
     * - WF-H1 Hairdresser Dashboard (today’s appointments summary)
     *
     */
    public List<Appointment> getDailyAppointments(int hairdresserId, LocalDate date) {
        if (hairdresserId <= 0)
            throw new ServiceException("Invalid hairdresserId.");

        Objects.requireNonNull(date, "date");

        return appointmentDAO.listByHairdresserAndDate(hairdresserId, date);
    }

    /* GET CUSTOMER PROFILE (Hairdresser view)
     * - FR-H-04 View a Single Customer’s Profile
     * - WF-H2 Hairdresser Views Schedule (select appointment -> view customer details)
     *
     */
    public Customer getCustomerProfile(int customerId) {
        if (customerId <= 0) throw new ServiceException("Invalid customerId.");
        return customerDAO.getCustomerById(customerId); // DAO method expected
    }

    /* AUTHORIZATION GUARD: ACTIVE HAIRDRESSER CHECK
     * Confirms that a given userId belongs to an ACTIVE HAIRDRESSER account.
     * Servlets will manage sessions; this method validates business authorization.
     */
    public void assertActiveHairdresserUser(int userId) {
        if (userId <= 0) throw new ServiceException("Invalid userId.");

        User u = userDAO.findById(userId);

        if (u == null)
            throw new ServiceException("User not found.");
        if (!u.isActive())
            throw new ServiceException("User inactive.");
        if (!"HAIRDRESSER".equalsIgnoreCase(u.getRole()))
            throw new ServiceException("User is not a HAIRDRESSER.");
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
