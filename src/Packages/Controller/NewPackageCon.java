package Packages.Controller;

import Packages.Model.PackageType;
import Resources.AlertCreator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

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
    void PkgAddClick(ActionEvent event){

        PackageType NewPack = new PackageType();
        NewPack.setPkgName(PkgName.getText());
        NewPack.setPkgStartDate(PkgStart.getText());
        NewPack.setPkgEndDate(PkgEnd.getText());
        NewPack.setPkgDesc(PkgDesc.getText());
        NewPack.setPkgBasePrice(Integer.parseInt(PkgBase.getText()));
        NewPack.setPkgAgencyCom(Integer.parseInt(PkgEnd.getText()));

        try {
            PackageDataLayer.insertPkg(NewPack);
        } catch (Exception e) {
            e.printStackTrace();
            AlertCreator.FailedAlert("Insert Failed");
        }

    }



}
