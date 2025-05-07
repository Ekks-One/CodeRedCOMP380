/**
 * CodeRed E-Commerce System
 * This {@code QueryInProduct} class handles the insertion and update of product data in the database
 * 
 * @author CodeRed Team (Jesus)
 * @version 1.0
 * @created on 04/12/2025
 */
package com.codered.ecomerce.sql;

import java.sql.*;

import com.codered.ecomerce.model.Variant;

/**
 * Write to product tables (insertions and updates)
 */ 
public class QueryInProduct extends SwagConnection{
    public static void UpdateStock(Variant product, int change, Connection conn) throws SQLException {
    String Update = "UPDATE product_price_stock SET product_stock = ? WHERE product_id = ? AND UPPER(color) = ? AND UPPER(prod_size) = ? AND UPPER(material) = ?";
    int upStock;
    int stock = product.getStock();

    if (stock >= 0 && (change <= stock || change < 0)) {
        upStock = stock - change;
    } else if (stock > 0) {
        upStock = 0;
        throw new SQLException("There isn't enough stock, only " + stock + " is/are available");
    } else {
        throw new SQLException("The stock is 0");
    }

    try (PreparedStatement pstm = conn.prepareStatement(Update, Statement.RETURN_GENERATED_KEYS);) {
        pstm.setInt(1, upStock);
        pstm.setInt(2, product.getID());
        pstm.setString(3, product.getColor().toString());
        pstm.setString(4, product.getSize().toString());
        pstm.setString(5, product.getMaterial().toString());
        int rowsAffected = pstm.executeUpdate();
        if (rowsAffected == 0) {
            throw new SQLException("No rows updated for product_id=" + product.getID() +
                ", color=" + product.getColor() +
                ", size=" + product.getSize() +
                ", material=" + product.getMaterial());
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    
    /**
     * Method to update the price of a product in the database
     * @param product
     * @param newPrice
     */
    public static void changeProductPrice(Variant product, double newPrice, Connection conn){
        String update = "UPDATE product_price_stock SET product_price = ? WHERE product_id = ? AND UPPER(color) = ? AND UPPER(prod_size) = ? AND (material) = ?";

        try(PreparedStatement pstm = conn.prepareStatement(update, Statement.RETURN_GENERATED_KEYS);){
                pstm.setDouble(1, newPrice);
                pstm.setInt(2, product.getID());
                pstm.setString(3, product.getColor().toString());
                pstm.setString(4, product.getSize().toString());
                pstm.setString(5,product.getMaterial().toString());
                pstm.executeUpdate();

            }catch (SQLException e){
                e.printStackTrace();
            }
    }
}
