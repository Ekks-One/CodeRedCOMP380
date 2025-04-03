package com.sql.swagShop;

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
    //private subProduct[] sProds;

    public void setProduct(int id, String Name, int BrandID, int CategoryID, Color CL[], Material MT[], Size SZ[], double Price) {
        this.ID = id;
        this.name = Name;
        this.brandID = BrandID;
        this.categoryID = CategoryID;
        this.cl = CL;
        this.mt = MT;
        this.sz = SZ;
        this.basePrice = Price;
    }
}
