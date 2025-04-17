/**
 * CodeRed E-Commerce Application
 * This {@code searchResultsController} class is responsible for handling the search functionality in the application.
 * It allows users to search for items using a search bar and menu options.
 * 
 * @author CodeRed Team (Xavier, Afredo)
 * @version 1.0
 * @created on 04/14/2025
 */ 
package com.codered.ecomerce;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.codered.ecomerce.model.*;
import com.codered.ecomerce.enums.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * This class is responsible for handling the search functionality in the application.
 */
public class searchResultsController {
    
    @FXML private TextField searchTextBox;

    /**
     * Method to return the user to the primary view when the title card is clicked
     * @param event
     * @throws IOException
     */
    public void returnPrimary(MouseEvent event) throws IOException {
        List<Variant> variants = new ArrayList<>();

        variants.add(new Variant(1, Color.RED, Material.COTTON, Size.S, 50, 19.99));
        variants.add(new Variant(2, Color.BLUE, Material.POLYESTER, Size.M, 30, 24.99));
        variants.add(new Variant(3, Color.BLACK, Material.DENIM, Size.L, 40, 49.99));
        variants.add(new Variant(4, Color.WHITE, Material.COTTON, Size.XL, 25, 29.99));
        variants.add(new Variant(5, Color.GREEN, Material.LINEN, Size.M, 60, 34.99));
        variants.add(new Variant(6, Color.YELLOW, Material.SILK, Size.S, 20, 39.99));
        variants.add(new Variant(7, Color.GREY, Material.WOOL, Size.L, 70, 59.99));
        variants.add(new Variant(8, Color.BROWN, Material.LEATHER, Size.XL, 10, 89.99));
        variants.add(new Variant(9, Color.PURPLE, Material.COTTON, Size.S, 35, 21.99));
        variants.add(new Variant(10, Color.ORANGE, Material.POLYESTER, Size.M, 80, 27.99));
        
        // Get the current stage
        App.switchScene("primary", event);
        // Test (successful)
        System.out.println("Returning to homepage...");
    }

    /**
     * method to perform search from the search bar when the search button is clicked.
     * This method retrieves the text from the searchTextBox and prints it to the console.
     * Brings you to searchResults Page
     * @throws IOException if there is an error loading the fxml file
     */ 
    @FXML
    public void search(ActionEvent event) throws IOException
    {
        if(!searchTextBox.getText().isEmpty()) {
            System.out.println("Taking you to Search Results!");
            String searchItem = searchTextBox.getText();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("searchResultsView.fxml"));
                Parent root = loader.load();

                // Get the current stage
                Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

                // Set the new scene
                stage.setScene(new Scene(root));
                stage.setTitle("Checkout Page");
                stage.show();
                //test(successful)
            System.out.println("Searching for: " + searchItem);
        }
        else{
            System.out.println("Please enter a search term.");
        }
    }

    /**
     * method to perform a filtered search from the menu bar corresponding to the selected menu item
     * @param event the mouse event that triggers the method
     * @throws IOException if there is an error loading the fxml file
     */ 
    @FXML
    public void menuSearch(ActionEvent event) throws IOException
    {
        String searchItem = ((MenuItem)event.getSource()).getText();
        //test (successful)
        System.out.println("Searching for: " + searchItem);
    }
    
}
