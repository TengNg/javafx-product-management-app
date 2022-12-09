package com.ndt.services;

import com.ndt.config.JdbcUtils;
import com.ndt.pojo.Product;
import com.ndt.pojo.ReceiptDetail;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReceiptDetailService {
    public List<ReceiptDetail> getReceiptDetails() throws SQLException {
        Connection conn = JdbcUtils.getConn();
        String query = "SELECT * FROM receipt_details";
        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery(query);
        List<ReceiptDetail> result = new ArrayList<>();
        ProductService productService = new ProductService();
        while (rs.next()) {
            int receiptId = rs.getInt(1);
            Product p = productService.getProductById(rs.getString(2));
            int quantity = rs.getInt(3);
            boolean isOk = rs.getBoolean(4);
            ReceiptDetail rd = new ReceiptDetail(receiptId, p, quantity, isOk);
            result.add(rd);
        }
        return result;
    }

    public List<ReceiptDetail> getReceiptDetailsById(int id) throws SQLException {
        Connection conn = JdbcUtils.getConn();
        String query = "SELECT * FROM receipt_details l LEFT JOIN products p on l.product_id = p.id WHERE l.receipt_id = " + id;
        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery(query);
        List<ReceiptDetail> result = new ArrayList<>();
        while (rs.next()) {
            int receiptId = rs.getInt(1);
            String prodId = rs.getString(2);
            int amount = rs.getInt(3);
            boolean isOk = rs.getBoolean(4);
            String name = rs.getString(6);
            double price = rs.getDouble(7);
            int categoryId = rs.getInt(8);
            int productQuantity = rs.getInt(9);
            Product p = new Product(prodId, name, price, categoryId, productQuantity);
            ReceiptDetail rd = new ReceiptDetail(receiptId, p, amount, isOk);
            result.add(rd);
        }
        return result;
    }

    public void retrieveProductQuantity(List<ReceiptDetail> list) throws SQLException {
        ProductService productService = new ProductService();
        for (ReceiptDetail r : list) {
            if (!r.isChecked()) {
                continue;
            }

            if (!r.getIsOk()) {
                continue;
            }

            String productId = r.getProduct().getId();
            int currQuantity = r.getProduct().getQuantity();
            int currAmount = r.getQuantity();
            int changedValue =  currQuantity + currAmount;
            this.updateIsOk(r.getReceiptId(), r.getProduct().getId(), true);
            productService.updateProductQuantityById(productId, changedValue);
        }
    }

    public void updateIsOk(int receiptId, String productId, boolean isOk) throws SQLException {
        Connection conn = JdbcUtils.getConn();
        String sql = "UPDATE receipt_details set is_ok=? WHERE receipt_id=? and product_id=?";
        PreparedStatement stm = conn.prepareStatement(sql);
        stm.setBoolean(1, isOk);
        stm.setInt(2, receiptId);
        stm.setString(3, productId);
        stm.executeUpdate();
    }

    public void updateAllReceiptDetailsIsOkStatus() throws SQLException {
        Connection conn = JdbcUtils.getConn();
        String sql = "UPDATE receipt_details set is_ok=1";
        PreparedStatement stm = conn.prepareStatement(sql);
        stm.executeUpdate();
    }

    public double calculateTotalPrice(int id) throws SQLException {
        double result = 0;

        for (ReceiptDetail r : this.getReceiptDetailsById(id)) {
            result += r.getQuantity() * r.getProduct().getPrice();
        }

        return result;
    }
}
