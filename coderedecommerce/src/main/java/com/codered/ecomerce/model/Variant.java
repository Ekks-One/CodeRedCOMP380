package com.codered.ecomerce.model;

import com.codered.ecomerce.enums.Color;
import com.codered.ecomerce.enums.Material;
import com.codered.ecomerce.enums.Size;
import com.codered.ecomerce.sql.*;

import java.sql.*;

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

    //only setting price and stock for now
    public void setPrice(double price){
        this.price = price;
    }
    public void updateStock(int change) throws SQLException{
        QueryInProduct.UpdateStock(this, change);
        this.stock = this.stock - change;
    }
    public void changePrice(double newPrice) throws SQLException{
        QueryInProduct.changeProductPrice(this, newPrice);
        this.price = newPrice;
    }

    //getters
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

    public void print(){
        System.out.println("color= "+cl+" material= "+mt+" size= "+sz);
    }
}
