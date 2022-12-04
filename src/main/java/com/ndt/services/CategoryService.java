package com.ndt.services;

import com.ndt.config.JdbcUtils;
import com.ndt.pojo.Category;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CategoryService {
    public List<Category> getCategories() throws SQLException {
        List<Category> categories = new ArrayList<>();
        Connection conn = JdbcUtils.getConn();
        String query = "SELECT * FROM categories";
        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery(query);
        while (rs.next()) {
            Category c = new Category(rs.getInt(1), rs.getString(2));
            categories.add(c);
        }
        return categories;
    }
}
