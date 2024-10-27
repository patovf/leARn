package main.java.com.leARn.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// TODO: password en archivo .env
public class Database {
    public static Connection connectDB() throws SQLException, ClassNotFoundException{
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/learn", "admin", "Abbondanzieri2003");

            return connect;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
