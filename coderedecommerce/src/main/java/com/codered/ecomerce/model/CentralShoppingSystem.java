/**
 * CodRed E-Commerce System
 * This {@code CentralShoppingSystem} class is used to handle the central shopping system of the application.
 * It contains methods to initialize the system, browse products, and manage the product list.
 * 
 * @author CodeRed Team (Alfredo, Jesus, Xavier)
 * @version 1.0
 * @see Product.java
 * @created 04/01/2025
 */
package com.codered.ecomerce.model;

import com.codered.ecomerce.enums.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;
import java.sql.SQLException.*;

import com.codered.ecomerce.sql.*;

import javafx.fxml.Initializable;

/**
 * CentralShoppingSystem class is used to manage the product list and system operations.
 */
public class CentralShoppingSystem extends SwagConnection 
{
    private static ArrayList<Product> products = new ArrayList<Product>();
    private static ArrayList<Variant> searchResults = new ArrayList<Variant>();
    private static CentralShoppingSystem instance;

    /**
     * Private Constructor prevent instantiation
     */
    private CentralShoppingSystem() {
        initializeProducts(); // Initialize products
    }

    /**
     * Singleton instance of CentralShoppingSystem
     * @return singleton instance
     */
    public static CentralShoppingSystem getInstance() {
        if(instance == null) {
            instance = new CentralShoppingSystem();
        }
        return instance;
    }

    public static void main(String[] args) throws SQLException{
        try (Connection conn = DriverManager.getConnection(properties.getProperty("url"), properties);){
            QuerySeProduct.getProducts((ArrayList<Product>) products, conn);
        } catch (SQLException e){
            e.printStackTrace();
        }

        //test
        int countP = 0;
        for(Product P : products){
            countP++;
        }

        System.out.println("succesfully initialized with "+countP+" products");
    }

    /**
     * This method initializes the CentralShoppingSystem by loading products from the database.
     * @return the list of products loaded from the database.
     */
    public static ArrayList<Product> getProducts() {
        return products;
    }

    /**
     * This method is used to browse products in the CentralShoppingSystem.
     * it allows users to view and select products for purchase.
     * @param product the product to be browsed.
     * @return the list of products available for browsing.
     */
    public static ArrayList<Variant> Browse(String search) {
        return SearchProducts.Search(search);
    }

    /** 
     * Initialize the product list before loading products from database.
     */
    private void initializeProducts() {
        try (Connection conn = DriverManager.getConnection(properties.getProperty("url"), properties);){
            QuerySeProduct.getProducts((ArrayList<Product>) products, conn);
        } catch (SQLException e){
            e.printStackTrace();
        }
        System.out.println("Success initializing: " + products.size());
    }

    /*  
        Takes an order and inserts a new customer in the database
        If customer had account, no new customer is inserted
        New order is inserted linked to customer.
     */
    public static void Checkout(LinkedList<Variant> orderItems, Payment payment, Customer customer){
        if (!customer.HasAccount()){
            // ask if they want to make an account
            QueryInCustomer.InsertCustomer(customer);
        }
        Order order = new Order(customer, orderItems, 1, LocalDateTime.now());
    }
}
