package com.chattech.hairsalon.hair_salon.dao;

import com.chattech.hairsalon.hair_salon.model.Hairdresser;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HairdresserDAO {

    public Hairdresser getHairdresserById(int id) {
        String sql = "SELECT * FROM [Hairdresser] WHERE hairdresser_id=?";
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

    public Hairdresser getHairdresserByUserId(int userId) {
        String sql = "SELECT * FROM [Hairdresser] WHERE user_id=?";
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

    public List<Hairdresser> listAllHairdressers() {
        List<Hairdresser> list = new ArrayList<>();
        String sql = "SELECT * FROM [Hairdresser]";
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) list.add(mapRow(rs));
            return list;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int createHairdresser(Hairdresser h) {
        String sql = "INSERT INTO [Hairdresser] (user_id, first_name, last_name, phone, specialties, bio) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, h.getUserId());
            ps.setString(2, h.getFirstName());
            ps.setString(3, h.getLastName());
            ps.setString(4, h.getPhone());
            ps.setString(5, h.getSpecialties());
            ps.setString(6, h.getBio());
            ps.executeUpdate();

            ResultSet rs = conn.createStatement().executeQuery("SELECT @@IDENTITY");
            if (rs.next()) return rs.getInt(1);
            return -1;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateHairdresser(Hairdresser h) {
        String sql = "UPDATE [Hairdresser] SET first_name=?, last_name=?, phone=?, specialties=?, bio=? WHERE hairdresser_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, h.getFirstName());
            ps.setString(2, h.getLastName());
            ps.setString(3, h.getPhone());
            ps.setString(4, h.getSpecialties());
            ps.setString(5, h.getBio());
            ps.setInt(6, h.getHairdresserId());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Hairdresser mapRow(ResultSet rs) throws SQLException {
        Hairdresser h = new Hairdresser();
        h.setHairdresserId(rs.getInt("hairdresser_id"));
        h.setUserId(rs.getInt("user_id"));
        h.setFirstName(rs.getString("first_name"));
        h.setLastName(rs.getString("last_name"));
        h.setPhone(rs.getString("phone"));
        h.setSpecialties(rs.getString("specialties"));
        h.setBio(rs.getString("bio"));
        return h;
    }
}
