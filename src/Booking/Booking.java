/*
Author: Mahda Kazemian
Date: May 19,2019
Course Module : PROJ-207-OSD - Threaded Project
Assignment : Team1- Workshop6
purpose: Booking class with its variables,Constructor and getter and setter to creat Booking object
 */
package Booking;


import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;


public class Booking {

private SimpleIntegerProperty bookingId;
private SimpleStringProperty bookingDate;
private SimpleStringProperty bookingNo;
private SimpleIntegerProperty travelerCount;
private SimpleIntegerProperty customerId;
private SimpleStringProperty tripTypeId;
private SimpleIntegerProperty packageId;


    // Booking constructor to creat Booking object

    public Booking(int bookingId, String bookingDate, String bookingNo,
                   int travelerCount, int customerId, String tripTypeId,
                   int packageId) {

        this.bookingId = new SimpleIntegerProperty(bookingId);
        this.bookingDate = new SimpleStringProperty(bookingDate);
        this.bookingNo = new SimpleStringProperty(bookingNo);
        this.travelerCount = new SimpleIntegerProperty(travelerCount);
        this.customerId = new SimpleIntegerProperty(customerId);
        this.tripTypeId = new SimpleStringProperty(tripTypeId);
        this.packageId = new SimpleIntegerProperty(packageId);
    }

    // getter and setter

    public int getBookingId() {
        return bookingId.get();
    }

    public void setBookingId(int bookingId) {
        this.bookingId.set(bookingId);
    }

    public String getBookingDate() {
        return bookingDate.get();
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate.set(bookingDate);
    }

    public String getBookingNo() {
        return bookingNo.get();
    }

    public void setBookingNo(String bookingNo) {
        this.bookingNo.set(bookingNo);
    }

    public int getTravelerCount() {
        return travelerCount.get();
    }

    public void setTravelerCount(int travelerCount) {
        this.travelerCount.set(travelerCount);
    }

    public int getCustomerId() {
        return customerId.get();
    }

    public void setCustomerId(int customerId) {
        this.customerId.set(customerId);
    }

    public String getTripTypeId() {
        return tripTypeId.get();
    }

    public void setTripTypeId(String tripTypeId) {
        this.tripTypeId.set(tripTypeId);
    }

    public int getPackageId() {
        return packageId.get();
    }

    public void setPackageId(int packageId) {
        this.packageId.set(packageId);
    }

//To String method for return bookingId

    @Override
    public String toString() {
        return bookingId.get() +"";
    }
}
