package com.ndt.tests;

import com.ndt.config.JdbcUtils;
import com.ndt.pojo.Customer;
import com.ndt.pojo.Product;
import com.ndt.pojo.statistic.Table1;
import com.ndt.pojo.statistic.Table2;
import com.ndt.pojo.statistic.Table3;
import com.ndt.services.StatisticService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class StatisticTest {
    private static Connection conn;
    private static StatisticService statisticService = new StatisticService();

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
    public void checkingTable1() throws SQLException {
        if (statisticService.getTopSellingProduct().size() > 10) {
            Assertions.assertTrue(false);
            return;
        }
        List<Table1> list = statisticService.getTopSellingProduct();
        Set<Table1> set = new HashSet<>(statisticService.getTopSellingProduct());
        Assertions.assertEquals(list.size(), set.size());
    }

    @Test
    public void checkingTable2() throws SQLException {
        if (statisticService.getTopCustomers().size() > 10) {
            Assertions.assertTrue(false);
            return;
        }
        List<Table2> list = statisticService.getTopCustomers();
        Set<Table2> set = new HashSet<>(statisticService.getTopCustomers());
        Assertions.assertEquals(list.size(), set.size());
    }

    @ParameterizedTest
    @CsvSource({"0,1", "1,2", "2,1", "3,1", "4,0"}) // [<TableRowIndex>,<ProductFreq>]
    public void checkingTable3(int index, int freq) throws SQLException {
        List<Table3> lines = statisticService.getProductsWithFreqInEachCategory();
        Assertions.assertEquals(lines.get(index).getFrequency(), freq);
    }
}