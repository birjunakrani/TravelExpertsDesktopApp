
package CustomerModule.Dashboard;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import CustomerModule.Customer;
import CustomerModule.CustomerDbOperations;
import CustomerModule.MainCustomer;
import Resources.AlertCreator;
import Resources.DBClass.DBHelper;
import Resources.Validator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import static CustomerModule.CustomerDbOperations.DeleteSelectedCustomer;

public class Controller {

         Customer cust,editedCust;

        @FXML
        private ResourceBundle resources;

        @FXML
        private URL location;

        @FXML
        private TableView<Customer> tvCustomers;

        @FXML
        private TableColumn< Customer,Integer> colCustId;

        @FXML
        private TableColumn<Customer, String> colFirstName;

        @FXML
        private TableColumn<Customer, String> colLastName;

        @FXML
        private TableColumn<Customer, String> colAddress;

        @FXML
        private TableColumn<Customer, String> colCity;

        @FXML
        private TableColumn<Customer, String> colProv;

        @FXML
        private TableColumn<Customer, String> colPostal;

        @FXML
        private TableColumn<Customer, String> colCountry;

        @FXML
        private TableColumn<Customer, String> colHomePhone;

        @FXML
        private TableColumn<Customer, String> colBusPhone;

        @FXML
        private TableColumn<Customer, String> colEmail;

        @FXML
        private TableColumn<Customer, Integer> colAgentId;

        @FXML
        private TextField tfFirstName;

        @FXML
        private TextField tfLastName;

        @FXML
        private TextField tfAddress;

        @FXML
        private TextField tfCity;

        @FXML
        private TextField tfProv;

        @FXML
        private TextField tfPostal;

        @FXML
        private TextField tfCountry;
        @FXML

        private TextField tfCustHomePhone;

        @FXML
        private TextField tfbizphone;// create text fields

        @FXML
        private TextField tfemail;// create text fields.

        @FXML
        private TextField tfagentId;// create fext field

        @FXML
        private Button btnEdit;

        @FXML
        private Button btnExit;

        @FXML
        private Button btnSave;

        @FXML
        private Button btnDelete;

        @FXML
        private Button btnInsert;

        @FXML
        void  btnInsertAction(ActionEvent event) {

            int count = 0;
            if (Validator.IsProvided(tfFirstName, "First Name") && Validator.IsProvided(tfLastName, "Last Name")
                    && Validator.IsProvided(tfAddress, "Address") && Validator.IsProvided(tfCity, "City")
                    && Validator.IsProvided(tfProv, "Province ") && Validator.IsProvided(tfPostal, "Postal Code ")
                    && Validator.IsProvided(tfbizphone, "Business Phone ") && Validator.IsProvided(tfemail, "Email ")&& Validator.IsNonNegative(tfagentId,"Agent ID")) {

                cust = new Customer(tfFirstName.getText(),tfLastName.getText(),tfAddress.getText(),tfCity.getText(),tfProv.getText(),tfPostal.getText(),
                tfCountry.getText(),tfCustHomePhone.getText(),tfbizphone.getText(),tfemail.getText(),Integer.parseInt(tfagentId.getText()));

                count = CustomerDbOperations.addCustomer(cust);
                if (count == 0) {
                    AlertCreator.FailedAlert("Insert Failed, Try again");
                } else {
                    RefreshListAndReload();
                    AlertCreator.SuccessAlert("Customer Inserted");

                }
            }
        }// END INSERT CUSTOMER

