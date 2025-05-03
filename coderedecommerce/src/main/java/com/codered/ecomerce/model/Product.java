/**
 * CodeRed E-Commerce System
 * This {@class Product} class represents a product in the e-commerce system.
 * It contains all pertinent information about the product such as the product ID,
 * name, brand ID, category ID, colors, materials, sizes, and base price.
 * It also contains methods to retrieve and update product information.
 * 
 * @author CodeRed Team (Alfredo, Jesus, Xavier)
 * @version 1.0
 * @created on 04/01/2025
 */
package com.codered.ecomerce.model;

import java.util.*;
//import java.util.ListIterator;

import com.codered.ecomerce.enums.*;
import com.codered.ecomerce.sql.*;

/**
 * This class represents a product object in the e-commerce system.
 * It contains methods to retrieve and update product information.
 * It also contains methods to fetch variants of the product.
 */
public class Product {
    private ArrayList<Color> cl = new ArrayList<Color>();
    private ArrayList<Material> mt = new ArrayList<Material>();
    private ArrayList<Size> sz = new ArrayList<Size>();
    private String name;
    private int brandID;
    private int categoryID;
    private int ID;
    private double basePrice;
    private ArrayList<Variant> variants = new ArrayList<Variant>();
    
    /**
     * Constructor for the Product class.
     * @param id, @param Name, @param BrandID, @param CategoryID, 
     * @param CL, @param MT, @param SZ, @param Price
     */
    public Product(int id, String Name, int BrandID, int CategoryID, ArrayList<Color> CL, ArrayList<Material> MT, ArrayList<Size> SZ, double Price) {
        this.ID = id;
        this.name = Name;
        this.brandID = BrandID;
        this.categoryID = CategoryID;
        this.cl = CL;
        this.mt = MT;
        this.sz = SZ;
        this.basePrice = Price;

        fetchVariants();
    }

    /**
     * Method to create a product object with only the ID, name, brand ID, and category ID.
     * @param id, @param Name
     * @param BrandID, @param CategoryID    
     */
    public Product(int id, String Name, int BrandID, int CategoryID){
        this.ID = id;
        this.name = Name;
        this.brandID = BrandID;
        this.categoryID = CategoryID;
        this.cl = new ArrayList<Color>();
        this.mt = new ArrayList<Material>();
        this.sz = new ArrayList<Size>();
        this.basePrice = 50;

        fetchVariants();
    }

    public void addColor(Color c){
        this.cl.add(c);
    }
    public void addMaterial(Material m){
        this.mt.add(m);
    }
    public void addSize(Size s){
        this.sz.add(s);
    }

    /**
     * Getter method for the product ID.
     * @return color, @return materials, @return name, @return brandID,
     * @return categoryID, @return ID, @return basePrice, @return variants
     */ 
    public ArrayList<Color> getColors() {
        return cl;
    }
    public ArrayList<Material> getMaterials() {
        return mt;
    }
    public ArrayList<Size> getSizes() {
        return sz;
    }
    public String getName() {
        return name;
    }
    public int getBrandID() {
        return brandID;
    }
    public int getCategoryID() {
        return categoryID;
    }
    public int getID() {
        return ID;
    }
    public double getBasePrice() {
        return basePrice;
    }
    public ArrayList<Variant> getVariants(){
        return variants;
    }

    /**
     * Method to fetch the variants of the prodicts from the database
     */
    public void fetchVariants(){
        QuerySeProduct.getVariants(ID, variants, this.cl, this.mt, this.sz);
    }

    /**
     * Method to print the product information to othe console
     */
    public void print(){
        System.out.println("id= "+ID+" name= "+name+" ");
    }
}
