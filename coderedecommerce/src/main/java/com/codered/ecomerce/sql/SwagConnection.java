package com.codered.ecomerce.sql;

import com.codered.ecomerce.enums.*;
import com.codered.ecomerce.model.*;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.*;
import java.util.logging.Logger;

// Just here to use the properties file to connect to database
public class SwagConnection {
    protected static final Logger log;
    protected static final Properties properties = new Properties();

    protected SwagConnection(){}

    static {
        System.setProperty("java.util.logging.SimpleFormatter.format", "[%4$-7s] %5$s %n");
        log = Logger.getLogger(SwagConnection.class.getName());

        try (InputStream input = SwagConnection.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                log.severe("Sorry, unable to find application.properties");
            } else {
                properties.load(input);
            }
        } catch (IOException e) {
            log.severe("Error loading application.properties: " + e.getMessage());
        }
    }

    // -----------------------------------PRODUCT INSERTION BLOCK START---------------------------------------------
    public static void InitializeProduct(String Name, int BrandID, int CategoryID, ArrayList<Color> CL, ArrayList<Material> MT, ArrayList<Size> SZ, double Price, Product product) throws SQLException{
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
        
                        for (Color cl : CL) {
                            for (Material mt : MT) {
                                for (Size sz : SZ) {
                                    InsertIntoPS(productID, cl, mt, sz, Price, 0, connection);
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

    public static <T> void InsertCMS(int product_id, ArrayList<T> cms, char type, Connection conn) throws SQLException{
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
            for (T Gcms : cms){
                pstm.setInt(1, product_id);
                pstm.setString(2, Gcms.toString());
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


}


