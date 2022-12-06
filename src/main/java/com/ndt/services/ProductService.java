package com.ndt.services;

import com.ndt.config.JdbcUtils;
import com.ndt.pojo.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductService {
    public List<Product> getProducts() throws SQLException {
        List<Product> products = new ArrayList<>();
        Connection conn = JdbcUtils.getConn();
        String query = "SELECT * FROM products";
        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery(query);
        while (rs.next()) {
            Product p = new Product(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getDouble(3),
                    rs.getInt(4),
                    rs.getInt(5)
            );
            products.add(p);
        }
        return products;
    }

    public boolean addProduct(Product p) {
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "INSERT INTO products(id, name, price, category_id, quantity) VALUES(?, ?, ?, ?, ?)";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, p.getId());
            stm.setString(2, p.getName());
            stm.setDouble(3, p.getPrice());
            stm.setInt(4, p.getCategoryId());
            stm.setInt(5, p.getQuantity());
            stm.execute();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean deleteProduct(String id) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "DELETE FROM products WHERE id = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, id);
            stm.execute();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean updateProduct(Product p) {
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "UPDATE products set name=?, price=?, quantity=?, category_id=? WHERE id=?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, p.getName());
            stm.setDouble(2, p.getPrice());
            stm.setInt(3, p.getQuantity());
            stm.setInt(4, p.getCategoryId());
            stm.setString(5, p.getId());
            stm.executeUpdate();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public Product getProductById(String id) throws SQLException {
        Connection conn = JdbcUtils.getConn();
        String sql = "SELECT * FROM products WHERE id = '" + id + "'";
        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery(sql);
        Product p = null;
        while (rs.next()) {
            p = new Product(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getDouble(3),
                    rs.getInt(4),
                    rs.getInt(5)
            );
        }
        return p;
    }

    public List<Product> getProductsByCategoryId(int categoryId) throws SQLException {
        List<Product> products = new ArrayList<>();
        Connection conn = JdbcUtils.getConn();
        String sql = "SELECT * FROM products WHERE category_id = '" + categoryId + "'";
        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery(sql);
        while (rs.next()) {
            Product p = new Product(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getDouble(3),
                    rs.getInt(4),
                    rs.getInt(5)
            );
            products.add(p);
        }
        return products;
    }

    public void updateProductQuantityById(String productId, int changedQuantity) throws SQLException {
        try {
            Connection conn = JdbcUtils.getConn();
            String sql = "UPDATE products SET quantity=? WHERE id=?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, changedQuantity);
            stm.setString(2, productId);
            stm.executeUpdate();
        } catch (Exception e) {
            System.out.println("Product id: " + productId + " - Error while updating this product's quantity");
        }
    }
}