package com.codered.ecomerce.model;

import java.util.*;
//import java.util.ListIterator;

import com.codered.ecomerce.enums.*;
import com.codered.ecomerce.sql.*;

public class Product {
    private ArrayList<Color> cl;
    private ArrayList<Material> mt;
    private ArrayList<Size> sz;
    private String name;
    private int brandID;
    private int categoryID;
    private int ID;
    private double basePrice;
    private ArrayList<Variant> variants = new ArrayList<Variant>();
    
    //for making NEW products
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

    //for instantiating product class
    public Product(int id, String Name, int BrandID, int CategoryID){
        this.ID = id;
        this.name = Name;
        this.brandID = BrandID;
        this.categoryID = CategoryID;

        fetchVariants();
    }

    //no setters for now

    //geters
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

    private void fetchVariants(){
        QuerySeProduct.getVariants(ID, variants);
    }

    public void print(){
        System.out.println("id= "+ID+" name= "+name+" ");
    }
}
