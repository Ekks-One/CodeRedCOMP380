/**
 * CodRed E-Commerce System
 * This class handles database queries related to products and variants.
 * It retrieves product and variant information from the database and populates the respective objects.
 * 
 * @author CodeRed Team (Jesus)
 * @version 1.0
 * @created on 04/15/2025
 */
package com.codered.ecomerce.sql;

import java.sql.*;
import java.util.*;

import com.codered.ecomerce.model.*;
import com.codered.ecomerce.enums.*;

/* 
 * Takes in a string input and queries database for direct searches
 * compunds search the held objects.
 */
public class SearchProducts extends SwagConnection {
    private static String search;

    private SearchProducts() {}

    /*public static void main(String[] args) {
        ArrayList<Variant> results = Search("black L cotton");
        System.out.println("Final search results: " + results);
    }
*/

    // holds the search logic parsing/search appends
    public static ArrayList<Variant> Search(String srh) {
        search = srh.toUpperCase().trim();
        System.out.println("Search term: " + search);
        
        ArrayList<Variant> searchResults = new ArrayList<>();
        ArrayList<Color> cl = new ArrayList<>();
        ArrayList<Material> mt = new ArrayList<>();
        ArrayList<Size> sz = new ArrayList<>();
        ArrayList<Brand> bd = new ArrayList<>();
        ArrayList<Category> ct = new ArrayList<>();

        // Initial product name or description search
        SearchHelperProduct(search, searchResults);

        // Parse enums
        for (Brand B : Brand.values()) {
            String label = B.getLabel().toUpperCase();
            System.out.println("Checking Brand: " + label);
            if (search.contains(label)) {
                search = search.replace(label, "").trim();
                System.out.println("After Brand match, search: " + search);
                if (search.isEmpty() && bd.isEmpty()) {
                    SearchHelperBC(B, searchResults);
                } else {
                    bd.add(B);
                }
            }
        }
        for (Category C : Category.values()) {
            String label = C.getLabel().toUpperCase();
            System.out.println("Checking Category: " + label);
            if (search.contains(label)) {
                search = search.replace(label, "").trim();
                System.out.println("After Category match, search: " + search);
                if (search.isEmpty() && ct.isEmpty()) {
                    SearchHelperBC(C, searchResults);
                } else {
                    ct.add(C);
                }
            }
        }
        for (Color C : Color.values()) {
            String value = C.toString().toUpperCase();
            System.out.println("Checking Color: " + value);
            if (search.contains(value)) {
                search = search.replace(value, "").trim();
                System.out.println("After Color match, search: " + search);
                if (search.isEmpty() && cl.isEmpty()) {
                    SearchHelperCMS(C, searchResults);
                } else {
                    cl.add(C);
                }
            }
        }
        for (Material M : Material.values()) {
            String value = M.toString().toUpperCase();
            System.out.println("Checking Material: " + value);
            if (search.contains(value)) {
                search = search.replace(value, "").trim();
                System.out.println("After Material match, search: " + search);
                if (search.isEmpty() && mt.isEmpty()) {
                    SearchHelperCMS(M, searchResults);
                } else {
                    mt.add(M);
                }
            }
        }
        for (Size S : Size.values()) {
            String value = S.toString().toUpperCase();
            System.out.println("Checking Size: " + value);
            if (search.contains(value)) {
                search = search.replace(value, "").trim();
                System.out.println("After Size match, search: " + search);
                if (search.isEmpty() && sz.isEmpty()) {
                    SearchHelperCMS(S, searchResults);
                } else {
                    sz.add(S);
                }
            }
        }

        // Search remaining string
        if (!search.isEmpty()) {
            System.out.println("Searching remaining term: " + search);
            SearchHelperProduct(search, searchResults);
        }

        // Handle compound search for collected terms
        if (!bd.isEmpty() || !ct.isEmpty() || !cl.isEmpty() || !mt.isEmpty() || !sz.isEmpty()) {
            System.out.println("Compound search with Brands: " + bd + ", Categories: " + ct +
                               ", Colors: " + cl + ", Materials: " + mt + ", Sizes: " + sz);
            CompoundSearchHelper(bd, ct, cl, mt, sz, searchResults);
        }

        return searchResults;
    }

