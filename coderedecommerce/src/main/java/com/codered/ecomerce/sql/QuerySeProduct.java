/**
 * CodRed E-Commerce System
 * This class handles database queries related to products and variants.
 * It retrieves product and variant information from the database and populates the respective objects.
 * 
 * @author CodeRed Team (Jesus)
 * @version 1.0
 * @created on 04/12/2025
 */
package com.codered.ecomerce.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.codered.ecomerce.model.*;
import com.codered.ecomerce.enums.*;

/**
 * Read from product tables (selects)
 * This class is responsible for querying the database to retrieve product and variant information.
 */
public class QuerySeProduct extends SwagConnection {
    /**
     * populates the product objects
     * @param products the arraylist to store the products
     * @throws SQLException if there is an error with the SQL query
     */ 
    public static void getProducts(ArrayList<Product> products) {
        String sql = "SELECT * FROM product";

        try (Connection connection = DriverManager.getConnection(properties.getProperty("url"), properties);
             PreparedStatement pstm = connection.prepareStatement(sql);
             ResultSet rt = pstm.executeQuery()) {
            
            while (rt.next()) { // reads the returned table from the query
                int id = rt.getInt("product_id");
                String name = rt.getString("product_name");
                int brandId = rt.getInt("brand_id");
                int categoryId = rt.getInt("category_id");

                // Ensure the list is large enough by padding with null
                while (products.size() <= id) {
                    products.add(null);
                }

                products.set(id, new Product(id, name, brandId, categoryId));
                products.get(id).print(); // visualizes the testing
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * populates the variant objects
     * @param prodID the product id, @param variants the arraylist to store the variants
     * @throws SQLException if there is an error with the SQL query
     */
    public static void getVariants(int prodID, ArrayList<Variant> variants) {
        String sql = "SELECT * FROM product_price_stock WHERE product_id = ?";
        ArrayList<Product> products = CentralShoppingSystem.getProducts();

        int numColors = Color.values().length; // for unique indexing
        int numSizes = Size.values().length;

        try (Connection conn = DriverManager.getConnection(properties.getProperty("url"), properties);
             PreparedStatement pstm = conn.prepareStatement(sql)) {
            
            pstm.setInt(1, prodID); // Set the product_id parameter
            try (ResultSet rt = pstm.executeQuery()) {
                while (rt.next()) {
                    String colorStr = rt.getString("color");
                    String materialStr = rt.getString("material");
                    String sizeStr = rt.getString("prod_size");

                    Color cl;
                    Material mt;
                    Size sz;

                    try {
                        cl = Color.valueOf(colorStr != null ? colorStr.toUpperCase() : "");
                        mt = Material.valueOf(materialStr != null ? materialStr.toUpperCase() : "");
                        sz = Size.valueOf(sizeStr != null ? sizeStr.toUpperCase() : "");
                    } catch (IllegalArgumentException e) {
                        System.err.println("Invalid enum value in database - Color: " + colorStr + ", Material: " + materialStr + ", Size: " + sizeStr);
                        continue; // Skip this variant
                    }

                    int stock = rt.getInt("product_stock");
                    double price = rt.getDouble("product_price");

                    int index = mt.ordinal() * (numColors * numSizes) + cl.ordinal() * numSizes + sz.ordinal();

                    // Ensure the list is large enough by padding with null
                    while (variants.size() <= index) {
                        variants.add(null);
                    }

                    variants.set(index, new Variant(prodID, cl, mt, sz, stock, price));
                    products.get(prodID).addColor(cl);
                    products.get(prodID).addMaterial(mt);
                    products.get(prodID).addSize(sz);
                    variants.get(index).print();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}