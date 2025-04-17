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

import com.codered.ecomerce.model.*;

/**
 *  Write to customer tables (insertions and updates)
 *  very rudementary, only works with inserting base customers at the moment
 *  @throws SQLException
 */ 
public class QueryInCustomer extends SwagConnection{
    public static void InsertCustomer(Customer customer) {
        String sql = "INSERT INTO customer (customer_first_name, customer_last_name, customer_email) VALUES (?,?,?)";

        try (Connection conn = DriverManager.getConnection(properties.getProperty("url"), properties);
            PreparedStatement pstm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);){
            
            pstm.setString(1, customer.getFname());
            pstm.setString(2, customer.getLname());
            pstm.setString(3, customer.getCustomerEmail());

            int affRows = pstm.executeUpdate();

            if (affRows > 0) {
                ResultSet generatedID = pstm.getGeneratedKeys();
                if (generatedID.next()) {
                    int custId = generatedID.getInt(1);
                    customer.setID(custId);
                } else {
                    System.out.println("Insert failed.");
                }
            }

            // another try catch to insert into shipping table TODO
        } catch (SQLException e) {e.printStackTrace();}
    }

    public static void InsertOrder(Order order, int custId) {
        String sql = "INSERT INTO orders (customer_id, order_date, order_est_arriaval) Values (?,?,?)";

        try (Connection conn =DriverManager.getConnection(properties.getProperty("url"), properties);
        PreparedStatement pstm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);){

            pstm.setInt(1, custId);
            //pstm.setDate(2, order.getOrderDate());
            //pstm.setDate(3, estArrival);

            int affRows = pstm.executeUpdate();

            if (affRows > 0){
                ResultSet genID = pstm.getGeneratedKeys();
                if (genID.next()){
                    int orId = genID.getInt(1);
                    order.setOrderID(orId);
                }else {
                    System.out.println("Insert into orders failed");
                }
            }

            // anoter try catch to insert into order items table TODO
        } catch (SQLException e) {e.printStackTrace();}
    }
}
