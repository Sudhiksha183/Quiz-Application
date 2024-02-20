package com.example.quizapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    Connection conn = null;

    public static Connection ConnectDb() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/quizdatabase", "root", "Sujatha@1");
            // System.out.println("Connection established successfully");
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;

        }
    }

}
