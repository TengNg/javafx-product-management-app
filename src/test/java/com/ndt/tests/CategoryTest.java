package com.ndt.tests;

import com.ndt.config.JdbcUtils;
import com.ndt.config.JdbcUtils;
import com.ndt.pojo.Category;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CategoryTest {
    private static Connection conn;

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
    @DisplayName("Is getting unique categories")
    public void checkUniqueCategories() throws SQLException {
        String query = "SELECT * FROM categories";
        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery(query);
        List<String> categories = new ArrayList<>();
        while (rs.next()) {
            String name = rs.getString(2);
            categories.add(name);
        }
        Set<String> set = new HashSet<>(categories);
        Assertions.assertEquals(set.size(), categories.size());
    }
}
