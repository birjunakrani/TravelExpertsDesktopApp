/*
Author: Mahda Kazemian
Date: May 19,2019
Course Module : PROJ-207-OSD - Threaded Project
Assignment : Team1- Workshop6
purpose: Package class with its variables,Constructor and getter and setter to creat Package object
 */
package Booking;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Package {


    private SimpleStringProperty pkgName;
    private SimpleIntegerProperty packageId;

    //constructor to creat Package object
    public Package(String pkgName, int packageId) {
        this.pkgName = new SimpleStringProperty(pkgName);
        this.packageId = new SimpleIntegerProperty(packageId);
    }

    //getter and setter
    public String getPkgName() {
        return pkgName.get();
    }

    public void setPkgName(String pkgName) {
        this.pkgName.set(pkgName);
    }

    public int getPackageId() {
        return packageId.get();
    }

    public void setPackageId(int packageId) {
        this.packageId.set(packageId);
    }

//to String method to show package info
    @Override
    public String toString() {
        return
                packageId.get() +"-"+pkgName.get() +"";
    }
}
