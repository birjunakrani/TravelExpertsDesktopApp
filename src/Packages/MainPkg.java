package Packages;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainPkg extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        //       FXMLLoader loader = new FXMLLoader();
        //    loader.setLocation(Main.class.getResource("SwitchBoard.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("View/PackageDisplay.fxml"));
        Scene scene = new Scene(root, 612, 512);
        primaryStage.setTitle("Package Form");
        primaryStage.setScene(scene);
        scene.getStylesheets().add("StylesSheet.css");
        primaryStage.show();
        //     controller = loader.getController();
    }


    public static void main(String[] args) {
        launch(args);



    }



}
