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

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;


/**
 * ItemViewController controls the item view page of the application. It handles the item view functionality
 */
public class ItemViewController extends App{

    @FXML
    private Text itemNameText;
    @FXML
    private Text itemPriceText;
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
    private AnchorPane leftAnchorPane;
    
    private String selectedColor;
    private String selectedSize;
    private String itemName;
    private int quantityAmmount;
    private String itemID;

    /*
     * Method to initialize the page and set the default values for the item view page
     */
    public void initialize()
    {
        itemImageView.fitWidthProperty().bind(imageStackPane.widthProperty());
        itemImageView.fitHeightProperty().bind(imageStackPane.heightProperty());    
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
    public void setItemID(String itemID)
    {
        this.itemID = itemID;
    }

    public void setItemImage(Image image)
    {
        itemImageView.setImage(image);
    }

    /**
     *  Method to select the color of the item the customer has selected
     * @param event the action event that triggers the method
     * @throws IOException if there is an error loading the fxml file
     */
    @FXML
    public void colorSelect(ActionEvent event) throws Exception {
        ToggleButton selectedButton = (ToggleButton) event.getSource();
        selectedColor = selectedButton.getText();
        System.out.println("Color " + selectedColor + " selected!");
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
     * Method to add functionality of the menu serch bar within the itemView page
     * @param even the mouse event that triggers the method
     * @throws IOException if there is an error loading the fxml file
     */
        public void menuSearch(ActionEvent event) throws IOException
    {
        String searchItem = ((MenuItem)event.getSource()).getText();
        //test (successful)
        System.out.println("Searching for: " + searchItem);
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
}
