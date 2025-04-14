/**
 * CodeRed E-Commerce System
 * This {@code QuerySeCustomer} class handles the selection of customer data from the database
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

import com.codered.ecomerce.model.Customer;

/**
 * Read from customer tables (Selects)
 */ 
public class QuerySeCustomer extends SwagConnection{
    public static void getCustomers(ArrayList<Customer> customers){
        String sql = "SELECT * FROM customer";

        try (Connection conn = DriverManager.getConnection(properties.getProperty("url"), properties);
        PreparedStatement psmt = conn.prepareStatement(sql);
        ResultSet rt = psmt.executeQuery()){
            int count = 0;
            String ship = "SELECT * FROM customer_shipping WHERE customer_id = ?";

            while(rt.next()){
                int custId = rt.getInt("customer_id");
                String fname = rt.getString("customer_first_name");
                String lname = rt.getString("customer_last_name");
                String Email = rt.getString("customer_email");

                customers.add(new Customer(fname, lname, custId, Email));

                customers.get(count).print();
                count++;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
