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
public class Variant {
    private int id;
    private Color cl;
    private Material mt;
    private Size sz;
    private int stock;
    private double price;

    public Variant(int id, Color cl, Material mt, Size sz, int stock, double price){
        this.id = id;
        this.cl = cl;
        this.mt = mt;
        this.sz = sz;
        this.stock = stock;
        this.price = price;
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
        QueryInProduct.UpdateStock(this, change);
        this.stock = this.stock - change;
    }

    /**
     * Method to create a variant object with only the ID, color, material, size, and stock.
     * @param newPrice
     * @throws SQLException
     */
    public void changePrice(double newPrice) throws SQLException{
        QueryInProduct.changeProductPrice(this, newPrice);
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


    /**
     * Method to create a variant object with only the ID, color, material, size, stock, and price.
     */
    public void print(){
        System.out.println("color= "+cl+" material= "+mt+" size= "+sz);
    }
}
