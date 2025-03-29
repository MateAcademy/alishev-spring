package com.example.demo.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresConnector {

    private static final String URL = "jdbc:postgresql://localhost:5432/demo_db";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    public static Connection connect() {
        Connection connection = null;
        try {
            // Не обязательно с современных JDK, но можно явно загрузить драйвер
            Class.forName("org.postgresql.Driver");

            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Успешное подключение к PostgreSQL!");

        } catch (ClassNotFoundException e) {
            System.err.println("❌ PostgreSQL JDBC Driver не найден!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("❌ Ошибка подключения к БД!");
            e.printStackTrace();
        }

        return connection;
    }
}