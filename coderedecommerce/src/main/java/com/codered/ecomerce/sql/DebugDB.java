/**
 * CodRed E-Commerce System
 * This class handles database queries related to products and variants.
 * It retrieves product and variant information from the database and populates the respective objects.
 * 
 * @author CodeRed Team (Jesus)
 * @version 1.0
 * @created on 04/16/2025
 */

package com.codered.ecomerce.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// just here to see what is currently in database
public class DebugDB extends SwagConnection {

    public static void debugDatabaseContents() {
        System.out.println("Debugging database contents:");
        try (Connection conn = DriverManager.getConnection(
                properties.getProperty("url"),
                properties.getProperty("user"),
                properties.getProperty("password"))) {
            // Products
            try (PreparedStatement pstm = conn.prepareStatement("SELECT * FROM product");
                 ResultSet rs = pstm.executeQuery()) {
                int count = 0;
                while (rs.next()) {
                    count++;
                    System.out.println("Product: " + rs.getInt("product_id") + ", " + rs.getString("product_name"));
                }
                System.out.println("Total products: " + count);
            }
            // Descriptions
            try (PreparedStatement pstm = conn.prepareStatement("SELECT * FROM descriptions");
                 ResultSet rs = pstm.executeQuery()) {
                int count = 0;
                while (rs.next()) {
                    count++;
                    System.out.println("Description: " + rs.getInt("product_id") + ", " + rs.getString("description"));
                }
                System.out.println("Total descriptions: " + count);
            }
            // Brands
            try (PreparedStatement pstm = conn.prepareStatement("SELECT * FROM brand");
                 ResultSet rs = pstm.executeQuery()) {
                int count = 0;
                while (rs.next()) {
                    count++;
                    System.out.println("Brand: " + rs.getInt("brand_id") + ", " + rs.getString("brand_name"));
                }
                System.out.println("Total brands: " + count);
            }
            // Categories
            try (PreparedStatement pstm = conn.prepareStatement("SELECT * FROM category");
                 ResultSet rs = pstm.executeQuery()) {
                int count = 0;
                while (rs.next()) {
                    count++;
                    System.out.println("Category: " + rs.getInt("category_id") + ", " + rs.getString("category_name"));
                }
                System.out.println("Total categories: " + count);
            }
            // Product Colors
            try (PreparedStatement pstm = conn.prepareStatement("SELECT * FROM product_color");
                 ResultSet rs = pstm.executeQuery()) {
                int count = 0;
                while (rs.next()) {
                    count++;
                    System.out.println("Product Color: " + rs.getInt("product_id") + ", " + rs.getString("color"));
                }
                System.out.println("Total product colors: " + count);
            }
            // Product Price Stock (Variants)
            try (PreparedStatement pstm = conn.prepareStatement("SELECT * FROM product_price_stock");
                 ResultSet rs = pstm.executeQuery()) {
                int count = 0;
                while (rs.next()) {
                    count++;
                    System.out.println("Variant: " + rs.getInt("product_id") + ", " + rs.getString("color") + ", " +
                                       rs.getString("prod_size") + ", " + rs.getString("material") + ", " +
                                       "stock=" + rs.getInt("product_stock") + ", price=" + rs.getDouble("product_price"));
                }
                System.out.println("Total variants: " + count);
            }
        } catch (SQLException e) {
            System.out.println("Failed to debug database contents: " + e.getMessage());
        }
    }  
}
