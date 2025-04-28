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
 * compounds search the held objects.
 */
public class SearchProducts extends SwagConnection {
    private static String search;
    private static final int limit = 32;

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
    
        // Parse enums
        for (Brand B : Brand.values()) {
            String label = B.getLabel().toUpperCase();
            System.out.println("Checking Brand: " + label);
            if (search.contains(label)) {
                search = search.replace(label, "").trim();
                System.out.println("After Brand match, search: " + search);
                System.out.println("Before CompoundSearchHelper for Brand " + label + ", searchResults size: " + searchResults.size());
                CompoundSearchHelper(B, searchResults);
                System.out.println("After CompoundSearchHelper for Brand " + label + ", searchResults size: " + searchResults.size());
            }
        }
        for (Category C : Category.values()) {
            String label = C.getLabel().toUpperCase();
            System.out.println("Checking Category: " + label);
            if (search.contains(label)) {
                search = search.replace(label, "").trim();
                System.out.println("After Category match, search: " + search);
                System.out.println("Before CompoundSearchHelper for Category " + label + ", searchResults size: " + searchResults.size());
                CompoundSearchHelper(C, searchResults);
                System.out.println("After CompoundSearchHelper for Category " + label + ", searchResults size: " + searchResults.size());
            }
        }
        for (Color C : Color.values()) {
            String value = C.toString().toUpperCase();
            System.out.println("Checking Color: " + value);
            if (search.contains(value)) {
                search = search.replace(value, "").trim();
                System.out.println("After Color match, search: " + search);
                System.out.println("Before CompoundSearchHelper for Color " + value + ", searchResults size: " + searchResults.size());
                CompoundSearchHelper(C, searchResults);
                System.out.println("After CompoundSearchHelper for Color " + value + ", searchResults size: " + searchResults.size());
            }
        }
        for (Material M : Material.values()) {
            String value = M.toString().toUpperCase();
            System.out.println("Checking Material: " + value);
            if (search.contains(value)) {
                search = search.replace(value, "").trim();
                System.out.println("After Material match, search: " + search);
                System.out.println("Before CompoundSearchHelper for Material " + value + ", searchResults size: " + searchResults.size());
                CompoundSearchHelper(M, searchResults);
                System.out.println("After CompoundSearchHelper for Material " + value + ", searchResults size: " + searchResults.size());
            }
        }
        for (Size S : Size.values()) {
            String value = S.toString().toUpperCase();
            System.out.println("Checking Size: " + value);
            if (search.contains(value)) {
                search = search.replace(value, "").trim();
                System.out.println("After Size match, search: " + search);
                System.out.println("Before CompoundSearchHelper for Size " + value + ", searchResults size: " + searchResults.size());
                CompoundSearchHelper(S, searchResults);
                System.out.println("After CompoundSearchHelper for Size " + value + ", searchResults size: " + searchResults.size());
            }
        }
    
        // Search remaining string
        if (!search.isEmpty()) {
            System.out.println("Searching remaining term: " + search);
            System.out.println("Before NameSearchHelper for term " + search + ", searchResults size: " + searchResults.size());
            NameSearchHelper(search, searchResults);
            System.out.println("After NameSearchHelper for term " + search + ", searchResults size: " + searchResults.size());
        } else {
            System.out.println("No remaining term to search by name, final searchResults size: " + searchResults.size());
        }
    
        return searchResults;
    }

    private static <E extends Enum<E>> void CompoundSearchHelper(E token, ArrayList<Variant> searchResults) {
        Map<Integer, Product> productMap = getProductMap();
        ArrayList<Product> products = CentralShoppingSystem.getProducts();
        System.out.println("Total products from productMap: " + (productMap != null ? productMap.size() : "null"));
        String type = token.getClass().getSimpleName();
    
        if (productMap == null || productMap.isEmpty()) {
            searchResults.clear();
            return;
        }
    
        ArrayList<Variant> filter = new ArrayList<>();
        if (searchResults.isEmpty()) {
            for (Product prod : productMap.values()) {
                if (prod == null) continue; // Skip null products
                ArrayList<Variant> variants = prod.getVariants();
                if (variants == null) continue; // Skip products with null variants
                for (Variant var : variants) {
                    if (var == null) continue; // Skip null variants
                    if (filter.size() >= limit) {
                        break;
                    }
                    switch (type) {
                        case "Brand":
                            if (products.get(var.getID()).getBrandID() == token.ordinal()) {
                                filter.add(var);
                            }
                            break;
                        case "Category":
                            if (products.get(var.getID()).getCategoryID() == token.ordinal()) {
                                filter.add(var);
                            }
                            break;
                        case "Color":
                            if (var.getColor() == token) {
                                filter.add(var);
                            }
                            break;
                        case "Material":
                            if (var.getMaterial() == token) {
                                filter.add(var);
                            }
                            break;
                        case "Size":
                            if (var.getSize() == token) {
                                filter.add(var);
                            }
                            break;
                        default:
                            break;
                    }
                }
                if (filter.size() >= limit) {
                    break;
                }
            }
        } else {
            for (Variant var : searchResults) {
                if (var == null) continue; // Skip null variants
                if (filter.size() >= limit) {
                    break;
                }
                Product prod = productMap.get(var.getID());
                if (prod == null) continue;
                switch (type) {
                    case "Brand":
                        if (products.get(var.getID()).getBrandID() == token.ordinal()) {
                            filter.add(var);
                        }
                        break;
                    case "Category":
                        if (products.get(var.getID()).getCategoryID() == token.ordinal()) {
                            filter.add(var);
                        }
                        break;
                    case "Color":
                        if (var.getColor() == token) {
                            filter.add(var);
                        }
                        break;
                    case "Material":
                        if (var.getMaterial() == token) {
                            filter.add(var);
                        }
                        break;
                    case "Size":
                        if (var.getSize() == token) {
                            filter.add(var);
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    
        searchResults.clear();
        searchResults.addAll(filter);
    }

    private static void NameSearchHelper(String token, ArrayList<Variant> searchResults) {
        Map<Integer, Product> productMap = getProductMap();
        System.out.println("Total products from productMap: " + (productMap != null ? productMap.size() : "null"));
    
        if (productMap == null || productMap.isEmpty()) {
            searchResults.clear();
            return;
        }
    
        ArrayList<Variant> filter = new ArrayList<>();
        if (searchResults.isEmpty()) {
            for (Product prod : productMap.values()) {
                if (prod == null) continue; // Skip null products
                ArrayList<Variant> variants = prod.getVariants();
                if (variants == null) continue; // Skip products with null variants
                for (Variant var : variants) {
                    if (var == null) continue; // Skip null variants
                    if (filter.size() >= limit) {
                        break;
                    }
                    if (prod.getName().toUpperCase().contains(token.toUpperCase())) {
                        filter.add(var);
                    }
                }
                if (filter.size() >= limit) {
                    break;
                }
            }
        } else {
            for (Variant var : searchResults) {
                if (var == null) continue; // Skip null variants
                if (filter.size() >= limit) {
                    break;
                }
                Product prod = productMap.get(var.getID());
                if (prod == null) continue;
                if (prod.getName().toUpperCase().contains(token.toUpperCase())) {
                    filter.add(var);
                }
            }
        }
    
        searchResults.clear();
        searchResults.addAll(filter);
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
}