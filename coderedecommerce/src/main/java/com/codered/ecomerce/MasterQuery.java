package com.codered.ecomerce;

import com.codered.ecomerce.enums.*;
import com.codered.ecomerce.model.*;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.*;
import java.util.logging.Logger;

public class MasterQuery {
    private static final Logger log;
    private static final Properties properties = new Properties();

    static {
    System.setProperty("java.util.logging.SimpleFormatter.format", "[%4$-7s] %5$s %n");
    log = Logger.getLogger(MasterQuery.class.getName());

    try (InputStream input = MasterQuery.class.getClassLoader().getResourceAsStream("application.properties")) {
        if (input == null) {
            log.severe("Sorry, unable to find application.properties");
        } else {
            properties.load(input);
        }
    } catch (IOException e) {
        log.severe("Error loading application.properties: " + e.getMessage());
    }
}

    //main takes a while so get a cup of tea to watch it do it's thing
    public static void main(String[] args) throws Exception{
        LinkedList<Product> products = new LinkedList<Product>();
        getProducts(products);
    }

    // -----------------------------------PRODUCT INSERTION BLOCK START---------------------------------------------
    public static void InitializeProduct(String Name, int BrandID, int CategoryID, Color CL[], Material MT[], Size SZ[], double Price, Product product) throws SQLException{
        int productID;
        String sql = "INSERT INTO product (product_name, brand_id, category_id) VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(properties.getProperty("url"), properties)) {
            connection.setAutoCommit(false); // Start transaction
        
            try (PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                pstm.setString(1, Name);
                pstm.setInt(2, BrandID);
                pstm.setInt(3, CategoryID);
        
                int affRows = pstm.executeUpdate();
        
                if (affRows > 0) {
                    ResultSet generatedID = pstm.getGeneratedKeys();
                    if (generatedID.next()) {
                        productID = generatedID.getInt(1);
                        product = new Product(productID, Name, BrandID, CategoryID, CL, MT, SZ, Price);
        
                        InsertCMS(productID, CL, 'c', connection);
                        InsertCMS(productID, MT, 'm', connection);
                        InsertCMS(productID, SZ, 's', connection);

                        try {
                            Thread.sleep(2000); // Pause execution for 2 seconds
                        } catch (InterruptedException e) {
                            e.printStackTrace(); // Print the error if sleep is interrupted
                        }
        
                        for (int i = 0; i < CL.length; i++) {
                            for (int j = 0; j < MT.length; j++) {
                                for (int k = 0; k < SZ.length; k++) {
                                    InsertIntoPS(productID, CL[i], MT[j], SZ[k], Price, 0, connection);
                                }
                            }
                        }
        
                        connection.commit(); // Commit only after all operations succeed
                    } else {
                        System.out.println("Insert failed.");
                        connection.rollback(); // Rollback in case of failure
                    }
                }
            } catch (SQLException e) {
                connection.rollback(); // Rollback if any issue occurs
                e.printStackTrace();
            } finally {
                connection.setAutoCommit(true);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static <T> void InsertCMS(int product_id, T cms[], char type, Connection conn) throws SQLException{
        String cmsSql;

        switch (type) {
            case 'c':
                cmsSql = "INSERT INTO product_color VALUES (?,?)";
                break;
            case 'm':
                cmsSql = "INSERT INTO product_material VALUES (?,?)";
                break;
            case 's':
                cmsSql = "INSERT INTO product_size VALUES (?,?)";
                break;
            default:
                throw new SQLException("Not a valid type.");
        }

        try (PreparedStatement pstm = conn.prepareStatement(cmsSql, Statement.RETURN_GENERATED_KEYS);) {
            for (int i = 0; i < cms.length; i++ ){
                pstm.setInt(1, product_id);
                pstm.setString(2, cms[i].toString());
                pstm.executeUpdate();
            }
        }
    }

    public static void InsertIntoPS(int prodId, Color CL, Material MT, Size SZ, double Price, int Stock, Connection conn){
        String psSql = "INSERT INTO product_price_stock VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement tpstm = conn.prepareStatement(psSql, Statement.RETURN_GENERATED_KEYS);) {

            tpstm.setInt(1,prodId);
            tpstm.setString(2, CL.toString());
            tpstm.setString(3, SZ.toString());
            tpstm.setString(4, MT.toString());
            tpstm.setDouble(5, Price);
            tpstm.setInt(6, Stock);

            System.out.println("Inserting into product_price_stock: " + prodId + ", " + CL + ", " + SZ + ", " + MT + ", " + Price + ", " + Stock);
            tpstm.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }

    }
    // ----------------------------------------------END------------------------------------------------------------ 

    public static void UpdateStock(int prodID, Color cl, Size sz, Material mt, int stock, int change, Connection conn) throws SQLException{
        String Update = "UPDATE product_price_stock SET product_stock = ? WHERE product_id = ? AND product_color = ? AND product_size = ? AND product_material = ?";
        int upStock;

        if (stock > 0 && (0-change) <= stock){ // so wen using the method you can use a positive change to signify adding to the stock
            upStock = stock + change;
        }else if (stock > 0){
            upStock = 0;
            throw new SQLException("There isn't enough stock, only " + stock + " is/are available");
        }else{
            throw new SQLException("The stock is 0");
        }

        try (PreparedStatement pstm = conn.prepareStatement(Update, Statement.RETURN_GENERATED_KEYS);){
            pstm.setInt(1, upStock);
            pstm.setInt(2, prodID);
            pstm.setString(3, cl.toString());
            pstm.setString(4, sz.toString());
            pstm.setString(5, mt.toString());
            pstm.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    // populates the product objects
    public static void getProducts(LinkedList<Product> products){
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
                products.get(count).getVariants(); //automatically populates the variants
                count++;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    // populates the varient objects
    public static void getVariants(int prodID, LinkedList<Variant> variants){
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
}


