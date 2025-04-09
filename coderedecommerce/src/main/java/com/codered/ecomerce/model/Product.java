package com.codered.ecomerce.model;

import java.util.LinkedList;
//import java.util.ListIterator;

import com.codered.ecomerce.enums.*;
import com.codered.ecomerce.MasterQuery;

public class Product {
    private LinkedList<Color> cl;
    private LinkedList<Material> mt;
    private LinkedList<Size> sz;
    private String name;
    private int brandID;
    private int categoryID;
    private int ID;
    private double basePrice;
    private LinkedList<Variant> variants = new LinkedList<Variant>();
    
    //for making NEW products
    public Product(int id, String Name, int BrandID, int CategoryID, LinkedList<Color> CL, LinkedList<Material> MT, LinkedList<Size> SZ, double Price) {
        this.ID = id;
        this.name = Name;
        this.brandID = BrandID;
        this.categoryID = CategoryID;
        this.cl = CL;
        this.mt = MT;
        this.sz = SZ;
        this.basePrice = Price;

        getVariants();
    }

    //for instantiating product class
    public Product(int id, String Name, int BrandID, int CategoryID){
        this.ID = id;
        this.name = Name;
        this.brandID = BrandID;
        this.categoryID = CategoryID;
        
        getVariants();
    }

    //no setters for now

    //geters
    public LinkedList<Color> getColors() {
        return cl;
    }
    public LinkedList<Material> getMaterials() {
        return mt;
    }
    public LinkedList<Size> getSizes() {
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
    public void getVariants(){
        MasterQuery.getVariants(ID, variants);
    }

    public void print(){
        System.out.println("id= "+ID+" name= "+name+" ");
    }
}
