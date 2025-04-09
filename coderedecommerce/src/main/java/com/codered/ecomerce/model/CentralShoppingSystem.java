package com.codered.ecomerce.model;

import com.codered.ecomerce.model.Customer;
import com.codered.ecomerce.model.Product;
import com.codered.ecomerce.MasterQuery;
//may need to restructure files in order to properly import

import java.util.LinkedList;
import java.util.List;
import java.sql.*;

public class CentralShoppingSystem 
{
    private List<Customer> customers;
    private List<Product> products;

    //Constructor
    public CentralShoppingSystem()
    {
        this.customers = new LinkedList<>();
        this.products = new LinkedList<>();
    }

    public void initializeSystem()
    {
        MasterQuery.getProducts((LinkedList<Product>) products);
            //MasterQuery.getCustomers((LinkedList<Customer>) customers);

            //test
            System.out.println("System successfully initialized with " + products.size() + 
            " products and " + customers.size() + " customers.");
    }

    public void displaySystemInfo()
    {
        return; 
    }
}
