package Resources;

import javafx.scene.control.Alert;

public class AlertCreator {

    public static void FailedAlert(String msg){

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Operation Failed");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    public static void SuccessAlert(String msg){

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Operation Succeed");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }



}
