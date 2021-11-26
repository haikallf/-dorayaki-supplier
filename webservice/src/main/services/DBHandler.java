package main.services;

import java.sql.*;

public class DBHandler {
    private Connection conn;
    private String url = "jdbc:mysql://ufkyenpyzvbthpgf:5btw1OE7XAz1zbSEcQKe@bhavz3m9joyfao3xpk4a-mysql.services.clever-cloud.com:3306/bhavz3m9joyfao3xpk4a";
    private String user = "user";
    private String pass = "pass";
    public DBHandler(String accessing) {
        try {
            System.out.println(accessing + " is connecting to the database.");
            conn = DriverManager.getConnection(url, user, pass);
            System.out.println("Database connected.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Connection getConnection() {return this.conn;}
}
