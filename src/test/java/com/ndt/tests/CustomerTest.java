package com.ndt.tests;

import com.ndt.config.JdbcUtils;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CustomerTest {
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
    @DisplayName("Is getting unique customers")
    public void checkUniqueCustomers() throws SQLException {
        String query = "SELECT * FROM customers";
        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery(query);
        List<Integer> customers = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt(1);
            customers.add(id);
        }
        Set<Integer> set = new HashSet<>(customers);
        Assertions.assertEquals(set.size(), customers.size());
    }
}