package Packages.Controller;

import Packages.Model.PackageType;
import Resources.AlertCreator;
import Resources.DBClass.DBHelper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.awt.event.ActionListener;
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
       // System.out.print(PackList.get(1).toString()); passed
            ObservableList<PackageType> OboList = FXCollections.observableList(PackList);
      //  System.out.print(ObList.get(1).toString()); //passed
       ComboID.setItems(OboList); //Nothing

    }
    public void fill(int PackID){
        List<PackageType> PackList = new ArrayList<>();

        PackageDataLayer Data = new PackageDataLayer();
        int FoundID = 0;
        try {
            PackList = Data.getPackages();


        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (PackageType pack:PackList
             ) {
            if(pack.getPkgId()==PackID){
                FoundID=PackID;
            }

        }

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
    PkgNamebox.setText(PackList.get(FoundID).getPkgName());
    Startbox.setText(df.format((PackList.get(FoundID).getPkgStartDate())));
    Endbox.setText(df.format((PackList.get(FoundID).getPkgEndDate())));
    Descbox.setText(PackList.get(FoundID).getPkgDesc());
    Basebox.setText(Long.toString(PackList.get(FoundID).getPkgBasePrice()));
    Combox.setText(Long.toString(PackList.get(FoundID).getPkgAgencyCom()));
    }


    public void selectChg(){

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
    void SavebtnClick(ActionEvent event) throws SQLException {
    //runsql

        PkgNamebox.setEditable(false);
        Startbox.setEditable(false);
        Endbox.setEditable(false);
        Descbox.setEditable(false);
        Basebox.setEditable(false);
        Combox.setEditable(false);
        Savebtn.setDisable(true);

        PackageDataLayer SqlFunc = new PackageDataLayer();
        PackageType UpdatedPack = new PackageType();
        UpdatedPack.setPkgId(ComboID.getValue().getPkgId());
        UpdatedPack.setPkgName(PkgNamebox.getText());
        UpdatedPack.setPkgDesc(Descbox.getText());
        UpdatedPack.setPkgBasePrice(Long.parseLong(Basebox.getText()));
        UpdatedPack.setPkgAgencyCom(Long.parseLong(Combox.getText()));

        try {
            UpdatedPack.setPkgStartDate(Date.valueOf(Startbox.getText()));
            UpdatedPack.setPkgEndDate(Date.valueOf(Endbox.getText()));
            SqlFunc.EditPkg(UpdatedPack);
        }
        catch(Exception e){
            AlertCreator.FailedAlert(e.toString());
        }

    fill(ComboID.getValue().getPkgId()-1);

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
                int FoundID = 0;
                System.out.print(SavedList.get(2).toString());
                for (PackageType pack:SavedList
                ) {
                    if(pack.getPkgName() == newValue.toString()){
                        FoundID=pack.getPkgId();

                    }

                }

                fill(FoundID -1);
            }
        });
    }


}
