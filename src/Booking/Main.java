/*
Author: Mahda Kazemian
Date: May 19,2019
Course Module : PROJ-207-OSD - Threaded Project
Assignment : Team1- Workshop6
purpose: Main Class to run the application
 */
package Booking;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Booking.fxml"));
        primaryStage.setTitle("Booking information");
        primaryStage.setScene(new Scene(root, 600, 500));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
