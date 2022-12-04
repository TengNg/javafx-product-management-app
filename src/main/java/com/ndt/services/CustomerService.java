package com.ndt.services;

import com.ndt.config.JdbcUtils;
import com.ndt.pojo.Category;
import com.ndt.pojo.Customer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CustomerService {
    public List<Customer> getCustomers() throws SQLException {
        List<Customer> customers = new ArrayList<>();
        Connection conn = JdbcUtils.getConn();
        String query = "SELECT * FROM customers";
        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery(query);
        while (rs.next()) {
            Customer c = new Customer(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getInt(3),
                    rs.getString(4));
            customers.add(c);
        }
        return customers;
    }

    public Customer getCustomerById(int id) throws SQLException {
        Connection conn = JdbcUtils.getConn();
        String sql = "SELECT * FROM customers WHERE id = " + id;
        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery(sql);
        Customer c = null;
        while (rs.next()) {
            c = new Customer(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getInt(3),
                    rs.getString(4)
            );
        }
        return c;
    }
}
