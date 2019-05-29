package Agent.Controller;

import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import Agency.Model.Agency;
import Resources.AlertCreator;
import Resources.DBClass.DBHelper;
import TravelExperts.Agent.Model.Agent;
import Resources.Validator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;

import javax.swing.*;


public class AgentController {

    DBHelper dbhelper = null;
    ObservableList<Agent> agents = FXCollections.observableArrayList();
    Agent agent;

    ObservableList<Agency> agencies = FXCollections.observableArrayList();
    Agency agency;


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab tbAgent;

    @FXML
    private Tab tbAgency;

    @FXML
    private ComboBox<Agent> cmbAgtId;


    @FXML
    private Button btnInsert;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnDelete;

    @FXML
    private TextField agtSearch;


    @FXML
    private TextField agtFirstName;

    @FXML
    private TextField agtMI;

    @FXML
    private TextField agtLastName;

    @FXML
    private TextField agtPhone;

    @FXML
    private TextField agtEmail;

    @FXML
    private TextField agtPosition;

    @FXML
    private TextField agtAgency;

    @FXML
    private TableView<Agent> tblAgent;

    @FXML
    private TableColumn<Agent, Integer> colId;

    @FXML
    private TableColumn<Agent, String> colFname;

    @FXML
    private TableColumn<Agent, String> colMI;

    @FXML
    private TableColumn<Agent, String> colLname;

    @FXML
    private TableColumn<Agent, String> colPosition;

    @FXML
    private TableColumn<Agent, String> colEmail;

    @FXML
    private TableColumn<Agent, String> colPhone;

    @FXML
    private TableColumn<Agent, Integer> colAgency;

    @FXML
    void tbAgencyAction(ActionEvent event) {
        loadAgencies();
        fillAgencyTable();
        selectionInAgencyTableChanged();
        selectionInAgencyComboChanged();
        searchByAgency();

    }

    @FXML
    void tbAgentAction(ActionEvent event) {



    }

    @FXML
    void btnSaveClick(MouseEvent event) {

        addAgent();
    }//btnSaveClick

    @FXML
    void btnAddClick(MouseEvent event) {

        cmbAgtId.setValue(null); agtFirstName.setText(null); agtLastName.clear(); agtMI.clear();agtEmail.clear();
        agtPhone.clear();agtPosition.clear();agtAgency.clear();
    }//btnAddClick

    @FXML
    void btnDeleteClick(MouseEvent event) {

        Agent selectedAgentForDelete = cmbAgtId.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Deleting Agent");
        alert.setContentText("Are you sure you want to delete this Agent" + selectedAgentForDelete.getAgtFirstName()+" "+selectedAgentForDelete.getAgtLastName());
        Optional<ButtonType> response = alert.showAndWait();

        if(response.get() == ButtonType.OK){
            Boolean result = DBHelper.getInstance().deleteAgent(selectedAgentForDelete);
            if(result){
                AlertCreator.SuccessAlert(selectedAgentForDelete.getAgtFirstName()+" " + selectedAgentForDelete.getAgtLastName() + " " + "has Successfully Deleted");
                agents.remove(selectedAgentForDelete);
                tblAgent.setItems(agents);
            } else { AlertCreator.FailedAlert("Delete operation has been failed, Please try Again!");}
        }else {
            AlertCreator.FailedAlert("Delete operation has been cancelled by user");
        }

    }//btnDeleteClick


