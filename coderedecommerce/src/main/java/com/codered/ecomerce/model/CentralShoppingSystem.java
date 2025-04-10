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
    private static LinkedList<Customer> customers = new LinkedList<Customer>();
    private static LinkedList<Product> products = new LinkedList<Product>();

    //Constructor
    private CentralShoppingSystem() {}

    static {
        MasterQuery.getProducts((LinkedList<Product>) products);
        MasterQuery.getCustomers((LinkedList<Customer>) customers);

        //test
        int countP = 0, countC = 0;
        for(Product P : products){
            countP++;
        }
        for(Customer C : customers){
            countC++;
        }
        System.out.println("succesfully initialized with "+countP+" products and "+countC+" customers");
    }

    public static LinkedList<Customer> getCustomers(){
        return customers;
    }
}
