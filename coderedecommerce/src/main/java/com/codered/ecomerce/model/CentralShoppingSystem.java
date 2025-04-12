package com.codered.ecomerce.model;

import java.util.*;

import com.codered.ecomerce.sql.*;

public class CentralShoppingSystem 
{
    private static ArrayList<Product> products = new ArrayList<Product>();

    //Constructor
    private CentralShoppingSystem() {}

    public static void main(String[] args) {
        QuerySeProduct.getProducts((ArrayList<Product>) products);
        

        //test
        int countP = 0;
        for(Product P : products){
            countP++;
        }

        System.out.println("succesfully initialized with "+countP+" products");
    }

    public static void Browse() {

    }

}
