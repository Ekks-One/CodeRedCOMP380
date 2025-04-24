/**
 * Codered E-Commece System
 * this {@code PaymentViewController} class is used to handle the payment view of the application.
 * it contains methods to handle the payment functionality, including
 * initializing the view, confirming the payment, and returning to the homepage.
 * 
 * @Author CodeRed Team (Miguel, Xavier)
 * @version 1.0
 */
package com.codered.ecomerce;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.codered.ecomerce.model.CartManager;
import com.codered.ecomerce.model.CentralShoppingSystem;
import com.codered.ecomerce.model.Customer;
import com.codered.ecomerce.model.CustomerManager;
import com.codered.ecomerce.model.Product;
import com.codered.ecomerce.model.Variant;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
 * PaymentViewController controls the operations of the payment view page
 */
public class PaymentViewController extends App implements Initializable{

    @FXML
    private ChoiceBox<String> cardTypeChoiceBox;
    @FXML
    private ChoiceBox<String> cardDateMonthChoiceBox;
    @FXML
    private ChoiceBox<String> cardDateYearChoiceBox;

    @FXML
    private TextField cardNumTextBox, securityNumTextBox, zipCodeTextBox, cardHolderTextBox,
                      searchTextBox;

    @FXML
    private String cardNumber, cardSecurityCode, cardHolderName, cardType, cardDateMonth, cardDateYear, zipCode;

