package SwitchBoard;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import Resources.AlertCreator;
import Resources.DBClass.DBHelper;
import Resources.Validator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import javax.swing.*;


public class MainController {

    AgentLogIn agentLogIn=null;
    Resources.DBClass.DBHelper dbhelper;
    ObservableList agentChart;
 //   PieChart agentChart;

        @FXML
        private ResourceBundle resources;

        @FXML
        private URL location;


        @FXML
        private PieChart pcAgent;

        @FXML
        private Button btnCustomers;

        @FXML
        private Button btnBookings;

        @FXML
        private Button btnPack;

        @FXML
        private Button btnAgents;

        @FXML
        private TextField txtLogin;

        @FXML
        private PasswordField txtPassword;

        @FXML
        private Button btnLogIn;

        @FXML
        private Button btnCancel;


        @FXML
        private Button btnLogout;



        @FXML
        void btnAgents(ActionEvent event) { loadWindow("../Agent/View/Agent.fxml","Agents"); }

        @FXML
        void btnCustomers(ActionEvent event) {
            loadWindow("../CustomerModule/CustomerDashBoard.fxml","Customers");
        }

        @FXML
        void btnPackagesOpen(ActionEvent event) {
            loadWindow("../Packages/View/PackageDisplay.fxml","Packages"); }


        @FXML
        void btnBookings(ActionEvent event) {

            loadWindow("../Booking/Booking.fxml","Bookings");
        }

        @FXML
        void btnCancelAction(ActionEvent event) {

            txtLogin.setText("");txtPassword.setText("");
        }

        @FXML
        void btnLoginAction(ActionEvent event) {

            boolean IsLoggedIn = false;
              if(Validator.IsProvided(txtLogin,"Username") && Validator.IsProvided(txtPassword,"Password")){

                  AgentLogIn agentLogIn = new AgentLogIn(txtLogin.getText(),txtPassword.getText());
                try {
                    IsLoggedIn = DBHelper.getInstance().AgentLogIn(agentLogIn);

                    if(!IsLoggedIn){ //if agent is not logged in

                        btnCustomers.setDisable(true);
                        btnAgents.setDisable(true);
                        btnBookings.setDisable(true);
                        btnPack.setDisable(true);
                        btnCustomers.setDisable(true);
                        btnLogout.setVisible(false);
                    }
                    else{
                        AlertCreator.SuccessAlert("Login Successfull");
                        btnCustomers.setDisable(false);
                        btnAgents.setDisable(false);
                        btnBookings.setDisable(false);
                        btnPack.setDisable(false);
                        btnCustomers.setDisable(false);
                        btnLogout.setVisible(true);
                        btnLogIn.setVisible(false);
                        btnCancel.setVisible(false);

                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else { AlertCreator.FailedAlert("Must Provide valid Username and Password"); }

        }

            @FXML
            void btnLogOutAction(ActionEvent event) {

                btnLogout.setVisible(false);
                btnLogIn.setVisible(true);
                btnCancel.setVisible(true);
                btnCustomers.setDisable(true);
                btnAgents.setDisable(true);
                btnBookings.setDisable(true);
                btnPack.setDisable(true);
                btnCustomers.setDisable(true);
                txtPassword.setText("");
                txtLogin.setText("");
                txtLogin.requestFocus();

            }


    private void initGraphics() {

         //   agentChart = new PieChart(DBHelper.getInstance().getAgentGraphicStats());
    //    agentChart = DBHelper.getInstance().getAgentGraphicStats();
         //   agentChart.setLegendSide(Side.RIGHT);
    //        pcAgent.setData(agentChart);
     //        vbChart.getChildren().add(agentChart);

    }

    public  void loadWindow(String loc, String title){
            try{
                Parent parent = FXMLLoader.load(getClass().getResource(loc));
                Stage stage = new Stage(StageStyle.DECORATED);
                stage.setTitle(title);
                stage.setScene(new Scene(parent));
             //   stage.setFullScreen(true);
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    public  void loadPackWindow(String loc, String title){
        try{
            Parent parent = FXMLLoader.load(getClass().getResource(loc));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
            //   stage.setFullScreen(true);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        @FXML
        void initialize() {
            btnCustomers.setDisable(true);
            btnAgents.setDisable(true);
            btnBookings.setDisable(true);
            btnPack.setDisable(true);
            btnCustomers.setDisable(true);
            btnLogout.setVisible(false);
      //      initGraphics();

        }




} //main class
