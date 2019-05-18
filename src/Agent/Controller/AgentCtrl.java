package TravelExperts.Agent.Controller;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import TravelExperts.Agent.DBClass.DBHelper;
import TravelExperts.Agent.Model.Agent;
import TravelExperts.Agent.Validator.Validator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.InputMethodEvent;

import javax.swing.*;


public class AgentCtrl {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;


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
    void TextChanged(InputMethodEvent event)
    {
        searchByAgent();
    }

    @FXML
    void btnInsertAction(ActionEvent event) {


        DBHelper dbhelper = DBHelper.getInstance();


        if(Validator.IsProvided(agtFirstName,"First Name can't be empty") && Validator.IsProvided(agtLastName,"Last Name can't be empty")
            && Validator.IsProvided(agtEmail,"Email can't be empty") && Validator.IsProvided(agtPosition,"Position can't be empty")
                && Validator.IsProvided(agtAgency,"Agency can't be empty")
                && Validator.IsNonNegative(agtAgency,"AgencyID has to be positive number"))
        {
       //     int agentId = cmbAgtId.getSelectionModel().getSelectedIndex(); auto-increment in database.
            String agtFname = agtFirstName.getText();
            String agtM = agtMI.getText();
            String agtLname = agtLastName.getText();
            String email = agtEmail.getText();
            String phone = agtPhone.getText();
            String position = agtPosition.getText();
            int agency = Integer.parseInt(agtAgency.getText());

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
                    JOptionPane.showMessageDialog(null,"Insert succeeded");

                } else {
                    JOptionPane.showMessageDialog(null,"Insert Failed");
                }
            }catch (SQLException ex){
                ex.printStackTrace();
            }
        }
    }

    @FXML
    void btnSaveAction(ActionEvent event) {

        DBHelper dbHelper = DBHelper.getInstance();
        boolean rowsUpdated = true;
        try {
            String nonquery = "UPDATE `agents` SET `AgentId`=?,`AgtFirstName`=?,`AgtMiddleInitial`=?,`AgtLastName`=?," +
                    "`AgtBusPhone`=?,`AgtEmail`=?,`AgtPosition`=?,`AgencyId`=? WHERE AgentId = ? ";
            if (rowsUpdated = false) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Error updating the database", ButtonType.OK);
                alert.showAndWait();
            }

        }catch(Exception ex){
            ex.printStackTrace();
        }

    }

    @FXML
    void btnUpdateAction(ActionEvent event) {

  /*      ObservableList<Agent> lst = FXCollections.observableArrayList();
        Agent agentObj = tblAgent.getSelectionModel().getSelectedItem();
        cmbAgtId;
        agtFirstName.setText(agentObj.getAgtFirstName());
        agtMI.setText(agentObj.getAgtMiddleInital());
        agtLastName.setText(agentObj.getAgtLastName());
        agtPhone.setText((agentObj.getAgtBusPhone()));
        agtEmail.setText(agentObj.getAgtEmail());
        cmbAgtPos;
        cmbAgtAgency*/


    }


    @FXML
    void btnDeleteAction(ActionEvent event) {

    }




    @FXML
   public  void initialize() {

        ObservableList<Agent> agents = FXCollections.observableArrayList(loadAgents());
        fillAgentTable();
        tblAgent.setItems(agents);
        cmbAgtId.setItems(agents);

        selectionInTableChanged();
        selectionInComboChanged();
    }

    private void searchByAgent() {

        ObservableList<Agent> agents = FXCollections.observableArrayList();
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
    }

    public  ObservableList loadAgents() {

        DBHelper dbHelper = DBHelper.getInstance();
        ObservableList<Agent> agentList= FXCollections.observableArrayList();
        String query = "SELECT * FROM agents";
       try {

            ResultSet rs = dbHelper.executeQuery(query);
            while (rs.next()) {
                agentList.add(new Agent(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getInt(8)
                ));
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return agentList;
    }
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

    }

    private void selectionInTableChanged() {

        ObservableList<Agent> agents = FXCollections.observableArrayList(loadAgents());
     //   if(!tblAgent.getSelectionModel().isEmpty()) {

            tblAgent.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Agent>() {
            @Override
            public void changed(ObservableValue<? extends Agent> observable, Agent oldValue, Agent newValue) {

                if (newValue != null) {
                    int agentId = cmbAgtId.getSelectionModel().getSelectedIndex();
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
                    agtAgency.setText(newValue.getAgencyId() + "");
                    }
                }
            });
       // }
    }
       private void selectionInComboChanged() {

    //    if (!cmbAgtId.getSelectionModel().isEmpty()) {
            cmbAgtId.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Agent>() {
                @Override
                public void changed(ObservableValue<? extends Agent> observable, Agent oldValue, Agent newValue) {
                agtFirstName.setText(newValue.getAgtFirstName());
                agtMI.setText(newValue.getAgtMiddleInital());
                agtLastName.setText(newValue.getAgtLastName());
                agtPhone.setText(newValue.getAgtBusPhone());
                agtEmail.setText(newValue.getAgtEmail());
                agtPosition.setText(newValue.getAgtPosition());
                agtAgency.setText(newValue.getAgencyId() + "");
                }
            });
    //    }
    }
    public static AgentCtrl singleton;
    public static void setAgentform (AgentCtrl agentform){
        singleton = agentform;
    }
}
/*
myform = new AgentForm(vars, for, init);
AgentForm.setAgentForm(myform);
AgentForm.getAgentForm(); // returns myform
*/


