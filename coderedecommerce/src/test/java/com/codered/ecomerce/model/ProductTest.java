/**
 * CodeRed E-Commerce System
 * This {@code ProductTest} class is a test class for the {@code Product} class
 * and contains unit tests to verify the functionality of the Product class.
 * It includes tests for the constructors, add methods, getters, print method, and getVariants.
 * 
 * @author CodeRed Team (Jesus, Miguel)
 * @version 1.0
 * @see Product
 * @created on 05/02/2025
 */
package com.codered.ecomerce.model;

import com.codered.ecomerce.enums.Color;
import com.codered.ecomerce.enums.Material;
import com.codered.ecomerce.enums.Size;
import com.codered.ecomerce.sql.SwagConnection;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class ProductTest extends SwagConnection{

    @Test
    public void testFullConstructor() {
        ArrayList<Color> colors = new ArrayList<>();
        colors.add(Color.RED);
        ArrayList<Material> materials = new ArrayList<>();
        materials.add(Material.COTTON);
        ArrayList<Size> sizes = new ArrayList<>();
        sizes.add(Size.M);

        Product product = new Product(1, "T-Shirt", 101, 201) {
        };

        System.out.println("Testing Full Constructor:");
        System.out.println("Expected ID: 1, Observed: " + product.getID());
        System.out.println("Expected name: T-Shirt, Observed: " + product.getName());
        System.out.println("Expected brandID: 101, Observed: " + product.getBrandID());
        System.out.println("Expected categoryID: 201, Observed: " + product.getCategoryID());
        System.out.println("Expected colors: [RED], Observed: " + product.getColors());
        System.out.println("Expected materials: [COTTON], Observed: " + product.getMaterials());
        System.out.println("Expected sizes: [M], Observed: " + product.getSizes());
        System.out.println("Expected base price: 29.99, Observed: " + product.getBasePrice());

        assertEquals(1, product.getID(), "Expected ID: 1, but got: " + product.getID());
        assertEquals("T-Shirt", product.getName(), "Expected name: T-Shirt, but got: " + product.getName());
        assertEquals(101, product.getBrandID(), "Expected brandID: 101, but got: " + product.getBrandID());
        assertEquals(201, product.getCategoryID(), "Expected categoryID: 201, but got: " + product.getCategoryID());
        assertEquals(colors, product.getColors(), "Expected colors: [RED], but got: " + product.getColors());
        assertEquals(materials, product.getMaterials(), "Expected materials: [COTTON], but got: " + product.getMaterials());
        assertEquals(sizes, product.getSizes(), "Expected sizes: [M], but got: " + product.getSizes());
        assertEquals(29.99, product.getBasePrice(), 0.001, "Expected base price: 29.99, but got: " + product.getBasePrice());
    }

    @Test
    public void testMinimalConstructor() {
        Product product = new Product(2, "Jeans", 102, 202) {
            
        };

        System.out.println("Testing Minimal Constructor:");
        System.out.println("Expected ID: 2, Observed: " + product.getID());
        System.out.println("Expected name: Jeans, Observed: " + product.getName());
        System.out.println("Expected brandID: 102, Observed: " + product.getBrandID());
        System.out.println("Expected categoryID: 202, Observed: " + product.getCategoryID());
        System.out.println("Expected colors: [], Observed: " + product.getColors());
        System.out.println("Expected materials: [], Observed: " + product.getMaterials());
        System.out.println("Expected sizes: [], Observed: " + product.getSizes());
        System.out.println("Expected base price: 50.0, Observed: " + product.getBasePrice());

        assertEquals(2, product.getID(), "Expected ID: 2, but got: " + product.getID());
        assertEquals("Jeans", product.getName(), "Expected name: Jeans, but got: " + product.getName());
        assertEquals(102, product.getBrandID(), "Expected brandID: 102, but got: " + product.getBrandID());
        assertEquals(202, product.getCategoryID(), "Expected categoryID: 202, but got: " + product.getCategoryID());
        assertTrue(product.getColors().isEmpty(), "Expected colors: [], but got: " + product.getColors());
        assertTrue(product.getMaterials().isEmpty(), "Expected materials: [], but got: " + product.getMaterials());
        assertTrue(product.getSizes().isEmpty(), "Expected sizes: [], but got: " + product.getSizes());
        assertEquals(50.0, product.getBasePrice(), 0.001, "Expected base price: 50.0, but got: " + product.getBasePrice());
    }

    @Test
    public void testAddMethods() {
        Product product = new Product(3, "Sweater", 103, 203) {
        };

        product.addColor(Color.BLUE);
        product.addMaterial(Material.WOOL);
        product.addSize(Size.L);

        System.out.println("Testing Add Methods:");
        System.out.println("Expected colors: [BLUE], Observed: " + product.getColors());
        System.out.println("Expected materials: [WOOL], Observed: " + product.getMaterials());
        System.out.println("Expected sizes: [L], Observed: " + product.getSizes());

        assertTrue(product.getColors().contains(Color.BLUE), "Expected colors to contain BLUE, but got: " + product.getColors());
        assertEquals(1, product.getColors().size(), "Expected colors size: 1, but got: " + product.getColors().size());
        assertTrue(product.getMaterials().contains(Material.WOOL), "Expected materials to contain WOOL, but got: " + product.getMaterials());
        assertEquals(1, product.getMaterials().size(), "Expected materials size: 1, but got: " + product.getMaterials().size());
        assertTrue(product.getSizes().contains(Size.L), "Expected sizes to contain L, but got: " + product.getSizes());
        assertEquals(1, product.getSizes().size(), "Expected sizes size: 1, but got: " + product.getSizes().size());
    }

    @Test
    public void testGetVariants() {
        // Create a test product with public fetchVariants
        TestProduct product = new TestProduct(1, "Jacket", 1, 1);
        Variant expectedVariant = new Variant(1, Color.BLACK, Material.COTTON, Size.M, 5, 31.0);

        // Call public fetchVariants and add the variant
        product.addVariantForTest(expectedVariant);

        ArrayList<Variant> variants = product.getVariants();

        System.out.println("Testing Get Variants:");
        System.out.println("Expected variants size: 1, Observed: " + variants.size());
        System.out.println("Expected variant: " + expectedVariant + ", Observed variants: " + variants);

        assertNotNull(variants, "Variants list should not be null");
        assertEquals(1, variants.size(), "Expected 1 variant, but got: " + variants.size());
        assertTrue(variants.contains(expectedVariant), "Expected variant " + expectedVariant + " to be in the list, but got: " + variants);
    }

    // Test-specific subclass to access and manipulate variants
    private static class TestProduct extends Product {
        public TestProduct(int id, String name, int brandID, int categoryID) {
            super(id, name, brandID, categoryID);
        }

        public void addVariantForTest(Variant variant) {
            this.getVariants().add(variant);
        }
    }
}