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

import com.codered.ecomerce.model.CentralShoppingSystem;
import com.codered.ecomerce.model.Product;
import com.codered.ecomerce.model.Variant;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * This class is responsible for handling the search functionality in the application.
 */
public class searchResultsController {
    
    @FXML private TextField searchTextBox;
    @FXML private GridPane searchGridPane;

    public void initialize() {
    ArrayList<Product> products = CentralShoppingSystem.getProducts();
    

    int row = 0;
    int col = 0;
    int maxCols = 4;
    int prodCount = 0;
    
    for (Product product: products) {
        if (product == null) {
            System.out.println("Null product found! Skipping...");
            continue;
        }

        if(prodCount >= 24) {
            break;
        }
    
    AnchorPane productPane = new AnchorPane();
    productPane.setStyle("-fx-border-color: black; -fx-padding: 10 10 10 10;");

    //Create ImageView for the product image
    ImageView productImageView = new ImageView();
    productImageView.setImage(new Image(getClass().getResource("/com/codered/ecomerce/images/Item 0-1.png").toExternalForm()));
    productImageView.setFitWidth(100);
    productImageView.setFitHeight(100);
    productImageView.setPreserveRatio(true);
    AnchorPane.setTopAnchor(productImageView, 10.0);
    AnchorPane.setLeftAnchor(productImageView, 10.0);

    Button addCartButton = new Button("Add to Cart");
    AnchorPane.setTopAnchor(addCartButton, 120.0);
    AnchorPane.setLeftAnchor(addCartButton, 10.0);
    addCartButton.setOnAction(event -> {
        // Add to cart logic here
        System.out.println("Added " + product.getName() + " to cart.");
    });


    //Create Label for the product name
    Label nameLabel = new Label(product.getName());
    AnchorPane.setTopAnchor(nameLabel,10.0);
    AnchorPane.setLeftAnchor(nameLabel, 120.0);

    Label priceLabel = new Label("$" + product.getBasePrice());
    priceLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: green;");
    AnchorPane.setTopAnchor(priceLabel,40.0);
    AnchorPane.setLeftAnchor(priceLabel, 120.0);

    productPane.getChildren().addAll(productImageView, nameLabel, priceLabel, addCartButton);

    searchGridPane.add(productPane, col, row);
    
    col++;
    if(col>= maxCols) {
        col = 0;
        row++;
    }

    prodCount++;
    
}


}


    /**
     * Method to return the user to the primary view when the title card is clicked
     * @param event
     * @throws IOException
     */
    public void returnPrimary(MouseEvent event) throws IOException {
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
