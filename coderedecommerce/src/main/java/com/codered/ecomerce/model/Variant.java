package com.codered.ecomerce.model;

import com.codered.ecomerce.enums.Color;
import com.codered.ecomerce.enums.Material;
import com.codered.ecomerce.enums.Size;

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

    public void print(){
        System.out.println("color= "+cl+" material= "+mt+" size= "+sz);
    }
}
