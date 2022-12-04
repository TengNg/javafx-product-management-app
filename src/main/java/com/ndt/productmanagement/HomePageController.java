package com.ndt.productmanagement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class HomePageController {
    @FXML private Button btnMcustomer;
    @FXML private Button btnMproduct;
    @FXML private Button btnMreceipt;
    @FXML private  Button btnStatistic;
    @FXML private  Button btnPrevious;

    public void previousPage(ActionEvent event) throws IOException {
        EnvironmentController evm = new EnvironmentController();
        evm.switchScene(event, "Login.fxml");
    }

    public void switchManageProduct(ActionEvent event) throws IOException {
        EnvironmentController evm = new EnvironmentController();
        evm.switchScene(event, "ManageProduct.fxml");
    }

    public void switchToCustomerView(ActionEvent event) throws IOException {
        EnvironmentController evm = new EnvironmentController();
        evm.switchScene(event, "ManageCustomer.fxml");
    }

    public void switchToReceiptView(ActionEvent event) throws IOException {
        EnvironmentController evm = new EnvironmentController();
        evm.switchScene(event, "ManageReceipt.fxml");
    }

    public void switchToStatisticView(ActionEvent event) throws IOException {
        EnvironmentController evm = new EnvironmentController();
        evm.switchScene(event, "StatisticInformation.fxml");
    }
}
