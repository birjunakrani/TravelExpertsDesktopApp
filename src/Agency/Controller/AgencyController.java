package Agency.Controller;

import Agency.Model.Agency;
import Resources.AlertCreator;
import Resources.DBClass.DBHelper;
import Resources.Validator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.*;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.function.Predicate;

public class AgencyController {

    DBHelper dbhelper = null;
    ObservableList<Agency> agencies = FXCollections.observableArrayList();
    Agency agency;


    @FXML
    private TableView<Agency> tblAgency;

    @FXML
    private Button btnAgncyAdd;

    @FXML
    private Button btnAgncySave;

    @FXML
    private Button btnAgncyUpdate;

    @FXML
    private Button btnAgncyDelete;

       @FXML
    private TextField agncySearch;

    @FXML
    private ComboBox<Agency> cmbAgencyId;

    @FXML
    private TextField agncyAddress;

    @FXML
    private TextField agncyCity;

    @FXML
    private TextField agncyProv;

    @FXML
    private TextField agncyPostal;

    @FXML
    private TextField agncyCountry;

    @FXML
    private TextField agncyPhone;

    @FXML
    private TextField agncyFax;

    @FXML
    private TableColumn<Agency, Integer> colAgncyId;

    @FXML
    private TableColumn<Agency, String> colAddress;

    @FXML
    private TableColumn<Agency, String> colCity;

    @FXML
    private TableColumn<Agency, String> colProv;

    @FXML
    private TableColumn<Agency, String> colPostal;

    @FXML
    private TableColumn<Agency, String> colCountry;

    @FXML
    private TableColumn<Agency, String> colAgncyPhone;

    @FXML
    private TableColumn<Agency, String> colFax;

    @FXML
    void btnAgncyAddClick(MouseEvent event) {

        cmbAgencyId.setValue(null); agncyAddress.setText(null); agncyCity.clear(); agncyCountry.clear();agncyPostal.clear();
        agncyProv.clear();agncyPhone.clear();agncyFax.clear();

    }

    @FXML
    void btnAgncyDeleteClick(MouseEvent event) {

        Agency SelectedAgencyForDelete = cmbAgencyId.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Deleting Agency");
        alert.setContentText("Are you sure you want to delete this Agency" + SelectedAgencyForDelete.getAgencyId());
        Optional<ButtonType> response = alert.showAndWait();

        if(response.get() == ButtonType.OK){
            int agentCount = DBHelper.getInstance().checkBeforedeleteAgency(cmbAgencyId.getValue());
            if(agentCount == 0){
                Boolean result = DBHelper.getInstance().deleteAgency(SelectedAgencyForDelete);
                if(result){
                    AlertCreator.SuccessAlert(SelectedAgencyForDelete.getAgencyId() + "has Successfully Deleted");
                    agencies.remove(SelectedAgencyForDelete);
                    tblAgency.setItems(agencies);
                } else { AlertCreator.FailedAlert("Delete operation has been failed, Please try Again!");}
            } else { AlertCreator.FailedAlert("Please delete the associated agent first"); }

        }else {
            AlertCreator.FailedAlert("Delete operation has been cancelled by user");
        }

    }

    @FXML
    void btnAgncySaveClick(MouseEvent event) {
        addAgency();
    }

