package Agent;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainAgent extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        //       FXMLLoader loader = new FXMLLoader();
        //    loader.setLocation(Main.class.getResource("SwitchBoard.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("View/Agents.fxml"));
        Scene scene = new Scene(root, 800, 500);
        primaryStage.setTitle("Agent Form");
        primaryStage.setScene(scene);
        scene.getStylesheets().add("StylesSheet.css");
        primaryStage.show();
        //     controller = loader.getController();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

