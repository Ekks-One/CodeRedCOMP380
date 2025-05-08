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

import java.util.*;

import com.codered.ecomerce.model.*;
import com.codered.ecomerce.enums.*;

/* 
 * Takes in a string input and queries database for direct searches
 * compounds search the held objects.
 */
public class SearchProducts extends SwagConnection {
    private static String search;
    private static final int limit = 3200;

    private SearchProducts() {}

    // holds the search logic parsing/search appends
    public static ArrayList<Variant> Search(String srh) {
        search = srh.toUpperCase().trim();
        System.out.println("Search term: " + search);
        
        ArrayList<Variant> searchResults = new ArrayList<>();
        NameSearchHelper(search, searchResults);
        if (search.isEmpty()){
            return searchResults;
        }

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
            if ((search.contains(value) && (value != "S" || value != "M" || value != "L")) || (search.contains("SMALL") || search.contains("MEDIUM") || search.contains("LARGE"))) {
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

            //return searchResults;
        } else {
            System.out.println("No remaining term to search by name, final searchResults size before reduction: " + searchResults.size());
        }

        // Reduce to one variant per product at the end
        searchResults = reduceToOneVariantPerProduct(searchResults);
        if (searchResults != null){Collections.shuffle(searchResults);}
        return searchResults;
    }