    @FXML
    private GridPane cartGridPane;
    @FXML private MenuBar menuBar;
    


    
    /**
     * Method to initialize the PaymentViewController class
     * @param location the URL location, 
     * @param resources the ResourceBundle
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            populateGridPane();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // Initialize the ChoiceBox with card types
        cardTypeChoiceBox.getItems().addAll(
            "Credit", "Debit", "Prepaid", "Gift Card"
        );
        cardTypeChoiceBox.setValue("Select Card Type"); //Default Text

        //Initialize the ChoiceBox with months
        cardDateMonthChoiceBox.getItems().addAll(
            "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"
        );
        cardDateMonthChoiceBox.setValue("MM"); 

        //Initialize the ChoiceBox with years
        cardDateYearChoiceBox.getItems().addAll(
            "2025", "2026", "2027", "2028", "2029", "2030", "2031", "2032", "2033", "2034", "2035"
        );
        cardDateYearChoiceBox.setValue("YYYY"); 

    }//end initialize


    public void populateGridPane() throws IOException {
        CartManager.getInstance();
        List<Variant> cartItems = CartManager.getCartItems();
        List<Product> products = CentralShoppingSystem.getProducts();
       
        
        int maxCol = 2;
        int row = 0;
        int col = 0;
        
        cartGridPane.getChildren().clear(); // Clear the grid pane before populating it
        
        // Loop through the products and create a new AnchorPane for each product
        for(Variant variant : cartItems) {

            // Limit the number of products displayed to 24
        
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
        // To be added once Variants Situation is figured out: products.get(variant.getID()).getName()
        Label nameLabel = new Label(products.get(variant.getID()).getName());
        AnchorPane.setTopAnchor(nameLabel,10.0);
        AnchorPane.setLeftAnchor(nameLabel, 120.0);

        // To be added once Variants Situation is figured out: variant.getPrice()
        Label priceLabel = new Label("$" + variant.getPrice());
        priceLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: green;");
        AnchorPane.setTopAnchor(priceLabel,40.0);
        AnchorPane.setLeftAnchor(priceLabel, 120.0);

        // Create a "Remove" button
        Button removeButton = new Button("Remove from Cart");
        removeButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
        AnchorPane.setTopAnchor(removeButton, 70.0);
        AnchorPane.setLeftAnchor(removeButton, 120.0);

        // Add an event handler to the "Remove" button
        removeButton.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to remove this item from the cart?");
            alert.setTitle("Remove Item");
            alert.showAndWait().ifPresent(response -> {
                if(response ==  ButtonType.OK) {
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
                }
            });
        });

        // Add all elements to the product pane
        productPane.getChildren().addAll(productImageView, nameLabel, priceLabel, removeButton);

        // Add the product pane to the grid
        cartGridPane.add(productPane, col, row);
        
        
        row++;
            if(row > 5) {
                row = 0;
                col++; 
            }
            if(col > maxCol) {
                break;
            }

        }
    }

/**
 * Method to confirm the payment and display the payment details
 * @param ActionEvent event that triggers the method
 * @throws IOException 
 */    
    @FXML
    public void confirmPayment(ActionEvent event) throws IOException {

        // Get the current customer from the CustomerManager
        Customer customer = CustomerManager.getCustomer();

        // Get the values from the text fields and choice boxes
        cardNumber = cardNumTextBox.getText();
        cardSecurityCode = securityNumTextBox.getText();
        cardHolderName = cardHolderTextBox.getText();
        cardType = cardTypeChoiceBox.getValue();
        cardDateMonth = cardDateMonthChoiceBox.getValue();
        cardDateYear = cardDateYearChoiceBox.getValue();
        zipCode = zipCodeTextBox.getText();

        try{
            // Check if any of the fields are empty or not selected, display an error message if so
            if(cardNumber.isEmpty() || cardSecurityCode.isEmpty() || cardHolderName.isEmpty() || cardType.equals("Select Card Type") || cardDateMonth.equals("Select Month") || cardDateYear.equals("Select Year")){
                System.out.println("Please fill out all fields.");
                //Display an alert to inform the user that all fields are required
                Alert missingInfoAlert = new Alert(Alert.AlertType.ERROR); // Create an error alert
                missingInfoAlert.setTitle("Something went wrong");               
                missingInfoAlert.setHeaderText("Missing required fields!");      
                missingInfoAlert.setContentText("Please fill in all fields.");
                missingInfoAlert.showAndWait();
            }else{
                
                // Test (successful)
                    System.out.println("Payment Successful! \n" +
                    "Card Holder Name: " + cardHolderName + "\n" +
                    "Card Number: " + cardNumber + "\n" +
                    "Card Security Code: " + cardSecurityCode + "\n" +
                    "Card Expiration Date: " + cardDateMonth + "/" + cardDateYear + "\n" +
                    "Card Zip Code: " + zipCode + "\n" +
                    "Card Type: " + cardType);

                    //Retrieves customer from Customer Class and formats the customer information to be displayed in the alert
                    String customerInfo = "Customer Name: " + customer.getFname() + " " + customer.getLname() + "\n" +
                    "Email: " + customer.getCustomerEmail() + "\n" +
                    "Shipping Address: " + String.join(", ", customer.getShippingAddress()) + "\n\n";

                    // Combines the customer information and payment details into a single string
                    // to be displayed in the alert
                    String confirmationText = customerInfo +
                    "Card Holder Name: " + cardHolderName + "\n" +
                    "Card Number: " + cardNumber + "\n" +
                    "Card Security Code: " + cardSecurityCode + "\n" +
                    "Card Expiration Date: " + cardDateMonth + "/" + cardDateYear + "\n" +
                    "Card Type: " + cardType;

                //Display an alert to confirm the order creation containing the payment details, order details
                    Alert OrderCreatedAlert = new Alert(Alert.AlertType.INFORMATION); // Create an information alert
                    OrderCreatedAlert.setTitle("Order Created Successfully!");
                    OrderCreatedAlert.setHeaderText("Order Details");
                    OrderCreatedAlert.setContentText(confirmationText); // Set the content text
                    OrderCreatedAlert.showAndWait();
            }//end else
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
             
        }

    }
    /**
     *  Method that returns the user to the homepage by clicking on the home title card
     * @param MouseEvent event that triggers the method
     * @throws IOException if there is an error loading the fxml file
     */
    @FXML
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
    
}
