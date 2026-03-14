package com.example.cist2931_hairsalon_grouptwo.dao;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String MDB_PATH = System.getenv("MDB_PATH");

    private DBConnection() {}

        public static Connection getConnection() throws SQLException {
            if (MDB_PATH == null || MDB_PATH.isBlank()) {
                throw new RuntimeException("Environment variable MDB_PATH is not set.");
            }

            try {
                Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            System.out.println("DBConnection: Using database path -> " + MDB_PATH);

            Path dbFile = Paths.get(MDB_PATH);
            String url = "jdbc:ucanaccess://" + dbFile;
            return DriverManager.getConnection(url);
        }
}


