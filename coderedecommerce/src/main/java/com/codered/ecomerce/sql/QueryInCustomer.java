/**
 * CodeRed E-Commerce System
 * This {@code QueryInCustomer} class handles the insertion and update of customer data in the database
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
import java.sql.*;

import com.codered.ecomerce.model.Customer;

/**
 *  Write to customer tables (insertions and updates)
 * @throws SQLException
 */ 
public class QueryInCustomer extends SwagConnection{
    public static void InsertCustomer(String fname, String lname, String email, String[] address) throws SQLException{
        String sql = "INSERT INTO customer (customer_first_name, customer_last_name, customer_email) VALUES (?,?,?)";
        int custId;
        Customer customer;

        try (Connection conn = DriverManager.getConnection(properties.getProperty("url"), properties);
            PreparedStatement pstm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);){
            
            pstm.setString(1, fname);
            pstm.setString(2, lname);
            pstm.setString(3, email);

            int affRows = pstm.executeUpdate();

            if (affRows > 0) {
                ResultSet generatedID = pstm.getGeneratedKeys();
                if (generatedID.next()) {
                    custId = generatedID.getInt(1);
                    customer = new Customer(fname, lname, custId, email);
                } else {
                    System.out.println("Insert failed.");
                }
            }
        }
    }
}
