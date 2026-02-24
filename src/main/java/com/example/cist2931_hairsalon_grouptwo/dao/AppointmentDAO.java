package com.example.cist2931_hairsalon_grouptwo.dao;

import com.example.cist2931_hairsalon_grouptwo.model.Appointment;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAO {

    public Appointment getAppointmentById(int id) {
        String sql = "SELECT * FROM [Appointment] WHERE appointment_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) return mapRow(rs);
            return null;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int createAppointment(Appointment a) {
        String sql = "INSERT INTO [Appointment] (customer_id, hairdresser_id, service_type, start_datetime, end_datetime, status, created_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, a.getCustomerId());
            ps.setInt(2, a.getHairdresserId());
            ps.setString(3, a.getServiceType());
            ps.setTimestamp(4, Timestamp.valueOf(a.getStartDateTime()));
            ps.setTimestamp(5, Timestamp.valueOf(a.getEndDateTime()));
            ps.setString(6, a.getStatus());

            LocalDateTime createdAt = (a.getCreatedAt() != null) ? a.getCreatedAt() : LocalDateTime.now();
            ps.setTimestamp(7, Timestamp.valueOf(createdAt));

            ps.executeUpdate();

            ResultSet rs = conn.createStatement().executeQuery("SELECT @@IDENTITY");
            if (rs.next()) return rs.getInt(1);
            return -1;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Appointment> listByCustomer(int customerId) {
        List<Appointment> list = new ArrayList<>();
        String sql = "SELECT * FROM [Appointment] WHERE customer_id=? ORDER BY start_datetime";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) list.add(mapRow(rs));
            return list;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Appointment> listByHairdresserAndDate(int hairdresserId, LocalDate date) {
        List<Appointment> list = new ArrayList<>();

        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.plusDays(1).atStartOfDay();

        String sql = "SELECT * FROM [Appointment] WHERE hairdresser_id=? AND start_datetime >= ? AND start_datetime < ? ORDER BY start_datetime";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, hairdresserId);
            ps.setTimestamp(2, Timestamp.valueOf(start));
            ps.setTimestamp(3, Timestamp.valueOf(end));

            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapRow(rs));

            return list;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Appointment mapRow(ResultSet rs) throws SQLException {
        Appointment a = new Appointment();
        a.setAppointmentId(rs.getInt("appointment_id"));
        a.setCustomerId(rs.getInt("customer_id"));
        a.setHairdresserId(rs.getInt("hairdresser_id"));
        a.setServiceType(rs.getString("service_type"));

        Timestamp st = rs.getTimestamp("start_datetime");
        Timestamp et = rs.getTimestamp("end_datetime");
        Timestamp ct = rs.getTimestamp("created_at");

        if (st != null) a.setStartDateTime(st.toLocalDateTime());
        if (et != null) a.setEndDateTime(et.toLocalDateTime());
        if (ct != null) a.setCreatedAt(ct.toLocalDateTime());

        a.setStatus(rs.getString("status"));
        return a;
    }
}

