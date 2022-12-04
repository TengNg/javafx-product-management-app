package com.ndt.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcUtils {
    private static Connection conn;
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static Connection getConn() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:mysql://localhost/mydb",
                "root",
                "abcd1234"
        );
    }
}