        @FXML
        void btnDeleteAction(ActionEvent event) {

            if((tvCustomers.getSelectionModel().getSelectedItem())==null)
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Please Select the Customer First", ButtonType.OK);
                alert.showAndWait();
                return;
            }
            boolean i = DeleteSelectedCustomer(tvCustomers.getSelectionModel().getSelectedItem().getCustomerId());
            if(i==false)
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Cannot Delete this Customer ,there are some bookings related to this customer", ButtonType.OK);
                alert.showAndWait();
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Customer Deleted", ButtonType.OK);
                alert.showAndWait();
                RefreshListAndReload();
            }

        }

        @FXML
        //This event will just display the user selection into the fields in the forms for Editing
        void btnEditAction(ActionEvent event) {
            // Retrive the slected row object and put them into a Customer object.
            if((tvCustomers.getSelectionModel().getSelectedItem())==null)
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Please Select the Customer First", ButtonType.OK);
                alert.showAndWait();
                return;
            }

            cust = tvCustomers.getSelectionModel().getSelectedItem();
            //now show the selected data in the form for editing.
            FillTheForm(cust);


        }

    private void FillTheForm(Customer cust) {

        tfFirstName.setText(cust.getCustFirstName());
        tfLastName.setText(cust.getCustLastName());
        tfAddress.setText(cust.getCustAddress());
        tfCity.setText(cust.getCustCity());
        tfPostal.setText(cust.getCustPostal());
        tfProv.setText(cust.getCustProv());
        tfCountry.setText(cust.getCustCountry());
        tfCustHomePhone.setText(cust.getCustHomePhone());
        tfbizphone.setText(cust.getCustBusPhone());
        tfemail.setText(cust.getCustEmail());
        tfagentId.setText(cust.getAgentId()+" ");


    }

    @FXML
    void btnExitAction(ActionEvent event) {
            System.exit(0);

    }

        @FXML
        //This method will update the edited fields.

        void btnSaveAction(ActionEvent event) {
            if(cust==null)
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Press Edit Before Save", ButtonType.OK);
                alert.showAndWait();
                return;
            }
            editedCust=new Customer();
           // editedCust=new Customer(tfFirstName.getText(),tfFirstName.getText(),tfAddress.getText()
           // ,tfCity.getText(),tfProv.getText(),tfPostal.getText(),tfCountry.getText(),tf);
            editedCust.setCustFirstName( tfFirstName.getText());
            editedCust.setCustLastName( tfLastName.getText());
            editedCust.setCustAddress( tfAddress.getText());
            editedCust.setCustCity( tfCity.getText());
            editedCust.setCustProv( tfProv.getText());
            editedCust.setCustCountry( tfCountry.getText());
            editedCust.setCustPostal( tfPostal.getText());
            editedCust.setCustCountry(tfCountry.getText());
            editedCust.setCustHomePhone(tfCustHomePhone.getText());
            editedCust.setCustBusPhone(tfbizphone.getText());
            editedCust.setCustEmail(tfemail.getText());
            String s1=tfagentId.getText();
            String trimedvalue=s1.trim();
            //System.out.println(s1);
            editedCust.setAgentId(Integer.parseInt(trimedvalue));
            int rowseffected=CustomerDbOperations.SaveChanges(cust,editedCust);
            if (rowseffected == 0)
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Error updating the database", ButtonType.OK);
                alert.showAndWait();
            }
            RefreshListAndReload();

        }

        @FXML
        void initialize() {

            SetUpCellFacotory();// make the view reday to get the data at right spot in the table view.

           List<Customer> c= CustomerDbOperations.RetriveCustomerData();//from data base table
           ObservableList<Customer> observablelist = FXCollections.observableList(c);
           tvCustomers.setItems(observablelist);


        }

        // we will are maping the our display with the corresponding database values
        public void SetUpCellFacotory()
        {
            colCustId.setCellValueFactory(cellData -> cellData.getValue().customerIdProperty().asObject());
            colFirstName.setCellValueFactory(cellData -> cellData.getValue().custFirstNameProperty());
            colLastName.setCellValueFactory(cellData -> cellData.getValue().custLastNameProperty());
            colAddress.setCellValueFactory(cellData -> cellData.getValue().custAddressProperty());
            colCity.setCellValueFactory(cellData -> cellData.getValue().custCityProperty());
            colProv.setCellValueFactory(cellData -> cellData.getValue().custProvProperty());
            colPostal.setCellValueFactory(cellData -> cellData.getValue().custPostalProperty());
            colCountry.setCellValueFactory(cellData -> cellData.getValue().custCountryProperty());
            colHomePhone.setCellValueFactory(cellData -> cellData.getValue().custHomePhoneProperty());
            colBusPhone.setCellValueFactory(cellData -> cellData.getValue().custBusPhoneProperty());
            colEmail.setCellValueFactory(cellData -> cellData.getValue().custEmailProperty());
            colAgentId.setCellValueFactory(cellData -> cellData.getValue().agentIdProperty().asObject());
        }

public void RefreshListAndReload()
{
    tvCustomers.setItems(null);// Clear the old list before loading the new one
    List<Customer> c= CustomerDbOperations.RetriveCustomerData();
    ObservableList<Customer> observablelist = FXCollections.observableList(c);
    tvCustomers.setItems(observablelist);
}


    }


