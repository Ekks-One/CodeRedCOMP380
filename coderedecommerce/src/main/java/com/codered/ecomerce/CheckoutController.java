/**
 * CodeRed E-Commerce Application
 * This {@code CheckoutController} class is used to handle the checkout operations of the application
 * it contains methods that initializes the checkout view, handles the checkout process,
 * and returns the user to the primary view.
 * 
 * @author CodeRed Team (Miguel, Xavier)
 * @version 1.0
 * @see checkoutView.fxml
 */
package com.codered.ecomerce;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.codered.ecomerce.model.CartManager;
import com.codered.ecomerce.model.CentralShoppingSystem;
import com.codered.ecomerce.model.Customer;
import com.codered.ecomerce.model.CustomerManager;
import com.codered.ecomerce.model.Product;
import com.codered.ecomerce.model.Variant;
import com.codered.ecomerce.sql.SearchProducts;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
public class CheckoutController extends App implements Initializable {

    @FXML
    private TextField fnameTextBox, lnameTextBox, addressTextBox, cityTextBox, zipTextBox, emailTextBox, phoneTextBox, zipCodeTextBox;

	//Population of the Choice Box
    @FXML
    private ChoiceBox<String> statesChoiceBox;
    @FXML
    private Button placeOrderButton, cartViewButton;
    @FXML 
    private TextField searchTextBox;
    @FXML private MenuBar menuBar;
    @FXML
    private GridPane cartGridPane;
    @FXML
    private Label totalCostLabel;

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


        int maxCol= 2;
        int row = 0;
        int col = 0;
        
        cartGridPane.getChildren().clear(); // Clear the grid pane before populating it
        