    @FXML
    void btnUpdateClick(MouseEvent event) {

        int selectedAgentForUpdate = cmbAgtId.getSelectionModel().getSelectedItem().getAgentId();
        Agent agent = new Agent(selectedAgentForUpdate, agtFirstName.getText(),agtMI.getText(),
                agtLastName.getText(),agtPhone.getText(),agtEmail.getText(),agtPosition.getText(),Integer.parseInt(agtAgency.getText()));

        if (Validator.IsProvided(agtFirstName, "First Name") && Validator.IsProvided(agtLastName, "Last Name")
                && Validator.IsProvided(agtEmail, "Email") && Validator.IsProvided(agtPosition, "Position") && Validator.IsProvided(agtAgency,"Agency"))
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Update Agent");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure want to update the record for ?" + agent.getAgtFirstName() + " " + agent.getAgtLastName());
            Optional<ButtonType> response = alert.showAndWait();
            try {
                if (response.get() == ButtonType.OK) {
                    Boolean result = DBHelper.getInstance().updateAgent(agent);

                    if (result) {
                        AlertCreator.SuccessAlert(agent.getAgtLastName() + " " + "has Successfully Updated");
                        loadAgents();

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
    }//btnUpdateClick


    private void searchByAgent() {

        FilteredList<Agent> filteredList = new FilteredList<>(agents, e ->true);
        agtSearch.setOnKeyReleased(e -> {
            agtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredList.setPredicate((Predicate<? super Agent>) agent -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (agent.getAgtFirstName().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (agent.getAgtLastName().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                    return false;
                });

            });

            SortedList<Agent> sortedList = new SortedList<>(filteredList);
            sortedList.comparatorProperty().bind(tblAgent.comparatorProperty());
            tblAgent.setItems(sortedList);
        });
    } //searchByAgent()

    public  void loadAgents() {

        tblAgent.setItems(null);
        cmbAgtId.setItems(null);
         dbhelper = DBHelper.getInstance();

        String query = "SELECT * FROM agents ORDER BY agtLastName ASC";
        ResultSet rs = dbhelper.executeQuery(query);
        try {

            while (rs.next()) {
                int id =  rs.getInt(1);
                String agtFname =  rs.getString(2);
                String agtM = rs.getString(3);
                String agtLname = rs.getString(4);
                String agtPhone = rs.getString(5);
                String agtEmail = rs.getString(6);
                String agtPos = rs.getString(7);
                int agtagency = rs.getInt(8);

                agents.add(new Agent(id,agtFname,agtM,agtLname,agtPhone,agtEmail,agtPos,agtagency));
            }
        }catch (SQLException ex){
            JOptionPane.showMessageDialog(null,"Errror:" + ex.getMessage(),"Error Occured" ,JOptionPane.ERROR_MESSAGE);
        }
        tblAgent.setItems(agents);
        cmbAgtId.setItems(agents);
    } //loadAgents()

    private void fillAgentTable()
    {
        colId.setCellValueFactory(cellData -> cellData.getValue().agentIdProperty().asObject());
        colFname.setCellValueFactory(cellData -> cellData.getValue().agtFirstNameProperty());
        colMI.setCellValueFactory((cellData -> cellData.getValue().agtMiddleInitalProperty()));
        colLname.setCellValueFactory(cellData -> cellData.getValue().agtLastNameProperty());
        colPhone.setCellValueFactory((cellData -> cellData.getValue().agtBusPhoneProperty()));
        colEmail.setCellValueFactory((cellData -> cellData.getValue().agtEmailProperty()));
        colPosition.setCellValueFactory(cellData -> cellData.getValue().agtPositionProperty());
        colAgency.setCellValueFactory((cellData -> cellData.getValue().agencyIdProperty().asObject()));

    }//fillAgentTable()

    private void selectionInTableChanged() {

         tblAgent.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Agent>() {
            @Override
            public void changed(ObservableValue<? extends Agent> observable, Agent oldValue, Agent newValue) {

                if (newValue != null) {
                    //     int agentId = cmbAgtId.getSelectionModel().getSelectedIndex();
                    for (Agent agent : agents) {
                        if (agent.getAgentId() == newValue.getAgentId()) {
                          cmbAgtId.getSelectionModel().select(agent);
                        }
                    }
                    agtFirstName.setText(newValue.getAgtFirstName());
                    agtMI.setText(newValue.getAgtMiddleInital());
                    agtLastName.setText(newValue.getAgtLastName());
                    agtPhone.setText(newValue.getAgtBusPhone());
                    agtEmail.setText(newValue.getAgtEmail());
                    agtPosition.setText(newValue.getAgtPosition());
                    agtAgency.setText(newValue.getAgencyId()+"");
                }
            }
        });

    } //selectionInTableChanged()

    private void selectionInComboChanged() {

        cmbAgtId.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Agent>() {
            @Override
            public void changed(ObservableValue<? extends Agent> observable, Agent oldValue, Agent newValue) {
                agtFirstName.setText(newValue.getAgtFirstName());
                agtMI.setText(newValue.getAgtMiddleInital());
                agtLastName.setText(newValue.getAgtLastName());
                agtPhone.setText(newValue.getAgtBusPhone());
                agtEmail.setText(newValue.getAgtEmail());
                agtPosition.setText(newValue.getAgtPosition());
                agtAgency.setText(newValue.getAgencyId()+"");
            }
        });

    } //selectionInComboChanged()

    private void addAgent(){

        DBHelper dbhelper = DBHelper.getInstance();

        if(Validator.IsProvided(agtFirstName,"First Name") && Validator.IsProvided(agtLastName,"Last Name")
                && Validator.IsProvided(agtEmail,"Email") && Validator.IsProvided(agtPosition,"Position") && Validator.IsProvided(agtAgency,"Agency"))

        {
            //     int agentId = cmbAgtId.getSelectionModel().getSelectedIndex(); auto-increment in database.
            String agtFname = agtFirstName.getText();
            String agtM = agtMI.getText();
            String agtLname = agtLastName.getText();
            String email = agtEmail.getText();
            String phone = agtPhone.getText();
            String position = agtPosition.getText();
            int agency = cmbAgtId.getSelectionModel().getSelectedItem().getAgencyId();

            String query = "INSERT INTO agents VALUES ( "+
                    "'" + agtFname + "'," +
                    "'" + agtM + "'," +
                    "'" + agtLname + "'," +
                    "'" + phone + "'," +
                    "'" + email + "'," +
                    "'" + position + "'," +
                    "'" + agency + "'" +
                    ")";
            System.out.print(query);
            try{
                if(dbhelper.execNonQuery(query)){
                    JOptionPane.showMessageDialog(null,"Agent has been successfully added");

                } else {
                    JOptionPane.showMessageDialog(null,"Failed to insert agent, Please try again");
                }
            }catch (SQLException ex){
                ex.printStackTrace();
            }
        }
    }//addAgent()


    //All about Agency

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

    } //btnAgncyAddClick

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

    } //btnAgncyDeleteClick

    @FXML
    void btnAgncySaveClick(MouseEvent event) {
        addAgency();
    } //btnAgncySaveClick

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

    } //btnAgncyUpdateClick


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
    } //searchByAgency()

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
    }//loadAgencies()

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

    } //fillAgencyTable()

    private void selectionInAgencyTableChanged() {

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

    }//selectionInAgencyTableChanged()

    private void selectionInAgencyComboChanged() {

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

    }//selectionInComboChanged()

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
    }//addAgency()

    @FXML
    public  void initialize() {

        dbhelper = DBHelper.getInstance();

        SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
        selectionModel.select(tbAgency);
        tabPane.getSelectionModel().select(tbAgency);


        if(tbAgent.isSelected()){
            loadAgents();
            fillAgentTable();
            selectionInTableChanged();
            selectionInComboChanged();
            searchByAgent();
        }
        if(tbAgency.isSelected()){
            loadAgencies();
            fillAgencyTable();
            selectionInAgencyTableChanged();
            selectionInAgencyComboChanged();
            searchByAgency();
        }

    }




} //class


