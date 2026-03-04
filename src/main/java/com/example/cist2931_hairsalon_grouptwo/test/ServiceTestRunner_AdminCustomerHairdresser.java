package com.example.cist2931_hairsalon_grouptwo.test;

/* ServiceTestRunner_AdminCustomer_Hairdresser
 * Service Tested
 * - AdminService
 * - CustomerService
 * - HairdresserService
 *
 * Check test results in the doc - Testing Report – Services Extended
 * Completed by Date: 02/24/2026
 *
 * Author: Maria Ravid
 */

import com.example.cist2931_hairsalon_grouptwo.dao.*;
import com.example.cist2931_hairsalon_grouptwo.model.*;
import com.example.cist2931_hairsalon_grouptwo.service.*;

import java.time.LocalDate;
import java.util.List;

public class ServiceTestRunner_AdminCustomerHairdresser {

    // --- DAOs (for setup + read-back verification) ---
    private final UserDAO userDAO = new UserDAO();
    private final CustomerDAO customerDAO = new CustomerDAO();
    private final HairdresserDAO hairdresserDAO = new HairdresserDAO();
    private final AppointmentDAO appointmentDAO = new AppointmentDAO();
    private final ScheduleDAO scheduleDAO = new ScheduleDAO();
    private final ScheduleBlockDAO scheduleBlockDAO = new ScheduleBlockDAO();

    // --- Services under test ---

    // CustomerService
    private final CustomerService customerService = new CustomerService(customerDAO, userDAO);

    // HairdresserService
    private final HairdresserService hairdresserService =
            new HairdresserService(hairdresserDAO, appointmentDAO, customerDAO, userDAO);

    // ScheduleService (needed for AdminService)
    private final ScheduleService scheduleService = new ScheduleService(
            scheduleDAO,
            scheduleBlockDAO
    );

    // AdminService
    private final AdminService adminService = new AdminService(
            userDAO,
            hairdresserDAO,
            scheduleDAO,
            scheduleBlockDAO,
            scheduleService
    );

    public static void main(String[] args) {
        new ServiceTestRunner_AdminCustomerHairdresser().runAll();
    }

    private void runAll() {
        System.out.println("==================================================");
        System.out.println("=== Service Tests: CustomerService / HairdresserService / AdminService ===");
        System.out.println("==================================================");

        // Update these to match your seeded DB IDs
        int seededCustomerUserId = 1; // user_id = 1 --> role = CUSTOMER
        int seededHairdresserId = 1; // hairdresser_id = 1 --> user_id = 4
        int seededHairdresserUserId = 4;   // User.user_id = 4 --> role = HAIRDRESSER
        int seededAdminUserId = 10;         // User.user_id = 10 --> role = ADMIN

        // CUSTOMER
        testGetCustomerByUserId(seededCustomerUserId);
        testUpdateCustomerProfile(seededCustomerUserId);

        // HAIRDRESSER
        testListAllHairdressers();
        testGetHairdresserByUserId(seededHairdresserUserId);
        testGetDailyAppointments(
                seededHairdresserId,
                LocalDate.of(2026, 2, 12)  // <-- use real date from your DB
        );

        // ADMIN
        testDeactivateHairdresser(seededHairdresserId);

        System.out.println("==================================================");
        System.out.println("DONE");
        System.out.println("==================================================");
    } //END runAll

    // -------------------------
    // CUSTOMER SERVICE TESTS
    // -------------------------
    private void testGetCustomerByUserId(int userId) {
        runTest("CustomerService.getCustomerByUserId userId=" + userId, () -> {
            Customer c = customerService.getCustomerByUserId(userId);
            if (c == null) throw new RuntimeException("Expected Customer, got null.");
            System.out.println("   customerId=" + c.getCustomerId() + ", name=" + c.getFirstName() + " " + c.getLastName());
        });
    }

