package com.example.cist2931_hairsalon_grouptwo.dao;

import com.example.cist2931_hairsalon_grouptwo.model.Schedule;

import java.sql.*;
import java.time.LocalDate;

public class ScheduleDAO {

    public Schedule getScheduleByWeek(int hairdresserId, LocalDate weekStart) {
        String sql = "SELECT * FROM [Schedule] WHERE hairdresser_id=? AND week_start_date=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, hairdresserId);
            ps.setDate(2, Date.valueOf(weekStart));

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Schedule s = new Schedule();
                s.setScheduleId(rs.getInt("schedule_id"));
                s.setHairdresserId(rs.getInt("hairdresser_id"));
                Date d = rs.getDate("week_start_date");
                if (d != null) s.setWeekStartDate(d.toLocalDate());
                s.setActive(rs.getBoolean("is_active"));
                return s;
            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int createWeeklySchedule(Schedule s) {
        String sql = "INSERT INTO [Schedule] (hairdresser_id, week_start_date, is_active) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, s.getHairdresserId());
            ps.setDate(2, Date.valueOf(s.getWeekStartDate()));
            ps.setBoolean(3, s.isActive());
            ps.executeUpdate();

            ResultSet rs = conn.createStatement().executeQuery("SELECT @@IDENTITY");
            if (rs.next()) return rs.getInt(1);
            return -1;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
