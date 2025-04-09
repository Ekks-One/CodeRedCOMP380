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

    private MasterQuery(){}

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
        LinkedList<Customer> customers = new LinkedList<Customer>();
        getCustomer(customers);
    }

    // -----------------------------------PRODUCT INSERTION BLOCK START---------------------------------------------
    public static void InitializeProduct(String Name, int BrandID, int CategoryID, LinkedList<Color> CL, LinkedList<Material> MT, LinkedList<Size> SZ, double Price, Product product) throws SQLException{
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

    public static <T> void InsertCMS(int product_id, LinkedList<T> cms, char type, Connection conn) throws SQLException{
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

    public static void UpdateStock(Variant product, int change) throws SQLException{
        String Update = "UPDATE product_price_stock SET product_stock = ? WHERE product_id = ? AND product_color = ? AND product_size = ? AND product_material = ?";
        int upStock;
        int stock = product.getStock();

        if (stock > 0 && (0-change) <= stock){ // so wen using the method you can use a positive change to signify adding to the stock
            upStock = stock + change;
        }else if (stock > 0){
            upStock = 0;
            throw new SQLException("There isn't enough stock, only " + stock + " is/are available");
        }else{
            throw new SQLException("The stock is 0");
        }

        try (Connection conn = DriverManager.getConnection(properties.getProperty("url"), properties);
            PreparedStatement pstm = conn.prepareStatement(Update, Statement.RETURN_GENERATED_KEYS);){
            pstm.setInt(1, upStock);
            pstm.setInt(2, product.getID());
            pstm.setString(3, product.getColor().toString());
            pstm.setString(4, product.getSize().toString());
            pstm.setString(5, product.getMaterial().toString());
            pstm.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public static void changeProductPrice(Variant product, double newPrice){
        String update = "UPDATE product_price_stock SET product_price = ? WHERE product_id = ? AND product_color = ? AND product_size = ? AND product_material = ?";

        try(Connection conn = DriverManager.getConnection(properties.getProperty("url"), properties);
            PreparedStatement pstm = conn.prepareStatement(update, Statement.RETURN_GENERATED_KEYS);){
                pstm.setDouble(1, newPrice);
                pstm.setInt(2, product.getID());
                pstm.setString(3, product.getColor().toString());
                pstm.setString(4, product.getSize().toString());
                pstm.setString(5,product.getMaterial().toString());
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

    // Customer Queries
    public static void getCustomer(LinkedList<Customer> customers) throws SQLException{
        String sql = "SELECT * FROM customer";

        try (Connection conn = DriverManager.getConnection(properties.getProperty("url"), properties);
        PreparedStatement psmt = conn.prepareStatement(sql);
        ResultSet rt = psmt.executeQuery()){
            int count = 0;
            String ship = "SELECT * FROM customer_shipping WHERE customer_id = ?";

            while(rt.next()){
                int custId = rt.getInt("customer_id");
                String fname = rt.getString("customer_first_name");
                String lname = rt.getString("customer_last_name");
                String Email = rt.getString("customer_email");

                customers.add(new Customer(fname, lname, custId, Email));

                try (PreparedStatement Spstm = conn.prepareStatement(ship);){

                    Spstm.setInt(1, custId);
                    ResultSet Srt = Spstm.executeQuery();
                    String address = Srt.getString("customer_address"); //this won't work right now since the address table is empty 
                    customers.get(count).setShippingAddress(address);
                }

                customers.get(count).print();
                count++;
            }
        }
    }
}