    @FXML
    void btnAgncyUpdateClick(MouseEvent event) {

        int selectedAgencyForUpdate = cmbAgencyId.getSelectionModel().getSelectedItem().getAgencyId();
        agency = new Agency(selectedAgencyForUpdate, agncyAddress.getText(),agncyCity.getText(),
                agncyProv.getText(),agncyPostal.getText(),agncyCountry.getText(),agncyPhone.getText(),agncyFax.getText());

        if (Validator.IsProvided(agncyAddress, "Address") && Validator.IsProvided(agncyCity, "City")
                && Validator.IsProvided(agncyPhone, "Phone#"))
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Update Agent");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure want to update the record for ?" + agency.getAgencyId());
            Optional<ButtonType> response = alert.showAndWait();
            try {
                if (response.get() == ButtonType.OK) {
                    Boolean result = DBHelper.getInstance().updateAgency(agency);

                    if (result) {
                        AlertCreator.SuccessAlert(agency.getAgencyId() + " " + "has Successfully Updated");
                   //     loadAgency();

                    } else {

                        AlertCreator.FailedAlert("Update operation has been failed, Please try Again!");
                    }
                } else {
                    Alert alertCancel = new Alert(Alert.AlertType.INFORMATION);
                    alertCancel.setTitle("Update Cancelled");
                    alertCancel.setHeaderText("Update operation has been cancelled by user");
                    alertCancel.showAndWait();
                }
            }catch (Exception ex) { ex.getLocalizedMessage(); }
        }else AlertCreator.FailedAlert("Please enter all required fileds");


    }


    @FXML
    public  void initialize() {

        dbhelper = DBHelper.getInstance();
        loadAgencies();
        fillAgencyTable();
        selectionInTableChanged();
        selectionInComboChanged();
        searchByAgency();
    }

    private void searchByAgency() {

        FilteredList<Agency> filteredList = new FilteredList<>(agencies, e ->true);
        agncySearch.setOnKeyReleased(e -> {
            agncySearch.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredList.setPredicate((Predicate<? super Agency>) agency -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (agency.getAgncyCity().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (agency.getAgncyPhone().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                    return false;
                });

            });

            SortedList<Agency> sortedList = new SortedList<>(filteredList);
            sortedList.comparatorProperty().bind(tblAgency.comparatorProperty());
            tblAgency.setItems(sortedList);
        });
    }

    public  void loadAgencies() {

        tblAgency.setItems(null);
        cmbAgencyId.setItems(null);
        dbhelper = DBHelper.getInstance();

        String query = "SELECT * FROM agencies";
        ResultSet rs = dbhelper.executeQuery(query);
        try {

            while (rs.next()) {
                int agencyId =  rs.getInt(1);
                String agncyAddress =  rs.getString(2);
                String agncyCity = rs.getString(3);
                String agncyProv = rs.getString(4);
                String agncyPostal = rs.getString(5);
                String agncyCountry = rs.getString(6);
                String agncyPhone = rs.getString(7);
                String agncyFax = rs.getString(8);

                agencies.add(new Agency(agencyId,agncyAddress,agncyCity,agncyProv,agncyPostal,agncyCountry,agncyPhone,agncyFax));
            }
        }catch (SQLException ex){
            JOptionPane.showMessageDialog(null,"Errror:" + ex.getMessage(),"Error Occured" ,JOptionPane.ERROR_MESSAGE);
        }
        tblAgency.setItems(agencies);
        cmbAgencyId.setItems(agencies);
    }

    private void fillAgencyTable()
    {
        colAgncyId.setCellValueFactory(cellData -> cellData.getValue().agencyIdProperty().asObject());
        colAddress.setCellValueFactory(cellData -> cellData.getValue().agncyAddressProperty());
        colCity.setCellValueFactory((cellData -> cellData.getValue().agncyCityProperty()));
        colProv.setCellValueFactory(cellData -> cellData.getValue().agncyProvProperty());
        colPostal.setCellValueFactory((cellData -> cellData.getValue().agncyPostalProperty()));
        colCountry.setCellValueFactory((cellData -> cellData.getValue().agncyCountryProperty()));
        colAgncyPhone.setCellValueFactory(cellData -> cellData.getValue().agncyPhoneProperty());
        colFax.setCellValueFactory((cellData -> cellData.getValue().agncyFaxProperty()));

    }

    private void selectionInTableChanged() {

        tblAgency.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Agency>() {
            @Override
            public void changed(ObservableValue<? extends Agency> observable, Agency oldValue, Agency newValue) {

                if (newValue != null) {
                    //     int agentId = cmbAgtId.getSelectionModel().getSelectedIndex();
                    for (Agency agency : agencies) {
                        if (agency.getAgencyId() == newValue.getAgencyId()) {
                            cmbAgencyId.getSelectionModel().select(agency);
                        }
                    }
                    agncyAddress.setText(newValue.getAgncyAddress());
                    agncyCity.setText(newValue.getAgncyCity());
                    agncyProv.setText(newValue.getAgncyProv());
                    agncyPostal.setText(newValue.getAgncyPostal());
                    agncyCountry.setText(newValue.getAgncyCountry());
                    agncyPhone.setText(newValue.getAgncyPhone());
                    agncyFax.setText(newValue.getAgncyFax()+"");
                }
            }
        });

    }

    private void selectionInComboChanged() {

        cmbAgencyId.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Agency>() {
            @Override
            public void changed(ObservableValue<? extends Agency> observable, Agency oldValue, Agency newValue) {
                agncyAddress.setText(newValue.getAgncyAddress());
                agncyCity.setText(newValue.getAgncyCity());
                agncyProv.setText(newValue.getAgncyProv());
                agncyPostal.setText(newValue.getAgncyPostal());
                agncyCountry.setText(newValue.getAgncyCountry());
                agncyPhone.setText(newValue.getAgncyPhone());
                agncyFax.setText(newValue.getAgencyId()+"");
            }
        });

    }

    private void addAgency(){

        DBHelper dbhelper = DBHelper.getInstance();

        if (Validator.IsProvided(agncyAddress, "Address") && Validator.IsProvided(agncyCity, "City") && Validator.IsProvided(agncyPhone, "Phone#"))

        {
            //     int agentId = cmbAgtId.getSelectionModel().getSelectedIndex(); auto-increment in database.
            String address = agncyAddress.getText();
            String city = agncyCity.getText();
            String prov = agncyProv.getText();
            String postal = agncyPostal.getText();
            String country = agncyCountry.getText();
            String phone = agncyPhone.getText();
            String fax = agncyFax.getText();

            String query = "INSERT INTO agencies VALUES ( "+
                    "'" + agncyAddress + "'," +
                    "'" + agncyCity + "'," +
                    "'" + agncyProv + "'," +
                    "'" + agncyPostal + "'," +
                    "'" + agncyCountry + "'," +
                    "'" + agncyPhone + "'," +
                    "'" + agncyFax + "'" +
                    ")";
            System.out.print(query);
            try{
                if(dbhelper.execNonQuery(query)){
                    JOptionPane.showMessageDialog(null,"Agency has been successfully added");

                } else {
                    JOptionPane.showMessageDialog(null,"Failed to insert Agency, Please try again");
                }
            }catch (SQLException ex){
                ex.printStackTrace();
            }
        }
    }


}