    public static <E extends Enum<E>> void CompoundSearchHelper(E token, ArrayList<Variant> searchResults) {
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
                    switch (type) {
                        case "Brand":
                            if (products.get(var.getID()).getBrandID() == token.ordinal()) {
                                filter.add(var);
                            }
                            break;
                        case "Category":
                            if (products.get(var.getID()).getCategoryID() == token.ordinal()) {
                                var.setCategory(token.toString());
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
        } else {
            for (Variant var : searchResults) {
                if (var == null) continue; // Skip null variants
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

    public static ArrayList<Variant> reduceToOneVariantPerProduct(ArrayList<Variant> variants) {
        if (variants == null || variants.isEmpty()) {
            return new ArrayList<>();
        }

        Map<Integer, Variant> productToVariant = new HashMap<>();
        for (Variant var : variants) {
            if (var == null) continue;
            int productId = var.getID();
            // Keep the first variant for each product ID
            productToVariant.putIfAbsent(productId, var);
            if (productToVariant.size() >= limit) {
                break; // Stop once we have 'limit' unique products
            }
        }

        ArrayList<Variant> reducedResults = new ArrayList<>(productToVariant.values());
        System.out.println("Reduced to one variant per product, final size: " + reducedResults.size());
        return reducedResults;
    }

    public static void NameSearchHelper(String token, ArrayList<Variant> searchResults) {
        Map<Integer, Product> productMap = getProductMap();
        System.out.println("Total products from productMap: " + (productMap != null ? productMap.size() : "null"));

        if (productMap == null || productMap.isEmpty()) {
            searchResults.clear();
            return;
        }

        ArrayList<Variant> filter = new ArrayList<>();
        // Track variants per product, per color, per material: productId -> color -> material -> count
        Map<Integer, Map<Color, Map<Material, Integer>>> variantCounts = new HashMap<>();
        // Track which products matched the name search
        Set<Integer> matchedProductIds = new HashSet<>();

        if (searchResults.isEmpty()) {
            for (Product prod : productMap.values()) {
                if (prod == null) continue; // Skip null products
                ArrayList<Variant> variants = prod.getVariants();
                if (variants == null) continue; // Skip products with null variants
                for (Variant var : variants) {
                    if (var == null) continue; // Skip null variants
                    // Use fuzzy matching instead of exact contains
                    String productName = prod.getName().toUpperCase();
                    if (isFuzzyMatch(productName, token.toUpperCase())) {
                        int productId = var.getID();
                        matchedProductIds.add(productId); // Track matched product
                        Color color = var.getColor();
                        Material material = var.getMaterial();
                        search = search.replace(token, "").trim();

                        // Initialize nested maps if not present
                        variantCounts.computeIfAbsent(productId, k -> new HashMap<>());
                        Map<Color, Map<Material, Integer>> colorMap = variantCounts.get(productId);
                        colorMap.computeIfAbsent(color, k -> new HashMap<>());
                        Map<Material, Integer> materialMap = colorMap.get(color);

                        // Count total variants for this product and color
                        int totalVariantsForColor = materialMap.values().stream().mapToInt(Integer::intValue).sum();
                        if (totalVariantsForColor >= 2 && search.isEmpty()) {
                            System.out.println("Skipping variant for productId " + productId + ", color " + color + ": already have 2 variants for this color");
                            continue; // Already have 2 variants for this color
                        }

                        // Check if we already have a variant with this material
                        materialMap.compute(material, (k, v) -> v == null ? 1 : v + 1);
                        if (materialMap.get(material) > 1) {
                            // Undo the count since we can't add this variant (same material)
                            materialMap.put(material, materialMap.get(material) - 1);
                            System.out.println("Skipping variant for productId " + productId + ", color " + color + ": already have a variant with material " + material);
                            continue;
                        }

                        filter.add(var);
                        System.out.println("Added variant for productId " + productId + ", color " + color + ", material " + material + ", productName '" + prod.getName() + "'");
                    }
                }
            }
        } else {
            for (Variant var : searchResults) {
                if (var == null) continue; // Skip null variants
                Product prod = productMap.get(var.getID());
                if (prod == null) continue;
                // Use fuzzy matching instead of exact contains
                String productName = prod.getName().toUpperCase();
                if (isFuzzyMatch(productName, token.toUpperCase())) {
                    int productId = var.getID();
                    matchedProductIds.add(productId); // Track matched product
                    Color color = var.getColor();
                    Material material = var.getMaterial();
                    search = search.replace(token, "").trim();

                    // Initialize nested maps if not present
                    variantCounts.computeIfAbsent(productId, k -> new HashMap<>());
                    Map<Color, Map<Material, Integer>> colorMap = variantCounts.get(productId);
                    colorMap.computeIfAbsent(color, k -> new HashMap<>());
                    Map<Material, Integer> materialMap = colorMap.get(color);

                    // Count total variants for this product and color
                    int totalVariantsForColor = materialMap.values().stream().mapToInt(Integer::intValue).sum();
                    if (totalVariantsForColor >= 2 && search.isEmpty()) {
                        System.out.println("Skipping variant for productId " + productId + ", color " + color + ": already have 2 variants for this color");
                        continue; // Already have 2 variants for this color
                    }

                    // Check if we already have a variant with this material
                    materialMap.compute(material, (k, v) -> v == null ? 1 : v + 1);
                    if (materialMap.get(material) > 1) {
                        // Undo the count since we can't add this variant (same material)
                        materialMap.put(material, materialMap.get(material) - 1);
                        System.out.println("Skipping variant for productId " + productId + ", color " + color + ": already have a variant with material " + material);
                        continue;
                    }

                    filter.add(var);
                    System.out.println("Added variant for productId " + productId + ", color " + color + ", material " + material + ", productName '" + prod.getName() + "'");
                }
            }
        }

        System.out.println("Number of products matched by name search: " + matchedProductIds.size());
        searchResults.clear();
        searchResults.addAll(filter);
    }

    // Compute Levenshtein distance between two strings
    public static int levenshteinDistance(String s1, String s2) {
        int len1 = s1.length();
        int len2 = s2.length();
        int[][] dp = new int[len1 + 1][len2 + 1];

        for (int i = 0; i <= len1; i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j <= len2; j++) {
            dp[0][j] = j;
        }

        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                int cost = (s1.charAt(i - 1) == s2.charAt(j - 1)) ? 0 : 1;
                dp[i][j] = Math.min(
                    Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1),
                    dp[i - 1][j - 1] + cost
                );
            }
        }

        return dp[len1][len2];
    }

    // Check if two strings are a fuzzy match based on Levenshtein distance
    public static boolean isFuzzyMatch(String productName, String token) {
        // If the token is a substring (exact match), accept it immediately
        if (productName.contains(token)) {
            System.out.println("Exact match: productName '" + productName + "' contains token '" + token + "'");
            return true;
        }

        // Split product name into words once
        String[] words = productName.split("\\s+");
        int tokenLen = token.length();
        int threshold = Math.max(2, tokenLen / 3); // Stricter threshold
        System.out.println("Fuzzy matching: productName '" + productName + "', token '" + token + "', threshold " + threshold);

        // Check individual words
        for (String word : words) {
            // Skip if lengths differ too much
            int wordLen = word.length();
            if (wordLen < tokenLen / 2 || wordLen > tokenLen * 2) {
                System.out.println("Skipping word '" + word + "' due to length mismatch with token '" + token + "'");
                continue;
            }
            int distance = levenshteinDistance(word, token);
            System.out.println("Distance between word '" + word + "' and token '" + token + "': " + distance);
            if (distance <= threshold) {
                System.out.println("Fuzzy match on word: '" + word + "' matches token '" + token + "'");
                return true;
            }
        }

        // Check combinations of words
        for (int i = 0; i < words.length; i++) {
            StringBuilder combined = new StringBuilder(words[i]);
            for (int j = i + 1; j < words.length; j++) {
                combined.append(" ").append(words[j]);
                String combinedStr = combined.toString();
                int combinedLen = combinedStr.length();
                if (combinedLen < tokenLen / 2 || combinedLen > tokenLen * 2) {
                    System.out.println("Skipping combined '" + combinedStr + "' due to length mismatch with token '" + token + "'");
                    continue;
                }
                int distance = levenshteinDistance(combinedStr, token);
                System.out.println("Distance between combined '" + combinedStr + "' and token '" + token + "': " + distance);
                if (distance <= threshold) {
                    System.out.println("Fuzzy match on combined: '" + combinedStr + "' matches token '" + token + "'");
                    return true;
                }
            }
        }

        System.out.println("No fuzzy match for productName '" + productName + "' and token '" + token + "'");
        return false;
    }

    // creates a hash map of the products in the system for faster access
    public static Map<Integer, Product> getProductMap() {
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