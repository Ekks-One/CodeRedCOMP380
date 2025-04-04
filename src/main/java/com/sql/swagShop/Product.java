package com.sql.swagShop;

import java.util.LinkedList;

import com.enums.Color;
import com.enums.Material;
import com.enums.Size;

public class Product {
    private Color cl[];
    private Material mt[];
    private Size sz[];
    private String name;
    private int brandID;
    private int categoryID;
    private int ID;
    private double basePrice;
    private LinkedList<Variant> variants = new LinkedList<Variant>();

    Product(int id, String Name, int BrandID, int CategoryID, Color CL[], Material MT[], Size SZ[], double Price) {
        this.ID = id;
        this.name = Name;
        this.brandID = BrandID;
        this.categoryID = CategoryID;
        this.cl = CL;
        this.mt = MT;
        this.sz = SZ;
        this.basePrice = Price;
    }

    Product(int id, String Name, int BrandID, int CategoryID){
        this.ID = id;
        this.name = Name;
        this.brandID = BrandID;
        this.categoryID = CategoryID;
    }

    public void getVariants(){
        MasterQuery.getVariants(ID, variants);
    }

    public void print(){
        System.out.println("id= "+ID+" name= "+name+" ");
    }
}
