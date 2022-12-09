package com.ndt.productmanagement;

import com.ndt.services.StatisticService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class StatisticInformationController implements Initializable {

    @FXML
    TableView tbTopCustomers;

    @FXML
    TableView tbTopSellingProducts;

    @FXML
    TableView tbProductFreq;

    public void previousPage(ActionEvent event) throws IOException {
        EnvironmentController evm = new EnvironmentController();
        evm.switchScene(event, "HomePage.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.loadTable1View();
        this.loadTable2View();
        this.loadTable3View();

        try {
            this.loadTable1Data();
            this.loadTable2Data();
            this.loadTable3Data();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadTable1View () {
        TableColumn col1 = new TableColumn("Product Id");
        col1.setCellValueFactory(new PropertyValueFactory("productId"));
        col1.setPrefWidth(200);

        TableColumn col2 = new TableColumn("Product Name");
        col2.setCellValueFactory(new PropertyValueFactory("productName"));
        col2.setPrefWidth(200);

        TableColumn col3 = new TableColumn("Category Id");
        col3.setCellValueFactory(new PropertyValueFactory("categoryId"));
        col3.setPrefWidth(100);

        TableColumn col4 = new TableColumn("Amount Sold");
        col4.setCellValueFactory(new PropertyValueFactory("amountSold"));
        col4.setPrefWidth(200);

        this.tbTopSellingProducts.getColumns().addAll(col1, col2, col3, col4);
    }

    public void loadTable2View() {
        TableColumn col1 = new TableColumn("Customer Id");
        col1.setCellValueFactory(new PropertyValueFactory("id"));
        col1.setPrefWidth(100);
        TableColumn col2 = new TableColumn("Customer Name");
        col2.setCellValueFactory(new PropertyValueFactory("name"));
        col2.setPrefWidth(200);

        TableColumn col3 = new TableColumn("Age");
        col3.setCellValueFactory(new PropertyValueFactory("age"));
        col3.setPrefWidth(100);

        TableColumn col4 = new TableColumn("Gender");
        col4.setCellValueFactory(new PropertyValueFactory("gender"));
        col4.setPrefWidth(200);

        TableColumn col5 = new TableColumn("Receipt Count");
        col5.setCellValueFactory(new PropertyValueFactory("nReceipts"));
        col5.setPrefWidth(100);
        this.tbTopCustomers.getColumns().addAll(col1, col2, col3, col4, col5);
    }

    public void loadTable3View() {
        TableColumn col1 = new TableColumn("Category");
        col1.setCellValueFactory(new PropertyValueFactory("category"));
        col1.setPrefWidth(200);

        TableColumn col2 = new TableColumn("ProductId");
        col2.setCellValueFactory(new PropertyValueFactory("productId"));
        col2.setPrefWidth(200);

        TableColumn col3 = new TableColumn("Product Name");
        col3.setCellValueFactory(new PropertyValueFactory("productName"));
        col3.setPrefWidth(200);

        TableColumn col4 = new TableColumn("Frequency");
        col4.setCellValueFactory(new PropertyValueFactory("frequency"));
        col4.setPrefWidth(200);

        this.tbProductFreq.getColumns().addAll(col1, col2, col3, col4);
    }

    public void loadTable1Data() throws SQLException {
        StatisticService statisticService = new StatisticService();
        this.tbTopSellingProducts.setItems(FXCollections.observableList(statisticService.getTopSellingProduct()));
    }

    public void loadTable2Data() throws SQLException {
        StatisticService statisticService = new StatisticService();
        this.tbTopCustomers.setItems(FXCollections.observableList(statisticService.getTopCustomers()));
    }

    public void loadTable3Data() throws SQLException {
        StatisticService statisticService = new StatisticService();
        this.tbProductFreq.setItems(FXCollections.observableList(statisticService.getProductsWithFreqInEachCategory()));
    }
}
