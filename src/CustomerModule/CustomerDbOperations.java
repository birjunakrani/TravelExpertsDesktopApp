package CustomerModule;

import Resources.DBClass.DBHelper;
import Resources.Validator;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Resources.DBClass.*;

public class CustomerDbOperations {



    public static List<Customer> RetriveCustomerData()
{
    List<Customer> CustomertList=new ArrayList<>();
    Customer ag=null;
    Connection con= DbConnect.GetConnection();
    try {
        Statement st=con.createStatement();
        String query ="select * from Customers order by customerid desc ";
        ResultSet rs=st.executeQuery(query);

        while (rs.next())
        {
            CustomertList.add(new Customer(rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getString(7),
                    rs.getString(8),
                    rs.getString(9),
                    rs.getString(10),
                    rs.getString(11),
                    rs.getInt(12)
            ));


        }
    }
    catch (SQLException | NullPointerException e)
    {
e.printStackTrace();
    }

    return  CustomertList;
}// End of Method RetriveAllCustomers

    public static int addCustomer(Customer cust) {

        int count = 0;
        Connection con= DbConnect.GetConnection();
        PreparedStatement stmt = null;
        String query = "INSERT INTO Customers (CustFirstName,CustLastName,CustAddress," +
                "CustCity,CustProv,CustPostal,CustCountry,CustHomePhone,CustBusPhone," +
                "CustEmail,AgentId)VALUES ( ?,?,?,?,?,?,?,?,?,?,?)";


        try {
            stmt = con.prepareStatement(query);
            stmt.setString(1, cust.getCustFirstName());
            stmt.setString(2, cust.getCustLastName());
            stmt.setString(3, cust.getCustAddress());
            stmt.setString(4, cust.getCustCity());
            stmt.setString(5, cust.getCustProv());
            stmt.setString(6, cust.getCustPostal());
            stmt.setString(7, cust.getCustCountry());
            stmt.setString(8, cust.getCustHomePhone());
            stmt.setString(9, cust.getCustBusPhone());
            stmt.setString(10, cust.getCustEmail());
            stmt.setInt(11, cust.getAgentId());

            count = stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return count;
    }

    public static int  SaveChanges(Customer oldData, Customer newData){
        Connection con= DbConnect.GetConnection();
        int rowsUpdated=0;
        try {
            PreparedStatement stmt = con.prepareStatement("UPDATE `customers` SET `CustFirstName`=?,`CustLastName`=?," +
                    "`CustAddress`=?,`CustCity`=?,`CustProv`=?,`CustPostal`=?,`CustCountry`=? WHERE CustomerId=?");
            stmt.setString(1, newData.getCustFirstName());
            stmt.setString(2, newData.getCustLastName());
            stmt.setString(3, newData.getCustAddress());
            stmt.setString(4, newData.getCustCity());
            stmt.setString(5, newData.getCustProv());
            stmt.setString(6, newData.getCustPostal());
            stmt.setString(7, newData.getCustCountry());
            stmt.setInt(8, oldData.getCustomerId());
             rowsUpdated = stmt.executeUpdate();

            con.close();

        } catch (SQLException e) {

            e.printStackTrace();

        }
        return  rowsUpdated;

    }// END OF METHOD SaveChanges




    public static boolean DeleteSelectedCustomer(int custId){
        int rowsEffected=0;
        Connection con= DbConnect.GetConnection();
        try {
            Statement st=con.createStatement();
            String sqlstmt=" Delete from  Customers where CustomerId="+custId;
             rowsEffected =st.executeUpdate(sqlstmt);
            if(rowsEffected==1) {

                return true  ;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;

    }// end of delete method

}//End of controller
