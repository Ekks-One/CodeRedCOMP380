/**
 * CodeRed E-Commerce System
 * This {@code QueryInCustomerTest} class is a test class for the {@code QueryInCustomer} class
 * and contains unit tests to verify the functionality of the QueryInCustomer.java class.
 * 
 * @author CodeRed Team (Xavier, Alfredo)
 * @version 1.0 
 * Created on 05/01/2025
 * @see QueryInCustomer
 */

package com.codered.ecomerce.sql;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.codered.ecomerce.model.Customer;
import com.codered.ecomerce.model.Order;
import com.codered.ecomerce.model.Variant;
import com.codered.ecomerce.enums.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

class QueryInCustomerTest 
{

    @Test
    void testInsertCustomer()
    {
        Customer customer = new Customer("John", "Doe", 0, "johndoe@example.com");
        QueryInCustomer query = new QueryInCustomer();

        assertTrue(customer.getID() > 0, "Customer ID should be greater than 0 after insertion.");

        try(Connection connection = DriverManager.getConnection("jdbc:sqlite:test.db"))
        {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM customer WHERE customer_id = ?");
            stmt.setInt(1, customer.getID());
            assertTrue(stmt.executeQuery().next(), "Customer should exist in the database");
        }
        catch(SQLException e)
        {
            fail("Database verification failed: " + e.getMessage());
        }
    }

    @Test
    void testInsertOrder()
    {
        Customer customer = new Customer("Jane", "Doe", 0, "janedoe@example.com");
        QueryInCustomer query = new QueryInCustomer();

        List<Variant> orderItems = new LinkedList<>();
        orderItems.add(new Variant(1, Color.RED, Material.COTTON, Size.M, 10,  19.99));
        orderItems.add(new Variant(2, Color.BLUE, Material.DENIM, Size.L, 5, 49.99));

        int orderCount = orderItems.size();
        LocalDateTime orderDate = LocalDateTime.now();
        
        Order order = new Order();

        QueryInCustomer.InsertOrder(order, customer.getID());

        assertTrue(order.getOrderID() > 0, "Order ID should be set after insertion.");

        try(Connection connection = DriverManager.getConnection("jdbc:sqlite:test.db"))
        {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM orders WHERE order_id = ?");
            stmt.setInt(1, order.getOrderID());
            assertTrue(stmt.executeQuery().next(), "Order should exist in the database");
        }
        catch(SQLException e)
        {
            fail("Database verification failed: " + e.getMessage());
        }
    }
}
