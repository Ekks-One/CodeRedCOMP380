/**
 * CodeRed E-Commerce Application
 * This {@code searchResultsController} class is responsible for handling the search functionality in the application.
 * It allows users to search for items using a search bar and menu options.
 * 
 * @author CodeRed Team (Xavier, Afredo, Jesus)
 * @version 1.2
 * @created on 04/14/2025
 */ 
package com.codered.ecomerce;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.codered.ecomerce.model.CartManager;
import com.codered.ecomerce.model.CentralShoppingSystem;
import com.codered.ecomerce.model.Product;
import com.codered.ecomerce.model.Variant;
import com.codered.ecomerce.sql.SearchProducts;

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
public class searchResultsController extends App{
    
    @FXML private TextField searchTextBox;
    @FXML private GridPane searchGridPane;
    @FXML private Label searchLabel;

    private String searchText;

    public void initialize() throws IOException {
        populateGridPane();
    }

    /// Method to set the search item text in the label
    public void setSearchItem(String searchText) {
        this.searchText = searchText;

        if(searchLabel != null) {
            searchLabel.setText(searchText);
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
     * Method to handle the click event on the view cart button and returns the 
     * cart page
     * @param event the mouse event that triggers the method
     * @throws IOException if there is an error loading the fxml file
     */
    @FXML
    public void cartView(ActionEvent event) throws IOException
    {
        System.out.println("Taking you to your cart!");
        App.setRoot("cartView");
    }

    /**
     * Method to populate the grid pane with product items from the database
     * @throws IOException if there is an error loading the fxml file
     */

    public void populateGridPane() throws IOException {
        List<Product> products = CentralShoppingSystem.getProducts();
        ArrayList<Variant> variants = new ArrayList<>();
        CartManager cart = CartManager.getInstance();
        
            
        int row = 0;
        int col = 0;
        int maxCols = 4;
        int prodCount = 0;
        
        // Loop through the products and create a new AnchorPane for each product
        for(Variant variant : searchResults) {
            if(variant == null) {
                System.out.println("Null product found! Skipping...");
                continue;
            }

            // Limit the number of products displayed to 24
            if(prodCount >= 24) {
                break;
            }
        
        // Create a new AnchorPane for each product
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
        productImageView.setOnMouseClicked(event -> {
            
                try {
                    App.switchScene("itemView", event);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                //test (successful)
            System.out.println("Image clicked for product: " + products.get(variant.getID()).getName());
        });

        // Create Button for adding to cart
        Button addCartButton = new Button("Add to Cart");
        AnchorPane.setTopAnchor(addCartButton, 120.0);
        AnchorPane.setLeftAnchor(addCartButton, 10.0);
        addCartButton.setOnAction(event -> {
            // Add the product to the car

            CartManager.addCartItem(variant); // Directly call the method without assignment
            
            // To be added once Variants Situation is figured out: products.get(variant.getID()).getName()
            System.out.println("Added " + products.get(variant.getID()).getName() + " to cart.");
        });


        // Create Label for the product name
        // To be added once Variants Situation is figured out: products.get(variant.getID()).getName()
        Label nameLabel = new Label(products.get(variant.getID()).getName());
        AnchorPane.setTopAnchor(nameLabel,10.0);
        AnchorPane.setLeftAnchor(nameLabel, 120.0);

        // To be added once Variants Situation is figured out: variant.getPrice()
        Label priceLabel = new Label("$" + variant.getPrice());
        priceLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: green;");
        AnchorPane.setTopAnchor(priceLabel,40.0);
        AnchorPane.setLeftAnchor(priceLabel, 120.0);

        // Add all elements to the product pane
        productPane.getChildren().addAll(productImageView, nameLabel, priceLabel, addCartButton);

        // Add the product pane to the grid
        searchGridPane.add(productPane, col, row);
        
        // Update the row and column for the next product
        col++;
        if(col>= maxCols) {
            col = 0;
            row++;
        }
        //Increment the product count
        prodCount++;

        }
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
            searchResults = SearchProducts.Search(searchItem);

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
