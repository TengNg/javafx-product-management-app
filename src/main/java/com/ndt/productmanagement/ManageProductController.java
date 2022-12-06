package com.ndt.productmanagement;

import com.ndt.pojo.Category;
import com.ndt.pojo.Product;
import com.ndt.services.CategoryService;
import com.ndt.services.ProductService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.UUID;

public class ManageProductController implements Initializable {
    @FXML private Stage stage;
    @FXML private Button btnFullScreen;
    @FXML private Button btnPrevious;

    @FXML private Button btnInsertProd;
    @FXML private Button btnDeleteProd;

    @FXML private TableView<Product> tbProduct;

    @FXML private ComboBox<Category> cbCategory;

    @FXML private TextField txtName;
    @FXML private TextField txtQuantity;
    @FXML private TextField txtPrice;

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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tbProduct.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        loadProductTableView();
        CategoryService categoryService = new CategoryService();
        try {
            cbCategory.setItems(FXCollections.observableList(categoryService.getCategories()));
            this.loadProductData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void clearingFields() {
        txtName.setText("");
        txtPrice.setText("");
        txtQuantity.setText("");
        cbCategory.getSelectionModel().clearSelection();
    }

    public void addProductHandler() {
        try {
            if (txtName.getText() == null
                    || txtPrice.getText() == null
                    || cbCategory.getSelectionModel().getSelectedItem() == null
                    || txtQuantity.getText() == null
            ) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Nhap thieu thong tin");
                alert.setContentText("Vui long dien day du thong tin");
                ButtonType buttonTypeYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
                alert.getButtonTypes().setAll(buttonTypeYes);
                alert.showAndWait();
                return;
            }

            String name = txtName.getText();
            double price = Double.parseDouble(txtPrice.getText());
            int categoryId = cbCategory.getSelectionModel().getSelectedItem().getId();
            int quantity = Integer.parseInt(txtQuantity.getText());
            Product p = new Product(
                    UUID.randomUUID().toString(),
                    name,
                    price,
                    categoryId,
                    quantity
            );
            ProductService productService = new ProductService();
            productService.addProduct(p);
            this.tbProduct.setItems(FXCollections.observableList(productService.getProducts()));
            this.clearingFields();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Nhap thong tin sai dinh dang");
            alert.setContentText("Vui long nhap lai");
            ButtonType buttonTypeYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
            alert.getButtonTypes().setAll(buttonTypeYes);
            alert.showAndWait();
        }
    }

    public void deleteProductHandler(ActionEvent event) throws SQLException {
        ObservableList<Product> productList = tbProduct.getItems();
        if(productList.isEmpty()){
            EnvironmentController evm = new EnvironmentController();
            evm.noti(event, "Table is empty!!");
            return;
        }
        if(tbProduct.getSelectionModel().getSelectedItem() == null){
            EnvironmentController evm = new EnvironmentController();
            evm.noti(event, "Please choose row to delete");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Confirm");
        alert.setContentText("Do you want to delete this row");
        alert.setHeaderText("Delete row?");
        ButtonType buttonTypeYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeCancel);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() != buttonTypeYes) {
            return;
        }

        Product selectedItem = tbProduct.getSelectionModel().getSelectedItem();
        String id = selectedItem.getId();
        ProductService productService = new ProductService();

        if (productService.deleteProduct(id)) {
            this.tbProduct.setItems(FXCollections.observableList(productService.getProducts()));
            return;
        }

        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Loi xoa san pham");
        alert.setContentText("San pham ton tai trong don hang khac");
        ButtonType okBtn = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(okBtn);
        alert.showAndWait();
    }

    public void updateProductHandler(ActionEvent event) {
        try {
            Product selectedItem = tbProduct.getSelectionModel().getSelectedItem();
            String id = selectedItem.getId();
            String name = txtName.getText();
            double price = Double.parseDouble(txtPrice.getText());
            int categoryId = cbCategory.getSelectionModel().getSelectedItem().getId();
            int quantity = Integer.parseInt(txtQuantity.getText());
            Product p = new Product(id, name, price, categoryId, quantity);
            ProductService productService = new ProductService();
            productService.updateProduct(p);
            this.tbProduct.setItems(FXCollections.observableList(productService.getProducts()));
            this.clearingFields();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Nhap thong tin sai dinh dang");
            alert.setContentText("Vui long nhap lai");
            ButtonType okBtn = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            alert.getButtonTypes().setAll(okBtn);
            alert.showAndWait();
        }
    }

    public void selectProductHandler(ActionEvent event) {
        Product selectedItem = tbProduct.getSelectionModel().getSelectedItem();
        String name = selectedItem.getName();
        String price = String.valueOf(selectedItem.getPrice());
        String quantity = String.valueOf(selectedItem.getQuantity());
        int categoryId = selectedItem.getCategoryId() - 1;
        txtName.setText(name);
        txtPrice.setText(price);
        txtQuantity.setText(quantity);
        cbCategory.getSelectionModel().select(categoryId);
    }

    public void loadProductTableView() {
        TableColumn col1 = new TableColumn("Id");
        col1.setCellValueFactory(new PropertyValueFactory("id"));
        col1.setPrefWidth(300);

        TableColumn col2 = new TableColumn("Product Name");
        col2.setCellValueFactory(new PropertyValueFactory("name"));
        col2.setPrefWidth(100);

        TableColumn col3 = new TableColumn("Price");
        col3.setCellValueFactory(new PropertyValueFactory("price"));
        col3.setPrefWidth(100);

        TableColumn col4 = new TableColumn("Category");
        col4.setCellValueFactory(new PropertyValueFactory("category"));
        col4.setPrefWidth(100);

        TableColumn col5 = new TableColumn("Quantity");
        col5.setCellValueFactory(new PropertyValueFactory("quantity"));
        col5.setPrefWidth(100);

        this.tbProduct.getColumns().addAll(col1, col2, col3, col4, col5);
    }

    public void loadProductData() throws SQLException {
        ProductService productService = new ProductService();
        this.tbProduct.setItems(FXCollections.observableList(productService.getProducts()));
    }
}