package com.ndt.tests;

import com.ndt.config.JdbcUtils;
import com.ndt.config.JdbcUtils;
import com.ndt.pojo.Category;
import com.ndt.services.CategoryService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

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

    private static CategoryService categoryService = new CategoryService();

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
        List<Integer> categories = new ArrayList<>();
        for (Category c : categoryService.getCategories())
            categories.add(c.getId());
        Set<Integer> set = new HashSet<>(categories);
        Assertions.assertEquals(set.size(), categories.size());
    }

    @ParameterizedTest
    @DisplayName("Checking correct product's quantity in each category")
    @CsvSource({"1,A", "2,B", "3,C", "4,D", "5,E"}) // [<CategoryId>,<CategoryName>]
    public void isGettingCorrectCategoryNameWithId(int id, String name) throws SQLException {
        Assertions.assertEquals(categoryService.getCategoryNameById(id), name);
    }
}
