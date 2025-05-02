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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

class QueryInCustomerTest 
{

    @Test
    void testInsertCustomer()
    {
        Customer customer = new Customer("John", "Doe", 001, "johndoe@example.com");
        QueryInCustomer query = new QueryInCustomer();

        assertTrue(customer.getID() > 0, "Customer ID should be greater than 000 after insertion.");

        
    }

    @Test
    void testInsertOrder()
    {

    }
}
