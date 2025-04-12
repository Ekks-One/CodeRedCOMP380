package com.codered.ecomerce;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;

public class CheckoutController extends App implements Initializable {

    @FXML
    private TextField fnameTextBox, lnameTextBox, addressTextBox, cityTextBox, zipTextBox, emailTextBox, phoneTextBox;

	//Population of the Choice Box
    @FXML
    private ChoiceBox<String> statesChoiceBox;

    private String selectedState;
    private String firstName;
    private String lastName;
    private String address;
    private String email;

    

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

    //* Gathers all info inputed from the page */
    public void placeOrder() throws Exception {
        firstName = fnameTextBox.getText();
        lastName = lnameTextBox.getText();
        address = addressTextBox.getText();
        selectedState = statesChoiceBox.getValue();
        email = emailTextBox.getText();
        
        if(!firstName.isEmpty() && !lastName.isEmpty() && !address.isEmpty() && 
            !selectedState.equals("Select State") && !email.isEmpty()) {
            System.out.println("Checkout Successful! \n" +
                "Name: " + firstName + " " + lastName + "\n" +
                "Address: " + address + ", " + selectedState + "\n" +
                "Email: " + email);
        } else {
            System.out.println("Please fill in all required fields.");
        }
    }

    //* Returns to primary(HomePage) */
    public void returnPrimary(MouseEvent event) throws IOException {
        // Get the current stage
        App.switchScene("primary", event);
        // Test (successful)
        System.out.println("Returning to homepage...");
    }


    // Example event handler
    @FXML
    private void handleSomething(ActionEvent event) {
        String selectedState = statesChoiceBox.getValue();
        System.out.println("Selected State: " + selectedState);
    }
}