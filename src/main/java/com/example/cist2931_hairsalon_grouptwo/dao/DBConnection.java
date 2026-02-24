package com.example.cist2931_hairsalon_grouptwo.dao;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String MDB_PATH = "C:\\Users\\Ravid\\Desktop\\School\\Spring 2026\\Systems Project\\Hair_Salon\\HairSalonDB_seed.accdb";

    private DBConnection() {}

        public static Connection getConnection() throws SQLException {
            try {
                Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            Path dbFile = Paths.get(MDB_PATH);
            String url = "jdbc:ucanaccess://" + dbFile;
            return DriverManager.getConnection(url);
        }
}

