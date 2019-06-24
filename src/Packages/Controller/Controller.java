package Packages.Controller;

import Packages.Model.PackageType;
import Packages.NewPackage;
import Packages.Package;
import Resources.AlertCreator;
import Resources.DBClass.DBHelper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.text.View;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.spi.DateFormatProvider;
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

    @FXML
            Button Exitbtn;
   @FXML
   Button Refresh;

List<PackageType> SavedList;


    public void LoadPacks(){
        List<PackageType> PackList = new ArrayList<>();

          PackageDataLayer Data = new PackageDataLayer();

        try {
            PackList = Data.getPackages();

            SavedList = PackList;
        } catch (SQLException e) {
            e.printStackTrace();
        }

            ObservableList<PackageType> OboList = FXCollections.observableList(PackList);

       ComboID.setItems(OboList); //Nothing

    }
    public void fill(PackageType Pkg){


    PkgNamebox.setText(Pkg.getPkgName());
    Startbox.setText(Pkg.getPkgStartDate());
    Endbox.setText(Pkg.getPkgEndDate());
    Descbox.setText(Pkg.getPkgDesc());
    Basebox.setText(Long.toString(Pkg.getPkgBasePrice()));
    Combox.setText(Long.toString(Pkg.getPkgAgencyCom()));
    }

    public void Refresh(){
        ComboID.setItems(null);
        this.LoadPacks();

    }


    @FXML
    void CloseFunc (){
        Stage stage = (Stage) Exitbtn.getScene().getWindow();
        // do what you have to do
        stage.close();
    }
    @FXML
    void AddbtnClick(ActionEvent event){
    //Open new window allowing for an insert on sql
        loadWindow("/Packages/View/NewPackage.fxml","New Package");

        }
    //load window function
    public  void loadWindow(String loc, String title){
        try{
            Parent parent = FXMLLoader.load(getClass().getResource(loc));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle(title);
            stage.setScene(new Scene(parent));

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }






    @FXML
    void DeletebtnClick(ActionEvent event) throws SQLException {
    //runsql
        int ID = ComboID.getValue().getPkgId();
       // System.out.println(ID);
            boolean result = PackageDataLayer.deletePkg(ID);
        if(result == false){
            AlertCreator.FailedAlert("Cannot delete as this entry has dependencies");
        }
        else {
            AlertCreator.SuccessAlert("Package Deleted");

            Refresh();
        }


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
    void SavebtnClick(ActionEvent event) throws SQLException {
    //runsql

        PkgNamebox.setEditable(false);
        Startbox.setEditable(false);
        Endbox.setEditable(false);
        Descbox.setEditable(false);
        Basebox.setEditable(false);
        Combox.setEditable(false);
        Savebtn.setDisable(true);

        PackageType UpdatedPack = new PackageType();


        UpdatedPack.setPkgId(ComboID.getValue().getPkgId());
        UpdatedPack.setPkgName(PkgNamebox.getText());
        UpdatedPack.setPkgDesc(Descbox.getText());
        UpdatedPack.setPkgBasePrice(Long.parseLong(Basebox.getText()));
        UpdatedPack.setPkgAgencyCom(Long.parseLong(Combox.getText()));
        UpdatedPack.setPkgStartDate(Startbox.getText());
        UpdatedPack.setPkgEndDate(Endbox.getText());
        System.out.println(Endbox.getText());
          boolean result =  PackageDataLayer.EditPkg(UpdatedPack);
       if(result == false){
            AlertCreator.FailedAlert("Update Failed");
        }
else {
            AlertCreator.SuccessAlert("Update Success");
           Refresh();
       }
    }

    @FXML
    void initialize() {
        this.LoadPacks();

        //   ComboID.addEventHandler(selectChange);
        ComboID.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {

                //List<PackageType> PackList = new ArrayList<>();
               //System.out.println(ComboID.getValue().getPkgId());
                //PackageDataLayer Data = new PackageDataLayer();

               // System.out.print(SavedList.get(2).toString());
                for (PackageType pack:SavedList
                ) {
                    if(pack.getPkgName() == newValue.toString()){

                        fill(pack);

                    }

                }


            }
        });
    }


}
