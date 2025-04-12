package com.codered.ecomerce.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.codered.ecomerce.model.Product;
import com.codered.ecomerce.model.Variant;
import com.codered.ecomerce.enums.*;

public class QuerySeProduct extends SwagConnection{
    // populates the product objects
    public static void getProducts(ArrayList<Product> products){
        String sql = "SELECT * FROM product";

        try (Connection connection = DriverManager.getConnection(properties.getProperty("url"), properties);
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet rt = pstm.executeQuery();){
            int count = 0;
            
            while (rt.next()){ //reads the returned table from the query
                int id = rt.getInt("product_id");
                String name = rt.getString("product_name");
                int brandId = rt.getInt("brand_id");
                int categoryId = rt.getInt("category_id");

                products.add(new Product(id,name,brandId,categoryId));
                products.get(count).print(); //visualizes the testing
                count++;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    // populates the varient objects
    public static void getVariants(int prodID, ArrayList<Variant> variants){
        String sql = "SELECT * FROM product_price_stock";

        try (Connection conn = DriverManager.getConnection(properties.getProperty("url"), properties);
        PreparedStatement pstm = conn.prepareStatement(sql);
        ResultSet rt = pstm.executeQuery()){
            int count = 0;

            while(rt.next()){
                Color cl = Color.valueOf(rt.getString("color").toUpperCase());
                Material mt = Material.valueOf(rt.getString("material").toUpperCase());
                Size sz = Size.valueOf(rt.getString("prod_size").toUpperCase());
                int stock = rt.getInt("product_stock");
                double price = rt.getDouble("product_price");

                variants.add(new Variant(prodID, cl, mt, sz, stock, price));
                variants.get(count).print();
                count++;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void SearchProducts(String search){
        String[] tokens = search.toUpperCase().split("\\s+");
        
        String sqlP = "SELECT * FROM product WHERE product_name = "+search.toUpperCase();
        String ppsC = "SELECT * FROM product_price_stock WHERE color = "+search.toUpperCase();
        String ppsM = "SELECT * FROM product_price_stock WHERE material = "+search.toUpperCase();
        String ppsS = "SELECT * FROM product_price_stock WHERE prod_size = "+search.toUpperCase();
        String sqlB = "SELECT * FROM brand WHERE brand_name = "+search.toUpperCase();
        String sqlC = "SELECT * FROM category WHERE category_name = "+search.toUpperCase();

        ArrayList<Variant> searchResults = new ArrayList<Variant>();

    }

}
