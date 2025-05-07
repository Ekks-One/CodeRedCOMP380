/**
 * CodeRed E-Commerce System
 * This {@code SwagConnection} class is responsible for establishing a connection to the database and performing various
 * database operations related to products, colors, materials, and sizes.
 * It provides methods to initialize products, insert product attributes, and manage product pricing and stock.
 * 
 * @author CodeRed Team (Jesus)
 * @version 1.0
 * @created on 04/12/2025
 */
package com.codered.ecomerce.sql;

import com.codered.ecomerce.enums.*;
import com.codered.ecomerce.model.*;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.*;
import java.util.logging.Logger;

// Just here to use the properties file to connect to database
public class SwagConnection {
    protected static final Logger log;
    protected static final Properties properties = new Properties();

    protected SwagConnection(){}

    static {
        System.setProperty("java.util.logging.SimpleFormatter.format", "[%4$-7s] %5$s %n");
        log = Logger.getLogger(SwagConnection.class.getName());

        try (InputStream input = SwagConnection.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                log.severe("Sorry, unable to find application.properties");
            } else {
                properties.load(input);
            }
        } catch (IOException e) {
            log.severe("Error loading application.properties: " + e.getMessage());
        }
    }
}


