/*
Author: Mahda Kazemian
Date: May 19,2019
Course Module : PROJ-207-OSD - Threaded Project
Assignment : Team1- Workshop6
purpose: DBHelper class to connect to the database and return a connection object
 */
package Booking;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBHelper {


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