    // searches the available objects through a map
    private static void CompoundSearchHelper(List<Brand> brands, List<Category> categories, List<Color> colors, List<Material> materials, List<Size> sizes, ArrayList<Variant> searchResults) {
        Map<Integer, Product> productMap = getProductMap();
        System.out.println("Total products from CentralShoppingSystem: " + (productMap != null ? productMap.size() : "null"));

        if (productMap == null || productMap.isEmpty()) {
            System.out.println("No products available to search.");
            return;
        }

        for (Product product : productMap.values()) {
            // Check brand and category
            boolean matchesBrand = brands.isEmpty() || brands.stream().anyMatch(b -> 
                b.getLabel().equalsIgnoreCase(getBrandName(product.getBrandID())));
            boolean matchesCategory = categories.isEmpty() || categories.stream().anyMatch(c -> 
                c.getLabel().equalsIgnoreCase(getCategoryName(product.getCategoryID())));
            if (!matchesBrand || !matchesCategory) {
                continue;
            }
            System.out.println("Product matches Brand/Category: " + product);

            ArrayList<Variant> variants = product.getVariants();
            System.out.println("Variants for product: " + (variants != null ? variants.size() : "null"));
            if (variants == null || variants.isEmpty()) {
                System.out.println("No variants for this product.");
                continue;
            }

            for (Variant variant : variants) {
                if (variant == null) {
                    continue;
                }
                System.out.println("Checking variant: " + variant);
                boolean matchesColor = colors.isEmpty() || colors.contains(variant.getColor());
                boolean matchesMaterial = materials.isEmpty() || materials.contains(variant.getMaterial());
                boolean matchesSize = sizes.isEmpty() || sizes.contains(variant.getSize());

                //makes sure there are no repeats
                if (matchesColor && matchesMaterial && matchesSize && !searchResults.contains(variant)) {
                    System.out.println("Adding variant to results: " + variant);
                    searchResults.add(variant);
                }
            }
        }
    }

    // searches for specific product name or description
    private static void SearchHelperProduct(String token, ArrayList<Variant> searchResults) {
        String sql = "SELECT DISTINCT p.product_id " +
                     "FROM product p " +
                     "LEFT JOIN descriptions d ON p.product_id = d.product_id " +
                     "WHERE UPPER(p.product_name) LIKE ? OR UPPER(CAST(d.description AS NVARCHAR(MAX))) LIKE ?";
        Map<Integer, Product> productMap = getProductMap();
        System.out.println("Product map size: " + (productMap != null ? productMap.size() : "null"));

        try (Connection conn = DriverManager.getConnection(
                properties.getProperty("url"),
                properties.getProperty("user"),
                properties.getProperty("password"));
             PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, "%" + token.toUpperCase() + "%");
            pstm.setString(2, "%" + token.toUpperCase() + "%");
            System.out.println("Executing SearchHelperProduct query with token: " + token);

            try (ResultSet rs = pstm.executeQuery()) {
                int matchCount = 0;
                while (rs.next()) {
                    matchCount++;
                    int productId = rs.getInt("product_id");
                    System.out.println("Found product_id: " + productId);
                    Product product = productMap.get(productId);
                    if (product != null) {
                        ArrayList<Variant> variants = product.getVariants();
                        System.out.println("Variants for product_id " + productId + ": " + 
                                          (variants != null ? variants.size() : "null"));
                        if (variants == null || variants.isEmpty()) {
                            continue;
                        }
                        for (Variant variant : variants) {
                            if (variant != null && !searchResults.contains(variant)) {
                                System.out.println("Adding variant from SearchHelperProduct: " + variant);
                                searchResults.add(variant);
                                break; // Add first non-null variant
                            }
                        }
                    } else {
                        System.out.println("product_id " + productId + " not found in product map.");
                    }
                }
                System.out.println("SearchHelperProduct found " + matchCount + " matching products.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Product search failed: " + e.getMessage(), e);
        }
    }

