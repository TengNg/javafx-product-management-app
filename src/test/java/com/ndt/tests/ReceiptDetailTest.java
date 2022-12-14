package com.ndt.tests;

import com.ndt.config.JdbcUtils;
import com.ndt.pojo.ReceiptDetail;
import com.ndt.services.ReceiptDetailService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class ReceiptDetailTest {
    private static Connection conn;

    private static ReceiptDetailService receiptDetailService = new ReceiptDetailService();

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
    @DisplayName("Is getting unique receipt detail")
    public void checkUniqueReceipts() throws SQLException {
        Set<ReceiptDetail> set = new HashSet<>(receiptDetailService.getReceiptDetails());
        Assertions.assertEquals(set.size(), receiptDetailService.getReceiptDetails().size());
    }

    @ParameterizedTest
    @CsvSource({"1,2", "2,1", "3,2", "4,0"})
    public void checkUniqueReceiptDetailById(int receiptId, int nReceitDetail) throws SQLException {
        Assertions.assertEquals(receiptDetailService.getReceiptDetailsById(receiptId).size(), nReceitDetail);
    }

}
