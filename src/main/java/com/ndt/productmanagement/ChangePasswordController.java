package com.ndt.productmanagement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChangePasswordController implements Initializable {
    @FXML private Button btnCancel;
    @FXML private Button btnSave;
    @FXML private TextField txtComfirmPassword;
    @FXML private TextField txtCurrentPassword;
    @FXML private TextField txtNewPassword;

    private EnvironmentController evm;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void backPageLogin(ActionEvent event) throws IOException {
        evm = new EnvironmentController();
        evm.switchScene(event, "Login.fxml");
    }

    public boolean isPasswordValid(String password) {
        String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{5,10}";
        return password.matches(pattern);
    }

    public void changePassword(ActionEvent event) throws IOException {
        String currPassword = txtCurrentPassword.getText();
        String newPassword = txtNewPassword.getText();
        String comfirmPassword = txtComfirmPassword.getText();
        if(!currPassword.equals(LoginController.defaultPassword)){
            EnvironmentController evm = new EnvironmentController();
            evm.noti(event, "Sai password hien tai");
            return;
        }
        if(!newPassword.equals(comfirmPassword) || newPassword.equals("") || comfirmPassword.equals("")){
            EnvironmentController evm = new EnvironmentController();
            evm.noti(event, "Password moi khong trung nhau!");
            return;
        }
        else{
            String currentPassword = txtNewPassword.getText();
            if (!isPasswordValid(currentPassword)) {
                System.out.println("Mat khau khong hop le");
                return;
            }
            LoginController.passwordChanged = currentPassword;
            LoginController.defaultPassword = LoginController.passwordChanged;
            EnvironmentController evm = new EnvironmentController();
            evm.noti(event, "Doi password thanh cong!!");
            //backPageLogin(event);
        }
    }
}