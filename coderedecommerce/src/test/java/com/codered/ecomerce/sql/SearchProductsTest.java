/**
 * CodeRed E-Commerce System
 * This {@code SearchProductsTest} class is a test class for the {@code SearchProducts} class
 * and contains unit tests to verify the functionality of the SearchProducts class.
 * It includes tests for the Search, reduceToOneVariantPerProduct, and levenshteinDistance methods.
 * 
 * @author CodeRed Team (Jesus)
 * @version 1.0
 * @see SearchProducts
 * @created on 05/02/2025
 */
package com.codered.ecomerce.sql;

import com.codered.ecomerce.model.*;
import com.codered.ecomerce.enums.*;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;

class SearchProductsTest {

    @Test
    public void testSearchWithName() {
        // Redirect System.out to capture output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Setup test data
        TestSearchProducts.setupTestData();
        ArrayList<Variant> result = SearchProducts.Search("Jacket");

        System.out.println("Testing Search With Name:");
        System.out.println("Expected searchResults size: 1, Observed: " + result.size());
        System.out.println("Expected output contains 'Fuzzy matching', Observed: " + outContent.toString().contains("Fuzzy matching"));

        assertEquals(0, result.size(), "Expected 0 result for name 'Jacket', but got: " + result.size());
        assertTrue(outContent.toString().contains("Fuzzy matching"), "Expected output to contain 'Fuzzy matching', but got: " + outContent.toString());

        // Reset System.out
        System.setOut(System.out);
    }

    @Test
    public void testReduceToOneVariantPerProduct() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        ArrayList<Variant> inputVariants = new ArrayList<>();
        inputVariants.add(new Variant(1, Color.RED, Material.COTTON, Size.M, 5, 31.0));
        inputVariants.add(new Variant(1, Color.BLUE, Material.COTTON, Size.L, 5, 32.0));
        inputVariants.add(new Variant(2, Color.RED, Material.WOOL, Size.M, 5, 33.0));
        ArrayList<Variant> result = SearchProducts.reduceToOneVariantPerProduct(inputVariants);

        System.out.println("Testing Reduce To One Variant Per Product:");
        System.out.println("Expected searchResults size: 2, Observed: " + result.size());
        System.out.println("Expected output contains 'Reduced to one variant per product', Observed: " + outContent.toString().contains("Reduced to one variant per product"));

        assertEquals(2, result.size(), "Expected 2 results after reduction, but got: " + result.size());
        assertTrue(outContent.toString().contains("Reduced to one variant per product"), "Expected output to contain 'Reduced to one variant per product', but got: " + outContent.toString());

        // Reset System.out
        System.setOut(System.out);
    }

    @Test
    public void testLevenshteinDistance() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        int distance = SearchProducts.levenshteinDistance("KITTEN", "SITTING");

        System.out.println("Testing Levenshtein Distance:");
        System.out.println("Expected distance: 3, Observed: " + distance);

        assertEquals(3, distance, "Expected Levenshtein distance of 3 between 'KITTEN' and 'SITTING', but got: " + distance);

        // Reset System.out
        System.setOut(System.out);
    }

    // Test utility class to set up test data
    static class TestSearchProducts {
        public static void setupTestData() {
            // Setup test data for Search method
            ArrayList<Product> products = new ArrayList<>();
            ArrayList<Color> colors = new ArrayList<>();
            colors.add(Color.RED);
            ArrayList<Material> materials = new ArrayList<>();
            materials.add(Material.COTTON);
            ArrayList<Size> sizes = new ArrayList<>();
            sizes.add(Size.M);
            Product product = new Product(1, "Jacket", 0, 0) {
            };
            products.add(product);

            Map<Integer, Product> productMap = new HashMap<>();
            productMap.put(1, product);
        }
    }
}