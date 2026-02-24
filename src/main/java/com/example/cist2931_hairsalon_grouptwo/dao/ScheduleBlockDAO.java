package com.example.cist2931_hairsalon_grouptwo.dao;

import com.example.cist2931_hairsalon_grouptwo.model.ScheduleBlock;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ScheduleBlockDAO {

    public int addBlock(ScheduleBlock b) {
        String sql = "INSERT INTO [ScheduleBlock] (schedule_id, day_of_week, start_time, end_time) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, b.getScheduleId());
            ps.setString(2, b.getDayOfWeek());
            ps.setTime(3, Time.valueOf(b.getStartTime()));
            ps.setTime(4, Time.valueOf(b.getEndTime()));
            ps.executeUpdate();

            ResultSet rs = conn.createStatement().executeQuery("SELECT @@IDENTITY");
            if (rs.next()) return rs.getInt(1);
            return -1;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ScheduleBlock> listBlocksBySchedule(int scheduleId) {
        List<ScheduleBlock> list = new ArrayList<>();
        String sql = "SELECT * FROM [ScheduleBlock] WHERE schedule_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, scheduleId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ScheduleBlock b = new ScheduleBlock();
                b.setScheduleBlockId(rs.getInt("schedule_block_id"));
                b.setScheduleId(rs.getInt("schedule_id"));
                b.setDayOfWeek(rs.getString("day_of_week"));

                Time st = rs.getTime("start_time");
                Time et = rs.getTime("end_time");
                if (st != null) b.setStartTime(st.toLocalTime());
                if (et != null) b.setEndTime(et.toLocalTime());

                list.add(b);
            }
            return list;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

