/*
Author: Mahda Kazemian
Date: May 19,2019
Course Module : PROJ-207-OSD - Threaded Project
Assignment : Team1- Workshop6
purpose: Controller class to get Booking info from DB,edit info,add new booking, save the changes and update the DB
 */

package Booking;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import javax.swing.*;
import java.sql.*;

public class Controller {

    @FXML
    private TextField tfBookDate;

    @FXML
    private TextField tfBookNum;

    @FXML
    private TextField tfTraveler;

    @FXML
    private TextField tfCustId;

    @FXML
    private TextField tfTripType;

    @FXML
    private TextField tfPackegeId;

    @FXML
    private ComboBox<Booking> cbBookingId;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnSaveNewBooking;

    //clear the text fields, make them editable and make other button invisible after clicking on add button
    @FXML
    void addAction(ActionEvent event) {

        btnSaveNewBooking.setVisible(true);
        cbBookingId.setVisible(false);
        btnSave.setVisible(false);
        btnEdit.setVisible(false);
        btnAdd.setVisible(false);
        tfBookDate.setText("");
        tfBookNum.setText("");
        tfTraveler.setText("");
        tfCustId.setText("");
        tfTripType.setText("");
        tfPackegeId.setText("");
        enableEdit();
    }

    // saveNewBookingAction to insert new booking in DB and create an invoice for new booking
    @FXML
    void saveNewBookingAction(ActionEvent event) {
        // connect to DB
        Connection conn = DBHelper.getConnection();
       /* String Bdate = tfBookDate.getText();
        String Bnum = tfBookNum.getText();
        Integer Traveler = Integer.parseInt(tfTraveler.getText());
        Integer CustID = Integer.parseInt(tfCustId.getText());
        String  Trip = tfTripType.getText();
        Integer Pckg = Integer.parseInt(tfPackegeId.getText());*/


        /*if (Validator.IsProvided(tfBookDate, "Booking Date is required to be filled.") &&
                Validator.IsProvided(tfBookNum, "Booking Number is required to be filled.") &&
                Validator.IsProvided(tfTraveler, "Traveler Count is required to be filled.") &&
                Validator.IsProvided(tfCustId, "CustomerId is required to be filled.") &&
                Validator.IsProvided(tfTripType, "Trip Type is required to be filled.") &&
                Validator.IsProvided(tfPackegeId, "PackageId is required to be filled.")) {*/

            // create a table to show the invoice of new booking
            String[] cols = {"BookingDate", "BookingNo", "TravelerCount", "CustomerId", "TripTypeId", "PackageId"};
            Object[][] row = {{tfBookDate.getText(), tfBookNum.getText(), tfTraveler.getText(),
                    tfCustId.getText(), tfTripType.getText(), tfPackegeId.getText()}};

            JTable table = new JTable(row, cols);

            String sql = " insert into bookings (BookingDate, BookingNo, TravelerCount, CustomerId, TripTypeId, PackageId) values (?, ?, ?, ?, ?, ?)";

            try {


                // get inputs from text fields and make them as string to insert in DB
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, tfBookDate.getText());
                stmt.setString(2, tfBookNum.getText());
                stmt.setInt(3, Integer.parseInt(tfTraveler.getText()));
                stmt.setInt(4, Integer.parseInt(tfCustId.getText()));
                stmt.setString(5, tfTripType.getText());
                stmt.setInt(6, Integer.parseInt(tfPackegeId.getText()));

                int numberRows = stmt.executeUpdate();
                if (numberRows == 0) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "No rows were updated. Contact Tech Support.");
                    alert.showAndWait();
                }
                conn.close();
                // show invoice table
                JOptionPane.showMessageDialog(null, new JScrollPane(table));

            } catch (SQLException e) {
                e.printStackTrace();
            }

        //}//end of validator
    }// end of saveNewBookingAction

    //load Booking's data after selecting the BookingId in comboBox
    @FXML
    void comboAction(ActionEvent event) {

        if (cbBookingId.getSelectionModel().getSelectedItem() != null)
        {
            tfBookDate.setText(cbBookingId.getSelectionModel().getSelectedItem().getBookingDate());
            tfBookNum.setText(cbBookingId.getSelectionModel().getSelectedItem().getBookingNo());
            tfTraveler.setText(cbBookingId.getSelectionModel().getSelectedItem().getTravelerCount()+"");
            tfCustId.setText(cbBookingId.getSelectionModel().getSelectedItem().getCustomerId()+"");
            tfTripType.setText(cbBookingId.getSelectionModel().getSelectedItem().getTripTypeId());
            tfPackegeId.setText(cbBookingId.getSelectionModel().getSelectedItem().getPackageId()+"");
        }
    }


    //load Booking info in comboBox
    @FXML
    void comboSelect(MouseEvent event) {
        loadCombo();
    }

    //make textFields editable and save button enabled after clicking the Edit Button
    @FXML
    void editBooking(ActionEvent event) {

        enableEdit();
        btnSave.setDisable(false);
    }

    //save button to save the changes after editing
    @FXML
    void saveBooking(ActionEvent event) {
        //connect to DB
        Connection conn = DBHelper.getConnection();

            String sql = " update bookings set BookingDate=?,BookingNo=?,TravelerCount=?,CustomerId=?,TripTypeId=?,PackageId=? where BookingId=?";
            try {
                // get inputs from text fields and make them as string to insert in DB
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, tfBookDate.getText());
                stmt.setString(2, tfBookNum.getText());
                stmt.setInt(3, Integer.parseInt(tfTraveler.getText()));
                stmt.setInt(4, Integer.parseInt(tfCustId.getText()));
                stmt.setString(5, tfTripType.getText());
                stmt.setInt(6, Integer.parseInt(tfPackegeId.getText()));
                stmt.setInt(7, cbBookingId.getSelectionModel().getSelectedItem().getBookingId());

                int numberRows = stmt.executeUpdate();
                if (numberRows == 0) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "No rows were updated. Contact Tech Support.");
                    alert.showAndWait();
                }
                conn.close();
                //show successful alert
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Successfully saved");
                alert.showAndWait();
                //make save button disable after saving
                btnSave.setDisable(true);
                // make text fields non-editable after saving
                disableEdit();
            } catch (SQLException e) {
                e.printStackTrace();
            }

    }//end of saveBooking


    //loadCombo method to load Booking's data from DB

    private void loadCombo() {

        ObservableList<Booking> data = FXCollections.observableArrayList();
        Connection conn = DBHelper.getConnection();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from bookings");
            while (rs.next()) {
                data.add(new Booking(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getInt(5),
                        rs.getString(6),
                        rs.getInt(7)));
            }
            conn.close();
            cbBookingId.setItems(data);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }//end of loadCombo


    //enableEdit method to make textfield editable
    private void enableEdit()
    {
        tfBookDate.setEditable(true);
        tfBookNum.setEditable(true);
        tfTraveler.setEditable(true);
        tfCustId.setEditable(true);
        tfTripType.setEditable(true);
        tfPackegeId.setEditable(true);
    }

    //disableEdit method to make textfield non-editable
    private void disableEdit()
    {
        tfBookDate.setEditable(false);
        tfBookNum.setEditable(false);
        tfTraveler.setEditable(false);
        tfCustId.setEditable(false);
        tfTripType.setEditable(false);
        tfPackegeId.setEditable(false);
    }

}//end of Controller class