    // searches brand and category tables
    private static <E extends Enum<E>> void SearchHelperBC(E token, ArrayList<Variant> searchResults) {
        String sql;
        String param;

        if (token.getClass().getSimpleName().equals("Brand")) {
            sql = "SELECT p.product_id " +
                  "FROM product p " +
                  "JOIN brand b ON p.brand_id = b.brand_id " +
                  "WHERE UPPER(b.brand_name) = ?";
            param = ((Brand) token).getLabel().toUpperCase();
        } else if (token.getClass().getSimpleName().equals("Category")) {
            sql = "SELECT p.product_id " +
                  "FROM product p " +
                  "JOIN category c ON p.category_id = c.category_id " +
                  "WHERE UPPER(c.category_name) = ?";
            param = ((Category) token).getLabel().toUpperCase();
        } else {
            return;
        }

        Map<Integer, Product> productMap = getProductMap();
        try (Connection conn = DriverManager.getConnection(
                properties.getProperty("url"),
                properties.getProperty("user"),
                properties.getProperty("password"));
             PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, param);
            System.out.println("Executing SearchHelperBC query for " + token + " with param: " + param);

            try (ResultSet rs = pstm.executeQuery()) {
                int matchCount = 0;

                while (rs.next()) {
                    matchCount++;
                    int productId = rs.getInt("product_id");
                    System.out.println("Found product_id: " + productId);
                    Product product = productMap.get(productId);

                    if (product != null) {
                        ArrayList<Variant> variants = product.getVariants();
                        System.out.println("Variants for product_id " + productId + ": " + 
                                          (variants != null ? variants.size() : "null"));
                        if (variants == null || variants.isEmpty()) {
                            continue;
                        }
                        for (Variant variant : variants) {
                            if (variant != null && !searchResults.contains(variant)) {
                                System.out.println("Adding variant from SearchHelperBC: " + variant);
                                searchResults.add(variant);
                                break; // Add first non-null variant
                            }
                        }
                    } else {
                        System.out.println("product_id " + productId + " not found in product map.");
                    }
                }

                System.out.println("SearchHelperBC found " + matchCount + " matching products.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Brand/Category search failed: " + e.getMessage(), e);
        }
    }

