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
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

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
    private Button placeOrderButton;
    @FXML 
    private TextField searchTextBox;

    private String selectedState;
    private String firstName;
    private String lastName;
    private String address;
    private String email;

    /**
     * Method to initialize the drop down menu that contains the states that are deliverable to the user
     * @param location the URL location of the FXML file
     * @param resources the ResourceBundle containing the resource for the FXML file
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        statesChoiceBox.getItems().addAll(
            "AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "DC", "FL", "GA", "HI",
            "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MN", "MS",
            "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH", "OK",
            "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV",
            "WI", "WY"
        );
        statesChoiceBox.setValue("Select State"); //Default Text
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
                System.out.println("Checkout Successful! \n" +
                    "Name: " + firstName + " " + lastName + "\n" +
                    "Address: " + address + ", " + selectedState + "\n" +
                    "Email: " + email);
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