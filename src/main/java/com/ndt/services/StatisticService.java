package com.ndt.services;

import com.ndt.config.JdbcUtils;
import com.ndt.pojo.Customer;
import com.ndt.pojo.statistic.Table1;
import com.ndt.pojo.statistic.Table2;
import com.ndt.pojo.statistic.Table3;
import com.ndt.pojo.Product;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StatisticService {
    public List<Table1> getTopSellingProduct() throws SQLException {
        String sql = """
                SELECT receipt_id, product_id, sum(quantity)
                FROM receipt_details
                GROUP BY product_id
                ORDER BY sum(quantity) desc
                LIMIT 10""";

        Connection conn = JdbcUtils.getConn();
        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery(sql);

        List<Table1> result = new ArrayList<>();
        ProductService productService = new ProductService();

        while (rs.next()) {
            Product p = productService.getProductById(rs.getString(2));
            result.add(new Table1(p.getId(), p.getName(), p.getCategoryId(), rs.getInt(3)));
        }

        return result;
    }

    public List<Table2> getTopCustomers() throws SQLException {

        // ===> Check sql <===

        String sql = """
                SELECT c.id, c.name, c.age, c.gender, count(r.customer_id)
                FROM receipts r
                LEFT JOIN customers c on r.customer_id = c.id
                GROUP BY r.customer_id
                ORDER BY count(r.customer_id) desc
                LIMIT 10""";

        Connection conn = JdbcUtils.getConn();
        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery(sql);

        List<Table2> result = new ArrayList<>();
        while (rs.next()) {
            Customer customer = new Customer(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getInt(3),
                    rs.getString(4)
            );
            result.add(new Table2(customer, rs.getInt(5)));
        }

        return result;
    }

    public List<Table3> getProductsWithFreqInEachCategory() throws SQLException {
        String sql = """
                SELECT c.id, p.id, p.name,count(category_id)
                FROM products p
                RIGHT JOIN categories c ON p.category_id = c.id
                GROUP BY c.id""";

        Connection conn = JdbcUtils.getConn();
        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery(sql);

        List<Table3> lines = new ArrayList<>();
        while (rs.next()) {
            int categoryId = rs.getInt(1);
            String productId = rs.getString(2);
            String productName = rs.getString(3);
            int freq = rs.getInt(4);

            if (productId == null) {
               productId = "None";
               productName = "None";
            }

            Table3 line = new Table3(
                    categoryId,
                    productId,
                    productName,
                    freq
            );
            lines.add(line);
        }

        return lines;
    }
}
