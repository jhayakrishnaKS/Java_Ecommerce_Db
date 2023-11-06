package com.JhayKrish.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private final static String connectionString = "jdbc:mysql://localhost:3306/javaecommerce";
    private final static String username = "root";
    private final static String password = "root";


    public static Connection getConnection()
    {
        Connection connection;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(connectionString,username,password);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }
}
