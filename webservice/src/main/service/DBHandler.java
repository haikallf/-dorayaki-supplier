package main.service;

import java.sql.*;

public class DBHandler {
    private Connection conn;
    public DBHandler() {
        try {
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/blabla", "user", "pass");
            System.out.println("Database connected.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Connection getConnection() {return this.conn;}
}
