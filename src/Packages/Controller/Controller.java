package Packages.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.awt.*;
import java.awt.event.MouseEvent;

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
