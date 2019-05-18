package Agent.Main;
import TravelExperts.Agent.Controller.AgentCtrl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class AgentForm extends Application {

    AgentCtrl controller;
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(AgentForm.class.getResource("../View/agent.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 800, 500);
        primaryStage.setTitle("Agent Form");
        primaryStage.setScene(scene);
        scene.getStylesheets().add("StylesSheet.css");
        primaryStage.show();
        controller = loader.getController();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
