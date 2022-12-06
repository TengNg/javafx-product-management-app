package com.ndt.tests;

import com.ndt.config.JdbcUtils;
import com.ndt.pojo.Product;
import com.ndt.services.ProductService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class ProductTest {
    private static Connection conn;
    private static ProductService productService = new ProductService();

    private Product testingProduct = new Product(
            UUID.randomUUID().toString(),
            "testing product",
            10.0,
            2,
            10
    );

    @BeforeAll
    public static void beforeAll() throws SQLException {
        conn = JdbcUtils.getConn();
    }

    @AfterAll
    public static void afterAll() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }

    @Test
    @Tag("important")
    @DisplayName("Is getting unique products")
    public void checkUniqueProducts() throws SQLException {
        String query = "SELECT * FROM products";
        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery(query);
        List<String> products = new ArrayList<>();
        while (rs.next()) {
            String name = rs.getString(1);
            products.add(name);
        }
        Set<String> set = new HashSet<>(products);
        Assertions.assertEquals(set.size(), products.size());
    }

    @Test
    @DisplayName("Is getting correct product by product id")
    public void checkIsGettingCorrectProductById() throws SQLException {
        Product real = productService.getProductById("3c2c3a2f-4b64-46a0-88cb-00e67b2ddf18");
        Product expected1 = new Product(
                "3c2c3a2f-4b64-46a0-88cb-00e67b2ddf18",
                "ProductA-01",
                10.00,
                1,
                20
        );

        Product expected2 = new Product(
                "3c2c3a2f-4b64-46a0-88cb-00e67b2ddf18",
                "ProductABCD",
                10.00,
                1,
                20
        );

        Assertions.assertEquals(real, expected1);
        Assertions.assertNotEquals(real, expected2);
    }

    @ParameterizedTest
    @DisplayName("Checking correct product's quantity in each category")
    @CsvSource({"1,1", "2,1", "3,1", "4,1", "5,0"}) // [<CategoryId>,<ProductFreq>]
    public void checkNProductsByCategory(int categoryId, int expected) throws SQLException {
        Assertions.assertEquals(productService.getProductsByCategoryId(categoryId).size(), expected);
    }

    @Test
    @DisplayName("Checking product CRUD method")
    public void checkCUD() throws SQLException {
        int currentSize = productService.getProducts().size();
        Assertions.assertTrue(this.isProductAdded());

        Product currentProduct = this.testingProduct;
        Product updatedProduct = new Product(this.testingProduct);
        updatedProduct.setName("new testing product");

        Assertions.assertTrue(this.isProductUpdated(updatedProduct));
        Assertions.assertNotEquals(currentProduct, updatedProduct);
        Assertions.assertEquals(productService.getProductById(this.testingProduct.getId()), updatedProduct);

        Assertions.assertTrue(this.isProductDeleted());
        int changedSize = productService.getProducts().size();
        Assertions.assertEquals(currentSize, changedSize);
    }

    public boolean isProductAdded() {
        return productService.addProduct(this.testingProduct);
    }

    public boolean isProductDeleted() throws SQLException { return productService.deleteProduct(this.testingProduct.getId()); }

    public boolean isProductUpdated(Product p) throws SQLException { return productService.updateProduct(p); }
}