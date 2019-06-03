/*
Author: Mahda Kazemian
Date: May 19,2019
Course Module : PROJ-207-OSD - Threaded Project
Assignment : Team1- Workshop6
purpose: Controller class to get Booking info from DB,edit info,add new booking, save the changes and update the DB
 */

package Booking;

import Resources.AlertCreator;
import Resources.Validator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import javax.swing.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.Optional;

public class Controller {


    @FXML
    private TextField tfBookNum;

    @FXML
    private TextField tfTraveler;

    @FXML
    private TextField tfCustId;

    @FXML
    private TextField tfTripType;

    @FXML
    private TextField tfPackageId;

    @FXML
    private ComboBox<Booking> cbBookingId;

    @FXML
    private ComboBox<TripType> cbTripType;

    @FXML
    private ComboBox<Package> cbPkgName;

    @FXML
    private DatePicker dpBookingDate;

    @FXML
    private Label lblBookingId;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnSaveNewBooking;

    @FXML
    private Button btnCancel;


    //comboSelect to load Booking info after clicking on combobox
    @FXML
    void comboSelect(MouseEvent event) {
        loadCombo();
        btnEdit.setDisable(false);
        btnDelete.setDisable(false);
    }

    //comboAction to load Booking's data after selecting the BookingId in comboBox
    @FXML
    void comboAction(ActionEvent event) {

        if (cbBookingId.getSelectionModel().getSelectedItem() != null)
        {
            dpBookingDate.setValue(LocalDate.parse(cbBookingId.getSelectionModel().getSelectedItem().getBookingDate()));
            tfBookNum.setText(cbBookingId.getSelectionModel().getSelectedItem().getBookingNo());
            tfTraveler.setText(cbBookingId.getSelectionModel().getSelectedItem().getTravelerCount()+"");
            tfCustId.setText(cbBookingId.getSelectionModel().getSelectedItem().getCustomerId()+"");
            tfTripType.setText(cbBookingId.getSelectionModel().getSelectedItem().getTripTypeId());
            tfPackageId.setText(cbBookingId.getSelectionModel().getSelectedItem().getPackageId()+"");
        }
        //dpBookingDate.setStyle("-fx-opacity: 1;");
        dpBookingDate.setOpacity(1);

    }

    //make textFields editable and save button enabled after clicking the Edit Button
    @FXML
    void editBooking(ActionEvent event) {

        enableEdit();
        dpBookingDate.setDisable(false);
        btnSave.setDisable(false);
        btnDelete.setDisable(true);
        btnAdd.setDisable(true);
        btnEdit.setDisable(true);
        btnCancel.setVisible(true);
    }

