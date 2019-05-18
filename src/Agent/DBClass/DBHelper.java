package TravelExperts.Agent.DBClass;

import javax.swing.*;
import java.sql.*;

public class DBHelper {

    //connect to database and returns connection object

    private  static DBHelper dbHelper;

    private  final String DB_URL = "jdbc:mysql://localhost:3306/travelexperts";

    private  Connection con = null;
    private  Statement stmt = null;

    private DBHelper()  {
        createConnection();
    }

    public static DBHelper getInstance() {

        try {
                    if (dbHelper == null) {
                        dbHelper = new DBHelper();
                    }
        } finally {}

        return dbHelper;
    }

    void createConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection(DB_URL,"root","");
        } catch (ClassNotFoundException  e) {
            e.printStackTrace();
            System.exit(0);
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
    }

    public ResultSet executeQuery (String query)  {
        ResultSet resultSet;
        createConnection();
        try{
            stmt = con.createStatement();
            resultSet = stmt.executeQuery(query);
        }
        catch(SQLException ex){
            System.out.println("Execution at executeQuery:DataHandler" + ex.getLocalizedMessage());
            return null;
        }


        return resultSet;
    }

    public boolean execNonQuery(String nonquery) throws SQLException{
        boolean result = true;
        try   {
            stmt = con.createStatement();
            result = stmt.execute(nonquery);
            return result;

        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null,"Errror:" + ex.getMessage(),"Error Occured" ,JOptionPane.ERROR_MESSAGE);
            System.out.println("Execution at executeQuery:DataHandler" + ex.getLocalizedMessage());
            return result;

        }


    }








}//class


 /*   private static DBHelper dbHelper;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/travelexperts";

    private static Connection con = null;
    private static Statement stmt = null;

    private DBHelper()  {
        createConnection();
    }

    public static DBHelper getInstance() {

        try {
            if (dbHelper == null) {
                dbHelper = new DBHelper();
            }

        } finally {}

        return dbHelper;
    }

    void createConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection(DB_URL);
        } catch (ClassNotFoundException  e) {
            e.printStackTrace();
            System.exit(0);
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
    }

    public ResultSet executeQuery (String query)  {
        ResultSet resultSet;

        try{
            stmt = con.createStatement();
            resultSet = stmt.executeQuery(query);
        }
        catch(SQLException ex){
            System.out.println("Execution at executeQuery:DataHandler" + ex.getLocalizedMessage());
            return null;
        }


        return resultSet;
    }

    public boolean execNonQuery(String nonquery) throws SQLException{
        boolean result = true;
        try   {
            stmt = con.createStatement();
            result = stmt.execute(nonquery);
            return result;

        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null,"Errror:" + ex.getMessage(),"Error Occured" ,JOptionPane.ERROR_MESSAGE);
            System.out.println("Execution at executeQuery:DataHandler" + ex.getLocalizedMessage());
            return result;

        }


    }
*/




