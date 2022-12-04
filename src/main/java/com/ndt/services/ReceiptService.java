package com.ndt.services;

import com.ndt.config.JdbcUtils;
import com.ndt.pojo.Receipt;
import com.ndt.pojo.ReceiptDetail;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReceiptService {
    public List<Receipt> getReceipts() throws SQLException {
        Connection conn = JdbcUtils.getConn();
        String query = "SELECT * FROM receipts ORDER BY id";
        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery(query);
        List<Receipt> receipts = new ArrayList<>();
        while (rs.next()) {
            int receiptId = rs.getInt(1);
            int customerId = rs.getInt(2);
            String status = rs.getString(3);
            boolean isValid = rs.getBoolean(4);
            Receipt receipt = new Receipt(receiptId, customerId, isValid, status);
            receipts.add(receipt);
        }
        return receipts;
    }

    public Receipt getReceiptById(int id) throws SQLException {
        Connection conn = JdbcUtils.getConn();
        String query = "SELECT * FROM receipts WHERE id = " + id;
        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery(query);
        Receipt r = null;
        while (rs.next()) {
            int receiptId = rs.getInt(1);
            int customerId = rs.getInt(2);
            r = new Receipt(receiptId, customerId);
        }
        return r;
    }

    public void deleteReceiptById(int id) throws SQLException {
        Connection conn = JdbcUtils.getConn();
        String sql1 = "DELETE FROM receipt_details WHERE receipt_id = ?";
        String sql2 = "DELETE FROM receipts WHERE id = ?";
        PreparedStatement stm1 = conn.prepareStatement(sql1);
        PreparedStatement stm2 = conn.prepareStatement(sql2);
        stm1.setInt(1, id);
        stm2.setInt(1, id);
        stm1.execute();
        stm2.execute();
    }

    public List<Receipt> updateReceipts() throws SQLException {
        ProductService productService = new ProductService();
        ReceiptDetailService receiptDetailService = new ReceiptDetailService();

        List<Receipt> result = new ArrayList<>();
        for (Receipt receipt : this.getReceipts()) {
            if (!receipt.getStatus().equals("Unchecked"))
                continue;

            int receiptId = receipt.getReceiptId();
            List<ReceiptDetail> details = receiptDetailService.getReceiptDetailsById(receiptId);

            for (ReceiptDetail r : details) {
                String productId = r.getProduct().getId();
                int currAmountInReceipt = r.getQuantity();
                int currProductQuantity = r.getProduct().getQuantity();

                r.setChecked(true);

                if (currAmountInReceipt > currProductQuantity) {
                    r.setOk(false);
                    receipt.setValid(false);
                    receiptDetailService.updateIsOk(r.getReceiptId(), r.getProduct().getId(), false);
                    this.updateIsValid(receiptId, false);
                    break;
                }

                int changedValue = currProductQuantity - currAmountInReceipt;
                productService.updateProductQuantityById(productId, changedValue);
            }

            if (!receipt.getIsValid()) {
                receipt.setStatus("Invalid");
                this.updateStatus(receiptId, "Invalid");
                receiptDetailService.retrieveProductQuantity(details);
            } else {
                receipt.setStatus("OK");
                this.updateStatus(receiptId, "OK");
            }

            result.add(receipt);
        }

        return result;
    }

    public void updateStatus(int id, String status) throws SQLException {
        Connection conn = JdbcUtils.getConn();
        String sql = "UPDATE receipts set status=? WHERE id=?";
        PreparedStatement stm = conn.prepareStatement(sql);
        stm.setString(1, status);
        stm.setInt(2, id);
        stm.executeUpdate();
    }

    public void updateIsValid(int id, boolean isOk) throws SQLException {
        Connection conn = JdbcUtils.getConn();
        String sql = "UPDATE receipts set is_valid=? WHERE id=?";
        PreparedStatement stm = conn.prepareStatement(sql);
        stm.setBoolean(1, isOk);
        stm.setInt(2, id);
        stm.executeUpdate();
    }

    public boolean isAllReceiptsChecked(List<Receipt> receipts) {
        for (Receipt r : receipts)
            if (!r.getStatus().equals("Unchecked"))
                return true;
        return false;
    }

    public void reset() throws SQLException {
        ReceiptService receiptService = new ReceiptService();
        ReceiptDetailService receiptDetailService = new ReceiptDetailService();
        ProductService productService = new ProductService();

        for (Receipt receipt : this.getReceipts()) {
            if (receipt.getStatus().equals("Unchecked"))
                continue;

            if (receipt.getStatus().equals("Invalid")) {
                this.updateStatus(receipt.getReceiptId(), "Unchecked");
                continue;
            }

            List<ReceiptDetail> receiptDetails = receiptDetailService.getReceiptDetailsById(receipt.getReceiptId());
            for (ReceiptDetail r : receiptDetails) {
                if (!r.getIsOk()) {
                    continue;
                }
                String productId = r.getProduct().getId();
                int currQuantity = r.getProduct().getQuantity();
                int currAmount = r.getQuantity();
                int changedValue =  currQuantity + currAmount;
                receiptDetailService.updateIsOk(r.getReceiptId(), r.getProduct().getId(), true);
                productService.updateProductQuantityById(productId, changedValue);
            }

            receiptService.updateIsValid(receipt.getReceiptId(), true);
            receiptService.updateStatus(receipt.getReceiptId(), "Unchecked");
        }
    }
}
