package com.ndt.productmanagement;

import com.ndt.pojo.Receipt;
import com.ndt.pojo.ReceiptDetail;
import com.ndt.services.ReceiptDetailService;
import com.ndt.services.ReceiptService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ManageReceiptController implements Initializable {
    @FXML private Stage stage;

    @FXML private Button btnFullScreen;

    @FXML private TableView<Receipt> tbReceipt;

    private Boolean checkFullScreen = true;

    private ReceiptService receiptService = new ReceiptService();

    public void fullScreen(ActionEvent event) {
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        if (!checkFullScreen){
            btnFullScreen.setText("Full screen");
            checkFullScreen = !checkFullScreen;
        }
        else {
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.loadReceiptTableView();
        try {
            this.loadReceiptData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadReceiptTableView() {
        TableColumn col1 = new TableColumn("Receipt ID");
        col1.setCellValueFactory(new PropertyValueFactory("receiptId"));
        col1.setPrefWidth(200);

        TableColumn col2 = new TableColumn("Customer ID");
        col2.setCellValueFactory(new PropertyValueFactory("customerId"));
        col2.setPrefWidth(200);

        TableColumn col3 = new TableColumn("Status");
        col3.setCellValueFactory(new PropertyValueFactory("status"));
        col3.setPrefWidth(200);

        TableColumn col4 = new TableColumn("Valid");
        col4.setCellValueFactory(new PropertyValueFactory("isValid"));
        col4.setPrefWidth(200);

        this.tbReceipt.getColumns().addAll(col1, col2, col3, col4);
    }

    public void loadReceiptData() throws SQLException {
        this.tbReceipt.setItems(FXCollections.observableList(this.receiptService.getReceipts()));
    }

    public void checkReceiptDetailHandler() throws SQLException {
        Receipt selectedReceipt = tbReceipt.getSelectionModel().getSelectedItem();

        if (selectedReceipt == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Khong tim thay hoa don muon xem thong tin");
            alert.setContentText("Vui long chon hoa don muon xem thong tin");
            ButtonType okBtn = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            alert.getButtonTypes().setAll(okBtn);
            alert.showAndWait();
            return;
        }

        ReceiptDetailService receiptDetailsService = new ReceiptDetailService();
        int receiptId = selectedReceipt.getReceiptId();
        StringBuilder str = new StringBuilder();

        List<ReceiptDetail> receiptDetails = receiptDetailsService.getReceiptDetailsById(selectedReceipt.getReceiptId());

        if (receiptDetails.size() > 0) {
            for (ReceiptDetail p : receiptDetails) {
                str.append(" - Ma san pham: " + p.getProduct().getId() + "\n");
                str.append(" - Ten san pham: " + p.getProduct().getName() + "\n");
                str.append(" - Gia san pham: " + p.getProduct().getPrice() + "\n");
                str.append(" - Loai san pham: " + p.getProduct().getCategoryId() + "\n");
                str.append(" - So luong: " + p.getQuantity() + "\n");
                if (!p.getIsOk())
                    str.append(" => * Het hang *\n");
                str.append(" ======================================= \n");
            }
            str.append("\n ===> Tong so tien: " + String.valueOf(receiptDetailsService.calculateTotalPrice(receiptId)) + "\n");
        } else {
            str.append("<< Empty >>");
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thong tin cac san pham trong hoa don");
        alert.setContentText(str.toString());
        alert.setHeaderText("Thong tin");
        ButtonType okBtn = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(okBtn);
        alert.showAndWait();
    }

    public void deleteReceiptHandler() throws SQLException {
        Receipt selectedItem = tbReceipt.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Vui long chon hoa don muon xoa");
            alert.setHeaderText("Thong tin");
            ButtonType okBtn = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            alert.getButtonTypes().setAll(okBtn);
            alert.showAndWait();
            return;
        }
        int receiptId = selectedItem.getReceiptId();

        Receipt receipt = this.receiptService.getReceiptById(receiptId);

        ReceiptDetailService receiptDetailService = new ReceiptDetailService();

        List<ReceiptDetail> receiptDetails = receiptDetailService.getReceiptDetailsById(receipt.getReceiptId());

        if (!receipt.getIsValid() || receipt.getStatus().equals("Unchecked")) {
            this.receiptService.deleteReceiptById(receiptId);
        } else {
            receiptDetailService.retrieveProductQuantity(receiptDetails);
            this.receiptService.deleteReceiptById(receiptId);
        }

        this.loadReceiptData();
        tbReceipt.refresh();
    }

    public void checkReceiptsHandler() throws SQLException {
        if (this.receiptService.isAllReceiptsChecked(this.receiptService.getReceipts())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Tat ca hoa don da duoc kiem tra");
            ButtonType okBtn = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            alert.getButtonTypes().setAll(okBtn);
            alert.showAndWait();
            return;
        }
        this.tbReceipt.setItems(FXCollections.observableList(this.receiptService.checkReceipts()));
        this.tbReceipt.refresh();
    }

    public void reset() throws SQLException {
        this.receiptService.reset();
        this.tbReceipt.setItems(FXCollections.observableList(this.receiptService.getReceipts()));
        this.tbReceipt.refresh();
    }
}