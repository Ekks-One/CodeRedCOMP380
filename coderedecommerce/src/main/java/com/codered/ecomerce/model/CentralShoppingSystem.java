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

import java.util.*;

import com.codered.ecomerce.sql.*;

/**
 * CentralShoppingSystem class is used to manage the product list and system operations.
 */
public class CentralShoppingSystem 
{
    private static ArrayList<Product> products = new ArrayList<Product>();

    /**
     * Default constructor for CentralShoppingSystem.
     */
    public CentralShoppingSystem() {}

    public static void main(String[] args) {
        QuerySeProduct.getProducts((ArrayList<Product>) products);
        

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
    public static void Browse() {

    }

}
