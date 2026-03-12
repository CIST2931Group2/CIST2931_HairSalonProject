package com.example.cist2931_hairsalon_grouptwo.dao;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private DBConnection() {}

        public static Connection getConnection() throws SQLException {
            try {
                Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            File dbFile = new File("HairSalonDB_seed.accdb");
            try {
                // Dynamically find the project root from the location of this class
                // (Assuming the class is in target/classes or WEB-INF/classes)
                URL classesUrl = DBConnection.class.getProtectionDomain().getCodeSource().getLocation();
                File classesDir = new File(classesUrl.toURI());
                File projectRoot = classesDir.getParentFile().getParentFile();
                File dynamicDbFile = new File(projectRoot, "HairSalonDB_seed.accdb");
                if (dynamicDbFile.exists()) {
                    dbFile = dynamicDbFile;
                }
            } catch (Exception e) {
                System.err.println("DBConnection warning: Could not dynamically resolve project root path.");
            }

            System.out.println("=== DBConnection: Using database path -> " + dbFile.getAbsolutePath() + " ===");
            
            String url = "jdbc:ucanaccess://" + dbFile.getAbsolutePath();
            return DriverManager.getConnection(url);
        }
}


