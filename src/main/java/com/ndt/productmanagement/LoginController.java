package com.ndt.productmanagement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML private Button btnSignin;
    @FXML private Stage stage;
    @FXML private Label lbChangePass;
    @FXML private Hyperlink hplChangePass;
    @FXML private PasswordField txtPassword;
    @FXML private TextField txtUsername;
    @FXML private TextField txtShowPasswordField;
    @FXML private CheckBox ckbShowPassword;
    public static String passwordChanged = "";
    private String username;
    private String password;
    public static String defaultPassword = "Admin@123";

    public void handle(KeyEvent key, ActionEvent event) throws IOException {
        if (key.getCode().equals(KeyCode.ENTER)) {
            switchSceneHomePage(event);
        }
    }

    public void switchSceneHomePage(ActionEvent event) throws IOException {
        username = txtUsername.getText();
        password = txtPassword.getText();
        if(username.equals("") && password.equals("")){
            EnvironmentController evm = new EnvironmentController();
            evm.noti(event, "Vui long nhap day du ten nguoi dung va mat khau");
            return;
        }
        if(username.equals("")){
            EnvironmentController evm = new EnvironmentController();
            evm.noti(event, "Vui long nhap ten nguoi dung");
            return;
        }
        else if(password.equals("")){
            EnvironmentController evm = new EnvironmentController();
            evm.noti(event, "Vui long nhap mat khau");
            return;
        }

        if(passwordChanged.equals("") && username.equals("admin") && password.equals(defaultPassword)){
            EnvironmentController evm = new EnvironmentController();
            evm.switchScene( event,"HomePage.fxml");
            evm.noti(event, "Dang nhap thanh cong");
        }
        else if(!passwordChanged.equals("") && username.equals("admin") && password.equals(passwordChanged)){
            EnvironmentController evm = new EnvironmentController();
            evm.switchScene( event,"HomePage.fxml");
            evm.noti(event, "Dang nhap thanh cong");
        }
        else{
            EnvironmentController evm = new EnvironmentController();
            evm.noti(event, "Dang nhap that bai");
            return;
        }
    }

    public void switchSceneChangePass(ActionEvent event) throws IOException {
        EnvironmentController evm = new EnvironmentController();
        evm.switchScene( event,"ChangePassword.fxml");
    }

    public void signout(Stage stage){
        EnvironmentController evm = new EnvironmentController();
        evm.signOut(stage);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtUsername.setText("admin");
        txtPassword.setText("Admin@123");
    }
}