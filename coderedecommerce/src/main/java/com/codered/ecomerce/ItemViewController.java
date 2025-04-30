/**
 * CodeRed E-Commerce Application
 * This {@code ItemViewController} class is used to handle the item view of the application.
 * It contains methods to handle the item view functionality, including adding items to the cart, selecting colors and sizes, 
 * and navigating back to the homepage.
 * 
 * @authors CodeRed Team (Xavier, Miguel, Alfredo)
 * @version 1.0
 * @see itemView.fxml
 */
package com.codered.ecomerce;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.codered.ecomerce.enums.Color;
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
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


/**
 * ItemViewController controls the item view page of the application. It handles the item view functionality
 */
public class ItemViewController extends App{

    @FXML
    private Label itemNameText, itemPriceText;
    @FXML
    private Button searchButton, checkoutButton; 
    @FXML
    private TextField quantityTextField, searchTextBox;
    @FXML
    private TextArea productDescriptionBox;
    @FXML 
    private ImageView itemImageView;
    @FXML 
    private StackPane imageStackPane;
    @FXML 
    private AnchorPane leftAnchorPane, colorAnchorPane;
    @FXML 
    private MenuBar menuBar;
    
    private String selectedColor;
    private String selectedSize;
    private String itemName;
    private int quantityAmmount;
    
    private int itemID;
    private ArrayList<Color> itemColors = new ArrayList<>();
    
    private Variant currentVariant;
    private CartManager cartItems = CartManager.getInstance();

    /*
     * Method to initialize the page and set the default values for the item view page
     */
    public void initialize()
    {
        
        itemImageView.fitWidthProperty().bind(imageStackPane.widthProperty());
        itemImageView.fitHeightProperty().bind(imageStackPane.heightProperty());
        createColorToggleButtons(itemColors);
        
    }

    /**
     * Method to add the selected item to the customers cart
     * @throws IOException if there is an error loading the fxml file
     */
    @FXML
    public void AddtoCart() throws Exception {
        
        
        itemName = itemNameText.getText();
        quantityAmmount = Integer.parseInt(quantityTextField.getText());
        if(selectedColor != null && selectedSize != null && quantityAmmount != 0) {
            System.out.println(quantityTextField.getText() +": "+ itemName + " added to cart! \n"+ "Color:"+ selectedColor + "\nSize: " + selectedSize);
        } else {
            System.out.println("Please select a color and size before adding to cart.");
        }
        for(int i = 0; i < quantityAmmount; i++) {

            CartManager.addCartItem(currentVariant);
        }
        
        
    }


    private void createColorToggleButtons(ArrayList<Color> colors) {
        if(colors == null || colors.isEmpty()) {
            System.out.println("No Colors available"); // Default color if none are available
        }
        
    

        ToggleGroup toggleGroup = new ToggleGroup();
        double layoutX = 0;
        for (Color color : colors) {
            ToggleButton colorButton = new ToggleButton(color.toString());
            colorButton.setStyle("-fx-background-color: " + color + "; -fx-text-fill: white;");
            colorButton.setPrefHeight(52);
            colorButton.setPrefWidth(47);
            colorButton.setLayoutX(layoutX);
            colorButton.setLayoutY(0);
            colorButton.setToggleGroup(toggleGroup);

            colorButton.setOnAction(event -> {

                Toggle selectedToggle = toggleGroup.getSelectedToggle();
                if (selectedToggle != null) {
                    selectedColor = ((ToggleButton) selectedToggle).getText();
                    System.out.println("Color " + selectedColor + " selected!");
                }
            });
            colorAnchorPane.getChildren().add(colorButton);
            layoutX += 60;
        }
    }

    /**
     *  Method that returns the user to the homepage by clicking on the home title card
     * @param MouseEvent "click" event that triggers the method
     * @throws IOException if there is an error loading the fxml file
     */
    @FXML
    public void returnPrimary(MouseEvent event) throws IOException {
        // Get the current stage
        App.switchScene("primary", event);
        // Test (successful)
        System.out.println("Returning to homepage...");
    }

    /*
     * Helper method to switch to the itemView of a particular item selected from the homepage 
     * and to return the image of the selected item
     */
    public void setItemID(int itemID)
    {
        this.itemID = itemID;
    }

    public void setItemImage(Image image)
    {
        itemImageView.setImage(image);
    }

    
    /** 
     * Method to select the size of the item the customer has selected
     * @param event the action event that triggers the method
     * @throws IOException if there is an error loading the fxml file
     */
    @FXML
    public void sizeSelect(ActionEvent event) throws Exception {
        Button selctedButton = (Button) event.getSource();
        selectedSize = selctedButton.getText();
        System.out.println("Size " + selectedSize + " selected!");
    }


    /**
     * Method to set the variant of the item selected from the homepage
     * @param variant the variant of the item selected
     */
    // This method sets the item name and price based on the selected variant.
    public void setVariant(Variant variant) {
        currentVariant = variant;
        List<Product> products = CentralShoppingSystem.getProducts();
        itemNameText.setText(products.get(variant.getID()).getName());
        itemPriceText.setText("$" + variant.getPrice());
        itemColors = products.get(variant.getID()).getColors();
        setItemID(variant.getID());
        if(itemColors == null) {
            itemColors = new ArrayList<>();
            itemColors.add(Color.PINK); // Default color if none are available
        }
        createColorToggleButtons(itemColors);
    }

    /** 
     * Method to add 1 to the current quantity in the TextField
     * @throws IOException if there is an error loading the fxml file 
     */
    public void addQuantity() throws Exception {
        quantityTextField.setText(String.valueOf(Integer.parseInt(quantityTextField.getText()) + 1));
    }

    /**
     * Method to subtract 1 from the current quantity in the TextField
     * @throws IOException if there is an error loading the fxml file
     */
    public void subtractQuantity() throws Exception {
        if(Integer.parseInt(quantityTextField.getText()) > 0) {
            quantityTextField.setText(String.valueOf(Integer.parseInt(quantityTextField.getText()) - 1));
        } else {
            System.out.println("Quantity cannot be less than 0.");
        }
    }

    /**
     * Method to add functionality of the checkout button within the itemView page
     * @param event the mouse event that triggers the method
     * @throws IOException if there is an error loading the fxml file
     * @see checkoutView.fxml
     */
    public void checkoutView(ActionEvent event) throws IOException
    {
       FXMLLoader loader = new FXMLLoader(getClass().getResource("checkoutView.fxml"));
                Parent root = loader.load();

                // Get the current stage
                Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

                // Set the new scene
                stage.setScene(new Scene(root));
                stage.setTitle("Checkout Page");
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
     * Method to add functionality of the menu serch bar within the itemView page
     * @param even the mouse event that triggers the method
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
