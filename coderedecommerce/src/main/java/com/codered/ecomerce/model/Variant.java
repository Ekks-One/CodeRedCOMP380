/**
 * CodeRed E-Commerce System
 * This {@class Variant} class represents a product variant in the e-commerce system.
 * It contains all pertinent information about the variant such as the variant ID,
 * color, material, size, stock, and price.
 * 
 * @authro CodeRed Team (Jesus)
 * @version 1.0
 * @created on 04/12/2025
 */
package com.codered.ecomerce.model;

import com.codered.ecomerce.enums.Color;
import com.codered.ecomerce.enums.Material;
import com.codered.ecomerce.enums.Size;
import com.codered.ecomerce.sql.*;

import java.sql.*;

/**
 * This class represents a product variant object in the e-commerce system.
 * It contains methods to retrieve and update variant information.
 */
public class Variant extends SwagConnection{
    private int id;
    private Color cl;
    private Material mt;
    private Size sz;
    private int stock;
    private double price;
    private String category;

    /**
     * construtor to create a variant object with only the ID, color, material, size, stock, and price.
     * @param id, @param cl, @param mt, @param sz, @param stock, @param price
     */
    public Variant(int id, Color cl, Material mt, Size sz, int stock, double price){
        this.id = id;
        this.cl = cl;
        this.mt = mt;
        this.sz = sz;
        this.stock = stock;
        this.price = price;
    }

    /**
     * Overloaded constructor to create a variant object with the category as a parameter.
     * @param id, @param cl, @param mt, @param sz, @param stock, @param price, @param category
     */
    public Variant(int id, Color cl, Material mt, Size sz, int stock, double price, String category)
    {
        this.id = id;
        this.cl = cl;
        this.mt = mt;
        this.sz = sz;
        this.stock = stock;
        this.price = price;
        this.category = category;
    }

    public void setCategory(String category)
    {
        this.category = category;
    }

    /**
     * Methods to create a variant object with only the ID, color, material, size, stock, and price.
     * @param price 
     */
    public void setPrice(double price){
        this.price = price;
    }

    /**
     * Method to create a variant object with only the ID, color, material, size, and stock.
     * @param change    
     * @throws SQLException
     */
    public void updateStock(int change) throws SQLException{
        try (Connection conn = DriverManager.getConnection(properties.getProperty("url"), properties);){
            QueryInProduct.UpdateStock(this, change, conn);
        }
        this.stock = this.stock - change;
    }

    /**
     * Method to create a variant object with only the ID, color, material, size, and stock.
     * @param newPrice
     * @throws SQLException
     */
    public void changePrice(double newPrice) throws SQLException{
        try (Connection conn = DriverManager.getConnection(properties.getProperty("url"), properties);){
            QueryInProduct.changeProductPrice(this, newPrice, conn);
        }
        this.price = newPrice;
    }

    /**
     * Method to create a variant object with only the ID, color, material, size, and stock.
     * @return id, @return color, @return material, @return size, @return stock, @return price
     */
    public int getID(){
        return id;
    }
    public Color getColor(){
        return cl;
    }
    public Material getMaterial(){
        return mt;
    }
    public Size getSize(){
        return sz;
    }
    public int getStock(){
        return stock;
    }
    public double getPrice(){
        return price;
    }
    public String getCategory()
    {
        return category;
    }


    /**
     * Method to create a variant object with only the ID, color, material, size, stock, and price.
     */
    public void print(){
        System.out.println("color= "+cl+" material= "+mt+" size= "+sz);
    }
}
