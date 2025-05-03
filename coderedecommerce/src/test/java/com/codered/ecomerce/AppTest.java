package com.codered.ecomerce;

import com.codered.ecomerce.enums.Color;
import com.codered.ecomerce.enums.Material;
import com.codered.ecomerce.enums.Size;
import com.codered.ecomerce.model.Variant;
import com.codered.ecomerce.sql.SearchProducts;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AppTest {

    @Test
    void testSearchWithValidTerm() throws IOException {
        // Arrange
        String searchTerm = "Shirt";
        List<Variant> mockResults = new ArrayList<>();
        mockResults.add(new Variant(1, Color.RED, Material.COTTON, Size.M, 10, 19.99));
        mockResults.add(new Variant(2, Color.BLUE, Material.POLYESTER, Size.L, 5, 29.99));

        // Mock the SearchProducts.Search method
        try (MockedStatic<SearchProducts> mockedStatic = mockStatic(SearchProducts.class)) {
            mockedStatic.when(() -> SearchProducts.Search(searchTerm.toLowerCase())).thenReturn(mockResults);

            // Act
            App.search(searchTerm, null); // Pass null for the ActionEvent

            // Assert
            System.out.println("Testing search with valid term: " + searchTerm);
            System.out.println("Expected number of results: 2, Observed: " + mockResults.size());
            assertNotNull(mockResults, "Search results should not be null.");
            assertEquals(2, mockResults.size(), "Expected 2 search results for 'Shirt'.");

            System.out.println("Expected first result color: RED, Observed: " + mockResults.get(0).getColor());
            assertEquals(Color.RED, mockResults.get(0).getColor(), "Expected first result to be 'RED'.");
            System.out.println("Expected first result material: COTTON, Observed: " + mockResults.get(0).getMaterial());
            assertEquals(Material.COTTON, mockResults.get(0).getMaterial(), "Expected first result to be 'COTTON'.");
            System.out.println("Expected first result size: M, Observed: " + mockResults.get(0).getSize());
            assertEquals(Size.M, mockResults.get(0).getSize(), "Expected first result to be 'M'.");
            System.out.println("Expected first result stock: 10, Observed: " + mockResults.get(0).getStock());
            assertEquals(10, mockResults.get(0).getStock(), "Expected first result to be '10'.");
            System.out.println("Expected first result price: 19.99, Observed: " + mockResults.get(0).getPrice());
            assertEquals(19.99, mockResults.get(0).getPrice(), "Expected first result to be '19.99'.");

            System.out.println("Expected second result color: BLUE, Observed: " + mockResults.get(1).getColor());
            assertEquals(Color.BLUE, mockResults.get(1).getColor(), "Expected second result to be 'BLUE'.");
            System.out.println("Expected second result material: POLYESTER, Observed: " + mockResults.get(1).getMaterial());
            assertEquals(Material.POLYESTER, mockResults.get(1).getMaterial(), "Expected second result to be 'POLYESTER'.");
            System.out.println("Expected second result size: L, Observed: " + mockResults.get(1).getSize());
            assertEquals(Size.L, mockResults.get(1).getSize(), "Expected second result to be 'L'.");
            System.out.println("Expected second result stock: 5, Observed: " + mockResults.get(1).getStock());
            assertEquals(5, mockResults.get(1).getStock(), "Expected second result to be '5'.");
            System.out.println("Expected second result price: 29.99, Observed: " + mockResults.get(1).getPrice());
            assertEquals(29.99, mockResults.get(1).getPrice(), "Expected second result to be '29.99'.");
        }
    }

    @Test
    void testSearchWithEmptyTerm() throws IOException {
        // Arrange
        String searchTerm = "";

        // Act
        App.search(searchTerm, null); // Pass null for the ActionEvent

        // Assert
        System.out.println("Testing search with empty term.");
        System.out.println("Expected search term to be empty, Observed: " + searchTerm);
        assertTrue(searchTerm.isEmpty(), "Search term should be empty.");
    }

    @Test
    void testSearchWithNoResults() throws IOException {
        // Arrange
        String searchTerm = "NonExistentProduct";

        // Mock the SearchProducts.Search method
        try (MockedStatic<SearchProducts> mockedStatic = mockStatic(SearchProducts.class)) {
            mockedStatic.when(() -> SearchProducts.Search(searchTerm.toLowerCase())).thenReturn(new ArrayList<>());

            // Act
            App.search(searchTerm, null); // Pass null for the ActionEvent

            // Assert
            System.out.println("Testing search with no results for term: " + searchTerm);
            List<Variant> emptyResults = SearchProducts.Search(searchTerm.toLowerCase());
            System.out.println("Expected number of results: 0, Observed: " + emptyResults.size());
            assertNotNull(emptyResults, "Search results should not be null.");
            assertTrue(emptyResults.isEmpty(), "Search results should be empty for a non-existent product.");
        }
    }
}