    private void testUpdateCustomerProfile(int userId) {
        runTest("CustomerService.updateCustomerProfile userId=" + userId, () -> {
            Customer c = customerService.getCustomerByUserId(userId);
            if (c == null) throw new RuntimeException("Customer not found for userId=" + userId);

            String originalPhone = c.getPhone();

            // Modify one field (basic testing)
            String newPhone = "999-999-9999";
            if (newPhone.equals(originalPhone)) newPhone = "888-888-8888";

            // WRITE
            c.setPhone(newPhone);
            customerService.updateCustomerProfile(c);

            // Read-back verification using DAO
            Customer c2 = customerDAO.getCustomerById(c.getCustomerId());
            if (c2 == null) throw new RuntimeException("Read-back failed.");
            if (!newPhone.equals(c2.getPhone()))
                throw new RuntimeException("Phone not updated. Expected=" + newPhone + ", got=" + c2.getPhone());

            System.out.println("   Original phone: " + originalPhone);
            System.out.println("   Updated phone verified: " + c2.getPhone());
        });
    }

    // -------------------------
    // HAIRDRESSER SERVICE TESTS
    // -------------------------
    private void testListAllHairdressers() {
        runTest("HairdresserService.listAllHairdressers  (ACTIVE only)", () -> {

            List<Hairdresser> list = hairdresserService.listAllHairdressers();
            if (list == null || list.isEmpty()) throw new RuntimeException("Expected non-empty list.");

            int activeCount = 0;
            System.out.println("   Active Hairdressers:");

            for (Hairdresser h : list) {
                User u = userDAO.findById(h.getUserId());
                if (u == null) throw new RuntimeException("User not found for hairdresserId=" + h.getHairdresserId());
                if (u.isActive()) {
                    activeCount++;
                    System.out.println("      - " + h.getFirstName() + " " + h.getLastName());
                }
            }
            if (activeCount == 0) throw new RuntimeException("No active hairdressers found.");
            System.out.println("   Active count=" + activeCount);
        });
    }

    private void testGetHairdresserByUserId(int userId) {
        runTest("HairdresserService.getHairdresserByUserId userId=" + userId, () -> {
            Hairdresser h = hairdresserService.getHairdresserByUserId(userId);
            if (h == null) throw new RuntimeException("Expected Hairdresser, got null.");
            System.out.println("   hairdresserId=" + h.getHairdresserId() + ", name=" + h.getFirstName() + " " + h.getLastName());
        });
    }

    private void testGetDailyAppointments(int hairdresserId, LocalDate date) {
        runTest("HairdresserService.getDailyAppointments hairdresserId=" + hairdresserId + " date=" + date, () -> {

            List<Appointment> list = hairdresserService.getDailyAppointments(hairdresserId, date);
            if (list == null) throw new RuntimeException("Expected list, got null.");
            if (list.isEmpty())
                throw new RuntimeException("Expected appointments for seeded date " + date + " but found none.");

            System.out.println("   Appointments found=" + list.size());
            for (Appointment a : list) {
                System.out.println("      - ApptId=" + a.getAppointmentId()
                        + ", CustomerId=" + a.getCustomerId()
                        + ", Start=" + a.getStartDateTime()
                        + ", Status=" + a.getStatus());
            }
        });
    }

    // -------------------------
    // ADMIN SERVICE TESTS
    // -------------------------
    private void testDeactivateHairdresser(int hairdresserId) {
        runTest("AdminService.deactivateHairdresser hairdresserId=" + hairdresserId, () -> {
            Hairdresser h = hairdresserDAO.getHairdresserById(hairdresserId);
            if (h == null) throw new RuntimeException("Hairdresser not found.");

            User uBefore = userDAO.findById(h.getUserId());
            if (uBefore == null) throw new RuntimeException("User not found for hairdresser.");
            System.out.println("   Before isActive=" + uBefore.isActive());

            adminService.deactivateHairdresser(hairdresserId);

            User uAfter = userDAO.findById(h.getUserId());
            if (uAfter == null) throw new RuntimeException("User not found after deactivate.");
            if (uAfter.isActive()) throw new RuntimeException("Expected inactive user after deactivation.");
            System.out.println("   After isActive=" + uAfter.isActive());
        });
    }

    // -------------------------
    // Test helper
    // -------------------------
    private void runTest(String name, Runnable test) {
        System.out.println("\nTEST: " + name);
        try {
            test.run();
            System.out.println("   RESULT: PASS");
        } catch (Throwable t) {
            System.out.println("   RESULT: FAIL");
            System.out.println("   ERROR : " + t.getMessage());
        }
    }

} // END Tester