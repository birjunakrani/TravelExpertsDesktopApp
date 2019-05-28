package Packages.Controller;

import Packages.Model.PackageType;
import Packages.Package;
import Resources.DBClass.DBHelper;

import java.sql.SQLException;
import java.util.ArrayList;

public class PackageDataLayer {

    ArrayList<PackageType> AllPacks;

    {
        try {
            AllPacks = DBHelper.dbHelper.getPackages();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }





}
