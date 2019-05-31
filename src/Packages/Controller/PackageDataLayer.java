package Packages.Controller;

import Packages.Model.PackageType;
import Packages.Package;
import Resources.DBClass.DBHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PackageDataLayer {


    //PackDB ConnPack;


    //Packages DB stuff
    public List<PackageType> getPackages() throws SQLException {
        List<PackageType> AllPacks = new ArrayList<PackageType>(25);
        Connection connection = PackDB.getConnection();
        String query = "SELECT * from packages";
        Statement state = null;

        //Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/travelexperts", "root", "");
        state = connection.createStatement();
        ResultSet rs = state.executeQuery(query);

        while(rs.next()){
            PackageType Pack = new PackageType(rs.getInt(1),rs.getString(2),
                    rs.getDate(3),rs.getDate(4),rs.getString(5),
                    rs.getLong(6),rs.getLong(7));

            AllPacks.add(Pack);


        }

        connection.close();
        return AllPacks;

    }



    public boolean deletePkg(PackageType pkg) throws SQLException {
        Connection connection = PackDB.getConnection();
        try {
            String query = "DELETE FROM packages where PackageId =" + pkg.getPkgId();

            PreparedStatement stmt = connection.prepareStatement(query);
            int res = stmt.executeUpdate();
            if (res == 1) {
                connection.close();
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        connection.close();
        return false;
    }

    public boolean EditPkg(PackageType pkg) throws SQLException {
        Connection connection = PackDB.getConnection();
        try {
            String query = "Update packages set (PkgName = "+pkg.getPkgName()+",PkgStartDate="+pkg.getPkgStartDate()+"," +
                    "PkgEndDate="+pkg.getPkgEndDate()+",PkgDesc="+ pkg.getPkgDesc()+",BasePrice="+pkg.getPkgBasePrice()
                    +",PkgAgencyCommision="+pkg.getPkgAgencyCom()+") where PackageId =" + pkg.getPkgId();

            PreparedStatement stmt = connection.prepareStatement(query);
            int res = stmt.executeUpdate();
            if (res == 1) {
                connection.close();
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        connection.close();
        return false;
    }
}
