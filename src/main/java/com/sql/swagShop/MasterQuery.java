package com.sql.swagShop;

import com.enums.Color;
import com.enums.Material;
import com.enums.Size;

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


    public static void main(String[] args) throws Exception{
        Color CL[] = {Color.BLUE, Color.GREEN, Color.RED};
        Material MT[] = {Material.COTTON, Material.FLEECE};
        Size SZ[] = {Size.L, Size.M};
        String name = "Garbage";
        double price = 0.56;
        Product product = InitializeProduct(name, 1, 1, CL, MT , SZ, price );
    }

    // -----------------------------------PRODUCT INSERTION BLOCK START---------------------------------------------
    public static Product InitializeProduct(String Name, int BrandID, int CategoryID, Color CL[], Material MT[], Size SZ[], double Price){
        Product product = new Product();
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
                        product.setProduct(productID, Name, BrandID, CategoryID, CL, MT, SZ, Price);
        
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
                        return null;
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

        return product;
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

    public static void UpdateStock(int prodID, Color cl, Size sz, Material mt, int change, Connection conn){

    }
}