        List<Variant> uniqueCartItems = new ArrayList<>();
        // Loop through the products and create a new AnchorPane for each product
        for(Variant variant : cartItems) {
            if (!uniqueCartItems.contains(variant)) {
                uniqueCartItems.add(variant);
            }
        }
        cartGridPane.getColumnConstraints().clear(); // Clear the column constraints before populating the grid
        cartGridPane.getRowConstraints().clear(); // Clear the row constraints before populating the grid
        for(Variant variant: uniqueCartItems) {
            
            // Create a new AnchorPane for each product
            AnchorPane productPane = new AnchorPane();
            productPane.setStyle("-fx-border-color: black; -fx-padding: 10 10 10 10;");
            productPane.setPrefWidth(300);
            productPane.setPrefHeight(150);
            
        
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

            // Create Label for the product quantity
            Label quantityLabel = new Label("Quantity: " + CartManager.getItemCount(variant));
            quantityLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: black;");
            AnchorPane.setTopAnchor(quantityLabel, 55.0);
            AnchorPane.setLeftAnchor(quantityLabel, 120.0);

            // To be added once Variants Situation is figured out: variant.getPrice()
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
            productPane.getChildren().addAll(materialLabel, colorLabel, sizeLabel, quantityLabel, productImageView, nameLabel, priceLabel, removeButton,addButton);

            // Add the product pane to the grid
            cartGridPane.add(productPane, col, row);
        
            row++;
            if(row > 2) {
                    row = 0;
                    col++; 
            }
            if(col > maxCol) {
                    break;
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
    @FXML
    public void returnPayment(ActionEvent event) {
        try {
            System.out.println("Proceed to Payment button clicked.");
    
            // Retrieve values from the TextFields and ChoiceBox
            String firstName = fnameTextBox.getText();
            String lastName = lnameTextBox.getText();
            String address = addressTextBox.getText();
            String selectedState = statesChoiceBox.getValue();
            String email = emailTextBox.getText();
            String city = cityTextBox.getText();
            String zipCode = zipCodeTextBox.getText();
            

            // Validate inputs

            if(firstName.isEmpty() && lastName.isEmpty()) {
                Alert missingNameAlert = new Alert(Alert.AlertType.WARNING);
                missingNameAlert.setTitle("Missing Name Fields");
                missingNameAlert.setHeaderText("Please complete the name fields.");
                missingNameAlert.setContentText("One or more name fields are empty. Please fill them in before continuing.");
                missingNameAlert.showAndWait();
                return;
            }

            if (email.isEmpty()) {
                Alert emptyEmailAlert = new Alert(Alert.AlertType.WARNING);
                emptyEmailAlert.setTitle("Missing Email");
                emptyEmailAlert.setHeaderText("Email field is empty.");
                emptyEmailAlert.setContentText("Please enter your email address before continuing.");
                emptyEmailAlert.showAndWait();
                return;
            }
            
            if (!email.contains("@")) {
                Alert missingAtSymbolAlert = new Alert(Alert.AlertType.WARNING);
                missingAtSymbolAlert.setTitle("Invalid Email");
                missingAtSymbolAlert.setHeaderText("Missing '@' symbol.");
                missingAtSymbolAlert.setContentText(email+ " must contain an '@' symbol. Please correct it before continuing.");
                missingAtSymbolAlert.showAndWait();
                return;
            }

            String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
            if (!email.matches(emailRegex)) {
                Alert invalidEmailAlert = new Alert(Alert.AlertType.WARNING);
                invalidEmailAlert.setTitle("Invalid Email");
                invalidEmailAlert.setHeaderText("Please enter a valid email address.");
                invalidEmailAlert.setContentText(email+ " is not valid. Please correct it before continuing.");
                invalidEmailAlert.showAndWait();
                return;
            }

            if(address.isEmpty() && selectedState.equals("Select State") && city.isEmpty() && zipCode.isEmpty()) {
                Alert missingAddressAlert = new Alert(Alert.AlertType.WARNING);
                missingAddressAlert.setTitle("Missing Address Fields");
                missingAddressAlert.setHeaderText("Please complete the address fields.");
                missingAddressAlert.setContentText("One or more address fields are empty. Please fill them in before continuing.");
                missingAddressAlert.showAndWait();
                return;
            }

            // Checks all fields are filled

            if (!firstName.isEmpty() && !lastName.isEmpty() && !address.isEmpty() && !selectedState.equals("Select State") && !email.isEmpty() && !city.isEmpty() && !zipCode.isEmpty()) {
    
                System.out.println("All required fields are filled. Proceeding to load payment view...");

                Customer customer = new Customer(firstName, lastName, address, city, zipCode, selectedState, email);
                CustomerManager.setCustomer(customer);
    
                // Load the payment view
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/codered/ecomerce/paymentView.fxml"));
                Parent root = loader.load();
                Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);
                informationAlert.setTitle("Current Customer Information");
                informationAlert.setHeaderText("Customer Information");
                informationAlert.setContentText("firstName: " + firstName + "\n" +
                        "lastName: " + lastName + "\n" +
                        "address: " + address + "\n" +
                        "selectedState: " + selectedState + "\n" +
                        "email: " + email + "\n" +
                        "city: " + city + "\n" +
                        "zipCode: " + zipCode);
                informationAlert.showAndWait();
    
                // Get the current stage
                Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
    
                // Set the new scene
                stage.setScene(new Scene(root));
                stage.setTitle("Order Confirmation");
                stage.show();
            } else {
                System.out.println("Please fill in all required fields.");
                Alert missingInfoAlert = new Alert(Alert.AlertType.WARNING);
                missingInfoAlert.setTitle("Missing Fields");
                missingInfoAlert.setHeaderText("Please complete all fields.");
                missingInfoAlert.setContentText("One or more fields are empty. Please fill them in before continuing.");
                missingInfoAlert.showAndWait();
            }
        } catch (IOException e) {
            System.out.println("Error loading payment view: " + e.getMessage());
            e.printStackTrace();
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText("Unable to load payment view.");
            errorAlert.setContentText("An error occurred while trying to load the payment view. Please try again.");
            errorAlert.showAndWait();
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