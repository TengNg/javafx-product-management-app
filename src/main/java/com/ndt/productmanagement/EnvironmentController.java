package com.ndt.productmanagement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.IOException;

public class EnvironmentController {
    @FXML private Stage stage;
    public EnvironmentController(){

    }
    public void switchScene(ActionEvent event, String file) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(file));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void signOut(Stage stage){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Sign out");
        alert.setHeaderText("Sign out");
        alert.setContentText("Do you want to sign out?");
        if(alert.showAndWait().get() == ButtonType.OK){
            stage.close();
        }
    }

    public void noti(ActionEvent event, String content){
        Notifications noti = Notifications.create().title("Notification").text(content).graphic(null).hideAfter(Duration.seconds(5)).position(Pos.BOTTOM_RIGHT);
        noti.darkStyle();
        noti.showConfirm();
    }
}