    //save button to save the changes after editing the booking info
    @FXML
    void saveBooking(ActionEvent event) {
        //connect to DB
        Connection conn = DBHelper.getConnection();
        //input validations
        if (Validator.IsProvided(tfBookNum, "Booking number ") &&
                Validator.IsProvided(tfTraveler, "Traveler Count ") &&
                Validator.IsInt(tfTraveler, "Traveler Count ")&&
                Validator.IsProvided(tfCustId, "Customer Id ") &&
                Validator.IsInt(tfCustId, "Customer Id ")&&
                Validator.IsProvided(tfTripType, "Trip Type ")&&
                Validator.IsLetter(tfTripType, "Trip Type ")&&
                Validator.IsProvided(tfPackageId, "Package Id ")&&
                Validator.SpecificId(tfPackageId, "Package Id "))
        {

            String sql = " update bookings set BookingDate=?,BookingNo=?,TravelerCount=?,CustomerId=?,TripTypeId=?,PackageId=? where BookingId=?";

            try {
                // get inputs from text fields and make them as string to insert in DB
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, dpBookingDate.getValue().toString());
                stmt.setString(2, tfBookNum.getText());
                stmt.setInt(3, Integer.parseInt(tfTraveler.getText()));
                stmt.setInt(4, Integer.parseInt(tfCustId.getText()));
                stmt.setString(5, tfTripType.getText());
                stmt.setInt(6, Integer.parseInt(tfPackageId.getText()));
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
                btnDelete.setDisable(false);
                btnAdd.setDisable(false);
                btnCancel.setVisible(false);
                btnEdit.setDisable(false);
                // make text fields non-editable after saving
                disableEdit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }//end of validation
    }//end of saveBooking


//deleteAction to delete a booking which has foreign key in bookingDetails table
    @FXML
    void deleteAction(ActionEvent event) {
        Connection conn = DBHelper.getConnection();
        String sql= "Delete from bookingdetails where bookingId=?";
        try
        {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1,cbBookingId.getSelectionModel().getSelectedItem().getBookingId());
            stmt.executeUpdate();
            conn.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        subDelete();
        loadCombo();
        //clear all fields
        dpBookingDate.setValue(null);
        clearFields();
        btnDelete.setDisable(true);
        btnEdit.setDisable(true);
    }

// subDelete method to delete a booking which dose not have foreign key(new booking)
    private void subDelete()
    {
        Connection conn = DBHelper.getConnection();
        String sql= "Delete from Bookings where bookingId=?";

        Alert alert0 = new Alert(Alert.AlertType.CONFIRMATION);
        alert0.setTitle("Delete Booking");
        alert0.setContentText("Are you sure you want to delete this Booking?");
        Optional<ButtonType> response = alert0.showAndWait();
        if(response.get() == ButtonType.OK)
        {
            try
            {
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, cbBookingId.getSelectionModel().getSelectedItem().getBookingId());
                int numberRows = stmt.executeUpdate();
                if (numberRows == 0) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "No rows were Deleted. Contact Tech Support.");
                    alert.showAndWait();
                }
                conn.close();
                // show invoice table
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Successfully Deleted.");
                alert.showAndWait();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }

        }//end of if
        else
        {
            AlertCreator.FailedAlert("Delete has been cancelled ");

        }
    }//end of subDelete
//////////////////////////////////////////////ADD New Booking Part/////////////////////////////////////////////////


    //clear the text fields, make them editable and make other button invisible after clicking on add button
    @FXML
    void addAction(ActionEvent event) {

        btnSaveNewBooking.setVisible(true);
        cbBookingId.setVisible(false);
        btnDelete.setVisible(false);
        btnSave.setVisible(false);
        btnEdit.setVisible(false);
        btnAdd.setVisible(false);
        btnCancel.setVisible(true);
        dpBookingDate.setDisable(false);
        cbPkgName.setVisible(true);
        cbTripType.setVisible(true);
        tfBookNum.setText("");
        tfTraveler.setText("");
        tfCustId.setText("");
        tfPackageId.setVisible(false);
        tfTripType.setVisible(false);
        lblBookingId.setVisible(false);
        dpBookingDate.setValue(null);
        cbTripType.setValue(null);
        cbPkgName.setValue(null);
        enableEdit();
    }

    //comboTripTypeSelect to load the TripType name after clicking on combobox
    @FXML
    void comboTripTypeSelect(MouseEvent event) {
        loadTripType();
    }


    //comboPkgSelect to load the package name after clicking on combobox
    @FXML
    void comboPkgSelect(MouseEvent event) {
        loadPackage();
    }


    //cancelAction to cancel and return to first page
    @FXML
    void cancelAction(ActionEvent event) {
        loadFirstPage();
    }


    //(second save button)saveNewBookingAction to insert new booking in DB and create an invoice for new booking
    @FXML
    void saveNewBookingAction(ActionEvent event) {
        // connect to DB
        Connection conn = DBHelper.getConnection();
        //validation for textFields

        if (
                Validator.DatePicked(dpBookingDate, "Booking Date ")&&
                Validator.IsProvided(tfBookNum, "Booking Number ") &&
                Validator.IsProvided(tfTraveler, "Traveler Count ") &&
                Validator.IsInt(tfTraveler, "Traveler Count ")&&
                Validator.IsProvided(tfCustId, "CustomerId ") &&
                Validator.IsInt(tfCustId, "Customer Id ")&&
                Validator.IsSelected(cbTripType, "Trip Type ")&&
                Validator.IsSelected(cbPkgName, "Package Name "))
        {

        // create a table to show the invoice of new booking
        String[] cols = {"BookingDate", "BookingNo", "TravelerCount", "CustomerId", "TripTypeId", "PackageName"};

        Object[][] row = {{dpBookingDate.getValue().toString(), tfBookNum.getText(), tfTraveler.getText(),
                tfCustId.getText(), cbTripType.getSelectionModel().getSelectedItem().getTripTypeId(), cbPkgName.getSelectionModel().getSelectedItem().getPkgName()}};

        JTable table = new JTable(row, cols);

        String sql = " insert into bookings (BookingDate, BookingNo, TravelerCount, CustomerId, TripTypeId, PackageId) values (?, ?, ?, ?, ?, ?)";

        try {
            // get inputs from text fields and make them as string to insert in DB
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, dpBookingDate.getValue().toString());
            stmt.setString(2, tfBookNum.getText());
            stmt.setInt(3, Integer.parseInt(tfTraveler.getText()));
            stmt.setInt(4, Integer.parseInt(tfCustId.getText()));
            stmt.setString(5, cbTripType.getSelectionModel().getSelectedItem().getTripTypeId());
            stmt.setInt(6, cbPkgName.getSelectionModel().getSelectedItem().getPackageId());

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
        //return to firstPage
        loadFirstPage();

         }//end of validator
    }// end of saveNewBookingAction


