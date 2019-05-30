package Packages;

import Packages.Controller.Controller;
import Resources.DBClass.DBHelper;
import SwitchBoard.Main;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Package extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
              FXMLLoader loader = new FXMLLoader();
        //   loader.setLocation(Package.class.getResource("PackageDisplay.fxml"));
      //  Parent root = FXMLLoader.load(getClass().getResource("View/PackageDisplay.java"));
       Parent root = loader.load(getClass().getResource("View/PackageDisplay.fxml"));
        Scene scene = new Scene(root, 612, 512);
        primaryStage.setTitle("Package Form");
        primaryStage.setScene(scene);
        scene.getStylesheets().add("StylesSheet.css");
        primaryStage.show();




    }


    public static void main(String[] args) {
        launch(args);
        Controller Boot = new Controller();
        Boot.LoadPacks();








    }
}
