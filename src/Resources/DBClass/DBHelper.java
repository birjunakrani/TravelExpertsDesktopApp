package Resources.DBClass;

import Agency.Model.Agency;
import SwitchBoard.AgentLogIn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

import Packages.Model.PackageType;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBHelper {

    //connect to database and returns connection object

    public  static DBHelper dbHelper;

    private  final String DB_URL = "jdbc:mysql://localhost:3306/travelexperts";

    private  Connection con = null;
    private  Statement stmt = null;

    public DBHelper()  {
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

   public void createConnection() {
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

    public boolean deleteAgent(TravelExperts.Agent.Model.Agent agent) {
        try {
            String query = "DELETE FROM agents where agentId = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, agent.getAgentId());
            int res = stmt.executeUpdate();
            if (res == 1) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }//deleteAgent method

    public boolean updateAgent(TravelExperts.Agent.Model.Agent agent) {
        boolean result=true;

        try {
            String query = "UPDATE agents SET agtFirstName = ?, agtMiddleInitial = ?, agtLastName = ?,agtBusPhone = ?, agtEmail = ?, agtPosition = ?, AgencyId = ? WHERE agentId = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, agent.getAgtFirstName());
            stmt.setString(2, agent.getAgtMiddleInital());
            stmt.setString(3, agent.getAgtLastName());
            stmt.setString(4, agent.getAgtBusPhone());
            stmt.setString(5, agent.getAgtEmail());
            stmt.setString(6, agent.getAgtPosition());
            stmt.setInt(7, agent.getAgencyId());
            stmt.setInt(8, agent.getAgentId());
            int res = stmt.executeUpdate();
            if (res == 1){
                result = true;
            }else result = false;

        } catch (SQLException ex) {
            Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }//updateAgent method

    public boolean deleteAgency(Agency agency) {

        try {
            String query = "DELETE FROM agencies where AgencyId = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, agency.getAgencyId());
            int res = stmt.executeUpdate();
            if (res == 0) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }//deleteAgency method

    public int checkBeforedeleteAgency(Agency agency) {

        int result = 0;
        try {
            String query = "SELECT COUNT(*) FROM agencies WHERE AgencyId in (SELECT AgencyId FROM agents WHERE AgencyId = ?)";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, agency.getAgencyId());
            result = stmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }//deleteAgency method

    public boolean updateAgency(Agency agency) {
        boolean result=true;

        try {
            String query = "UPDATE agencies SET AgncyAddress = ?, AgncyCity = ?, AgncyProv = ?,AgncyPostal = ?, AgncyCountry = ?, AgncyPhone = ?, AgncyFax = ? WHERE AgencyId = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, agency.getAgncyAddress());
            stmt.setString(2, agency.getAgncyCity());
            stmt.setString(3, agency.getAgncyProv());
            stmt.setString(4, agency.getAgncyPostal());
            stmt.setString(5, agency.getAgncyCountry());
            stmt.setString(6, agency.getAgncyPhone());
            stmt.setString(7, agency.getAgncyFax());
            stmt.setInt(8, agency.getAgencyId());
            int res = stmt.executeUpdate();
            if (res == 1){
                result = true;
            }else result = false;

        } catch (SQLException ex) {
            Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    } //udateAgency method

    public boolean AgentLogIn(AgentLogIn agentLogIn) throws SQLException {

        boolean IsLoggedIn = false;
        Resources.DBClass.DBHelper dbhelper = Resources.DBClass.DBHelper.getInstance();
        ObservableList<AgentLogIn> agentAccount = FXCollections.observableArrayList();
        String query = "SELECT Username,Password FROM Agents WHERE Username = ? and Password = ? ";
        PreparedStatement ps = con.prepareStatement(query);
        try {
            ps.setString(1,agentLogIn.getUsername());
            ps.setString(2,agentLogIn.getPassword());
            ResultSet rs = ps.executeQuery();

            if(rs.next()){ //if agent has username/password
                IsLoggedIn = true;
            } else IsLoggedIn = false;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return IsLoggedIn;
    } //agentlogin method


    public ObservableList<PieChart.Data> getAgentGraphicStats()
    {

        Resources.DBClass.DBHelper dbhelper = Resources.DBClass.DBHelper.getInstance();
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        String agentquery = "SELECT COUNT(*) FROM agents";
        String agencyquery = "SELECT COUNT(*) FROM agencies";

        ResultSet rs = executeQuery(agentquery);
        try {
            if (rs.next()){
                int count = rs.getInt(1);
                data.add(new PieChart.Data("Total Agents",count));
            }
            rs = executeQuery(agencyquery);
            if (rs.next()){
                int count = rs.getInt(1);
                data.add(new PieChart.Data("Total Agencies",count));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }

    public ObservableList<PieChart.Data> getPackageGraphicStats()
    {

        DBHelper dbhelper = DBHelper.getInstance();
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        String packagequery = "SELECT COUNT(*) FROM packages";
        String productquery = "SELECT COUNT(*) FROM products";

        ResultSet rs = executeQuery(packagequery);
        try {
            if (rs.next()){
                int count = rs.getInt(1);
                data.add(new PieChart.Data("Packages",count));
            }
            rs = executeQuery(productquery);
            if (rs.next()){
                int count = rs.getInt(1);
                data.add(new PieChart.Data("Products",count));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return data;
    }



    //Packages DB stuff
    public ArrayList<PackageType> getPackages() throws SQLException {
        ArrayList<PackageType> AllPacks = new ArrayList<>();
        String query = "SELECT * from packages";
        Statement stmt = null;
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/travelexperts", "root", "");
        stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        while(rs.next()){
            AllPacks.add(new PackageType(rs.getInt(1),rs.getString(2),
                    rs.getDate(3),rs.getDate(4),rs.getString(5),
                    rs.getBigDecimal(6),rs.getBigDecimal(7)));
        }
        return AllPacks;
    }



    public boolean deletePkg(PackageType pkg) {
        try {
            String query = "DELETE FROM packages where PackageId =" + pkg.getPkgId();
            PreparedStatement stmt = con.prepareStatement(query);
            int res = stmt.executeUpdate();
            if (res == 1) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public boolean EditPkg(PackageType pkg) {
        try {
            String query = "Update packages set (PkgName = "+pkg.getPkgName()+",PkgStartDate="+pkg.getPkgStartDate()+"," +
                    "PkgEndDate="+pkg.getPkgEndDate()+",PkgDesc="+ pkg.getPkgDesc()+",BasePrice="+pkg.getPkgBasePrice()
                    +",PkgAgencyCommision="+pkg.getPkgAgencyCom()+") where PackageId =" + pkg.getPkgId();
            PreparedStatement stmt = con.prepareStatement(query);
            int res = stmt.executeUpdate();
            if (res == 1) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }
}//class

