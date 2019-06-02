/*
Author: Mahda Kazemian
Date: May 19,2019
Course Module : PROJ-207-OSD - Threaded Project
Assignment : Team1- Workshop6
purpose: TripType class with its variables,Constructor and getter and setter to creat TripType object
 */
package Booking;

import javafx.beans.property.SimpleStringProperty;

public class TripType {

    private SimpleStringProperty tripTypeId;
   private SimpleStringProperty TTName;


    public TripType(String tripTypeId, String TTName) {
        this.tripTypeId = new SimpleStringProperty(tripTypeId);
        this.TTName = new SimpleStringProperty(TTName);
    }


    public String getTripTypeId() {
        return tripTypeId.get();
    }

    public void setTripTypeId(String tripTypeId) {
       this.tripTypeId.set(tripTypeId);
    }

    public String getTTName() {
        return TTName.get();
    }

    public void setTTName(String TTName) {
        this.TTName.set(TTName);
    }

    @Override
    public String toString() {
        return  tripTypeId.get() +"-" + TTName.get() +"";
    }
}
