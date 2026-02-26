package com.chattech.hairsalon.hair_salon.test;

/* ServiceTestRunner
 * Service Tested
 * - AuthService
 * - AppointmentService
 * - ScheduleService
 *
 * Check test results in the doc - Testing Report – Services Basic
 * Completed by Date: 02/21/2026
 *
 * Author: Maria Ravid
 */

import com.chattech.hairsalon.hair_salon.dao.*;
import com.chattech.hairsalon.hair_salon.model.Appointment;
import com.chattech.hairsalon.hair_salon.model.Schedule;
import com.chattech.hairsalon.hair_salon.model.User;
import com.chattech.hairsalon.hair_salon.service.AppointmentService;
import com.chattech.hairsalon.hair_salon.service.AuthService;
import com.chattech.hairsalon.hair_salon.service.ScheduleService;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ServiceTestRunner {

    // ---- Services under test ----
    private final AuthService authService;
    private final AppointmentService appointmentService;
    private final ScheduleService scheduleService;

    // ---- DAOs (sometimes needed for verification reads) ----
    private final UserDAO userDAO;
    private final CustomerDAO customerDAO;
    private final HairdresserDAO hairdresserDAO;
    private final ScheduleDAO scheduleDAO;
    private final ScheduleBlockDAO scheduleBlockDAO;
    private final AppointmentDAO appointmentDAO;

    public ServiceTestRunner() {
        // DAOs
        this.userDAO = new UserDAO();
        this.customerDAO = new CustomerDAO();
        this.hairdresserDAO = new HairdresserDAO();
        this.scheduleDAO = new ScheduleDAO();
        this.scheduleBlockDAO = new ScheduleBlockDAO();
        this.appointmentDAO = new AppointmentDAO();

        // Services (constructor injection)
        this.authService = new AuthService(userDAO, customerDAO);

        // Based on what you provided:
        // new AppointmentService(new AppointmentDAO(), new ScheduleDAO(), new ScheduleBlockDAO(), new HairdresserDAO(), new UserDAO());
        this.appointmentService = new AppointmentService(
                appointmentDAO,
                scheduleDAO,
                scheduleBlockDAO,
                hairdresserDAO,
                userDAO
        );

        this.scheduleService = new ScheduleService(scheduleDAO, scheduleBlockDAO);
    }

    public static void main(String[] args) {
        ServiceTestRunner runner = new ServiceTestRunner();
        runner.runAll();
    }

    public void runAll() {
        System.out.println("==================================================");
        System.out.println("Hair Salon - Week 6 Service Testing");
        System.out.println("==================================================");

        // Customer.customer_id from DB seed
        // Hairdresser.hairdresser_id from DB seed
        int existingCustomerId = 1;
        int existingHairdresserId = 1;

        // 1) Login test (uses seed user)
        testLoginSuccess("SamLow@gmail.com", "SamLow123");
        testLoginFailWrongPassword("SamLow@gmail.com", "WRONGPASS");

        // 2) Schedule creation + block insertion (Admin-like actions)
        // Use a future weekStartDate so it doesn't collide with existing schedules.
        LocalDate nextMonday = LocalDate.now().with(DayOfWeek.MONDAY).plusWeeks(2);
        int scheduleId = testCreateWeeklySchedule(existingHairdresserId, nextMonday);

        // Add a block for that schedule (MON 10:00-16:00)
        if (scheduleId > 0) {
            testAddScheduleBlock(scheduleId, "MON", LocalTime.of(10, 0), LocalTime.of(16, 0));
        }

        // 3) Appointment booking tests
        if (scheduleId > 0) {
            // Choose a time that falls within the MON block.
            LocalDateTime start = nextMonday.atTime(11, 0);
            LocalDateTime end = nextMonday.atTime(12, 0);

            int apptId = testBookAppointmentSuccess(existingCustomerId, existingHairdresserId, start, end, "Haircut");

            // Overlap: 11:30-12:30 overlaps with 11:00-12:00
            testBookAppointmentOverlapFail(existingCustomerId, existingHairdresserId,
                    nextMonday.atTime(11, 30),
                    nextMonday.atTime(12, 30),
                    "Haircut");

            // Past booking should fail (FR-C-05)
            testBookAppointmentPastFail(existingCustomerId, existingHairdresserId,
                    LocalDateTime.now().minusDays(1),
                    LocalDateTime.now().minusDays(1).plusHours(1),
                    "Haircut");

            // check customer appointments list
            testGetCustomerAppointments(existingCustomerId);
        }

        System.out.println("==================================================");
        System.out.println("DONE");
        System.out.println("==================================================");
    }

    // -------------------------
    // AUTH SERVICE TESTS
    // -------------------------

    private void testLoginSuccess(String email, String passwordHash) {
        runTest("AuthService.login SUCCESS (" + email + ")",
                () -> {
            User u = authService.login(email, passwordHash);
            if (u == null) throw
                    new RuntimeException("Expected non-null User.");
            System.out.println("   Logged in userId=" + u.getUserId() + ", role=" + u.getRole());
        });
    }

    private void testLoginFailWrongPassword(String email, String wrongPass) {
        runTest("AuthService.login FAIL wrong password (" + email + ")",
                () -> {
            try {
                authService.login(email, wrongPass);
                throw new RuntimeException("Expected exception but login succeeded.");
            } catch (RuntimeException ex) {
                System.out.println("   Expected failure: " + ex.getMessage());
            }
        });
    }

    // -------------------------
    // SCHEDULE SERVICE TESTS
    // -------------------------

    private int testCreateWeeklySchedule(int hairdresserId, LocalDate weekStart) {
        final int[] out = {-1}; // out - container that holds the scheduleId, with safe default value -1

        runTest("ScheduleService.createWeeklySchedule hairdresserId=" + hairdresserId + " weekStart=" + weekStart,
                () -> {
            int newScheduleId = scheduleService.createWeeklySchedule(hairdresserId, weekStart);
            if (newScheduleId <= 0) throw
                    new RuntimeException("Expected positive scheduleId, got " + newScheduleId);
            out[0] = newScheduleId;
            System.out.println("    Created scheduleId=" + newScheduleId);

            // Verify read-back via DAO
            Schedule s = scheduleDAO.getScheduleByWeek(hairdresserId, weekStart);
            if (s == null) throw
                    new RuntimeException("Schedule not found after creation.");
            System.out.println("   Verified schedule exists, isActive=" + s.isActive());
        });

        return out[0];
    }

    private void testAddScheduleBlock(int scheduleId, String day, LocalTime start, LocalTime end) {
        runTest("ScheduleService.addScheduleBlock scheduleId=" + scheduleId + " " + day + " " + start + "-" + end,
                () -> {
            int blockId = scheduleService.addScheduleBlock(scheduleId, day, start, end);
            if (blockId <= 0) throw
                    new RuntimeException("Expected positive blockId, got " + blockId);
            System.out.println("   Added blockId=" + blockId);

            // Verify blocks list
            var blocks = scheduleService.getBlocks(scheduleId);
            System.out.println("   Blocks count now=" + blocks.size());
        });
    }

    // -------------------------
    // APPOINTMENT SERVICE TESTS
    // -------------------------

    private int testBookAppointmentSuccess(int customerId, int hairdresserId,
                                           LocalDateTime start, LocalDateTime end,
                                           String serviceType) {
        final int[] out = {-1};

        runTest("AppointmentService.bookAppointment SUCCESS " + start + " to " + end,
                () -> {
            int apptId = appointmentService.bookAppointment(customerId, hairdresserId, start, end, serviceType);
            if (apptId <= 0) throw
                    new RuntimeException("Expected positive appointmentId, got " + apptId);
            out[0] = apptId;
            System.out.println("   Created appointmentId=" + apptId);

            // Verify read-back via DAO
            Appointment a = appointmentDAO.getAppointmentById(apptId);
            if (a == null) throw
                    new RuntimeException("Appointment not found after creation.");
            System.out.println("   Verified appointment status=" + a.getStatus());
        });

        return out[0];
    }

    private void testBookAppointmentOverlapFail(int customerId, int hairdresserId,
                                                LocalDateTime start, LocalDateTime end,
                                                String serviceType) {
        runTest("AppointmentService.bookAppointment FAIL overlap " + start + " to " + end, () -> {
            try {
                appointmentService.bookAppointment(customerId, hairdresserId, start, end, serviceType);
                throw
                        new RuntimeException("Expected overlap exception but booking succeeded.");
            } catch (RuntimeException ex) {
                System.out.println("   Expected failure: " + ex.getMessage());
            }
        });
    }

    private void testBookAppointmentPastFail(int customerId, int hairdresserId,
                                             LocalDateTime start, LocalDateTime end,
                                             String serviceType) {
        runTest("AppointmentService.bookAppointment FAIL booking in past (FR-C-05)", () -> {
            try {
                appointmentService.bookAppointment(customerId, hairdresserId, start, end, serviceType);
                throw
                        new RuntimeException("Expected 'past' exception but booking succeeded.");
            } catch (RuntimeException ex) {
                System.out.println("   Expected failure: " + ex.getMessage());
            }
        });
    }

    private void testGetCustomerAppointments(int customerId) {
        runTest("AppointmentService.getCustomerAppointments customerId=" + customerId, () -> {
            var list = appointmentService.getCustomerAppointments(customerId);
            System.out.println("   Appointments found=" + list.size());
            // Print first few
            for (int i = 0; i < Math.min(3, list.size()); i++) {
                Appointment a = list.get(i);
                System.out.println("   - " + a.getStartDateTime() + " " + a.getServiceType() + " " + a.getStatus());
            }
        });
    }

    // -------------------------
    // Test helper format - runTest("Test name", () -> { ... });
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
}