////////////////////////////////////////////required methods/////////////////////////////////////////////////

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


    // loadTripType to load tripType info from DB
    private void loadTripType() {

        ObservableList<TripType> data = FXCollections.observableArrayList();
        Connection conn = DBHelper.getConnection();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select TripTypeId,TTName from triptypes");
            while (rs.next()) {
                data.add(new TripType(
                        rs.getString(1),
                        rs.getString(2)));
            }
            conn.close();
            cbTripType.setItems(data);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }//end of loadTripType



    // LoadPackage to load package info from DB
    private void loadPackage() {

        ObservableList<Package> data = FXCollections.observableArrayList();
        Connection conn = DBHelper.getConnection();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select PkgName,PackageId from Packages");
            while (rs.next()) {
                data.add(new Package(
                        rs.getString(1),
                        rs.getInt(2)));
            }
            conn.close();
            cbPkgName.setItems(data);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }//end of loadPackage


    //enableEdit method to make textfield editable
    private void enableEdit()
    {
        tfBookNum.setEditable(true);
        tfTraveler.setEditable(true);
        tfCustId.setEditable(true);
        tfTripType.setEditable(true);
        tfPackageId.setEditable(true);

    }

    //disableEdit method to make textfield non-editable
    private void disableEdit()
    {
        tfBookNum.setEditable(false);
        tfTraveler.setEditable(false);
        tfCustId.setEditable(false);
        tfTripType.setEditable(false);
        tfPackageId.setEditable(false);
    }

    //clearFields method to clear the textFields
    private void clearFields()
    {
        tfBookNum.setText("");
        tfTraveler.setText("");
        tfCustId.setText("");
        tfTripType.setText("");
        tfPackageId.setText("");
    }

    //loadFirstPage method to return everything to firstPage condition
    private void loadFirstPage()
    {
        btnSaveNewBooking.setVisible(false);
        btnCancel.setVisible(false);
        btnEdit.setVisible(true);
        btnDelete.setVisible(true);
        btnAdd.setVisible(true);
        btnSave.setVisible(true);
        cbBookingId.setVisible(true);
        tfTripType.setVisible(true);
        tfPackageId.setVisible(true);
        cbPkgName.setVisible(false);
        cbTripType.setVisible(false);
        dpBookingDate.setDisable(true);
        btnEdit.setDisable(true);
        btnDelete.setDisable(true);
        btnSave.setDisable(true);
        btnAdd.setDisable(false);
        dpBookingDate.setValue(null);
        cbBookingId.setValue(null);
        clearFields();
        disableEdit();
    }

}//end of Controller class

