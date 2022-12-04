package com.ndt.productmanagement;


import com.ndt.services.CustomerService;
import com.ndt.services.ProductService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ManageCustomerController implements Initializable {
    @FXML private Stage stage;
    @FXML private TableView tbCustomer;
    @FXML private Button btnFullScreen;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadCustomerTableView();
        try {
            loadCustomerData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Boolean checkFullScreen = true;
    public void fullScreen(ActionEvent event) throws IOException {
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        if (!checkFullScreen){
            btnFullScreen.setText("Full screen");
            checkFullScreen = !checkFullScreen;
        }
        else{
            btnFullScreen.setText("Escape");
            checkFullScreen = !checkFullScreen;
        }
        if (!stage.isFullScreen())
            stage.setFullScreen(true);
        else
            stage.setFullScreen(false);
    }

    public void previousPage(ActionEvent event) throws IOException {
        EnvironmentController evm = new EnvironmentController();
        evm.switchScene(event, "HomePage.fxml");
    }

    public void loadCustomerTableView() {
        TableColumn col1 = new TableColumn("Id");
        col1.setCellValueFactory(new PropertyValueFactory("id"));
        col1.setPrefWidth(200);

        TableColumn col2 = new TableColumn("Customer Name");
        col2.setCellValueFactory(new PropertyValueFactory("name"));
        col2.setPrefWidth(200);

        TableColumn col3 = new TableColumn("Age");
        col3.setCellValueFactory(new PropertyValueFactory("age"));
        col3.setPrefWidth(200);

        TableColumn col4 = new TableColumn("Gender");
        col4.setCellValueFactory(new PropertyValueFactory("gender"));
        col4.setPrefWidth(200);

        this.tbCustomer.getColumns().addAll(col1, col2, col3, col4);
    }

    public void loadCustomerData() throws SQLException {
        CustomerService customerService = new CustomerService();
        this.tbCustomer.setItems(FXCollections.observableList(customerService.getCustomers()));
    }
}