    // searches a hash map and defaults to queries if none are found.
    private static <E extends Enum<E>> void SearchHelperCMS(E token, ArrayList<Variant> searchResults) {
        Map<Integer, Product> productMap = getProductMap();
        System.out.println("Executing SearchHelperCMS for " + token);
        boolean foundVariantsInProducts = false;

        if (productMap != null && !productMap.isEmpty()) {
            for (Product product : productMap.values()) {
                ArrayList<Variant> variants = product.getVariants();
                System.out.println("Checking variants for product: " + product + ", Variants: " + (variants != null ? variants.size() : "null"));

                if (variants == null || variants.isEmpty()) {
                    continue;
                }
                for (Variant variant : variants) {
                    if (variant == null) {
                        continue;
                    }

                    System.out.println("Variant: " + variant);
                    boolean matches = false;

                    if (token.getClass().getSimpleName().equals("Color")) {
                        matches = token.equals(variant.getColor());
                    } else if (token.getClass().getSimpleName().equals("Material")) {
                        matches = token.equals(variant.getMaterial());
                    } else if (token.getClass().getSimpleName().equals("Size")) {
                        matches = token.equals(variant.getSize());
                    }

                    if (matches && !searchResults.contains(variant)) {
                        System.out.println("Adding variant from SearchHelperCMS (pre-fetched): " + variant);
                        searchResults.add(variant);
                        foundVariantsInProducts = true;
                    }
                }
            }
        }

        // Fallback: Directly query the database if no variants were found in pre-fetched products
        if (!foundVariantsInProducts) {
            System.out.println("No matching variants found in pre-fetched products. Querying database directly.");
            String sql;
            String param = token.toString();

            if (token.getClass().getSimpleName().equals("Color")) {
                sql = "SELECT product_id, color, material, prod_size, product_stock, product_price " +
                      "FROM product_price_stock WHERE UPPER(color) = ?";
            } else if (token.getClass().getSimpleName().equals("Material")) {
                sql = "SELECT product_id, color, material, prod_size, product_stock, product_price " +
                      "FROM product_price_stock WHERE UPPER(material) = ?";
            } else if (token.getClass().getSimpleName().equals("Size")) {
                sql = "SELECT product_id, color, material, prod_size, product_stock, product_price " +
                      "FROM product_price_stock WHERE UPPER(prod_size) = ?";
            } else {
                return;
            }

            try (Connection conn = DriverManager.getConnection(
                    properties.getProperty("url"),
                    properties.getProperty("user"),
                    properties.getProperty("password"));
                 PreparedStatement pstm = conn.prepareStatement(sql)) {
                pstm.setString(1, param.toUpperCase());
                
                try (ResultSet rs = pstm.executeQuery()) {
                    while (rs.next()) {
                        int productId = rs.getInt("product_id");
                        String colorStr = rs.getString("color");
                        String materialStr = rs.getString("material");
                        String sizeStr = rs.getString("prod_size");
                        int stock = rs.getInt("product_stock");
                        double price = rs.getDouble("product_price");

                        // Convert strings to enums
                        Color color;
                        Material material;
                        Size size;
                        try {
                            color = colorStr != null ? Color.valueOf(colorStr.toUpperCase()) : null;
                            material = materialStr != null ? Material.valueOf(materialStr.toUpperCase()) : null;
                            size = sizeStr != null ? Size.valueOf(sizeStr.toUpperCase()) : null;
                        } catch (IllegalArgumentException e) {
                            System.out.println("Failed to convert database values to enums: " + e.getMessage());
                            continue;
                        }

                        // Create Variant object
                        Variant variant = new Variant(productId, color, material, size, stock, price);
                        if (!searchResults.contains(variant)) {
                            System.out.println("Adding variant from SearchHelperCMS (database): " + variant);
                            searchResults.add(variant);
                        }
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException("CMS search failed: " + e.getMessage(), e);
            }
        }
    }

    // creates a hash map of the products in the system for faster access
    private static Map<Integer, Product> getProductMap() {
        ArrayList<Product> products = CentralShoppingSystem.getProducts();
        Map<Integer, Product> productMap = new HashMap<>();

        if (products != null) {
            for (Product product : products) {
                if (product != null) {
                    productMap.put(product.getID(), product);
                }
            }
        }

        return productMap;
    }

    private static String getBrandName(int brandId) {
        String sql = "SELECT brand_name FROM brand WHERE brand_id = ?";

        try (Connection conn = DriverManager.getConnection(
                properties.getProperty("url"),
                properties.getProperty("user"),
                properties.getProperty("password"));
             PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setInt(1, brandId);
            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    String brandName = rs.getString("brand_name");
                    System.out.println("Brand ID " + brandId + " -> " + brandName);
                    return brandName;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get brand name: " + e.getMessage(), e);
        }

        System.out.println("Brand ID " + brandId + " not found.");

        return "";
    }

    private static String getCategoryName(int categoryId) {
        String sql = "SELECT category_name FROM category WHERE category_id = ?";

        try (Connection conn = DriverManager.getConnection(
                properties.getProperty("url"),
                properties.getProperty("user"),
                properties.getProperty("password"));
             PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setInt(1, categoryId);
            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    String categoryName = rs.getString("category_name");
                    System.out.println("Category ID " + categoryId + " -> " + categoryName);
                    return categoryName;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get category name: " + e.getMessage(), e);
        }

        System.out.println("Category ID " + categoryId + " not found.");

        return "";
    }
}