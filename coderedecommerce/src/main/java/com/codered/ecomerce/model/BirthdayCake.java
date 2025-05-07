package com.codered.ecomerce.model;

import com.codered.ecomerce.sql.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

public class BirthdayCake extends SwagConnection{
    private static String song;
    private static String name;
    
    BirthdayCake(String name, String song){
        this.song = song;
        this.name = name;
    }

    public static void main(String arg[]) throws SQLException{
    }

    public static void SpecialRestock() throws SQLException {
        ArrayList<Product> products = CentralShoppingSystem.getProducts();
        ArrayList<Variant> variants = new ArrayList<>();
        if (products == null) {
            System.out.println("the database was not reached");
            return;
        }
    
        try (Connection conn = DriverManager.getConnection(properties.getProperty("url"), properties);){
            for (Product p : products) {
                if (p != null) {
                    ArrayList<Variant> var = p.getVariants();
                    for (Variant v : var) {
                        if (v != null) {
                            variants.add(v);
                        }
                    }
                }
            }
            if (variants.isEmpty()) {
                System.out.println("No variants to restock");
                return;
            }
            for (Variant v : variants) {
                Random rand = new Random();
                int randomNum = rand.nextInt(35 - 25 + 1) + 25;
                if (v != null) {
                    QueryInProduct.UpdateStock(v, -1 * randomNum, conn);
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
