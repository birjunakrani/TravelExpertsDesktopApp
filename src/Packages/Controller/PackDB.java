package Packages.Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PackDB {

    public static Connection getConnection() {
        Connection conn = null;
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/travelexperts", "root", "");
        }
        catch (ClassNotFoundException | SQLException e)
        {
            e.printStackTrace();
        }
        return conn;
    }



}
