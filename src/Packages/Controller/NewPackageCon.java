package Packages.Controller;

import Packages.Model.PackageType;
import Resources.AlertCreator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;


public class NewPackageCon {

    @FXML
    Button PkgAdd;

    @FXML
    Button PkgClear;

    @FXML
    TextField PkgName;

    @FXML
    TextField PkgStart;

    @FXML
    TextField PkgEnd;

    @FXML
    TextField PkgBase;

    @FXML
    TextField PkgCom;

    @FXML
    TextArea PkgDesc;
    @FXML
    Button Exitbtn;

    @FXML
    void PkgAddClick(ActionEvent event){
        boolean result = true;
        PackageType NewPack = new PackageType();
        try {
            NewPack.setPkgName(PkgName.getText());
            NewPack.setPkgStartDate(PkgStart.getText());
            NewPack.setPkgEndDate(PkgEnd.getText());
            NewPack.setPkgDesc(PkgDesc.getText());
            NewPack.setPkgBasePrice(Integer.parseInt(PkgBase.getText()));
            NewPack.setPkgAgencyCom(Integer.parseInt(PkgCom.getText()));
        }
        catch(Exception e){
            AlertCreator.FailedAlert("Invalid Entries");
           result = false;
        }
        try {
            PackageDataLayer.insertPkg(NewPack);

        }catch(Exception e){
            e.printStackTrace();
        }
        if (result == true){ AlertCreator.SuccessAlert("Insertion Success");}


    }


    @FXML
    void CloseFunc (){
        Stage stage = (Stage) Exitbtn.getScene().getWindow();
        // do what you have to do
        stage.close();
    }



    @FXML
    void initialize() {

    }
    @FXML
    void Clear(){
        //Clears the boxes
        PkgName.setText("");
        PkgStart.setText("");
        PkgEnd.setText("");
        PkgDesc.setText("");
        PkgBase.setText("");
        PkgCom.setText("");




    }

}
