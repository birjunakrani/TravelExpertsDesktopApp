package Packages.Controller;

import Packages.Model.PackageType;
import Resources.DBClass.DBHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    @FXML
    Button Editbtn;

    @FXML
    Button Savebtn;

    @FXML
    Button Addbtn;

    @FXML
    Button Deletebtn;

    @FXML
    TextField PkgNamebox;

    @FXML
    TextField Startbox;

    @FXML
    TextField Endbox;

    @FXML
    TextArea Descbox;

    @FXML
    TextField Basebox;

    @FXML
    TextField Combox;

    @FXML
    ComboBox<PackageType> ComboID;




    public void LoadPacks(){
        List<PackageType> PackList = new ArrayList<>();

          PackageDataLayer Data = new PackageDataLayer();

        try {
            PackList = Data.getPackages();


        } catch (SQLException e) {
            e.printStackTrace();
        }
       // System.out.print(PackList.get(1).toString()); passed
            ObservableList<PackageType> OboList = FXCollections.observableList(PackList);
      //  System.out.print(ObList.get(1).toString()); //passed
       ComboID.setItems(OboList); //Nothing

    }

    @FXML
    void AddbtnClick(ActionEvent event) {
    //runsql




    }

    @FXML
    void DeletebtnClick(ActionEvent event) {
    //runsql
    }

    @FXML
    void EditbtnClick(ActionEvent event) {
        PkgNamebox.setEditable(true);
        Startbox.setEditable(true);
        Endbox.setEditable(true);
        Descbox.setEditable(true);
        Basebox.setEditable(true);
        Combox.setEditable(true);
        Savebtn.setDisable(false);
    }

    @FXML
    void SavebtnClick(ActionEvent event) {
    //runsql

        PkgNamebox.setEditable(false);
        Startbox.setEditable(false);
        Endbox.setEditable(false);
        Descbox.setEditable(false);
        Basebox.setEditable(false);
        Combox.setEditable(false);
        Savebtn.setDisable(true);

    }

    @FXML
    void initialize() {
        this.LoadPacks();
    }


}
