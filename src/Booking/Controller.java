/*
Author: Mahda Kazemian
Date: May 19,2019
Course Module : PROJ-207-OSD - Threaded Project
Assignment : Team1- Workshop6
purpose: Controller class to get Booking info from DB,edit info,add new booking, save the changes and update the DB
 */

package Booking;

import Resources.Validator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.DatePicker;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

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
    private Button btnSave;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnSaveNewBooking;


    //comboSelect to load Booking info after clicking on combobox
    @FXML
    void comboSelect(MouseEvent event) {
        loadCombo();
    }

    //load Booking's data after selecting the BookingId in comboBox
    @FXML
    void comboAction(ActionEvent event) {

        if (cbBookingId.getSelectionModel().getSelectedItem() != null)
        {
            dpBookingDate.setValue(LocalDate.parse(cbBookingId.getSelectionModel().getSelectedItem().getBookingDate()));
            //tfBookDate.setText(cbBookingId.getSelectionModel().getSelectedItem().getBookingDate());
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
        tfTripType.setEditable(true);
        dpBookingDate.setDisable(false);
        btnSave.setDisable(false);
    }

    //save button to save the changes after editing the booking info
    @FXML
    void saveBooking(ActionEvent event) {
        //connect to DB
        Connection conn = DBHelper.getConnection();

        if (//Validator.IsProvided(tfBookDate, "Booking Date ") &&
                Validator.IsProvided(tfBookNum, "Booking number ") &&
                Validator.IsProvided(tfTraveler, "Traveler Count ") &&
                Validator.IsInt(tfTraveler, "Traveler Count ")&&
                Validator.IsProvided(tfCustId, "Customer Id ") &&
                Validator.IsInt(tfCustId, "Customer Id ")&&
                Validator.IsProvided(tfTripType, "Trip Type ")&&
                Validator.IsLetter(tfTripType, "Trip Type "))
        {

            String sql = " update bookings set BookingDate=?,BookingNo=?,TravelerCount=?,CustomerId=?,TripTypeId=?,PackageId=? where BookingId=?";

            try {
                // get inputs from text fields and make them as string to insert in DB
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, dpBookingDate.getValue().toString());
                //stmt.setString(1, tfBookDate.getText());
                stmt.setString(2, tfBookNum.getText());
                stmt.setInt(3, Integer.parseInt(tfTraveler.getText()));
                stmt.setInt(4, Integer.parseInt(tfCustId.getText()));
                stmt.setString(5, tfTripType.getText());
                stmt.setInt(6, Integer.parseInt(tfPackageId.getText()));
                //stmt.setInt(6, cbPkgName.getSelectionModel().getSelectedItem().getPackageId());
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
        }//end of validation
    }//end of saveBooking

//////////////////////////////////////////////ADD New Booking Part/////////////////////////////////////////////////


    //clear the text fields, make them editable and make other button invisible after clicking on add button
    @FXML
    void addAction(ActionEvent event) {

        btnSaveNewBooking.setVisible(true);
        cbBookingId.setVisible(false);
        btnSave.setVisible(false);
        btnEdit.setVisible(false);
        btnAdd.setVisible(false);
        dpBookingDate.setDisable(false);
        cbPkgName.setVisible(true);
        cbTripType.setVisible(true);
        //tfBookDate.setText("");
        tfBookNum.setText("");
        tfTraveler.setText("");
        tfCustId.setText("");
        //tfTripType.setText("");
        tfPackageId.setVisible(false);
        tfTripType.setVisible(false);
        lblBookingId.setVisible(false);
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

    //(second save button)saveNewBookingAction to insert new booking in DB and create an invoice for new booking
    @FXML
    void saveNewBookingAction(ActionEvent event) {
        // connect to DB
        Connection conn = DBHelper.getConnection();
        //validation for textFields
        if (//Validator.IsProvided(tfBookDate, "Booking Date ") &&
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
            //stmt.setString(1, tfBookDate.getText());
            stmt.setString(1, dpBookingDate.getValue().toString());
            stmt.setString(2, tfBookNum.getText());
            stmt.setInt(3, Integer.parseInt(tfTraveler.getText()));
            stmt.setInt(4, Integer.parseInt(tfCustId.getText()));
            stmt.setString(5, cbTripType.getSelectionModel().getSelectedItem().getTripTypeId());
            //stmt.setString(5, tfTripType.getText());
            //stmt.setInt(6, Integer.parseInt(tfPackageId.getText()));
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
            //cbPkgName.setValue(cbBookingId.getSelectionModel().getSelectedItem().getPackageId());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }//end of loadPackage


    //enableEdit method to make textfield editable
    private void enableEdit()
    {
        //tfBookDate.setEditable(true);
        tfBookNum.setEditable(true);
        tfTraveler.setEditable(true);
        tfCustId.setEditable(true);
        //tfTripType.setEditable(true);
       // tfPackageId.setEditable(true);
    }

    //disableEdit method to make textfield non-editable
    private void disableEdit()
    {
        //tfBookDate.setEditable(false);
        tfBookNum.setEditable(false);
        tfTraveler.setEditable(false);
        tfCustId.setEditable(false);
        tfTripType.setEditable(false);
       // tfPackageId.setEditable(false);
    }

}//end of Controller class

