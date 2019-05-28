package SwitchBoard;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import Resources.AlertCreator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class MainController {

    boolean IsLoggedIn = true;
    Resources.DBClass.DBHelper dbhelper = null;



        @FXML
        private ResourceBundle resources;

        @FXML
        private URL location;

        @FXML
        private Button btnCustomers;

        @FXML
        private Button btnBookings;

        @FXML
        private Button btnPackages;

        @FXML
        private Button btnAgents;

        @FXML
        private TextField txtLogin;

        @FXML
        private TextField txtPassword;


        @FXML
        void btnAgents(MouseEvent event) {
            loadWindow("../Agent/View/agent.fxml","Agents");
        }

        @FXML
        void btnCustomers(MouseEvent event) {

        }

        @FXML
        void btnPackages(MouseEvent event) {loadWindow("../Packages/View/PackageDisplay.fxml","Packages");
        //Open Packages Source
        }
        @FXML
        void btnBookings(MouseEvent event) {

        }

        @FXML
        private Button btnLogIn;

        @FXML
        private Button btnCancel;

        @FXML
        void btnCancelAction(ActionEvent event) {
        }

        @FXML
        void btnLoginAction(ActionEvent event) {

       }



        void loadWindow(String loc, String title){
            try{
                Parent parent = FXMLLoader.load(getClass().getResource(loc));
                Stage stage = new Stage(StageStyle.DECORATED);
                stage.setTitle(title);
                stage.setScene(new Scene(parent));
                stage.setFullScreen(true);
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @FXML
        void initialize() {
            assert btnCustomers != null : "fx:id=\"btnCustomers\" was not injected: check your FXML file 'SwitchBoard.fxml'.";
            assert btnBookings != null : "fx:id=\"btnBookings\" was not injected: check your FXML file 'SwitchBoard.fxml'.";
            assert btnPackages != null : "fx:id=\"btnPackages\" was not injected: check your FXML file 'SwitchBoard.fxml'.";
            assert btnAgents != null : "fx:id=\"btnAgents\" was not injected: check your FXML file 'SwitchBoard.fxml'.";



        }

        private void AgentLogIn() throws SQLException {

            Resources.DBClass.DBHelper dbhelper = Resources.DBClass.DBHelper.getInstance();
            ObservableList<AgentLogIn> agentAccount = FXCollections.observableArrayList();
            AgentLogIn agentLogIn = null;

            String query = "SELECT * FROM UserAccounts";
      //      AgentLogIn agentLogin = new AgentLogIn();

            ResultSet rs = dbhelper.executeQuery(query);

            while(rs.next()){

                String username =  rs.getString(1);
                String password = rs.getString(2);
                agentLogIn = new AgentLogIn(username,password);
                agentAccount.add(agentLogIn);

            }

            if(!IsLoggedIn) //if agent is not logged in
            {
                if (txtLogin.toString() == agentLogIn.getUsername() || txtPassword.toString() == agentLogIn.getPassword()) {
                    AlertCreator.SuccessAlert("You are logged in");
                    btnCustomers.setDisable(true);
                    btnAgents.setDisable(true);
                    btnBookings.setDisable(true);
                    btnCustomers.setDisable(true);
                    IsLoggedIn = true;
                } else {AlertCreator.FailedAlert("Enter valid UserName or Password"); return;}
            }else //if agent is logged in
                btnCustomers.setDisable(false);
                btnAgents.setDisable(false);
                btnBookings.setDisable(false);
                btnCustomers.setDisable(false);
            }

} //main class
