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
import java.util.Collections;
import java.util.List;

import com.codered.ecomerce.enums.Brand;
import com.codered.ecomerce.enums.Category;
import com.codered.ecomerce.model.CentralShoppingSystem;
import com.codered.ecomerce.model.Product;
import com.codered.ecomerce.model.Variant;
import com.codered.ecomerce.sql.SearchProducts;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
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
    @FXML private MenuBar menuBar;

    private String searchText;
    private List<Variant> searchResults;

    @FXML
    public void initialize() throws IOException {
        if(searchResults == null)
        {
            searchResults = new ArrayList<>();
        }
        //populates the grid pane with the search results
        populateGridPane(searchResults);
    }


    /// Method to set the search item text in the label
    public void setSearchItem(String searchText) {
        this.searchText = searchText;

        if(searchLabel != null) {
            searchLabel.setText(searchText);
        }
    }

    public void setSearchResults(List<Variant> searchResults)
    {
        this.searchResults = searchResults;
        try 
        {
            populateGridPane(searchResults);
        } catch (IOException e) {
            e.printStackTrace();
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

        FXMLLoader loader = new FXMLLoader(getClass().getResource("cartView.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * Method to populate the grid pane with product items from the database
     * @throws IOException if there is an error loading the fxml file
     */

    public void populateGridPane(List<Variant> results) throws IOException {
        searchGridPane.getChildren().clear();

        if(results == null || results.isEmpty()) {
            System.out.println("No search results found.");
            return;
        }

        List<Product> products = CentralShoppingSystem.getProducts();
        
            
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
            if(prodCount >= 52) {
                break;
            }
        
        // Create a new AnchorPane for each product
        AnchorPane productPane = new AnchorPane();
        productPane.setStyle("-fx-border-color: black; -fx-padding: 10 10 10 10;");
        productPane.setOnMouseClicked(event -> {
            
            try {
                FXMLLoader loader = new FXMLLoader(App.class.getResource("itemView.fxml"));
                Parent root = loader.load();
                ItemViewController itemViewController = loader.getController();
                itemViewController.setVariant(variant); 
                Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Item View");
                stage.show();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            //test (successful)
        System.out.println("Image clicked for product: " + products.get(variant.getID()).getName());
    });

        //Create ImageView for the product image
        ImageView productImageView = new ImageView();
        productImageView.setImage(new Image(getClass().getResource("/com/codered/ecomerce/images/Item 0-1.png").toExternalForm()));
        productImageView.setFitWidth(100);
        productImageView.setFitHeight(100);
        productImageView.setPreserveRatio(true);
        AnchorPane.setTopAnchor(productImageView, 10.0);
        AnchorPane.setLeftAnchor(productImageView, 10.0);

        
        Label categoryLabel = new Label("Category: " + Category.values()[products.get(variant.getID()).getCategoryID()].getLabel().toLowerCase());
        categoryLabel.setStyle("-fx-font-size: 12px;");
        AnchorPane.setTopAnchor(categoryLabel, 120.0);
        AnchorPane.setLeftAnchor(categoryLabel, 10.0);

        Label stockLabel = new Label("Stock: " + variant.getStock());
        stockLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: red;");
        AnchorPane.setTopAnchor(stockLabel, 80.0);
        AnchorPane.setLeftAnchor(stockLabel, 10.0);

        Label brandLabel = new Label("Brand: " + Brand.values()[products.get(variant.getID()).getBrandID()].getLabel().toLowerCase());
        brandLabel.setStyle("-fx-font-size: 12px;");
        AnchorPane.setTopAnchor(brandLabel, 100.0);
        AnchorPane.setLeftAnchor(brandLabel, 10.0);


        

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
        productPane.getChildren().addAll(productImageView, nameLabel, priceLabel, categoryLabel,stockLabel, brandLabel);

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

    public void AppendSearch(ArrayList<Variant> result, String search){
        result.addAll(SearchProducts.Search(search));
        Collections.shuffle(result);
    }
    /**
     * method to perform search from the search bar when the search button is clicked.
     * This method retrieves the text from the searchTextBox and prints it to the console.
     * Brings you to searchResults Page
     * @throws IOException if there is an error loading the fxml file
     */ 
    @FXML
    public void search(ActionEvent event) throws IOException {
        String searchItem = searchTextBox.getText().trim();
        App.search(searchItem, event);
    }

    /**
     * method to perform a filtered search from the menu bar corresponding to the selected menu item
     * @param event the mouse event that triggers the method
     * @throws IOException if there is an error loading the fxml file
     */ 
    @FXML
    public void menuSearch(ActionEvent event) throws IOException {
        String searchItem = ((MenuItem)event.getSource()).getText();
        System.out.println("Searching for: " + searchItem);
    
        if (searchItem.equals("Tops")) {
            ArrayList<Variant> tResults = SearchProducts.Search("Tops");
            tResults.addAll(SearchProducts.Search("Shirts"));
            tResults.addAll(SearchProducts.Search("Gown"));
            Collections.shuffle(tResults);
            searchResults.clear();
            searchResults.addAll(tResults);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("topsSearchView.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) menuBar.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Tops Search Results");
            stage.show();
    
        } else if (searchItem.equals("Bottoms")) {
            ArrayList<Variant> tResults = SearchProducts.Search("Pants");
            tResults.addAll(SearchProducts.Search("Skirts"));
            tResults.addAll(SearchProducts.Search("Sweatpants"));
            Collections.shuffle(tResults);
            searchResults.clear();
            searchResults.addAll(tResults);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("bottomsSearchView.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) menuBar.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Bottoms Search Results");
            stage.show();
        }
    }
}