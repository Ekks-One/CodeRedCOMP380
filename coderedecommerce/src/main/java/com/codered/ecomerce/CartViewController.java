/**
 * CodeRed E-Commerce Application
 * This {@code CheckoutController} class is used to handle the checkout operations of the application
 * it contains methods that initializes the checkout view, handles the checkout process,
 * and returns the user to the primary view.
 * 
 * @author CodeRed Team (Miguel)
 * @version 1.0
 * @see checkoutView.fxml
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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
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
 * CheckoutController controls the operation of the checkout page
 */
public class CartViewController extends App {

    @FXML
    private TextField fnameTextBox, lnameTextBox, addressTextBox, cityTextBox, zipTextBox, emailTextBox, phoneTextBox;

	//Population of the Choice Box
    @FXML
    private ChoiceBox<String> statesChoiceBox;
    @FXML
    private Button checkOutButton;
    @FXML 
    private TextField searchTextBox;
    @FXML
    private GridPane cartGridPane;
    @FXML private MenuBar menuBar;



    /**
     * Method to initialize the drop down menu that contains the states that are deliverable to the user
     * @param location the URL location of the FXML file
     * @param resources the ResourceBundle containing the resource for the FXML file
     */
    
    public void initialize() {
        try {
            populateGridPane();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void populateGridPane() throws IOException {
        CartManager.getInstance();
        List<Variant> cartItems = CartManager.getCartItems();
        List<Product> products = CentralShoppingSystem.getProducts();

        int row = 0;
        int col = 0;
        
        cartGridPane.getChildren().clear(); // Clear the grid pane before populating it
        
       
        
        List<Variant> uniqueCartItems = new ArrayList<>();
        for (Variant variant : cartItems) {
            if (!uniqueCartItems.contains(variant)) {
                uniqueCartItems.add(variant);
            }
        }

        for(Variant variant : uniqueCartItems) {


            
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

        // Create Label for the product name
        Label nameLabel = new Label(products.get(variant.getID()).getName());
        AnchorPane.setTopAnchor(nameLabel,10.0);
        AnchorPane.setLeftAnchor(nameLabel, 120.0);


        //Create Label for product price
        Label priceLabel = new Label("$" + variant.getPrice());
        priceLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: green;");
        AnchorPane.setTopAnchor(priceLabel,23.0);
        AnchorPane.setLeftAnchor(priceLabel, 120.0);

        //Create Label for the product color
        Label colorLabel = new Label("Color: " + variant.getColor());
        colorLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: black;");
        AnchorPane.setTopAnchor(colorLabel, 10.0);
        AnchorPane.setLeftAnchor(colorLabel, 250.0);

        //Create Label for the product size
        Label sizeLabel = new Label("Size: " + variant.getSize());
        sizeLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: black;");
        AnchorPane.setTopAnchor(sizeLabel, 30.0);
        AnchorPane.setLeftAnchor(sizeLabel, 250.0);

        //Create Label for material
        Label materialLabel = new Label("Material: " + variant.getMaterial());
        materialLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: black;");
        AnchorPane.setTopAnchor(materialLabel, 50.0);
        AnchorPane.setLeftAnchor(materialLabel, 250.0);

        // Create Label for the product quantity
        Label quantityLabel = new Label("Quantity: " + CartManager.getItemCount(variant));
        quantityLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: black;");
        AnchorPane.setTopAnchor(quantityLabel, 50.0);
        AnchorPane.setLeftAnchor(quantityLabel, 120.0);


        // Create a "Remove" button
        Button removeButton = new Button(" - ");
        removeButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
        AnchorPane.setTopAnchor(removeButton, 70.0);
        AnchorPane.setLeftAnchor(removeButton, 120.0);
        // Add an event handler to the "Remove" button
        removeButton.setOnAction(event -> {   
            // Remove item form cart
            CartManager.removeCartItem(variant);
            // Refresh the GridPane
            try {
                populateGridPane();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (cartItems.isEmpty()) {
                Label emptyCartLabel = new Label("Your cart is empty.");
                cartGridPane.add(emptyCartLabel, 0, 0);
            }
        });

        // Create a "Remove" button
        Button addButton = new Button(" + ");
        addButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
        AnchorPane.setTopAnchor(addButton, 70.0);
        AnchorPane.setLeftAnchor(addButton, 140.0);

        // Add an event handler to the "Remove" button
        addButton.setOnAction(event -> {   
            // Remove item form cart
            CartManager.addCartItem(variant);
            // Refresh the GridPane
            try {
                populateGridPane();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


        

        // Add all elements to the product pane
        productPane.getChildren().addAll(materialLabel,sizeLabel,colorLabel,quantityLabel, productImageView, nameLabel, priceLabel, addButton,removeButton);

        // Add the product pane to the grid
        cartGridPane.add(productPane, col, row);
        
        
            row++;
            if(row >= 4) {
                row = 0;
                col++; 
            }

        }
    }

    /**
     * Method to handle the checkout process when the user clicks the place order button
     * it checks if all the required fields are filled in and then proceeds to load the payment view
     * @param event
     * @throws IOException
     */


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

    @FXML
    public void checkoutView(ActionEvent event) throws IOException
    {
        System.out.println("Taking you to order checkout!");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("checkoutView.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
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
     * method to perform a filtered search from the menu bar corresponding to the selected menu item
     * @param event the mouse event that triggers the method
     * @throws IOException if there is an error loading the fxml file
     */ 
@FXML
    public void menuSearch(ActionEvent event) throws IOException {
        String searchItem = ((MenuItem)event.getSource()).getText();
        System.out.println("Searching for: " + searchItem);
    
        if (searchItem.equals("Tops")) {
            searchResults = SearchProducts.Search(searchItem);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("topsSearchView.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) menuBar.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Tops Search Results");
            stage.show();
    
        } else if (searchItem.equals("Bottoms")) {
            searchResults = SearchProducts.Search(searchItem);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("bottomsSearchView.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) menuBar.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Bottoms Search Results");
            stage.show();
        }
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

}