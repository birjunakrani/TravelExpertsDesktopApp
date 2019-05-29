package Packages.Controller;

import Packages.Model.PackageType;
import Resources.DBClass.DBHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;

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
    TextField Descbox;

    @FXML
    TextField Basebox;

    @FXML
    TextField Combox;

    @FXML
    ComboBox IDbox;

    ArrayList<PackageType> AllPacks;


    public void LoadPacks(){

        try {
            AllPacks = DBHelper.dbHelper.getPackages();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ObservableList<PackageType> ObList = FXCollections.observableList(AllPacks);

        IDbox.setItems(ObList);
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
        Savebtn.setEnabled(true);
    }

    @FXML
    void SavebtnClick(ActionEvent event) {
    //runsql



    }


}
