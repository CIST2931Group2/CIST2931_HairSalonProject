package com.example.cist2931_hairsalon_grouptwo.dao;

import com.example.cist2931_hairsalon_grouptwo.model.User;

import java.sql.*;
import java.time.LocalDateTime;

public class UserDAO {

    public User findById(int userId) {
        String sql = "SELECT * FROM [User] WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) return mapRow(rs);
            return null;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User findByEmail(String email) {
        String sql = "SELECT * FROM [User] WHERE email = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) return mapRow(rs);
            return null;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int createUser(User u) {
        String sql = "INSERT INTO [User] (email, password_hash, role, is_active, created_at) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, u.getEmail());
            ps.setString(2, u.getPasswordHash());
            ps.setString(3, u.getRole());
            ps.setBoolean(4, u.isActive());

            LocalDateTime createdAt = (u.getCreatedAt() != null) ? u.getCreatedAt() : LocalDateTime.now();
            ps.setTimestamp(5, Timestamp.valueOf(createdAt));

            ps.executeUpdate();

            ResultSet rs = conn.createStatement().executeQuery("SELECT @@IDENTITY");
            if (rs.next()) return rs.getInt(1);
            return -1;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setActive(int userId, boolean active) {
        String sql = "UPDATE [User] SET is_active=? WHERE user_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setBoolean(1, active);
            ps.setInt(2, userId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private User mapRow(ResultSet rs) throws SQLException {
        User u = new User();
        u.setUserId(rs.getInt("user_id"));
        u.setEmail(rs.getString("email"));
        u.setPasswordHash(rs.getString("password_hash"));
        u.setRole(rs.getString("role"));
        u.setActive(rs.getBoolean("is_active"));

        Timestamp ts = rs.getTimestamp("created_at");
        if (ts != null) u.setCreatedAt(ts.toLocalDateTime());

        return u;
    }
}

