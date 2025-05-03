/**
 * CodeRed E-Commerce System
 * This {@code QueryInCustomer} class handles the insertion and update of customer data in the database
 * 
 * @author CodeRed Team (Jesus)
 * @version 1.0
 * @created on 04/12/2025
 */
package com.codered.ecomerce.sql;

import java.sql.*;

import com.codered.ecomerce.model.*;

/**
 *  Write to customer tables (insertions and updates)
 *  very rudementary, only works with inserting base customers at the moment
 *  @throws SQLException
 */ 
public class QueryInCustomer extends SwagConnection{
    public static void InsertCustomer(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }
        if (customer.getFname() == null || customer.getLname() == null || customer.getCustomerEmail() == null) {
            throw new IllegalArgumentException("Customer fields cannot be null");
        }
    
        String sql = "INSERT INTO customer (customer_first_name, customer_last_name, customer_email) VALUES (?,?,?)";
    
        try (Connection conn = DriverManager.getConnection(properties.getProperty("url"), properties);
             PreparedStatement pstm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
    
            // Set parameters
            pstm.setString(1, customer.getFname());
            pstm.setString(2, customer.getLname());
            pstm.setString(3, customer.getCustomerEmail());
    
            // Execute the query
            int affRows = pstm.executeUpdate();
    
            if (affRows > 0) {
                ResultSet generatedID = pstm.getGeneratedKeys();
                if (generatedID.next()) {
                    int custId = generatedID.getInt(1);
                    customer.setID(custId);
                    System.out.println("Customer inserted successfully with ID: " + custId);
                } else {
                    System.out.println("Insert failed: No generated key returned.");
                }
            } else {
                System.out.println("Insert failed: No rows affected.");
            }
    
        } catch (SQLException e) {
            System.err.println("SQL Exception occurred while inserting customer: " + e.getMessage());
            throw new RuntimeException("Failed to insert customer", e);
        }
    }

    public static void InsertOrder(Order order, int custId) {
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }
        if (order.getOrderDate() == null) {
            throw new IllegalArgumentException("Order date cannot be null");
        }
    
        String sql = "INSERT INTO orders (customer_id, order_date, order_est_arrival) VALUES (?, ?, ?)";
    
        try (Connection conn = DriverManager.getConnection(properties.getProperty("url"), properties);
             PreparedStatement pstm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
    
            // Validate customer ID
            System.out.println("Validating customer ID: " + custId);
            try (PreparedStatement checkCustomer = conn.prepareStatement("SELECT customer_id FROM customer WHERE customer_id = ?")) {
                checkCustomer.setInt(1, custId);
                ResultSet rs = checkCustomer.executeQuery();
                if (!rs.next()) {
                    throw new IllegalArgumentException("Customer ID " + custId + " does not exist in the database.");
                }
            }
    
            // Set parameters
            pstm.setInt(1, custId);
            pstm.setDate(2, java.sql.Date.valueOf(order.getOrderDate().toLocalDate())); // Convert LocalDateTime to SQL Date
            pstm.setDate(3, java.sql.Date.valueOf(order.getOrderDate().toLocalDate().plusDays(7))); // Estimated arrival in 7 days
    
            // Debugging statements
            System.out.println("Inserting order with customer ID: " + custId);
            System.out.println("Order date: " + order.getOrderDate());
            System.out.println("Estimated arrival: " + order.getOrderDate().toLocalDate().plusDays(7));
    
            // Execute the query
            int affRows = pstm.executeUpdate();
    
            if (affRows > 0) {
                ResultSet genID = pstm.getGeneratedKeys();
                if (genID.next()) {
                    int orId = genID.getInt(1);
                    order.setOrderID(orId);
                    System.out.println("Order inserted successfully with ID: " + orId);
                } else {
                    System.out.println("Insert into orders failed: No generated key returned.");
                }
            } else {
                System.out.println("Insert into orders failed: No rows affected.");
            }
    
        } catch (SQLException e) {
            System.err.println("SQL Exception occurred while inserting order: " + e.getMessage());
            throw new RuntimeException("Failed to insert order", e);
        }
    }
}
