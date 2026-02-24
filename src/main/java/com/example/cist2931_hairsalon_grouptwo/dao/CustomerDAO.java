package com.chattech.hairsalon.hair_salon.dao;

import com.chattech.hairsalon.hair_salon.model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDAO {

    public Customer getCustomerById(int id) {
        String sql = "SELECT * FROM [Customer] WHERE customer_id=?";
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

    public Customer getCustomerByUserId(int userId) {
        String sql = "SELECT * FROM [Customer] WHERE user_id=?";
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

    public int createCustomer(Customer c) {
        String sql = "INSERT INTO [Customer] (user_id, first_name, last_name, phone) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, c.getUserId());
            ps.setString(2, c.getFirstName());
            ps.setString(3, c.getLastName());
            ps.setString(4, c.getPhone());
            ps.executeUpdate();

            ResultSet rs = conn.createStatement().executeQuery("SELECT @@IDENTITY");
            if (rs.next()) return rs.getInt(1);
            return -1;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateCustomer(Customer c) {
        String sql = "UPDATE [Customer] SET first_name=?, last_name=?, phone=? WHERE customer_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, c.getFirstName());
            ps.setString(2, c.getLastName());
            ps.setString(3, c.getPhone());
            ps.setInt(4, c.getCustomerId());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Customer mapRow(ResultSet rs) throws SQLException {
        Customer c = new Customer();
        c.setCustomerId(rs.getInt("customer_id"));
        c.setUserId(rs.getInt("user_id"));
        c.setFirstName(rs.getString("first_name"));
        c.setLastName(rs.getString("last_name"));
        c.setPhone(rs.getString("phone"));
        return c;
    }
}
