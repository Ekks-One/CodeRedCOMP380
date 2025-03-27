package com.sql.swagShop;


import java.sql.*;
import java.util.*;
import java.util.logging.Logger;

public class swagShopConnection {
    private static final Logger log;

    static {
        System.setProperty("java.util.loggin.SimpleFormatter.format", "[%4$-7s] %5$s %n");
        log =Logger.getLogger(swagShopConnection.class.getName());
    }

    public static void main(String[] args) throws Exception {
        log.info("Loading application properties");
        Properties properties = new Properties();
        properties.load(swagShopConnection.class.getClassLoader().getResourceAsStream("application.properties"));

        log.info("Connectiong to the database");
        Connection connection = DriverManager.getConnection(properties.getProperty("url"), properties);
        log.info("Database connection test: " + connection.getCatalog());

        log.info("Show database schema");
        Scanner scanner = new Scanner(swagShopConnection.class.getClassLoader().getResourceAsStream("schema.sql"));
        Statement statement = connection.createStatement();
        while (scanner.hasNextLine()) {
            statement.execute(scanner.nextLine());
        }

        log.info("Closing database connection");
        connection.close();
    }
}