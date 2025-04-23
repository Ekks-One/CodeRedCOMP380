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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
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
 * CheckoutController controls the operation of the checkout page
 */
public class CheckoutController extends App implements Initializable {

    @FXML
    private TextField fnameTextBox, lnameTextBox, addressTextBox, cityTextBox, zipTextBox, emailTextBox, phoneTextBox;

	//Population of the Choice Box
    @FXML
    private ChoiceBox<String> statesChoiceBox;
    @FXML
    private Button placeOrderButton, cartViewButton;
    @FXML 
    private TextField searchTextBox;
    @FXML
    private GridPane cartGridPane;
    @FXML
    private Label totalCostLabel;

    private String selectedState;
    private String firstName;
    private String lastName;
    private String address;
    private String email;
    private double totalCost = 0.0;
   

    /**
     * Method to initialize the drop down menu that contains the states that are deliverable to the user
     * @param location the URL location of the FXML file
     * @param resources the ResourceBundle containing the resource for the FXML file
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            populateGridPane();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        statesChoiceBox.getItems().addAll(
            "AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "DC", "FL", "GA", "HI",
            "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MN", "MS",
            "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH", "OK",
            "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV",
            "WI", "WY"
        );
        statesChoiceBox.setValue("Select State"); //Default Text
    }

    public void populateGridPane() throws IOException {
        CartManager.getInstance();
        List<Variant> cartItems = CartManager.getCartItems();
        List<Product> products = CentralShoppingSystem.getProducts();


        
        int row = 0;
        int col = 0;
        
        cartGridPane.getChildren().clear(); // Clear the grid pane before populating it
        
        // Loop through the products and create a new AnchorPane for each product
        for(Variant variant : cartItems) {

        
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
                    // Update the total cost
                    totalCost -= variant.getPrice();
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
        
        if(row == 2) {
            row = 0; // Reset column index to 0
            col++; // Move to the next row
        } else {
            row++; // Move to the next column
        }

        }
        totalCost = CartManager.getTotalPrice(); // Get the total cost label from the CartManager
        totalCostLabel.setText(totalCost + ""); // Set the total cost label to the total cost of the cart

    }
    /**
     * Method to handle the checkout process when the user clicks the place order button
     * it checks if all the required fields are filled in and then proceeds to load the payment view
     * @param event
     * @throws IOException
     */
    public void returnPayment(ActionEvent event) throws IOException {
        firstName = fnameTextBox.getText();
        lastName = lnameTextBox.getText();
        address = addressTextBox.getText();
        selectedState = statesChoiceBox.getValue();
        email = emailTextBox.getText();

        try {
            if(!firstName.isEmpty() && !lastName.isEmpty() && !address.isEmpty() && 
                !selectedState.equals("Select State") && !email.isEmpty()) {

                // Test (successful)
                System.out.println("Checkout Successful! \n" +
                    "Name: " + firstName + " " + lastName + "\n" +
                    "Address: " + address + ", " + selectedState + "\n" +
                    "Email: " + email);

                //Create new customer object and store the address and state as a string array
                Customer customer = new Customer(firstName, lastName, address, selectedState, email);
                //customer.setShippingAddress(new String[] {address, selectedState});

                //Store the customer globally so that it can be accessed by other classes
                CustomerManager.setCustomer(customer);

                // Load the new FXML file
                FXMLLoader loader = new FXMLLoader(getClass().getResource("paymentView.fxml"));
                Parent root = loader.load();

                // Get the current stage
                Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

                // Set the new scene
                stage.setScene(new Scene(root));
                stage.setTitle("Order Confirmation");
                stage.show();
            } 
            else {
                System.out.println("Please fill in all required fields.");
                        // Display an alert to inform the user that all fields are required
                        Alert missingInfoAlert = new Alert(Alert.AlertType.WARNING);
                        missingInfoAlert.setTitle("Missing Fields");
                        missingInfoAlert.setHeaderText("Please complete all fields.");
                        missingInfoAlert.setContentText("One or more fields are empty. Please fill them in before continuing.");
                        missingInfoAlert.showAndWait();
            }
            
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
     * Method to handle the action event when the user selects a state from the 
     * choice box. It retrieves the selected state and prints it to the console
     */
    @FXML
    private void getSelectedState(ActionEvent event) {
        String selectedState = statesChoiceBox.getValue();
        System.out.println("Selected State: " + selectedState);
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