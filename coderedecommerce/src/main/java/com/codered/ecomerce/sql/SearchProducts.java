package com.codered.ecomerce.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.codered.ecomerce.model.*;
import com.codered.ecomerce.enums.*;

// parses and searches for imputted string
public class SearchProducts extends SwagConnection{
    static String search;

    private SearchProducts(){}

    // to get the result for direct strings
    public static void SearchHelperProduct(String token, ArrayList<Variant> searchResults){
        String sql = "SELECT * FROM product WHERE product_name LIKE %"+ token.toString() + "% OR DIFFERENCE (UPPER(product_name), "+token.toString()+") >= 3";
        ArrayList<Product> products = CentralShoppingSystem.getProducts();

        try (Connection conn = DriverManager.getConnection(properties.getProperty("url"), properties);
        PreparedStatement pstm = conn.prepareStatement(sql);
        ResultSet rt = pstm.executeQuery();){

            while(rt.next()){
                int id = rt.getInt("product_id");
                int count = 0;
                ArrayList<Variant> variants = products.get(id).getVariants();

                while(variants.get(count) == null){
                    count++;
                }
                searchResults.add(variants.get(count));
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    // to get the results for matching colors/materials/sizes
    public static <E extends Enum<E>> void SearchHelperCMS(E token, ArrayList<Variant> searchResults) throws SQLException{
        StringBuilder sql = new StringBuilder("SELECT * FROM product_price_stock WHERE ");
        ArrayList<Product> products = CentralShoppingSystem.getProducts();

        int numOther1;
        int numOther2;

        if (token.getClass().toString() == "Color"){
            sql.append("color = " + token.toString());
            numOther1 = Material.values().length;
            numOther2 = Size.values().length;
        }
        else if (token.getClass().toString() == "Material"){
            sql.append("material = " + token.toString());
            numOther1 = Color.values().length;
            numOther2 = Size.values().length;
        }
        else if (token.getClass().toString() == "Size"){
            sql.append("prod_size = " + token.toString());
            numOther1 = Color.values().length;
            numOther2 = Material.values().length;
        }
        else {throw new SQLException("invalid type");}

        try (Connection conn = DriverManager.getConnection(properties.getProperty("url"), properties);
        PreparedStatement pstm = conn.prepareStatement(sql.toString());
        ResultSet rt = pstm.executeQuery();){
            ArrayList<Integer> usedIDs = new ArrayList<Integer>();

            while(rt.next()){
                int id = rt.getInt("product_id");
                ArrayList<Variant> variants = products.get(id).getVariants();
                usedIDs.add(id,id);

                if (id == usedIDs.get(id)){
                    continue;
                }

                IndexHelper(numOther1, numOther2, variants, searchResults, token);
            }
        }
    }

    // so that the nested loops can be broken easily
    private static <E extends Enum<E>> int IndexHelper(int numOther1, int numOther2, ArrayList<Variant> variants, ArrayList<Variant> searchResults, E token){
        
        for (int i = 0; i < numOther1; i++){
            for (int j = 0; j < numOther2; j++){
                int index = 0;
                
                // get the index that contains x enum
                if (variants.get(i).getColor() == token){
                    int numColor = Color.values().length;
                    index = i * (numColor * numOther2) + token.ordinal() * numOther2 + j;
                }
                else if (variants.get(i).getMaterial() == token){
                    index = token.ordinal() * (numOther1 * numOther2) + i * numOther2 + j;
                }
                else if (variants.get(i).getSize() == token){
                    int numSize = Size.values().length;
                    index = j * (numOther1 * numSize) + i * numSize + token.ordinal();
                }
                else {return 0;}

                // if it exists add it to results if it's the last variant then stop itterating
                if (variants.get(index) != null){
                    searchResults.add(variants.get(index));
                }
                if (index >= variants.size() - 1){
                    return 0;
                }
            }
        }

        return 0;
    }
}
