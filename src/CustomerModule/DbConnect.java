package CustomerModule;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnect {

    public static Connection GetConnection()

    {
        Connection con=null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con= DriverManager.getConnection("jdbc:mysql://localhost:3306/travelexperts","root","");

        }
        catch (ClassNotFoundException | SQLException e)
        {
            e.printStackTrace();
            System.exit(0);
        }

        return con;
    }
}
