/**
 * Codered E-Commerce System
 * This {@code PrimaryController} class is used to handle the primart view of the application
 * * It contains methods to handle the search functionality, menu bar functionality, and image click events.
 * * It also contains methods to initialize the view and set up the layout of the images in the grid.
 * * The class uses JavaFX to create the user interface and handle events.
 * 
 * @Author CodeRed Team (Miguel, Xavier, Alfredo, jesus)
 * @version 1.0
 * @see Primary.fxml
 * @see App.java
 * @see ItemViewController.java
 * @see checkoutView.java
 * @see searchView,java
 */
package com.codered.ecomerce;

import java.io.IOException;
import java.util.ArrayList;

import com.codered.ecomerce.enums.Color;
import com.codered.ecomerce.enums.Material;
import com.codered.ecomerce.enums.Size;
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
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

///* PrimaryController controls the primary page of the application. It handles the search functionality and image grid display.
/// @author(s) Miguel Alfaro, Alfredo Catzin, Xavier Ramos
/// @version 1.0
/// */
public class PrimaryController extends App{

    //declaration of FXML elements
    @FXML
    private TextField searchTextBox;
    @FXML
    private Button searchButton, cartViewButton;
    @FXML
    private AnchorPane headerAnchorP;
    @FXML
    private GridPane productGridPane;
    @FXML
    private ImageView homeTitleCard, 
                      imageV00, imageV10, imageV20,
                      imageV01, imageV11, imageV21,
                      imageV02, imageV12, imageV22;
    @FXML
    private StackPane stackP00, stackP10, stackP20,
                      stackP01, stackP11, stackP21,
                      stackP02, stackP12, stackP22;
    @FXML
    private MenuItem mensTops, mensBottoms, 
                     womensTops, womensBottoms, 
                     kidsTops, kidsBottoms,
                     aboutUs, ordersMenuItem;

    

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

            // Get the controller instance
            searchResultsController controller = loader.getController();

            // Pass the searchItem to the controller
            controller.setSearchItem(searchItem);
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
     * method to return to the primary view when the home title card is clicked
     * @param event the mouse even that triggers the method
     * @throws IOException if there is an error loading the fxml file
     */ 
    public void returnPrimary(MouseEvent event) throws IOException
    {
        App.switchScene("primary", event);
        //test (successful)
        System.out.println("Returning to homepage...");
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

    @FXML
    /** Method initialize is called automatically after fxml file loads
    * Can Reuse for other pages to switch fxml files  
    * Add all buttons, methods, etc that need to be implemented when loaded 
    */
    public void initialize() throws IOException {
        headerAnchorP.setStyle("-fx-background-color: red;");

        /*
         * Declaring 2D array to map the respective image views to their corresponding 
         * stack panes
         */
        ImageView[][] imageViews =
        {
            {imageV00, imageV10, imageV20},
            {imageV01, imageV11, imageV21},
            {imageV02, imageV12, imageV22}
        };
        
        StackPane[][] stackPanes =
        {
            {stackP00, stackP10, stackP20},
            {stackP01, stackP11, stackP21},
            {stackP02, stackP12, stackP22}  
        };

        /*
         * Nested for loop to iterate through the grid of images and set their fitWidth
         * and fitHeight properties to the stack panes' which ensures that the grid of images
         * on the home page will scale dynamically when the window is resized
         */
        for(int i = 0; i < imageViews.length; i++)
        {
            for(int j = 0; j < stackPanes[i].length; j++)
            {
                ImageView imageView = imageViews[i][j];
                StackPane stackPane = stackPanes[i][j];

                stackPane.prefWidthProperty().bind(productGridPane.widthProperty().divide(3));
                stackPane.prefHeightProperty().bind(productGridPane.heightProperty().divide(3));

                imageView.fitWidthProperty().bind(stackPane.widthProperty());
                imageView.fitHeightProperty().bind(stackPane.heightProperty());
                imageView.setPreserveRatio(false);
            }
        } 
    }
    
    /*
     * Method to handle the click event on the images within the gridPane and returns the
     * itemView page of the selected item, passing the image from the homepage to the itemView page
     */
    public void itemView(MouseEvent event) throws IOException
    {
        ImageView clickedImage = (ImageView) event.getSource();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("itemView.fxml"));
        Parent root = loader.load();

        ItemViewController controller = loader.getController();
        controller.setItemImage(clickedImage.getImage());

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    /** A method to populate the GridPane
     *  Method to populate the primary grid pane with product items from the database,
     *  If we decide to keep the stackPanes, then we will remove or edit this method
     *  @throws IOException if there is an error loading the fxml file
     *  */
    public void populateGridPane() throws IOException {
        ArrayList<Product> products = CentralShoppingSystem.getProducts();
        ArrayList<Variant> variants = new ArrayList<>();
        
        //Added as a Test to figure out Variant situation
        
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
            

        int row = 0;
        int col = 0;
        int maxCols = 3;
        int prodCount = 0;
        
        // Loop through the products and create a new AnchorPane for each product
        for(Variant variant : searchResults) {
            if(variant == null) {
                System.out.println("Null product found! Skipping...");
                continue;
            }

            // Limit the number of products displayed to 24
            if(prodCount >= 9) {
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

        // Create Button for adding to cart
        Button addCartButton = new Button("Add to Cart");
        AnchorPane.setTopAnchor(addCartButton, 120.0);
        AnchorPane.setLeftAnchor(addCartButton, 10.0);
        addCartButton.setOnAction(event -> {
            // Add the product to the cart

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
        productGridPane.add(productPane, col, row);
        
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
}
