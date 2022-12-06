package com.ndt.tests;

import com.ndt.config.JdbcUtils;
import com.ndt.pojo.Receipt;
import com.ndt.services.ReceiptService;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class ReceiptTest {
    private static Connection conn;

    private static ReceiptService receiptService = new ReceiptService();

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
    @DisplayName("Is getting unique receipts")
    public void checkUniqueReceipts() throws SQLException {
        Set<Receipt> set = new HashSet<>(receiptService.getReceipts());
        Assertions.assertEquals(set.size(), receiptService.getReceipts().size());
    }
}